package com.prashanth.project.staySpace.controller;

import com.prashanth.project.staySpace.dto.BookingDto;
import com.prashanth.project.staySpace.dto.BookingRequest;
import com.prashanth.project.staySpace.dto.GuestDto;
import com.prashanth.project.staySpace.service.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/booking")
public class HotelBookingController {
    private final BookingService bookingService;
    @PostMapping("/init")
    public ResponseEntity<BookingDto> initializeBooking(@RequestBody BookingRequest bookingRequest){
        return new ResponseEntity<>(bookingService.initializeBooking(bookingRequest), HttpStatus.OK);
    }

    @PostMapping("/{bookingId}/addGuests")
    public ResponseEntity<BookingDto> addGuests(@PathVariable Long bookingId, @RequestBody List<GuestDto> guests){
        return new ResponseEntity<>(bookingService.addGuests(bookingId, guests), HttpStatus.OK);
    }
}
