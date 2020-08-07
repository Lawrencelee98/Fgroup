package client;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import transData.transData;
import java.util.*;
//import client.;

public class Room {

	private PrintWriter out;
	static Socket s_room = null;
	static String str;
	static Map<Integer, Integer> room_info;
	static java.util.List<String> players_info;
	static ObjectOutputStream oos = null;
	static ObjectInputStream ois = null;
	Client client = null;
	static ObjectOutputStream oos_room = null;
	static ObjectInputStream ois_room = null;
	static ObjectInputStream ois_room1 = null;
	private static int room_port = 0;
	private static int room_num = -1;
	JFrame j = new JFrame();
	Container c;

	public Room(Client client, Map<Integer, Integer> room_info, java.util.List<String> players_info, ObjectOutputStream oos,
			ObjectInputStream ois) {
		this.client = client;
		this.room_info = room_info;
		this.players_info = players_info;
		this.oos = oos;
		this.ois = ois;
		client.oos = oos;
		client.ois = ois;
		new Display4();

	}

	// use function -- client.send_room_num(int room_number); to send information of
	// room_number

	public int getRoomPort() {
		return room_port;
	}

	// ルームを選ぶ画面
	public class Display4 extends JFrame implements ActionListener {
		JLabel label1 = new JLabel("横浜");
		JLabel label2 = new JLabel("韓国");
		JLabel label3 = new JLabel("中国");
		JLabel label4 = new JLabel("日本");
		// M:空き情報などのラベルの格納
		String[] room_label = new String[8];
		// M:room内のプレイヤー管理(いるなら1, いないなら0)
		int[] room_player = new int[8];

		JButton b11 = null;
		JButton b12 = null;
		JButton b21 = null;
		JButton b22 = null;
		JButton b31 = null;
		JButton b32 = null;
		JButton b41 = null;
		JButton b42 = null;
		JButton CPU_match = null;

