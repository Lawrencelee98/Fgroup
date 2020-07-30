package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import client.Oserov4.Ban;
import transData.transData;

public class Reciever extends Thread{
	Client client =  null;
	Ban map = null;
	ObjectInputStream ois = null;
	int protocol;
	transData end = null;
	public Reciever(Client client, Ban map, ObjectInputStream ois,transData end) {
		this.client = client;
		this.map = map;
		this.ois = ois;
		this.end = end;
	}
	@Override
	public void run (){
		transData r_data = null;
		try {
			
			System.out.println("receiver start ");
			r_data = (transData)ois.readObject();
			System.out.println("receive data");
			this.protocol=r_data.get_protocol();
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		if(r_data instanceof transData) {
				if (this.protocol==3){
						System.out.println("opponent:row="+r_data.get_row()+",line="+r_data.get_line());
						//client.r_data = r_data;
						client.your_turn = 1;
						System.out.println("row: "+r_data.get_row()+" line: "+r_data.get_line());
						map.updateMap(r_data.get_row(), r_data.get_line(),1-client.turn);
						map.checkMap(client.turn);
						map.castToBoard();
						map.timeupdater();
				}else if(this.protocol==2000){
					System.out.println("protocol 2000");
					end.set_endinfo_lose();
					int your_turn = client.your_turn;
					int result = 1-your_turn;
					new Result(result,this.client);
				}
				else{
					System.out.println("no data");
				}
		}
		//run();
	}
}
