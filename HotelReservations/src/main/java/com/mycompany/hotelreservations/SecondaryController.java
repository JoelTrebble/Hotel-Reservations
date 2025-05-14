package com.mycompany.hotelreservations;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

public class SecondaryController implements Initializable {

    @FXML private Button btnCloseWindow;
    
    @FXML private ListView<String> lstOutput;
    
    
    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
    
   @Override
   public void initialize(URL url, ResourceBundle rb){
        
       
    try 
    {
        loadReservations();
    } 
    catch (SQLException e)
    {
        Alert myAlert = new Alert(Alert.AlertType.ERROR); //alert from assignment 4
                    myAlert.setTitle("Sorry!");
                    myAlert.setContentText(e.getMessage());
                    myAlert.show(); //alert box
    }
     
    }
    
    @FXML
    private void loadReservations() throws SQLException {
        
        List<HotelReservation> reservations = HotelDB.GetReservations();  //Gets reservations
        
        //list for showing each string row data point
        ObservableList<String> reservationList = FXCollections.observableArrayList();
        
        //acts like a ForEach in C#. For each object in the objects pulled back, add that reservation to the new list for listview output
        for (HotelReservation reservation : reservations) {
            reservationList.add(reservation.toString());  //toString is used each time
        }
        
        lstOutput.setItems(reservationList);
        lstOutput.refresh();
        
        
    }
}