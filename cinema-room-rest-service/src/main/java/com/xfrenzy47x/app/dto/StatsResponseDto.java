package com.xfrenzy47x.app.dto;

import com.xfrenzy47x.app.model.Seat;
import com.xfrenzy47x.app.model.Theatre;

public class StatsResponseDto {
    private long current_income;
    private long number_of_available_seats;
    private long number_of_purchased_tickets;

    public StatsResponseDto() {}

    public StatsResponseDto(Theatre theatre) {
        number_of_purchased_tickets = theatre.getAvailable_seats().stream().filter(Seat::isPurchased).count();
        number_of_available_seats = theatre.getAvailable_seats().stream().filter(x -> !x.isPurchased()).count();
        current_income = theatre.getAvailable_seats().stream().filter(Seat::isPurchased).mapToLong(Seat::getPrice).sum();
    }

    public long getCurrent_income() {
        return current_income;
    }

    public void setCurrent_income(long current_income) {
        this.current_income = current_income;
    }

    public long getNumber_of_available_seats() {
        return number_of_available_seats;
    }

    public void setNumber_of_available_seats(long number_of_available_seats) {
        this.number_of_available_seats = number_of_available_seats;
    }

    public long getNumber_of_purchased_tickets() {
        return number_of_purchased_tickets;
    }

    public void setNumber_of_purchased_tickets(long number_of_purchased_tickets) {
        this.number_of_purchased_tickets = number_of_purchased_tickets;
    }
}
