package server;
import java.io.*;
import java.net.*;
import java.util.*;

import transData.transData;
public class Socket_thread extends Thread{
    private boolean running = true;

    private int port, num, room_num=-1;

    private String username;

    public Socket_thread(int port,int num){
        this.port = port;
        this.num = num;
    }

    public int get_num(){
        return num;
    }
    public int get_port(){
        return port;
    }
    public int get_room_num(){
        return room_num;
    }
    public String get_username(){
        return username;
    }




    public void run_inside(){
        System.out.println("thread [ " + String.valueOf(num) + " ] is running");

        ServerSocket ss = null;
        Socket s = null;

        try{
            ss = new ServerSocket(port);
            s = ss.accept();
            System.out.println("thread [ " + String.valueOf(num) + " ] socket accept");

            //  入出力ストリーム
            ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());
            InputStream is = s.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);

            while(true){
                // todo break if login success
                transData data = (transData) ois.readObject();

                int protocol_1 = data.get_protocol();

                // check data is for login?
                if (protocol_1 == 10) {
                    // login
                    if (Server.login(data, os)) {
                        // login success

//                        System.out.println("thread [ " + String.valueOf(num) + " ] : room info sent");
//                        os.flush();

                        // name set
                        this.username = data.get_login_name();
                        if (num==1){
                            Server.name_p1 = this.username;
                        }else if (num==2){
                            Server.name_p2 = this.username;
                        }else if (num==3){
                            Server.name_p3 = this.username;
                        }else if (num==4){
                            Server.name_p4 = this.username;
                        }else if (num==5){
                            Server.name_p5 = this.username;
                        }else if (num==6){
                            Server.name_p6 = this.username;
                        }else if (num==7){
                            Server.name_p7 = this.username;
                        }else if (num==8){
                            Server.name_p8 = this.username;
                        }


                        break;
                    } else {
                        // login fail

                    }
                } else if (protocol_1 == 20) {
                    // todo register
                    if(Server.register(data)){
                        // register success

                        // name set
                        this.username = data.get_register_name();
                        if (num==1){
                            Server.name_p1 = this.username;
                        }else if (num==2){
                            Server.name_p2 = this.username;
                        }else if (num==3){
                            Server.name_p3 = this.username;
                        }else if (num==4){
                            Server.name_p4 = this.username;
                        }else if (num==5){
                            Server.name_p5 = this.username;
                        }else if (num==6){
                            Server.name_p6 = this.username;
                        }else if (num==7){
                            Server.name_p7 = this.username;
                        }else if (num==8){
                            Server.name_p8 = this.username;
                        }



                        break;
                    }else{
                        // register fail

                    }


                } else if (protocol_1 == 37) {
                    if(Server.change_pass(data)){
                        // name set
                        this.username = data.get_change_name();
                        if (num==1){
                            Server.name_p1 = this.username;
                        }else if (num==2){
                            Server.name_p2 = this.username;
                        }else if (num==3){
                            Server.name_p3 = this.username;
                        }else if (num==4){
                            Server.name_p4 = this.username;
                        }else if (num==5){
                            Server.name_p5 = this.username;
                        }else if (num==6){
                            Server.name_p6 = this.username;
                        }else if (num==7){
                            Server.name_p7 = this.username;
                        }else if (num==8){
                            Server.name_p8 = this.username;
                        }

                        break;
                    }else{

                    }
                }else{
                    // todo no login and no register data

                }


            }
            // send room info
            transData room_obj = new transData(12);
            room_obj.set_room_info(Server.room_info, Server.get_record());
            os.writeObject(room_obj);


//            transData data =(transData)ois.readObject();
//            if(data instanceof transData) {
//                //System.out.println("read:"+ss.isBound());
//                int protocol_1 = data.get_protocol();
//
//                // check data is for login?
//								//M:protocol-10-obj is used for login data
//                if (protocol_1==10){
//                    // login
//                    if (Server.login(data, os)){
//                        // 認証成功
//                        // ルーム情報送信
//												//M:protocol-12-obj is used to exchange room info
//                        transData room_obj = new transData(12);
//                        room_obj.set_room_info(Server.room_info);
//                        os.writeObject(room_obj);
//
//                        System.out.println("thread [ " + String.valueOf(num) + " ] : room info sent");
////                        os.flush();
//
//                        // ユーザネーム設定
//                        this.username = data.get_login_name();
//
//                    }else{
//                        // 失敗
//
//                    }
//                }else{
//                    // ログイン用のデータでは無いエラー
//
//                }
//            }else{
//
//            }


            //選択ルーム受信
            System.out.println("before read room select");
            transData data_room_num = null;
            try {
                data_room_num = (transData)ois.readObject();
            }catch(Exception e3) {
                System.out.println("error4");
            }
            System.out.println("after read room select");
            //M:protocol-13-obj is used to exchange room select data
            if (data_room_num.get_protocol()==13){
                int num = data_room_num.get_room_num();
                if(Server.room_info.get(num)==0){
                    // CPU 対戦 -> M:client starts CPU Battle, server send  battle-start notice to client
                    Server.room_info.put(num,1);
                    this.room_num = num;
                    this.running = false;

                    int port_send=0;
                    if(num==1){
                        port_send = 10011;
                    }else if(num==2){
                        port_send = 10021;
                    }else if(num==3){
                        port_send = 10031;
                    }else if(num==4){
                        port_send = 10041;
                    }

                    if (num==1){
                        Server.name_p1 = null;
                    }else if (num==2){
                        Server.name_p2 = null;
                    }else if (num==3){
                        Server.name_p3 = null;
                    }else if (num==4){
                        Server.name_p4 = null;
                    }else if (num==5){
                        Server.name_p5 = null;
                    }else if (num==6){
                        Server.name_p6 = null;
                    }else if (num==7){
                        Server.name_p7 = null;
                    }else if (num==8){
                        Server.name_p8 = null;
                    }

                    transData port_send_obj = new transData(85);
                    port_send_obj.set_port_send(port_send);
                    os.writeObject(port_send_obj);

                }else if(Server.room_info.get(num)==1){
                    // 対局スタート
                    Server.room_info.put(num,2);
                    this.room_num = num;
                    this.running = false;

                    int port_send=0;
                    if(num==1){
                        port_send = 10012;
                    }else if(num==2){
                        port_send = 10022;
                    }else if(num==3){
                        port_send = 10032;
                    }else if(num==4){
                        port_send = 10042;
                    }

                    if (num==1){
                        Server.name_p1 = null;
                    }else if (num==2){
                        Server.name_p2 = null;
                    }else if (num==3){
                        Server.name_p3 = null;
                    }else if (num==4){
                        Server.name_p4 = null;
                    }else if (num==5){
                        Server.name_p5 = null;
                    }else if (num==6){
                        Server.name_p6 = null;
                    }else if (num==7){
                        Server.name_p7 = null;
                    }else if (num==8){
                        Server.name_p8 = null;
                    }

                    transData port_send_obj = new transData(85);
                    port_send_obj.set_port_send(port_send);
                    os.writeObject(port_send_obj);
//                    os.flush();

                }else{
                    //入れないルームを選択したエラー
                    //

                }
            }


        }catch(Exception e) {
            e.printStackTrace();

        }finally {
            try{
                if (s!=null){
                    s.close();
                }
                if (ss!=null){
                    ss.close();
                }
                System.out.println("thread [ " + String.valueOf(num) + " ] : socket close");
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("thread [ " + String.valueOf(num) + " ] is finished");
            run_inside();
        }
    }


    public void run() {

        run_inside();

    }
}
