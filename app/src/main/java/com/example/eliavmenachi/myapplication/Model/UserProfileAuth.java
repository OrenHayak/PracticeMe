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



        public void signOut() {
            FirebaseAuth.getInstance().signOut();
        }
}
