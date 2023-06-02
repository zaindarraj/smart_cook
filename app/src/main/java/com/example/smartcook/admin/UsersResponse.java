package com.example.smartcook.admin;

import com.example.smartcook.classes.User;

import java.util.ArrayList;

public class UsersResponse {
    int code;
    ArrayList<User> users;

    public int getCode() {
        return code;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
