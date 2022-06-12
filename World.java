import java.awt.*;
import java.util.*;
import java.awt.image.*;
import javax.swing.*;
import java.awt.event.*;

class Util
{
	public static final int MAX_R = 250;
	public static final int PLAYER_RADIUS = 15;
	public static final int ARROW_LENGTH = 20;
	public static final double ARROW_SPEED = 2;
	public static final double ENEMY_FREQUENCY = 1;
}

public class World
{
	public Player player;
	public Level[] levels;
	public int level = 0;
	public boolean win = false;
	public boolean lose = false;

	public World(Player player, Level... levels)
	{
		this.player = player;
		this.levels = levels;
	}

	public void tryKey(int key)
	{
		for(int i = 0; i < levels.length; i++)
		{
			if(levels[i].key==key)
			{
				level = i;
				i = levels.length;
			}
		}
	}

	public Level getLevel()
	{
		return levels[level];
	}

	public void update()
	{
		if(!win && !lose)
		{
			getLevel().update();
			player.hit(getLevel().getHitting());
			if(player.dead())
				lose = true;
			if(getLevel().finished()&& !lose)
			{
				player.heal();
				level++;
				if(level==levels.length)
				{
					win = true;
					level--;
				}
			}
		}
	}

	public void draw(Graphics g)
	{
		getLevel().drawEnemies(g);
		player.draw(g);
		g.setColor(Color.BLACK);
		g.setFont(new Font("Dialog",Font.BOLD,14));
		g.drawString("Level: "+(level+1),5,15);
		g.drawString("Enter the Answer: ",5,30);
		// g.drawString("Key: "+getLevel().key,Util.MAX_R*2-90,15);
		if(win)
		{
			g.setColor(Color.GREEN);
			g.setFont(new Font("Dialog",Font.BOLD,14));
			g.drawString("You WIN!",140,150);
		}
		if(lose)
		{
			g.setColor(Color.RED);
			g.setFont(new Font("Dialog",Font.BOLD,14));
			g.drawString("You lose.",220,150);
		}
	}

