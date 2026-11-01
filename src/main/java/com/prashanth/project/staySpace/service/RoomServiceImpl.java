package com.prashanth.project.staySpace.service;

import com.prashanth.project.staySpace.dto.RoomDto;
import com.prashanth.project.staySpace.entity.Hotel;
import com.prashanth.project.staySpace.entity.Room;
import com.prashanth.project.staySpace.exception.ResourceNotFoudException;
import com.prashanth.project.staySpace.repository.HotelRepository;
import com.prashanth.project.staySpace.repository.RoomRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService{
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final ModelMapper modelMapper;
    private final InventoryService inventoryService;

    @Override
    @Transactional
    public RoomDto createRoom(Long hotelId,RoomDto roomDto) {
        log.info("Creating a Room for HotelId: {}",hotelId);
        Hotel hotel=hotelRepository.findById(hotelId)
                .orElseThrow(()->new ResourceNotFoudException("Hotel not found with ID: " + hotelId));
        Room room=modelMapper.map(roomDto,Room.class);
        room.setHotel(hotel);
        room=roomRepository.save(room);
        if(hotel.getActive()){
            inventoryService.initializeRoomForAYear(room);
        }

        log.info("Room created successfully with id: {}", room.getId());
        return modelMapper.map(room,RoomDto.class);
    }

    @Override
    public RoomDto getRoomById(Long roomId) {
        log.info("Getting a Room by roomId: {}",roomId);
        Room room=roomRepository.findById(roomId)
                .orElseThrow(()->new ResourceNotFoudException("Room not found with ID: " + roomId));
        return modelMapper.map(room,RoomDto.class);
    }

    @Override
    public RoomDto updateRoom(Long roomId, RoomDto roomDto) {
       log.info("updating Room by roomId:{}",roomId);
       Room room=roomRepository.findById(roomId)
                .orElseThrow(()->new ResourceNotFoudException("Room not found with ID: " + roomId));
       modelMapper.map(roomDto,room);
       room.setId(roomId);
       room=roomRepository.save(room);
       log.info("Room updated successfully with id: {}", room.getId());
       return modelMapper.map(room,RoomDto.class);

    }

    @Transactional
    @Override
    public void deleteRoom(Long roomId) {
        log.info("Deleting a Room by roomId: {}",roomId);
        Room room=roomRepository.findById(roomId)
                .orElseThrow(()->new ResourceNotFoudException("Room not found with ID: " + roomId));
        inventoryService.deleteFutureInventories(room);
        roomRepository.delete(room);
        log.info("Room deleted successfully with id: {}", roomId);

    }

    @Override
    public List<RoomDto> getAllRoomsInHotel(Long hotelId) {
        log.info("Getting all Rooms for HotelId: {}",hotelId);
        Hotel hotel=hotelRepository.findById(hotelId)
                .orElseThrow(()->new ResourceNotFoudException("Hotel not found with ID: " + hotelId));
        return hotel.getRooms()
                .stream()
                .map(room->modelMapper.map(room,RoomDto.class))
                .collect(Collectors.toList());
    }
}
