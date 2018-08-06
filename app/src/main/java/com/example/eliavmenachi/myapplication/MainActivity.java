package com.example.eliavmenachi.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;



public class MainActivity extends AppCompatActivity {
    EditText nameTv;


    final int REQUEST_WRITE_STORAGE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("TAG","my message .... ");

        if (savedInstanceState == null) {
            loginFragment fragment = new loginFragment();
            FragmentTransaction tran = getSupportFragmentManager().beginTransaction();
            tran.add(R.id.main_container, fragment);
                         tran.addToBackStack("");
            tran.commit();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_STORAGE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_WRITE_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_add:
                Log.d("TAG","menu add selected");
                NewExerciseFragment fragment = new NewExerciseFragment();
                FragmentTransaction tran = getSupportFragmentManager().beginTransaction();
                tran.replace(R.id.main_container, fragment);
                tran.addToBackStack("tag");
                tran.commit();
                return true;

            case R.id.menu_my_posts:
                Log.d("TAG","menu login selected");
                ExercisesListFragment mypostsfragment = new ExercisesListFragment();
                mypostsfragment.isbyuser = true;
                FragmentTransaction tranmyposts = getSupportFragmentManager().beginTransaction();
                tranmyposts.replace(R.id.main_container, mypostsfragment);
                tranmyposts.addToBackStack("tag");
                tranmyposts.commit();
                return true;


            case R.id.menu_all_posts:
                Log.d("TAG","menu all posts selected");
                ExercisesListFragment allPostsFragments = new ExercisesListFragment();
                FragmentTransaction tranAll = getSupportFragmentManager().beginTransaction();
                tranAll.replace(R.id.main_container, allPostsFragments);
                tranAll.addToBackStack("tag");
                tranAll.commit();
                return true;

            case R.id.menu_out:
                Log.d("TAG","menu out selected");
                loginFragment loginfragment = new loginFragment();
                FragmentTransaction tranLogin = getSupportFragmentManager().beginTransaction();
                tranLogin.replace(R.id.main_container, loginfragment);
                tranLogin.addToBackStack("tag");
                tranLogin.commit();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

}














