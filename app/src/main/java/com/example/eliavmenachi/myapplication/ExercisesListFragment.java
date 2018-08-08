package com.example.eliavmenachi.myapplication;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.eliavmenachi.myapplication.Model.Exercise;
import com.example.eliavmenachi.myapplication.Model.Model;
import com.example.eliavmenachi.myapplication.Model.UserProfileModel;

import java.util.List;


public class ExercisesListFragment extends Fragment {
    //private OnFragmentInteractionListener mListener;

    ListView list;
    MyAdapter myAdapter = new MyAdapter();
    MyAdapterByUser userAdapter = new MyAdapterByUser();
    StudentListViewModel dataModel;
    public boolean isbyuser;

    public static ExercisesListFragment newInstance() {
        ExercisesListFragment fragment = new ExercisesListFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dataModel = ViewModelProviders.of(this).get(StudentListViewModel.class);

        Log.d("TAG", "isbyuser " +isbyuser);
        if (!isbyuser) {
            dataModel.getData().observe(this, new Observer<List<Exercise>>() {
                @Override
                public void onChanged(@Nullable List<Exercise> exercises) {
                    myAdapter.notifyDataSetChanged();
                    Log.d("TAG", "notifyDataSetChanged" + exercises.size());
                }
            });
        }
        else
        {

            dataModel.getExerciseListByUserMail(UserProfileModel.instance.getCurrentUserMail()).observe(this, new Observer<List<Exercise>>() {
                @Override
                public void onChanged(@Nullable List<Exercise> exercises) {
                    userAdapter.notifyDataSetChanged();
                    Log.d("TAG", "notifyDataSetChanged" + exercises.size());
                }
            });

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Model.instance.cancellGetAllExercises();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_students_list, container, false);

        getActivity().invalidateOptionsMenu();

        list = view.findViewById(R.id.studentslist_list);

        if (!isbyuser) {
            list.setAdapter(myAdapter);
        }
        else
        {
            list.setAdapter(userAdapter);
        }
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("TAG","item selected:" + i);

                Exercise exerciseChosen;

                if (!isbyuser) {
                    exerciseChosen = dataModel.getData().getValue().get(i);
                }
                else
                {
                    exerciseChosen = dataModel.getExerciseListByUserMail(UserProfileModel.instance.getCurrentUserMail()).getValue().get(i);
                }

                showDetailsFragments detailsfragment = new showDetailsFragments();
                detailsfragment.chosen = exerciseChosen;
                FragmentTransaction tranDetails = getActivity().getSupportFragmentManager().beginTransaction();
                tranDetails.replace(R.id.main_container, detailsfragment);
                tranDetails.addToBackStack("tag");
                tranDetails.commit();
            }
        });
        return view;
    }



    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }


    class MyAdapter extends BaseAdapter {
        public MyAdapter(){
        }

        @Override
        public int getCount() {
            Log.d("TAG","list size:" + dataModel.getData().getValue().size());

            return dataModel.getData().getValue().size();

        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            if (view == null){
                view = LayoutInflater.from(getActivity()).inflate(R.layout.student_list_item,null);

            }

            final Exercise exercise = dataModel.getData().getValue().get(i);

            TextView nameTv = view.findViewById(R.id.stListItem_name_tv);
            TextView idTv = view.findViewById(R.id.stListItem_id_tv);

            final ImageView avatarView = view.findViewById(R.id.stListItem_avatar);



            nameTv.setText(exercise.description);
            idTv.setText(exercise.id);

            avatarView.setImageResource(R.drawable.practice);
            avatarView.setTag(exercise.id);
            if (exercise.image != null){
                Model.instance.getImage(exercise.image, new Model.GetImageListener() {
                    @Override
                    public void onDone(Bitmap imageBitmap) {
                        if (exercise.id.equals(avatarView.getTag()) && imageBitmap != null) {
                            avatarView.setImageBitmap(imageBitmap);
                        }
                    }
                });
            }
            return view;
        }
    }




    class MyAdapterByUser extends BaseAdapter {
        public MyAdapterByUser(){
        }

        @Override
        public int getCount() {
            Log.d("TAG","list size:" + dataModel.getExerciseListByUserMail(UserProfileModel.instance.getCurrentUserMail()).getValue().size());

            return dataModel.getExerciseListByUserMail(UserProfileModel.instance.getCurrentUserMail()).getValue().size();

        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            if (view == null){
                view = LayoutInflater.from(getActivity()).inflate(R.layout.student_list_item,null);

            }

            final Exercise exercise = dataModel.getExerciseListByUserMail(UserProfileModel.instance.getCurrentUserMail()).getValue().get(i);

            TextView nameTv = view.findViewById(R.id.stListItem_name_tv);
            TextView idTv = view.findViewById(R.id.stListItem_id_tv);

            final ImageView avatarView = view.findViewById(R.id.stListItem_avatar);



            nameTv.setText(exercise.description);
            idTv.setText(exercise.id);

            avatarView.setImageResource(R.drawable.practice);
            avatarView.setTag(exercise.id);
            if (exercise.image != null){
                Model.instance.getImage(exercise.image, new Model.GetImageListener() {
                    @Override
                    public void onDone(Bitmap imageBitmap) {
                        if (exercise.id.equals(avatarView.getTag()) && imageBitmap != null) {
                            avatarView.setImageBitmap(imageBitmap);
                        }
                    }
                });
            }
            return view;
        }
    }
}
