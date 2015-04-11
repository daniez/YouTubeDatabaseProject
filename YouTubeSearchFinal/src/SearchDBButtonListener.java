import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Listener which performs searches on LINKS table
 * when user clicks on buttons in Search GUIs for Basic or Advanced Search
 * @author Danielle Zoe Aloicius
 *
 */
public class SearchDBButtonListener implements ActionListener {

	//basic and advanced Search GUIs
	private GUISearchBasic basicSearchGUI;
	private GUISearchAdv advSearchGUI;
	
	//hash map that hold result set from last search of LINkS table
	private HashMap<Integer, String> map;

    /**
     * First constructor
     * @param  GUI for basic search
     */
	public SearchDBButtonListener(GUISearchBasic sdb){
		this.basicSearchGUI=sdb;
	}

	/**
	 * Second constructor
	 * @param  GUI for advanced search
	 */
	public SearchDBButtonListener(GUISearchAdv asdb){
		this.advSearchGUI=asdb;
	}

    /**
     * Overrides method
     * Performs basic or advanced search of LINKS table
     */
	public void actionPerformed(ActionEvent e) {
		
		//gets name of button clicked
		String itemName = e.getActionCommand();

		//if button clicked was for basic search
		if(itemName.equals("Search")){
			basicSearchGUI.clearDisplay();
		
			//gets user's search words from GUI
			String keywords= basicSearchGUI.getKeyword();

            //takes out stop words and quotes, saves in array list
			ArrayList<String> searchWords = stopWordAndQuotes(keywords);
	
            //calls method to do basic search
			basicSearch(searchWords);

		}
        //if user clicked on Advanced Search
		else {

			advSearchGUI.clearDisplay();
			
			//gets user's keywords and category for search
			String description = advSearchGUI.getDescriptionKeyword();
			String category = advSearchGUI.getCategory();
			
			//takes out stop words and quotes, saves in array list
			ArrayList<String> searchWords = stopWordAndQuotes(description);
			
			//calls method to do advanced search
			advancedSearch(searchWords, category);

		}

	}

	/**
	 * Calls method to perform select query on LINKS table using querySearchWords
	 * adds results to display
	 * @param querySearchWords is ArrayList of search words which has had stopwords taken out
	 * 
	 */
	public void basicSearch(ArrayList <String> querySearchWords){

		try {
			map = MySQLUtility.select(querySearchWords);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		addResultsToDisplay(map);

	}

	/**
	 * Calls method to perform select query on LINKS table using querySearchWords and category
	 * adds results to display
	 * @param querySearchWords is ArrayList of search words which has had stopwords taken out
	 * @param category
	 * 
	 */
	public void advancedSearch(ArrayList <String> querySearchWords, String category){

		try {
			map = MySQLUtility.selectAdv(querySearchWords, category,advSearchGUI.getAndOr());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		addResultsToDisplayAdv(map);
	}	 

	/**
	 * Removes stopwords unless they are within a quoted String (ex "The Beatles")
	 * or unless stopword is only word in search.
	 * This will also work if user forgot to put quote at end of quoted string.
	 *
	 * @param keywords is a String of keywords and quoted phrases separated by whitespace
	 * @return ArrayList where each element in array is word or phrase
	 * Quoted phrases are kept together as 1 element of array
	 */
	public ArrayList<String> stopWordAndQuotes(String keywords){
        
		//will hold arrayList of words and phrases to be returned
		//Initially empty.
		ArrayList <String> searchWordsFixed = new ArrayList<String>(); 

		//tempString is StringBuffer to save keyword with any quoted strings taken out
		//then tempString will have all stopwords removed.
		StringBuffer tempString = new StringBuffer(keywords);
		
		//index of keyword to start searching for quotation mark
		int startIndex = 0;
		
		//while not at end of keyword string, advance startIndex pointer
		start:
			while(startIndex < keywords.length()){
				//when a quote is found
				if(keywords.charAt(startIndex)=='"'){
					
					int endIndex = startIndex+1;
					//look for ending quote
					while(endIndex < keywords.length()){
						
						if(keywords.charAt(endIndex)=='"'){
							//delete the substring between the quotes from temp string
							String subStringToDelete=keywords.substring(startIndex, endIndex); //includes first quote,not last
							int indexToDelete = tempString.indexOf(subStringToDelete);
							tempString = tempString.delete(indexToDelete, indexToDelete + subStringToDelete.length());
							//add quoted phrase to searchWordsFixed
							searchWordsFixed.add(keywords.substring(startIndex+1, endIndex));
							startIndex=endIndex+1;
							
							continue start;

						}
						endIndex++;
					}
				}

				startIndex++;
			}

        //create new String which is a copy of string with quoted phrases removed
		String temp = new String(tempString);
		String [] wordsSpaceSep = temp.split("\\s"); //split with spaces
		//iterate through and save string with stop words taken out
		//unless search is only one word
		//trim words
		for(int j = 0; j < wordsSpaceSep.length; j++){
			wordsSpaceSep[j] = wordsSpaceSep[j].trim();
			
			if(wordsSpaceSep[j].length()>0 && !wordsSpaceSep[j].contains("\"")&& (!isAStopWord(wordsSpaceSep[j]))){
				searchWordsFixed.add(wordsSpaceSep[j]);
			}
		}//for

		return searchWordsFixed; //return string of words without stop words and phrases
	}

    //searches the stopword array to check if word is a stopword
	private boolean isAStopWord(String word){
		ArrayList<String> stopWords= Globals.getStopWords();
		for (Iterator<String> iter = stopWords.iterator(); iter.hasNext(); ) {
			String  stopword= iter.next();
			//System.out.println(element);
			if(word.equals(stopword)){
				return true;
			}

		}//for
		return false;
	}

	/**
	 * Adds results of searching YouTube to TextArea in Basic Search GUI
	 * @param map contains result set of last search of LINKS table
	 */
	public void addResultsToDisplay(HashMap <Integer, String>map){
        //iterate through hash map containing result set
		for (Map.Entry<Integer, String> entry : map.entrySet()) {
			Integer key = entry.getKey();
			String value = entry.getValue();
			basicSearchGUI.appendToDisplay("ID=" +key + " "+ value + "\n");
		}
		System.out.println("map is empty= "+map.isEmpty());
	}

	/**
	 * Adds results of searching YouTube to TextArea in Advanced Search GUI
	 * @param map contains result set of last search of LINKS table
	 */
	public void addResultsToDisplayAdv(HashMap <Integer, String>map){
		//iterate through hash map containing result set
		for (Map.Entry<Integer, String> entry : map.entrySet()) {
			Integer key = entry.getKey();
			String value = entry.getValue();
			advSearchGUI.appendToDisplay("ID=" +key + " "+ value + "\n");

		}
		System.out.println("map is empty= "+map.isEmpty());

	}




}

