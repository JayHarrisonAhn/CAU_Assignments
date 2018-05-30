package com.github.lbr0452000.chess;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.github.io.lbr0452000.chess.pieces.ChessPiece;
import com.github.lbr0452000.chess.panels.ChessBoard1;
import com.github.lbr0452000.chess.panels.ChessBoardCell;

public class Game1Manager extends JFrame implements ActionListener{

	private ChessBoard1 board = new ChessBoard1();
	
	ChessBoardCell onHand;
	
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
		onHand = (ChessBoardCell) e.getSource();
		System.out.print("(");
		System.out.print(onHand.position.x);
		System.out.print(",");
		System.out.print(onHand.position.y);
		System.out.println(")");
	}
}
