import java.awt.*;
import java.awt.List;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class d2 extends JFrame implements ActionListener{
	static final long serialVersionUID = 1;
	JTextArea txt = new JTextArea("�I�Z���̃Q�[�������B123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890");
	JScrollPane scr = new JScrollPane(txt);
	JButton btn = new JButton("�߂�");

	public d2(String title){
		super(title);
		JPanel p = (JPanel)getContentPane();
		p.setLayout(null);

		txt.setEditable(false);
		txt.setLineWrap(true);

		scr.setBounds(15, 10, 200, 150);
		btn.setBounds(90, 180, 60, 20);

		p.add(scr);
		p.add(btn);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(250, 250);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		//onclick
	}

	public static void main(String[] args) {
		new d2("Display-2");
	}
}