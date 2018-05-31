package com.github.lbr0452000.chess;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.github.lbr0452000.chess.panels.ChessBoard;
import com.github.lbr0452000.chess.panels.ChessBoard1;

public class ChooseManager extends JFrame {//게임 모드 선택하는 매니
	ChessBoard board;
	
	public ChooseManager() {
		super();
		
	    setSize(400, 300);//화면 크기를 지
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//x버튼 누르면 프로그램 종료되도록 설
	    setLocationRelativeTo(null);//???
	    
	    JButton mode1 = new JButton("1 vs 1");//1대1버튼 생성 
		JButton mode2 = new JButton("2 vs 2");//2대2버튼 생성 
	    
		mode1.addActionListener(new startMode1());//1대1버튼이 눌러지면 startMode1() 실행 
		mode2.addActionListener(new startMode2());
		
		add(mode1);//1대1버튼을 화면에 추가 
		add(mode2);
		
		setLayout(new FlowLayout(FlowLayout.CENTER, 0, 150));//레이아웃 설정 
		setVisible(true);//화면이 보이도록 설정 

	}
	
	private class startMode1 implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			System.out.println("1대1모드 실행");//콘솔창에 출력 
			dispose();//현재창은 종료(닫기) 
			new Game1Manager();//게임1매니저 생
		}
	}
	
	private class startMode2 implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			System.out.println("2대2모드 실행");
		}
	}
	
}
