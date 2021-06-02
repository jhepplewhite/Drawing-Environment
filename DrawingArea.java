import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @File DrawingArea.java
 * @author Jonathan Hepplewhite
 * @date 9 Dec 2016
 * @brief DrawingArea is the area which the user draws on screen 
 * \n \n
 * DrawingArea is a class that that displays the area which the user
 * can draw to. The DrawingArea uses a nested DrawingListener to 
 * take the user mouse input and draw DrawingElements to the DrawingArea.
 */
public class DrawingArea extends JPanel
{
	/**
	 * Constructor that creates a new DrawingArea
	 * @param fileName The name the user specifies for the file
	 * @param isNew Specifies if this is a new or loaded drawing
	 */
	public DrawingArea(String fileName, Boolean isNew)
	{
		this.fileName = fileName;
		//Set the layout to null to allow custom placement of components
		this.setLayout(null);
		//Make the DrawingArea visible
		this.setVisible(true);
		this.setBackground(Color.WHITE);
		//Create the listener which will catch all drawing actions and add it to the this DrawingArea
		DrawingListener drawingListener = new DrawingListener();
		this.addMouseListener(drawingListener);
		this.addMouseMotionListener(drawingListener);
		//Initialize the BufferedImage which will hold the drawn elements
		drawing = new BufferedImage(FILE_WIDTH,FILE_HEIGHT,
									BufferedImage.TYPE_INT_ARGB);
		//If the drawing is new, run new setup, else load setup
		if(isNew)
		{
			initialiseNewDrawing();
		}
		else
		{
			initialiseLoadDrawing();
		}
		//Ensure all components are displayed on screen
		repaint();
	}
	
	/**
	 * Draw the DrawingArea panel and any drawn elements
	 */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		//Draw all drawn DrawingElements to screen
		for(DrawingElement e: myDrawingElements)
		{
			e.draw(g);
		}
	}
	
	/**
	 * Save the current state of the drawing to the file system
	 */
	public void saveImage()
	{
		//Try to write the draiwng to file
		try 
		{
			//Make sure all drawn elements are contained by BufferedImage
			this.paintAll(drawing.getGraphics());
			//Write drawing to the file system in the designated file location and file extension 
			ImageIO.write(drawing, IMAGE_FILE_TYPE, 
								new File(fileName + "." + IMAGE_FILE_TYPE));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	
	/**
	 * If a drawing was loaded, load the image file and apply it to the DrawingArea
	 */
	private void initialiseLoadDrawing()
	{
		//Try to read the image file and add it to the BufferedImage
		try 
		{
			File image = new File(fileName + "." + IMAGE_FILE_TYPE);
			drawing = ImageIO.read(image);
		} 
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
		//Create a JLabel which will display the image on screen
		JLabel picture = new JLabel(new ImageIcon(fileName + "." + IMAGE_FILE_TYPE))
		{
		//Paint drawing elements to the JLabel
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			//Draw all drawn DrawingElements to JLabel
			for(DrawingElement e: myDrawingElements)
			{
				e.draw(g);
			}
		}};
		//Add the loaded drawing image and ensure it displays properly
		this.add(picture);
		picture.setBounds(0, 0, FILE_WIDTH, FILE_HEIGHT);
		picture.setVisible(true);
	}
	
	/**
	 * If this is a new drawing, setup a new JPanel
	 */
	private void initialiseNewDrawing()
	{
		JPanel p = new JPanel()		
		{
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				//Draw all drawn DrawingElements to screen
				for(DrawingElement e: myDrawingElements)
				{
					e.draw(g);
				}
			}};
		//Make background white and make visible
		p.setBackground(Color.WHITE);
		this.setVisible(true);
	}
	
	/**
	 * @author Jonathan Hepplewhite
	 * @Date 9 Dec 2016
	 * 
	 * Class that deals with all drawing area mouse inputs
	 */
	private class DrawingListener implements MouseMotionListener, MouseListener
	{
		//Methods required by MouseListener and MouseMotionListener that are not used
		public void mouseClicked(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mouseMoved(MouseEvent e) {}	

		/**
		 * Method that is called if the mouse is moved across the screen whilst pressed
		 */
		@Override
		public void mouseDragged(MouseEvent e) 
		{
			//If the current selected element is ParicleTrace
			if(DrawingEnvironment.getCurrentElement() == 0)
			{
				//Create a new ParticleTrace object with the current mouse coordinates
				DrawingElement newElement = new ParticleTrace(e.getX(), e.getY());
				//Add this new ParticleTrace to the Arraylist of all DrawingElements
				myDrawingElements.add(newElement);
				//Draw the elements to screen
				repaint();
			}
			//If the current selected element is Line
			else if (DrawingEnvironment.getCurrentElement() == 1)
			{
				//Set the X and Y coordinates of the Line 
				((Line) myDrawingElements.get(myDrawingElements.size()- 1)).setEndX(e.getX());
				((Line) myDrawingElements.get(myDrawingElements.size()- 1)).setEndY(e.getY());
				//Paint the line to screen at its current position
				repaint();
			}
		}
		
		/**
		 * Method that is called is the mouse is pressed
		 */
		@Override
		public void mousePressed(MouseEvent e) 
		{
			//If the current element is Line
			if(DrawingEnvironment.getCurrentElement() == 1)
			{
				//Create a new Line element and add it the the ArrayList of all elements 
				DrawingElement newElement = new Line(e.getX(), e.getY());
				myDrawingElements.add(newElement);
			}
		}

		/**
		 * Method that is called of the mouse is released
		 */
		@Override
		public void mouseReleased(MouseEvent e) 
		{
			//If the current element is Line
			if(DrawingEnvironment.getCurrentElement() == 1)
			{
				//Set the X and Y coordinates of the Line 
				((Line) myDrawingElements.get(myDrawingElements.size()- 1)).setEndX(e.getX());
				((Line) myDrawingElements.get(myDrawingElements.size()- 1)).setEndY(e.getY());
				//Paint the line to screen at its final position
				repaint();
			}
		}
	}
	
	/** The width of the drawing */
	private final int FILE_WIDTH = 500;
	/** The height of the drawing */
	private final int FILE_HEIGHT = 500;
	/** The location of the drawings and save file */
	/** The image file type */
	private final String IMAGE_FILE_TYPE = "png";
	/** The BufferedImage involved with saving the drawing to file */
	private BufferedImage drawing;
	/** The ArrayList that stores all the DrawingElement objects */
	private ArrayList<DrawingElement> myDrawingElements = new ArrayList<>();
	/** The name of the file */
	private String fileName;
	

}