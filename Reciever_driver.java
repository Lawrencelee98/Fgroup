package client;
import java.io.*;
import java.net.*;
import transData.*;
import javax.swing.JFrame;
import client.Oserov4.*;
import java.util.*;
public class Reciever_driver {
    
    public static void main(String[] arg){
        try{
            Client client = new Client();
            Socket s = new Socket("localhost",10000);
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            List<String> players_info = new ArrayList<String>();
            players_info.add("default");
            //public Reciever(Client client, Ban map, ObjectInputStream ois,transData end,JFrame j,boolean pass,int rate,int result) 
            Oserov4 osero = new Oserov4(client,client.oos,client.ois,30,players_info);
            Ban map = osero.new Ban();
            ObjectInputStream ois = client.ois;
            transData end = new transData(36);
            JFrame j = new JFrame();
            boolean pass = true;
            int rate =0;
            int result =0;
            Reciever rec = new Reciever(client,map,ois,end,j,pass,rate,result);
            rec.start();
     
        }catch(Exception e){

        }
    } 
}
