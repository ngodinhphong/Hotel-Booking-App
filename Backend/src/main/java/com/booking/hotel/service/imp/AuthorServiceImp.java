package com.booking.hotel.service.imp;

import com.booking.hotel.entity.UserEntity;
import com.booking.hotel.payload.request.LoginRequest;
import com.booking.hotel.payload.request.UserRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthorServiceImp {
    String checkLogin(LoginRequest loginRequest, HttpServletResponse response);

    UserEntity registerUser(UserRequest user);
}
