package com.himantha;

import java.sql.*;

public class DAO {

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


    public static boolean checkAvailability(String seatId, Connection conn){
        try {
            String query = "SELECT * FROM tickets WHERE seatId = ?";
            PreparedStatement ps = conn.prepareStatement(query);
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


