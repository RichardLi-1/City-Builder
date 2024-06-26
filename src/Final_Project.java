import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.event.*;
import java.util.Scanner;


public class Final_Project extends JPanel implements Runnable, MouseListener{
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
    public static BufferedImage sidePanel;
    public static BufferedImage selection;
    public static BufferedImage emptyPlotSelection;
    public static BufferedImage lockedArea;
    public static BufferedImage[] instructions = new BufferedImage[6];
    public static BufferedImage[] buildings = new BufferedImage[60];
    public static BufferedImage[] numbers = new BufferedImage[10];
    public static BufferedImage[] sidePanelSelection = new BufferedImage[60];
    public static int dialogue;
    public static int instructionStep;
    public static int gameState;
    public static int lastGameState;
    public static int mainscreenX;
    public static int mainscreenY;
    public static int storylineState;
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
        buttonPressed = 0;
        money = 10000;
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
        
        buildingType[10][10] = 42;
        buildingType[15][10] = 2;
        buildingType[13][10] = 52;
        buildingType[14][10] = 57;
        
        for(int i = 0; i<24;i++) {
        	buildingType[i][11] = 3;
        }
        
        
        tornadox = 0;
        tornadoy = 0;
        sidePanelOpen = false;
    }
    
    public static void save() throws IOException {
    	
		PrintWriter outputFile = new PrintWriter(new FileWriter("save.txt"));
    	
    	outputFile.println(dialogue);
    	outputFile.println(gameState);
    	outputFile.println(mainscreenX);
    	outputFile.println(mainscreenY);
    	outputFile.println(storylineState);
    	outputFile.println(buttonPressed);
    	outputFile.println(money);
    	outputFile.println(population);
    	outputFile.println(cloudsUnlocked[0]);
    	outputFile.println(cloudsUnlocked[1]);
    	outputFile.println(cloudsUnlocked[2]);
    	outputFile.println(cloudsUnlocked[3]);
    	
        
        
        //Set initial map of empty and locked area
        
        for(int i = 0; i<24;i++) {
        	for(int ii = 0; ii<17; ii++) {
        		if(buildingType[i][ii] == 9) {
        			outputFile.println(0);
        		}
        		else {
        			outputFile.println(buildingType[i][ii]);
        		}
        		
        	}
        }
        
        outputFile.println(tornadox);
        outputFile.println(tornadoy);
        System.out.println("Save complete");
        outputFile.close();
    }
    
    public static void load() throws IOException {
		Scanner inputFile = new Scanner(new File("save.txt"));
    	
		dialogue = inputFile.nextInt();
		gameState = inputFile.nextInt();
		mainscreenX = inputFile.nextInt();
		mainscreenY = inputFile.nextInt();
		storylineState = inputFile.nextInt();
		buttonPressed = inputFile.nextInt();
		money = inputFile.nextInt();
		population = inputFile.nextInt();
		for (int i = 0; i < 4; i++) {
	        cloudsUnlocked[i] = inputFile.next().equals("true");
	    }

		for(int i = 0; i<24;i++) {
        	for(int ii = 0; ii<17; ii++) {
        		buildingType[i][ii] = inputFile.nextInt();
        	}
        }
        
		tornadox = inputFile.nextInt();
		tornadoy = inputFile.nextInt();
        System.out.println("Load complete");
        inputFile.close();
    }
    

    // JPanel
    public Final_Project(){
        setPreferredSize(new Dimension(960, 718));
        this.setFocusable(true);
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
	        			g.drawImage(buildings[buildingType[i][ii]], mainscreenX+960+(i*40), mainscreenY+(ii*40), null);
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
        	}
        	
        		g.drawImage(background, 0, 0, null);
	        	g.drawImage(screen, mainscreenX, mainscreenY, null);
	        	g.drawImage(grass, mainscreenX+960, mainscreenY, null);
	        	
	        	
	        	//Spawning the buildings
	        	for(int i = 0; i < 24; i++) {
	        		for(int ii = 0; ii < 17; ii++) {
	        			g.drawImage(buildings[buildingType[i][ii]], mainscreenX+960+(i*40), mainscreenY+(ii*40), null);
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
        		g.drawImage(selection, mainscreenX+960+(lastxPressed*40), mainscreenY+(lastyPressed*40), null);
        		g.drawImage(sidePanel, 0, 0, null);
        		g.drawImage(buildings[buildingType[xPressed][yPressed]], 700, 30, null);
        		g.drawImage(sidePanelSelection[buildingType[xPressed][yPressed]], 0, 0, null);
        	
        }
        else if(gameState == 5) {//Credits
        	g.drawImage(blurredBackground, 0, 0, null);
        	g.drawImage(credit, 0, 0, null);
        }
        else if(gameState == 6) {//Reset
        	g.drawImage(blurredBackground, 0, 0, null);
        	g.drawImage(reset, 0, 0, null);
        }
        	
        else if(gameState == 7) {//Instructions
        	g.drawImage(instructions[instructionStep], 0, 0, null);
        	
        }
        
        if(buttonPressed > 0) {
        	g.drawImage(buttonShade, 0, 0, null);
        }
    }
    public static void main(String[] args) throws IOException{
    	
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
        buildings[2] = ImageIO.read(new File("house.png"));
        buildings[7] = ImageIO.read(new File("coffee.png"));
        sidePanel = ImageIO.read(new File("sidePanel.png"));
        selection = ImageIO.read(new File("selection.png"));
        buildings[3] = ImageIO.read(new File("roadHorizontal.png"));
        buildings[4] = ImageIO.read(new File("roadVertical.png"));
        lockedArea = ImageIO.read(new File("lockedArea.png"));
        buildings[8] = ImageIO.read(new File("townhall.png"));
        buildings[9] = ImageIO.read(new File("tornado.png"));
        buildings[10] = ImageIO.read(new File("tornadoWall.png"));
        buildings[22] = ImageIO.read(new File("reinforcedHouse.png"));
        buildings[27] = ImageIO.read(new File("reinforcedCoffeeShop.png"));
        buildings[42] = ImageIO.read(new File("house.png"));//kailynsHouse
        buildings[52] = ImageIO.read(new File("houseAbandoned.png"));
        buildings[57] = ImageIO.read(new File("coffeeShopAbandoned.png"));
        sidePanelSelection[0] = ImageIO.read(new File("emptyPanel.png"));
        sidePanelSelection[1] = ImageIO.read(new File("lockedArea.png"));
        sidePanelSelection[2] = ImageIO.read(new File("housePanel.png"));
        sidePanelSelection[7] = ImageIO.read(new File("coffeeShopPanel.png"));
        sidePanelSelection[22] = ImageIO.read(new File("reinforcedHousePanel.png"));
        sidePanelSelection[27] = ImageIO.read(new File("reinforcedCoffeeShopPanel.png"));
        sidePanelSelection[42] = ImageIO.read(new File("kailynsHouse.png"));
        sidePanelSelection[52] = ImageIO.read(new File("abandonedHousePanel.png"));
        sidePanelSelection[57] = ImageIO.read(new File("coffeeShopAbandonedPanel.png"));
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
        instructions[0] = ImageIO.read(new File("instruction1.png"));
        instructions[1] = ImageIO.read(new File("instruction2.png"));
        instructions[2] = ImageIO.read(new File("instruction3.png"));
        instructions[3] = ImageIO.read(new File("instruction4.png"));
        instructions[4] = ImageIO.read(new File("instruction5.png"));
        instructions[5] = ImageIO.read(new File("instruction6.png"));

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
        	
        }
    }
    
    
    public void mouseClicked(MouseEvent e) {
    	
    }
	public void mousePressed(MouseEvent e) {
		
		
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
			else if(e.getX() >= 516 && e.getX() <=672 && e.getY() >= 389 && e.getY() <= 592 && e.getButton()==1){
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
			//Instructions
			else if(buttonPressed == 2 && e.getX() >= 516 && e.getX() <=672 && e.getY() >= 389 && e.getY() <= 472 && e.getButton()==1){
				gameState = 7;
				instructionStep = 0;
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
			//Save
			else if(e.getX() >= 15 && e.getX() <=88 && e.getY() >= 678 && e.getY() <= 708 && e.getButton()==1){
        		try {
					save();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else if(e.getX() >= 100 && e.getX() <=173 && e.getY() >= 678 && e.getY() <= 708 && e.getButton()==1){
        		try {
					load();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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
			if(dialogue==9) {
				finishGame();
			}
			try {
				refreshDialogue();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			repaint();
		}
		else if(gameState ==3) {//Game
			if(storylineState == 5) {
				reset();
			}
			addMoney();
			if(e.getButton()==3) {
				try {
					lose();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				repaint();
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
	    				//EMPTY PANEL
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
	    					
	    					//IF THEY CLICK TO BUY A HORIZONTAL ROAD
	    					if(e.getX() >= 709 && e.getX() <=756 && e.getY() >= 235 && e.getY() <= 322 && e.getButton()==1) {
	    						if(money >= 200) {
		    						buildingType[lastxPressed][lastyPressed] = 3;
		    						sidePanelOpen = false;
		    						money -= 200;
	    						}
	    						else {
	    							System.out.println("no money");
	    						}
	    					}
	    					
	    					//IF THEY CLICK TO BUY A VERTICAL ROAD
	    					if(e.getX() >= 780 && e.getX() <=852 && e.getY() >= 235 && e.getY() <= 322 && e.getButton()==1) {
	    						if(money >= 200) {
		    						buildingType[lastxPressed][lastyPressed] = 4;
		    						sidePanelOpen = false;
		    						money -= 200;
	    						}
	    						else {
	    							System.out.println("no money");
	    						}
	    					}
	    					countGDP();
	    					
	    					//IF THEY CLICK TO BUY A TORNADO WALL
	    					if(e.getX() >= 865 && e.getX() <= 930 && e.getY() >= 235 && e.getY() <= 322 && e.getButton()==1) {
	    						if(money >= 20000) {
		    						buildingType[lastxPressed][lastyPressed] = 10;
		    						sidePanelOpen = false;
		    						money -= 20000;
	    						}
	    						else {
	    							System.out.println("no money");
	    						}
	    					}
	    					countGDP();
	    				}
	    				//CLOUDS PANEL
	    				else if(buildingType[lastxPressed][lastyPressed] == 1) {
	    					//IF THEY CLICK TO UNLOCK
	    					if(e.getX() >= 709 && e.getX() <=930 && e.getY() >= 132 && e.getY() <= 213 && e.getButton()==1) {//Panel button
	    						if(money >= 100000) {
	    							if(lastyPressed<3) {
		    							for(int i = 0; i<24; i++) {
		    								for(int ii = 0; ii<3; ii++) {
			    								buildingType[i][ii] = 0;
		    								}
		    							}
		    							cloudsUnlocked[0] = true;
	    							}
	    							else if(lastyPressed>13) {
		    							for(int i = 0; i<24; i++) {
		    								for(int ii = 13; ii<18; ii++) {
			    								buildingType[i][ii] = 0;
		    								}
		    							}
		    							cloudsUnlocked[2] = true;
	    							}
	    							
	    							else if(lastxPressed<7) {
		    							for(int i = 0; i<7; i++) {
		    								for(int ii = 0; ii<18; ii++) {
			    								buildingType[i][ii] = 0;
		    								}
		    							}
		    							cloudsUnlocked[3] = true;
	    							}
	    							
	    							else if(lastxPressed>20) {
		    							for(int i = 0; i>20; i++) {
		    								for(int ii = 0; ii<18; ii++) {
			    								buildingType[i][ii] = 0;
		    								}
		    							}
		    							cloudsUnlocked[1] = true;
	    							}
		    						
	    							
		    						sidePanelOpen = false;
		    						money -= 100000;
	    						}
	    						else {
	    							System.out.println("no money");
	    						}
	    					}
	    				}
	    				//ABANDONED PANEL
	    				else if(buildingType[lastxPressed][lastyPressed] > 50) {
	    					//Refurbish
	    					if(e.getX() >= 696 && e.getX() <=809 && e.getY() >= 432 && e.getY() <= 480 && e.getButton()==1) {
	    						if(money >= 500) {
	    							buildingType[lastxPressed][lastyPressed] -= 50;
		    						sidePanelOpen = false;
		    						money -= 500;
	    						}
	    						else {
	    							System.out.println("no money");
	    						}
	    					}
	    					//Demolish
	    					else if(e.getX() >= 815 && e.getX() <=925 && e.getY() >= 432 && e.getY() <= 480 && e.getButton()==1) {
    							buildingType[lastxPressed][lastyPressed] = 0;
	    						sidePanelOpen = false;
	    						money += 50;
	    					}
	    				}
	    				
	    				//House or Coffee Shop
	    				else if(buildingType[lastxPressed][lastyPressed] == 2 || buildingType[lastxPressed][lastyPressed] == 7){
	    					//Refurbish
	    					if(e.getX() >= 696 && e.getX() <=809 && e.getY() >= 432 && e.getY() <= 480 && e.getButton()==1) {
	    						if(money >= 1000) {
	    							buildingType[lastxPressed][lastyPressed] += 20;
		    						sidePanelOpen = false;
		    						money -= 1000;
	    						}
	    						else {
	    							System.out.println("no money");
	    						}
	    					}
	    					//Demolish
	    					else if(e.getX() >= 815 && e.getX() <=925 && e.getY() >= 432 && e.getY() <= 480 && e.getButton()==1) {
    							buildingType[lastxPressed][lastyPressed] = 0;
	    						sidePanelOpen = false;
	    						money += 100;
	    					}
	    				}
	    				
	    				//Reinforced House or Coffee Shop
	    				else if(buildingType[lastxPressed][lastyPressed] == 22 || buildingType[lastxPressed][lastyPressed] == 27){
	    					
	    					//Demolish
	    					if(e.getX() >= 815 && e.getX() <=925 && e.getY() >= 432 && e.getY() <= 480 && e.getButton()==1) {
    							buildingType[lastxPressed][lastyPressed] = 0;
	    						sidePanelOpen = false;
	    						money += 100;
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
		else if(gameState ==7) {
			instructionStep++;
			if(instructionStep>5) {
				gameState = 1;
			}
		}
		buttonPressed = 0;
	}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {
		
	}
	
	public static void countGDP(){
		countPopulation();
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
            	if(buildingType[i][ii] == 22) {
            		gdp += 2000;
            	}
            	if(buildingType[i][ii] == 27) {
            		gdp += 2000;
            	}
            	if(buildingType[i][ii] == 42) {
            		gdp += 2000;
            	}
            	if(buildingType[i][ii] == 52) {
            		gdp += 500;
            	}
            	if(buildingType[i][ii] == 57) {
            		gdp += 500;
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
	
	public static void lose() throws IOException {
		dialogue = 5;
		refreshDialogue();
		gameState = 2;
		lastGameState = 3;
		storylineState = 2;
		sidePanelOpen = false;
		refreshDialogue();
		
	}
	
	public static void refreshDialogue() throws IOException{
		String fileName = dialogue + ".png";
    	dialogue0 = ImageIO.read(new File(fileName));
	}
	public static void countPopulation(){
		population = 0;
        for(int i = 0; i<24; i++) {
        	for(int ii = 0; ii<17; ii++) {
        		if(buildingType[i][ii] == 2) {
            		population += 3;
            	}
            	if(buildingType[i][ii] == 22) {
            		population += 5;
            	}
            	if(buildingType[i][ii] == 42) {
            		population += 1;
            	}
            }
        }
        
        if(population<4) {
        	dialogue = 5;
			gameState = 2;
			lastGameState = 3;
			storylineState = 2;
        }
        
        //WIN
        if(population>1000 && gdp>400000) {
        	dialogue = 7;
			gameState = 2;
			lastGameState = 3;
			storylineState = 4;
        }
        
        
	}

	
	public static void addMoney(){
        for(int i = 0; i<24; i++) {
        	for(int ii = 0; ii<17; ii++) {
        		if(buildingType[i][ii] == 7) {
            		money += (int)(8*Math.random());
            	}
            	if(buildingType[i][ii] == 27) {
            		money += (int)(15*Math.random());
            	}
            	if(buildingType[i][ii] == 57) {
            		money -= (int)(6*Math.random());
            	}
            }
        }
        
	}

	public static void abandon() {
		
		double townHallMultiplier = 0.995;
		
		//Check for town hall
		for(int i = 0; i<24; i++) {
			for(int ii = 0; ii<17; ii++) {
	    		if(buildingType[i][ii] == 8 && (buildingType[i+1][ii] == 3 || buildingType[i+1][ii] == 4 || buildingType[i-1][ii] == 3 || buildingType[i-1][ii] == 4 || buildingType[i][ii+1] == 3 || buildingType[i][ii+1] == 4 || buildingType[i][ii-1] == 3 || buildingType[i][ii-1] == 4)) {
	    			townHallMultiplier = 0.999;
	    		}
	    	}
		}
		for(int i = 0; i<24; i++) {
			for(int ii = 0; ii<17; ii++) {
	    		if(buildingType[i][ii] == 2 || buildingType[i][ii] == 7) {
	    			//Road Check + Randomizer
	    			if(buildingType[i+1][ii] != 3 && buildingType[i+1][ii] != 4 && buildingType[i-1][ii] != 3 && buildingType[i-1][ii] != 4 && buildingType[i][ii+1] != 3 && buildingType[i][ii+1] != 4 && buildingType[i][ii-1] != 3 && buildingType[i][ii-1] != 4 && Math.random()>0.001 && Math.random()>0.999999 && Math.random()>townHallMultiplier) {
	    				buildingType[i][ii] += 50;
	    			}
	    			if(Math.random()>0.0001 && Math.random()>0.999999 && Math.random()>townHallMultiplier) {
	    				buildingType[i][ii] += 50;
	    			}
	    		}
	    	}
		}
	}

	public static void finishGame() {
		gameState = 3;
		storylineState = 5;
		
		for(int i = 0; i<24;i++) {
			for(int ii = 0; ii<17;ii++) {
				buildingType[i][ii] = 9;
				
			}
		}
	}
	
	public static void naturalDisaster() {
		abandon();
		buildingType[tornadox][tornadoy] = 9;
		if(Math.random()>0.001 && Math.random()>0.99999 && !tornadoActive && gameState == 3) {
			tornadoActive = true;
			tornado();
		}
		
	}
	
	public static void tornado() {
		int tornadox = (int) Math.round(Math.random()*23);
		int tornadoy = (int) Math.round(Math.random()*16);
		int tornadoDirection = (int)Math.round(Math.random()*4);
		System.out.println("a " + tornadoDirection + " direction tornado is forming");
		boolean destroy = false;
		int lastBuildingType = 0;
		countGDP();
		buildingType[tornadox][tornadoy] = 9;
		while(tornadoActive) {
			int movement = (int) (Math.random()*30000000);
			//Certain Direction
			if(movement>10 && movement <20) {
				movement = tornadoDirection + 1;
			}
			//Random Direction (or certain if it landed on certain)
			if(movement==2 && tornadox > 0) {
				tornadox -= 1;
				buildingType[tornadox+1][tornadoy] = 0;
				if(!destroy) {
					buildingType[tornadox+1][tornadoy] = lastBuildingType;
				}
				lastBuildingType = buildingType[tornadox][tornadoy];
				buildingType[tornadox][tornadoy] = 9;
				addMoney();
			}
			else if(movement == 3 && tornadox < 24) {
				tornadox += 1;
				buildingType[tornadox-1][tornadoy] = 0;
				if(!destroy) {
					buildingType[tornadox-1][tornadoy] = lastBuildingType;
				}
				lastBuildingType = buildingType[tornadox][tornadoy];
				buildingType[tornadox][tornadoy] = 9;
				addMoney();
			}
			else if(movement == 4 && tornadoy > 0) {
				tornadoy -= 1;
				buildingType[tornadox][tornadoy+1] = 0;
				if(!destroy) {
					buildingType[tornadox][tornadoy+1] = lastBuildingType;
				}
				lastBuildingType = buildingType[tornadox][tornadoy];
				buildingType[tornadox][tornadoy] = 9;
				addMoney();
			}
			else if(movement == 5 && tornadoy < 17) {
				tornadoy += 1;
				buildingType[tornadox][tornadoy-1] = 0;
				if(!destroy) {
					buildingType[tornadox][tornadoy-1] = lastBuildingType;
				}
				lastBuildingType = buildingType[tornadox][tornadoy];
				buildingType[tornadox][tornadoy] = 9;
				addMoney();
			}
			
			
			if(lastBuildingType == 8) {
				dialogue = 5;
				gameState = 2;
				lastGameState = 3;
				storylineState = 2;
			}
			if(lastBuildingType == 1 || Math.random()<0.5) {
				destroy = false;
			}
			//Reinforced Buildings
			else if(lastBuildingType > 20 & lastBuildingType < 30 && Math.random()<0.2) {
				destroy = true;
			}
			else {
				destroy = true;
			}
			//Cancel Tornado
			abandon();
			if(tornadox<=0 || tornadox == 24 || tornadoy <= 0 || tornadoy == 17 || (lastBuildingType==10 && Math.random()<0.8)) {
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
            
            
        }
    }
}