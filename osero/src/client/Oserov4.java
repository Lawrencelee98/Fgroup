package client;
import javax.swing.*;
//import jdk.vm.ci.aarch64.AArch64.Flag;
import transData.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;
import java.io.*;
import java.util.Arrays;
import java.util.*;

public class Oserov4 extends JFrame {
	// implements ActionListener {
	/**
	 *
	 */

	private static final long serialVersionUID = 1L;

	Container c;
	JLabel l1 = new JLabel("対戦相手" + " vs " + "相手の成績");
	JLabel l2 = new JLabel("残り時間：");
	JLabel l3 = new JLabel("");
	JLabel l4 = new JLabel("黒");
	JLabel l6 = new JLabel("白");
	JLabel l5 = new JLabel("あなたの番");
	JLabel l7 = new JLabel("あなたは[黒/白]です");
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
	boolean interuput = false;
	static boolean G_switch = true;
	Osero_setting osero_setting;

	ImageIcon iconB = new ImageIcon(getClass().getResource("00Black.jpg"));
	ImageIcon iconW = new ImageIcon(getClass().getResource("00White.jpg"));
	ImageIcon iconG = new ImageIcon(getClass().getResource("green.jpg"));
	// static int turn = 1;//初始化先手 黑色为0 白色为1
	// static int your_turn = 0; //自分のターン1, 相手のターン0
	Client client = null;
	int count = 0;
	int result = 5;
	int rate;
	boolean pass = true;

	Ban map = new Ban();
	HashMap<Integer, transData> hash = new HashMap<Integer, transData>();
	java.util.List<String> players_info;
	boolean puttable = true;
	Reciever rec = null;
	int time_limit = 30;
	Timer_count_down time_count_down;
	JFrame j = new JFrame();
	String res = new String();
	transData end = new transData(36);
	transData battle_end = new transData(50);
	transData s_data = new transData(3);

	public Oserov4(Client client, ObjectOutputStream oos, ObjectInputStream ois, int time, java.util.List<String> players_info) {
		this.client = client;
		try{
			client.oos.close();
			client.ois.close();
		}catch(Exception eea){
			System.out.println("关闭client原来的socket出现error");
		} 
		client.oos = oos;
		 client.ois = ois;
		// this.room = room;
		this.time_limit = time;
		this.players_info = players_info;
		System.out.println("players_info : " + players_info.toString());
		
		int i;
		for(i=0; i<8; i++){
			String[] arr = players_info.get(i).split(",", 0);
			if(arr[0].substring(16).trim().equals(client.username)){
				res = get_record_label(players_info.get(i)) + " vs  ";
				rate=Integer.parseInt(arr[4].substring(6).trim());
				if(i%2 == 0){
					res += get_record_label(players_info.get(i+1));
				}else{
					res += get_record_label(players_info.get(i-1));
				}
				break;
			}
		}
		
		l1.setText(res);
		System.out.println("res:" + res);
		String bgfile = "";
		switch(i/2){
			case 0: bgfile = "yokohama.jpg"; break;
			case 1: bgfile = "korea.jpg"; break;
			case 2: bgfile = "china.jpg"; break;
			case 3: bgfile = "japan.jpg"; break;
		}
		new Display(bgfile);

		// System.out.println("Battlereceiver");
		// client.BattleReceiver(map);
	}

	private String get_record_label(String str) {// strには"name:usr_0, win:0, lose:0, draw:0, rate:0"を与える
		String res = new String();
		String[] arr = str.split(",", 0);
		res = "";
		res += arr[0].substring(16).trim() + " ［";// name
		res += arr[1].substring(5).trim() + "勝 ";// win
		res += arr[2].substring(6).trim() + "負 ";// lose
		res += arr[3].substring(6).trim() + "分 ";// draw
		res += "レート" + arr[4].substring(6).trim();// rate
		res += "］";
		return res;
	}

	public class Display extends JFrame implements ActionListener {

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;

