package com.github.lbr0452000.chess.panels;
import javax.swing.*;

import com.github.io.lbr0452000.chess.pieces.*;

import java.awt.Color;
import java.awt.GridLayout;

public class ChessBoard1 extends JPanel {
	public Color team1 = Color.BLACK;
	public Color team2 = Color.GRAY;
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
			cells[1][i].piece = new Pawn(team1);
		}
		for(int i=0;i<8;i++) {
			cells[6][i].piece = new Pawn(team2);
		}
		
		//나이트 놓기
		cells[0][1].piece = new Knight(team1);
		cells[0][6].piece = new Knight(team1);
		cells[7][1].piece = new Knight(team2);
		cells[7][6].piece = new Knight(team2);
		
		//비숍 놓기 
		cells[0][2].piece = new Bishop(team1);
		cells[0][5].piece = new Bishop(team1);
		cells[7][2].piece = new Bishop(team2);
		cells[7][5].piece = new Bishop(team2);
		
		//룩 놓기
		cells[0][0].piece = new Rook(team1);
		cells[0][7].piece = new Rook(team1);
		cells[7][0].piece = new Rook(team2);
		cells[7][7].piece = new Rook(team2);
		
		//퀸 놓기
		cells[0][3].piece = new Queen(team1);
		cells[7][4].piece = new Queen(team2);

		// 놓기
		cells[0][4].piece = new King(team1);
		cells[7][3].piece = new King(team2);
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