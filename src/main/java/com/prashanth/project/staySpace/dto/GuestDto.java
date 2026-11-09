package com.prashanth.project.staySpace.dto;

import com.prashanth.project.staySpace.entity.User;
import com.prashanth.project.staySpace.entity.enums.Gender;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class GuestDto {
    private Long id;
    private User user;
    private String name;
    private Gender gender;
    private Integer age;
}
