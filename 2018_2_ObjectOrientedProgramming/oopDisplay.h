#pragma once
#include <iostream>
#include <string>
#include "oopBoard.h"

#define DISPLAY_MAX_ROW 30
#define DISPLAY_MAX_COL 50

#define BOARD_START_ROW 3
#define BOARD_START_COL 3

#define DASHBOARD_START_ROW 5
#define DASHBOARD_START_COL 40

#define SHUFFLE_STRING_START_ROW 20
#define SHUFFLE_STRING_START_COLUMN 2

#define MESSAGE_START_ROW 17
#define MESSAGE_START_COL 4

using namespace std;



class Display {
private:
	Board* board;
public:
	string display[DISPLAY_MAX_ROW][DISPLAY_MAX_COL];
	Display(Board* board) {
		this->board = board;
		for (int i = 0;i < DISPLAY_MAX_ROW;i++) {
			for (int j = 0;j < DISPLAY_MAX_COL;j++) {
				display[i][j] = " ";
			}
		}
	}

	void refresh() {
		system("cls");
		for (int i = 0;i < DISPLAY_MAX_ROW;i++) {
			for (int j = 0;j < DISPLAY_MAX_COL;j++) {
				display[i][j] = " ";
			}
		}


		refreshBoard();
		refreshShuffle();
		refreshDashboard();
		refreshMessage();
		showDisplay();
	}
	void showDisplay() {
		for (int i = 0;i < DISPLAY_MAX_ROW;i++) {
			for (int j = 0;j < DISPLAY_MAX_COL;j++) {
				cout << display[i][j];
			}
			cout << endl;
		}
	}

	void refreshShuffle() {
		display[SHUFFLE_STRING_START_ROW][SHUFFLE_STRING_START_COLUMN] = "1 : New Game";
		display[SHUFFLE_STRING_START_ROW+1][SHUFFLE_STRING_START_COLUMN] = "2 : Save My Score";
		display[SHUFFLE_STRING_START_ROW + 2][SHUFFLE_STRING_START_COLUMN] = "3 : Show Other People's Score";
	}
	void refreshMessage() {
		display[MESSAGE_START_ROW][MESSAGE_START_COL] = board->message;
	}
	void refreshDashboard() {
		display[DASHBOARD_START_ROW][DASHBOARD_START_COL] = "Movement : " + to_string(board->moveCount);

		int shownScores = 0;
		for (int i = (board->gameRecords.size() - 1);i >= 0 && shownScores <= 4;i--) {
			display[DASHBOARD_START_ROW - i + board->gameRecords.size()][DASHBOARD_START_COL] = "Game " + to_string(i + 1) + " : " + to_string(board->gameRecords[i].movecount);
			shownScores++;
		}
	}

	void refreshBoard() {
		for (int i = 0; i<7; i++) {
			for (int j = 0; j<7; j++) {
				if (board->board[i][j] != 0) {
					display[(int)(1.5*i + 0) + BOARD_START_ROW][(int)(3 * j + 0) + BOARD_START_COL]= "忙";
					display[(int)(1.5*i + 1) + BOARD_START_ROW][(int)(3 * j + 0) + BOARD_START_COL]= "弛";
					display[(int)(1.5*i + 2) + BOARD_START_ROW][(int)(3 * j + 0) + BOARD_START_COL]= "戌";
					display[(int)(1.5*i + 0) + BOARD_START_ROW][(int)(3 * j + 1) + BOARD_START_COL]= "式";
					display[(int)(1.5*i + 0) + BOARD_START_ROW][(int)(3 * j + 2) + BOARD_START_COL]= "式";
					display[(int)(1.5*i + 0) + BOARD_START_ROW][(int)(3 * j + 3) + BOARD_START_COL]= "式";
					display[(int)(1.5*i + 0) + BOARD_START_ROW][(int)(3 * j + 4) + BOARD_START_COL]= "式";
					display[(int)(1.5*i + 0) + BOARD_START_ROW][(int)(3 * j + 5) + BOARD_START_COL]= "忖";
					display[(int)(1.5*i + 1) + BOARD_START_ROW][(int)(3 * j + 5) + BOARD_START_COL]= "弛";
					display[(int)(1.5*i + 2) + BOARD_START_ROW][(int)(3 * j + 5) + BOARD_START_COL]= "戎";
					display[(int)(1.5*i + 2) + BOARD_START_ROW][(int)(3 * j + 1) + BOARD_START_COL]= "式";
					display[(int)(1.5*i + 2) + BOARD_START_ROW][(int)(3 * j + 2) + BOARD_START_COL]= "式";
					display[(int)(1.5*i + 2) + BOARD_START_ROW][(int)(3 * j + 3) + BOARD_START_COL]= "式";
					display[(int)(1.5*i + 2) + BOARD_START_ROW][(int)(3 * j + 4) + BOARD_START_COL]= "式";
					display[(int)(1.5*i + 1) + BOARD_START_ROW][(int)(3 * j + 2) + BOARD_START_COL]= to_string((int)board->board[i][j] / 10)[0];
					display[(int)(1.5*i + 1) + BOARD_START_ROW][(int)(3 * j + 3) + BOARD_START_COL]= to_string((int)board->board[i][j] % 10)[0];
				}
			}
		}
	}
};