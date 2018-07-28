package com.example.eliavmenachi.myapplication.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Exercise {

    @PrimaryKey
    @NonNull
    public String id;
    public String description;
    public String image;
    public boolean active;

    public String getid() {
        return id;
    }
    public String getDescription() {
        return description;
    }
    public String getImage() {
        return image;
    }
    public boolean isActive() {
        return active;
    }

    public void setDescription(String name) {
        this.description = name;
    }
    public void setImage(String name) {
        this.image = name;
    }
    public void setActive(boolean act) {
        this.active = act;
    }
}
