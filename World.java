/**
Amirhosein Soleimanian
Grade 11 Computer Science
Mr.Benum
*/

//Import the necessary packages
import java.awt.*;
import java.util.*;
import java.awt.image.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;
import java.awt.event.*;

/**
 * The public variables are located in Util class
 */
class Util
{
	public static final int MAX_R = 250;
	public static final int PLAYER_RADIUS = 15;
	public static final int ARROW_LENGTH = 20;
	public static final double ARROW_SPEED = 2;
	public static final double ENEMY_FREQUENCY = 1;
	public static int score;
}

/**
 * This is where the whole game components come together and it is called world
 * @author Amirhosein Soleimanian
 * @version 1.0
 */
public class World
{
	public Player player; //The player
	public Level[] levels; //The levels
	public int level = 0; //The current level
	public boolean win = false; //if the player wins the game
	public boolean lose = false; //This is used to determine if the player has lost

	public World(Player player, Level... levels) //Constructor
	{
		this.player = player; //Initialize the player
		this.levels = levels; //Initialize the levels
	}

	public void tryKey(int key) //This is used to try a key
	{
		for(int i = 0; i < levels.length; i++) // loop through all the levels and check the keys
		{
			if(levels[i].key==key) // if the key is correct
			{
				level = i; // set the level to the current level
				i = levels.length; 
			}
		}
	}

	/**
	 * This is used to get the level
	 * @return the level
	 */
	public Level getLevel() //This is used to get the current level
	{
		return levels[level]; // return the current level
	}

	public void update() //This is used to update the world
	{
		if(!win && !lose) // if the player has not won or lost
		{
			getLevel().update(); // update the level
			player.hit(getLevel().getHitting()); // check if the player has hit the enemy
			if(player.dead()) // if the player is dead
				lose = true; // set the lose to true
			if(getLevel().finished()&& !lose) // if the level is finished and the player has not lost
			{ // set the level to the next level
				player.heal(); // heal the player for the next level
				level++; // change the level
				if(level==levels.length) // if the level is the last level
				{
					win = true; // set the win to true
					level--; // set the level to the last level
				}
			}
		}
	}


	public void draw(Graphics g) //This is used to draw the world
	{
		try { 
			FileReader reader = new FileReader("S.txt"); // read the file for the score variable
			BufferedReader bufferedReader = new BufferedReader(reader); // create a buffered reader
			String line; // create a string called line
			while ((line = bufferedReader.readLine()) != null) { // while the line is not null or empty read the file
				Util.score = Integer.parseInt(line); // set the score to the line (from string to int for the score)
			}
			reader.close(); // close the file
		} catch (IOException e) { // if the file is not found
			e.printStackTrace(); // print the error
		}
		try { // this will delete everything that is in the file
			FileWriter writer = new FileWriter("S.txt"); // open the file we need to write to it for score.
			BufferedWriter bufferedWriter = new BufferedWriter(writer); // create a buffered writer
			bufferedWriter.write(""); // delete every item in the file
			bufferedWriter.close(); // close the file
		} catch (IOException e) { // if the file is not found
			e.printStackTrace(); // print the error
		}
		getLevel().drawEnemies(g); // draw the enemies
		player.draw(g); // draw the player
		g.setColor(Color.BLACK); // set the color to black
		g.setFont(new Font("Dialog",Font.BOLD,14)); // set the font to Dialog and bold and set size 14
		g.drawString("Level: "+(level+1),5,15); // draw the level
		g.drawString("Enter the Answer: ",5,30); // draw the question
		g.drawString("score: "+ Util.score,5,60); // draw the score
		if(win) // if the player has won
		{
			g.setColor(Color.GREEN); // set the text color to green
			g.setFont(new Font("Dialog",Font.BOLD,14)); // set the font to Dialog and bold and set size 14
			g.drawString("You WIN!",140,150); // draw the win message
			try {
				FileWriter writer = new FileWriter("S.txt"); // open the file we need to write to it for score.
				BufferedWriter bufferedWriter = new BufferedWriter(writer); // create a buffered writer
				bufferedWriter.write(""); // delete every item in the file
				bufferedWriter.write(Integer.toString(Util.score)); // write the score to the file for future games
				bufferedWriter.close(); // close the file
			} catch (IOException e) { // if the file is not found
				e.printStackTrace(); // print the error
			}
		}
		if(lose) // if the player has lost
		{
			g.setColor(Color.RED); // set the text color to red
			g.setFont(new Font("Dialog",Font.BOLD,14)); // set the font to Dialog and bold and set size 14
			g.drawString("You lose.",220,150); 	// draw the lose message
			try {
				FileWriter writer = new FileWriter("S.txt"); // open the file we need to write to it for score.
				BufferedWriter bufferedWriter = new BufferedWriter(writer); // create a buffered writer
				bufferedWriter.write(""); // delete every item in the file
				bufferedWriter.write(Integer.toString(Util.score)); // write the score to the file for future games
				bufferedWriter.close(); // close the file
			} catch (IOException e) { // if the file is not found
				e.printStackTrace(); // print the error
			}
		}
	}

