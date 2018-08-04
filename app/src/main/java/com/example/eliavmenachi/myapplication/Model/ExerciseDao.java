package com.example.eliavmenachi.myapplication.Model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ExerciseDao {
    @Query("select * from Exercise")
    List<Exercise> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Exercise... exercises);

    @Delete
    void delete(Exercise exercise);

    //@Query("SELECT * FROM Exercise where ownermail LIKE :userMail and  active = 1")
    //List<Exercise> getExerciseByUserMail(String userMail);
}

