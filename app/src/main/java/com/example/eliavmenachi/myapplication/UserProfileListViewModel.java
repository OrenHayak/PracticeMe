package com.example.eliavmenachi.myapplication;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.eliavmenachi.myapplication.Model.Exercise;
import com.example.eliavmenachi.myapplication.Model.Model;
import com.example.eliavmenachi.myapplication.Model.UserProfile;
import com.example.eliavmenachi.myapplication.Model.UserProfileModel;

import java.util.List;

public class UserProfileListViewModel extends ViewModel {
    LiveData<List<UserProfile>> data;

    public LiveData<List<UserProfile>> getData(){
        data = UserProfileModel.instance.getAllUserProfiles();
        return data;
    }

}
