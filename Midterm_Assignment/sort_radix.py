arr = list(range(100, 0, -1))
def radixsort(arr):
  maxval = max(arr)
  exponent = 1

  result = arr

  while(maxval / exponent >= 1):
    result = countsort(result, exponent)
    exponent *= 10
  return result

def countsort(arr, exponent):
  count = [0,0,0,0,0,0,0,0,0,0]
  result = [0 for i in range(len(arr))]

  for i in arr:
    index = round(((i % (exponent * 10)) - (i % (exponent))) / exponent)
    count[index] += 1

  for i, _ in enumerate(count[:-1]):
    count[i+1] += count[i]

  for i in reversed(arr):
    index = round(((i % (exponent * 10)) - (i % (exponent))) / exponent)
    count[index] -= 1
    result[count[index]] = i
    
  return result
print(arr)
print(radixsort(arr))
