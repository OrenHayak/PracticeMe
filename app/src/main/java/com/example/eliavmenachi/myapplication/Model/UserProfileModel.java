package com.example.eliavmenachi.myapplication.Model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.webkit.URLUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

public class UserProfileModel {
    public static UserProfileModel instance = new UserProfileModel();
    UserProfileModelFirebase modelFirebase;
    private UserProfileAuth userAuthModel;
    private UserProfileModel(){
        modelFirebase = new UserProfileModelFirebase();
        userAuthModel = new UserProfileAuth();
    }
    UserProfile userProfile;

    public void cancellGetAllUserProfiles() {
        modelFirebase.cancellGetAllUserProfiles();
    }

    class UserProfileListData extends MutableLiveData<List<UserProfile>> {



        @Override
        protected void onActive() {
            super.onActive();
            // new thread tasks
            // 1. get the UserProfiles list from the local DB
            UserProfileAsynchDao.getAll(new UserProfileAsynchDao.UserProfileAsynchDaoListener<List<UserProfile>>() {
                @Override
                public void onComplete(List<UserProfile> data) {
                    // 2. update the live data with the new UserProfile list
                    setValue(data);
                    Log.d("TAG","got UserProfiles from local DB " + data.size());

                    // 3. get the UserProfile list from firebase
                    modelFirebase.getAllUserProfiles(new UserProfileModelFirebase.GetAllUserProfilesListener() {
                        @Override
                        public void onSuccess(List<UserProfile> userProfileslist) {
                            // 4. update the live data with the new UserProfile list
                            setValue(userProfileslist);
                            Log.d("TAG","got UserProfiles from firebase " + userProfileslist.size());

                            // 5. update the local DB
                            UserProfileAsynchDao.insertAll(userProfileslist, new UserProfileAsynchDao.UserProfileAsynchDaoListener<Boolean>() {
                                @Override
                                public void onComplete(Boolean data) {

                                }
                            });
                        }
                    });
                }
            });
        }

        @Override
        protected void onInactive() {
            super.onInactive();
            modelFirebase.cancellGetAllUserProfiles();
            Log.d("TAG","cancellGetAllUserProfiles");
        }

        public UserProfileListData() {
            super();
            //setValue(AppLocalDb.db.exerciseDao().getAll());
            setValue(new LinkedList<UserProfile>());
        }
    }

    UserProfileModel.UserProfileListData userProfileListData = new UserProfileModel.UserProfileListData();

    public LiveData<List<UserProfile>> getAllUserProfiles(){
        return userProfileListData;
    }

    public void addUserProfile(UserProfile userProfile){
        modelFirebase.addUserProfile(userProfile);
    }

    public interface SaveUserProfileListener{
        void onDone();
    }

    public void saveUserProfile(UserProfile userProfile, UserProfileModel.SaveUserProfileListener listener) {
        modelFirebase.saveUserProfile(userProfile, listener);
    }

    public interface SignInListener {
        void onSuccess();

        void onFailure(String exceptionMessage);
    }
    public void signIn(final String email, final String password, final SignInListener listener) {
        userAuthModel.signInWithEmailAndPassword(email, password, new UserProfileAuth.SignInCallback() {
            @Override
            public void onSuccess(String userID, String userName) {
                userProfile = new UserProfile();
                listener.onSuccess();
            }

            @Override
            public void onFailure(String exceptionMessage) {
                listener.onFailure(exceptionMessage);
            }
        });
    }

}
