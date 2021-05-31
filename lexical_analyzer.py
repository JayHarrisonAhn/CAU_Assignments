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
    DFA_Graph('vtype', [
      ["i", "n", "t"],
      [2, None, None],
      [None, 3, None],
      [None, None, 4],
      [None, None, None]
    ], [4], []),
    DFA_Graph('vtype', [
      ["c", "h", "a", "r"],
      [2, None, None, None],
      [None, 3, None, None],
      [None, None, 4, None],
      [None, None, None, 5],
      [None, None, None, None]
    ], [5], []),
    DFA_Graph('vtype', [
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
    DFA_Graph('vtype', [
      ["S", "t", "r", "i", "n", "g"],
      [2, None, None, None, None, None],
      [None, 3, None, None, None, None],
      [None, None, 4, None, None, None],
      [None, None, None, 5, None, None],
      [None, None, None, None, 6, None],
      [None, None, None, None, None, 7],
      [None, None, None, None, None, None],
    ], [7], []),
    DFA_Graph('character', [
      ["'", "1234567890", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ", None],
      [2, None, None, None],
      [None, 3, 4, 5],
      [6, None, None, None],
      [6, None, None, None],
      [6, None, None, None],
      [None, None, None, None],
    ], [6], []),
    DFA_Graph('boolstr', [
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
    DFA_Graph('literal', [
      ['"', None],
      [2, None],
      [3, 2],
      [None, None]
    ], [3], []),
    DFA_Graph('if', [
      ["i", "f"],
      [2, None],
      [None, 3],
      [None, None],
    ], [3], []),
    DFA_Graph('else', [
      ["e", "l", "s"],
      [2, None, None],
      [None, 3, None],
      [None, None, 4],
      [5, None, None],
      [None, None, None]
    ], [5], []),
    DFA_Graph('while', [
      ["w", "h", "i", "l", "e"],
      [2, None, None, None, None],
      [None, 3, None, None, None],
      [None, None, 4, None, None],
      [None, None, None, 5, None],
      [None, None, None, None, 6],
      [None, None, None, None, None]
    ], [6], []),
    DFA_Graph('class', [
      ["c", "l", "a", "s"],
      [2, None, None, None],
      [None, 3, None, None],
      [None, None, 4, None],
      [None, None, None, 5],
      [None, None, None, 6],
      [None, None, None, None]
    ], [6], []),
    DFA_Graph('return', [
      ["r", "e", "t", "u", "n"],
      [2, None, None, None, None],
      [None, 3, None, None, None],
      [None, None, 4, None, None],
      [None, None, None, 5, None],
      [6, None, None, None, None],
      [None, None, None, None, 7],
      [None, None, None, None, None],
    ], [7], []),
    DFA_Graph('addsub', [
      ["+-"],
      [2],
      [None]
    ], [2], []),
    DFA_Graph('multdiv', [
      ["*/"],
      [2],
      [None]
    ], [2], []),
    DFA_Graph('assign', [
      ["="],
      [2],
      [None]
    ], [2], []),
    DFA_Graph('comp', [
      [">", "<", "!", "="],
      [2, 3, 4, 5],
      [None, None, None, 6],
      [None, None, None, 6],
      [None, None, None, 7],
      [None, None, None, 7],
      [None, None, None, None],
      [None, None, None, None],
    ], [2, 3, 6, 7], []),
    DFA_Graph('num', [
      ["-", "0123456789"],
      [2, 3],
      [None, 3],
      [None, 3],
    ], [3], ['id', 'num']),
    DFA_Graph('semi', [
      [";"],
      [2],
      [None]
    ], [2], []),
    DFA_Graph('lparen', [
      ["("],
      [2],
      [None]
    ], [2], []),
    DFA_Graph('rparen', [
      [")"],
      [2],
      [None]
    ], [2], []),
    DFA_Graph('lbrace', [
      ["{"],
      [2],
      [None]
    ], [2], []),
    DFA_Graph('rbrace', [
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
    DFA_Graph('comma', [
      [","],
      [2],
      [None]
    ], [2], []),
    DFA_Graph('id', [
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


if __name__ == "__main__":
  main(sys.argv[1])