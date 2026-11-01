package com.prashanth.project.staySpace.controller;

import com.prashanth.project.staySpace.dto.HotelDto;
import com.prashanth.project.staySpace.dto.HotelInfoDto;
import com.prashanth.project.staySpace.dto.HotelSearchrequest;
import com.prashanth.project.staySpace.service.HotelService;
import com.prashanth.project.staySpace.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
@Slf4j
@RequiredArgsConstructor
public class HotelBrowseController {
    private final InventoryService inventoryService;
    private final HotelService hotelService;
    @GetMapping("/search")
    public ResponseEntity<Page<HotelDto>> searchHotels(@RequestBody HotelSearchrequest hotelSearchrequest){
        log.info("Searching hotels with request: {}", hotelSearchrequest);
        Page<HotelDto> page = inventoryService.searchHotels(hotelSearchrequest);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }
    @GetMapping("/{hotelId}/info")
    public ResponseEntity<HotelInfoDto> getHotelInfo(@PathVariable Long hotelId){
        log.info("Getting hotel info with id: {}", hotelId);
        HotelInfoDto hotelInfoDto = hotelService.getHotelInfo(hotelId);
        return new ResponseEntity<>(hotelInfoDto, HttpStatus.OK);
    }
}
