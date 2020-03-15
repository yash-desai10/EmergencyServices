package com.example.jigish.emse;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;

import java.util.LinkedHashMap;
import java.util.Map;


public class feedback extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText edt;
    Button btn;
    ProgressDialog p;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("feedback");
    String feedback;

    private OnFragmentInteractionListener mListener;

    public feedback() {
        // Required empty public constructor
    }

    public static feedback newInstance(String param1, String param2) {
        feedback fragment = new feedback();
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
        View v=inflater.inflate(R.layout.fragment_feedback, container, false);
        edt=(EditText)v.findViewById(R.id.feedbacked);

        btn=(Button)v.findViewById(R.id.btn);
                SmileRating smileRating = (SmileRating) v.findViewById(R.id.smile_rating);
        smileRating.setSelectedSmile(BaseRating.GOOD,true);



        smileRating.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(@BaseRating.Smiley int smiley, boolean reselected) {
                // reselected is false when user selects different smiley that previously selected one
                // true when the same smiley is selected.
                // Except if it first time, then the value will be false.
                switch (smiley) {
                    case SmileRating.BAD:
                        Toast.makeText(getActivity(),"BAD",Toast.LENGTH_SHORT).show();
                        break;
                    case SmileRating.GOOD:
                        Toast.makeText(getActivity(),"GOOD",Toast.LENGTH_SHORT).show();

                        //Log.i(TAG, "Good");
                        break;
                    case SmileRating.GREAT:
                        Toast.makeText(getActivity(),"GREAT",Toast.LENGTH_SHORT).show();
                        //Log.i(TAG, "Great");
                        break;
                    case SmileRating.OKAY:
                        Toast.makeText(getActivity(),"OKAY",Toast.LENGTH_SHORT).show();
                        //Log.i(TAG, "Okay");
                        break;
                    case SmileRating.TERRIBLE:
                        Toast.makeText(getActivity(),"TERRIBLE",Toast.LENGTH_SHORT).show();
                        //Log.i(TAG, "Terrible");
                        break;
                }
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {   feedback=edt.getText().toString();
                onSubmit();
            }


        });

        // Inflate the layout for this fragment
        return v;
    }

    private void onSubmit() {
        p=new ProgressDialog(getActivity());
        p.setCancelable(false);
        p.setMessage("please wait");
        p.setMax(100);
        p.show();

        myRef.child(MyApp.myphone).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()==null)
                {
                    Map<String ,String> map= new LinkedHashMap<String, String>();
                    map.put("feedback",feedback);


//this method is for storeing the data
                    myRef.child("feedback").setValue(map).addOnCompleteListener( new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            p.dismiss();
                            Toast.makeText(getActivity(), "Thank you for your feedback !!!", Toast.LENGTH_SHORT).show();
//                            Intent i=new Intent(getActivity(),Main2Activity.class);
//                            startActivity(i);
//                           insert();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
