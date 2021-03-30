package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChooseManager extends JFrame {

	public ChooseManager() {
		this.setTitle("SW Chess Game");
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setLocationRelativeTo(null);

	    MyTitleImage title = new MyTitleImage();
		title.setLayout(new FlowLayout(FlowLayout.CENTER, 1280, 100));
		setLocationRelativeTo(null);

	    JButton mode1 = new JButton("1 vs 1 모드");
		JButton mode2 = new JButton("2 vs 2 모드");
		JButton exit = new JButton("Exit");

		mode1.setPreferredSize(new Dimension(400, 80));
		mode2.setPreferredSize(new Dimension(400, 80));
		exit.setPreferredSize(new Dimension(400, 80));

		mode1.setBackground(Color.WHITE);
		mode1.setOpaque(true);
		mode2.setBackground(Color.WHITE);
		mode2.setOpaque(true);
		exit.setBackground(Color.WHITE);
		exit.setOpaque(true);

		mode1. setFont(new Font("고딕",Font.BOLD,40));
		mode2. setFont(new Font("고딕",Font.BOLD,40));
		exit. setFont(new Font("고딕",Font.BOLD,40));

		mode1.setBorderPainted(false);
		mode2.setBorderPainted(false);
		exit.setBorderPainted(false);



		mode1.addActionListener(new startMode1());
		mode2.addActionListener(new startMode2());
		exit.addActionListener(new ExitMode());


		title.add(mode1);
		title.add(mode2);
		title.add(exit);

		this.add(title);
		this.setSize(1280,720);
		this.setVisible(true);


	}
	
	private class startMode1 implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			System.out.println("1대1모드 실행");
			dispose();
			new Game1Manager();
		}
	}
	
	private class startMode2 implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			System.out.println("2대2모드 실행");
			dispose();
			new Game2Manager();
		}
	}
	private class ExitMode implements ActionListener{
		public void actionPerformed(ActionEvent e){
			System.exit(0);
		}
	}

}
