package com.example.eliavmenachi.myapplication;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class loginFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    UserProfileListViewModel userProfileListViewModel;


  //  private OnFragmentInteractionListener mListener;

    public loginFragment() {
        // Required empty public constructor
    }

    /*// TODO: Rename and change types and number of parameters
    public static loginFragment newInstance(String param1, String param2) {
        loginFragment fragment = new loginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }*/

    EditText etUsername;
    EditText etPassword;
    EditText etEmail;
    Button btnSave;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        userProfileListViewModel = ViewModelProviders.of(this).get(UserProfileListViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        etUsername = view.findViewById(R.id.etUserNameLog);
        etPassword = view.findViewById(R.id.etPasswordLog);
        etEmail = view.findViewById(R.id.etEmailLog);

        btnSave = view.findViewById(R.id.btnSubmitLog);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strEmail = etEmail.getText().toString();
                String strPassword = etPassword.getText().toString();

                    try {
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        //imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);

                    } catch (Exception e) {

                    }

                userProfileListViewModel.signIn(strEmail, strPassword, new UserProfileListViewModel.SignInListener() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(getActivity(), "Signed in successfully!", Toast.LENGTH_LONG).show();

//                            Intent intent = new Intent();
//                            intent.putExtra("SignIn", true);
//                            getActivity().setResult(1);
                            //getActivity().finish();
//                            Intent intent = new Intent(LoginFragment.this.getContext(), SalesActivity.class);
//                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(String exceptionMessage) {
                            Toast.makeText(getActivity(), "One of the fields above isn't valid.", Toast.LENGTH_LONG).show();

                        }
                    });
            }
        });

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
