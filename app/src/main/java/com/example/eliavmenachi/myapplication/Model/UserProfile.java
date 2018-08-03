package com.example.eliavmenachi.myapplication.Model;

import android.app.admin.DeviceAdminInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

@Entity
public class UserProfile {

    @PrimaryKey
    @NonNull
    public String username;
    public String password;
    public String name;
    public String email;
    public String goals;


    public String getName() {
        return this.name;
    }
    public String getGoals() {
        return this.goals;
    }
    public String getPassword() {
        return this.password;
    }
    public String getUsername() {
        return this.username;
    }
    public String getEmail() { return this.email; }

    public void setName(String namee) { this.name = namee; }
    public void setGoals(String goalse) {
        this.goals = goalse;
    }
    public void setPassword(String passworde) {
        this.password = passworde;
    }
    public void setEmail(String emaile) { this.email = emaile; }
    public void setUsername(String usernamee) {
        this.username = usernamee;
    }
}
