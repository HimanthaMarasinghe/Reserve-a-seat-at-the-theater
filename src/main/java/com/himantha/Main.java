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

        System.out.printf("%-70s", "Enter the Customer Phone number : ");
        String customerPhone = sc.nextLine();

        if(!DAO.checkCustomer(customerPhone, con)){
            addCustomer(con, customerPhone);
        }

        String showID = scanMovieCode();
        LocalDate date = scanDate();
        String time = scanTime();
        if(time == null)
            return;

        String seatID;

        while(true){
            System.out.printf("%-70s", "Enter the Seat ID (Enter 0 to finish) : ");
            seatID = sc.nextLine().toUpperCase();
            if(seatID.equals("0"))
                break;

            if(!Ticket.seatValidator(seatID)){
                System.out.println("Invalid Seat ID");
                continue;
            }
            if(!DAO.checkSeat(showID, seatID, date, time, con)){
                System.out.println("Seat is not available");
                continue;
            }

            Ticket ticket = new Ticket(seatID, showID, customerPhone, date, time);
            DAO.addTicketToDb(ticket, con);
            System.out.println("Seat reserved successfully");
        }
    }

    /**
     * Add a movie into the database
     * @param con
     */
    private static void addMovie(Connection con){
        Scanner sc = new Scanner(System.in);
        String movieCode = scanMovieCode();

        System.out.printf("%-70s", "Enter the movie name : ");
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

        System.out.printf("%-70s", "Enter the Customer First Name : ");
        String customerName = sc.nextLine();

        System.out.printf("%-70s", "Enter the Customer Last Name : ");
        String customerLastName = sc.nextLine();

        System.out.printf("%-70s", "Enter the Customer Email : ");
        String customerEmail = sc.nextLine();

        DAO.addCustomerToDb(new Customer(customerName, customerLastName, customerEmail, phone), con);
    }

    /**
     * Shows the seat reservation status
     * @param con Database conection
     */
    private static void showSeats(Connection con){
        Scanner sc = new Scanner(System.in);

        System.out.printf("%-70s", "Enter the movie code : ");
        String showID = sc.nextLine();

        LocalDate date = scanDate();
        String time = scanTime();
        if(time == null)
            return;

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
     * Print all the reservation details as a list with customer details
     * @param con Database Connection
     */
    private static void showReservationsInDetail(Connection con){
        Scanner sc = new Scanner(System.in);

        String showID = scanMovieCode();
        LocalDate date = scanDate();
        String time = scanTime();
        if(time == null)
            return;

        ResultSet rs = DAO.getAllReservedSeats(time, date, showID, con);
        try{
            System.out.println("┌"+"─".repeat(7)+"┬"+"─".repeat(22)+"┬"+"─".repeat(22)+"┬"+"─".repeat(12)+"┬"+"─".repeat(42)+"┐");
            System.out.printf("│ %-5s │ %-20s │ %-20s │ %-10s │ %-40s │\n", "Seat", "First Name", "Last Name", "Phone", "Email");
            System.out.println("├"+"─".repeat(7)+"┼"+"─".repeat(22)+"┼"+"─".repeat(22)+"┼"+"─".repeat(12)+"┼"+"─".repeat(42)+"┤");
            while (rs.next()){
                System.out.printf("│  %-3s  │ %-20s │ %-20s │ %-10s │ %-40s │\n",
                        rs.getString("seatID"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("customer_phone"),
                        rs.getString("email"));
            }
            System.out.println("└"+"─".repeat(7)+"┴"+"─".repeat(22)+"┴"+"─".repeat(22)+"┴"+"─".repeat(12)+"┴"+"─".repeat(42)+"┘");
        }catch (SQLException e){
            System.out.println(e);
        }
    }

    /**
     * Print all the movies currently showing
     * @param con
     */
    private static void showAllMovies(Connection con){
        ResultSet rs = DAO.getAllMovies(con);
        try{
            System.out.println("┌────────┬────────────────────────────────┐");
            System.out.printf("│ %-6s │ %-30s │\n", "Code", "Title");
            System.out.println("├────────┼────────────────────────────────┤");
            while (rs.next()){
                System.out.printf("│ %-6s │ %-30s │\n", rs.getString("movie_id"), rs.getString("title"));
            }
            System.out.println("└────────┴────────────────────────────────┘");
        }catch (SQLException e){
            System.out.println(e);
        }
    }

    /**
     * Print details of all the Customers that is in the database
     * @param con
     */
    private static void showAllCustomers(Connection con){
        ResultSet rs = DAO.getAllCustomers(con);
        try{
            System.out.println("┌"+"─".repeat(22)+"┬"+"─".repeat(22)+"┬"+"─".repeat(12)+"┬"+"─".repeat(42)+"┐");
            System.out.printf("│ %-20s │ %-20s │ %-10s │ %-40s │\n", "First Name", "Last Name", "Phone", "Email");
            System.out.println("├"+"─".repeat(22)+"┼"+"─".repeat(22)+"┼"+"─".repeat(12)+"┼"+"─".repeat(42)+"┤");
            while (rs.next()){
                System.out.printf("│ %-20s │ %-20s │ %-10s │ %-40s │\n",
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("phone_number"),
                        rs.getString("email"));
            }
            System.out.println("└"+"─".repeat(22)+"┴"+"─".repeat(22)+"┴"+"─".repeat(12)+"┴"+"─".repeat(42)+"┘");

        }catch(SQLException e){
            System.out.println(e);
        }
    }

    /**
     * Remove a reservation
     * @param con
     */
    private static void removeTicket(Connection con){
        Scanner sc = new Scanner(System.in);

        System.out.printf("%-70s", "Enter the Seat ID : ");
        String seatID = sc.nextLine().toUpperCase();

        if(!Ticket.seatValidator(seatID)){
            System.out.println("Invalid Seat ID");
            return;
        }

        String showID = scanMovieCode();
        LocalDate date = scanDate();
        String time = scanTime();
        if(time == null)
            return;

        if(DAO.checkSeat(showID, seatID, date, time, con)){
            System.out.println("Seat is not reserved");
            return;
        }

        DAO.deleteTicket(seatID, con);
        System.out.println("Reservation has been deleted");
    }

    /**
     * Remove a movie from the database
     * @param con
     */
    private static void removeMovie(Connection con){
        Scanner sc = new Scanner(System.in);
        String movieCode = scanMovieCode();
        DAO.deleteMovie(movieCode, con);
        System.out.println("Movie has been deleted");
    }




    /**
     * Scan an integer and return the time slot as a String
     * @return
     */
    private static String scanTime(){
        Scanner sc = new Scanner(System.in);

        System.out.printf("%-70s", "Enter the time (1. Morning, 2. Evening, 3. Night, 4. Special) : ");
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
                return null;
        }
        return time;
    }

    /**
     * Scan a String, convert it in to LocalDate and return
     * @return
     */
    private static LocalDate scanDate(){
        Scanner sc = new Scanner(System.in);

        System.out.printf("%-70s", "Enter the Date of reservation (yyyy-mm-dd) : ");
        String dateOfReservation = sc.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateOfReservation, formatter);

        return date;
    }

    /**
     * Scan a String, convert it in to uppercase and return
     * @return movie code
     */
    private static String scanMovieCode(){
        Scanner sc = new Scanner(System.in);
        System.out.printf("%-70s", "Enter the movie code : ");
        return sc.nextLine().toUpperCase();
    }
}