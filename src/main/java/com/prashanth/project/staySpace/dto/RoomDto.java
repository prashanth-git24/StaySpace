package com.prashanth.project.staySpace.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class RoomDto {

    private Long id;
    private String type;
    private BigDecimal price;
    private String[] photos;
    private String[] amenities;
    private Integer totalCount;
    private Integer capacity;

}
