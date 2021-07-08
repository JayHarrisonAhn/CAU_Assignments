X = "ABCBDAB"
Y = "BDCABA"
dptable = [[None for col in range(len(Y)+1)] for row in range(len(X)+1)]

def LCS_length():
  for i in range(len(dptable)):
    for j in range(len(dptable[i])):
      if i==0 or j==0 :
        dptable[i][j] = (0, None)
      else:
        if X[i-1] == Y[j-1]:
          dptable[i][j] = (dptable[i-1][j-1][0]+1, (-1, -1))
        else :
          if dptable[i][j-1][0] > dptable[i-1][j][0]:
            dptable[i][j] = (dptable[i][j-1][0], (0, -1))
          else:
            dptable[i][j] = (dptable[i-1][j][0], (-1, 0))      
  return dptable[-1][-1][0]


def LCS_print():
  i = len(dptable)-1
  j = len(dptable[0])-1
  lcs_string = ""

  while i!=0 and j!=0:
    if X[i-1] == Y[j-1]:
      lcs_string += X[i-1]
      i-=1
      j-=1
      continue
    else:
      way = dptable[i][j][1]
      i += way[0]
      j += way[1]
      continue
  return lcs_string[::-1]


print(LCS_length())
print(LCS_print())
