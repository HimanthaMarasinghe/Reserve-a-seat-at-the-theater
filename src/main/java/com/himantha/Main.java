package com.himantha;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
    }

    private void add(){
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the Seat ID : ");
        String seatID = sc.nextLine();

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

        DAO.add(ticket);
    }
}