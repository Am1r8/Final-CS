import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
public class Game extends ListeningGameComponent
{
	protected World world;
	protected String typed;

	public Game()
	{
		super(Util.MAX_R*2,Util.MAX_R*2);
		world = World.getWorld();
		typed = "";
		start();
	}

	public void update()
	{
		world.update();
	}

	public void draw(Graphics g)
	{
		world.draw(g);
		g.setColor(Color.BLACK);
		g.drawString(typed,5,45);
	}

	public void keyTyped(KeyEvent ke)
	{
		if(ke.getKeyChar()=='\n'&&typed.length()>0)
		{
			if(typed.length()==7)
				world.tryKey(Integer.parseInt(typed));
			else
				world.player.addExp(world.getLevel().process(Integer.parseInt(typed)));
			typed = "";
		}
		if(Character.isDigit(ke.getKeyChar()))
			typed=typed+ke.getKeyChar();
	}

	public static void playmusic(String path){
        try {
            File file = new File(path);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            while (true) {
                clip.start();
                Thread.sleep(clip.getMicrosecondLength() / 1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public static void main(String[] args)
	{
		new Game().makeTestWindow();
		playmusic("1.wav");
	}
}
