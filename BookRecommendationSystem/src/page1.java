import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Color;
import javax.swing.UIManager;
import java.awt.SystemColor;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class page1 extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					page1 frame = new page1();
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
	public page1() {
		setBackground(SystemColor.inactiveCaptionBorder);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1672, 957);
		contentPane = new JPanel();
		contentPane.setForeground(SystemColor.text);
		contentPane.setBackground(SystemColor.activeCaptionBorder);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblBookRecommendationSystem = new JLabel("Book Recommendation System");
		lblBookRecommendationSystem.setForeground(new Color(0, 0, 0));
		lblBookRecommendationSystem.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 50));
		
		JButton btnNewButton = new JButton("");
		
			ImageIcon img = new ImageIcon("F:/java/BookRecommendationSystem/src/res/user.png");
	       
		    btnNewButton.setIcon(img);
		  
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			 Frame ud = new userdetails();
			 ud.setVisible(true);
			 
			}
		});
		
		JLabel lblEditUserDetails = new JLabel("Edit User Details");
		lblEditUserDetails.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblEditUserDetails.setForeground(new Color(0, 0, 0));
		
		JButton button = new JButton("");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				 Frame ud = new recommendbook();
				 ud.setVisible(true);
				
			}
		});
		ImageIcon img1 = new ImageIcon("F:/java/BookRecommendationSystem/src/res/books1.jpg");
	    button.setIcon(img1);
	    
	    
		JLabel lblGetBookRecommendations = new JLabel("Get Book Recommendations");
		lblGetBookRecommendations.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblGetBookRecommendations.setForeground(new Color(0, 0, 0));
		
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				 Frame ud = new findbook();
				 ud.setVisible(true);
			}
		});
		ImageIcon img2 = new ImageIcon("F:/java/BookRecommendationSystem/src/res/booksearch.png");
		btnNewButton_1 .setIcon(img2);
		
		JLabel lblFindBookInformation = new JLabel("Find Book Information");
		lblFindBookInformation.setForeground(new Color(0, 0, 0));
		lblFindBookInformation.setFont(new Font("Times New Roman", Font.BOLD, 20));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(172)
							.addComponent(lblEditUserDetails))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(68)
							.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 357, GroupLayout.PREFERRED_SIZE)))
					.addGap(92)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(button, GroupLayout.PREFERRED_SIZE, 343, GroupLayout.PREFERRED_SIZE)
							.addGap(101)
							.addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 335, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(56, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(43)
							.addComponent(lblGetBookRecommendations)
							.addPreferredGap(ComponentPlacement.RELATED, 233, Short.MAX_VALUE)
							.addComponent(lblFindBookInformation, GroupLayout.PREFERRED_SIZE, 240, GroupLayout.PREFERRED_SIZE)
							.addGap(79))))
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addContainerGap(640, Short.MAX_VALUE)
					.addComponent(lblBookRecommendationSystem)
					.addGap(327))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(34)
					.addComponent(lblBookRecommendationSystem)
					.addGap(75)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(button, 0, 0, Short.MAX_VALUE)
						.addComponent(btnNewButton_1, 0, 0, Short.MAX_VALUE)
						.addComponent(btnNewButton, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblEditUserDetails)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblGetBookRecommendations)
							.addComponent(lblFindBookInformation, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)))
					.addGap(279))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
