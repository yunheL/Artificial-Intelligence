import java.util.ArrayList;
import java.util.Collections;

public class Yunhe_Liu_AStar 
{		
	public ArrayList<SearchPoint> frontier;
	public ArrayList<SearchPoint> explored;
	
	// TODO - add any extra member fields that you would like here
	public ArrayList<SearchPoint> SPpath;
	public ArrayList<Pair> path;
	
	public ArrayList<Map.Point> all;
	
	//additional fields
	public int option;
	
	//additional fields -> Map.Point
	public Map.Point startPoint;
	public Map.Point endPoint;
	
	//additional fields -> SearchPoint
	public SearchPoint starting;
	public SearchPoint ending;
	public SearchPoint exploring;
	public SearchPoint curr;
	
	//helper class to keep and point and its cost from start
	public class Pair
	{
		public Map.Point mapPoint;
		public double dist;
		
		public Pair(Map.Point mapPoint, double dist)
		{
			this.mapPoint = mapPoint;
			this.dist = dist;
		}
	}
	
	public class SearchPoint implements Comparable<SearchPoint>
	{
		public Map.Point mapPoint;
		// TODO - add any extra member fields or methods that you would like here

		public double realDist;
		public SearchPoint prev;
		
		//constructor
		public SearchPoint(Map.Point mapPoint)
		{
			this.mapPoint = mapPoint;
			this.prev = null;
		}
		
		// TODO - implement this method to return the minimum cost
		// necessary to travel from the start point to here
		public float g() 
		{
			float gValue;
			
			if(this.prev == null)
			{
				gValue = 0;
			}
			else
			{
				//recursively get the gValue
				gValue = (float) (dist(this, this.prev) + this.prev.g());
			}
			
			return gValue;
		}	
		
		// TODO - implement this method to return the heuristic estimate
		// of the remaining cost, based on the H parameter passed from main:
		// 0: always estimate zero, 1: manhattan distance, 2: euclidean l2 distance
		public float h()
		{
			float heuristic = 0;
			if(option == 0)
			{
				return 0;
			}
			else if(option == 1)
			{
				float xDis = 0;
				float yDis = 0;
				
				//square and sqrt to get absolute value
				xDis = (endPoint.x - this.mapPoint.x) * (endPoint.x - this.mapPoint.x);
				yDis = (endPoint.y - this.mapPoint.y) * (endPoint.y - this.mapPoint.y);
				
				xDis = (float)(Math.sqrt((double)(xDis)));
				yDis = (float)(Math.sqrt((double)(yDis)));
				
				heuristic = xDis + yDis;
			}
			else if(option == 2)
			{
				float xDis = 0;
				float yDis = 0;
				float squareDis = 0;
				
				xDis = (endPoint.x - this.mapPoint.x) * (endPoint.x - this.mapPoint.x);
				yDis = (endPoint.y - this.mapPoint.y) * (endPoint.y - this.mapPoint.y);
				
				squareDis = xDis + yDis;
				heuristic = (float)(Math.sqrt((double)(squareDis)));
			}
			else
			{
				System.out.println("Option Error!");
			}
			return heuristic;
		}
		
		// TODO - implement this method to return to final priority for this
		// point, which include the cost spent to reach it and the heuristic 
		// estimate of the remaining cost
		public float f()
		{
			float estimateSumCost = 0;
			estimateSumCost = g() + h();
			return estimateSumCost;
		}
		
		// TODO - override this compareTo method to help sort the points in 
		// your frontier from highest priority = lowest f(), and break ties
		// using whichever point has the lowest g()
		@Override
		public int compareTo(SearchPoint other)
		{
			//TODO: possible bug location, the new searchpoint may not be right
			SearchPoint curr = new SearchPoint(this.mapPoint);
			if(curr.f() > other.f())
			{
				return 1;
			}
			else if(curr.f() < other.f())
			{
				return -1;
			}
			else
			{
				if(curr.g() > other.g())
				{
					return 1;
				}
				else if(curr.g() < other.g())
				{
					return -1;
				}
				else
				{
					return 0;
				}
			}
		}
		
		//TODO: Read instruction again to debug. Bug here. I think the method
		//is meant to be implemented in a different way. Make sure to modify
		//the iscomplete function after modifying this function.
		
		// TODO - override this equals to help you check whether your ArrayLists
		// already contain a SearchPoint referencing a given Map.Point
		@Override
		public boolean equals(Object other)
		{
			if(other instanceof SearchPoint)
			{
				//compare reference
				if (this.mapPoint == ((SearchPoint)other).mapPoint)
				{
					return true;
				}
			}
			return false;
			
		}		
	}
	