	/**
	 * This class will make the levels and it is called get world
	 * @return levels from 0 to 16
	 */
	public static World getWorld() //This is used to get the world
	{
		Player player = new Player(); // create a new player

		//Level 1
		Vector<Enemy> l1e = new Vector(); // create a vector for the enemies
		for(int i = 0; i < 10; i ++) l1e.add(new Addition()); // add 10 addition enemies to the vector
		Level l1 = new Level(2,1234567,l1e); // create a level a key and a vector of enemies

		//Level 2
		Vector<Enemy> l2e = new Vector(); // create a vector for the enemies
		for(int i = 0; i < 8; i ++) l2e.add(new Addition()); // add 8 addition enemies to the vector
		for(int i = 0; i < 5; i ++) l2e.add(new Multiplication()); // add 5 multiplication enemies to the vector
		Level l2 = new Level(2,6394658,l2e); // create a level a key and a vector of enemies

		//Level 3
		Vector<Enemy> l3e = new Vector(); // create a vector for the enemies
		for(int i = 0; i < 5; i ++) l3e.add(new Addition()); // add 5 addition enemies to the vector
		for(int i = 0; i < 8; i ++) l3e.add(new Multiplication()); // add 8 multiplication enemies to the vector
		Level l3 = new Level(3,1563826,l3e); // create a level a key and a vector of enemies

		//Level 4
		Vector<Enemy> l4e = new Vector(); // create a vector for the enemies
		for(int i = 0; i < 7; i ++) l4e.add(new Addition()); // add 7 addition enemies to the vector
		for(int i = 0; i < 7; i ++) l4e.add(new Multiplication()); // add 7 multiplication enemies to the vector
		for(int i = 0; i < 3; i ++) l4e.add(new Subtraction()); // add 3 subtraction enemies to the vector
		Level l4 = new Level(3,1927462,l4e); // create a level a key and a vector of enemies

		//Level 5
		Vector<Enemy> l5e = new Vector(); // create a vector for the enemies
		for(int i = 0; i < 7; i ++) l5e.add(new Addition()); // add 7 addition enemies to the vector
		for(int i = 0; i < 7; i ++) l5e.add(new Multiplication()); // add 7 multiplication enemies to the vector
		for(int i = 0; i < 5; i ++) l5e.add(new Subtraction()); // add 5 subtraction enemies to the vector
		for(int i = 0; i < 3; i ++) l5e.add(new Division()); // add 3 division enemies to the vector
		Level l5 = new Level(3,3728465,l5e); // create a level a key and a vector of enemies

		//Level 6
		Vector<Enemy> l6e = new Vector(); // create a vector for the enemies
		for(int i = 0; i < 7; i ++) l6e.add(new Addition()); // add 7 addition enemies to the vector
		for(int i = 0; i < 7; i ++) l6e.add(new Multiplication()); // add 7 multiplication enemies to the vector
		for(int i = 0; i < 7; i ++) l6e.add(new Subtraction()); // add 7 subtraction enemies to the vector
		for(int i = 0; i < 5; i ++) l6e.add(new Division()); // add 5 division enemies to the vector
		Level l6 = new Level(3,7384920,l6e);  // create a level a key and a vector of enemies

		//Level 7
		Vector<Enemy> l7e = new Vector(); // create a vector for the enemies
		for(int i = 0; i < 5; i ++) l7e.add(new BigAddition()); // add 5 big addition enemies to the vector
		for(int i = 0; i < 7; i ++) l7e.add(new Multiplication()); // add 7 multiplication enemies to the vector
		for(int i = 0; i < 7; i ++) l7e.add(new Subtraction()); // add 7 subtraction enemies to the vector
		for(int i = 0; i < 7; i ++) l7e.add(new Division()); // add 7 division enemies to the vector
		Level l7 = new Level(3,6374198,l7e); // create a level a key and a vector of enemies

		//Level 8
		Vector<Enemy> l8e = new Vector(); // create a vector for the enemies
		for(int i = 0; i < 5; i ++) l8e.add(new BigAddition()); // add 5 big addition enemies to the vector
		for(int i = 0; i < 1; i ++) l8e.add(new BigMultiplication()); // add 1 big multiplication enemies to the vector
		for(int i = 0; i < 7; i ++) l8e.add(new Subtraction()); // add 7 subtraction enemies to the vector
		for(int i = 0; i < 7; i ++) l8e.add(new Division()); // add 7 division enemies to the vector
		Level l8 = new Level(3,2538610,l8e); // create a level a key and a vector of enemies

		//Level 9
		Vector<Enemy> l9e = new Vector(); // create a vector for the enemies
		for(int i = 0; i < 5; i ++) l9e.add(new BigAddition()); // add 5 big addition enemies to the vector
		for(int i = 0; i < 1; i ++) l9e.add(new BigMultiplication()); // add 1 big multiplication enemies to the vector
		for(int i = 0; i < 7; i ++) l9e.add(new Subtraction()); // add 7 subtraction enemies to the vector
		for(int i = 0; i < 7; i ++) l9e.add(new Division()); // add 7 division enemies to the vector
		Level l9 = new Level(3,1527943,l9e);
		
		//Level 10
		Vector<Enemy> l10e = new Vector(); // create a vector for the enemies
		for(int i = 0; i < 5; i ++) l10e.add(new Addition()); // add 5 addition enemies to the vector
		for(int i = 0; i < 1; i ++) l10e.add(new Multiplication()); // add 1 multiplication enemies to the vector
		for(int i = 0; i < 7; i ++) l10e.add(new Subtraction()); // add 7 subtraction enemies to the vector
		for(int i = 0; i < 7; i ++) l10e.add(new Division()); // add 7 division enemies to the vector
		Level l10 = new Level(3,4268197,l10e);

		//Level 11
		Vector<Enemy> l11e = new Vector(); // create a vector for the enemies
		for(int i = 0; i < 2; i ++) l11e.add(new BigAddition()); // add 2 big addition enemies to the vector
		for(int i = 0; i < 6; i ++) l11e.add(new BigMultiplication()); // add 6 big multiplication enemies to the vector
		for(int i = 0; i < 7; i ++) l11e.add(new Subtraction()); // add 7 subtraction enemies to the vector
		Level l11 = new Level(3,1738295,l11e); // create a level a key and a vector of enemies

		//Level 12
		Vector<Enemy> l12e = new Vector(); // create a vector for the enemies
		for(int i = 0; i < 5; i ++) l12e.add(new BigAddition()); // add 5 big addition enemies to the vector
		for(int i = 0; i < 1; i ++) l12e.add(new BigMultiplication()); // add 1 big multiplication enemies to the vector
		for(int i = 0; i < 7; i ++) l12e.add(new Subtraction()); // add 7 subtraction enemies to the vector
		for(int i = 0; i < 7; i ++) l12e.add(new Division()); // add 7 division enemies to the vector
		Level l12 = new Level(3,0651243,l12e); // create a level a key and a vector of enemies

		//Level 13
		Vector<Enemy> l13e = new Vector(); // create a vector for the enemies
		for(int i = 0; i < 5; i ++) l13e.add(new BigAddition()); // add 5 big addition enemies to the vector
		for(int i = 0; i < 1; i ++) l13e.add(new BigMultiplication()); // add 1 big multiplication enemies to the vector
		for(int i = 0; i < 7; i ++) l13e.add(new Subtraction());	// add 7 subtraction enemies to the vector
		for(int i = 0; i < 7; i ++) l13e.add(new Division()); // add 7 division enemies to the vector
		Level l13 = new Level(3,3628491,l13e);


		//Level 14
		Vector<Enemy> l14e = new Vector(); // create a vector for the enemies
		for(int i = 0; i < 9; i ++) l14e.add(new Subtraction()); // add 9 subtraction enemies to the vector
		for(int i = 0; i < 7; i ++) l14e.add(new Division()); // add 7 division enemies to the vector
		Level l14 = new Level(3,4016275,l14e); 	// create a level a key and a vector of enemies


		//Level 15
		Vector<Enemy> l15e = new Vector(); // create a vector for the enemies
		for(int i = 0; i < 5; i ++) l15e.add(new BigAddition()); // add 5 big addition enemies to the vector
		for(int i = 0; i < 7; i ++) l15e.add(new BigMultiplication()); // add 7 big multiplication enemies to the vector
		Level l15 = new Level(3,6142063,l15e); // create a level a key and a vector of enemies

		//Level 16
		Vector<Enemy> l16e = new Vector(); // create a vector for the enemies
		for(int i = 0; i < 1; i ++) l16e.add(new BigAddition()); // add 1 big addition enemies to the vector
		for(int i = 0; i < 7; i ++) l16e.add(new BigMultiplication()); // add 7 big multiplication enemies to the vector
		for(int i = 0; i < 2; i ++) l16e.add(new Subtraction()); // add 2 subtraction enemies to the vector
		for(int i = 0; i < 10; i ++) l16e.add(new Division()); // add 10 division enemies to the vector
		Level l16 = new Level(3,5390528,l16e); // create a level a key and a vector of enemies

		return new World(player,l1,l2,l3,l4,l5,l6,l7,l8,l9,l10,l11,l12,l13,l14,l15,l16); // return the world
	}
}

