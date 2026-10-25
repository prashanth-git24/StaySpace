package com.prashanth.project.staySpace.controller;

import com.prashanth.project.staySpace.dto.RoomDto;
import com.prashanth.project.staySpace.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/hotels/{hotelId}/rooms")
@RequiredArgsConstructor
@Slf4j
public class RoomController {
    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<RoomDto> createRoom(@PathVariable Long hotelId, @RequestBody RoomDto roomDto){
        log.info("Creating a new room for hotel with ID: {}", hotelId);
        RoomDto room = roomService.createRoom(hotelId, roomDto);
        return new ResponseEntity<>(room, HttpStatus.CREATED);
    }

    @PutMapping("/{roomId}")
    public ResponseEntity<RoomDto> updateRoom(@PathVariable Long hotelId, @PathVariable Long roomId, @RequestBody RoomDto roomDto){
        log.info("Updating room with ID: {}", roomId);
        RoomDto room = roomService.updateRoom(roomId, roomDto);
        return new ResponseEntity<>(room, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<RoomDto>> getAllRoomsInHotel(@PathVariable Long hotelId){
        log.info("Getting all rooms for hotel with ID: {}", hotelId);
        List<RoomDto> rooms = roomService.getAllRoomsInHotel(hotelId);
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<RoomDto> getRoomById(@PathVariable Long hotelId, @PathVariable Long roomId){
        log.info("Getting room with ID: {}", roomId);
        RoomDto room = roomService.getRoomById(roomId);
        return new ResponseEntity<>(room, HttpStatus.OK);
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long hotelId, @PathVariable Long roomId){
        log.info("Deleting room with ID: {}", roomId);
        roomService.deleteRoom(roomId);
        return ResponseEntity.noContent().build();
    }
}
