package com.example.jigish.emse;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.jigish.emse.Fire.TAG;



public class police extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String REQUESTTAG = "string request first";
    private RequestQueue mRequestqeue;
    private StringRequest request;
    static String longi, lati;
    GoogleApiClient googleApiClient;
//    String url3 ="https://maps.googleapis.com/maps/api/place/search/json?location=22.3221027,73.1730862&radius=5000&type=police&key=AIzaSyCvvmu5OxfIJIh9vHBK4dGaDxJLR4k-tkA";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button report, psearch,p_mcf;
    private OnFragmentInteractionListener mListener;

    public police() {
        // Required empty public constructor
    }

    public static police newInstance(String param1, String param2) {
        police fragment = new police();
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
        View v = inflater.inflate(R.layout.fragment_police, container, false);
//        report=(Button)v.findViewById(R.id.report);
//        report.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
////                Toast.makeText(getActivity(),"search police",Toast.LENGTH_SHORT).show();
//            }
//        });
        psearch = (Button) v.findViewById(R.id.psearch);
        psearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url3 = "https://maps.googleapis.com/maps/api/place/search/json?location="+MyApp.lati +","+MyApp.longi+"&radius=5000&type=police&key=AIzaSyCvvmu5OxfIJIh9vHBK4dGaDxJLR4k-tkA";
                sendrequestpolice(url3);
//                Toast.makeText(getActivity(),"report",Toast.LENGTH_SHORT).show();
            }
        });
        if (googleApiClient ==null){
            googleApiClient=new GoogleApiClient.Builder(getActivity())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        if (googleApiClient !=null)
        {
            googleApiClient.connect();
        }

        p_mcf=(Button)v.findViewById(R.id.p_mcf);
       p_mcf.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i=new Intent(getActivity(),MissingComplaint.class);
               startActivity(i);
           }
       });
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    private void sendrequestpolice(String url3) {
//        double lait=lastlocation.getLatitude();
//        double longi=lastlocation.getLongitude();
//        String u="https://maps.googleapis.com/maps/api/place/search/json?location="+lastlocation.getLatitude()+","+lastlocation.getLongitude()+"&radius=5000&type=fire&key=AIzaSyCvvmu5OxfIJIh9vHBK4dGaDxJLR4k-tkA";


        mRequestqeue = Volley.newRequestQueue(getActivity());
        request = new StringRequest(Request.Method.GET, url3, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(TAG, "Response =" + response.toString());
                try {
                    if (MyApp.allloc.size() > 0) {
                        MyApp.allloc.clear();
                        MyApp.allres.clear();
                    }

                    JSONObject r = new JSONObject(response);
                    JSONArray results = r.getJSONArray("results");

                    ArrayList<Result> resultsList = new ArrayList<Result>();


                    for (int i = 0; i < results.length(); i++) {
                        JSONObject thisResult = results.getJSONObject(i);
                        String name = thisResult.getString("name");
                        String vicinity = thisResult.getString("vicinity");
                        JSONObject geometry = thisResult.getJSONObject("geometry");
                        JSONObject location = geometry.getJSONObject("location");
                        double latitude = location.getDouble("lat");
                        double longitude = location.getDouble("lng");
                        Result result = new Result(name, latitude, longitude, vicinity);
                        MyApp.allloc.add(name);
                        MyApp.allres.put(name, result);
                        resultsList.add(result);

                    }

//                    Intent ii= new Intent(getActivity(),Main2Activity.class);
//                    startActivity(ii);
                    Fragment frag;
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.main_content, new searchpolice()).addToBackStack(null).commit();
                    for (int i = 0; i < resultsList.size(); i++) {
                        Log.v(TAG, "Result #" + (i + 1) + ": " + resultsList.get(i).toString());
                    }

                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Something went wrong while parsing the JSON response." + e, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "Error =" + error.toString());
            }
        });
        request.setTag(REQUESTTAG);
        mRequestqeue.add(request);

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

    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if(mLastLocation!=null)
        {
            MyApp.lati=String.valueOf(mLastLocation.getLatitude());
            MyApp.longi=String.valueOf(mLastLocation.getLongitude());
            Toast.makeText(getActivity(),"find:"+String.valueOf(mLastLocation.getLatitude())+"-"+String.valueOf(mLastLocation.getLongitude()),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

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
