from enum import Enum
class Color(Enum):
  white = 1
  gray = 2
  black = 3

class Vertex:
  def __init__(self):
    self.predecessor = None
    self.d = None
    self.f = None
    self.color = Color.white

class DFS:
  def __init__(self, graph):
    self.graph = graph
    self.graph_dfs = dict(map(lambda x: (x[0], Vertex()), graph))
    self.time = 0

  def visit(self, u):
    self.time += 1
    self.graph_dfs[u].d = self.time
    self.graph_dfs[u].color = Color.gray
    for i in self.graph[u]:
      if self.graph_dfs[i].color == Color.white:
        self.graph_dfs[i].predecessor = u
        self.visit(i)
    self.graph_dfs[u].color = Color.black
    self.time += 1
    self.graph_dfs[u].f = self.time

dfs = DFS({
  'r': ['v', 's'],
  's': ['r', 'w'],
  't': ['u', 'w', 'x'],
  'u': ['t', 'x', 'y'],
  'v': ['r'],
  'w': ['s', 't', 'x'],
  'x': ['t', 'u', 'w', 'y'],
  'y': ['u', 'x'],
})
dfs.visit('s')

for key, value in dfs.graph_dfs.items():
  print(f"{key} - pi : {value.predecessor}, d : {value.d}")