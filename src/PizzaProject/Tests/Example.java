package PizzaProject.Tests;

import PizzaProject.PizzaWorldImpl;
import PizzaProject.Provided.PizzaLover;
import PizzaProject.Provided.PizzaLover.*;
import PizzaProject.Provided.PizzaPlace;
import PizzaProject.Provided.PizzaPlace.PizzaPlaceNotInSystemException;
import PizzaProject.Provided.PizzaPlace.RateRangeException;
import PizzaProject.Provided.PizzaWorld;
import PizzaProject.Provided.PizzaWorld.ImpossibleConnectionException;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Predicate;

import static org.junit.Assert.*;


public class Example {
    @Test
    public void ExampleTest() {
        PizzaWorld network = new PizzaWorldImpl();
        PizzaLover s1 = null, s2 = null;
        try {
            s1 = network.joinNetwork(100, "Anne");
            s2 = network.joinNetwork(300, "Ben");
        } catch (PizzaLoverAlreadyInSystemException e) {
        	fail();
        }

        Set<String> menu1 = new HashSet<>(), menu2 = new HashSet<>();
        menu1.add("Hamburger");
        menu1.add("Fries");
        menu2.add("Steak");
        menu2.add("Fries");
        menu2.add("Orange Juice");
        PizzaPlace r1 = null, r2 = null, r3 = null;
        try {
            r1 = network.addPizzaPlace(10, "BBB", 12, menu1);
            r2 = network.addPizzaPlace(12, "Bob's place", 5, menu2);
            r3 = network.addPizzaPlace(14, "Ben's hut", 1, menu1);
        } catch (PizzaPlace.PizzaPlaceAlreadyInSystemException e) {
        	fail();
        }

        try {
            r1.rate(s1, 4)
                    .rate(s2, 5);

            r2.rate(s1, 2)
                    .rate(s1, 3)
                    .rate(s2, 4);
            r3.rate(s2, 4);
        } catch (RateRangeException e) {
        	fail();
        }

        assertEquals(2, r1.numberOfRates(), 0);
        assertEquals(3.5, r2.averageRating(), 0);

        try {
            s1.favorite(r1)
                    .favorite(r2);
            s2.favorite(r2)
                    .favorite(r3);
        } catch (UnratedFavoritePizzaPlaceException e) {
        	fail();
        }

        try {
            PizzaPlace r1Favorites = network.getPizzaPlace(10);
            PizzaPlace r2Favorites = network.getPizzaPlace(12);
            Predicate<PizzaPlace> p1 = r -> Objects.equals(r, r1Favorites);
            Predicate<PizzaPlace> p2 = r -> Objects.equals(r, r2Favorites);
            Collection<PizzaPlace> s1Favorites = s1.favorites();
            assertEquals(2, s1Favorites.size());
            assertTrue(s1Favorites.stream().anyMatch(p1) && s1Favorites.stream().anyMatch(p2));
            Collection<PizzaPlace> s2Favorites = s2.favorites();
            assertTrue(s2Favorites.stream().noneMatch(p1) && s2Favorites.stream().anyMatch(p2));

        } catch (PizzaPlace.PizzaPlaceNotInSystemException e) {
        	fail();
        }

        Iterator<PizzaPlace> s1RateIterator = s1.favoritesByRating(1).iterator();
        Iterator<PizzaPlace> s2DistIterator = s2.favoritesByDist(20).iterator();

        assertEquals(s1RateIterator.next(), r1);
        assertEquals(s1RateIterator.next(), r2);
        assertEquals(s2DistIterator.next(), r3);
        assertEquals(s2DistIterator.next(), r2);

        try {
            network.addConnection(s1, s2);
        } catch (ConnectionAlreadyExistsException | PizzaLoverNotInSystemException | SamePizzaLoverException e) {
        	fail();
        }

        try {
            assertTrue(network.getRecommendation(s1, r3, 1));
            assertTrue(network.getRecommendation(s1, r1, 0));
            assertFalse(network.getRecommendation(s1, r3, 0));
        } catch (PizzaLoverNotInSystemException | PizzaPlaceNotInSystemException | ImpossibleConnectionException e) {
        	fail();
        }
    }
}