		public Display4() {
			System.out.println("Display4 method");
			c = j.getContentPane();
			j.setBounds(0, 0, 800, 600);

			for (int i = 1, j = 0; i < 5; i++, j += 2) {
				if (room_info.get(i) == 0) {
					room_label[j] = "空き";
					room_label[j + 1] = "空き";
					room_player[j] = 0;
					room_player[j + 1] = 0;
				} else if (room_info.get(i) == 1) {
					// M:プレイヤー情報の格納
					room_label[j] = "Player1";
					room_label[j + 1] = "空き";
					room_player[j] = 1;
					room_player[j + 1] = 0;
				} else if (room_info.get(i) == 2) {
					// M:プレイヤー情報の格納
					room_label[j] = "Player1";
					room_label[j + 1] = "Player2";
					room_player[j] = 1;
					room_player[j + 1] = 1;
				} else {
					System.out.println("Room.Display4:room_info error");
				}
			}
			b11 = new JButton(room_label[0]);
			b12 = new JButton(room_label[1]);
			b21 = new JButton(room_label[2]);
			b22 = new JButton(room_label[3]);
			b31 = new JButton(room_label[4]);
			b32 = new JButton(room_label[5]);
			b41 = new JButton(room_label[6]);
			b42 = new JButton(room_label[7]);
			j.setLayout(null);
			JPanel main = new JPanel();
			main.setLayout(new BorderLayout());
			main.setBounds(0, 0, 1000, 600);
			JLayeredPane lp = new JLayeredPane();
			lp.setLayout(null);
			lp.setBounds(0, 0, 1000, 600);
			ImageIcon img = new ImageIcon(getClass().getResource("fuji.jpg"));
			JLabel bg = new JLabel(img);
			bg.setBounds(0, 0, 1000, 600);
			JPanel w = new JPanel();
			JButton b = new JButton("ログアウト");
			CPU_match = new JButton("CPU対戦");
			CPU_match.addActionListener(this);
			b.addActionListener(this);
			w.add(b);
			w.add(CPU_match);
			// main.add(w, "West");
			JPanel m = new JPanel();
			m.setLayout(new BorderLayout());
			JPanel x = new JPanel();
			x.setOpaque(false);
			x.setPreferredSize(new Dimension(350, 15));
			m.add(x, "North");
			JPanel p = new JPanel();
			p.setOpaque(false);
			p.setLayout(new GridLayout(2, 2));
			JPanel p1 = new JPanel();
			p1.setOpaque(false);
			JPanel p11 = new JPanel();
			p11.setOpaque(false);
			p11.setBackground(Color.BLUE);
			JPanel p111 = new JPanel();
			p111.setOpaque(false);
			p111.setPreferredSize(new Dimension(300, 250));
			p111.setLayout(new BorderLayout());
			JPanel p1111 = new JPanel();
			p1111.setOpaque(false);
			p1111.setPreferredSize(new Dimension(300, 70));
			p1111.add(label1);
			p111.add(p1111, "North");
			JPanel p1112 = new JPanel();
			p1112.setOpaque(false);
			p1112.setPreferredSize(new Dimension(300, 90));
			b11.addActionListener(this);
			if (room_player[0] == 0) {
				b11.setFont(b11.getFont().deriveFont(40.0f));
				p1112.add(b11);
			} else {
				JLabel playera = new JLabel(get_record_label(players_info.get(0)));
				p1112.add(playera);
				playera.setFont(playera.getFont().deriveFont(20.0f));
			}
			p111.add(p1112, "Center");
			JPanel p1113 = new JPanel();
			p1113.setOpaque(false);
			p1113.setPreferredSize(new Dimension(300, 90));
			b12.addActionListener(this);
			if (room_player[1] == 0) {
				b12.setFont(b12.getFont().deriveFont(40.0f));
				p1113.add(b12);
			} else {
				JLabel playerb = new JLabel(get_record_label(players_info.get(1)));
				p1113.add(playerb);
				playerb.setFont(playerb.getFont().deriveFont(20.0f));
			}
			p111.add(p1113, "South");
			p11.add(p111);
			label1.setFont(label1.getFont().deriveFont(44.0f));
			p1.add(p11);
			p.add(p1);
			JPanel p2 = new JPanel();
			p2.setOpaque(false);
			JPanel p22 = new JPanel();
			p22.setOpaque(false);
			p22.setBackground(Color.BLUE);
			JPanel p222 = new JPanel();
			p222.setOpaque(false);
			p222.setPreferredSize(new Dimension(300, 250));
			p222.setLayout(new BorderLayout());
			JPanel p2221 = new JPanel();
			p2221.setOpaque(false);
			p2221.setPreferredSize(new Dimension(300, 70));
			p2221.add(label2);
			p222.add(p2221, "North");
			JPanel p2222 = new JPanel();
			p2222.setOpaque(false);
			p2222.setPreferredSize(new Dimension(300, 90));
			b21.addActionListener(this);
			if (room_player[2] == 0) {
				b21.setFont(b21.getFont().deriveFont(40.0f));
				p2222.add(b21);
			} else {
				JLabel playerc = new JLabel(get_record_label(players_info.get(2)));
				p2222.add(playerc);
				playerc.setFont(playerc.getFont().deriveFont(20.0f));
			}
			p222.add(p2222, "Center");
			JPanel p2223 = new JPanel();
			p2223.setOpaque(false);
			p2223.setPreferredSize(new Dimension(300, 90));
			b22.addActionListener(this);
			if (room_player[3] == 0) {
				b22.setFont(b22.getFont().deriveFont(40.0f));
				p2223.add(b22);
			} else {
				JLabel playerd = new JLabel(get_record_label(players_info.get(3)));
				p2223.add(playerd);
				playerd.setFont(playerd.getFont().deriveFont(20.0f));
			}
			p222.add(p2223, "South");
			p22.add(p222);
			label2.setFont(label2.getFont().deriveFont(44.0f));
			p2.add(p22);
			p.add(p2);
			JPanel p3 = new JPanel();
			p3.setOpaque(false);
			JPanel p33 = new JPanel();
			p33.setOpaque(false);
			p33.setBackground(Color.BLUE);
			JPanel p333 = new JPanel();
			p333.setOpaque(false);
			p333.setPreferredSize(new Dimension(300, 250));
			p333.setLayout(new BorderLayout());
			JPanel p3331 = new JPanel();
			p3331.setOpaque(false);
			p3331.setPreferredSize(new Dimension(300, 70));
			p3331.add(label3);
			p333.add(p3331, "North");
			JPanel p3332 = new JPanel();
			p3332.setOpaque(false);
			p3332.setPreferredSize(new Dimension(300, 90));
			b31.addActionListener(this);
			if (room_player[4] == 0) {
				b31.setFont(b31.getFont().deriveFont(40.0f));
				p3332.add(b31);
			} else {
				JLabel playere = new JLabel(get_record_label(players_info.get(4)));
				p3332.add(playere);
				playere.setFont(playere.getFont().deriveFont(20.0f));
			}
			p333.add(p3332, "Center");
			JPanel p3333 = new JPanel();
			p3333.setOpaque(false);
			p3333.setPreferredSize(new Dimension(300, 90));
			b32.addActionListener(this);
			if (room_player[5] == 0) {
				b32.setFont(b32.getFont().deriveFont(40.0f));
				p3333.add(b32);
			} else {
				JLabel playerf = new JLabel(get_record_label(players_info.get(5)));
				p3333.add(playerf);
				playerf.setFont(playerf.getFont().deriveFont(20.0f));
			}
			p333.add(p3333, "South");
			p33.add(p333);
			label3.setFont(label3.getFont().deriveFont(44.0f));
			p3.add(p33);
			p.add(p3);
			JPanel p4 = new JPanel();
			p4.setOpaque(false);
			JPanel p44 = new JPanel();
			p44.setOpaque(false);
			p44.setBackground(Color.BLUE);
			JPanel p444 = new JPanel();
			p444.setOpaque(false);
			p444.setPreferredSize(new Dimension(300, 250));
			p444.setLayout(new BorderLayout());
			JPanel p4441 = new JPanel();
			p4441.setOpaque(false);
			p4441.setPreferredSize(new Dimension(300, 70));
			p4441.add(label4);
			p444.add(p4441, "North");
			JPanel p4442 = new JPanel();
			p4442.setOpaque(false);
			p4442.setPreferredSize(new Dimension(300, 90));
			b41.addActionListener(this);
			if (room_player[6] == 0) {
				b41.setFont(b41.getFont().deriveFont(40.0f));
				p4442.add(b41);
			} else {
				JLabel playerg = new JLabel(get_record_label(players_info.get(6)));
				p4442.add(playerg);
				playerg.setFont(playerg.getFont().deriveFont(20.0f));
			}
			p444.add(p4442, "Center");
			JPanel p4443 = new JPanel();
			p4443.setOpaque(false);
			p4443.setPreferredSize(new Dimension(300, 90));
			b42.addActionListener(this);
			if (room_player[7] == 0) {
				b42.setFont(b42.getFont().deriveFont(40.0f));
				p4443.add(b42);
			} else {
				JLabel playerh = new JLabel(get_record_label(players_info.get(7)));
				p4443.add(playerh);
				playerh.setFont(playerh.getFont().deriveFont(20.0f));
			}
			p444.add(p4443, "South");
			p44.add(p444);
			label4.setFont(label4.getFont().deriveFont(44.0f));
			p4.add(p44);
			p.add(p4);
			m.add(p, "Center");
			// main.add(m, "Center");
			// main.setOpaque(false);
			w.setOpaque(false);
			m.setOpaque(false);
			lp.add(bg);
			w.setBounds(0, 0, 200, 600);
			lp.add(w);
			m.setBounds(200, 0, 800, 600);
			lp.add(m);
			// lp.add(main);
			lp.setLayer(bg, 0);
			lp.setLayer(w, 1);
			lp.setLayer(m, 2);
			// lp.setLayer(main, 1);
			// add(lp);

			c.setLayout(null);
			c.add(lp);
			j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			j.setSize(1000, 600);
			j.setLocation(client.pos);
			j.setTitle("ルーム選択画面");
			j.setVisible(true);

		}

