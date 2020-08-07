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
	boolean f = true;
	int result=3;
	int rate;
	boolean loop_flag = false;
	boolean pass_game_over; //if both side have no place to put another chess,pass_game_over will turn to be true
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
			this.loop_flag=false;
			System.out.println("receiver start ");
			r_data = (transData)ois.readObject();
			this.protocol=r_data.get_protocol();
			System.out.println("receive data protocol : " + this.protocol);
			
			if (this.protocol==3){
						System.out.println("opponent:row="+r_data.get_row()+",line="+r_data.get_line());
						client.your_turn = 1;
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
							map.checkMap(1-client.turn);
							for(int i=0;i<8;i++){
								for(int j=0;j<8;j++){
									if(map.getMapValue(i, j)==2){   
										pass=true;
										System.out.println("other side has place to put");
										break;       }
								}
							}
							if(pass){
								System.out.println("send pass");
								transData pass = new transData(3000);
								client.oos.writeObject(pass);
								client.your_turn=0;	
								this.loop_flag=true;
							}else{
								System.out.println("both have no place to put");
								// judge who is winner
								if(map.countNumber(client.turn)>map.countNumber(1-client.turn)){
									//you win send 2100
									transData win = new transData(2100);
									client.oos.writeObject(win);
									this.loop_flag = true;
								}
								else if(map.countNumber(client.turn)<map.countNumber(1-client.turn)){
									//you lose
									transData lose = new transData(2000);
									client.oos.writeObject(lose);
									this.loop_flag = true;
								}
								else{
									// draw
									transData draw = new transData(2300);
									client.oos.writeObject(draw);
									this.loop_flag = true;
								}
							}	
							
						}// if(pass)
				}else if(this.protocol==2000){
						client.osero.l2.setVisible(false);
						client.osero.l3.setVisible(false);
						System.out.println("protocol 2000 : you lose");
						end.set_endinfo_lose();
						client.oos.writeObject(end);
						System.out.println("send end protocol 36");
						transData end_info = (transData)ois.readObject();
						room_info = (transData)ois.readObject();
						if(room_info.get_protocol()==14){
							System.out.println("room_info: "+room_info.get_room_info());
							System.out.println("get_player_info: "+room_info.get_players_info());	
							String Rate=String.valueOf (rate)+"→"+String.valueOf (r);
							result = 1-client.turn;
							System.out.println(result);
							new Result(result, this.client, room_info.get_room_info(), room_info.get_players_info(),Rate,j);
						}
					}else if(this.protocol==2100){
						client.osero.l2.setVisible(false);
						client.osero.l3.setVisible(false);
						System.out.println("prtocol 2100 : you win");
						end.set_endinfo_win();				
						client.oos.writeObject(end);
						System.out.println("send end protocol 36");
						transData end_info = (transData)ois.readObject();
						room_info = (transData)ois.readObject();
						System.out.println(result);
						if(room_info.get_protocol()==14){
							System.out.println(room_info.get_room_info());
							System.out.println(room_info.get_players_info());
							result = client.turn;
							System.out.println(result);
							String Rate=String.valueOf (rate)+"→"+String.valueOf (rate+10);
							new Result(result, this.client, room_info.get_room_info(), room_info.get_players_info(),Rate,j);	
						}
					}else if(this.protocol==2300){
						client.osero.l2.setVisible(false);
						client.osero.l3.setVisible(false);
						System.out.println("prtocol 2300 : draw");
						end.set_endinfo_draw();					
						client.oos.writeObject(end);
						System.out.println("send end protocol 36");
						transData end_info = (transData)ois.readObject();
						room_info = (transData)ois.readObject();
						System.out.println("get room information");
						if(room_info.get_protocol()==14){
							System.out.println(room_info.get_room_info());
							System.out.println(room_info.get_players_info());
							int result = client.turn;
							System.out.println(result);
							String Rate=String.valueOf (rate)+"→"+String.valueOf (rate);
							new Result(2, this.client, room_info.get_room_info(), room_info.get_players_info(),Rate,j);	
						}
					}else if(this.protocol ==3000){
						System.out.println("recieve r_data protocol 3000");
						client.your_turn=1;
						JOptionPane.showConfirmDialog(j, "対戦相手がパスしました", null, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
						}// 以上为 protocol 3000 的内容	
					else if(this.protocol==5000){
						System.out.println("recieve protocol 5000");
						System.out.println("opponent:row="+r_data.get_row()+",line="+r_data.get_line());
						client.your_turn = 1;
						map.updateMap(r_data.get_row(), r_data.get_line(),1-client.turn);
						map.checkMap(client.turn);
						map.castToBoard();
						map.timeupdater();
						result = map.isGameFinish();
						if(result == client.turn){
							transData win = new transData(2100);
							client.oos.writeObject(win);
						}else if(result==2){
							transData draw = new transData(2300);
							client.oos.writeObject(draw);
						}
						else{
							transData lose = new transData(2000);
							client.oos.writeObject(lose);
						}
						this.loop_flag=true;
					}
					else{
						System.out.println("no data");
					}
					//protocol 判断结束
				}while(this.loop_flag);
			}catch(Exception e){
				e.printStackTrace();
			}					
		}//run();
	}//thread
