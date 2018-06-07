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
                turn.setText("흰팀 차례");
                break;
            case 1:
                turn.setText("빨강팀 차례");
                break;
            case 2:
                turn.setText("검정팀 차례");
                break;
            case 3:
                turn.setText("초록팀 차례");
                break;
        }
    }

    public void showNothing() {
        this.judge.setVisible(false);
    }

    public void showCheck(Color team) {
        ImageIcon icon = new ImageIcon("src/View/panels/displayImages/Check.jpg");
        Image temp = icon.getImage();
        temp = temp.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        icon = new ImageIcon(temp);

        this.judge.setIcon(icon);

        if(team==Color.WHITE) {
            this.judge.setText("Check(하양팀의 왕이 공격받고 있습니다.)");
        }
        else if(team==Color.RED) {
            this.judge.setText("Check(빨강팀의 왕이 공격받고 있습니다.)");
        }
        else if(team==Color.BLACK) {
            this.judge.setText("Check(검정팀의 왕이 공격받고 있습니다.)");
        }
        else if(team==Color.GREEN) {
            this.judge.setText("Check(초록팀의 왕이 공격받고 있습니다.)");
        }
        else {
            this.judge.setText("Check(??팀.)");
        }

        this.judge.setVisible(true);
    }

    public void showCheckmate(Color team) {
        ImageIcon icon = new ImageIcon("src/View/panels/displayImages/Checkmate.jpg");
        Image temp = icon.getImage();
        temp = temp.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        icon = new ImageIcon(temp);

        this.judge.setIcon(icon);

        if(team==Color.WHITE) {
            this.judge.setText("아쉽지만 탈락입니다.(하양팀 Checkmate 패배)");
        }
        else if(team==Color.RED) {
            this.judge.setText("아쉽지만 탈락입니다.(빨강팀 Checkmate 패배)");
        }
        else if(team==Color.BLACK) {
            this.judge.setText("아쉽지만 탈락입니다.(검정팀 Checkmate 패배)");
        }
        else if(team==Color.GREEN) {
            this.judge.setText("아쉽지만 탈락입니다.(초록팀 Checkmate 패배)");
        }
        else {
            this.judge.setText("탈락(??팀.)");
        }

        this.judge.setVisible(true);
    }

    public void showStalemate() {
        this.judge.setText("Stalemate 무승부입니다.");
        this.judge.setVisible(true);
    }


    @Override       //TODO : 제거
    public void actionPerformed(ActionEvent e) {
        showCheck(Color.GREEN);
    }
}
