package PizzaProject;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import PizzaProject.Provided.PizzaLover;
import PizzaProject.Provided.PizzaPlace;

public class PizzaLoverImpl implements PizzaLover {
	
	private int id;
	private String name;
	private Set<PizzaPlace> favorites;
	private Set<PizzaLover> friends;
	
	public PizzaLoverImpl(int id, String name) // Constructor
	{
		this.id = id;
		this.name = name;
		this.favorites = new TreeSet<PizzaPlace>();
		this.friends = new TreeSet<PizzaLover>(); 
	}															   

	@Override
	public int compareTo(PizzaLover o) { // Sort the collections we have  by the natural order 
		if(id < o.getId()) 
		{
			return -1;
		}
		else if(id > o.getId()) 
		{
			return 1;
		}
		else
		{
			return 0;	
		}
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public PizzaLover favorite(PizzaPlace pp) throws UnratedFavoritePizzaPlaceException {
		if(((PizzaPlaceImpl)pp).getRates().containsKey(this)){ // if this student rate the PizzaPlase then add to favorites
			favorites.add(pp);
		}
		else 
		{
			throw new UnratedFavoritePizzaPlaceException();
		}
		return this;
	}

	@Override
	public Collection<PizzaPlace> favorites() {
		return favorites;
	}

	@Override
	public PizzaLover addFriend(PizzaLover pl) throws SamePizzaLoverException, ConnectionAlreadyExistsException {
		if(pl.equals(this)) { // Check if its the same pizzaLover
			throw new SamePizzaLoverException();
		}
		else if(friends.contains(pl)) { // Check if they are already friends
			throw new ConnectionAlreadyExistsException();
		}
		else 
		{
			friends.add(pl);
		}
		
		return this;
	}

	@Override
	public Set<PizzaLover> getFriends() {
		return friends;
	}

	@Override
	public Collection<PizzaPlace> favoritesByRating(int rLimit) {
		Set<PizzaPlace> SortedList = new TreeSet<PizzaPlace>(new CompareFavoritesByRating()); 
		// We decide to sort the collection above using comparator in class CompareFavoritesByRating
		this.favorites.forEach((shop) -> {
			if(((PizzaPlaceImpl)shop).averageRating() >= rLimit) // add element if the rating above the limit
			{
				SortedList.add(shop);
			}
		});
		return SortedList;
	}

	@Override
	public Collection<PizzaPlace> favoritesByDist(int dLimit) {
		Set<PizzaPlace> SortedList = new TreeSet<PizzaPlace>(new CompareFavoritedByDist());
		// We decide to sort the collection above using comparator in class CompareFavoritedByDist
		this.favorites.forEach((shop) -> {
			if(((PizzaPlaceImpl)shop).distance() <= dLimit) // add element if the distance below the limit
			{
				SortedList.add(shop);
			}
		});
		return SortedList;
	}

	public String getName() {
		return this.name;
	}

	@Override
	public String toString() { // We return String with all details needed
		String favoritesNames = "";
		String friendsNames = "";
		for(PizzaPlace n : this.favorites) 
		{
			favoritesNames = favoritesNames.concat(((PizzaPlaceImpl)n).getName() + ", ");
		}
		for(PizzaLover n : this.friends) 
		{
			friendsNames = friendsNames.concat(((PizzaLoverImpl)n).getName() + ", ");
		}
		return "\n--- PizzaLover ---\n"
				+ "name : " + name + "\n" 
				+ "id : " + id + "\n"
				+ "favorites : " + favoritesNames + "\n" 
				+ "friends : " + friendsNames  ;
	}
}
