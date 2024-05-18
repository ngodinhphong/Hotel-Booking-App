package com.booking.hotel.controller;

import com.booking.hotel.dto.RoomDTO;
import com.booking.hotel.payload.request.RoomRequest;
import com.booking.hotel.payload.response.BaseResponse;
import com.booking.hotel.service.imp.RoomServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    RoomServiceImp roomServiceImp;

    @PostMapping("/add")
    public ResponseEntity<?> addRoom( RoomRequest roomRequest) {
        boolean isSuccess = roomServiceImp.addRoom(roomRequest.getRoomType(), roomRequest.getRoomPrice(), roomRequest.getImage());

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(isSuccess ? 200 : 400);
        baseResponse.setMessage(isSuccess ? "Thêm thành công" : "thêm thất bại");

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @GetMapping("/types")
    public ResponseEntity<?> getRoomTypes() {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(roomServiceImp.getRoomType());

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllRooms() {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(roomServiceImp.getAllRoom());

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteRoom(@PathVariable int id) {
        BaseResponse baseResponse = new BaseResponse();
        boolean isSuccess = roomServiceImp.deleteRoom(id);
        baseResponse.setStatusCode(isSuccess ? 200 : 500);
        baseResponse.setMessage(isSuccess ? "xóa thành công" : "lỗi khi xóa");

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateRoom(RoomRequest roomRequest){
        BaseResponse baseResponse = new BaseResponse();
        boolean isSuccess = roomServiceImp.updateRoom(roomRequest);
        baseResponse.setStatusCode(isSuccess ? 200 : 500);
        baseResponse.setMessage(isSuccess ? "Cập nhật thành công" : "Cập nhật thất bại");

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @GetMapping("/rooms/{id}")
    public ResponseEntity<?>getRoomById(@PathVariable int id){
        BaseResponse baseResponse = new BaseResponse();
        RoomDTO roomDTO = roomServiceImp.getRoomById(id);
        baseResponse.setMessage(roomDTO == null ? "Không tìm thấy room" : "");
        baseResponse.setData(roomDTO);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @GetMapping("/available-rooms")
    public ResponseEntity<?> getAvailableRooms(
            @RequestParam("checkInDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
            @RequestParam("checkOutDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate checkOutDate,
            @RequestParam("roomType") String roomType){
        List<RoomDTO> roomDTOS = roomServiceImp.getAvailableRooms(checkInDate, checkOutDate, roomType);
        if (roomDTOS.isEmpty()){
            return ResponseEntity.noContent().build();
        } else {
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setData(roomDTOS);
            return ResponseEntity.ok(baseResponse);
        }
    }

}
