package com.prashanth.project.staySpace.service;

import com.prashanth.project.staySpace.dto.RoomDto;
import java.util.List;
public interface RoomService {
    RoomDto createRoom(Long hotelId,RoomDto roomDto);
    RoomDto getRoomById(Long RoomId);
    RoomDto updateRoom(Long RoomId, RoomDto roomDto);
    void deleteRoom(Long RoomId);
    List<RoomDto> getAllRoomsInHotel(Long hotelId);
}
