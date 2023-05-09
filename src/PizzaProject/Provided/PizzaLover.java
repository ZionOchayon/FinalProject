package PizzaProject.Provided;

import java.util.Collection;
import java.util.Set;

/**
 * Each instance of the PizzaLover class has an id, a name, and holds
 * a collection of favorite pizza places, and a collection of pizza lover friends.
 * The id is unique for every pizza lover.
 * */
public interface PizzaLover extends Comparable<PizzaLover> {
	@SuppressWarnings("serial")
	class SamePizzaLoverException               extends Exception {}
    @SuppressWarnings("serial")
	class PizzaLoverAlreadyInSystemException    extends Exception {}
    @SuppressWarnings("serial")
    class PizzaLoverNotInSystemException        extends Exception {}
    @SuppressWarnings("serial")
    class UnratedFavoritePizzaPlaceException    extends Exception {}
    @SuppressWarnings("serial")
    class ConnectionAlreadyExistsException      extends Exception {}

    /**
     * @return the id of the pizza lover.
     * */
    int getId();

    /**
     * the pizza lover favorites a pizza place
     *
     * @return the object to allow concatenation of function calls.
     * @param pp - the pizza place being favored by the pizza lover
     * */
    PizzaLover favorite(PizzaPlace pp)
            throws UnratedFavoritePizzaPlaceException;

    /**
     * @return the pizza lover's favorite pizza places
     * */
    Collection<PizzaPlace> favorites();

    /**
     * adding a pizza lover as a friend
     * @return the object to allow concatenation of function calls.
     * @param pl - the pizza lover being "friend-ed"
     * */
    PizzaLover addFriend(PizzaLover pl)
            throws SamePizzaLoverException, ConnectionAlreadyExistsException;

    /**
     * @return the pizza lover's set of friends
     * */
    Set<PizzaLover> getFriends();

    /**
     * @return the pizza lover's favorite pizza places, ordered by rating.
     * @param rLimit - the limit of rating under which pizza places will not be included.
     * */
    Collection<PizzaPlace> favoritesByRating(int rLimit);

    /**
     * @return the pizza lover's favorite pizza places, ordered by distance and then rating.
     * @param dLimit - the limit of distance above which pizza places will not be included.
     * */
    Collection<PizzaPlace> favoritesByDist(int dLimit);

    /**
     * @return the pizza lover's description as a string in the following format:
     * <format>
     * Pizza lover: <name>.
     * Id: <id>.
     * Favorites: <pizzaPlaceName1, pizzaPlaceName2, pizzaPlaceName3...>
     * </format>
     * Note: favorite pizza places are ordered by lexicographical order, asc.
     * Note: There is no new-line at the end of the string returned!
     *
     * Example:
     *
     * Pizza lover: Oren.
     * Id: 236703.
     * Favorites: A's Pizza, Pizza salon.
     *
     * */

    String toString();
}
