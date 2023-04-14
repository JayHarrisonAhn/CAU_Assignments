import os
import time
from functools import reduce
stack = []

class Puzzle:
  def __init__(self, puzzle, from_root):
    self.puzzle = puzzle
    self.from_root = from_root

  def empty_cell_index(self):
    for i in range(len(self.puzzle)):
      for j in range(len(self.puzzle[i])):
        if self.puzzle[i][j] is None:
          return i, j

  def duplicated(self):
    result = []
    for i in self.puzzle:
      result_row = []
      for j in i:
        result_row.append(j)
      result.append(result_row)
    return Puzzle(result, self.from_root)
  
  def all_movables(self):
    result = []
    i = self.empty_cell_index()
    if i[0] != 0:
      dup = self.duplicated()
      dup.from_root += 1
      dup.puzzle[i[0]][i[1]] = dup.puzzle[i[0]-1][i[1]]
      dup.puzzle[i[0]-1][i[1]] = None
      result.append(dup)

    if i[1] != 0:
      dup = self.duplicated()
      dup.from_root += 1
      dup.puzzle[i[0]][i[1]] = dup.puzzle[i[0]][i[1]-1]
      dup.puzzle[i[0]][i[1]-1] = None
      result.append(dup)

    if i[0] != len(self.puzzle)-1:
      dup = self.duplicated()
      dup.from_root += 1
      dup.puzzle[i[0]][i[1]] = dup.puzzle[i[0]+1][i[1]]
      dup.puzzle[i[0]+1][i[1]] = None
      result.append(dup)

    if i[1] != len(self.puzzle[0])-1:
      dup = self.duplicated()
      dup.from_root += 1
      dup.puzzle[i[0]][i[1]] = dup.puzzle[i[0]][i[1]+1]
      dup.puzzle[i[0]][i[1]+1] = None
      result.append(dup)

    return result

  def cost(self, goal):
    misplaced = 0
    for i in range(len(self.puzzle)):
      for j in range(len(self.puzzle[i])):
        if self.puzzle[i][j] is None:
          continue
        if self.puzzle[i][j] != goal[i][j]:
          misplaced += 1
    return self.from_root + misplaced

  def is_same(self, puzzle):
    for i in range(len(self.puzzle)):
      for j in range(len(self.puzzle[i])):
        if self.puzzle[i][j] != puzzle[i][j]:
          return False
    return True


  

goal = [
  [1,2,3,4],
  [5,6,7,8],
  [9,10,11,12],
  [13,14,15,None],
]
    
class Tree:
  def __init__(self, data):
    self.data = data
    self.parent = None
    self.children = []

  def is_goal_achieved(self):
    achieved_result = list(map(lambda x: x.is_goal_achieved(), self.children))
    achieved_result.append(self.data.is_same(goal))
    return reduce(lambda x, y: x or y, achieved_result)

  def lowest_cost_node(self):
    result_candidates = list(map(lambda x: x.lowest_cost_node(), self.children))
    if len(result_candidates) == 0:
      return self
    else:
      return reduce(lambda x, y: x if x.data.cost(goal)<y.data.cost(goal) else y, result_candidates)

  def root(self):
    current = self
    while current.parent != None:
      current = current.parent
    return current

  def tree_span(self):
    children = list(map(lambda x: Tree(x), self.data.all_movables()))
    # print(children)
    children = list(filter(lambda x: not self.root().does_exist(x.data.puzzle), children))
    # print(children)
    for i in self.children:
      i.parent = self
    self.children = children

  def does_exist(self, puzzle):
    result = list(map(lambda x: x.does_exist(puzzle), self.children))
    result.append(self.data.is_same(puzzle))
    return reduce(lambda x, y: x or y, result)

  def search(self):
    while not self.is_goal_achieved():
      current_node = self.lowest_cost_node()
      # print(current_node.data.puzzle, current_node.data.cost(goal), current_node.data.from_root)
      # os.system("clear")
      print("----------")
      for i in current_node.data.puzzle:
        print(i)
      current_node.tree_span()
      # print(self.children)

root = Tree(Puzzle([
  [10, 7, 3, 4],
  [5 ,9 ,None ,11],
  [6 ,1 ,2 ,8],
  [13, 14, 15, 12]
], 0))

print(root.search())
