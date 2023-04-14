import random


class LL:
    top_node = None

    @classmethod
    def last_node(cls):
        if cls.top_node is None:
            return None
        else:
            current_node = cls.top_node
            while current_node.next is not None:
                current_node = current_node.next
            return current_node

    @classmethod
    def num_of_nodes(cls):
        current_node = cls.top_node
        result = 0
        while current_node is not None:
            current_node = current_node.next
            result += 1
        return result

    @classmethod
    def search_node(cls, index):
        if index < 0:
            return None
        elif index >= cls.num_of_nodes():
            return None
        current_index = 0
        current_node = cls.top_node
        while current_index < index:
            current_index += 1
            current_node = current_node.next
        return current_node

    @classmethod
    def append(cls, value):
        if cls.top_node is None:
            cls.top_node = LL(value)
        else:
            cls.last_node().next = LL(value)

    @classmethod
    def insert(cls, at, value):
        if at > (cls.num_of_nodes() + 1):
            raise NameError("Index Overflow")
        else:
            node_new = LL(value)
            node_before = cls.search_node(at - 1)
            node_after = cls.search_node(at)

            if node_before is None:
                cls.top_node = node_new
            else:
                node_before.next = node_new

            if node_after is not None:
                node_new.next = node_after




    @classmethod
    def traverse(cls):
        current_node = cls.top_node
        while current_node is not None:
            print(current_node.value)
            current_node = current_node.next
        print("")

    @classmethod
    def remove(cls, index):
        if index >= cls.num_of_nodes():
            raise NameError("Index Overflow")
        else:
            if index == 0:
                cls.top_node = cls.top_node.next
            elif index == cls.num_of_nodes() - 1:
                cls.search_node(index - 1).next = None
            else:
                cls.search_node(index - 1).next = cls.search_node(index + 1)

    # 1
    @classmethod
    def reverse(cls):
        if cls.num_of_nodes() <= 0:
            raise NameError("Empty List")
        else:
            new_top_node = cls.last_node()
            new_last_node = new_top_node
            cls.remove(cls.num_of_nodes() - 1)
            while cls.num_of_nodes() > 0:
                new_last_node.next = cls.last_node()
                new_last_node = new_last_node.next
                cls.remove(cls.num_of_nodes() - 1)
            cls.top_node = new_top_node

    # 2
    @classmethod
    def remove_duplicates(cls):
        current_node = cls.top_node
        current_node_index = 0
        values = []
        while current_node is not None:
            if current_node.value in values:
                LL.remove(current_node_index)
                current_node_index -= 1
            else:
                values.append(current_node.value)

            current_node = current_node.next
            current_node_index += 1

    def __init__(self, value):
        self.value = value
        self.next = None


# Problem 1
print ("Problem #1")
LL.top_node = None
for i in range(10):
    LL.append(random.randrange(1, 10))
LL.traverse()
LL.insert(2, 101)
LL.remove(3)
LL.reverse()
LL.traverse()


# Problem 2
print ("Problem #2")
LL.top_node = None
LL.append(20)
LL.append(24)
LL.append(78)
LL.append(20)
LL.append(73)
LL.append(20)
LL.append(24)
LL.append(68)
LL.append(20)
LL.append(20)
LL.append(20)
LL.append(24)
LL.append(78)
LL.append(76)
LL.append(46)
LL.append(20)
LL.append(24)
LL.append(78)
LL.append(20)
LL.append(20)
LL.traverse()
LL.remove_duplicates()
LL.traverse()