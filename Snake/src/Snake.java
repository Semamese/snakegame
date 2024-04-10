
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import javax.imageio.ImageIO;
 
public class Snake {
	private Random rand = new Random();
	private int to=0;
	
	
	private int step;
	private int offsetX;
	private int offsetY;
	private BufferedImage imgSnakeHead;
	private BufferedImage imgSnakeBody1;
	private BufferedImage imgSnakeBody2;
	private BufferedImage imgSnakeTail1;
	private BufferedImage imgSnakeTail2;
	private BufferedImage imgRotate;
 
	
	private int energy;
	private int snakeLength;
 
	
	private int snakeHeadX;
	private int snakeHeadY;
	private int snakeHeadDirection;
	private int snakeDisplayStyle;
	private boolean snakeIsDeath;
 
	
	private int[] bodyX;
	private int[] bodyY;
	private int[] bodyDirection;
 
	
	public Snake(int snakeHeadX, int snakeHeadY, int snakeHeadDirection, int snakeDisplayStyle) {
		super();
		this.snakeHeadX = snakeHeadX;
		this.snakeHeadY = snakeHeadY;
		this.snakeHeadDirection = snakeHeadDirection;
		this.snakeDisplayStyle = snakeDisplayStyle;
		this.snakeIsDeath = true;
 
		snakeLength =6;
 
		
		try {
			imgSnakeHead = ImageIO.read(new FileInputStream("images\\snake\\s" + snakeDisplayStyle + "\\head.png"));
			imgSnakeBody1 = ImageIO.read(new FileInputStream("images\\snake\\s" + snakeDisplayStyle + "\\body1.png"));
			imgSnakeBody2 = ImageIO.read(new FileInputStream("images\\snake\\s" + snakeDisplayStyle + "\\body2.png"));
			imgSnakeTail1 = ImageIO.read(new FileInputStream("images\\snake\\s" + snakeDisplayStyle + "\\tail1.png"));
			imgSnakeTail2 = ImageIO.read(new FileInputStream("images\\snake\\s" + snakeDisplayStyle + "\\tail2.png"));
 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.offsetX =imgSnakeHead.getWidth()/2;
		this.offsetY =imgSnakeHead.getHeight()/2;
		this.step = 8;
 
		
		bodyX = new int[snakeLength];
		bodyY = new int[snakeLength];
		bodyDirection = new int[snakeLength];
 
		for (int i = 0; i < snakeLength; i++) {
			bodyX[i] = snakeHeadX - (i + 1) * step;
			bodyY[i] = snakeHeadY;
			bodyDirection[i] = 0;
		}
 
	}
 
	
	public void snakeShow(Graphics cg,int x,int y) {
 
		
		for (int i = snakeLength - 1; i >= 0; i--) {
			if (i == snakeLength - 1) {
				if ((i % 2 == 0)) {
					imgRotate = Tools.rotateImage(imgSnakeTail1, bodyDirection[i]);
				} else {
					imgRotate = Tools.rotateImage(imgSnakeTail2, bodyDirection[i]);
				}
 
			} else {
				
				if ((i % 2) == 0) {
					imgRotate = Tools.rotateImage(imgSnakeBody1, bodyDirection[i]);
				} else {
					imgRotate = Tools.rotateImage(imgSnakeBody2, bodyDirection[i]);
				}
 
			}
			cg.drawImage(imgRotate, bodyX[i] - offsetX+x, bodyY[i] - offsetY+y,null);
			
			//cg.setColor(Color.WHITE);
			//cg.fillRect(bodyX[i] - 1+x, bodyY[i] - 1+y, 2, 2);
		}
 
		
		imgRotate = Tools.rotateImage(imgSnakeHead, snakeHeadDirection);
		cg.drawImage(imgRotate, snakeHeadX - offsetX+x, snakeHeadY - offsetY+y,null);
 
		
		//cg.setColor(Color.WHITE);
		//cg.fillRect(snakeHeadX - 1+x, snakeHeadY - 1+y, 2, 2);
		
		
		
		//cg.setColor(Color.BLUE);
		int sX = (int) (snakeHeadX + 150 * Math.cos(snakeHeadDirection * 3.14 / 180));
		int sY = (int) (snakeHeadY + 150 * Math.sin(snakeHeadDirection * 3.14 / 180));
		//cg.fillRect(sX - 1+x, sY - 1+y, 2, 2);
		
		sX = (int) (snakeHeadX + 100 * Math.cos((snakeHeadDirection+45) * 3.14 / 180));
		sY = (int) (snakeHeadY + 100 * Math.sin((snakeHeadDirection+45) * 3.14 / 180));
		//cg.fillRect(sX - 1+x, sY - 1+y, 2, 2);
		sX = (int) (snakeHeadX + 100 * Math.cos((snakeHeadDirection-45) * 3.14 / 180));
		sY = (int) (snakeHeadY + 100 * Math.sin((snakeHeadDirection-45) * 3.14 / 180));
		//cg.fillRect(sX - 1+x, sY - 1+y, 2, 2);
		
		//cg.setColor(Color.WHITE);
		//cg.fillRect(snakeHeadX - 1+x, snakeHeadY - 1+y, 2, 2);
		
		
 
	}
	public void snakeAutoMove(){
		
		int sX = (int) (snakeHeadX + 150 * Math.cos(snakeHeadDirection * 3.14 / 180));
		int sY = (int) (snakeHeadY + 150 * Math.sin(snakeHeadDirection * 3.14 / 180));
		
		
		if(sX<=20||sX>=(Tools.MAP_X-20)||sY<=20||sY>=(Tools.MAP_Y-20)){
			boolean isL=false,isR=false;
		
			sX = (int) (snakeHeadX + 100 * Math.cos((snakeHeadDirection+45) * 3.14 / 180));
			sY = (int) (snakeHeadY + 100 * Math.sin((snakeHeadDirection+45) * 3.14 / 180));
			if(sX<=20||sX>=(Tools.MAP_X-20)||sY<=20||sY>=(Tools.MAP_Y-20)){
				isL=true;
			}
			
			sX = (int) (snakeHeadX + 100 * Math.cos((snakeHeadDirection-45) * 3.14 / 180));
			sY = (int) (snakeHeadY + 100 * Math.sin((snakeHeadDirection-45) * 3.14 / 180));
			if(sX<=20||sX>=(Tools.MAP_X-15)||sY<=20||sY>=(Tools.MAP_Y-15)){
				isR=true;
			}
			
			
			if((isL)&&(isL)){
				if(rand.nextInt(1)==1){
					snakeHeadDirection+=(rand.nextInt(5)+20);
					to=1;
				}else{
					snakeHeadDirection-=(rand.nextInt(5)+20);
					to=2;
				}
			}else if(isL){
				snakeHeadDirection-=(rand.nextInt(5)+5);
				to=2;
			}else{
				snakeHeadDirection+=(rand.nextInt(5)+5);
				to=1;
			}
		}else{
			if(rand.nextInt(rand.nextInt(20)+10)==1){
				to=rand.nextInt(3);
			}
			if (to==1) snakeHeadDirection+=(rand.nextInt(5)+2);
			if (to==2) snakeHeadDirection-=(rand.nextInt(5)+2);
		}
		snakeMove();
 
 
		
	}
	
	public void snakeMove() {
		if (snakeIsDeath) {
			
			for (int i = snakeLength - 1; i > 0; i--) {
				bodyX[i] = bodyX[i - 1];
				bodyY[i] = bodyY[i - 1];
				bodyDirection[i] = bodyDirection[i - 1];
			}
			
			bodyX[0] = snakeHeadX;
			bodyY[0] = snakeHeadY;
			bodyDirection[0] = snakeHeadDirection;
 
			snakeHeadX = (int) (snakeHeadX + step * Math.cos(snakeHeadDirection * 3.14 / 180));
			snakeHeadY = (int) (snakeHeadY + step * Math.sin(snakeHeadDirection * 3.14 / 180));
		}
 
	}
 
	
	public void snakGrow() {
 
		snakeLength = snakeLength + 1;
 
		if (snakeLength > bodyX.length) {
			bodyX = Arrays.copyOf(bodyX, snakeLength);
			bodyY = Arrays.copyOf(bodyY, snakeLength);
			bodyDirection = Arrays.copyOf(bodyDirection, snakeLength);
		}
 
	}
 
	
	public void snakeDeath() {
		this.snakeIsDeath = false;
		
		Random rand = new Random();
		for (int i = 0; i < snakeLength; i++) {
			bodyX[i] = bodyX[i] + rand.nextInt(step * 2) - rand.nextInt(step * 2);
			bodyY[i] = bodyY[i] + rand.nextInt(step * 2) - rand.nextInt(step * 2);
			bodyDirection[i] = bodyDirection[i] + rand.nextInt(360);
		}
		//snakeHeadX = snakeHeadX + rand.nextInt(step) - rand.nextInt(step);
		//snakeHeadY = snakeHeadY + rand.nextInt(step) - rand.nextInt(step);
		snakeHeadX = (int) (snakeHeadX + step * Math.cos(snakeHeadDirection * 3.14 / 180));
		snakeHeadY = (int) (snakeHeadY + step * Math.sin(snakeHeadDirection * 3.14 / 180));
	}
 
 
	// get---- set-----
	public int getSnakeHeadDirection() {
		return snakeHeadDirection;
	}
 
	public void setSnakeHeadDirection(int snakeHeadDirection) {
		this.snakeHeadDirection = snakeHeadDirection;
	}
 
	public int getSnakeHeadX() {
		return snakeHeadX;
	}
 
	public void setSnakeHeadX(int snakeHeadX) {
		this.snakeHeadX = snakeHeadX;
	}
 
	public int getSnakeHeadY() {
		return snakeHeadY;
	}
 
	public void setSnakeHeadY(int snakeHeadY) {
		this.snakeHeadY = snakeHeadY;
	}
 
	public boolean getSnakIsDeath() {
		return snakeIsDeath;
	}
 
	public boolean isSnakeIsDeath() {
		return snakeIsDeath;
	}
 
	public void setSnakeIsDeath(boolean snakeIsDeath) {
		this.snakeIsDeath = snakeIsDeath;
	}
 
	public int getEnergy() {
		return energy;
	}
 
	public void setEnergy(int energy) {
		this.energy = energy;
		
		if((energy-1)>=0){
			this.snakGrow();
			this.energy=this.energy-1;
		}
	}
 
	public int getSnakeLength() {
		return snakeLength;
	}
 
	public void setSnakeLength(int snakeLength) {
		this.snakeLength = snakeLength;
	}
 
	public int getSnakeDisplayStyle() {
		return snakeDisplayStyle;
	}
 
	public void setSnakeDisplayStyle(int snakeDisplayStyle) {
		this.snakeDisplayStyle = snakeDisplayStyle;
	}
 
}
