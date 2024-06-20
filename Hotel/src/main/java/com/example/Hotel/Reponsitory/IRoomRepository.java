package com.example.Hotel.Reponsitory;

import com.example.Hotel.Model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IRoomRepository extends JpaRepository<Room,Long> {
    @Query("Select Distinct r.RoomType from Room r ")
    List<String> findDistinctRoomType();

}
