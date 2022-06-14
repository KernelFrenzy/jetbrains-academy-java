package com.xfrenzy47x.app.dto;

import com.xfrenzy47x.app.model.Seat;

public class PurchaseResponseDto {
    private String token;
    private Seat ticket;

    public PurchaseResponseDto() {}

    public PurchaseResponseDto(Seat seat) {
        this.token = seat.getToken();
        this.ticket = seat;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Seat getTicket() {
        return ticket;
    }

    public void setTicket(Seat ticket) {
        this.ticket = ticket;
    }
}
