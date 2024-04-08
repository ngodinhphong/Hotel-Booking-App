package com.booking.hotel.service;

import com.booking.hotel.entity.UsersEntity;
import com.booking.hotel.repository.UsersRepository;
import com.booking.hotel.service.imp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserServiceImp {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UsersEntity addUser() {
        UsersEntity usersEntity = new UsersEntity();


        return null;
    }
}
