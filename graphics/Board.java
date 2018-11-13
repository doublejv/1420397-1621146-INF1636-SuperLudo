package graphics;

import java.awt.*;
import java.awt.geom.*;
import java.io.*;

import javax.imageio.*;
import javax.swing.*;

import systems.*;

public class Board extends JPanel {

	//to remove warning
	private static final long serialVersionUID = -8919768790110412092L;
	
	//SHOW GRID LETTERS AND NUMBERS
	public final static boolean debugMode = true;
	
	//TEAM COLORS
	public final static Color redColor = new Color(198, 67, 49);
	public final static Color greenColor = new Color(85, 163, 83);
	public final static Color yellowColor = new Color(244, 217, 65);
	public final static Color blueColor = new Color(64, 128, 239);
	
	//RADIUS
	public final static int radiusPawn = 8;
	public final static int radiusWhiteCircle = 25;
	
	//IMAGES POSITION
	public static int img_diceX = 670;
	public static int img_diceY = 275;
	public static int img_diceMinX = 734;
	public static int img_diceMinY = 309;
	
	//IMAGES DIRECTORY
	private final static String dice0Dir = "Dado0.png";
	private final static String dice1Dir = "Dado1.png";
	private final static String dice2Dir = "Dado2.png";
	private final static String dice3Dir = "Dado3.png";
	private final static String dice4Dir = "Dado4.png";
	private final static String dice5Dir = "Dado5.png";
	private final static String dice6Dir = "Dado6.png";
	
	private final static String dice1MinDir = "Dado1Min.png";
	private final static String dice2MinDir = "Dado2Min.png";
	private final static String dice3MinDir = "Dado3Min.png";
	private final static String dice4MinDir = "Dado4Min.png";
	private final static String dice5MinDir = "Dado5Min.png";
	private final static String dice6MinDir = "Dado6Min.png";
	
	//IMAGES ARRAY
	private static Image[] diceImgs = new Image[7];	
	private static Image[] diceMinImgs = new Image[7];
	
