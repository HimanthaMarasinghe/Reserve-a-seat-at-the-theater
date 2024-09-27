package com.himantha;

import java.sql.*;

public class DAO {

    public static void add(Ticket T){

        Customer customer = T.getCustomer();

        String seatId = T.getSeatId();
        String CusFirstName = customer.getFirstName();
        String CusLastName = customer.getLastName();
        String CusEmail = customer.getEmail();
        String CusPhone = customer.getPhone();

        try {
            Connection con = DBConection.getConnection();
            con.setAutoCommit(false);

            String query = "INSERT INTO tickets VALUES(?,?,?,?,?)";
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

    public static boolean checkAvailability(String seatId){
        try {
            Connection conn = DBConection.getConnection();
            String query = "SELECT available FROM seats";
        }catch (SQLException e){
            System.out.println(e);
        }
    }
}


