package com.example.booking.service.modal;

import lombok.Data;

@Data
public class SaloonReport {


    private long saloonId;
    private String saloonName;
    private int totalEarnings;
    private Integer totalBookings;
    private Integer cancelledBookings;
    private Double totalRefund;

}
