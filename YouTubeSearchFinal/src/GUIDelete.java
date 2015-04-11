import java.awt.Button;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * GUI for user to choose which records to delete from last search of LINKS table
 * Or user can delete all results from last search of LINKS table
 * (LINKS table holds results of YouTube searches)
 * @author Danielle Zoe Aloicius
 *
 */
public class GUIDelete extends JFrame{

	//vars for panel with drop down list of IDs of records in last search result set
	private JLabel recToDeleteLabel = new JLabel("Enter the ID number of the record to be deleted   ");
	private JComboBox<Integer> recToDeleteByIDBox = new JComboBox<Integer>();
	private JPanel idDeletePanel = new JPanel();	

	//vars for button panel
	private Button deleteSelectedBtn = new Button("Delete Selected");
	private Button deleteAllBtn = new Button("Delete All");
	private JPanel deleteButtonPanel = new JPanel();
	private DeleteListener deleteListener = new DeleteListener(this);

	//panel to put all panels
	private JPanel largeDeletePanel = new JPanel();

	//this GUIs content pane
	private Container deleteContentPane;

	/**
	 * Constructor for GUI to delete from LINKS table
	 */
	public GUIDelete(){

		setSize(500, 400);
		setLocation(300, 100);
		setTitle("Delete");

		//initialize buttons
		deleteSelectedBtn.addActionListener(deleteListener);
		deleteAllBtn.addActionListener(deleteListener);
		deleteSelectedBtn.setBackground(Color.RED);
		deleteAllBtn.setBackground(Color.RED);

		//get content pane and set layout
		deleteContentPane=getContentPane();
		deleteContentPane.setLayout(new BoxLayout(deleteContentPane, BoxLayout.LINE_AXIS));

		//get only the IDs from last search result and populate drop down list
		Integer[] validIDs = getIDsFromResults();
		recToDeleteByIDBox = new JComboBox<Integer>(validIDs);
		recToDeleteByIDBox.setEditable(false);

		//add components to large Panel, add that to content pane with spacing between
		largeDeletePanel.setLayout(new BoxLayout(largeDeletePanel, BoxLayout.PAGE_AXIS));

		//add components to idPanel and add idPanel to large Panel
		idDeletePanel.setLayout(new BoxLayout(idDeletePanel, BoxLayout.LINE_AXIS));
		idDeletePanel.add(recToDeleteLabel);
		idDeletePanel.add(recToDeleteByIDBox);
		largeDeletePanel.add(idDeletePanel);
		largeDeletePanel.add(Box.createRigidArea(new Dimension(0,80)));

		//add buttons to buttonPanel, add button Panel to large panel
		deleteButtonPanel.setLayout(new BoxLayout(deleteButtonPanel, BoxLayout.LINE_AXIS));
		deleteButtonPanel.add(Box.createRigidArea(new Dimension(30,0)));
		deleteButtonPanel.add(deleteSelectedBtn);
		deleteButtonPanel.add(Box.createRigidArea(new Dimension(50,0)));
		deleteButtonPanel.add(deleteAllBtn);
		largeDeletePanel.add(deleteButtonPanel); 

		//add large panel to content pane
		deleteContentPane.add(Box.createRigidArea(new Dimension(20,0)));
		deleteContentPane.add(largeDeletePanel);
		
		//set border
		idDeletePanel.setBorder(BorderFactory.createEtchedBorder());

		setSizes();
		align();

		setVisible(true);
	}

	/**
	 * Gets ID of record chosen by user
	 * @return ID chosen by user in ComboBox
	 */
	public int getID(){
		int id;

		id = (int) recToDeleteByIDBox.getSelectedItem();

		return id;		
	}

    //get IDs of results from last search of LINKS table
	private Integer[] getIDsFromResults(){
        
		//get reference to hash map that stores result set of last search
		HashMap<Integer, String> results = MySQLUtility.getResults();
		
		//iterate through hash map and save ids in array
		Integer[] validIDsFromResults= new Integer[results.size()];
		Iterator it = results.entrySet().iterator();
		int i =0;
		while (it.hasNext()){
			Map.Entry pairs = (Map.Entry)it.next();

			validIDsFromResults[i]=(Integer) pairs.getKey();
			i++;
		}
		return validIDsFromResults; //return array of IDs
	}

    //method to set sizes of components
	private void setSizes(){
		//panels

		idDeletePanel.setMinimumSize(new Dimension(400, 50));
		idDeletePanel.setPreferredSize(new Dimension(400, 50));
		idDeletePanel.setMaximumSize(new Dimension(400, 50));


		deleteButtonPanel.setMinimumSize(new Dimension(320, 70));
		deleteButtonPanel.setPreferredSize(new Dimension(320, 70));
		deleteButtonPanel.setMaximumSize(new Dimension(320,70));


		largeDeletePanel.setMinimumSize(new Dimension(475, 250));
		largeDeletePanel.setPreferredSize(new Dimension(475, 250));
		largeDeletePanel.setMaximumSize(new Dimension(475,250));


		//textfields
		recToDeleteByIDBox.setMinimumSize(new Dimension(50, 20));
		recToDeleteByIDBox.setPreferredSize(new Dimension(50, 20));
		recToDeleteByIDBox.setMaximumSize(new Dimension(50,20));

		//buttons
		deleteSelectedBtn.setMinimumSize(new Dimension(120, 50));
		deleteSelectedBtn.setPreferredSize(new Dimension(120, 50));
		deleteSelectedBtn.setMaximumSize(new Dimension(120,50));

		deleteAllBtn.setMinimumSize(new Dimension(120, 50));
		deleteAllBtn.setPreferredSize(new Dimension(120, 50));
		deleteAllBtn.setMaximumSize(new Dimension(120,50));

	}
    
	//method to set alignment of components
	private void align(){

		idDeletePanel.setAlignmentX(LEFT_ALIGNMENT);
		deleteButtonPanel.setAlignmentX(LEFT_ALIGNMENT);

	}


}
