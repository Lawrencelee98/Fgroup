package client;
import java.util.*;
import javax.swing.*;
public class Result_driver {
    public static void main(String[] arg){
       // public Result(int result, Client client,Map<Integer, Integer> room_info, List<String> players_info, String Rate, JFrame j)
        int result;
        Client client= new Client();
        Map<Integer, Integer> room_info = new HashMap<Integer, Integer>() {
            private static final long serialVersionUID = 1L;

            {
                put(1, 0);
                put(2, 0);
                put(3, 0);
                put(4, 0);
            }
        };
        List<String> players_info = new ArrayList<String>();
        players_info.add("default");

        String Rate = "Rate";
        JFrame j = new JFrame();

        result = 0;
        client.turn=0;
        System.out.println("白が勝つ場合");
        Result result1 = new Result(result,client,room_info,players_info,Rate,j);
        System.out.println("白が負け場合");
        result=0;
        client.turn=1;
        Result result2 = new Result(result,client,room_info,players_info,Rate,j);
        System.out.println("黒が勝つ場合");
        result = 0;
        client.turn = 0;
        Result result3 = new Result(result,client,room_info,players_info,Rate,j);
        System.out.println("黒が負け場合");
        result =1;
        client.turn=0;
        Result result4 = new Result(result,client,room_info,players_info,Rate,j);

    }
}