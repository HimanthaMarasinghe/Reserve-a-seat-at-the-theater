package com.himantha;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class DAO {

    /**
     * Adds a ticket to the database
     * @param T Ticket object
     * @param con Connection object
     */
    public static void addTicketToDb(Ticket T, Connection con){

        String seatId = T.getSeatId();
        String showId = T.getShowId();
        String customerPhone = T.getCustomerPhone();
        java.sql.Date sqlDate = java.sql.Date.valueOf(T.getDate());
        String time = T.getTime();

        try {
            String query = "INSERT INTO tickets(seatId, show_id, customer_phone, date, time) VALUES(?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, seatId);
            ps.setString(2, showId);
            ps.setString(3, customerPhone);
            ps.setDate(4, sqlDate);
            ps.setString(5, time);

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void addCustomerToDb(Customer C, Connection con){
        String phome = C.getPhone();
        String firstName = C.getFirstName();
        String lastName = C.getLastName();
        String email = C.getEmail();

        try{
            String query = "INSERT INTO customers(phone_number, first_name, last_name, email) VALUES(?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, phome);
            ps.setString(2, firstName);
            ps.setString(3, lastName);
            ps.setString(4, email);
            ps.executeUpdate();
        }catch (SQLException e){
            System.out.println(e);
        }
    }

    public static void addMovieToDb(Movie M, Connection con){
        String code = M.getCode();
        String title = M.getTitle();

        try{
            String query = "INSERT INTO movies VALUES(?,?)";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1,code);
            stmt.setString(2,title);
            stmt.executeUpdate();
        }catch (SQLException e){
            System.out.println(e);
        }
    }

    /**
     * Delete a reservation according to the seatID
     * @param seatID Seat ID
     * @param con Database Connection
     */
    public static void deleteTicket(String seatID, Connection con){
        try {
            String query = "DELETE FROM tickets WHERE seatId = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, seatID);
            stmt.executeUpdate();
        }catch (SQLException e){
            System.out.println(e);
        }
    }

    public static ResultSet getAllReservedSeats(String time, LocalDate date, String movieCode, Connection con){
        try {
            String query = "SELECT * FROM `tickets` " +
                           "INNER JOIN `movies` ON tickets.show_id = movies.movie_id " +
                           "INNER JOIN `customers` ON tickets.customer_phone = customers.phone_number " +
                           "WHERE `time` = ? AND `date` = ? AND movies.movie_id = ?;";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, time);
            stmt.setDate(2, java.sql.Date.valueOf(date));
            stmt.setString(3, movieCode);
            return stmt.executeQuery();
        }catch (SQLException e){
            System.out.println(e);
        }
        return null;
    }

    /**
     * get All movies from the database
     * @param con
     * @return
     */
    public static ResultSet getAllMovies(Connection con){
        try{
            String query = "SELECT * FROM `movies`;";
            PreparedStatement stmt = con.prepareStatement(query);
            return stmt.executeQuery();
        }catch (SQLException e){
            System.out.println(e);
        }
        return null;
    }

    /**
     * get details of All the customers
     * @param con
     * @return
     */
    public static ResultSet getAllCustomers(Connection con){
        try {
            String query = "SELECT * FROM `customers`;";
            PreparedStatement stmt = con.prepareStatement(query);
            return stmt.executeQuery();
        }catch (SQLException e){
            System.out.println(e);
        }
        return null;
    }

    public static boolean checkCustomer(String customerPhone, Connection con){
        try {
            String query = "SELECT * FROM customers WHERE phone_number = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, customerPhone);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                //customer is in the Database
                return true;
            }
        }catch (SQLException e){
            System.out.println(e);
        }
        return false;
    }

    /**
     * Return true if the seat is available
     * @param con Connection object
     * @param seatId The Id of the seat that want to be checked (eg: A01, B18)
     */
    public static boolean checkSeat(String movieCode, String seatId, LocalDate date, String time, Connection con){
        try {
            String query = "SELECT * FROM `tickets` " +
                    "INNER JOIN `movies` ON tickets.show_id = movies.movie_id " +
                    "INNER JOIN `customers` ON tickets.customer_phone = customers.phone_number " +
                    "WHERE `time` = ? AND `date` = ? AND seatId = ? AND movies.movie_id = ?;";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, time);
            ps.setDate(2, java.sql.Date.valueOf(date));
            ps.setString(3, seatId);
            ps.setString(4, movieCode);
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

    /**
     * Delete a movie according to the movie_id from database
     * @param movieCode
     * @param con
     */
    public static void deleteMovie(String movieCode, Connection con){
        try{
            String query = "DELETE FROM movies WHERE movie_id = ?;";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, movieCode);
            stmt.executeUpdate();
        }catch (SQLException e){
            System.out.println(e);
        }
    }
}


