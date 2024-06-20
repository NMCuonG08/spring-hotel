package com.example.Hotel.Controller;

import com.example.Hotel.Exception.PhotoRetrievalException;
import com.example.Hotel.Exception.ResourceNotFoundException;
import com.example.Hotel.Model.BookedRoom;
import com.example.Hotel.Model.Room;
import com.example.Hotel.RoomResponse.RoomResponse;
import com.example.Hotel.Service.BookingServiceImpl;
import com.example.Hotel.Service.IRoomService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin("http://localhost:5173")
@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class RoomController {

        private final IRoomService roomService;
        private final BookingServiceImpl bookingService;
    @PostMapping("/add/newroom")
    public ResponseEntity<RoomResponse> AddNewRoom(
            @RequestParam("photo")MultipartFile photo,
            @RequestParam("RoomType") String RoomType,
            @RequestParam("Price")BigDecimal Price) throws SQLException, IOException {
        Room saveRoom = roomService.addNewRoom(photo,RoomType,Price);
        RoomResponse roomResponse = new RoomResponse(saveRoom.getId(), saveRoom.getRoomType(), saveRoom.getPrice());
        return ResponseEntity.ok(roomResponse);
    }
    @GetMapping("/room/types")
    public List<String> getRoomTypes(){
        return roomService.getAllRoomTypes();
    }
    @GetMapping("/all-room")
    public ResponseEntity<List<RoomResponse>> getAllRooms(){
        List<Room> rooms = roomService.getAllRooms();
        List<RoomResponse> roomResponses = new ArrayList<>();
        for (Room room: rooms) {
            byte[] photoByte = roomService.getRoomPhotoByRoomId(room.getId());
            if (photoByte != null && photoByte.length > 0 ) {
                String BasePhoto = Base64.encodeBase64String(photoByte);
                RoomResponse roomResponse = getRoomResponse(room);
                roomResponse.setPhoto(BasePhoto);
                roomResponses.add(roomResponse);
            }
        }
        return ResponseEntity.ok(roomResponses);


    }
    @GetMapping("/room/{roomID}")
    public ResponseEntity<Optional<RoomResponse>> getRoomByID(@PathVariable Long roomID){
        Optional<Room> theRoom = roomService.getRoomByID(roomID);
        return theRoom.map(room -> {
            RoomResponse roomResponse = getRoomResponse(room);
            return ResponseEntity.ok(Optional.of(roomResponse));
        }).orElseThrow(()-> new ResourceNotFoundException("Room not found"));
    }

    private RoomResponse getRoomResponse(Room room) {
        List<BookedRoom> bookings = getAllBookingRoomById(room.getId());
       /* List<BookingResponse> bookingInfor  = bookings
                .stream()
                .map(booking -> new BookingResponse(booking.getBookingID(),
                            booking.getCheckinDate(),
                            booking.getCheckoutDate(),
                            booking.getBookingConfirmCode())).toList();*/
        byte[] photoByte = null;
        Blob photoBlob = room.getPhoto();
        if (photoBlob != null ) {
            try {
                photoByte = photoBlob.getBytes(1,(int) photoBlob.length());
            }
            catch  (SQLException e) {
                throw new PhotoRetrievalException("Error Photo here");
            }
        }
        return new RoomResponse(room.getId(),
                room.getRoomType(),
                room.getPrice(),
                room.isBooked(),
                photoByte
                );
    }

    private List<BookedRoom> getAllBookingRoomById(Long id) {
        return bookingService.getAllBookingRoomById(id);
    }
    @DeleteMapping("/delete/room/{roomID}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long roomID){
        roomService.deleteRoomByID(roomID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update/{roomID}")
    public ResponseEntity<RoomResponse> UpdateRoom(@PathVariable Long roomID,
                                                   @RequestParam(required = false) String roomType,
                                                   @RequestParam(required = false) BigDecimal price,
                                                   @RequestParam(required = false) MultipartFile photo) throws IOException, SQLException {
    byte[] photoBytes = photo != null && !photo.isEmpty() ? photo.getBytes() : roomService.getRoomPhotoByRoomId(roomID);
    Blob photoBlob = photoBytes != null && photoBytes.length > 0 ? new SerialBlob(photoBytes) : null;
    Room theRoom = roomService.updateRoom(roomID, roomType, price, photoBytes);
    theRoom.setPhoto(photoBlob);
    RoomResponse roomResponse = getRoomResponse(theRoom);
    return ResponseEntity.ok(roomResponse);
    }
}
