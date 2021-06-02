import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * @File DrawingEnvironmentLauncher.java
 * @author Jonathan Hepplewhite
 * @date 6 Dec 2016
 * @brief Class that launches the DrawingEnvironment
 * \n \n
 * Class that launches the DrawingEnvironment, specifying how the frame
 * should react when closing the window
 */
public class DrawingEnvironmentLauncher 
{
	/**
	 * @return The output message for the popup
	 */
	public static String getMessage()
	{
		return m_OutputMessage;
	}
	
	/**
	 * Set the message depending on whether the user has opened a drawing
	 * @param isDrawing True if the user has opened a new drawing, false otherwise
	 */
	public static void setMessage(Boolean isDrawing)
	{
		if(isDrawing)
		{
			m_OutputMessage = MESSAGE1;
		}
		else
		{
			m_OutputMessage = MESSAGE2;
		}
	}
	
	/**
	 * Constructor which setups up the DrawingEnvironment
	 * @param user The current user
	 * @param contactList The current users contacts
	 */
	public DrawingEnvironmentLauncher(String user, ArrayList<String> contactList)
	{
		//Create a new frame to hold the DrawingEnvironment
		JFrame frame = new JFrame(FRAME_TITLE);
		//Create and initialize a new DrawingEnvironment to be added to the frame
		DrawingEnvironment environment = new DrawingEnvironment(user, contactList);
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.add(environment);
		
		//Listener that reacts when the frame close button is clicked
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) 
		    {
		    	//If the user clicks yes on the optionpane, exit the program
		        if (JOptionPane.showConfirmDialog(frame, 
		        		m_OutputMessage, DIALOG_SCREEN_TITLE, 
		            JOptionPane.YES_NO_OPTION,
		            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
		            System.exit(0);
		        }
		    }
		});
		//Set the frame to not close on default so it does not close if the user selects yes above
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		//Make the frame visible
		frame.setVisible(true);
	}
	
	/** The frame title */
	private final String FRAME_TITLE = "Drawing Environment";
	/** The frame width */
	private final int FRAME_WIDTH =500;
	/** The frame height */
	private final int FRAME_HEIGHT = 550;
	/** The string to be shown the JOptionPane if the user is currently drawing */
	private static final String MESSAGE1 = "Are you sure you want to close the window?" 
											+ "\n" + "All unsaved progress will be lost.";
	/** The string to be shown the JOptionPane if the user is NOT currently drawing */
	private static final String MESSAGE2 = "Are you sure you want to close the window?";
	/** The output message displayed */
	private static String m_OutputMessage = MESSAGE2;
	/** The title of the popup window */
	private final String DIALOG_SCREEN_TITLE = "Confirm Close Window?";
}
