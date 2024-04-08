package com.booking.hotel.controller;

import com.booking.hotel.entity.UsersEntity;
import com.booking.hotel.payload.request.LoginRequest;
import com.booking.hotel.payload.response.BaseResponse;
import com.booking.hotel.repository.UsersRepository;
import com.booking.hotel.service.imp.LoginServiceImp;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@CrossOrigin
public class LoginController {

    @Autowired
    private LoginServiceImp loginServiceImp;

    @PostMapping("")
    public ResponseEntity<?> login(@Valid LoginRequest loginRequest){

//        SecretKey secretKey = Jwts.SIG.HS256.key().build();
//        String strKey = Encoders.BASE64.encode(secretKey.getEncoded());
//        System.out.println("Kiểm tra key " + strKey);

        String token = loginServiceImp.checkLogin(loginRequest.getUsername(), loginRequest.getPassword());
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(token.trim().length() > 0 ? 200 : 400);
        baseResponse.setData(token.trim().length() > 0 ? token : "Đăng nhâp thất bại");

        baseResponse.setData(token);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

}
