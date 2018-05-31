package com.github.lbr0452000.chess.panels;
import javax.swing.*;

import com.github.io.lbr0452000.chess.pieces.*;

import java.awt.Color;
import java.awt.GridLayout;

public class ChessBoard1 extends JPanel {
	public ChessBoardCell[][] cells = new ChessBoardCell[8][8];
	
	public ChessBoard1() {
		//initialize board
		setSize(800, 800);
	    setLayout(new GridLayout(8,8));
		
		//initialize cells
		for(int i=0; i<8; i++) {
	    	for(int j=0; j<8; j++) {
	    		cells[i][j] = new ChessBoardCell();
	    		
	    		cells[i][j].position.x = i;
	    		cells[i][j].position.y = j;
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
			cells[6][i].piece = new Pawn(Color.GRAY);
		}
		
		//비숍 놓기 
		cells[0][2].piece = new Bishop(Color.BLACK);
		cells[0][5].piece = new Bishop(Color.BLACK);
		cells[7][2].piece = new Bishop(Color.GRAY);
		cells[7][5].piece = new Bishop(Color.GRAY);
	}
	
	public void refresh() {
		for(int i=0; i<8; i++) {
	    	for(int j=0; j<8; j++) {
	    		if(((i+j)%2)==0) {
	    			cells[i][j].setBackground(Color.WHITE);
	    		}
	    		else {
	    			cells[i][j].setBackground(Color.DARK_GRAY);
	    		}
	    		if(cells[i][j].piece!=null) {
	    			cells[i][j].setText(cells[i][j].piece.name);
	    			cells[i][j].setForeground(cells[i][j].piece.team);
	    		}
	    		else {
	    			cells[i][j].setText(null);
	    		}
	    		
	    	}
		}
	}
}