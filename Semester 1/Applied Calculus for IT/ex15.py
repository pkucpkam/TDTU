import sympy as sp 
import numpy as np 
import matplotlib.pyplot as plt

x = sp.symbols('x')
f1 = x**2 - 7
lm1 = sp.limit(f1,x,1)

if (lm1 == -6):
    print("Function is continous at 1")
else:
        print("Function is not continous at 1")


f2 = (2*x - 3)**(1/2) 
lm2 = sp.limit(f2,x,2)
if (lm2 == 1):
    print("Function is continous at 2")
else:
        print("Function is not continous at 2")
