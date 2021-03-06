package client;

import javax.swing.*;

import transData.*;

import java.awt.*;
import java.awt.event.*;

import java.util.Timer;
import java.util.TimerTask;
import java.net.*;
import java.io.*;
import java.util.Arrays;
import java.util.*;

public class Oserov4_cpu extends JFrame implements ActionListener {
	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;

	Container c;
	JLabel l1 = new JLabel("CPU戦");
	JLabel l2 = new JLabel("残り時間：");
	JLabel l4 = new JLabel("黒");
	JLabel l6 = new JLabel("白");
	JLabel l5 = new JLabel("あなたの番です");
	JButton b1 = new JButton("設定");

	JButton[] A = new JButton[10];
	JButton[] B = new JButton[10];
	JButton[] C = new JButton[10];
	JButton[] D = new JButton[10];
	JButton[] E = new JButton[10];
	JButton[] F = new JButton[10];
	JButton[] G = new JButton[10];
	JButton[] H = new JButton[10];

	JLabel chessboard;
	JLabel chess;
	double x = 0;
	double y = 0;
	int time_limit = 30;
	boolean interuput = false;
	// int CPU_switch = 1;
	boolean cpu_switch = false;
	
	boolean G_switch = true;
	boolean endflag = false;
	
	Client client = null;
	Osero_setting_cpu osero_setting;
	ImageIcon iconB = new ImageIcon(getClass().getResource("00Black.jpg"));
	ImageIcon iconW = new ImageIcon(getClass().getResource("00White.jpg"));
	ImageIcon iconG = new ImageIcon(getClass().getResource("green.jpg"));
	static int turn = 0;// 初始化先手 黑色为0 白色为1
	Map map = new Map();
	HashMap<Integer, transData> hash = new HashMap<Integer, transData>();
	JFrame j = new JFrame("CPU");
	// Timer_count_down time_count_down = new Timer_count_down(time_limit, l2);
	Room room = null;
	ObjectInputStream ois = null;
	ObjectOutputStream oos = null;
	int f_time = 0;

