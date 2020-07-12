package client;

import javax.swing.*;

public class Result extends JFrame {
    JLabel l = new JLabel("");

    public Result(int result) {
        switch (result) {
            case 0:
                l.setText("白の勝ち");
            case 1:
                l.setText("黒の勝ち");
            case 2:
                l.setText("引き分け");
        }
        l.setBounds(70, 50, 50, 50);
        add(l);
        this.setLayout(null);
        this.setSize(200, 200);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
    }

}