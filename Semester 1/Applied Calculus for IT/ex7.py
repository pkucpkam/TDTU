arr1 = [1,2,3,4,5,6,7,8,9]
chan = 0;
le = 0;
for x in arr1 :
    if x % 2 == 0 :
        chan += 1;
    else :
        le += 1;
print("Number of even numbers : ",chan)
print("Number of odd number : ",le)