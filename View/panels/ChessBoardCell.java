package View.panels;

import Piece.ChessPiece;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ChessBoardCell extends ImagePanel {
	ImagePanel ip = new ImagePanel();
	public ChessPiece piece = null;
	public Position position = new Position();
	public boolean use = true;

	public ChessBoardCell() {
		add(ip);
	}
}
