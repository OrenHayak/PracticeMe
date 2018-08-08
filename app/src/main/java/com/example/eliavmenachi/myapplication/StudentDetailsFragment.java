package com.example.eliavmenachi.myapplication;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



public class StudentDetailsFragment extends Fragment {
    private static final String ARG_PARAM1 = "studentId";
    String studentId;

    //private OnFragmentInteractionListener mListener;

    public StudentDetailsFragment() {
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student_details, container, false);
        TextView title = view.findViewById(R.id.st_details_title);
        title.setText(studentId);

        return view;
    }

}
