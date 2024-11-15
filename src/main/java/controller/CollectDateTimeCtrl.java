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
import model.*;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class CollectDateTimeCtrl implements Initializable {

    private Scene scene;
    private Stage stage;
    private Parent root;

    private OrderDetail selectedOrderDetail;

    @FXML
    private TextField dateTextField, timeTextField;
    @FXML
    private Label messageLabel;


    /**
     * Set the current DateTime as default input for order DateTime.
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
     * Validates collect order time and changes status to "collected" if input date time valid.
     **/
    public void clickConfirmTime() {
        String collectDate = dateTextField.getText();
        String collectTime = timeTextField.getText();

        if (selectedOrderDetail.collectOrder(collectDate, collectTime)) {
            messageLabel.setText("Order No." + selectedOrderDetail.getOrderNo() + "  collected.");
        } else {
            messageLabel.setText("Food not ready yet!");
        }
    }

    @FXML
    public void goToMainMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void setSelectedOrder(OrderDetail selectedOrder) {
        this.selectedOrderDetail = selectedOrder;
    }

}
