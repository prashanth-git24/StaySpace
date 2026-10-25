package com.prashanth.project.staySpace.service;

import com.prashanth.project.staySpace.entity.Room;

public interface InventoryService {
    void initializeRoomForAYear(Room room);

    void deleteFutureInventories(Room room);
}
