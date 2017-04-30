import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import net.proteanit.sql.DbUtils;

import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import java.awt.TextField;
import java.awt.GridLayout;
import java.awt.TextArea;

public class Main {

	private JFrame frame;
	private JTextField Last_name;
	private JTextField First_name;
	private JTextField Date_of_birth;
	private JTextField Address_1;
	private JTextField Address_2;
	private JTextField State;
	private JTextField Injury;
	private JTextField Progame;
	private JTable table;
	private static Connection connection;
	private static Connection conn;
	private static Statement myStmt;
	private static final int portNumber = 8082;
	private static final String hostname = "192.168.43.145" + "";
	private String output;
	public ServerSocket serverSocket;
	public Socket socket;
	public BufferedWriter bw;
	public boolean flag_Disconnect = false;
	private JTextField textField;
	private JTable User_table;
	private JTextField textField_1;
	private JTable table_1;
	private JTable Comment_table;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 599, 479);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane);
		
		JPanel Main = new JPanel();
		tabbedPane.addTab("Main", null, Main, null);
		Main.setLayout(new MigLayout("", "[][][][][][grow]", "[][grow][][][][]"));
		
		JTextArea textArea = new JTextArea();
		Main.add(textArea, "cell 5 1,grow");
		///////////////////////////////////////////////////////////////////////////////////////
		JButton Graph = new JButton("Graph");
		Graph.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent arg0) {
					Graph g = new Graph();
					g.Graph();
				}
			});
		//	panel.add(GraphEMG);
		
		Main.add(Graph, "cell 5 3");
		////////////////////////////////////////////////////////////////////////////////////////
		JButton Knee_Gui = new JButton("Knee GUI");
		Knee_Gui.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Knee_Gui kg = new Knee_Gui();
				kg.Knee_gui();
			}
		});
	//	panel.add(AccMove);
		Main.add(Knee_Gui, "cell 5 4");
		
		JPanel DataBase = new JPanel();
		tabbedPane.addTab("DataBase", null, DataBase, null);
		DataBase.setLayout(new MigLayout("", "[][][][grow]", "[][][][][][][][][][][grow][][][][][grow]"));
			
		
		try{
			
			String driver = ("com.mysql.jdbc.Driver");
			 String url = ("jdbc:mysql://localhost:3306/fydatabase?user=root");
			 System.out.println("update driver");
			 Class.forName( driver );
			 
			 connection = DriverManager.getConnection( url );
			  conn =DriverManager.getConnection("jdbc:mysql://localhost:3306/userdb?user=root");	
			 
			  myStmt = connection.createStatement();
			 
			 
			 ResultSet myrs = myStmt.executeQuery("SELECT * FROM `fydatabase'");
			 while(myrs.next()){
				System.out.println(myrs.getString("lastName") + "," + myrs.getString("firstName") + "Thats all folks"); 
			 }
		}
		catch(Exception e){
			
		}
		
		
		
		JLabel First_Name = new JLabel("First Name");
		DataBase.add(First_Name, "cell 1 1");
		
		First_name = new JTextField();
		DataBase.add(First_name, "cell 3 1,growx");
		First_name.setColumns(10);
		
		JLabel Last_Name = new JLabel("Last Name");
		DataBase.add(Last_Name, "cell 1 2");
		
		Last_name = new JTextField();
		DataBase.add(Last_name, "cell 3 2,growx");
		Last_name.setColumns(10);
		
		JLabel DOB = new JLabel("Date of Birth");
		DataBase.add(DOB, "cell 1 3");
		
		Date_of_birth = new JTextField();
		DataBase.add(Date_of_birth, "cell 3 3,growx");
		Date_of_birth.setColumns(10);
		
		JLabel Address1 = new JLabel("Address");
		DataBase.add(Address1, "cell 1 4");
		
		Address_1 = new JTextField();
		DataBase.add(Address_1, "cell 3 4,growx");
		Address_1.setColumns(10);
		
		JLabel Address2 = new JLabel("Address");
		DataBase.add(Address2, "cell 1 5");
		
		Address_2 = new JTextField();
		DataBase.add(Address_2, "cell 3 5,growx");
		Address_2.setColumns(10);
		
		JLabel City = new JLabel("City/State");
		DataBase.add(City, "cell 1 6");
		
		State = new JTextField();
		DataBase.add(State, "cell 3 6,growx");
		State.setColumns(10);
		
		JLabel Inj = new JLabel("Injury");
		DataBase.add(Inj, "cell 1 7");
		
		Injury = new JTextField();
		DataBase.add(Injury, "cell 3 7,growx");
		Injury.setColumns(10);
		
		JLabel Prog = new JLabel("Programe");
		DataBase.add(Prog, "cell 1 8");
		
		Progame = new JTextField();
		DataBase.add(Progame, "cell 3 8,growx");
		Progame.setColumns(10);
		////////////////////////////////////////////////////////////////////
		JButton Update = new JButton("Update");
		Update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					Class.forName ("com.mysql.jdbc.Driver");
					 Connection con =DriverManager.getConnection("jdbc:mysql://localhost:3306/fydatabase?user=root");
					PreparedStatement pst = con.prepareStatement("Insert Into data Values(?,?,?,?,?,?,?,?)");
					
					
					pst.setString(1, First_name.getText());
					pst.setString(2, Last_name.getText());
					pst.setString(3, Date_of_birth.getText());
					pst.setString(4, Address_1.getText());
					pst.setString(5, Address_2.getText());
					pst.setString(6, State.getText());
					pst.setString(7, Injury.getText());
					pst.setString(8, Progame.getText());
