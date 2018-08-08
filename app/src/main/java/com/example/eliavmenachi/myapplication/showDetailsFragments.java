package com.example.eliavmenachi.myapplication;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eliavmenachi.myapplication.Model.Exercise;
import com.example.eliavmenachi.myapplication.Model.Model;
import com.example.eliavmenachi.myapplication.Model.UserProfileModel;

public class showDetailsFragments extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public Exercise chosen;

    TextView tvName;
    TextView tvDesc;
    ImageView imgImage;
    Button btnEdit;
    Button btnDelete;

    public showDetailsFragments() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exercise_details_no_edit, container, false);
        tvName = view.findViewById(R.id.tvNoEditName);
        tvDesc = view.findViewById(R.id.tvNoEditDesc);
        imgImage = view.findViewById(R.id.imgNoEditImg);
        btnEdit = view.findViewById(R.id.btnEditExercise);
        btnDelete = view.findViewById(R.id.btnDeleteExercise);

        // Check authorizations - If user is not the creator of exercise, do not permit delete and edit
        String strLoginUserMail = UserProfileModel.instance.getCurrentUserMail();
        Log.d("TAG","curr user:" + strLoginUserMail);
        Log.d("TAG","chosen:" + chosen.ownermail);
        if (!strLoginUserMail.toString().trim().equalsIgnoreCase(chosen.ownermail.toString().trim()))
        {
            btnDelete.setVisibility(View.GONE);
            btnEdit.setVisibility(View.GONE);
        }

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NewExerciseFragment fragmentEdit = new NewExerciseFragment();
                fragmentEdit.exEdited = chosen;
                FragmentTransaction tranEdit = getActivity().getSupportFragmentManager().beginTransaction();
                tranEdit.replace(R.id.main_container, fragmentEdit);
                tranEdit.addToBackStack("tag");
                tranEdit.commit();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chosen.active = false;
                Model.instance.addExercise(chosen);
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        tvDesc.setText(chosen.description);
        tvName.setText(chosen.id);

        if ((chosen.image != null) && (chosen.image != "")) {
            Model.instance.getImage(chosen.image, new Model.GetImageListener() {
                @Override
                public void onDone(Bitmap imageBitmap) {
                    imgImage.setImageBitmap(imageBitmap);
                }
            });
        }

        return view;
    }


}
