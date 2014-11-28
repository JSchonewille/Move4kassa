package com.example.jeff.move4kassa;


import android.app.Activity;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link personInfo.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link personInfo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class personInfo extends Fragment {
    private static final String ARG_PARAM1 = "name";
    private static final String ARG_PARAM2 = "lastname";
    private static final String ARG_PARAM3 = "email";
    private static final String ARG_PARAM4 = "likes";
    private static final String ARG_PARAM5 = "imgpath";


    //private static final String ARG_PARAM4 = "param2";
    // private static final String ARG_PARAM5 = "param2";


    private ArrayList<String> likes;
    private String name;
    private String lastname;
    private String email;
    private String imgpath;
    static personInfo fragment;

    private OnFragmentInteractionListener mListener;


    public static personInfo newInstance(String name, String lastname, String email, ArrayList<String> list, String path) {
        fragment = new personInfo();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, name);
        args.putString(ARG_PARAM2, lastname);
        args.putString(ARG_PARAM3, email);
        args.putStringArrayList(ARG_PARAM4, list);
        args.putString(ARG_PARAM5, path);
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
            likes = getArguments().getStringArrayList(ARG_PARAM4);
            imgpath = getArguments().getString(ARG_PARAM5);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myInflatedView = inflater.inflate(R.layout.fragment_person_info, container, false);
        ImageView v_pic = (ImageView) myInflatedView.findViewById(R.id.imageView);
        TextView t_name = (TextView) myInflatedView.findViewById(R.id.t_Firstname);
        TextView t_lastname = (TextView) myInflatedView.findViewById(R.id.t_Lastname);
        TextView t_email = (TextView) myInflatedView.findViewById(R.id.t_Email);
        ListView l_likes = (ListView) myInflatedView.findViewById(R.id.l_likes);
        Button b_close = (Button) myInflatedView.findViewById(R.id.button);

        File f = new File(imgpath);
        Bitmap b;
        try {
            b = BitmapFactory.decodeStream(new FileInputStream(f));
            b = Bitmap.createScaledBitmap(b, 600, 800, true);
            v_pic.setImageBitmap(b);

        } catch (FileNotFoundException e) {
            b = BitmapFactory.decodeResource(getActivity().getResources(),
                    R.drawable.nopic);
            b = Bitmap.createScaledBitmap(b, 600, 800, true);
            v_pic.setImageBitmap(b);
        }

        b_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentTransaction t = getActivity().getSupportFragmentManager().beginTransaction();
                t.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                t.remove(fragment);
                t.commit();
            }
        });


        t_name.setText(name);
        t_lastname.setText(lastname);
        t_email.setText(email);
        l_likes.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, likes));
        v_pic.setScaleType(ImageView.ScaleType.FIT_XY);
        return myInflatedView;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction();
        }
    }


    public Onclose mCallBack;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallBack = (Onclose) activity;
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    @Override
    public void onDetach() {
        mCallBack.onClose();
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction();

    }

    public interface Onclose {
        public void onClose();
    }

}
