# 4) [Programming] Write a program that search for the integer 120 in the
# following list of integers using the binary search algorithm.
#
# 12 34 37 45 57 82 99 120 134

searchValue = 12
arr = [12, 34, 37, 45, 57, 82, 99, 120, 134]
unsearchedIndex = [0, len(arr)-1]

while True:
    centerIndex = int((unsearchedIndex[0] + unsearchedIndex[1]) / 2)
    centerValue = arr[centerIndex]
    if unsearchedIndex[0] > unsearchedIndex[1]:
        print(f"There is no {searchValue} in this array")
        break
    elif centerValue == searchValue:
        print(f"{searchValue}'s index is {centerIndex}")
        break
    elif centerValue > searchValue:
        unsearchedIndex[1] = centerIndex - 1
    elif centerValue < searchValue:
        unsearchedIndex[0] = centerIndex + 1
