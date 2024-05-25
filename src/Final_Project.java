import java.awt.*;
//import java.util.concurrent.TimeUnit;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.event.*;


public class Final_Project extends JPanel implements KeyListener, Runnable, MouseListener{
    // Game Stats
    public static BufferedImage background;
    public static BufferedImage blurredBackground;
    public static BufferedImage screen;
    public static BufferedImage credit;
    public static BufferedImage reset;
    public static BufferedImage grass;
    public static BufferedImage menubar;
    public static BufferedImage dialogue0;
    public static BufferedImage buttonShade;
    public static BufferedImage cloudLeft;
    public static BufferedImage cloudTop;
    public static BufferedImage cloudRight;
    public static BufferedImage cloudBottom;
    public static BufferedImage house;
    public static BufferedImage sidePanel;
    public static BufferedImage selection;
    public static BufferedImage horizontalRoad;
    public static int dialogue;
    public static int gameState;
    public static int lastGameState;
    public static int mainscreenX;
    public static int mainscreenY;
    public static int storylineState;
    public static int velocity;
    public static int gravity;
    public static int buttonPressed;
    public static boolean isJumping;
    public static boolean returningHome;
    public static int[][] buildingType = new int[24][17];
    
    
    public static boolean[] cloudsUnlocked = {false, false, false, false};
    
    public static boolean sidePanelOpen;
    
    public static void reset() {
    	dialogue = 1;
        gameState = 0;
        lastGameState = 0;
        mainscreenX = 0;
        mainscreenY = 802;
        storylineState = 0;
        velocity = -15;
        gravity = 2;
        buttonPressed = 0;
        
        cloudsUnlocked[0] = false;
        cloudsUnlocked[1] = false;
        cloudsUnlocked[2] = false;
        cloudsUnlocked[3] = false;
        
        buildingType[15][10] = 2;
        
        for(int i = 0; i<24;i++) {
        	buildingType[i][11] = 3;
        }
        
        sidePanelOpen = false;
    }
    

