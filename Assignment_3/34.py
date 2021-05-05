def find_descendant_indices(tree, node_index):
    if node_index >= len(tree):
        return []
    elif tree[node_index] is None:
        return []
    else:
        return [node_index]+find_descendant_indices(tree, (node_index*2)+1)+find_descendant_indices(tree, (node_index*2)+2)

def find_ancestor_indices(tree, node_index):
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

def check_if_CBT(tree):
    i = 0
    while i < len(tree):
        print(i, tree[i])
        if tree[i] is None:
            i += 1
            continue
        else:
            left_node_values = list(map(lambda index: tree[index], find_descendant_indices(tree, (i * 2) + 1)))
            if len(list(filter(lambda value: tree[i] < value, left_node_values))) > 0:
                print("1This is NOT a valid Binary Search Tree")
                return

            right_node_values = list(map(lambda index: tree[index], find_descendant_indices(tree, (i * 2) + 2)))
            if len(list(filter(lambda value: tree[i] > value, right_node_values))) > 0:
                print("2This is NOT a valid Binary Search Tree", right_node_values)
                return
            i += 1
    print("This is a valid Binary Search Tree")

def find_lowest_common_ancestor(tree, node1_index, node2_index):
    node1_ancestors = find_ancestor_indices(tree, node1_index)
    node2_ancestors = find_ancestor_indices(tree, node2_index)
    i = 0
    while i < len(node1_ancestors):
        if node1_ancestors[i] != node2_ancestors[i]:
            return node1_ancestors[i-1]
        else:
            i += 1
    return node1_ancestors[i-1]


inputTree = [8, 3, 9, None, None, 4, 7]
check_if_CBT(inputTree)