		public Display(String bgfile) {
			c = j.getContentPane();
			
			// j.setSize(800,600);
			j.setBounds(0, 0, 800, 600);
			j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			time_count_down = new Timer_count_down(time_limit,client.your_turn,client,j);
			JLayeredPane lp = new JLayeredPane();
			lp.setLayout(null);
			lp.setBounds(0, 0, 800, 600);

			System.out.println("bgfile : " + bgfile);
			ImageIcon bgimg = new ImageIcon(getClass().getResource(bgfile));
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
			
			l1.setBounds(40, 25, 760, 35);
			chessboard.setBounds(20, 80, 420, 420);
			chess.setBounds(28, 57, 43, 43);
			l2.setBounds(475, 130, 200, 40);
			l3.setBounds(630, 130, 150, 40);
			l4.setBounds(470, 240, 120, 40);
			l6.setBounds(620, 240, 120, 40);
			l5.setBounds(480, 350, 200, 30);
			l7.setBounds(480, 300, 190, 30);
			b1.setBounds(640, 470, 100, 30);
			b1.addActionListener(this);
			
			l1.setFont(l1.getFont().deriveFont(22.0f));
			l2.setFont(l2.getFont().deriveFont(30.0f));
			l3.setFont(l3.getFont().deriveFont(40.0f));
			l4.setFont(l4.getFont().deriveFont(30.0f));
			l6.setFont(l6.getFont().deriveFont(30.0f));
			l5.setFont(l5.getFont().deriveFont(25.0f));
			l7.setFont(l7.getFont().deriveFont(25.0f));

			l1.setForeground(Color.BLACK);
			l2.setForeground(Color.BLACK);
			l3.setForeground(Color.BLACK);
			l4.setForeground(Color.BLACK);
			l6.setForeground(Color.WHITE);
			l5.setForeground(Color.BLACK);
			l4.setBackground(new Color(210, 210, 210, 255));
			l6.setBackground(new Color(20, 20, 20, 255));
			l4.setOpaque(true);
			l6.setOpaque(true);
			l7.setOpaque(true);

			int buttonSize = 46, i = 0;

			Timer chess_number_count = new Timer();
			chess_number_count.schedule(new TimerTask() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
				l4.setText(" 黒　" + String.valueOf(map.countNumber(0)));
				l6.setText(" 白　" + String.valueOf(map.countNumber(1)));
					if (client.your_turn == 1) {
						l5.setText("あなたの番です");
					} else {
						l5.setText("相手の番です");
					}
				}
			}, 100, 100);

			int x=12, y=42;
			for (i = 0; i < 8; i++) {
				A[i] = new JButton();
				A[i].setBounds(28+x, 57+y + i * 48, buttonSize, buttonSize);
				A[i].addActionListener(this);
				A[i].setOpaque(false);
				A[i].setContentAreaFilled(false);
			}
			for (i = 0; i < 8; i++) {
				B[i] = new JButton();
				B[i].setBounds(76+x, 57+y + i * 48, buttonSize, buttonSize);
				B[i].addActionListener(this);
				B[i].setOpaque(false);
				B[i].setContentAreaFilled(false);
			}
			for (i = 0; i < 8; i++) {
				C[i] = new JButton();
				C[i].setBounds(124+x, 57+y + i * 48, buttonSize, buttonSize);
				C[i].addActionListener(this);
				C[i].setOpaque(false);
				C[i].setContentAreaFilled(false);
			}
			for (i = 0; i < 8; i++) {
				D[i] = new JButton();
				D[i].setBounds(172+x, 57+y + i * 48, buttonSize, buttonSize);
				D[i].addActionListener(this);
				D[i].setOpaque(false);
				D[i].setContentAreaFilled(false);
			}
			for (i = 0; i < 8; i++) {
				E[i] = new JButton();
				E[i].setBounds(219+x, 57+y + i * 48, buttonSize, buttonSize);
				E[i].addActionListener(this);
				E[i].setOpaque(false);
				E[i].setContentAreaFilled(false);
			}
			for (i = 0; i < 8; i++) {
				F[i] = new JButton();
				F[i].setBounds(266+x, 57+y + i * 48, buttonSize, buttonSize);
				F[i].addActionListener(this);
				F[i].setOpaque(false);
				F[i].setContentAreaFilled(false);
			}
			for (i = 0; i < 8; i++) {
				G[i] = new JButton();
				G[i].setBounds(314+x, 57+y + i * 48, buttonSize, buttonSize);
				G[i].addActionListener(this);
				G[i].setOpaque(false);
				G[i].setContentAreaFilled(false);
			}
			for (i = 0; i < 8; i++) {
				H[i] = new JButton();
				H[i].setBounds(361+x, 57+y + i * 48, buttonSize, buttonSize);
				H[i].addActionListener(this);
				H[i].setOpaque(false);
				H[i].setContentAreaFilled(false);
		}

