package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import model.OrderDetail;
import model.SessionManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class OrderHistoryExportCtrl implements Initializable {
    private Scene scene;
    private Stage stage;
    private Parent root;

    private String fileName;
    ArrayList<OrderDetail> sortedOrderDetails;

    @FXML
    private ListView<String> orderHistoryView;
    @FXML
    private Label messageLabel;
    @FXML
    private TextField fileNameTextField;
    @FXML
    private CheckBox orderNoCheckbox, dateCheckbox, timeCheckbox, foodCheckbox, costCheckbox, statusCheckbox;


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        ArrayList<OrderDetail> orderDetails = SessionManager.getInstance().getOrderDetails();
        sortedOrderDetails = sortByReverseTime(orderDetails);

        List<String> orderHistory = sortedOrderDetails.stream()
                .map(sod -> sod.getSelectedAttributes(List.of("orderNo", "orderDate", "orderTime", "orderDetail", "orderCost", "orderStatus")))
                .collect(Collectors.toList());

        // Set display text font and add the display items to FXListView
        orderHistoryView.setStyle("-fx-font-family: monospace; -fx-font-size: 10px; -fx-font-weight: bold;");
        orderHistoryView.getItems().setAll(orderHistory);
        // Allow multiple selection from FXListView
        orderHistoryView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }


    /**
     * Sort the order history in a reverse order according to the order DateTime.
     **/
    public ArrayList<OrderDetail> sortByReverseTime(ArrayList<OrderDetail> orderDetails) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        // Parse date string into a LocalDate object and use Comparator.comparing to extract a sort key (LocalDate) from OrderDetail
        Comparator<OrderDetail> dateComparator = Comparator
                .comparing((OrderDetail o) -> LocalDate.parse(o.getOrderDate(), dateFormatter))
                .reversed();

        Comparator<OrderDetail> timeComparator = Comparator
                .comparing((OrderDetail o) -> LocalTime.parse(o.getOrderTime(), timeFormatter))
                .reversed();

        ArrayList<OrderDetail> sortedOrderDetails = orderDetails.stream()
                .sorted(dateComparator.thenComparing(timeComparator))
                .collect(Collectors.toCollection(ArrayList::new));

        return sortedOrderDetails;
    }

    /**
     * Export selected order and information into a specified name and directory.
     **/
    @FXML
    public void clickExport(ActionEvent event) {
        // Get selected order index to export
        ObservableList<Integer> listOfIndex = orderHistoryView.getSelectionModel().getSelectedIndices();
        // Create list to hold selected order details to export
        ArrayList<OrderDetail> orderToExport = new ArrayList<>();
        // Get user specified file name
        fileName = fileNameTextField.getText();

        // Show the directory chooser dialog and get the selected directory
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File selectedDirectory = directoryChooser.showDialog(stage);

        if (selectedDirectory != null && !listOfIndex.isEmpty() && !fileName.isEmpty()) {

            // Create the file in the selected directory with the specified file name
            File csvFile = new File(selectedDirectory, fileName + ".csv");
            for (int index : listOfIndex) {
                orderToExport.add(sortedOrderDetails.get(index));
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) {

                // Write CSV header based on selected fields
                ArrayList<String> selectedFields = new ArrayList<>();
                if (orderNoCheckbox.isSelected()) selectedFields.add("Order No.");
                if (dateCheckbox.isSelected()) selectedFields.add("Order Date");
                if (timeCheckbox.isSelected()) selectedFields.add("OrderTime");
                if (foodCheckbox.isSelected()) selectedFields.add("Food Items");
                if (costCheckbox.isSelected()) selectedFields.add("Order Cost");
                if (statusCheckbox.isSelected()) selectedFields.add("Order Status");

                writer.write(String.join(",", selectedFields));
                writer.newLine();

                // Write each OrderDetail based on selected fields
                for (OrderDetail order : orderToExport) {
                    ArrayList<String> rowData = new ArrayList<>();
                    if (orderNoCheckbox.isSelected()) rowData.add(String.valueOf(order.getOrderNo()));
                    if (dateCheckbox.isSelected()) rowData.add(order.getOrderDate());
                    if (timeCheckbox.isSelected()) rowData.add(order.getOrderTime());
                    if (foodCheckbox.isSelected()) rowData.add(order.getOrderDetail());
                    if (costCheckbox.isSelected()) rowData.add(String.valueOf(order.getOrderCost()));
                    if (statusCheckbox.isSelected()) rowData.add(order.getOrderStatus().toString());

                    writer.write(String.join(",", rowData));
                    writer.newLine();

                    messageLabel.setText("Exported to " + csvFile.getAbsolutePath());
                }
            } catch (IOException e) {
                e.printStackTrace();
                messageLabel.setText("Export failed.");
            }
        } else {
            messageLabel.setText("Export failed. Please check selected order, file name or directory.");
        }

    }

    @FXML
    public void goToMainMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        showScene(event);
    }


    public void showScene(ActionEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
