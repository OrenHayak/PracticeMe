package com.example.eliavmenachi.myapplication.Model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import com.example.eliavmenachi.myapplication.MyApplication;

@Database(entities = {Exercise.class,UserProfile.class}, version = 8)
abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract ExerciseDao exerciseDao();
    public abstract UserProfileDao userprofileDao();
}

public class AppLocalDb{
    static public AppLocalDbRepository db = Room.databaseBuilder(MyApplication.context,
            AppLocalDbRepository.class,
            "dbFileName.db").fallbackToDestructiveMigration().build();
}
