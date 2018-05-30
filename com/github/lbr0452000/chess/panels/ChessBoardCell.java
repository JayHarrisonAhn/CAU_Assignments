package com.github.lbr0452000.chess.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import com.github.io.lbr0452000.chess.pieces.ChessPiece;
import com.github.lbr0452000.chess.Game1Manager;

public class ChessBoardCell extends JButton {
	public ChessPiece piece = null;
	public Position position = new Position();
}