			lp.add(l1);
			lp.add(l2);
			lp.add(l3);
			lp.add(l4);
			lp.add(l6);
			lp.add(l5);
			lp.add(l7);
			lp.add(b1);
			lp.add(chessboard);
			lp.setLayer(l1, 2);
			lp.setLayer(l2, 2);
			lp.setLayer(l3, 2);
			lp.setLayer(l4, 5);
			lp.setLayer(l6, 5);
			lp.setLayer(l5, 2);
			lp.setLayer(l7, 2);
			lp.setLayer(b1, 2);
			lp.setLayer(chessboard, 2);
	

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
			map.castToBoard();
			time_count_down.start();
			// data exchange
			transData r_data = null;
			// 次以降の時のため

			System.out.println("please wait for opponent");
			try {
				r_data = (transData)client.ois.readObject();
				int protocol;
				protocol =r_data.get_protocol();
				if(r_data instanceof transData) {
					//1000が送られてきた方が先攻
					if(protocol==1000) {
						System.out.println("You hava black!");
						client.turn = 0; //先攻なので黒
						client.your_turn = 1;
						l7.setForeground(Color.BLACK);
						l7.setBackground(new Color(210, 210, 210, 255));
						l7.setText("あなたは黒です");
					}else if(protocol==1200){
						System.out.println("You hava white!");
						client.turn = 1;
						client.your_turn = 0;
						l7.setBackground(new Color(20, 20, 20, 255));
						l7.setForeground(Color.WHITE);
						l7.setText("あなたは白です");

						//client.BattleReceiver(map);
						rec = new Reciever(client, map, client.ois,end,j,pass, rate,3);
						count++;
						rec.start();
					}else{}			
					//client.ois.reset();
					JOptionPane.showConfirmDialog(j, "対戦相手が現れたので対局を開始します", "", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (ClassNotFoundException e1) {
				// TODO 自動生成された catch ブロック
				e1.printStackTrace();
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}finally  {
			
			}
			/*
			System.out.println("Waiting for timeout info");
			int timeout_protocol = 2000;
			try {
				r_data = (transData) ois.readObject();
				if (r_data.get_protocol() == timeout_protocol) {
					new Result(1 - my_turn, client, this);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
				*/
		}

		public void actionPerformed(ActionEvent e) {
			client.pos = j.getLocation();
			boolean pressable = false;
			try {
				client.oos.reset();
			} catch (IOException e1) {
				// TODO 自動生成された catch ブロック
				e1.printStackTrace();
			}
			if (e.getSource() == b1) {
				System.out.println("open new window");
				new Osero_setting(chessboard, map);

			}
			try {
				if (client.your_turn == 1) {

					for (int i = 0; i < 8; i++) {
						if (e.getSource() == A[i] && map.getMapValue(i, 0) == 2) {
							map.updateMap(i, 0, client.turn);
							map.castToBoard();
							s_data.set_line(0);
							s_data.set_row(i);
							pressable =true;
							map.checkMap(client.turn);
							time_count_down.reset();

						} else if (e.getSource() == B[i] && map.getMapValue(i, 1) == 2) {
							map.updateMap(i, 1, client.turn);
							map.castToBoard();
							s_data.set_line(1);
							s_data.set_row(i);
							pressable =true;
							map.checkMap(client.turn);
							time_count_down.reset();

						} else if (e.getSource() == C[i] && map.getMapValue(i, 2) == 2) {
							map.updateMap(i, 2, client.turn);
							map.castToBoard();
							s_data.set_line(2);
							s_data.set_row(i);
							pressable =true;
							map.checkMap(client.turn);
							time_count_down.reset();

						} else if (e.getSource() == D[i] && map.getMapValue(i, 3) == 2) {
							map.updateMap(i, 3, client.turn);
							map.castToBoard();
							s_data.set_line(3);
							s_data.set_row(i);
							pressable =true;
							map.checkMap(client.turn);
							time_count_down.reset();

						} else if (e.getSource() == E[i] && map.getMapValue(i, 4) == 2) {
							map.updateMap(i, 4, client.turn);
							map.castToBoard();
							s_data.set_line(4);
							s_data.set_row(i);
							pressable =true;
							map.checkMap(client.turn);
							time_count_down.reset();

						} else if (e.getSource() == F[i] && map.getMapValue(i, 5) == 2) {
							map.updateMap(i, 5, client.turn);
							map.castToBoard();
							s_data.set_line(5);
							s_data.set_row(i);
							pressable =true;
							map.checkMap(client.turn);
							time_count_down.reset();

						} else if (e.getSource() == G[i] && map.getMapValue(i, 6) == 2) {
							map.updateMap(i, 6, client.turn);
							map.castToBoard();
							s_data.set_line(6);
							s_data.set_row(i);
							pressable =true;
							map.checkMap(client.turn);
							time_count_down.reset();

						} else if (e.getSource() == H[i] && map.getMapValue(i, 7) == 2) {
							map.updateMap(i, 7, client.turn);
							map.castToBoard();
							s_data.set_line(7);
							s_data.set_row(i);
							pressable =true;
							map.checkMap(client.turn);
							time_count_down.reset();

						}
					}
					if(pressable){
						result = map.isGameFinish();
						if(result != 3){
							try {
								System.out.println("s_data=" + s_data.get_row() + "," + s_data.get_line());
								transData f_data = new transData(5000);
								f_data.set_row(s_data.get_row());
								f_data.set_line(s_data.get_line());
								client.oos.writeObject(f_data);
								//rec = new Reciever(client, map, client.ois,end,j,pass, rate,result);
								//rec.start();
							} catch (Exception erro) {
								erro.printStackTrace();
							}
						}else{
								System.out.println("s_data=" + s_data.get_row() + "," + s_data.get_line());
								client.oos.writeObject(s_data);
								System.out.println("send s_data !!");
								result = map.isGameFinish();
								System.out.println("result : " + result);
							}
						client.your_turn = 0;
					if (count == 0) {						
						rec = new Reciever(client, map, client.ois,end,j,pass, rate,result);
						rec.start();						
						count++;				
					} else {					
						rec = new Reciever(client, map, client.ois,end,j,pass, rate,result);
						rec.start();						
					}				
				}
			}
			} catch (ArithmeticException pe) {
				System.out.println("you can't place here");
			} catch (Exception erro) {
				erro.printStackTrace();
			} finally {

			}
		}//action perform
	}// Action listern 

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Oserov4();
	}

