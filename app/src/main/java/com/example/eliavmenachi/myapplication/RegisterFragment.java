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

    // TODO: Rename method, update argument and hook method into UI event
    /*public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    /*@Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }*/

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    /*public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }*/


    // TODO - look what this method does.
        /*@Override
        public void  onSaveInstanceState(Bundle bundle){
            super.onSaveInstanceState(bundle);
            bundle.putString(ARG_NAME, nameEt.getText().toString());
            bundle.putString(ARG_ID, idEt.getText().toString());
        }*/
}
