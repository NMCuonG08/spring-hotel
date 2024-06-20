package com.example.Hotel.Service;

import com.example.Hotel.Exception.InvalidBookingRequestException;
import com.example.Hotel.Model.BookedRoom;
import com.example.Hotel.Model.Room;
import com.example.Hotel.Reponsitory.BookingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements IBookingSevice {

    private final BookingRepository bookingRepository;
    private final RoomServiceImpl roomService;
    @Override
    public List<BookedRoom> getAllBookingRoomById(Long id) {
        return bookingRepository.findByRoomId(id);
    }

    @Override
    public void cancelBooking(Long bookingID) {
        bookingRepository.deleteById(bookingID);
    }

    @Transactional
    @Override
    public String saveBooking(Long roomID, BookedRoom bookingRequest) {
        if (bookingRequest.getCheckOutDate() == null || bookingRequest.getCheckInDate() == null) {
            throw new InvalidBookingRequestException("Check-in date and check-out date cannot be null");
        }

        if (bookingRequest.getCheckOutDate().isBefore(bookingRequest.getCheckInDate())){
            throw new InvalidBookingRequestException("Check-in date must come before check-out date");
        }
        Room room = roomService.getRoomByID(roomID).get();
        List<BookedRoom> existingBookings = room.getBookings();
        boolean roomIsAvailable = roomIsAvailable(bookingRequest, existingBookings);
        if(roomIsAvailable) {
            room.addBooking(bookingRequest);
            bookingRepository.save(bookingRequest);
        } else {
            throw new InvalidBookingRequestException("Sorry! This room has been booked for the selected dates");
        }

        return bookingRequest.getBookingConfirmCode();
    }




    @Override
    public List<BookedRoom> getAllBooking() {
        return  bookingRepository.findAll();
    }

    @Override
    public BookedRoom findByBookingConfirmCode(String bookingConfirmCode) {
        return bookingRepository.findByBookingConfirmCode(bookingConfirmCode);
    }

    private boolean roomIsAvailable(BookedRoom bookingRequest, List<BookedRoom> existingBookings) {
        return existingBookings.stream()
                .noneMatch(existingBooking->
                        bookingRequest.getCheckInDate().equals(existingBooking.getCheckInDate()));


    }
}
