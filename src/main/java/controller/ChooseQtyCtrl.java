package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChooseQtyCtrl implements Initializable {

    private Scene scene;
    private Stage stage;
    private Parent root;

    private int quantity;
    private String foodName;

    @FXML
    private Label foodLabel;
    @FXML
    private Spinner<Integer> qtySpinner;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100);
        valueFactory.setValue(1);
        qtySpinner.setValueFactory(valueFactory);
        quantity = qtySpinner.getValue();
        qtySpinner.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> arg0, Integer arg1, Integer arg2) {
                quantity = qtySpinner.getValue();
            }
        });
    }

    /**
     * Gets the selected food name from previous Food menu scene
     * **/
    public void setFoodName(String foodname) {
        this.foodName = foodname;
        this.foodLabel.setText(foodname + ":");
    }

    /**
     * Goes back Food menu scene after choosing quantity
     * **/
    @FXML
    public void goToFoodMenu(ActionEvent event) throws IOException {
        addFoodToCart(foodName, quantity);
        root = FXMLLoader.load(getClass().getResource("FoodMenu.fxml"));
        showScene(event);
    }

    /**
     * Adds the food item to the active order session manager is holding
     * **/
    public void addFoodToCart(String foodName, int qty) {
        if (foodName.equals("Burrito"))
            SessionManager.getInstance().addFoodItem(new Burrito(Restaurant.getPrice(foodName), qty));
        else if (foodName.equals("Fries"))
            SessionManager.getInstance().addFoodItem(new Fries(Restaurant.getPrice(foodName), qty));
        else if (foodName.equals("Soda"))
            SessionManager.getInstance().addFoodItem(new Soda(Restaurant.getPrice(foodName), qty));
        else if (foodName.equals("Meal")) {
            double price = Restaurant.getPrice("Burrito") + Restaurant.getPrice("Fries")
                    + Restaurant.getPrice("Soda") - Restaurant.getDiscount();
            SessionManager.getInstance().addFoodItem(new Meal(price, qty));
        }
    }

    public void showScene(ActionEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
