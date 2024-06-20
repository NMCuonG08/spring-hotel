package com.example.Hotel.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class BookedRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long BookingId;
    @Column(name="Check_In")
    private LocalDate CheckInDate;

    public BookedRoom(LocalDate checkInDate, LocalDate checkOutDate, String guestName, String guestEmail, Integer numOfAdults, Integer numOfChildren, Room room, Integer totalGuest) {
        CheckInDate = checkInDate;
        CheckOutDate = checkOutDate;
        GuestName = guestName;
        GuestEmail = guestEmail;
        NumOfAdults = numOfAdults;
        NumOfChildren = numOfChildren;
        this.room = room;
        TotalGuest = totalGuest;
    }

    @Column(name="Check_Out")
    private  LocalDate CheckOutDate;
    @Column(name="Guest_Name")
    private String GuestName;
    @Column(name="Guest_Email")
    private String GuestEmail;
    @Column(name="Num_Of_Adults")
    private Integer NumOfAdults;
    @Column(name="Num_Of_Children")
    private Integer NumOfChildren;
    @Column(name = "Booking_Confirm_Code")
    private String bookingConfirmCode;

    public Integer getNumOfChildren() {
        return NumOfChildren;
    }

    public void setNumOfChildren(Integer numOfChildren) {
        if (numOfChildren != null) {
            this.NumOfChildren = numOfChildren;
        }
       else this.NumOfChildren = 0;
        CalculateTotalGust();
    }


    public Integer getNumOfAdults() {
        return NumOfAdults;
    }

    public void setNumOfAdults(Integer numOfAdults) {
        NumOfAdults = numOfAdults;
        CalculateTotalGust();
    }
    @Column(name="Total_Guest")
    private Integer TotalGuest;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="RoomID")
    private  Room room;

    public void CalculateTotalGust() {
        int adults = (this.NumOfAdults != null) ? this.NumOfAdults : 0;
        int children = (this.NumOfChildren != null) ? this.NumOfChildren : 0;
        this.TotalGuest = adults + children;
    }

    public String getBookingConfirmCode() {
        return bookingConfirmCode;
    }

    public void setBookingConfirmCode(String bookingConfirmCode) {
        this.bookingConfirmCode = bookingConfirmCode;
    }
}
