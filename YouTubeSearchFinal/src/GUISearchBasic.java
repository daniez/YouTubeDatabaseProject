import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.TextArea;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * GUI for user to do a basic search through LINKS table
 * (LINKS table holds results of YouTube searches)
 * @author Danielle Zoe Aloicius
 *
 */
public class GUISearchBasic extends MainGUI{

	//variables for search Panel
	private JLabel keywordSrchBscLabel = new JLabel("Enter keywords to search database");	
	private JTextField keywordSrchBscTF = new JTextField("");
	private JPanel srchBscPanel = new JPanel();	
	
	//variables for button Panel
	private Button searchBscBtn = new Button("Search");
	private JPanel srchBscButtonPanel = new JPanel();
	private SearchDBButtonListener srchBscListener = new SearchDBButtonListener(this);

	//text area to display results
	private TextArea srchBscDisplay = new TextArea("Results", 5, 60, TextArea.SCROLLBARS_BOTH );

	/**
	 * constructor of GUI for basic search of LINKS table
	 */
	public GUISearchBasic(){
		super();

		setTitle("Basic Database Search");

		//add action listener to button
		searchBscBtn.addActionListener(srchBscListener);

		//set color of button
		searchBscBtn.setBackground(Color.RED);

		//enable buttons for delete, update and print search results
		deleteMenu.setEnabled(true);
		updateMenu.setEnabled(true);
		printMenu.setEnabled(true);

		setSizes();
		align();

		//add components to panels and add Panels to ContentPane with spacing between
		cp.add(Box.createRigidArea(new Dimension(0,50)));
		srchBscPanel.setLayout(new BoxLayout(srchBscPanel, BoxLayout.PAGE_AXIS));
		srchBscPanel.add(keywordSrchBscLabel);
		srchBscPanel.add(keywordSrchBscTF);
		srchBscPanel.add(Box.createRigidArea(new Dimension(0,25)));
		srchBscButtonPanel.add(searchBscBtn);
		srchBscPanel.add(srchBscButtonPanel);
		cp.add(srchBscPanel);
		cp.add(Box.createRigidArea(new Dimension(0,25)));
		cp.add(srchBscDisplay);

		setVisible(true);		
	}
	
	//method to set sizes of components
	private void setSizes(){
		srchBscPanel.setMinimumSize(new Dimension(350, 150));
		srchBscPanel.setPreferredSize(new Dimension(350, 150));
		srchBscPanel.setMaximumSize(new Dimension(350, 150));

		keywordSrchBscTF.setMinimumSize(new Dimension(300, 20));
		keywordSrchBscTF.setPreferredSize(new Dimension(300, 20));
		keywordSrchBscTF.setMaximumSize(new Dimension(300,20));

		searchBscBtn.setMinimumSize(new Dimension(100, 50));
		searchBscBtn.setPreferredSize(new Dimension(100, 50));
		searchBscBtn.setMaximumSize(new Dimension(100,50));

		srchBscButtonPanel.setMinimumSize(new Dimension(100, 70));
		srchBscButtonPanel.setPreferredSize(new Dimension(100, 70));
		srchBscButtonPanel.setMaximumSize(new Dimension(100,70));

	}

	//method to set alignment of components
	private void align(){

		//search text panel
		keywordSrchBscLabel.setAlignmentX(LEFT_ALIGNMENT);
		keywordSrchBscTF.setAlignmentX(LEFT_ALIGNMENT);
		srchBscButtonPanel.setAlignmentX(LEFT_ALIGNMENT);
	}

    /**
     * Gets keyword on which user wants to search LINKS table
     * @return String keyword
     */
	public String getKeyword(){
		String s;
		try{
			s = keywordSrchBscTF.getText();
		}
		catch(NullPointerException e){
			return "";
		}
		return s;

	}
	
    /**
     * Method to clear the display TextArea
     */
	public void clearDisplay(){
		srchBscDisplay.setText(null);
		//display.repaint();
	}

	/**
	 * Method to add text to the display TextArea
	 * @param text to be added to TextArea
	 */
	public void appendToDisplay(String text){
		srchBscDisplay.append(text);
	}


}
