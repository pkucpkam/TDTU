import numpy as np

data = [
    [5, 45, 'H'],
    [5.11, 26, 'L'],
    [5.6, 30, 'M'],
    [5.9, 34, 'M'],
    [4.8, 40, 'H'],
    [5.8, 36, 'M'],
    [5.3, 19, 'L'],
    [5.8, 18, 'M'],
    [5.5, 23, 'L'],
    [5.6, 32, 'M'],
]

def euclidean_distance(point1, point2):
    return np.sqrt((point1[0] - point2[0])**2 + (point1[1] - point2[1])**2)

def predict_size(height, age, data, k=3):
    distances = []
    for item in data:
        dist = euclidean_distance([height, age], item[:2])
        distances.append((dist, item[2])) 
    
    distances.sort(key=lambda x: x[0])
    
    k_nearest = distances[:k]
    
    size_counts = {}
    for _, size in k_nearest:
        size_counts[size] = size_counts.get(size, 0) + 1
    
    predicted_size = max(size_counts, key=size_counts.get)
    return predicted_size, k_nearest

new_height = 5.5  
new_age = 38      
predicted_size, nearest_neighbors = predict_size(new_height, new_age, data)

print(f"Với height = {new_height} feet và age = {new_age}:")
print(f"- Size quần áo dự đoán là: {predicted_size}")
print("- 3 điểm gần nhất:")
for i, (dist, size) in enumerate(nearest_neighbors):
    print(f"  {i+1}. Khoảng cách: {dist:.2f}, Size: {size}")