	public class Ban {
		int r;
		int l;
		int[][] map = new int[8][8];// 黒0,白1,置ける場所2,空白3

		public Ban() {
			this.r = 8;
			this.l = 8;
		}

		public void timeupdater() {
			time_count_down.reset();
		}

		public void initMap() {
			for (int i = 0; i < 8; i++) {
				Arrays.fill(map[i], 3);// 空白で埋める
			}

			// 中央4マスの配置
			map[3][3] = 1;
			map[3][4] = 0;
			map[4][3] = 0;
			map[4][4] = 1;
			// 先手が黒なので黒に対する”置ける場所”を探索する
			checkMap(0);
			printMap(0);
		}

		public int getMapValue(int r, int l) {
			return map[r][l];
		}

		public void printSign(int p) {
			if (p == 0) {
				System.out.print("● ");
			} else if (p == 1) {
				System.out.print("○ ");
			} else if (p == 2) {
				System.out.print("* ");
			} else {
				System.out.print("　");
			}
		}

		public void printMap(int p) {
			System.out.println("====================");
			System.out.println(" | 0 1 2 3 4 5 6 7");
			System.out.println("-+------------------");
			checkMap(p);
			for (int i = 0; i < 8; i++) {
				System.out.print(String.valueOf(i) + "| ");
				for (int j = 0; j < 8; j++) {
					printSign(map[i][j]);
				}
				System.out.println();
			}
			System.out.println("====================");
		}