	public static World getWorld()
	{
		Player player = new Player();

		//Level 1
		Vector<Enemy> l1e = new Vector();
		for(int i = 0; i < 10; i ++) l1e.add(new Addition());
		Level l1 = new Level(2,1234567,l1e);

		//Level 2
		Vector<Enemy> l2e = new Vector();
		for(int i = 0; i < 8; i ++) l2e.add(new Addition());
		for(int i = 0; i < 5; i ++) l2e.add(new Multiplication());
		Level l2 = new Level(2,6394658,l2e);

		//Level 3
		Vector<Enemy> l3e = new Vector();
		for(int i = 0; i < 5; i ++) l3e.add(new Addition());
		for(int i = 0; i < 8; i ++) l3e.add(new Multiplication());
		Level l3 = new Level(3,1563826,l3e);

		//Level 4
		Vector<Enemy> l4e = new Vector();
		for(int i = 0; i < 7; i ++) l4e.add(new Addition());
		for(int i = 0; i < 7; i ++) l4e.add(new Multiplication());
		for(int i = 0; i < 3; i ++) l4e.add(new Subtraction());
		Level l4 = new Level(3,1927462,l4e);

		//Level 5
		Vector<Enemy> l5e = new Vector();
		for(int i = 0; i < 7; i ++) l5e.add(new Addition());
		for(int i = 0; i < 7; i ++) l5e.add(new Multiplication());
		for(int i = 0; i < 5; i ++) l5e.add(new Subtraction());
		for(int i = 0; i < 3; i ++) l5e.add(new Division());
		Level l5 = new Level(3,3728465,l5e);

		//Level 6
		Vector<Enemy> l6e = new Vector();
		for(int i = 0; i < 7; i ++) l6e.add(new Addition());
		for(int i = 0; i < 7; i ++) l6e.add(new Multiplication());
		for(int i = 0; i < 7; i ++) l6e.add(new Subtraction());
		for(int i = 0; i < 5; i ++) l6e.add(new Division());
		Level l6 = new Level(3,7384920,l6e);

		//Level 7
		Vector<Enemy> l7e = new Vector();
		for(int i = 0; i < 5; i ++) l7e.add(new BigAddition());
		for(int i = 0; i < 7; i ++) l7e.add(new Multiplication());
		for(int i = 0; i < 7; i ++) l7e.add(new Subtraction());
		for(int i = 0; i < 7; i ++) l7e.add(new Division());
		Level l7 = new Level(3,6374198,l7e);

		//Level 8
		Vector<Enemy> l8e = new Vector();
		for(int i = 0; i < 5; i ++) l8e.add(new BigAddition());
		for(int i = 0; i < 1; i ++) l8e.add(new BigMultiplication());
		for(int i = 0; i < 7; i ++) l8e.add(new Subtraction());
		for(int i = 0; i < 7; i ++) l8e.add(new Division());
		Level l8 = new Level(3,2538610,l8e);

		//Level 9
		Vector<Enemy> l9e = new Vector();
		for(int i = 0; i < 5; i ++) l9e.add(new BigAddition());
		for(int i = 0; i < 1; i ++) l9e.add(new BigMultiplication());
		for(int i = 0; i < 7; i ++) l9e.add(new Subtraction());
		for(int i = 0; i < 7; i ++) l9e.add(new Division());
		Level l9 = new Level(3,1527943,l9e);
		
		//Level 10
		Vector<Enemy> l10e = new Vector();
		for(int i = 0; i < 5; i ++) l10e.add(new BigAddition());
		for(int i = 0; i < 1; i ++) l10e.add(new BigMultiplication());
		for(int i = 0; i < 7; i ++) l10e.add(new Subtraction());
		for(int i = 0; i < 7; i ++) l10e.add(new Division());
		Level l10 = new Level(3,4268197,l10e);

		//Level 11
		Vector<Enemy> l11e = new Vector();
		for(int i = 0; i < 5; i ++) l11e.add(new BigAddition());
		for(int i = 0; i < 1; i ++) l11e.add(new BigMultiplication());
		for(int i = 0; i < 7; i ++) l11e.add(new Subtraction());
		for(int i = 0; i < 7; i ++) l11e.add(new Division());
		Level l11 = new Level(3,1738295,l11e);

		//Level 12
		Vector<Enemy> l12e = new Vector();
		for(int i = 0; i < 5; i ++) l12e.add(new BigAddition());
		for(int i = 0; i < 1; i ++) l12e.add(new BigMultiplication());
		for(int i = 0; i < 7; i ++) l12e.add(new Subtraction());
		for(int i = 0; i < 7; i ++) l12e.add(new Division());
		Level l12 = new Level(3,0651243,l12e);


		//Level 13
		Vector<Enemy> l13e = new Vector();
		for(int i = 0; i < 5; i ++) l13e.add(new BigAddition());
		for(int i = 0; i < 1; i ++) l13e.add(new BigMultiplication());
		for(int i = 0; i < 7; i ++) l13e.add(new Subtraction());
		for(int i = 0; i < 7; i ++) l13e.add(new Division());
		Level l13 = new Level(3,3628491,l13e);


		//Level 14
		Vector<Enemy> l14e = new Vector();
		for(int i = 0; i < 5; i ++) l14e.add(new BigAddition());
		for(int i = 0; i < 1; i ++) l14e.add(new BigMultiplication());
		for(int i = 0; i < 7; i ++) l14e.add(new Subtraction());
		for(int i = 0; i < 7; i ++) l14e.add(new Division());
		Level l14 = new Level(3,4016275,l14e);


		//Level 15
		Vector<Enemy> l15e = new Vector();
		for(int i = 0; i < 5; i ++) l15e.add(new BigAddition());
		for(int i = 0; i < 1; i ++) l15e.add(new BigMultiplication());
		for(int i = 0; i < 7; i ++) l15e.add(new Subtraction());
		for(int i = 0; i < 7; i ++) l15e.add(new Division());
		Level l15 = new Level(3,6142063,l15e);

		//Level 16
		Vector<Enemy> l16e = new Vector();
		for(int i = 0; i < 5; i ++) l16e.add(new BigAddition());
		for(int i = 0; i < 1; i ++) l16e.add(new BigMultiplication());
		for(int i = 0; i < 7; i ++) l16e.add(new Subtraction());
		for(int i = 0; i < 7; i ++) l16e.add(new Division());
		Level l16 = new Level(3,5390528,l16e);

		return new World(player,l1,l2,l3,l4,l5,l6,l7,l8,l9,l10,l11,l12,l13,l14,l15,l16);
	}
}

