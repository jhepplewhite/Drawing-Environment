import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JPanel;

/**
 * @File NewDrawingPane.java
 * @author Jonathan Hepplewhite
 * @date 8 Dec 2016
 * @brief Panel class that holds the GUI for creating a new drawing
 * \n\n
 * NewDrawingPane is a panel class that holds the GUI that allows 
 * the user to create a new image file.
 */
public class NewDrawingPane extends JPanel implements ActionListener
{
	/**
	 * @return The ArrayList file names
	 */
	public static ArrayList<String> getFileNames()
	{
		return m_FileNames;
	}
	
	/**
	 * Set the list of current file names
	 */
	public static void setFileNames()
	{
		m_FileNames = ReferenceFileReader.getFileNames();
	}
	
	/**
	 * Initialize the lists
	 */
	private void initialiseLists()
	{
		DefaultListModel model = new DefaultListModel();
		//For each of the user's contacts, add them to the list
		for(int i = 0; i < contactList.size(); i++)
		{
			model.addElement(contactList.get(i));
		}
		
		list  = new JList(model);
		//Create a scrollpane using the list and set its size and position
		scrollPane1 = new JScrollPane(list);
		scrollPane1.setBounds(SCROLLPANE_BOUNDS_X, 0, 
								SCROLLPANE_WIDTH, SCROLLPANE_HEIGHT);
		scrollPane1.setAutoscrolls(true);
		//Let the user select multiple contacts at a time
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		model2 = new DefaultListModel();
		list2 = new JList(model2);
		list2.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		scrollPane2 = new JScrollPane(list2);
		scrollPane2.setBounds(SCROLLPANE_BOUNDS_X, SCROLLPANE2_BOUNDS_Y, 
								SCROLLPANE_WIDTH, SCROLLPANE_HEIGHT);
	}
	
	/**
	 * Setup each of the panes buttons
	 */
	private void initialiseButtons()
	{
		/*Setup the add contact button, its position, size,
		   action command and listener and make it visible */
		addContact = new JButton(ADD_BUTTON_NAME);
		addContact.setBounds(ADD_BOUNDS_X, ADD_REMOVE_BOUNDS_Y, 
							ADD_REMOVE_BUTTON_WIDTH, BUTTON_HEIGHT);
		addContact.addActionListener(this);
		addContact.setActionCommand(ADD_BUTTON_NAME);
		addContact.setVisible(true);
		
		/*Setup the remove contact button, its position, size,
		   action command and listener and make it visible */
		removeContact = new JButton(REMOVE_BUTTON_NAME);
		removeContact.setBounds(REMOVE_BOUNDS_X, ADD_REMOVE_BOUNDS_Y, 
							ADD_REMOVE_BUTTON_WIDTH, BUTTON_HEIGHT);
		removeContact.addActionListener(this);
		removeContact.setActionCommand(REMOVE_BUTTON_NAME);
		removeContact.setVisible(true);
		
		/*Setup the create button, its position, size,
		   action command and listener and make it visible */
		newDrawing = new JButton(CREATE_BUTTON_NAME);
		newDrawing.setBounds(CREATE_BOUNDS_X, CREATE_BOUNDS_Y, 
							CREATE_BUTTON_WIDTH, BUTTON_HEIGHT);
		newDrawing.setActionCommand(CREATE_BUTTON_NAME);
		newDrawing.addActionListener(this);

	}
	
	/**
	 * Initialize the text field for entering the file name
	 */
	private void initialiseTextField()
	{
		//Initialze the text field with default text, position and make it visible
		fileNameInput = new JTextField(DEFAULT_FILENAME);
		fileNameInput.setBounds(TEXTFIELD_BOUNDS_X, TEXTFIELD_BOUNDS_Y, 
								TEXTFIELD_WIDTH, TEXTFIELD_HEIGHT);
		fileNameInput.setVisible(true);
	}

