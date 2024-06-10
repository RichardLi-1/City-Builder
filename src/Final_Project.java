import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.event.*;
import java.util.Scanner;


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
    public static BufferedImage coffeeShop;
    public static BufferedImage sidePanel;
    public static BufferedImage selection;
    public static BufferedImage horizontalRoad;
    public static BufferedImage emptyPlot;
    public static BufferedImage townHall;
    public static BufferedImage tornado;
    public static BufferedImage lockedArea;
    public static BufferedImage[] numbers = new BufferedImage[10];
    public static int dialogue;
    public static int gameState;
    public static int lastGameState;
    public static int mainscreenX;
    public static int mainscreenY;
    public static int storylineState;
    public static int gravity;
    public static int buttonPressed;
    public static int xPressed;
    public static int yPressed;
    public static int lastxPressed;
    public static int lastyPressed;
    public static int money;
    public static int gdp;
    public static int population;
    public static int tornadox;
    public static int tornadoy;
    public static boolean isJumping;
    public static boolean returningHome;
    public static boolean tornadoActive;
    public static int[][] buildingType = new int[25][18];
    
    
    public static boolean[] cloudsUnlocked = {false, false, false, false};
    
    public static boolean sidePanelOpen;
    
    public static void reset() {
    	dialogue = 1;
        gameState = 0;
        lastGameState = 0;
        mainscreenX = 0;
        mainscreenY = 802;
        storylineState = 0;
        gravity = 2;
        buttonPressed = 0;
        money = 134567;
        population = 10;
        
        cloudsUnlocked[0] = false;
        cloudsUnlocked[1] = false;
        cloudsUnlocked[2] = false;
        cloudsUnlocked[3] = false;
        
        //Set initial map of empty and locked area
        
        for(int i = 0; i<24;i++) {
        	for(int ii = 0; ii<17; ii++) {
        		buildingType[i][ii] = 0;
        		if(i<7 || i > 21 || ii<2 || ii > 13) {
        			buildingType[i][ii] = 1;	
        		}
        	}
        }
        
        buildingType[15][10] = 2;
        
        for(int i = 0; i<24;i++) {
        	buildingType[i][11] = 3;
        }
        
        sidePanelOpen = false;
        tornadox = 0;
        tornadoy = 0;
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
            	if(cloudsUnlocked[0] == false) {
	        		g.drawImage(cloudTop, mainscreenX+960, mainscreenY, null);
	        	}
	        	if(cloudsUnlocked[1] == false) {
	        		g.drawImage(cloudRight, mainscreenX+960, mainscreenY, null);
	        	}
	        	if(cloudsUnlocked[2] == false) {
	        		g.drawImage(cloudBottom, mainscreenX+960, mainscreenY, null);
	        	}
	        	if(cloudsUnlocked[3] == false) {
	        		g.drawImage(cloudLeft, mainscreenX+960, mainscreenY, null);
	        	}
	        	g.drawImage(menubar, mainscreenX+960, 0, null);
            	
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
	        	if(cloudsUnlocked[0] == false) {
	        		g.drawImage(cloudTop, mainscreenX+960, mainscreenY, null);
	        	}
	        	if(cloudsUnlocked[1] == false) {
	        		g.drawImage(cloudRight, mainscreenX+960, mainscreenY, null);
	        	}
	        	if(cloudsUnlocked[2] == false) {
	        		g.drawImage(cloudBottom, mainscreenX+960, mainscreenY, null);
	        	}
	        	if(cloudsUnlocked[3] == false) {
	        		g.drawImage(cloudLeft, mainscreenX+960, mainscreenY, null);
	        	}
	        	
	        	
	        	//Change to array later
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
	        	if(cloudsUnlocked[0] == false) {
	        		g.drawImage(cloudTop, mainscreenX+960, mainscreenY, null);
	        	}
	        	if(cloudsUnlocked[1] == false) {
	        		g.drawImage(cloudRight, mainscreenX+960, mainscreenY, null);
	        	}
	        	if(cloudsUnlocked[2] == false) {
	        		g.drawImage(cloudBottom, mainscreenX+960, mainscreenY, null);
	        	}
	        	if(cloudsUnlocked[3] == false) {
	        		g.drawImage(cloudLeft, mainscreenX+960, mainscreenY, null);
	        	}
	        	g.drawImage(menubar, mainscreenX+960, 0, null);
	        	//if(mainscreenX >-960) {
		        //	mainscreenX -= 30;
		        //}
	        	//else {
	        	//	mainscreenX=-960;
	        	//}
        	}
        	
        		g.drawImage(background, 0, 0, null);
	        	g.drawImage(screen, mainscreenX, mainscreenY, null);
	        	g.drawImage(grass, mainscreenX+960, mainscreenY, null);
	        	
	        	if(sidePanelOpen) {
	        		g.drawImage(selection, mainscreenX+960+(lastxPressed*40), mainscreenY+(lastyPressed*40), null);
	        	}
	        	//Spawning the buildings
	        	for(int i = 0; i < 24; i++) {
	        		for(int ii = 0; ii < 17; ii++) {
	        			if(buildingType[i][ii] == 2) {//House
	        				g.drawImage(house, mainscreenX+960+(i*40), mainscreenY+(ii*40), null);
	        			}
	        			if(buildingType[i][ii] == 3) {//Horizontal Road
	        				g.drawImage(horizontalRoad, mainscreenX+960+(i*40), mainscreenY+(ii*40), null);
	        			}
	        			if(buildingType[i][ii] == 7) {//Coffee Shop
	        				g.drawImage(coffeeShop, mainscreenX+960+(i*40), mainscreenY+(ii*40), null);
	        			}
	        			if(buildingType[i][ii] == 8) {//Town Hall
	        				g.drawImage(townHall, mainscreenX+960+(i*40), mainscreenY+(ii*40), null);
	        			}
	        			if(buildingType[i][ii] == 9) {//Tornado
	        				g.drawImage(tornado, mainscreenX+960+(i*40), mainscreenY+(ii*40), null);
	        			}//change to array bufferedimage to remove the if statements later!!!!!
	        			
	        		}
	        	}
	        	g.drawImage(dialogue0, mainscreenX, mainscreenY-718, null);
	        	if(cloudsUnlocked[0] == false) {
	        		g.drawImage(cloudTop, mainscreenX+960, mainscreenY, null);
	        	}
	        	if(cloudsUnlocked[1] == false) {
	        		g.drawImage(cloudRight, mainscreenX+960, mainscreenY, null);
	        	}
	        	if(cloudsUnlocked[2] == false) {
	        		g.drawImage(cloudBottom, mainscreenX+960, mainscreenY, null);
	        	}
	        	if(cloudsUnlocked[3] == false) {
	        		g.drawImage(cloudLeft, mainscreenX+960, mainscreenY, null);
	        	}
	        	g.drawImage(menubar, mainscreenX+960, mainscreenY, null);
	        	
	        	if(mainscreenX >-960) {
		        	mainscreenX -= 40;
		        }
	        	if(mainscreenY >0) {
		        	mainscreenY -= 30;
		        }
	        	
	        	//Writing the money
	        	
	        	//First Digit
	        	g.drawImage(numbers[money % 10], mainscreenX+960+140, mainscreenY+20, null);
	        	//Second Digit
	        	if(money>=10) {
	        		g.drawImage(numbers[(money % 100 - (money % 10))/10], mainscreenX+960+127, mainscreenY+20, null);
	        	}
	        	//Third Digit
	        	if(money>=100) {
	        		g.drawImage(numbers[(money % 1000 - (money % 100 - (money % 10)))/100], mainscreenX+960+114, mainscreenY+20, null);
	        	}
	        	//Fourth Digit
	        	if(money>=1000) {
	        		g.drawImage(numbers[(money % 10000 - (money % 1000 - (money % 100 - (money % 10))))/1000], mainscreenX+960+101, mainscreenY+20, null);
	        	}
	        	//Fifth Digit
	        	if(money>=10000) {
	        		g.drawImage(numbers[(money % 100000 - (money % 10000 - (money % 1000 - (money % 100 - (money % 10)))))/10000], mainscreenX+960+88, mainscreenY+20, null);
	        	}
	        	//Sixth Digit
	        	if(money>=100000) {
	        		g.drawImage(numbers[(money % 1000000 - (money % 100000 - (money % 10000 - (money % 1000 - (money % 100 - (money % 10))))))/100000], mainscreenX+960+75, mainscreenY+20, null);
	        	}
	        	
	        	//Writing the GDP
	        	int populationOffset = 300;
	        	
	        	//First Digit
	        	g.drawImage(numbers[gdp % 10], mainscreenX+960+140+populationOffset, mainscreenY+20, null);
	        	//Second Digit
	        	if(gdp>=10) {
	        		g.drawImage(numbers[(gdp % 100 - (gdp % 10))/10], mainscreenX+960+127+populationOffset, mainscreenY+20, null);
	        	}
	        	//Third Digit
	        	if(gdp>=100) {
	        		g.drawImage(numbers[(gdp % 1000 - (gdp % 100 - (gdp % 10)))/100], mainscreenX+960+114+populationOffset, mainscreenY+20, null);
	        	}
	        	//Fourth Digit
	        	if(gdp>=1000) {
	        		g.drawImage(numbers[(gdp % 10000 - (gdp % 1000 - (gdp % 100 - (population % 10))))/1000], mainscreenX+960+101+populationOffset, mainscreenY+20, null);
	        	}
	        	//Fifth Digit
	        	if(gdp>=10000) {
	        		g.drawImage(numbers[(gdp % 100000 - (gdp % 10000 - (gdp % 1000 - (gdp % 100 - (gdp % 10)))))/10000], mainscreenX+960+88+populationOffset, mainscreenY+20, null);
	        	}
	        	//Sixth Digit
	        	if(gdp>=100000) {
	        		g.drawImage(numbers[(gdp % 1000000 - (gdp % 100000 - (gdp % 10000 - (gdp % 1000 - (gdp % 100 - (gdp % 10))))))/100000], mainscreenX+960+75+populationOffset, mainscreenY+20, null);
	        	}
	        	
	        	//Writing the population
	        	populationOffset = 150;
	        	
	        	//First Digit
	        	g.drawImage(numbers[population % 10], mainscreenX+960+140+populationOffset, mainscreenY+20, null);
	        	//Second Digit
	        	if(population>=10) {
	        		g.drawImage(numbers[(population % 100 - (population % 10))/10], mainscreenX+960+127+populationOffset, mainscreenY+20, null);
	        	}
	        	//Third Digit
	        	if(population>=100) {
	        		g.drawImage(numbers[(population % 1000 - (population % 100 - (population % 10)))/100], mainscreenX+960+114+populationOffset, mainscreenY+20, null);
	        	}
	        	//Fourth Digit
	        	if(population>=1000) {
	        		g.drawImage(numbers[(population % 10000 - (population % 1000 - (population % 100 - (population % 10))))/1000], mainscreenX+960+101+populationOffset, mainscreenY+20, null);
	        	}
	        	//Fifth Digit
	        	if(population>=10000) {
	        		g.drawImage(numbers[(population % 100000 - (population % 10000 - (population % 1000 - (population % 100 - (population % 10)))))/10000], mainscreenX+960+88+populationOffset, mainscreenY+20, null);
	        	}
	        	//Sixth Digit
	        	if(population>=100000) {
	        		g.drawImage(numbers[(population % 1000000 - (population % 100000 - (population % 10000 - (population % 1000 - (population % 100 - (population % 10))))))/100000], mainscreenX+960+75+populationOffset, mainscreenY+20, null);
	        	}
	        	
        	}
        	
        	if(sidePanelOpen) {
        		g.drawImage(sidePanel, 0, 0, null);
    			if(buildingType[xPressed][yPressed] == 0) {//if empty
    				g.drawImage(emptyPlot, 0, 0, null);
    			}
    			if(buildingType[xPressed][yPressed] == 1) {//if locked under cloud
    				g.drawImage(lockedArea, 0, 0, null);
    			}
        		if(buildingType[xPressed][yPressed] == 2) {//if house
        			g.drawImage(house, 700, 100, null);
        		}
        		else if(buildingType[xPressed][yPressed] == 3) {//if horizontal road
        			g.drawImage(horizontalRoad, 700, 100, null);
        		}
        		else if(buildingType[xPressed][yPressed] == 7) {//if coffee shop
        			g.drawImage(coffeeShop, 700, 100, null);
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
    public static void main(String[] args) throws IOException{
    	Scanner inputFile = new Scanner(new File("stats.txt"));
		PrintWriter outputFile = new PrintWriter(new FileWriter("stats.txt"));
    	reset();

        JFrame frame = new JFrame("KailynDingVille City Builder");
        Final_Project panel = new Final_Project();
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
        background = ImageIO.read(new File("background.png"));
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
        coffeeShop = ImageIO.read(new File("coffee.png"));
        sidePanel = ImageIO.read(new File("sidePanel.png"));
        selection = ImageIO.read(new File("selection.png"));
        horizontalRoad = ImageIO.read(new File("roadHorizontal.png"));
        emptyPlot = ImageIO.read(new File("emptyPlot.png"));
        lockedArea = ImageIO.read(new File("lockedArea.png"));
        townHall = ImageIO.read(new File("townhall.png"));
        tornado = ImageIO.read(new File("tornado.png"));
        numbers[0] = ImageIO.read(new File("zero.png"));
        numbers[1] = ImageIO.read(new File("one.png"));
        numbers[2] = ImageIO.read(new File("two.png"));
        numbers[3] = ImageIO.read(new File("three.png"));
        numbers[4] = ImageIO.read(new File("four.png"));
        numbers[5] = ImageIO.read(new File("five.png"));
        numbers[6] = ImageIO.read(new File("six.png"));
        numbers[7] = ImageIO.read(new File("seven.png"));
        numbers[8] = ImageIO.read(new File("eight.png"));
        numbers[9] = ImageIO.read(new File("nine.png"));

        while(true) {
        	if(storylineState != 1) {
	        	String fileName = dialogue + ".png";
	        	dialogue0 = ImageIO.read(new File(fileName));
		        if(buttonPressed > 0) {
		        	buttonShade = ImageIO.read(new File("button" + buttonPressed + ".png"));
		        }
        	}
        	countGDP();
        	naturalDisaster();
        	//if(!sidePanelOpen) {
        	//	lastxPressed = 0;
            //    lastyPressed = 0;
        	//}
        	
        	//System.out.println(storylineState);
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
				xPressed = e.getX()/40;
	            yPressed = e.getY()/40; 
				if(!sidePanelOpen) {
					lastxPressed = e.getX()/40;
		            lastyPressed = e.getY()/40;
				}
	            System.out.println(xPressed + ", " + yPressed);
	            
	            //Controls opening and closing the side panel
	            if(sidePanelOpen==true) {
	            	//Clicking things in the side panel
	    			if(sidePanelOpen) {
	    				if(buildingType[lastxPressed][lastyPressed] == 0) {
	    					//IF THEY CLICK TO BUY A HOUSE
	    					if(e.getX() >= 709 && e.getX() <=756 && e.getY() >= 132 && e.getY() <= 213 && e.getButton()==1) {
	    						if(money >= 1000) {
		    						buildingType[lastxPressed][lastyPressed] = 2;
		    						sidePanelOpen = false;
		    						money -= 1000;
	    						}
	    						else {
	    							System.out.println("no money");
	    						}
	    					}
	    					//IF THEY CLICK TO BUY A COFFEE SHOP
	    					if(e.getX() >= 780 && e.getX() <=852 && e.getY() >= 132 && e.getY() <= 213 && e.getButton()==1) {
	    						if(money >= 1000) {
		    						buildingType[lastxPressed][lastyPressed] = 7;
		    						sidePanelOpen = false;
		    						money -= 1000;
	    						}
	    						else {
	    							System.out.println("no money");
	    						}
	    					}
	    					
	    					//IF THEY CLICK TO BUY A TOWN HALL
	    					if(e.getX() >= 865 && e.getX() <= 930 && e.getY() >= 132 && e.getY() <= 213 && e.getButton()==1) {
	    						if(money >= 10000) {
		    						buildingType[lastxPressed][lastyPressed] = 8;
		    						sidePanelOpen = false;
		    						money -= 10000;
	    						}
	    						else {
	    							System.out.println("no money");
	    						}
	    					}
	    					countGDP();
	    				}
	    				else if(buildingType[lastxPressed][lastyPressed] == 1) {
	    					//IF THEY CLICK TO UNLOCK
	    					if(e.getX() >= 709 && e.getX() <=930 && e.getY() >= 132 && e.getY() <= 213 && e.getButton()==1) {
	    						if(money >= 100000) {
	    							for(int i = 0; i<24; i++) {
	    								for(int ii = 0; ii<3; ii++) {
		    								buildingType[i][ii] = 0;
	    								}
	    							}
		    						
	    							cloudsUnlocked[0] = true;
		    						sidePanelOpen = false;
		    						money -= 100000;
	    						}
	    						else {
	    							System.out.println("no money");
	    						}
	    					}
	    				}
	    			}
	    			else if(buildingType[xPressed][yPressed] == 0){
	    				sidePanelOpen = false;
	    			}
				}
	            else if(sidePanelOpen==false) {
					sidePanelOpen = true;
				}
				
				
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
	
	public static void countGDP(){
		gdp = 0;
        for(int i = 0; i<24; i++) {
        	for(int ii = 0; ii<17; ii++) {
        		if(buildingType[i][ii] == 1) {
            		gdp -= 5;
            	}
            	if(buildingType[i][ii] == 2) {
            		gdp += 1000;
            	}
            	if(buildingType[i][ii] == 3 || buildingType[i][ii] == 4) {
            		gdp += 200;
            	}
            	if(buildingType[i][ii] == 7) {
            		gdp += 1000;
            	}
            	if(buildingType[i][ii] == 8) {
            		gdp += 10000;
            	}
            }
        }
        
        if(gdp<4000) {
        	dialogue = 5;
			gameState = 2;
			lastGameState = 3;
			storylineState = 2;
        }
	}
	
	public static void naturalDisaster() {
		buildingType[tornadox][tornadoy] = 9;
		if(Math.random()>0.001 && Math.random()>0.9 && !tornadoActive && gameState == 3) {
			tornadoActive = true;
			tornado();
		}
		
	}
	
	public static void tornado() {
		System.out.println("a tornado is forming");
		int tornadox = (int) Math.round(Math.random()*23);
		int tornadoy = (int) Math.round(Math.random()*16);
		int tornadoDirection = (int)Math.round(Math.random()*4);
		System.out.println(tornadoDirection);
		countGDP();
		buildingType[tornadox][tornadoy] = 9;
		while(tornadoActive) {
			int movement = (int) (Math.random()*300000000);
			//Certain Direction
			if(movement>10 && movement <20) {
				movement = tornadoDirection + 1;
			}
			//Random Direction (or certain if it landed on certain)
			if(movement==2 && tornadox > 0) {
				tornadox -= 1;
				buildingType[tornadox+1][tornadoy] = 0;
				buildingType[tornadox][tornadoy] = 9;
			}
			else if(movement == 3 && tornadox < 24) {
				tornadox += 1;
				buildingType[tornadox-1][tornadoy] = 0;
				buildingType[tornadox][tornadoy] = 9;
			}
			else if(movement == 4 && tornadoy > 0) {
				tornadoy -= 1;
				buildingType[tornadox][tornadoy+1] = 0;
				buildingType[tornadox][tornadoy] = 9;
			}
			else if(movement == 5 && tornadoy < 17) {
				tornadoy += 1;
				buildingType[tornadox][tornadoy-1] = 0;
				buildingType[tornadox][tornadoy] = 9;
			}
			//Cancel Tornado
			if(tornadox<=0 || tornadox == 24 || tornadoy <= 0 || tornadoy == 17) {
				tornadoActive = false;
				buildingType[tornadox][tornadoy] = 0;
				tornadox = 0;
				tornadoy = 0;
				System.out.println("tornado despawned");
			}
		}
		
		
	}
    public void run() {
        while(true){
            // sleep
            try{Thread.sleep(16);}// orig 20
            //62.5fps
            catch(Exception e){}

            // 2) Drawing screen
            repaint();
            
            //Game Mechanics
            //Recount the net worth
            
            
        }
    }
}