package com.github.lbr0452000.chess.panels;

import javax.swing.JButton;
import javax.swing.JPanel;

public interface ChessBoard {
	JPanel board = new JPanel();
	JButton[][] cells = new JButton[8][8];
}
