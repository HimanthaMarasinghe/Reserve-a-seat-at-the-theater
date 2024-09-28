package com.himantha;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
            System.out.println("│ 2. Add a Movie                    │");
            System.out.println("│ 3. Show Seats                     │");
            System.out.println("│ 4. Show all Reservation in detail │");
            System.out.println("│ 5. Show all Movies                │");
            System.out.println("│ 6. Show all Customers             │");
            System.out.println("│ 7. Remove a Reservation           │");
            System.out.println("│ 8. Remove a Movie                 │");
            System.out.println("│ 0. Exit                           │");
            System.out.println("└───────────────────────────────────┘");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    addTicket(con);
                    break;
                case 2:
                    addMovie(con);
                    break;
                case 3:
                    showSeats(con);
                    break;
                case 4:
                    showReservationsInDetail(con);
                    break;
                case 5:
                    showAllMovies(con);
                    break;
                case 6:
                    showAllCustomers(con);
                    break;
                case 7:
                    removeTicket(con);
                    break;
                case 8:
                    removeMovie(con);
                    break;
                case 0: return;
            }
        }while (true);
    }

    /**
     * Adds a ticket to the database
     * @param con
     */
    private static void addTicket(Connection con){
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the Customer Phone number : ");
        String customerPhone = sc.nextLine();

        if(!DAO.checkCustomer(customerPhone, con)){
            addCustomer(con, customerPhone);
        }

        System.out.print("Enter the Seat ID : ");
        String seatID = sc.nextLine();

        if(!Ticket.seatValidator(seatID)){
            System.out.println("Invalid Seat ID");
            return;
        }

        System.out.print("Enter the movie code : ");
        String showID = sc.nextLine();

        System.out.print("Enter the Date of reservation : ");
        String dateOfReservation = sc.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateOfReservation, formatter);

        System.out.print("Enter the time (1. Morning, 2. Evening, 3. Night, 4. Special) : ");
        int t = sc.nextInt();
        String time;
        switch(t){
            case 1:
                time = "Morning";
                break;
            case 2:
                time = "Evening";
                break;
            case 3:
                time = "Night";
                break;
            case 4:
                time = "Special";
                break;
            default:
                System.out.println("Invalid Time");
                return;
        }

        if(!DAO.checkSeat(showID, seatID, date, time, con)){
            System.out.println("Seat is not available");
            return;
        }

        Ticket ticket = new Ticket(seatID, showID, customerPhone, date, time);
        DAO.addTicketToDb(ticket, con);
    }

    /**
     * Add a movie into the database
     * @param con
     */
    private static void addMovie(Connection con){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the Movie code : ");
        String movieCode = sc.nextLine();

        System.out.println("Enter the movie name : ");
        String movieName = sc.nextLine();

        Movie movie = new Movie(movieCode, movieName);
        DAO.addMovieToDb(movie, con);
    }

    /**
     * Add a customer in to the database. Meant to call inside the addTicket method after scanning the phone number.
     * If the phone number is not in the database, this method should be called and add the new customer to the db.
     * @param con
     * @param phone
     */
    private static void addCustomer(Connection con ,String phone){
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the Customer First Name : ");
        String customerName = sc.nextLine();

        System.out.print("Enter the Customer Last Name : ");
        String customerLastName = sc.nextLine();

        System.out.print("Enter the Customer Email : ");
        String customerEmail = sc.nextLine();

        DAO.addCustomerToDb(new Customer(customerName, customerLastName, customerEmail, phone), con);
    }

    /**
     * Shows the seat reservation status
     * @param con Database conection
     */
    private static void showSeats(Connection con){
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the movie code : ");
        String showID = sc.nextLine();

        System.out.print("Enter the Date of reservation : ");
        String dateOfReservation = sc.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateOfReservation, formatter);

        System.out.print("Enter the time (1. Morning, 2. Evening, 3. Night, 4. Special) : ");
        int t = sc.nextInt();
        String time;
        switch(t){
            case 1:
                time = "Morning";
                break;
            case 2:
                time = "Evening";
                break;
            case 3:
                time = "Night";
                break;
            case 4:
                time = "Special";
                break;
            default:
                System.out.println("Invalid Time");
                return;
        }

        ResultSet rs = DAO.getAllReservedSeats(time, date, showID, con);
        char rowLetter = 'A';
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
    private static void removeTicket(Connection con){
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the Seat ID : ");
        String seatID = sc.nextLine();

        if(!Ticket.seatValidator(seatID)){
            System.out.println("Invalid Seat ID");
            return;
        }

        System.out.print("Enter the movie code : ");
        String showID = sc.nextLine();

        System.out.print("Enter the Date of reservation : ");
        String dateOfReservation = sc.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateOfReservation, formatter);

        System.out.print("Enter the time (1. Morning, 2. Evening, 3. Night, 4. Special) : ");
        int t = sc.nextInt();
        String time;
        switch(t){
            case 1:
                time = "Morning";
                break;
            case 2:
                time = "Evening";
                break;
            case 3:
                time = "Night";
                break;
            case 4:
                time = "Special";
                break;
            default:
                System.out.println("Invalid Time");
                return;
        }

        if(DAO.checkSeat(showID, seatID, date, time, con)){
            System.out.println("Seat is not reserved");
            return;
        }

        DAO.deleteTicket(seatID, con);
        System.out.println("Reservation has been deleted");
    }

    /**
     * Print all the reservation details as a list with customer details
     * @param con Database Connection
     */
    private static void showReservationsInDetail(Connection con){
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the movie code : ");
        String showID = sc.nextLine();

        System.out.print("Enter the Date of reservation : ");
        String dateOfReservation = sc.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateOfReservation, formatter);

        System.out.print("Enter the time (1. Morning, 2. Evening, 3. Night, 4. Special) : ");
        int t = sc.nextInt();
        String time;
        switch(t){
            case 1:
                time = "Morning";
                break;
            case 2:
                time = "Evening";
                break;
            case 3:
                time = "Night";
                break;
            case 4:
                time = "Special";
                break;
            default:
                System.out.println("Invalid Time");
                return;
        }

        ResultSet rs = DAO.getAllReservedSeats(time, date, showID, con);
        try{
            while (rs.next()){
                System.out.print(rs.getString("seatID") + " ");
                System.out.print(rs.getString("first_name") + " ");
                System.out.print(rs.getString("last_name") + " ");
                System.out.print(rs.getString("email") + " ");
                System.out.print(rs.getString("customer_phone"));
                System.out.println();
            }
        }catch (SQLException e){
            System.out.println(e);
        }
    }

    private static void showAllMovies(Connection con){
        ResultSet rs = DAO.getAllMovies(con);
        try{
            while (rs.next()){
                System.out.print(rs.getString("movie_id") + " ");
                System.out.print(rs.getString("title") + " ");
                System.out.println();
            }
        }catch (SQLException e){
            System.out.println(e);
        }
    }

    private static void showAllCustomers(Connection con){
        ResultSet rs = DAO.getAllCustomers(con);
        try{
            while (rs.next()){
                System.out.print(rs.getString("first_name") + " ");
                System.out.print(rs.getString("last_name") + " ");
                System.out.print(rs.getString("phone_number") + " ");
                System.out.print(rs.getString("email") + " ");
            }
        }catch(SQLException e){
            System.out.println(e);
        }
    }

    private static void removeMovie(Connection con){
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the movie code : ");
        String movieCode = sc.nextLine();

        DAO.deleteMovie(movieCode, con);
        System.out.println("Movie has been deleted");
    }
}