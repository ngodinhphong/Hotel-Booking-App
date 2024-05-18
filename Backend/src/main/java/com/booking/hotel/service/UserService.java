package com.booking.hotel.service;

import com.booking.hotel.dto.RoleDTO;
import com.booking.hotel.dto.UserDTO;
import com.booking.hotel.entity.UserEntity;
import com.booking.hotel.exception.NotRoleDeleteException;
import com.booking.hotel.exception.UserAlreadyExistsException;
import com.booking.hotel.exception.UsernameNotFoundException;
import com.booking.hotel.payload.response.RoleResponse;
import com.booking.hotel.repository.UserRepository;
import com.booking.hotel.service.imp.UserServiceImp;
import com.booking.hotel.utils.JwtUltils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class UserService implements UserServiceImp {

    @Autowired
    private UserRepository usersRepository;

    @Autowired
    private JwtUltils jwtUltils;

    @Autowired
    Gson gson;

    @Override
    public List<UserDTO> getUser() {
        List<UserEntity> usersEntities = usersRepository.findAll();
        List<UserDTO> userDTOS = usersEntities
                .stream()
                .map(user -> new UserDTO(
                        user.getId()
                        ,user.getEmail()
                        ,user.getFirstName()
                        ,user.getLastName()
                        ,new RoleDTO(
                              user.getRole().getId()
                            ,user.getRole().getName())
                ))
                .toList();
        return userDTOS;
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        return usersRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public void deleteUser(String email, HttpServletRequest request) {
        UserEntity usersEntity = getUserByEmail(email);
        String role = "";
        String headerAuthen = request.getHeader("Authorization");
        if(headerAuthen != null && headerAuthen.trim().length() > 0) {
            String token = headerAuthen.substring(7);
            //Giải mã token
            String data = jwtUltils.decryptToken(token);
            if(data != null){
                JsonObject jsonObject = JsonParser.parseString(data).getAsJsonObject();
                role = jsonObject.get("name").getAsString();
                System.out.println(getUserNameBycookei(request));
            }
            if(usersEntity != null){
                if (role.equals("ROLE_ADMIN") || (role.equals("ROLE_USER")) && usersEntity.getEmail().equals(getUserNameBycookei(request))) {
                    usersRepository.delete(usersEntity);
                }else {
                    throw new NotRoleDeleteException("There is no right to delete");
                }
            }

        }

    }
    public String getUserNameBycookei(HttpServletRequest request) {
        String userName = "";
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userName")) {
                    try {
                        userName = URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8);
                    } catch (Exception e) {
                        throw new RuntimeException(e.getMessage());
                    }


                }
            }
        }
        return userName;
    }
}
