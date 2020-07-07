package client;

import java.io.IOException;
import java.io.ObjectInputStream;

import transData.transData;

public class Reciever extends Thread{
	Client client =  null;
	transData r_data = null;
	ObjectInputStream ois  = null;
	
	
	public Reciever(Client client, ObjectInputStream ois) {
		this.client = client;
		this.ois = ois;
	}
	
	public void run (){
		try {
			r_data = (transData)ois.readObject();
		} catch (ClassNotFoundException | IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		if(r_data instanceof transData) {
				if (r_data.get_protocol()==3){
						System.out.println("opponent:row="+r_data.get_row()+",line="+r_data.get_line());
				}else{
					System.out.println("no data");
				}
		}
	}
}
