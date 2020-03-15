package com.example.jigish.emse;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link firefrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link firefrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class firefrag extends Fragment{
    //googel map object
//    GoogleMap m_map;
//    boolean mapReady=false;
//    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


//    //set marker fire station
//    MarkerOptions Firestation;
//    static final CameraPosition Fire=CameraPosition.builder()
//            .target(new LatLng(22.2945063,73.2276146))
//            .zoom(17)
//            .bearing(0)
//            .tilt(45)
//            .build();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
  //  MapView mview=null;
   // private GoogleMap mMap=null;
    private OnFragmentInteractionListener mListener;
//    genrete the result
    ListView lv;

    public firefrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment firefrag.
     */
    // TODO: Rename and change types and number of parameters
    public static firefrag newInstance(String param1, String param2) {
        firefrag fragment = new firefrag();
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
       // inflater.inflate(R.layout.fragment_firefrag, container, false);
        // Gets the MapView from the XML layout and creates it
      //  mapview = (MapView) v.findViewById(R.id.mapview);
    //    mapview.onCreate(savedInstanceState);
      //  FragmentManager fragmentManager = getActivity().getFragmentManager();
       // MapFragment supportMapFragment = (MapFragment) fragmentManager
         //       .findFragmentById(R.id.map);

       // supportMapFragment.getMapAsync(this);
        //mview= (MapView) v.findViewById(R.id.fmap);
        View view=inflater.inflate(R.layout.fragment_firefrag, container, false);
        lv=(ListView)view.findViewById(R.id.mobile_list);

        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,MyApp.allloc);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
               Toast.makeText(getActivity(),"Item : "+i+ "- "+MyApp.allres.get(MyApp.allloc.get(i)), Toast.LENGTH_SHORT).show();
                Intent il=new Intent(getActivity(),MapsActivity.class);
                il.putExtra("loc",""+i);
                startActivity(il);
            }
        });


//        Firestation =new MarkerOptions()
//                .position(new LatLng(22.2945063,73.2276146))
//                .title("Fire Headquarter");
//        MapFragment mapFragment=(MapFragment) this.getChildFragmentManager().findFragmentById(R.id.map2);
//        mapFragment.getMapAsync(this);
//


        // Inflate the layout for this fragment
        return view;
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

//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mapReady=true;
//        m_map=googleMap;
//        m_map.addMarker(Firestation);
//        flyto(Fire);
//
//    }
//    void flyto(CameraPosition target)
//    {
//        m_map.moveCamera(CameraUpdateFactory.newCameraPosition(target));
//    }

   /* @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
