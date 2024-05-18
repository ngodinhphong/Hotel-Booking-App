package com.booking.hotel.dto;

import java.util.List;

public class RoleDTO {
    private int id;
    private String name;
    private List<UserDTO> users;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }

    public RoleDTO() {
    }

    public RoleDTO(int id, String name, List<UserDTO> users) {
        this.id = id;
        this.name = name;
        this.users = users;
    }

    public RoleDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
