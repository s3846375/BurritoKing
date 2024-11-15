module fxfolder.assign2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens controller to javafx.fxml;
    exports controller;
    exports model;
    opens model to javafx.fxml;
}