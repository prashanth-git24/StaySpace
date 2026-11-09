package com.prashanth.project.staySpace.service;

import com.prashanth.project.staySpace.dto.BookingDto;
import com.prashanth.project.staySpace.dto.BookingRequest;
import com.prashanth.project.staySpace.dto.GuestDto;
import com.prashanth.project.staySpace.entity.*;
import com.prashanth.project.staySpace.entity.enums.BookingStatus;
import com.prashanth.project.staySpace.repository.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.prashanth.project.staySpace.exception.ResourceNotFoudException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService{
    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final GuestRepository guestRepository;
    private final InventoryRepository inventoryRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public BookingDto initializeBooking(BookingRequest bookingRequest) {
        log.info("Initalizing booking for hotel :{}, room :{},date:{}-{}",bookingRequest.getHotelId(),bookingRequest.getRoomId(),bookingRequest.getCheckInDate(),bookingRequest.getCheckOutDate());
        Hotel hotel = hotelRepository.findById(bookingRequest.getHotelId()).orElseThrow(() -> new ResourceNotFoudException("Hotel not found"+bookingRequest.getHotelId()));
        Room room = roomRepository.findById(bookingRequest.getRoomId()).orElseThrow(() -> new ResourceNotFoudException("Room not found"+bookingRequest.getRoomId()));
        List<Inventory> inventoryList = inventoryRepository.findAndLockAvailableInventory(room.getId(),bookingRequest.getCheckInDate(),bookingRequest.getCheckOutDate(),bookingRequest.getRoomsCount());
        long daysCount = ChronoUnit.DAYS.between(bookingRequest.getCheckInDate(),bookingRequest.getCheckOutDate() )+1;
        if(inventoryList.size()!=daysCount){
            throw new ResourceNotFoudException("No available inventory for the given date range");
        }
//Reserve the room
        for(Inventory inventory : inventoryList){
            inventory.setReservedCount(inventory.getBookedCount()+bookingRequest.getRoomsCount());
        }
        inventoryRepository.saveAll(inventoryList);
        //Create Booking
       //TODO: Get user from session
        //TODO: Calculate dynamic amount
        Booking booking = Booking.builder()
                .hotel(hotel)
                .room(room)
                .user(getCurrentUser())
                .roomCount(bookingRequest.getRoomsCount())
                .checkInDate(bookingRequest.getCheckInDate())
                .checkOutDate(bookingRequest.getCheckOutDate())
                .bookingStatus(BookingStatus.RESERVED)
                .amount(new BigDecimal(0))
                .build();
       booking = bookingRepository.save(booking);
        return modelMapper.map(booking, BookingDto.class);

    }

    @Override
    @Transactional
    public BookingDto addGuests(Long bookingId, List<GuestDto> guests) {
        log.info("Adding Guests with Booking ID :{}",bookingId);
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new ResourceNotFoudException("Booking not found with id:"+ bookingId));
        if(hasBookingExpired(booking)){
            throw new ResourceNotFoudException("Booking expired with id:"+ bookingId);
        }
        if(booking.getBookingStatus()!=BookingStatus.RESERVED){
            throw new IllegalStateException("Booking is not under Reserved State,cannot add guests");
        }
        for(GuestDto guestDto:guests){
            Guest guest = modelMapper.map(guestDto, Guest.class);
            guest.setUser(getCurrentUser());
            guest = guestRepository.save(guest);
            booking.getGuests().add(guest);
        }
        booking.setBookingStatus(BookingStatus.GUESTS_ADDED);
        booking =bookingRepository.save(booking);
        return modelMapper.map(booking, BookingDto.class);
    }

    public boolean hasBookingExpired(Booking booking){
        return booking.getCreatedAt().plusMinutes(10).isBefore(LocalDateTime.now());
    }
    public User getCurrentUser(){
        User user = new User();
        user.setId(1L);
        return user;
    }
}
