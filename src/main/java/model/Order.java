package model;

import java.util.LinkedList;

/**
 * The class that maintains a list of food items ordered by a user
 **/
public class Order {
    private LinkedList<FoodItem> items; //FoodItem(double price, int quantity)

    public Order() {
        items = new LinkedList<FoodItem>();
    }

    public void addFoodItem(FoodItem newItem) {
        for (FoodItem item : items) {
            if (item.getClass().getSimpleName().equals(newItem.getClass().getSimpleName())) {
                item.addQuantity(newItem.getQuantity());
                return;
            }
        }
        items.add(newItem);
    }

    /**
     * Finds the matching food item to remove or update quantity.
     * **/
    public void updateFoodItem(String foodName, int quantity) {
        for (int i = 0; i < items.size(); i++) {
            FoodItem item = items.get(i);
            if (item.getClass().getSimpleName().equals(foodName)) {
                if (quantity == 0) {
                    items.remove(i);
                } else {
                    item.setQuantity(quantity);
                }
                return; // Exit after processing the matching item
            }
        }
    }

    public LinkedList<FoodItem> getItems() {
        return this.items;
    }

    /**
     * The method for calculating the total price of an order
     **/
    public double getTotalPrice() {
        double sum = 0.0;
        for (FoodItem item : items) {
            sum += item.getTotalPrice();
        }
        return sum;
    }

}

