package com.booking.hotel.controller;

import com.booking.hotel.dto.BookingDTO;
import com.booking.hotel.entity.BookingEntity;
import com.booking.hotel.exception.InvalidBookingRequestException;
import com.booking.hotel.exception.ResourceNotFoundException;
import com.booking.hotel.payload.response.BaseResponse;
import com.booking.hotel.service.imp.BookingServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingServiceImp bookingServiceImp;

    @GetMapping("/all-bookings")
    public ResponseEntity<?> getAllBooking(){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(bookingServiceImp.getAllBookings());

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("/add/{id}")
    public ResponseEntity<?> saveBooking(@PathVariable int id, @RequestBody BookingEntity bookingRequest){

        try{
            String confirmationCode = bookingServiceImp.saveBooking(id, bookingRequest);
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setMessage("Room booked successfully, Your booking confirmation code is :");
            baseResponse.setData(confirmationCode);
            return ResponseEntity.ok(baseResponse);

        }catch (InvalidBookingRequestException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/confirmation/{confirmationCode}")
    public ResponseEntity<?> getBookingByConfirmationCode(@PathVariable String confirmationCode){
        try {
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setData(bookingServiceImp.getBookingByConfirmationCode(confirmationCode));
            return ResponseEntity.ok(baseResponse);
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<?> getBookingsByUserEmail(@PathVariable String email){
        List<BookingDTO> bookingDTO = bookingServiceImp.getBookingByEmail(email);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(bookingDTO);
        return ResponseEntity.ok(baseResponse);
    }

    @DeleteMapping("/delete/{bookingId}")
    public ResponseEntity<?> cancelBooking(@PathVariable int bookingId){
        BaseResponse baseResponse = new BaseResponse();
        boolean cancelBooking = bookingServiceImp.deleteBooking(bookingId);
        baseResponse.setMessage(cancelBooking ? "Booking has been cancelled successfully!" : "Booking has been cancelled failed!");

        return ResponseEntity.ok(baseResponse);
    }
}
