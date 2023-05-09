package PizzaProject.Provided;

import java.util.Collection;
import PizzaProject.Provided.PizzaLover.*;
import PizzaProject.Provided.PizzaPlace.*;
import java.util.Set;

/**
 * Each instance of the PizzaWorld class holds a collection of registered pizza lovers,
 * a collection of registered pizza places, and manages different relations between
 * the two.
 * */
public interface PizzaWorld {

	@SuppressWarnings("serial")
    class ImpossibleConnectionException     extends Exception {}

    /**
     * add a pizza lover to the network.
     *
     * @param id - the id of the pizza lover
     * @param name - the name of the pizza lover
     * @return the PizzaLover added
     * */
    PizzaLover joinNetwork(int id, String name)
            throws PizzaLoverAlreadyInSystemException;

    /**
     * add a pizza place to the network
     * @param id - the id of the pizza place
     * @param name - the name of the pizza place
     * @param dist - the distance of the pizza place from the Technion
     * @param menu - the set of menu items of the pizza place
     * @return the PizzaPlace added
     * */
    PizzaPlace addPizzaPlace(int id, String name, int dist, Set<String> menu)
            throws PizzaPlaceAlreadyInSystemException;

    /**
     * @return a collection of all pizza lovers in the network
     * */
    Collection<PizzaLover> registeredPizzaLovers();

    /**
     * @return a collection of all pizza places in the network
     * */
    Collection<PizzaPlace> registeredPizzaPlaces();

    /**
     * @return the pizza lover in the network by the id given
     * @param id - the id of the pizza lover to look for in the network
     * */
    PizzaLover getPizzaLover(int id)
            throws PizzaLoverNotInSystemException;

    /**
     * @return the pizza place in the network by the id given
     * @param id - the id of the pizza place to look for in the network
     * */
    PizzaPlace getPizzaPlace(int id)
            throws PizzaPlaceNotInSystemException;

    /**
     * add a connection of friendship between the two pizza lovers received.
     * friendship is a symmetric relation!
     *
     * @return the object to allow concatenation of function calls.
     * @param pl1 - the first pizza lover
     * @param pl2 - the second pizza lover
     * */
    PizzaWorld addConnection(PizzaLover pl1, PizzaLover pl2)
            throws PizzaLoverNotInSystemException, ConnectionAlreadyExistsException, SamePizzaLoverException;

    /**
     * returns a collection of pizza places favored by the friends of the received pizza lover,
     * ordered by rating
     *
     * @param pl - pl's friends' favorite pizza places are considered
     * */
    Collection<PizzaPlace> favoritesByRating(PizzaLover pl)
            throws PizzaLoverNotInSystemException;

    /**
     * returns a collection of pizza places favored by the friends of the received pizza lover,
     * ordered by distance from the Technion
     *
     * @param pl - the pizza lover whom in relation to him, favored pizza places by his friends are considered
     * */
    Collection<PizzaPlace> favoritesByDist(PizzaLover pl)
            throws PizzaLoverNotInSystemException;

    /**
     * check whether the pizza place received is t-recommended by the received pizza lover (definition in the PDF)
     *
     * @param pl - the pizza lover (dis)recommending the pizza place
     * @param pp - the pizza place being (dis)recommended
     * @param t - the limit in the t-recommendation
     *
     * @return whether pl t-recommends pp
     * */
    boolean getRecommendation(PizzaLover pl, PizzaPlace pp, int t)
            throws PizzaLoverNotInSystemException, PizzaPlaceNotInSystemException, ImpossibleConnectionException;

    /**
     * @return the network's description as a string in the following format:
     * <format>
     * Registered pizza lovers: <pizzaLoverId1, pizzaLoverId2, pizzaLoverId3...>.
     * Registered pizza places: <pizzaPlaceId1, pizzaPlaceId2, pizzaPlaceId3...>.
     * Pizza lovers:
     * <pizzaLover1Id> -> [<friend1Id, friend2Id, friend3Id...>].
     * <pizzaLover2Id> -> [<friend1Id, friend2Id, friend3Id...>].
     * ...
     * End pizza lovers.
     * </format>
     * Note: pizza lovers, pizza places and friends' ids are ordered by natural integer order, asc.*
     * Note: There is no new-line at the end of the string returned!
     * Note: If there are empty collections in the system (e.g. no students), then you should print nothing for these collection!
     *       For example:
     * Pizza lovers:
     * End pizza lovers.
     *
     * Example:
     *
     * Registered pizza lovers: 1, 236703, 555555.
     * Registered pizza places: 12, 13.
     * Pizza lovers:
     * 1 -> [236703, 555555].
     * 236703 -> [1].
     * 555555 -> [1].
     * End pizza lovers.
     * */

    String toString();
}
