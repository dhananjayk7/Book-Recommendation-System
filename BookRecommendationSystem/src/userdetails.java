import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.LayoutStyle.ComponentPlacement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

import java.awt.SystemColor;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class userdetails extends JFrame {

	private JPanel contentPane;
	private JTextField newloc;
	private JTextField newuserid;
	private JTextField newuserloc;
	private JTextField newage;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					userdetails frame = new userdetails();
					frame.setVisible(true);
					MongoClient mongoClient = null;
					try {
						mongoClient = new MongoClient( "localhost" , 27017 );
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					DB db = mongoClient.getDB("bookrecommendation");
					
				
				
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public userdetails() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 632, 520);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.activeCaptionBorder);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblSelectUserid = new JLabel("Select UserID : ");
		lblSelectUserid.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblSelectUserid.setForeground(SystemColor.desktop);
		JComboBox usercombo = new JComboBox();
		MongoClient mongoClient = null;
		try {
			mongoClient = new MongoClient( "localhost" , 27017 );
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			DB db = mongoClient.getDB("bookrecommendation");
			BasicDBObject whereQuery = new BasicDBObject();
		
			DBCollection col = db.getCollection("userinfo");
			DBCursor cursor = col.find();
			DBObject o;
			
			String s;
			while(cursor.hasNext()) {
			    o=cursor.next();
			usercombo.addItem(o.get("userid".toString()));
			}
			usercombo.setSelectedIndex(-1);
		usercombo.setForeground(SystemColor.inactiveCaptionText);
		
		JLabel lblEnterUpdatedLocation = new JLabel("Enter Updated Location: ");
		lblEnterUpdatedLocation.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblEnterUpdatedLocation.setForeground(SystemColor.desktop);
		
		newloc = new JTextField();
		newloc.setColumns(10);
		
		JButton btnUpdateLocation = new JButton("Update Location");
		btnUpdateLocation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String s= newloc.getText();
				if(s.equals(""))
				{
					JOptionPane.showMessageDialog(null, "Enter Location First");
				}
				else{
				MongoClient mongoClient = null;
				try {
					mongoClient = new MongoClient( "localhost" , 27017 );
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				DB db = mongoClient.getDB("bookrecommendation");
				DBCollection col = db.getCollection("userinfo");
				BasicDBObject newDocument = new BasicDBObject();
				newDocument.append("$set", new BasicDBObject().append("Location", newloc.getText()));
						
				BasicDBObject searchQuery = new BasicDBObject().append("userid", usercombo.getSelectedItem());
				
				col.update(searchQuery, newDocument);
				JOptionPane.showMessageDialog(null, "Location Updated ");
				}	
			}
			
			
		});
		
		JButton btnShowDetails = new JButton("Show details");
		btnShowDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MongoClient mongoClient = null;
				try {
					mongoClient = new MongoClient( "localhost" , 27017 );
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				DB db = mongoClient.getDB("bookrecommendation");
				
				BasicDBObject whereQuery = new BasicDBObject();
				whereQuery.put("userid", usercombo.getSelectedItem());
				DBCollection col = db.getCollection("userinfo");
				DBCursor cursor = col.find(whereQuery);
				DBObject o;
				
				String s;
				while(cursor.hasNext()) {
				    o=cursor.next();
				    JOptionPane.showMessageDialog(null, "UserID : "+o.get("userid").toString()+"\nLocation : "+o.get("Location").toString()+"\n Age :"+o.get("Age").toString());
				    System.out.println(o.get("Location").toString());
				}
				
				
				
			}
		});
		
		JButton btnDeleteRecord = new JButton("Delete Record");
		btnDeleteRecord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MongoClient mongoClient = null;
				try {
					mongoClient = new MongoClient( "localhost" , 27017 );
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				DB db = mongoClient.getDB("bookrecommendation");
				DBCollection col = db.getCollection("userinfo");
				BasicDBObject document = new BasicDBObject();  
				document.put("userid",usercombo.getSelectedItem());  
				col.remove(document);
				
				DBCursor cursor = col.find();
				DBObject o;
				
				String s;
				while(cursor.hasNext()) {
				    o=cursor.next();
				usercombo.addItem(o.get("userid".toString()));
				}
				newloc.setText("");
				
				usercombo.setSelectedIndex(-1);
				JOptionPane.showMessageDialog(null, "Record Deleted");
				System.out.println("Record Deleted");
				
				
				
				
			}
		});
		
		JLabel lblCreateNewUser = new JLabel("Create New User  ");
		lblCreateNewUser.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblCreateNewUser.setForeground(SystemColor.desktop);
		
		JLabel lblEnterNewUserid = new JLabel("Enter new UserID:");
		lblEnterNewUserid.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblEnterNewUserid.setForeground(SystemColor.desktop);
		
		newuserid = new JTextField();
		newuserid.setColumns(10);
		
		JLabel lblEnterLocation = new JLabel("Enter Location:");
		lblEnterLocation.setForeground(SystemColor.desktop);
		lblEnterLocation.setFont(new Font("Times New Roman", Font.BOLD, 16));
		
		newuserloc = new JTextField();
		newuserloc.setColumns(10);
		
		JLabel lblEnterAge = new JLabel("Enter Age:");
		lblEnterAge.setForeground(SystemColor.desktop);
		lblEnterAge.setFont(new Font("Times New Roman", Font.BOLD, 16));
		
		newage = new JTextField();
		newage.setColumns(10);
		
		JButton btnCreateUser = new JButton("Create User");
		btnCreateUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String n1= newage.getText();
				String n2= newuserloc.getText();
				String n3= newuserid.getText();
				
				if(n1.equals("") || n2.equals("") || n3.equals(""))
				{
					JOptionPane.showMessageDialog(null, "Enter all details First");
				}
				else{
				MongoClient mongoClient = null;
				try {
					mongoClient = new MongoClient( "localhost" , 27017 );
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				DB db = mongoClient.getDB("bookrecommendation");
				DBCollection col = db.getCollection("userinfo");
				

				BasicDBObject document = new BasicDBObject();
				document.put("userid", newuserid.getText());
				document.put("Location", newuserloc.getText() );
				document.put("Age", newage.getText());
				col.insert(document);
				newage.setText(" ");
				newuserid.setText(" ");
				newuserloc.setText(" ");
				DBCursor cursor = col.find();
				DBObject o;
				
				String s;
				while(cursor.hasNext()) {
				    o=cursor.next();
				usercombo.addItem(o.get("userid".toString()));
				}
				JOptionPane.showMessageDialog(null, "Record Inserted");
				System.out.println("Record Inserted");	
				
				}
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(btnUpdateLocation)
									.addGap(74)
									.addComponent(btnShowDetails, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(lblEnterUpdatedLocation)
										.addComponent(lblSelectUserid)
										.addComponent(lblEnterNewUserid))
									.addGap(18)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(newuserid, GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
										.addComponent(usercombo, 0, 116, Short.MAX_VALUE)
										.addComponent(newloc, GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
										.addComponent(newuserloc, GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
										.addComponent(newage, GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE))))
							.addGap(70)
							.addComponent(btnDeleteRecord, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
							.addGap(114))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblEnterLocation, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(476, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblEnterAge, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(476, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnCreateUser)
							.addContainerGap(511, Short.MAX_VALUE))))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(101)
					.addComponent(lblCreateNewUser)
					.addContainerGap(366, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(21)
					.addComponent(lblCreateNewUser)
					.addGap(33)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblEnterNewUserid)
						.addComponent(newuserid, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(35)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblEnterLocation, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
						.addComponent(newuserloc, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(43)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblEnterAge, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
						.addComponent(newage, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(37)
					.addComponent(btnCreateUser)
					.addGap(48)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(usercombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblSelectUserid))
					.addGap(30)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblEnterUpdatedLocation)
						.addComponent(newloc, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(39)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnUpdateLocation)
						.addComponent(btnShowDetails)
						.addComponent(btnDeleteRecord))
					.addContainerGap(35, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
