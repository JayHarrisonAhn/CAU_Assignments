package com.github.lbr0452000.chess.panels;
import javax.swing.*;

import com.github.io.lbr0452000.chess.pieces.Pawn;

import java.awt.Color;
import java.awt.GridLayout;

public class ChessBoard1 extends JPanel {
	ChessBoardCell[][] cells = new ChessBoardCell[8][8];
	
	public ChessBoard1() {
		//initialize board
		setSize(800, 800);
	    setLayout(new GridLayout(8,8));
		
		//initialize cells
		for(int i=0; i<8; i++) {
	    	for(int j=0; j<8; j++) {
	    		cells[i][j] = new ChessBoardCell();
	    		if(((i+j)%2)==0) {
	    			cells[i][j].setBackground(Color.WHITE);
	    		}
	    		else {
	    			cells[i][j].setBackground(Color.GRAY);
	    		}
	    		cells[i][j].setOpaque(true);
    			cells[i][j].setBorderPainted(false);
	    		add(cells[i][j]);
	    	}
	    }
		putPieces();
		refresh();
	}
	
	public void putPieces() {
		//폰 놓기
		for(int i=0;i<8;i++) {
			cells[1][i].piece = new Pawn(Color.BLACK);
		}
		for(int i=0;i<8;i++) {
			cells[6][i].piece = new Pawn(Color.WHITE);
		}
	}
	
	public void refresh() {
		for(int i=0; i<8; i++) {
	    	for(int j=0; j<8; j++) {
	    		if(cells[i][j].piece!=null) {
	    			cells[i][j].setText(cells[i][j].piece.name);
	    		}
	    		
	    	}
		}
	}
}