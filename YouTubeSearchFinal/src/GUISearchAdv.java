import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.TextArea;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * GUI for user to do an advanced search through LINKS table
 * (LINKS table holds results of YouTube searches)
 * @author Danielle Zoe Aloicius
 *
 */
public class GUISearchAdv extends MainGUI{

	//variables for category Panel
	private JLabel categorySrchAdvLabel= new JLabel("Enter category to search database");
	private JComboBox<String> categorySrchAdvBox =Globals.getCategoryBox();
	private JPanel categorySrchAdvPanel = new JPanel();
	private JPanel innerSrchAdvCategoryPanel = new JPanel();

	//vars for keyword panel
	private JLabel keywordSrchAdvLabel = new JLabel("Enter keywords to search database");
	private JTextField keywordSrchAdvTF = new JTextField("");
	private JPanel keywordSrchAdvPanel = new JPanel();

	//variables for button Panel
	private Button srchAdvBtn = new Button("Advanced Search");
	private SearchDBButtonListener srchAdvlistener = new SearchDBButtonListener(this);
	private JPanel srchAdvbuttonPanel = new JPanel();

	//text area to display results
	private TextArea srchAdvdisplay = new TextArea("Results", 5, 60, TextArea.SCROLLBARS_BOTH );

	//and-or panel variables
	private JComboBox<String> andOr = new JComboBox<String>();
	private JPanel andOrSrchAdvPanel = new JPanel();

	//panel to hold all panels
	private JPanel largeSrchAdvPanel = new JPanel();

	/**
	 * constructor of GUI for advanced search of LINKS table
	 */
	public GUISearchAdv(){
		super();

		//add action listener to button
		setTitle("Advanced Database Search");

		//set color of button
		srchAdvBtn.setBackground(Color.RED);

		srchAdvBtn.addActionListener(srchAdvlistener);

		//enable buttons for delete, update and print search results
		deleteMenu.setEnabled(true);
		updateMenu.setEnabled(true);
		printMenu.setEnabled(true);

		setSizes();
		align();

		//and or box create
		String[] andOrStrings = {"and", "or"};
		andOr = new JComboBox<String>(andOrStrings);
		andOr.setEditable(false);

		//add components to inner category panel
		innerSrchAdvCategoryPanel.setLayout(new BoxLayout(innerSrchAdvCategoryPanel, BoxLayout.PAGE_AXIS));
		innerSrchAdvCategoryPanel.add(categorySrchAdvLabel);
		innerSrchAdvCategoryPanel.add(categorySrchAdvBox);

		//add inner category panel and and/or drop down to category panel
		categorySrchAdvPanel.setLayout(new BoxLayout(categorySrchAdvPanel, BoxLayout.LINE_AXIS));
		categorySrchAdvPanel.add(innerSrchAdvCategoryPanel);
		andOrSrchAdvPanel.add(andOr);
		categorySrchAdvPanel.add(andOrSrchAdvPanel);

		// add components to keyword panel
		keywordSrchAdvPanel.setLayout(new BoxLayout(keywordSrchAdvPanel, BoxLayout.PAGE_AXIS));
		keywordSrchAdvPanel.add(keywordSrchAdvLabel);
		keywordSrchAdvPanel.add(keywordSrchAdvTF);	

		//add Panels to ContentPane with spacing between
		cp.add(Box.createRigidArea(new Dimension(0,20)));
		largeSrchAdvPanel.setLayout(new BoxLayout(largeSrchAdvPanel, BoxLayout.PAGE_AXIS));
		largeSrchAdvPanel.add(categorySrchAdvPanel);
		largeSrchAdvPanel.add(Box.createRigidArea(new Dimension(0,120)));
		largeSrchAdvPanel.add(keywordSrchAdvPanel);
		largeSrchAdvPanel.add(Box.createRigidArea(new Dimension(0,25)));
		srchAdvbuttonPanel.add(srchAdvBtn); //add button to button panel
		largeSrchAdvPanel.add(srchAdvbuttonPanel);
		cp.add(largeSrchAdvPanel);
		cp.add(Box.createRigidArea(new Dimension(0,10)));
		cp.add(srchAdvdisplay);

		setVisible(true);		
	}

