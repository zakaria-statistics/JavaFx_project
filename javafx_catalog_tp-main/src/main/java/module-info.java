module ma.enset.javafx_tp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens ma.enset.javafx_tp to javafx.fxml;
    opens ma.enset.javafx_tp.controller to javafx.fxml;
    opens ma.enset.javafx_tp.entities to javafx.base;
    exports ma.enset.javafx_tp;
}