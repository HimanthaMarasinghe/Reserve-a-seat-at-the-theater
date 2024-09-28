package com.himantha;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {
//    private char[][] seats = new char[20][20];
    public static Connection con = null;

    /**
     * Main method
     * @param args
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try{
            con = DBConection.getConnection();
        }catch(SQLException e){
            System.out.println(e);
        }

            System.out.println("Welcome to the theater ticket system");
        do {
            System.out.println();
            System.out.println("┌───────────────────────────────────┐");
            System.out.println("│               Menu                │");
            System.out.println("├───────────────────────────────────┤");
            System.out.println("│ 1. Add a Reservation              │");
            System.out.println("│ 2. Show Reservations              │");
            System.out.println("│ 3. Remove a Reservation           │");
            System.out.println("│ 4. Show all Reservation in detail │");
            System.out.println("│ 0. Exit                           │");
            System.out.println("└───────────────────────────────────┘");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    add(con);
                    break;
                case 2:
                    showSeats(con);
                    break;
                case 3:
                    remove(con);
                    break;
                case 4:
                    showReservationsInDetail(con);
                    break;
                case 0: return;
            }
        }while (true);
    }

    /**
     * Adds a ticket to the database
     * @param con
     */
    private static void add(Connection con){
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the Seat ID : ");
        String seatID = sc.nextLine();

        if(!Ticket.seatValidator(seatID)){
            System.out.println("Invalid Seat ID");
            return;
        }

        if(!DAO.checkAvailability(seatID, con)){
            System.out.println("Seat is not available");
            return;
        }

        System.out.println("Enter the Customer First Name : ");
        String customerFirstName = sc.nextLine();

        System.out.println("Enter the Customer Last Name : ");
        String customerLastName = sc.nextLine();

        System.out.println("Enter the Customer Email : ");
        String customerEmail = sc.nextLine();

        System.out.println("Enter the Customer Phone Number : ");
        String customerPhoneNumber = sc.nextLine();

        Customer customer = new Customer(customerFirstName, customerLastName, customerEmail, customerPhoneNumber);
        Ticket ticket = new Ticket(seatID, customer);

        DAO.add(ticket, con);
    }

    /**
     * Shows the seat reservation status
     * @param con Database conection
     */
    private static void showSeats(Connection con){
        char rowLetter = 'A';
        ResultSet rs = DAO.getAllReservedSeats(con);
        Set<String> reservedSeats = new HashSet<>();
        try {
            while (rs.next()){
                reservedSeats.add(rs.getString("seatID"));
            }
        }catch (SQLException e){
            System.out.println(e);
        }

        System.out.print("┌");
        System.out.print("──┬".repeat(30));
        System.out.println("──┐");

        System.out.print("│  │");
        for (int i = 0; i < 30; i++) {
            System.out.print(String.format("%02d", i+1) + "│");
        }
        System.out.println();

        for (int i = 0; i < 15; i++) {
            System.out.print("├");
            System.out.print("──┼".repeat(30));
            System.out.println("──┤");
            System.out.print("│ " + rowLetter + "│");
            for (int j = 0; j < 30; j++) {
                String seatID = rowLetter + String.format("%02d", j+1);
                if(reservedSeats.contains(seatID)){
                    System.out.print("██│");
                }else
                    System.out.print("  │");
            }
            System.out.println();
            rowLetter++;
        }

        System.out.print("└");
        System.out.print("──┴".repeat(30));
        System.out.println("──┘");
    }

    /**
     * Remove a reservation
     * @param con
     */
    private static void remove(Connection con){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the Seat ID : ");
        String seatID = sc.nextLine();

        if(!Ticket.seatValidator(seatID)){
            System.out.println("Invalid Seat ID");
            return;
        }

        if(DAO.checkAvailability(seatID, con)){
           System.out.println("There is no reservation with this Seat ID");
           return;
        }

        DAO.delete(seatID, con);
        System.out.println("Reservation has been deleted");
    }

    /**
     * Print all the reservation details as a list with customer details
     * @param con Database Connection
     */
    private static void showReservationsInDetail(Connection con){
        ResultSet rs = DAO.getAllReservedSeats(con);
        try{
            while (rs.next()){
                System.out.print(rs.getString("seatID") + " ");
                System.out.print(rs.getString("cusFirstName") + " ");
                System.out.print(rs.getString("cusLastName") + " ");
                System.out.print(rs.getString("cusEmail") + " ");
                System.out.print(rs.getString("CUSPHONE"));
                System.out.println();
            }
        }catch (SQLException e){
            System.out.println(e);
        }
    }
}