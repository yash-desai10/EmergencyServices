package com.example.jigish.emse;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link personal2.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link personal2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class personal2 extends Fragment {

   static TextView smob,sadd,sstreet,scity,scountry,family_no;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public personal2() {
        // Required empty public constructor
    }


    public static personal2 newInstance(String param1, String param2) {
        personal2 fragment = new personal2();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_personal2, container, false);
        smob=(TextView)v.findViewById(R.id.SMobile);
        sadd=(TextView)v.findViewById(R.id.SAddressline1);
        sstreet=(TextView)v.findViewById(R.id.Sstreet);
        scity=(TextView)v.findViewById(R.id.Scity);
        scountry=(TextView)v.findViewById(R.id.Scountry);
        family_no=(TextView)v.findViewById(R.id.family_no1);

        smob.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0){
                    if(!phoneValidator(s)){
                        smob.setError("Please Enter Valid number!");
                    }
                }
            }
        });

        family_no.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0){
                    if(!pValidator(s)){
                        family_no.setError("Please Enter Valid Family member number!");
                    }
                }
            }
        });



        return v;
    }

    private boolean phoneValidator(CharSequence phone){
        if(phone.length() < 10 )
            return false;
        else
            return true;
    }

    private boolean pValidator(CharSequence phone){
        if(phone.length() < 10 )
            return false;
        else
            return true;
    }




    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
