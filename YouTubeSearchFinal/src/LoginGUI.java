import java.awt.Button;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPanel;

/**
 * First GUI displayed to user
 * Existing users can sign in and method is called to check existence of username/password
 * New users can sign up and method is called to check validity of username/password chosen
 * @author Danielle Zoe Aloicius
 *
 */
public class LoginGUI extends JFrame{

	//content pane
	private Container cp;

	//sign in panel variables
	private JPanel signInPanel = new JPanel();
	private BoxLayout signInLayout = new BoxLayout(signInPanel, BoxLayout.PAGE_AXIS);
	private JPanel signInButtonPanel = new JPanel(); // panel to hold sign in button

	//sign up panel variables
	private JPanel signUpPanel = new JPanel();
	private BoxLayout signUpLayout = new BoxLayout(signUpPanel, BoxLayout.PAGE_AXIS);	
	private JPanel signUpButtonPanel = new JPanel(); // panel to hold sign up button

	//label variables
	private JLabel userLabel = new JLabel("Enter username:");
	private JLabel passwordLabel = new JLabel("Enter password:");
	private JLabel firstTimeLabel = new JLabel("First time user?");
	private JLabel newUserLabel = new JLabel("<html><body>Choose username:<br>Usernames must be between 3-15 characters long and contain at least one letter, and can also use numbers or underscores</body></html>");
	private JLabel newPasswordLabel = new JLabel("<html><body>Choose a password. <br>Passwords must be 6-32 characters long, contain at least 1 digit, at least 1 letter, and at least 1 special character (!?&$)</body></html>");

	//text field variables
	private JTextField userTF = new JTextField();
	private JTextField passwordTF = new JTextField();
	private JTextField newUserTF = new JTextField();
	private JTextField newPasswordTF = new JTextField();

	//button variables
	private Button signInBtn = new Button("Sign In");
	private Button signUpBtn = new Button("Sign Up");	

	//listener
	private LoginListener signInUpListener = new LoginListener(this);	

	/**
	 * Constructor for LoginGUI
	 */
	public LoginGUI(){	
		//initialize JFrame
		setSize(java.awt.Toolkit.getDefaultToolkit().getScreenSize()); //width, height
		setTitle("Login to YouTube Application");

		//get the ContentPane and add a layout
		cp= getContentPane();
		cp.setLayout(new BoxLayout(cp, BoxLayout.PAGE_AXIS));
		
		//add spacing and panels to content pane
		cp.add(Box.createRigidArea(new Dimension(0,20)));
		cp.add(signInPanel);
		cp.add(Box.createRigidArea(new Dimension(0,20)));
		cp.add(signUpPanel);

		//add buttons to panels
		signInButtonPanel.add(signInBtn);		
		signUpButtonPanel.add(signUpBtn);

		//add components to Sign in Panel
		signInPanel.setLayout(signInLayout);
		signInPanel.add(userLabel);
		signInPanel.add(userTF);
		signInPanel.add(Box.createRigidArea(new Dimension(0,20)));
		signInPanel.add(passwordLabel);
		signInPanel.add(passwordTF);
		signInPanel.add(Box.createRigidArea(new Dimension(0,20)));
		signInPanel.add(signInButtonPanel);
		signInPanel.add(Box.createRigidArea(new Dimension(0,20)));
		signInPanel.setBorder(BorderFactory.createTitledBorder("Sign In"));

		//add components to Sign Up Panel
		signUpPanel.setLayout(signUpLayout);
		signUpPanel.add(firstTimeLabel);
		signUpPanel.add(Box.createRigidArea(new Dimension(0,20)));
		signUpPanel.add(newUserLabel);
		signUpPanel.add(newUserTF);
		signUpPanel.add(Box.createRigidArea(new Dimension(0,20)));
		signUpPanel.add(newPasswordLabel);
		signUpPanel.add(newPasswordTF);
		signUpPanel.add(Box.createRigidArea(new Dimension(0,20)));
		signUpPanel.add(signUpButtonPanel);
		signUpPanel.setBorder(BorderFactory.createTitledBorder("Sign Up"));

		//add Listeners to buttons
		signInBtn.addActionListener(signInUpListener);
		signUpBtn.addActionListener(signInUpListener);

		align();		
		setSizes();
		setVisible(true);		


	}