/**
 * This class is for capturing the mouse and keyboard events.
 * @return the input that we get from the player
 */
abstract class ListeningGameComponent extends GameComponent implements MouseListener, MouseMotionListener, KeyListener // create a class that extends GameComponent and implements MouseListener, MouseMotionListener, KeyListener
{
	public boolean mousePressed1 = false, mousePressed2 = false, mousePressed3 = false; // create a boolean for the mouse buttons
	public ArrayList<String> keysPressed = new ArrayList(); // create an array list for the keys pressed
	public boolean debug = false; // create a boolean for the debug mode

	public int mouseX = 0; // The x location of the mouse.
	public int mouseY = 0; // The y location of the mouse.

	/**
		Constructs a ListeningGameComponent with a width of w, and a height of h.
		@param w the width of the component
		@param h the height of the component
	*/
	public ListeningGameComponent(int w, int h) // create a constructor that takes in a width and height
	{
		super(w,h); // call the super constructor
		addMouseListener(this); // add the mouse listener
		addMouseMotionListener(this); // add the mouse motion listener
		addKeyListener(this); // add the key listener
	}

	/**
		The method that draws the component called draw that gets the g "graphics" object.
	*/
	public abstract void draw(Graphics g); 
	/**
		The method that updates the component.
	*/
	public abstract void update();
	/**
		Does nothing. Activated when the mouse is pressed and released.
	*/
	public void mouseClicked(MouseEvent e){
		// nothing here
	}
	/**
		Does nothing. Activated when the mouse enters the component.
	*/
	public void mouseEntered(MouseEvent e){
		// nothing here
	}

