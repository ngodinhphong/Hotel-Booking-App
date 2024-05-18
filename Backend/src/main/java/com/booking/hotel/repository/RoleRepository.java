package com.booking.hotel.repository;

import com.booking.hotel.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
    Optional<RoleEntity> findByName(String role);

    boolean existsByName(String role);

    boolean existsById (int id);
}
