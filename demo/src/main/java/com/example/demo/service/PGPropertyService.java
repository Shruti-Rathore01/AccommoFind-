package com.example.demo.service;

import com.example.demo.entity.PGProperty;
import com.example.demo.entity.Role;
import com.example.demo.entity.Room;
import com.example.demo.entity.User;
import com.example.demo.repo.PGPropertyRepository;
import com.example.demo.repo.RoomRepository;
import com.example.demo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PGPropertyService {
    @Autowired
    private PGPropertyRepository pgPropertyRepository;
    public List<PGProperty> getAllProperties() {
        return pgPropertyRepository.findAll();
    }
    public PGProperty getPropertyById(Long id)
    {
        return pgPropertyRepository
                .findById(id)
                .orElseThrow(()->new RuntimeException("Property not found with id:"+ id));

    }
    @Autowired
    private UserRepository userRepository;




    public List<PGProperty> getPropertiesByOwner(Long ownerId) {
        User owner=userRepository.findById(ownerId)
                .orElseThrow(()-> new RuntimeException("Owner not found with id:"+ownerId));
        return pgPropertyRepository
                .findByOwner(owner);
    }

    public List<PGProperty> getPropertiesByCity(String city) {
        return pgPropertyRepository.findByCity(city);
    }

    public List<PGProperty> getPropertiesByState(String state) {
        return pgPropertyRepository.findByState(state);
    }

    public List<PGProperty> searchProperties(String query) {
        List<PGProperty> results=new ArrayList<>();
        results.addAll(pgPropertyRepository.findByNameContainingIgnoreCase(query));
        results.addAll(pgPropertyRepository.findByAddressContainingIgnoreCase(query));
        return results.stream().distinct().collect(Collectors.toList());

    }

    public PGProperty createProperty(PGProperty property) {
        if (property.getOwner() == null || property.getOwner().getId() == null) {
            throw new RuntimeException("Owner is required");
        }

        User owner = userRepository.findById(property.getOwner().getId())
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        if (owner.getRole() != Role.OWNER) {
            throw new RuntimeException("User must have OWNER role to create properties");
        }

        property.setOwner(owner);

        // Validate required fields
        if (property.getName() == null || property.getName().trim().isEmpty()) {
            throw new RuntimeException("Property name is required");
        }

        if (property.getAddress() == null || property.getAddress().trim().isEmpty()) {
            throw new RuntimeException("Property address is required");
        }

        return pgPropertyRepository.save(property);
    }

    public PGProperty updateProperty(Long id, PGProperty propertyDetails) {
        PGProperty property = getPropertyById(id);

        property.setName(propertyDetails.getName());
        property.setAddress(propertyDetails.getAddress());
        property.setCity(propertyDetails.getCity());
        property.setState(propertyDetails.getState());
        property.setPincode(propertyDetails.getPincode());
        property.setDescription(propertyDetails.getDescription());

        return pgPropertyRepository.save(property);
    }

    @Autowired
    private RoomRepository roomRepository;

    public void deleteProperty(Long id) {
        PGProperty property = getPropertyById(id);

        // Check if property has rooms
        List<Room> rooms = roomRepository.findByPgProperty(property);
        if (!rooms.isEmpty()) {
            throw new RuntimeException("Cannot delete property with existing rooms. Delete rooms first.");
        }

        pgPropertyRepository.delete(property);
    }


    public List<Room> getPropertyRooms(Long id) {
        PGProperty property = getPropertyById(id);
        return roomRepository.findByPgProperty(property);
    }
}

