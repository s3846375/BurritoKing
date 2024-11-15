package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TotalCostTest {

    private Restaurant restaurant;
    private Order activeOrder;

    @BeforeEach
    void setUp() {
        restaurant = new Restaurant("Burrito Restaurant");
        activeOrder = new Order();
    }

    /**
     * Test for ordering 1 Burrito, 2 Fries
     **/
    @Test
    void totalCostWithoutMeal() {
        activeOrder.addFoodItem(new Burrito(Restaurant.getPrice("Burrito"), 1));
        activeOrder.addFoodItem(new Fries(Restaurant.getPrice("Fries"), 2));

        assertEquals(15, activeOrder.getTotalPrice());
    }

    /**
     * Test for ordering 1 Burrito, 1 Fries, 1 Soda, 2 Burrito
     **/
    @Test
    public void totalCostWithRepeatFood() {
        activeOrder.addFoodItem(new Burrito(Restaurant.getPrice("Burrito"), 1));
        activeOrder.addFoodItem(new Fries(Restaurant.getPrice("Fries"), 1));
        activeOrder.addFoodItem(new Soda(Restaurant.getPrice("Soda"), 1));
        activeOrder.addFoodItem(new Burrito(Restaurant.getPrice("Burrito"), 2));

        assertEquals(27.5, activeOrder.getTotalPrice());
    }

    /**
     * Test for ordering 1 Burrito, 5 Fries, 2 Meal
     **/
    @Test
    public void totalCostWithMeal() {
        activeOrder.addFoodItem(new Burrito(Restaurant.getPrice("Burrito"), 1));
        activeOrder.addFoodItem(new Fries(Restaurant.getPrice("Fries"), 5));
        double price = Restaurant.getPrice("Burrito") + Restaurant.getPrice("Fries")
                + Restaurant.getPrice("Soda") - Restaurant.getDiscount();
        activeOrder.addFoodItem(new Meal(price, 2));

        assertEquals(48, activeOrder.getTotalPrice());
    }

}