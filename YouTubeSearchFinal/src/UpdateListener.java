import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * Listener to be called when user clicks on one of the buttons
 * in GUI for updating records from user's LINKS table
 * which is their table of stored YouTube searches.
 * @author Danielle Zoe Aloicius
 *
 */
public class UpdateListener implements ActionListener {

	//reference to GUI for updating records in LINKS table
	private GUIUpdate updateGUI;

    /**
     * Constructor for UpdateListener
     * @param updateGUI is GUI for updating LINKS table
     */
	public UpdateListener(GUIUpdate updateGUI){
		this.updateGUI = updateGUI;
	}

    /**
     * overrides method 
     * Called when user clicks button in Update GUI
     * Updates record with new Category chosen in GUI
     */
	public void actionPerformed(ActionEvent e) {	

		//get id and category from GUI for updating
		int id = (int)updateGUI.getID();
		String catToUpdate=updateGUI.getCategory();
		
		try {
			//call mySQL update method that takes id and category and updates that record
			MySQLUtility.update(id, catToUpdate);
			updateGUI.dispose();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

}

