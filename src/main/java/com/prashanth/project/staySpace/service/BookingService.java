package com.prashanth.project.staySpace.service;

import com.prashanth.project.staySpace.dto.BookingDto;
import com.prashanth.project.staySpace.dto.BookingRequest;
import com.prashanth.project.staySpace.dto.GuestDto;

import java.util.List;

public interface BookingService {
    BookingDto initializeBooking(BookingRequest bookingRequest);

    BookingDto addGuests(Long bookingId, List<GuestDto> guests);
}
