# 6) [Programming] Write a function to find all pairs of an integer array whose
# sum is equal to a given number.
#
# Function: pairSum([2, 4, 3, 5, 6, -2, 4, 7, 8, 9], 7)
# Output: [‘2+5’, ‘4+3’, ‘3+4’, ‘-2+9’]


def pairSum(arr, num):
    result = []
    for i in range(0, len(arr)):
        for j in range(i+1, len(arr)):
            if arr[i]+arr[j] == num:
                result.append(f'{arr[i]}+{arr[j]}')
    print(result)
    return result


pairSum([2, 4, 3, 5, 6, -2, 4, 7, 8, 9], 7)
