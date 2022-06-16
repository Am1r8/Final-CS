/**
Amirhosein Soleimanian
Grade 11 Computer Science
Mr.Benum
*/

//Import the necessary packages
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * This class is the main class of the game.
 * It contains the main method that starts the game.
 * In here we can start the game and it brings all the classes and components together.
 * @author Amirhosein
 */
public class Game extends ListeningGameComponent
{
	protected World world; // The world that the game is in
	protected String typed; // The string that the user has typed

	public Game()
	{
		super(Util.MAX_R*2,Util.MAX_R*2); // Set the size of the game
		world = World.getWorld(); // Get the world
		typed = ""; // Set the typed string to empty
		start(); // Start the game
	}

	/**
	 * Updates the whole world of the game.
	 */
	public void update()
	{
		world.update(); // Update the world
	}

	/**
	 * Draws the game and some other components like the typed numbers.
	 */
	public void draw(Graphics g)
	{
		world.draw(g); // Draw the world
		g.setColor(Color.BLACK); // Set the color to black
		g.drawString(typed,5,45); // Draw the typed string
	}

	/**
	 * This method is called when the user presses a key.
	 * @param ke The key that the user pressed.
	 */
	public void keyTyped(KeyEvent ke)
	{
		if(ke.getKeyChar()=='\n'&&typed.length()>0) // if the user pressed enter and the typed string is not empty
		{
			if(typed.length()==7) // if the typed string is 7 characters long
				world.tryKey(Integer.parseInt(typed)); // try to guess the key
			else
				world.player.addExp(world.getLevel().process(Integer.parseInt(typed))); // add the experience to the player
			typed = ""; // set the typed string to empty
		}
		if(Character.isDigit(ke.getKeyChar())) // if the key is a digit
			typed=typed+ke.getKeyChar(); // add the digit to the typed string
	}

	/**
	 * with this method we will play the music in the background.
	 */
	public static void playmusic(String path){
        try { // try to play the music
            File file = new File(path); // the path of music
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(file); // get the music
            Clip clip = AudioSystem.getClip(); // get the clip
            clip.open(audioIn); // open the music
            while (true) { // play the music forever
                clip.start(); // start the music
                Thread.sleep(clip.getMicrosecondLength() / 1000); // wait for the music to finish
            }
        } catch (Exception e) { // if the music is not found
            e.printStackTrace(); // print the error
        }
    }

	/**
	 * START THE GAME HERE.
	 */
	public static void main(String[] args)
	{
		new Game().makeTestWindow(); // make the window
		playmusic("1.wav"); // play the music
	}
}