package client;
import client.Oserov4.Ban;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import transData.*;
public class Osero_setting extends JFrame implements ActionListener{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	JButton b1 = new JButton("戻る");
	
	JButton b3 = new JButton("緑色");
	JButton b4 = new JButton("金色");
	JButton b5 = new JButton("赤色");

    	JLabel l6 = new JLabel("置ける場所表現 : ");
	JRadioButton r1 = new JRadioButton("0n", true);
    	JRadioButton r2 = new JRadioButton("Off", false);

	
	JLabel l2 = new JLabel("盤面の色");
	JFrame j = new JFrame();
	Container c;
	JLabel chessboard;
	Ban map;

	public Osero_setting(JLabel chessboard, Ban map){
		this.map = map;

	c = j.getContentPane();
	j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	j.setSize(500,400);
	c.setLayout(null);
	this.chessboard = chessboard;
	b1.setBounds(10,10,80,30);
	b1.addActionListener(this);
		
	
    	l6.setBounds(150, 150, 100, 30);
    	ButtonGroup group = new ButtonGroup();
	group.add(r1);
	group.add(r2);
	r1.setBounds(250, 150, 50, 30);
	r2.setBounds(300, 150, 50, 30);

   	r1.addActionListener(this);
    	r2.addActionListener(this);
    
    
	b1.setBounds(10,10,80,30);
	b1.addActionListener(this);


	
	
	l2.setBounds(210,240,100,30);

	b3.setBounds(30,320,80,30);
	b3.addActionListener(this);

	b4.setBounds(200,320,80,30);
	b4.addActionListener(this);
	
	b5.setBounds(380,320,80,30);
	b5.addActionListener(this);

	c.add(b1);
	

	c.add(l2);
	c.add(b3);
	c.add(b4);
	c.add(b5);
		
	c.add(l6);
    	c.add(r1);
    	c.add(r2);


	j.setVisible(true);
	}

	public void actionPerformed(ActionEvent ae) {
		
    		boolean status1 = r1.isSelected();
		boolean status2 = r2.isSelected();
	    	if (ae.getSource() == r1 || ae.getSource() == r2) {
			if(status1 == true) {
				Oserov4.G_switch = true;
				map.castToBoard();
    		}else if(status2 == true) {
				Oserov4.G_switch = false;
				map.castToBoard();
    		}
		}
		if(ae.getSource()==b1) {
			System.out.println("close setting window");
			//j.setVisible(false);
			j.dispose();

		}
		if(ae.getSource()==b3) {
			ImageIcon icon = new ImageIcon(getClass().getResource("frame_green.jpg"));
			this.chessboard.setIcon(icon);
		}
		if(ae.getSource()==b4){
			ImageIcon icon = new ImageIcon(getClass().getResource("frame.jpg"));
			this.chessboard.setIcon(icon);
		}
		if(ae.getSource()==b5){
			ImageIcon icon = new ImageIcon(getClass().getResource("frame_red.jpg"));
			this.chessboard.setIcon(icon);
		}
	}

}