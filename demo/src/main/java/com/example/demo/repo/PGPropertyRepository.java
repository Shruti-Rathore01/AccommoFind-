package com.example.demo.repo;

import com.example.demo.entity.PGProperty;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PGPropertyRepository extends JpaRepository<PGProperty, Long> {
    
    // Find properties by owner
    List<PGProperty> findByOwner(User owner);
    
    // Find properties by city
    List<PGProperty> findByCity(String city);
    
    // Find properties by state
    List<PGProperty> findByState(String state);
    
    // Find properties by city and state
    List<PGProperty> findByCityAndState(String city, String state);
    
    // Find properties by name (case-insensitive)
    List<PGProperty> findByNameContainingIgnoreCase(String name);
    
    // Find properties by address containing
    List<PGProperty> findByAddressContainingIgnoreCase(String address);
    
    // Find properties by pincode
    List<PGProperty> findByPincode(String pincode);
    
    // Find properties by owner and city
    List<PGProperty> findByOwnerAndCity(User owner, String city);
} 