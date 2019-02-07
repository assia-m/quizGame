
import java.io.*;
import java.net.*;
import java.util.*;

public class Client {	
	static Socket sock; // CREATES A SOCKET
	static String username; // USERNAME OF CLIENT
	static String numPlayers; 
	static ObjectInputStream in; 
	static ObjectOutputStream out; 
	
	public static void start()
    {
      try
      {  
    	  // GETS USERNAME FROM LOGIN PAGE
    	  username = Login.username.getText(); 
    	  
    	  // CREATES A NEW SOCKET WITH HOST AND PORT VALUES FROM THE METHODS 'getHost()' AND 'getPort()'
          Socket sock = new Socket(getHost(), getPort()); 
          
          // SETS A TIMER FOR CONNECTION TO BE ESTABLISHED BY, IF THE TIMER RUNS OUT, CLIENT IS NOT CONNECTED AND ERROR IS GIVEN
          sock.setSoTimeout(10000); 
          
          // ALERTS THE CLIENT OF THE SUCCESSFULL CONNECTION
          System.out.println("Connection established"); 
          String data = "";
          
          // LISTENING OBJECT FOR INCOMING MESSAGES FROM SERVER
          in  = new ObjectInputStream(sock.getInputStream()); 
          
          // SENDS MESSAGES TO THE SERVER
          out = new ObjectOutputStream(sock.getOutputStream()); 
          
          // SENDS THE USERNAME VALUE TO THE SERVER
          out.writeObject(username); 
          
          // SCANNER OBJECT CREATED
          Scanner sc = new Scanner(System.in); 
          
          // VARIABLE, CALLED DATA, READS MESSAGE COMING IN FROM SERVER AND WHILE THE MESSAGE IS NOT NULL, THIS LOOP RUNS AND THE MESSAGE FROM THE SERVER IS PASSED TO THE DATA VARIABLE
          while((data = (String) in.readObject()) != null) 
          {
        	  // PRINTS THE MESSAGE FROM THE SERVER VIA THE DATA VARIABLE
    		  System.out.println(data); 
    		  
    		  // IF THE VALUE OF THE VARIABLE DATA HAS A SPECIFIC WORD, IN THIS CASE 'PLAYERS', THIS LOOP WILL BE CALLED
    		  if(data.contains("players")) {
    			  // THE USER INPUT WILL BNE SAVED IN THE 'NUMPLAYERS' VARIABLE
    			  numPlayers = sc.next();
    			  
    			  // THE 'NUMPLAYERS' VARIABLE WILL THEN BE SENT TO THE SERVER TO NOTIFY THE SERVER OF HOW MANY PLAYERS IN THE GAME
    			  out.writeObject(numPlayers);
    			  
    			  // IF THE VALUE OF 'NUMPLAYERS' IS HIGHER THAN ONE, THIS LOOP WILL LOAD
    			  if(Integer.parseInt(numPlayers) > 1) {
    				  
    				  // TIMER COMES UP BLANK
    				  new QuizTimer(); 
    	          }
    		  }
    		  
    		  // IF THE MESSAGE FORM THE SERVER CONTAINS THE WORDS 'FINAL SCORE' OR 'WAITING' THIS LOOP WILL LOAD
    		  else if(data.contains("Final Score:") || data.contains("waiting")) {
    			  
    		  }
    		  
    		  // IF THE MESSAGE FROM THE SERVER DOES NOT INCLUDE 'PLAYERS', 'FINAL SCORE' OR 'WAITING', THIS LOOP WILL LOAD
    		  else {
    			  
    			  // THIS MESSAGE WILL PRINT TO THE SCREEN TO INFORM THE CLIENT THAT THEY ARE REQUIRED TO ENTER AN ANSWER
    			  System.out.print("Your Answer: ");
        		  
    			  // THE USERS INPUT IS THEN SAVED TO THE VARIABLE 'STRCLI'
    			  String strCli = sc.next();
    			  
    			  // THE STRING VARIABLE IS THEN SENT TO THE SERVER VIA THE OUTPUT OBJECT
        		  out.writeObject(strCli);
    		  }
          }
          
          // SCANNER IS CLOSED
          sc.close();
          
          // INPUT OBJECT IS CLOSED
          in.close();
          
          // OUTPUT OBJECT IS CLOSED
          out.close();
          
          // SOCKET IS CLOSED
          sock.close();
      }
      
      // CATCHES ANY POSSIBLE ERRORS INCLUDING POSSIBLE ISSUES FOR CONNECTION TO THE SERVER, WRITING MESSAGES TO THE SERVER, READING MESSAGES FROM THE SERVER
      catch(IOException e)
      {
    	  // PRINTS THE ERROR TO ALERT THE USER
    	  System.out.println(e);
      } catch (ClassNotFoundException e) {
    	  System.out.println(e);
	}
  }
		
