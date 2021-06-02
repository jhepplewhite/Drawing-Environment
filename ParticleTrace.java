import java.awt.Color;
import java.awt.Graphics;

/**
 * @File Particletrace.java
 * @author Jonathan Hepplewhite
 * @date 5 Dec 2016
 * @see DrawingElement.java	
 * @brief ParticleTrace is an object that can be drawn to the screen. 
 * \n \n
 * ParticleTrace is an object that can be drawn to the screen. 
 * It inherits from DrawingElement
 */
public class ParticleTrace extends DrawingElement
{
	/**
	 * Draw the ParticleTrace to the screen
	 */
	public void draw(Graphics g)
	{
		g.setColor(colour);
		g.fillOval(getStartX(), getStartY(), 
					getDrawSize(), getDrawSize());
	}
	
	/**
	 * Constructor that creates a new ParticleTrace
	 * @param x1 The start X position
	 * @param y1 The start Y position
	 */
	public ParticleTrace(int x1, int y1)
	{
		super(x1, y1);
		colour = super.getColour();
	}
	
	/** The colour of the ParticleTrace */ 
	private Color colour;
}
