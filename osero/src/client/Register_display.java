package client;

import java.awt.*;
import java.awt.List;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.net.*;
import java.io.*;

public class Register_display extends JFrame implements ActionListener {
	static final long serialVersionUID = 1;
	JLabel label[] = new JLabel[4];
	JTextArea txt[] = new JTextArea[3];
	JButton btn = new JButton("送信する");
	Client client2;

	public Register_display(String title, Client client2) {
		super(title);
		this.client2 = client2;
		JPanel p = (JPanel) getContentPane();
		p.setLayout(null);
		label[0] = new JLabel("プレイヤ名と誕生日の回答を入力してください");
		label[1] = new JLabel("プレイヤ名");
		label[3] = new JLabel("パスワード");
		label[2] = new JLabel("誕生日の回答");
		txt[0] = new JTextArea();
		txt[1] = new JTextArea();
		txt[2] = new JTextArea();
		label[0].setBounds(15, 10, 200, 60);
		label[1].setBounds(15, 100, 75, 20);
		label[2].setBounds(15, 130, 75, 20);
		label[3].setBounds(15, 160, 75, 20);
		txt[0].setBounds(95, 100, 120, 20);
		txt[1].setBounds(95, 130, 120, 20);
		txt[2].setBounds(95, 160, 120, 20);
		btn.setBounds(40, 180, 150, 20);
		btn.addActionListener(this);
		p.add(label[0]);
		p.add(label[1]);
		p.add(label[2]);
		p.add(label[3]);
		p.add(txt[0]);
		p.add(txt[1]);
		p.add(txt[2]);
		p.add(btn);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(250, 300);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		boolean flag = false;
		if (e.getSource() == btn) {
			// updata 7.20 start from here
			try {
				// need register port
				// at Socket s = new Socket(client2.ServerAddress,client2.getLoginPort());
				Socket s = new Socket(client2.ServerAddress, client2.getLoginPort());
				OutputStream os = s.getOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(os);
				InputStream is = s.getInputStream();
				ObjectInputStream ois = new ObjectInputStream(is);
				client2.ois = ois;
				client2.oos = oos;
				flag = client2.send_register_name_pass(txt[0].getText(), txt[1].getText(), txt[2].getText(), oos, ois);
				if (flag) {
					// login success
					client2.choose_room(client2.oos, client2.ois);
					this.dispose();
				} else {
					// login failed
					// TODO show "login failed"
					System.out.println("Register Failed");
				}
			} catch (Exception se) {
				System.out.println("Error(Login_display):Socket error");
				se.printStackTrace();
			}
		}
		// updata 7.20 untill here

	}
}
