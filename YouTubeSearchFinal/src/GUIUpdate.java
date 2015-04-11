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
 * GUI for user to choose record to update from last search of LINKS table.
 * User chooses new category in which to place record.
 * (LINKS table holds results of YouTube searches)
 * @author Danielle Zoe Aloicius
 *
 */
public class GUIUpdate extends JFrame{

	//vars for panel with drop down list of IDs
	private JLabel recToUpdateLabel = new JLabel("Enter the ID number of the record to be updated   ");
	private JComboBox<Integer> recToUpdateByID = new JComboBox<Integer>();
	private JPanel idUpdtePanel = new JPanel();

	//vars for panel with category drop down
	private JComboBox<String> categoryUpdteBox =Globals.getCategoryBox();
	private JLabel categoryUpdteLabel = new JLabel("Update with new category");	
	private JPanel categoryUpdtePanel = new JPanel();

	//variables for button Panel
	private Button updateBtn = new Button("Update");
	private JPanel updteButtonPanel = new JPanel();
	private UpdateListener updteListener = new UpdateListener(this);

	//panel to put all panels
	private JPanel largeUpdtePanel = new JPanel();

	//create Content Pane for this GUI to put large panel
	private Container updteContentPane;

	/**
	 * Constructor for GUI to update record from LINKS table
	 */
	public GUIUpdate(){

		setSize(500, 500);
		setLocation(300, 100);
		setTitle("Update");

		//initialize button
		updateBtn.addActionListener(updteListener);
		updateBtn.setBackground(Color.RED);

		//get content pane and set layout
		updteContentPane=getContentPane();
		updteContentPane.setLayout(new BoxLayout(updteContentPane, BoxLayout.LINE_AXIS));

		//get only the IDs from last search result and populate drop down list
		Integer[] validIDs = getIDsFromResults();
		recToUpdateByID = new JComboBox<Integer>(validIDs);
		recToUpdateByID.setEditable(false);

		//add components to large Panel add that to content pane with spacing between
		largeUpdtePanel.setLayout(new BoxLayout(largeUpdtePanel, BoxLayout.PAGE_AXIS));

		//add components to id panel and add that to large panel
		idUpdtePanel.setLayout(new BoxLayout(idUpdtePanel, BoxLayout.LINE_AXIS));
		idUpdtePanel.add(recToUpdateLabel);
		idUpdtePanel.add(recToUpdateByID);
		largeUpdtePanel.add(idUpdtePanel);
		largeUpdtePanel.add(Box.createRigidArea(new Dimension(0,80)));
		
		//add components to category panel and add that to large panel
		categoryUpdtePanel.setLayout(new BoxLayout(categoryUpdtePanel, BoxLayout.PAGE_AXIS));
		categoryUpdtePanel.add(categoryUpdteLabel);
		categoryUpdtePanel.add(categoryUpdteBox);
		largeUpdtePanel.add(categoryUpdtePanel);
		largeUpdtePanel.add(Box.createRigidArea(new Dimension(0,80)));

		//add button to panel and add that to large panel
		updteButtonPanel.add(updateBtn);
		largeUpdtePanel.add(updteButtonPanel); 

		//add large panel to content pane
		updteContentPane.add(Box.createRigidArea(new Dimension(20,0)));
		updteContentPane.add(largeUpdtePanel);
		
		//add borders
		categoryUpdtePanel.setBorder(BorderFactory.createEtchedBorder());
		idUpdtePanel.setBorder(BorderFactory.createEtchedBorder());

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

		id = (int) recToUpdateByID.getSelectedItem();

		return id;		
	}

	/**
	 * Gets new category in which user wishes to save chosen result
	 * @return new category
	 */
	public String getCategory(){
		return (String) categoryUpdteBox.getSelectedItem();
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
		categoryUpdtePanel.setMinimumSize(new Dimension(400, 100));
		categoryUpdtePanel.setPreferredSize(new Dimension(400, 100));
		categoryUpdtePanel.setMaximumSize(new Dimension(400, 100));

		idUpdtePanel.setMinimumSize(new Dimension(400, 50));
		idUpdtePanel.setPreferredSize(new Dimension(400, 50));
		idUpdtePanel.setMaximumSize(new Dimension(400, 50));

		updteButtonPanel.setMinimumSize(new Dimension(100, 70));
		updteButtonPanel.setPreferredSize(new Dimension(100, 70));
		updteButtonPanel.setMaximumSize(new Dimension(100,70));

		largeUpdtePanel.setMinimumSize(new Dimension(475, 400));
		largeUpdtePanel.setPreferredSize(new Dimension(475, 400));
		largeUpdtePanel.setMaximumSize(new Dimension(475,400));

		//textfields
		recToUpdateByID.setMinimumSize(new Dimension(50, 20));
		recToUpdateByID.setPreferredSize(new Dimension(50, 20));
		recToUpdateByID.setMaximumSize(new Dimension(50,20));

        //JCombo box
		categoryUpdteBox.setMinimumSize(new Dimension(300, 20));
		categoryUpdteBox.setPreferredSize(new Dimension(300, 20));
		categoryUpdteBox.setMaximumSize(new Dimension(300,20));

		//buttons
		updateBtn.setMinimumSize(new Dimension(80, 50));
		updateBtn.setPreferredSize(new Dimension(80, 50));
		updateBtn.setMaximumSize(new Dimension(80,50));
	}

	//method to set alignment of components
	private void align(){
		
		//align within panel
		categoryUpdteLabel.setAlignmentX(LEFT_ALIGNMENT); 
		categoryUpdteBox.setAlignmentX(LEFT_ALIGNMENT);

		//align panels
		categoryUpdtePanel.setAlignmentX(LEFT_ALIGNMENT);
		idUpdtePanel.setAlignmentX(LEFT_ALIGNMENT);
		updteButtonPanel.setAlignmentX(LEFT_ALIGNMENT);
	}

}
