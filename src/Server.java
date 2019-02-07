// NEED A LEADERBOARD FOR FRIENDLY AND TOURNAMENT MODE
	// NEED TO TEST WITH MORE THAN 1 CLIENT
		/// NEED TO CREATE THREADS TO MAKE USERS WAIT FOR EACH OTHER IF CLIENT IS MORE THAN 1
			// FOR FRIENDLY AND TOURNAMENT MODE

// NEED TO GET USERNAME FROM CLIENT SIDE
	// DONE BUT NEED TO TEST WITH CLIENT/SERVER ON DIFFERENT PC

// NUMUSERS NEEDS TO BE INPUT FROM CLIENT
	// PRACTICE MODE IS 1
	// FRIENDLY AND TOURNAMENT
		// NEED TO ASK USER HOW MANY PLAYERS 
			// THE M PLAYERS REQUIREMENT ON CW SPEC
				// DONE BUT NEED TO MAKE THREAD WAIT FOR THE AMOUNT OF PLAYERS STATED

import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;

public class Server
{
	
	// SERVER SOCKET CREATED 
	static ServerSocket server;
	
	// SOCKET CREATED
	static Socket client;
	
	// VARIABLE TO KEEP TRACK OF SCORE
	static int score = 0;
	
	// OUTPUT OBJECT CREATED TO SEND MESSAGES
	static ObjectOutputStream out;
	
	// INPUT OBJECT CREATED TO RECIEVE MESSAGES
	static ObjectInputStream in;
	
	// ARRAY TO KEEP LIST OF ONLINE USERS
	static String[] onlineUsers;
	
	// ARRAY TO STORES THREADS CREATED FOR EACH USER
	static ArrayList<Thread> onlineClients = new ArrayList<Thread>();

	static String message = "";
	static String username = "";
	static int numPlayers = 0;

