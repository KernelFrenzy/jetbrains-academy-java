package com.xfrenzy47x.app.dto;

import com.xfrenzy47x.app.model.Seat;

public class ReturnResponseDto {
    private Seat returned_ticket;

    public ReturnResponseDto(Seat seat) {
        this.returned_ticket = seat;
    }

    public Seat getReturned_ticket() {
        return returned_ticket;
    }

    public void setReturned_ticket(Seat returned_ticket) {
        this.returned_ticket = returned_ticket;
    }

    public ReturnResponseDto() {}
}
