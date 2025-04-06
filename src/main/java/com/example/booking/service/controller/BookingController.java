package com.example.booking.service.controller;

import com.example.booking.service.domain.BookingStatus;
import com.example.booking.service.dto.*;
import com.example.booking.service.mapper.BookingMapper;
import com.example.booking.service.modal.Booking;
import com.example.booking.service.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> createBooking(
            @RequestParam Long saloonId,
            @RequestBody BookingReq bookingReq
            ) throws Exception {

        UserDTO user = new UserDTO();
        user.setId(1L);

        SaloonDTO saloon = new SaloonDTO();
        saloon.setId(saloonId);

        Set<ServiceDTO> serviceDTOSet = new HashSet<>();

        ServiceDTO serviceDTO = new ServiceDTO();
        serviceDTO.setId(1L);
        serviceDTO.setPrice(399);
        serviceDTO.setDuration(45);
        serviceDTO.setName("Hair cut for men");


        serviceDTOSet.add(serviceDTO);

        Booking booking = bookingService.createBooking(bookingReq,user,saloon,serviceDTOSet);


        return ResponseEntity.ok(booking);

    }

    @GetMapping("/customer")
    public ResponseEntity<Set<BookingDTO>> getBookingsByCustomer(){
        UserDTO user = new UserDTO();
        user.setId(1L);
        List<Booking> bookings = bookingService.getBookingByCustomer(1L);

        return ResponseEntity.ok(getBookingDTOs(bookings));
    }

    @GetMapping("/saloon")
    public ResponseEntity<Set<BookingDTO>> getBookingsBySaloon(){
        UserDTO user = new UserDTO();
        user.setId(1L);
        List<Booking> bookings = bookingService.getBookingBySaloon(1L);

        return ResponseEntity.ok(getBookingDTOs(bookings));
    }

    private Set<BookingDTO> getBookingDTOs(List<Booking> bookings){
        return bookings.stream()
                .map(booking -> {
                    return BookingMapper.toDTO(booking);
                }).collect(Collectors.toSet());
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingDTO> getBookingById(
            @PathVariable Long bookingId
    ) throws Exception {
        Booking booking =  bookingService.getBookingById(bookingId);

        return ResponseEntity.ok(BookingMapper.toDTO(booking));
    }

    @PutMapping("/{bookingId}/status")
    public ResponseEntity<BookingDTO> updateBookingStatus(
            @PathVariable Long bookingId,
            @RequestParam BookingStatus status
            ) throws Exception {

        Booking booking =  bookingService.updateBooking(bookingId,status);

        return ResponseEntity.ok(BookingMapper.toDTO(booking));
    }

}
