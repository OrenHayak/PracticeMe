package com.example.eliavmenachi.myapplication.Model;

import android.os.AsyncTask;
import java.util.List;

public class ExerciseAsynchDao {

    interface ExerciseAsynchDaoListener<T> {
        void onComplete(T data);
    }

    static public void getAll(final ExerciseAsynchDao.ExerciseAsynchDaoListener<List<Exercise>> listener) {
        class MyAsynchTask extends AsyncTask<String, String, List<Exercise>> {
            @Override
            protected List<Exercise> doInBackground(String... strings) {
                List<Exercise> exList = AppLocalDb.db.exerciseDao().getAll();
                return exList;
            }

            @Override
            protected void onPostExecute(List<Exercise> exercise) {
                super.onPostExecute(exercise);
                listener.onComplete(exercise);
            }
        }

        MyAsynchTask task = new MyAsynchTask();
        task.execute();

    }

    static void insertAll(final List<Exercise> exercises, final ExerciseAsynchDao.ExerciseAsynchDaoListener<Boolean> listener){
        class MyAsynchTask extends AsyncTask<List<Exercise>,String,Boolean>{
            @Override
            protected Boolean doInBackground(List<Exercise>... exercises) {
                for (Exercise st:exercises[0]) {
                    AppLocalDb.db.exerciseDao().insertAll(st);
                }
                return true;
            }

            @Override
            protected void onPostExecute(Boolean success) {
                super.onPostExecute(success);
                listener.onComplete(success);
            }
        }
        MyAsynchTask task = new MyAsynchTask();
        task.execute(exercises);
    }
}