	//method to set sizes of components
	private void setSizes(){
		//panels

		signInPanel.setMinimumSize(new Dimension(400, 250));
		signInPanel.setPreferredSize(new Dimension(400, 250));
		signInPanel.setMaximumSize(new Dimension(400, 250));
		signInPanel.setAlignmentX(CENTER_ALIGNMENT);

		signUpPanel.setMinimumSize(new Dimension(400, 350));
		signUpPanel.setPreferredSize(new Dimension(400, 350));
		signUpPanel.setMaximumSize(new Dimension(400, 350));
		signUpPanel.setAlignmentX(CENTER_ALIGNMENT);		

		//textfields
		userTF.setMinimumSize(new Dimension(300, 20));
		userTF.setPreferredSize(new Dimension(300, 20));
		userTF.setMaximumSize(new Dimension(300,20));

		passwordTF.setMinimumSize(new Dimension(300, 20));
		passwordTF.setPreferredSize(new Dimension(300, 20));
		passwordTF.setMaximumSize(new Dimension(300,20));

		newUserTF.setMinimumSize(new Dimension(300, 20));
		newUserTF.setPreferredSize(new Dimension(300, 20));
		newUserTF.setMaximumSize(new Dimension(300,20));

		newPasswordTF.setMinimumSize(new Dimension(300, 20));
		newPasswordTF.setPreferredSize(new Dimension(300, 20));
		newPasswordTF.setMaximumSize(new Dimension(300,20));		

		//buttons
		signInBtn.setMinimumSize(new Dimension(80, 50));
		signInBtn.setPreferredSize(new Dimension(80, 50));
		signInBtn.setMaximumSize(new Dimension(80,50));

		signUpBtn.setMinimumSize(new Dimension(80, 50));
		signUpBtn.setPreferredSize(new Dimension(80, 50));
		signUpBtn.setMaximumSize(new Dimension(80,50));

		//button Panels
		signInButtonPanel.setMinimumSize(new Dimension(90, 60));
		signInButtonPanel.setPreferredSize(new Dimension(90, 60));
		signInButtonPanel.setMaximumSize(new Dimension(90,60));

		signUpButtonPanel.setMinimumSize(new Dimension(90, 60));
		signUpButtonPanel.setPreferredSize(new Dimension(90, 60));
		signUpButtonPanel.setMaximumSize(new Dimension(90,60));

	}

	//aligns  the components in the LoginGUI
	private void align(){
		//sign In panel
		userLabel.setAlignmentX(LEFT_ALIGNMENT);
		passwordLabel.setAlignmentX(LEFT_ALIGNMENT);
		userTF.setAlignmentX(LEFT_ALIGNMENT);
		passwordTF.setAlignmentX(LEFT_ALIGNMENT);
		signInButtonPanel.setAlignmentX(LEFT_ALIGNMENT);

		//sign up panel		
		firstTimeLabel.setAlignmentX(LEFT_ALIGNMENT);
		newUserLabel.setAlignmentX(LEFT_ALIGNMENT);
		newPasswordLabel.setAlignmentX(LEFT_ALIGNMENT);
		newUserTF.setAlignmentX(LEFT_ALIGNMENT);
		newPasswordTF.setAlignmentX(LEFT_ALIGNMENT);
		signUpButtonPanel.setAlignmentX(LEFT_ALIGNMENT);

	}

    /**
     * Gets username entered by user in Sign in section
     * @return String username
     */
	public String getUserName(){
		String s;
		try{
			s = userTF.getText();
		}
		catch(NullPointerException e){
			return "";
		}
		return s;		
	}

    /**
     * Get password entered by user in Sign in section
     * @return String password
     */
	public String getPassword(){
		String s;
		try{
			s = passwordTF.getText();
		}
		catch(NullPointerException e){
			return "";
		}
		return s;		
	}

    /**
     * Gets username entered by user in Sign up section
     * @return String username
     */
	public String getNewUserName(){
		String s;
		try{
			s = newUserTF.getText();
		}
		catch(NullPointerException e){

			return "";
		}

		return s;		
	}

    /**
     * Get password entered by user in Sign up section
     * @return String password
     */
	public String getNewPassword(){
		String s;
		try{
			s = newPasswordTF.getText();
		}
		catch(NullPointerException e){
			return "";
		}
		return s;		
	}





}
