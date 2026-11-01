package com.prashanth.project.staySpace.service;

import com.prashanth.project.staySpace.dto.HotelDto;
import com.prashanth.project.staySpace.dto.HotelInfoDto;
import com.prashanth.project.staySpace.entity.Hotel;

public interface HotelService {
    HotelDto createHotel(HotelDto hotelDto);
    HotelDto getHotelById(Long id);
    HotelDto updateHotel(Long id, HotelDto hotelDto);
    void deleteHotel(Long id);
    void updateHotelStatus(Long id, boolean active);

    HotelInfoDto getHotelInfo(Long hotelId);
}
