
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class Login implements ActionListener {
	static JFrame login;
	JButton btnLogin, btnExit, btnRegister;
	static JTextField username;
	JPasswordField password;
	
	public static void main(String[] args) {
		new Login();                                         
	}   
	
	public Login() {
		login = new JFrame ("Login Screen"); 
		login .setSize(248,215);
		login.setLocation(300,300);
		login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		login.getContentPane().setLayout(null);
		
		JLabel lblwelcome = new JLabel("Welcome");
		lblwelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblwelcome.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblwelcome.setBounds(60, 11, 99, 25);
		login.getContentPane().add(lblwelcome);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUsername.setBounds(21, 47, 68, 25);
		login.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPassword.setBounds(21, 78, 68, 25);
		login.getContentPane().add(lblPassword);
		
		username = new JTextField();
		username.setBounds(110, 51, 86, 20);
		login.getContentPane().add(username);
		username.setColumns(10);
		
		password = new JPasswordField();
		password.setBounds(110, 82, 86, 20);
		login.getContentPane().add(password);
		
		btnRegister = new JButton("Register");
		btnRegister.setBounds(10, 114, 89, 23);
		login.getContentPane().add(btnRegister);
		btnRegister.addActionListener(this);
		
		btnLogin = new JButton("Login");
		btnLogin.setBounds(134, 114, 89, 23);
		login.getContentPane().add(btnLogin);
		btnLogin.addActionListener(this);
		
		btnExit = new JButton("Exit");
		btnExit.setBounds(134, 148, 89, 23);
		login.getContentPane().add(btnExit);
		
		btnExit.addActionListener(this);
		
		login.setVisible(true);  
	}
	
	public void actionPerformed(ActionEvent e) {
		
		// RUNS IF LOGIN BUTTON WAS PRESSED
		if(e.getSource() == btnLogin) { 
			
			// IF THE USERNAME AND PASSWORD FIELDS HAVE BEEN ENETERED
			if(!(username.getText().equals("")) && !(password.getText().equals(""))) 
			{
				
				// GETS USERNAME FIELD STRING
				String uname = username.getText(); 
				
				// GETS PASSWORD FIEL STRING
				String pass = password.getText(); 
							
				// PASSES THE USERNAME AND PASSWORD TO THE METHO 'CHECKLOGIN'
				checkLogin(uname, pass); 			
			}
			else {
				
				// IF USERNAME OR/AND PASSWORD NOT ENTERED
				JOptionPane.showMessageDialog(null, "Please enter all login details", "Error", JOptionPane.ERROR_MESSAGE); 
			}
		}
		
		// RUNS IF REGISTER BUTTON WAS PRESSED
		else if(e.getSource() == btnRegister) { 
			
			// CLOSES GUI
			login.dispose(); 
			
			// OPENS REGISTER GUI
			new Register(); 
		}
		else {
			
			// CLOSES GUI IF BUTTON 'EXIT' IS CLOSED
			login.dispose(); 
		}
	}
	
	// CALLED WHEN BUTTON 'LOGIN' IS PRESSED
	public static void checkLogin(String user, String password) 
	{
		try{
			Class.forName("org.h2.Driver");
			
			// CONNECTS TO DATABASE
			Connection con = DriverManager.getConnection("jdbc:h2:~/test","sa","sa"); 
			Statement state = con.createStatement();
			
			// GETS EVERYTHING FROM THE TABLE IN THE DATABASE
			String query = "SELECT * FROM SAVEDUSERS WHERE USERNAME='" + user + "' and PASSWORD='" + password + "';"; 
			ResultSet rs = state.executeQuery(query);
			String Uname = null;
			String pass = null;
									
			while (rs.next()){
				
				// GETS THE USERNAME COLUMN STRINGS
				Uname = rs.getString("USERNAME"); 
				
				// GETS THE PASSWORD COLUMN VALUES
				pass = rs.getString("PASSWORD"); 
			}
			
			// IF THE CORRECT USERNAME AND PASSWORD HAS BEEN ENETERED
			if(user.equals(Uname) && (password.equals(pass))) 
			{			
				
				// GUI CLOSES
				login.dispose();
				
				// OPTIONS GUI LOADS
				new Options(); 
			}
			else {
				
				// MESSAGE SAYING USERNAME OR PASSWORD IS WRONG AND ALLOWS THEM TO TRY AGAIN
				JOptionPane.showMessageDialog(null, "Username or/and Password not correct. Please try again or register", "Error", JOptionPane.ERROR_MESSAGE); 
			}
			
		// CATCHES ANY ERRORS 
		} catch(ClassNotFoundException | SQLException e)
		{
			e.printStackTrace();
		}
	}
}