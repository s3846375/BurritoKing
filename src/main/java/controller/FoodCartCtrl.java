package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class FoodCartCtrl implements Initializable {

    private Scene scene;
    private Stage stage;
    private Parent root;

    @FXML
    private TextArea orderSumTextArea;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        orderSumTextArea.setText(getOrderSummary());
    }

    /**
     * Collect the whole active order information into a string for display.
     **/
    public String getOrderSummary() {
        Order activeOrder = SessionManager.getInstance().getActiveOrder();
        Restaurant restaurant = SessionManager.getInstance().getRestaurant();

        double price = activeOrder.getTotalPrice();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < activeOrder.getItems().size(); i++) {
            FoodItem item = activeOrder.getItems().get(i);
            sb.append(item.getClass().getSimpleName() + "  x" + item.getQuantity() + "\n");
        }
        sb.append("---------------------\n");
        sb.append("Total cost is $" + price + "\n\n");
        sb.append("Prepare Time: " + restaurant.getPrepareTime(activeOrder) + " minutes");
        return sb.toString();
    }

    /**
     * Goes back to food menu to let user add more food items to the cart
     **/
    @FXML
    public void goToFoodMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FoodMenu.fxml"));
        root = loader.load();
        showScene(event);
    }

    @FXML
    public void goToEditFoodCart(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("FoodCartEdit.fxml"));
        showScene(event);
    }

    @FXML
    public void goToPayment(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("OrderPayment.fxml"));
        showScene(event);
    }

    public void showScene(ActionEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
