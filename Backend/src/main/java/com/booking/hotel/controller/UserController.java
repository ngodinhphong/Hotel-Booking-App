package com.booking.hotel.controller;

import com.booking.hotel.dto.RoleDTO;
import com.booking.hotel.dto.UserDTO;
import com.booking.hotel.entity.UserEntity;
import com.booking.hotel.exception.NotRoleDeleteException;
import com.booking.hotel.payload.response.BaseResponse;
import com.booking.hotel.service.UserService;
import com.booking.hotel.service.imp.UserServiceImp;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserServiceImp userServiceImp;

    @Autowired
    UserService userService;

    @GetMapping("/all")
    public ResponseEntity<?> getUsers(){

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(userServiceImp.getUser());

        return new ResponseEntity<>(baseResponse, HttpStatus.FOUND);
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable("email") String email){

        try{
            BaseResponse baseResponse = new BaseResponse();
            UserEntity usersEntity = userServiceImp.getUserByEmail(email);
            UserDTO userDTO = new UserDTO(
                    usersEntity.getId()
                    , usersEntity.getEmail()
                    , usersEntity.getFirstName()
                    , usersEntity.getLastName()
                    , new RoleDTO(
                            usersEntity.getRole().getId()
                            , usersEntity.getRole().getName())
            );
            baseResponse.setData(userDTO);
            return ResponseEntity.ok(baseResponse);
        }catch (UsernameNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching user");
        }
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") String email, HttpServletRequest request){
        try {
            userServiceImp.deleteUser(email, request);
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setMessage("User deleted successfully");
            return ResponseEntity.ok(baseResponse);
        }catch (UsernameNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (NotRoleDeleteException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting user: " + e.getMessage());
        }

    }

}
