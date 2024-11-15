package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class FoodMenuCtrl implements Initializable {

    private Scene scene;
    private Stage stage;
    private Parent root;

    @FXML
    private Label messageLabel;
    @FXML
    private Button mealButton;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        if (SessionManager.getInstance().getCurrentUser() instanceof UserVIP) {
            mealButton.setVisible(true); // Show Meal button for VIP
        } else {
            mealButton.setVisible(false);  // Hide Meal button for non-VIP
        }
    }

    @FXML
    public void clickFood(ActionEvent event) throws IOException {
        Button clickedButton = (Button) event.getSource();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ChooseQty.fxml"));
        root = loader.load();

        //create instance of ChooseQtyControl and pass the food name to the scene
        ChooseQtyCtrl chooseQtyCtrl = loader.getController();
        chooseQtyCtrl.setFoodName(clickedButton.getText());

        showScene(event);
    }


    @FXML
    public void goToFoodCart(ActionEvent event) throws IOException {
        if (SessionManager.getInstance().getActiveOrder().getItems().size() == 0) {
            messageLabel.setText("There are no food items in your cart.");
        } else {
            root = FXMLLoader.load(getClass().getResource("FoodCart.fxml"));
            showScene(event);
        }
    }

    public void showScene(ActionEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
