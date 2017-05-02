import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import java.awt.SystemColor;
import java.net.UnknownHostException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JTextPane;
import javax.swing.JFormattedTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class findbook extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					findbook frame = new findbook();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public findbook() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.activeCaptionBorder);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblSelectBookTitle = new JLabel("Select book Title:");
		
		JComboBox comboBox = new JComboBox();
		MongoClient mongoClient = null;
		try {
			mongoClient = new MongoClient( "localhost" , 27017 );
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			DB db = mongoClient.getDB("bookrecommendation");
			BasicDBObject whereQuery = new BasicDBObject();
		
			DBCollection col = db.getCollection("bookinfo");
			DBCursor cursor = col.find();
			DBObject o;
			
			String s;
			while(cursor.hasNext()) {
			    o=cursor.next();
			comboBox.addItem(o.get("BookTitle".toString()));
			}
			comboBox.setSelectedIndex(-1);
		comboBox.setForeground(SystemColor.inactiveCaptionText);
		
		
		
		
		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		
		JButton btnGetInformation = new JButton("Get Information");
		btnGetInformation.addActionListener(new ActionListener() {
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
				whereQuery.put("BookTitle", comboBox.getSelectedItem());
				DBCollection col = db.getCollection("bookinfo");
				DBCursor cursor = col.find(whereQuery);
				DBObject o;
				
				String s = null;
				while(cursor.hasNext()) {
				    o=cursor.next();
				   
				s=   "ISBN: "+o.get("ISBN").toString()+"\nBook Author : "+o.get("BookAuthor").toString()+"\nYear Of Publication :"+o.get("YearOfPublication").toString()+"\nPublisher :"+o.get("Publisher").toString();
				    //System.out.println(o.get("Location").toString());
				 textPane.setText(s);
				}
				
				
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblSelectBookTitle)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(textPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
						.addComponent(btnGetInformation)
						.addComponent(comboBox, 0, 304, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(20)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSelectBookTitle)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
					.addComponent(btnGetInformation)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textPane, GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
