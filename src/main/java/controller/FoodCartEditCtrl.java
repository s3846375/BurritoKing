package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.FoodItem;
import model.Order;
import model.SessionManager;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class FoodCartEditCtrl implements Initializable {
    private Scene scene;
    private Stage stage;
    private Parent root;

    @FXML
    private ListView<String> foodCartView;
    @FXML
    private Label messageLabel;

    /**
     * Get all the food item names in current active order and add to an ArrayList to display in ListView for user to select.
     **/
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        Order activeOrder = SessionManager.getInstance().getActiveOrder();
        List<String> foodList = activeOrder.getItems().stream()
                .map(item -> item.getClass().getSimpleName())
                .collect(Collectors.toList());

        // Set display text font and add the display items to FXListView
        foodCartView.setStyle("-fx-font-family: monospace; -fx-font-size: 16px; -fx-font-weight: bold;");
        foodCartView.getItems().setAll(foodList);
    }

    /**
     * Select the desired food to edit quantity and pass the EditQty scene to chose new quantity.
     **/
    @FXML
    public void goToEditQty(ActionEvent event) throws IOException {
        String selectedFood = foodCartView.getSelectionModel().getSelectedItem();
        if (selectedFood == null) {
            messageLabel.setText("Please select a food item to edit");
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditQty.fxml"));
            root = loader.load();

            // Pass the food name and current food qty to the scene
            FoodCartEditQtyCtrl foodCartEditQtyCtrl = loader.getController();
            foodCartEditQtyCtrl.setfoodName(selectedFood);
            foodCartEditQtyCtrl.setOldQty(getCurrentFoodQty());

            showScene(event);
        }
    }

    public int getCurrentFoodQty() {
        int selectedFoodIndex = foodCartView.getSelectionModel().getSelectedIndex();
        Order activeOrder = SessionManager.getInstance().getActiveOrder();
        FoodItem item = activeOrder.getItems().get(selectedFoodIndex);
        return item.getQuantity();
    }

    public void showScene(ActionEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