		public void updateMap(int r, int l, int p) throws ArithmeticException {
			// r:行, l:列, p:置かれた色
			/*
			 * if(map[r][l] != 2) {//置ける場所以外に置こうとしたとき,空白に置こうとしたときも含む
			 * System.out.println("this place has been occupied"); throw new
			 * ArithmeticException(); } else if(p != 0 && p != 1){//手番情報が黒(0),白(1)以外のとき
			 * System.out.println("invalid value"); throw new ArithmeticException(); } else
			 */ {
				map[r][l] = p;

				// 置いた場所の周囲8マスを探索
				// 0が置かれたら1を、1が置かれたら0を探索する
				int target = 1 - p;// 探索対象

				// 探索方向を指定してilistに格納=>有効な探索方向には値が残り無効な探索方向には-1が残る
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
					if (i >= 0) {// i=-1の場合を除く
						// System.out.println(i);
						boolean turnFlag = false;// 石をひっくり返すかどうか
						int tr, tl;// tr=行,tl=列
						for (tr = r + dir[i][0], tl = l + dir[i][1]; (0 <= tr && tr < 8 && 0 <= tl
								&& tl < 8); tr += dir[i][0], tl += dir[i][1]) {// 行列が0～7の範囲を超えないように
							// System.out.println("tr:"+tr+" tl:"+tl);
							if (map[tr][tl] == target) {
								// turnFlagがtrue->trueの場合は探索を続行
								// turnFlagがfalse->trueの場合は続行(初回のみ発生)
								turnFlag = true;
							} else if (turnFlag) {
								// turnFlagがtrue->falseと変化し、かつmap[tr][tl]が自分の手番情報である場合は実際にひっくり返す必要が生じる
								// ひっくり返す
								if (map[tr][tl] == p) {
									// System.out.println("turn");
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
								// turnFlagがfalse->falseの場合はこの探索方向での探索を終了(初回のみ発生)
								break;
							}
						}
						// 探索終了時にturnFlagがtrueの場合はひっくり返せない
					}
				}
			}

			// checkMap(p);
			// printMap();
		}

		public void checkMap(int t) {// t:自分の手番情報=0or1
			// 「自分の」置ける場所を調べ、そのマスを2に変える

			// マスを絞るのが面倒なので全マスに対して探索
			int j, k;// j=行,k=列
			for (j = 0; j < 8; j++) {
				for (k = 0; k < 8; k++) {
					// map[j][k]の周囲8マスを探索
					if (map[j][k] == 0 || map[j][k] == 1) {
						// map[j][k]に既に石が置かれていたら判別する必要がないのでcontinue
						continue;
					} else {
						// 置ける場所(2)の情報が残っていると面倒なので一旦3でリセット
						map[j][k] = 3;
					}

					// 1が置かれたら0を、0が置かれたら1を探索する
					int target = 1 - t;// 探索対象

					// 探索方向を指定してilistに格納=>有効な探索方向には値が残り無効な探索方向には-1が残る
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
						if (i >= 0) {// i=-1の場合を除く
							boolean turnFlag = false;// 石をひっくり返せるかどうか
							int tr, tl;// tr=行,tl=列
							for (tr = j + dir[i][0], tl = k + dir[i][1]; (0 <= tr && tr < 8 && 0 <= tl
									&& tl < 8); tr += dir[i][0], tl += dir[i][1]) {// 行列が0～7の範囲を超えないように
								if (map[tr][tl] == target) {
									// turnFlagがtrue->trueの場合は探索を続行
									// turnFlagがfalse->trueの場合は続行(初回のみ発生)
									turnFlag = true;
								} else if (turnFlag) {
									// turnFlagがtrue->falseと変化し、かつmap[tr][tl]が自分の手番情報である場合は実際にひっくり返せる
									if (map[tr][tl] == t) {
										map[j][k] = 2;
										break;// 1方向でもひっくり返せれば良いのでループを抜ける
									} else {
										break;
									}
								} else {
									// turnFlagがfalse->falseの場合はこの探索方向での探索を終了(初回のみ発生)
									break;
								}
							}
							// 探索終了時にturnFlagがtrueの場合はひっくり返せない
						}
					}
				}
			} // j,kによる全探索終了
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

