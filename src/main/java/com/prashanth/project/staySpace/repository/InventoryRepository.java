package com.prashanth.project.staySpace.repository;

import com.prashanth.project.staySpace.entity.Inventory;
import com.prashanth.project.staySpace.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    void deleteByRoom(Room room);
}
