package client;
import javax.swing.*;
import java.awt.*;

public class Gui0 extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Container c;
	JLabel l1 = new JLabel("��������"+" vs "+"���֤γɿ�");
	JLabel l2 = new JLabel("�Ф�r�g");
	JLabel l3 = new JLabel("���֤γ֤��r�g");
	JLabel l4 = new JLabel("�\�������פ���");
	JLabel l5 = new JLabel("���ʤ��η�");
	JButton b1 = new JButton("�O��");
	JButton b2 = new JButton("�K��");
	JLabel map ;
	public Gui0() {
		JFrame j = new JFrame();
		c=j.getContentPane();
		
		j.setSize(500,400);
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ImageIcon img = new ImageIcon(getClass().getResource("map.jpg"));
		map= new JLabel();
		map.setIcon(img);
		map.setBounds(10,10,200,200);
		
		l1.setBounds(170,10,300,20);
		map.setBounds(10,40,250,250);
		l2.setBounds(300,50,200,30);
		l3.setBounds(300,150,200,30);
		l4.setBounds(300,250,200,30);
		l5.setBounds(50,300,100,30);
		b1.setBounds(200,300,100,30);
		b2.setBounds(380,300,100,30);
		
		c.add(l1);
		c.add(l2);
		c.add(l3);
		c.add(l4);
		c.add(l5);
		c.add(b1);
		c.add(b2);
		c.add(map);
		c.setLayout(null);
		
		j.setVisible(true);
				
	}
	 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Gui0();
		
	}

}
