package client;
import java.awt.*;
import java.awt.List;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.net.*;
import transData.*;

public class Register_display extends JFrame implements ActionListener{
	static final long serialVersionUID = 1;
	JLabel label[] = new JLabel[3];
	JTextArea txt[] = new JTextArea[2];
	JButton btn = new JButton("送信する");
	client client2;
	public Register_display(String title,client client2){
		super(title);
		this.client2 = client2;
		JPanel p = (JPanel)getContentPane();
		p.setLayout(null);

		label[0] = new JLabel("<html>プレイヤ名と[秘密の質問]の回答を入力してください</html>");
		label[1] = new JLabel("プレイヤ名");
		label[2] = new JLabel("質問の回答");
		txt[0] = new JTextArea();
		txt[1] = new JTextArea();

		label[0].setBounds(15, 10, 200, 60);
		label[1].setBounds(15, 100, 75, 20);
		label[2].setBounds(15, 130, 75, 20);
		txt[0].setBounds(95, 100, 120, 20);
		txt[1].setBounds(95, 130, 120, 20);
		btn.setBounds(40, 180, 150, 20);
		btn.addActionListener(this);

		p.add(label[0]);
		p.add(label[1]);
		p.add(label[2]);
		p.add(txt[0]);
		p.add(txt[1]);
		p.add(btn);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(250, 250);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btn) {
			client2.send_register_name_pass(txt[0].getText(), txt[0].getText());
		}

	}

}