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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.DatabaseManager;
import model.OrderDetail;
import model.SessionManager;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UpgradeVIPCtrl implements Initializable {

    private Scene scene;
    private Stage stage;
    private Parent root;

    @FXML
    private Label messageLabel;
    @FXML
    private TextField emailTextField;
    @FXML
    private Button yesButton, noButton,logOutButton;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        logOutButton.setVisible(false);
    }

    @FXML
    public void clickYes(){
        String username = SessionManager.getInstance().getCurrentUser().getUsername();
        String email = emailTextField.getText();
        if (email.isEmpty()) {
            messageLabel.setText("Please enter a valid email address");
        } else {
            DatabaseManager.saveVIPtoDB(username, email);
            messageLabel.setText(username + " Upgrade Success! Please logout and login again.");
            logOutButton.setVisible(true);
            noButton.setVisible(false);
            yesButton.setVisible(false);
        }
    }

    @FXML
    public void goToMainMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        showScene(event);
    }

    @FXML
    public void goToLogin(ActionEvent event) throws IOException {
        // Save current user all orders to the orderHistory table in database
        String username = SessionManager.getInstance().getCurrentUser().getUsername();
        ArrayList<OrderDetail> orderDetails = SessionManager.getInstance().getOrderDetails();
        DatabaseManager.saveOrderToDB(username, orderDetails);

        root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        showScene(event);
    }

    public void showScene(ActionEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}