//					pst.setString(7, "44");
					
					System.out.println("In update database");
					System.out.println("In update database"+First_name.getText()+Last_name.getText());
					JOptionPane.showMessageDialog(null, "Data Saved");
					
					//ResultSet rs = pst.executeQuery();
				   // myStmt.executeUpdate(sql);
					pst.executeUpdate();
					pst.close();
					con.close();
			
				}
				catch(Exception e1){
					
				}
			}
		});
		DataBase.add(Update, "cell 1 10");
		
		JScrollPane scrollPane = new JScrollPane();
		DataBase.add(scrollPane, "cell 3 10 1 6,grow");
		
		table = new JTable();
		scrollPane.setViewportView(table);
		//////////////////////////////////////////////////////////////////////
		JButton Find_Person = new JButton("Find Person");
		Find_Person.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "SELECT * FROM data where firstName = ?";
					PreparedStatement pst;
					pst = connection.prepareStatement(query);
					System.out.println("got here");
					pst.setString(1,First_name.getText());			
					ResultSet rs1 = pst.executeQuery();				
					System.out.println(First_name.getText().toString());
					table.setModel(DbUtils.resultSetToTableModel(rs1));
					System.out.println("got here");
					JOptionPane.showMessageDialog(null, "Data Found");
					pst.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		DataBase.add(Find_Person, "cell 1 11");
		//////////////////////////////////////////////////////////////////////
		JButton Show_DB = new JButton("Show Database");
		Show_DB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					
					String query = "SELECT * FROM data";
					PreparedStatement pst = connection.prepareStatement(query);
					ResultSet rs = pst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs));

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
			
			}
		});
		DataBase.add(Show_DB, "cell 1 12");
		//////////////////////////////////////////////////////////////////////
		JButton Delete = new JButton("Delete");
		Delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "delete FROM data where firstName = ?";
					PreparedStatement pst;
					pst = connection.prepareStatement(query);
					System.out.println("got here");
					pst.setString(1,First_name.getText());	
					pst.execute();
