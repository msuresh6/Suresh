import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class PlayGame extends JPanel implements ActionListener, KeyListener{
	
	private boolean play = false;
	private int score = 0;
	private int totalBricks = 7;
	private int brickCount = 1;
	private Timer timer;
	private int delay = 8;
	
	private int playerX = 220;
	
	private int ballPosX = 300;
	private int ballPosY = 528;
	private int ballXDir = -1;
	private int ballYDir = -2;
	
	private SetMap game;
	
	public PlayGame() {
		game = new SetMap(1,7);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
	}
	
	public void paint(Graphics g) {
		//background
		g.setColor(Color.black);
		g.fillRect(1, 1, 582, 592);
		
		//draw map
		game.draw((Graphics2D) g);
		
		//border
		g.setColor(Color.yellow);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 582, 3);
		g.fillRect(581, 0, 3, 592);
		
		//the paddle 
		g.setColor(Color.green);
		g.fillRect(playerX, 550, 150, 6);
		
		//ball
		g.setColor(Color.cyan);
		g.fillOval(ballPosX, ballPosY, 20, 20);
		
		if (totalBricks <= 0) {
//			play = false;
//			ballXDir = 0;
//			ballYDir = 0;
//			g.setColor(Color.RED);
//			g.setFont(new Font("serif", Font.BOLD, 30));
//			g.drawString("You WON", 190, 300);
//			
//			g.setFont(new Font("serif", Font.BOLD, 20));
//			g.drawString("Press Enter to Restart", 180, 350);
			
			play = true;
			brickCount++;
			totalBricks = brickCount * 7;;
			playerX = 220;
			ballPosX = 300;
			ballPosY = 528;
			ballXDir = -1;
			ballYDir = -2;
			game = new SetMap(brickCount,7);
			repaint();
		}
		
		if (brickCount > 10) {
			play = false;
			ballXDir = 0;
			ballYDir = 0;
			g.setColor(Color.magenta);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("You WON", 190, 300);
			
			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("Press Enter to Restart", 180, 350);
		}
		
		if(ballPosY > 560) {
			play = false;
			g.setColor(Color.RED);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("Game Over, Score: "+ score, 150, 300);

			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("Press Enter to Restart", 190, 350);
		}
		
		g.dispose();
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
				
		if(play) {
			
			if(new Rectangle(ballPosX, ballPosY, 20,20).intersects(new Rectangle(playerX, 550, 150, 6))) {
				ballYDir = -ballYDir;
			}
			A: for (int i = 0; i < game.map.length; i++) {
				for (int j = 0; j < game.map[0].length; j++) {
					if (game.map[i][j] > 0) {
						int BrickX = j * game.brickWidth + 80;
						int BrickY = i * game.brickHeight +50;
						int BrickWidth = game.brickWidth;
						int BrickHeight = game.brickHeight;
						
						Rectangle rect = new Rectangle(BrickX,BrickY,BrickWidth,BrickHeight);
						Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20, 20);
						Rectangle brickRect = rect;
						
						if(ballRect.intersects(brickRect)) {
							game.setBrickValue(0, i, j);
							totalBricks--;
							score++;
							
							if (ballPosX + 19 < ballRect.x || ballPosX + 1 >= brickRect.x + brickRect.width) {
								ballXDir = -ballXDir;
							} else {
								ballYDir = -ballYDir;
							}
							break A;
						}
					}
				}
			}
			ballPosX += ballXDir;
			ballPosY += ballYDir;
			if(ballPosX == 0) {
				ballXDir = -ballXDir;
			}
			if(ballPosY == 0) {
				ballYDir = -ballYDir;
			}
			if(ballPosX > 560) {
				ballXDir = -ballXDir;
			}
		}
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (playerX >= 425) {
				playerX = 425;
			}else {
				play = true;
				playerX += 20;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (playerX <= 10) {
				playerX = 10;
			}else {
				play = true;
				playerX -= 20;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			play = false;
			score = 0;
			totalBricks = 7;
			brickCount = 1;
			playerX = 220;
			ballPosX = 300;
			ballPosY = 528;
			ballXDir = -1;
			ballYDir = -2;
			game = new SetMap(1,7);
			repaint();
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {}
	
	@Override
	public void keyTyped(KeyEvent e) {}
}
