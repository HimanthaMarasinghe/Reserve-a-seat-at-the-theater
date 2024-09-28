package com.himantha;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class DAO {

    /**
     * Adds a ticket to the database
     * @param T Ticket object
     * @param con Connection object
     */
    public static void add(Ticket T, Connection con){

        Customer customer = T.getCustomer();

        String seatId = T.getSeatId();
        String CusFirstName = customer.getFirstName();
        String CusLastName = customer.getLastName();
        String CusEmail = customer.getEmail();
        String CusPhone = customer.getPhone();

        try {
            con.setAutoCommit(false);

            String query = "INSERT INTO tickets(seatId, cusFirstName, cusLastName, cusEmail, cusPhone) VALUES(?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, seatId);
            ps.setString(2, CusFirstName);
            ps.setString(3, CusLastName);
            ps.setString(4, CusEmail);
            ps.setString(5, CusPhone);

            ps.executeUpdate();

            con.commit();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    /**
     * Delete a reservation according to the seatID
     * @param seatID Seat ID
     * @param con Database Connection
     */
    public static void delete(String seatID, Connection con){
        try {
            String query = "DELETE FROM tickets WHERE seatId = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, seatID);
            stmt.executeUpdate();
        }catch (SQLException e){
            System.out.println(e);
        }
    }

    public static ResultSet getAllReservedSeats(Connection con){
        try {
            String query = "SELECT * FROM tickets";
            PreparedStatement stmt = con.prepareStatement(query);
            return stmt.executeQuery();
        }catch (SQLException e){
            System.out.println(e);
        }
        return null;
    }

    /**
     * Return true if the seat is available
     * @param con Connection object
     * @param seatId The Id of the seat that want to be checked (eg: A01, B18)
     */
    public static boolean checkAvailability(String seatId, Connection con){
        try {
            String query = "SELECT * FROM tickets WHERE seatId = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, seatId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                //seat is not available
                return false;
            }
        }catch (SQLException e) {
            System.out.println(e);
        }
        return true;
    }
}


