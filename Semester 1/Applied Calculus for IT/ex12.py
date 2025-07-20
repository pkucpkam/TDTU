import numpy as np 
arr1 = np.array(range(0,100))
arr2 = np.array(range(50,100))
for i in range(0,100) :
    a = arr1[i]
    for j in range(0,50) :
        b = arr2[j]
        if a == b :
            print(a)

#đề không cho mảng nên em lấy tạm 2 mảng này ạ