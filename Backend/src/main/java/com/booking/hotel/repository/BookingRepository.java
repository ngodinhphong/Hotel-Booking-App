package com.booking.hotel.repository;

import com.booking.hotel.entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Integer> {
    Optional<BookingEntity> findByConfirmationCode (String confirmationCode);

    List<BookingEntity> findByGuestEmail(String email);
}
