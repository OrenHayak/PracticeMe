package com.example.eliavmenachi.myapplication.Model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import com.example.eliavmenachi.myapplication.MyApplication;

@Database(entities = {Exercise.class}, version = 2)
abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract ExerciseDao exerciseDao();
}

public class AppLocalDb{
    static public AppLocalDbRepository db = Room.databaseBuilder(MyApplication.context,
            AppLocalDbRepository.class,
            "dbFileName.db").fallbackToDestructiveMigration().build();
}
