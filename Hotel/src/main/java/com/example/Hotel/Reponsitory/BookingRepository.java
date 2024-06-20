package com.example.Hotel.Reponsitory;

import com.example.Hotel.Model.BookedRoom;
import com.example.Hotel.Model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingRepository extends JpaRepository<BookedRoom, Long> {
    List<BookedRoom> findByRoomId(Long roomId);


    BookedRoom findByBookingConfirmCode(String bookingConfirmCode);
}
