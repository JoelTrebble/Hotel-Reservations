/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hotelreservations;

import java.time.LocalDate;

/**
 *
 * @author Joel
 */
public class HotelReservation {
    
    private LocalDate arrival_date; //step 11 complete
    private LocalDate departure_date;
    private int numNights;
    private double price;

    public HotelReservation(LocalDate arrival_date, LocalDate departure_date, int numNights, double price) {
        this.arrival_date = arrival_date;
        this.departure_date = departure_date;
        this.numNights = numNights;
        this.price = price;
    }

    public LocalDate getArrival_date() {
        return arrival_date;
    }

    public LocalDate getDeparture_date() {
        return departure_date;
    }

    public int getNumNights() {
        return numNights;
    }

    public double getPrice() {
        return price;
    }
    
    
    
    @Override
    public String toString() {
        return "arrival=" + arrival_date + ", departure=" + departure_date + ", numNights=" + numNights +", price=" + price;
    } 
    
}
