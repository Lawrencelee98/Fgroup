import java.awt.*;
import java.awt.List;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class d3 extends JFrame implements ActionListener{
	static final long serialVersionUID = 1;
	JLabel label[] = new JLabel[3];
	JTextArea txt[] = new JTextArea[2];
	JButton btn = new JButton("���M����");

	public d3(String title){
		super(title);
		JPanel p = (JPanel)getContentPane();
		p.setLayout(null);

		label[0] = new JLabel("<html>�v���C������[�閧�̎���]�̉񓚂���͂��Ă�������</html>");
		label[1] = new JLabel("�v���C����");
		label[2] = new JLabel("����̉�");
		txt[0] = new JTextArea();
		txt[1] = new JTextArea();

		label[0].setBounds(15, 10, 200, 60);
		label[1].setBounds(15, 100, 75, 20);
		label[2].setBounds(15, 130, 75, 20);
		txt[0].setBounds(95, 100, 120, 20);
		txt[1].setBounds(95, 130, 120, 20);
		btn.setBounds(40, 180, 150, 20);

		p.add(label[0]);
		p.add(label[1]);
		p.add(label[2]);
		p.add(txt[0]);
		p.add(txt[1]);
		p.add(btn);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(250, 250);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		//onclick
	}

	public static void main(String[] args) {
		new d3("Display-3");
	}
}