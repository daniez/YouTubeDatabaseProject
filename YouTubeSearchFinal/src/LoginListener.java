import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 * LoginListener is called when user clicks "Sign In" or "Sign Up" buttons
 * 
 * @author Danielle Zoe Aloicius
 * 
 */
public class LoginListener implements ActionListener {

	//variable holding login GUI 
	private LoginGUI login;

	//variable holding main GUI--this is next GUI to be called
	private MainGUI main;

	/**
	 * Constructor for LoginListener
	 * @param reference to Login GUI
	 */
	public LoginListener(LoginGUI login){
		this.login = login;
	}

	/**
	 * actionPerformed is automatically called when 
	 * "Sign In" or "Sign Up" buttons are clicked
	 */
	public void actionPerformed(ActionEvent e) {
		String itemName = e.getActionCommand();

		//if the user clicks on the "Sign In" button
		if(itemName.equals("Sign In")){

			//get username and password from GUI
			String userName = login.getUserName();
			String password = login.getPassword();


			try {
				MySQLUtility.createNewYouTubeDB(); //creates if does not exist
				MySQLUtility.createUsersTable(); //creates if does not exist

				//if user does not exist, show message, return to login
				if(!MySQLUtility.existingUser(userName)){
					JOptionPane.showMessageDialog(login, "Username not found","Sign in error", JOptionPane.ERROR_MESSAGE);
				}

				//if password is wrong, show message, reurn to login
				else if(!MySQLUtility.correctPassword(userName, password)){
					JOptionPane.showMessageDialog(login, "Incorrect password","Sign in error", JOptionPane.ERROR_MESSAGE);
				}
				else{
					//if username and password entered correctly
					//set currentUser value and show main GUI
					Globals.setCurrentUser(userName);
					main = new MainGUI();
					login.dispose();
				}
			} catch (HeadlessException e1) {
				e1.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

		}

		//For new users:
		//if the user clicks on the "Sign Up" button
		else if(itemName.equals("Sign Up")){

			//get the user name and password user has entered & save in variables
			String newUserName = login.getNewUserName();
			String newPassword = login.getNewPassword();

			//check if db exists, if not create it
			try {
				MySQLUtility.createNewYouTubeDB(); //creates database if it does not exist
				MySQLUtility.createUsersTable(); //creates Users table if does not exist

				//if the username exists already, show message
				if(MySQLUtility.existingUser(newUserName)){
					JOptionPane.showMessageDialog(login, "Duplicate user name","Sign up error", JOptionPane.ERROR_MESSAGE);
				}

				//if user chooses invalid user name, show message
				else if(!validUserName(newUserName)){
					JOptionPane.showMessageDialog(login, "Usernames must be between 3-15 characters long and contain at least one letter, and can also use numbers or underscores","Sign up error", JOptionPane.ERROR_MESSAGE);

				}

				//if user chooses invalid password, show message
				else if(!validPassword(newPassword)){
					JOptionPane.showMessageDialog(login, "Passwords must be 6-32 characters long, contain at least 1 digit, at least 1 letter, and at least 1 special character (!?&$)","Sign up error", JOptionPane.ERROR_MESSAGE);

				}

				//if user name and password are valid
				//update database table
				//set value of currentUser
				//call Main GUI
				else{
					MySQLUtility.updateUsers(newUserName, newPassword);
					Globals.setCurrentUser(newUserName);
					main = new MainGUI();
					login.dispose();
				}  
			} catch (SQLException e1) {
				e1.printStackTrace();
			} 			

		}


	}//actionPerformed

	/**
	 * Checks is valid userName
	 * Usernames must be between 3-15 characters long
	 *  and contain at least one letter,
	 *  and can also use numbers or underscores
	 * @param userName
	 * @return true if valid, false if invalid
	 */
	public boolean validUserName(String userName){
		Pattern p = Pattern.compile("((?=.*[a-z[A-Z]])\\w{3,15})");
		Matcher m = p.matcher(userName);
		return m.matches();
	}


	/**
	 * Checks if valid password
	 * Passwords must be 6-32 characters long, 
	 * contain at least 1 digit, at least 1 letter, 
	 * and at least 1 special character (!?&$)
	 * @param password
	 * @return true if valid, false if invalid
	 */
	public boolean validPassword(String password){
		Pattern p = Pattern.compile("((?=.*\\d)(?=.*[a-z[A-Z]])(?=.*[!?&$]).{6,32})");
		Matcher m = p.matcher(password);
		return m.matches();
	}



}
