import java.awt.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javax.swing.JOptionPane;

import net.proteanit.sql.DbUtils;


public class Server {
	
	HashMap<String,Socket> map = new HashMap<String,Socket>();
	HashMap<String,Socket> map1 = new HashMap<String,Socket>();
	
    ServerSocket myServerSocket;
    Socket clientSocket;
    boolean ServerOn = true;
    int x = 1;
    int y = 1;
    
   
    boolean flag = false;
    String name, exercise;
    
    String mbed;

    public Server() {
        try {
            myServerSocket = new ServerSocket(8082);
        } catch (IOException ioe) {
            System.out.println("Could not create server socket on port 8082");
            System.exit(-1);
        }

        Calendar now = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat(
                "E yyyy.MM.dd 'at' hh:mm:ss a zzz");
        System.out.println("It is now : " + formatter.format(now.getTime()));

        while (ServerOn) {
            try {
                clientSocket = myServerSocket.accept();
                map.put("someKey " + x, clientSocket);
                x++;
                System.out.println("The clients address" + clientSocket.getRemoteSocketAddress());
                ClientServiceThread cliThread = new ClientServiceThread(clientSocket);
                cliThread.start();
            } catch (IOException ioe) {
                System.out.println("Exception found on accept. Ignoring. Stack Trace :");
                ioe.printStackTrace();
            }
        }
        try {
            myServerSocket.close();
            System.out.println("Server Stopped");
        } catch (Exception ioe) {
            System.out.println("Error Found stopping server socket");
            System.exit(-1);
        }
    }

    public static void main(String[] args) {
        new Server();
    }

    class ClientServiceThread extends Thread {
        Socket myClientSocket;
        boolean m_bRunThread = true;

        public ClientServiceThread() {
            super();
        }

        ClientServiceThread(Socket s) {
            myClientSocket = s;
        }

