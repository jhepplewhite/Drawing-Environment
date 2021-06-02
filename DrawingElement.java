import java.awt.Color;
import java.awt.Graphics;

/**
 * @File DrawingElement.java
 * @author Jonathan Hepplewhite
 * @date 5 Dec 2016
 * @brief Abstract class that specifies objects draw to screen
 * \n \n
 * Abstract class that specifies information about a DrawingElement
 * that is drawn to screen.
 */
public abstract class DrawingElement 
{
	/**
	 * @return The current colour
	 */
	public static Color getColour()
	{
		return m_Colour;
	}
	
	/**
	 * @return The current drawing size
	 */
	public static int getDrawSize()
	{
		return m_DrawSize;
	}
	
	/**
	 * @return The start x position
	 */
	public int getStartX()
	{
		return x1;
	}
	
	/**
	 * @return The start y position
	 */
	public int getStartY()
	{
		return y1;
	}
	
	/**
	 * Set the drawing size
	 * @param size
	 */
	public static void setDrawSize(int size)
	{
		m_DrawSize = size;
	}
	
	/**
	 * Set the current colour of the DrawingElement
	 * @param c The colour to set to
	 */
	public static void setColour(Color c)
	{
		m_Colour = c;
	}
	
	/**
	 * Constructor for a DrawingElement
	 * @param x1 The start x position of the element
	 * @param y1 The start y position of the element
	 */
	public DrawingElement(int x1, int y1)
	{
		this.x1 = x1;
		this.y1 = y1;
		setDrawSize(INITIAL_DRAWSIZE);
	}
	
	/**
	 * Abstract method that the child classes implement to 
	 * draw themselves to screen
	 * @param g
	 */
	public abstract void draw(Graphics g);
	
	
	/** The initial drawsize of the element */
	private final int INITIAL_DRAWSIZE = 5;
	/** The colour of the element */
	private static Color m_Colour;
	/** The current drawsize of the element */
	private static int m_DrawSize;
	/** The start x position */
	private int x1;
	/** The start y position */
	private int y1;
}
