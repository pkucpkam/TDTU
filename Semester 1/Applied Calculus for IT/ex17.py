import sympy as sp 
import numpy as np 
import matplotlib.pyplot as plt

x = sp.symbols('x')
#a
fa = (x**2 - x - 6) / (x - 3)
lma = sp.limit(fa,x,0)
if (lma == 5):
    print("Function is cotinuous at 0")
else:
    print("Function is not cotinuous at 0")

#b
fb = (x**2 - x - 2) / (x - 2)
lmb = sp.limit(fb,x,0)
if (lmb == 2):
    print("Function is cotinuous at 2")
else:
    print("Function is not cotinuous at 2")

#c
fc = (x**2 - x - 2) / (x - 2)
lmc = sp.limit(fc,x,0)
if (lmc == 1):
    print("Function is cotinuous at 2")
else:
    print("Function is not cotinuous at 2")
