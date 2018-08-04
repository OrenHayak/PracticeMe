package com.example.eliavmenachi.myapplication;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eliavmenachi.myapplication.Model.Exercise;
import com.example.eliavmenachi.myapplication.Model.Model;

public class showDetailsFragments extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public Exercise chosen;

    TextView tvName;
    TextView tvDesc;
    ImageView imgImage;

    public showDetailsFragments() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            studentId = getArguments().getString(ARG_PARAM1);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exercise_details_no_edit, container, false);
        tvName = view.findViewById(R.id.tvNoEditName);
        tvDesc = view.findViewById(R.id.tvNoEditDesc);
        imgImage = view.findViewById(R.id.imgNoEditImg);

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
