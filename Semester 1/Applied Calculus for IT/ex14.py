import sympy as sp 
import math

t = sp.symbols('t')
f = 10**6 + (10**4)*t - (10**3)*(t**2)
df = sp.diff(f,t)
dfa = df.subs(t,0)
dfb = df.subs(t,5)
dfc = df.subs(t,10)

print("Growth rates at 0 hour : ", dfa)
print("Growth rates at 5 hour : ", dfb)
print("Growth rates at 10 hour : ", dfc)