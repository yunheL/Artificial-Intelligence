import java.util.ArrayList;
import processing.core.*;


public class Main extends PApplet
{
	FIRSTNAME_LASTNAME_AStar search; // your implementation of A* search
	Map map; // map to search for path between start and end points of
	boolean enterPressed; // press enter to watch entire search until solution
	boolean spaceWasDown; // press space repeatedly to step through search

	// initialize window
	public void settings() { size(800, 600); }
	
	// load map, and initialize fields along with processing modes
	public void setup()
	{		
		map = new Map("Madison_WI.osm",this);
		search = null;
		spaceWasDown = false;
		enterPressed = false;
		
		textAlign(CENTER);
		rectMode(CORNER);
	}

	// update
	public void draw()
	{
		background(0,0,127); // clear display
		map.draw(); // draw map

		// when solution exists, (potentially) continue search for path
		if(search != null) 
		{
			// always explore when enter has been pressed
			if(enterPressed) search.exploreNextNode();
			else if(keyPressed && key == '\n') enterPressed = true;
			else if(keyPressed && key == ' ')
			{
				// otherwise explore one step per spacebar press
				if(!spaceWasDown) 
					search.exploreNextNode();
				spaceWasDown = true;
			}
			else spaceWasDown = false;
			
			// draw frontier, explored, control instructions, and solution when available
			colorPoints(search.getFrontier(),true);
			colorPoints(search.getExplored(),false);
			fill(255); // display prompt to continue exploring or reset search				
			text("Press <Enter> to continue or <spacebard> to step through search.",width/2,height-32);				
			if(search.isComplete())
			{
				drawSolution(search.getSolution());
				text("Press <0>, <1>, or <2> to begin a new search.",width/2,height-16);
			}
			
			// clear the search and solution when end points are moved
			if(map.dirtyPoints || (search.isComplete() && (key == '0' || key == '1' || key == '2')))
			{
				search = null;
				enterPressed = false;
			}
		}
		else
		{
			// and check for key press
			if(keyPressed && (key == '0' || key == '1' || key == '2'))
			{
				search = new FIRSTNAME_LASTNAME_AStar(map,key-'0');
				map.dirtyPoints = false;
			}
			// display prompt to compute a new solution
			fill(255);
			text("Press <0>, <1>, or <2> to find a path from the green to red circle.",width/2,height-32);
		}		
		updateGUI(); // allow user to drag around end points		
	}
	
	public void colorPoints(ArrayList<Map.Point> points, boolean isFrontier)
	{
		if(isFrontier) 
		{
			stroke(color(127,127,0));
			fill(color(255,255,0)); // color frontier points yellow
			text("FRONTIER: "+points.size(),width/2,16);
		}
		else 
		{
			stroke(color(127,0,0));
			fill(color(255,0,0)); // color explored points red
			text("EXPLORED: "+points.size(),width/2,32);
		}
		
		for(Map.Point p : points)
			ellipse(p.x,p.y,4,4);
	}
	
	public void drawSolution(ArrayList<Map.Point> points)
	{
		stroke(255); // draw white lines between points
		strokeWeight(2);
		for(int i=1;i<points.size();i++)
			line( points.get(i-1).x, points.get(i-1).y,
				  points.get(i).x, points.get(i).y );
	}
	
	public void updateGUI()
	{
		// check for mouse press over start and end locations
		if(mousePressed && map.guiDragging == null)
		{
			float dToStartSqr = (float)(Math.pow(mouseX-map.guiStart.x,2)+
									    Math.pow(mouseY-map.guiStart.y,2));
			float dToEndSqr = (float)(Math.pow(mouseX-map.guiEnd.x,2)+
									  Math.pow(mouseY-map.guiEnd.y,2));
			if(dToStartSqr <= 50 && dToStartSqr < dToEndSqr)
				map.guiDragging = map.guiStart;
			else if(dToEndSqr <= 50)
				map.guiDragging = map.guiEnd;
		}
		// dragging start or end location
		if(mousePressed & map.guiDragging != null)
		{
			map.guiDragging.x = mouseX;
			map.guiDragging.y = mouseY;
		}
		// stop dragging start or end location
		if(!mousePressed && map.guiDragging != null)
		{
			map.moveEndPointsToClosestStreet();
			map.guiDragging = null;
		}
		
		// draw start and end
		fill(0,0,0,0);
		stroke(0,255,0);
		ellipse(map.guiStart.x, map.guiStart.y, 8,8);
		stroke(255,0,0);
		ellipse(map.guiEnd.x, map.guiEnd.y, 8,8);			
	}
	
	public static void main(String args[])
	{
		PApplet.main(new String[] {"Main"});
	}
}
