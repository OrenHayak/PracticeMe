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

    public interface SignInListener {
        void onSuccess();

        void onFailure(String exceptionMessage);
    }
    public void signIn(final String email, final String password, final SignInListener listener) {
        UserProfileModel.instance.signIn(email, password, new UserProfileModel.SignInListener() {
            @Override
            public void onSuccess() {
                listener.onSuccess();
            }

            @Override
            public void onFailure(String exceptionMessage) {
                listener.onFailure(exceptionMessage);
            }
        });
    }

    public interface SignOutListener {
        void onSuccess();

        void onFailure(String exceptionMessage);
    }
    public void signOut(final SignOutListener listener) {
        UserProfileModel.instance.signOut(new UserProfileModel.SignOutListener() {
            @Override
            public void onSuccess() {
                listener.onSuccess();
            }

            @Override
            public void onFailure(String exceptionMessage) {
                listener.onFailure(exceptionMessage);
            }
        });
    }
}
