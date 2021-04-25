arr = list(range(100, 0, -1))
def bubblesort(arr):
  result = arr
  for i in range(0, len(arr) - 1):
    for j in range(0, len(arr) - i - 1):
      if(result[j] > result[j+1]):
        temp = result[j]
        result[j] = result[j+1]
        result[j+1] = temp
  return result
print(arr)
print(bubblesort(arr))
