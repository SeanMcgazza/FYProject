import java.awt.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;

import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class Server {
	
	HashMap<String,Socket> map = new HashMap<String,Socket>();
	HashMap<String,Socket> map1 = new HashMap<String,Socket>();
	
    ServerSocket myServerSocket;
    Socket clientSocket;
    boolean ServerOn = true;
    int x = 1;
    int y = 1;

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
            	    
            	   System.out.println("Got as far as here");
                    String clientCommand = in.readLine();
                    System.out.println("Got as far as here");
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
                    
                    
                   
                    
                    
                    if(clientCommand.equals("DataBase")){
                     	System.out.println("Got in here");
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
                         
                         database(name,Rep,Rate,ID);
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
                    if (clientCommand.equalsIgnoreCase("John1")) {
                       // out.println("Server Says : " + clientCommand);
                        // database(clientCommand );
                       // out.flush();
                       // out.println("Bye");
                       // out.println("Server Says 2 : " + clientCommand);
                        m_bRunThread = false;
                        System.out.print("Stopping client thread for client : \n");
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
                        out.println("Server Says : " + clientCommand);
                        out.flush();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        public void database(String name, String rep, String rate, String iD ){
            try {
                String driver = "com.mysql.jdbc.Driver";
                Class.forName(driver);
                System.out.println("working");
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/userdb?user=root");
                PreparedStatement pst = conn.prepareStatement("Insert Into names Values(?,?,?,?)");
				
				
				pst.setString(1, iD);
				pst.setString(2, name);
				pst.setString(3, rep);
				pst.setString(4, rate);
				
                //String query = "insert into names(personID)" + "values(?)";
//                java.sql.PreparedStatement preparedStmt = conn.prepareStatement(query);
//                preparedStmt.setString(1,name);
                pst.execute();
                System.out.println("working2");
                conn.close();
            }catch(Exception e){

            }

        }
    }
}