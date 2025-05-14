module com.mycompany.hotelreservations {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.logging;
    opens com.mycompany.hotelreservations to javafx.fxml;
    exports com.mycompany.hotelreservations;
}
