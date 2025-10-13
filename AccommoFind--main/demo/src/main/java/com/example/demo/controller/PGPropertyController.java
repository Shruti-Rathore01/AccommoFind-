package com.example.demo.controller;

import com.example.demo.entity.PGProperty;
import com.example.demo.entity.Room;
import com.example.demo.service.PGPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/properties")
public class PGPropertyController {
    @Autowired
    private PGPropertyService pgPropertyService;

    @GetMapping
    public ResponseEntity<List<PGProperty>> getAllProperties() {
        List<PGProperty> properties = pgPropertyService.getAllProperties();
        return ResponseEntity.ok(properties);
    }

    // GET property by ID
    @GetMapping("/{id}")
    public ResponseEntity<PGProperty> getPropertyById(@PathVariable Long id) {
        PGProperty property = pgPropertyService.getPropertyById(id);
        return ResponseEntity.ok(property);
    }

    // GET properties by owner
    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<PGProperty>> getPropertiesByOwner(@PathVariable Long ownerId) {
        List<PGProperty> properties = pgPropertyService.getPropertiesByOwner(ownerId);
        return ResponseEntity.ok(properties);
    }
    // GET properties by city
    @GetMapping("/city/{city}")
    public ResponseEntity<List<PGProperty>> getPropertiesByCity(@PathVariable String city) {
        List<PGProperty> properties = pgPropertyService.getPropertiesByCity(city);
        return ResponseEntity.ok(properties);
    }

    // GET properties by state
    @GetMapping("/state/{state}")
    public ResponseEntity<List<PGProperty>> getPropertiesByState(@PathVariable String state) {
        List<PGProperty> properties = pgPropertyService.getPropertiesByState(state);
        return ResponseEntity.ok(properties);
    }

    // GET properties by search (name or address)
    @GetMapping("/search")
    public ResponseEntity<List<PGProperty>> searchProperties(@RequestParam String query) {
        List<PGProperty> properties = pgPropertyService.searchProperties(query);
        return ResponseEntity.ok(properties);
    }

    // POST create new property
    @PostMapping
    public ResponseEntity<PGProperty> createProperty(@RequestBody PGProperty property) {
        PGProperty savedProperty = pgPropertyService.createProperty(property);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProperty);
    }

    // PUT update property
    @PutMapping("/{id}")
    public ResponseEntity<PGProperty> updateProperty(@PathVariable Long id, @RequestBody PGProperty propertyDetails) {
        PGProperty updatedProperty = pgPropertyService.updateProperty(id, propertyDetails);
        return ResponseEntity.ok(updatedProperty);
    }
    // DELETE property
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProperty(@PathVariable Long id) {
        pgPropertyService.deleteProperty(id);
        return ResponseEntity.noContent().build();
    }

    // GET property with rooms
    @GetMapping("/{id}/rooms")
    public ResponseEntity<List<Room>> getPropertyRooms(@PathVariable Long id) {
        List<Room> rooms = pgPropertyService.getPropertyRooms(id);
        return ResponseEntity.ok(rooms);
    }

}
