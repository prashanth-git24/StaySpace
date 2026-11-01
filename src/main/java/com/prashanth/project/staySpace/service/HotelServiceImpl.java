package com.prashanth.project.staySpace.service;

import com.prashanth.project.staySpace.dto.HotelDto;
import com.prashanth.project.staySpace.dto.HotelInfoDto;
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

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final InventoryService inventoryService;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final ModelMapper modelMapper;

    @Override
    public HotelDto createHotel(HotelDto hotelDto) {
        log.info("Creating a new hotel with name: {}", hotelDto.getName());
        Hotel hotel =modelMapper.map(hotelDto,Hotel.class);
        hotel.setActive(false);
        hotel=hotelRepository.save(hotel);
        log.info("Hotel created successfully with id: {}", hotel.getId());
        return modelMapper.map(hotel,HotelDto.class);
    }

    @Override
    public HotelDto getHotelById(Long id) {
        log.info("Getting the hotel with ID: {}",id);
        Hotel hotel=hotelRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoudException("Hotel not found with ID: " + id));
        return modelMapper.map(hotel,HotelDto.class);
    }

    @Override
    public HotelDto updateHotel(Long id, HotelDto hotelDto) {
        log.info("Updating the hotel with ID: {}",id);
        Hotel hotel=hotelRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoudException("Hotel not found with ID: " + id));
        modelMapper.map(hotelDto, hotel);
        hotel.setId(id);
        hotel = hotelRepository.save(hotel);
        log.info("Hotel updated successfully with id: {}", hotel.getId());
        return modelMapper.map(hotel,HotelDto.class);
    }

    @Override
    @Transactional
    public void deleteHotel(Long id) {
        log.info("Deleting the hotel with ID: {}",id);
        Hotel hotel=hotelRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoudException("Hotel not found with ID: " + id));
        for(Room room:hotel.getRooms()){
            inventoryService.deleteFutureInventories(room);
            roomRepository.deleteById(room.getId());
        }
        hotelRepository.delete(hotel);
        log.info("Hotel deleted successfully with id: {}", hotel.getId());
    }

    @Override
    @Transactional
    public void updateHotelStatus(Long id, boolean active) {
        log.info("Updating active status of the hotel with ID: {}",id);
        Hotel hotel=hotelRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoudException("Hotel not found with ID: " + id));
        hotel.setActive(active);
        if(active){
            for(Room room:hotel.getRooms()){
                inventoryService.initializeRoomForAYear(room);
            }
        }
        else{
            for(Room room:hotel.getRooms()){
                inventoryService.deleteFutureInventories(room);
            }
        }
        hotelRepository.save(hotel);
        log.info("Active status updated successfully for hotel with ID: {}", id);

    }

    @Override
    public HotelInfoDto getHotelInfo(Long hotelId) {
        Hotel hotel=hotelRepository.findById(hotelId)
                .orElseThrow(()->new ResourceNotFoudException("Hotel not found with ID: " + hotelId));
        List<RoomDto> rooms = hotel.getRooms().stream().map((room)->modelMapper.map(room,RoomDto.class)).toList();
        return new HotelInfoDto(modelMapper.map(hotel,HotelDto.class),rooms);
    }

}
