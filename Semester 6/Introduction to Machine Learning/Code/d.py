import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.neighbors import KNeighborsRegressor
from sklearn.preprocessing import LabelEncoder, StandardScaler
from sklearn.metrics import mean_squared_error

# Read data from the abalone.data file
file_path = 'abalone.data'  # Make sure to set the correct file path
columns = ['Sex', 'Length', 'Diameter', 'Height', 'Whole_weight', 'Shucked_weight', 'Viscera_weight', 'Shell_weight', 'Rings']
data = pd.read_csv(file_path, header=None, names=columns)

# Display the first few rows to check the data
print(data.head())

# Encode the categorical variable 'Sex'
label_encoder = LabelEncoder()
data['Sex'] = label_encoder.fit_transform(data['Sex'])  # M: 0, F: 1, I: 2

# Split features (X) and target (y)
X = data.drop(columns=['Rings'])  # All columns except 'Rings'
y = data['Rings']  # Target column is 'Rings'

# Standardize the data
scaler = StandardScaler()
X_scaled = scaler.fit_transform(X)  # Normalize numerical features

# Split data into training and testing sets
X_train, X_test, y_train, y_test = train_test_split(X_scaled, y, test_size=0.3, random_state=42)

# Initialize the KNN model
knn = KNeighborsRegressor(n_neighbors=3)

# Train the model
knn.fit(X_train, y_train)

# Make predictions on the test set
y_pred = knn.predict(X_test)

# Evaluate the model using Mean Squared Error (MSE)
mse = mean_squared_error(y_test, y_pred)
print(f"Mean Squared Error: {mse}")

# Example prediction for a new sample
new_data = [[0, 0.5, 0.4, 0.1, 0.5, 0.2, 0.1, 0.15]]  # New sample data
new_data_scaled = scaler.transform(new_data)  # Normalize the new sample data
predicted_rings = knn.predict(new_data_scaled)
print(f"Predicted number of rings for new data {new_data}: {predicted_rings[0]}")
