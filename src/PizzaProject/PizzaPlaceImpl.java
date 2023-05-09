package PizzaProject;
import java.util.HashMap;
import java.util.Set;

import PizzaProject.Provided.PizzaLover;
import PizzaProject.Provided.PizzaPlace;

public class PizzaPlaceImpl implements PizzaPlace {
	
	private int id;
	private String name;
	private int distFromTech;
	private Set<String> menu;
	private HashMap<PizzaLover,Integer> rates;
	
	public PizzaPlaceImpl(int id, String name, int distFromTech, Set<String> menu) // Constructor
	{
		this.id = id;
		this.name = name;
		this.distFromTech = distFromTech;
		this.menu = menu;
		this.rates = new HashMap<PizzaLover,Integer>();
		
	}

	@Override
	public int compareTo(PizzaPlace o) { // Sort the collections we have  by the natural order
		if(this.id < o.getId()) 
		{
			return -1;
		}
		else if(this.id > o.getId()) 
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
	public int distance() {
		return distFromTech;
	}

	@Override
	public PizzaPlace rate(PizzaLover pl, int r) throws RateRangeException {
		
		if(r<0 || r>5)  // Check if is the right value
		{
			throw new RateRangeException();
		}else 
		{
			if(rates.containsKey(pl)) // Check if PizzaLover already rated 
			{
				rates.replace(pl, r); 
			}
			else 
			{
				rates.put(pl, r);
			}
			
		}
		return this;
	}

	@Override
	public int numberOfRates() {
		return rates.size();
	}

	@Override
	public double averageRating() {
		int size = rates.size();
		float sum = 0;
		if(size <= 0) 
		{
			return sum;
		}
		else 
		{
			for(int i : rates.values()) 
			{
				sum += i;
			}
			sum = sum/size; // calculate the average
			return sum;
		}
	}
	
	@Override
	public boolean equals(Object o) {
		return id == ((PizzaPlace)o).getId(); 
	}

	public String getName() {
		return name;
	}

	public Set<String> getMenu() {
		return menu;
	}

	public HashMap<PizzaLover,Integer> getRates() {
		return rates;
	}

	@Override
	public String toString() // We return String with all details needed
	{ 
		return "\n--- PizzaPlace ---\n"
				+ "name : " + name + "\n" 
				+ "id : " + id + "\n"
				+ "menu : " + this.menu + "\n" 
				+ "rates : " + this.rates.values()  ;
	}

}
