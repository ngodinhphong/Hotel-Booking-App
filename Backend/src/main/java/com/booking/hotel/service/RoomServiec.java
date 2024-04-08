package com.booking.hotel.service;

import com.booking.hotel.dto.RoomDTO;
import com.booking.hotel.dto.RoomTypesDTO;
import com.booking.hotel.entity.RoomEntity;
import com.booking.hotel.payload.request.RoomRequest;
import com.booking.hotel.repository.RoomRepository;
import com.booking.hotel.service.imp.FileServiceImp;
import com.booking.hotel.service.imp.RoomServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomServiec implements RoomServiceImp {

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    FileServiceImp fileServiceImp;

    @Override
    public boolean addRoom(String roomType, double roomPrice, MultipartFile image) {
        boolean isSuccess = false;
        fileServiceImp.saveFile(image);
        try {
            RoomEntity roomEntity = new RoomEntity();
            roomEntity.setRoomType(roomType);
            roomEntity.setRoomPrice(roomPrice);
            roomEntity.setImage(image.getOriginalFilename());
            roomRepository.save(roomEntity);
            isSuccess = true;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi dữ liệu" + e.getMessage());
        }

        return isSuccess;
    }

    @Override
    public List<RoomDTO> getAllRoom() {
        List<RoomDTO> roomDTOS = new ArrayList<>();
        List<RoomEntity> roomEntities = roomRepository.findAll();
        roomEntities.forEach(room -> {
            RoomDTO roomDTO = new RoomDTO();
            roomDTO.setRoomType(room.getRoomType());
            roomDTO.setId(room.getId());
            roomDTO.setRoomPrice(room.getRoomPrice());
            roomDTO.setImageDynamic(room.getImage());
            roomDTOS.add(roomDTO);
        });

        return roomDTOS;
    }

    @Override
    public List<RoomTypesDTO> getRoomType() {
        List<RoomEntity> roomEntities = roomRepository.findAll();
        List<RoomTypesDTO> roomDTOS = new ArrayList<>();
        roomEntities.forEach(room -> {
            RoomTypesDTO roomDTO = new RoomTypesDTO();
            roomDTO.setRoomType(room.getRoomType());
            roomDTOS.add(roomDTO);
        });

        return roomDTOS.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public boolean deleteRoom(int id) {
        boolean isSuccess = false;
        Optional<RoomEntity> roomEntity = roomRepository.findById(id);
        if (roomEntity.isPresent()) {
            roomRepository.deleteById(id);
            isSuccess = true;
        }
        return isSuccess;
    }

    @Override
    public boolean updateRoom(RoomRequest roomRequest) {
        boolean isSucces = false;
        RoomEntity roomEntity = roomById(roomRequest.getId());
        try {
            fileServiceImp.deleteFile(roomEntity.getImage());
            fileServiceImp.saveFile(roomRequest.getImage());

            RoomEntity rooms = new RoomEntity();
            rooms.setId(roomRequest.getId());
            rooms.setRoomType(roomRequest.getRoomType());
            rooms.setRoomPrice(roomRequest.getRoomPrice());
            rooms.setImage(roomRequest.getImage().getOriginalFilename());
            roomRepository.save(rooms);
            isSucces = true;

        } catch (Exception e) {
            throw new RuntimeException("lỗi update" + e.getMessage());
        }


        return isSucces;
    }

    @Override
    public RoomDTO getRoomById(int id) {
        RoomEntity roomEntity = roomById(id);
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setId(roomEntity.getId());
        roomDTO.setRoomType(roomEntity.getRoomType());
        roomDTO.setRoomPrice(roomEntity.getRoomPrice());
        roomDTO.setImageDynamic(roomEntity.getImage());
        return roomDTO;
    }

    public RoomEntity roomById(int id) {
        Optional<RoomEntity> roomEntity = roomRepository.findById(id);
        return roomEntity.orElse(null);
    }


}
