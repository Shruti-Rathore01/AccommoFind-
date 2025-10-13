package com.example.demo.service;

import com.example.demo.entity.PGProperty;
import com.example.demo.entity.Room;
import com.example.demo.entity.RoomType;
import com.example.demo.repo.PGPropertyRepository;
import com.example.demo.repo.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private PGPropertyRepository pgPropertyRepository;



    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Room getRoomById(Long id) {
        return roomRepository

                .findById(id)
                .orElseThrow(()->new RuntimeException("Room not found with available id: "+id));

    }

    public List<Room> getAvailableRooms() {
        return  roomRepository.findByIsAvailableTrue();

    }

    public List<Room> getRoomsByProperty(Long propertyId) {
        PGProperty property=pgPropertyRepository
                .findById(propertyId)
                .orElseThrow(()->new RuntimeException("Property not found with id :"+propertyId));
        
        return roomRepository.findByPgProperty(property);
    }

    public List<Room> getRoomsByType(RoomType roomType) {
        return roomRepository.findByRoomType(roomType);
    }

    public Room createRoom(Room room) {
        if (room.getPgProperty() == null || room.getPgProperty().getId() == null) {
            throw new RuntimeException("PG Property is required");
        }

        PGProperty property = pgPropertyRepository.findById(room.getPgProperty().getId())
                .orElseThrow(() -> new RuntimeException("Property not found"));

        room.setPgProperty(property);

        // Check if room number already exists in this property
        if (roomRepository.findByRoomNumberAndPgProperty(room.getRoomNumber(), property).isPresent()) {
            throw new RuntimeException("Room number already exists in this property");
        }

        return roomRepository.save(room);

    }

    public Room updateRoom(Long id, Room roomDetails) {
        Room room = getRoomById(id);

        room.setRoomNumber(roomDetails.getRoomNumber());
        room.setCapacity(roomDetails.getCapacity());
        room.setMonthlyRent(roomDetails.getMonthlyRent());
        room.setRoomType(roomDetails.getRoomType());

        return roomRepository.save(room);
    }

    public Room updateRoomAvailability(Long id, boolean isAvailable) {
        Room room = getRoomById(id);
        room.setIsAvailable(isAvailable);
        return roomRepository.save(room);
    }


    public void deleteRoom(Long id) {
        Room room = getRoomById(id);
        roomRepository.delete(room);
    }
}
