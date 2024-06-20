package com.example.Hotel.RoomResponse;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor

public class BookingResponse {

    private  Long BookingID;

    private LocalDate CheckinDate;

    public BookingResponse() {
    }

    private  LocalDate CheckoutDate;

    private String GusetName;

    private String GuestEmail;

    private Integer NumOfAdults;

    private Integer NumOfChildren;

    private Integer TotalGuest;

    private String BookingConfirmCode;

    public Long getBookingID() {
        return BookingID;
    }

    public void setBookingID(Long bookingID) {
        BookingID = bookingID;
    }

    public LocalDate getCheckinDate() {
        return CheckinDate;
    }

    public void setCheckinDate(LocalDate checkinDate) {
        CheckinDate = checkinDate;
    }

    public LocalDate getCheckoutDate() {
        return CheckoutDate;
    }

    public void setCheckoutDate(LocalDate checkoutDate) {
        CheckoutDate = checkoutDate;
    }

    public String getGusetName() {
        return GusetName;
    }

    public void setGusetName(String gusetName) {
        GusetName = gusetName;
    }

    public String getGuestEmail() {
        return GuestEmail;
    }

    public void setGuestEmail(String guestEmail) {
        GuestEmail = guestEmail;
    }

    public Integer getTotalGuest() {
        return TotalGuest;
    }

    public void setTotalGuest(Integer totalGuest) {
        TotalGuest = totalGuest;
    }



    public String getBookingConfirmCode() {
        return BookingConfirmCode;
    }

    public void setBookingConfirmCode(String bookingConfirmCode) {
        BookingConfirmCode = bookingConfirmCode;
    }

    private RoomResponse  room;

    public BookingResponse(Long bookingID, LocalDate checkinDate, LocalDate checkoutDate, String bookingConfirmCode) {
        BookingID = bookingID;
        CheckinDate = checkinDate;
        CheckoutDate = checkoutDate;
        BookingConfirmCode = bookingConfirmCode;
    }
}
