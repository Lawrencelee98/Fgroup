//package client;
import java.io.*;
import java.util.*;
import java.net.*;
import transData.*;

public class Client {

    public static void main(String[] args) {


        //M:socket for choosing room(room-port)
				Socket s = null;

				//M:socket for recieving login-port
				Socket ls = null;
        ObjectOutputStream loos = null;
        ObjectInputStream lois = null;
				//M:all clients connetct this  port
				int first_port=10000;

				int login_port=0;
				int room_port=0;
				try{
						//M:TODO input server address
						String ServerAddress = "localhost";
	          ls = new Socket(ServerAddress,first_port);
	          OutputStream los = ls.getOutputStream();
	          loos = new ObjectOutputStream(los);
	          InputStream lis = ls.getInputStream();
	          lois = new ObjectInputStream(lis);

						//M:receive login-port(10100,10200,...,10800)
						while(true){
							transData login_port_obj = (transData)lois.readObject();
							//M:protocol-85-object is used to exchange port number
							if(login_port_obj.get_protocol()==85){
								login_port = login_port_obj.get_port();
								ls.close();
								break;
							}else{
								continue;
							}
						}

						//M:connect the port for login(10100,10200,...,10800)
						s = new Socket("localhost",login_port);
						OutputStream os = s.getOutputStream();
						oos = new ObjectOutputStream(os);
						InputStream is = s.getInputStream();
	          ois = new ObjectInputStream(is);

	       // TODO Auto-generated method stub
		       while(true){
						 //M:TODO input username and password
						 String username = "usr_1";
						 String password = "pass_1";
		         boolean flag = send_login_info(username,password, ois, oos);

			        if(flag) {
								//login success
			              break;
		          }else{
										//login failed
		          }
			 			}

						//M:choose room and receive room-port
			      room_port = choose_room(oos, ois);

				}catch (Exception e){

	      }finally {

	      }

        // stream for room
        ObjectOutputStream oos_room = null;
        ObjectInputStream ois_room = null;
        try{
            Socket s_room = new Socket("localhost",room_port);
            s.close(); // close unnecessary socket

            OutputStream os_room = s_room.getOutputStream();
            oos_room = new ObjectOutputStream(os_room);

            InputStream is_room = s_room.getInputStream();
            ois_room = new ObjectInputStream(is_room);

        }catch (Exception e){
            //e.printStackTrace();
        }finally {

        }

				//room connected
				//M:battle start notice with protocol-80
				try{
					transData r_data = (transData)ois_room.readObject();
					if (r_data.get_protocol()==80){
						System.out.println("Battle start!");
					}else{
						System.out.println("cant receive start");
					}
					//M:battle start
					Scanner scan = new Scanner(System.in);

					while(true){
						transData s_data = new transData(3);
						System.out.println("please wait for opponent");
						r_data = (transData)ois_room.readObject();
						if(r_data instanceof transData) {
								if (r_data.get_protocol()==3 || r_data.get_protocol()==1000){
									if(r_data.get_protocol()==3){
										System.out.println("opponent:row="+r_data.get_row()+",line="+r_data.get_line());
									}else{
										System.out.println("Start!!");
									}
									System.out.println("Your turn!!");
									System.out.println("row?");
									s_data.set_row(scan.nextInt());
									System.out.println("line?");
									s_data.set_line(scan.nextInt());
									oos_room.writeObject(s_data);
									System.out.println("send!!");
								}else{
									System.out.println("no data");
								}
						}
					}
			}catch (Exception e){
				e.printStackTrace();
			}finally{

			}

    }

    static boolean send_login_info(String usr,String pass, ObjectInputStream ois, ObjectOutputStream oos) {
        boolean flag = true;

        // TODO send login info (name, pass) to server
        transData user = new transData(10);
        user.set_login_name_pass(usr,pass);

        try {
            oos.writeObject(user);

            transData answer = (transData)ois.readObject();
            String ans = answer.get_login_answer();
            System.out.println(ans);

            if (ans.equals("login succeed")){
                // todo

                flag = true;
            }else if (ans.equals("login failed : false password")){
                // todo reset pass


                flag = false;
            }else if (ans.equals("login failed : this name does not exist")){
                // todo reset name or name&pass


                flag = false;
            }

        }catch(Exception e){
            //e.printStackTrace();
        }finally {
            return flag;
        }
    }

    static int  choose_room(ObjectOutputStream oos, ObjectInputStream ois){
        int room_port=0, room_num=-1;

        try{
            System.out.println("room_obj start");
            transData room_obj = (transData)ois.readObject();
            System.out.println("room_obj end");

            Map room_info = room_obj.get_room_info();
						System.out.println(room_info);
						Scanner scan = new Scanner(System.in);
						room_num = scan.nextInt();
            // todo chose room having 0or1 player

            transData room_choice = new transData(13);
            room_choice.set_room_num(room_num);
            oos.writeObject(room_choice);

            transData return_port = (transData)ois.readObject();
            room_port = return_port.get_port();
            //System.out.println(return_port.get_port());


        }catch(Exception e){
            //e.printStackTrace();
        }finally {
            return room_port;
        }

    }

}
