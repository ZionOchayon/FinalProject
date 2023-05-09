package PizzaProject;

import java.util.Comparator;

import PizzaProject.Provided.PizzaPlace;

public class CompareFavoritedByDist implements Comparator<PizzaPlace> {

	@Override
	public int compare(PizzaPlace o1, PizzaPlace o2) {
		if(o1.distance() < o2.distance()) // Sort by distance in ascending order
		{
			return -1;
		}
		else if(o1.distance() > o2.distance()) 
		{
			return 1;
		}
		else 
		{
			if(o1.averageRating() > o2.averageRating()) // If same distance Sort by averageRating in descending order
			{
				return -1;
			}
			else if(o1.averageRating() < o2.averageRating())
			{
				return 1;
			}
			else 
			{
				if(o1.getId() < o2.getId()) // If same averageRating Sort by id in ascending order
				{
					return -1;
				}
				else if(o1.getId() > o2.getId())
				{
					return 1;
				}
			}
		}	
		return 0;
	}

}
