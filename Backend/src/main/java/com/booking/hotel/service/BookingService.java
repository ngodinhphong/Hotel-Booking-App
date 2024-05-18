package com.booking.hotel.service;

import com.booking.hotel.dto.BookingDTO;
import com.booking.hotel.dto.RoomDTO;
import com.booking.hotel.entity.BookingEntity;
import com.booking.hotel.entity.RoomEntity;
import com.booking.hotel.exception.InvalidBookingRequestException;
import com.booking.hotel.exception.ResourceNotFoundException;
import com.booking.hotel.repository.BookingRepository;
import com.booking.hotel.repository.RoomRepository;
import com.booking.hotel.service.imp.BookingServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService implements BookingServiceImp {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomService roomService;

    @Override
    public List<BookingDTO> getAllBookings() {
        List<BookingDTO> bookingDTO = new ArrayList<>() ;
        List<BookingEntity> bookingEntities = bookingRepository.findAll();

        bookingEntities.forEach( booking -> {
            BookingDTO bookingDTOs = getBookingDto(booking);
            bookingDTO.add(bookingDTOs);
        });

        return bookingDTO;
    }

    @Override
    public String saveBooking(int id, BookingEntity bookingRequest) {
        if (bookingRequest.getCheckOutDate().isBefore(bookingRequest.getCheckInDate())){
            throw new InvalidBookingRequestException("Check-in date must come before check-out date");
        }
        RoomEntity roomEntity = roomService.roomById(id);
        List<BookingEntity> existingBookings = roomEntity.getBookings();
        boolean roomIsAvailable = roomIsAvailable(bookingRequest,existingBookings);
        if (roomIsAvailable){
            roomEntity.addBooking(bookingRequest);
            bookingRepository.save(bookingRequest);
        }else{
            throw  new InvalidBookingRequestException("Sorry, This room is not available for the selected dates;");
        }
        return bookingRequest.getConfirmationCode();
    }

    @Override
    public BookingDTO getBookingByConfirmationCode(String confirmationCode) {
        BookingEntity booking = bookingRepository.findByConfirmationCode(confirmationCode)
                .orElseThrow(() -> new ResourceNotFoundException("No booking found with booking code :"+confirmationCode));
        BookingDTO bookingDTO = getBookingDto(booking);
        return bookingDTO;
    }


    @Override
    public boolean deleteBooking(int bookingId) {
        boolean isSuccess = false;
        Optional<BookingEntity> bookingEntity = bookingRepository.findById(bookingId);
        if (bookingEntity.isPresent()) {
            bookingRepository.deleteById(bookingId);
            isSuccess = true;
        }
        return isSuccess;
    }

    @Override
    public List<BookingDTO> getBookingByEmail(String email) {
        List<BookingEntity> bookingEntities = bookingRepository.findByGuestEmail(email);
        List<BookingDTO> bookingDTOS = bookingEntities
                .stream()
                .map(
                        booking -> new BookingDTO(
                        booking.getId(),
                        booking.getCheckInDate(),
                        booking.getCheckOutDate(),
                        booking.getConfirmationCode(),
                        new RoomDTO(
                                booking.getRoom().getId()
                                ,booking.getRoom().getRoomType()
                        )
                )).toList();
        return bookingDTOS;
    }

    private BookingDTO getBookingDto(BookingEntity booking){
        RoomDTO roomDTO = new RoomDTO(
                booking.getRoom().getId(),
                booking.getRoom().getRoomType(),
                booking.getRoom().getRoomPrice()

        );
        return new BookingDTO(
                booking.getId(),
                booking.getCheckInDate(),
                booking.getCheckOutDate(),
                booking.getAdults(),
                booking.getChildren(),
                booking.getTotalGuest(),
                booking.getConfirmationCode(),
                booking.getGuestEmail(),
                booking.getGuestFullName(),
                roomDTO
        );
    }


    private boolean roomIsAvailable(BookingEntity bookingRequest, List<BookingEntity> bookingEntities) {
        return bookingEntities.stream()
                .noneMatch(existingBooking ->
                        bookingRequest.getCheckInDate().equals(existingBooking.getCheckInDate())
                                || bookingRequest.getCheckOutDate().isBefore(existingBooking.getCheckOutDate())
                                || (bookingRequest.getCheckInDate().isAfter(existingBooking.getCheckInDate())
                                && bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckOutDate()))
                                || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())

                                && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckOutDate()))
                                || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())

                                && bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckOutDate()))

                                || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                                && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckInDate()))

                                || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                                && bookingRequest.getCheckOutDate().equals(bookingRequest.getCheckInDate()))
                );
    }
}
