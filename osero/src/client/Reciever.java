package client;
import java.io.ObjectInputStream;

import javax.swing.JFrame;

import client.Oserov4.Ban;
import transData.transData;
import java.net.*;

public class Reciever extends Thread{
	Client client =  null;
	Ban map = null;
	ObjectInputStream ois = null;
	int protocol;
	JFrame j;
	transData end = null;
	transData battle_end = new transData(50);
	transData room_info =null;
	boolean pass = true;
	Socket s;
	public Reciever(Client client, Ban map, ObjectInputStream ois,transData end,JFrame j,boolean pass) {
		this.client = client;
		this.map = map;
		this.ois = ois;
		this.end = end;
		this.j = j;
		this.pass = pass;
		this.s = s;
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
		}try{
				if (this.protocol==3){
						System.out.println("opponent:row="+r_data.get_row()+",line="+r_data.get_line());
						//client.r_data = r_data;
						client.your_turn = 1;
						System.out.println("row: "+r_data.get_row()+" line: "+r_data.get_line());

						map.updateMap(r_data.get_row(), r_data.get_line(),1-client.turn);
						map.checkMap(client.turn);
						map.castToBoard();
						map.timeupdater();
						for(int i=0;i<8;i++){
							for(int j=0;j<8;j++){
								if(map.getMapValue(i, j)==2){
									pass=false;
									break;
								}
							}
						}
						if(pass){
								System.out.println("pass");
								transData pass = new transData(3000);
								client.oos.writeObject(pass);
								client.your_turn=0;
						}
				}else if(this.protocol==2000){

						System.out.println("protocol 2000 : you lose");
						end.set_endinfo_lose();
						battle_end.set_battle_end(false);
						if (client.turn == 0)client.oos.writeObject(end);
						System.out.println("send end protocol 36");
						client.oos.writeObject(battle_end);
						System.out.println("send battle end protocol 50");
						
						room_info = (transData)ois.readObject();
						int result = client.turn^1;
						System.out.println(result);
						if(room_info.get_protocol()==14){
							System.out.println("room_info: "+room_info.get_room_info());
							System.out.println("get_player_info: "+room_info.get_players_info());
							this.client.ois=ois;
							new Result(result, this.client, room_info.get_room_info(), room_info.get_players_info());
							this.j.dispose();
						}



					}else if(this.protocol==2100){
						System.out.println("prtocol 2100 : you win");
						end.set_endinfo_win();
						battle_end.set_battle_end(false);
						if (client.turn == 0)client.oos.writeObject(end);
						System.out.println("send end protocol 36");
						client.oos.writeObject(battle_end);
						System.out.println("send battle end protocol 50");
						
						room_info = (transData)ois.readObject();
						int result = client.turn;
						System.out.println(result);
						if(room_info.get_protocol()==14){
							System.out.println(room_info.get_room_info());
							System.out.println(room_info.get_players_info());
							this.client.ois=ois;
							new Result(result, this.client, room_info.get_room_info(), room_info.get_players_info());
							this.j.dispose();
						}

					}else if(this.protocol==12){
						System.out.println("get room info ");
						System.out.println(
							r_data.get_room_info()
						);

					}else if(this.protocol ==3000){
						map.checkMap(client.turn);
						map.castToBoard();
						map.timeupdater();
						client.your_turn=1;
					}
					else{
						System.out.println("no data");
					}
				}catch(Exception e){
						System.out.println("io exception");
					}
				}
		//run();
	}

