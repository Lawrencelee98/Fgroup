package client;
import transData.*;
import java.net.*;
import java.io.*;
public class client {
	//atributions for Register_display
	private String register_name = "default";
	private String register_pass = "default";
	
	//atributions for Login_display
	private String login_name = "default";
	private String login_pass = "default";

	//atributions for Room selecting
	int room_num = -1;
	
	public client() {
		
	}
	//method for Login_display
	public void send_login_name_pass(String login_name,String login_pass) {
		int port =10;
		transData data = new transData(port);
		this.login_name = login_name;
		this.login_pass = login_pass;
		data.set_login_name_pass(login_name, login_pass);
		try {
			Socket socket = new Socket("localhost",port);
			OutputStream os = socket.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(os);
			oos.writeObject(data);
			oos.flush();
			socket.shutdownOutput();
			System.out.println("Send login info successfully");
		}catch(Exception erro) {
			System.out.println("Send login info erro");
			erro.printStackTrace();
		}
	}
	//method for Register_display
	public void send_register_name_pass(String register_name,String register_pass) {
		int port = 20;
		transData data = new transData(port);
		this.register_name = register_name;
		this.register_pass = register_pass;
		data.set_register_name_pass(register_name, register_pass);
		try {
			Socket socket = new Socket("localhost",port);
			OutputStream os = socket.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(os);
			oos.writeObject(data);
			oos.flush();
			socket.shutdownOutput();
			System.out.println("Send register info successfully");
		}catch(Exception erro) {
			System.out.println("Send register info erro");
			erro.printStackTrace();
		}
	}
	public void send_room_num(int room_num){
		this.room_num = room_num;
		int port = 13;
		transData data = new transData(port);
		try{
			Socket socket = new Socket("localhost",port);
			OutputStream os = socket.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(os);
			oos.writeObject(data);
			oos.flush();
			socket.shutdownOutput();
			System.out.println("Send room number successfully");
		}catch(Exception erro){
			erro.printStackTrace();
			System.out.println("Send room number failed");
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		client client = new client();
		new Login_display("Login",client);
	}

}
