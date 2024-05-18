package com.booking.hotel.service.imp;

import com.booking.hotel.dto.UserDTO;
import com.booking.hotel.entity.UserEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface UserServiceImp {

    List<UserDTO> getUser();

    UserEntity getUserByEmail(String email);

    void deleteUser(String email, HttpServletRequest request);
}
