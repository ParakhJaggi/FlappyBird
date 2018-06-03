package flappybird;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

public class flappyBird implements ActionListener, MouseListener
{
	public final int WIDTH = 1200, HEIGHT = 700;
	public static flappyBird flappyBird;
	public Renderer renderer;
	public Rectangle bird;
	public int ticks, yMotion, score;
	public boolean gameOver, started;
	public ArrayList<Rectangle> columns;
	public Random rand;
	public int highScore =0;
	
    public flappyBird()
    {
    	JFrame jFrame = new JFrame();
    	Timer timer = new Timer(20,this);
    	
    	renderer = new Renderer();
    	rand = new Random();
    	
    	jFrame.add(renderer);
    	jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	jFrame.setSize(WIDTH, HEIGHT);
    	jFrame.addMouseListener(this);
    	jFrame.setResizable(false);
    	jFrame.setTitle("Flappy Bird");
    	jFrame.setVisible(true);
    	bird = new Rectangle(WIDTH/2 -10, HEIGHT/2 -10, 20, 20);
    	columns = new ArrayList<Rectangle>();
    	addColumn(true);
    	addColumn(true);
    	addColumn(true);
    	addColumn(true);
    	
    	
    	timer.start();
    	
    }
    
    public void addColumn(boolean start )
    {
    	int space = 300;
    	int width = 100;
    	int height = 50 + rand.nextInt(300);
    	if(start)
    	{	
    		columns.add(new Rectangle(WIDTH + width + columns.size() *300 , HEIGHT - height - 120, width, height));
    		columns.add(new Rectangle(WIDTH + width + (columns.size() - 1) *300, 0 , width, HEIGHT - height - space));
    	}
    	else
    	{
    		columns.add(new Rectangle(columns.get(columns.size()-1).x + 600 , HEIGHT - height - 120, width, height));
    		columns.add(new Rectangle(columns.get(columns.size()-1).x, 0 , width, HEIGHT - height - space));	
    	}
    }
    	
    
    public void paintColumn (Graphics g, Rectangle column)
    {
    	g.setColor(Color.green.darker());
    	g.fillRect(column.x, column.y, column.width, column.height);
    }
    
    public void jump()
    {
    	if(gameOver)
    	{
    	bird = new Rectangle(WIDTH/2 -10, HEIGHT/2 -10, 20, 20);
    	columns.clear();
    	yMotion=0;
    	score =0;
    	addColumn(true);
    	addColumn(true);
    	addColumn(true);
    	addColumn(true);
    	
    	gameOver = false; 
    	}
    	if(!started)
    	{
    		started = true;
    	}
    	else if (!gameOver)
    	{
    		if(yMotion > 0)
    		{
    			yMotion = 0;
    		}
    		yMotion -= 10;
    	}
    }
    
    @Override
	public void actionPerformed(ActionEvent e) 
	{
    	ticks++;
    	int speed =10;
    if(started)
    {
    		
    	
    	for( int i = 0; i< columns.size(); i++)
    	{
    		Rectangle column = columns.get(i);
    		column.x -= speed;
    	}
    	
		
		if (ticks % 2 == 0 && yMotion < 15)	
		{
			yMotion+=2;
		}
		
		for( int i = 0; i< columns.size(); i++)
    	{
    		Rectangle column = columns.get(i);
    		if(column.x + column.width < 0)
    		{
    			columns.remove(column);
    			if(column.y == 0)
    			{
    				addColumn(false);
    			}
    		}
    	}
		
		bird.y+=yMotion;
		for ( Rectangle column: columns)
		{
			
			if( column.y == 0 && bird.x + bird.width / 2 > column.x + column.width / 2 -10 && bird.x + bird.width / 2 < column.x + column.width / 2 + 10)
			{
				score++;
			}
			if( score >= highScore)
			{
				highScore = score; 
			}
			if(column.intersects(bird))
			{
				gameOver =true;
				bird.x = column.x - bird.width;
			}
		}
		if(bird.y > HEIGHT - 120 || bird.y < 0)
		{
			gameOver =true;
		}
		if(bird.y + yMotion >= HEIGHT - 120)
			
		{
			bird.y = HEIGHT - 120 - bird.height;
		}
			
		
		renderer.repaint();
	}
	}
    
    
    public void repaint(Graphics g) 
	{
    	g.setColor(Color.cyan);
    	g.fillRect(0, 0, WIDTH, HEIGHT);
    	
    	g.setColor(Color.orange);
    	g.fillRect(0, HEIGHT-120, WIDTH, 120);
    	
    	g.setColor(Color.green);
    	g.fillRect(0, HEIGHT-120, WIDTH, 20);
    	g.setColor(Color.red);
    	g.fillRect(bird.x, bird.y, bird.width, bird.height);
		
    	for( Rectangle column : columns)
    	{
    		paintColumn(g, column);
    	}
		
    	g.setColor(Color.white);
    	g.setFont(new Font ("Ariel", 1, 100));
    	if(!started)
    	{
    		g.drawString("CLICK TO START!" , 100, HEIGHT / 2 - 50);
    	}
    	if(gameOver)
    	{
    		g.drawString("GAME OVER!" , 100, HEIGHT / 2 - 50);
    	}
    	if(!gameOver || started)
    	{
    		g.drawString(String.valueOf(score), WIDTH/2 -25, 100);
    	}
    	g.setFont(new Font ("Ariel", 1, 50));
    	g.drawString("Made by Parakh Jaggi", 500, 600);
    	g.drawString("High Score: " + String.valueOf(highScore), 50, 50);
	}
	public static void main (String[] args)
		{
		flappyBird = new flappyBird();
 		}

	@Override
	public void mouseClicked(MouseEvent arg0)
	{
		jump();
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}
	
}
