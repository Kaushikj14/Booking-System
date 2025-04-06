package com.example.booking.service.dto;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalTime;
import java.util.List;

@Data
public class SaloonDTO {

    private Long id;
    private String name;
    private List<String> images;
    private String address;
    private String email;
    private String city;
    private Long ownerId;
    //    private UserDTO owner;
    private String phoneNumber;
    private LocalTime openTime;
    private LocalTime closingTime;

    public SaloonDTO() {
    }

    public SaloonDTO(Long id, String name, List<String> images, String address, String email, String city, Long ownerId, String phoneNumber, LocalTime openTime, LocalTime closingTime) {
        this.id = id;
        this.name = name;
        this.images = images;
        this.address = address;
        this.email = email;
        this.city = city;
        this.ownerId = ownerId;
        this.phoneNumber = phoneNumber;
        this.openTime = openTime;
        this.closingTime = closingTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalTime getOpenTime() {
        return openTime;
    }

    public void setOpenTime(LocalTime openTime) {
        this.openTime = openTime;
    }

    public LocalTime getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(LocalTime closingTime) {
        this.closingTime = closingTime;
    }


}

