/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hotelreservations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Joel
 */
public class HotelDB {
    
    public static Connection GetConnection() { //copied and pasted from chap21-JDBC
        try {
            Connection conn = null;
            String user = "root";
            String password = "";
            String dbURL = "jdbc:mysql://localhost:3306/HotelDB";
            
            conn = DriverManager.getConnection(dbURL, user, password);
            
            return conn;
        } //end getConnection
        catch (SQLException ex) {
            Logger.getLogger(HotelDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static void Disconnect(Connection conn) { //copied and pasted from chap21-JDBC
        
        try 
        {
            conn.close();
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(HotelDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//end disconnect
    
     public static boolean AddReservation(HotelReservation hr) throws SQLException { //step 12 complete. throws SQL Exception throws everything back to the calling method like I did in assignment 5
        Connection conn = null;
        PreparedStatement pstmt = null; //better than CreateStatement and also required per instructions
        boolean success = false; //initialized variables
        
        try 
        {
            conn = GetConnection();
            String sql = "INSERT INTO reservations (arrival_date, departure_date, num_nights, price) VALUES (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            
         
            pstmt.setDate(1, java.sql.Date.valueOf(hr.getArrival_date()));
            pstmt.setDate(2, java.sql.Date.valueOf(hr.getDeparture_date()));
            pstmt.setInt(3, hr.getNumNights());
            pstmt.setDouble(4, hr.getPrice());
            
            int rowsAffected = pstmt.executeUpdate();
            success = (rowsAffected > 0); //if more than 0 rows are affected, then flag is set to true. if 0, set to false (not added)
        } 
        finally //closes prepared statement if not null and regardless will disconnect the connection (good best practice)
        {
            if (pstmt != null) pstmt.close();
            Disconnect(conn);
        }
        
        return success;
    }
    
    public static List<HotelReservation> GetReservations() throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<HotelReservation> reservations = new ArrayList<>();
        
        try 
        {
            conn = GetConnection();
            String sql = "SELECT * FROM reservations";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            while (rs.next()) 
            {
                //convert from date to localdate
                HotelReservation hr = new HotelReservation(
                    rs.getDate("arrival_date").toLocalDate(), 
                    rs.getDate("departure_date").toLocalDate(),
                    rs.getInt("num_nights"),
                    rs.getDouble("price")
                );
                reservations.add(hr); //add record to array list
            }
        } 
        finally 
        {
            if (rs != null) rs.close(); //closes processes
            if (pstmt != null) pstmt.close();
            Disconnect(conn);
        }
        
        return reservations;
    }
    
    
}
