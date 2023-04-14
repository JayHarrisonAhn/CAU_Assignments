arr = list(range(100, 0, -1))
def insertionsort(arr):
  result = arr
  for i in range(1, len(result)):
    for j in range(i, 0, -1):

      if(result[j-1] < result[j]):
        break
      else:
        temp = result[j]
        result[j] = result[j-1]
        result[j-1] = temp
  return result
print(arr)
print(insertionsort(arr))
