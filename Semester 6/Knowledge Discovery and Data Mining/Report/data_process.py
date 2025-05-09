import os
import cv2
from imagededup.methods import PHash
from shutil import move

# Thư mục chứa ảnh (cùng cấp với file code)
BASE_DIR = os.path.dirname(os.path.abspath(__file__))
IMAGE_DIR = os.path.join(BASE_DIR, 'images')
REMOVED_DIR = os.path.join(IMAGE_DIR, 'removed')
os.makedirs(REMOVED_DIR, exist_ok=True)

# ---------- 1. Loại bỏ ảnh trùng lặp ----------
def remove_duplicates(image_dir):
    phasher = PHash()
    duplicates = phasher.find_duplicates(image_dir=image_dir, max_distance_threshold=10)

    removed = set()
    for img, dups in duplicates.items():
        # Nếu ảnh đã bị xóa ở vòng trước, bỏ qua
        if img in removed:
            continue
        # Giữ lại ảnh `img`, xóa các ảnh trùng với nó trong `dups`
        for dup in dups:
            if dup not in removed and dup != img:
                removed.add(dup)
                move(os.path.join(image_dir, dup), os.path.join(REMOVED_DIR, dup))
    print(f"Đã loại bỏ {len(removed)} ảnh trùng lặp (mỗi nhóm giữ lại 1 ảnh).")


# ---------- 2. Loại bỏ ảnh mờ ----------
def is_blurry(image_path, threshold=100.0):
    image = cv2.imread(image_path, cv2.IMREAD_GRAYSCALE)
    if image is None:
        return False  # Không xử lý được ảnh, bỏ qua
    lap_var = cv2.Laplacian(image, cv2.CV_64F).var()
    return lap_var < threshold

# ---------- Hàm chính ----------
def clean_dataset():
    # Bước 1: Loại bỏ ảnh trùng lặp
    remove_duplicates(IMAGE_DIR)

    # Bước 2: Loại bỏ ảnh mờ
    for img_name in os.listdir(IMAGE_DIR):
        img_path = os.path.join(IMAGE_DIR, img_name)
        if os.path.isfile(img_path) and is_blurry(img_path):
            move(img_path, os.path.join(REMOVED_DIR, img_name))
            print(f"Ảnh mờ: {img_name}")

    print("Hoàn tất quá trình làm sạch dữ liệu.")

# ---------- Thực thi ----------
if __name__ == "__main__":
    clean_dataset()
