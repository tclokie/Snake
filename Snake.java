import java.awt.*;

public class Snake
{
	private static final byte ROWS = 20;
	private static final byte COLUMNS = 20;
	private static final byte SIZE = 30;

	private static int[][] board = new int[COLUMNS][ROWS]; // top, left is 0
//	private static Color[] colours = {Color.BLACK, Color.RED, Color.GREEN, Color.BLUE,
//									Color.CYAN, Color.MAGENTA, Color.YELLOW, new Color (255,127,0)};

	static byte posX, posY;
	static int length;
	static byte direction; // 0 = right, 1 = up, 2 = left, 3 = down
	boolean pause, gameOver;

	public Snake()
	{
		posX = COLUMNS/2;
		posY = ROWS/2;
		direction = 0;
		
		for (byte i = 0; i < ROWS; i++)
			for (byte j = 0; j < COLUMNS; j++)
				board[i][j] = 0;
				
		length = 1;
		board[posX][posY] = length;
		pause = gameOver = false;
		
		generate();
	}

	public void update()
	{
		if (length >= ROWS*COLUMNS) gameOver = true;
		
		if (gameOver) return;
		
		switch (direction)
		{
			case 0: // right
				if (posX >= COLUMNS-1 || board[posX+1][posY] > 1) gameOver = true;
				else {
					posX++;
					move();
				}
				break;
			case 1: // up
				if (posY <= 0 || board[posX][posY-1] > 1) gameOver = true;
				else{
					posY--;
					move();
				}
				break;
			case 2: // left
				if (posX <= 0 || board[posX-1][posY] > 1) gameOver = true;
				else {
					posX--;
					move();
				}
				break;
			case 3: // down
				if (posY >= ROWS-1 || board[posX][posY+1] > 1) gameOver = true;
				else{
					posY++;
					move();
				}
				break;
			default:
				System.err.println("Direction Error");
				break;
		}
	}
	
	private void move()
	{
		if (board[posX][posY] == -1) {	// eat
			length++;
			board[posX][posY] = length;
			generate();
		}
		else // move
		{
			for (byte i = 0; i < COLUMNS; i++){
				for (byte j = 0; j < ROWS; j++){
					if (board[i][j] > 0) board[i][j]--;
				}
			}
			board[posX][posY] = length;
		}
	}
	
	private void generate()
	{
		byte x,y;
		do{
			x = (byte)(Math.random()*COLUMNS);
			y = (byte)(Math.random()*ROWS);
		}while(board[x][y] != 0);
		
		board[x][y] = -1;
	}

	public void display (Graphics g)
	{
		// display board
		for (byte i = 0; i < ROWS; i++)
		{
			for (byte j = 0; j < COLUMNS; j++)
			{
				if (board[j][i] == -1) g.setColor(Color.RED);
				else if (board[j][i] == 0) g.setColor(Color.BLACK);
				else g.setColor(new Color(184-(int)(2*Math.sqrt(length-board[j][i])),200-(int)(2*Math.sqrt(length-board[j][i])),184-(int)(2*Math.sqrt(length-board[j][i]))));
				g.fillRect(SIZE*j, SIZE*i, SIZE, SIZE);
				//g.drawString(""+(board[j][i]), SIZE*j, SIZE*i);
			}
		}

		// grid lines
		g.setColor(Color.GRAY);
		for (byte i = 0; i <= COLUMNS; i++) g.drawLine(SIZE*i, 0, SIZE*i, ROWS*SIZE); // vertical
		for (byte i = 0; i <= ROWS; i++) g.drawLine(0, SIZE*i, COLUMNS*SIZE, SIZE*i); // horizontal

/*		g.setColor(Color.RED);
		g.drawLine(0, 0, 0, 20*SIZE); // Left
		g.drawLine(10*SIZE, 0, 10*SIZE, 20*SIZE); // Right
		g.drawLine(0, SIZE*20, 10*SIZE, SIZE*20); // Bottom
*/
		// Score
		g.setColor(Color.BLACK);
		g.drawString(("Score: "+10*(length-1)), 615, 300);

		if (gameOver)
		{
			g.fillRect(290,265,77,20);
			g.setColor(Color.RED);
			g.drawRect(290,265,77,20);
			g.drawString(("GAME OVER"), 293, 280);
		}
		else if (pause)
		{
			g.setColor(Color.WHITE);
			g.fillRect(300,265,60,20);
			g.setColor(Color.BLUE);
			g.drawString(("PAUSE"), 309, 280);
		}
	}

	public static void main(String[] args)
	{
		Program prog = new Program();
	}
}