		public void actionPerformed(ActionEvent e) {
			client.pos = j.getLocation();

			if (e.getActionCommand().equals("ログアウト")) {
				j.setVisible(false);

			} else {
				if (e.getSource() == b11) {
					room_player[0] = 1;
					room_num = 1;
					new Display5(client);
					j.setVisible(false);
					if (room_player[1] == 1) {
						// new Display5(client);
					}

				} else if (e.getSource() == b12) {
					room_player[1] = 1;
					room_num = 1;
					new Display5(client);
					j.setVisible(false);
					if (room_player[0] == 1) {
						// new Display5(client);
					}
				} else if (e.getSource() == b21) {
					room_player[2] = 1;
					room_num = 2;
					new Display5(client);
					j.setVisible(false);
					if (room_player[3] == 1) {
						// new Display5(client);
					}
				} else if (e.getSource() == b22) {
					room_player[3] = 1;
					room_num = 2;
					new Display5(client);
					j.setVisible(false);
					if (room_player[2] == 1) {
						// new Display5(client);
					}
				} else if (e.getSource() == b31) {
					room_player[4] = 1;
					room_num = 3;
					new Display5(client);
					j.setVisible(false);
					if (room_player[5] == 1) {
						// new Display5(client);
					}
				} else if (e.getSource() == b32) {
					room_player[5] = 1;
					room_num = 3;
					new Display5(client);
					j.setVisible(false);
					if (room_player[4] == 1) {
						// new Display5(client);
					}
				} else if (e.getSource() == b41) {
					room_player[6] = 1;
					room_num = 4;
					new Display5(client);
					j.setVisible(false);
					if (room_player[7] == 1) {
						// new Display5(client);
					}
				} else if (e.getSource() == b42) {
					room_player[7] = 1;
					room_num = 4;
					new Display5(client);
					j.setVisible(false);
					if (room_player[6] == 1) {
						// new Display5(client);
					}
				} else if (e.getSource() == CPU_match) {
					new Oserov4_cpu(client, ois_room, oos_room, 30);
				}

				transData room_choice = new transData(13);
				room_choice.set_room_num(room_num);
				System.out.println("room choice=" + room_choice.get_room_num());
				try {
					oos.writeObject(room_choice);
					System.out.println("sent room choice");
					transData return_port = (transData) ois.readObject();
					System.out.println("receive return port");
					client.room_port = return_port.get_port();
					System.out.println("Room:Action, room_port=" + client.room_port);

				} catch (Exception e2) {
					e2.printStackTrace();
				} finally {

					this.dispose();
				}

			}
		}

	}