abstract class ListeningGameComponent extends GameComponent implements MouseListener, MouseMotionListener, KeyListener
{
	public boolean mousePressed1 = false, mousePressed2 = false, mousePressed3 = false;
	public ArrayList<String> keysPressed = new ArrayList();
	public boolean debug = false;

	/**
		The x location of the mouse.
	*/
	public int mouseX = 0;
	/**
		The y location of the mouse.
	*/
	public int mouseY = 0;

	/**
		Constructs a ListeningGameComponent with a width of w, and a height of h.
	*/
	public ListeningGameComponent(int w, int h)
	{
		super(w,h);
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
	}

	/**
		The method that draws the component.
	*/
	public abstract void draw(Graphics g);
	/**
		The method that updates the component.
	*/
	public abstract void update();

	/**
		Does nothing. Activated when the mouse is pressed and released.
	*/
	public void mouseClicked(MouseEvent e){}

	/**
		Does nothing. Activated when the mouse enters the component.
	*/
	public void mouseEntered(MouseEvent e){}

	/**
		Does nothing. Activated when the mouse exits the component.
	*/
	public void mouseExited(MouseEvent e){}

	/**
		Updates the mouse variables.
	*/
	public void mousePressed(MouseEvent e)
	{
		if(e.getButton() == MouseEvent.BUTTON1)
			mousePressed1 = true;
		if(e.getButton() == MouseEvent.BUTTON2)
			mousePressed2 = true;
		if(e.getButton() == MouseEvent.BUTTON3)
			mousePressed3 = true;
	}


	/**
		Updates the mouse variables.
	*/
	public void mouseReleased(MouseEvent e)
	{
		if(e.getButton() == MouseEvent.BUTTON1)
			mousePressed1 = false;
		if(e.getButton() == MouseEvent.BUTTON2)
			mousePressed2 = false;
		if(e.getButton() == MouseEvent.BUTTON3)
			mousePressed3 = false;
	}


	/**
		Updates the mouse variables.
	*/
	public void mouseDragged(MouseEvent e)
	{
		if(e.getButton() == MouseEvent.BUTTON1)
			mousePressed1 = !mousePressed1;
		if(e.getButton() == MouseEvent.BUTTON2)
			mousePressed2 = !mousePressed2;
		if(e.getButton() == MouseEvent.BUTTON3)
			mousePressed3 = !mousePressed3;
		mouseX = e.getX();
		mouseY = e.getY();
	}


	/**
		Updates the mouse variables.
	*/
	public void mouseMoved(MouseEvent e)
	{
		mousePressed1 = false;
		mousePressed2 = false;
		mousePressed3 = false;

		mouseX = e.getX();
		mouseY = e.getY();
	}


	/**
		Updates the keyboard variables.
	*/
	public void keyPressed(KeyEvent e)
	{
		if(debug)
			System.out.println(KeyEvent.getKeyText(e.getKeyCode()));
		keysPressed.add(KeyEvent.getKeyText(e.getKeyCode()));
	}

	/**
		Updates the keyboard variables.
	*/
	public void keyReleased(KeyEvent e)
	{
		for(int i = 0; i < keysPressed.size(); i++)
		{
			if(keysPressed.get(i).equals(KeyEvent.getKeyText(e.getKeyCode())))
			{
				keysPressed.remove(i);
				i--;
			}
		}
	}

	/**
		Updates the keyboard variables.
	*/
	public void keyTyped(KeyEvent e){}

