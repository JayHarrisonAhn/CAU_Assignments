package com.github.lbr0452000.chess.panels;
import javax.swing.*;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import kr.ac.cau.mecs.lenerd.chess.*;
import kr.ac.cau.mecs.lenerd.chess.ChessPieceSprite.ChessPieceSpriteType;

public class ChessBoard1 extends JPanel {
	JButton[][] cells = new JButton[8][8];
	
	public ChessBoard1() {
		//initialize board
		setSize(800, 800);
	    setLayout(new GridLayout(8,8));
		
		//initialize cells
		for(int i=0; i<8; i++) {
	    	for(int j=0; j<8; j++) {
	    		cells[i][j] = new JButton("Hello");
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
	}
}