package com.prashanth.project.staySpace.service;

import com.prashanth.project.staySpace.dto.HotelDto;
import com.prashanth.project.staySpace.dto.HotelSearchrequest;
import com.prashanth.project.staySpace.entity.Hotel;
import com.prashanth.project.staySpace.entity.Inventory;
import com.prashanth.project.staySpace.entity.Room;
import com.prashanth.project.staySpace.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService{
    private final InventoryRepository inventoryRepository;
    private final ModelMapper modelMapper;
    @Override
    public void initializeRoomForAYear(Room room) {
        LocalDate startDate=LocalDate.now();
        LocalDate endDate=startDate.plusYears(1);
       for(;!startDate.isAfter(endDate);startDate=startDate.plusDays(1)){
           Inventory inventory = Inventory.builder()
                   .hotel(room.getHotel())
                   .room(room)
                   .date(startDate)
                   .totalCount(room.getTotalCount())
                   .bookedCount(0)
                   .surgeFactor(BigDecimal.ONE)
                   .price(room.getPrice())
                   .city(room.getHotel().getCity())
                   .closed(false)
                   .build();
           inventoryRepository.save(inventory);
       }
    }

    @Override
    public void deleteFutureInventories(Room room) {
       inventoryRepository.deleteByRoom(room);
    }

    @Override
    public Page<HotelDto> searchHotels(HotelSearchrequest hotelSearchrequest) {
        Pageable pageable = PageRequest.of(hotelSearchrequest.getPage(), hotelSearchrequest.getSize());
        long dateCount = ChronoUnit.DAYS.between(hotelSearchrequest.getStartDate(), hotelSearchrequest.getEndDate())+1;
        Page<Hotel> hotelPage=inventoryRepository.findHotelsWithAvailableInventory(hotelSearchrequest.getCity(),hotelSearchrequest.getStartDate(),hotelSearchrequest.getEndDate(),hotelSearchrequest.getRoomsCount(),dateCount,pageable);
        return hotelPage.map((hotel)->modelMapper.map(hotel,HotelDto.class));
    }
}
