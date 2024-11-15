package model;

import java.util.ArrayList;

public class SessionManager {

    private static SessionManager instance;
    private Restaurant restaurant;
    private Order activeOrder;
    private User currentUser;
    private ArrayList<OrderDetail> orderDetails;

    private SessionManager() {
        restaurant = new Restaurant("Burrito Restaurant");
        activeOrder = new Order();
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void setUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public Order getActiveOrder() {
        return activeOrder;
    }

    public void addFoodItem(FoodItem item) {
        activeOrder.addFoodItem(item);
    }

    public void updateFoodItem(String foodName, int quantity) {
        activeOrder.updateFoodItem(foodName, quantity);
    }

    public void setOrderDetails(ArrayList<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public ArrayList<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    /**
     * Adds a new order to the list of history orders
     */
    public void addOrderDetailToHistory(OrderDetail orderDetail) {
        orderDetails.add(orderDetail);
    }

    public void resetOrder() {
        activeOrder = new Order();
    }
}
