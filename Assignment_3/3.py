def check_if_CBT(tree):
    i = 0
    while i < len(tree):
        if tree[i] is None:
            i += 1
            continue
        if len(tree) > ((2 * i) + 1):
            if (tree[(2*i)+1] is not None) & (tree[(2*i)+1] > tree[i]):
                print("This is NOT a Binary Search Tree")
                return
        if len(tree) > ((2 * i) + 2):
            if (tree[(2*i)+2] is not None) & (tree[(2*i)+2] < tree[i]):
                print("This is NOT a Binary Search Tree")
                return
        i += 1
    print("This is a valid Binary Search Tree")

inputTree = [8, 3, 9, None, None, 4, 7]
check_if_CBT(inputTree)