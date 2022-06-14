package com.xfrenzy47x.app.controller;

import com.xfrenzy47x.app.dto.*;
import com.xfrenzy47x.app.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

@RestController
public class TheatreController {

    Theatre theatre = new Theatre(9, 9);

    @GetMapping("/seats")
    public Theatre getAvailableSeats() {
        return theatre;
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> purchase(@RequestBody PurchaseRequestDto purchaseBody) {
        Seat seat = theatre.getAvailable_seats().stream().filter(x -> x.getColumn() == purchaseBody.getColumn() && x.getRow() == purchaseBody.getRow()).findFirst().orElse(null);
        String error = "";
        PurchaseResponseDto ticketDto = new PurchaseResponseDto();
        if (seat == null) {
            error = "The number of a row or a column is out of bounds!";
        } else if (seat.isPurchased()) {
            error = "The ticket has been already purchased!";
        } else {
            seat.setPurchased(true);
            seat.setToken(UUID.randomUUID().toString());

            ticketDto = new PurchaseResponseDto(seat);
        }

        return ResponseEntity
                .status(error.isEmpty() ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(error.isEmpty() ? ticketDto : new ApiErrorDto(error));
    }

    @PostMapping("/return")
    public ResponseEntity<?> returnTicket(@RequestBody ReturnRequestDto tokenBody) {
        Seat seat = theatre.getAvailable_seats().stream().filter(x -> x.getToken().equals(tokenBody.getToken())).findFirst().orElse(null);
        String error = "";
        ReturnResponseDto returnedTicketDto = new ReturnResponseDto();
        if (seat == null) {
            error = "Wrong token!";
        } else {
            seat.setPurchased(false);
            seat.setToken("");

            returnedTicketDto = new ReturnResponseDto(seat);
        }

        return ResponseEntity
                .status(error.isEmpty() ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(error.isEmpty() ? returnedTicketDto : new ApiErrorDto(error));
    }

    @PostMapping("/stats")
    public ResponseEntity<?> stats(@RequestParam(required = false, defaultValue = "") String password) {
        String error = "";
        StatsResponseDto statsResponseDto = new StatsResponseDto();
        if (Objects.equals(password, "super_secret")) {
            statsResponseDto = new StatsResponseDto(theatre);
        } else {
            error = "The password is wrong!";
        }

        return ResponseEntity
                .status(error.isEmpty() ? HttpStatus.OK : HttpStatus.UNAUTHORIZED)
                .body(error.isEmpty() ? statsResponseDto : new ApiErrorDto(error));
    }
}
