package com.github.lbr0452000.chess;

import javax.swing.JFrame;

import com.github.lbr0452000.chess.panels.ChessBoard;
import com.github.lbr0452000.chess.panels.InitialPanel;

public class GameManager {
	JFrame frame = new JFrame("Chess");
	ChessBoard board;
	
	public GameManager() {
	    frame.setSize(800, 800);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setLocationRelativeTo(null);
	    InitialPanel initialPanel = new InitialPanel();
	    frame.add(initialPanel.panel);
	    frame.setVisible(true);
	    
	}
	
	void start() {
		
	}
}
