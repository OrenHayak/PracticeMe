package com.example.eliavmenachi.myapplication.Model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface UserProfileDao {
    @Query("select * from UserProfile")
    List<UserProfile> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(UserProfile... userProfiles);

    @Delete
    void delete(UserProfile userProfile);
}
