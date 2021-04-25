import time, math, sort_bubble, sort_bucket, sort_insertion, sort_merge, sort_quick, sort_radix

def printtime(label, time):
  print(format(time, 'f'))

arr = list(range(100, 0, -1))

start = time.time()
sort_bubble.bubblesort(arr)
printtime('bubble', time.time()-start)

# start = time.time()
# result = sort_insertion.insertionsort(arr)
# printtime('insertion', time.time()-start)

# start = time.time()
# sort_merge.mergesort(arr)
# printtime('merge', time.time()-start)

# start = time.time()
# sort_quick.quicksort(arr)
# printtime('quick', time.time()-start)

# start = time.time()
# sort_radix.radixsort(arr)
# printtime('radix', time.time()-start)

# start = time.time()
# sort_bucket.bucketsort(arr, 10)
# printtime('bucket', time.time()-start)
