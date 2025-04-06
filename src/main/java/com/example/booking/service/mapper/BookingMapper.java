package com.example.booking.service.mapper;

import com.example.booking.service.dto.BookingDTO;
import com.example.booking.service.modal.Booking;

public class BookingMapper {

    public static BookingDTO toDTO(Booking booking){
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setId(booking.getId());
        bookingDTO.setCustomerId(booking.getCustomerId());
        bookingDTO.setStatus(booking.getStatus());
        bookingDTO.setStartTime(booking.getStartTime());
        bookingDTO.setEndTime(booking.getEndTime());
        bookingDTO.setSaloonId(booking.getSaloonId());
        bookingDTO.setServiceIds(booking.getServiceIds());

        return bookingDTO;
    }

}
