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
		//TODO: possible bug here that the way using non-static method
		public Map.Point mapPoint;
		// TODO - add any extra member fields or methods that you would like here
		//public float x;
		//public float y;
		public double realDist;
		public SearchPoint prev;
		
		public SearchPoint(Map.Point mapPoint)
		{
			
			this.mapPoint = mapPoint;
			//this.x = mapPoint.x;
			//this.y = mapPoint.y;
			this.prev = null;
		}
		
		// TODO - implement this method to return the minimum cost
		// necessary to travel from the start point to here
		public float g() 
		{
			float gValue;
			SearchPoint curr = new SearchPoint(mapPoint);
			
			if(curr.prev == null)
			{
				gValue = 0;
				//System.out.println("here1");
			}
			else
			{
				gValue = (float) (dist(curr, curr.prev) + curr.prev.g());
				//System.out.println("here2");
				//System.out.println(dist(curr, curr.prev));
			}
			
			
			
			return gValue;
			
			/*
			float additionalDist;
			float gValue;
			
			SearchPoint curr = new SearchPoint(mapPoint);
			
			SearchPoint lastPoint = new SearchPoint(path.get(path.size()-1).mapPoint);
			
			additionalDist = (float)(dist(curr, lastPoint));
			
			gValue = (float)(path.get(path.size()-1).dist) + additionalDist;
					
			return gValue;
			*/
			
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
				
				xDis = (endPoint.x - mapPoint.x);
				yDis = (endPoint.y - mapPoint.y);
				heuristic = xDis + yDis;
			}
			else if(option == 2)
			{
				float xDis = 0;
				float yDis = 0;
				float squareDis = 0;
				
				xDis = (endPoint.x - mapPoint.x) * (endPoint.x - mapPoint.x);
				yDis = (endPoint.y - mapPoint.y) * (endPoint.y - mapPoint.y);
				
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
			SearchPoint curr = new SearchPoint(mapPoint);
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
				//other = (SearchPoint)(other);
				//other.mapPoint;
				if (mapPoint == ((SearchPoint)other).mapPoint)
				{
					return true;
				}
			}
			return false;
			/*
			int i = 0;
			
			//check Frontier list
			for(i = 0; i < frontier.size(); i++)
			{
				//TODO: possible error using "==". May want consider .equals();
				if(frontier.get(i) == other)
				{
					return true;
				}
			}
			
			//check explored list
			for(i = 0; i < explored.size(); i++)
			{
				//TODO: possible error using "==". May want consider .equals();
				if(explored.get(i) == other)
				{
					return true;
				}
			}
			return false;
			*/
		}		
	}
	
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
	
	public static void resetPath(ArrayList<SearchPoint> path)
	{
		while(path.size() != 0)
		{
			path.remove(0);
		}
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
		
		//TODO remove debug code
		//starting.x = 0;
		//starting.y = 0;
		
		//System.out.println("x is:" + startPoint.x);
		
		starting = new SearchPoint(map.start);
		ending = new SearchPoint(map.end);
		exploring = new SearchPoint(map.start);
		
		
		//System.out.println("x is:" + starting.x);
		
//		starting.x = startPoint.x;
//		starting.y = startPoint.y;
		
		frontier = new ArrayList<SearchPoint>();
		explored = new ArrayList<SearchPoint>();
		path = new ArrayList<Pair>();
		SPpath = new ArrayList<SearchPoint>();
		
		frontier.add(starting);
		
		Pair startPair = new Pair(map.start, 0);
		path.add(startPair);
		
		//System.out.println("frontier x is" + frontier.get(0).x);
		all = map.allPoints;
		//System.out.println("all(0).x is " + all.get(0).x);
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
		//exploring = new SearchPoint(frontier.get(0));
		
		exploring = frontier.get(0);
		
		/*
		if((exploring.mapPoint.x == starting.mapPoint.x) && (exploring.mapPoint.y == starting.mapPoint.y))
		{
			for(int i = 0; i<exploring.mapPoint.neighbors.size(); i++)
			{
				exploring.mapPoint.neighbors.get(i).prev = starting;
			}
		}
		*/
		
		//System.out.println("exploring.x is " + exploring.mapPoint.x);
		frontier.remove(0);
		explored.add(exploring);
		
		//TODO: debug code
		//System.out.println(exploring.g());
		
		//this part is good
		int i = 0;
		for(i = 0; i < exploring.mapPoint.neighbors.size(); i++)
		{
			SearchPoint front = new SearchPoint(exploring.mapPoint.neighbors.get(i));
			front.prev = exploring;
			//System.out.println("exploring is [" + exploring.mapPoint.x + "," + exploring.mapPoint.y + "]");
			//System.out.println("front.prev is [" + front.prev.mapPoint.x + "," + front.prev.mapPoint.y + "]");
			frontier.add(front);
		}
		
		
		//the problem is with this part. The newly created traverse seach point always have the
		//prev have the value of null
		//update path
		SearchPoint traverse = new SearchPoint(exploring.mapPoint);
		while(traverse.prev != null)
		{
			resetPath(SPpath);
			SPpath.add(0, traverse);
		}
		
		//got empty path lists in the following test
		//System.out.println(SPpath.toString());
			
		/*
		for(i = 0; )
			
		double additionalDist = 0;
		SearchPoint lastPoint = new SearchPoint(path.get(path.size()-1).mapPoint);
		additionalDist = dist(exploring, lastPoint);
		
		Pair pairExploring = new Pair(exploring.mapPoint, (path.get(path.size()-1).dist + additionalDist));
		
		path.add(pairExploring);
		*/
	}

	// TODO - implement this method to return an ArrayList of Map.Points
	// that represents the SearchPoints in your frontier.
	public ArrayList<Map.Point> getFrontier()
	{
		ArrayList<Map.Point> pointsFrontier = new ArrayList<Map.Point>();
		
		int i = 0;
		for(i = 0; i < frontier.size(); i++)
		{
			//SearchPoint front = new SearchPoint(frontier.get(i).mapPoint);
			pointsFrontier.add(frontier.get(i).mapPoint);
		}
		
		return pointsFrontier;
		/*
		int i = 0;
		//TODO: initializing to null, possible bug
		Map.Point curr = null;
		for (i = 0; i < all.size(); i++)
		{
			if(all.get(i).x == exploring.x)
			{
				if(all.get(i).y == exploring.y)
				{
					curr = all.get(i);
				}
			}
		}
		
		if(curr != null)
		{
			return curr.neighbors;
		}
		else
		{
			System.out.println("getFrontier error");
			return null;
		}
		*/
		
	}
	
	// TODO - implement this method to return an ArrayList of Map.Points
	// that represents the SearchPoints that you have explored.
	public ArrayList<Map.Point> getExplored()
	{
		
		ArrayList<Map.Point> pointsExplored = new ArrayList<Map.Point>();
		
		//explored = new ArrayList<SearchPoint>();
		int i = 0;
		for(i = 0; i < explored.size(); i++)
		{
			//SearchPoint front = new SearchPoint(frontier.get(i).mapPoint);
			pointsExplored.add(explored.get(i).mapPoint);
		}
		
		return pointsExplored;
		/*
		int i = 0;
		for (i = 0; i < explored.size(); i++)
		{
			int j = 0;
			for (j = 0; j < all.size(); j++)
			{
				if(all.get(j).x == explored.get(i).x)
				{
					if(all.get(j).y == explored.get(i).y)
					{
						exploredPoints.add(all.get(j));
					}
				}
			}
		}
		return exploredPoints;
		*/
	}

	//TODO: rethink the stopping condition
	// TODO - implement this method to return true only after a solution
	// has been found, or you have determined that no solution is possible.
	public boolean isComplete()
	{
		//ending.x = endPoint.x;
		//ending.y = endPoint.y;
		
		
		
		//TODO: Read instruction again to debug. Bug here
		if(exploring.equals(ending)|| getFrontier().size() == 0)
		{
			return true;
		}
			
		return false;
	}

	// TODO - implement this method to return an ArrayList of the Map.Points
	// that are along the path that you have found from the start to end.  
	// These points must be in the ArrayList in the order that they are 
	// traversed while moving along the path that you have found.
	
	//TODO: Re-implement
	public ArrayList<Map.Point> getSolution()
	{
		ArrayList<Map.Point> solution = new ArrayList<Map.Point>();
		
		int i = 0;
		for(i = 0; i < SPpath.size(); i++)
		{
			//SearchPoint front = new SearchPoint(frontier.get(i).mapPoint);
			solution.add(SPpath.get(i).mapPoint);
		}
		
		//pointsExplored.add(endPoint);
		return solution;
	}	
}
