package com.booking.hotel.controller;

import com.booking.hotel.entity.UserEntity;
import com.booking.hotel.exception.UserAlreadyExistsException;
import com.booking.hotel.payload.request.LoginRequest;
import com.booking.hotel.payload.request.UserRequest;
import com.booking.hotel.payload.response.BaseResponse;
import com.booking.hotel.service.imp.AuthorServiceImp;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    private AuthorServiceImp authorServiceImp;

    @PostMapping("/register-user")
    public ResponseEntity<?> registerUser(@RequestBody UserRequest user){

        try{
            UserEntity usersEntity = authorServiceImp.registerUser(user);
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setMessage("Registration successful!");
            baseResponse.setData(usersEntity);
            return ResponseEntity.ok(baseResponse);
        }catch (UserAlreadyExistsException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response){

//        SecretKey secretKey = Jwts.SIG.HS256.key().build();
//        String strKey = Encoders.BASE64.encode(secretKey.getEncoded());
//        System.out.println("Kiểm tra key " + strKey);

        String token = authorServiceImp.checkLogin(loginRequest, response);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(token.trim().length() > 0 ? 200 : 400);
        baseResponse.setMessage(token.trim().length() > 0 ? "Login Success!" : "Login failed!");
        baseResponse.setData(token);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

}
