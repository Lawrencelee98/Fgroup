package client;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import javax.swing.*;
import transData.*;
public class Result extends JFrame {
    JLabel l = new JLabel("");
    Client client = null;
    int result;
    int display;
    JFrame j;
    public Result(int result, Client client,int display,JFrame j) {
        this.client = client;
        this.result = result;
        this.j = j;
        System.out.println("Result : "+ result+ "display:"+display);
        l.setBounds(70, 50, 50, 50);
        add(l);
        if(result==0){
            l.setText("白の勝ち");
        }else if(result ==1){
            l.setText("黒の勝ち");
        }else if(result ==2){
            l.setText("引き分け");
        }
       
       
        this.setLayout(null);
        this.setSize(200, 200);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
    
        try {
            j.dispose();
            // client.s.close();
             client.setLoginPort(client.FirstConnect(client.ServerAddress, client.first_port));
             Socket s = new Socket(client.ServerAddress,client.getLoginPort());
             client.s = s;
             new Login_display("login",client);
             OutputStream os = s.getOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(os);
             InputStream is = s.getInputStream();
             ObjectInputStream ois = new ObjectInputStream(is);
             client.choose_room(oos, ois);
           
         } catch (Exception e) {
             // TODO 自動生成された catch ブロック
             e.printStackTrace();
         }
    

    }
    
}