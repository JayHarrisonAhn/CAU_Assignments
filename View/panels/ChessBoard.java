package View.panels;

import javax.swing.JButton;
import javax.swing.JPanel;

public interface ChessBoard {
	JPanel board = new JPanel();
	JButton[][] cells = new JButton[8][8];
	public JPanel showBoard();
}
