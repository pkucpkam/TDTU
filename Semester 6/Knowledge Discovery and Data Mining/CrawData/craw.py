import sys
import tkinter as tk
from tkinter import filedialog, messagebox
import threading
import time
import os
import requests
import urllib.parse

def main():
    if len(sys.argv) > 2:
        raw_header = sys.argv[1]
        folder_path = sys.argv[2]
        start_download_cli(raw_header, folder_path)
    else:
        launch_gui()

def start_download_cli(raw_header, folder_path):
    # Function to handle command line operation
    status_label = None  # Not using GUI, so status label is not needed
    countdown_label = None
    start_download(raw_header, folder_path, status_label, countdown_label)

def launch_gui():
    # Setup GUI as usual
    global root
    root = tk.Tk()
    root.title("Nhóm Data 4 Life : Tải ảnh Real-time")
    setup_gui(root)
    root.mainloop()

def setup_gui(root):
    # Create GUI components here
    # Raw Header input
    tk.Label(root, text="Raw Header:").pack()
    raw_header_entry = tk.Text(root, height=10, width=50)
    raw_header_entry.pack()

    # Folder selection
    tk.Label(root, text="Chọn ổ đĩa lưu trữ:").pack()
    folder_entry = tk.Entry(root, width=50)
    folder_entry.pack()
    choose_button = tk.Button(root, text="Chọn Thư Mục", command=lambda: folder_entry.insert(0, filedialog.askdirectory()))
    choose_button.pack()

    # Start and Stop buttons in the same row
    button_frame = tk.Frame(root)
    button_frame.pack(pady=10)

    start_button = tk.Button(button_frame, text="Bắt Đầu", command=lambda: start_download(raw_header_entry.get("1.0", tk.END), folder_entry.get(), status_label, countdown_label))
    start_button.pack(side=tk.LEFT, padx=10)

    stop_button = tk.Button(button_frame, text="Dừng", command=lambda: stop_download(status_label))
    stop_button.pack(side=tk.LEFT, padx=10)

    # Status and countdown labels
    status_label = tk.Label(root, text="Trạng thái: Đang chờ lệnh bắt đầu...", fg="blue")
    status_label.pack()

    countdown_label = tk.Label(root, text="Chờ...")
    countdown_label.pack()

is_downloading = False
download_interval = 12

def parse_raw_headers(raw_header):
    headers = {}
    cookies = {}
    referer = ""
    host = ""
    base_image_url = ""

    lines = raw_header.splitlines()
    for line in lines:
        if line.startswith("Referer:"):
            referer = line.split("Referer: ")[1].strip()
        if line.startswith("Cookie:"):
            raw_cookies = line.split("Cookie: ")[1].strip()
            cookie_pairs = raw_cookies.split("; ")
            for pair in cookie_pairs:
                key, value = pair.split("=", 1)
                cookies[key] = value
        if line.startswith("Host:"):
            host = line.split("Host: ")[1].strip()
        if line.startswith("User-Agent:"):
            headers["User-Agent"] = line.split("User-Agent: ")[1].strip()
        if line.startswith("GET "):
            request_line = line.split(" ")[1]
            base_image_url = request_line.split("&t=")[0]

    headers['Referer'] = referer
    headers['Host'] = host

    return headers, cookies, base_image_url

def decode_cam_location(referer):
    parsed_url = urllib.parse.urlparse(referer)
    query_params = urllib.parse.parse_qs(parsed_url.query)
    if 'camLocation' in query_params:
        cam_location_encoded = query_params['camLocation'][0]
        cam_location = urllib.parse.unquote(cam_location_encoded)
        return cam_location
    return "default_folder"

def download_image(timestamp, headers, cookies, base_image_url, folder, status_label):
    full_url = f"http://{headers['Host']}{base_image_url}&t={timestamp}"
    cookie_string = "; ".join([f"{key}={value}" for key, value in cookies.items()])
    headers['Cookie'] = cookie_string

    try:
        response = requests.get(full_url, headers=headers)
        if response.status_code == 200:
            file_name = f"{folder}/camera_{timestamp}.jpg"
            with open(file_name, 'wb') as file:
                file.write(response.content)
            print(f"Download successful: {file_name}")
            if status_label:
                status_label.config(text=f"Downloaded: {os.path.basename(file_name)} successfully.", fg="green")
        else:
            print(f"Error: Failed to download image (Error code {response.status_code})")
            if status_label:
                status_label.config(text=f"Error: Failed to download image (Error code {response.status_code})", fg="red")
    except Exception as e:
        print(f"Exception occurred: {e}")
        if status_label:
            status_label.config(text=f"Exception occurred: {e}", fg="red")

def start_download(raw_header, folder_path, status_label, countdown_label):
    global is_downloading
    is_downloading = True

    headers, cookies, base_image_url = parse_raw_headers(raw_header)
    referer = headers['Referer']
    cam_location_folder = decode_cam_location(referer)

    folder = os.path.join(folder_path, cam_location_folder)
    if not os.path.exists(folder):
        os.makedirs(folder)

    def download_loop():
        while is_downloading:
            timestamp = int(time.time() * 1000)
            download_image(timestamp, headers, cookies, base_image_url, folder, status_label)

            for remaining_time in range(download_interval, 0, -1):
                if not is_downloading:
                    break
                countdown_label.config(text=f"Chờ {remaining_time} giây trước lần tải tiếp theo...")
                time.sleep(1)

    threading.Thread(target=download_loop, daemon=True).start()

def stop_download(status_label):
    global is_downloading
    is_downloading = False
    status_label.config(text="Đã dừng tải xuống.", fg="red")

if __name__ == "__main__":
    main()
