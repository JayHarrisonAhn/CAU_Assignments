package View;

import Piece.ChessPiece;
import View.panels.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Game2Manager extends JFrame implements MouseListener {
	private ChessBoard2 board = new ChessBoard2();
	private int turn = 0;
	
	ChessPiece onHand;
	
	public Game2Manager() {
		setSize(800, 800);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setLocationRelativeTo(null);
		
		add(board);
		setLayout(new GridLayout(1, 1));
		
		for(int i=0;i<14;i++) {
			for(int j=0;j<14;j++) {
				if(((i<3)||(i>10))&&((j<3)||(j>10))) {
	    			
	    		}
				else {
					board.cells[i][j].addMouseListener(this);
				}
			}
		}
		setVisible(true);
	}

	//MouseListener 구현 
	@Override
	public void mouseClicked(MouseEvent e) {
	}
	@Override
	public void mousePressed(MouseEvent e) {
	}
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}
	@Override
	public void mouseReleased(MouseEvent e) {	//mouseClicked는 가끔 인식이 안될 때가 있어서 mouseReleased로 구현합니다.
		ChessBoardCell selected = (ChessBoardCell) e.getSource();
		
		if(onHand != null) {//손에 들고있을 상
			selected.piece = onHand;
			onHand = null;
			board.refresh();
		}
		else if(selected.piece == null) {		//아무것도 없는 칸을 선택했을 경우에는 아무것도 안하고 지나간다.
		}
		else {
			if(turn == selected.piece.team) {
				onHand = selected.piece;
				selected.piece = null;
				selected.setBackground(Color.YELLOW);
				turn = (turn+1)%4;
				System.out.println("온핸드에 올림"+turn);
				
			}
		}
	}
	
	//판정메서드
	boolean isValidMove(Position from, Position to) {//TODO : from에서 to로의 이동이 가능한 것인지 판단하는 메서드 
		return true;//개발되기 전까지는 항상 true를 리턴하게끔 만들어주세요 
	}
	
	Position[] validMoves(Position from) {//TODO : from에서 이동이 가능한 모든 칸의 배열을 리턴하는 메서드 
		return new Position[0];//개발되기 전까지는 항상 이것을 리턴하게끔 만들어주세요 
	}
	
	boolean isCheckmate() {//TODO : 체크메이트 상황인지 판단해주는 메서드  
		return false;//개발되기 전까지는 항상 false를 리턴하게끔 만들어주세요 
	}
	
	boolean isStalemate() {//TODO : Stalemate 상황인지 판단해주는 메서
		return false;//개발되기 전까지는 항상 false를 리턴하게끔 만들어주세요 
	}
}
