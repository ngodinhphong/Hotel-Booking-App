package com.booking.hotel.dto;

import java.util.List;

public class RoomDTO {
    private int id;

    private String RoomType;

    private double roomPrice;

    private String image;

    List<BookingDTO> bookingDTOS;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoomType() {
        return RoomType;
    }

    public void setRoomType(String roomTypes) {
        RoomType = roomTypes;
    }

    public double getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(double roomPrice) {
        this.roomPrice = roomPrice;
    }

    public List<BookingDTO> getBookingDTOS() {
        return bookingDTOS;
    }

    public void setBookingDTOS(List<BookingDTO> bookingDTOS) {
        this.bookingDTOS = bookingDTOS;
    }

    public RoomDTO(){

    }

    public RoomDTO(int id, String roomType, double roomPrice) {
        this.id = id;
        RoomType = roomType;
        this.roomPrice = roomPrice;
    }

    public RoomDTO(int id, String roomType, double roomPrice, String image, List<BookingDTO> bookingDTOS) {
        this.id = id;
        RoomType = roomType;
        this.roomPrice = roomPrice;
        this.image = image;
        this.bookingDTOS = bookingDTOS;
    }

    public RoomDTO(int id, String roomType) {
        this.id = id;
        RoomType = roomType;
    }

    // Phương thức để thiết lập đường dẫn ảnh động
    public void setImageDynamic(String imageName) {
        // Đường dẫn cơ bản của ảnh
        String basePath = "http://localhost:8088/file/";

        // Xây dựng đường dẫn đầy đủ
        this.image = basePath + imageName;
    }
}
