# 5) [Programming] Given an image represented by a 5 × 5 matrix, write a method
# to rotate the image by 90 degrees (clockwise). You can generate a matrix
# randomly.

import random


originalMatrix = [[0 for col in range(5)] for row in range(5)]
print("Original Matrix : ")
for i in range(0, len(originalMatrix)):
    for j in range(0, len(originalMatrix[i])):
        originalMatrix[i][j] = random.randrange(0, 256)
    print(originalMatrix[i])


print("\nRotated Matrix : ")
rotatedMatrix = [[0 for col in range(5)] for row in range(5)]
for i in range(0, len(rotatedMatrix)):
    for j in range(0, len(rotatedMatrix[i])):
        rotatedMatrix[i][j] = originalMatrix[len(originalMatrix)-1-j][i]
    print(rotatedMatrix[i])

