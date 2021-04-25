arr = list(range(100, 0, -1))
def quicksort(arr):
  if(len(arr) <= 1):
    return arr
  else:
    x = round(len(arr)/2)
    left = []
    right = []

    for i, i_value in enumerate(arr):
      if(i == x):
        continue
      else:
        if(i_value <= arr[x]):
          left.append(i_value)
        else:
          right.append(i_value)
    return quicksort(left) + [arr[x]] + quicksort(right)
print(arr)
print(quicksort(arr))
