class rbt_node:
    def __init__(self, value):
        self.color = 0 #red
        self.value = value

        self.parent = None
        self.left = None
        self.right = None

    def insert_left(self, left):
        self.left = left
        left.parent = self

    def insert_right(self, right):
        self.right = right
        right.parent = self

    def swap(self, other_node):
        temp_value = other_node.value
        other_node.value = self.value
        self.value = temp_value

    def right_rotate(self):
        B = self
        A = self.left
        a = A.left
        b = A.right
        c = B.right

        B.left = a
        B.right = A
        A.left = b
        A.right = c

        if a is not None:
            a.parent = B
        if b is not None:
            b.parent = A
        if c is not None:
            c.parent = A

        B.swap(A)


    def left_rotate(self):
        A = self
        B = self.right
        b = B.left
        c = B.right
        a = A.left

        B.left = a
        B.right = b
        A.left = B
        A.right = c

        if a is not None:
            a.parent = B
        if b is not None:
            b.parent = B
        if c is not None:
            c.parent = A

        A.swap(B)

    def tree_insert(self, node):
        if node.value < self.value:
            if self.left is None:
                self.insert_left(node)
            else:
                self.left.tree_insert(node)
        if node.value > self.value:
            if self.right is None:
                self.insert_right(node)
            else:
                self.right.tree_insert(node)

    def uncle(self):
        if self.parent is None:
            return None
        parent = self.parent

        if parent.parent is None:
            return None
        grandparent = self.parent.parent

        if grandparent.left == parent:
            return grandparent.right
        else:
            return grandparent.left

    def insert(self, value):
        new_node = rbt_node(value)
        self.tree_insert(new_node)

        while new_node != self:
            if new_node.parent.color != 0:
                break

            if new_node.uncle() is not None:
                if new_node.uncle().color == 0:
                    new_node.parent.parent.color = 0
                    new_node.parent.color = 1
                    new_node.uncle().color = 1
                    new_node = new_node.parent.parent
                    continue

            if new_node == new_node.parent.left:
                if new_node.parent == new_node.parent.parent.left:
                    new_node.parent.parent.right_rotate()
                else:
                    new_node.parent.right_rotate()
            elif new_node == new_node.parent.right:
                if new_node.parent == new_node.parent.parent.left:
                    new_node.parent.left_rotate()
                else:
                    new_node.parent.parent.left_rotate()

        self.color = 1

    def printNode(self):
        left_print = None
        right_print = None
        if self.left is not None:
            left_print = self.left.printNode()
        if self.right is not None:
            right_print = self.right.printNode()

        return '['+str(left_print)+','+str(self.value)+'-'+str(self.color)+','+str(right_print)+']'


root_node = rbt_node(41)
root_node.color = 1
root_node.insert(38)
root_node.insert(31)
root_node.insert(12)
root_node.insert(19)
root_node.insert(8)

print("Problem #5 (0 is red, 1 is black)")
print(root_node.printNode())
