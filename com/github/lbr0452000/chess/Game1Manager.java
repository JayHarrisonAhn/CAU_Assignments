package com.github.lbr0452000.chess;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.*;
import com.github.lbr0452000.chess.panels.ChessBoard1;

public class Game1Manager extends JFrame {

	private ChessBoard1 board = new ChessBoard1();
	
	public Game1Manager() {
		setSize(800, 800);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setLocationRelativeTo(null);
		
		add(board);
		setLayout(new GridLayout(1, 1));
		
		
		
		
		
		
		
		setVisible(true);
	}
}
