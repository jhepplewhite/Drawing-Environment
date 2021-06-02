import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * @File DrawingEnvironment.java
 * @author Jonathan Hepplewhite
 * @date 7 Dec 2016
 * @brief Class that displays the startup panes and drawing screen
 * \n \n
 * DrawingEnvironment is a class that displays the startup panes
 * (NewDrawingPane and LoadDrawingPane) and will display the DrawingScreen
 * when the user creates a new drawing or loads a saved one
 */
public class DrawingEnvironment extends JPanel
{
	/**
	 * Set the currently selected element
	 * @param element The element selected
	 */
	public static void setCurrentElement(int element)
	{
		m_currentElement = element;
	}
	
	/**
	 * @return The current element
	 */
	public static int getCurrentElement()
	{
		return m_currentElement;
	}
	
	/**
	 * Constructor that creates a new DrawingEnvonment
	 * @param user The current user
	 * @param contacts The current users contacts
	 */
	public DrawingEnvironment(String user, ArrayList<String> contacts)
	{
		this.user = user;
		contactList = contacts;
		paneSetup();
	}
	
	/**
	 * Method that sets up and displays both the create new drawing pane
	 * and the load saved drawing pane.
	 */
	private void paneSetup()
	{
		//Create JTabbedPane that will hold the NewDrawingPane and the LoadDrawingPane
		tabbedPane = new JTabbedPane();
		NewDrawingPane newPane = new NewDrawingPane(user, contactList, this);
		LoadDrawingPane loadPane = new LoadDrawingPane(user, contactList, this);
		//Add the NewDrawingPane and the LoadDrawingPane to the JTabbedPane
		tabbedPane.add(NEW_TAB_NAME, newPane);
		tabbedPane.add(LOAD_TAB_NAME, loadPane);
		//Set the diplay size of the tabbed pane and make it visible
		tabbedPane.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		tabbedPane.setVisible(true);
		//Add the tabbed pane to this DrawingEnvironment
		this.add(tabbedPane);
		//Revalidate and repaint the DrawingEnvironment to display the tabbed pane
		revalidate();
		repaint();
	}
	
	/**
	 * Method that removes the startup panes and creates and adds the
	 * drawing screen to the DrawingEnvironment
	 * @param fileName The name of the file to be created/loaded
	 * @param isNew Whether or not the file is new
	 */
	public void openDrawingArea(String fileName, Boolean isNew)
	{
		//Remove the tabbedpane as it no longer needed
		this.remove(tabbedPane);
		//Create a new DrawingScreen and add it to the DrawingEnvironment
		DrawingScreen drawScreen = new DrawingScreen(fileName, isNew);
		this.add(drawScreen);
		//Set the new DrawingScreen display setup
		drawScreen.setVisible(true);
		drawScreen.setBackground(Color.WHITE);
		drawScreen.setPreferredSize(new Dimension(FRAME_WIDTH,FRAME_HEIGHT));
		//Revalidate and redraw the panel
		revalidate();
		repaint();
	}
	
	/** Set the name of the tab for new drawing */
	private final String NEW_TAB_NAME = "New Drawing";
	/** Set the name of the tab for load drawing */
	private final String LOAD_TAB_NAME = "Load Drawing";
	/** The current element, initialised to 0 (ParticleTrace) */
	private static int m_currentElement = 0;
	/** The pane that holds the new drawing and load drawing tabs */
	private JTabbedPane tabbedPane = new JTabbedPane();
	/** The list of all the current users contacts */
	private ArrayList<String> contactList;
	/** The name of current user */
	private String user;
	/** The frame width */
	private final int FRAME_WIDTH = 500;
	/** The frame height */
	private final int FRAME_HEIGHT = 525;
}
