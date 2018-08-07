package com.example.eliavmenachi.myapplication;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class loginFragment extends Fragment {
    private static final String ARG_EMAILL = "ARG_EMAILL";
    private static final String ARG_PASSWORDL = "ARG_PASSWORDL";
    UserProfileListViewModel userProfileListViewModel;

    public loginFragment() {

    }

    EditText etPassword;
    EditText etEmail;
    Button btnSave;
    Button btnRegister;
    ProgressBar pbLogin;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        userProfileListViewModel = ViewModelProviders.of(this).get(UserProfileListViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        etPassword = view.findViewById(R.id.etPasswordLog);
        etEmail = view.findViewById(R.id.etEmailLog);
        pbLogin = view.findViewById(R.id.pbLogin);

        pbLogin.setVisibility(View.GONE);

        btnSave = view.findViewById(R.id.btnSubmitLog);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pbLogin.setVisibility(View.VISIBLE);

                String strEmail = etEmail.getText().toString();
                String strPassword = etPassword.getText().toString();



                userProfileListViewModel.signIn(strEmail, strPassword, new UserProfileListViewModel.SignInListener() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(getActivity(), "Signed in successfully!", Toast.LENGTH_LONG).show();



                            ExercisesListFragment allPostsFragments = new ExercisesListFragment();
                            FragmentTransaction tranAll = getActivity().getSupportFragmentManager().beginTransaction();
                            tranAll.replace(R.id.main_container, allPostsFragments);
                            tranAll.addToBackStack("tag");
                            tranAll.commit();
                        }

                        @Override
                        public void onFailure(String exceptionMessage) {
                            pbLogin.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "One of the fields above isn't valid.", Toast.LENGTH_LONG).show();

                        }
                    });
            }
        });


        btnRegister = view.findViewById(R.id.btnregister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RegisterFragment fragmentRegister = new RegisterFragment();
                FragmentTransaction tranRegister = getActivity().getSupportFragmentManager().beginTransaction();
                tranRegister.replace(R.id.main_container, fragmentRegister);
                tranRegister.addToBackStack("tag");
                tranRegister.commit();
            }
        });


        if (savedInstanceState != null) {
            String email = savedInstanceState.getString(ARG_EMAILL);
            if (email != null) {
                etEmail.setText(email);
            }
            String password = savedInstanceState.getString(ARG_PASSWORDL);
            if (password != null) {
                etPassword.setText(password);
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
        bundle.putString(ARG_EMAILL, etEmail.getText().toString());
        bundle.putString(ARG_PASSWORDL, etPassword.getText().toString());
    }

    // TODO: Rename method, update argument and hook method into UI event
    //public void onButtonPressed(Uri uri) {
      //  if (mListener != null) {
        //    mListener.onFragmentInteraction(uri);
       // }
  //  }

   // @Override
   // public void onAttach(Context context) {
    //    super.onAttach(context);
      //  if (context instanceof OnFragmentInteractionListener) {
      //      mListener = (OnFragmentInteractionListener) context;
     //   } else {
       //     throw new RuntimeException(context.toString()
           //         + " must implement OnFragmentInteractionListener");
      //  }
    //}

   // @Override
   // public void onDetach() {
      // super.onDetach();
  //      mListener = null;
   // }

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
   // public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
       // void onFragmentInteraction(Uri uri);
   // }
}
