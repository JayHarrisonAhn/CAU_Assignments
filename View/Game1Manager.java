package View;

import Piece.ChessPiece;
import View.panels.ChessBoard1;
import View.panels.ChessBoardCell;
import View.panels.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game1Manager extends JFrame implements ActionListener{

	private ChessBoard1 board = new ChessBoard1();
	
	ChessPiece onHand;
	
	public Game1Manager() {
		setSize(800, 800);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setLocationRelativeTo(null);
		
		add(board);
		setLayout(new GridLayout(1, 1));
		
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				board.cells[i][j].addActionListener(this);
			}
		}
		
		
		
		
		
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		ChessBoardCell selected = (ChessBoardCell) e.getSource();
		
		
		if(onHand != null) {//손에 들고있을 상
			selected.piece = onHand;
			onHand = null;
			board.refresh();
		}
		else if(selected.piece == null) {		//아무것도 없는 칸을 선택했을 경우에는 아무것도 안하고 지나간다.
			
		}
		else {
			onHand = selected.piece;
			selected.piece = null;
			selected.setBackground(Color.YELLOW);
		}
		
		
	}
	
	void move(Position from, Position to) {
		
	}
}
