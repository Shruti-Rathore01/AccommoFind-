package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;



import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String roomNumber;

    @Column(nullable = false)
    private Integer capacity;

    @Column(nullable = false)
    private BigDecimal monthlyRent;

    @Column(nullable = false)
    private Boolean isAvailable = true;

    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pg_property_id", nullable = false)
    private PGProperty pgProperty;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Booking> bookings = new ArrayList<>();

    // Default constructor
    public Room() {
    }

    // Parameterized constructor
    public Room(String roomNumber, Integer capacity, BigDecimal monthlyRent, Boolean isAvailable,
                RoomType roomType, PGProperty pgProperty) {
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.monthlyRent = monthlyRent;
        this.isAvailable = isAvailable;
        this.roomType = roomType;
        this.pgProperty = pgProperty;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public BigDecimal getMonthlyRent() {
        return monthlyRent;
    }

    public void setMonthlyRent(BigDecimal monthlyRent) {
        this.monthlyRent = monthlyRent;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public PGProperty getPgProperty() {
        return pgProperty;
    }

    public void setPgProperty(PGProperty pgProperty) {
        this.pgProperty = pgProperty;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
}
