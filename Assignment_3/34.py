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


def find_ancestors(tree, node_index):
    ancestors = [node_index]
    latest_ancestor_index = node_index
    while latest_ancestor_index > 0:
        if (latest_ancestor_index % 2) == 0:
            latest_ancestor_index -= 1
        latest_ancestor_index = ((latest_ancestor_index - 1) / 2)
        ancestors.append(latest_ancestor_index)
    ancestors.reverse()
    print(ancestors)
    return ancestors


def find_lowest_common_ancestor(tree, node1_index, node2_index):
    node1_ancestors = find_ancestors(tree, node1_index)
    node2_ancestors = find_ancestors(tree, node2_index)
    i = 0
    while i < len(node1_ancestors):
        if node1_ancestors[i] != node2_ancestors[i]:
            return node1_ancestors[i-1]
        else:
            i += 1
    return node1_ancestors[i-1]


inputTree = [8, 3, 9, None, None, 4, 7, None, None, None, None, 2, 5]
print(find_lowest_common_ancestor(inputTree, 0, 12))
