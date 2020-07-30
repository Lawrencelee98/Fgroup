package server;
import java.io.*;
import java.net.*;
import java.util.*;
import transData.*;

public class Timer_manage_server extends Thread{

    int time;
    int count;
    ObjectOutputStream os = null;
    boolean exit =false;
    Timer_manage_server(int time, ObjectOutputStream os){
        this.time = time;
        this.os = os;
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
                os.writeObject(time_over);
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
