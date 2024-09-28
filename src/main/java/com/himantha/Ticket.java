package com.himantha;

public class Ticket {
    private String seatId;
    private Customer customer;

    public Ticket(String seatId, Customer customer) {
        this.seatId = seatId;
        this.customer = customer;
    }

    public static boolean seatValidator(String seatID) {
        if (seatID.matches("^[A-O][0-3][0-9]$")) {
            int seatNum = Integer.parseInt(seatID.substring(1));
            return seatNum >= 1 && seatNum <= 30;
        }
        return false;
    }

    public String getSeatId() {
        return seatId;
    }

    public void setSeatId(String seatId) {
        this.seatId = seatId;
    }

    public Customer getCustomer() {
        return customer;
    }
}