	/**
		Does nothing. Activated when the mouse exits the component.
	*/
	public void mouseExited(MouseEvent e){
		// nothing here
	}

	/**
		Updates the mouse variables.
	*/
	public void mousePressed(MouseEvent e)
	{
		if(e.getButton() == MouseEvent.BUTTON1) // if the mouse button is the left button
			mousePressed1 = true; // set the mouse pressed to true
		if(e.getButton() == MouseEvent.BUTTON2) // if the mouse button is the middle button
			mousePressed2 = true; // set the mouse pressed to true
		if(e.getButton() == MouseEvent.BUTTON3) // if the mouse button is the right button
			mousePressed3 = true; // set the mouse pressed to true
	}

	/**
		Updates the mouse variables.
	*/
	public void mouseReleased(MouseEvent e)
	{
		if(e.getButton() == MouseEvent.BUTTON1) // if the mouse button is the left button
			mousePressed1 = false; // set the mouse pressed to false
		if(e.getButton() == MouseEvent.BUTTON2) // if the mouse button is the middle button
			mousePressed2 = false; // set the mouse pressed to false
		if(e.getButton() == MouseEvent.BUTTON3) // if the mouse button is the right button
			mousePressed3 = false; // set the mouse pressed to false
	}

	/**
		Updates the mouse variables.
	*/
	public void mouseDragged(MouseEvent e)
	{
		if(e.getButton() == MouseEvent.BUTTON1) // if the mouse button is the left button
			mousePressed1 = !mousePressed1; // set the mouse pressed to the opposite of what it was
		if(e.getButton() == MouseEvent.BUTTON2) // if the mouse button is the middle button
			mousePressed2 = !mousePressed2; // set the mouse pressed to the opposite of what it was
		if(e.getButton() == MouseEvent.BUTTON3) // if the mouse button is the right button
			mousePressed3 = !mousePressed3; // set the mouse pressed to the opposite of what it was
		mouseX = e.getX(); // set the mouse x to the x location of the mouse
		mouseY = e.getY(); // set the mouse y to the y location of the mouse
	}

	/**
		Updates the mouse variables.
	*/
	public void mouseMoved(MouseEvent e)
	{
		mousePressed1 = false; // set the mouse pressed to false
		mousePressed2 = false; // set the mouse pressed to false
		mousePressed3 = false; // set the mouse pressed to false
		mouseX = e.getX(); // set the mouse x to the x location of the mouse
		mouseY = e.getY(); // set the mouse y to the y location of the mouse
	}

	/**
		Updates the keyboard variables.
	*/
	public void keyPressed(KeyEvent e){
		// nothing here
	}

	/**
		Updates the keyboard variables.
	*/
	public void keyReleased(KeyEvent e)
	{
		for(int i = 0; i < keysPressed.size(); i++) // for each key in the keys pressed array list
		{
			if(keysPressed.get(i).equals(KeyEvent.getKeyText(e.getKeyCode()))) // if the key in the array list is the same as the key pressed
			{
				keysPressed.remove(i); // remove the key from the array list
				i--; // decrement i
			}
		}
	}

