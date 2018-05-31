package com.github.lbr0452000.chess;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.github.io.lbr0452000.chess.pieces.ChessPiece;
import com.github.lbr0452000.chess.panels.ChessBoard1;
import com.github.lbr0452000.chess.panels.ChessBoardCell;
import com.github.lbr0452000.chess.panels.Position;

public class Game1Manager extends JFrame implements ActionListener{//Game1Manager는 JFrame을 상속받는 동시에 리스너의 역할도 수행합니다.

	private ChessBoard1 board = new ChessBoard1();//내부 프로퍼티인 보드 생성 
	ChessPiece onHand;//onHand 생성 
	
	public Game1Manager() {
		setSize(800, 800);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setLocationRelativeTo(null);
		
		add(board);//보드 추가 
		setLayout(new GridLayout(1, 1));
		
		for(int i=0;i<8;i++) {//각각의 셀의 리스너로 자기 자신을 추가(각각의 셀이 눌리면 밑에 있는 actionPerformed가 실행된다.) 
			for(int j=0;j<8;j++) {
				board.cells[i][j].addActionListener(this);
			}
		}
		
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ChessBoardCell selected = (ChessBoardCell) e.getSource();//selected라는 레퍼런스 선언(현재 버튼이 눌린 ChessBoardCell을 갖는다)  
		if(onHand != null) {			//손(onHand)에 무언가 들고있을 상황 
			selected.piece = onHand;	// onHand에 있는 ChessPiece를 selected.piece에 집어넣는다.
			onHand = null;				//onHand를 비운다. 
			board.refresh();			//board를 새로 띄운다(새로고침)
		}
		else if(selected.piece == null) {		//아무것도 없는 칸을 선택했을 경우
			
		}
		else {//손(onHand)에 아무것도 들고있지 않은 상황 
			onHand = selected.piece;//선택된 칸의 piece를 손(onHand)에 넣는다. 
			System.out.print(selected.position.x);//콘솔에 선택된 위치를 출력시킨다. 
			System.out.print(",");
			System.out.println(selected.position.y);
			
			selected.piece = null;//선택된 ChessBoardCell의 piece를 비운다.
			selected.setBackground(Color.YELLOW);//선택된 ChessBoardCell을 노란색으로 채운다.
		}
		
		
	}
	
	void move(Position from, Position to) {//TODO
		
	}
}
