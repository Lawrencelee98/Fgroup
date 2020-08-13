package client;
import java.net.*;
import java.io.*;
import transData.transData;
public class EchoServer{

    public static void main(String[]arg){
        try{
            System.out.println("Echo server prepering ");
            ServerSocket ss = new ServerSocket(10000);
            Socket s = ss.accept();
            System.out.println("Echo server start");
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            transData data_3 = new transData(3);
            System.out.println("send protocol 3");
            oos.writeObject(data_3);
            System.out.println("send protocol 2000");
            transData data_2000 = new transData(2000);
            oos.writeObject(data_2000);
            System.out.println("send protocol 2100");
            transData data_2100 = new transData(2100);
            oos.writeObject(data_2100);
            System.out.println("send protocol 2300");
            transData data_2300 = new transData(2300);
            oos.writeObject(data_2300);
            System.out.println("send protocol 3000");
            transData data_3000 = new transData(3000);
            oos.writeObject(data_3000);
            System.out.println("send protocol 5000");
            transData data_5000 = new transData(5000);
            oos.writeObject(data_5000);
            ss.close();
        }catch(Exception e){
            e.printStackTrace();
        }


    }
}