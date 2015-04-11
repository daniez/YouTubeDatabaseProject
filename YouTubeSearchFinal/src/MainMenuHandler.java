import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JOptionPane;

/**
 * MainMenuHandler is called when menu items are chosen
 * on GUIs.
 * @author Danielle Zoe Aloicius
 *
 */
public class MainMenuHandler implements ActionListener {

	//GUIs that are called from Main GUI
	private MainGUI main;
	private GUISearchYT searchYouTubeGUI;
	private GUISearchBasic searchBasicGUI;
	private GUISearchAdv searchAdvGUI;
	private GUIUpdate updateGUI;
	private GUIDelete deleteGUI;

    /**
     * Constructor for MainMenuHandler
     * @param reference to MainGUI that calls this
     */
	public MainMenuHandler(MainGUI mg){
		this.main = mg;
	}

	/**
	 * Overridden method that is called when menu items are clicked
	 */
	public void actionPerformed(ActionEvent e) {
		
		//get which item clicked
		String itemName = e.getActionCommand();

		//If "Create New YouTube Links Table" is clicked in Construct menu:
		//Search YouTube
		//replacing old LINKS table (which is user's table holding results of YouTube searches)
		//with these new YouTube search results.
		if(itemName.equals("Create New YouTube Links Table")){
			JOptionPane.showMessageDialog(main,
					"Any YouTube search results you get next will overwrite any previous table of searches!",
					"",
					JOptionPane.WARNING_MESSAGE);
			Globals.setDeleteLINKSTableFlag(true);
			searchYouTubeGUI = new GUISearchYT();


		}
		//if "Update Your Existing YouTube Links Table" is clicked in Construct menu:
		//Search YouTube
		//updating LINKS table (which is user's table holding results of YouTube searches)
		//to include these new YouTube search results.
		else if (itemName.equals("Update Your Existing YouTube Links Table")){
			JOptionPane.showMessageDialog(main,
					"Any YouTube search results you get next will update your existing table of searches, if you have one.");
			Globals.setDeleteLINKSTableFlag(false);
			searchYouTubeGUI = new GUISearchYT();

		}

        //if "Basic Database Search" is clicked in Search Menu
		//check if user's LINKS table exists and if it does
		//the user can search it with a simple keyword search
		else if (itemName.equals("Basic Database Search")){
			try {
				if(MySQLUtility.checkExistingLinksTable()){
					main.dispose();
					searchBasicGUI = new GUISearchBasic();
				}
				else{
					JOptionPane.showMessageDialog(main,
							"You don't have a LINKS table to search. Go to Construct menu and choose \"Create New YouTube Links Table\"");

				}
			} catch (HeadlessException e1) {
				e1.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}	
		
		//if "Advanced Database Search" is clicked in Search menu
		//check if user's LINKS table exists and if it does
		//the user can search it with an advanced search
		else if (itemName.equals("Advanced Database Search")){
			try {
				if(MySQLUtility.checkExistingLinksTable()){
					main.dispose();
					searchAdvGUI = new GUISearchAdv();
				}
				else{
					JOptionPane.showMessageDialog(main,
							"You don't have a database to search. Choose \"Create New YouTube Links Table\"");

				}
			} catch (HeadlessException e1) {
				e1.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		//if "Delete" item is clicked in Delete menu:
		//call method to delete searched records
		//from user's LINKS table
		else if (itemName.equals("Delete")){
			delete();
		}

		//if "Update Category' item is clicked in Update menu
		//call method to update category of
		//searched records in user's LINKS table
		else if (itemName.equals("Update Category")){
			update();
		}
		
		//if "Print Results To File" is clicked on Print menu
		//call method to print to file
		//the last search of user's LINKS table
		else if (itemName.equals("Print Results To File")){
			printReport();
		}
		
		//if "Sign Out" is clicked in SignOut menu
		//clear the current window,
		//close the log and exit application
		else if (itemName.equals("Sign Out")){
			Globals.clearCurrentUser();
			MySQLUtility.closeLog();
			System.exit(0);

		}
	}

	//method to print database search results to file
	private void printReport(){

		//gets a reference to the HashMap holding the last search results
		HashMap<Integer, String> resultsPrint = MySQLUtility.getResults();

		//iterate through results
		Iterator it = resultsPrint.entrySet().iterator();
		
		//If no results, show message
		if (!it.hasNext()){
			JOptionPane.showMessageDialog(main,
					"There are no results to print.");
		}
		
		//If there are results print them to file "results.txt"
		else{
			try {
				FileWriter fw;

				fw = new FileWriter(new File("results.txt"));
				BufferedWriter bw = new BufferedWriter(fw);
				JOptionPane.showMessageDialog(main,
						"This will print search results to results.txt");
				
				//iterate through results and print
				while (it.hasNext()) {

					Map.Entry pairs = (Map.Entry)it.next();
					bw.append(pairs.getKey() + " = " + pairs.getValue());
					bw.newLine();
				}
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	//calls GUI for deletion of records in LINKS table
	private void delete(){
		deleteGUI = new GUIDelete();		
	}

	//calls GUI for updating records in LINKS table
	public  void  update(){
		updateGUI = new GUIUpdate();
	}

}
