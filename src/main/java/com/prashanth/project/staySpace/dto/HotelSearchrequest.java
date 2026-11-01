package com.prashanth.project.staySpace.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class HotelSearchrequest {
    private String city;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer roomsCount;
    private Integer page=0;
    private Integer size=10;
}