	/**
		Updates the keyboard variables.
	*/
	public void keyTyped(KeyEvent e){
		// nothing here
	}

	/**
		Returns weather a mouse button is pressed.
		@return true if the button is pressed
	*/
	public boolean isMousePressed(int b)
	{
		if(b == 1) // if the button is the left button
			return mousePressed1; // return the mouse pressed
		else if(b == 2) // if the button is the middle button
			return mousePressed2; // return the mouse pressed
		else if(b == 3) // if the button is the right button
			return mousePressed3; // return the mouse pressed

		return false; // return false if the button is not the left, middle, or right button
	}

	/**
		Returns weather any mouse button is pressed.
		@return true if the button is pressed
	*/
	public boolean isMousePressed()
	{
		if(mousePressed1) // if the left button is pressed
			return mousePressed1; // return the mouse pressed
		else if(mousePressed2) // if the middle button is pressed
			return mousePressed2; // return the mouse pressed
		else if(mousePressed3) // if the right button is pressed
			return mousePressed3; // return the mouse pressed

		return false; // return false if the button is not the left, middle, or right button
	}

	/**
		Returns weather a mouse button is pressed.
		@return true if the key is pressed
	*/
	public boolean isKeyPressed(String k)
	{
		for(int i = 0; i < keysPressed.size(); i++) // for each key in the keys pressed array list
		{
			if(keysPressed.get(i).equalsIgnoreCase(k)) // if the key in the array list is the same as the key pressed
			{
				return true; // return true
			}
		}
		return false;
	}

	//returns the number of keys pressed.
	public int getKeysPressed()
	{
		return keysPressed.size();
	}

	// clears the keys pressed array list.
	public void resetKeys()
	{
		keysPressed = new ArrayList();
	}
}

/**
 * This class is used to create a window that can be used to display graphics.
 * this game is made with help of JPanel and JFrame.
 * @return the graphics window
 */
abstract class GameComponent extends JPanel
{
	public static int WIDTH, HEIGHT; // the width and height of the window
	protected BufferedImage background = null; // the background image
	public int delay = 25; // the delay of the game

	/**
		Constructs a GameComponent with a width of w, and a height of h.
		@param w the width of the window
		@param h the height of the window
	*/
	public GameComponent(int w, int h)
	{
		super(); // call the super constructor
		WIDTH = w; // set the width
		HEIGHT = h; // set the height
		setSize(WIDTH, HEIGHT); // set the size of the window
		setPreferredSize(new Dimension(WIDTH, HEIGHT)); // set the preferred size of the window
		setBackground(Color.WHITE); // set the background color to white
		setVisible(true); // set the window to visible
	}

