import java.util.ArrayList;
import java.util.Collections;

public class FIRSTNAME_LASTNAME_AStar 
{		
	public ArrayList<SearchPoint> frontier;
	public ArrayList<SearchPoint> explored;
	// TODO - add any extra member fields that you would like here 
	
	public class SearchPoint implements Comparable<SearchPoint>
	{
		public Map.Point mapPoint;
		// TODO - add any extra member fields or methods that you would like here
		
		// TODO - implement this method to return the minimum cost
		// necessary to travel from the start point to here
		public float g() 
		{
			return -1;
		}	
		
		// TODO - implement this method to return the heuristic estimate
		// of the remaining cost, based on the H parameter passed from main:
		// 0: always estimate zero, 1: manhattan distance, 2: euclidean l2 distance
		public float h()
		{
			return -1;
		}
		
		// TODO - implement this method to return to final priority for this
		// point, which include the cost spent to reach it and the heuristic 
		// estimate of the remaining cost
		public float f()
		{
			return -1;
		}
		
		// TODO - override this compareTo method to help sort the points in 
		// your frontier from highest priority = lowest f(), and break ties
		// using whichever point has the lowest g()
		@Override
		public int compareTo(SearchPoint other)
		{
			return -1;
		}
		
		// TODO - override this equals to help you check whether your ArrayLists
		// already contain a SearchPoint referencing a given Map.Point
		@Override
		public boolean equals(Object other)
		{
			return false;
		}		
	}
	
	// TODO - implement this constructor to initialize your member variables
	// and search, by adding the start point to your frontier.  The parameter
	// H indicates which heuristic you should use while searching:
	// 0: always estimate zero, 1: manhattan distance, 2: euclidean l2 distance
	public FIRSTNAME_LASTNAME_AStar(Map map, int H)
	{
		
	}
	
	// TODO - implement this method to explore the single highest priority
	// and lowest f() SearchPoint from your frontier.  This method will be 
	// called multiple times from Main to help you visualize the search.
	// This method should not do anything, if your search is complete.
	public void exploreNextNode() 
	{
		
	}

	// TODO - implement this method to return an ArrayList of Map.Points
	// that represents the SearchPoints in your frontier.
	public ArrayList<Map.Point> getFrontier()
	{
		return new ArrayList<Map.Point>();
	}
	
	// TODO - implement this method to return an ArrayList of Map.Points
	// that represents the SearchPoints that you have explored.
	public ArrayList<Map.Point> getExplored()
	{
		return new ArrayList<Map.Point>();
	}

	// TODO - implement this method to return true only after a solution
	// has been found, or you have determined that no solution is possible.
	public boolean isComplete()
	{
		return false;
	}

	// TODO - implement this method to return an ArrayList of the Map.Points
	// that are along the path that you have found from the start to end.  
	// These points must be in the ArrayList in the order that they are 
	// traversed while moving along the path that you have found.
	public ArrayList<Map.Point> getSolution()
	{
		return new ArrayList<Map.Point>();
	}	
}