	private String get_record_label(String str) {// strには"name:usr_0, win:0, lose:0, draw:0, rate:0"を与える
		String res = new String();
		String[] arr = str.split(",", 0);
		res = "<html>";
		res += arr[0].substring(16).trim() + "<br/>";// name
		res += arr[1].substring(5).trim() + "勝 ";// win
		res += arr[2].substring(6).trim() + "負 ";// lose
		res += arr[3].substring(6).trim() + "分 ";// draw
		res += "レート" + arr[4].substring(6).trim();// rate
		res += "</html>";
		return res;
	}

	public static class Display5 extends JFrame implements ActionListener {

		JLabel label = new JLabel("<html>自分の持ち時間の希望を<br/>対戦相手に送信します。<br/>以下から選んでください。</html>");
		Client client = null;

		Display5(Client client) {

			System.out.println("Display5 aroused by Display4");

			this.client = client;
			setLayout(new FlowLayout());
			JPanel p = new JPanel();
			p.setLayout(new BorderLayout());
			JPanel x = new JPanel();
			x.setPreferredSize(new Dimension(350, 15));
			p.add(x, "North");
			JPanel pn = new JPanel();
			pn.setPreferredSize(new Dimension(350, 70));
			pn.setBackground(Color.GREEN);
			JPanel pnc = new JPanel();
			pnc.setPreferredSize(new Dimension(340, 60));
			pnc.add(label);
			pn.add(pnc);
			p.add(pn, "North");
			JPanel pc = new JPanel();
			pc.setLayout(new FlowLayout());

			JButton b2 = new JButton("10秒");
			b2.setPreferredSize(new Dimension(90, 30));
			b2.addActionListener(this);
			pc.add(b2);
			JButton b3 = new JButton("15秒");
			b3.setPreferredSize(new Dimension(90, 30));
			b3.addActionListener(this);
			pc.add(b3);
			p.add(pc, "Center");
			JPanel ps = new JPanel();
			ps.setLayout(new FlowLayout());
			JButton b4 = new JButton("30秒");
			b4.setPreferredSize(new Dimension(90, 30));
			b4.addActionListener(this);
			ps.add(b4);
			p.add(ps, "South");
			add(p, "Center");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setSize(400, 250);
			setTitle("持ち時間選択画面");
			setVisible(true);
		}

