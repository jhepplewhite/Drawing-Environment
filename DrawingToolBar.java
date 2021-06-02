import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @File DrawingToolBar.java
 * @author Jonathan Hepplewhite
 * @date 9 Dec 2016
 * @brief Panel that holds the tools and colours available to the user
 * \n \n
 * DrawingToolBar is a panel that holds all the tools and colours that the user can
 * user when drawing on the DrawingArea
 */
public class DrawingToolBar extends JPanel implements ActionListener
{
	/**
	 * Method that handles the mouse clicks
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		switch(e.getActionCommand())
		{
		case TRACE_NAME: 	//Set the current element to 0 (ParticleTrace)
							DrawingEnvironment.setCurrentElement(0);
							toolTip.setText(TRACE_SELECTED);
							break;
		case LINE_NAME:		//Set the current element to 1 (Line)
							DrawingEnvironment.setCurrentElement(1);
							toolTip.setText(LINE_SELECTED);
							break;
		case SAVE_NAME:		//If the file does not exist
							if(!ReferenceFileReader.getFileNames().contains(
									ReferenceFileReader.getFileName().toString()))
							{
								//Create the file
								ReferenceFileReader.createFile();
							}
							//Save the image
							area.saveImage();
							break;
		default:			break;
		}
	}
	
	/**
	 * Constructor that creates a DrawingToolBar
	 * @param area A reference to the DrawingArea
	 */
	public DrawingToolBar(DrawingArea area)
	{
		this.area = area;
		//Setup the buttons
		initialiseButtons();
		
		//Create a new colour panel and set its size
		ColourPanel colourPanel = new ColourPanel();
		colourPanel.setPreferredSize(new Dimension(COLOUR_PANEL_WIDTH, 
												COLOUR_PANEL_HEIGHT));
		//Add this colour panel to this toolbar
		this.add(colourPanel);
		//Add each of the initialized buttons to this toolbar
		this.add(traceSelect);
		this.add(lineSelect);
		this.add(save);
		this.add(toolTip);
		
		//Set the size and colour of this toolbar and make it visible
		this.setPreferredSize(new Dimension(TOOLBAR_WIDTH, TOOLBAR_HEIGHT));
		this.setBackground(new Color(GRAY_RGB,GRAY_RGB,GRAY_RGB));
		this.setVisible(true);
	}
	
	/**
	 * Sets up the button
	 */
	private void initialiseButtons()
	{
		//Setup the trace button
		traceSelect = new JButton(TRACE_NAME);
		traceSelect.setActionCommand(TRACE_NAME);
		traceSelect.addActionListener(this);		
		
		//Setup the line button
		lineSelect = new JButton(LINE_NAME);
		lineSelect.setActionCommand(LINE_NAME);
		lineSelect.addActionListener(this);

		//Setup the save button
		save = new JButton(SAVE_NAME);
		save.setActionCommand(SAVE_NAME);
		save.addActionListener(this);
	}
	
	/** The width of the colour panel */
	private final int COLOUR_PANEL_WIDTH = 200;
	/** The height of the colour panel */
	private final int COLOUR_PANEL_HEIGHT = 40;
	/** The total width of the toolbar */
	private final int TOOLBAR_WIDTH = 500;
	/** The total height of the toolbar */
	private final int TOOLBAR_HEIGHT = 80;
	
	/** Fixed RGB value that creates grey
	 *  (when used for each red, green and blue value) */
	private final int GRAY_RGB = 200;
	/** Create a reference to the trace select button */
	private JButton traceSelect;
	/** Create a reference to the line select button */
	private JButton lineSelect;
	/** Create a reference to the save button */
	private JButton save;
	/** Create a reference to the drawing area */
	private DrawingArea area;
	/** Set the name and action command of the trace button */
	private final String TRACE_NAME = "Trace";
	/** Set the name and action command of the line button */
	private final String LINE_NAME = "Line";
	/** Set the name and action command of the save button */
	private final String SAVE_NAME = "Save";

	/** Create a tooltip string for when trace is selected */
	private final String TRACE_SELECTED = "Trace Selected";
	/** Create a tooltip string for when line is selected */
	private final String LINE_SELECTED = "Line Selected";
	/** Create a reference to the tooltip label */
	private JLabel toolTip = new JLabel(TRACE_SELECTED);

	
	/**
	 * @File ColourPanel
	 * @author Jon
	 * @date 9 Dec 2016
	 * @brief Creates a panel that holds the colours the user can use
	 *
	 * ColourPanel class creates a panel with each of the colours available to the user.
	 * It also handles the input when the user clicks on a button to change the colour.
	 */
	private class ColourPanel extends JPanel implements ActionListener
	{
		/**
		 * Method than handles the button presses for colours
		 */
		@Override
		public void actionPerformed(ActionEvent e)
		{
			switch (e.getActionCommand())
			{
				case(RED):		DrawingElement.setColour(Color.RED);
								break;
				case(BLACK):	DrawingElement.setColour(Color.BLACK);	
								break;
				case(WHITE):	DrawingElement.setColour(Color.WHITE);
								break;
				case(GREEN):	DrawingElement.setColour(Color.GREEN);
								break;
				case(YELLOW):	DrawingElement.setColour(Color.YELLOW);
								break;
				case(PINK):		DrawingElement.setColour(Color.PINK);
								break;
				case(BLUE):		DrawingElement.setColour(Color.BLUE);
								break;
				case(CYAN):		DrawingElement.setColour(Color.CYAN);
								break;
				case(GREY):		DrawingElement.setColour(Color.GRAY);
								break;
				case(ORANGE):	DrawingElement.setColour(Color.ORANGE);
								break;
				default:		DrawingElement.setColour(Color.BLACK);
								break;
			}
			
		}
		
