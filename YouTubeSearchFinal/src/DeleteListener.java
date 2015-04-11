import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 * Listener to be called when user clicks on one of the buttons
 * in GUI for deleting records from user's LINKS table
 * which is their table of stored YouTube searches.
 * @author Danielle Zoe Aloicius
 *
 */
public class DeleteListener implements ActionListener {

	//reference to GUI for deleting records from user's LINKS table 
	private GUIDelete deleteGUI;

	/**
	 * Constructor for listener for buttons in DeleteGUI
	 * @param reference to GUI used for deleting records
	 */
	public DeleteListener(GUIDelete deleteGUI){
		this.deleteGUI = deleteGUI;
	}

    /**
     * overridden method called when delete buttons are clicked
     */
	public void actionPerformed(ActionEvent e) {
		
		//which button was clicked
		String itemName = e.getActionCommand();

		//if "Delete Selected" button was clicked choose that method
		//and close window
		if(itemName.equals("Delete Selected")){
			deleteSelected();
			deleteGUI.dispose();	

		}	
		//if "Delete All" button was clicked choose that method
		//and close window
		else if(itemName.equals("Delete All")){
			deleteAll();
			deleteGUI.dispose();
		}
	}
	
	//deletes all records from user's LINKS table from the user's last set of search results
	private void deleteAll(){
        
		//gets a reference to the HashMap holding the last search results
		HashMap<Integer, String> resultsToDelete = MySQLUtility.getResults();

		//iterate through results
		Iterator it = resultsToDelete.entrySet().iterator();
		
		//if there are no results, show message
		if (!it.hasNext()){
			JOptionPane.showMessageDialog(deleteGUI,
					"There are no results to delete.");
		}
		
		//if there are results, show message and
		//call MySQLUtility method to delete them
		else{

			JOptionPane.showMessageDialog(deleteGUI,
					"This will delete all entries in search result");
			while (it.hasNext()) {
				Map.Entry pairs = (Map.Entry)it.next();
				
				try {
					MySQLUtility.delete((int)pairs.getKey());
				} catch (SQLException e) {
					e.printStackTrace();
				}

                //clear all results in HashMap that holds search results
				//since these will deleted from database
				it.remove(); 
			}

		}

	}

	//deletes 1 selected record from user's LINKS table from the user's last set of search results
	private void deleteSelected(){

		JOptionPane.showMessageDialog(deleteGUI,
				"This will delete selected entries in search result");
		
		//get the ID of the record to be deleted from deleteGUI
		int id = deleteGUI.getID();
		try {
			MySQLUtility.delete(id);
			//remove deleted result from the HashMap that holds search results
			(MySQLUtility.getResults()).remove(new Integer(id));
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}




}

