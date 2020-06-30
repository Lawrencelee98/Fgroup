import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Client {

	private PrintWriter out;
	static String str;
	public Client(){
		
	}

	public void access(String ipAddress, int port) {
		Socket socket = null;
		try {
			socket = new Socket(ipAddress, port);
			out = new PrintWriter(socket.getOutputStream(), true);
		}catch (Exception e) {
            e.printStackTrace ();
        }
	}

	public void send() {
		
	}

	public void receive() {
		
	}

	public void display() {
		
	}

	public void updateDisp() {
		
	}

	public void operation() {
		
	}
	
	public static class Display4 extends JFrame implements ActionListener  {

		JLabel label1 = new JLabel ("ルーム1");
		JLabel label2 = new JLabel ("ルーム2");
		JLabel label3 = new JLabel ("ルーム3");
		JLabel label4 = new JLabel ("ルーム4");
		JButton b11 = new JButton ( "空き");
		JButton b12 = new JButton ( "空き");
		JButton b21 = new JButton ( "空き");
		JButton b22 = new JButton ( "空き");
		JButton b31 = new JButton ( "空き");
		JButton b32 = new JButton ( "空き");
		JButton b41 = new JButton ( "空き");
		JButton b42 = new JButton ( "空き");
		int A=1,B=1,C=1,D=0,E=0,F=0,G=0,H=0;
		
		Display4() {
			setLayout (new BorderLayout ());
			JPanel w = new JPanel();
			JButton b = new JButton ( "ログアウト");
			b.addActionListener (this);
			w.add(b);
			add(w,"West");
			JPanel m = new JPanel();
			m.setLayout (new BorderLayout ());
			JPanel x = new JPanel();
			x.setPreferredSize(new Dimension(350,15));
			m.add(x,"North");
			JPanel p = new JPanel();
			p.setLayout (new GridLayout (2, 2));
			JPanel p1 = new JPanel();
			JPanel p11 = new JPanel();
			p11.setBackground(Color.BLUE);
			JPanel p111 = new JPanel();
			p111.setPreferredSize(new Dimension(300,250));
			p111.setLayout (new BorderLayout ());
			JPanel p1111 = new JPanel();
			p1111.setPreferredSize(new Dimension(300,70));
			p1111.add(label1);
			p111.add(p1111,"North");
			JPanel p1112 = new JPanel();
			p1112.setPreferredSize(new Dimension(300,90));
			b11.addActionListener (this);
			if(A==0) {
			b11.setFont(b11.getFont().deriveFont(40.0f)); 
			p1112.add(b11);
			}else {
				JLabel playera = new JLabel ("<html>プレイヤ名A<br/>（成績）</html>");
				p1112.add(playera);
				playera.setFont(playera.getFont().deriveFont(20.0f)); 
			}
			p111.add(p1112,"Center");
			JPanel p1113 = new JPanel();
			p1113.setPreferredSize(new Dimension(300,90));
			b12.addActionListener (this);
			if(B==0) {
			b12.setFont(b12.getFont().deriveFont(40.0f)); 
			p1113.add(b12);
			}else {
				JLabel playerb = new JLabel ("<html>プレイヤ名B<br/>（成績）</html>");
				p1113.add(playerb);
				playerb.setFont(playerb.getFont().deriveFont(20.0f)); 
			}
			p111.add(p1113,"South");
			p11.add(p111);
			label1.setFont(label1.getFont().deriveFont(44.0f)); 
			p1.add(p11);
			p.add(p1);
			JPanel p2 = new JPanel();
			JPanel p22 = new JPanel();
			p22.setBackground(Color.BLUE);
			JPanel p222 = new JPanel();
			p222.setPreferredSize(new Dimension(300,250));
			p222.setLayout (new BorderLayout ());
			JPanel p2221 = new JPanel();
			p2221.setPreferredSize(new Dimension(300,70));
			p2221.add(label2);
			p222.add(p2221,"North");
			JPanel p2222 = new JPanel();
			p2222.setPreferredSize(new Dimension(300,90));
			b21.addActionListener (this);
			if(C==0) {
			b21.setFont(b21.getFont().deriveFont(40.0f)); 
			p2222.add(b21);
			}else {
				JLabel playerc = new JLabel ("<html>プレイヤ名C<br/>（成績）</html>");
				p2222.add(playerc);
				playerc.setFont(playerc.getFont().deriveFont(20.0f)); 
			}
			p222.add(p2222,"Center");
			JPanel p2223 = new JPanel();
			p2223.setPreferredSize(new Dimension(300,90));
			b22.addActionListener (this);
			if(D==0) {
			b22.setFont(b22.getFont().deriveFont(40.0f)); 
			p2223.add(b22);
			}else {
				JLabel playerd = new JLabel ("<html>プレイヤ名D<br/>（成績）</html>");
				p2223.add(playerd);
				playerd.setFont(playerd.getFont().deriveFont(20.0f)); 
			}
			p222.add(p2223,"South");
			p22.add(p222);
			label2.setFont(label2.getFont().deriveFont(44.0f)); 
			p2.add(p22);
			p.add(p2);
			JPanel p3 = new JPanel();
			JPanel p33 = new JPanel();
			p33.setBackground(Color.BLUE);
			JPanel p333 = new JPanel();
			p333.setPreferredSize(new Dimension(300,250));
			p333.setLayout (new BorderLayout ());
			JPanel p3331 = new JPanel();
			p3331.setPreferredSize(new Dimension(300,70));
			p3331.add(label3);
			p333.add(p3331,"North");
			JPanel p3332 = new JPanel();
			p3332.setPreferredSize(new Dimension(300,90));
			b31.addActionListener (this);
			if(E==0) {
			b31.setFont(b31.getFont().deriveFont(40.0f)); 
			p3332.add(b31);
			}else {
				JLabel playere = new JLabel ("<html>プレイヤ名E<br/>（成績）</html>");
				p3332.add(playere);
				playere.setFont(playere.getFont().deriveFont(20.0f)); 
			}
			p333.add(p3332,"Center");
			JPanel p3333 = new JPanel();
			p3333.setPreferredSize(new Dimension(300,90));
			b32.addActionListener (this);
			if(F==0) {
			b32.setFont(b32.getFont().deriveFont(40.0f)); 
			p3333.add(b32);
			}else {
				JLabel playerf = new JLabel ("<html>プレイヤ名F<br/>（成績）</html>");
				p3333.add(playerf);
				playerf.setFont(playerf.getFont().deriveFont(20.0f)); 
			}
			p333.add(p3333,"South");
			p33.add(p333);
			label3.setFont(label3.getFont().deriveFont(44.0f)); 
			p3.add(p33);
			p.add(p3);
			JPanel p4 = new JPanel();
			JPanel p44 = new JPanel();
			p44.setBackground(Color.BLUE);
			JPanel p444 = new JPanel();
			p444.setPreferredSize(new Dimension(300,250));
			p444.setLayout (new BorderLayout ());
			JPanel p4441 = new JPanel();
			p4441.setPreferredSize(new Dimension(300,70));
			p4441.add(label4);
			p444.add(p4441,"North");
			JPanel p4442 = new JPanel();
			p4442.setPreferredSize(new Dimension(300,90));
			b41.addActionListener (this);
			if(G==0) {
			b41.setFont(b41.getFont().deriveFont(40.0f)); 
			p4442.add(b41);
			}else {
				JLabel playerg = new JLabel ("<html>プレイヤ名G<br/>（成績）</html>");
				p4442.add(playerg);
				playerg.setFont(playerg.getFont().deriveFont(20.0f)); 
			}
			p444.add(p4442,"Center");
			JPanel p4443 = new JPanel();
			p4443.setPreferredSize(new Dimension(300,90));
			b42.addActionListener (this);
			if(H==0) {
			b42.setFont(b42.getFont().deriveFont(40.0f)); 
			p4443.add(b42);
			}else {
				JLabel playerh = new JLabel ("<html>プレイヤ名H<br/>（成績）</html>");
				p4443.add(playerh);
				playerh.setFont(playerh.getFont().deriveFont(20.0f)); 
			}
			p444.add(p4443,"South");
			p44.add(p444);
			label4.setFont(label4.getFont().deriveFont(44.0f)); 
			p4.add(p44);
			p.add(p4);
			m.add(p,"Center");
			add(m,"Center");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setSize(800,600);
	        setTitle("ルーム選択画面");
	        setVisible(true);
		}public void actionPerformed (ActionEvent e) {
			if (e.getActionCommand ().equals("ログアウト")) {
				setVisible(false);
				
			}else if (e.getSource() == b11) {
				A=1;
				setVisible(false);
				if(B==1) {
					new Display5();
				}
			}else if (e.getSource() == b12) {
				B=1;
				setVisible(false);
				if(A==1) {
					new Display5();
				}
			}else if (e.getSource() == b21) {
				C=1;
				setVisible(false);
				if(D==1) {
					new Display5();
				}
			}else if (e.getSource() == b22) {
				D=1;
				setVisible(false);
				if(C==1) {
					new Display5();
				}
			}else if (e.getSource() == b31) {
				E=1;
				setVisible(false);
				if(F==1) {
					new Display5();
				}
			}else if (e.getSource() == b32) {
				F=1;
				setVisible(false);
				if(E==1) {
					new Display5();
				}
			}else if (e.getSource() == b41) {
				G=1;
				setVisible(false);
				if(H==1) {
					new Display5();
				}
			}else if (e.getSource() == b42) {
				H=1;
				setVisible(false);
				if(G==1) {
					new Display5();
				}
			}
		}

	}
	public static class Display6 extends JFrame implements ActionListener {
		
		JLabel label = new JLabel ("<html>対戦相手の希望時間は、<br/>"+str+"です。<br/>合意しますか？</html>");
		
		Display6(){
			setLayout (new FlowLayout());
			JPanel p = new JPanel();
			p.setLayout (new BorderLayout ());
			JPanel x = new JPanel();
			x.setPreferredSize(new Dimension(350,15));
			p.add(x,"North");
			JPanel pn = new JPanel();
			pn.setPreferredSize(new Dimension(350,70));
			pn.setBackground(Color.GREEN);
			JPanel pnc = new JPanel();
			pnc.setPreferredSize(new Dimension(340,60));
	        pnc.add(label);
	        pn.add(pnc);
			p.add(pn,"Center");
			
			JPanel pc = new JPanel();
			pc.setLayout (new FlowLayout());
	        JButton b1 = new JButton ( "合意する");
	        b1.setPreferredSize(new Dimension(120,30));
	        b1.addActionListener (this);
	        pc.add (b1);
	        JButton b2 = new JButton ( "合意しない");
	        b2.setPreferredSize(new Dimension(120,30));
	        b2.addActionListener (this);
	        pc.add (b2);
	        p.add (pc, "South");
	        add (p, "Center");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setSize(400,200);
	        setTitle("持ち時間合意画面");
	        setVisible(true);
		}
		public void actionPerformed (ActionEvent e) {
			if(e.getActionCommand ().equals("合意する")) {
				setVisible(false);
			}
			else if(e.getActionCommand ().equals("合意しない")) {
				setVisible(false);
				new Display5();
			}
		}

	}

	public static class Display5 extends JFrame implements ActionListener {

	
	JLabel label = new JLabel ("<html>自分の持ち時間の希望を<br/>対戦相手に送信します。<br/>以下から選んでください。</html>");
	
	Display5(){
		setLayout (new FlowLayout());
		JPanel p = new JPanel();
		p.setLayout (new BorderLayout ());
		JPanel x = new JPanel();
		x.setPreferredSize(new Dimension(350,15));
		p.add(x,"North");
		JPanel pn = new JPanel();
		pn.setPreferredSize(new Dimension(350,70));
		pn.setBackground(Color.GREEN);
		JPanel pnc = new JPanel();
		pnc.setPreferredSize(new Dimension(340,60));
        pnc.add(label);
        pn.add(pnc);
		p.add(pn,"North");
		JPanel pc = new JPanel();
		pc.setLayout (new FlowLayout());
        JButton b1 = new JButton ( "5秒");
        b1.setPreferredSize(new Dimension(90,30));
        b1.addActionListener (this);
        pc.add (b1);
        JButton b2 = new JButton ( "10秒");
        b2.setPreferredSize(new Dimension(90,30));
        b2.addActionListener (this);
        pc.add (b2);
        JButton b3 = new JButton ( "15秒");
        b3.setPreferredSize(new Dimension(90,30));
        b3.addActionListener (this);
        pc.add (b3);
        p.add (pc, "Center");
        JPanel ps = new JPanel();
		ps.setLayout (new FlowLayout());
        JButton b4 = new JButton ( "30秒");
        b4.setPreferredSize(new Dimension(90,30));
        b4.addActionListener (this);
        ps.add (b4);
        JButton b5 = new JButton ( "無制限");
        b5.setPreferredSize(new Dimension(90,30));
        b5.addActionListener (this);
        ps.add (b5);
        JButton b6 = new JButton ( "ランダム");
        b6.setPreferredSize(new Dimension(90,30));
        b6.addActionListener (this);
        ps.add (b6);
        p.add (ps, "South");
        add (p, "Center");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,250);
        setTitle("持ち時間選択画面");
        setVisible(true);
	}
	public void actionPerformed (ActionEvent e) {
		if(e.getActionCommand ().equals("5秒")) {
			str="5秒";
			setVisible(false);
		}else if(e.getActionCommand ().equals("10秒")) {
			str="10秒";
			setVisible(false);
		}else if(e.getActionCommand ().equals("15秒")) {
			str="15秒";
			setVisible(false);
		}else if(e.getActionCommand ().equals("30秒")) {
			str="30秒";
			setVisible(false);
		}else if(e.getActionCommand ().equals("無制限")) {
			str="無制限";
			setVisible(false);
		}else if(e.getActionCommand ().equals("ランダム")) {
			str="ランダム";
			setVisible(false);
		}
	}
	

}


	public static void main(String[] args) {
		
		
	}

}
