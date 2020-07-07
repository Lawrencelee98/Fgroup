package client;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Osero_setting extends JFrame implements ActionListener{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	JButton b1 = new JButton("戻る");
	JButton b2 = new JButton("切り替え");
	JButton b3 = new JButton("緑色");
	JButton b4 = new JButton("金色");
	JButton b5 = new JButton("赤色");

	JLabel l1 = new JLabel("おけるところの色を変える機能");
	JLabel l2 = new JLabel("盤面の色");
	JFrame j = new JFrame();
	Container c;
	Oserov4 oserov4;
	public Osero_setting(Oserov4 Oserov4){

	c = j.getContentPane();
	j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	j.setSize(500,400);
	c.setLayout(null);
	this.oserov4= Oserov4;
	b1.setBounds(10,10,80,30);
	b1.addActionListener(this);

	l1.setBounds(150,80,300,30);
	b2.setBounds(200,150,80,30);
	l2.setBounds(210,240,100,30);

	b3.setBounds(30,320,80,30);
	b3.addActionListener(this);

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

	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==b1) {
			System.out.println("close setting window");
			//j.setVisible(false);
			j.dispose();

		}
		if(ae.getSource()==b3) {
			ImageIcon icon = new ImageIcon(getClass().getResource("frame_green.jpg"));
			oserov4.chessboard.setIcon(icon);
		}
	}

}