//					ResultSet rs1 = pst.executeQuery();				
//					System.out.println(First_name.getText().toString());
//					table.setModel(DbUtils.resultSetToTableModel(rs1));
//					System.out.println("got here");
					JOptionPane.showMessageDialog(null, "Data Deleted");
					pst.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		DataBase.add(Delete, "cell 1 13");
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Exercise UD", null, panel, null);
		panel.setLayout(null);
		
		TextArea Exer_textArea = new TextArea();
		Exer_textArea.setBounds(10, 37, 558, 187);
		panel.add(Exer_textArea);
		
		JButton Update_Exer = new JButton("Update");
		Update_Exer.setBounds(10, 306, 89, 23);
		panel.add(Update_Exer);
		
		
		
		textField_1 = new JTextField();
		textField_1.setBounds(10, 260, 124, 20);
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblEnterClientsNames = new JLabel("Enter Clients UserName");
		lblEnterClientsNames.setBounds(10, 241, 232, 14);
		panel.add(lblEnterClientsNames);
		
		JLabel lblUpdateClientsExercies = new JLabel("Update Clients Exercies");
		lblUpdateClientsExercies.setBounds(10, 17, 170, 14);
		panel.add(lblUpdateClientsExercies);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(164, 260, 404, 141);
		panel.add(scrollPane_3);
		
		Comment_table = new JTable();
		scrollPane_3.setViewportView(Comment_table);
		
		JButton btnComments = new JButton("Comments");
		btnComments.setBounds(10, 343, 89, 23);
		panel.add(btnComments);
		
		btnComments.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {	
					Class.forName ("com.mysql.jdbc.Driver");
					Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/physio?user=root");
					String query = "SELECT * FROM names";
					PreparedStatement pst = conn.prepareStatement(query);
					ResultSet rs = pst.executeQuery();
					Comment_table.setModel(DbUtils.resultSetToTableModel(rs));
					JOptionPane.showMessageDialog(null, "Comment table");
					pst.execute();
					pst.close();
					conn.close();
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}			
			}
			
		});
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("User Database", null, panel_1, null);
		panel_1.setLayout(new MigLayout("", "[][][grow][][][][][][][][grow]", "[][][][][grow]"));
		
		JButton Update_user_db = new JButton("Update Table");
		panel_1.add(Update_user_db, "cell 1 0");
		
		JButton btnUpdateAppTable = new JButton("Update App Table");
		panel_1.add(btnUpdateAppTable, "cell 3 0");
		
		btnUpdateAppTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {	
					Class.forName ("com.mysql.jdbc.Driver");
					Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/logindb?user=root");
					String query = "SELECT * FROM names";
					PreparedStatement pst = conn.prepareStatement(query);
					ResultSet rs = pst.executeQuery();
					table_1.setModel(DbUtils.resultSetToTableModel(rs));
					JOptionPane.showMessageDialog(null, "User Updated");
					pst.execute();
					pst.close();
					conn.close();
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}			
				
			}
		});
		
		JButton Delete_Person = new JButton("Delete Person");
		panel_1.add(Delete_Person, "cell 3 1");
		
		JScrollPane scrollPane_1 = new JScrollPane();
		panel_1.add(scrollPane_1, "cell 4 3 7 2,grow");
		
		User_table = new JTable();
		scrollPane_1.setViewportView(User_table);
		
		JButton Find_user_db = new JButton("Find Person");
		panel_1.add(Find_user_db, "cell 1 1");
		
		textField = new JTextField();
		panel_1.add(textField, "cell 2 1,growx");
		textField.setColumns(10);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		panel_1.add(scrollPane_2, "cell 1 3 3 2,grow");
		
		table_1 = new JTable();
		scrollPane_2.setViewportView(table_1);
		
		Find_user_db.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					System.out.println("got here");
					String query = "SELECT * FROM names where ID = ?";
					PreparedStatement pst = conn.prepareStatement(query);
					System.out.println("got here");
					pst.setString(1,textField.getText());
				
					ResultSet rs1 = pst.executeQuery();
					
					System.out.println(textField.getText().toString());
					User_table.setModel(DbUtils.resultSetToTableModel(rs1));
					System.out.println("got here");
					JOptionPane.showMessageDialog(null, "Table updated");
					pst.close();
					
					
					String query1 = "SELECT * FROM names where Name = ?";
					Connection conn1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/logindb?user=root");
					PreparedStatement pst1 = conn1.prepareStatement(query1);
					System.out.println("got here");
					pst1.setString(1,textField.getText());
				
					ResultSet rs2 = pst1.executeQuery();
					
					System.out.println(textField.getText().toString());
					table_1.setModel(DbUtils.resultSetToTableModel(rs2));
					System.out.println("got here");
					JOptionPane.showMessageDialog(null, "Table updated");
					pst1.close();
	
			
					System.out.println("got here");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		Update_user_db.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					
					Class.forName ("com.mysql.jdbc.Driver");			
					String query = "SELECT * FROM names";
					PreparedStatement pst = conn.prepareStatement(query);
					ResultSet rs = pst.executeQuery();
					User_table.setModel(DbUtils.resultSetToTableModel(rs));
					JOptionPane.showMessageDialog(null, "User Updated");
					pst.executeUpdate();
					pst.close();
					conn.close();
			
				}
				catch(Exception e1){
					
				}
			}
		});
		
		
		Update_Exer.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String Exercise = Exer_textArea.getText();
	
				try{
				
				System.out.println("Got here");
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/exercise_db?user=root");
				
				// PreparedStatement pst = conn.prepareStatement("Insert Into names(ID,Exercise )" +" Values(?,?)");
				 
				// PreparedStatement pst = conn.prepareStatement("update names set Exercise = ? where ID = ?");
				
					String name = textField_1.getText();
					
					PreparedStatement pst1 = conn.prepareStatement("delete FROM names where ID = ?");
					 pst1.setString(1, name);
					 pst1.executeUpdate();
				     PreparedStatement pst2 = conn.prepareStatement("Insert Into names(ID,Exercise )" +" Values(?,?)");
					 pst2.setString(1, textField_1.getText());
			         pst2.setString(2, Exer_textArea.getText());
			         pst2.executeUpdate();
				
				
				 
				 
//				PreparedStatement pst3 = conn.prepareStatement("Insert Into names(ID,Exercise )" +" Values(?,?)");
//				pst3.setString(1, textField_1.getText());
//	             pst3.setString(2, Exer_textArea.getText());
//	             pst3.executeUpdate();
				 
	             System.out.println("After Prepared statment");
//	             
//	             pst.setString(1, textField_1.getText());
//            	// pst.setString(2, Exer_textArea.getText());
//				System.out.println("got here");
//                int change = pst.executeUpdate();
//                	
//                if(change == 1){
//                	System.out.println("update");
//                }
//		
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
				
			
			}	
			

			
			
		});
	}
}