        public void run() {
            BufferedReader in = null;
            BufferedReader IN = null;
            BufferedReader IN1 = null;
            BufferedReader IN2 = null;
            PrintWriter out = null;
            PrintWriter OUT = null;
            PrintWriter OUT1 = null;
            PrintWriter OUT2 = null;
            System.out.println("Accepted Client Address - " + myClientSocket.getInetAddress().getHostName());
            System.out.println("Client ip address =" +myClientSocket.getRemoteSocketAddress().toString());
           
        	Socket clnt = map.get("someKey 1");
           
            try {
            	 System.out.println("Got as far as here 1");
                in = new BufferedReader(
                        new InputStreamReader(myClientSocket.getInputStream()));
                out = new PrintWriter(
                        new OutputStreamWriter(myClientSocket.getOutputStream()));
                System.out.println("Got as far as here 2");
                OUT =  new PrintWriter(
                        new OutputStreamWriter(clnt.getOutputStream()));
            	IN = new BufferedReader(
                         new InputStreamReader(clnt.getInputStream()));
            	 System.out.println("Got as far as here 3");

               // while (m_bRunThread) 
                //out.println("Hello");
               for(int x = 0; x<35;x++) {
            	    
            	   System.out.println("Got as far as here 1234");
                    String clientCommand = in.readLine();
                    System.out.println("Got as far as here" + clientCommand  + "/////////");
                    String s = myClientSocket.getInetAddress().getHostName().toString();
                    System.out.println("Got as far as here");
                   // System.out.println("The output of the mbed device " + clientCommand.toString());
                    
                    
                    
                    /////////////////////////////////////////////
                    if(clientCommand.equals("chat")){
                    	System.out.println("In chat room!!!!!!!!");
                    	 map1.put("someKey " + y, clientSocket);
                         
                    	
                        Socket first = map.get("someKey 1");
                        Socket Second = map.get("someKey 2");
                        
                        OUT1 =  new PrintWriter(
                                new OutputStreamWriter(first.getOutputStream()));
                    	IN1 = new BufferedReader(
                                 new InputStreamReader(first.getInputStream()));
                    	if(y==2){
                    	OUT2 =  new PrintWriter(
                                 new OutputStreamWriter(Second.getOutputStream()));
                     	IN2 = new BufferedReader(
                                  new InputStreamReader(Second.getInputStream()));
                    	}
                    	System.out.println("the value of y: "+ y);
                    	y++;
                    	String clientCommand_2 = null;
                    	while(!clientCommand.equals("disconect")){
                    		
                    		
                    		if(IN1.ready()){
                    			clientCommand = IN1.readLine();
                    			OUT2.println(clientCommand);
                    			OUT2.flush();
                    			System.out.println("You sent from in1");
                    		}
                    		if(IN2.ready()){
                    			clientCommand_2 = IN2.readLine();
                    			OUT1.println(clientCommand_2);	
                        		OUT1.flush();
                        		System.out.println("You sent from in2");
                    		}

                    		System.out.println("Your stuck in here");
                    	}
                    	System.out.println("I got out");
//                    	IN1.close();
//                    	OUT2.close();
//                    	IN2.close();
                    	System.out.println("The Socket is close");
                    	y=1;
                    }
                    
                    if(clientCommand.equals("Send_Exercises")){
                    	
                    	System.out.println("Got into send Exercise");
                    	ObjectInputStream ois = new ObjectInputStream(myClientSocket.getInputStream());
                        // obj = ois.readObject();
                        
                         String[] myObjects = (String[])ois.readObject();
                         System.out.println("Got as far as  here");
                         
                         String name = myObjects[0];
                         String comment = myObjects[1];
                         
                         Physio_comment(name, comment);
                        
                    	
                    }
                    
                    if(clientCommand.equals("Get_Exercise")){
                    	
                    	System.out.println("Got into the get exercise statment");
                    	
                    	ObjectInputStream ois = new ObjectInputStream(myClientSocket.getInputStream());
                        // obj = ois.readObject();
                        
                         String[] myObjects = (String[])ois.readObject();
                         System.out.println("Got as far as  here");
                         
                         String name = myObjects[0];
                         String pass = myObjects[1];
                         String date_time = myObjects[2];
                       
                    	Get_Exer_db(name);	
                    	
                    }
                    
                   
                    
                    
                    if(clientCommand.equals("DataBase")){
                     	System.out.println("Got in here inside databse");
//                    	clientCommand = in.readLine();
//                    	System.out.println(clientCommand);
                    	Object obj = new Object();
                    	ObjectInputStream ois = new ObjectInputStream(myClientSocket.getInputStream());
                        // obj = ois.readObject();
                        
                         String[] myObjects = (String[])ois.readObject();
                         System.out.println("Got as far as  here");
                         
                         String name = myObjects[0];
                         String Rep = myObjects[1];
                         String Rate = myObjects[2];
                         String ID = myObjects[3];
                         String comment = myObjects[4];
                         System.out.println("Got in here inside databse 1234");
                         database(name,Rep,Rate,ID,comment);
                         System.out.println("Got in here inside databse 5678");
                         System.out.println("String is: '" + myObjects + "'");
                       //  System.out.println(obj.toString());
                         System.out.println("Here");
                    }
                    
                    
                    
                    if(clientCommand.equals("Logindatabase")){
                     	System.out.println("Got in here");
//                    	clientCommand = in.readLine();
//                    	System.out.println(clientCommand);
                    	Object obj = new Object();
                    	ObjectInputStream ois = new ObjectInputStream(myClientSocket.getInputStream());
                        // obj = ois.readObject();
                        
                         String[] myObjects = (String[])ois.readObject();
                         System.out.println("Got as far as  here");
                         
                         String name = myObjects[0];
                         String pass = myObjects[1];
                         String date_time = myObjects[2];
                       
                         System.out.println("Here above the function");
                         Logindatabase(name,pass,date_time);
                         System.out.println("String is: '" + myObjects + "'");
                       //  System.out.println(obj.toString());
                         System.out.println("Here");
                    }
                    
                    
                    
                    
                    
                    
                    
                    ////////////////////////////////////////////////////////////////////////////////
//                    if(s.equals("LAPTOP-1QL5I6MB")){
//                    	System.out.println("In th embed part");
//                    	System.out.println("This is the app");
//                    	System.out.println("Hash map" + clnt);
//                    
////                    	System.out.println("The output of the mbed device " + IN.readLine().toString() );
// //                   	OUT.println("We could be on to a winner lads");
////                    	OUT.flush();
////                    	System.out.println("Sent to mbed");
//                    	
//                    	String get_the_job = in.readLine().toString();
//                    	System.out.println(get_the_job);
//                    	
//                    	if(get_the_job.equals("Sean")){
//                    		OUT.println("We could be on to a winner lads");
//                        	OUT.flush();
//                        	get_the_job = "";
//                        	String the_outcome =  IN.readLine().toString() ;
//                        	System.out.println("The output of the mbed device " + the_outcome);
//                        	out.println(the_outcome);
//                        	
//                    	}
//                    	
//                    	if(get_the_job.equals("john")){
//                    		OUT.println("We could be on to a winner lads slightly closer");
//                        	OUT.flush();
//                        	get_the_job = "";
//                        	String the_outcome =  IN.readLine().toString() ;
//                        	System.out.println("The output of the mbed device " + the_outcome);
//                    	}
//                    }
                 //   System.out.println("Client Says :" + clientCommand);
                    
                    if (!ServerOn) {
                        System.out.print("Server has already stopped");
                        out.println("Server has already stopped");
                        out.flush();
                        m_bRunThread = false;
                    }
                    
                    if(clientCommand.equalsIgnoreCase("This is embed")) {
                    	System.out.println("Got into the embed bit");
                    	 map1.put("someKey " + y, clientSocket);
                         
                      	
                         Socket first = map.get("someKey 1");
                        while(true){
                        System.out.println("After the while 1");
                        System.out.println("Client ip address =" +myClientSocket.getRemoteSocketAddress().toString());
                        OUT1 =  new PrintWriter(
                                 new OutputStreamWriter(first.getOutputStream()));
                     	IN1 = new BufferedReader(
                                  new InputStreamReader(first.getInputStream()));
                     	
                     		
                     	mbed = IN1.readLine();
                     	System.out.println("Above the mbed out printline " + mbed );
//                     	out.println(mbed);
//                     	out.flush();
                     	
                     	//EmgDatabase(mbed,name,exercise);
                     	
                     	System.out.println("check database");
                     	
                    	System.out.println("check after the flush");
                    	System.out.println("The mbed flag is "+flag);
                    	while(flag){
                    		
                    	}
                    	
                    	OUT1.println("Emg Request");
                    	OUT1.flush();
                     	
                     	}
//                     	OUT1.println("Emg finished");
//                    	OUT1.flush();
                     	
                    }
                    
                    
                    if (clientCommand.equalsIgnoreCase("Emg Request")) {
                    	System.out.println(flag);
                    	flag = false;
                    	System.out.println(flag);
                    	System.out.println("In emg request part");
                    	
                    	name= in.readLine();
                    	exercise= in.readLine();
                    	
                    	System.out.println(flag);
                    	flag=true;
                    	System.out.println(flag);
                    	 Socket first = map.get("someKey 1");
                    	 
                    	System.out.println("the first key = " + first);
                    	   
                        OUT1 =  new PrintWriter(
                                new OutputStreamWriter(first.getOutputStream()));
                    	IN1 = new BufferedReader(
                                 new InputStreamReader(first.getInputStream()));
                    	OUT1.println("Emg Request");
                    	OUT1.flush();
                    	System.out.println("Client command = " + clientCommand);
                    	String Input = IN1.readLine();
                    	
                    	String[] tokens = Input.split(" ");
                    	
                    	System.out.println("This is the in put recived =" + tokens);
                    	
                    	for(int y = 0 ; y <15; y++){
                    		System.out.println(tokens[y]);
//                    		double emg_signal = (double) Double.parseDouble(tokens[y]);
//                    		
//                    		System.out.println("The emg_signal is  = " + emg_signal);
                    	}
                    
                    	EmgDatabase(Input,name,exercise);
                        m_bRunThread = false;
                        System.out.print("At the end of the emg loop");
                    } else if (clientCommand.equalsIgnoreCase("Mnald1")) {
                        out.println("Server Says : " + clientCommand);
                    	//out.println("Hello");
                        out.flush();
                        m_bRunThread = false;
                        System.out.print("Stopping client thread for client: ");
                    } else if (clientCommand.equalsIgnoreCase("\0")) {
                    	String comand = clientCommand;
                    	System.out.print("The array of values is: " + comand);
                        m_bRunThread = false;
                        System.out.print("Stopping client thread for client : ");
                        ServerOn = false;
                    } else {
//                        OUT1.println("This is embed");
//                        OUT1.flush();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        public void database(String name, String rep, String rate, String iD,String comment ){
            try {
                String driver = "com.mysql.jdbc.Driver";
                Class.forName(driver);
                System.out.println("working");
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/userdb?user=root");
                PreparedStatement pst = conn.prepareStatement("Insert Into names(ID, Name, Rep, Out_Of_Ten, Comment_)" +" Values(?,?,?,?,?)");
                System.out.println("After Prepared statment");
             
				
				pst.setString(1, iD);
				pst.setString(2, name);
				pst.setString(3, rep);
				pst.setString(4, rate);
				pst.setString(5, comment);
				System.out.println("After E statment");
                //String query = "insert into names(personID)" + "values(?)";
//                java.sql.PreparedStatement preparedStmt = conn.prepareStatement(query);
//                preparedStmt.setString(1,name);
                pst.execute();
                System.out.println("working2");
                conn.close();
            }catch(Exception e){

            }

        }
        
        public void Physio_comment(String id, String comment){
        	
        	   try {
                   String driver = "com.mysql.jdbc.Driver";
                   Class.forName(driver);
                   System.out.println("working");
                   Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/physio?user=root");
                   PreparedStatement pst = conn.prepareStatement("Insert Into names(ID, Question)" +" Values(?,?)");
                   System.out.println("After Prepared statment");
                
   				
   				pst.setString(1, id);
   				pst.setString(2, comment);
   				
   				System.out.println("After E statment");
                   //String query = "insert into names(personID)" + "values(?)";
//                   java.sql.PreparedStatement preparedStmt = conn.prepareStatement(query);
//                   preparedStmt.setString(1,name);
                   pst.execute();
                   System.out.println("working2");
                   conn.close();
               }catch(Exception e){

               }
        }
        
        public void Get_Exer_db(String name) throws SQLException, IOException{
        	
        	 String driver = "com.mysql.jdbc.Driver";
             try {
            	 
				Class.forName(driver);
				  System.out.println("In get exercise");
		             Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/exercise_db?user=root");
		             PreparedStatement pst = conn.prepareStatement("Insert Into names Values(?,?)");
		             
		             String query = "SELECT * FROM names where ID = ?";
						PreparedStatement pst1 = conn.prepareStatement(query);
						System.out.println("got here 123 the name is " + name);
						pst1.setString(1,name);
						System.out.println("got here 1234567");
						PrintWriter out1 = null;
						 out1 = new PrintWriter(
			                        new OutputStreamWriter(myClientSocket.getOutputStream()));
					
						ResultSet rs1 = pst1.executeQuery();
						
						if(rs1.next()) { 
							 String id = rs1.getString("ID"); 
							 String Exercise = rs1.getString("Exercise");
							 out1.println(Exercise);
							 out1.flush();
							 out1.close();
						}
//						System.out.println("got here 12389");
//					    String Exercise = rs1.getString("Exercise");
//					    System.out.println("got here 12310");
						 
						 
						
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
           
        	
			
        	
        }
        
        public void EmgDatabase(String emg, String name, String exercise){
        	
        	  try {
                  String driver = "com.mysql.jdbc.Driver";
                  Class.forName(driver);
                  System.out.println("working");
                  Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/emg_data?user=root");
                  PreparedStatement pst = conn.prepareStatement("Insert Into names(ID, exercise, emg)" +" Values(?,?,?)");
                  System.out.println("After Prepared statment");
               
  				
  				pst.setString(1, name);
  				pst.setString(2, exercise);
  				pst.setString(3, emg);
  				System.out.println("After Emg update to database statment");
                  //String query = "insert into names(personID)" + "values(?)";
//                  java.sql.PreparedStatement preparedStmt = conn.prepareStatement(query);
//                  preparedStmt.setString(1,name);
                  pst.execute();
                  System.out.println("working2");
                  conn.close();
              }catch(Exception e){

              }

        	
        }
        
        public void Logindatabase(String name, String Pass,String time ){
            try {
                String driver = "com.mysql.jdbc.Driver";
                Class.forName(driver);
                System.out.println("working");
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/logindb?user=root");
                PreparedStatement pst = conn.prepareStatement("Insert Into names Values(?,?,?)");
				
                

                String query = "SELECT * FROM names where Name = ?";
				PreparedStatement pst1 = conn.prepareStatement(query);
				System.out.println("got here 123");
				pst1.setString(1,name);
				PrintWriter out1 = null;
				 out1 = new PrintWriter(
	                        new OutputStreamWriter(myClientSocket.getOutputStream()));
				 
				ResultSet rs1 = pst1.executeQuery();
				if(rs1.next()) { 
					System.out.println("Got into password is correct");
					 String id = rs1.getString("Name"); 
					 String Password = rs1.getString("Pass_word");
					 System.out.println(id + Password);
					 
					 if(id.equals(name) && Password.equals(Pass)){
						 System.out.println("The password and username are correct");
						 pst.setString(1, name);
						 pst.setString(2, Pass);
						 pst.setString(3, time);
						 pst.execute();
						 pst.close();
						 out1.println("Correct");
	                     out1.flush();
					 }
					 if(id.equals(name) && !Password.equals(Pass)){
						 System.out.println("The password incorect");
						 out1.println("Password Incorect");
	                     out1.flush();
					 }
					 
					}	
				else{
					 System.out.println("New user");
	                 pst.setString(1, name);
					 pst.setString(2, Pass);
					 pst.setString(3, time);
						
					 pst.execute();
					 pst.close();
					 out1.println("NewUser");
                    out1.flush();
				 }
				
				
				
//				
//				pst.setString(1, name);
//				pst.setString(2, Pass);
				
                //String query = "insert into names(personID)" + "values(?)";
//                java.sql.PreparedStatement preparedStmt = conn.prepareStatement(query);
//                preparedStmt.setString(1,name);
                System.out.println("working2");
                conn.close();
            }catch(Exception e){

            }

        }
    }
}