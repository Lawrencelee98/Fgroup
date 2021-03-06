package client;
import javax.swing.*;
import java.net.*;
import java.util.*;
import transData.transData;
import java.io.*;
import transData.*;
import java.awt.*;

public class Client {
	// atributions for Register_display
	private String register_name = "default";
	private String register_pass = "default";
	// atributions for Login_display
	private String login_name = "default";
	private String login_pass = "default";
	// atributions for Room selecting
	int room_num = 1;
	int room_port = 0;
	private int login_port = 0;

	Socket s = null;
	Socket s_room = null;
	public ObjectOutputStream oos = null;
	public ObjectInputStream ois = null;

	int first_port = 10000;
	String ServerAddress = "localhost";
	int turn = 1;
	int your_turn = 0;
	String secret_ans = null;

	boolean CPUflag = false;
	Oserov4_cpu oserov4_cpu = null;
	
	transData r_data = null;
	Oserov4 osero = null;
	String username;
	String password;
	Login_display Login;
	Point pos = new Point(0, 0);

	public Client() {
	}

	public void setLoginPort(int login_port) {
		this.login_port = login_port;
	}

	public int getLoginPort() {
		return login_port;
	}

	// M:TODO receive login-port(10100,10200,...,10800)
	public int FirstConnect(String ServerAddress, int first_port) {
		Socket s = null;
		try {
			s = new Socket(ServerAddress, first_port);
			OutputStream os = s.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(os);
			InputStream is = s.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(is);
			transData login_port_obj = (transData) ois.readObject();
			// M:protocol-85-object is used to exchange port number
			if (login_port_obj.get_protocol() == 85) {
				login_port = login_port_obj.get_port();
			} else {
				System.out.println("Error(FirstConnect Method)1: can't receive login-port");
				System.exit(1);
			}
		} catch (Exception se) {
			System.out.println("Error(FirstConnect Method)2: can't connect Server");
			se.printStackTrace();
			System.exit(2);
		} finally {
			try {
				s.close();
			} catch (Exception ser) {
				System.out.println("Error(FirstConnect Method)3: can't close Socket");
				ser.printStackTrace();
			} finally {
			}
		}
		return login_port;
	}

	// method for
	public boolean send_login_info(String usr, String pass, ObjectInputStream ois, ObjectOutputStream oos) {
		boolean flag = false;
		transData user = new transData(10);
		user.set_login_name_pass(usr, pass);
		try {
			oos.writeObject(user);
			transData answer = (transData) ois.readObject();
			String ans = answer.get_login_answer();
			if (ans.equals("login succeed")) {
				// todo
				System.out.println("receive login success");
				this.login_name = usr;
				this.login_pass = pass;
				flag = true;
			} else if (ans.equals("login failed : false password")) {
				// todo reset pass
				System.out.println("receive login failed");
				JOptionPane.showConfirmDialog(null, "パスワードが違います", "ログインに失敗しました", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
				flag = false;
			} else if (ans.equals("login failed : this name does not exist")) {
				// todo reset name or name&pass
				JOptionPane.showConfirmDialog(null, "指定されたユーザ名が存在しません", "ログインに失敗しました", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
				flag = false;
			}
		} catch (Exception ee) {
			ee.printStackTrace();
		} finally {
			return flag;
		}
	}
	public boolean send_changepass_info(String usr, String pass,String question, ObjectInputStream ois, ObjectOutputStream oos) {
		boolean flag = false;
		transData user = new transData(37);
		user.set_change_name_pass_question(usr, pass,question);
		try {
			oos.writeObject(user);
			transData answer = (transData) ois.readObject();
			String ans = answer.get_login_answer();
			if (ans.equals("change succeed")) {
				// todo
				System.out.println("receive change succeed");
				this.login_name = usr;
				this.login_pass = pass;
				flag = true;
			} else if (ans.equals("change failed : false question answer")) {
				// todo reset pass
				System.out.println("revcieve change failed : false question answer");
				flag = false;
			} else if (ans.equals("change failed : this name does not exist")) {
				// todo reset name or name&pass
				System.out.println("revcieve change failed : this name does not exist");
				flag = false;
			}
		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			return flag;
		}
	}
	// method for Register_display

	public boolean send_register_name_pass(String register_name, String register_pass, String secret_ans,
			ObjectInputStream ois, ObjectOutputStream oos) {
		int protocol = 20;
		transData data = new transData(protocol);
		boolean flag = false;

		this.register_name = register_name;
		this.register_pass = register_pass;
		this.secret_ans = secret_ans;
		data.set_register_name_pass_question(register_name, register_pass, secret_ans);
		System.out.println("set the register data suceess");
		try {
			oos.writeObject(data);
			transData answer = (transData) ois.readObject();
			String ans = answer.get_login_answer();
			System.out.println("answer.get login answer : " + ans);
			if (ans.equals("register succeed")) {
				// todo
				System.out.println("receive register succeed");
				flag = true;
			} else if (ans.equals("Register failed : this name is already used")) {
				// todo reset pass
				JOptionPane.showConfirmDialog(null, "指定したユーザ名は既に使用されています", "登録に失敗しました", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
				System.out.println("receive register failed");
				flag = false;
			}
			System.out.println("Send register info successfully");
			return flag;
		} catch (Exception erro) {
			System.out.println("Send register info erro");
			erro.printStackTrace();
			return flag;
		}
	}

	public void choose_room(ObjectOutputStream oos, ObjectInputStream ois) {
		int room_port = 10011;
		Room room = null;
		Room.Display4 dis4 = null;
		try {
			System.out.println("room_obj start");
			// room info 受け取る
			transData room_obj = (transData) ois.readObject();
			System.out.println("room_obj end");
			Map<Integer, Integer> room_info = room_obj.get_room_info();
			System.out.println(room_info);
			java.util.List<String> players_info = room_obj.get_players_info();
			System.out.println(players_info);
			room = new Room(this, room_info, players_info, oos, ois);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// return room_port;
		}
	}
	// ルームに入った後、時間選択をして、サーバにprotocol80で時間を送る、CPU対戦に入る。
	// 二番目のプレイヤーがルームに入ると、サーバがprotocol80で先に入ったプレイヤーに教える。
	// 先に入ったプレイヤーがCPU対戦から出る。サーバがランダム選択した時間の合意を選択する。
	public void send_wait_time(int time, ObjectInputStream ois, ObjectOutputStream oos) {
		int protocal = 0;
		transData data = new transData(protocal);
		data.set_wait_time(time);
		try {
			oos.writeObject(data);
			oos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//send battle start information to server
	public void send_battle_start(ObjectOutputStream oos){
		transData battle_start = new transData(80);
		try{
			oos.writeObject(battle_start);
			oos.flush();
		}catch(Exception erro){
			erro.printStackTrace();
		}

	}
	public String get_user_name(){
		return this.username;
	}

	public String get_user_pass(){
		return this.password;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Client client = new Client();
		client.setLoginPort(client.FirstConnect(client.ServerAddress, client.first_port));
		client.Login = new Login_display("Login", client);

	}
}
