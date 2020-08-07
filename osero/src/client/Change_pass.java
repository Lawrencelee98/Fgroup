package client;
import java.awt.*;
import java.awt.List;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import java.net.*;
import transData.*;
public class Change_pass extends JFrame implements ActionListener{
    static final long serialVersionUID = 1;
    JLabel label[] = new JLabel[4];
    JTextArea txt[] = new JTextArea[3];
    JButton btn = new JButton("送信する");
    Client client = null;
    JPanel p = (JPanel)getContentPane();
    public Change_pass(String title,Client client){
        super(title);
        this.client = client;
        p.setLayout(null);
        label[0] = new JLabel("<html>プレイヤ名と質問：【母の旧姓は？】の回答を入力してください</html>");
        label[1] = new JLabel("ユーザネーム");
        label[2] = new JLabel("新しいパスワード");
        label[3] = new JLabel("質問の答え");
        txt[0] = new JTextArea();
        txt[1] = new JTextArea();
        txt[2] = new JTextArea();
        label[0].setBounds(15, 10, 270, 60);
        label[1].setBounds(15, 100, 120, 20);
        label[2].setBounds(15, 125, 120, 20);
        label[3].setBounds(15, 150, 120, 20);
        txt[0].setBounds(155, 100, 120, 20);
        txt[1].setBounds(155, 125, 120, 20);
        txt[2].setBounds(155, 150, 120, 20);
        btn.setBounds(90, 180, 120, 20);
        btn.addActionListener (this);
        p.add(label[0]);
        p.add(label[1]);
        p.add(label[2]);
        p.add(label[3]);
        p.add(txt[0]);
        p.add(txt[1]);
        p.add(txt[2]);
        p.add(btn);
       // p.setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 250);
        setTitle("パスワード変更");
        setVisible(true);
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn) {
            String pass;
            String name;
            String question;
            
            name = txt[0].getText();
            pass = txt[1].getText();
            question = txt[2].getText();
            try{
                boolean flag = client.send_changepass_info(name,pass,question,client.ois,client.oos);
                if(flag){
					System.out.println(" change password success");
					JOptionPane.showConfirmDialog(null, "パスワードの変更に成功しました", "", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                    this.dispose();
                    
                }else{
					System.out.println("change password failed");
					JOptionPane.showConfirmDialog(null, "パスワードの変更に失敗しました", "", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                }
            }catch(Exception ee){
                ee.getStackTrace();
            }
           
           
        }
    }
   // public static void main(String[] args){ 
       // new Change_pass(new Client());
     //}
}