	public Oserov4_cpu(Client client, ObjectInputStream ois_room, ObjectOutputStream oos_room, int time) {
		this.time_limit = time;
		this.client = client;
		this.ois = ois_room;
		this.oos = oos_room;
 
		c = j.getContentPane();

		j.setBounds(0, 0, 800, 600);
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JLayeredPane lp = new JLayeredPane();
		lp.setLayout(null);
		lp.setBounds(0, 0, 800, 600);
		ImageIcon bgimg = new ImageIcon(getClass().getResource("fuji.jpg"));
		JLabel bg = new JLabel(bgimg);
		bg.setBounds(0, 0, 800, 600);
		lp.add(bg);
		lp.setLayer(bg, 0);

		ImageIcon img = new ImageIcon(getClass().getResource("frame.jpg"));
		chessboard = new JLabel();
		chessboard.setIcon(img);

		img = new ImageIcon(getClass().getResource("00Black.jpg"));
		chess = new JLabel();
		chess.setIcon(img);

		l1.setBounds(360, 25, 300, 24);
		chessboard.setBounds(20, 80, 420, 420);
		chess.setBounds(28, 57, 43, 43);
		l2.setBounds(500, 50, 200, 40);

		l4.setBounds(470, 240, 120, 40);
		l6.setBounds(620, 240, 120, 40);
		l5.setBounds(470, 380, 200, 30);
		b1.setBounds(640, 470, 100, 30);
		b1.addActionListener(this);
		
		l1.setFont(l1.getFont().deriveFont(30.0f));
		l2.setFont(l2.getFont().deriveFont(30.0f));
		l4.setFont(l4.getFont().deriveFont(30.0f));
		l6.setFont(l6.getFont().deriveFont(30.0f));
		l5.setFont(l6.getFont().deriveFont(20.0f));
		
		l1.setForeground(Color.BLACK);
		l2.setForeground(Color.BLACK);
		l4.setForeground(Color.BLACK);
		l6.setForeground(Color.WHITE);
		l5.setForeground(Color.BLACK);
		l4.setBackground(new Color(210, 210, 210, 255));
		l6.setBackground(new Color(50, 50, 50, 255));
		l4.setOpaque(true);
		l6.setOpaque(true);
		
		int buttonSize = 46;
		// time_count_down.start();
		// Timer

		Timer chess_number_count = new Timer();
		chess_number_count.schedule(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				l4.setText(" 黒　" + String.valueOf(map.count_black_chess()));
				l6.setText(" 白　" + String.valueOf(map.count_white_chess()));
			}
		}, 100, 100);

		// Timer finish
		int x=12, y=42;
		for (int i = 0; i < 8; i++) {
			A[i] = new JButton();
			A[i].setBounds(28+x, 57+y + i * 48, buttonSize, buttonSize);
			A[i].addActionListener(this);
			A[i].setOpaque(false);
			A[i].setContentAreaFilled(false);
		}
		for (int i = 0; i < 8; i++) {
			B[i] = new JButton();
			B[i].setBounds(76+x, 57+y + i * 48, buttonSize, buttonSize);
			B[i].addActionListener(this);
			B[i].setOpaque(false);
			B[i].setContentAreaFilled(false);
		}

		for (int i = 0; i < 8; i++) {
			C[i] = new JButton();
			C[i].setBounds(124+x, 57+y + i * 48, buttonSize, buttonSize);
			C[i].addActionListener(this);
			C[i].setOpaque(false);
			C[i].setContentAreaFilled(false);
		}
		for (int i = 0; i < 8; i++) {
			D[i] = new JButton();
			D[i].setBounds(172+x, 57+y + i * 48, buttonSize, buttonSize);
			D[i].addActionListener(this);
			D[i].setOpaque(false);
			D[i].setContentAreaFilled(false);
		}
		for (int i = 0; i < 8; i++) {
			E[i] = new JButton();
			E[i].setBounds(219+x, 57+y + i * 48, buttonSize, buttonSize);
			E[i].addActionListener(this);
			E[i].setOpaque(false);
			E[i].setContentAreaFilled(false);
		}
		for (int i = 0; i < 8; i++) {
			F[i] = new JButton();
			F[i].setBounds(266+x, 57+y + i * 48, buttonSize, buttonSize);
			F[i].addActionListener(this);
			F[i].setOpaque(false);
			F[i].setContentAreaFilled(false);
		}
		for (int i = 0; i < 8; i++) {
			G[i] = new JButton();
			G[i].setBounds(314+x, 57+y + i * 48, buttonSize, buttonSize);
			G[i].addActionListener(this);
			G[i].setOpaque(false);
			G[i].setContentAreaFilled(false);
		}
		for (int i = 0; i < 8; i++) {
			H[i] = new JButton();
			H[i].setBounds(361+x, 57+y + i * 48, buttonSize, buttonSize);
			H[i].addActionListener(this);
			H[i].setOpaque(false);
			H[i].setContentAreaFilled(false);
		}

		lp.add(l1);
		//lp.add(l2);
		lp.add(l4);
		lp.add(l6);
		lp.add(l5);
		lp.add(b1);
		lp.add(chessboard);
		lp.setLayer(l1, 2);
		//lp.setLayer(l2, 2);
		lp.setLayer(l4, 5);
		lp.setLayer(l6, 5);
		lp.setLayer(l5, 2);
		lp.setLayer(b1, 2);
		lp.setLayer(chessboard, 2);

		int i;
		for (i = 0; i < 8; i++) {
			lp.add(A[i]);
			lp.add(B[i]);
			lp.add(C[i]);
			lp.add(D[i]);
			lp.add(E[i]);
			lp.add(F[i]);
			lp.add(G[i]);
			lp.add(H[i]);
			lp.setLayer(A[i], 10);
			lp.setLayer(B[i], 10);
			lp.setLayer(C[i], 10);
			lp.setLayer(D[i], 10);
			lp.setLayer(E[i], 10);
			lp.setLayer(F[i], 10);
			lp.setLayer(G[i], 10);
			lp.setLayer(H[i], 10);
		}

		c.setLayout(null);
		c.add(lp);

		j.setLocation(client.pos);
		j.setVisible(true);
		j.setResizable(false);
		map.initMap();
		map.castToBoard(this);
		Data_reciever data_reciever = new Data_reciever(client, ois_room, oos_room, j);
		data_reciever.start();
		Timer Switch = new Timer();
		Switch.schedule(new TimerTask() {
			@Override
			public void run() {
				if (data_reciever.cpu_switch()) {
					f_time = data_reciever.get_time();
					j.dispose();
					cancel();
				}
			}
		}, 0, 500);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		client.pos = j.getLocation();

		if (e.getSource() == b1) {
			System.out.println("open new window");
			osero_setting = new Osero_setting_cpu(this);

		}

		try {
			for (int i = 0; i < 8; i++) {
				if (e.getSource() == A[i] && map.getMapValue(i, 0) == 2) {
					map.updateMap(i, 0, turn);

					map.castToBoard(this);
					turn = 1 - turn;
					map.checkMap(turn);
					matchCPU(turn);
					turn = 1 - turn;
					map.checkMap(0);
					map.castToBoard(this);
					// time_count_down.reset();

				} else if (e.getSource() == B[i] && map.getMapValue(i, 1) == 2) {
					map.updateMap(i, 1, turn);

					map.castToBoard(this);
					turn = 1 - turn;
					map.checkMap(turn);
					matchCPU(turn);
					turn = 1 - turn;
					map.checkMap(0);
					map.castToBoard(this);
					// time_count_down.reset();

				} else if (e.getSource() == C[i] && map.getMapValue(i, 2) == 2) {
					map.updateMap(i, 2, turn);

					map.castToBoard(this);
					turn = 1 - turn;
					map.checkMap(turn);
					matchCPU(turn);
					turn = 1 - turn;
					map.checkMap(0);
					map.castToBoard(this);
					// time_count_down.reset();

				} else if (e.getSource() == D[i] && map.getMapValue(i, 3) == 2) {
					map.updateMap(i, 3, turn);

					map.castToBoard(this);
					turn = 1 - turn;
					map.checkMap(turn);
					matchCPU(turn);
					turn = 1 - turn;
					map.checkMap(0);
					map.castToBoard(this);
					// time_count_down.reset();

				} else if (e.getSource() == E[i] && map.getMapValue(i, 4) == 2) {
					map.updateMap(i, 4, turn);

					map.castToBoard(this);
					turn = 1 - turn;
					map.checkMap(turn);
					matchCPU(turn);
					turn = 1 - turn;
					map.checkMap(0);
					map.castToBoard(this);
					// time_count_down.reset();

				} else if (e.getSource() == F[i] && map.getMapValue(i, 5) == 2) {
					map.updateMap(i, 5, turn);

					map.castToBoard(this);
					turn = 1 - turn;
					map.checkMap(turn);
					matchCPU(turn);
					turn = 1 - turn;
					map.checkMap(0);
					map.castToBoard(this);
					// time_count_down.reset();

				} else if (e.getSource() == G[i] && map.getMapValue(i, 6) == 2) {
					map.updateMap(i, 6, turn);

					map.castToBoard(this);
					turn = 1 - turn;
					map.checkMap(turn);
					matchCPU(turn);
					turn = 1 - turn;
					map.checkMap(0);
					map.castToBoard(this);
					// time_count_down.reset();

				} else if (e.getSource() == H[i] && map.getMapValue(i, 7) == 2) {
					map.updateMap(i, 7, turn);

					map.castToBoard(this);
					turn = 1 - turn;
					map.checkMap(turn);
					matchCPU(turn);
					turn = 1 - turn;
					map.checkMap(0);
					map.castToBoard(this);
					// time_count_down.reset();

				}

				int result;
				if ((result = isGameFinish()) != 3 && !endflag ){
					// 勝敗を表すディスプレイ//
					System.out.println("Gameover");
					switch(result){
						case 0: JOptionPane.showConfirmDialog(j, "あなたの勝ちです。", null, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE); break;
						case 1: JOptionPane.showConfirmDialog(j, "CPUの勝ちです。", null, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE); break;
						case 2: JOptionPane.showConfirmDialog(j, "引き分けです。", null, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE); break;
					}
					endflag = true;
				}
			}

		} catch (ArithmeticException pe) {
			System.out.println("you can't place here");
		} catch (Exception erro) {
			erro.printStackTrace();
		}

	}

	public int isGameFinish() {// ゲームが終了した:0or1or2[0=黒勝,1=白勝,2=引き分け],終了しない:3
		// 両者置ける場所が無くなったときにゲーム終了
		int b, b2, w, w2;
		map.checkMap(0);
		b = map.countNumber(0);// 黒石の個数
		b2 = map.countNumber(2);// 黒の置ける場所の数
		map.printMap();

		map.checkMap(1);
		w = map.countNumber(1);// 白石の個数
		w2 = map.countNumber(2);// 白の置ける場所の数
		map.printMap();

		map.checkMap(turn);// map[][]を元に戻しておく

		if (b2 == 0 && w2 == 0) {
			// ゲーム終了確定
			if (b > w) {
				return 0;
			} else if (w > b) {
				return 1;
			} else {
				return 2;
			}
		} else {
			return 3;
		}
	}

	public void matchCPU(int t) {// t:CPUの手番[0=CPUが黒,1=CPUが白,2=CPUstop]
		// CPUとの対戦
		if (t == 0 || t == 1) {
			map.checkMap(t);
			int c = map.countNumber(2);// CPUが置ける場所の個数

			if (c > 0) {
				Random r = new Random();
				int p = r.nextInt(c);// 乱数で置く場所を一つ選ぶ
				int i, j, d = 0;
				for (i = 0; i < 8; i++) {
					for (j = 0; j < 8; j++) {
						if (map.map[i][j] == 2) {
							if (p == d) {
								System.out.println("CPU" + t + " put " + i + " " + j);
								map.updateMap(i, j, t);// i,jに置く
							}
							d++;
						}
					}
				}
			} else {
				// パスする
				System.out.println("CPU pass");
				JOptionPane.showConfirmDialog(j, "CPUがパスしました", null, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
			}
		} else {
		}

	}

	public class Map {
		int r;
		int l;
		int whiteChessNum;
		int blackChessNum;
		int[][] map = new int[8][8];

		public Map() {
			this.r = 8;
			this.l = 8;
		}

		public int getMapValue(int r, int l) {
			return map[r][l];
		}

		int count_white_chess() {
			this.whiteChessNum = 0;
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (map[i][j] == 1) {
						whiteChessNum = whiteChessNum + 1;
					}
				}
			}
			return whiteChessNum;
		}

		int count_black_chess() {
			this.blackChessNum = 0;
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (map[i][j] == 0) {
						blackChessNum = blackChessNum + 1;
					}
				}
			}
			return blackChessNum;
		}

		public void initMap() {
			for (int i = 0; i < 8; i++) {
				Arrays.fill(map[i], 3);
			}
			map[3][3] = 1;
			map[3][4] = 0;
			map[4][3] = 0;
			map[4][4] = 1;

			checkMap(0);
			printMap();
		}

		public void printMap() {
			System.out.println("====================");
			System.out.println(" | 0 1 2 3 4 5 6 7");
			System.out.println("-+------------------");
			for (int i = 0; i < 8; i++) {
				System.out.print(String.valueOf(i) + "| ");
				for (int j = 0; j < 8; j++) {
					System.out.print(map[i][j]);
					System.out.print(' ');
				}
				System.out.println();
			}
			System.out.println("====================");
		}

		public void updateMap(int r, int l, int p) throws ArithmeticException {

			if (map[r][l] != 2) {
				System.out.println("this place has been occupied");
				throw new ArithmeticException();
			} else if (p != 0 && p != 1) {
				System.out.println("invalid value");
			} else {
				map[r][l] = p;

				int target = 1 - p;

				// 0:[-1,-1] 1:[-1, 0] 2:[-1, 1]
				// 3:[ 0,-1] [ r, l] 4:[ 0, 1]
				// 5:[ 1,-1] 6:[ 1, 0] 7:[ 1, 1]
				Integer dir[][] = { { -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, -1 }, { 0, 1 }, { 1, -1 }, { 1, 0 },
						{ 1, 1 } };
				Integer ilist[] = { 0, 1, 2, 3, 4, 5, 6, 7 };
				if (r == 0) {
					ilist[0] = -1;
					ilist[1] = -1;
					ilist[2] = -1;
				} else if (r == 7) {
					ilist[5] = -1;
					ilist[6] = -1;
					ilist[7] = -1;
				}
				if (l == 0) {
					ilist[0] = -1;
					ilist[3] = -1;
					ilist[5] = -1;
				} else if (l == 7) {
					ilist[2] = -1;
					ilist[4] = -1;
					ilist[7] = -1;
				}
				// System.out.println(Arrays.asList(ilist));

				for (int i : ilist) {
					if (i >= 0) {
						// System.out.println(i);
						boolean turnFlag = false;
						int tr, tl;
						for (tr = r + dir[i][0], tl = l + dir[i][1]; (0 <= tr && tr < 8 && 0 <= tl
								&& tl < 8); tr += dir[i][0], tl += dir[i][1]) {// 琛屽垪銇�0锝�7銇瘎鍥层倰瓒呫亪銇亜銈堛亞銇�
							// System.out.println("tr:"+tr+" tl:"+tl);
							if (map[tr][tl] == target) {

								turnFlag = true;
							} else if (turnFlag) {

								if (map[tr][tl] == p) {

									int ttr, ttl;
									for (ttr = r, ttl = l; (tr != ttr
											|| tl != ttl); ttr += dir[i][0], ttl += dir[i][1]) {
										map[ttr][ttl] = p;
									}
									break;
								} else {
									break;
								}
							} else {

								break;
							}
						}
					}
				}
			}

			checkMap(p);
			printMap();
		}

		public void checkMap(int t) {
			int j, k;
			for (j = 0; j < 8; j++) {
				for (k = 0; k < 8; k++) {

					if (map[j][k] == 0 || map[j][k] == 1) {

						continue;
					} else {

						map[j][k] = 3;
					}
					int target = 1 - t;
					// 0:[-1,-1] 1:[-1, 0] 2:[-1, 1]
					// 3:[ 0,-1] [ r, l] 4:[ 0, 1]
					// 5:[ 1,-1] 6:[ 1, 0] 7:[ 1, 1]
					Integer dir[][] = { { -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, -1 }, { 0, 1 }, { 1, -1 }, { 1, 0 },
							{ 1, 1 } };
					Integer ilist[] = { 0, 1, 2, 3, 4, 5, 6, 7 };
					if (j == 0) {
						ilist[0] = -1;
						ilist[1] = -1;
						ilist[2] = -1;
					} else if (j == 7) {
						ilist[5] = -1;
						ilist[6] = -1;
						ilist[7] = -1;
					}
					if (k == 0) {
						ilist[0] = -1;
						ilist[3] = -1;
						ilist[5] = -1;
					} else if (k == 7) {
						ilist[2] = -1;
						ilist[4] = -1;
						ilist[7] = -1;
					}
					// System.out.println(Arrays.asList(ilist));

					for (int i : ilist) {
						if (i >= 0) {
							boolean turnFlag = false;
							int tr, tl;
							for (tr = j + dir[i][0], tl = k + dir[i][1]; (0 <= tr && tr < 8 && 0 <= tl
									&& tl < 8); tr += dir[i][0], tl += dir[i][1]) {
								if (map[tr][tl] == target) {

									turnFlag = true;
								} else if (turnFlag) {

									if (map[tr][tl] == t) {
										map[j][k] = 2;
										break;
									} else {
										break;
									}
								} else {

									break;
								}
							}

						}
					}
				}
			}
		}

		public int countNumber(int n) {// n:数えたい数字(0～3)
			int i, j, k = 0;
			for (i = 0; i < 8; i++) {
				for (j = 0; j < 8; j++) {
					if (map[i][j] == n) {
						k++;
					}
				}
			}

			return k;
		}

		public void castToBoard(Oserov4_cpu osero) {

			for (int i = 0; i < 8; i++) {
				if (i == 0) {
					for (int j = 0; j < 8; j++) {
						if (map[j][i] == 1) {
							osero.A[j].setIcon(iconW);
							osero.A[j].setOpaque(true);
						} else if (map[j][i] == 0) {
							osero.A[j].setIcon(iconB);
							osero.A[j].setOpaque(true);
						} else if (map[j][i] == 2 && G_switch == true) {
							A[j].setIcon(iconG);
						} else {
							A[j].setIcon(null);
						}
					}
				} else if (i == 1) {
					for (int j = 0; j < 8; j++) {
						if (map[j][i] == 1) {
							osero.B[j].setIcon(iconW);
							osero.B[j].setOpaque(true);
						} else if (map[j][i] == 0) {
							osero.B[j].setIcon(iconB);
							osero.B[j].setOpaque(true);
						} else if (map[j][i] == 2 && G_switch == true) {
							B[j].setIcon(iconG);
						} else {
							B[j].setIcon(null);
						}
					}
				} else if (i == 2) {
					for (int j = 0; j < 8; j++) {
						if (map[j][i] == 1) {
							osero.C[j].setIcon(iconW);
							osero.C[j].setOpaque(true);
						} else if (map[j][i] == 0) {
							osero.C[j].setIcon(iconB);
							osero.C[j].setOpaque(true);
						} else if (map[j][i] == 2 && G_switch == true) {
							C[j].setIcon(iconG);
						} else {
							C[j].setIcon(null);
						}
					}
				} else if (i == 3) {
					for (int j = 0; j < 8; j++) {
						if (map[j][i] == 1) {
							osero.D[j].setIcon(iconW);
							osero.D[j].setOpaque(true);
						} else if (map[j][i] == 0) {
							osero.D[j].setIcon(iconB);
							osero.D[j].setOpaque(true);
						} else if (map[j][i] == 2 && G_switch == true) {
							D[j].setIcon(iconG);
						} else {
							D[j].setIcon(null);
						}
					}
				} else if (i == 4) {
					for (int j = 0; j < 8; j++) {
						if (map[j][i] == 1) {
							osero.E[j].setIcon(iconW);
							osero.E[j].setOpaque(true);
						} else if (map[j][i] == 0) {
							osero.E[j].setIcon(iconB);
							osero.E[j].setOpaque(true);
						} else if (map[j][i] == 2 && G_switch == true) {
							E[j].setIcon(iconG);
						} else {
							E[j].setIcon(null);
						}
					}
				} else if (i == 5) {
					for (int j = 0; j < 8; j++) {
						if (map[j][i] == 1) {
							osero.F[j].setIcon(iconW);
							osero.F[j].setOpaque(true);
						} else if (map[j][i] == 0) {
							osero.F[j].setIcon(iconB);
							osero.F[j].setOpaque(true);
						} else if (map[j][i] == 2 && G_switch == true) {
							F[j].setIcon(iconG);
						} else {
							F[j].setIcon(null);
						}
					}
				} else if (i == 6) {
					for (int j = 0; j < 8; j++) {
						if (map[j][i] == 1) {
							osero.G[j].setIcon(iconW);
							osero.G[j].setOpaque(true);
						} else if (map[j][i] == 0) {
							osero.G[j].setIcon(iconB);
							osero.G[j].setOpaque(true);
						} else if (map[j][i] == 2 && G_switch == true) {
							G[j].setIcon(iconG);
						} else {
							G[j].setIcon(null);
						}
					}
				} else if (i == 7) {
					for (int j = 0; j < 8; j++) {
						if (map[j][i] == 1) {
							osero.H[j].setIcon(iconW);
							osero.H[j].setOpaque(true);
						} else if (map[j][i] == 0) {
							osero.H[j].setIcon(iconB);
							osero.H[j].setOpaque(true);
						} else if (map[j][i] == 2 && G_switch == true) {
							H[j].setIcon(iconG);
						} else {
							H[j].setIcon(null);
						}
					}
				}
			} // for

		}// castToBoard 将棋子投射到棋盘上
	}
	/*
	 * class Iner_Thread extends Thread { Oserov4 osero;
	 * 
	 * Iner_Thread(Oserov4 osero) { this.osero = osero; }
	 * 
	 * @Override public void run() { new Osero_setting(osero); } }
	 */

}

