import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * MySQLUtility holds all methods used to interact with database
 * @author Danielle Zoe Aloicius
 *
 */
public class MySQLUtility {
  
	//Connection to connect to database
	private static Connection con=null;
	
	//When a search is done on the user's LINKS file, this HashMap will be used to store
	//the result set, since the result set is closed when the Statement used in
	//the query is closed.
	private static HashMap<Integer, String> results = new HashMap<Integer, String> ();

	//variables for log file used to recreate db in case of crash
	private static String logName = "log.txt";
	private static File file;
	private static FileWriter fw;
	private static BufferedWriter bw;


	/**
	 * Initialized the variables to write to the log file
	 * called when program begins
	 */
	public static void initLogWriter(){
		try {
			file = new File(logName);
			fw = new FileWriter(file, true); 
			bw = new BufferedWriter(fw);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//call this every time the database is changed with a new SQL statement
	//write it  to log file
	private static void addToLog(String statement){
		try {
			bw.write(statement);
			bw.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//close log file
	public static void closeLog(){
		try {
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * runLog reads every line in log file
	 * and executes each statement to return database to previous state
	 * before crash.
	 * @throws SQLException
	 */
	public static void runLog() throws SQLException{
		BufferedReader reader;
		Statement stmt = null;
		try {
			stmt = con.createStatement();

			reader = new BufferedReader(new FileReader(new File(logName)));
			String line = reader.readLine();
			while(line !=null){
				stmt.execute(line);
				line = reader.readLine();
			}
			file=new File(logName); //does not create new empty file

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (stmt != null) { stmt.close(); }
		}

	}

   /**
    * connects to MySQL database on localhost, port 3306
    * with user name: root
    * password: root
    */
	public static void connect() throws SQLException {		
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "root");
		System.out.println("Connected to database");
	}

	/**
	 * Creates new database if database does not exist
	 * Database has name DBNAME given as a constant in Globals class
	 * @throws SQLException
	 */
	public static void createNewYouTubeDB() throws SQLException {  
      
		Statement stmt = null;
		try {
			//SQL statement strings
			String createString ="CREATE DATABASE IF NOT EXISTS " + Globals.DBNAME ;
			String useString="USE " + Globals.DBNAME;
			
			//create and execute statements
			stmt = con.createStatement();
			stmt.executeUpdate(createString); 
			stmt.executeUpdate(useString);		        

			//write SQL commands to log
			addToLog(createString);
			addToLog(useString);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (stmt != null) { stmt.close(); }
		}
	}

	/**
	 * Creates a new LINKS table for current user, if it does not exist.
	 * Each user has own LINKS table. Format of name of LINKS table is LINKS_CurrentUser
	 * LINKS table holds user's searches of YouTube.
	 * @throws SQLException
	 */
	public static void createNewLinksTable() throws SQLException {  
		//SQL statement strings
		String dropString = "DROP TABLE IF EXISTS "+ Globals.DBNAME + ".LINKS_" + Globals.getCurrentUser();
		//number of varchars == number of characters in string
		String createString =
				"create table " + Globals.DBNAME +
				".LINKS_"+Globals.getCurrentUser()+ 

				" (id MEDIUMINT NOT NULL AUTO_INCREMENT,"
				+ "SHORT_LINK varchar(1024) NOT NULL, " +
				"DESCRIPTION varchar(1024), " +
				"DATE_ADDED_TO_RESULTS DATETIME DEFAULT CURRENT_TIMESTAMP, " +
				"CATEGORY varchar(1024), " + 
				"PRIMARY KEY (id))" ; //*****truncate or error message//"IF NOT EXISTS"+
		String useString = "USE " + Globals.DBNAME;
		
		//run statements and save statements in log
		Statement stmt = null;
		try {
			stmt = con.createStatement();

			stmt.executeUpdate(dropString);		        
			stmt.executeUpdate(createString);
			stmt.executeUpdate(useString);

			//write to log
			addToLog(dropString);
			addToLog(createString);
			addToLog(useString);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (stmt != null) { stmt.close(); }
		}
	}

	/**
	 * Checks existence of user's LINKS table
	 * @return true if exists, false if it does not exist
	 * @throws SQLException
	 */
	public static boolean checkExistingLinksTable() throws SQLException{
		
		Statement stmt = null;
		ResultSet rs = null;

		//SQL statements
		String useString="USE " + Globals.DBNAME;
		String showString ="SHOW TABLES LIKE 'LINKS_"+ Globals.getCurrentUser() + "'";
		
		try {
			//execute statements
			stmt = con.createStatement();
			stmt.executeUpdate(useString);
			rs= stmt.executeQuery(showString);	
			if (!rs.next() ) { //if no LINKS table
				//System.out.println("no data");
				return false; 
			}		        	     

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return true;

	}


	/**
	 * Reads file links.html that was created in LinkFinder class
	 * which contains results of search of YouTube.
	 * Saves these links in user's LINKS table.
	 * @param category
	 * @throws SQLException
	 * Preconditions: links.html exists, database and LINKS table exist
	 */
	public static void updateYouTubeDB(String category) throws SQLException {
		String line="";
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			
			//fields
			String link = "";
			String description = "";
			
			String insertString; //will hold SQL statement

			//open links.html and iterate through
			BufferedReader reader=new BufferedReader(new FileReader(new File("links.html")));
			line = reader.readLine();         
			nextline:
				while (line != null) {

					link = line;
					//if the link is too long it will cause an exception
					//though it really should not be longer than 1024 char, that would be nuts
					//But just in case..
					//can't truncate it because then it won't work, so skip
					if (link.length() > 1024){
						continue nextline;
					}


					line = reader.readLine(); 

					//get the next line which is description
					//if the description is too long, trim it
					if (line != null){
						description = line;
						description=description.substring(0, Math.min(description.length(), 1024));
					}
                    
					//run SQL to insert into table
					insertString = "insert into " + Globals.DBNAME + ".LINKS_"+Globals.getCurrentUser() + " (SHORT_LINK, DESCRIPTION, CATEGORY ) values('" +link+ "', '"+ description+ "', '"+category +"')";
					line = reader.readLine(); 

					stmt.executeUpdate(insertString);	   
					addToLog(insertString);

				} // while
			reader.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();


		} finally {
			
			if (stmt != null) {
				stmt.close();
			}
		}
	}


	/**
	 * Runs a select statement using user's query words
	 * @param queryWords
	 * @return result set returned by SQL statement in form of HashMap
	 * where key=id and value=string containing description
	 * Also change static variable results
	 * @throws SQLException
	 * Precondition: user's LINKS table exists
	 */
	public static HashMap<Integer, String> select(ArrayList <String> queryWords) throws SQLException {

		Statement stmt = null;
		ResultSet rs = null;
        //SQL query
		String query = "select * from " + Globals.DBNAME + ".LINKS_"+Globals.getCurrentUser() + " where DESCRIPTION LIKE '%";
		
		//iterate through query string provided by user, add to SQL select statement
		for (Iterator<String> iter = queryWords.iterator(); iter.hasNext(); ) {
			
			String nextQueryWord = iter.next();			
			query+=nextQueryWord;
			
			if (iter.hasNext())
				query+="%' AND DESCRIPTION LIKE '%";
			else //last word
				query+="%'";
		}

		System.out.println(query);


		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);

			//clear previous result set from hash map results
			results.clear();
			
			//if no results of query
			if (!rs.isBeforeFirst() ) {    
				results.put(new Integer(0), "This search returned no results"); 
			}

			//if there are results, save in hash map
			while (rs.next()) {
				Integer i= new Integer(rs.getInt("id"));
				String s= "\tLink=https://www.youtube.com/watch?v=" + rs.getString("SHORT_LINK")+ "\tDescription=" +rs.getString("DESCRIPTION")+
						"\tCategory=" + rs.getString("CATEGORY");

				results.put(i, s);
			}


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();


		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return results;
	}

	
	/**
	 * Runs a select statement using user's query words and category user has selected
	 * @param queryWords, category, andOr (user can choose and/or)
	 * @return result set returned by SQL statement in form of HashMap
	 * where key=id and value=string containing description
	 * Also change static variable results
	 * @throws SQLException
	 * Precondition: user's LINKS table exists
	 */
	public static HashMap<Integer, String> selectAdv(ArrayList <String> queryWords, String category, String andOr) throws SQLException {

		Statement stmt = null;
		ResultSet rs = null;
		String query = "";

		//iterate through user's query words
		for (Iterator<String> iter = queryWords.iterator(); iter.hasNext(); ) {
			//add to beginning of query string
			if(query.length()<1){
				query+="select * from " + Globals.DBNAME + ".LINKS_"+Globals.getCurrentUser()  + " where DESCRIPTION LIKE '%";
			}   
			
			String nextQueryWord = iter.next();
			
			query+=nextQueryWord;
			
			//not the last word
			if (iter.hasNext())
				query+="%' AND DESCRIPTION LIKE '%";
			
			else{ //last word
				query+="%'";
				if(category.length() > 0)
					query+=" "+andOr+" "; //user picks and/or
			}
		}
        //if user picked category, add to query
		if (category.length() > 0 ){
			if(query.length()<1){
				query+="select * from " + Globals.DBNAME + ".LINKS_"+Globals.getCurrentUser() + " where";
			}
			query+=" CATEGORY='" + category + "'";
		}

		System.out.println(query);

		//clear old result set from hash map
		results.clear();

		if(query.length() <1){
			results.put(new Integer(0), "No search choices were selected");
			return results;
		}

		try {
			//execute select statement
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);

			//if no results
			if (!rs.isBeforeFirst() ) {    
				results.put(new Integer(0), "This search returned no results");
				 
			}

			//if there are results add them to hash map
			while (rs.next()) {
				Integer i= new Integer(rs.getInt("id"));
				String s= "\tLink=https://www.youtube.com/watch?v=" + rs.getString("SHORT_LINK")+ "\tDescription=" +rs.getString("DESCRIPTION")+
						"\tCategory=" + rs.getString("CATEGORY");

				results.put(i, s);
			}


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();


		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return results;
	}

	/**
	 * Displays results in hashmap to console
	 * @param map=HashMap holding result set from last select query
	 */
	public static void viewResults(HashMap<Integer, String> map) {

		for (Map.Entry<Integer, String> entry : map.entrySet()) {
			Integer key = entry.getKey();
			String value = entry.getValue();
			System.out.println(key + " "+ value);
		}

	} 

	/**
	 * Checks if user exists in User table
	 * @param user
	 * @return true if exists, false if does not exist
	 * @throws SQLException
	 */
	public static boolean existingUser(String user) throws SQLException{
		Statement stmt = null;
		ResultSet rs = null;
		//SQl statement
		String query = "select * from " + Globals.DBNAME + ".USERS WHERE USERNAME='"+user+"'";  
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
            
			//if no user by that name in database
			if (!rs.next() ) { 
				return false; 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return true;

	}

	/**
	 * Checks if password exists in User table, for specified user
	 * @param user, password
	 * @return true if exists, false if does not exist
	 * @throws SQLException
	 */
	public static boolean correctPassword(String user, String password) throws SQLException{
		Statement stmt = null;
		ResultSet rs = null;
		//SQl statement for case sensitive search
		String query = "select * from " + Globals.DBNAME + ".USERS WHERE USERNAME = '"+user+"' AND BINARY PASSWORD='"+password+"'";  
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			//if password is not found for that user
			if (!rs.isBeforeFirst() ) {    
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if (stmt != null) { stmt.close(); }
		}
		return true;

	}

    /**
     * creates a Users table which holds usernames and passwords if it does not exist
     * @throws SQLException
     */
	public static void createUsersTable() throws SQLException {
		
		//SQL statements
		String useString = "USE " + Globals.DBNAME;
		String createString =
				"create table IF NOT EXISTS " + Globals.DBNAME +
				".USERS" + 
				"(USERNAME varchar(100) NOT NULL," +
				"PASSWORD varchar(100)," +

				        "PRIMARY KEY (USERNAME))" ; 
		Statement stmt = null;
		try {
			//execute statements to create table
			stmt = con.createStatement();
			stmt.executeUpdate(useString);		        
			stmt.executeUpdate(createString);

		} catch (SQLException e) {e.printStackTrace();
		} finally {
			if (stmt != null) { stmt.close(); }
		}

		addToLog(useString);
		addToLog(createString);
	}

    /**
     * Add new user to USERS table
     * @param newUserName
     * @param newPassword
     * @throws SQLException
     * Precondition: database is created
     */
	public static void updateUsers(String newUserName, String newPassword) throws SQLException{
		//SQl statements
		String useString = "USE " + Globals.DBNAME;
		Statement stmt = null;
		String insertUserString = "insert into " + Globals.DBNAME + ".USERS" +
				" (USERNAME, PASSWORD ) values('" +newUserName+ "', '"+ newPassword +"')";
		try {
			//execute statements creating table
			stmt = con.createStatement();
			stmt.execute(useString);
			stmt.executeUpdate(insertUserString);	     

		} catch (Exception e) {
			e.printStackTrace();


		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		addToLog(useString);
		addToLog(insertUserString);


	}
    
	/**
	 * Gets HashMap that holds result set of last search of LINKS table
	 * @return
	 */
	public static HashMap<Integer, String> getResults(){
		//System.out.println("results is empty= "+results.isEmpty());
		return results;
	}

	/**
	 * Get Connection object holding connection to database
	 * @return
	 */
	public static Connection getConnection(){
		return con;
	}

	/**
	 * Get log file
	 * @return
	 */
	public static File getLogFile(){
		return file;
	}


	/**
	 * Deletes records from user's LINKS table
	 * @param i is id number of record to be deleted
	 * @throws SQLException
	 */
	public static void  delete(int i) throws SQLException {

		Statement stmt = null;
		ResultSet rs = null;

        //SQL statements for deleting records
		String useString="USE " + Globals.DBNAME;
		String deleteString = "delete  from LINKS_"+Globals.getCurrentUser() + " where id like '"+i+"'";

		try {
			//execute statements and add to log
			stmt = con.createStatement();
			stmt.execute(useString);
			stmt.executeUpdate(deleteString);
			addToLog(useString);
			addToLog(deleteString);

		} catch (Exception e) {
			e.printStackTrace();


		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}

	}   

	/**
	 * Updates category of record in LINKS table
	 * @param i is id of record to be updated
	 * @param newCategory is the new category for record
	 * @throws SQLException
	 *
	 */
	public static void  update(int i, String newCategory) throws SQLException {

		Statement stmt = null;
		ResultSet rs = null;

		//SQL statements
		String useString="USE " + Globals.DBNAME;;
		String updateString = "UPDATE LINKS_"+Globals.getCurrentUser() + " SET CATEGORY='"+newCategory+ 
				"' where id like '"+i+"'";

		try {
			//run statements to update LINKS table and add to log
			stmt = con.createStatement();
			stmt.execute(useString);
			stmt.executeUpdate(updateString);
			addToLog(useString);
			addToLog(updateString);

		} catch (Exception e) {
			e.printStackTrace();


		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}

	}







}
