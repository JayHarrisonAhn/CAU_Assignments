class ll:

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
    current_index = 0
    current_node = cls.top_node
    while current_index < index:
      current_index += 1
      current_node = current_node.next
    return current_node



  @classmethod
  def append(cls, value):
    if cls.top_node is None:
      cls.top_node = ll(value)
    else:
      cls.last_node().next = ll(value)

  @classmethod
  def insert(cls, at, value):
    if at > (cls.num_of_nodes()+1):
      raise NameError("Index Overflow")
    else:
      node_before = cls.search_node(at-1)
      node_after = cls.search_node(at)
      node_new = ll(value)
      node_before.next = node_new
      node_new.next = node_after

  @classmethod
  def traverse(cls):
    current_node = cls.top_node
    while current_node is not None:
      print(current_node.value)
      current_node = current_node.next

  @classmethod
  def remove(cls, index):
    if index >= cls.num_of_nodes():
      raise NameError("Index Overflow")
    else:
      if index == 0:
        cls.top_node = cls.top_node.next
      elif index == cls.num_of_nodes()-1:
        cls.search_node(index-1).next = None
      else:
        cls.search_node(index-1).next = cls.search_node(index+1)

  #1
  @classmethod
  def reverse(cls):
    if cls.num_of_nodes() <= 0:
      raise NameError("Empty List")
    else:
      new_top_node = cls.last_node()
      new_last_node = new_top_node
      cls.remove(cls.num_of_nodes()-1)
      while cls.num_of_nodes() > 0:
        new_last_node.next = cls.last_node()
        new_last_node = new_last_node.next
        cls.remove(cls.num_of_nodes()-1)
      cls.top_node = new_top_node


  #2
  @classmethod
  def remove_duplicates(cls):
    current_node = cls.top_node
    current_node_index = 0
    values = []
    while current_node is not None:
      if current_node.value in values:
        ll.remove(current_node_index)
        current_node_index -= 1
      else:
        values.append(current_node.value)

      current_node = current_node.next
      current_node_index += 1


  def __init__(self, value):
    self.value = value
    self.next = None

ll.append(1)
ll.append(1)
ll.append(2)
ll.append(3)
ll.append(4)
ll.append(4)
ll.append(5)
ll.append(6)
ll.append(7)
ll.append(7)
ll.append(7)
ll.append(7)
ll.append(7)

ll.traverse()
ll.remove_duplicates()
ll.traverse()

