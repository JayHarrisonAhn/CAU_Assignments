package View.panels;

import Piece.ChessPiece;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ChessBoardCell extends JPanel {
	JLabel label = new JLabel();
	public ChessPiece piece = null;
	public Position position = new Position();
	public boolean use = true;
	
	public void setIcon(ImageIcon icon) {
		if (icon != null) {//icon이 왔을때 
			label.setIcon(icon);
			label.setVisible(true);
		}
		else {
			label.setVisible(false);
		}
		revalidate();
	}
	public ChessBoardCell() {
		add(label);
	}
}
