import java.util.*;
import processing.core.*;
import processing.data.*;

public class Map 
{
	// access to all points and streets
	public ArrayList<Point> allPoints;
	public ArrayList<Street> allStreets;
	// actual points to search between
	public Point start;
	public Point end;
	// point class
	public class Point 
	{
		// x and y screen coordinate
		public float x;
		public float y;
		// list of points connected to this one by roads
		public ArrayList<Point> neighbors;
		// temporary variable used to cull unused points
		public boolean isOnStreet; 

		public Point(float x, float y)
		{
			this.x = x;
			this.y = y;
			neighbors = new ArrayList<Point>();
			isOnStreet = false;
			allPoints.add(this);
		}	
	}

	// YOU CAN IGNORE THE REST OF THIS FILE FOR THE PURPOSES
	// OF COMPLETING THIS ASSIGNMENT...
	
	public class Street 
	{
		public ArrayList<Point> points;
		public String name;
		
		public Street(ArrayList<Point> points, String name)
		{
			this.points = points;
			this.name = name;
			allStreets.add(this);
		}	
	}

	private PApplet p;
	// extra points to manipulate with gui
	public Point guiStart;
	public Point guiEnd;
	// reference to which guiPoint is being dragged, or null
	public Point guiDragging;
	// actual points to search between have changed
	public boolean dirtyPoints;
	// based on aspect ration of map data vs 800x600 window
	public float usableHeight;

	public Map(String filename, PApplet p)
	{
		this.p = p;
		read(filename);
		// initialize gui points near center of the screen
		guiStart = new Point(p.width * 4 / 10, p.height / 2);
		allPoints.remove(guiStart);
		guiEnd = new Point(p.width * 6 / 10, p.height / 2);
		allPoints.remove(guiEnd);
		guiDragging = null;
		moveEndPointsToClosestStreet();
	}
	
	public void read(String filename)
	{
		// initialize point and street arrays
		allPoints = new ArrayList<Point>();
		allStreets = new ArrayList<Street>();
		
		// read file
		XML xml = p.loadXML(filename);
		
		// read dimensions to create proportional window
		//             and to scale myPoint positions
	    float minlat = xml.getChild("bounds").getFloat("minlat");
	    float minlon = xml.getChild("bounds").getFloat("minlon");
	    float maxlat = xml.getChild("bounds").getFloat("maxlat");
	    float maxlon = xml.getChild("bounds").getFloat("maxlon");
	    float dLat = maxlat - minlat;
	    float dLon = maxlon - minlon;
	    usableHeight = (800*dLat/dLon);

	    // read points
	    XML nodes[] = xml.getChildren("node");
		Hashtable<Long,Integer> indexConvert = new Hashtable<Long,Integer>();
	    for(XML node : nodes)
	    {
	    	if(!node.hasAttribute("id") || !node.hasAttribute("lat") || !node.hasAttribute("lon")) continue;
	    	
	    	long id = node.getLong("id", -1);
	    	float lat = node.getFloat("lat");
	    	float lon = node.getFloat("lon");
	    	Point point = new Point(p.width * (lon-minlon)/dLon, p.height - (usableHeight * (lat-minlat)/dLat) - (p.height-usableHeight)/2);
	    	indexConvert.put(id, allPoints.indexOf(point));
	    }
	    
	    // read streets
	    XML ways[] = xml.getChildren("way");
	    for(XML way : ways)
	    {
	    	// read road type and name
	    	boolean isRoad = false;
	    	String name = "";
	    	XML tags[] = way.getChildren("tag");
	    	for(XML tag : tags)
	    		if(tag.hasAttribute("k") && tag.hasAttribute("v"))
	    		{
	    			if(tag.getString("k").equals("highway")) 
	    				switch(tag.getString("v"))
		    			{
		    			case "pedestrian":
		    			case "footway":
		    			case "cycleway":
		    			case "steps":
		    			case "path":
		    			case "living street":
		    				// isRoad = false; // initialized to false above
		    				break;
		    			default:
		    				isRoad = true;
		    			}
	    			else if(tag.getString("k").equals("name")) name = tag.getString("v");
	    		}
	    	if(!isRoad) continue;
	    	// read list of points
	    	ArrayList<Point> points = new ArrayList<Point>();
	    	XML nds[] = way.getChildren("nd");
	    	for(XML nd : nds)
	    	{
	    		long index = nd.getLong("ref", -1);
	    		Point nextPoint = allPoints.get((Integer)indexConvert.get(new Long(index)));
	    		nextPoint.isOnStreet = true;
	    		points.add(nextPoint);
	    	}
	    	// create new street
	    	@SuppressWarnings("unused")
			Street street = new Street(points,name);
	    	// add neighboring nodes to each node
	    	if(points.size() > 1) points.get(0).neighbors.add(points.get(1));
	    	for(int i=1;i<points.size()-1;i++)
	    	{
	    		points.get(i).neighbors.add(points.get(i-1));
	    		points.get(i).neighbors.add(points.get(i+1));
	    	}
	    	if(points.size() > 1) points.get(points.size()-1).neighbors.add(points.get(points.size()-2));
	    }
	    
	    // remove unused points from allPoints
	    ArrayList<Point> remPoints = new ArrayList<Point>();
	    for(Point point : allPoints)
	    	if(!point.isOnStreet) remPoints.add(point);
	    for(Point point : remPoints)
	    	allPoints.remove(point);
	}
	
	public void draw()
	{
		p.stroke(127);
		p.strokeWeight(1);
		for(Street street : allStreets)
			for(int i=1;i<street.points.size();i++)
				p.line(street.points.get(i-1).x,street.points.get(i-1).y,
					   street.points.get(i).x,  street.points.get(i).y);
//		for(Point point : allPoints)
//			p.ellipse(point.x,point.y,2,2);		
		p.stroke(0);
		p.fill(0);
		p.rect(0, 0, p.width, (p.height-usableHeight)/2);
		p.rect(0, p.height-(p.height-usableHeight)/2, p.width, (p.height-usableHeight)/2);
	}
	
	public void clear()
	{
		allPoints.clear();
		allStreets.clear();
	}
	
	public void moveEndPointsToClosestStreet()
	{
		float dStart = Float.MAX_VALUE;
		float dEnd = Float.MAX_VALUE;
		float distSqr = Float.MAX_VALUE;
		
		// find closest point to start and end, save locations, and store mapStart & mapEnd
		for(Point point : allPoints)
		{
			if(point.x < 0 || point.x >= p.width || point.y < 0 || point.y >= p.height) continue;
			
			distSqr = (guiStart.x-point.x)*(guiStart.x-point.x)+(guiStart.y-point.y)*(guiStart.y-point.y);
			if(distSqr < dStart)
			{
				start = point;
				dStart = distSqr;
			}
			distSqr = (guiEnd.x-point.x)*(guiEnd.x-point.x)+(guiEnd.y-point.y)*(guiEnd.y-point.y);
			if(distSqr < dEnd)
			{
				end = point;
				dEnd = distSqr;
			}
		}
		
		// copy locations of closest points back into 
		guiStart.x = start.x;
		guiStart.y = start.y;
		guiEnd.x = end.x;
		guiEnd.y = end.y;		
		
		dirtyPoints = true;
	}	
}
