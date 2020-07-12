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
	ObjectOutputStream oos = null;
	
	public Reciever(Client client, Ban map, ObjectInputStream ois, ObjectOutputStream oos) {
		this.client = client;
		this.map = map;
		this.ois = ois;
		this.oos = oos;
	}
	@Override
	public void run (){
		transData r_data = null;
		try {
			System.out.println("receive start");
			r_data = (transData)ois.readObject();
			System.out.println("receive data");
		} catch (ClassNotFoundException | IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		if(r_data instanceof transData) {
			if (r_data.get_protocol()==3){
						System.out.println("opponent:row="+r_data.get_row()+",line="+r_data.get_line());
						//client.r_data = r_data;
						client.your_turn = 1;
						map.updateMap(r_data.get_row(), r_data.get_line(),1-client.turn);
						map.castToBoard();
						map.timeupdater();
						//終了判定
						if(map.isGameFinish() != 3) {
							new Result(map.isGameFinish());
							
							try {
								client.s.close();
								client.setLoginPort(client.FirstConnect(client.ServerAddress, client.first_port));
								Socket s = new Socket(client.ServerAddress,client.getLoginPort());
								client.s = s;
								OutputStream os = s.getOutputStream();
								oos = new ObjectOutputStream(os);
								InputStream is = s.getInputStream();
								ois = new ObjectInputStream(is);
								client.choose_room(oos, ois);
							} catch (IOException e) {
								// TODO 自動生成された catch ブロック
								e.printStackTrace();
							}
						}
						map.checkMap(client.turn);
						map.printMap(client.turn);
						//おける場所がない
						System.out.println("count 2 = "+map.countNumber(2));
						if(map.countNumber(2) == 0) {
							//pass
							transData s_data = new transData(3000);
							client.your_turn = 0;
							try {
								client.oos.writeObject(s_data);
								System.out.println("You cant put anywhere, so your turn is passed");
								run();
							} catch (IOException e) {
								// TODO 自動生成された catch ブロック
								e.printStackTrace();
							}
						}
			}else if(r_data.get_protocol()==3000) {
				//相手からpassを受け取った
				client.your_turn = 1;
				map.castToBoard();
				map.timeupdater();
				map.checkMap(client.turn);
			}else{
				
			}
				System.out.println("no data");
			}
		//run();
	}
}
