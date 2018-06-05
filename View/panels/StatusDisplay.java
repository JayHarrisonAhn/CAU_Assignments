package View.panels;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class StatusDisplay extends JPanel implements ActionListener {   //TODO : actionlistener는 테스트를 위한것입니다!
    JLabel turn = new JLabel();//자신의 차례를 보여주는 라벨
    JLabel judge = new JLabel();
    JButton test = new JButton("테스트 버튼");   //TODO : 제거

    public StatusDisplay() {
        setPreferredSize(new Dimension(800, 100));
        setLayout(new BorderLayout());


        add("Center", judge);
        judge.setPreferredSize(new Dimension(300,100));
        judge.setHorizontalAlignment(JLabel.CENTER);
        judge.setVisible(false);

//        add("East", test);                   //TODO : 제거
//        test.addActionListener(this);
//        test.setVisible(true);

        add("West", turn);
        turn.setPreferredSize(new Dimension(100,100));//turn 설정
        turn.setHorizontalAlignment(JLabel.CENTER);
        updateTurn(0);
        turn.setVisible(true);

        setVisible(true);
    }

    public void updateTurn(int to) {//turn을 업데이트 시켜주는 메서드
        switch (to) {
            case 0:
                turn.setText("검정팀 차례");
                break;
            case 1:
                turn.setText("흰팀 차례");
                break;
        }
    }

    public void showNothing() {
        this.judge.setVisible(false);
    }

    public void showCheck() {
        ImageIcon icon = new ImageIcon("src/View/panels/displayImages/Check.jpg");
        Image temp = icon.getImage();
        temp = temp.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        icon = new ImageIcon(temp);

        this.judge.setIcon(icon);
        this.judge.setText("췍(Check, 왕이 공격받고 있습니다.)");
        this.judge.setVisible(true);
    }



    @Override       //TODO : 제거
    public void actionPerformed(ActionEvent e) {
        showCheck();
    }
}
