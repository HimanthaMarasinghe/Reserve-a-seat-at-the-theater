package com.himantha;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Ticket {
    private String seatId;
    private String showId;
    private String customerPhone;
    private LocalDate date;
    private String time;

    public Ticket(String seatId, String showId, String customerPhone, LocalDate date, String time) {
        this.seatId = seatId;
        this.showId = showId;
        this.customerPhone = customerPhone;
        this.date = date;
        this.time = time;
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

    public String getShowId() {
        return showId;
    }

    public void setShowId(String showId) {
        this.showId = showId;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
