package View.panels;

import Piece.*;
import java.awt.*;
import static Piece.ChessPieceSprite.ChessPieceSpriteType.*;

public class ChessBoard2 extends ChessBoard {
	
	public ChessBoard2() {
		//initialize board
		setSize(1400, 1400);
	    setLayout(new GridLayout(14,14));
		
		//initialize cells
		super.cells = new ChessBoardCell[14][14];
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
		cells[13][3].piece = new Rook(0, WHITE_ROOK);
		cells[13][10].piece = new Rook(0, WHITE_ROOK);
		cells[13][4].piece = new Knight(0, WHITE_KNIGHT);
		cells[13][9].piece = new Knight(0, WHITE_KNIGHT);
		cells[13][5].piece = new Bishop(0, WHITE_BISHOP);
		cells[13][8].piece = new Bishop(0, WHITE_BISHOP);
		cells[13][6].piece = new King(0, WHITE_KING);
		cells[13][7].piece = new Queen(0, WHITE_QUEEN);
		for (int i = 3; i < 11; i++) {
			cells[12][i].piece = new Pawn(0, WHITE_PAWN);
		}
		
		cells[3][0].piece = new Rook(1, RED_ROOK);
		cells[10][0].piece = new Rook(1, RED_ROOK);
		cells[4][0].piece = new Knight(1, RED_KNIGHT);
		cells[9][0].piece = new Knight(1, RED_KNIGHT);
		cells[5][0].piece = new Bishop(1, RED_BISHOP);
		cells[8][0].piece = new Bishop(1, RED_BISHOP);
		cells[6][0].piece = new King(1, RED_KING);
		cells[7][0].piece = new Queen(1, RED_QUEEN);
		for (int i = 3; i < 11; i++) {
			cells[i][1].piece = new Pawn(1, RED_PAWN);
		}

        cells[0][3].piece = new Rook(2, BLACK_ROOK);
        cells[0][10].piece = new Rook(2, BLACK_ROOK);
        cells[0][4].piece = new Knight(2, BLACK_KNIGHT);
        cells[0][9].piece = new Knight(2, BLACK_KNIGHT);
        cells[0][5].piece = new Bishop(2, BLACK_BISHOP);
        cells[0][8].piece = new Bishop(2, BLACK_BISHOP);
        cells[0][7].piece = new King(2, BLACK_KING);
        cells[0][6].piece = new Queen(2, BLACK_QUEEN);
        for (int i = 3; i < 11; i++) {
            cells[1][i].piece = new Pawn(2, BLACK_PAWN);
        }

        cells[3][13].piece = new Rook(3, GREEN_ROOK);
        cells[10][13].piece = new Rook(3, GREEN_ROOK);
        cells[4][13].piece = new Knight(3, GREEN_KNIGHT);
        cells[9][13].piece = new Knight(3, GREEN_KNIGHT);
        cells[5][13].piece = new Bishop(3, GREEN_BISHOP);
        cells[8][13].piece = new Bishop(3, GREEN_BISHOP);
        cells[7][13].piece = new King(3, GREEN_KING);
        cells[6][13].piece = new Queen(3, GREEN_QUEEN);
        for (int i = 3; i < 11; i++) {
            cells[i][12].piece = new Pawn(3, GREEN_PAWN);
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
	    			cells[i][j].setImage(cells[i][j].piece.image);
	    		}
	    		else {
	    			cells[i][j].setImage(null);
	    		}
	    	}
		}
	}
}