import sympy as sp 
import numpy as np 
import matplotlib.pyplot as plt

x = sp.symbols('x')
print("f(x) is continuous for all x # ", end = '')
#a
fa = (x**2 - x - 6) / (x - 3)
#at point x = 0
lma = sp.limit(fa,x,0)
if (lma != 5):
    print(0, end = '')
#at point x != 0 
for a in np.arange(-100,100,1):
    if (a != 0):
        lma_a = sp.limit(fa,x,a)
        fa_a = fa.subs(x,a)
        if (lma_a != fa_a):
            print("," + str(a), end = '')
print()

#b
print("f(x) is continuous for all x # ",end ='')
fb = (x**3 - 8) / (x**2 - 4)
#at point x = 2
lmb1 = sp.limit(fb,x,2)
if (lmb1 != 3):
    print(2,end = '')
#at point x = -2
lmb2 = sp.limit(fb,x,-2)
if (lmb2 != 4):
    print(-2,end = '')
#at point x != 2 and x != -2 
for a in np.arange(-100,100,1):
    if (a != 2 and a != -2):
        lmb = sp.limit(fb,x,a)
        fb_b = fb.subs(x,a)
        if (lmb != fb_b):
            print("," +  str(a), end ='')   
print()       


print("f(x) is continuous for all x # ",end ='')
#c
fc = (x**2 - x - 6) / (x - 3)
#at point x = 2
lmc = sp.limit(fc,x,2)
if (lmc != 1):
    print(2,end ='')
#at point x != 2
for a in np.arange(-100,100,1):
    if (a != 2):
        lmc1 = sp.limit(fc,x,a)
        fc1 = fc.subs(x,a)
        if (lmc1 != fc1):
            print("," +  str(a), end ='')
print()


print("f(x) is continuous for all x # ",end = '')
#d
fd = 1 / (x**2)
#at point x = 0
lmd = sp.limit(fd,x,0)
if (lma != 1):
    print(0,end ='')
#at point x != 0 
for a in np.arange(-100,100,1):
    if (a != 0):
        lmd1 = sp.limit(fd,x,a)
        fd1 = fd.subs(x,a)
        if (lmd1 != fd1):
            print("," +  str(a), end = '')

