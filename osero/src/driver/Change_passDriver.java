package driver;
import client.*;

public class Change_passDriver{
	ClientStub c = new ClientStub();

	public Change_passDriver(){
		c.flag = false;
		System.out.println("client.flag : " + c.flag);
		c.Login = new Login_display("Login", c);
		c.Login.setVisible(false);
		System.out.println("start Change_pass");
		new Change_pass("Change Password", c);
	}
	public static void main(String[] args) throws Exception{
		new Change_passDriver();
	}
}