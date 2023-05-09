package PizzaProject.Provided;

/**
 * Each instance of the PizzaPlace class has an id, a name, distance
 * from the Technion, and holds a collection of menu items.
 * The id is unique for every pizza place.
 * */
public interface PizzaPlace extends Comparable<PizzaPlace> {
	
	@SuppressWarnings("serial")
    class PizzaPlaceAlreadyInSystemException    extends Exception {}
	@SuppressWarnings("serial")
    class PizzaPlaceNotInSystemException        extends Exception {}
	@SuppressWarnings("serial")
    class RateRangeException                    extends Exception {}

    /**
     * @return the id of the pizza place.
     * */
    int getId();

    /**
     * @return the distance from the Technion.*/
    int distance();

    /**
     * rate the pizza place by a pizza lover
     * @return the object to allow concatenation of function calls.
     * @param pl - the pizza lover rating the pizza place
     * @param r - the rating
     * */
    PizzaPlace rate(PizzaLover pl, int r)
            throws RateRangeException;

    /**
     * @return the number of rating the pizza place has received
     * */
    int numberOfRates();

    /**
     * @return the pizza place's average rating
     * */
    double averageRating();

    /**
     * @return the pizza place's description as a string in the following format:
     * <format>
     * PizzaPlace: <name>.
     * Id: <id>.
     * Distance: <dist>.
     * Menu: <menuItem1, menuItem2, menuItem3...>.
     * </format>
     * Note: Menu items are ordered by lexicographical order, asc.
     * Note: There is no new-line at the end of the string returned!
     *
     * Example:
     *
     * PizzaPlace: BBB.
     * Id: 1.
     * Distance: 5.
     * Menu: Cola, French Fries, Steak.
     *
     * */

    String toString();
}
