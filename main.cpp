//
//  main.cpp
//  OOPGame
//
//  Created by Harrison on 13/11/2018.
//  Copyright © 2018 Harrison. All rights reserved.
//

#include <iostream>
#include <fstream>
#include <conio.h>
#include <string>
#include "oopBoard.h"
#include "oopDisplay.h"
using namespace std;

int main(int argc, const char * argv[]) {
	Board board;
	Display display(&board);
	//board.shuffle();
	while (true) {
		
		display.refresh();
		board.message = "";
		int input;
		input = getch();
		if (input == 224) {
			input = getch();
			if (input == 72) {	//up
				board.moveUp(true);
			} else if (input == 80) {	//down
				board.moveDown(true);
			} else if (input == 75) {	//left
				board.moveLeft(true);
			} else if (input == 77) {	//right
				board.moveRight(true);
			}
		}
		else {
			string name;
			switch (input) {
			case '1':
				board.shuffle();
				break;
			case '2':
				if (board.maxScore() > 0) {
					board.message = "Enter your name (Your name should not contain blank)";
					display.refresh();
					cout << endl << "name : ";
					cin >> name;
					ofstream file("score.txt", ios::app);
					file << endl << name + " " + to_string(board.maxScore());
					board.message = name + "'s score is saved";
				} else {
					board.message = "There's no score to save.";
				}
				break;
			case '3':
				ifstream inFile("score.txt");
				system("cls");
				if (inFile.is_open()) {
					while (!inFile.eof()) {
						string temp;
						getline(inFile, temp);
						cout << temp << endl;
					}
					
				}
				else {
					cout << "Can't find save scores" << endl;
				}
				cout << endl << "Press any key to come back to game";
				getch();
				inFile.close();
				break;
			}
		}
	}
	return 0;
}
