# 1) [Programming] Write a program that takes a number 𝑛 and displays the
# largest positive integer 𝑘 satisfying the following equations: 2^k ≤ 𝑛.
# Display the results for three different 𝑛’s: 10, 50, and 1025.

n = int(input())

k = 0
k_powered = 1
while k_powered <= n:
    k += 1
    k_powered *= 2

print(k-1)
