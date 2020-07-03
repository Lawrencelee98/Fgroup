package client;

import javax.swing.*;

import transData.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.Point;
import java.util.Timer;
import java.util.TimerTask;
import java.net.*;
import java.io.*;
import java.util.Arrays;
import java.util.*;



public class Oserov4 extends JFrame implements ActionListener{
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
	Container c;
	JLabel l1 = new JLabel("対戦相手"+" vs "+"相手の成績");
	JLabel l2 = new JLabel("残り時間");
	JLabel l3 = new JLabel("相手の持ち時間");
	JLabel l4 = new JLabel("黒の数：白の数");
	JLabel l5 = new JLabel("あなたの番");
	JButton b1 = new JButton("設定");
	JButton b2 = new JButton("終了");
	
	
	JButton[] A = new JButton[10];
	JButton[] B = new JButton[10];
	JButton[] C = new JButton[10];
	JButton[] D = new JButton[10];
	JButton[] E = new JButton[10];
	JButton[] F = new JButton[10];
	JButton[] G = new JButton[10];
	JButton[] H = new JButton[10];
	
	JLabel chessboard ;
	JLabel chess;
	double x = 0;
	double y = 0;
	boolean interuput=false;
	Osero_setting osero_setting;
	ImageIcon iconB = new ImageIcon(getClass().getResource("00Black.jpg"));
	ImageIcon iconW = new ImageIcon(getClass().getResource("00White.jpg"));
	static int turn = 0;//初始化先手 黑色为0 白色为1
	Map map = new Map();
	HashMap<Integer,transData>hash = new HashMap<Integer,transData>();
	JFrame j = new JFrame();
	public Oserov4() {
		
		c=j.getContentPane();
		
		//j.setSize(800,600);
		j.setBounds(0,0,800,600);
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLayeredPane lp = new JLayeredPane();
		
		ImageIcon img = new ImageIcon(getClass().getResource("frame.jpg"));
		chessboard= new JLabel();
		chessboard.setIcon(img);
		
		img = new ImageIcon(getClass().getResource("00Black.jpg"));
		chess = new JLabel();
		chess.setIcon(img);
		
		l1.setBounds(300,10,300,20);
		chessboard.setBounds(10,40,420,420);
		chess.setBounds(28,57,43,43);
		l2.setBounds(600,50,200,30);
		l3.setBounds(600,150,200,30);
		l4.setBounds(600,250,200,30);
		l5.setBounds(50,500,100,30);
		b1.setBounds(200,500,100,30);
		b1.addActionListener(this);
		b2.setBounds(380,500,100,30);
		int buttonSize =46;
		
		for(int i=0;i<8;i++) {
			A[i]=new JButton();
			
			A[i].setBounds(28,57+i*48,buttonSize,buttonSize);
			A[i].addActionListener(this);
			A[i].setOpaque(false);A[i].setContentAreaFilled(false);
		}
		for(int i=0;i<8;i++) {
			B[i]=new JButton();
			
			B[i].setBounds(77,57+i*48,buttonSize,buttonSize);
			B[i].addActionListener(this);
			B[i].setOpaque(false);B[i].setContentAreaFilled(false);
		}
		
		for(int i=0;i<8;i++) {
			C[i]=new JButton();
			
			C[i].setBounds(126,57+i*48,buttonSize,buttonSize);
			C[i].addActionListener(this);
			C[i].setOpaque(false);C[i].setContentAreaFilled(false);
		}
		for(int i=0;i<8;i++) {
			D[i]=new JButton();
			
			D[i].setBounds(172,57+i*48,buttonSize,buttonSize);
			D[i].addActionListener(this);
			D[i].setOpaque(false);D[i].setContentAreaFilled(false);
		}
		for(int i=0;i<8;i++) {
			E[i]=new JButton();
			
			E[i].setBounds(220,57+i*48,buttonSize,buttonSize);
			E[i].addActionListener(this);
			E[i].setOpaque(false);E[i].setContentAreaFilled(false);
		}
		for(int i=0;i<8;i++) {
			F[i]=new JButton();
			
			F[i].setBounds(268,57+i*48,buttonSize,buttonSize);
			F[i].addActionListener(this);
			F[i].setOpaque(false);F[i].setContentAreaFilled(false);
		}
		for(int i=0;i<8;i++) {
			G[i]=new JButton();
			
			G[i].setBounds(314,57+i*48,buttonSize,buttonSize);
			G[i].addActionListener(this);
			G[i].setOpaque(false);G[i].setContentAreaFilled(false);
		}
		for(int i=0;i<8;i++) {
			H[i]=new JButton();
			
			H[i].setBounds(360,57+i*48,buttonSize,buttonSize);
			H[i].addActionListener(this);
			H[i].setOpaque(false);H[i].setContentAreaFilled(false);
		}
		

		c.add(l1);
		c.add(l2);
		c.add(l3);
		c.add(l4);
		c.add(l5);
		c.add(b1);
		c.add(b2);
		j.getLayeredPane().add(chessboard,100);
		
		for(int i=0;i<8;i++) {
			j.getLayeredPane().add(A[i],0);
			j.getLayeredPane().add(B[i],0);
			j.getLayeredPane().add(C[i],0);
			j.getLayeredPane().add(D[i],0);
			j.getLayeredPane().add(E[i],0);
			j.getLayeredPane().add(F[i],0);
			j.getLayeredPane().add(G[i],0);
			j.getLayeredPane().add(H[i],0);
		}
		c.setLayout(null);
		
		j.setVisible(true);
		j.setResizable(false);
		map.initMap();
		map.castToBoard(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		/*
		if(e.getSource()==A[0]) {
			System.out.println("A[0]");
			try {
				
				Socket s = new Socket("localhost",10301);
				
				OutputStream os = s.getOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(os);
				
				transData data =new transData(0,0);
				//hash.put(3,data);
				oos.writeObject(data);
				oos.close();
				s.close();
				
			}catch(Exception erro) {erro.printStackTrace();}
		}
	*/
		if(e.getSource()==b1) {
			System.out.println("open new window");
			osero_setting = new Osero_setting(this);
			
		}
		
		try {
			for(int i=0; i<8;i++) {
				if(e.getSource()==A[i]) {
					map.updateMap(i, 0,turn);
					map.castToBoard(this);
					
					turn = 1-turn;
					map.checkMap(turn);
				}
			else if(e.getSource()==B[i]) {
				map.updateMap(i, 1,turn);
				map.castToBoard(this);
				
				turn = 1-turn;
				map.checkMap(turn);
			}
			else if(e.getSource()==C[i]) {
				map.updateMap(i, 2,turn);
				map.castToBoard(this);
				
				turn = 1-turn;
				map.checkMap(turn);
			}
			else if(e.getSource()==D[i]) {
				map.updateMap(i, 3,turn);
				map.castToBoard(this);
				
				turn = 1-turn;
				map.checkMap(turn);
			}
			else if(e.getSource()==E[i]) {
				map.updateMap(i, 4,turn);
				map.castToBoard(this);
				
				turn = 1-turn;
				map.checkMap(turn);
			}
			else if(e.getSource()==F[i]) {
				map.updateMap(i, 5,turn);
				map.castToBoard(this);
				
				turn = 1-turn;
				map.checkMap(turn);
			}
			else if(e.getSource()==G[i]) {
				map.updateMap(i, 6,turn);
				map.castToBoard(this);
				
				turn = 1-turn;
				map.checkMap(turn);
			}
			else if(e.getSource()==H[i]) {
				map.updateMap(i, 7,turn);
				map.castToBoard(this);
				
				turn = 1-turn;
				map.checkMap(turn);
			}
			}
	
		}catch(ArithmeticException pe) {
			System.out.println("you can't place here");
		}
		catch(Exception erro) {
			erro.printStackTrace();
		}
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Oserov4();
		}
	
	public class Map {
		int r;
		int l;
		int [][]map = new int [8][8];//榛�0,鐧�1,缃亼銈嬪牬鎵�2,绌虹櫧3

		public Map(){
			this.r = 8;
			this.l = 8;
		}

		public void initMap() {
			for(int i=0;i<8;i++) {
				Arrays.fill(map[i], 3);//绌虹櫧銇у煁銈併倠
			}
			
			//涓ぎ4銉炪偣銇厤缃�
			map[3][3] = 1;
			map[3][4] = 0;
			map[4][3] = 0;
			map[4][4] = 1;
			//鍏堟墜銇岄粧銇伄銇ч粧銇銇欍倠鈥濈疆銇戙倠鍫存墍鈥濄倰鎺㈢储銇欍倠
			checkMap(0);
			printMap();
		}
		
		public void printMap() {
			System.out.println("====================");
			System.out.println(" | 0 1 2 3 4 5 6 7");
			System.out.println("-+------------------");
			for(int i=0; i<8;i++) {
				System.out.print(String.valueOf(i)+"| ");
				for(int j=0; j<8; j++) {
					System.out.print(map[i][j]);
					System.out.print(' ');
				}
				System.out.println();
			}
			System.out.println("====================");
		}
		
		public void updateMap(int r, int l, int p) throws ArithmeticException {//r:琛�, l:鍒�, p:缃亱銈屻仧鑹�
			
			if(map[r][l] != 2) {//缃亼銈嬪牬鎵�浠ュ銇疆銇撱亞銇ㄣ仐銇熴仺銇�,绌虹櫧銇疆銇撱亞銇ㄣ仐銇熴仺銇嶃倐鍚個
				System.out.println("this place has been occupied");
				throw new ArithmeticException();
			} else if(p != 0 && p != 1){//鎵嬬暘鎯呭牨銇岄粧(0),鐧�(1)浠ュ銇仺銇�
				System.out.println("invalid value");
			} else {
				map[r][l] = p;

				//缃亜銇熷牬鎵�銇懆鍥�8銉炪偣銈掓帰绱�
				//0銇岀疆銇嬨倢銇熴倝1銈掋��1銇岀疆銇嬨倢銇熴倝0銈掓帰绱€仚銈�
				int target = 1 - p;//鎺㈢储瀵捐薄

				//鎺㈢储鏂瑰悜銈掓寚瀹氥仐銇list銇牸绱�=>鏈夊姽銇帰绱㈡柟鍚戙伀銇�ゃ亴娈嬨倞鐒″姽銇帰绱㈡柟鍚戙伀銇�-1銇屾畫銈�
				//0:[-1,-1] 1:[-1, 0] 2:[-1, 1]
				//3:[ 0,-1]   [ r, l] 4:[ 0, 1]
				//5:[ 1,-1] 6:[ 1, 0] 7:[ 1, 1]
				Integer dir[][] = {{-1,-1},{-1,0},{-1,1},{0,-1},{0,1},{1,-1},{1,0},{1,1}};
				Integer ilist[] = {0,1,2,3,4,5,6,7};
				if(r == 0){
					ilist[0] = -1;
					ilist[1] = -1;
					ilist[2] = -1;
				}else if(r == 7){
					ilist[5] = -1;
					ilist[6] = -1;
					ilist[7] = -1;
				}
				if(l == 0){
					ilist[0] = -1;
					ilist[3] = -1;
					ilist[5] = -1;
				}else if(l == 7){
					ilist[2] = -1;
					ilist[4] = -1;
					ilist[7] = -1;
				}
				//System.out.println(Arrays.asList(ilist));

				for(int i: ilist){
					if(i >= 0){//i=-1銇牬鍚堛倰闄ゃ亸
						//System.out.println(i);
						boolean turnFlag = false;//鐭炽倰銇层仯銇忋倞杩斻仚銇嬨仼銇嗐亱
						int tr,tl;//tr=琛�,tl=鍒�
						for(tr=r+dir[i][0], tl=l+dir[i][1]; (0<=tr && tr<8 && 0<=tl && tl<8); tr+=dir[i][0], tl+=dir[i][1]){//琛屽垪銇�0锝�7銇瘎鍥层倰瓒呫亪銇亜銈堛亞銇�
							//System.out.println("tr:"+tr+" tl:"+tl);
							if(map[tr][tl] == target){
								//turnFlag銇宼rue->true銇牬鍚堛伅鎺㈢储銈掔稓琛�
								//turnFlag銇宖alse->true銇牬鍚堛伅缍氳(鍒濆洖銇伩鐧虹敓)
								turnFlag = true;
							}else if(turnFlag){
								//turnFlag銇宼rue->false銇ㄥ鍖栥仐銆併亱銇ap[tr][tl]銇岃嚜鍒嗐伄鎵嬬暘鎯呭牨銇с亗銈嬪牬鍚堛伅瀹熼殯銇伈銇ｃ亸銈婅繑銇欏繀瑕併亴鐢熴仒銈�
								//銇层仯銇忋倞杩斻仚
								if(map[tr][tl] == p){
									//System.out.println("turn");
									int ttr, ttl;
									for(ttr=r, ttl=l; (tr!=ttr || tl!=ttl); ttr+=dir[i][0], ttl+=dir[i][1]){
										map[ttr][ttl] = p;
									}
									break;
								}
							}else{
								//turnFlag銇宖alse->false銇牬鍚堛伅銇撱伄鎺㈢储鏂瑰悜銇с伄鎺㈢储銈掔祩浜�(鍒濆洖銇伩鐧虹敓)
								break;
							}
						}
						//鎺㈢储绲備簡鏅傘伀turnFlag銇宼rue銇牬鍚堛伅銇层仯銇忋倞杩斻仜銇亜
					}
				}
			}
			
			checkMap(p);
			printMap();
		}

		public void checkMap(int t){//t:鑷垎銇墜鐣儏鍫�=0or1
			//銆岃嚜鍒嗐伄銆嶇疆銇戙倠鍫存墍銈掕銇广�併仢銇優銈广倰2銇銇堛倠

			//銉炪偣銈掔禐銈嬨伄銇岄潰鍊掋仾銇仹鍏ㄣ優銈广伀瀵俱仐銇︽帰绱�
			int j,k;//j=琛�,k=鍒�
			for(j=0; j<8; j++){
				for(k=0; k<8; k++){
					//map[j][k]銇懆鍥�8銉炪偣銈掓帰绱�
					if(map[j][k] == 0 || map[j][k] == 1){
						//map[j][k]銇棦銇煶銇岀疆銇嬨倢銇︺亜銇熴倝鍒ゅ垾銇欍倠蹇呰銇屻仾銇勩伄銇ontinue
						continue;
					}else{
						//缃亼銈嬪牬鎵�(2)銇儏鍫便亴娈嬨仯銇︺亜銈嬨仺闈㈠�掋仾銇仹涓�鏃�3銇с儶銈汇儍銉�
						map[j][k] = 3;
					}

					//1銇岀疆銇嬨倢銇熴倝0銈掋��0銇岀疆銇嬨倢銇熴倝1銈掓帰绱€仚銈�
					int target = 1 - t;//鎺㈢储瀵捐薄

					//鎺㈢储鏂瑰悜銈掓寚瀹氥仐銇list銇牸绱�=>鏈夊姽銇帰绱㈡柟鍚戙伀銇�ゃ亴娈嬨倞鐒″姽銇帰绱㈡柟鍚戙伀銇�-1銇屾畫銈�
					//0:[-1,-1] 1:[-1, 0] 2:[-1, 1]
					//3:[ 0,-1]   [ r, l] 4:[ 0, 1]
					//5:[ 1,-1] 6:[ 1, 0] 7:[ 1, 1]
					Integer dir[][] = {{-1,-1},{-1,0},{-1,1},{0,-1},{0,1},{1,-1},{1,0},{1,1}};
					Integer ilist[] = {0,1,2,3,4,5,6,7};
					if(j == 0){
						ilist[0] = -1;
						ilist[1] = -1;
						ilist[2] = -1;
					}else if(j == 7){
						ilist[5] = -1;
						ilist[6] = -1;
						ilist[7] = -1;
					}
					if(k == 0){
						ilist[0] = -1;
						ilist[3] = -1;
						ilist[5] = -1;
					}else if(k == 7){
						ilist[2] = -1;
						ilist[4] = -1;
						ilist[7] = -1;
					}
					//System.out.println(Arrays.asList(ilist));

					for(int i: ilist){
						if(i >= 0){//i=-1銇牬鍚堛倰闄ゃ亸
							boolean turnFlag = false;//鐭炽倰銇层仯銇忋倞杩斻仜銈嬨亱銇┿亞銇�
							int tr,tl;//tr=琛�,tl=鍒�
							for(tr=j+dir[i][0], tl=k+dir[i][1]; (0<=tr && tr<8 && 0<=tl && tl<8); tr+=dir[i][0], tl+=dir[i][1]){//琛屽垪銇�0锝�7銇瘎鍥层倰瓒呫亪銇亜銈堛亞銇�
								if(map[tr][tl] == target){
									//turnFlag銇宼rue->true銇牬鍚堛伅鎺㈢储銈掔稓琛�
									//turnFlag銇宖alse->true銇牬鍚堛伅缍氳(鍒濆洖銇伩鐧虹敓)
									turnFlag = true;
								}else if(turnFlag){
									//turnFlag銇宼rue->false銇ㄥ鍖栥仐銆併亱銇ap[tr][tl]銇岃嚜鍒嗐伄鎵嬬暘鎯呭牨銇с亗銈嬪牬鍚堛伅瀹熼殯銇伈銇ｃ亸銈婅繑銇涖倠
									if(map[tr][tl] == t){
										map[j][k] = 2;
										break;//1鏂瑰悜銇с倐銇层仯銇忋倞杩斻仜銈屻伆鑹亜銇仹銉兗銉椼倰鎶溿亼銈�
									}
								}else{
									//turnFlag銇宖alse->false銇牬鍚堛伅銇撱伄鎺㈢储鏂瑰悜銇с伄鎺㈢储銈掔祩浜�(鍒濆洖銇伩鐧虹敓)
									break;
								}
							}
						//鎺㈢储绲備簡鏅傘伀turnFlag銇宼rue銇牬鍚堛伅銇层仯銇忋倞杩斻仜銇亜
						}
					}
				}
			}//j,k銇倛銈嬪叏鎺㈢储绲備簡
		}
		public void castToBoard(Oserov4 osero) {

			for(int i=0; i<8;i++) {
				if(i==0) {
					for(int j=0;j<8;j++) {
						if(map[j][i]==1) {
					        osero.A[j].setIcon(iconW);
					        osero.A[j].setOpaque(true);
						}
						else if(map[j][i]==0) {
							osero.A[j].setIcon(iconB);
					        osero.A[j].setOpaque(true);
						}
					}
				}
				else if(i==1) {
					for(int j=0;j<8;j++) {
						if(map[j][i]==1) {
					        osero.B[j].setIcon(iconW);
					        osero.B[j].setOpaque(true);
						}
						else if(map[j][i]==0) {
							osero.B[j].setIcon(iconB);
					        osero.B[j].setOpaque(true);
						}
					}
				}
				else if(i==2) {
					for(int j=0;j<8;j++) {
						if(map[j][i]==1) {
					        osero.C[j].setIcon(iconW);
					        osero.C[j].setOpaque(true);
						}
						else if(map[j][i]==0) {
							osero.C[j].setIcon(iconB);
					        osero.C[j].setOpaque(true);
						}
					}
				}
				else if(i==3) {
					for(int j=0;j<8;j++) {
						if(map[j][i]==1) {
					        osero.D[j].setIcon(iconW);
					        osero.D[j].setOpaque(true);
						}
						else if(map[j][i]==0) {
							osero.D[j].setIcon(iconB);
					        osero.D[j].setOpaque(true);
						}
					}
				}
				else if(i==4) {
					for(int j=0;j<8;j++) {
						if(map[j][i]==1) {
					        osero.E[j].setIcon(iconW);
					        osero.E[j].setOpaque(true);
						}
						else if(map[j][i]==0) {
							osero.E[j].setIcon(iconB);
					        osero.E[j].setOpaque(true);
						}
					}
				}
				else if(i==5) {
					for(int j=0;j<8;j++) {
						if(map[j][i]==1) {
					        osero.F[j].setIcon(iconW);
					        osero.F[j].setOpaque(true);
						}
						else if(map[j][i]==0) {
							osero.F[j].setIcon(iconB);
					        osero.F[j].setOpaque(true);
						}
					}
				}
				else if(i==6) {
					for(int j=0;j<8;j++) {
						if(map[j][i]==1) {
					        osero.G[j].setIcon(iconW);
					        osero.G[j].setOpaque(true);
						}
						else if(map[j][i]==0) {
							osero.G[j].setIcon(iconB);
					        osero.G[j].setOpaque(true);
						}
					}
				}
				else if(i==7) {
					for(int j=0;j<8;j++) {
						if(map[j][i]==1) {
					        osero.H[j].setIcon(iconW);
					        osero.H[j].setOpaque(true);
						}
						else if(map[j][i]==0) {
							osero.H[j].setIcon(iconB);
					        osero.H[j].setOpaque(true);
						}
					}
				}
			}//for
			
			

		}//castToBoard 将棋子投射到棋盘上
	}
	
	class Iner_Thread extends Thread{
		Oserov4 osero;
		Iner_Thread(Oserov4 osero){
			this.osero = osero;
		}
		@Override
		public void run() {
			new Osero_setting(osero);
		}
	}
	
} //Oserov4
	

// comment: 棋盘应该是横纵为偶数，并且初始化双方各两个子交叉排列在中心，只有旁边有棋子的时候才能下棋
			

/*映射机制：按钮映射到Map的实例上，Map的实例将按钮的状态（黑或者白）映射到棋盘上
    协议机制：Osero传送Object数据，根据Object的类型（isinstanceof()）来判断是什么数据然后读取数据
  */
 //(GUI)button(send chess loction to the sever) - map -updata - map - GUI
//GUI(Login Room Osero) - client - sever - client - GUI(Login Room Osero)

