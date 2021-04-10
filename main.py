#!/usr/bin/python

import sys
import os

class DFA_Graph:
  def __init__(self, token, table, finish_states):
    self.token = token
    self.table = table
    self.finish_states = finish_states
    self.reset()

  def reset(self):
    self.current_state = 1
    self.string = '' #History of input symbols

  def symbol_type(self, symbol):
    for i, symbol_set in enumerate(self.table[0]):
      if(symbol_set is None): #Any Symbol
        return i
      if(symbol in symbol_set):
        return i
    return None

  def input_symbol(self, symbol):
    if(self.current_state is None): #Already Terminated
      return None

    symbol_type = self.symbol_type(symbol)
    if(symbol_type is None): #Can't move, Terminate
      self.current_state = None
      return None

    self.current_state = self.table[self.current_state][symbol_type]
    self.string += symbol
    if(self.current_state is None): #Can't move, Terminate
      return None
    elif(self.current_state in self.finish_states):
      return (self.token, self.string)
    else:
      return None


class Syntax:
  specs = [
    DFA_Graph('INT', [
      ["i", "n", "t"],
      [2, None, None],
      [None, 3, None],
      [None, None, 4],
      [None, None, None]
    ], [4]),
    DFA_Graph('STRING', [ #Literal String
      ['"', None],
      [2, None],
      [3, 2],
      [None, None]
    ], [3]),
    DFA_Graph('WHITESPACE', [
      [[" ", '\t', '\n']],
      [2],
      [2]
    ], [2]),
    DFA_Graph('ID', [
      ["abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_", "0123456789"],
      [2, None],
      [2, 2]
    ], [2])
  ]

  @classmethod
  def input(cls, symbol):
    result = list(map(lambda spec: spec.input_symbol(symbol), cls.specs))
    return result

  @classmethod
  def reset(cls):
    list(map(lambda spec: spec.reset(), cls.specs))


def printToken(token, file):
  if(token[0] == 'WHITESPACE'): return
  else:
    log = f"{token[0]}\t{token[1]}"
    print(log)
    file.write(log+"\n")


def main(input):
  f_in = open(input, 'rt')
  f_out = open(input+"_output.txt", 'wt')

  last_syntax_result = []
  while(True):
    symbol = f_in.read(1)
    if(symbol == ''):
      if(last_syntax_result):
        printToken(last_syntax_result[0], f_out)
      break

    syntax_result = list(filter(lambda syntax: syntax is not None, Syntax.input(symbol)))
    if(not syntax_result):
      if(last_syntax_result):
        printToken(last_syntax_result[0], f_out)
        Syntax.reset()
        f_in.seek(f_in.tell() - 1, os.SEEK_SET)
    last_syntax_result = syntax_result

  f_in.close()
  f_out.close()


  # Auto Test Code Below (Delete for submit file)
  try:
    with open(input.replace('.java','')+".out.txt", 'rt') as f_autotest_out:
      with open(input+"_output.txt", 'rt') as f_result:
        while True:
          autotest_out = f_autotest_out.readline()
          autotest_in = f_result.readline()
          if((not autotest_in) | (not autotest_out)): break
          if(autotest_in != autotest_out):
            print("[Autotest] Fail")
            return
        print("[Autotest] Success")
  except:
    return


if __name__ == "__main__":
  main(sys.argv[1])