	/**
	 * start is where we gather all the graphics components and add them to the window.
	 */
	public void start()
	{
		Thread t = new Thread() // create a new thread
		{
			public void run() // run the graphics
			{
				while(true) 
				{
					long time = System.currentTimeMillis(); // get the time
					if(background == null) // if background is null
					{
						background = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB); // create a new background
						background.getGraphics().setColor(Color.WHITE); // set the background color to white
						background.getGraphics().fillRect(0,0,WIDTH,HEIGHT); // fill the background with white
					}

					requestFocus(); // request focus from windows

					//update game state
					standardUpdates();
					update();

					//draw stuff
					standardDraw(getCanvas());
					draw(background.getGraphics()); // draw the background
					refreshImage(); // refresh the page

					time = System.currentTimeMillis()-time; 
					try {
						if(delay-(int)time > 0) // if the delay is greater than the time
							sleep(delay-(int)time); // sleep for the difference
					}
					catch(Exception ex) { 
						ex.printStackTrace(); // print the stack trace
					}
				}
			}
		};
		try{Thread.sleep(500);}catch(Exception ex){} // sleep for half a second
		t.start(); // start the thread
	}

	/**get a blank image to draw onto
	 * @return the blank image/the background
	 */
	private Graphics getCanvas()
	{
		if(background == null) 
		{
			background = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB); // create a new background
		}
		background.getGraphics().setColor(Color.WHITE); // set the background color to white
		background.getGraphics().fillRect(0,0,WIDTH,HEIGHT); // fill the background with white
		return background.getGraphics(); // return the background
	}

	//take the canvas that you have drawn on and draw it onto the component
	private void refreshImage()
	{
		if(background != null)
		{
			if(getGraphics() != null) // if the graphics is not null
			{
				getGraphics().drawImage(background,0,0,null); // draw the background
			}
		}
	}

	/**
		Creates a JFrame that contains this GameComponent.
		@param title the title of the window
		@return the JFrame
	*/
	public JFrame makeTestWindow()
	{
		JFrame frame = new JFrame(); // create a new frame
		frame.getContentPane().setLayout(new FlowLayout()); // set the layout to flow layout
		frame.getContentPane().add(this); // add the component to the frame
		frame.pack(); // pack the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // set the default close operation to exit on close
		frame.setVisible(true); // set the frame to visible
		return frame; // return the frame
	}

	/**
		Creates a fullscreen JFrame that contains this GameComponent.
		Note that the width and height of the component must be 640x480
		@param title the title of the window
		@return the JFrame created
	*/
	public JFrame makeFullScreenWindow()
	{
		JFrame frame = new JFrame(); // create a new frame
		GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice(); // get the default screen device
		DisplayMode oldDisplayMode = device.getDisplayMode(); // get the old display mode
		DisplayMode newDisplayMode = new DisplayMode(640, 480, (oldDisplayMode.getBitDepth()), (oldDisplayMode.getRefreshRate())); // create a new display mode
		frame.getContentPane().setLayout(null); // set the layout to null
		frame.getContentPane().add(this, 0, 0); // add the component to the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // set the default close operation to exit on close
		frame.setSize(WIDTH, HEIGHT); // set the size of the frame
		frame.setResizable(false); // set the frame to not be resizable
		frame.setUndecorated(true); // set the frame to be undecorated
		frame.setVisible(true); // set the frame to visible
		if(device.isFullScreenSupported()) { // if the device is supported
			device.setFullScreenWindow(frame); // set the device to the frame
			device.setDisplayMode(newDisplayMode); // set the display mode
		}
		else {
			System.out.println("Not supported to be fullscreen!"); // print out that fullscreen is not supported
		}
		return frame; // return the frame
	}

	/**
		Preforms the standard updates of the component.(Preformed before update() is called)
	*/
	public void standardUpdates(){
		// nothing to do
	}

	/**
		The method that draws the component.
		@param g the graphics to draw on
	*/
	public abstract void draw(Graphics g);

	/**
		Draws the standard parts of the component. (Preformed before draw(Graphics) is called)
		@param g the graphics to draw on
	*/
	public void standardDraw(Graphics g){
		// nothing to do
	}


	/**
		The method that updates the component.
	*/
	public abstract void update();
}

/**
 * This class is for the player, everything for the player is located here.
 * such as health, level and etc
 */
class Player
{
	protected int maxHealth = 100; // the max health of the player
	protected int health = maxHealth; // the current health of the player
	protected int exp = 0; // the current exp of the player
	protected int expToNextLevel = 10; // the amount of exp needed to level up
	protected int level = 1; // the current level of the player

	/**
	 * if the player is hit by an enemy this method will be called and it will decrease the health of the player
	 * @param amount the amount of damage the enemy does
	 */
	public void hit(int amount) {
		health-=amount; // subtract the amount from the health
	}

	/**
	 * heal class will heal the player when it is called
	 */
	public void heal() {
		health = maxHealth;
	}

	/**
	 * this is for when the player is dead and it will return that the player is dead and health is zero.
	 * @return if the player is dead
	 */
	public boolean dead() {
		return health <= 0;
	}

	/**
	 * this method will increase the exp of the player
	 * @param amount the amount of exp to increase
	 */
	public void addExp(int amount)
	{
		exp+=amount; // add the amount of exp to the current exp
		while(exp>expToNextLevel) // while the exp is greater than the exp to next level
			levelUp(); // level up
	}

	/**
	 * this method will level up the player, very basic lol
	 */
	public void levelUp()
	{
		exp-=expToNextLevel; // subtract the exp to next level from the current exp
		expToNextLevel*=2; // double the exp to next level
		maxHealth+=25; // add 25 health to the max health
		level++; // increase the level
	}

	/**
	 * in the draw we will draw how the character looks like and how much health it has, thats basically it.
	 */
	public void draw(Graphics g)
	{
		g.setColor(Color.MAGENTA); // set the color to magenta
		g.fillOval(Util.MAX_R-Util.PLAYER_RADIUS,Util.MAX_R-Util.PLAYER_RADIUS,Util.PLAYER_RADIUS*2,Util.PLAYER_RADIUS*2); // draw the player
		g.setColor(Color.ORANGE); // set the color to orange
		g.fillRect(Util.MAX_R-Util.PLAYER_RADIUS,Util.MAX_R-Util.PLAYER_RADIUS-6,Util.PLAYER_RADIUS*2,5); // draw the health bar
		g.setColor(Color.GREEN); // set the color to green
		g.fillRect(Util.MAX_R-Util.PLAYER_RADIUS,Util.MAX_R-Util.PLAYER_RADIUS-6,Util.PLAYER_RADIUS*2*health/maxHealth,5); // draw the death bar
		g.setColor(Color.YELLOW); // set the color to yellow
		g.fillRect(Util.MAX_R-Util.PLAYER_RADIUS,Util.MAX_R-Util.PLAYER_RADIUS-1,Util.PLAYER_RADIUS*2*exp/expToNextLevel,1); // draw the exp bar
	}
}

