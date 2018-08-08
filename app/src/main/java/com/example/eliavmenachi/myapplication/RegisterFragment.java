package com.example.eliavmenachi.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.eliavmenachi.myapplication.Model.UserProfile;
import com.example.eliavmenachi.myapplication.Model.UserProfileModel;


public class RegisterFragment extends Fragment {
    private static final String ARG_EMAIL = "ARG_EMAIL";
    private static final String ARG_PASSWORD = "ARG_PASSWORD";
    private static final String ARG_USER_NAME = "ARG_USER_NAME";
    private static final String ARG_NAME = "ARG_NAME";
    private static final String ARG_GOALS = "ARG_GOALS";

    //private OnFragmentInteractionListener mListener;

    public RegisterFragment() {
        // Required empty public constructor
    }

    EditText etEmail;
    EditText etPassword;
    EditText etUsername;
    EditText etName;
    EditText etGoals;
    Button btnSave;
    ProgressBar pbRegister;

    /*public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }*/

    /*@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        etEmail = view.findViewById(R.id.etEmailLog);
        etGoals = view.findViewById(R.id.etGoals);
        etPassword = view.findViewById(R.id.etPassword);
        etUsername = view.findViewById(R.id.etUsername);
        etName = view.findViewById(R.id.etName);
        pbRegister = view.findViewById(R.id.pbRegister);

        pbRegister.setVisibility(View.GONE);

        btnSave = view.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isInputValid())
                {

                    pbRegister.setVisibility(View.VISIBLE);

                final UserProfile userProfile = new UserProfile();
                userProfile.password = etPassword.getText().toString();
                userProfile.username = etUsername.getText().toString();
                userProfile.email = etEmail.getText().toString();
                userProfile.goals = etGoals.getText().toString();
                userProfile.name = etName.getText().toString();


                UserProfileModel.instance.saveUserProfile(userProfile, new UserProfileModel.SaveUserProfileListener() {
                    @Override
                    public void onDone() {

                        UserProfileModel.instance.addUserProfile(userProfile);
                        getActivity().getSupportFragmentManager().popBackStack();
                    }
                });

            }
            }
        });


        if (savedInstanceState != null) {
            String email = savedInstanceState.getString(ARG_EMAIL);
            if (email != null) {
                etEmail.setText(email);
            }
            String password = savedInstanceState.getString(ARG_PASSWORD);
            if (password != null) {
                etPassword.setText(password);
            }

            String userName = savedInstanceState.getString(ARG_USER_NAME);
            if (userName != null) {
                etUsername.setText(userName);
            }
            String name = savedInstanceState.getString(ARG_NAME);
            if (name != null) {
                etName.setText(name);
            }
            String goals = savedInstanceState.getString(ARG_GOALS);
            if (goals != null) {
                etGoals.setText(goals);
            }
        }

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
    }
    @Override
    public void  onSaveInstanceState(Bundle bundle){
        super.onSaveInstanceState(bundle);
        bundle.putString(ARG_EMAIL, etEmail.getText().toString());
        bundle.putString(ARG_PASSWORD, etPassword.getText().toString());
        bundle.putString(ARG_USER_NAME, etUsername.getText().toString());
        bundle.putString(ARG_NAME, etName.getText().toString());
        bundle.putString(ARG_GOALS, etGoals.getText().toString());
    }

    private boolean isInputValid(){
        boolean isValid = true;


        if (etUsername.getText().toString().isEmpty()) {
            isValid = false;
            etUsername.requestFocus();
            etUsername.setError("Please insert username");
        }
        else if (etPassword.getText().toString().isEmpty()) {
            isValid = false;
            etPassword.requestFocus();
            etPassword.setError("Please insert password");
        }
        else if (etPassword.getText().toString().length() < 6) {
            isValid = false;
            etPassword.requestFocus();
            etPassword.setError("Please insert password with at least 6 characters");
        }
        else if (etEmail.getText().toString().isEmpty()){
            isValid = false;
            etEmail.requestFocus();
            etEmail.setError("Please insert E-mail address");
        }
        else if (!(etEmail.getText().toString().contains("@") &&  etEmail.getText().toString().contains(".com"))){
            isValid = false;
            etEmail.requestFocus();
            etEmail.setError("Please insert correct E-mail address");
        }
        else if (etName.getText().toString().isEmpty()) {
            isValid = false;
            etName.requestFocus();
            etName.setError("Please insert your name");
        }

        return isValid;
    }
}
