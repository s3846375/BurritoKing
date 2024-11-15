package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;
import model.SessionManager;
import model.UserVIP;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RedeemCreditCtrl implements Initializable {

    private Scene scene;
    private Stage stage;
    private Parent root;

    private int redeemAmount;


    @FXML
    private Label availableCreditLabel;
    @FXML
    private Spinner<Integer> qtySpinner;


    /**
     * Constrain the spinner so user can only select up to the redeemable amount
     * **/
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        double subtotal = SessionManager.getInstance().getActiveOrder().getTotalPrice();
        int RedeemableAmount = ((UserVIP) SessionManager.getInstance().getCurrentUser()).getRedeemableAmount(subtotal);

        availableCreditLabel.setText("Maximum redeemable for this order: $" + RedeemableAmount);
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, RedeemableAmount);
        valueFactory.setValue(RedeemableAmount);
        qtySpinner.setValueFactory(valueFactory);
        redeemAmount = qtySpinner.getValue();
        qtySpinner.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> arg0, Integer arg1, Integer arg2) {
                redeemAmount = qtySpinner.getValue();
            }
        });
    }

    /**
     * Pass the user selected redeem amount back to order payment scene
     * **/
    @FXML
    public void goToPayment(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("OrderPayment.fxml"));
        root = loader.load();
        OrderPaymentCtrl orderPaymentCtrl = loader.getController();
        orderPaymentCtrl.setFinalPayment(redeemAmount);

        showScene(event);
    }

    public void showScene(ActionEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