class Timer_count_down extends Thread {
	int time_limit;
	int time;
	JLabel l2;

	Timer_count_down(int time_limit, JLabel l2) {
		this.time_limit = time_limit;
		this.l2 = l2;
	}

	public void run() {
		time = time_limit;
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				time = time - 1;
				l2.setText("Left time: " + String.valueOf(time));
				if (time_limit == 0) {
					System.out.println("Time out");
					cancel();
				}
			}
		}, 0, 1000);
	}

	public void reset() {
		time = time_limit;
	}
}

class Data_reciever extends Thread {
	ObjectInputStream ois_room = null;
	ObjectOutputStream oos_room = null;
	Client client = null;
	boolean cpu_switch = false;
	JFrame j;
	int f_time = -1;
	Socket s;
	Data_reciever(Client client, ObjectInputStream ois_room, ObjectOutputStream oos_room, JFrame j) {
		this.ois_room = ois_room;
		this.oos_room = oos_room;
		this.client = client;
		this.j = j;
		// this.cpu_switch =cpu_switch;

	}

	public void run() {
		try {
			transData data = new transData(80);

			data = (transData) ois_room.readObject();
			System.out.println("recieved battle start from server");
			if (data.get_protocol() == 80) {
				f_time = data.get_time();
				java.util.List<String> players_info = data.get_players_info();
				System.out.println("recieve data protocol 80");
				cpu_switch = true;
				
				client.osero = new Oserov4(client,this.oos_room,this.ois_room,f_time,players_info);
			   
				this.interrupt();
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean cpu_switch() {
		return cpu_switch;
	}

	public int get_time() {
		return f_time;
	}
}
// Oserov4
