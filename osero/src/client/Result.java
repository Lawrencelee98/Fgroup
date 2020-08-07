package client;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
public class Result extends JFrame implements ActionListener{
	JLabel l = new JLabel("");
	JLabel l2 = new JLabel("");
	JButton b= new JButton("New Game");
	Client client = null;
	int result;
	Map<Integer, Integer> room_info;
	List<String> players_info;
	JFrame j;

	String Rate;
	
	public Result(int result, Client client,Map<Integer, Integer> room_info, List<String> players_info, String Rate,JFrame j)  {

		this.client = client;
		this.result = result;
		this.room_info=room_info;
		this.players_info=players_info;
		this.j=j;
		this.Rate=Rate;
		l2.setText("あなたのレート：" + Rate);
		System.out.println("Result : "+ result);
		l.setBounds(70, 10, 100, 30);
		add(l);
		l2.setBounds(40, 40, 100, 30);
		add(l2);
		if(result==0){
			l.setText("黒の勝ち");
		}else if(result ==1){
			l.setText("白の勝ち");
		}else if(result ==2){
			l.setText("引き分け");
		}

		l.setFont(l.getFont().deriveFont(15.0f));
		l2.setFont(l2.getFont().deriveFont(15.0f));

		b.setBounds(30, 80, 120, 30);
		b.addActionListener(this);
		add(b);

		this.setLayout(null);
		this.setSize(200, 200);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		
	}
	public void actionPerformed(ActionEvent e){
		if (e.getSource() == b) {
			/*try {
				 client.s.close();
				 client.setLoginPort(client.FirstConnect(client.ServerAddress, client.first_port));
				 Socket s = new Socket(client.ServerAddress,client.getLoginPort());
				 client.s = s;
				 OutputStream os = s.getOutputStream();
				 ObjectOutputStream oos = new ObjectOutputStream(os);
				 InputStream is = s.getInputStream();
				 ObjectInputStream ois = new ObjectInputStream(is);
				 client.choose_room(oos, ois);
				 System.out.println("open new room");
	 			 this.setVisible(false);
			 } catch (IOException es) {
				 // TODO 自動生成された catch ブロック
				 es.printStackTrace();
			 }

			*/
		   try{
			client.s.close();
			client.s_room.close();
			client.setLoginPort(client.FirstConnect(client.ServerAddress, client.first_port));
			Socket s = new Socket(client.ServerAddress, client.getLoginPort());
			OutputStream os = s.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(os);
			InputStream is = s.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(is);
			client.s = s;
			client.ois = ois;
			client.oos = oos;
			client.send_login_info(client.get_user_name(), client.get_user_pass(), client.ois, client.oos);
			client.choose_room(client.oos, client.ois);
		   }catch(Exception ee){
			   ee.printStackTrace();
		   }

		//	new Room(client, room_info, players_info, client.oos, client.ois);
			/*try {
				System.out.println("roomData");
				transData t=(transData)client.ois.readObject();
				if(t.get_protocol()==12) {
				Map<Integer, Integer> room_info = t.get_room_info();
				System.out.println(room_info);
				List<String> players_info = t.get_players_info();
				System.out.println(players_info);
				client.choose_room(client.oos, client.ois);
				System.out.println("open new room");
				}
			} catch (Exception ee) {
				// ee.printStackTrace();
			}*/

			j.dispose();
			this.dispose();




		}
	}

}
