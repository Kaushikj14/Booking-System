package com.example.booking.service.service;

import com.example.booking.service.domain.BookingStatus;
import com.example.booking.service.dto.BookingReq;
import com.example.booking.service.dto.SaloonDTO;
import com.example.booking.service.dto.ServiceDTO;
import com.example.booking.service.dto.UserDTO;
import com.example.booking.service.modal.Booking;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public interface BookingService {

    Booking createBooking(BookingReq booking,
                          UserDTO userDTO,
                          SaloonDTO saloonDTO,
                          Set<ServiceDTO> serviceDTOSet);

    List<Booking> getBookingByCustomer(Long customerId);
    List<Booking> getBookingBySaloon(Long saloonId);

    Booking getBookingById(Long id);
    Booking updateBooking(Long bookingId, BookingStatus status);

    List<Booking> getBookingByDates(LocalDate date,Long saloonId);

}
