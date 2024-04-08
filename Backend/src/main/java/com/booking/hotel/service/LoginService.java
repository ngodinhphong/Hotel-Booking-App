package com.booking.hotel.service;

import com.booking.hotel.entity.UsersEntity;
import com.booking.hotel.payload.response.RoleResponse;
import com.booking.hotel.repository.UsersRepository;
import com.booking.hotel.service.imp.LoginServiceImp;
import com.booking.hotel.utils.JwtUltils;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements LoginServiceImp {


    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUltils jwtUltils;

    private Gson gson = new Gson();

    @Override
    public String checkLogin(String username, String password) {
        String token = "";
        UsersEntity usersEntity = usersRepository.findByEmail(username);
        if(passwordEncoder.matches(password, usersEntity.getPassword()) ){
            //Tạo token từ key đã sinh ra và lưu trữ trước đó
            RoleResponse roleResponse = new RoleResponse();
            roleResponse.setName(usersEntity.getRole().getName());

            String roles = gson.toJson(roleResponse);
            token = jwtUltils.createToken(roles);
        }

        return token;
    }
}