	// METHOD TO GET HOST VALUE FROM A PROP FILE ON THE SYSTEM 
	public static String getHost() {
		String host = "";
		try {
			
			// PROP VARIABLE
			Properties prop = new Properties(); 
			
			// GETS 'CLIENT' PROP FILE
			FileInputStream in = new FileInputStream("Client.prop"); 
			
			// LOADS THE PROP FILE
			prop.load(in); 
			
			// CLOSES THE FILEINPUTSTREAM
			in.close(); 
			
			// GETS THE VARIABLE 'HOST' FROM THE PROP FILE AND PUSHES IT TO THE LOCAL HOST VARIABLE
			host = prop.getProperty("Host"); 
			
		// CATCHES ANY POSSIBLE ERRORS INCLUDING THE ERROR OF NOT BEING ABLE TO FIND THE FILE
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// RETURNS THE VARIABLE HOST 
		return host;
	}
	
	public static int getPort() {
		int port = 0;
		try {
			
			// PROP VARIABLE
			Properties prop = new Properties(); 
			
			// READS PROP FILE CALLED 'CLIENT'
			FileInputStream in = new FileInputStream("Client.prop"); 
			
			// LOADS THE DATA FROM THE FILE
			prop.load(in); 
			
			// CLOSES THE FILEINPUTSTREAM
			in.close(); 
			
			// GLOABL PORT VARIABLE (AT TOP OF CLASS) GIVEN THE PORT VALUE FROM THE FILE
			port = Integer.parseInt(prop.getProperty("Port")); 
			
		// CATCHES ANY POSSIBLE ERRORS INCLUDING THE ERROR OF NOT BEING ABLE TO FIND THE FILE
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// RETURNS THE VALUE OF PORT
		return port; 
	}
	
//	public Client() {
//		JFrame client = new JFrame();
//		client.setTitle("Quiz");
//		client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    	client.setLocation(300, 300);
//    	client.setSize(596, 400);
//    	client.getContentPane().setLayout(null);
//    	
//    	textField = new JTextField();
//    	textField.setEditable(false);
//    	textField.setBounds(174, 11, 386, 202);
//    	client.getContentPane().add(textField);
//    	textField.setColumns(10);
//    	
//    	textField_1 = new JTextField();
//    	textField_1.setBounds(253, 224, 150, 63);
//    	client.getContentPane().add(textField_1);
//    	textField_1.setColumns(10);
//    	
//    	JLabel lblAnswer = new JLabel("Answer: ");
//    	lblAnswer.setFont(new Font("Tahoma", Font.BOLD, 13));
//    	lblAnswer.setBounds(184, 224, 59, 26);
//    	client.getContentPane().add(lblAnswer);
//    	
//    	btnSubmit = new JButton("Submit");
//    	btnSubmit.setBounds(435, 244, 89, 23);
//    	btnSubmit.setEnabled(false);
//    	client.getContentPane().add(btnSubmit);
//    	btnSubmit.addActionListener(this);
//    	
//    	btnStart = new JButton("Start");
//    	btnStart.setBounds(34, 83, 89, 23);
//    	client.getContentPane().add(btnStart);
//    	btnStart.addActionListener(this);
//    	
//    	textField_2 = new JTextField();
//    	textField_2.setText(Login.username.getText());
//    	textField_2.setEditable(false);
//    	textField_2.setBounds(23, 37, 125, 35);
//    	client.getContentPane().add(textField_2);
//    	textField_2.setColumns(10);
//    	
//    	JLabel lblUsername = new JLabel("Username:");
//    	lblUsername.setFont(new Font("Tahoma", Font.BOLD, 13));
//    	lblUsername.setBounds(23, 11, 125, 26);
//    	client.getContentPane().add(lblUsername);
//    	
//    	textField_3 = new JTextField();
//    	textField_3.setText(Server.myMap.toString());
//    	textField_3.setEditable(false);
//    	textField_3.setBounds(10, 143, 150, 209);
//    	client.getContentPane().add(textField_3);
//    	textField_3.setColumns(10);
//    	
//    	JLabel lblLeaderboard = new JLabel("Leaderboard");
//    	lblLeaderboard.setFont(new Font("Tahoma", Font.BOLD, 13));
//    	lblLeaderboard.setBounds(10, 121, 95, 26);
//    	client.getContentPane().add(lblLeaderboard);
//    	
//    	client.setVisible(true);
//	}
//	
//	public void actionPerformed(ActionEvent e) { // HAS ALL BUTTON EVENTS (EXCEPT 'HELP' BUTTON WHICH IS ABOVE)
//		Object o = e.getSource(); // OBJECTS LISTENS FOR WHICH BUTTON IS PRESSED
//		try {
//			if(o == btnStart) {
//				sock = new Socket(getHost(), getPort());
//	            sock.setSoTimeout(10000);
//	            System.out.println("Connection established");
//				
//				btnStart.setEnabled(false); // BUTTON NO LONGER ACTIVE/CLICKABLE
//				btnSubmit.setEnabled(true);
//				
//				ObjectInputStream in  = new ObjectInputStream(sock.getInputStream());
//	
//	            String data;
//	            
//	            while((data = (String) in.readObject()) != null)
//	            {
//	            	textField.setText(data);
////	                String strCli = textField_1.getText();
////	                pwrite.println(strCli);
//
//	            }
//			}
//			else if(o == btnSubmit) { // IF BUTTON PRESSED IS 'SEND'				
//    				if(textField_1.getText().isEmpty()) { // IF MESSAGE FIELD IS BLANK THIS RUNS
//    					JOptionPane.showMessageDialog(null, "No message to send", "Error", JOptionPane.ERROR_MESSAGE); // POP UP SAYING NO MESSAGE TO SEND
//    				}
//    				else { // IF THERE IS A MESSAGE TO SEND
//    					ObjectOutputStream out = new ObjectOutputStream(sock.getOutputStream());
//    					out.writeObject(textField_1.getText()); // SENDS MESSAGE TO CLIENT SIDE METHOD 'SENDMESSAGE'				
//    					textField_1.setText(""); // MESSAGE FIELD IS SET TO BLANK
//    					return;
//    				} 
//    			}
//		} 
//		catch (IOException | ClassNotFoundException e1) {
//			e1.printStackTrace();
//		}
//	}
}