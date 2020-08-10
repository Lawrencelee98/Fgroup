package driver;
import java.util.*;
import transData.transData;

public class transDataDriver{
	public static void main(String[] args) throws Exception{
		System.out.println("new transData(1000)");
		transData td = new transData(1000);
		System.out.println("get_protocol : " + td.get_protocol());

		System.out.println("set_protocol 1200");
		td.set_protocol(1200);
		System.out.println("get_protocol : " + td.get_protocol());

		System.out.println("set_protocol 2000");
		td.set_protocol(2000);
		System.out.println("get_protocol : " + td.get_protocol());

		System.out.println("set_protocol 2100");
		td.set_protocol(2100);
		System.out.println("get_protocol : " + td.get_protocol());
		
		System.out.println("set_protocol 2200");
		td.set_protocol(2200);
		System.out.println("get_protocol : " + td.get_protocol());

		System.out.println("set_protocol 3000");
		td.set_protocol(3000);
		System.out.println("get_protocol : " + td.get_protocol());

		System.out.println("new transData(2)");
		transData td2 = new transData(2);
		System.out.println("get_protocol : " + td2.get_protocol());
		System.out.println("set_time_choise 30");
		td2.set_time_choice(30);
		System.out.println("get_time_choise : " + td2.get_time_choice());

		System.out.println("new transData(3)");
		transData td3 = new transData(3);
		System.out.println("get_protocol : " + td3.get_protocol());
		System.out.println("set_row 1, set_line 2");
		td3.set_row(1);
		td3.set_line(2);
		System.out.println("get_row : " + td3.get_row());
		System.out.println("get_line : " + td3.get_line());
		
		System.out.println("new transData(50)");
		transData td50 = new transData(50);
		System.out.println("get_protocol : " + td50.get_protocol());
		System.out.println("set_battle_end false");
		td50.set_battle_end(false);
		System.out.println("get_battle_end : " + td50.get_battle_end());

		System.out.println("new transData(12)");
		transData td12 = new transData(12);
		System.out.println("get_protocol : " + td12.get_protocol());
		Map<Integer,Integer> room_info = new HashMap<Integer, Integer>();
		room_info.put(1, 0);
		room_info.put(2, 0);
		room_info.put(3, 0);
		room_info.put(4, 0);
		List<String> players_info = new ArrayList<String>();
		players_info.add("room 1-1 : empty");
		players_info.add("room 1-2 : empty");
		players_info.add("room 2-1 : empty");
		players_info.add("room 2-2 : empty");
		players_info.add("room 3-1 : empty");
		players_info.add("room 3-2 : empty");
		players_info.add("room 4-1 : empty");
		players_info.add("room 4-2 : empty");
		System.out.println("room_info : " + room_info.toString());
		System.out.println("players_info : " + players_info.toString());
		System.out.println("set_room_info room_info,players_info");
		td12.set_room_info(room_info, players_info);
		System.out.println("get_room_info : " + td12.get_room_info().toString());
		System.out.println("get_players_info : " + td12.get_players_info().toString());

		System.out.println("new transData(13)");
		transData td13 = new transData(13);
		System.out.println("get_protocol : " + td13.get_protocol());
		System.out.println("set_room_num 2");
		td13.set_room_num(2);
		System.out.println("get_room_num : " + td13.get_room_num());

		System.out.println("new transData(85)");
		transData td85 = new transData(85);
		System.out.println("get_protocol : " + td85.get_protocol());
		System.out.println("set_port_send 10011");
		td85.set_port_send(10011);
		System.out.println("get_port : " + td85.get_port());

		System.out.println("new transData(80)");
		transData td80 = new transData(80);
		System.out.println("get_protocol : " + td80.get_protocol());
		System.out.println("set_time 15");
		td80.set_time(15);
		System.out.println("get_time : " + td80.get_time());

		System.out.println("new transData(10)");
		transData td10 = new transData(10);
		System.out.println("get_protocol : " + td10.get_protocol());
		System.out.println("set_login_name_pass name:usr1,pass:pass_1");
		td10.set_login_name_pass("usr1", "pass_1");
		System.out.println("get_login_name : " + td10.get_login_name());
		System.out.println("get_login_pass : " + td10.get_login_pass());

		System.out.println("new transData(11)");
		transData td11 = new transData(11);
		System.out.println("get_protocol : " + td11.get_protocol());
		System.out.println("set_login_answer \"login succeed\"");
		td11.set_login_answer("login succeed");
		System.out.println("get_login_answer : " + td11.get_login_answer());

		System.out.println("new transData(20)");
		transData td20 = new transData(20);
		System.out.println("get_protocol : " + td20.get_protocol());
		System.out.println("set_register_name_pass_question name:test_uer,pass:test_pass,question:test_q");
		td20.set_register_name_pass_question("test_uer", "test_pass", "test_q");
		System.out.println("get_register_name : " + td20.get_register_name());
		System.out.println("get_register_pass : " + td20.get_register_pass());
		System.out.println("get_register_question : " + td20.get_register_secret_question());

		System.out.println("new transData(36)");
		transData td36 = new transData(36);
		System.out.println("get_protocol : " + td36.get_protocol());
		System.out.println("set_draw_result winner:usr1,loser:usr2");
		td36.set_draw_result("usr1", "usr2");
		System.out.println("get_winner : " + td36.get_winner());
		System.out.println("get_loser : " + td36.get_loser());
		System.out.println("set_no_draw_result winner:usr1,loser:usr2");
		td36.set_no_draw_result("usr1", "usr2");
		System.out.println("get_winner : " + td36.get_winner());
		System.out.println("get_loser : " + td36.get_loser());
		System.out.println("set_endinfo_draw");
		td36.set_endinfo_draw();
		System.out.println("get_endinfo_draw : " + td36.get_endinfo_draw());
		System.out.println("set_endinfo_win");
		td36.set_endinfo_win();
		System.out.println("get_endinfo_win : " + td36.get_endinfo_win());
		System.out.println("set_endinfo_lose");
		td36.set_endinfo_lose();
		System.out.println("get_endinfo_lose : " + td36.get_endinfo_lose());
		System.out.println("set_end_result 0");
		td36.set_end_result(0);
		System.out.println("get_end_result : " + td36.get_end_result());
		System.out.println("get_pass_count : " + td36.get_pass_count());
		System.out.println("add_pass_count");
		td36.add_pass_count();
		System.out.println("get_pass_count : " + td36.get_pass_count());
		System.out.println("reset_pass_count");
		td36.reset_pass_count();
		System.out.println("get_pass_count : " + td36.get_pass_count());

		System.out.println("new transData(37)");
		transData td37 = new transData(37);
		System.out.println("get_protocol : " + td37.get_protocol());
		System.out.println("set_change_name_pass_question name:test_uer,pass:test_pass,question:test_q");
		td37.set_change_name_pass_question("test_uer", "test_pass", "test_q");
		System.out.println("get_change_name : " + td37.get_change_name());
		System.out.println("get_change_pass : " + td37.get_change_pass());
		System.out.println("get_change_question : " + td37.get_change_secret_question());

		System.out.println("new transData(0)");
		transData td0 = new transData(0);
		System.out.println("get_protocol : " + td0.get_protocol());
		System.out.println("set_wait_time 15");
		td0.set_wait_time(15);
		System.out.println("get_wait_time : " + td0.get_wait_time());
		System.out.println("set_result_time 15");
		td0.set_result_time(15);
		System.out.println("get_result_time : " + td0.get_result_time());
	}
}