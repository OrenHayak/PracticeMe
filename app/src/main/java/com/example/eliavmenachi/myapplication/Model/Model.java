package com.example.eliavmenachi.myapplication.Model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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

/**
 * Created by eliav.menachi on 09/05/2018.
 */

public class Model {
    public static Model instance = new Model();
    ModelFirebase modelFirebase;
    private Model(){
        modelFirebase = new ModelFirebase();
    }

    public void cancellGetAllExercises() {
        modelFirebase.cancellGetAllExercises();
    }

    class ExerciseListData extends  MutableLiveData<List<Exercise>>{



        @Override
        protected void onActive() {
            super.onActive();
            // new thread tsks
            // 1. get the exercises list from the local DB
            ExerciseAsynchDao.getAll(new ExerciseAsynchDao.ExerciseAsynchDaoListener<List<Exercise>>() {
                @Override
                public void onComplete(List<Exercise> data) {
                    // 2. update the live data with the new exercise list
                    setValue(data);
                    Log.d("TAG","got exercises from local DB " + data.size());

                    // 3. get the exercise list from firebase
                    modelFirebase.getAllExercises(new ModelFirebase.GetAllExercisesListener() {
                        @Override
                        public void onSuccess(List<Exercise> exerciseslist) {
                            // 4. update the live data with the new exercise list
                            setValue(exerciseslist);
                            Log.d("TAG","got exercises from firebase " + exerciseslist.size());

                            // 5. update the local DB
                            ExerciseAsynchDao.insertAll(exerciseslist, new ExerciseAsynchDao.ExerciseAsynchDaoListener<Boolean>() {
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
            modelFirebase.cancellGetAllExercises();
            Log.d("TAG","cancellGetAllExercises");
        }

        public ExerciseListData() {
            super();
            //setValue(AppLocalDb.db.exerciseDao().getAll());
            setValue(new LinkedList<Exercise>());
        }
    }

    ExerciseListData exerciseListData = new ExerciseListData();

    public LiveData<List<Exercise>> getAllExercises(){
        return exerciseListData;
    }

    public void addExercise(Exercise exercise){
        modelFirebase.addExercise(exercise);
    }

    public class ExerciseListDataByUserMail extends MutableLiveData<List<Exercise>> {
        String usermail = "";

        @Override
        protected void onActive() {

            ExerciseAsynchDao.getExerciseByUserMail(usermail, new ExerciseAsynchDao.ExerciseAsynchDaoListener<List<Exercise>>() {
                @Override
                public void onComplete(List<Exercise> data) {
                    setValue(data);
                    modelFirebase.GetExerciseByUserMail(usermail, new ModelFirebase.GetExerciseByUserMail() {
                        @Override
                        public void onGetData(List<Exercise> data) {
                            setValue(data);
                        }
                    });
                }
            });
        }

        @Override
        protected void onInactive() {
            super.onInactive();
        }

        public ExerciseListDataByUserMail() {
            super();
            setValue(new LinkedList());
        }

        public void InitUserMail(String usermaile) {
            usermail = usermaile;
        }
    }

    public ExerciseListDataByUserMail ExerciseListDataByUserMail = new ExerciseListDataByUserMail();

    public LiveData<List<Exercise>> getExerciseByUserMail(String usermaile) {
        return ExerciseListDataByUserMail;
    }

    public void InitUserMail(String usermaile) {
        if (ExerciseListDataByUserMail == null) {
            ExerciseListDataByUserMail = new ExerciseListDataByUserMail();
        }

        ExerciseListDataByUserMail.InitUserMail(usermaile);
    }




    ////////////////////////////////////////////////////////
    //  HAndle Image Files
    ////////////////////////////////////////////////////////



    public interface SaveImageListener{
        void onDone(String url);
    }

    public void saveImage(Bitmap imageBitmap, SaveImageListener listener) {
        modelFirebase.saveImage(imageBitmap,listener);
    }



    public interface GetImageListener{
        void onDone(Bitmap imageBitmap);
    }
    public void getImage(final String url, final GetImageListener listener ){
        String localFileName = URLUtil.guessFileName(url, null, null);
        final Bitmap image = loadImageFromFile(localFileName);
        if (image == null) {                                      //if image not found - try downloading it from parse
            modelFirebase.getImage(url, new GetImageListener() {
                @Override
                public void onDone(Bitmap imageBitmap) {
                    if (imageBitmap == null) {
                        listener.onDone(null);
                    }else {
                        //2.  save the image localy
                        String localFileName = URLUtil.guessFileName(url, null, null);
                        Log.d("TAG", "save image to cache: " + localFileName);
                        saveImageToFile(imageBitmap, localFileName);
                        //3. return the image using the listener
                        listener.onDone(imageBitmap);
                    }
                }
            });
        }else {
            Log.d("TAG","OK reading cache image: " + localFileName);
            listener.onDone(image);
        }
    }

    // Store / Get from local mem
    private void saveImageToFile(Bitmap imageBitmap, String imageFileName){
        if (imageBitmap == null) return;
        try {
            File dir = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES);
            if (!dir.exists()) {
                dir.mkdir();
            }
            File imageFile = new File(dir,imageFileName);
            imageFile.createNewFile();

            OutputStream out = new FileOutputStream(imageFile);
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.close();

            //addPicureToGallery(imageFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Bitmap loadImageFromFile(String imageFileName){
        Bitmap bitmap = null;
        try {
            File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File imageFile = new File(dir,imageFileName);
            InputStream inputStream = new FileInputStream(imageFile);
            bitmap = BitmapFactory.decodeStream(inputStream);
            Log.d("tag","got image from cache: " + imageFileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

}





