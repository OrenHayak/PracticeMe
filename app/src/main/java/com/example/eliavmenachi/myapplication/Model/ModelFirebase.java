package com.example.eliavmenachi.myapplication.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ModelFirebase {

    public void addExercise(Exercise exercise){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("exercises").child(exercise.id).setValue(exercise);
    }

    public void cancellGetAllExercises() {
        DatabaseReference stRef = FirebaseDatabase.getInstance().getReference().child("exercises");
        stRef.removeEventListener(eventListener);
    }




    interface GetAllExercisesListener{
        public void onSuccess(List<Exercise> exerciseslist);
    }

    ValueEventListener eventListener;

    public void getAllExercises(final GetAllExercisesListener listener) {
        DatabaseReference exRef = FirebaseDatabase.getInstance().getReference().child("exercises");

        eventListener = exRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Exercise> exList = new LinkedList<>();

                for (DataSnapshot stSnapshot: dataSnapshot.getChildren()) {
                    Exercise exercise = stSnapshot.getValue(Exercise.class);

                    if (exercise.active == true) {
                        exList.add(exercise);
                    }
                }
                listener.onSuccess(exList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    interface GetExerciseByUserMail {
        public void onGetData(List<Exercise> data);
    }

    ValueEventListener eventListener4;

    public void GetExerciseByUserMail(final String UserMail, final GetExerciseByUserMail listener) {
        Query stRef = FirebaseDatabase.getInstance().getReference().child("exercises").orderByChild("ownermail").equalTo(UserMail);
        eventListener4 = stRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Exercise> stExercises = new LinkedList<>();
                for (DataSnapshot stSnapshot : dataSnapshot.getChildren()) {
                    Exercise currExercise = stSnapshot.getValue(Exercise.class);
                    if ((currExercise.active) && (currExercise.ownermail.toString().trim().equalsIgnoreCase(UserMail.toString().trim()))) {
                        stExercises.add(currExercise);
                    }
                }

                listener.onGetData(stExercises);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }







    //Managing Files
    public void saveImage(Bitmap imageBitmap, final Model.SaveImageListener listener) {
        FirebaseStorage storage = FirebaseStorage.getInstance();

        Date d = new Date();
        String name = ""+ d.getTime();
        StorageReference imagesRef = storage.getReference().child("images").child(name);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception exception) {
                listener.onDone(null);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();
                listener.onDone(downloadUrl.toString());
            }
        });

    }


    public void getImage(String url, final Model.GetImageListener listener){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference httpsReference = storage.getReferenceFromUrl(url);
        final long ONE_MEGABYTE = 1024 * 1024;
        httpsReference.getBytes(3* ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap image = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                Log.d("TAG","get image from firebase success");
                listener.onDone(image);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception exception) {
                Log.d("TAG",exception.getMessage());
                Log.d("TAG","get image from firebase Failed");
                listener.onDone(null);
            }
        });
    }

}
