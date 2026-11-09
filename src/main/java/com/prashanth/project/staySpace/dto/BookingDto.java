package com.prashanth.project.staySpace.dto;

import com.prashanth.project.staySpace.entity.Guest;
import com.prashanth.project.staySpace.entity.Hotel;
import com.prashanth.project.staySpace.entity.Room;
import com.prashanth.project.staySpace.entity.User;
import com.prashanth.project.staySpace.entity.enums.BookingStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import lombok.Data;

@Data
public class BookingDto {

    private Long id;
    private Integer roomCount;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private BookingStatus bookingStatus;
    private Set<GuestDto> guests;
}
