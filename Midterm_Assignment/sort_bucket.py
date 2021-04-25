import math
arr = list(range(100, 0, -1))
def bucketsort(arr, buckets):
  result = [[] for i in range(buckets)]
  arr_min = min(arr)
  arr_max = max(arr)
  arr_range = arr_max - arr_min
  bucket_range = arr_range/buckets

  for i in arr:
    index = math.floor((i-arr_min)/bucket_range)
    if(index == buckets):
      index -= 1
    insert_index = len(result[index])
    for j_index, j in enumerate(result[index]) :
      if(j >= i):
        insert_index = j_index
        break
    result[index].insert(insert_index, i)

  result_return = []
  for i in result:
    result_return += i

  return result_return
print(arr)
print(bucketsort(arr, 10))
