package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PrepareTimeTest {

    private Restaurant restaurant;
    private Order activeOrder;

    @BeforeEach
    void setUp() {
        restaurant = new Restaurant("Burrito Restaurant");
        activeOrder = new Order();
    }

    /**
     * Test order prepare time when there is no unsold Fries
     **/
    @Test
    public void prepareTimeWithNoUnsoldFries() {
        // First order has 3 Burrito, 1 Fries
        activeOrder.addFoodItem(new Burrito(Restaurant.getPrice("Burrito"), 3));
        activeOrder.addFoodItem(new Fries(Restaurant.getPrice("Fries"), 1));

        assertEquals(18, restaurant.getPrepareTime(activeOrder));
    }


    /**
     * Test the second order prepare time when there is 4 serves of fries left after first order
     **/
    @Test
    public void prepareTimeWithUnsoldFries() {
        // First order has 3 Burrito, 1 Fries
        activeOrder.addFoodItem(new Burrito(Restaurant.getPrice("Burrito"), 3));
        activeOrder.addFoodItem(new Fries(Restaurant.getPrice("Fries"), 1));
        // Update unsold Fries after first order
        restaurant.updateRemainedFries(activeOrder);

        // Create a second new order and order 2 Burrito, 12 Fries
        activeOrder = new Order();
        activeOrder.addFoodItem(new Burrito(Restaurant.getPrice("Burrito"), 2));
        activeOrder.addFoodItem(new Fries(Restaurant.getPrice("Fries"), 12));

        assertEquals(16, restaurant.getPrepareTime(activeOrder));
    }

    /**
     * Test the remain fries after ordering 6 fries
     **/
    @Test
    public void finalRemainFriesTest1() {
        activeOrder.addFoodItem(new Burrito(Restaurant.getPrice("Burrito"), 3));
        activeOrder.addFoodItem(new Fries(Restaurant.getPrice("Fries"), 6));
        restaurant.updateRemainedFries(activeOrder);

        assertEquals(4, restaurant.getRemainedFries());
    }

    /**
     * Test the remain fries after two orders
     **/
    @Test
    public void finalRemainFriesTest2() {
        // First order has 3 Burrito, 1 Fries
        activeOrder.addFoodItem(new Burrito(Restaurant.getPrice("Burrito"), 3));
        activeOrder.addFoodItem(new Fries(Restaurant.getPrice("Fries"), 1));
        // Update unsold Fries after first order
        restaurant.updateRemainedFries(activeOrder);

        // Create a second new order and order 2 Burrito, 12 Fries
        activeOrder = new Order();
        activeOrder.addFoodItem(new Burrito(Restaurant.getPrice("Burrito"), 2));
        activeOrder.addFoodItem(new Fries(Restaurant.getPrice("Fries"), 12));
        // Update unsold Fries after second order
        restaurant.updateRemainedFries(activeOrder);

        assertEquals(2, restaurant.getRemainedFries());
    }

    /**
     * Test the remain fries after two orders
     **/
    @Test
    public void finalRemainFriesTest3() {
        // First order has 4 Fries
        activeOrder.addFoodItem(new Fries(Restaurant.getPrice("Fries"), 4));
        restaurant.updateRemainedFries(activeOrder);

        // Create a second new order and order 7 Fries
        activeOrder = new Order();
        activeOrder.addFoodItem(new Fries(Restaurant.getPrice("Fries"), 7));
        restaurant.updateRemainedFries(activeOrder);

        assertEquals(4, restaurant.getRemainedFries());
    }

}