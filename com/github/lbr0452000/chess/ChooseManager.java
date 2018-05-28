package com.github.lbr0452000.chess;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.github.lbr0452000.chess.panels.ChessBoard;
import com.github.lbr0452000.chess.panels.ChessBoard1;

public class ChooseManager extends JFrame {
	ChessBoard board;
	
	public ChooseManager() {
		super();
		
	    setSize(400, 300);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setLocationRelativeTo(null);
	    
	    JButton mode1 = new JButton("1 vs 1");
		JButton mode2 = new JButton("2 vs 2");
	    
		mode1.addActionListener(new startMode1());
		mode2.addActionListener(new startMode2());
		
		add(mode1);
		add(mode2);
		
		setLayout(new FlowLayout(FlowLayout.CENTER, 0, 150));
		setVisible(true);

	}
	
	private class startMode1 implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			System.out.println("1대1모드 실행");
			dispose();
			new Game1Manager();
		}
	}
	
	private class startMode2 implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			System.out.println("2대2모드 실행");
		}
	}
	
}
