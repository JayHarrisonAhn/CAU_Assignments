arr = list(range(100, 0, -1))
def mergesort(arr):
  if(len(arr) == 1):
    return arr
  else:
    left = arr[:round(len(arr)/2)]
    right = arr[round(len(arr)/2):]

    left = mergesort(left)
    right = mergesort(right)

    resultArr = []
    l = r = 0
    for _ in range(0, len(arr)):

      if(r >= len(right)):
        resultArr.append(left[l])
        l += 1
        if(l >= len(left)):
          for _ in range(r, len(right)):
            resultArr.append(right[r])
            r += 1
          break

      else:
        resultArr.append(right[r])
        r += 1
        if(r >= len(right)):
          for _ in range(l, len(left)):
            resultArr.append(left[l])
            l += 1
          break
    return resultArr
print(arr)
print(mergesort(arr))
