package com.example.eliavmenachi.myapplication.Model;

import android.app.admin.DeviceAdminInfo;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

public class UserProfile {

    @PrimaryKey
    @NonNull
    public String username;
    public String password;
    public String Name;
    public String Email;
    public String Goals;


    public String getFirstName() {
        return Name;
    }
    public String getGoals() {
        return Goals;
    }
    public String getPassword() {
        return password;
    }
    @NonNull
    public String getUsername() {
        return username;
    }
    public String getEmail() { return Email; }

    public void setName(String name) {
        Name = name;
    }
    public void setGoals(String goals) {
        Goals = goals;
    }
}
