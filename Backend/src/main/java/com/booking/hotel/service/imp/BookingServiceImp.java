package com.booking.hotel.service.imp;

import com.booking.hotel.dto.BookingDTO;
import com.booking.hotel.entity.BookingEntity;
import com.booking.hotel.payload.request.BookingRequest;

import java.util.List;

public interface BookingServiceImp {

    List<BookingDTO> getAllBookings();

    String saveBooking(int id, BookingEntity bookingRequest);

    BookingDTO getBookingByConfirmationCode(String confirmationCode);

    boolean deleteBooking(int bookingId);

    List<BookingDTO> getBookingByEmail(String email);
}
