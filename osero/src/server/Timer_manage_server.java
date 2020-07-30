package server;
import java.io.*;
import java.net.*;
import java.util.*;
import transData.*;

public class Timer_manage_server extends Thread{

    int time;
    int count;
    ObjectOutputStream os_1 = null;
    ObjectOutputStream os_2 = null;
    boolean exit =false;
    Timer_manage_server(int time, ObjectOutputStream os_1, ObjectOutputStream os_2){
        this.time = time;
        this.os_1 = os_1;
        this.os_2 = os_2;
    }


    public void run(){
      
        System.out.println("timer start");
        Timer timer = new Timer();
        timer.schedule(new TimerTask(){
    @Override
    public void run(){
        count = count -1;
        System.out.println(count);
        if(count ==0){
            try {
                System.out.println("send protocol 2000");
                transData time_over = new transData(2000);
                os_1.writeObject(time_over);
                os_2.writeObject(time_over);
                cancel();
            } catch (Exception e) {
                e.printStackTrace();
                count = time;
            }
        }else{}

    }

        }, 0, 1000);
        if(isInterrupted()){
            count = time;
        }
    
        System.out.println("timer finish");
    }
}
