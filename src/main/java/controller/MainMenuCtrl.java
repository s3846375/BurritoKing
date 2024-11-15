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
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;


public class MainMenuCtrl implements Initializable {

    private Scene scene;
    private Stage stage;
    private Parent root;

    ArrayList<OrderDetail> filteredOrderDetails;

    @FXML
    private Label helloLabel;
    @FXML
    private Button upgradeButton;
    @FXML
    private ListView<String> waitCollectOrderView;
    @FXML
    private Label messageLabel;


    /**
     * Display the user’s first name, last name, and any active order that has not been picked up.
     * Only non-VIP users will have a option to upgrade.
     **/
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        if (SessionManager.getInstance().getCurrentUser() instanceof UserVIP) {
            upgradeButton.setVisible(false); // Hide upgrade button for VIP
        } else {
            upgradeButton.setVisible(true);  // Show upgrade button for non-VIP
        }
        // Display firstName lastName on dashboard for current user
        String firstName = SessionManager.getInstance().getCurrentUser().getFirstName();
        String lastName = SessionManager.getInstance().getCurrentUser().getLastName();
        helloLabel.setText("Hello: " + firstName + " " + lastName);

        updateActiveOrderView();
    }

    /**
     * Sets the ListView to show current user active orders that has not been picked up (order status "await collection").
     **/
    private void updateActiveOrderView() {
        // Get current user order history details and filter the ones that are "await collection"
        ArrayList<OrderDetail> orderDetails = SessionManager.getInstance().getOrderDetails();
        filteredOrderDetails = orderDetails.stream()
                .filter(od -> od.getOrderStatus() == StatusEnum.AWAIT_COLLECTION)
                .collect(Collectors.toCollection(ArrayList::new));

        // Specify the orderDetail attributes to display and add to the ListView
        List<String> waitCollectOrders = filteredOrderDetails.stream()
                .map(fod -> fod.getSelectedAttributes(List.of("orderNo", "orderDetail", "orderCost", "orderStatus")))
                .collect(Collectors.toList());

        // Set display text font and add the display items to FXListView
        waitCollectOrderView.setStyle("-fx-font-family: monospace; -fx-font-size: 12px; -fx-font-weight: bold;");
        waitCollectOrderView.getItems().setAll(waitCollectOrders);
    }


    @FXML
    public void goToUpgradeVIP(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("UpgradeVIP.fxml"));
        showScene(event);
    }

    @FXML
    public void goToEditProfile(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("EditProfile.fxml"));
        showScene(event);
    }

    @FXML
    public void goToFoodMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("FoodMenu.fxml"));
        showScene(event);
    }

    /**
     * Select active order from ListView to cancel.
     * Will check if any order is selected and change the order status after successfully canceled.
     **/
    @FXML
    public void clickCancelOrder() {
        int selectedOrderIndex = waitCollectOrderView.getSelectionModel().getSelectedIndex();

        // Check if no order selected from list view
        if (selectedOrderIndex != -1) {

            OrderDetail selectedOrderDetail = filteredOrderDetails.get(selectedOrderIndex);
            if (selectedOrderDetail.cancelOrder()) {
                messageLabel.setText("Order No." + selectedOrderDetail.getOrderNo() + "  canceled.");
                updateActiveOrderView();
            } else {
                messageLabel.setText("Selected order cannot be canceled!");
            }

        } else {
            messageLabel.setText("Pleas Select order to cancel.");
        }
    }

    /**
     * Select active order from ListView to collect.
     * Will check if any order is selected and pass the selected order to next scene to confirm collect order date time..
     **/
    @FXML
    public void goToCollectDateTime(ActionEvent event) throws IOException {

        int selectedOrderIndex = waitCollectOrderView.getSelectionModel().getSelectedIndex();
        // Check if no order selected from list view
        if (selectedOrderIndex != -1) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("CollectDateTime.fxml"));
            root = loader.load();

            // Pass selected OrderDetail to CollectDateTime scene to validate collect DateTime
            OrderDetail orderDetail = filteredOrderDetails.get(selectedOrderIndex);
            CollectDateTimeCtrl collectDateTimeCtrl = loader.getController();
            collectDateTimeCtrl.setSelectedOrder(orderDetail);

            showScene(event);
        } else {
            messageLabel.setText("Pleas Select order to collect.");
        }
    }

    /**
     * Goes to the next scene to view the order history of current user.
     **/
    @FXML
    public void goToOrderHistoryExport(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("OrderHistoryExport.fxml"));
        showScene(event);
    }

    /**
     * Goes back to Login scene if click on Log Out.
     * Saves all the updated information to database for the current user session.
     **/
    @FXML
    public void goToLogin(ActionEvent event) throws IOException {
        User currentUser = SessionManager.getInstance().getCurrentUser();
        String username = currentUser.getUsername();
        ArrayList<OrderDetail> orderDetails = SessionManager.getInstance().getOrderDetails();
        DatabaseManager.saveOrderToDB(username, orderDetails);

        // Save current user credit if user type is VIP
        if (currentUser instanceof UserVIP) {
            DatabaseManager.updateVIP(username, ((UserVIP) currentUser).getCreditPoint());
        }

        root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        showScene(event);
    }

    public void setMessageLabel(String message) {
        messageLabel.setText(message);
    }

    public void showScene(ActionEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}