package com.prashanth.project.staySpace.service;

import com.prashanth.project.staySpace.entity.Inventory;
import com.prashanth.project.staySpace.entity.Room;
import com.prashanth.project.staySpace.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService{
    private final InventoryRepository inventoryRepository;
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
       LocalDate toDate=LocalDate.now();
       inventoryRepository.deleteByDateAfterAndRoom(toDate,room);
    }
}
