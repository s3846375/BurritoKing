package model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * The Restaurant class tracks the state of food prices, all placed orders, and remained fries on the food stack.
 */
public class Restaurant {
    private String name;
    // keep food prices
    private static final HashMap<String, Double> priceMap = new HashMap<String, Double>();
    // track left-overs from previous order
    private int remainedFries;
    private static final int mealDiscount = 3;

    public Restaurant(String name) {
        priceMap.put("Burrito", 7.0);
        priceMap.put("Fries", 4.0);
        priceMap.put("Soda", 2.5);
        this.remainedFries = 0;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static int getDiscount() {
        return mealDiscount;
    }

    public static double getPrice(String foodName) {
        return priceMap.get(foodName);
    }

    public int getRemainedFries() {
        return this.remainedFries;
    }


    /**
     * Calculates the preparation time for the order passed to the kitchen.
     */
    public double getPrepareTime(Order order) {

        double prepareTime = 0.0;

        HashMap<String, Integer> cookables = this.mapToCookables(order);
        for (String food : cookables.keySet()) {
            if (food == "Burritos")
                prepareTime = Math.max(prepareTime, getBurritoTime(cookables.get(food)));

            else if (food == "Fries")
                prepareTime = Math.max(prepareTime, getFriesTime(cookables.get(food)));
        }
        return prepareTime;
    }

    /**
     * The method that maps a list of ordered food items to a map
     * containing the number of different items to be cooked.
     * Burritos and Fries are items that need to be cooked. Soda is not cookable.
     * For example, an order of one meal will be mapped to {Burrito=1, Fries=1}
     * An order of two burritos, one soda, and one meal will be mapped to {Burrito=3, Fries=1}
     **/
    public HashMap<String, Integer> mapToCookables(Order order) {
        LinkedList<FoodItem> items = order.getItems();
        int numOfBurritos = 0;
        int numOfFries = 0;
        for (FoodItem item : items) {
            if (item instanceof Burrito) {
                numOfBurritos += item.getQuantity();
            } else if (item instanceof Fries) {
                numOfFries += item.getQuantity();
            } else if (item instanceof Meal) {
                numOfBurritos += item.getQuantity();
                numOfFries += item.getQuantity();
            }
        }
        HashMap<String, Integer> mapped = new HashMap<String, Integer>();
        mapped.put("Burritos", numOfBurritos);
        mapped.put("Fries", numOfFries);
        return mapped;
    }

    /**
     * Calculates the preparation time for burritos.
     * Burritos can be cooked in a batch of 2.
     */
    public double getBurritoTime(int qty) {
        return Math.ceil(qty / (double) Burrito.getBatchSize()) * Burrito.getBatchPrepTime();
    }

    /**
     * Calculates the preparation time for fries.
     * Fries can be cooked in a batch of 5.
     */
    public double getFriesTime(int qty) {
        double friesTime = 0.0;
        int friesBatchSize = Fries.getBatchSize();
        if (qty <= remainedFries) {
            friesTime = 0.0;
        } else {
            if ((qty - remainedFries) % friesBatchSize == 0) {
                friesTime = ((qty - remainedFries) / friesBatchSize) * Fries.getBatchPrepTime();
            } else {
                friesTime = Math.ceil((qty - remainedFries) / (double) friesBatchSize) * Fries.getBatchPrepTime();
            }
        }
        return friesTime;
    }

    /**
     * Will check if order has fries and update the remaining Fries
     **/
    public void updateRemainedFries(Order order) {
        int orderFriesQty = 0;
        int friesBatchSize = Fries.getBatchSize();

        HashMap<String, Integer> cookables = this.mapToCookables(order);

        for (String food : cookables.keySet()) {
            if (food == "Fries")
                orderFriesQty = cookables.get(food);

        }

        if (orderFriesQty != 0) {
            if (orderFriesQty <= remainedFries) {
                remainedFries -= orderFriesQty;
            } else {
                // When ordered fries qty is larger than remained fries and is divisible by friesBatchSize
                if ((orderFriesQty - remainedFries) % friesBatchSize == 0) {
                    remainedFries = 0;

                } else { // When ordered fries qty is larger than remained fries and is not divisible by friesBatchSize
                    remainedFries = friesBatchSize - (orderFriesQty - remainedFries) % friesBatchSize;
                }
            }
        }
    }

}
