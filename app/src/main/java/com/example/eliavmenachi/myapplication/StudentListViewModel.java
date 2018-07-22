package com.example.eliavmenachi.myapplication;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.eliavmenachi.myapplication.Model.Exercise;
import com.example.eliavmenachi.myapplication.Model.Model;

import java.util.List;

public class StudentListViewModel extends ViewModel {
    LiveData<List<Exercise>> data;

    public LiveData<List<Exercise>> getData(){
        data = Model.instance.getAllExercises();
        return data;
    }


}


