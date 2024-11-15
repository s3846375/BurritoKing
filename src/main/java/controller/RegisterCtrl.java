package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.DatabaseManager;
import model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RegisterCtrl {

    private Scene scene;
    private Stage stage;
    private Parent root;

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private ArrayList<User> userList;

    @FXML
    private Button registerButton;
    @FXML
    private Label messageLabel;
    @FXML
    private TextField usernameField, passwordField, firstNameField, lastNameField;

    /**
     * Gets the user input and check if user enters username that already exist.
     * Saves the new user to database user table.
     * **/
    @FXML
    public void clickRegister(){
        username = usernameField.getText();
        password = passwordField.getText();
        firstName = firstNameField.getText();
        lastName = lastNameField.getText();

        if (username.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
            messageLabel.setText("Please fill all the text fields");
        } else if ( findUser(username)) {
            messageLabel.setText("User name already exist.");
        } else {
            registerButton.setVisible(false);
            messageLabel.setText(username + " Registered Success! You can login now.");
            // Save new user to database
            DatabaseManager.saveUserToDB(username, password, firstName, lastName);
        }
    }

    /**
     * Check if already have username in user list.
     * **/
    public boolean findUser(String username) {
        for (User user : userList)
            if (user.getUsername().equals(username))
                return true;
        return false;
    }

    public void setUserList(ArrayList<User> userList) {
        this.userList = userList;
    }

    @FXML
    public void goToLogin(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}