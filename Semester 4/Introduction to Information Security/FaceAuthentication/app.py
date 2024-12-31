import os
import cv2
import dlib
import numpy as np
import json
import base64
from flask import Flask, render_template, Response, request, jsonify, redirect, url_for

app = Flask(__name__)

# Đường dẫn tới file shape_predictor_68_face_landmarks.dat
predictor_path = "shape_predictor_68_face_landmarks.dat"
img_dir = 'img'  # Thư mục để lưu các bức ảnh chụp

os.makedirs(img_dir, exist_ok=True)

detector = dlib.get_frontal_face_detector()
predictor = dlib.shape_predictor(predictor_path)

landmarks_data = {}
captured_landmarks = {} 

def shape_to_np(shape, dtype="int"):
    coords = np.zeros((68, 2), dtype=dtype)
    for i in range(68):
        coords[i] = (shape.part(i).x, shape.part(i).y)
    return coords

# Hàm phát hiện khuôn mặt, vẽ khung, thêm điểm đặc trưng
def detect_faces_and_draw_landmarks(frame):
    gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
    rects = detector(gray, 0)

    frame_height, frame_width = frame.shape[:2]
    box_width, box_height = 230, 200
    start_x = (frame_width - box_width) // 2
    start_y = (frame_height - box_height) // 2
    end_x = start_x + box_width
    end_y = start_y + box_height

    cv2.rectangle(frame, (start_x, start_y), (end_x, end_y), (255, 0, 0), 2)
    cv2.putText(frame, "Align your face in the box", (start_x, start_y - 10), cv2.FONT_HERSHEY_SIMPLEX, 0.7, (255, 0, 0), 2)

    for rect in rects:
        x, y, w, h = rect.left(), rect.top(), rect.width(), rect.height()
        cv2.rectangle(frame, (x, y), (x + w, y + h), (0, 255, 0), 2)

        shape = predictor(gray, rect)
        shape = shape_to_np(shape)
        captured_landmarks.setdefault(username, []).append(shape)  # Lưu điểm đặc trưng của từng bức ảnh theo username

        # Vẽ các điểm đặc trưng
        for (x, y) in shape:
            cv2.circle(frame, (x, y), 2, (0, 0, 255), -1)
    
    return frame

def crop_face(frame, rect):
    x, y, w, h = rect.left(), rect.top(), rect.width(), rect.height()
    cropped_face = frame[y:y+h, x:x+w]
    return cropped_face

# Hàm lấy frame từ camera
def generate_frames():
    cap = cv2.VideoCapture(0)
    while True:
        ret, frame = cap.read()
        if not ret:
            break
        else:
            frame = detect_faces_and_draw_landmarks(frame)
            ret, buffer = cv2.imencode('.jpg', frame)
            frame = buffer.tobytes()
            yield (b'--frame\r\n'
                   b'Content-Type: image/jpeg\r\n\r\n' + frame + b'\r\n')
    cap.release()

def calculate_distances(landmarks):
    distances = []
    num_points = landmarks.shape[0]
    for i in range(num_points):
        for j in range(i + 1, num_points):
            distance = np.linalg.norm(landmarks[i] - landmarks[j])
            distances.append(distance)
    return [distances]

@app.route('/')
def index():
    return render_template('register.html')

@app.route('/register', methods=['POST'])
def register():
    global username
    username = request.form['username']  # Lưu username vào biến global
    return redirect(url_for('scan_face', username=username))

@app.route('/scan_face/<username>')
def scan_face(username):
    return render_template('index.html', username=username)

@app.route('/video_feed')
def video_feed():
    return Response(generate_frames(), mimetype='multipart/x-mixed-replace; boundary=frame')

@app.route('/capture', methods=['POST'])
def capture():
    global username

    data = request.json
    angle = data['angle']
    frame_data = base64.b64decode(data['frame'])
    frame = np.frombuffer(frame_data, dtype=np.uint8)
    frame = cv2.imdecode(frame, cv2.IMREAD_COLOR)

    gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
    rects = detector(gray, 0)
    if len(rects) > 0:
        rect = rects[0]
        cropped_face = crop_face(frame, rect)

        shape = predictor(gray, rect)
        shape = shape_to_np(shape)
        captured_landmarks.setdefault(username, []).append(shape)

        img_filename = os.path.join(img_dir, f"face_{angle}.jpg")
        cv2.imwrite(img_filename, cropped_face)
        print(f"Image saved: {img_filename}")

        _, buffer = cv2.imencode('.jpg', cropped_face)
        frame_base64 = base64.b64encode(buffer).decode('utf-8')

        return jsonify({"message": f"Captured {angle} angle", "frame": frame_base64})
    else:
        return jsonify({"message": "No face detected"}), 400



@app.route('/save', methods=['GET'])
def save():
    global captured_landmarks

    if len(captured_landmarks.get(username, [])) > 0:  # Kiểm tra xem đã có landmarks của username hay chưa
        avg_landmarks = np.mean(captured_landmarks[username], axis=0)
        distances = calculate_distances(avg_landmarks)
        landmarks_data[username] = []  # Thêm danh sách khoảng cách vào landmarks_data dưới tên username

        if os.path.exists(f'{username}.json'):  # Kiểm tra xem tệp JSON của username đã tồn tại hay chưa
            os.remove(f'{username}.json')
            print(f"Old {username}.json file removed.")  # Debug output

        with open(f'{username}.json', 'w') as f:
            json.dump(distances, f, ensure_ascii=False, indent=4)
            print(f"Landmarks distances saved to {username}.json")  # Debug output
    else:
        print("No captured landmarks distances to save")  # Debug output

    return jsonify({"message": f"Landmarks distances saved to {username}.json"})


# Check file exist
@app.route('/check_username_exist')
def check_username_exist():
    username = request.args.get('username')
    if os.path.exists(f"{username}.json"):
        return jsonify({"exists": True})
    else:
        return jsonify({"exists": False})

if __name__ == '__main__':
    app.run(debug=True)
