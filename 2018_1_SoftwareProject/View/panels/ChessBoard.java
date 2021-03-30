package View.panels;

import javax.swing.JButton;
import javax.swing.JPanel;

public abstract class ChessBoard extends JPanel {
	public ChessBoardCell[][] cells;
	
	public abstract void putPieces();
	public abstract void refresh();
}
