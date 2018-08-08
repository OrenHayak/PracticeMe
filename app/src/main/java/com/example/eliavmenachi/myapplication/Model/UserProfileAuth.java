package com.example.eliavmenachi.myapplication.Model;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class UserProfileAuth {


        public FirebaseUser getCurrentUser() {
            return FirebaseAuth.getInstance().getCurrentUser();
        }

        public interface SignInCallback {
            void onSuccess(String userID, String userName);

            void onFailure(String exceptionMessage);
        }

        public void signInWithEmailAndPassword(String email, String password, final SignInCallback callback) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                callback.onFailure("Signing in has failed: " + task.getException().getMessage());
                            } else {
                                FirebaseUser user = task.getResult().getUser();
                                callback.onSuccess(user.getUid(), user.getDisplayName());
                            }
                        }
                    });
        }

        /*public interface CreateUserCallback {
            void onSuccess(UserProfile userProfile;

            void onFailure(String exceptionMessage);
        }*/

        /*public void createUser(final UserProfile userProfile,
                               final String email,
                               final String password,
                               final CreateUserCallback callback) {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        String exception = task.getException().getMessage();
                        callback.onFailure("Registration has failed: " + exception);
                        //callback.onFailure("Registration has failed: " + exception);
                    } else {
                        final FirebaseUser firebaseUser = task.getResult().getUser();

                        updateUserProfileAfterCreate(firebaseUser, userProfile, callback);
                    }
                }
            });
        }*/

        /*private void updateUserProfileAfterCreate(final FirebaseUser firebaseUser,
                                                  final UserProfile userProfile,
                                                  final CreateUserCallback callback) {
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(userProfile.username)
                    .build();

            firebaseUser.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (!task.isSuccessful()) {
                        String exception = task.getException().getMessage();
                        callback.onFailure("Updating the user's profile has failed: " + exception);
                    } else {
                        // Todo Check with Oren if it's the username actually
                        userProfile.username = firebaseUser.getUid();
//                    user.email = firebaseUser.getEmail();
                        userProfile.username = userProfile.username;
                        userProfile.name = userProfile.name;

                        callback.onSuccess(userProfile);
                        // TODO: CALL from user model to firebase
                        //UserModelFirebase.addUser()
                    }
                }
            });
        }*/

        public void signOut() {
            FirebaseAuth.getInstance().signOut();
        }
}