    public static void main(String args[])
    {
        try
        {
        	// SERVER SOCKET CREATED WITH VARIABLE FROM 'getPort()' METHOD
            server = new ServerSocket(getPort());
            
            // MESSAGE PRINTED TO THE SCREEN TO INFORM USER OF SERVER RUNNING SUCCESSFULLY
            System.out.println("Server waiting for clients on port " + getPort());
            
            // SOCKET CREATED TO LISTEN TO CLIENT SIDE
            client = server.accept();
            
            // MESSAGE PRINTED TO ALERT USER OF SUCCESSFUL CONNECTION
            System.out.println("Connection Established");
            
            // OUTPUT OBJECT CREATED TO SEND MESSAGES
            out = new ObjectOutputStream(client.getOutputStream());
            
            // INPUT OBJECT CREATED TO READ MESSAGES
            in = new ObjectInputStream(client.getInputStream());
            
            // USERNAME VARIABLE IS PASSED VALUE OF THE MESSAGE RECIEVED FROM CLIENT SIDE
            username = (String) in.readObject();
            
            // ARRAY 'ONLINE USERS' ADDS THE USERNAME TO THE ARRAY
            onlineUsers = new String[]{username};
            
//            out.writeObject("How many players?");
            
//            String players = (String) in.readObject();
//            numPlayers = Integer.parseInt(players);
            
            // NUMBER OF PLAYERS IS ONE
            numPlayers = 1;
            
            // LOOPS FOR HOW EVER MANY PLAYERS ARE ENTERED
            for(int i = 0; i < numPlayers; i++) {
            	
            	// CREATED A THREAD FOR EACH PLAYER
            	Thread T1 = new Thread(new ClientThread(i));
            	
            	// STARTS THE THREAD
            	T1.start();
            	
            	// ONLINE CLIENTS ARRAY SAVES THE THREAD
            	onlineClients.add(T1);
            	
            	try {
            		
            		// ALL THREADS ARE JOINED 
					onlineClients.get(i).join();
					
				// CATCHES ANY ERRORS
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            }
            
            // IF THE SIZE OF THE ONLINE CLIENTS ARRAY IS THE SAME AS THE NUMPLAYERS VARIABLE VALUE, THIS LOOP WILL LOAD
            if(numPlayers == onlineClients.size()) {
            	String question = "";
        	    String choice1 = "";
        	    String choice2 = "";
        	    String choice3 = "";
        	    String choice4 = "";
        	    String choice5 = "";
        	    String answer = "";
        	    String data = "";
        	    String line;
        	    
        	    // VARIABLE TO STORE QUESTION NUMBER 
        	    int i = 0;

        	    //  BUFFERED READER VARIABLE CREATED TO LOAD THE FILE 'questions.txt' FROM THE LOCAL MACHINE
    			BufferedReader reader = new BufferedReader(new FileReader("questions.txt")); 
    			
    			// QUIZ CLASS OBJECT CREATED TO LOAD QUESTIONS WITH THE CORRECT TEMPLATE
    			QuizClass run = new QuizClass(i, question, choice1, choice2, choice3, choice4, choice5, answer);

    			// RUNS WHILE THERE IS STILL FILE CONTENTS TO READ
    			while((line = reader.readLine()) != null){
    			    
    				// FINDS THE QUESTION
    			    if(line.contains("?")){
    			        run.question = line + "\n";
    			    }

    			    // FINDS CHOICE '1'
    			    if(line.contains("1)")){
    			        run.choice1 = line + "\n";
    			    }

    			    // FINDS CHOICE '2'
    			    if(line.contains("2)")){
    			        run.choice2 = line + "\n";
    			    }

    			    // FINDS CHOICE '3'
    			    if(line.contains("3)")){
    			        run.choice3 = line + "\n";
    			    }
    			    
    			    // FINDS CHOICE '4'
    			    if(line.contains("4)")){
    			        run.choice4 = line + "\n";
    			    }
    			    
    			    // FINDS CHOICE '5'
    			    if(line.contains("5)")){
    			        run.choice5 = line;
    			    }
    			    
    		    	//FINDS THE CORRECT ANSWER FOR THE QUESTION
    			    if(line.contains("Correct Answer: ")) {
    			    	
    			    	// ARRAY a CREATED TO SAVE FILE ANSWER VALUE
    			        String[] a = line.split(": ");
    			        
    			        // ANSWER IS PASSED TO a ARRAY
    			        answer = a[1];
    			        
    			        // VALUE OF answer VARIABLE IS PASSED TO QUIZ CLASS
    			        run.answer = answer;
    			        
    			        // MESSAGE SENT TO CLIENT LOADING ANY MESSAGES, QUESTION WITH POSSIBLE CHOICES
    			        out.writeObject(message + run.question + run.choice1 + run.choice2 + run.choice3 + run.choice4 + run.choice5);
    			        
    			        // STRING VARIABLE data READS INPUT FROM CLIENT SOCKET 
	    		        data = (String) in.readObject();
	    						
	    	            // CHECKS IF THE USER'S INPUT MATCHES THE CORRECT ANSWER FROM THE FILE
	    	            if(data.equalsIgnoreCase(answer)){
	    	            	
	    	            	// THE MESSAGE VARIABLE GETS THE VALUE BELOW TO PRINT TO THE USER WITH THE NEXT QUESTION
	    	            	message = message.replace(message, "Correct!\n\n");
	    	            	
	    	            	// QUESTION NUMBER IS INCREMENTED BY 1
	    	            	i++;
	    	            	
	    	            	// SCORE IS INCREMENTED BY 1
	    	                score++;
	    	            } 
	
	    	            // CHECKS IF THE USER'S ANSWER DOES NOT MATCH THE CORRECT ANSWER ON THE FILE
	    	            else if (!data.equalsIgnoreCase(answer)) {
	    	            	
	//    	            	message = "Wrong. Correct Answer was: " + run.getAnswer() + "\n\n";
	    	            	
	    	            	// MESSAGE VARIABLE VALUE IS REPLACES WITH THE VALUE/MESSAGE BELOW TO SHOW USER WITH THE NEXT QUESTION 
	    	            	message = message.replace(message, "Wrong. Correct Answer was: " + run.getAnswer() + "\n\n");
	    	            	
	    	            	// QUESTION NUMBER INCREMENTS BY 1
	    	            	i++;
	    	            }
    			    }
    			} 
    			
    			// CLOSES THE BUFFERED READER VARIABLE
    			reader.close();
            }
            else {
            	
            	// CHECKS THE DIFFERENCE BETWEEN THE VALUE OF 'NUMPLAYERS' AND THE SIZE OF THE 'ONLINECLIENTS' ARRAY
            	int playersLeft = numPlayers - onlineClients.size();
            	
            	// INFORMS CLIENT/S THEY ARE WAITING FOR MORE PLAYERS TO JOIN
            	out.writeObject("Waiting for " + playersLeft + " players to join");
            }
    	
        // LOOPS TO REMOVE THE CLIENT FROM THE THREAD 'ONLINECLIENTS' ARRAY
    	for(int o = 0; o <= numPlayers; o++) {
        	Thread T1 = new Thread(new ClientThread(o));
        	T1.start();
        	onlineClients.remove(T1);
        }
    	
    	// IF THE SIZE OF 'ONLINECLIENTS' ARRAY IS MORE THAN ONE
    	if(onlineClients.size() > 1) {
    		
    		// MESSAGE TO CLIENTS WHO FINISHED ALL QUESTIONS LOADS INFORMING THEM THAT OTHER CLIENTS ARE STILL IN THE GAME
    		out.writeObject("Waiting for other clients to finish the quiz.");
    	}
    	else {
    		
    		// IF THE VALUE OF 'NUMPLAYERS' IS ONE
    		if(numPlayers == 1) {
    			
    			// PRINTS THE MESSAGE BELOW TO THE CLIENT WITH ANY FINAL MESSGAES (IF THEY GOT THE ANSWER RIGHT OR WRONG) ALONG WITH THEIR FINAL SCORE 
    			out.writeObject(message + "Final Score: " + score + ".");
    			
    			// CLOSES THE OUTPUT OBJECT
    			out.close();
    			
    			// CLOSES THE CLIENT SOCKET
    			client.close();
    		}
    		
    		// RUNS IF THERE IS MORE THAN ONE CLIENT 
    		else {
    			
    			// CALLSS THE LEADEBROARD METHOD
    			leaderboard();
    		}
    	}
    	
    	// CLOSES THE INPUT OBJECT
    	in.close();
    	
    	// CATCHES ANY ERRORS INCLUDING THE ERROR OF NOT BEING ABLE TO FIND THE FILE
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    }
    
    // MAP VARIABLE CREATED TO STORE USER'S USERNAMES AND SCORES
    static Map<String,Integer> myMap = new HashMap<String, Integer>(); // MAP TO STORE USERNAMES AND SCORES
    
    // LEADERBOARD METHOD CALLED
    public static void leaderboard() {
		try {
			
			// ADDS 2 VALUES TO THE MAP OBJECT CREATED - USERNAME FROM THE CLIENT SIDE AND SCORE VALUE
			myMap.put(username, score); 
			
			// SERVER SENDS MESSAGE TO THE CLIENT WITH ANY MESSAGES (IF THE FINAL ANSWER WAS RIGHT OR WRONG) AS WELL AS THEIR FINAL SCORE AND A LEADERBOARD (WHICH LOADS ALL OTHER PLAYERS USERNAMES AND THEIR SCORES)
			out.writeObject(message + "Final Score: " + score + ".\n\nLeaderboard: \n" + myMap.toString());

			// CLOSES OUTPUT OBJECT VARIABLE
			out.close();
			
			// CLOSES CLIENT SOCKET CONNECTION
			client.close();
			
		// CATCHES ANY ERRORS
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    // GET HOST METHOD
    public static String getHost() { 
    	String host = "";
		try {
			
			// USES INET TO GET HOSTNAME OF LOCAL MACHINE
			host = InetAddress.getLocalHost().getHostName(); 
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		// RETURNS VARIABLE HOST
		return host; 
	}
	
 // GET PORT
	public static int getPort() { 
		int port = 0;
		try {
			
			// PROP FILE OBJECT CREATED
			Properties prop = new Properties(); 
			
			// GETS 'SERVER' PROP FILE FROM SYSTEM
			FileInputStream in = new FileInputStream("Server.prop"); 
			prop.load(in);
			in.close();
			
			// GETS PORT FROM THE PROP FILE
			port = Integer.parseInt(prop.getProperty("Port")); 
		} catch (IOException e) {
			System.out.println(e);
		}
		
		// RETURNS VARIABLE PORT
		return port; 
	}
    
    // METHOD BELOW TO STORE NEW USER ACCOUNTS TO DATABASE WHICH IS CALLED ON THE CLIENT SIDE THROUGH THE REGISTER CLASS	
	// CONSTRUCTOR WITH PARAMETERS --> USER(USERNAME FROM REGISTER GUI), PASS(PASSWORD FROM REGISTER GUI), NAME(NAME FROM REGISTER GUI)
	public void Database(String user, String pass, String name) 
 	{
 		try {
 			Class.forName("org.h2.Driver"); 
 			
 			// CONNECTION TO DATABASE WITH PASSWORD AND USERNAME 'SA'
 			Connection con = DriverManager.getConnection("jdbc:h2:~/test","sa","sa"); 
 			Statement state = con.createStatement();
 						
 			// CREATES TABLE IN DATABASE IF IT DOESNT ALREADY EXISTS
 			String table = "CREATE TABLE IF NOT EXISTS SAVEDUSERS (USERNAME VARCHAR(255) PRIMARY KEY, PASSWORD VARCHAR(255), NAME VARCHAR(255));"; 
 			int n = state.executeUpdate(table);

 			// CREATES A NEW ROW IN THE TABLE ON THE DATABASE WITH THE VALUES PASSED THROUGH TO THE CLASS
 			String insertQ = "INSERT INTO SAVEDUSERS VALUES('" + user + "', '" + pass + "', '" + name + "');"; 
 			int n1 = state.executeUpdate(insertQ);

 			state.close();
 			con.close();
 			
		// CATCHES ANY ERRORS 
 		} catch (ClassNotFoundException e) {
 			e.printStackTrace();
 		} catch (SQLException e) {
 			e.printStackTrace();
 		}
 	}
}

// CLASS TO CREATE THREADS FOR CLIENTS CONNECTED
class ClientThread implements Runnable {
    private int i;

    ClientThread(int ind) 
    {
       i = ind;
    }
    public void run() {
       try
       {
           Thread.sleep(1000);
       }
       catch (Exception e) {
           System.out.println(e.getMessage());
       }
    }
}