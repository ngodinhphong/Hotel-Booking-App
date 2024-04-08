package com.booking.hotel.repository;

import com.booking.hotel.entity.RolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RolesEntity, Integer> {
}
