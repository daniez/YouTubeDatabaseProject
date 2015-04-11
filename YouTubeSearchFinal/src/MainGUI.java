import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * Empty GUI that has nothing but menu system
 * @author Danielle Zoe Aloicius
 *
 */
public class MainGUI extends JFrame {

	Container cp;

	//new Menu Bar
	protected JMenuBar menuBarMain = new JMenuBar();

	protected MainMenuHandler menuHandlerMain = new MainMenuHandler(this);

	//new JMenu
	protected JMenu constructMenu = new JMenu("Construct");

	//new JMenu Item 
	protected JMenuItem newLinksTable = new JMenuItem("Create New YouTube Links Table");
	protected JMenuItem updateTable = new JMenuItem("Update Your Existing YouTube Links Table");
	//protected JMenuItem searchYouTube = new JMenuItem("Search You Tube");

	//new JMenu
	protected JMenu searchMenu = new JMenu("Search");

	//new JMenu Items
	protected JMenuItem basicDatabaseSearch = new JMenuItem("Basic Database Search");
	protected JMenuItem advancedDatabaseSearch = new JMenuItem("Advanced Database Search");

	//new JMenu
	protected JMenu deleteMenu = new JMenu("Delete");

	//new JMenu Items
	protected JMenuItem deleteItem = new JMenuItem("Delete");
	//protected JMenuItem deleteSelected = new JMenuItem("Delete Selected");

	//new JMenu
	protected JMenu updateMenu = new JMenu("Update");

	//new JMenu Items
	protected JMenuItem updateCat = new JMenuItem("Update Category");

	//new JMenu
	protected JMenu printMenu = new JMenu("Print");

	//new JMenu Items
	protected JMenuItem printToFile = new JMenuItem("Print Results To File");

	//new JMenu
	protected JMenu signOutMenu = new JMenu("SignOut");

	//new JMenu Items
	protected JMenuItem signOutItem = new JMenuItem("Sign Out");


	public MainGUI(){
		
		//sets size of GUI to size of screen
		setSize(java.awt.Toolkit.getDefaultToolkit().getScreenSize());

		setTitle("You Tube Database Application");

		//create Menus
		createConstructMenu();
		createSearchMenu();
		createDeleteMenu();
		createUpdateMenu();
		createPrintMenu();
		createSignOutMenu();

        //disable certain menus since those actions are not initially possible 
		//until the user creates a LINKS table and runs a search of LINKS table
		deleteMenu.setEnabled(false);
		updateMenu.setEnabled(false);
		printMenu.setEnabled(false);

		setJMenuBar(menuBarMain);

		//get content pane and set layout
		cp= getContentPane();
		cp.setLayout(new BoxLayout(cp, BoxLayout.PAGE_AXIS));

		setVisible(true);
	}

	//creates Menu titled "Construct"
	private void createConstructMenu(){
		//add listener to item
		newLinksTable.addActionListener(menuHandlerMain);
		updateTable.addActionListener(menuHandlerMain);

		//add item to JMenu
		constructMenu.add(newLinksTable);
		constructMenu.add(updateTable);
		//database.add(searchYouTube);

		//add JMenu to JMenuBar
		menuBarMain.add(constructMenu);		
	}

	//creates Menu titled "Search"
	private void createSearchMenu() {

		//add listener to item
		basicDatabaseSearch.addActionListener(menuHandlerMain);
		advancedDatabaseSearch.addActionListener(menuHandlerMain);

		//add items to JMenu
		searchMenu.add(basicDatabaseSearch);
		searchMenu.add(advancedDatabaseSearch);

		//add JMenu to JMenuBar
		menuBarMain.add(searchMenu);				
	}

	//creates Menu titled "Delete"
	private void createDeleteMenu() {

		//add listener to item
		deleteItem.addActionListener(menuHandlerMain);

		//add items to JMenu
		deleteMenu.add(deleteItem);

		//add JMenu to JMenuBar
		menuBarMain.add(deleteMenu);				
	}

	//creates Menu titled "Update"
	private void createUpdateMenu() {

		//add listener to item
		updateCat.addActionListener(menuHandlerMain);


		//add items to JMenu
		updateMenu.add(updateCat);


		//add JMenu to JMenuBar
		menuBarMain.add(updateMenu);	


	}

	//creates Menu titled "Print"
	private void createPrintMenu() {

		//add listener to item
		printToFile.addActionListener(menuHandlerMain);


		//add items to JMenu
		printMenu.add(printToFile);


		//add JMenu to JMenuBar
		menuBarMain.add(printMenu);	


	}
    
	//creates Menu titled "SignOut"
	private void createSignOutMenu() {

		//add listener to item
		signOutItem.addActionListener(menuHandlerMain);


		//add items to JMenu
		signOutMenu.add(signOutItem);


		//add JMenu to JMenuBar
		menuBarMain.add(signOutMenu);				
	}

}
