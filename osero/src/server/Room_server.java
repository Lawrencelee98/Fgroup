package server;

import java.io.*;
import java.net.*;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

import transData.*;

public class Room_server extends Thread {
    private boolean running = true;
    private int port_1, port_2, room_num;

    private String name_p1, name_p2;

    private int time = 10, time_p1, time_p2;


    public Room_server(int port_1, int port_2, int room_num) {

        this.port_1 = port_1;
        this.port_2 = port_2;
        this.room_num = room_num;
    }

    public void run_inside() {

    }

    public void run() {
        System.out.println("Room [ " + String.valueOf(room_num) + " ] is running");
        try {

            ServerSocket ss_1 = new ServerSocket(port_1);
            ServerSocket ss_2 = new ServerSocket(port_2);
            while (true) {
                Socket s_1 = ss_1.accept();
                System.out.println("Room [ " + String.valueOf(room_num) + " ] : socket [1] accept");

                // set name
                if (room_num == 1) {
                    name_p1 = Server.name_r1_1;
                } else if (room_num == 2) {
                    name_p1 = Server.name_r2_1;
                } else if (room_num == 3) {
                    name_p1 = Server.name_r3_1;
                } else if (room_num == 4) {
                    name_p1 = Server.name_r4_1;
                }

                //  入出力ストリーム 1
                ObjectOutputStream os_1 = new ObjectOutputStream(s_1.getOutputStream());
                //InputStream is_1 = s_1.getInputStream();
                ObjectInputStream ois_1 = new ObjectInputStream(s_1.getInputStream());

                transData time_choice_1 = (transData) ois_1.readObject();
                if (time_choice_1.get_protocol() == 0) {
                    time_p1 = time_choice_1.get_wait_time();
                } else {
                    // false protocol

                }
                // ois_1.reset();


                Socket s_2 = ss_2.accept();
                System.out.println("Room [ " + String.valueOf(room_num) + " ] : socket [2] accept");

                // set name
                if (room_num == 1) {
                    name_p2 = Server.name_r1_2;
                } else if (room_num == 2) {
                    name_p2 = Server.name_r2_2;
                } else if (room_num == 3) {
                    name_p2 = Server.name_r3_2;
                } else if (room_num == 4) {
                    name_p2 = Server.name_r4_2;
                }

                //  入出力ストリーム 2
                ObjectOutputStream os_2 = new ObjectOutputStream(s_2.getOutputStream());
                //InputStream is_2 = s_2.getInputStream();
                ObjectInputStream ois_2 = new ObjectInputStream(s_2.getInputStream());

                transData time_choice_2 = (transData) ois_2.readObject();
                if (time_choice_2.get_protocol() == 0) {
                    time_p2 = time_choice_2.get_wait_time();
                } else {
                    // false protocol

                }
                //ois_2.reset();

                // time random set
                Random random = new Random();
                int randomValue = random.nextInt(2);
//            System.out.println(randomValue);
                switch (randomValue) {
                    case 0:
                        time = time_p1;
                        break;
                    case 1:
                        time = time_p2;
                        break;
                }
                System.out.println("Room [ " + String.valueOf(room_num) + " ] : time after random = " + String.valueOf(time) + " [sec]");

                // 対局スタート通知
                transData battle_start = new transData(80);
                battle_start.set_time(time);
                battle_start.set_room_info(Server.room_info, Server.get_record());
                // battle_start.set_user_name(name_p1, name_p2);
                os_1.writeObject(battle_start);
                os_2.writeObject(battle_start);


                int first_turn = 1;// 1 or 2
                boolean battle_end = true;
                //Timer_manage_server timer_1;
                //Timer_manage_server timer_2;
                Instant instant1;
                Instant instant2;
                long span;
                transData time_out_lose = new transData(2000);
                transData time_out_win = new transData(2100);
                transData isend = null;
                if (first_turn == 1) {
                    // 1 が先手の場合

                    transData start = new transData(1000);
                    os_1.writeObject(start);
                    System.out.println("Room [ " + String.valueOf(room_num) + " ] " + "send --start-- to player1");
                    transData koukou = new transData(1200);
                    os_2.writeObject(koukou);
                    os_2.flush();
                    System.out.println("Room [ " + String.valueOf(room_num) + " ] " + "send --turn-- to player2");

                    while (battle_end) {
                        instant1 = Instant.now();
                        instant2 = instant1.plus(Duration.ofSeconds(time));
                        transData data_1 = (transData) ois_1.readObject();
                        instant1 = Instant.now();
                        span = Duration.between(instant1, instant2).getSeconds();
                        System.out.println("span:" + span);
                        if (span <= 0) {
                            System.out.println("send time out 2000");
                            os_1.writeObject(time_out_lose);
                            os_2.writeObject(time_out_win);
                            isend = (transData) ois_1.readObject();
                            if (isend.get_protocol() == 50) {
                                System.out.println("get battle end protocol 50");
                                battle_end = isend.get_battle_end();
                                if (battle_end) {
                                    break;
                                }
                            }
                        } else {

                            if (data_1.get_protocol() == 3 || data_1.get_protocol() == 3000) {

                                os_2.writeObject(data_1);
                                System.out.println("Room [ " + String.valueOf(room_num) + " ] " + "send Object to 2");
                            } else if (data_1.get_protocol() == 50) {
                                System.out.println("get battle end protocol 50");
                                battle_end = data_1.get_battle_end();
                                if (battle_end) {
                                    break;
                                }
                                // Server.update_record();
                            }

                        }

                        if (!battle_end) {
                            break;
                        }

                        //timer_2 = new Timer_manage_server(time, os_2,os_1);
                        instant1 = Instant.now();
                        instant2 = instant1.plus(Duration.ofSeconds(time));
                        transData data_2 = (transData) ois_2.readObject();
                        instant1 = Instant.now();
                        span = Duration.between(instant1, instant2).getSeconds();
                        System.out.println("span:" + span);
                        if (span <= 0) {
                            System.out.println("send time out 2000");
                            os_2.writeObject(time_out_lose);
                            os_1.writeObject(time_out_win);
                            isend = (transData) ois_2.readObject();
                            if (isend.get_protocol() == 50) {
                                System.out.println("get battle end protocol 50");
                                battle_end = isend.get_battle_end();
                                if (battle_end) {
                                    break;
                                }
                            }
                        } else {

                            if (data_2.get_protocol() == 3 || data_1.get_protocol() == 3000) {

                                os_1.writeObject(data_2);
                                System.out.println("Room [ " + String.valueOf(room_num) + " ] " + "send Object to 1");
                            } else if (data_2.get_protocol() == 50) {
                                System.out.println("get battle end protocol 50");
                                battle_end = data_2.get_battle_end();
                                if (battle_end) {
                                    break;
                                }
                                //  Server.update_record();
                            }
                        }

                    }
                } else {
                    // 2 が先手の場合

                    while (battle_end) {

                        instant1 = Instant.now();
                        instant2 = instant1.plus(Duration.ofSeconds(time));
                        transData data_2 = (transData) ois_2.readObject();
                        instant1 = Instant.now();
                        span = Duration.between(instant1, instant2).getSeconds();
                        System.out.println("span:" + span);
                        if (span <= 0) {
                            System.out.println("send time out 2000");
                            os_2.writeObject(time_out_lose);
                            os_1.writeObject(time_out_win);
                            isend = (transData) ois_2.readObject();
                            if (isend.get_protocol() == 50) {
                                System.out.println("get battle end protocol 50");
                                battle_end = isend.get_battle_end();
                                if (battle_end) {
                                    break;
                                }
                            }

                        } else {

                            if (data_2.get_protocol() == 3 || data_2.get_protocol() == 3000) {
                                os_1.writeObject(data_2);
                                System.out.println("Room [ " + String.valueOf(room_num) + " ] " + "send Object to 1");
                            } else if (data_2.get_protocol() == 50) {
                                System.out.println("get battle end protocol 50");
                                battle_end = data_2.get_battle_end();
                                if (battle_end) {
                                    break;
                                }
                                // Server.update_record();
                            }
                        }
                        if (!battle_end) {
                            break;
                        }
                        instant1 = Instant.now();
                        instant2 = instant1.plus(Duration.ofSeconds(time));
                        transData data_1 = (transData) ois_1.readObject();
                        instant1 = Instant.now();
                        span = Duration.between(instant1, instant2).getSeconds();
                        System.out.println("span:" + span);
                        if (span <= 0) {
                            System.out.println("send time out 2000");
                            os_1.writeObject(time_out_lose);
                            os_2.writeObject(time_out_win);
                            isend = (transData) ois_1.readObject();
                            if (isend.get_protocol() == 50) {
                                System.out.println("get battle end protocol 50");
                                battle_end = isend.get_battle_end();
                                if (battle_end) {
                                    break;
                                }
                            }
                        } else {

                            if (data_1.get_protocol() == 3 || data_1.get_protocol() == 3000) {
                                os_1.writeObject(data_1);
                                System.out.println("Room [ " + String.valueOf(room_num) + " ] " + "send Object to 2");
                            } else if (data_1.get_protocol() == 50) {
                                System.out.println("get battle end protocol 50");
                                battle_end = data_1.get_battle_end();
                                if (battle_end) {
                                    break;
                                }
                                // Server.update_record();
                            }

                        }
                    }
                }

                System.out.println("out of while loop");
                //対戦記録更新
                try {
                    transData end_result = new transData(2200);
                    transData end_info = (transData) ois_1.readObject();

                    System.out.println("get end_info protocol 36");
                    transData temp_end_info = new transData(36);
                    if (end_info.get_protocol() == 36) {
                        if (end_info.get_endinfo_draw()) {
                            temp_end_info.set_draw_result(name_p1, name_p2);

                        } else if (end_info.get_endinfo_win()) {
                            temp_end_info.set_no_draw_result(name_p1, name_p2);

                        } else if (end_info.get_endinfo_lose()) {
                            temp_end_info.set_no_draw_result(name_p2, name_p1);

                        }
                        os_1.writeObject(end_result);
                        os_2.writeObject(end_result);
                    }
                    Server.update_record(temp_end_info);
                    System.out.println("Server updated record");
                } catch (Exception eee) {
                    eee.printStackTrace();
                }

                // update room info
                if (room_num == 1) {
                    Server.name_r1_1 = null;
                    Server.name_r1_2 = null;
                } else if (room_num == 2) {
                    Server.name_r2_1 = null;
                    Server.name_r2_2 = null;
                } else if (room_num == 3) {
                    Server.name_r3_1 = null;
                    Server.name_r3_2 = null;
                } else if (room_num == 4) {
                    Server.name_r4_1 = null;
                    Server.name_r4_2 = null;
                }
                Server.room_info.put(room_num, 0);
                transData room_info = new transData(14);
                room_info.set_room_info(Server.room_info, Server.get_record());
                //os_1.reset();
                //os_2.reset();
                os_1.writeObject(room_info);
                os_2.writeObject(room_info);
                System.out.println("send room_info");
                System.out.println(room_info.get_room_info());

                // ois_1.reset();
                // ois_2.reset();
            }//while loop
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //run();

        }
    }
}
