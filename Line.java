import java.awt.Color;
import java.awt.Graphics;

/**
 * @File Line.java
 * @author Jonathan Hepplewhite
 * @date 5 Dec 2016
 * @see DrawingElement.java
 * @brief Line is an object that can be drawn to the screen.
 * \n \n
 * Line is an object that can be drawn to the screen. 
 * It inherits from DrawingElement
 */
public class Line extends DrawingElement
{
	/**
	 * @return The lines end X position
	 */
	public int getEndX()
	{
		return endX;
	}
	
	/**
	 * @return The lines end Y position
	 */
	public int getEndY()
	{
		return endY;
	}
	
	/**
	 * Set the lines end X position
	 * @param x The X position
	 */
	public void setEndX(int x)
	{
		endX = x;
	}
	
	/**
	 * Set the lines end Y position
	 * @param x The Y position
	 */
	public void setEndY(int y)
	{
		endY = y;
	}
	
	/**
	 * Draw the line to screen
	 */
	public void draw(Graphics g)
	{
		g.setColor(colour);
		g.drawLine(getStartX(), getStartY(), getEndX(), getEndY());	
	}
	
	/**
	 * Constructor that creates a Line object
	 * @param x1 The start X position of the Line
	 * @param y1 The start Y position of the Line
	 */
	public Line(int x1, int y1)
	{
		super(x1,y1);
		colour = super.getColour(); 
	}
		
	/** The colour of the line */
	private Color colour;
	/** The end X coordinate of the line */
	private int endX;
	/** The end Y coordinate of the line */
	private int endY;
}
