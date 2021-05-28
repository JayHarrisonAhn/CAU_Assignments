bag = [
    # index, weight
]
max_capacity = 16
items = [
    # index, weight, value
    [1, 6, 60],
    [2, 10, 20],
    [3, 3, 12],
    [4, 5, 80],
    [5, 1, 30],
    [6, 3, 60],
]
sorted_items = sorted(items, key=lambda item: item[2]/item[1], reverse=True)


def current_capacity():
    result = max_capacity
    for i in bag:
        result -= i[1]
    return result


def current_value():
    result = 0
    for i in bag:
        index = i[0]-1
        result += items[index][2] / items[index][1] * i[1]
    return result


def put_in_the_bag():
    for i in sorted_items:
        if current_capacity() <= 0:
            break
        elif current_capacity() < i[1]:
            item = [
                i[0],
                current_capacity(),
            ]
            bag.append(item)
            break
        else:
            item = [
                i[0],
                i[1],
            ]
            bag.append(item)


put_in_the_bag()
print("The bag's maximum value is ", current_value())
for i in bag:
    print("item #", i[0], " : ", i[1] / items[i[0]-1][1])
