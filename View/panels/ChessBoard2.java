package View.panels;

import Piece.*;

import javax.swing.*;
import java.awt.*;

import static Piece.ChessPieceSprite.ChessPieceSpriteType.*;

public class ChessBoard2 extends ChessBoard {
	public ChessBoardCell[][] cells = new ChessBoardCell[14][14];
	
	public ChessBoard2() {
		//initialize board
		setSize(1400, 1400);
	    setLayout(new GridLayout(14,14));
		
		//initialize cells
		for(int i=0; i<14; i++) {
	    	for(int j=0; j<14; j++) {
	    		if(((i<3)||(i>10))&&((j<3)||(j>10))) {
	    			cells[i][j] = new ChessBoardCell();
	    			cells[i][j].use = false;
	    		}
	    		else {
	    			cells[i][j] = new ChessBoardCell();
		    		cells[i][j].position.x = i;
		    		cells[i][j].position.y = j;
		    		cells[i][j].setOpaque(true);
	    		}
	    		add(cells[i][j], BorderLayout.CENTER);
	    	}
	    }
		putPieces();
		refresh();
	}
	
	public void putPieces() {
		//폰 놓기
		cells[0][3].piece = new Rook(1, Color.BLACK, BLACK_ROOK);
		cells[0][10].piece = new Rook(1, Color.BLACK, BLACK_ROOK);
		cells[0][4].piece = new Knight(1, Color.BLACK, BLACK_KNIGHT);
		cells[0][9].piece = new Knight(1, Color.BLACK, BLACK_KNIGHT);
		cells[0][5].piece = new Bishop(1, Color.BLACK, BLACK_BISHOP);
		cells[0][8].piece = new Bishop(1, Color.BLACK, BLACK_BISHOP);
		cells[0][6].piece = new King(1, Color.BLACK, BLACK_KING);
		cells[0][7].piece = new Queen(1, Color.BLACK, BLACK_QUEEN);
		for (int i = 3; i < 11; i++) {
			cells[1][i].piece = new Pawn(1, Color.BLACK, BLACK_PAWN);
		}
		cells[13][3].piece = new Rook(1, Color.WHITE, WHITE_ROOK);
		cells[13][10].piece = new Rook(1, Color.WHITE, WHITE_ROOK);
		cells[13][4].piece = new Knight(1, Color.WHITE, WHITE_KNIGHT);
		cells[13][9].piece = new Knight(1, Color.WHITE, WHITE_KNIGHT);
		cells[13][5].piece = new Bishop(1, Color.WHITE, WHITE_BISHOP);
		cells[13][8].piece = new Bishop(1, Color.WHITE, WHITE_BISHOP);
		cells[13][7].piece = new King(1, Color.WHITE, WHITE_KING);
		cells[13][6].piece = new Queen(1, Color.WHITE, WHITE_QUEEN);
		for (int i = 3; i < 11; i++) {
			cells[12][i].piece = new Pawn(2, Color.WHITE, WHITE_PAWN);
		}
	}
	
	public void refresh() {
		for(int i=0; i<14; i++) {
	    	for(int j=0; j<14; j++) {
	    		if(((i<3)||(i>10))&&((j<3)||(j>10))) {
	    			cells[i][j].setBackground(Color.GRAY);
	    		}
	    		else if (((i+j)%2)==0) {
	    			cells[i][j].setBackground(Color.WHITE);
	    		}
	    		else {
	    			cells[i][j].setBackground(Color.DARK_GRAY);
	    		}
	    		
	    		if (cells[i][j].piece!=null) {
	    			cells[i][j].piece.image.getScaledInstance(10, 10, Image.SCALE_SMOOTH);
	    			cells[i][j].setIcon(new ImageIcon(cells[i][j].piece.image));
	    		}
	    		else {
	    			cells[i][j].setIcon(null);
	    		}
	    	}
		}
	}
}