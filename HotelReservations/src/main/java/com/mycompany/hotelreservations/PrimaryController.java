package com.mycompany.hotelreservations;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class PrimaryController implements Initializable {

    
   @FXML private Button btnBookIt;
    
   @FXML private Button btnExit;
   
   @FXML private Button btnCalculate;
   
   @FXML private TextField txtTotal;
   
   @FXML private TextField txtNights;
    
   @FXML private TextField txtArrival;
   
   @FXML private TextField txtDeparture;
    
   private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); //MM must be capitalized. Funny
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        //dates on initilization and when coming back to the form
        LocalDate currentDate = LocalDate.now();
        LocalDate threeDaysLater = currentDate.plusDays(3);
        
        txtArrival.setText(currentDate.format(dateFormatter));
        txtDeparture.setText(threeDaysLater.format(dateFormatter));
        
        int nights = (int) ChronoUnit.DAYS.between(currentDate, threeDaysLater); //better than Period
           
            
        // Calculate the total cost
        double price = nights * 120.00;
            
        // Display the result
        txtTotal.setText(String.format("$%.2f", price)); //bit different from the word document but formats in currency to 2 decimal points
        txtNights.setText(String.valueOf(nights));
     
    }


    
    
@FXML
private void calculateTotal()
{
    if (IsValid())
    {
       
            LocalDate arrivalDate = LocalDate.parse(txtArrival.getText().trim(), dateFormatter); //trim in case whitespace is an issue. then formats with yyyy-MM--dd
            LocalDate departureDate = LocalDate.parse(txtDeparture.getText().trim(), dateFormatter);
            
            
            int numNights = (int) ChronoUnit.DAYS.between(arrivalDate, departureDate);
           
            
            
            double price = numNights * 120.00;
            
            
            
            txtTotal.setText(String.format("$%.2f", price));
            txtNights.setText(String.valueOf(numNights));
            
    }
    
}

    
    
    
    private boolean IsValid() //step 9 complete
    {
        try 
        {
        String arrivalText = txtArrival.getText().trim();
        String departureText = txtDeparture.getText().trim();
        
        
        if (arrivalText.isEmpty() || departureText.isEmpty()) 
        {
            throw new IllegalArgumentException("Arrival and departure dates must not be blank.");
        }
        
        
        LocalDate arrivalDate = LocalDate.parse(arrivalText, dateFormatter);
        LocalDate departureDate = LocalDate.parse(departureText, dateFormatter);
        LocalDate currentDate = LocalDate.now();
        
        
        if (arrivalDate.isBefore(currentDate) || departureDate.isBefore(currentDate))
        {
            throw new IllegalArgumentException("Dates must be after the current date.");
        }
        
        
        if (arrivalDate.isAfter(departureDate) || arrivalDate.isEqual(departureDate))
        {
            throw new IllegalArgumentException("Arrival date must be before departure date.");
            
        }
        
        return true;
        }
        
        catch (DateTimeParseException e) 
        {
            Alert myAlert = new Alert(Alert.AlertType.ERROR);
                    myAlert.setTitle("Invalid Date Format");
                    myAlert.setContentText("Please enter dates in the correct format."); //Good for the failed LocalDate parsing
                    myAlert.show();
        return false;
        }
       
        catch (Exception e) 
        {
        Alert myAlert = new Alert(Alert.AlertType.ERROR); //alert from assignment 4
                    myAlert.setTitle("Sorry!");
                    myAlert.setContentText(e.getMessage()); // messages usually come from the IllegalArgumentExceptions
                    myAlert.show(); //alert box
       
        return false;
        }
    }
    
    
    
    
    @FXML
    private void switchToSecondary() throws IOException, SQLException //a bit of a "weird" way of doing the try-catches and the sql exception but I added another try catch in order to stop it if it's not connected to a DB
    {
        
        if (IsValid())
        {
            
            LocalDate arrivalDate = LocalDate.parse(txtArrival.getText().trim(), dateFormatter);
            LocalDate departureDate = LocalDate.parse(txtDeparture.getText().trim(), dateFormatter);
            
            
            int numNights = (int) ChronoUnit.DAYS.between(arrivalDate, departureDate);
           
            
            
            double price = numNights * 120.00;
            
            HotelReservation hr = new HotelReservation(arrivalDate, departureDate, numNights, price);
            try 
            {
            HotelDB.AddReservation(hr); //adds reservation
            App.setRoot("secondary"); //switches to secondary controller
            }
            catch (Exception e)
            {
                Alert myAlert = new Alert(Alert.AlertType.ERROR); //alert from assignment 4
                    myAlert.setTitle("Sorry!");
                    myAlert.setContentText("DB Connection is not set. Please set DB connection");
                    myAlert.show(); //alert box

            }
        
        }
        
        
    }
    
    @FXML
    private void exitApplication() 
    {
        Platform.exit();
        System.exit(0); //both together makes sure that the java application is completely closed
    }
    
    
   
}
