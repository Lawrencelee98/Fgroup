package driver;
import client.*;

public class ExplainDriver{
	ClientStub c = new ClientStub();

	public ExplainDriver(){
		System.out.println("start Explain");
		new Explain("Explanation", c);
	}
	public static void main(String[] args) throws Exception{
		new ExplainDriver();
	}
}