	/**
		Returns weather a mouse button is pressed.
		return true if the button is pressed
	*/
	public boolean isMousePressed(int b)
	{
		if(b == 1)
			return mousePressed1;
		else if(b == 2)
			return mousePressed2;
		else if(b == 3)
			return mousePressed3;

		return false;
	}

	/**
		Returns weather any mouse button is pressed.
		return true if the button is pressed
	*/
	public boolean isMousePressed()
	{
		if(mousePressed1)
			return mousePressed1;
		else if(mousePressed2)
			return mousePressed2;
		else if(mousePressed3)
			return mousePressed3;

		return false;
	}

	/**
		Returns weather a mouse button is pressed.
		return true if the key is pressed
	*/
	public boolean isKeyPressed(String k)
	{
		for(int i = 0; i < keysPressed.size(); i++)
		{
			if(keysPressed.get(i).equalsIgnoreCase(k))
			{
				return true;
			}
		}
		return false;
	}

	//returns the number of keys pressed.
	public int getKeysPressed()
	{
		return keysPressed.size();
	}

	public void resetKeys()
	{
		keysPressed = new ArrayList();
	}
}


abstract class GameComponent extends JPanel
{
	public static int WIDTH, HEIGHT;
	protected BufferedImage background = null;
	public int delay = 25;

	/**
		Constructs a GameComponent with a width of w, and a height of h.
	*/
	public GameComponent(int w, int h)
	{
		super();
		WIDTH = w;
		HEIGHT = h;
		setSize(WIDTH, HEIGHT);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBackground(Color.WHITE);
		setVisible(true);
	}

	public void start()
	{
		Thread t = new Thread()
		{
			public void run()
			{
				while(true)
				{
					long time = System.currentTimeMillis();
					if(background == null)
					{
						background = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
						background.getGraphics().setColor(Color.WHITE);
						background.getGraphics().fillRect(0,0,WIDTH,HEIGHT);
					}

					requestFocus();

					//update game state
					standardUpdates();
					update();

					//draw stuff
					standardDraw(getCanvas());
					draw(background.getGraphics());
					refreshImage();

					time = System.currentTimeMillis()-time;
					try
					{
						if(delay-(int)time > 0)
							sleep(delay-(int)time);
					}
					catch(Exception ex)
					{
					}
				}
			}
		};
		try{Thread.sleep(500);}catch(Exception ex){}
		t.start();
	}

	//get a blank image to draw onto
	private Graphics getCanvas()
	{
		if(background == null)
		{
			background = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		}
		background.getGraphics().setColor(Color.WHITE);
		background.getGraphics().fillRect(0,0,WIDTH,HEIGHT);
		return background.getGraphics();
	}

	//take the canvas that you have drawn on and draw it onto the component
	private void refreshImage()
	{
		if(background != null)
		{
			if(getGraphics() != null)
			{
				getGraphics().drawImage(background,0,0,null);
			}
		}
	}

	/**
		Creates a JFrame that contains this GameComponent.
	*/
	public JFrame makeTestWindow()
	{
		JFrame frame = new JFrame();
		frame.getContentPane().setLayout(new FlowLayout());
		frame.getContentPane().add(this);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		return frame;
	}

	/**
		Creates a fullscreen JFrame that contains this GameComponent.
		Note that the width and height of the component must be 640x480
		return the JFrame created
	*/
	public JFrame makeFullScreenWindow()
	{
		JFrame frame = new JFrame();
		GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		DisplayMode oldDisplayMode = device.getDisplayMode();
		DisplayMode newDisplayMode = new DisplayMode(640, 480, (oldDisplayMode.getBitDepth()), (oldDisplayMode.getRefreshRate()));
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(this, 0, 0);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH, HEIGHT);
		frame.setResizable(false);
		frame.setUndecorated(true);
		frame.setVisible(true);
		if(device.isFullScreenSupported())
		{
			device.setFullScreenWindow(frame);
			device.setDisplayMode(newDisplayMode);
		}
		else
		{
			System.out.println("ARGS! NO FULLSCRENE!");
		}
		return frame;
	}

	/**
		Preforms the standard updates of the component.(Preformed before update() is called)
	*/
	public void standardUpdates()
	{
	}

	/**
		The method that draws the component.
	*/
	public abstract void draw(Graphics g);

	/**
		Draws the standard parts of the component. (Preformed before draw(Graphics) is called)
	*/
	public void standardDraw(Graphics g)
	{
	}


	/**
		The method that updates the component.
	*/
	public abstract void update();
}

