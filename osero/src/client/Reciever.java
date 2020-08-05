package client;
import java.io.ObjectInputStream;
import javax.swing.*;
import client.Oserov4.Ban;
import transData.transData;
import java.net.*;

public class Reciever extends Thread{
	Client client =  null;
	Ban map = null;
	ObjectInputStream ois = null;
	int protocol;
	JFrame j =null;
	transData r_data = null;
	transData end = null;
	transData battle_end = new transData(50);
	transData room_info =null;
	boolean pass = true;
	int result;
	int rate;
	boolean pass_flag = false;
	public Reciever(Client client, Ban map, ObjectInputStream ois,transData end,JFrame j,boolean pass,int rate,int result) {
		this.client = client;
		this.map = map;
		this.ois = ois;
		this.end = end;
		this.j = j;
		this.pass = pass;
		this.rate=rate;
		this.result = result;
		
	}
	@Override
	public void run (){
		int r=rate-5;
		if(r<=0) {
			r=0;
		}

		try {	
			do{
			transData r_data = null;
			this.pass_flag=false;
			System.out.println("receiver start ");
			r_data = (transData)ois.readObject();
			System.out.println("receive data");
			this.protocol=r_data.get_protocol();
		
		
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
								this.pass_flag=true;						
						}
						pass = true;
				}else if(this.protocol==2000){

						System.out.println("protocol 2000 : you lose");
						end.set_endinfo_lose();
						battle_end.set_battle_end(false);
						client.oos.writeObject(battle_end);
						System.out.println("send battle end protocol 50");
						if (client.turn == 0){
							client.oos.writeObject(end);
							System.out.println("send end protocol 36");
						}
						ois.readObject();
						room_info = (transData)ois.readObject();
						int result = client.turn^1;
						System.out.println(result);
						if(room_info.get_protocol()==14){
							System.out.println("room_info: "+room_info.get_room_info());
							System.out.println("get_player_info: "+room_info.get_players_info());
							//this.client.ois=ois;
							String Rate=String.valueOf (rate)+"→"+String.valueOf (r);
							
							new Result(result, this.client, room_info.get_room_info(), room_info.get_players_info(),Rate,j);
							
						}



					}else if(this.protocol==2100){
						System.out.println("prtocol 2100 : you win");
						end.set_endinfo_win();
						battle_end.set_battle_end(false);
						client.oos.writeObject(battle_end);
						System.out.println("send battle end protocol 50");
						if (client.turn == 0){
							client.oos.writeObject(end);
							System.out.println("send end protocol 36");
						}
						
						transData end_info = (transData)ois.readObject();
						room_info = (transData)ois.readObject();
						int result = client.turn;
						System.out.println(result);
						if(room_info.get_protocol()==14){
							System.out.println(room_info.get_room_info());
							System.out.println(room_info.get_players_info());
							//this.client.ois=ois;
							String Rate=String.valueOf (rate)+"→"+String.valueOf (rate+10);
							
							new Result(result, this.client, room_info.get_room_info(), room_info.get_players_info(),Rate,j);
							
						}

					}else if(this.protocol==12){
						System.out.println("get room info ");
						System.out.println(
							r_data.get_room_info()
						);




					}else if(this.protocol ==3000){
						int result = 3;
						JOptionPane.showConfirmDialog(null, "対戦相手がパスしました", null, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
						//产生分歧
						//分歧1：如果是我发送过p3000后对方也没有地方放棋子并且add_pass_count后发送信息的话
						System.out.println("r_data pass count:"+ r_data.get_pass_count());
						transData end = new transData(36);
						if(map.countNumber(0)>map.countNumber(1)){
							//black win
							if(client.turn == 0){
								end.set_endinfo_win();
								
							}else if(client.turn ==1){
								end.set_endinfo_lose();
							}
							this.result=0;
						}else if(map.countNumber(0)<map.countNumber(1)){
							if(client.turn==0){
								end.set_endinfo_lose();
							}else if(client.turn ==1){
								end.set_endinfo_win();
							}
							this.result=1;
						}else{
							end.set_endinfo_draw();
							this.result=2;
						}//设置对局输赢
						if(r_data.get_pass_count()>=1){
							// Game over
							// 发送 end PROTOCOL 36
							if(client.turn==0){
							transData battle_end = new transData(50);
							
							//发送结束
							client.oos.writeObject(battle_end);
							client.oos.writeObject(end);
							}else{}
						
							ois.readObject();
							room_info = (transData)ois.readObject();
							System.out.println(result);
						
							if(room_info.get_protocol()==14){
								System.out.println(room_info.get_room_info());
								System.out.println(room_info.get_players_info());
								//this.client.ois=ois;
								}
								if(this.result == 0){
									String Rate=String.valueOf (rate)+"→"+String.valueOf (rate+10);
									
									new Result(this.result, this.client, room_info.get_room_info(), room_info.get_players_info(),Rate,j);
								}else if(this.result == 1){
									String Rate=String.valueOf (rate)+"→"+String.valueOf (r);
									
									new Result(this.result, this.client, room_info.get_room_info(), room_info.get_players_info(),Rate,j);
								}else{
									String Rate=String.valueOf (rate)+"→"+String.valueOf (rate);
									
									new Result(this.result, this.client, room_info.get_room_info(), room_info.get_players_info(),Rate,j);
								}
						}
						//分歧2 如果从对方那里第一次收到p3000对方也没有地方放的时候
						else{
							for(int i=0;i<8;i++){
								for(int j=0;j<8;j++){
									if(map.getMapValue(i, j)==2){
										pass=false;
										break;
									}
								}
							}
							//如果我也没有地方放的话
							if(pass){
								
								//发送结束
								if(client.turn==0){
									transData battle_end = new transData(50);
									client.oos.writeObject(battle_end);
									client.oos.writeObject(end);
								}else{
									//把你也没有地方法，我也没有地方放的消息告诉对方，用同一个r_data
									transData rp_data =new transData(3000);
									rp_data.add_pass_count();
									client.oos.writeObject(rp_data);
									System.out.println("send rp data");
								}
								ois.readObject();
								room_info = (transData)ois.readObject();				
								System.out.println(result);
								if(room_info.get_protocol()==14){
									System.out.println(room_info.get_room_info());
									System.out.println(room_info.get_players_info());
									//this.client.ois=ois;
								}
							
								if(this.result == 0){
									String Rate=String.valueOf (rate)+"→"+String.valueOf (rate+10);
									
									new Result(this.result, this.client, room_info.get_room_info(), room_info.get_players_info(),Rate,j);
								}else if(this.result == 1){
									String Rate=String.valueOf (rate)+"→"+String.valueOf (r);
									
									new Result(this.result, this.client, room_info.get_room_info(), room_info.get_players_info(),Rate,j);
								}else{
									String Rate=String.valueOf (rate)+"→"+String.valueOf (rate);
									
									new Result(this.result, this.client, room_info.get_room_info(), room_info.get_players_info(),Rate,j);
								}

								}
								//如果我有地方放的话
							else {
									map.checkMap(client.turn);
									map.castToBoard();
									map.timeupdater();
									client.your_turn=1;
									pass = true;
									//r_data.reset_pass_count();
								}			
						}
					}
					// 以上为 protocol 3000 的内容
					else if(this.protocol == 2200){
						System.out.println("get end info -- protocol 2200");
						room_info = (transData)ois.readObject();
						int result = client.turn;
						System.out.println(result);
						if(room_info.get_protocol()==14){
							System.out.println(room_info.get_room_info());
							System.out.println(room_info.get_players_info());
							//this.client.ois=ois;
						}
						if(this.result == 0){
							String Rate=String.valueOf (rate)+"→"+String.valueOf (rate+10);
							
							
							new Result(this.result, this.client, room_info.get_room_info(), room_info.get_players_info(),Rate,j);
							
						}else if(this.result == 1){
							
							String Rate=String.valueOf (rate)+"→"+String.valueOf (r);
							
							
							new Result(this.result, this.client, room_info.get_room_info(), room_info.get_players_info(),Rate,j);
							
						}else{
							String Rate=String.valueOf (rate)+"→"+String.valueOf (rate);
							
							new Result(this.result, this.client, room_info.get_room_info(), room_info.get_players_info(),Rate,j);
							
						}
						
					}
					else{
						System.out.println("no data");
					}
					//protocol 判断结束
				}while(this.pass_flag);
			}catch(Exception e){
						System.out.println("io exception");
								}					
		}//run();
	}//thread
