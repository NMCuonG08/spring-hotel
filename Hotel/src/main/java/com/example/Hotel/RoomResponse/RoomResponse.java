package com.example.Hotel.RoomResponse;

import jakarta.persistence.Lob;
import org.apache.commons.codec.binary.Base64;

import java.math.BigDecimal;
import java.util.List;

public class RoomResponse {

    private  Long ID;

    private String RoomType;

    private BigDecimal Price;

    private  boolean isBooked;

    private List<BookingResponse> bookings;

    public RoomResponse(Long ID, String roomType, BigDecimal price, boolean isBooked, List<BookingResponse> bookings, byte[] photobyte) {
        this.ID = ID;
        RoomType = roomType;
        Price = price;
        this.isBooked = isBooked;
        this.bookings = bookings;
        this.photo = photobyte != null ? Base64.encodeBase64String(photobyte) : null;
    }

    public Long getID() {
        return ID;
    }

    public RoomResponse(Long ID, String roomType, BigDecimal price, boolean isBooked,  byte[] photobyte) {
        this.ID = ID;
        RoomType = roomType;
        Price = price;
        this.isBooked = isBooked;
        this.photo = photobyte != null ? Base64.encodeBase64String(photobyte) : null;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getRoomType() {
        return RoomType;
    }

    public void setRoomType(String roomType) {
        RoomType = roomType;
    }

    public BigDecimal getPrice() {
        return Price;
    }

    public void setPrice(BigDecimal price) {
        Price = price;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }

    public List<BookingResponse> getBookings() {
        return bookings;
    }

    public void setBookings(List<BookingResponse> bookings) {
        this.bookings = bookings;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public RoomResponse(Long ID, String roomType, BigDecimal price) {
        this.ID = ID;
        this.RoomType = roomType;
        this.Price = price;
    }

    @Lob
    private String photo;



}
