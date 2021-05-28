memo = []


def fibonacci(n):
    if len(memo) < (n+1):
        for i in range(n-len(memo)+1):
            memo.append(None)

    if memo[n] is not None:
        return memo[n]
    else:
        result = None
        if n == 0:
            result = 0
        elif n == 1:
            result = 1
        else:
            result = fibonacci(n - 2) + fibonacci(n - 1)
        memo[n] = result
        return result


print(fibonacci(5))
print(fibonacci(10))