		/**
		 * Constructor that sets up the ColourPanel
		 */
		public ColourPanel()
		{
			//Set the panel to have a grid layout
			this.setLayout(new GridLayout(COLOUR_ROWS,COLOUR_COLUMNS));
			//Setup each colour button
			JButton blackButton = setupBlack();
			JButton redButton = setupRed();
			JButton whiteButton = setupWhite();
			JButton greenButton = setupGreen();
			JButton yellowButton = setupYellow();
			JButton pinkButton = setupPink();
			JButton blueButton = setupBlue();
			JButton cyanButton = setupCyan();
			JButton greyButton = setupGrey();
			JButton orangeButton = setupOrange();
			
			//Add each of the created buttons to the panel
			this.add(blackButton);
			this.add(whiteButton);
			this.add(redButton);
			this.add(greenButton);
			this.add(yellowButton);
			this.add(pinkButton);
			this.add(blueButton);
			this.add(cyanButton);
			this.add(greyButton);
			this.add(orangeButton);
			repaint();
		}
		
		/**
		 * Method that sets up red button
		 */
		private JButton setupRed()
		{
			JButton b = new JButton();
			b.setBackground(Color.RED);
			b.setOpaque(true);
			b.setBorderPainted(false);
			b.setVisible(true);
			b.addActionListener(this);
			b.setActionCommand(RED);
			return b;
		}
		
		/**
		 * Method that sets up black button
		 */
		private JButton setupBlack()
		{
			JButton b = new JButton();
			b.setBackground(Color.BLACK);
			b.setOpaque(true);
			b.setBorderPainted(false);
			b.setVisible(true);
			b.addActionListener(this);
			b.setActionCommand(BLACK);
			return b;
		}
		
		/**
		 * Method that sets up white button
		 */
		private JButton setupWhite()
		{
			JButton b = new JButton();
			b.setBackground(Color.WHITE);
			b.setOpaque(true);
			b.setBorderPainted(false);
			b.setVisible(true);
			b.addActionListener(this);
			b.setActionCommand(WHITE);
			return b;
		}
		
		/**
		 * Method that sets up green button
		 */
		private JButton setupGreen()
		{
			JButton b = new JButton();
			b.setBackground(Color.GREEN);
			b.setOpaque(true);
			b.setBorderPainted(false);
			b.setVisible(true);
			b.addActionListener(this);
			b.setActionCommand(GREEN);
			return b;
		}
		
		/**
		 * Method that sets up yellow button
		 */
		private JButton setupYellow()
		{
			JButton b = new JButton();
			b.setBackground(Color.YELLOW);
			b.setOpaque(true);
			b.setBorderPainted(false);
			b.setVisible(true);
			b.addActionListener(this);
			b.setActionCommand(YELLOW);
			return b;
		}
		
		/**
		 * Method that sets up pink button
		 */
		private JButton setupPink()
		{
			JButton b = new JButton();
			b.setBackground(Color.PINK);
			b.setOpaque(true);
			b.setBorderPainted(false);
			b.setVisible(true);
			b.addActionListener(this);
			b.setActionCommand(PINK);
			return b;
		}
		
		/**
		 * Method that sets up blue button
		 */
		private JButton setupBlue()
		{
			JButton b = new JButton();
			b.setBackground(Color.BLUE);
			b.setOpaque(true);
			b.setBorderPainted(false);
			b.setVisible(true);
			b.addActionListener(this);
			b.setActionCommand(BLUE);
			return b;
		}
		
		/**
		 * Method that sets up cyan button
		 */
		private JButton setupCyan()
		{
			JButton b = new JButton();
			b.setBackground(Color.CYAN);
			b.setOpaque(true);
			b.setBorderPainted(false);
			b.setVisible(true);
			b.addActionListener(this);
			b.setActionCommand(CYAN);
			return b;
		}
		
		/**
		 * Method that sets up grey button
		 */
		private JButton setupGrey()
		{
			JButton b = new JButton();
			b.setBackground(Color.GRAY);
			b.setOpaque(true);
			b.setBorderPainted(false);
			b.setVisible(true);
			b.addActionListener(this);
			b.setActionCommand(GREY);
			return b;
		}
		
		/**
		 * Method that sets up orange button
		 */
		private JButton setupOrange()
		{
			JButton b = new JButton();
			b.setBackground(Color.ORANGE);
			b.setOpaque(true);
			b.setBorderPainted(false);
			b.setVisible(true);
			b.addActionListener(this);
			b.setActionCommand(ORANGE);
			return b;
		}
		
		/**
		 * Method that paints the ColourPanel
		 */
		@Override
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
		}
		
		/** String for the name and action command for the red button */
		private final String RED = "red";
		/** String for the name and action command for the black button */
		private final String BLACK = "black";
		/** String for the name and action command for the white button */
		private final String WHITE = "white";
		/** String for the name and action command for the green button */
		private final String GREEN = "green";
		/** String for the name and action command for the yellow button */
		private final String YELLOW = "yellow";
		/** String for the name and action command for the pink button */
		private final String PINK = "pink";
		/** String for the name and action command for the blue button */
		private final String BLUE = "blue";
		/** String for the name and action command for the cyan button */
		private final String CYAN = "cyan";
		/** String for the name and action command for the grey button */
		private final String GREY = "grey";
		/** String for the name and action command for the orange button */
		private final String ORANGE = "orange";
		/** Number of rows the panel has */
		private final int COLOUR_ROWS = 2;
		/** Number of columns the panel has */
		private final int COLOUR_COLUMNS = 5;
	}

}