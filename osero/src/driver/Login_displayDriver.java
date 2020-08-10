package driver;
import client.*;

public class Login_displayDriver{
	ClientStub c = new ClientStub();

	public Login_displayDriver(){
		c.flag = false;
		System.out.println("client.flag : " + c.flag);
		new Login_display("Login", c);
	}
	public static void main(String[] args) throws Exception{
		new Login_displayDriver();
	}

}