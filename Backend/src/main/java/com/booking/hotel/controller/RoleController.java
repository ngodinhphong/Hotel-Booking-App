package com.booking.hotel.controller;

import com.booking.hotel.entity.RoleEntity;
import com.booking.hotel.exception.RoleAlreadyExistException;
import com.booking.hotel.payload.request.RoleRequest;
import com.booking.hotel.payload.response.BaseResponse;
import com.booking.hotel.service.imp.RoleServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/role")
@RestController
public class RoleController {

    @Autowired
    private RoleServiceImp roleServiceImp;

    @GetMapping("/all-roles")
    public ResponseEntity<?> getAllRoles(){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(roleServiceImp.getAllRole());
        return ResponseEntity.ok(baseResponse);
    }

    @PostMapping("/create-new-role")
    public ResponseEntity<?> createRole(@RequestBody RoleRequest theRole){
        try {
            RoleEntity roles = roleServiceImp.createNewRole(theRole);
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setData(roles);
            baseResponse.setMessage("New role created successfully!");
            return ResponseEntity.ok(baseResponse);
        }catch (RoleAlreadyExistException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{roleId}")
    public ResponseEntity<?> deleteRole(@PathVariable("roleId") int roleId){
            boolean isSucces = roleServiceImp.deleteRole(roleId);
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setMessage(isSucces ? "Delete successfully!" : "Id not found.");
            return ResponseEntity.ok(baseResponse);

    }

//    @PostMapping("/change-user-to-role")
//    public ResponseEntity<?> assignUserToRole( @RequestParam("userId") Long userId,
//                                               @RequestParam("roleId") Long roleId){
//        return roleServiceImp.changeRoleToUser(userId, roleId);
//    }

}
