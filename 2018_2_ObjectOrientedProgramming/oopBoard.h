#pragma once
#include <iostream>
#include <string>
#include <ctime>
#include <vector>
#define MAX_BOARD_SIZE 7

using namespace std;

typedef struct {
	int movecount;
}GameRecord;

class Board {
public:
	string message = "";
	int board[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
	int moveCount = 0;

	vector<GameRecord> gameRecords;

	int maxScore() {
		if (gameRecords.size() == 0) {
			return -1;
		}
		int maxScore = 0;
		for (int i = 0;i < gameRecords.size();i++) {
			if (gameRecords[i].movecount > maxScore)
				maxScore = gameRecords[i].movecount;
		}
		return maxScore;
	}

	Board() {
		int temp = 1;
		for (int i = 0; i<7; i++) {
			for (int j = 0; j<7; j++) {
				board[i][j] = 0;
			}
		}
		for (int i = 0; i<7; i += 2) {
			for (int j = 0; j<7; j += 2) {
				board[i][j] = temp++;
			}
		}
		board[6][6] = 0;
	}
	void shuffle() {
		srand((unsigned int)time(0));
		for (int i = 0;i < 200;i++) {
			switch (rand() % 4) {
			case 0:
				moveLeft(false);
				break;
			case 1:
				moveRight(false);
				break;
			case 2:
				moveUp(false);
				break;
			case 3:
				moveDown(false);
				break;
			}
		}
		moveCount = 0;
	}
	int findEmptyRow() {
		for (int i = 0; i < 7; i += 2) {
			for (int j = 0; j < 7; j += 2) {
				if (board[i][j] == 0)
					return i;
			}
		}
		return -1;
	}
	int findEmptyCol() {
		for (int i = 0; i < 7; i += 2) {
			for (int j = 0; j < 7; j += 2) {
				if (board[i][j] == 0)
					return j;
			}
		}
		return -1;
	}
	void moveLeft(bool checkIfWon) {
		if (checkIfWon&&didWin()) {
			message = "Please start new game.";
			return;
		}
		int emptyRow = findEmptyRow();
		int emptyCol = findEmptyCol();
		if (emptyRow < (MAX_BOARD_SIZE - 0) && emptyCol < (MAX_BOARD_SIZE - 0)) {
			if (emptyRow >= 0 && emptyCol > 0) {
				swap(board[emptyRow][emptyCol - 2], board[emptyRow][emptyCol]);
			}
		}
		if (checkIfWon&&didWin()) {
			GameRecord newRecord;
			newRecord.movecount = moveCount;
			gameRecords.push_back(newRecord);
		}
	}
	void moveRight(bool checkIfWon) {
		if (checkIfWon&&didWin()) {
			message = "Please start new game.";
			return;
		}
		int emptyRow = findEmptyRow();
		int emptyCol = findEmptyCol();
		if (emptyRow < (MAX_BOARD_SIZE - 0) && emptyCol < (MAX_BOARD_SIZE - 1)) {
			if (emptyRow >= 0 && emptyCol >= 0) {
				swap(board[emptyRow][emptyCol + 2], board[emptyRow][emptyCol]);
			}
		}
		if (checkIfWon&&didWin()) {
			GameRecord newRecord;
			newRecord.movecount = moveCount;
			gameRecords.push_back(newRecord);
		}
	}
	void moveUp(bool checkIfWon) {
		if (checkIfWon&&didWin()) {
			message = "Please start new game.";
			return;
		}
		int emptyRow = findEmptyRow();
		int emptyCol = findEmptyCol();
		if (emptyRow < (MAX_BOARD_SIZE - 0) && emptyCol < (MAX_BOARD_SIZE - 0)) {
			if (emptyRow > 0 && emptyCol >= 0) {
				swap(board[emptyRow - 2][emptyCol], board[emptyRow][emptyCol]);
			}
		}
		if (checkIfWon&&didWin()) {
			GameRecord newRecord;
			newRecord.movecount = moveCount;
			gameRecords.push_back(newRecord);
		}
	}
	void moveDown(bool checkIfWon) {
		if (checkIfWon&&didWin()) {
			message = "Please start new game.";
			return;
		}
		int emptyRow = findEmptyRow();
		int emptyCol = findEmptyCol();
		if (emptyRow < (MAX_BOARD_SIZE - 1) && emptyCol < (MAX_BOARD_SIZE - 0)) {
			if (emptyRow >= 0 && emptyCol >= 0) {
				swap(board[emptyRow + 2][emptyCol], board[emptyRow][emptyCol]);
			}
		}
		if (checkIfWon&&didWin()) {
			GameRecord newRecord;
			newRecord.movecount = moveCount;
			gameRecords.push_back(newRecord);
		}
	}
	void swap(int &a, int &b) {
		moveCount++;
		int temp = a;
		a = b;
		b = temp;
	}
	bool didWin() {
		int temp = 1;
		for (int i = 0; i<7; i += 2) {
			for (int j = 0; j<7; j += 2) {
				if (i == 6 && j == 6)
					break;
				if (board[i][j] != temp++)
					return false;
			}
		}
		return true;
	}
};