	//helper function to calculate the distance between to points
	public static double dist(SearchPoint A, SearchPoint B)
	{
		double dist = 0;
		
		double xDis = 0;
		double yDis = 0;
		double squareDis = 0;
		
		xDis = (A.mapPoint.x - B.mapPoint.x) * (A.mapPoint.x - B.mapPoint.x);
		yDis = (A.mapPoint.y - B.mapPoint.y) * (A.mapPoint.y - B.mapPoint.y);
		
		squareDis = xDis + yDis;
		dist = Math.sqrt(squareDis);

		return dist;
	}
	
	//helper function to remove all elements from an arrayList
	public static void resetPath(ArrayList<SearchPoint> path)
	{
		while(path.size() != 0)
		{
			path.remove(0);
		}
	}
	
	//Helper function to see whether a point is in a List
	public static boolean inList(ArrayList<SearchPoint> list, SearchPoint point)
	{
		for(int i = 0; i < list.size(); i++)
		{
			if((list.get(i).mapPoint.x == point.mapPoint.x) && (list.get(i).mapPoint.y == point.mapPoint.y))
			{
				return true;
			}
		}
		return false;
	}
	
	// TODO - implement this constructor to initialize your member variables
	// and search, by adding the start point to your frontier.  The parameter
	// H indicates which heuristic you should use while searching:
	// 0: always estimate zero, 1: manhattan distance, 2: euclidean l2 distance
	public Yunhe_Liu_AStar(Map map, int H)
	{
		//System.out.println("here");
		
		option = H;
		startPoint = map.start;
		endPoint = map.end;

		starting = new SearchPoint(map.start);
		ending = new SearchPoint(map.end);
		exploring = new SearchPoint(map.start);
		curr = new SearchPoint(map.start);
		
		frontier = new ArrayList<SearchPoint>();
		explored = new ArrayList<SearchPoint>();
		path = new ArrayList<Pair>();
		SPpath = new ArrayList<SearchPoint>();
		
		frontier.add(starting);
		
		Pair startPair = new Pair(map.start, 0);
		path.add(startPair);
		
		all = map.allPoints;
	}
	
	// TODO - implement this method to explore the single highest priority
	// and lowest f() SearchPoint from your frontier.  This method will be 
	// called multiple times from Main to help you visualize the search.
	// This method should not do anything, if your search is complete.
	public void exploreNextNode() 
	{
		if(isComplete())
		{
			return;
		}
		
		Collections.sort(frontier);
		
		exploring = frontier.get(0);
		curr = frontier.get(0);

		frontier.remove(0);
		explored.add(exploring);
		
		//update frontier and set the prev field for each search point
		int i = 0;
		for(i = 0; i < exploring.mapPoint.neighbors.size(); i++)
		{
			SearchPoint front = new SearchPoint(exploring.mapPoint.neighbors.get(i));
			front.prev = exploring;
			if(!inList(explored, front))
			{
				frontier.add(front);
			}
		}
		
		//update path
		resetPath(SPpath);
		SPpath.add(exploring);
		
		while(exploring.prev != null)
		{
			exploring = exploring.prev;
			SPpath.add(0, exploring);
		}
	}

	// TODO - implement this method to return an ArrayList of Map.Points
	// that represents the SearchPoints in your frontier.
	public ArrayList<Map.Point> getFrontier()
	{
		ArrayList<Map.Point> pointsFrontier = new ArrayList<Map.Point>();
		
		int i = 0;
		for(i = 0; i < frontier.size(); i++)
		{
			pointsFrontier.add(frontier.get(i).mapPoint);
		}
		
		return pointsFrontier;
	}
	
	// TODO - implement this method to return an ArrayList of Map.Points
	// that represents the SearchPoints that you have explored.
	public ArrayList<Map.Point> getExplored()
	{
		
		ArrayList<Map.Point> pointsExplored = new ArrayList<Map.Point>();
		
		int i = 0;
		for(i = 0; i < explored.size(); i++)
		{
			pointsExplored.add(explored.get(i).mapPoint);
		}
		
		return pointsExplored;
	}

	//TODO: rethink the stopping condition
	// TODO - implement this method to return true only after a solution
	// has been found, or you have determined that no solution is possible.
	public boolean isComplete()
	{
		if(curr.equals(ending)|| getFrontier().size() == 0)
		{
			return true;
		}
			
		return false;
	}

	// TODO - implement this method to return an ArrayList of the Map.Points
	// that are along the path that you have found from the start to end.  
	// These points must be in the ArrayList in the order that they are 
	// traversed while moving along the path that you have found.
	public ArrayList<Map.Point> getSolution()
	{
		ArrayList<Map.Point> solution = new ArrayList<Map.Point>();
		
		int i = 0;
		for(i = 0; i < SPpath.size(); i++)
		{
			solution.add(SPpath.get(i).mapPoint);
		}
		return solution;
	}	
}
//this is the right file to handin 11/15-5:40am