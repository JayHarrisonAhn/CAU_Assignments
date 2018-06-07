package View.panels;

import Piece.*;
import java.awt.*;
import static Piece.ChessPieceSprite.ChessPieceSpriteType.*;

public class ChessBoard1 extends ChessBoard {
	
	public ChessBoard1() {
		//initialize board
		setSize(800, 800);
	    setLayout(new GridLayout(8,8));
		
		//initialize cells
		super.cells = new ChessBoardCell[8][8];
		for(int i=0; i<8; i++) {
	    	for(int j=0; j<8; j++) {
	    		cells[i][j] = new ChessBoardCell();
	    		
	    		cells[i][j].position.x = i;
	    		cells[i][j].position.y = j;
	    		cells[i][j].setOpaque(true);
	    		add(cells[i][j]);
	    	}
	    }
		putPieces();
		refresh();
	}
	
	public void putPieces() {
		//말 놓기 : BLACK은 0, WHITE는 1
		cells[0][0].piece = new Rook(2, BLACK_ROOK);
		cells[0][7].piece = new Rook(2, BLACK_ROOK);
		cells[0][1].piece = new Knight(2, BLACK_KNIGHT);
		cells[0][6].piece = new Knight(2, BLACK_KNIGHT);
		cells[0][2].piece = new Bishop(2, BLACK_BISHOP);
		cells[0][5].piece = new Bishop(2, BLACK_BISHOP);
		cells[0][4].piece = new King(2, BLACK_KING);
		cells[0][3].piece = new Queen(2, BLACK_QUEEN);
		for (int i = 0; i < 8; i++) {
			cells[1][i].piece = new Pawn(2, BLACK_PAWN);
		}
		cells[7][0].piece = new Rook(0, WHITE_ROOK);
		cells[7][7].piece = new Rook(0, WHITE_ROOK);
		cells[7][1].piece = new Knight(0, WHITE_KNIGHT);
		cells[7][6].piece = new Knight(0, WHITE_KNIGHT);
		cells[7][2].piece = new Bishop(0, WHITE_BISHOP);
		cells[7][5].piece = new Bishop(0, WHITE_BISHOP);
		cells[7][4].piece = new King(0, WHITE_KING);
		cells[7][3].piece = new Queen(0, WHITE_QUEEN);
		for (int i = 0; i < 8; i++) {
			cells[6][i].piece = new Pawn(0, WHITE_PAWN);
		}
	}
	
	public void refresh() {
		for(int i=0; i<8; i++) {
	    	for(int j=0; j<8; j++) {
	    		if (((i+j)%2)==0) {
	    			cells[i][j].setBackground(Color.WHITE);
	    		}
	    		else {
	    			cells[i][j].setBackground(Color.GRAY);
	    		}
	    		
	    		if (cells[i][j].piece!=null) {
	    			cells[i][j].setImage(cells[i][j].piece.image);
	    		}
	    		else {
	    			cells[i][j].setImage(null);
	    		}
	    		
	    	}
		}
	}
}