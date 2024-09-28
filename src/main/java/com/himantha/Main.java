package com.himantha;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
//    private char[][] seats = new char[20][20];
    public static Connection con = null;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try{
            con = DBConection.getConnection();
        }catch(SQLException e){
            System.out.println(e);
        }

        do {
            System.out.println("Welcome to the theater ticket system");
            System.out.println("+----------------------------------+");
            System.out.println("+              Menu                +");
            System.out.println("+----------------------------------+");
            System.out.println("+ 1. Add a ticket                  +");
            System.out.println("+ 2. Remove a ticket               +");
            System.out.println("+ 3. Show all tickets              +");
            System.out.println("+ 0. Exit                          +");
            System.out.println("+----------------------------------+");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    add(con);
                    break;
                case 2:
                    showSeats(con);
                    break;
                case 3:
                case 0: return;
            }
        }while (true);
    }

    private static void showSeats(Connection con){
        char rowLetter = 'A';
        for (int i = 0; i < 21; i++) {
            System.out.print(String.format("%02d", i) + " ");
        }
        System.out.println();
        for (int i = 0; i < 20; i++) {
            System.out.print(rowLetter + "   ");
            for (int j = 0; j < 20; j++) {
                String seatID = rowLetter + String.format("%02d", j+1);
                if(DAO.checkAvailability(seatID, con)){
                    System.out.print("_  ");
                }else
                    System.out.print("X  ");
            }
            System.out.println();
            rowLetter++;
        }

    }

    private static void add(Connection con){
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the Seat ID : ");
        String seatID = sc.nextLine();

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
}