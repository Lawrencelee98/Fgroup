package client;
import javax.swing.*;
import java.awt.*;

public class Osero_setting extends JFrame {

	JButton b1 = new JButton("����");
	JButton b2 = new JButton("�Ф��椨");
	JButton b3 = new JButton("�vɫ");
	JButton b4 = new JButton("��ɫ");
	JButton b5 = new JButton("��ɫ");
	
	JLabel l1 = new JLabel("������Ȥ����ɫ��䤨��C��");
	JLabel l2 = new JLabel("�P���ɫ");
	
	Container c;
	
	public Osero_setting(){
	JFrame j = new JFrame();
	c = j.getContentPane();
	j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	j.setSize(500,400);
	c.setLayout(null);
	
	b1.setBounds(10,10,80,30);
	l1.setBounds(150,80,300,30);
	b2.setBounds(200,150,80,30);
	l2.setBounds(210,240,100,30);
	b3.setBounds(30,320,80,30);
	b4.setBounds(200,320,80,30);
	b5.setBounds(380,320,80,30);
	
	c.add(b1);
	c.add(l1);
	c.add(b2);
	c.add(l2);
	c.add(b3);
	c.add(b4);
	c.add(b5);	
	
	
	j.setVisible(true);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Osero_setting();
	}

}
