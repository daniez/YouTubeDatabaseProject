import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JComboBox;

/**
 * Global variables and classes
 * @author Danielle Zoe Aloicius
 *
 */
public class Globals {

	//constants to be used in the URL and query construction
	public static final String URLQUERY = "https://www.youtube.com/results?search_query=";	 
	public static final String DBNAME = "youtubedatabase";
	public static String TABLENAME = ".LINKS";

	//shared variables

	//ArrayList of stopwords
	private static ArrayList<String> stopWords = new ArrayList<String>();

	//ComboBox of categories from which user picks to describe search results
	private static JComboBox<String> categories;

	//Holds current user of database by username
	private static String currentUser="";

	//Flag if LINKS table should be overwritten with new search results
	private static boolean deleteLINKSTable = false;

    /**
     * Reads stopwords from file into array
     * 	Stopwords gotten from list on website http://www.ranks.nl/stopwords.
	 *   It is a modified copy of list formerly used by Google.
     * 
     */
	public static void readStopWords(String filename){
		String line="";

		try {
			BufferedReader reader=new BufferedReader(new FileReader(new File(filename)));
			line = reader.readLine();

			while (line != null) {
				//read line into stopWords List
				stopWords.add(line);
				line = reader.readLine(); 
			} // while
			reader.close();

		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

   /**
    * getter for stopword list
    * @return stopword arraylist
    */
	public static ArrayList<String> getStopWords(){
		return stopWords;
	}

	/**
	 * Initialized JComboBox of categories from which user picks to describe search results
	 */
	private static void initCategoryBox(){
		String[] categoryNames = {"arts", "health", "sports", "music", "movies", "television"};
		categories = new JComboBox<String>(categoryNames);
		categories.setEditable(true);

	}

	/**
	 * Getter for category JComboBox
	 * @return
	 */
	public static JComboBox<String> getCategoryBox(){
		initCategoryBox();
		return categories;
	}

	/**
	 * Sets currentUser
	 */
	public static void setCurrentUser(String user){
		currentUser=user;
	}
    
	/**
	 * getter for CurrentUser
	 * @return String holding username of current user
	 */
	public static String getCurrentUser(){
		return currentUser;
	}

	/**
	 * Clears contents of CurrentUser
	 */
	public static void clearCurrentUser(){
		currentUser="";
	}

	/**
	 * Getter for deleteFlag
	 * @return boolean 
	 */
	public static boolean getDeleteLINKSTableFlag(){
		return deleteLINKSTable;
	}

	/**
	 * setter for deleteFlag
	 * @param b = boolean value of deleteFlag
	 */
	public static void setDeleteLINKSTableFlag(boolean b){
		deleteLINKSTable = b;
	}



}
