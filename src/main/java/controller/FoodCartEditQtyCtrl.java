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

public class FoodCartEditQtyCtrl implements Initializable {

    private Scene scene;
    private Stage stage;
    private Parent root;

    private int oldQty;
    private int newQty;

    private String foodName;

    @FXML private Label foodLabel;
    @FXML private Spinner<Integer> qtySpinner;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100);
        valueFactory.setValue(oldQty);
        qtySpinner.setValueFactory(valueFactory);
        qtySpinner.valueProperty().addListener( new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> arg0, Integer arg1, Integer arg2) {
                newQty = qtySpinner.getValue();
            }
        });

    }

    public void setOldQty(int oldQty) {
        this.oldQty = oldQty;
        if (qtySpinner != null) {
            qtySpinner.getValueFactory().setValue(oldQty);
        }
    }

    /**
     * Goes back Food menu scene after editing order food quantity
     * **/
    @FXML
    public void goToFoodMenu(ActionEvent event) throws IOException {
        // Add the food item to the active order
        updateOrder(foodName, newQty);

        root = FXMLLoader.load(getClass().getResource("FoodMenu.fxml"));
        showScene(event);
    }

    /**
     * Update the active order stored in the session manager.
     **/
    public void updateOrder(String foodName, int newQty) {
        if (foodName.equals("Burrito"))
            SessionManager.getInstance().updateFoodItem(foodName, newQty);
        else if (foodName.equals("Fries"))
            SessionManager.getInstance().updateFoodItem(foodName, newQty);
        else if (foodName.equals("Soda"))
            SessionManager.getInstance().updateFoodItem(foodName, newQty);
        else if (foodName.equals("Meal"))
            SessionManager.getInstance().updateFoodItem(foodName, newQty);

    }

    public void setfoodName(String foodName) {
        this.foodName = foodName;
        this.foodLabel.setText(foodName + ":");
    }

    public void showScene(ActionEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



}
