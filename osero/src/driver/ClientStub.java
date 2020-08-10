package driver;
import javax.swing.*;
import java.net.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.lang.*;
import transData.transData;
import client.*;

public class ClientStub extends Client{
	
	private String register_name = "default";
	private String register_pass = "default";
	private String login_name = "default";
	private String login_pass = "default";

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

	Boolean flag;

	public ClientStub(){
	}

	public int getLoginPort() {
		return 10000;
	}

	public int FirstConnect(String ServerAddress, int first_port) {
		System.out.println("client.FirstConnect ServerAddress:" + ServerAddress + " first_port:" + first_port);
		return 1000;
	}

	public boolean send_login_info(String usr, String pass, ObjectInputStream ois, ObjectOutputStream oos) {
		System.out.println("client.send_login_info usr:" + usr + " pass:" + pass + " oos:" + oos.toString() + " ois:" + ois.toString());
		return flag;
	}
	public boolean send_changepass_info(String usr, String pass,String question, ObjectInputStream ois, ObjectOutputStream oos) {
		System.out.println("client.send_changepass_info usr:" + usr + " pass:" + pass + " question:" + question +" oos:" + oos.toString() + " ois:" + ois.toString());
		return flag;
	}

	public boolean send_register_name_pass(String register_name, String register_pass, String secret_ans, ObjectInputStream ois, ObjectOutputStream oos) {
		System.out.println("client.send_changepass_info register_name:" + register_name + " register_pass:" + register_pass + " secret_ans:" + secret_ans +" oos:" + oos.toString() + " ois:" + ois.toString());
		return flag;
	}

	public void choose_room(ObjectOutputStream oos, ObjectInputStream ois) {
		System.out.println("client.choose_room oos:" + oos.toString() + " ois:" + ois.toString());
	}

	public void send_wait_time(int time, ObjectInputStream ois, ObjectOutputStream oos) {
		System.out.println("client.send_wait_time time:" + time + " oos:" + oos.toString() + " ois:" + ois.toString());
	}

	public void send_battle_start(ObjectOutputStream oos){
		System.out.println("client.send_battle_start:" + oos.toString());
	}

	public String get_user_name(){
		System.out.println("client.get_user_name");
		return "usr";
	}

	public String get_user_pass(){
		System.out.println("client.get_user_pass");
		return "pwd";
	}

}