		public int isGameFinish() {// ゲームが終了した:0or1or2[0=黒勝,1=白勝,2=引き分け],終了しない:3
			// 両者置ける場所が無くなったときにゲーム終了
			int b, b2, w, w2;
			checkMap(0);
			b = countNumber(0);// 黒石の個数
			b2 = countNumber(2);// 黒の置ける場所の数
			// printMap();

			checkMap(1);
			w = countNumber(1);// 白石の個数
			w2 = countNumber(2);// 白の置ける場所の数
			// printMap();

			// checkMap(turn);//map[][]を元に戻しておく

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

		public void castToBoard() {

			for (int i = 0; i < 8; i++) {
				if (i == 0) {
					for (int j = 0; j < 8; j++) {
						if (map[j][i] == 1) {
							A[j].setIcon(iconW);
							A[j].setOpaque(true);
						} else if (map[j][i] == 0) {
							A[j].setIcon(iconB);
							A[j].setOpaque(true);
						} else if (map[j][i] == 2 && G_switch == true) {
							A[j].setIcon(iconG);
						} else {
							A[j].setIcon(null);
						}
					}
				} else if (i == 1) {
					for (int j = 0; j < 8; j++) {
						if (map[j][i] == 1) {
							B[j].setIcon(iconW);
							B[j].setOpaque(true);
						} else if (map[j][i] == 0) {
							B[j].setIcon(iconB);
							B[j].setOpaque(true);
						} else if (map[j][i] == 2 && G_switch == true) {
							B[j].setIcon(iconG);
						} else {
							B[j].setIcon(null);
						}
					}
				} else if (i == 2) {
					for (int j = 0; j < 8; j++) {
						if (map[j][i] == 1) {
							C[j].setIcon(iconW);
							C[j].setOpaque(true);
						} else if (map[j][i] == 0) {
							C[j].setIcon(iconB);
							C[j].setOpaque(true);
						} else if (map[j][i] == 2 && G_switch == true) {
							C[j].setIcon(iconG);
						} else {
							C[j].setIcon(null);
						}
					}
				} else if (i == 3) {
					for (int j = 0; j < 8; j++) {
						if (map[j][i] == 1) {
							D[j].setIcon(iconW);
							D[j].setOpaque(true);
						} else if (map[j][i] == 0) {
							D[j].setIcon(iconB);
							D[j].setOpaque(true);
						} else if (map[j][i] == 2 && G_switch == true) {
							D[j].setIcon(iconG);
						} else {
							D[j].setIcon(null);
						}
					}
				} else if (i == 4) {
					for (int j = 0; j < 8; j++) {
						if (map[j][i] == 1) {
							E[j].setIcon(iconW);
							E[j].setOpaque(true);
						} else if (map[j][i] == 0) {
							E[j].setIcon(iconB);
							E[j].setOpaque(true);
						} else if (map[j][i] == 2 && G_switch == true) {
							E[j].setIcon(iconG);
						} else {
							E[j].setIcon(null);
						}
					}
				} else if (i == 5) {
					for (int j = 0; j < 8; j++) {
						if (map[j][i] == 1) {
							F[j].setIcon(iconW);
							F[j].setOpaque(true);
						} else if (map[j][i] == 0) {
							F[j].setIcon(iconB);
							F[j].setOpaque(true);
						} else if (map[j][i] == 2 && G_switch == true) {
							F[j].setIcon(iconG);
						} else {
							F[j].setIcon(null);
						}
					}
				} else if (i == 6) {
					for (int j = 0; j < 8; j++) {
						if (map[j][i] == 1) {
							G[j].setIcon(iconW);
							G[j].setOpaque(true);
						} else if (map[j][i] == 0) {
							G[j].setIcon(iconB);
							G[j].setOpaque(true);
						} else if (map[j][i] == 2 && G_switch == true) {
							G[j].setIcon(iconG);
						} else {
							G[j].setIcon(null);
						}
					}
				} else if (i == 7) {
					for (int j = 0; j < 8; j++) {
						if (map[j][i] == 1) {
							H[j].setIcon(iconW);
							H[j].setOpaque(true);
						} else if (map[j][i] == 0) {
							H[j].setIcon(iconB);
							H[j].setOpaque(true);
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

	class Timer_count_down extends Thread {
		int time_limit;
		int time;
		int your_turn;
		Client client;
		int result;
		JFrame j;
		boolean timeout = false;

		Timer_count_down(int time_limit, int your_turn, Client client, JFrame j) {
			this.time_limit = time_limit;
			this.your_turn = your_turn;
			this.client = client;
			this.j = j;
		}
		public void run() {
			time = time_limit;
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					if (time >= 1) {
						time = time - 1;
					}
					l3.setText(String.valueOf(time));
					if (time <= 0&&client.your_turn==1) {
						System.out.println("client time out");
						transData timeout = new transData(2000);
							try {
								rec = new Reciever(client, map, client.ois,end,j,pass, rate,result);
								rec.start();
								client.oos.writeObject(timeout);
								System.out.println("send timeout protocol 2000");	
							} catch (Exception e) {
								e.printStackTrace();
							}
						cancel();
					}
				}
			}, 0, 1000);
		}
		public void reset() {
			time = time_limit;
		}
	}
} 