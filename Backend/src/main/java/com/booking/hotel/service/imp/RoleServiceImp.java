package com.booking.hotel.service.imp;

import com.booking.hotel.dto.RoleDTO;
import com.booking.hotel.entity.RoleEntity;
import com.booking.hotel.payload.request.RoleRequest;

import java.util.List;

public interface RoleServiceImp {
    List<RoleDTO> getAllRole();

    RoleEntity createNewRole(RoleRequest theRole);

    boolean deleteRole(int roleId);

}
