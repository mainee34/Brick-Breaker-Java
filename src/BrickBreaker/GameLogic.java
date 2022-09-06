package BrickBreaker;

import javax.swing.*;
import java.awt.event.KeyListener;
import java.awt.event.ActionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;


public class GameLogic extends JPanel implements KeyListener,ActionListener{
   
    boolean play = false;
    private int score = 0;
    private int totalBricks = 21;
    private Timer timer;
    private int delay = 8;
    
    private int playerX = 310;
    private int ballpositionX = 120;
    private int ballpositionY = 350;
    private int ballXdir = -1;
    private int ballYdir = -2;
    
    private MapGenerator map;
    
    GameLogic()
    {
        map = new MapGenerator(3,7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        
        timer = new Timer(delay,this);
        timer.start();
    } 
       
        
    
    public void paint(Graphics g)
    {
        //background
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);
        
       //drawing map
        map.draw((Graphics2D)g);
        
       
        
        //scores
        g.setColor(Color.white);
        g.setFont(new Font("Consoles",Font.BOLD,30));
        g.drawString(""+score,590,30);
        
        
        //paddle
        g.setColor(Color.blue);
        g.fillRect(playerX, 550, 100, 8);
        
        //ball
        g.setColor(Color.yellow);
        g.fillOval(ballpositionX, ballpositionY, 20, 20);
        
        if(totalBricks<=0)
        {
            play=false;
            ballXdir=0;
            ballYdir=0;
            
            g.setColor(Color.red);
            g.setFont(new Font("Consoles",Font.BOLD,30));
            g.drawString("You Won!!",190,300);
            g.setColor(Color.red);
            g.setFont(new Font("serif",Font.BOLD,20));
            g.drawString("Press Enter to Restart",230,350);
        }
        if(ballpositionY>570)
        {
            play=false;
            ballXdir=0;
            ballYdir=0;
            g.setColor(Color.red);
            g.setFont(new Font("Consoles",Font.BOLD,60));
            g.drawString("GAME OVER ",190,300);
            g.setColor(Color.red);
            g.setFont(new Font("serif",Font.BOLD,20));
            g.drawString("Press Enter to Restart",250,350);
        }
        g.dispose();
        
        
    }
    
    
    
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
     timer.start();
     
     
     if(play)
     {    
         if(new Rectangle(ballpositionX,ballpositionY,20,20).intersects(new Rectangle(playerX,550,100,8)))
         {
             ballYdir = -ballYdir;
         }
				
         //check collison with brick rectangles
         A: for(int i=0;i<map.map.length;i++)
         {
             for(int j =0;j<map.map[0].length;j++)
             {
                 if(map.map[i][j]>0)
                 {
                     int brickX= j*map.brickwidth+80;
                     int brickY= i*map.brickheight+50;
                     int brickwidth= map.brickwidth;
                     int brickheight=map.brickheight;
                     
                     Rectangle rect = new Rectangle(brickX,brickY,brickwidth,brickheight);
                     Rectangle ballRect = new Rectangle(ballpositionX,ballpositionY,20,20);
                     Rectangle brickRect= rect;
                    
                     if(ballRect.intersects(brickRect))
                     {
                         map.setBrickValue(0, i, j);
                         score+=5;
                         totalBricks--;
                         
              
                         
                    
                        //ball  hits right or left
                         if(ballpositionX+19<=brickRect.x || ballpositionX+1>=(brickRect.x+brickRect.width))
                         {
                             ballXdir = -ballXdir;
                         }
                         else //when it hits top /bottom
                         {
                             
                             ballYdir = -ballYdir;
                         }
                         break A;
                     }
                 }
             }
         }
         ballpositionX+=ballXdir;
         ballpositionY+=ballYdir;
         
         //left
         if(ballpositionX<0)
         {
            ballXdir= -ballXdir ;
         }
        
         //top
         if(ballpositionY<0)
         {
            ballYdir= -ballYdir ;
         }
         
         //right
         if(ballpositionX>670)
         {
            ballXdir= -ballXdir ;
         }
     
     repaint();
    }
    }
    
   @Override
     public void keyTyped(KeyEvent e)
     {
         
     }
     @Override
     public void keyReleased(KeyEvent e)
     {
         
     }
    @Override
     public void keyPressed(KeyEvent e)
    {
        if(e.getKeyCode()==KeyEvent.VK_RIGHT)
        {
            if(playerX>=600)
            {
                playerX=600;
            }
            
            else
            {
                moveRight();
            }
        }
        
        if(e.getKeyCode()==KeyEvent.VK_LEFT)
        {
          
            if(playerX<10)
            {
                playerX=10;
            }
            
            else
            {
                moveLeft();
            }
        }
        
         if(e.getKeyCode()==KeyEvent.VK_ENTER)
         {
             if(!play)
             {
                 play = true;
                 ballpositionX = 120;
                 ballpositionY= 350;
                 ballXdir = -1;
                 ballYdir = -2;
                 playerX=310;
                 score=0;
                 totalBricks=21;
                 map = new MapGenerator(3,7);
                 
                 repaint();
                 
                 
                 
             }
         }
        }
    
     
     public void moveRight()
     {
         play= true;
         playerX += 20;
     }
    
      public void moveLeft()
     {
         play= true;
         playerX -= 20;
     }

    
}
