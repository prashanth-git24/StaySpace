package com.prashanth.project.staySpace.service;

import com.prashanth.project.staySpace.dto.HotelDto;
import com.prashanth.project.staySpace.dto.HotelSearchrequest;
import com.prashanth.project.staySpace.entity.Room;
import org.springframework.data.domain.Page;

public interface InventoryService {
    void initializeRoomForAYear(Room room);

    void deleteFutureInventories(Room room);

    Page<HotelDto> searchHotels(HotelSearchrequest hotelSearchrequest);
}
