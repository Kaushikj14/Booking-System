package com.example.booking.service.service.impl;

import com.example.booking.service.domain.BookingStatus;
import com.example.booking.service.dto.BookingReq;
import com.example.booking.service.dto.SaloonDTO;
import com.example.booking.service.dto.ServiceDTO;
import com.example.booking.service.dto.UserDTO;
import com.example.booking.service.modal.Booking;
import com.example.booking.service.modal.SaloonReport;
import com.example.booking.service.repository.BookingRepository;
import com.example.booking.service.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    @Override
    public Booking createBooking(BookingReq booking,
                                 UserDTO user,
                                 SaloonDTO saloonDTO,
                                 Set<ServiceDTO> serviceDTOSet) throws Exception {
        int totalDuration = serviceDTOSet.stream().mapToInt(ServiceDTO::getDuration).sum();
        LocalDateTime bookingStartTime = booking.getStartTime();
        LocalDateTime bookingEndTime = bookingStartTime.plusMinutes(totalDuration) ;

        Boolean isSlotAvailable = isTimeSlotAvailable(saloonDTO,bookingStartTime,bookingEndTime);

        int totalPrice = serviceDTOSet.stream().mapToInt(ServiceDTO::getPrice).sum();

        Set<Long> idList =  serviceDTOSet.stream().map(ServiceDTO::getId).collect(Collectors.toSet());


        Booking newBooking = new Booking();
        newBooking.setCustomerId(user.getId());
        newBooking.setSaloonId(saloonDTO.getId());
        newBooking.setServiceIds(idList);
        newBooking.setStatus(BookingStatus.PENDING);
        newBooking.setStartTime(bookingStartTime);
        newBooking.setEndTime(bookingEndTime);
        newBooking.setTotalPrice(totalPrice);



        return bookingRepository.save(newBooking);
    }

    public boolean isTimeSlotAvailable(SaloonDTO saloonDTO,
                                       LocalDateTime bookingStartTime,
                                       LocalDateTime bookingEndTime) throws Exception {

        List<Booking> existingBookings = getBookingBySaloon(saloonDTO.getId());

        LocalDateTime saloonOpenTime = saloonDTO.getOpenTime().atDate(bookingStartTime.toLocalDate());
        LocalDateTime saloonCloseTime = saloonDTO.getClosingTime().atDate(bookingStartTime.toLocalDate());


        if (bookingStartTime.isBefore(saloonOpenTime) || bookingEndTime.isAfter(saloonCloseTime)){
            throw  new Exception("Booking time must be in saloon working hours");
        }

        for (Booking existingBooking : existingBookings){
            LocalDateTime existingBookingStartTime = existingBooking.getStartTime();
            LocalDateTime existingBookingEndTime = existingBooking.getEndTime();

            if (bookingStartTime.isBefore(existingBookingEndTime)&&bookingEndTime.isAfter(existingBookingStartTime)){
                throw new Exception("slot not available, choose different time");
            }

            if (bookingStartTime.isEqual(existingBookingStartTime)|| bookingEndTime.isEqual(existingBookingEndTime)){
                throw new Exception("slot not available, choose different time");
            }

        }


        return true;
    }

    @Override
    public List<Booking> getBookingByCustomer(Long customerId) {
        return bookingRepository.findByCustomerId(customerId);
    }

    @Override
    public List<Booking> getBookingBySaloon(Long saloonId) {
        return bookingRepository.findBySaloonId(saloonId);
    }

    @Override
    public Booking getBookingById(Long Id) throws Exception {
        Booking booking = bookingRepository.findById(Id).orElse(null);

        if (booking==null){
            throw new Exception("booking not found");
        }

        return booking;
    }

    @Override
    public Booking updateBooking(Long bookingId, BookingStatus status) throws Exception {
        Booking booking = getBookingById(bookingId);
        booking.setStatus(status);

        return bookingRepository.save(booking);
    }

    @Override
    public List<Booking> getBookingByDates(LocalDate date, Long saloonId) {

        List<Booking> allBookings = getBookingBySaloon(saloonId);

        if (date==null){
            return allBookings;
        }

        return allBookings.stream().
                filter(booking -> isSameDate(booking.getStartTime(),date)  || isSameDate(booking.getEndTime(),date))
                .collect(Collectors.toList());

    }

    private boolean isSameDate(LocalDateTime dateTime, LocalDate date) {

        return dateTime.toLocalDate().isEqual(date);
        
    }

    @Override
    public SaloonReport getSaloonReport(Long saloonId) {

        List<Booking> bookings = getBookingBySaloon(saloonId);

        int totalEarning = bookings.stream().mapToInt(Booking::getTotalPrice).sum();

        Integer totalBooking = bookings.size();

        List<Booking> cancelledBooking = bookings.stream()
                                                    .filter(booking -> booking.getStatus().equals(BookingStatus.CANCELLED)).collect(Collectors.toList());

        Double totalRefund = cancelledBooking.stream().mapToDouble(Booking::getTotalPrice).sum();

        SaloonReport report = new SaloonReport();
        report.setSaloonId(saloonId);
        report.setCancelledBookings(cancelledBooking.size());
        report.setTotalBookings(totalEarning);
        report.setTotalBookings(totalEarning);
        report.setTotalRefund(totalRefund);
        report.setTotalBookings(totalBooking);

        return report;
    }
}
