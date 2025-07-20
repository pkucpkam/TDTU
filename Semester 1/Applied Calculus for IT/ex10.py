import matplotlib.pyplot as plt
import numpy as np
import math

#a
for k in range(2,14,2):
    def f(x):
        return x**2 + k
    #main
    x_array = np.arange(-10, 10, 0.0001)
    y_array = list(map(f,x_array))
    plt.plot(x_array, y_array, color ='magenta')
plt.title("Cau 6a")
plt.grid()
plt.show()

#b
for k in range(2,14,2):
    def fb(x):
        return (x + k)**2
    #main
    x_array = np.arange(-10,10,0.1)
    y_array = list(map(fb,x_array))
    plt.plot(x_array,y_array, color ='blue')
plt.title("Cau 6b")
plt.grid()
plt.show()

#d
def fd1(x):
    return (x**3)
x_array = np.arange(-10,10,1)
y_array = list(map(fd1,x_array))
plt.plot(x_array,y_array, color = 'red')

def fd2(x):
    return ((x+1)**3) - 1
x_array = np.arange(-10,10,1)
y_array = list(map(fd2,x_array))
plt.plot(x_array,y_array,color = "blue")

plt.title("Cau 6d")
plt.grid()
plt.show()

#e
def fe1(x):
    return (x**(2/3))
x_array = np.arange(-20,20,1)
y_array = list(map(fe1,x_array))
plt.plot(x_array,y_array, color = 'red')

def fe2(x):
    return ((x-1)**(2/3)) - 1
x_array = np.arange(-20,20,1)
y_array = list(map(fe2,x_array))
plt.plot(x_array,y_array,color = "blue")

plt.title("Cau 6e")
plt.grid()
plt.show()

#f
def ff1(x):
    return (1/2)*(x+1) + 5
x_array = np.arange(-20,20,1)
y_array = list(map(ff1,x_array))
plt.plot(x_array,y_array, color = 'red')

def ff2(x):
    return (1/2)*(x)
x_array = np.arange(-20,20,1)
y_array = list(map(ff2,x_array))
plt.plot(x_array,y_array, color = 'blue')

plt.title("Cau 6f")
plt.grid()
plt.show()

#g

def fg1(x):
    return 1/(x**2)
x_array = np.arange(-20,20,1)
y_array = list(map(fg1,x_array))
plt.plot(x_array,y_array, color = 'red')

def fg2(x):
    return (1/((x+2)**2)) - 1
x_array = np.arange(-20,20,1)
y_array = list(map(fg2,x_array)) 
plt.plot(x_array,y_array,color = 'blue')

plt.title("Cau 6g")
plt.grid()
plt.show()


#h
def fh1(x):
    return 1 - x**3
x_array = np.arange(-20,20,1)
y_array = list(map(fh1,x_array))
plt.plot(x_array,y_array, color = 'red')

def fh2(x):
    return 1 - (2*x)**3
x_array = np.arange(-20,20,1)
y_array = list(map(fh2,x_array))
plt.plot(x_array,y_array, color = 'blue')

plt.title("Cau 6h")
plt.grid()
plt.show()

#i
def fi1(x):
    return math.sqrt(x + 1)
x_array = np.arange(0,20,1)
y_array = list(map(fi1,x_array))
plt.plot(x_array,y_array, color = 'red')

def fi2(x):
    return math.sqrt((x/4)+1)
x_array = np.arange(0,20,1)
y_array = list(map(fi2,x_array))
plt.plot(x_array,y_array, color = 'blue')
plt.title("Cau 6i")
plt.grid()
plt.show()

#j
def fj1(x):
    return math.sqrt(x + 1)
x_array = np.arange(0,20,1)
y_array = list(map(fj1,x_array))
plt.plot(x_array,y_array, color = 'red')

def fj2(x):
    return 3*(math.sqrt((x/4)+1))
x_array = np.arange(0,20,1)
y_array = list(map(fj2,x_array))
plt.plot(x_array,y_array, color = 'blue')
plt.title("Cau 6j")
plt.grid()
plt.show()