
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
 
public class SnakePlayer  extends JFrame implements Runnable, KeyListener{
	Random rand = new Random();
	private int runGame=0;
	
	//UI
	private UIGameStart uiGameStart=null;
	
	private int inputSleep=20;
 
	private int drawMapX,drawMapY;
	
	private static BufferedImage bfMap= new BufferedImage(Tools.MAP_X, Tools.MAP_Y, BufferedImage.TYPE_3BYTE_BGR); 
	
    private static BufferedImage bfGameMap = new BufferedImage(Tools.SCREEN_X, Tools.SCREEN_Y, BufferedImage.TYPE_3BYTE_BGR); 
    private static Graphics bfGameMapGraphics =bfGameMap.getGraphics();
    private BufferedImage imgDisplayInformation=null;
    private BufferedImage imeGameOver=null;
    
    private Foods []foods=null;
    
    private Snake snake=null;
    //private Snake enemy=null;
 
	public SnakePlayer() {
		
		this.setTitle(Tools.GameName);
		
		this.setSize(Tools.SCREEN_X, Tools.SCREEN_Y);
		
		this.setResizable(false);
		
		this.setLocationRelativeTo(null);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ImageIcon snakeIcon = new ImageIcon("images\\snakeIcon.png");
		this.setIconImage(snakeIcon.getImage());
		
		this.setVisible(true); 
	 
	    this.addKeyListener(this);
	  
		this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	   
	    uiGameStart=new UIGameStart();
	    
	    
	    loadGameResources();
	    bfMapDraw();
	    setFoods();
	    setSnake();
	    
	     Thread thread = new Thread(this); 
	     thread.start(); 
	}
	
	
	private void loadGameResources() {
	    try {
			imgDisplayInformation = ImageIO.read(new FileInputStream("images\\DisplayInformation.png"));
			imeGameOver = ImageIO.read(new FileInputStream("images\\GameOver.png"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
 
	
	private void setSnake() {
		snake=new Snake(Tools.MAP_X/2, Tools.MAP_Y/2, 0,rand.nextInt(Tools.SNAKE_STYLE_SUM));
		
//		int sx=rand.nextInt(Tools.MAP_X-40)+20;
//		int sy=rand.nextInt(Tools.MAP_Y-40)+20;
//		int sd=rand.nextInt(360);
//		int ss=rand.nextInt(Tools.SNAKE_STYLE_SUM);
//
//		enemy=new Snake(sx, sy, sd, ss);
	}
 
	
	private void setFoods() {
 
		foods=new Foods[Tools.FOODS_SUM];
		for(int i=0;i<Tools.FOODS_SUM;i++){
 
				int imgID=rand.nextInt(Tools.FOODS_TYPE_SUM);
				int drawX=rand.nextInt(Tools.MAP_X-40)+20;
				int drawY=rand.nextInt(Tools.MAP_Y-40)+20;
				
				int generateEnergy;
				if (imgID<7){
					generateEnergy=1;
				}else {
					generateEnergy=5;
				}
				foods[i]=new Foods(imgID, drawX, drawY, generateEnergy);
		}
 
	}
	
	private void bfMapDraw() {
		Graphics bfMapGraphics =bfMap.getGraphics();
		
		bfMapGraphics.setColor(new Color(90, 90, 90)); 
		bfMapGraphics.fillRect(0, 0, Tools.MAP_X, Tools.MAP_Y);
 
		/*bfMapGraphics.setColor(new Color(90, 90, 90)); Color.RED
		bfMapGraphics.fillRect(10, 10, Tools.MAP_X - 20, Tools.MAP_Y - 20);*/
 
		bfMapGraphics.setColor(new Color(208, 208, 224)); 
		bfMapGraphics.fillRect(20, 20, Tools.MAP_X - 40, Tools.MAP_Y - 40);
		
		/*bfMapGraphics.setColor(Color.BLACKnew Color(241, 241, 240));
		for (int i = 20; i < Tools.MAP_Y; i += 20) {
			bfMapGraphics.drawLine(0 + 12, i, Tools.MAP_X -12, i);
		}*/
 
		/*for (int j = 20; j < Tools.MAP_X; j += 20) {
			bfMapGraphics.drawLine(j, 0 + 12, j, Tools.MAP_Y - 12);
		}*/
	}
	
	public void reDrawGame(){
		
		
		drawMapX=-snake.getSnakeHeadX()+Tools.SCREEN_X/2;
		drawMapY=-snake.getSnakeHeadY()+Tools.SCREEN_Y/2;
		
		
		bfGameMapGraphics.setColor(Color.BLACK);
		bfGameMapGraphics.fillRect(0, 0, Tools.SCREEN_X, Tools.SCREEN_Y);
		
		bfGameMapGraphics.drawImage(bfMap, drawMapX, drawMapY, null);
		
		for(int i=0;i<Tools.FOODS_SUM;i++){
 
			int distance = (int) Math.sqrt(Math.abs((foods[i].getDrawX() -  snake.getSnakeHeadX())*(foods[i].getDrawX() -  snake.getSnakeHeadX()))+Math.abs((foods[i].getDrawY() -  snake.getSnakeHeadY())*(foods[i].getDrawY() -  snake.getSnakeHeadY())));
			
			if (distance<30){
				
				snake.setEnergy(snake.getEnergy()+foods[i].getGenerateEnergy());
				
				
 
				foods[i].setDrawX(rand.nextInt(Tools.MAP_X-80)+20);
				foods[i].setDrawY(rand.nextInt(Tools.MAP_Y-80)+20);
				
				
			}
			foods[i].drawFoods(bfGameMapGraphics, drawMapX,drawMapY);
 
	}
		//===============================================
		if (snake.getSnakIsDeath()){
			snake.snakeMove();
			
			
			if(snake.getSnakeHeadX()<=40||snake.getSnakeHeadX()>=(Tools.MAP_X-40)||snake.getSnakeHeadY()<=40||snake.getSnakeHeadY()>=(Tools.MAP_Y-40)){
				//snake.snakeDeath();
				runGame=2;
			}
		}
 
		snake.snakeShow(bfGameMapGraphics,drawMapX,drawMapY);
 
	}
	
 
 
	public void paint(Graphics gr) {
		switch (runGame){
		case 0:
			uiGameStart.drawUIGameStart(bfGameMapGraphics);
			gr.drawImage(bfGameMap,0,0, null); 
			break;
		case 1:
			reDrawGame();
			
			showDisplayInformation();
			gr.drawImage(bfGameMap,0,0, null);
			
            break;
		case 2:
			
			showDisplayInformation();
			bfGameMapGraphics.drawImage(imeGameOver,0,0, null);
			
			gr.drawImage(bfG
					ameMap,0,0, null);
			break;

		default:
			break;
			
		}
 
 }  
 
 
		
		private void showDisplayInformation() {
			bfGameMapGraphics.drawImage(imgDisplayInformation,300,30, null); 
			bfGameMapGraphics.setColor(Color.BLACK);
			bfGameMapGraphics.setFont(new Font("arial",Font.BOLD, 30));
			bfGameMapGraphics.drawString(snake.getSnakeLength()+"", 460, 66);
			//bfGameMapGraphics.drawString(snake.getEnergy()+"", 680, 66);
	}
 
		
	    public void run() {  
	        try {  
	            while (true) {  
	                this.repaint();  
	                Thread.sleep(inputSleep);  
	            }  
	        } catch (InterruptedException e) {  
	            e.printStackTrace();  
	        }  
	    }  
	  
	   
	    public void keyPressed(KeyEvent e) {  
	    	
	        int keyCode = e.getKeyCode();
	        
	        
	        if (runGame==0){
	    		runGame=1;
	    	}
	        
	        if (keyCode ==KeyEvent.VK_LEFT&&runGame==1) { 
	        	snake.setSnakeHeadDirection(snake.getSnakeHeadDirection()-5);
	        }  
	        if (keyCode == KeyEvent.VK_RIGHT&&runGame==1) {
	        	snake.setSnakeHeadDirection(snake.getSnakeHeadDirection()+5);
	        }  
	       /*if(keyCode==KeyEvent.VK_UP&&runGame==1){
	    	   inputSleep=20;
	       }*/
	       if(keyCode==KeyEvent.VK_L&&runGame==2){
 
	    	   snake.setSnakeHeadX(Tools.MAP_X/2);
	    	   snake.setSnakeHeadY(Tools.MAP_Y/2);
	    	   runGame=1;
	    	   snake.setSnakeIsDeath(true);
	       }
	       if(keyCode==KeyEvent.VK_Y&&runGame==2){
	    	   snake.setSnakeHeadX(Tools.MAP_X/2);
	    	   snake.setSnakeHeadY(Tools.MAP_Y/2);
	    	   snake.setSnakeLength(3);
	    	   snake.setEnergy(0);
	    	   runGame=1;
	    	   snake.setSnakeIsDeath(true);
	       }
	    }  
	  
	   
	    public void keyReleased(KeyEvent e) { 
	    	 int keyCode = e.getKeyCode(); 
	    	 /*if(keyCode==KeyEvent.VK_UP){
		    	   inputSleep=80;
		       }*/
	    }  
	  
	    
	    public void keyTyped(KeyEvent e) {  
	  
	    }  
	  
	    public static void main(String[] args) {  
	        new SnakePlayer();  
	    }
 
	}
