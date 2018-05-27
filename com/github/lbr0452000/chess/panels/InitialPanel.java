package com.github.lbr0452000.chess.panels;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class InitialPanel {
	public JPanel panel = new JPanel();
	
	JButton mode1 = new JButton("1 vs 1");
	JButton mode2 = new JButton("2 vs 2");
	
	public InitialPanel() {
		mode1.addActionListener(new startMode1());
		mode2.addActionListener(new startMode2());
		
		panel.add(mode1);
		panel.add(mode2);
		
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 20));

		panel.setVisible(true);
	}
	
	private class startMode1 implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			System.out.println("1대1모드 실행");
		}
	}
	
	private class startMode2 implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			System.out.println("2대2모드 실행");
		}
	}
}
