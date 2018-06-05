package View.panels;

import javax.swing.*;
import java.awt.*;

public class StatusDisplay extends JPanel {
    JLabel turn = new JLabel("검정팀 차례");

    public StatusDisplay() {
        setPreferredSize(new Dimension(800, 100));
        setLayout(new BorderLayout());
        add("West", turn);


        turn.setPreferredSize(new Dimension(100,100));
        turn.setHorizontalAlignment(JLabel.CENTER);
        turn.setVisible(true);

        setVisible(true);
    }

    public void updateTurn(int to) {
        switch (to) {
            case 0:
                turn.setText("검정팀 차례");
                break;
            case 1:
                turn.setText("흰팀 차례");
                break;
        }
    }
}
