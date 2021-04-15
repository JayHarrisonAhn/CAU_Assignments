#!/usr/bin/python

import sys
import os

class DFA_Graph:
  def __init__(self, token, table, finish_states, deny_last_token):
    self.token = token
    self.table = table
    self.finish_states = finish_states
    self.deny_last_token = deny_last_token
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

  def input_symbol(self, symbol, last_parsed_token):
    if(last_parsed_token): #Terminate after deny_last_token
      if(last_parsed_token[0] in self.deny_last_token):
        self.current_state = None

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
    ], [4], []),
    DFA_Graph('CHAR', [
      ["c", "h", "a", "r"],
      [2, None, None, None],
      [None, 3, None, None],
      [None, None, 4, None],
      [None, None, None, 5],
      [None, None, None, None]
    ], [5], []),
    DFA_Graph('BOOLEAN', [
      ["b", "o", "l", "e", "a", "n"],
      [2, None, None, None, None, None],
      [None, 3, None, None, None, None],
      [None, 4, None, None, None, None],
      [None, None, 5, None, None, None],
      [None, None, None, 6, None, None],
      [None, None, None, None, 7, None],
      [None, None, None, None, None, 8],
      [None, None, None, None, None, None],
    ], [8], []),
    DFA_Graph('STRING', [
      ["S", "t", "r", "i", "n", "g"],
      [2, None, None, None, None, None],
      [None, 3, None, None, None, None],
      [None, None, 4, None, None, None],
      [None, None, None, 5, None, None],
      [None, None, None, None, 6, None],
      [None, None, None, None, None, 7],
      [None, None, None, None, None, None],
    ], [7], []),
    DFA_Graph('SINGLE CHARACTER', [
      ["'", "1234567890", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ", None],
      [2, None, None, None],
      [None, 3, 4, 5],
      [6, None, None, None],
      [6, None, None, None],
      [6, None, None, None],
      [None, None, None, None],
    ], [6], []),
    DFA_Graph('BOOLEAN STRING', [
      ["t", "r", "u", "e", "f", "a", "l", "s"],
      [2, None, None, None, 5, None, None, None],
      [None, 3, None, None, None, None, None, None],
      [None, None, 4, None, None, None, None, None],
      [None, None, None, 9, None, None, None, None],
      [None, None, None, None, None, 6, None, None],
      [None, None, None, None, None, None, 7, None],
      [None, None, None, None, None, None, None, 8],
      [None, None, None, 9, None, None, None, None],
      [None, None, None, None, None, None, None, None]
    ], [9], []),
    DFA_Graph('STRING', [
      ['"', None],
      [2, None],
      [3, 2],
      [None, None]
    ], [3], []),
    DFA_Graph('IF', [
      ["i", "f"],
      [2, None],
      [None, 3],
      [None, None],
    ], [3], []),
    DFA_Graph('ELSE', [
      ["e", "l", "s"],
      [2, None, None],
      [None, 3, None],
      [None, None, 4],
      [5, None, None],
      [None, None, None]
    ], [5], []),
    DFA_Graph('WHILE', [
      ["w", "h", "i", "l", "e"],
      [2, None, None, None, None],
      [None, 3, None, None, None],
      [None, None, 4, None, None],
      [None, None, None, 5, None],
      [None, None, None, None, 6],
      [None, None, None, None, None]
    ], [6], []),
    DFA_Graph('CLASS', [
      ["c", "l", "a", "s"],
      [2, None, None, None],
      [None, 3, None, None],
      [None, None, 4, None],
      [None, None, None, 5],
      [None, None, None, 6],
      [None, None, None, None]
    ], [6], []),
    DFA_Graph('RETURN', [
      ["r", "e", "t", "u", "n"],
      [2, None, None, None, None],
      [None, 3, None, None, None],
      [None, None, 4, None, None],
      [None, None, None, 5, None],
      [6, None, None, None, None],
      [None, None, None, None, 7],
      [None, None, None, None, None],
    ], [7], []),
    DFA_Graph('OP_ARITHMATIC', [
      ["+-*/"],
      [2],
      [None]
    ], [2], []),
    DFA_Graph('OP_ASSIGNMENT', [
      ["="],
      [2],
      [None]
    ], [2], []),
    DFA_Graph('OP_COMPARISON', [
      [">", "<", "!", "="],
      [2, 3, 4, 5],
      [None, None, None, 6],
      [None, None, None, 6],
      [None, None, None, 7],
      [None, None, None, 7],
      [None, None, None, None],
      [None, None, None, None],
    ], [2, 3, 6, 7], []),
    DFA_Graph('SIGNED INTEGER', [
      ["-", "123456789", "0"],
      [2, 3, 4],
      [None, 3, None],
      [None, 5, 6],
      [None, None, None],
      [None, 5, 6],
      [None, 5, 6],
    ], [3, 4, 5, 6], ['ID', 'SIGNED INTEGER']),
    DFA_Graph('TERMINATE', [
      [";"],
      [2],
      [None]
    ], [2], []),
    DFA_Graph('LPAREN', [
      ["("],
      [2],
      [None]
    ], [2], []),
    DFA_Graph('RPAREN', [
      [")"],
      [2],
      [None]
    ], [2], []),
    DFA_Graph('LBRACE', [
      ["{"],
      [2],
      [None]
    ], [2], []),
    DFA_Graph('RBRACE', [
      ["}"],
      [2],
      [None]
    ], [2], []),
    DFA_Graph('LBRACKET', [
      ["["],
      [2],
      [None]
    ], [2], []),
    DFA_Graph('RBRACKET', [
      ["]"],
      [2],
      [None]
    ], [2], []),
    DFA_Graph('SEPARATE', [
      [","],
      [2],
      [None]
    ], [2], []),
    DFA_Graph('ID', [
      ["abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ", "0123456789", "_"],
      [2, None, 3],
      [4, 5, 6],
      [4, 5, 6],
      [4, 5, 6],
      [4, 5, 6],
      [4, 5, 6],
    ], [2, 3, 4, 5, 6], []),
    DFA_Graph('WHITESPACE', [
      [[" ", '\t', '\n']],
      [2],
      [2]
    ], [2], [])
  ]

  @classmethod
  def input(cls, symbol, last_parsed_token): #input symbol to every DFA.
    result = list(map(lambda spec: spec.input_symbol(symbol, last_parsed_token), cls.specs))
    return result

  @classmethod
  def reset(cls): #reset all DFAs.
    list(map(lambda spec: spec.reset(), cls.specs))


