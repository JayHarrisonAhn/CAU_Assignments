from queue import PriorityQueue

class Vertex:
  def __init__(self):
    self.d = float("inf")
    self.predecessor = None

class Dijkstra:
  def __init__(self, graph):
    self.graph = graph
    self.Q = dict(map(lambda x: (x[0], Vertex()), graph))
    self.S = {}
    self.G = {}
    self.G.update(self.Q)
    self.G.update(self.S)

  def search(self, start):
    self.start = start
    self.Q[start].d = 0

    while len(self.Q) > 0:
      u = min(list(self.Q.items()),key= lambda i: i[1].d)
      self.S[u[0]] = u[1]
      del self.Q[u[0]]

      # print(self.graph[u[0]])
      # print(self.graph)

      for adj_key, adj_weight in self.graph[u[0]].items():
        # print(adj_key, adj_weight)
        if self.G[adj_key].d > u[1].d + adj_weight:
          self.G[adj_key].d = u[1].d + adj_weight
          self.G[adj_key].predecessor = u[0]

  def way(self, to):
    current = to
    while current != self.start:
      print(current)
      current = self.G[current].predecessor




dijkstra = Dijkstra({
  's': {'t': 3, 'y': 5},
  't': {'y': 2, 'x': 6},
  'x': {'z': 2},
  'y': {'t': 1, 'x': 4, 'z': 6},
  'z': {'s': 3, 'x': 7}
})
dijkstra.search('s')
for key, value in dijkstra.G.items():
  print(key, value.d, value.predecessor)
dijkstra.way('z')
