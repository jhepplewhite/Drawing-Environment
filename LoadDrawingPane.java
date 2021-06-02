import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * @File LoadDrawingPane.java
 * @author Jonathan Hepplewhite
 * @date 8 Dec 2016
 * @Brief Panel class that holds the GUI for loading a saved drawing
 * \n \n
 * LoadDrawingPane is a panel class that holds the GUI that allows 
 * the user to load a saved image file.
 */
public class LoadDrawingPane extends JPanel implements ActionListener
{
	
	/**
	 * @return The ArrayList of file names
	 */
	public static ArrayList<String> getFileNames()
	{
		return m_FileNames;
	}
	
	/**
	 * Set the file names
	 */
	public static void setFileNames()
	{
		m_FileNames = ReferenceFileReader.getFileNames();
	}
	
	/**
	 * Method that handles the mouse clicks
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		switch(e.getActionCommand())
		{
		
			case LOAD_NAME:	//If a file in the list is selected
							if(fileList.getSelectedValue() != null)
							{
								//Create a File reference to the image
								File file = new File(fileList.getSelectedValue().toString() + 
														FILE_EXTENSION);
								//If the file does not exist
								if(!file.exists())
								{
									//Display a pane with a message displaying the issue
									JOptionPane.showMessageDialog(this,
												IMAGE_NOT_FOUND, NOT_FOUND_POPUP_TITLE, 
												JOptionPane.ERROR_MESSAGE);
								}
								else
								{
									//Pass the file name to the file reader for use when saving
									ReferenceFileReader.setFileName(fileList.getSelectedValue().toString());
									//Switch the message to be displayed when clicking the frame close button
									DrawingEnvironmentLauncher.setMessage(true);
									//Open the DrawingArea
									de.openDrawingArea((String)fileList.getSelectedValue(), false);
								}
							}
							break;
		case DELETE_NAME:	//If an item in the list is selected
							if(!fileList.isSelectionEmpty())
							{
								//Read the choice the user makes from the popup
								int choice = JOptionPane.showConfirmDialog(this, 
													CONFIRM_DELETION_MESSAGE, 
													DELETION_POPUP_TITLE, JOptionPane.YES_NO_OPTION);
								File file = new File(fileList.getSelectedValue().toString() 
													+ FILE_EXTENSION);
								//If they choose yes
								if(choice == 0)
								{
									//Delete the file if it exists
									if(file.exists())
									{
										file.delete();
									}
									//Call FileDeleter to delete the file
									FileDeleter.deleteFile(fileList.getSelectedValue().toString());
									//Update the names of file on the system
									setFileNames();
									//Remove the item delted from the list
									model.removeElement(fileList.getSelectedValue());
									/*Make sure the NewDrawingPane has the new list of files
									(for checking if file name available at creation) */
									NewDrawingPane.setFileNames();
								}
							}
							break;	
		default: 			break;
		}
	}
	
	/**
	 * Initialize the list that contains the image files
	 */
	private void initialiseList()
	{
		model = new DefaultListModel();
		//Populate the list with all the names of the image files
		for(int i = 0; i< m_FileNames.size(); i++)
		{
			if(ReferenceFileReader.checkUserValid(user, m_FileNames.get(i)))
			{
				model.addElement(m_FileNames.get(i));
			}
		}
		
		fileList = new JList(model);
		//Create a scroll pane with the list and set its size
		scrollPane = new JScrollPane(fileList);
		scrollPane.setBounds(SCROLLPANE_BOUNDS_X, SCROLLPANE_BOUNDS_Y, 
						SCROLLPANE_WIDTH, SCROLLPANE_HEIGHT);
	}
	
	/**
	 * Intialize the buttons on the pane
	 */
	private void initialiseLoadButton()
	{
		//Initialize and setup the load button
		loadDrawing = new JButton(LOAD_NAME);
		loadDrawing.setActionCommand(LOAD_NAME);
		loadDrawing.addActionListener(this);
		loadDrawing.setBounds(LOAD_BUTTON_BOUNDS_X, BUTTON_BOUNDS_Y, 
								BUTTON_WIDTH, BUTTON_HEIGHT);
		
		//Initialize and setup the delete button
		deleteDrawing = new JButton(DELETE_NAME);
		deleteDrawing.setActionCommand(DELETE_NAME);
		deleteDrawing.addActionListener(this);
		deleteDrawing.setBounds(DELETE_BUTTON_BOUNDS_X, BUTTON_BOUNDS_Y, 
								BUTTON_WIDTH, BUTTON_HEIGHT);
	}
	
	/**
	 * Constructorthat creates a new LoadDrawingPane object
	 * @param user The current user
	 * @param contacts The current user's contacts
	 * @param de The DrawingEnvironment the pane is added to
	 */
	public LoadDrawingPane(String user, ArrayList<String> contacts, DrawingEnvironment de) 
	{
		this.de = de;
		this.user = user;
		this.contactList = contacts;
		this.setLayout(null);
		setFileNames();
		//Initialze list and buttons
		initialiseList();
		initialiseLoadButton();
		//Add all scrollpane and buttons
		this.add(scrollPane);
		this.add(loadDrawing);
		this.add(deleteDrawing);
	}
	
	/** The name and action command for load button */
	private final String LOAD_NAME = "Load";
	/** The name and action command for delete button */
	private final String DELETE_NAME = "Delete";
	/** The location of the image and reference file */
	/** Popup title */
	private final String DELETION_POPUP_TITLE = "Confirm file deletion";
	/** Deletion popup message */
	private final String CONFIRM_DELETION_MESSAGE = 
				"Are you sure you would like to delete this file?";
	/** Image file not found error message */
	private final String IMAGE_NOT_FOUND = "Image File Not Found";
	/** Image file not found popup title */
	private final String NOT_FOUND_POPUP_TITLE = "Image File Error";
	/** File extension name */
	private final String FILE_EXTENSION = ".png";
	
	/** Width of each button */
	private final int BUTTON_WIDTH = 100;
	/** Height of each button */
	private final int BUTTON_HEIGHT = 25;
	/** X bounds of the load button */
	private final int LOAD_BUTTON_BOUNDS_X = 100;
	/** X bounds of the delete button */
	private final int DELETE_BUTTON_BOUNDS_X = 300;
	/** Y bounds of both button */
	private final int BUTTON_BOUNDS_Y = 250;
	
	/** ScrollPane X bounds */
	private final int SCROLLPANE_BOUNDS_X = 15;
	/** ScrollPane Y bounds */
	private final int SCROLLPANE_BOUNDS_Y = 25;
	/** ScrollPane width */
	private final int SCROLLPANE_WIDTH = 450;
	/** ScrollPane height */
	private final int SCROLLPANE_HEIGHT = 200;
	
	/** A reference to the list model that holds the items */
	private DefaultListModel model;
	/** The current user's name */
	private String user;
	/** The users contact list */
	private ArrayList<String> contactList;
	/** The scrollpane that holds the list of image files */
	private JScrollPane scrollPane;
	/** Create load button */
	private JButton loadDrawing;
	/** Create delete button */
	private JButton deleteDrawing;
	/** Create ArrayList which will store the image file names */
	private static ArrayList<String> m_FileNames;
	/** Reference to the DrawingEnvironment that holds the pane */
	private DrawingEnvironment de;
	/** List that holds the displays the image file names */
	private JList fileList;
}