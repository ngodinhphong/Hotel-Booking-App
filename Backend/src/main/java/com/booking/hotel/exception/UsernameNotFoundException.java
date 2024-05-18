package com.booking.hotel.exception;

public class UsernameNotFoundException extends RuntimeException{
    public UsernameNotFoundException(String messgae){
        super(messgae);
    }
}
