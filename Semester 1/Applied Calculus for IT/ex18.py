import sympy as sp 
import math

x = sp.symbols('x')
f = x**3 - 3*x + 1
#a
y0 = f.subs(x,3)
df = sp.diff(f,x)
slope = df.subs(x,3)
y_tagentLine = slope*(x - 3) + y0
print("TagentLine in case a is ", y_tagentLine)
sp.plot(f,y_tagentLine,(x,-10, 10))

#b
x0 = sp.solve(df - 9,x)
for i in x0:
    y1 = f.subs(x,i)
    y_tagentLine_b = 9*(x - i) + y1
    sp.plot(f,y_tagentLine_b,(x,-5,5))

#c
slope = df.subs(x,2/3)
y_tagentLine = slope*(x - 2/3) - 1
print("TagentLine in case c is ", y_tagentLine)
sp.plot(f,y_tagentLine,(x,-5, 5))