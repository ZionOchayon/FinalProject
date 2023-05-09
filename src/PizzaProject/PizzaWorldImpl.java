package PizzaProject;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import PizzaProject.Provided.PizzaLover;
import PizzaProject.Provided.PizzaLover.ConnectionAlreadyExistsException;
import PizzaProject.Provided.PizzaLover.PizzaLoverAlreadyInSystemException;
import PizzaProject.Provided.PizzaLover.PizzaLoverNotInSystemException;
import PizzaProject.Provided.PizzaLover.SamePizzaLoverException;
import PizzaProject.Provided.PizzaPlace;
import PizzaProject.Provided.PizzaPlace.PizzaPlaceAlreadyInSystemException;
import PizzaProject.Provided.PizzaPlace.PizzaPlaceNotInSystemException;
import PizzaProject.Provided.PizzaWorld;

public class PizzaWorldImpl implements PizzaWorld{
	private Set<PizzaLover> pepoleNetwork;
	private Set<PizzaPlace> shopsNetwork;
	
	public PizzaWorldImpl()   // Constructor
	{ 
		this.pepoleNetwork = new TreeSet<PizzaLover>();
		this.shopsNetwork = new TreeSet<PizzaPlace>();
	}
	@Override
	public PizzaLover joinNetwork(int id, String name) throws PizzaLoverAlreadyInSystemException {
		PizzaLover newGuy = new PizzaLoverImpl(id,name);
		for(PizzaLover n : pepoleNetwork)
		{
			if(n.compareTo(newGuy) == 0)   // Check by id if PizzaLover is already exist. We override compareTo method in PizzaLover class
			{
				throw new PizzaLoverAlreadyInSystemException();
			}
		}
		pepoleNetwork.add(newGuy);
		return newGuy;
	}

	@Override
	public PizzaPlace addPizzaPlace(int id, String name, int dist, Set<String> menu) throws PizzaPlaceAlreadyInSystemException {
		PizzaPlace newShop = new PizzaPlaceImpl(id,name,dist,menu);
		for(PizzaPlace n : shopsNetwork) 
		{
			if(n.compareTo(newShop) == 0)  // Check by id if PizzaPlace is already exist. We override compareTo method in PizzaPlace class
			{
				throw new PizzaPlaceAlreadyInSystemException();
			}
		}
		shopsNetwork.add(newShop);
		return newShop;
	}

	@Override
	public Collection<PizzaLover> registeredPizzaLovers() {	// shallow copy
		return pepoleNetwork;  
	}

	@Override
	public Collection<PizzaPlace> registeredPizzaPlaces() { // shallow copy
		return shopsNetwork;
	}

	@Override
	public PizzaLover getPizzaLover(int id) throws PizzaLoverNotInSystemException {
		for(PizzaLover n : pepoleNetwork) 
		{
			if(n.getId() == id) 
			{
				return n; // If id exist return referees of this PizzaLover
			}
		}
		throw new PizzaLoverNotInSystemException();
	}

	@Override
	public PizzaPlace getPizzaPlace(int id) throws PizzaPlaceNotInSystemException {
		for(PizzaPlace n : shopsNetwork) 
		{
			if(n.getId() == id) // If id exist return referees of this PizzaPlace
			{
				return n;
			}
		}
		throw new PizzaPlaceNotInSystemException();
	}

	@Override
	public PizzaWorld addConnection(PizzaLover pl1, PizzaLover pl2) throws PizzaLoverNotInSystemException, ConnectionAlreadyExistsException, SamePizzaLoverException {
		if(pl1.equals(pl2)) // If its the same PizzaLover 
		{
			throw new SamePizzaLoverException();
		}
		else if (!(pepoleNetwork.contains(pl1)) || !(pepoleNetwork.contains(pl2))) // If one of them is not in the network
		{
			throw new PizzaLoverNotInSystemException();
		}
		else if(pl1.getFriends().contains(pl2) || pl2.getFriends().contains(pl1))  // If they are already friends
		{
			throw new ConnectionAlreadyExistsException();
		}
		else 
		{
			pl1.addFriend(pl2); // Connect both of them
			pl2.addFriend(pl1);
		}
		return this;
	}

	@Override
	public Collection<PizzaPlace> favoritesByRating(PizzaLover pl) throws PizzaLoverNotInSystemException {
		Set<PizzaPlace> newList = new TreeSet<PizzaPlace>();
		if(pepoleNetwork.contains(pl)) 
		{
			for(PizzaLover n : pl.getFriends()) // Friends are already sorted by compareTo in PizzaLover class
			{
				for(PizzaPlace i : n.favoritesByRating(0)) // Iterate friend favorites in favoritesByRating Order
				{
					if(!(newList.contains(i))) // Add without duplicates
					{
						newList.add(i);
					}
				}
			}
		}
		else // If PizzaLover is not in the network throw exception
		{
			throw new PizzaLoverNotInSystemException();
		}
		return newList;
	}

	@Override
	public Collection<PizzaPlace> favoritesByDist(PizzaLover pl) throws PizzaLoverNotInSystemException {
		Set<PizzaPlace> newList = new TreeSet<PizzaPlace>();
		if(this.pepoleNetwork.contains(pl)) 
		{
			for(PizzaLover n : pl.getFriends())  // Friends are already sorted by compareTo in PizzaLover class
			{
				for(PizzaPlace i : n.favoritesByDist(100000)) // Iterate friend favorites in favoritesByDist Order
				{
					if(!(newList.contains(i))) // Add without duplicates
					{
						newList.add(i);
					}
				}
			}
		}
		else  // If PizzaLover is not in the network throw exception
		{
			throw new PizzaLoverNotInSystemException();
		}
		return newList;
	}

	@Override
	public boolean getRecommendation(PizzaLover pl, PizzaPlace pp, int t) throws PizzaLoverNotInSystemException, PizzaPlaceNotInSystemException, ImpossibleConnectionException {
		if(t < 0) // If the distance is negative
		{
			throw new ImpossibleConnectionException();
		}
		else if(!(pepoleNetwork.contains(pl))) // If PizzaLover not in network
		{
			throw new PizzaLoverNotInSystemException();
		}
		else if(!(shopsNetwork.contains(pp))) // If PizzaPlace not in network
		{
			throw new PizzaPlaceNotInSystemException();
		}
		else 
		{
			if(pl.favorites().contains(pp)) // if this PizzaLover have this shop in favorites so its the minimum distance = 0 
			{
				return true;
			}
			else 
			{
				int counter = 1; // Count the number of friends = distance
				for(PizzaLover n : pl.getFriends()) 
				{
					if(n.favorites().contains(pp)) // If this friend have the shop in favorites
					{
						if(counter <= t) // If the distance below the limit
						{
							return true;
						}
					}
					counter++;
				}
				return false;
			}
		}
	}
	
	public String String() {  // in the task we were asked for String() and not toString()
		return "\n--- PizzaWorldImpl ---\n"
				+ "Pepole Network : " + pepoleNetwork + "\n"
				+ "Shops Network : " + shopsNetwork;
	}
	

}
