package com.example.eliavmenachi.myapplication.Model;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class UserProfileModelFirebase {
    public void addUserProfile(UserProfile userProfile){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("userprofiles").child(userProfile.username).setValue(userProfile);
    }

    public void cancellGetAllUserProfiles() {
        DatabaseReference stRef = FirebaseDatabase.getInstance().getReference().child("userprofiles");
        stRef.removeEventListener(eventListener);
    }

    interface GetAllUserProfilesListener{
        public void onSuccess(List<UserProfile> userProfileList);
    }

    ValueEventListener eventListener;

    public void getAllUserProfiles(final UserProfileModelFirebase.GetAllUserProfilesListener listener) {
        DatabaseReference exRef = FirebaseDatabase.getInstance().getReference().child("userprofiles");

        eventListener = exRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<UserProfile> usList = new LinkedList<>();

                for (DataSnapshot stSnapshot: dataSnapshot.getChildren()) {
                    UserProfile userProfile = stSnapshot.getValue(UserProfile.class);
                    usList.add(userProfile);
                }
                listener.onSuccess(usList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void saveUserProfile(final UserProfile userProfile, final UserProfileModel.SaveUserProfileListener listener) {

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(userProfile.email, userProfile.password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            listener.onDone();
                        } else {
                            FirebaseUser userData = task.getResult().getUser();
                            //listener.onDone();
                            updateUserProfileAfterCreate(userData, userProfile, listener);
                        }
                    }
                });
    }

    private void updateUserProfileAfterCreate(final FirebaseUser firebaseUser,
                                              final UserProfile user,
                                              final UserProfileModel.SaveUserProfileListener callback) {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(user.username)
                .build();

        firebaseUser.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (!task.isSuccessful()) {
                    //String exception = task.getException().getMessage();
                    callback.onDone();
                } else {
                    user.email = firebaseUser.getEmail();
                    user.username = firebaseUser.getDisplayName();


                    callback.onDone();
                    // TODO: CALL from user model to firebase
                    //UserModelFirebase.addUser()
                }
            }
        });
    }
    }
