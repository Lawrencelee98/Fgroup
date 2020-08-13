package client;
import java.util.*;
import java.io.*;

public class Oserov4_driver {
    public static void main(String arg[]){
     try{
        Ban ban = new Ban();
        System.out.println("初期化の状態");
        ban.initMap();
        System.out.println("黒の盤面情報");
        ban.printMap(0);
        System.out.println("白の盤面情報");
        ban.printMap(1);

        while(true){
            BufferedReader r = new BufferedReader(new InputStreamReader(System.in), 1);
            System.out.println("石を置く行を入力");
            int x = Integer.parseInt(r.readLine());
            System.out.println("石を置く列を入力");
            int y = Integer.parseInt(r.readLine());
            ban.updateMap(x, y, 0);
            System.out.println("更新した");
            System.out.println("黒の盤面情報");
            ban.printMap(0);
            System.out.println("白の盤面情報");
            ban.printMap(1);
            System.out.println("黒の数は"+ban.countNumber(0));
            System.out.println("白の数は"+ban.countNumber(1));
            System.out.println("ゲームの結果は"+ban.isGameFinish());
        }
     }catch(Exception e){
         e.printStackTrace();
     }
    }

}