import matplotlib.pyplot as plt
import numpy as np
import math

def f1(x):
    return math.sqrt(1-(abs(x)-1)**2)
#main
x_array = np.arange(-2, 2, 0.0001)
y_array = list(map(f1,x_array))

def f2(x):
    return -3*(math.sqrt(1-math.sqrt((abs(x)/2))))
#main
x1_array = np.arange(-2, 2, 0.0001)
y1_array = list(map(f2,x1_array))

plt.plot(x_array, y_array, color ='magenta')
plt.plot(x1_array, y1_array, color ='red')
plt.grid()
plt.show()




