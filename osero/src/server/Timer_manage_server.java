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
    ObjectOutputStream os=null;
    boolean exit =false;
    //Timer_manage_server(int time, ObjectOutputStream os_1,ObjectOutputStream os_2){
        Timer_manage_server(int time, ObjectOutputStream os){
        this.time = time;
        //this.os_1 = os_1;
        //this.os_2 = os_2;
        this.os = os;
    }


    public void run(){
     try{
        sleep(time*1000L);
        transData time_out = new transData(2000);
       // os_1.writeObject(time_out);
       // os_2.writeObject(time_out);
       os.writeObject(time_out);
        System.out.println("send time out protocol 2000");
        if(isInterrupted()){
            wait();
        }
     }catch(InterruptedException e){
         e.printStackTrace();
         System.out.println("Interrupted");
     } catch(IOException er){
         er.printStackTrace();
     }
    }
}
