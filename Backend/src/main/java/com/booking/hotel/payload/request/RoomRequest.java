package com.booking.hotel.payload.request;

import org.springframework.web.multipart.MultipartFile;

public class RoomRequest {
    private int id;

    private String roomType;

    private double roomPrice;

    private MultipartFile image;

    public String getRoomType() {
        return roomType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public double getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(double roomPrice) {
        this.roomPrice = roomPrice;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
