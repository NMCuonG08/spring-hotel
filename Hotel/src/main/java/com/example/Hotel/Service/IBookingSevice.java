package com.example.Hotel.Service;

import com.example.Hotel.Model.BookedRoom;

import java.util.List;

public interface IBookingSevice  {

    List<BookedRoom> getAllBookingRoomById(Long id);

    void cancelBooking(Long bookingID);

 

    String saveBooking(Long roomID, BookedRoom bookingRequest);

    List<BookedRoom> getAllBooking();

    BookedRoom findByBookingConfirmCode(String bookingConfirmCode);
}
