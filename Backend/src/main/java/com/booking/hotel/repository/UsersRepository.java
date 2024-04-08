package com.booking.hotel.repository;

import com.booking.hotel.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity,Integer> {
    UsersEntity findByEmail(String username);
}