	//method to set sizes of components
	private void setSizes(){
		//panels		
		categorySrchAdvPanel.setMinimumSize(new Dimension(400, 50));
		categorySrchAdvPanel.setPreferredSize(new Dimension(400, 50));
		categorySrchAdvPanel.setMaximumSize(new Dimension(400, 50));

		innerSrchAdvCategoryPanel.setMinimumSize(new Dimension(320, 50));
		innerSrchAdvCategoryPanel.setPreferredSize(new Dimension(320, 50));
		innerSrchAdvCategoryPanel.setMaximumSize(new Dimension(320, 50));

		srchAdvbuttonPanel.setMinimumSize(new Dimension(120, 70));
		srchAdvbuttonPanel.setPreferredSize(new Dimension(120, 70));
		srchAdvbuttonPanel.setMaximumSize(new Dimension(120,70));

		andOrSrchAdvPanel.setMinimumSize(new Dimension(80, 30));
		andOrSrchAdvPanel.setPreferredSize(new Dimension(80, 30));
		andOrSrchAdvPanel.setMaximumSize(new Dimension(80, 30));

		largeSrchAdvPanel.setMinimumSize(new Dimension(420, 300));
		largeSrchAdvPanel.setPreferredSize(new Dimension(420, 300));
		largeSrchAdvPanel.setMaximumSize(new Dimension(420, 300));


        // JCombo Boxes
		andOr.setMinimumSize(new Dimension(60, 20));
		andOr.setPreferredSize(new Dimension(60, 20));
		andOr.setMaximumSize(new Dimension(60, 20));

		categorySrchAdvBox.setMinimumSize(new Dimension(300, 20));
		categorySrchAdvBox.setPreferredSize(new Dimension(300, 20));
		categorySrchAdvBox.setMaximumSize(new Dimension(300,20));

		//textfields
		keywordSrchAdvTF.setMinimumSize(new Dimension(300, 20));
		keywordSrchAdvTF.setPreferredSize(new Dimension(300, 20));
		keywordSrchAdvTF.setMaximumSize(new Dimension(300,20));

		//buttons
		srchAdvBtn.setMinimumSize(new Dimension(100, 50));
		srchAdvBtn.setPreferredSize(new Dimension(100, 50));
		srchAdvBtn.setMaximumSize(new Dimension(100,50));

	}

	//method to set alignment of components
	private void align(){
        //within larger panels
		categorySrchAdvLabel.setAlignmentX(LEFT_ALIGNMENT);
		categorySrchAdvBox.setAlignmentX(LEFT_ALIGNMENT);
		keywordSrchAdvLabel.setAlignmentX(LEFT_ALIGNMENT);
		keywordSrchAdvTF.setAlignmentX(LEFT_ALIGNMENT);		
		srchAdvbuttonPanel.setAlignmentX(LEFT_ALIGNMENT);

		//within large Panel
		categorySrchAdvPanel.setAlignmentX(LEFT_ALIGNMENT);
		keywordSrchAdvPanel.setAlignmentX(LEFT_ALIGNMENT);

		//within category panel
		categorySrchAdvBox.setAlignmentX(TOP_ALIGNMENT);
		andOr.setAlignmentX(TOP_ALIGNMENT);


	}
    /**
     * Gets keyword on which user wants to search LINKS table
     * @return String keyword
     */
	public String getDescriptionKeyword(){
		String s;
		try{
			s = keywordSrchAdvTF.getText();
		}
		catch(NullPointerException e){
			return "";
		}
		return s;		
	}

	/**
	 * Gets the category on which the user wants to search LINKS table
	 * @return String category
	 */
	public String getCategory(){
		return (String) categorySrchAdvBox.getSelectedItem();
	}

	/**
	 * Gets the word "and" or the word "or" to build the user's advanced search query
	 * (The user can choose to search for keyword and/or category)
	 * @return String "and" or "or"
	 */
	public String getAndOr(){
		return (String) andOr.getSelectedItem();
	}

    /**
     * Method to clear the display TextArea
     */
	public void clearDisplay(){
		srchAdvdisplay.setText("");
		//display.repaint();
	}
    
	/**
	 * Method to add text to the display TextArea
	 * @param text to be added to TextArea
	 */
	public void appendToDisplay(String text){
		srchAdvdisplay.append(text);
	}



}
