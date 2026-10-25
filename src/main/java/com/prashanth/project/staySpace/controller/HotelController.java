package com.prashanth.project.staySpace.controller;

import com.prashanth.project.staySpace.dto.HotelDto;
import com.prashanth.project.staySpace.service.HotelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/hotels")
@RequiredArgsConstructor
@Slf4j
public class HotelController {
    private final HotelService hotelService;

    @PostMapping
    public ResponseEntity<HotelDto> createHotel(@RequestBody HotelDto hotelDto){
        log.info("Attempting to create a new hotel with name: {}", hotelDto.getName());
        HotelDto hotel= hotelService.createHotel(hotelDto);
        return new ResponseEntity<>(hotel,HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelDto> getHotelById(@PathVariable Long id){
        log.info("Getting the hotel with ID: {}", id);
        HotelDto hotel = hotelService.getHotelById(id);
        return new ResponseEntity<>(hotel,HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<HotelDto> updateHotel(@PathVariable Long id, @RequestBody HotelDto hotelDto){
        log.info("Updating the hotel with ID: {}", id);
        HotelDto hotel = hotelService.updateHotel(id, hotelDto);
        return new ResponseEntity<>(hotel,HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable Long id){
        log.info("Deleting the hotel with ID: {}", id);
        hotelService.deleteHotel(id);
        return ResponseEntity.noContent().build();
    }
    @PatchMapping("/{id}")
    public ResponseEntity<Void> activateHotel(@PathVariable Long id, @RequestBody HotelDto hotelDto){
        log.info("Deleting the hotel with ID: {}", id);
        hotelService.updateHotelStatus(id, hotelDto.getActive());
        return ResponseEntity.noContent().build();
    }

}