/**
 * This class is for the enemy, everything for the enemy is located here.
 * such as speed, death, animation, etc
 * The bullet properties are located here too
 */
abstract class Enemy
{
	protected double r,t,speed; // the radius, theta, and speed of the enemy
	protected Color color = Color.RED; // the color of the enemy
	protected int radius = 7; // the radius of the enemy
	private boolean dying1 = false; // if the enemy is dying
	private boolean dying2 = false; // if the enemy is dying
	private boolean dead = false; // if the enemy is dead
	private int alpha = 255; // the alpha of the enemy
	private int arrowR = Util.PLAYER_RADIUS+Util.ARROW_LENGTH; // the radius of the arrow

	/**
	 * this method will draw the enemy, and sets the speed of the enemy
	 * @param speed the speed of enemies
	 */
	public Enemy(double speed)
	{
		this.speed = speed; // set the speed of the enemy
		r = Util.MAX_R; // set the radius of the enemy
		t = Math.random()*Math.PI*2; // set the theta of the enemy
	}

	/**
	 * The x coordinate of the enemy
	 * @return the x coordinate of the enemy
	 */
	public int x()
	{
		return Util.MAX_R+(int)(Math.cos(t)*r+.5); // return the x coordinate of the enemy 
	}

	/**
	 * The y coordinate of the enemy
	 * @return the y coordinate of the enemy
	 */
	public int y()
	{
		return Util.MAX_R+(int)(Math.sin(t)*r+.5); // return the y coordinate of the enemy
	}


	/**
	 * the x coordinate of the bullet/arrow
	 * @return the x coordinate of the bullet/arrow
	 */
	private int arrowX1()
	{
		return Util.MAX_R+(int)(Math.cos(t)*arrowR+.5); // return the x coordinate of the bullet/arrow
	}

	/**
	 * the y coordinate of the bullet/arrow
	 * @return the y coordinate of the bullet/arrow
	 */
	private int arrowY1()
	{
		return Util.MAX_R+(int)(Math.sin(t)*arrowR+.5); // return the y coordinate of the bullet/arrow
	}

	/**
	 * the x2 coordinate of the bullet/arrow
	 * @return the x2 coordinate of the bullet/arrow
	 */
	private int arrowX2()
	{
		return Util.MAX_R+(int)(Math.cos(t)*(arrowR-Util.ARROW_LENGTH)+.5); // return the x2 coordinate of the bullet/arrow
	}

	/**
	 * the y2 coordinate of the bullet/arrow
	 * @return the y2 coordinate of the bullet/arrow
	 */
	private int arrowY2()
	{
		return Util.MAX_R+(int)(Math.sin(t)*(arrowR-Util.ARROW_LENGTH)+.5); // return the y2 coordinate of the bullet/arrow
	}

	/**
	 * this method will be called if the enemy is dying
	 */
	public void die()
	{
		dying1 = true; // set the enemy to dying
	}

	/**
	 * this method will be called if the enemy is dead
	 * @return if the enemy is dead
	 */
	public boolean dead()
	{
		return dead; // return if the enemy is dead
	}

	/**
	 * this method will be called if the enemy is hitting the player
	 * @return if the enemy is hitting the player
	 */
	public boolean hitting()
	{
		return r == Util.PLAYER_RADIUS+radius && !dying1; // return if the enemy is hitting the player
	}

	public void update()
	{
		if(!dying2) // if the enemy is not dying
		{
			r-=speed; // decrease the radius of the enemy
			if(r<Util.PLAYER_RADIUS+radius) // if the enemy is too close to the player
				r = Util.PLAYER_RADIUS+radius; // set the radius of the enemy to the player radius
		}

		// the animation of the enemy dying
		if(dying2) // if the enemy is dying
		{
			alpha/=1.1; // decrease the alpha of the enemy
			if(alpha==0) // if the alpha is 0
				dead = true; // set the enemy to dead
		}
		else if(dying1) // if the enemy is dying
		{
			arrowR+=Util.ARROW_SPEED; // increase the radius of the arrow
			if(arrowR>=r) // if the arrow is too big
				dying2 = true; // set the enemy to dying
		}
	}

	public abstract String getProblem(); // get the problem of the enemy
	public abstract int getSolution(); // get the solution of the enemy

