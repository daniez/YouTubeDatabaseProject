import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;

/**
 * Listener for Searching You Tube when user clicks on search button
 * @author Danielle Zoe Aloicius
 *
 */
public class SearchYTButtonListener implements ActionListener {

	//reference to GUI to search YouTube
	private GUISearchYT searchYouTubeGUI;

	/**
	 * Constructor for listener for Searching You Tube when user clicks on search button
	 * @param sg is GUI for Searching YouTube
	 */
	public SearchYTButtonListener(GUISearchYT sg){
		this.searchYouTubeGUI=sg;
	}

	/**
	 * Overrides actionPerformed--builds YouTUbe search query
	 * then calls method to search YouTube with this query and save and display results. 
	 */
	public void actionPerformed(ActionEvent e) {

		//clear search GUI display and add message
		searchYouTubeGUI.clearDisplay();
		searchYouTubeGUI.appendToDisplay("Searching...");

        //builds YouTube search query from user's search words from GUI
		String url = buildQueryURL(searchYouTubeGUI.getSearchText());
		System.out.println(url);
		
		//calls method to search, save in file and display results
		File links = LinkFinder.read(url, searchYouTubeGUI);

		//if no links were read/file is empty
		System.out.println("read");
		if (links == null){
			System.out.println("No links were read.");
			System.exit(0);
		}	
		try {
            //if user indicated to replace LINKS table
			//or LINKS table has never been created
			//create a new one
			if(Globals.getDeleteLINKSTableFlag() || !MySQLUtility.checkExistingLinksTable()){					
				MySQLUtility.createNewLinksTable();
			}	

            //update LINKS table with results of searching YouTube
			MySQLUtility.updateYouTubeDB(searchYouTubeGUI.getCategory());

		} catch (SQLException  e1) {
			e1.printStackTrace();
		}

	}

	/**
	 * Build a url query to search YouTube for query words
	 * @param text is a String containing query words or phrases, separated by spaces
	 * @return query url to search YouTube
	 */
	public String buildQueryURL(String text){
		String fullURLQuery = Globals.URLQUERY; //start with basic part of url query
		String[] searchWords = text.split("\\s"); //splits text around whitespace
		//iterate through search words adding them to query
		for (int index=0; index<searchWords.length; index++){
			fullURLQuery +=searchWords[index];
			if (index+1<searchWords.length)
				fullURLQuery += "+";
		}	

		return  fullURLQuery;
	}

}

