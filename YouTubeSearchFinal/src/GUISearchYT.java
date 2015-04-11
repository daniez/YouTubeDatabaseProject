import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.TextArea;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * GUI for user to search YouTube.
 * Adds the results of the search to LINKS table
 * Creates LINKS table if it does not exist
 * @author Danielle Zoe Aloicius
 *
 */
public class GUISearchYT extends MainGUI{

	//create variables for search text panel
	private JLabel ytSrchTextLabel = new JLabel("Enter your search words");
	private JTextField ytSrchText = new JTextField();
	private JPanel ytSrchPanel = new JPanel();

	//create variables for category panel
	private JComboBox<String> ytSrchCategory=Globals.getCategoryBox();
	private JPanel ytSrchCategoryPanel = new JPanel();
	private JLabel ytSrchCategoryLabel = new JLabel("<html><body>Select the category in which the results will be saved <br>or type new category</body></html>");

	//create search button and add listener
	private Button ytSrchBtn = new Button("Search");
	private SearchYTButtonListener ytSrchListener = new SearchYTButtonListener(this);

	//create Button panel
	private JPanel ytSrchButtonPanel = new JPanel();

	//area to display results
	private TextArea ytSrchDisplay = new TextArea("Results", 5, 60, TextArea.SCROLLBARS_BOTH );

	/**
	 * constructor of GUI to search YouTube
	 */
	public GUISearchYT(){
		super();

		setTitle("Search YouTube");

		//add action listener to button
		ytSrchBtn.addActionListener(ytSrchListener);
		ytSrchBtn.setBackground(Color.RED);

		setSizes();
		align();

		//add components to Panels and Panels to ContentPane with spacing between
		cp.add(Box.createRigidArea(new Dimension(0,25)));
		
		//add components to search Panel and add search Panel to content pane
		ytSrchPanel.setLayout(new BoxLayout(ytSrchPanel, BoxLayout.PAGE_AXIS));
		ytSrchPanel.add(ytSrchTextLabel);
		ytSrchPanel.add(ytSrchText);		
		cp.add(ytSrchPanel);
		cp.add(Box.createRigidArea(new Dimension(0,25)));
		
		//add components to category panel and add category panel to content pane
		ytSrchCategoryPanel.setLayout(new BoxLayout(ytSrchCategoryPanel, BoxLayout.PAGE_AXIS));
		ytSrchCategoryPanel.add(ytSrchCategoryLabel);
		ytSrchCategoryPanel.add(ytSrchCategory);
		cp.add(ytSrchCategoryPanel);
		cp.add(Box.createRigidArea(new Dimension(0, 20)));

		//add button panel
		ytSrchButtonPanel.add(ytSrchBtn);
		cp.add(ytSrchButtonPanel);
		cp.add(Box.createRigidArea(new Dimension(0, 25)));

        //add display Text Area
		ytSrchDisplay.setEditable(false);		
		cp.add(ytSrchDisplay);

		setVisible(true);
	}
	
	//method to set sizes of components
	private void setSizes(){
		//panels

		ytSrchPanel.setMinimumSize(new Dimension(400, 50));
		ytSrchPanel.setPreferredSize(new Dimension(400, 50));
		ytSrchPanel.setMaximumSize(new Dimension(400, 50));

		ytSrchCategoryPanel.setMinimumSize(new Dimension(400, 150));
		ytSrchCategoryPanel.setPreferredSize(new Dimension(400, 150));
		ytSrchCategoryPanel.setMaximumSize(new Dimension(400, 150));

		ytSrchButtonPanel.setMinimumSize(new Dimension(90, 60));
		ytSrchButtonPanel.setPreferredSize(new Dimension(90, 60));
		ytSrchButtonPanel.setMaximumSize(new Dimension(90,60));

		//textfields
		ytSrchText.setMinimumSize(new Dimension(300, 20));
		ytSrchText.setPreferredSize(new Dimension(300, 20));
		ytSrchText.setMaximumSize(new Dimension(300,20));

        //JCombo box
		ytSrchCategory.setMinimumSize(new Dimension(300, 20));
		ytSrchCategory.setPreferredSize(new Dimension(300, 20));
		ytSrchCategory.setMaximumSize(new Dimension(300,20));

		//buttons
		ytSrchBtn.setMinimumSize(new Dimension(80, 50));
		ytSrchBtn.setPreferredSize(new Dimension(80, 50));
		ytSrchBtn.setMaximumSize(new Dimension(80,50));
	}

	//method to set alignment of components
	private void align(){

		//search text panel
		ytSrchTextLabel.setAlignmentX(LEFT_ALIGNMENT);
		ytSrchText.setAlignmentX(LEFT_ALIGNMENT);
		ytSrchPanel.setAlignmentX(LEFT_ALIGNMENT);

		//category panel
		ytSrchCategory.setAlignmentX(LEFT_ALIGNMENT);
		ytSrchCategoryPanel.setAlignmentX(LEFT_ALIGNMENT);
		ytSrchCategoryLabel.setAlignmentX(LEFT_ALIGNMENT);

		ytSrchButtonPanel.setAlignmentX(LEFT_ALIGNMENT);
	}

    /**
     * Gets keyword on which user wants to search YouTube
     * @return String keyword
     */
	public String getSearchText(){
		try{
			String text=ytSrchText.getText();
			return text;
		}
		catch(NullPointerException e){

			System.out.print(e.getStackTrace());
		}
		return null;
	}


	/**
	 * Gets the category in which the user wants to place the search results
	 * @return String holding category
	 */
	public String getCategory(){
		return (String) ytSrchCategory.getSelectedItem();
	}

	 /**
     * Method to clear the display TextArea
     */
	public void clearDisplay(){
		ytSrchDisplay.setText("");
	}

	/**
	 * Method to add text to the display TextArea
	 * @param text to be added to TextArea
	 */	
	public void appendToDisplay(String text){
		ytSrchDisplay.append(text);
	}

}
