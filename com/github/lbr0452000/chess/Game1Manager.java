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

public class Game1Manager extends JFrame implements ActionListener{

	private ChessBoard1 board = new ChessBoard1();
	
	ChessPiece onHand;
	
	public Game1Manager() {
		setSize(800, 800);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setLocationRelativeTo(null);
		
		add(board);
		setLayout(new GridLayout(1, 1));
		
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				board.cells[i][j].addActionListener(this);
			}
		}
		
		
		
		
		
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		ChessBoardCell selected = (ChessBoardCell) e.getSource();
		
		
		if(onHand != null) {//손에 들고있을 상
			selected.piece = onHand;
			onHand = null;
			board.refresh();
		}
		else {
			onHand = selected.piece;
			System.out.print(selected.position.x);
			System.out.print(",");
			System.out.println(selected.position.y);
			
			selected.piece = null;
			selected.setBackground(Color.YELLOW);
		}
		
		
	}
	
	void move(Position from, Position to) {
		
	}
}