    // JPanel
    public Final_Project(){
        setPreferredSize(new Dimension(960, 718));
        this.setFocusable(true);
        addKeyListener(this);
        Thread thread = new Thread(this);
        thread.start();
        addMouseListener(this);
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        
        if(gameState == 0) {//When game is first opened
        	if(mainscreenY >450) {
	        	mainscreenY -= 6;
	        }
        	else if(mainscreenY >400) {
	        	mainscreenY -= 11;
	        }
        	else if(mainscreenY >200) {
	        	mainscreenY -= 13;
	        }
	        else if(mainscreenY >50) {
	        	mainscreenY -= 15;
	        }
	        else if(mainscreenY >35) {
	        	mainscreenY -= 10;
	        }
	        else if(mainscreenY >45) {
	        	mainscreenY -= 8;
	        }
	        else if(mainscreenY >0) {
	        	mainscreenY -= 6;
	        }
	        else {
	        	mainscreenY = 0;
	        	gameState = 1;
	        }
	
	        g.drawImage(background, 0, 0, null);
	        g.drawImage(screen, 0, mainscreenY, null);
        }
        
        else if(gameState == 1) {
        	g.drawImage(background, 0, 0, null);
        	g.drawImage(screen, 0, mainscreenY, null);
        	if(lastGameState == 3) {
        		g.drawImage(background, mainscreenX, 0, null);
            	g.drawImage(screen, mainscreenX, 0, null);
            	g.drawImage(grass, mainscreenX+960, 0, null);
            	g.drawImage(cloudLeft, mainscreenX+960, 0, null);
	        	g.drawImage(cloudTop, mainscreenX+960, 0, null);
	        	g.drawImage(cloudRight, mainscreenX+960, 0, null);
	        	g.drawImage(cloudBottom, mainscreenX+960, 0, null);
	        	g.drawImage(menubar, mainscreenX+960, 0, null);
	        	g.drawImage(house, mainscreenX+1360, 400, null);
            	
            	if(mainscreenX <0) {
    	        	mainscreenX += 30;
    	        }
            	else {
            		mainscreenX=0;
            	}
        	}
        }
        else if(gameState == 2) {//Dialogue
        	if(lastGameState != 3) {//Pre-Game Dialogue (Transition from Home Screen)
	        	g.drawImage(background, 0, 0, null);
	        	g.drawImage(screen, 0, mainscreenY, null);
	        	g.drawImage(dialogue0, 0, mainscreenY-718, null);
	        	if(mainscreenY <718) {
		        	mainscreenY += 15;
		        }
        	}
        	else {//Winning or Losing Dialogue (Transition from Game)
        		g.drawImage(background, 0, 0, null);
	        	g.drawImage(screen, mainscreenX, mainscreenY, null);
	        	g.drawImage(grass, mainscreenX+960, mainscreenY, null);
	        	g.drawImage(dialogue0, mainscreenX, mainscreenY-718, null);
	        	g.drawImage(cloudLeft, mainscreenX+960, mainscreenY, null);
	        	g.drawImage(cloudTop, mainscreenX+960, mainscreenY, null);
	        	g.drawImage(cloudRight, mainscreenX+960, mainscreenY, null);
	        	g.drawImage(cloudBottom, mainscreenX+960, mainscreenY, null);
	        	g.drawImage(menubar, mainscreenX+960, mainscreenY, null);
	        	
	        	
	        	if(mainscreenX <0) {
		        	mainscreenX += 40;
		        }
	        	if(mainscreenY < 718) {
		        	mainscreenY += 30;
		        }
        	}
        }
        else if(gameState == 3) {
        	
        	if(lastGameState == 1) {
	        	g.drawImage(background, mainscreenX, 0, null);
	        	g.drawImage(screen, mainscreenX, 0, null);
	        	g.drawImage(grass, mainscreenX+960, 0, null);
	        	for(int i = 0; i < 24; i++) {
	        		for(int ii = 0; ii < 17; ii++) {
	        			if(buildingType[i][ii] == 2) {
	        				g.drawImage(house, mainscreenX+960+(i*40), mainscreenY+(ii*40), null);
	        			}
	        			if(buildingType[i][ii] == 3) {
	        				g.drawImage(horizontalRoad, mainscreenX+960+(i*40), mainscreenY+(ii*40), null);
	        			}
	        		}
	        	}
	        	g.drawImage(cloudLeft, mainscreenX+960, 0, null);
	        	g.drawImage(cloudTop, mainscreenX+960, 0, null);
	        	g.drawImage(cloudRight, mainscreenX+960, 0, null);
	        	g.drawImage(cloudBottom, mainscreenX+960, 0, null);
	        	g.drawImage(menubar, mainscreenX+960, 0, null);
	        	if(mainscreenX >-960) {
		        	mainscreenX -= 30;
		        }
	        	else {
	        		mainscreenX=-960;
	        	}
        	}
        	else {//change this later to if(lastGameState == 1);
        		g.drawImage(background, 0, 0, null);
	        	g.drawImage(screen, mainscreenX, mainscreenY, null);
	        	g.drawImage(grass, mainscreenX+960, mainscreenY, null);
	        	for(int i = 0; i < 24; i++) {
	        		for(int ii = 0; ii < 17; ii++) {
	        			if(buildingType[i][ii] == 2) {
	        				g.drawImage(house, mainscreenX+960+(i*40), mainscreenY+(ii*40), null);
	        			}
	        			if(buildingType[i][ii] == 3) {
	        				g.drawImage(horizontalRoad, mainscreenX+960+(i*40), mainscreenY+(ii*40), null);
	        			}
	        		}
	        	}
	        	g.drawImage(dialogue0, mainscreenX, mainscreenY-718, null);
	        	g.drawImage(cloudLeft, mainscreenX+960, mainscreenY, null);
	        	g.drawImage(cloudTop, mainscreenX+960, mainscreenY, null);
	        	g.drawImage(cloudRight, mainscreenX+960, mainscreenY, null);
	        	g.drawImage(cloudBottom, mainscreenX+960, mainscreenY, null);
	        	g.drawImage(menubar, mainscreenX+960, mainscreenY, null);
	        	
	        	if(mainscreenX >-960) {
		        	mainscreenX -= 40;
		        }
	        	if(mainscreenY >0) {
		        	mainscreenY -= 30;
		        }
        	}
        	
        	if(sidePanelOpen) {
        		g.drawImage(sidePanel, 0, 0, null);
        	}
        }
        else if(gameState == 5) {//Credits
        	g.drawImage(blurredBackground, 0, 0, null);
        	g.drawImage(credit, 0, 0, null);
        	System.out.println("Credits open");
        }
        else if(gameState == 6) {//Reset
        	g.drawImage(blurredBackground, 0, 0, null);
        	g.drawImage(reset, 0, 0, null);
        }
        
        if(buttonPressed > 0) {
        	g.drawImage(buttonShade, 0, 0, null);
        }
    }
    public static void main(String[] args) throws IOException, InterruptedException{
        // Image Importation
        //trex[1] = ImageIO.read(new File("trexleft.png"));
        //trex[2] = ImageIO.read(new File("trexjump.png"));
    	reset();

        JFrame frame = new JFrame("KailynDingVille City Builder");
        Final_Project panel = new Final_Project();
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
        background = ImageIO.read(new File("background.png"));
        //TimeUnit.SECONDS.sleep(2);
        screen = ImageIO.read(new File("screen.png"));
        blurredBackground = ImageIO.read(new File("blur.png"));
        credit = ImageIO.read(new File("credits.png"));
        reset = ImageIO.read(new File("reset.png"));
        grass = ImageIO.read(new File("grass.png"));
        menubar = ImageIO.read(new File("menubar.png"));
        cloudLeft = ImageIO.read(new File("cloud1.png"));
        cloudTop = ImageIO.read(new File("cloud2.png"));
        cloudRight = ImageIO.read(new File("cloud3.png"));
        cloudBottom = ImageIO.read(new File("cloud4.png"));
        house = ImageIO.read(new File("house.png"));
        sidePanel = ImageIO.read(new File("sidePanel.png"));
        selection = ImageIO.read(new File("selection.png"));
        horizontalRoad = ImageIO.read(new File("roadHorizontal.png"));

        while(true) {
        	if(storylineState != 1) {
	        	String fileName = dialogue + ".png";
	        	dialogue0 = ImageIO.read(new File(fileName));
	        if(buttonPressed > 0) {
	        	buttonShade = ImageIO.read(new File("button" + buttonPressed + ".png"));
	        }
	        	//try{Thread.sleep(50);}
	            //catch(Exception e){}
        	}
        	System.out.println(storylineState);
        }
    }
    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()==32){
            isJumping = true;
        }
    }


    public void keyReleased(KeyEvent e) {

    }
    
    public void mouseClicked(MouseEvent e) {
    	
    }
    
	public void mousePressed(MouseEvent e) {
		//if(gameState == 1) {
		//	if(e.getX() >= 317 && e.getX() <=672 && e.getY() >= 314 && e.getY() <= 592 && e.getButton()==1){
		//		
		//	}
		//}
		if(gameState == 1) {
    		//Credits
			if(e.getX() >= 516 && e.getX() <=672 && e.getY() >= 512 && e.getY() <= 592 && e.getButton()==1){
				buttonPressed = 4;
			}
			//Reset
			else if(e.getX() >= 289 && e.getX() <=456 && e.getY() >= 512 && e.getY() <= 592 && e.getButton()==1){
				buttonPressed = 3;
			}
			//Instructions
			else if(e.getX() >= 516 && e.getX() <=672 && e.getY() >= 512 && e.getY() <= 592 && e.getButton()==1){
				buttonPressed = 2;
			}
			//Play
			else if(e.getX() >= 289 && e.getX() <=456 && e.getY() >= 389 && e.getY() <= 472 && e.getButton()==1){
				buttonPressed = 1;
			}
    	}
    	repaint();
	}
	public void mouseReleased(MouseEvent e) {
		if(gameState == 1) {//Home Screen
			lastGameState = 1;
			//Credits
			if(buttonPressed == 4 && e.getX() >= 516 && e.getX() <=672 && e.getY() >= 512 && e.getY() <= 592 && e.getButton()==1){
				gameState = 5;
				repaint();
			}
			//Reset
			else if(buttonPressed == 3 && e.getX() >= 289 && e.getX() <=456 && e.getY() >= 512 && e.getY() <= 592 && e.getButton()==1){
				gameState = 6;
				repaint();
			}
			//Play
			else if(buttonPressed == 1 && e.getX() >= 289 && e.getX() <=456 && e.getY() >= 389 && e.getY() <= 472 && e.getButton()==1){
        		mainscreenX = 0;
				gameState = 3;
				if(storylineState %2==0) {
					gameState = 2;
				}
				repaint();
			}
		}
		else if(gameState == 2) {//Dialogue
			dialogue ++;
			if(dialogue==4) {
				lastGameState = 2;
				gameState = 3;
				storylineState = 1;
			}
			if(dialogue==6) {
				reset();
			}
			repaint();
		}
		else if(gameState ==3) {//Game
			if(e.getButton()==3) {
				dialogue = 5;
				gameState = 2;
				lastGameState = 3;
				storylineState = 2;
			}
			else if(e.getX() >= 842 && e.getX() <=926 && e.getY() >= 14 && e.getY() <= 55 && e.getButton()==1){
				if(!sidePanelOpen) {
					gameState = 1;
					lastGameState = 3;
					repaint();
				}
				else {
					sidePanelOpen = false;
				}
			}
			else {
				sidePanelOpen = true;
			}
			repaint();
		}
		else if(gameState ==5) {//Credits
			gameState = 1;
			repaint();
		}
		else if(gameState ==6) {//Reset
			gameState = 1;
			repaint();
			if(e.getX() >= 299 && e.getX() <=378 && e.getY() >= 425 && e.getY() <= 471 && e.getButton()==1) {
				reset();
			}
		}
		buttonPressed = 0;
	}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {
		
	}


    public void run() {
        while(true){
            // sleep
            try{Thread.sleep(16);}// orig 20
            //62.5fps
            catch(Exception e){}

            // 2) Drawing screen
            repaint();
        }
    }
}