package com.booking.hotel.service;

import com.booking.hotel.dto.RoleDTO;
import com.booking.hotel.entity.RoleEntity;
import com.booking.hotel.exception.RoleAlreadyExistException;
import com.booking.hotel.payload.request.RoleRequest;
import com.booking.hotel.repository.RoleRepository;
import com.booking.hotel.service.imp.RoleServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService implements RoleServiceImp {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public List<RoleDTO> getAllRole() {
        List<RoleEntity> rolesEntities = roleRepository.findAll();
        List<RoleDTO> roleDTOS = rolesEntities
                .stream()
                .map(role -> new RoleDTO(role.getId(), role.getName()))
                .toList();
        return roleDTOS;
    }

    @Override
    public RoleEntity createNewRole(RoleRequest theRole) {
        String roleName = "ROLE_" + theRole.getName().toUpperCase();
        if(roleRepository.existsByName(theRole.getName())){
            throw new RoleAlreadyExistException(theRole.getName()+" role already exists");
        }
        RoleEntity rolesEntity = new RoleEntity();
        rolesEntity.setName(roleName);
        return roleRepository.save(rolesEntity);
    }

    @Override
    public boolean deleteRole(int roleId) {
        if(roleRepository.existsById(roleId)){
            roleRepository.deleteById(roleId);
            return true;
        } else return false;
    }
}
