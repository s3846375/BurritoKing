package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LoginCtrl implements Initializable {

    private Scene scene;
    private Stage stage;
    private Parent root;

    private ArrayList<User> userList;
    private ArrayList<UserVIP> vipList;

    @FXML
    private TextField nameTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label messageLabel;

    /**
     * Get user and VIP user list through Database manager retrieve data from database.
     * **/
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        userList = DatabaseManager.getUserList();
        vipList = DatabaseManager.getVIPList();
    }

    /**
     * Gets the user input and check validation and user type.
     * Sets the current user and order history in the session manager.
     * **/
    public void clickLogin(ActionEvent event) throws IOException {

        String username = nameTextField.getText();
        String password = passwordField.getText();

        if ( !validUser(username, password)) {
            messageLabel.setText("Invalid username or password");
        } else {
            // Get order history of the current user from database and pass to session manager
            SessionManager.getInstance().setOrderDetails(DatabaseManager.getUserOrderDetails(username));

            // Check the current user type and set the user it in the session manager
            if (findVIP(username) != null) {
                SessionManager.getInstance().setUser(findVIP(username));
            } else {
                SessionManager.getInstance().setUser(findUser(username));
            }

            root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
            showScene(event);
        }

    }


    public boolean validUser(String username, String password) {
        for (User user : userList) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public UserVIP findVIP(String username) {
        for (UserVIP vip : vipList)
            if (vip.getUsername().equals(username))
                return vip;
        return null;
    }

    public User findUser(String username) {
        for (User user : userList)
            if (user.getUsername().equals(username))
                return user;
        return null;
    }


    public void goToRegister(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Register.fxml"));
        root = loader.load();

        //Pass the user list to the Register scene to check duplicate username
        RegisterCtrl registerCtrl = loader.getController();
        registerCtrl.setUserList(userList);

        showScene(event);
    }

    public void showScene(ActionEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
