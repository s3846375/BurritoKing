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
import model.CreditCardAuthenticator;
import model.SessionManager;
import model.User;
import model.UserVIP;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class OrderPaymentCtrl implements Initializable {

    private Scene scene;
    private Stage stage;
    private Parent root;

    double orderSubtotal;
    double finalPayment;
    int redeemAmount;

    @FXML
    private TextField cardNoTextField, expiryTextField, cvvTextField;
    @FXML
    private Label messageLabel, subtotalLabel, finalPayLabel, availableCreditLabel, redeemCreditLabel;
    @FXML
    private Button redeemButton;


    /**
     * Show order subtotal and current user credit points.
     * Only VIP will see the redeem button.
     * Display 0 credits for non-VIP.
     **/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        orderSubtotal = SessionManager.getInstance().getActiveOrder().getTotalPrice();
        User currentUser = SessionManager.getInstance().getCurrentUser();

        // Set the credit points and show the redeem button for VIP users
        if ( currentUser instanceof UserVIP) {
            redeemButton.setVisible(true);
            availableCreditLabel.setText("Available Credits :  " + ((UserVIP)currentUser).getCreditPoint());
        } else {
            redeemButton.setVisible(false);
            availableCreditLabel.setText("Available Credits :  0");
        }
        finalPayment = orderSubtotal;
        subtotalLabel.setText("Order Subtotal: $" + orderSubtotal);
        finalPayLabel.setText("Final Payment: $" + finalPayment);
    }

    /**
     * Validate credit card information and update user credit points for VIP users
     **/
    @FXML
    public void goToOrderDateTime(ActionEvent event) throws IOException {
        String cardNo = cardNoTextField.getText();
        String expiryDate = expiryTextField.getText();
        String cvv = cvvTextField.getText();

        // Check if credit card is valid
        if (!CreditCardAuthenticator.validateCreditCard(cardNo, expiryDate, cvv)) {
            messageLabel.setText("Invalid card");
        } else {

            // Update credit if current user is VIP
            if (SessionManager.getInstance().getCurrentUser() instanceof UserVIP) {
                ((UserVIP) SessionManager.getInstance().getCurrentUser()).updateCredit(redeemAmount, finalPayment);
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("OrderDateTime.fxml"));
            root = loader.load();

            // Pass the final payment amount to OrderDateTime scene
            OrderDateTimeCtrl orderDateTimeCtrl = loader.getController();
            orderDateTimeCtrl.setFinalPayment(finalPayment);

            showScene(event);
        }

    }

    @FXML
    public void clickOnRedeem(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("RedeemCredit.fxml"));
        showScene(event);
    }

    /**
     * Update the final payment for VIP user after they choose to redeem credit.
     * **/
    public void setFinalPayment(int redeemAmount) {
        this.redeemAmount = redeemAmount;
        this.finalPayment = orderSubtotal - redeemAmount;
        redeemCreditLabel.setText("Redeemed:  -$" + redeemAmount);
        finalPayLabel.setText("Final Payment: $" + finalPayment);
    }

    public void showScene(ActionEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
