package controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;


public class OrderDateTimeCtrl implements Initializable {

    private Scene scene;
    private Stage stage;
    private Parent root;

    double finalPayment;

    @FXML
    private TextField dateTextField, timeTextField;

    /**
     * Set the current date and time as default input order time
     **/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        String formattedDate = currentDate.format(formatDate);
        String formattedTime = currentTime.format(formatter);

        dateTextField.setText(formattedDate);
        timeTextField.setText(formattedTime);
    }

    /**
     * Adds new order to current user orderHistory.
     * update the remained Fries.
     * clears current active order for the next food order.
     **/
    @FXML
    public void goToMainMenu(ActionEvent event) throws IOException {
        Restaurant restaurant = SessionManager.getInstance().getRestaurant();

        // Add new order to orderHistory
        addNewOrderDetailToHistory();

        // update the remained Fries
        restaurant.updateRemainedFries(SessionManager.getInstance().getActiveOrder());

        // Clear food cart and reset the active order
        SessionManager.getInstance().resetOrder();

        root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        showScene(event);
    }

    public void addNewOrderDetailToHistory() {
        String orderDate = dateTextField.getText();
        String orderTime = timeTextField.getText();

        Restaurant restaurant = SessionManager.getInstance().getRestaurant();
        String username = SessionManager.getInstance().getCurrentUser().getUsername();
        int orderNo = SessionManager.getInstance().getOrderDetails().size() + 1;
        double prepareTime = restaurant.getPrepareTime(SessionManager.getInstance().getActiveOrder());
        OrderDetail orderDetail = new OrderDetail(username, orderNo, orderDate, orderTime, finalPayment, getOrderDetail(), StatusEnum.AWAIT_COLLECTION, prepareTime);
        SessionManager.getInstance().addOrderDetailToHistory(orderDetail);
    }

    public String getOrderDetail() {
        Order activeOrder = SessionManager.getInstance().getActiveOrder();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < activeOrder.getItems().size(); i++) {
            FoodItem item = activeOrder.getItems().get(i);
            sb.append(item.getClass().getSimpleName() + " x" + item.getQuantity());
            if (i < activeOrder.getItems().size() - 1) {
                sb.append(";");
            }
        }
        return sb.toString();
    }
    public void setFinalPayment(double finalPayment) {
        this.finalPayment = finalPayment;
    }

    public void showScene(ActionEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
