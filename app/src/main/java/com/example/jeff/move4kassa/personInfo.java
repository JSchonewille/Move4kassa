package com.example.jeff.move4kassa;


import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link personInfo.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link personInfo#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class personInfo extends Fragment {
    private static final String ARG_PARAM1 = "name";
    private static final String ARG_PARAM2 = "lastname";
    private static final String ARG_PARAM3 = "email";
   //private static final String ARG_PARAM4 = "param2";
   // private static final String ARG_PARAM5 = "param2";



    private String name;
    private String lastname;
    private String email;

    private OnFragmentInteractionListener mListener;


    public static personInfo newInstance(String name,String lastname , String email) {
        personInfo fragment = new personInfo();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, name);
        args.putString(ARG_PARAM2, lastname);
        args.putString(ARG_PARAM3, email);


        fragment.setArguments(args);
        return fragment;
    }
    public personInfo() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString(ARG_PARAM1);
            lastname = getArguments().getString(ARG_PARAM2);
            email = getArguments().getString(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myInflatedView = inflater.inflate(R.layout.fragment_person_info, container, false);
        TextView t_name = (TextView) myInflatedView.findViewById(R.id.t_Firstname);
        TextView t_lastname  = (TextView) myInflatedView.findViewById(R.id.t_Lastname);
        TextView t_email = (TextView) myInflatedView.findViewById(R.id.t_Email);

        t_name.setText(name);
        t_lastname.setText(lastname);
        t_email.setText(email);


        return myInflatedView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }



}
