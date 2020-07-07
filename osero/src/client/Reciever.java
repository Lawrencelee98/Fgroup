package client;

import java.io.IOException;
import java.io.ObjectInputStream;

import client.Oserov4.Ban;
import transData.transData;

public class Reciever extends Thread{
	Client client =  null;
	transData r_data = null;
	Ban map = null;
	
	public Reciever(Client client, Ban map) {
		this.client = client;
		this.map = map;
	}
	
	public void run (){
		try {
			r_data = (transData)client.ois.readObject();
		} catch (ClassNotFoundException | IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		if(r_data instanceof transData) {
				if (r_data.get_protocol()==3){
						System.out.println("opponent:row="+r_data.get_row()+",line="+r_data.get_line());
						client.r_data = r_data;
				}else{
					System.out.println("no data");
				}
		}
	}
}
