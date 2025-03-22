import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.neighbors import KNeighborsClassifier
from sklearn.metrics import accuracy_score

# Read data from the iris.data file
# Change the file path if the file is not in the same directory as this script
file_path = 'iris.data'
column_names = ['sepal_length', 'sepal_width', 'petal_length', 'petal_width', 'species']

# Load the dataset
data = pd.read_csv(file_path, header=None, names=column_names)

# Split features and labels
X = data[['sepal_length', 'sepal_width', 'petal_length', 'petal_width']]  # Features
y = data['species']  # Labels (flower species)

# Split data into training and testing sets
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.3, random_state=42)

# Initialize the KNN model with k = 3
knn = KNeighborsClassifier(n_neighbors=3)

# Train the model
knn.fit(X_train, y_train)

# Make predictions on the test set
y_pred = knn.predict(X_test)

# Evaluate model accuracy
accuracy = accuracy_score(y_test, y_pred)

# Print results
print(f"Model accuracy: {accuracy * 100:.2f}%")

# Example prediction with new sample data
new_data = [[5.1, 3.5, 1.4, 0.2]]  # New input
predicted_class = knn.predict(new_data)
print(f"Predicted flower species for new data {new_data}: {predicted_class[0]}")
