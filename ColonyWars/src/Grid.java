import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class Grid extends JPanel  {

	//@QuiescentRabbitt 5/26/18
	//Colony Wars
	
	//Set the grid to generate based on the size of the monitor
	private int gridWidth = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	private int gridHeight = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	
	//Variables to effect gridSize
	private int gridSizeX = 12;
	private int gridSizeY = 12;
	
	private Timer updater = new Timer();
	
	//Various variables to store units temporarily
	private Unit unit;
	private Unit tempUnit;
	private Unit removeUnit;
	
	private Random locationGen = new Random();
	private Random reproductionGen = new Random();
	private int randomX = 0;
	private int randomY = 0;
	
	//The amount this value can change by upon colony generation
	public static int maxDistanceBound = 2;
	public static int reproductionRarityBound = 20;
	public static int reproductiveAgeBound = 15; 
	public static int deathAgeBound = 20;
	
	//The base amount this value will be
	public static int maxDistanceMinimum = 1;
	public static int reproductionRarityMinimum = 30;
	public static int reproductiveAgeMinimum = 80; 
	public static int deathAgeMinimum = 100;
	
	private ArrayList<Colony> colonyList;
	private int colonyCount = 0;

	//How many units will be made upon creating a colony
	private int startingColonyUnitCount = 25;

	private boolean drawGrid = true;

	public Grid() { 
		updater.schedule(new UpdaterTask(), 500,16); //62.5 FPS
		addMouseListener (new UnitListener());
		maxDistanceBound*=gridSizeX; //Makes it so that this is in term of gridSquares instead of pixels
		colonyList = new ArrayList<Colony>();
	}
	
	public void paintComponent (Graphics page)  {
		
		synchronized(updater) {
			
			super.paintComponent(page);
			
			//Creates the grid thats in the background by default. Can be turned off
			if (drawGrid) {
				for (int x = 0; x < gridWidth; x+=gridSizeX) {
						
					page.drawLine(x, 0, x, gridWidth * 2);
					
					
				}
				
				for (int y = 0; y < gridHeight; y+=gridSizeY) {
					
					page.drawLine(0, y, gridHeight * 2, y);
				
				}
			}
			
			
			//Renders every unit
			for (Colony colony : colonyList) {
				for (Unit unit : colony.unitList) {
					page.setColor(colony.color);
					page.fillRect(unit.bounds.x, unit.bounds.y, unit.bounds.width, unit.bounds.height);
				}
			}
			
		
			
	    }
	}
		
		
	
	public boolean sameLocation(int x, int y, ArrayList<Colony> colonialList) {
		
			//Iterates through every single colony and unit checking to see if any match the same location
			for(Colony colony : colonialList) {
				for (Unit unit : colony.unitList) {
					if (unit.bounds.x == x && unit.bounds.y == y) {
						removeUnit = unit;
						return true;
					}
				}
			}
			return false;
			
	}

	
	public class UpdaterTask extends TimerTask {
		public void run() {		
			
		
				synchronized(updater) {
					
					//Will go through every colony and unit and update them based on game rules
					for (Colony colony : colonyList) {
						colony.unitCount = colony.unitList.size();
						for (int x = 0; x < colony.unitCount; x++) {
							unit = colony.unitList.get(x);	
							if (reproduces(unit, colony)) {
								
								//Determines how far away the new unit will be
								randomX=locationGen.nextInt(colony.maxDistance *2 + gridSizeX) - colony.maxDistance;
								randomY=locationGen.nextInt(colony.maxDistance *2 + gridSizeY) - colony.maxDistance;
								
								//Creates a new unit similar to the current one
								tempUnit = new Unit(((unit.bounds.x + randomX) / gridSizeX) * gridSizeX,((unit.bounds.y + randomY) / gridSizeY) * gridSizeY,unit.bounds.width,unit.bounds.height);
							
								//Checks to see if the generated unit intersects any others. If it intersects a unit from the same colony, try reproduction again, else it'll remove whatever unit was in the way
								if (!sameLocation(tempUnit.bounds.x,tempUnit.bounds.y,colonyList)) {
									
									colony.unitList.add(tempUnit);	
									
								} else if (!colony.unitList.contains(removeUnit)) {
									
									for (Colony removeColony : colonyList) {
										
										if (removeColony.unitList.contains(removeUnit)) {
											
										removeColony.unitList.remove(removeUnit);
										
										}
										
									}
									
								} else {
									x-=1;
									continue;
								}
							
							}
							
							if (unit.age >= colony.deathAge) {			
								colony.unitList.remove(unit);
								colony.unitCount-=1;
								continue;
							}
							
							unit.age+=1;		
						}
						
					}
					repaint();			
				}			
			
			}

		
			
		}
	
	
	private boolean reproduces(Unit unit, Colony colony) {
		
		if (unit.age < colony.reproductiveAge) {
			if (reproductionGen.nextInt(colony.reproductionRarity) == 0) {
				return true;	
			}
		}
	return false;
	}
	
	
	
	public class UnitListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {

			synchronized(updater) {
			
				colonyList.add(new Colony(new ArrayList<Unit>()));
				colonyCount = colonyList.size();
				
				for (int i = 0; i < startingColonyUnitCount; i++) { //Adds units upon clicking
					colonyList.get(colonyCount - 1).unitList.add(new Unit((e.getX() / gridSizeX) * gridSizeX,(e.getY() / gridSizeY) * gridSizeY,gridSizeX,gridSizeY));		
				}
				
			}
		
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
	
	
	
}