	/**
	 * Method that handles the mouse clicks on buttons
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		switch(e.getActionCommand())
		{
		case ADD_BUTTON_NAME:		//For each of the selected items in the first list
									for(int i = 0; i < 
											list.getSelectedValuesList().size(); i++)
									{
										//If they're not in the second list
										if(!model2.contains(
												list.getSelectedValuesList().get(i)))
										{
											//Add them to the second list
											model2.addElement(
												list.getSelectedValuesList().get(i));
										}
									}
									break;
		case REMOVE_BUTTON_NAME:	//For each selected object in the second list
									for(Object o : list2.getSelectedValuesList())
									{
										//Remove the object
										model2.removeElement(o);
									}
									break;
		case CREATE_BUTTON_NAME:	Boolean valid = true;
									//For each file name that currently exists
									for(String s : m_FileNames)
									{
										//If the input matches an existing name
										if(fileNameInput.getText().equals(s))
										{
											if(valid)
											{
												//Show popup with issue
												JOptionPane.showMessageDialog(this, 
															FILE_NAME_INVALID, 
															FILE_ERROR_POPUP_TITLE, 
															JOptionPane.ERROR_MESSAGE);
											}
											//Make the click invalid
											valid = false;
										}
									}
									//If the input is empty or has not been changed
									if(fileNameInput.getText().equals(DEFAULT_FILENAME) || 
													fileNameInput.getText().equals(""))
									{
										//Show error
										if(valid)
										{
											JOptionPane.showMessageDialog(this, 
															FILE_NAME_INVALID, 
															FILE_ERROR_POPUP_TITLE, 
															JOptionPane.ERROR_MESSAGE);
										}
										valid = false;
									}
									//If input is valid
									else if (valid == true)
									{
										/* Create a new String array and add the selected 
										  users to it*/
										String[] selectedContacts = new String[model2.size()];
										for(int i = 0; i < selectedContacts.length; i++)
										{
											selectedContacts[i] = (String)model2.getElementAt(i);
										}
										//Pass the input file name to the reference file reader
										ReferenceFileReader.setFileName(
														fileNameInput.getText().toString());
										/*Pass the users who can access the file to
										 the reference file reader */
										ReferenceFileReader.setUsers(selectedContacts);
										//Pass the user to the reference file reader
										ReferenceFileReader.setUserName(user);
										//Switch the message to be displayed when clicking the frame close button
										DrawingEnvironmentLauncher.setMessage(true);
										//Open the DrawingArea
										de.openDrawingArea(fileNameInput.getText(), true);
									}
									break;
		default:					break;
		}	
	}
	
	/**
	 * Constructor for the NewDrawingPane
	 * @param user The current user
	 * @param contacts The current user's contacts
	 * @param de Reference to the DrawingEnvironment
	 */
	public NewDrawingPane(String user, ArrayList<String> contacts, DrawingEnvironment de) 
	{		
		this.user = user;
		contactList = contacts;
		this.de = de;
		this.setLayout(null);
		setFileNames();
		
		//Initialize the GUI
		initialiseLists();
		initialiseButtons();
		initialiseTextField();

		//Add each of the components to the panel
		this.add(scrollPane1);
		this.add(scrollPane2);
		this.add(addContact);
		this.add(removeContact);
		this.add(fileNameInput);
		this.add(newDrawing);
	}
	
	/** Both scrollpanes X bounds */
	private final int SCROLLPANE_BOUNDS_X = 15;
	/** Both scrollpanes widths */
	private final int SCROLLPANE_WIDTH = 450;
	/** Both scrollpanes height */
	private final int SCROLLPANE_HEIGHT = 200;
	/** The second scrollpane's Y bounds */
	private final int SCROLLPANE2_BOUNDS_Y = 230;
	/** Remove and add button width */
	private final int ADD_REMOVE_BUTTON_WIDTH = 150;
	/** All buttons height */
	private final int BUTTON_HEIGHT = 25;
	/** Create button width */
	private final int CREATE_BUTTON_WIDTH = 100;
	
	/** Add button name and action command */
	private final String ADD_BUTTON_NAME = "Add Contact";
	/** Remove button name and action command */
	private final String REMOVE_BUTTON_NAME = "Remove Contact";
	/** Create button name and action command */
	private final String CREATE_BUTTON_NAME = "Create";
	
	/** Invalid file name error message */
	private final String FILE_NAME_INVALID = "File name is taken or invalid. "
											+ "Please try a different name.";
	/** File error popup title */
	private final String FILE_ERROR_POPUP_TITLE = "File Name Error"; 
	/** The default text field text */
	private final String DEFAULT_FILENAME = "Enter File Name";
	
	/** Add button X bounds */
	private final int ADD_BOUNDS_X = 75;
	/** Remove button X bounds */
	private final int REMOVE_BOUNDS_X = 275;
	/** Add and remove button Y bounds */
	private final int ADD_REMOVE_BOUNDS_Y = 205;
	/** Create button X bounds */
	private final int CREATE_BOUNDS_X = 380;
	/** Create button y bounds */
	private final int CREATE_BOUNDS_Y =	450;
	
	/** Textfield X bounds */
	private final int TEXTFIELD_BOUNDS_X = 10;
	/** Textfield Y bounds */
	private final int TEXTFIELD_BOUNDS_Y = 450;
	/** Textfield width */
	private final int TEXTFIELD_WIDTH = 360;
	/** Textfield height */
	private final int TEXTFIELD_HEIGHT = 25;
	
	/** Reference to remove contact button */
	private JButton removeContact;
	/** Reference to add contact button */
	private JButton addContact;
	/** Reference to the DrawingEnvironment the pane is added to */
	private DrawingEnvironment de;
	/** Reference to create button  */
	private JButton newDrawing;
	/** Reference to current user */
	private String user;
	/** Current user's contact list */
	private ArrayList<String> contactList;
	/** Reference to file name text field */
	private JTextField fileNameInput;
	/** ArrayList which holds all file names */
	private static ArrayList<String> m_FileNames;
	/** Reference to list2 model */
	private DefaultListModel model2;
	/** List that holds all the user's contacts */
	private JList list;
	/** List that holds the selected users */
	private JList list2;
	/** Scrollpane that displays all the user's contacts */
	private JScrollPane scrollPane1;
	/** Scrollpane that displays the list of selected users */
	private JScrollPane scrollPane2;
}
