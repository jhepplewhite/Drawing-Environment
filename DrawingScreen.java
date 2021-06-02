import java.awt.Dimension;
import javax.swing.JPanel;

/**
 * @File DrawingScreen.java
 * @author Jonathan Hepplewhite
 * @date 9 Dec 2016
 * @brief DrawingScreen holds the DrawingArea and the DrawingToolBar
 * \n \n
 * DrawingScreen is a panel that holds both the drawing area (where the user draws) 
 * and the drawing tool bar (where each of the tools are held)
 */
public class DrawingScreen extends JPanel
{
	/**
	 * Constructor that creates a new DrawingScreen
	 * @param name The name of the file
	 * @param isNew True if the drawing is new, false otherwise
	 */
	public DrawingScreen(String name, Boolean isNew)
	{	
		String fileName = name;
		Boolean isNewFile = isNew;
		//Set the layout to null so that layout is customisable
		this.setLayout(null);

		//Create a new DrawingArea and DrawingToolBar
		DrawingArea drawArea = new DrawingArea(fileName, isNewFile);
		DrawingToolBar toolBar = new DrawingToolBar(drawArea);
		
		//Setup the layout of the drawArea and make it visible
		drawArea.setPreferredSize(new Dimension(FRAME_WIDTH,DRAW_AREA_HEIGHT));
		drawArea.setVisible(true);
		drawArea.setBounds(0, TOOL_BAR_HEIGHT, FRAME_WIDTH, DRAW_AREA_HEIGHT);
		
		//Setup the layout of the toolBar and make it visible
		toolBar.setPreferredSize(new Dimension(FRAME_WIDTH,TOOL_BAR_HEIGHT));
		toolBar.setVisible(true);
		toolBar.setBounds(0, 0, FRAME_WIDTH, TOOL_BAR_HEIGHT);
		
		//Add the toolBar and drawArea to this Panel
		this.add(toolBar);
		this.add(drawArea);
	}
	
	/** The frame width */
	private final int FRAME_WIDTH = 500;
	/** The draw area height */
	private final int DRAW_AREA_HEIGHT = 500;
	/** The tool bar height */
	private final int TOOL_BAR_HEIGHT = 80;
}