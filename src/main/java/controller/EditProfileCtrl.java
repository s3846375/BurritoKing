package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

public class EditProfileCtrl implements Initializable {

    private Scene scene;
    private Stage stage;
    private Parent root;

    private String username;

    @FXML
    private Label messageLabel, usernameLabel;
    @FXML
    private TextField passwordField, firstNameField, lastNameField;

    public void initialize(URL arg0, ResourceBundle arg1) {
        username = SessionManager.getInstance().getCurrentUser().getUsername();
        usernameLabel.setText(username);
    }

    /**
     * Gets the user input new password,firstName,lastName and updates the user table in database.
     */
    @FXML
    public void clickConfirm() {
        String password = passwordField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        messageLabel.setText(username + " Edit profile Success! Please login again.");
        DatabaseManager.updateUserDB(username, password, firstName, lastName);
    }

    @FXML
    public void goToLogin(ActionEvent event) throws IOException {
        // Save current user all orders to the orderHistory table in database
        String username = SessionManager.getInstance().getCurrentUser().getUsername();
        ArrayList<OrderDetail> orderDetails = SessionManager.getInstance().getOrderDetails();
        DatabaseManager.saveOrderToDB(username, orderDetails);

        root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}