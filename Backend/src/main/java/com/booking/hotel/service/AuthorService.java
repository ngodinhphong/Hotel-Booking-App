package com.booking.hotel.service;

import com.booking.hotel.entity.RoleEntity;
import com.booking.hotel.entity.UserEntity;
import com.booking.hotel.exception.UserAlreadyExistsException;
import com.booking.hotel.payload.request.LoginRequest;
import com.booking.hotel.payload.request.UserRequest;
import com.booking.hotel.payload.response.RoleResponse;
import com.booking.hotel.repository.RoleRepository;
import com.booking.hotel.repository.UserRepository;
import com.booking.hotel.service.imp.AuthorServiceImp;
import com.booking.hotel.utils.JwtUltils;
import com.google.gson.Gson;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class AuthorService implements AuthorServiceImp {


    @Autowired
    private UserRepository usersRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUltils jwtUltils;

    private Gson gson = new Gson();

    @Override
    public String checkLogin(LoginRequest loginRequest, HttpServletResponse response) {
        String token = "";
        UserEntity usersEntity = usersRepository.findByEmail(loginRequest.getEmail())
                .orElse(null);
        if(usersEntity != null){
            if(passwordEncoder.matches(loginRequest.getPassword(), usersEntity.getPassword()) ){
                //Tạo token từ key đã sinh ra và lưu trữ trước đó
                RoleResponse roleResponse = new RoleResponse();
                roleResponse.setName(usersEntity.getRole().getName());
                String roles = gson.toJson(roleResponse);
                token = jwtUltils.createToken(roles);
                Cookie saveUserName = new Cookie("userName",loginRequest.getEmail());
                saveUserName.setHttpOnly(false);
                saveUserName.setSecure(false);
                saveUserName.setPath("/");
                saveUserName.setMaxAge(7 * 24 * 60 * 60);
                response.addCookie(saveUserName);
            }
        }
        return token;
    }

    @Override
    public UserEntity registerUser(UserRequest user) {
        if(usersRepository.existsByEmail(user.getEmail())){
            throw new UserAlreadyExistsException(user.getEmail() + " already exists");
        }
        UserEntity usersEntity = new UserEntity();
        usersEntity.setEmail(user.getEmail());
        usersEntity.setFirstName(user.getFirstName());
        usersEntity.setLastName(user.getLastName());
        usersEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println(usersEntity.getPassword());
        RoleEntity rolesEntity = roleRepository.findByName("ROLE_USER").get();
        usersEntity.setRole(rolesEntity);
        return usersRepository.save(usersEntity);

    }
}
