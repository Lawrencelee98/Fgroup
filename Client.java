package client;
import java.io.*;
import java.net.*;
public class Client{
    int turn=0;
    int your_turn=0;
    String username = "default";
    ObjectOutputStream oos;
    ObjectInputStream ois ;
   public Client(){
    try{ 
        Socket s = new Socket("localhost",10000);
        oos = new ObjectOutputStream(s.getOutputStream());
        ois = new ObjectInputStream(s.getInputStream());
    }catch(Exception e){
        e.printStackTrace();
    }
}
}