from flask import Flask, render_template, Response, request, jsonify, redirect, url_for
import cv2
import dlib
import numpy as np
import json
from scipy.spatial import distance
import os


app = Flask(__name__)

predictor_path = "shape_predictor_68_face_landmarks.dat"

# Khởi tạo dlib's face detector và facial landmark predictor
detector = dlib.get_frontal_face_detector()
predictor = dlib.shape_predictor(predictor_path)


#Biến global
match_found = False
user = None

def shape_to_np(shape):
    coords = np.zeros((68, 2), dtype=int)
    for i in range(68):
        coords[i] = (shape.part(i).x, shape.part(i).y)
    return coords

def calculate_distances(landmarks):
    distances = []
    num_points = landmarks.shape[0]
    for i in range(num_points):
        for j in range(i + 1, num_points):
            distance_ij = distance.euclidean(landmarks[i], landmarks[j])
            distances.append(distance_ij)
    return distances

def compare_landmarks(landmarks, stored_landmarks):
    dist_threshold = 150 #Mức xác định
    distances = calculate_distances(landmarks)
    stored_distances = stored_landmarks
    dist_diff = np.linalg.norm(np.array(distances) - np.array(stored_distances))
    print(dist_diff)
    if dist_diff < dist_threshold:
        return True, dist_diff
    else:
        return False, dist_diff

def load_landmarks_data(username):
    file_path = f"{username}.json"
    if os.path.exists(file_path):
        with open(file_path, 'r') as f:
            landmarks_data = json.load(f)
        return landmarks_data
    else:
        return []

def gen_frames():
    global match_found
    camera = cv2.VideoCapture(0)
    while True:
        success, frame = camera.read()  # Đọc frame từ webcam
        if not success:
            break                                                                                                                                              
        else:
            gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
            rects = detector(gray, 0)
            for rect in rects:
                x1, y1, x2, y2 = rect.left(), rect.top(), rect.right(), rect.bottom()
                cropped_face = frame[y1:y2, x1:x2]
                if cropped_face.size == 0:
                    continue
                gray_cropped = cv2.cvtColor(cropped_face, cv2.COLOR_BGR2GRAY)
                rects_cropped = detector(gray_cropped, 0)
                
                for rect_cropped in rects_cropped:
                    shape = predictor(gray_cropped, rect_cropped)
                    landmarks = shape_to_np(shape)
                    landmarks_data = load_landmarks_data(user)
                    match_found = False
                    for stored_landmarks in landmarks_data: 
                        is_match, dist_diff = compare_landmarks(landmarks, stored_landmarks)
                        if is_match:
                            match_found = True
                            cv2.putText(frame, "Match found! Distance difference: {}".format(dist_diff), (x1, y1 - 10), cv2.FONT_HERSHEY_SIMPLEX, 0.7, (0, 255, 0), 2)
                            break
                    if not match_found:
                        cv2.putText(frame, "No match found.", (x1, y1 - 10), cv2.FONT_HERSHEY_SIMPLEX, 0.7, (0, 0, 255), 2)
                    # for (x, y) in landmarks:
                    #     cv2.circle(cropped_face, (x, y), 2, (0, 255, 0), -1)
            ret, buffer = cv2.imencode('.jpg', frame)
            frame = buffer.tobytes()
            yield (b'--frame\r\n'
                   b'Content-Type: image/jpeg\r\n\r\n' + frame + b'\r\n')

@app.route('/')
def index():
    return render_template('register.html')

@app.route('/login', methods=['POST'])
def login():
    username = request.form['username']
    global user
    user = username
    filename = f"{username}.json"
    if os.path.exists(filename):
        return redirect(url_for('verify_face', username=username))
    else:
        return jsonify({"match_found": False})

@app.route('/verify_face/<username>')
def verify_face(username):
    return render_template('verify.html', username=username)

@app.route('/video_feed')
def video_feed():
    return Response(gen_frames(), mimetype='multipart/x-mixed-replace; boundary=frame')

@app.route('/check_match')
def check_match():
    global match_found
    if match_found:
        return jsonify({"match_found": True})
    else:
        return jsonify({"match_found": False})

@app.route('/complete')
def complete():
    return render_template('complete.html')

if __name__ == '__main__':
    app.run(debug=True)
