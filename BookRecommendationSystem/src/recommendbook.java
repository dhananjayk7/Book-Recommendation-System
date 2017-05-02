import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

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
import com.mongodb.MongoClient;

import java.awt.SystemColor;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.Scrollable;
import javax.swing.SwingUtilities;

import java.awt.event.ActionListener;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.ScrollPaneConstants;
import javax.swing.JScrollBar;
import javax.swing.JProgressBar;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class recommendbook extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					recommendbook frame = new recommendbook();
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
	public recommendbook() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 369, 385);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.activeCaptionBorder);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		String[] t1 = { "ISBN", "Total Count", "Average Rating"};
		String[] t2 = { "ISBN", "Book Title", "Rating"};
		String[] t3 = { "ISBN", "Number of times Rated"};
		final JFrame frame1 = new JFrame("Database Search Result");
		frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame1.getContentPane().setLayout(new BorderLayout());
		// TableModel tm = new TableModel();
		//String[] columnNames = { "pid", "fname", "lname", "age", "gender", "email" };
		final DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(t1);
		// DefaultTableModel model = new DefaultTableModel(tm.getData1(),
		// tm.getColumnNames());
		// table = new JTable(model);
		JTable table = new JTable();
		table.setModel(model);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setFillsViewportHeight(true);
		final JScrollPane scroll = new JScrollPane(table);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		
		 final int MY_MINIMUM = 0;
		  final int MY_MAXIMUM = 100;
		  
	
		
		JButton btnNewButton = new JButton("Boooks with ratings more than 8");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				while (model.getRowCount() != 0)
					model.removeRow(0);
				model.setColumnIdentifiers(t2);
			table.setModel(model);
				MongoClient mongoClient = null;
				try {
					mongoClient = new MongoClient( "localhost" , 27017 );
				} catch (UnknownHostException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				DB db = mongoClient.getDB("bookrecommendation");
				DBCollection col = db.getCollection("ratings");
				int count=0;
				String map = "function (){"
						+ "emit(this.ISBN,"
						+"this.rating"
						+ ");}";
				
			    String reduce = "function(key, values) { "
			    		+" var a = values[0]; "
			    		+"for (var i=1; i < values.length; i++){"
			    		+" var b = values[i];"
			    		+"a.max = Math.max(a.max, b.max);}"
			    		+"return a"+"} ;";
			      MapReduceCommand cmd = new MapReduceCommand(col, map, reduce,null, MapReduceCommand.OutputType.INLINE, null);
			      MapReduceOutput out = col.mapReduce(cmd);
			      
			  	List<String> list=new ArrayList<String>();
			      for (DBObject o:out.results()) {
			    	  Float abc1 = Float.parseFloat(o.get("value").toString());
			          if(abc1 >=8.0)
			          {

				    	  BasicDBObject whereQuery = new BasicDBObject();
							whereQuery.put("ISBN", o.get("_id").toString()); 
							DBCollection col1 = db.getCollection("bookinfo");
							DBCursor cursor = col1.find(whereQuery);
							
							String s;
							 frame1.getContentPane().add(scroll);
								frame1.setVisible(true);
								frame1.setSize(400, 300);  
							while(cursor.hasNext()) {
							   DBObject o1=cursor.next();
								//System.out.println(o1.get("BookTitle").toString());
							  // System.out.println("ISBN IS :"+o.get("_id").toString()+".BookTitle is "+o1.get("BookTitle").toString()+"rating is:"+o.get("value").toString());
							   model.addRow(new Object[] {o.get("_id").toString(),o1.get("BookTitle").toString(),o.get("value").toString()});
							
							}
			          } 
			      }	
			}
		});
		
		JButton btnNewButton_1 = new JButton("Rated by most users");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				
				model.setColumnIdentifiers(t3);
				while (model.getRowCount() != 0)
					model.removeRow(0);
				table.setModel(model);
				
				MongoClient mongoClient = null;
				try {
					mongoClient = new MongoClient( "localhost" , 27017 );
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				DB db = mongoClient.getDB("bookrecommendation");
				DBCollection col = db.getCollection("ratings");
				int count=0;
				String map = "function (){"
						+ "if(this.rating>=6){"
						+ "emit({ISBN:this.ISBN},"
						+"{rating:this.rating}"
						+ ");}}";
				
			    String reduce = "function(key, values) { "
			    		+"var a=values[0];"
			    		+ "for(var i=1;i<values.length;i++)"
			    		+ "{"
			    		+ "var b=values[i];"
			    		+ "a.rating+=b.rating;"
			    		+ "}"
			    		+"return  a; "+"} ;";
			      MapReduceCommand cmd = new MapReduceCommand(col, map, reduce,null, MapReduceCommand.OutputType.INLINE, null);
			      MapReduceOutput out = col.mapReduce(cmd);
			  List<String> list=new ArrayList<String>();
			 
			  List<Double> zoom1=new ArrayList<Double>();
			  Double p1=null,p2=null;
			  JSONParser jsonParser = new JSONParser();
			  frame1.getContentPane().add(scroll);
				frame1.setVisible(true);
				frame1.setSize(400, 300);  
			  for (DBObject o : out.results()) {
			    	 //System.out.println(o.toString());
				 JSONObject jsonObject;
					try {
						jsonObject = (JSONObject) jsonParser.parse(o.toString());
					
			         JSONObject jsonObject1= (JSONObject) jsonObject.get("_id");
			          String sp1=(String)jsonObject1.get("ISBN").toString();
			          list.add(sp1);
			          jsonObject1= (JSONObject) jsonObject.get("value");
			          
			         p2=(Double)jsonObject1.get("rating");
			         zoom1.add(p2);
			          
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    	 // list.add(o.toString());
			      }
			 String[] array=list.toArray(new String[0]);
			 Double[] pp2=zoom1.toArray(new Double[0]);
			    	
				  
			  
			 for(int i=0;i<array.length;i++)
			    	  {  System.out.println(array[i]+"================="+pp2[i]);
			    		  model.addRow(new Object[] {array[i],pp2[i]});
			    	 }
			
			  
			}
		});
		
		JButton btnNewButton_2 = new JButton("Based on average rating by all users");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setColumnIdentifiers(t1);
				while (model.getRowCount() != 0)
					model.removeRow(0);
				table.setModel(model);
				
				
				
				
				
				MongoClient mongoClient = null;
				try {
					mongoClient = new MongoClient( "localhost" , 27017 );
				} catch (UnknownHostException we) {
					// TODO Auto-generated catch block
					we.printStackTrace();
				}
				DB db = mongoClient.getDB("bookrecommendation");
				DBCollection col = db.getCollection("ratings");
				int count=0;
				String map = "function (){"
						+ "emit({ISBN:this.ISBN},"
						+"{rating:this.rating,count:1}"
						+ ");}";
				
			    String reduce = "function(key, values) { "
			    		+"var a=values[0];"
			    		+ "for(var i=1;i<values.length;i++)"
			    		+ "{"
			    		+ "var b=values[i];"
			    		+ "a.rating+=b.rating;"
			    		+ "a.count+=b.count;"
			    		+ "}"
			    		+"return  a; "+"} ;";
			      MapReduceCommand cmd = new MapReduceCommand(col, map, reduce,null, MapReduceCommand.OutputType.INLINE, null);
			      MapReduceOutput out = col.mapReduce(cmd);
			  List<String> list=new ArrayList<String>();
			  List<String> zoom=new ArrayList<String>();
			  List<String> zoom1=new ArrayList<String>();
			  String p1=null,p2=null;
			  JSONParser jsonParser = new JSONParser();
			     final BasicDBObject one
			        = (BasicDBObject)mongoClient.getDB("admin").getCollection("$cmd.sys.inprog").findOne();

			        System.out.println(one);
			  
			  for (DBObject o : out.results()) {
			    	 //System.out.println(o.toString());
				  JSONObject jsonObject;
					try {
						jsonObject = (JSONObject) jsonParser.parse(o.toString());
					
			         JSONObject jsonObject1= (JSONObject) jsonObject.get("_id");
			          String sp1=(String)jsonObject1.get("ISBN").toString();
			          list.add(sp1);
			          jsonObject1= (JSONObject) jsonObject.get("value");
			          p1=(String)jsonObject1.get("count").toString();
			         zoom.add(p1);
			         p2=(String)jsonObject1.get("rating").toString();
			         zoom1.add(p2);
			          
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			    	 // list.add(o.toString());
			      }
			 String[] array=list.toArray(new String[0]);
			 String[] pp1=zoom.toArray(new String[0]);
			 String[] pp2=zoom1.toArray(new String[0]);
			 frame1.getContentPane().add(scroll);
				frame1.setVisible(true);
				frame1.setSize(400, 300);   
				//progressBar.setMaximum(array.length);
			 for(int i=0;i<array.length;i++)
			    	  {
			    		  //System.out.println(array[i]+"========"+pp1[i]+"========="+pp2[i]);
			    	 
			    		Float a = Float.parseFloat(pp2[i]);
			    		Float b = Float.parseFloat(pp1[i]);
			    		Float c=  (a/b);
	
			    	//	  model.addRow(new Object[] {array[i],pp1[i],pp2[i]});
			    		  model.addRow(new Object[] {array[i],pp2[i],c});
			    	
			    	  
			    	  }	
		//	 progressBar.setValue(0);
			}
		});
		
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addGap(59)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnNewButton_2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
						.addComponent(btnNewButton_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnNewButton, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE))
					.addGap(51))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(19)
					.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
					.addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
					.addGap(59)
					.addComponent(btnNewButton_2, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
					.addGap(32))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
