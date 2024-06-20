package com.example.Hotel.Service;

import com.example.Hotel.Exception.InernalServerException;
import com.example.Hotel.Exception.ResourceNotFoundException;
import com.example.Hotel.Model.Room;
import com.example.Hotel.Reponsitory.IRoomRepository;
import com.example.Hotel.RoomResponse.RoomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements IRoomService {
    private final IRoomRepository roomRepository;
    @Override
    public Room addNewRoom(MultipartFile photo, String RoomType, BigDecimal Price) throws SQLException, IOException {
        Room room = new Room();
        room.setRoomType(RoomType);
        room.setPrice(Price);
        if (!photo.isEmpty()){
            byte[] photobyte = photo.getBytes();
            Blob  photoBlob = new SerialBlob(photobyte);
            room.setPhoto(photoBlob);
        }
        return roomRepository.save(room);
    }

    @Override
    public List<String> getAllRoomTypes() {
        return roomRepository.findDistinctRoomType();
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }


    @Override
    public byte[] getRoomPhotoByRoomId(Long id) {
        Optional<Room> theRoom = roomRepository.findById(id);
        if (theRoom.isPresent()) {
            Blob photo = theRoom.get().getPhoto();
            if (photo != null) {
                try {
                    int length = (int) photo.length();
                    if (length > 0) {
                        return photo.getBytes(1, length);
                    }
                } catch (SQLException e) {
                    // Handle the exception appropriately
                    e.printStackTrace(); // Example: Log the exception
                }
            }
        } else {
            throw new ResourceNotFoundException("Sorry, Room not found");
        }
        return null;
    }

    @Override
    public void deleteRoomByID(Long roomID) {
        Optional<Room> theRoom = roomRepository.findById(roomID);
        if(theRoom.isPresent()) {
            roomRepository.deleteById(roomID);
        }
    }

    @Override
    public Room updateRoom(Long roomID, String roomType, BigDecimal roomPrice, byte[] photoBytes) {
        Room room = roomRepository.findById(roomID)
                .orElseThrow(()-> new ResourceNotFoundException("Room not found"));
        if (roomType != null) room.setRoomType(roomType);
        if (roomPrice != null) room.setPrice(roomPrice);
        if (photoBytes != null && photoBytes.length > 0) {
            try {
                room.setPhoto(new SerialBlob(photoBytes));
            }
            catch (Exception e) {
                    throw  new InernalServerException("Error updating room");
            }
        }


        return roomRepository.save(room);
    }

    @Override
    public Optional<Room> getRoomByID(Long roomID) {
        return Optional.of(roomRepository.findById((roomID)).get());
    }


}