		public void actionPerformed(ActionEvent e) {
			int time = -1;
			if (e.getActionCommand().equals("10秒")) {
				str = "10秒";
				time = 10;
				this.setVisible(false);
				// this.dispose();

				// new Display6(this.client);

			} else if (e.getActionCommand().equals("15秒")) {
				str = "15秒";
				time = 15;
				this.setVisible(false);
				// this.dispose();

				// new Display6(this.client);

			} else if (e.getActionCommand().equals("30秒")) {
				str = "30秒";
				time = 30;
				this.setVisible(false);
				// this.dispose();

				// new Display6(this.client);

			}
			try {
				client.s.close();
				System.out.println("setting socket s_room");
				s_room = new Socket(client.ServerAddress, client.room_port);
				System.out.println("connect with room server");

				// InputStream
				InputStream is_room = s_room.getInputStream();
				System.out.println("get Input stream ");
				// ObjectInputStream ois_room1= new ObjectInputStream(is_room);
				// ois_room = ois_room1;
				ois_room = new ObjectInputStream(is_room);
				System.out.println("get input stream");

				// OutputStream
				OutputStream os_room = s_room.getOutputStream();
				System.out.println("get output stream ");
				oos_room = new ObjectOutputStream(os_room);
				System.out.println("take the oos to oos_room");

				client.s_room = s_room;

				// System.out.println("start oserov4");
				// new Oserov4(client, oos_room, ois_room);
				System.out.println("set socket s_room ");
			} catch (Exception e1) {
				e1.printStackTrace();

			} finally {

				System.out.println("finally socket s_room setting");
			}

			try {
				transData time_data = new transData(0);
				time_data.set_wait_time(time);
				oos_room.writeObject(time_data);

				System.out.println("start osero_cpu");

				new Oserov4_cpu(client, ois_room, oos_room, time);

			} catch (Exception erro1) {
				erro1.printStackTrace();
			} finally {
				System.out.println("___dispose display5 ");
				this.dispose();
			}
		}

	}

	public static void main(String[] args) {
		// System.out.println("Room:main");
		// new Room();
	}

}
/*
 * class start_Osero extends Thread { Client client; ObjectInputStream ois;
 * ObjectOutputStream oos; int time;
 * 
 * start_Osero(Client client, ObjectInputStream ois, ObjectOutputStream oos, int
 * time) { this.client = client; this.ois = ois; this.oos = oos; this.time =
 * time; }
 * 
 * public void run() { Oserov4 osero = new Oserov4(this.client, oos, ois, time);
 * } }
 */