
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class holding main method
 * @author Danielle Zoe Aloicius
 *
 */
public class Main {

	//the GUI that the program opens initially
	private static LoginGUI login;

	public static void main(String[] args){

		//initialize FileWriter that will write to log file
		MySQLUtility.initLogWriter();
		try {
			MySQLUtility.connect(); //connect

		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		//check existence of database
		ResultSet rs;
		boolean dbExists=false;
		try {
			//gets list of all databases in table
			rs=((MySQLUtility.getConnection()).getMetaData()).getCatalogs();
			while (rs.next() ) { 
				//if our database is in list do nothing
				if((rs.getString("TABLE_CAT")).equals(Globals.DBNAME)){
					dbExists=true;
					break;
				}

			}//while
			
			//If database does not exist AND the log file has data in it,
			//assume system crash and
			//run all commands in logfile to re-create database.
			if(!dbExists){
				System.out.println("no database");
				if((MySQLUtility.getLogFile()).length() > 0L){
					MySQLUtility.runLog();
				}//if  	
			}//if

		} catch (SQLException e) {
			e.printStackTrace();
		}

		//calls method to read in stopwords from file		
		Globals.readStopWords("stopwords.txt");

        //creates login GUI
		login = new LoginGUI();


	}



}
