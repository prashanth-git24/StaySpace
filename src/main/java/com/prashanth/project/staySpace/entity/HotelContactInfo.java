package com.prashanth.project.staySpace.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Embeddable
@Data
public class HotelContactInfo {

    @NotBlank(message = "Address is required")
    @Size(max = 255, message = "Address cannot exceed 255 characters")
    @Column(name = "address", nullable = false)
    private String address;

    @NotBlank(message = "Phone number is required")
    @Pattern(
            regexp = "^\\+?[0-9]{10,15}$",
            message = "Phone number must be valid digits containing between 10 and 15 numbers"
    )
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @NotBlank(message = "Email address is required")
    @Email(message = "Please provide a valid email address")
    @Size(max = 100, message = "Email cannot exceed 100 characters")
    @Column(name = "email", nullable = false)
    private String email;

    @NotBlank(message = "Location coordinates or description are required")
    @Column(name = "location", nullable = false)
    private String location;
}