	public void draw(Graphics g)
	{
		g.setColor(color);
		g.setFont(new Font("Dialog",Font.BOLD,10));
		g.drawString(getProblem(), x()-radius, y()-radius);
		g.fillOval(x()-radius, y()-radius, radius*2, radius*2);
		if(dying1)
		{
			g.setColor(Color.BLACK);
			g.drawLine(arrowX1(),arrowY1(),arrowX2(),arrowY2());
		}
		if(dying2)
		{
			color = new Color(color.getRed(),color.getGreen(),color.getBlue(),alpha);
		}
	}
}

class Level
{
	protected Vector<Enemy> enemies;
	protected Vector<Enemy> actives;
	protected int active;
	public int key;

	public Level(int active, int key, Vector<Enemy> enemies)
	{
		this.active = active;
		this.key = key;
		this.enemies = enemies;
		actives = new Vector();
	}

	public int process(int solution)
	{
		int killed = 0;
		for(int i = 0; i < actives.size(); i++)
		{
			if(actives.get(i).getSolution()==solution)
			{
				actives.get(i).die();
				killed++;
				Util.score++;
			}
		}
		return killed;
	}

	public int getHitting()
	{
		int hitting = 0;
		for(int i = 0; i < actives.size(); i++)
			if(actives.get(i).hitting())
				hitting++;
		return hitting;
	}

	public boolean finished()
	{
		return actives.size()==0&&enemies.size()==0;
	}

	public void update()
	{
		if(actives.size()<active&&enemies.size()>0&&Math.random()<Util.ENEMY_FREQUENCY*active)
		{
			actives.add(enemies.remove((int)(Math.random()*enemies.size())));
		}
		for(int i = 0; i < actives.size(); i++)
		{
			actives.get(i).update();
			if(actives.get(i).dead())
			{
				actives.remove(i);
				i--;
			}
		}
	}

	public void drawEnemies(Graphics g)
	{
		for(int i = 0; i < actives.size(); i++)
		{
			actives.get(i).draw(g);
		}
	}
}

// Calculations of the game
class Addition extends Enemy
{
	protected String problem;
	protected int solution;

	public Addition()
	{
		super(.6);
		int n1 = (int)(Math.random()*9)+1;
		int n2 = (int)(Math.random()*9)+1;
		solution = n1+n2;
		problem = ""+n1+"+"+n2;
	}

	public String getProblem()
	{
		return problem;
	}

	public int getSolution()
	{
		return solution;
	}
}

class Division extends Enemy
{
	protected String problem;
	protected int solution;

	public Division()
	{
		super(.3);
		int n1 = (int)(Math.random()*9)+1;
		int n2 = (int)(Math.random()*9)+1;
		solution = n1;
		problem = ""+(n1*n2)+"/"+n2;
		radius = 8;
		color = java.awt.Color.RED;
	}

	public String getProblem()
	{
		return problem;
	}

	public int getSolution()
	{
		return solution;
	}
}
class Subtraction extends Enemy
{
	protected String problem;
	protected int solution;

	public Subtraction()
	{
		super(.4);
		int n1 = (int)(Math.random()*9)+1;
		int n2 = (int)(Math.random()*9)+1;
		solution = n1;
		problem = ""+(n1+n2)+"-"+n2;
		color = java.awt.Color.RED;
	}

	public String getProblem()
	{
		return problem;
	}

	public int getSolution()
	{
		return solution;
	}
}

class Multiplication extends Enemy
{
	protected String problem;
	protected int solution;

	public Multiplication()
	{
		super(.4);
		int n1 = (int)(Math.random()*9)+1;
		int n2 = (int)(Math.random()*9)+1;
		solution = n1*n2;
		problem = ""+n1+"*"+n2;
		radius = 8;
	}

	public String getProblem()
	{
		return problem;
	}

	public int getSolution()
	{
		return solution;
	}
}
class BigAddition extends Enemy
{
	protected String problem;
	protected int solution;

	public BigAddition()
	{
		super(.3);
		int n1 = (int)(Math.random()*90)+10;
		int n2 = (int)(Math.random()*90)+10;
		solution = n1+n2;
		problem = ""+n1+"+"+n2;
		radius = 15;
	}

	public String getProblem()
	{
		return problem;
	}

	public int getSolution()
	{
		return solution;
	}
}

class BigMultiplication extends Enemy
{
	protected String problem;
	protected int solution;

	public BigMultiplication()
	{
		super(.05);
		int n1 = (int)(Math.random()*90)+10;
		int n2 = (int)(Math.random()*90)+10;
		solution = n1*n2;
		problem = ""+n1+"*"+n2;
		radius = 20;
	}

	public String getProblem()
	{
		return problem;
	}

	public int getSolution()
	{
		return solution;
	}
}