	{
		try {
			
			diceImgs[0] = ImageIO.read(new File(dice0Dir));
			diceImgs[1] = ImageIO.read(new File(dice1Dir));
			diceImgs[2] = ImageIO.read(new File(dice2Dir));
			diceImgs[3] = ImageIO.read(new File(dice3Dir));
			diceImgs[4] = ImageIO.read(new File(dice4Dir));
			diceImgs[5] = ImageIO.read(new File(dice5Dir));
			diceImgs[6] = ImageIO.read(new File(dice6Dir));
			
			diceMinImgs[0] = null;
			diceMinImgs[1] = ImageIO.read(new File(dice1MinDir));
			diceMinImgs[2] = ImageIO.read(new File(dice2MinDir));
			diceMinImgs[3] = ImageIO.read(new File(dice3MinDir));
			diceMinImgs[4] = ImageIO.read(new File(dice4MinDir));
			diceMinImgs[5] = ImageIO.read(new File(dice5MinDir));
			diceMinImgs[6] = ImageIO.read(new File(dice6MinDir));
			
		} catch (IOException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
    public Board() {
    	setLayout(null);
        setBorder(BorderFactory.createLineBorder(Color.black));
    }
    
    public void paintComponent(Graphics g) {
    	
    	super.paintComponent(g);
    	
    	Graphics2D g2d = (Graphics2D) g;
    	
		drawWhiteGrid(g2d);		
		drawSafePlaces(g2d);		
		drawRedPolygons(g2d);
		drawGreenPolygons(g2d);		
		drawYellowPolygons(g2d);		
		drawBluePolygons(g2d);
		
		drawRedPawns(g2d);
		drawGreenPawns(g2d);
		drawYellowPawns(g2d);
		drawBluePawns(g2d);
		
		drawTeamOnTurnSquare(g2d);
		drawDiceImg(g2d);
		
		drawLastTeamOnTurnSquare(g2d);
		drawLastDiceImg(g2d);
		
		debugMode(g2d);
	}
        
    private void drawRedPawns (Graphics2D g2d) {
    	    	
    	//se o time vermelho existir
		if (CtrlGame.getCtrlGame().getGameTeam("redTeam") != null) {	
			
			//percorre o array de peoes do time
			for (int i = 0; i < 4; i++) { 
				
				//se o pe�o existir
				if (CtrlGame.getCtrlGame().checkTeamPawn("redTeam", i)) { 
					
					//se n�o tiver outro peao nessa posi��o
					if (CtrlGame.getCtrlGame().checkPawnPositionVacancy("redTeam", i)) { 
					
						int centerX = CtrlGame.getCtrlGame().getPawnPositionX("redTeam", i);
						int centerY = CtrlGame.getCtrlGame().getPawnPositionY("redTeam", i);
				    	
				    	Ellipse2D circ = new Ellipse2D.Double();
						circ.setFrameFromCenter(centerX, centerY, centerX + radiusPawn, centerY + radiusPawn);
						g2d.setPaint(Color.RED);
						g2d.fill(circ);
						g2d.setPaint(Color.BLACK);
						g2d.draw(circ);
						g2d.setPaint(Color.WHITE);
						drawPawnIdString(centerX, centerY, i, g2d);
					}
					
					//se tiver outro pe�o nessa posi��o (barreira)
					else if (CtrlGame.getCtrlGame().checkPawnPositionBarrier("redTeam", i)) {
						
						int centerX = CtrlGame.getCtrlGame().getPawnPositionX("redTeam", i);
						int centerY = CtrlGame.getCtrlGame().getPawnPositionY("redTeam", i);
						
				    	Ellipse2D circExt = new Ellipse2D.Double();
				    	circExt.setFrameFromCenter(centerX, centerY, centerX + radiusPawn+6, centerY + radiusPawn+6);
						g2d.setPaint(Color.RED);
						g2d.fill(circExt);
						
						g2d.setPaint(Color.BLACK);
						g2d.draw(circExt);
						
						Ellipse2D circWhite = new Ellipse2D.Double();
						circWhite.setFrameFromCenter(centerX, centerY, centerX + radiusPawn+2, centerY + radiusPawn+2);
						g2d.setPaint(Color.WHITE);
						g2d.fill(circWhite);
						
						g2d.setPaint(Color.BLACK);
						g2d.draw(circWhite);
						
						Ellipse2D circ = new Ellipse2D.Double();
						circ.setFrameFromCenter(centerX, centerY, centerX + radiusPawn-1, centerY + radiusPawn-1);
						g2d.setPaint(Color.RED);
						g2d.fill(circ);
						
						g2d.setPaint(Color.BLACK);
						g2d.draw(circ);
					}
					
					//se tiver outro pe�o nessa posi��o (e nao for do time vermelho)
					else if (CtrlGame.getCtrlGame().checkPawnPositionEnemy("redTeam", i)) {
						
						int centerX = CtrlGame.getCtrlGame().getPawnPositionX("redTeam", i);
						int centerY = CtrlGame.getCtrlGame().getPawnPositionY("redTeam", i);
						
				    	Ellipse2D circExt = new Ellipse2D.Double();
				    	circExt.setFrameFromCenter(centerX, centerY, centerX + radiusPawn+6, centerY + radiusPawn+6);
						g2d.setPaint(Color.RED);
						g2d.fill(circExt);
						
						g2d.setPaint(Color.BLACK);
						g2d.draw(circExt);
						
						switch (CtrlGame.getCtrlGame().comparePawnTeam("redTeam", i)) {
							case "YELLOW": g2d.setPaint(Color.YELLOW); break;
							case "GREEN": g2d.setPaint(Color.GREEN); break;
							case "BLUE": g2d.setPaint(Color.BLUE); break;
							default: System.out.println("comparePawnTeam default case triggered!\n"); g2d.setPaint(Color.BLACK); break;
						}
						
						Ellipse2D circInt = new Ellipse2D.Double();
						circInt.setFrameFromCenter(centerX, centerY, centerX + radiusPawn-1, centerY + radiusPawn-1);
						g2d.fill(circInt);
						
						g2d.setPaint(Color.BLACK);
						g2d.draw(circInt);
					}
				}	
			}
		}
    }
    
    private void drawGreenPawns (Graphics2D g2d) {
    	
    	//se o time verde existir
		if (CtrlGame.getCtrlGame().getGameTeam("greenTeam") != null) {	
			
			//percorre o array de peoes do time
			for (int i = 0; i < 4; i++) { 
				
				//se o pe�o existir
				if (CtrlGame.getCtrlGame().checkTeamPawn("greenTeam", i)) { 
					
					//se n�o tiver outro peao nessa posi��o
					if (CtrlGame.getCtrlGame().checkPawnPositionVacancy("greenTeam", i)) { 
					
						int centerX = CtrlGame.getCtrlGame().getPawnPositionX("greenTeam", i);
						int centerY = CtrlGame.getCtrlGame().getPawnPositionY("greenTeam", i);
				    	
				    	Ellipse2D circ = new Ellipse2D.Double();
						circ.setFrameFromCenter(centerX, centerY, centerX + radiusPawn, centerY + radiusPawn);
						g2d.setPaint(Color.GREEN);
						g2d.fill(circ);
						g2d.setPaint(Color.BLACK);
						g2d.draw(circ);
						g2d.setPaint(Color.BLACK);
						drawPawnIdString(centerX, centerY, i, g2d);
					}
					
					//se tiver outro pe�o nessa posi��o (barreira)
					else if (CtrlGame.getCtrlGame().checkPawnPositionBarrier("greenTeam", i)){
						
						int centerX = CtrlGame.getCtrlGame().getPawnPositionX("greenTeam", i);
						int centerY = CtrlGame.getCtrlGame().getPawnPositionY("greenTeam", i);
						
				    	Ellipse2D circExt = new Ellipse2D.Double();
				    	circExt.setFrameFromCenter(centerX, centerY, centerX + radiusPawn+6, centerY + radiusPawn+6);
						g2d.setPaint(Color.GREEN);
						g2d.fill(circExt);
						
						g2d.setPaint(Color.BLACK);
						g2d.draw(circExt);
						
						Ellipse2D circWhite = new Ellipse2D.Double();
						circWhite.setFrameFromCenter(centerX, centerY, centerX + radiusPawn+2, centerY + radiusPawn+2);
						g2d.setPaint(Color.WHITE);
						g2d.fill(circWhite);
						
						g2d.setPaint(Color.BLACK);
						g2d.draw(circWhite);
						
						Ellipse2D circ = new Ellipse2D.Double();
						circ.setFrameFromCenter(centerX, centerY, centerX + radiusPawn-1, centerY + radiusPawn-1);
						g2d.setPaint(Color.GREEN);
						g2d.fill(circ);
						
						g2d.setPaint(Color.BLACK);
						g2d.draw(circ);
					}
					
					//se tiver outro pe�o nessa posi��o (e nao for do time verde)
					else if (CtrlGame.getCtrlGame().checkPawnPositionEnemy("greenTeam", i)) {
						
						int centerX = CtrlGame.getCtrlGame().getPawnPositionX("greenTeam", i);
						int centerY = CtrlGame.getCtrlGame().getPawnPositionY("greenTeam", i);
						
				    	Ellipse2D circExt = new Ellipse2D.Double();
				    	circExt.setFrameFromCenter(centerX, centerY, centerX + radiusPawn+6, centerY + radiusPawn+6);
						g2d.setPaint(Color.GREEN);
						g2d.fill(circExt);
						
						g2d.setPaint(Color.BLACK);
						g2d.draw(circExt);
						
						switch (CtrlGame.getCtrlGame().comparePawnTeam("greenTeam", i)) {
							case "RED": g2d.setPaint(Color.RED); break;
							case "YELLOW": g2d.setPaint(Color.YELLOW); break;
							case "BLUE": g2d.setPaint(Color.BLUE); break;
							default: System.out.println("comparePawnTeam default case triggered!\n"); g2d.setPaint(Color.BLACK); break;
						}
						
						Ellipse2D circInt = new Ellipse2D.Double();
						circInt.setFrameFromCenter(centerX, centerY, centerX + radiusPawn-1, centerY + radiusPawn-1);
						g2d.fill(circInt);
						
						g2d.setPaint(Color.BLACK);
						g2d.draw(circInt);
					}
				}	
			}
		}
    }
    
    private void drawYellowPawns(Graphics2D g2d) {
    	
    	//se o time amarelo existir
		if (CtrlGame.getCtrlGame().getGameTeam("yellowTeam") != null) {	
			
			//percorre o array de peoes do time
			for (int i = 0; i < 4; i++) { 
				
				//se o pe�o existir
				if (CtrlGame.getCtrlGame().checkTeamPawn("yellowTeam", i)) { 
					
					//se n�o tiver outro peao nessa posi��o
					if (CtrlGame.getCtrlGame().checkPawnPositionVacancy("yellowTeam", i)) { 
					
						int centerX = CtrlGame.getCtrlGame().getPawnPositionX("yellowTeam", i);
						int centerY = CtrlGame.getCtrlGame().getPawnPositionY("yellowTeam", i);
				    	
				    	Ellipse2D circ = new Ellipse2D.Double();
						circ.setFrameFromCenter(centerX, centerY, centerX + radiusPawn, centerY + radiusPawn);
						g2d.setPaint(Color.YELLOW);
						g2d.fill(circ);
						g2d.setPaint(Color.BLACK);
						g2d.draw(circ);
						g2d.setPaint(Color.BLACK);
						drawPawnIdString(centerX, centerY, i, g2d);
					}
					
					//se tiver outro pe�o nessa posi��o (barreira)
					else if (CtrlGame.getCtrlGame().checkPawnPositionBarrier("yellowTeam", i)) {
						
						int centerX = CtrlGame.getCtrlGame().getPawnPositionX("yellowTeam", i);
						int centerY = CtrlGame.getCtrlGame().getPawnPositionY("yellowTeam", i);
						
				    	Ellipse2D circExt = new Ellipse2D.Double();
				    	circExt.setFrameFromCenter(centerX, centerY, centerX + radiusPawn+6, centerY + radiusPawn+6);
						g2d.setPaint(Color.YELLOW);
						g2d.fill(circExt);
						
						g2d.setPaint(Color.BLACK);
						g2d.draw(circExt);
						
						Ellipse2D circWhite = new Ellipse2D.Double();
						circWhite.setFrameFromCenter(centerX, centerY, centerX + radiusPawn+2, centerY + radiusPawn+2);
						g2d.setPaint(Color.WHITE);
						g2d.fill(circWhite);
						
						g2d.setPaint(Color.BLACK);
						g2d.draw(circWhite);
						
						Ellipse2D circ = new Ellipse2D.Double();
						circ.setFrameFromCenter(centerX, centerY, centerX + radiusPawn-1, centerY + radiusPawn-1);
						g2d.setPaint(Color.YELLOW);
						g2d.fill(circ);
						
						g2d.setPaint(Color.BLACK);
						g2d.draw(circ);
					}
					
					//se tiver outro pe�o nessa posi��o (e nao for do time amarelo)
					else if (CtrlGame.getCtrlGame().checkPawnPositionEnemy("yellowTeam", i)) {
						
						int centerX = CtrlGame.getCtrlGame().getPawnPositionX("yellowTeam", i);
						int centerY = CtrlGame.getCtrlGame().getPawnPositionY("yellowTeam", i);
						
				    	Ellipse2D circExt = new Ellipse2D.Double();
				    	circExt.setFrameFromCenter(centerX, centerY, centerX + radiusPawn+6, centerY + radiusPawn+6);
						g2d.setPaint(Color.YELLOW);
						g2d.fill(circExt);
						
						g2d.setPaint(Color.BLACK);
						g2d.draw(circExt);
						
						switch (CtrlGame.getCtrlGame().comparePawnTeam("yellowTeam", i)) {
							case "RED": g2d.setPaint(Color.RED); break;
							case "GREEN": g2d.setPaint(Color.GREEN); break;
							case "BLUE": g2d.setPaint(Color.BLUE); break;
							default: System.out.println("comparePawnTeam default case triggered!\n"); g2d.setPaint(Color.BLACK); break;
						}
						
						Ellipse2D circInt = new Ellipse2D.Double();
						circInt.setFrameFromCenter(centerX, centerY, centerX + radiusPawn-1, centerY + radiusPawn-1);
						g2d.fill(circInt);
						
						g2d.setPaint(Color.BLACK);
						g2d.draw(circInt);
					}
				}	
			}
		}
    }
    
    private void drawBluePawns(Graphics2D g2d) {
    	
    	//se o time azul existir
		if (CtrlGame.getCtrlGame().getGameTeam("blueTeam") != null) {	
			
			//percorre o array de peoes do time
			for (int i = 0; i < 4; i++) { 
				
				//se o pe�o existir
				if (CtrlGame.getCtrlGame().checkTeamPawn("blueTeam", i)) { 
					
					//se n�o tiver outro peao nessa posi��o
					if (CtrlGame.getCtrlGame().checkPawnPositionVacancy("blueTeam", i)) { 
					
						int centerX = CtrlGame.getCtrlGame().getPawnPositionX("blueTeam", i);
						int centerY = CtrlGame.getCtrlGame().getPawnPositionY("blueTeam", i);
				    	
				    	Ellipse2D circ = new Ellipse2D.Double();
						circ.setFrameFromCenter(centerX, centerY, centerX + radiusPawn, centerY + radiusPawn);
						g2d.setPaint(Color.BLUE);
						g2d.fill(circ);
						g2d.setPaint(Color.BLACK);
						g2d.draw(circ);
						g2d.setPaint(Color.WHITE);
						drawPawnIdString(centerX, centerY, i, g2d);
					}
					
					//se tiver outro pe�o nessa posi��o (barreira)
					else if (CtrlGame.getCtrlGame().checkPawnPositionBarrier("blueTeam", i)){
						
						int centerX = CtrlGame.getCtrlGame().getPawnPositionX("blueTeam", i);
						int centerY = CtrlGame.getCtrlGame().getPawnPositionY("blueTeam", i);
						
				    	Ellipse2D circExt = new Ellipse2D.Double();
				    	circExt.setFrameFromCenter(centerX, centerY, centerX + radiusPawn+6, centerY + radiusPawn+6);
						g2d.setPaint(Color.BLUE);
						g2d.fill(circExt);
						
						g2d.setPaint(Color.BLACK);
						g2d.draw(circExt);
						
						Ellipse2D circWhite = new Ellipse2D.Double();
						circWhite.setFrameFromCenter(centerX, centerY, centerX + radiusPawn+2, centerY + radiusPawn+2);
						g2d.setPaint(Color.WHITE);
						g2d.fill(circWhite);
						
						g2d.setPaint(Color.BLACK);
						g2d.draw(circWhite);
						
						Ellipse2D circ = new Ellipse2D.Double();
						circ.setFrameFromCenter(centerX, centerY, centerX + radiusPawn-1, centerY + radiusPawn-1);
						g2d.setPaint(Color.BLUE);
						g2d.fill(circ);
						
						g2d.setPaint(Color.BLACK);
						g2d.draw(circ);
					}
					
					//se tiver outro pe�o nessa posi��o (e nao for do time azul)
					else if (CtrlGame.getCtrlGame().checkPawnPositionEnemy("blueTeam", i)) {
						
						int centerX = CtrlGame.getCtrlGame().getPawnPositionX("blueTeam", i);
						int centerY = CtrlGame.getCtrlGame().getPawnPositionY("blueTeam", i);
						
				    	Ellipse2D circExt = new Ellipse2D.Double();
				    	circExt.setFrameFromCenter(centerX, centerY, centerX + radiusPawn+6, centerY + radiusPawn+6);
						g2d.setPaint(Color.BLUE);
						g2d.fill(circExt);
						
						g2d.setPaint(Color.BLACK);
						g2d.draw(circExt);
						
						switch (CtrlGame.getCtrlGame().comparePawnTeam("blueTeam", i)) {
						case "RED": g2d.setPaint(Color.RED); break;
						case "GREEN": g2d.setPaint(Color.GREEN); break;
						case "YELLOW": g2d.setPaint(Color.YELLOW); break;
						default: System.out.println("comparePawnTeam default case triggered!\n"); g2d.setPaint(Color.BLACK); break;
					}
						
						Ellipse2D circInt = new Ellipse2D.Double();
						circInt.setFrameFromCenter(centerX, centerY, centerX + radiusPawn-1, centerY + radiusPawn-1);
						g2d.fill(circInt);
						
						g2d.setPaint(Color.BLACK);
						g2d.draw(circInt);
					}
				}	
			}
		}
    }
    
    private void drawSafePlaces (Graphics2D g2d) {
    	
    	Rectangle2D safePlace;
    	
    	//SAFE PLACE 1
		safePlace = new Rectangle2D.Double(240,40,40,40);
		g2d.setPaint(Color.BLACK); 
		g2d.fill(safePlace); 
		g2d.setPaint(Color.BLACK); 
		g2d.draw(safePlace);
		
		//SAFE PLACE 2
		safePlace = new Rectangle2D.Double(40,320,40,40);
		g2d.setPaint(Color.BLACK); 
		g2d.fill(safePlace); 
		g2d.setPaint(Color.BLACK); 
		g2d.draw(safePlace);
		
		//SAFE PLACE 3
		safePlace = new Rectangle2D.Double(520,240,40,40);
		g2d.setPaint(Color.BLACK); 
		g2d.fill(safePlace); 
		g2d.setPaint(Color.BLACK); 
		g2d.draw(safePlace);
		
		//SAFE PLACE 4
		safePlace = new Rectangle2D.Double(320,520,40,40);
		g2d.setPaint(Color.BLACK); 
		g2d.fill(safePlace); 
		g2d.setPaint(Color.BLACK); 
		g2d.draw(safePlace);
    }
    
    private void drawWhiteGrid (Graphics2D g2d) {
		for (int y = 0; y < 15; y++) {
			for (int x = 0; x < 15; x++) {
				Rectangle2D white = new Rectangle2D.Double(40 * x, 40 * y, 40, 40);
				g2d.setPaint(Color.WHITE); 
				g2d.fill(white); 
				g2d.setPaint(Color.BLACK); 
				g2d.draw(white);
			}
		}
    }
    
    private void drawRedPolygons (Graphics2D g2d) {
    	
    	Ellipse2D circ;
    	
    	//RED HOME
		Rectangle2D redHome = new Rectangle2D.Double(0,0,240,240);
		g2d.setPaint(redColor);
		g2d.fill(redHome);
		g2d.setPaint(Color.BLACK);
		g2d.draw(redHome);
		
		//WHITE CIRCLES OF RED HOME
		circ = new Ellipse2D.Double();
		circ.setFrameFromCenter(60, 60, 60 + radiusWhiteCircle,60 + radiusWhiteCircle);
		g2d.setPaint(Color.WHITE);
		g2d.fill(circ);
		g2d.setPaint(Color.BLACK);
		g2d.draw(circ);
		
		circ = new Ellipse2D.Double();
		circ.setFrameFromCenter(180, 60, 180 + radiusWhiteCircle, 60 + radiusWhiteCircle);
		g2d.setPaint(Color.WHITE);
		g2d.fill(circ);
		g2d.setPaint(Color.BLACK);
		g2d.draw(circ);
		
		circ = new Ellipse2D.Double();
		circ.setFrameFromCenter(60, 180, 60 + radiusWhiteCircle, 180 + radiusWhiteCircle);
		g2d.setPaint(Color.WHITE);
		g2d.fill(circ);
		g2d.setPaint(Color.BLACK);
		g2d.draw(circ);
		
		circ = new Ellipse2D.Double();
		circ.setFrameFromCenter(180, 180, 180 + radiusWhiteCircle, 180 + radiusWhiteCircle);
		g2d.setPaint(Color.WHITE);
		g2d.fill(circ);
		g2d.setPaint(Color.BLACK);
		g2d.draw(circ);
		
		//RED WAY
		Rectangle2D redWay = new Rectangle2D.Double(40,240,40,40);
		g2d.setPaint(redColor); 
		g2d.fill(redWay); 
		g2d.setPaint(Color.BLACK); 
		g2d.draw(redWay);
		for (int i = 1; i <= 5; i++) {
			redWay = new Rectangle2D.Double(40.0*i,280,40,40);
			g2d.setPaint(redColor); 
			g2d.fill(redWay); 
			g2d.setPaint(Color.BLACK); 
			g2d.draw(redWay);
		}
		
		//RED TRIANGLE
		int xPointsRed[] = {240,300,240};
		int yPointsRed[] = {240,300,360};
		g2d.setPaint(redColor);
		g2d.fillPolygon(xPointsRed, yPointsRed, 3);
		g2d.setPaint(Color.BLACK);
		g2d.drawPolygon(xPointsRed, yPointsRed, 3);
		
		//WHITE TRIANGLE
		int xPointsWhite[] = {50,70,50};
		int yPointsWhite[] = {250,260,270};
		g2d.setPaint(Color.WHITE);
		g2d.fillPolygon(xPointsWhite, yPointsWhite, 3);
		g2d.setPaint(Color.BLACK);
		g2d.drawPolygon(xPointsWhite, yPointsWhite, 3);
    }
    
    private void drawGreenPolygons (Graphics2D g2d) {
    	
    	Ellipse2D circ;
    	
    	//GREEN HOME
		Rectangle2D greenHome = new Rectangle2D.Double(360,0,240,240);
		g2d.setPaint(greenColor); 
		g2d.fill(greenHome); 
		g2d.setPaint(Color.BLACK); 
		g2d.draw(greenHome);
		
		//WHITE CIRCLES OF GREEN HOME
		circ = new Ellipse2D.Double();
		circ.setFrameFromCenter(420,180,420+radiusWhiteCircle,180+radiusWhiteCircle);
		g2d.setPaint(Color.WHITE);
		g2d.fill(circ);
		g2d.setPaint(Color.BLACK);
		g2d.draw(circ);
		
		circ = new Ellipse2D.Double();
		circ.setFrameFromCenter(420,60,420+radiusWhiteCircle,60+radiusWhiteCircle);
		g2d.setPaint(Color.WHITE);
		g2d.fill(circ);
		g2d.setPaint(Color.BLACK);
		g2d.draw(circ);
		
		circ = new Ellipse2D.Double();
		circ.setFrameFromCenter(540,180,540+radiusWhiteCircle,180+radiusWhiteCircle);
		g2d.setPaint(Color.WHITE);
		g2d.fill(circ);
		g2d.setPaint(Color.BLACK);
		g2d.draw(circ);
		
		circ = new Ellipse2D.Double();
		circ.setFrameFromCenter(540,60,540+radiusWhiteCircle,60+radiusWhiteCircle);
		g2d.setPaint(Color.WHITE);
		g2d.fill(circ);
		g2d.setPaint(Color.BLACK);
		g2d.draw(circ);
		
		//GREEN WAY
		Rectangle2D greenWay = new Rectangle2D.Double(320,40,40,40);
		g2d.setPaint(greenColor); 
		g2d.fill(greenWay); 
		g2d.setPaint(Color.BLACK); 
		g2d.draw(greenWay);
		for (int i = 1; i <= 5; i++) {
			greenWay = new Rectangle2D.Double(280, 40 * i, 40, 40);
			g2d.setPaint(greenColor); 
			g2d.fill(greenWay); 
			g2d.setPaint(Color.BLACK); 
			g2d.draw(greenWay);
		}
		
		//GREEN TRIANGLE
		int xPointsGreen[] = {240,300,360};
		int yPointsGreen[] = {240,300,240};
		g2d.setPaint(new Color(85, 163, 83));
		g2d.fillPolygon(xPointsGreen, yPointsGreen, 3);
		g2d.setPaint(Color.BLACK);
		g2d.drawPolygon(xPointsGreen, yPointsGreen, 3);
		
		//WHITE TRIANGLE
		int xPointsWhite[] = {330,350,340};
		int yPointsWhite[] = {50,50,70};
		g2d.setPaint(Color.WHITE);
		g2d.fillPolygon(xPointsWhite, yPointsWhite, 3);
		g2d.setPaint(Color.BLACK);
		g2d.drawPolygon(xPointsWhite, yPointsWhite, 3);
    }
    
    private void drawYellowPolygons (Graphics2D g2d) {
    	
    	Ellipse2D circ;
    	
    	//YELLOW HOME
		Rectangle2D yellowHome = new Rectangle2D.Double(360,360,240,240);
		g2d.setPaint(yellowColor);	
		g2d.fill(yellowHome); 
		g2d.setPaint(Color.BLACK); 
		g2d.draw(yellowHome);
		
		//WHITE CIRCLES OF YELLOW HOME
		circ = new Ellipse2D.Double();
		circ.setFrameFromCenter(420,540,420+radiusWhiteCircle,540+radiusWhiteCircle);
		g2d.setPaint(Color.WHITE);
		g2d.fill(circ);
		g2d.setPaint(Color.BLACK);
		g2d.draw(circ);
		
		circ = new Ellipse2D.Double();
		circ.setFrameFromCenter(420,420,420+radiusWhiteCircle,420+radiusWhiteCircle);
		g2d.setPaint(Color.WHITE);
		g2d.fill(circ);
		g2d.setPaint(Color.BLACK);
		g2d.draw(circ);
		
		circ = new Ellipse2D.Double();
		circ.setFrameFromCenter(540,540,540+radiusWhiteCircle,540+radiusWhiteCircle);
		g2d.setPaint(Color.WHITE);
		g2d.fill(circ);
		g2d.setPaint(Color.BLACK);
		g2d.draw(circ);
		
		circ = new Ellipse2D.Double();
		circ.setFrameFromCenter(540,420,540+radiusWhiteCircle,420+radiusWhiteCircle);
		g2d.setPaint(Color.WHITE);
		g2d.fill(circ);
		g2d.setPaint(Color.BLACK);
		g2d.draw(circ);
		
		//YELLOW WAY
		Rectangle2D yellowWay = new Rectangle2D.Double(520,320,40,40);
		g2d.setPaint(yellowColor); 
		g2d.fill(yellowWay); 
		g2d.setPaint(Color.BLACK); 
		g2d.draw(yellowWay);
		for (int i = 1; i <= 5; i++) {
			yellowWay = new Rectangle2D.Double(320 + (40 * i),280,40,40);
			g2d.setPaint(yellowColor); 
			g2d.fill(yellowWay); 
			g2d.setPaint(Color.BLACK); 
			g2d.draw(yellowWay);
		}
		
		//YELLOW TRIANGLE
		int xPointsYellow[] = {360,300,360};
		int yPointsYellow[] = {360,300,240};
		g2d.setPaint(yellowColor);
		g2d.fillPolygon(xPointsYellow, yPointsYellow, 3);
		g2d.setPaint(Color.BLACK);
		g2d.drawPolygon(xPointsYellow, yPointsYellow, 3);
		
		//WHITE TRIANGLE
		int xPointsWhite[] = {530,550,550};
		int yPointsWhite[] = {340,330,350};
		g2d.setPaint(Color.WHITE);
		g2d.fillPolygon(xPointsWhite, yPointsWhite, 3);
		g2d.setPaint(Color.BLACK);
		g2d.drawPolygon(xPointsWhite, yPointsWhite, 3);
    }
    
    private void drawBluePolygons (Graphics2D g2d) {
    	
    	Ellipse2D circ;
    	
    	//BLUE HOME
		Rectangle2D blueHome = new Rectangle2D.Double(0,360,240,240);
		g2d.setPaint(blueColor); 
		g2d.fill(blueHome); 
		g2d.setPaint(Color.BLACK); 
		g2d.draw(blueHome);	
		
		//WHITE CIRCLES OF BLUE HOME
		circ = new Ellipse2D.Double();
		circ.setFrameFromCenter(60,420,60+radiusWhiteCircle,420+radiusWhiteCircle);
		g2d.setPaint(Color.WHITE);
		g2d.fill(circ);
		g2d.setPaint(Color.BLACK);
		g2d.draw(circ);
		
		circ = new Ellipse2D.Double();
		circ.setFrameFromCenter(180,420,180+radiusWhiteCircle,420+radiusWhiteCircle);
		g2d.setPaint(Color.WHITE);
		g2d.fill(circ);
		g2d.setPaint(Color.BLACK);
		g2d.draw(circ);
		
		circ = new Ellipse2D.Double();
		circ.setFrameFromCenter(60,540,60+radiusWhiteCircle,540+radiusWhiteCircle);
		g2d.setPaint(Color.WHITE);
		g2d.fill(circ);
		g2d.setPaint(Color.BLACK);
		g2d.draw(circ);
		
		circ = new Ellipse2D.Double();
		circ.setFrameFromCenter(180,540,180+radiusWhiteCircle,540+radiusWhiteCircle);
		g2d.setPaint(Color.WHITE);
		g2d.fill(circ);
		g2d.setPaint(Color.BLACK);
		g2d.draw(circ);
		
		//BLUE WAY
		Rectangle2D blueWay = new Rectangle2D.Double(240,520,40,40);
		g2d.setPaint(blueColor); 
		g2d.fill(blueWay); 
		g2d.setPaint(Color.BLACK); 
		g2d.draw(blueWay);
		for (int i = 1; i <= 5; i++) {
			blueWay = new Rectangle2D.Double(280,320 + (40.0 * i),40,40);
			g2d.setPaint(blueColor); 
			g2d.fill(blueWay); 
			g2d.setPaint(Color.BLACK); 
			g2d.draw(blueWay);
		}
		
		//BLUE TRIANGLE
		int xPointsBlue[] = {240,300,360};
		int yPointsBlue[] = {360,300,360};
		g2d.setPaint(blueColor);
		g2d.fillPolygon(xPointsBlue, yPointsBlue, 3);
		g2d.setPaint(Color.BLACK);
		g2d.drawPolygon(xPointsBlue, yPointsBlue, 3);
		
		//WHITE TRIANGLE
		int xPointsWhite[] = {250,260,270};
		int yPointsWhite[] = {550,530,550};
		g2d.setPaint(Color.WHITE);
		g2d.fillPolygon(xPointsWhite, yPointsWhite, 3);
		g2d.setPaint(Color.BLACK);
		g2d.drawPolygon(xPointsWhite, yPointsWhite, 3);
    }
    
    private void drawTeamOnTurnSquare (Graphics2D g2d) {
    	if (CtrlGame.getCtrlGame().getGameTeam("currentTeam") != null) {
			
			if (CtrlGame.getCtrlGame().getGameTeam("currentTeam") == CtrlGame.getCtrlGame().getGameTeam("redTeam")) {
				g2d.setPaint(Board.redColor);
			}
			else if (CtrlGame.getCtrlGame().getGameTeam("currentTeam") == CtrlGame.getCtrlGame().getGameTeam("greenTeam")) {
				g2d.setPaint(Board.greenColor);
			}
			else if (CtrlGame.getCtrlGame().getGameTeam("currentTeam") == CtrlGame.getCtrlGame().getGameTeam("yellowTeam")) {
				g2d.setPaint(Board.yellowColor);
			}
			else if (CtrlGame.getCtrlGame().getGameTeam("currentTeam") == CtrlGame.getCtrlGame().getGameTeam("blueTeam")) {
				g2d.setPaint(Board.blueColor);
			}
			Rectangle2D teamOnTurn = new Rectangle2D.Double(667, 272, 60, 60);
			g2d.fill(teamOnTurn); 
			g2d.setPaint(Color.BLACK); 
			g2d.draw(teamOnTurn);
		}
    }
    
    private void drawLastTeamOnTurnSquare (Graphics2D g2d) {
    	if (CtrlGame.getCtrlGame().getGameTeam("oldTeam") != null) {
			
			if (CtrlGame.getCtrlGame().getGameTeam("oldTeam") == CtrlGame.getCtrlGame().getGameTeam("redTeam")) {
				g2d.setPaint(Board.redColor);
			}
			else if (CtrlGame.getCtrlGame().getGameTeam("oldTeam") == CtrlGame.getCtrlGame().getGameTeam("greenTeam")) {
				g2d.setPaint(Board.greenColor);
			}
			else if (CtrlGame.getCtrlGame().getGameTeam("oldTeam") == CtrlGame.getCtrlGame().getGameTeam("yellowTeam")) {
				g2d.setPaint(Board.yellowColor);
			}
			else if (CtrlGame.getCtrlGame().getGameTeam("oldTeam") == CtrlGame.getCtrlGame().getGameTeam("blueTeam")) {
				g2d.setPaint(Board.blueColor);
			}
			Rectangle2D lastTeamOnTurn = new Rectangle2D.Double(732, 307, 25, 25);
			g2d.fill(lastTeamOnTurn); 
			g2d.setPaint(Color.BLACK); 
			g2d.draw(lastTeamOnTurn);
		}
    }
    
    private void drawDiceImg (Graphics g2d) {
    	switch (CtrlGame.getCtrlGame().getGameDice("currentDice")) {
		case -1:
			break;
		case 0:
			g2d.drawImage(diceImgs[0], img_diceX, img_diceY, null);
			break;
		case 1:
			g2d.drawImage(diceImgs[1], img_diceX, img_diceY, null);
			break;
		case 2:
			g2d.drawImage(diceImgs[2], img_diceX, img_diceY, null);
			break;
		case 3:
			g2d.drawImage(diceImgs[3], img_diceX, img_diceY, null);
			break;
		case 4:
			g2d.drawImage(diceImgs[4], img_diceX, img_diceY, null);
			break;
		case 5:
			g2d.drawImage(diceImgs[5], img_diceX, img_diceY, null);
			break;
		case 6:
			g2d.drawImage(diceImgs[6], img_diceX, img_diceY, null);
			break;
		}
    }

    private void drawLastDiceImg (Graphics g2d) {
    	switch (CtrlGame.getCtrlGame().getGameDice("oldDice")) {
		case 1:
			g2d.drawImage(diceMinImgs[1], img_diceMinX, img_diceMinY, null);
			break;
		case 2:
			g2d.drawImage(diceMinImgs[2], img_diceMinX, img_diceMinY, null);
			break;
		case 3:
			g2d.drawImage(diceMinImgs[3], img_diceMinX, img_diceMinY, null);
			break;
		case 4:
			g2d.drawImage(diceMinImgs[4], img_diceMinX, img_diceMinY, null);
			break;
		case 5:
			g2d.drawImage(diceMinImgs[5], img_diceMinX, img_diceMinY, null);
			break;
		case 6:
			g2d.drawImage(diceMinImgs[6], img_diceMinX, img_diceMinY, null);
			break;
		}
    }
    
    private void drawPawnIdString (int centerX, int centerY, int i, Graphics g2d) {
    	switch (i) {
		case 0:
			g2d.drawString("1", centerX-3, centerY+4);
			break;
		case 1:
			g2d.drawString("2", centerX-3, centerY+4);
			break;
		case 2:
			g2d.drawString("3", centerX-3, centerY+4);
			break;
		case 3:
			g2d.drawString("4", centerX-3, centerY+4);
			break;
		}
    }
    
    private void debugMode(Graphics2D g2d) {
    	if (debugMode) {
			
			g2d.setPaint(Color.BLACK);
			
			g2d.drawString("A", 18, 615);
			g2d.drawString("B", 58, 615);
			g2d.drawString("C", 98, 615);
			g2d.drawString("D", 138, 615);
			g2d.drawString("E", 178, 615);
			g2d.drawString("F", 218, 615);
			g2d.drawString("G", 258, 615);
			g2d.drawString("H", 298, 615);
			g2d.drawString("I", 338, 615);
			g2d.drawString("J", 378, 615);
			g2d.drawString("K", 418, 615);
			g2d.drawString("L", 458, 615);
			g2d.drawString("M", 498, 615);
			g2d.drawString("N", 538, 615);
			g2d.drawString("O", 578, 615);
			
			g2d.drawString("1", 604, 22);
			g2d.drawString("2", 604, 62);
			g2d.drawString("3", 604, 102);
			g2d.drawString("4", 604, 142);
			g2d.drawString("5", 604, 182);
			g2d.drawString("6", 604, 222);
			g2d.drawString("7", 604, 262);
			g2d.drawString("8", 604, 302);
			g2d.drawString("9", 604, 342);
			g2d.drawString("10", 604, 382);
			g2d.drawString("11", 604, 422);
			g2d.drawString("12", 604, 462);
			g2d.drawString("13", 604, 502);
			g2d.drawString("14", 604, 542);
			g2d.drawString("15", 604, 585);
		}
    }
}