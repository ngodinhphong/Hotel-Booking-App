package com.booking.hotel.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity(name = "booking")
public class BookingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "check_in")
    private LocalDate checkIn;

    @Column(name = "check_out")
    private LocalDate checkOut;

    @Column(name = "adults")
    private int adults;

    @Column(name = "children")
    private int children;

    @Column(name = "total_guest")
    private int totalGuest;

    @Column(name = "confirmation_code")
    private String confirmationCode;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private RoomEntity room;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UsersEntity user;

    public void calculateTotalNumberOfGuest() {
        this.totalGuest = this.adults + children;
    }

    public void setAdults(int adults) {
        this.adults = adults;
        calculateTotalNumberOfGuest();
    }

    public void setChildren(int children) {
        this.children = children;
        calculateTotalNumberOfGuest();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public int getAdults() {
        return adults;
    }

    public int getChildren() {
        return children;
    }

    public int getTotalGuest() {
        return totalGuest;
    }

    public void setTotalGuest(int totalGuest) {
        this.totalGuest = totalGuest;
    }

    public String getConfirmationCode() {
        return confirmationCode;
    }

    public void setConfirmationCode(String confirmationCode) {
        this.confirmationCode = confirmationCode;
    }

    public RoomEntity getRoom() {
        return room;
    }

    public void setRoom(RoomEntity room) {
        this.room = room;
    }

    public UsersEntity getUser() {
        return user;
    }

    public void setUser(UsersEntity user) {
        this.user = user;
    }
}
