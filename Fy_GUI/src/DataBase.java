import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JScrollBar;

public class DataBase extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField FName;
	private JTextField LName;
	private JTextField add_Age;
	private JTextField add_Address1;
	private JTextField add_Address2;
	private JTextField add_City;
	private JTextField add_Contact;
	private String name;
	private String age;
	private String address;
	private static Connection connection;
	private static Statement myStmt;

	
	public static void DataBase() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DataBase frame = new DataBase();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public DataBase() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 559, 410);
		contentPane = new JPanel(); 
		contentPane.setForeground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setForeground(Color.WHITE);
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel Databasse = new JLabel("DataBase");
		Databasse.setFont(new Font("Times New Roman", Font.BOLD, 22));
		panel.add(Databasse);
		
		JButton Return = new JButton("Return");
		Return.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		panel.add(Return);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);
		
		table = new JTable();
		
		try{
			
			String driver = ("com.mysql.jdbc.Driver");
			 String url = ("jdbc:mysql://localhost:3306/fyproject?user=root");
			 System.out.println("update driver");
			 Class.forName( driver );
			 
			 connection = DriverManager.getConnection( url );
			 
			  myStmt = connection.createStatement();
			 
			 
			 ResultSet myrs = myStmt.executeQuery("SELECT * FROM `fyproject'");
			 while(myrs.next()){
				System.out.println(myrs.getString("lastName") + "," + myrs.getString("firstName") + "Thats all folks"); 
			 }
		}
		catch(Exception e){
			
		}
//		table.setModel(new DefaultTableModel(
//			new Object[][] {
//				{null, null, null, null, null, null, null},
//				{null, null, null, null, null, null, null},
//				{null, null, null, null, null, null, null},
//				{null, null, null, null, null, null, null},
//				{null, null, null, null, null, null, null},
//			},
//			new String[] {
//				"Name", "Age", "Address1", "Address2", "City", "Contact", "EMG"
//			}
//		));
		panel_1.add(table);
		
		JScrollBar scrollBar = new JScrollBar();
		panel_1.add(scrollBar);
		
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(null);
		
		JLabel NewLabelName = new JLabel("First Name");
		NewLabelName.setBounds(36, 29, 63, 14);
		panel_2.add(NewLabelName);
		
		JLabel NewLabelAddress = new JLabel("Add Address1");
		NewLabelAddress.setBounds(36, 104, 70, 14);
		panel_2.add(NewLabelAddress);
		
		JButton Update = new JButton("Update DataBase");
		Update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					Class.forName ("com.mysql.jdbc.Driver");
					 Connection con =DriverManager.getConnection("jdbc:mysql://localhost:3306/fyproject?user=root");
					PreparedStatement pst = con.prepareStatement("Insert Into data Values(?,?,?,?,?,?)");
					
					
					pst.setString(1, FName.getText());
					pst.setString(2, LName.getText());
					pst.setString(3, add_Address1.getText());
					pst.setString(4, add_Address2.getText());
					pst.setString(5, add_Contact.getText());
					pst.setString(6, add_City.getText());
//					pst.setString(7, "44");
					System.out.println("In update database");
					System.out.println("In update database"+FName.getText()+LName.getText());
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
		Update.setBounds(299, 129, 117, 23);
		panel_2.add(Update);
		
		JButton Delete = new JButton("Delete");
		Delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		Delete.setBounds(299, 95, 117, 23);
		panel_2.add(Delete);
		
		JButton Find_Person = new JButton("Find Person");
		Find_Person.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {			
				
			}
		});
		Find_Person.setBounds(299, 54, 117, 23);
		panel_2.add(Find_Person);
		
		JButton ShowData = new JButton("Show Database");
		ShowData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					System.out.println("Got in here not the other");
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
		ShowData.setBounds(299, 20, 117, 23);
		panel_2.add(ShowData);
		
		JLabel Add_address2 = new JLabel("Add Address2");
		Add_address2.setBounds(36, 126, 70, 14);
		panel_2.add(Add_address2);
		
		JLabel lblNewLabel_1 = new JLabel("City");
		lblNewLabel_1.setBounds(36, 147, 70, 14);
		panel_2.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Contact");
		lblNewLabel_2.setBounds(36, 172, 70, 14);
		panel_2.add(lblNewLabel_2);
		
		FName = new JTextField();
		FName.setBounds(122, 26, 104, 20);
		panel_2.add(FName);
		FName.setColumns(10);
		
		JLabel addLName = new JLabel("Last Name");
		addLName.setBounds(36, 54, 63, 14);
		panel_2.add(addLName);
		
		LName = new JTextField();
		LName.setBounds(122, 51, 104, 20);
		panel_2.add(LName);
		LName.setColumns(10);
		
		add_Address1 = new JTextField();
		add_Address1.setBounds(122, 101, 104, 20);
		panel_2.add(add_Address1);
		add_Address1.setColumns(10);
		
		add_Address2 = new JTextField();
		add_Address2.setBounds(122, 123, 104, 20);
		panel_2.add(add_Address2);
		add_Address2.setColumns(10);
		
		add_City = new JTextField();
		add_City.setBounds(122, 147, 104, 20);
		panel_2.add(add_City);
		add_City.setColumns(10);
		
		add_Contact = new JTextField();
		add_Contact.setBounds(122, 171, 104, 20);
		panel_2.add(add_Contact);
		add_Contact.setColumns(10);
	}
}
