package com.booking.hotel.exception;

public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException(String messgae){
        super(messgae);
    }
}
