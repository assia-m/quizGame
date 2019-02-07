import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class QuizGUI extends JFrame implements ActionListener {
	JLabel name, message, chatLog;
	static JTextField txtMessageArea, displayName;
	JButton joinRoom, send, leave;
	static JTextArea txtStatusArea;
	boolean connected; // TRUE OR FALSE FOR CONNECTION STATUS (WHEN CONNECTED TO CLIENT)
	Client client; // CLIENT OBJECT
	int defaultPort;
	String defaultHost;
	static JTextField textField;
	
	public QuizGUI() {
		super("Game Session"); // NAME OF GUI
		setTitle("Game");
		setDefaultCloseOperation(EXIT_ON_CLOSE); // CLOSES GUI WHEN 'X' IS PRESSED
		setSize(813, 714);
		setLocation(350, 50);
		getContentPane().setLayout(null);
		
		name = new JLabel("Display Name:");
		name.setFont(new Font("Tahoma", Font.PLAIN, 13));
		name.setBounds(10, 101, 93, 24);
		getContentPane().add(name);
		
		message = new JLabel("Answer:");
		message.setFont(new Font("Tahoma", Font.PLAIN, 13));
		message.setBounds(10, 133, 74, 24);
		getContentPane().add(message);
		
		txtMessageArea = new JTextField();
		txtMessageArea.setBounds(74, 136, 154, 78);
		getContentPane().add(txtMessageArea);
		
		txtStatusArea = new JTextArea();
		txtStatusArea.setBounds(238, 37, 531, 627);
		txtStatusArea.setEditable(false); // CHAT LOG NOT EDITABLE BY USER
		getContentPane().add(txtStatusArea);
		
		chatLog = new JLabel("Game Log");
		chatLog.setFont(new Font("Tahoma", Font.PLAIN, 13));
		chatLog.setBounds(457, 11, 74, 24);
		getContentPane().add(chatLog);
		
		joinRoom = new JButton("Play");
		joinRoom.setBounds(10, 225, 89, 23);
		getContentPane().add(joinRoom);
		joinRoom.addActionListener(this); // BUTTON LISTENER
		
		leave = new JButton("Leave");
		leave.setBounds(74, 259, 89, 23);
		getContentPane().add(leave);
		leave.addActionListener(this);
		
		send = new JButton("Submit");
		send.setBounds(125, 225, 89, 23);
		getContentPane().add(send);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(10, 322, 218, 342);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblOnlineUsers = new JLabel("Online Users:");
		lblOnlineUsers.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblOnlineUsers.setBounds(10, 305, 93, 14);
		getContentPane().add(lblOnlineUsers);
		
        setVisible(true);
    }

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == joinRoom) {
			Client.start();
		}
		else if(e.getSource() == send) {
			try {
				Client.out.writeObject(textField.getText());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		else if(e.getSource() == leave) {
			dispose();
		}
	}
}