def printToken(token, file): #output token
  if(token[0] == 'WHITESPACE'): return #skip WS token.
  else:
    log = f"{token[0]}\t{token[1]}"
    print(log)
    file.write(log+"\n")


def main(input):
  f_in = open(input, 'rt')
  f_out = open(input+"_output.txt", 'wt')

  current_line = 1
  last_syntax_result = []
  last_parsed_token = None
  last_parsed_token_line = 0
  
  while(True):
    symbol = f_in.read(1) #Read each symbol
    if(symbol == '\n'):
      current_line += 1
    if(symbol == ''): #end of file
      if(last_syntax_result):
        printToken(last_syntax_result[0], f_out)
        last_parsed_token = last_syntax_result[0]
      else:
        print(f"Lexical Analyzer Error : Cannot Parse at line {last_parsed_token_line}")
      break

    syntax_result = list(filter(lambda syntax: syntax is not None, Syntax.input(symbol, last_parsed_token)))
    if(not syntax_result):
      if(last_syntax_result): #token parse succeeded
        printToken(last_syntax_result[0], f_out)
        last_parsed_token = last_syntax_result[0]
        last_parsed_token_line = current_line
        Syntax.reset()
        if(symbol == '\n'):
          current_line -= 1
        f_in.seek(f_in.tell() - 1, os.SEEK_SET)
        
    last_syntax_result = syntax_result

  f_in.close()
  f_out.close()


  # Auto Test Code Below (Remove for submit file)
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