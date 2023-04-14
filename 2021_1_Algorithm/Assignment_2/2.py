# 2) [Programming] Palindrome refers to words that have the same results when
# we read from the beginning and read from the end, such as level, bob, and
# radar.
# Write a function that determines if the given word is palindrome or not.
# Display the results when you put two different words (one is palindrome and
# the other is not).

word = input()
isPalindrome = True

for i in range(0, int(len(word)/2)):
    if word[i] != word[len(word)-1-i]:
        isPalindrome = False
        break

if isPalindrome:
    print(f"{word} is Palindrome")
else:
    print(f"{word} is NOT Palindrome")
