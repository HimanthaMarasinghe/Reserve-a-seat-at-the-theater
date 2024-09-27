package com.himantha;

public class Ticket {
    private String seatId;
    private Customer customer;

    public Ticket(String seatId, Customer customer) {
        this.seatId = seatId;
        this.customer = customer;
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
