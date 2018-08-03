package com.example.eliavmenachi.myapplication.Model;

import android.os.AsyncTask;
import java.util.List;

public class UserProfileAsynchDao {

    interface UserProfileAsynchDaoListener<T> {
        void onComplete(T data);
    }

    static public void getAll(final UserProfileAsynchDao.UserProfileAsynchDaoListener<List<UserProfile>> listener) {
        class MyAsynchTask extends AsyncTask<String, String, List<UserProfile>> {
            @Override
            protected List<UserProfile> doInBackground(String... strings) {
                List<UserProfile> usList = AppLocalDb.db.userprofileDao().getAll();
                return usList;
            }

            @Override
            protected void onPostExecute(List<UserProfile> userProfiles) {
                super.onPostExecute(userProfiles);
                listener.onComplete(userProfiles);
            }
        }

        MyAsynchTask task = new MyAsynchTask();
        task.execute();

    }

    static void insertAll(final List<UserProfile> userProfiles, final UserProfileAsynchDao.UserProfileAsynchDaoListener<Boolean> listener){
        class MyAsynchTask extends AsyncTask<List<UserProfile>,String,Boolean>{
            @Override
            protected Boolean doInBackground(List<UserProfile>... userprofiles) {
                for (UserProfile us:userprofiles[0]) {
                    AppLocalDb.db.userprofileDao().insertAll(us);
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
        task.execute(userProfiles);
    }
}
