import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

public class GamePanel extends Panel implements KeyListener
{
	private Dimension dim = null;

	Snake game = null;

	BufferedImage osi;
	Graphics osg;

	static final int delay = 10;
	static final int period = 30;
	
	static int count = 0;
	static final int countMax = 10;

	Timer tm = new Timer ();
	TimerTask task = new TimerTask ()
	{
		public void run()
		{
			if (dim != null && !game.pause)
			{
				if (count >= countMax)
				{
					game.update();
					repaint();
					count = 1;
				}
				else count++;
			}
		}
	};

	public GamePanel ()
	{
		addKeyListener(this);
		game = new Snake();
		tm.scheduleAtFixedRate(task, delay, period);
	}

	public void paint (Graphics g)
	{
		dim = getSize();
		osi = new BufferedImage(dim.width, dim.height, BufferedImage.TYPE_INT_RGB);
		osg = osi.getGraphics();
		osg.setFont(new Font("Courier", Font.BOLD, 14));
		update(g);
	}

	public void update (Graphics g)
	{
		// clear previous frame;
		osg.setColor(Color.LIGHT_GRAY);
		osg.fillRect(0, 0, dim.width, dim.height);

		// do everything in osg
		game.display(osg);

		// then output:
		g.drawImage(osi, 0, 0, this);
	}

	public void keyPressed(KeyEvent ke)
	{
		switch (ke.getKeyCode())
		{
			case KeyEvent.VK_UP:
						if (!game.pause && !game.gameOver && game.direction != 3){
							game.direction = 1;
							count = countMax;
						}
						break;
			case KeyEvent.VK_DOWN:	if (!game.pause && !game.gameOver && game.direction != 1){
							game.direction = 3;
							count = countMax;
						}
						break;
			case KeyEvent.VK_LEFT:	if (!game.pause && !game.gameOver && game.direction != 0){
							game.direction = 2;
							count = countMax;
						}
						break;
			case KeyEvent.VK_RIGHT:	if (!game.pause && !game.gameOver && game.direction != 2){
							game.direction = 0;
							count = countMax;
						}
						break;
			case KeyEvent.VK_SPACE: if (!game.pause && !game.gameOver) count = countMax;
						break;
			case KeyEvent.VK_ESCAPE:	System.exit(0);
							break;
			case KeyEvent.VK_R:	if (game.pause || game.gameOver) game = new Snake();
								break;
			case KeyEvent.VK_P:	game.pause = !game.pause;
								break;
		}

		repaint();
	}

	public void keyReleased(KeyEvent ke){}
	public void keyTyped(KeyEvent ke){}

	public static void main(String[] args)
	{
		Program prog = new Program();
	}
}
