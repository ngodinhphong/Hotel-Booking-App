package com.booking.hotel.dto;

import java.util.Objects;

public class RoomTypesDTO {

    private String roomType;

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomTypesDTO that = (RoomTypesDTO) o;
        return Objects.equals(roomType, that.roomType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomType);
    }
}
