package com.example.Hotel.Controller;

import com.example.Hotel.Exception.InvalidBookingRequestException;
import com.example.Hotel.Exception.ResourceNotFoundException;
import com.example.Hotel.Model.BookedRoom;
import com.example.Hotel.Model.Room;
import com.example.Hotel.Reponsitory.BookingRepository;
import com.example.Hotel.RoomResponse.BookingResponse;
import com.example.Hotel.RoomResponse.RoomResponse;
import com.example.Hotel.Service.IBookingSevice;
import com.example.Hotel.Service.IRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:5173")
public class BookingController {
    private final IBookingSevice bookingService;
    private final IRoomService roomService;


    @GetMapping("all-bookings")
    public ResponseEntity<List<BookingResponse>> getAllBookings(){
        List<BookedRoom> bookings = bookingService.getAllBooking();
        List<BookingResponse> bookingResponses = new ArrayList<>();
        for (BookedRoom booking : bookings) {
            BookingResponse bookingResponse = getBookingResponse(booking);
            bookingResponses.add(bookingResponse);
        }
        return ResponseEntity.ok(bookingResponses);
    }

    @GetMapping("/confirmation/{bookingConfirmCode}")
    public ResponseEntity<?> getBookingByConfirmCode(@PathVariable String bookingConfirmCode){
            try {
                BookedRoom booking = bookingService.findByBookingConfirmCode(bookingConfirmCode);
                BookingResponse bookingResponse = getBookingResponse(booking);
                return ResponseEntity.ok(bookingResponse);
            }
            catch (ResourceNotFoundException e) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
    }


    @PostMapping ("/room/{roomID}/booking")
    public ResponseEntity<?> saveBooking(
            @PathVariable Long roomID,@RequestBody BookedRoom bookingRequest) {
        try {
            String confirmCode = bookingService.saveBooking(roomID, bookingRequest);
            return ResponseEntity.ok("Room booked successfully! Your booking confirmation code is: " + confirmCode);
        } catch (InvalidBookingRequestException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    @DeleteMapping("/booking/{bookingID}/delete")
    public void CancelBooking(@PathVariable Long bookingID){
        bookingService.cancelBooking(bookingID);
    }

    private BookingResponse getBookingResponse(BookedRoom booking){
        Room theRoom = roomService.getRoomByID(booking.getRoom().getId()).get();
        RoomResponse room = new RoomResponse(theRoom.getId()
                          , theRoom.getRoomType()
                           , theRoom.getPrice());
        return new BookingResponse(booking.getBookingId()
                 , booking.getCheckInDate()
                  , booking.getCheckOutDate(),
                booking.getGuestName(),booking.getGuestEmail(), booking.getNumOfAdults(), booking.getNumOfChildren(), booking.getTotalGuest(), booking.getBookingConfirmCode(), room);
    }

}
