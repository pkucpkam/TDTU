#bài này là tự nhập mảng ạ


import numpy as np
import array as arr
a = 0
arr1=[]
arr2=[]
b = 0
print("Nhap so phan tu mang 1")
n1 = int(input())
print("Nhap cac phan tu cua mang")
for i in range(0, n1):
	ele = int(input())
	arr1.append(ele)

print("Nhap so phan tu mang 2")
n2 = int(input())
print("Nhap cac phan tu cua mang")
for i in range(0, n2):
	ele = int(input())
	arr2.append(ele)

print("Cac so trung nhau la : ")
for i in range(0,n1) :
    a = arr1[i]
    for j in range(0,n2) :
        b = arr2[j]
        if a == b :
            print(a)