class Player
{
	protected int maxHealth = 100;
	protected int health = maxHealth;
	protected int exp = 0;
	protected int expToNextLevel = 10;
	protected int level = 1;

	public void hit(int amount)
	{
		health-=amount;
	}

	public void heal()
	{
		health = maxHealth;
	}

	public boolean dead()
	{
		return health <= 0;
	}

	public void addExp(int amount)
	{
		exp+=amount;
		while(exp>expToNextLevel)
			levelUp();
	}

	public void levelUp()
	{
		exp-=expToNextLevel;
		expToNextLevel*=2;
		maxHealth+=25;
		level++;
	}

	public void draw(Graphics g)
	{
		g.setColor(Color.MAGENTA);
		g.fillOval(Util.MAX_R-Util.PLAYER_RADIUS,Util.MAX_R-Util.PLAYER_RADIUS,Util.PLAYER_RADIUS*2,Util.PLAYER_RADIUS*2);
		g.setColor(Color.ORANGE);
		g.fillRect(Util.MAX_R-Util.PLAYER_RADIUS,Util.MAX_R-Util.PLAYER_RADIUS-6,Util.PLAYER_RADIUS*2,5);
		g.setColor(Color.GREEN);
		g.fillRect(Util.MAX_R-Util.PLAYER_RADIUS,Util.MAX_R-Util.PLAYER_RADIUS-6,Util.PLAYER_RADIUS*2*health/maxHealth,5);
		g.setColor(Color.YELLOW);
		g.fillRect(Util.MAX_R-Util.PLAYER_RADIUS,Util.MAX_R-Util.PLAYER_RADIUS-1,
			Util.PLAYER_RADIUS*2*exp/expToNextLevel,1);
	}
}

abstract class Enemy
{
	protected double r,t,speed;
	protected Color color = Color.RED;
	protected int radius = 7;
	private boolean dying1 = false;
	private boolean dying2 = false;
	private boolean dead = false;
	private int alpha = 255;
	private int arrowR = Util.PLAYER_RADIUS+Util.ARROW_LENGTH;

	public Enemy(double speed)
	{
		this.speed = speed;
		r = Util.MAX_R;
		t = Math.random()*Math.PI*2;
	}

	public int x()
	{
		return Util.MAX_R+(int)(Math.cos(t)*r+.5);
	}

	public int y()
	{
		return Util.MAX_R+(int)(Math.sin(t)*r+.5);
	}

	private int arrowX1()
	{
		return Util.MAX_R+(int)(Math.cos(t)*arrowR+.5);
	}

	private int arrowY1()
	{
		return Util.MAX_R+(int)(Math.sin(t)*arrowR+.5);
	}

	private int arrowX2()
	{
		return Util.MAX_R+(int)(Math.cos(t)*(arrowR-Util.ARROW_LENGTH)+.5);
	}

	private int arrowY2()
	{
		return Util.MAX_R+(int)(Math.sin(t)*(arrowR-Util.ARROW_LENGTH)+.5);
	}

	public void die()
	{
		dying1 = true;
	}

	public boolean dead()
	{
		return dead;
	}

	public boolean hitting()
	{
		return r == Util.PLAYER_RADIUS+radius && !dying1;
	}

	public void update()
	{
		if(!dying2)
		{
			r-=speed;
			if(r<Util.PLAYER_RADIUS+radius)
				r = Util.PLAYER_RADIUS+radius;
		}

		if(dying2)
		{
			alpha/=1.1;
			if(alpha==0)
				dead = true;
		}
		else if(dying1)
		{
			arrowR+=Util.ARROW_SPEED;
			if(arrowR>=r)
				dying2 = true;
		}
	}

	public abstract String getProblem();
	public abstract int getSolution();

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

