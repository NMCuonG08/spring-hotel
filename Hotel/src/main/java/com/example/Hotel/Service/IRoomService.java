package com.example.Hotel.Service;

import com.example.Hotel.Model.Room;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface IRoomService {
    Room addNewRoom(MultipartFile photo, String RoomType, BigDecimal Price) throws SQLException, IOException;

    List<String> getAllRoomTypes();

    List<Room> getAllRooms();

    byte[] getRoomPhotoByRoomId(Long id);

    void deleteRoomByID(Long roomID);

    Room updateRoom(Long roomID, String roomType, BigDecimal roomPrice, byte[] photoBytes);

    Optional<Room> getRoomByID(Long roomID);
}
