import random
matrix = [
    [[random.randint(1, 99), random.randint(1, 99), random.randint(1, 99)],
     [random.randint(1, 99), random.randint(1, 99), random.randint(1, 99)],
     [random.randint(1, 99), random.randint(1, 99), random.randint(1, 99)],
     [random.randint(1, 99), random.randint(1, 99), random.randint(1, 99)],
     [random.randint(1, 99), random.randint(1, 99), random.randint(1, 99)]],

    [[random.randint(1, 99), random.randint(1, 99), random.randint(1, 99), random.randint(1, 99),
        random.randint(1, 99), random.randint(1, 99), random.randint(1, 99)],
     [random.randint(1, 99), random.randint(1, 99), random.randint(1, 99), random.randint(1, 99),
        random.randint(1, 99), random.randint(1, 99), random.randint(1, 99)],
     [random.randint(1, 99), random.randint(1, 99), random.randint(1, 99), random.randint(1, 99),
        random.randint(1, 99), random.randint(1, 99), random.randint(1, 99)]],

    [[random.randint(1, 99), random.randint(1, 99), random.randint(1, 99), random.randint(1, 99),
      random.randint(1, 99), random.randint(1, 99), random.randint(1, 99),
      random.randint(1, 99), random.randint(1, 99), random.randint(1, 99)],
     [random.randint(1, 99), random.randint(1, 99), random.randint(1, 99), random.randint(1, 99),
      random.randint(1, 99), random.randint(1, 99), random.randint(1, 99),
      random.randint(1, 99), random.randint(1, 99), random.randint(1, 99)],
     [random.randint(1, 99), random.randint(1, 99), random.randint(1, 99), random.randint(1, 99),
      random.randint(1, 99), random.randint(1, 99), random.randint(1, 99),
      random.randint(1, 99), random.randint(1, 99), random.randint(1, 99)],
     [random.randint(1, 99), random.randint(1, 99), random.randint(1, 99), random.randint(1, 99),
      random.randint(1, 99), random.randint(1, 99), random.randint(1, 99),
      random.randint(1, 99), random.randint(1, 99), random.randint(1, 99)],
     [random.randint(1, 99), random.randint(1, 99), random.randint(1, 99), random.randint(1, 99),
      random.randint(1, 99), random.randint(1, 99), random.randint(1, 99),
      random.randint(1, 99), random.randint(1, 99), random.randint(1, 99)],
     [random.randint(1, 99), random.randint(1, 99), random.randint(1, 99), random.randint(1, 99),
      random.randint(1, 99), random.randint(1, 99), random.randint(1, 99),
      random.randint(1, 99), random.randint(1, 99), random.randint(1, 99)],
     [random.randint(1, 99), random.randint(1, 99), random.randint(1, 99), random.randint(1, 99),
      random.randint(1, 99), random.randint(1, 99), random.randint(1, 99),
      random.randint(1, 99), random.randint(1, 99), random.randint(1, 99)]]
]

memo_count = [[None for col in range(len(matrix))]
              for row in range(len(matrix))]
memo_way = [[None for col in range(len(matrix))]
            for row in range(len(matrix))]


def M(i, j):
    # Matrix들의 sequence of dimensions는 matrix에서 직접 추출하여 feed됩니다.
    if memo_count[i][j] != None:
        return memo_count[i][j]

    result = None
    result_k = None
    if i == j:
        result = 0
        result_k = i
    else:
        for k in range(i, j):
            count = M(i, k) + M(k+1, j) + \
                (len(matrix[i]) * len(matrix[k+1]) * len(matrix[j][0]))
            if result is None:
                result = count
                result_k = k
            elif count < result:
                result = count
                result_k = k

    memo_count[i][j] = result
    memo_way[i][j] = result_k
    return result


def matrix_multiple(x, y):
    result = [[0 for col in range(len(y[0]))]
              for row in range(len(x))]
    for i in range(len(x)):
        for j in range(len(y[0])):
            for k in range(len(y)):
                result[i][j] += x[i][k] * y[k][j]

    return result


def calculate(i, j):
    if i == j:
        return matrix[i]
    elif (j-i) == 1:
        print(i, j)
        return matrix_multiple(matrix[i], matrix[j])
    else:
        result = matrix_multiple(
            calculate(i, memo_way[i][j]),
            calculate(memo_way[i][j]+1, j)
        )
        print(i, j)
        return result


print("Original Matrix : ")
for i in matrix:
    for j in i:
        print(j)
    print("-------------------------")

print("Minimum number of computation : ", M(0, len(matrix) - 1))

print("Optimal chain order : ")
calculated_result = calculate(0, len(matrix) - 1)

print("Output Matrix : ")
for i in range(len(calculated_result)):
    print(calculated_result[i])
