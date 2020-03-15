package com.example.jigish.emse;

import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link home.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class  home extends Fragment implements GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks, LocationListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    Button Hospital, fire, police;
    private static final int PERMISSION_SEND_SMS = 123;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;

    private String mParam1;
    private String mParam2;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    static String mobil_no;
    String smob;
    protected GoogleApiClient googleApiClient;
    protected Location lastlocation;
    private OnFragmentInteractionListener mListener;
    LocationRequest mLocationRequest;
    double latitude, longitude ;
    String phoneNo;
    String message;



    public home() {
        // Required empty public constructor
    }

    public static home newInstance(String param1, String param2) {
        home fragment = new home();
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
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        final Button hospital = (Button) v.findViewById(R.id.hospital1);
        Button fire = (Button) v.findViewById(R.id.fire2);
        Button police = (Button) v.findViewById(R.id.police3);
//          final String family_no=personal2.family_no.getText().toString();
        // mobil_no = personal2.smob.getText().toString();
        hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getActivity(),family_no,Toast.LENGTH_LONG).show();
                //Time
                long date = System.currentTimeMillis();

                SimpleDateFormat sdf = new SimpleDateFormat(" MM/dd/yyyy ");
                String dateString = sdf.format(date);
                SimpleDateFormat stf = new SimpleDateFormat(" hh:mm:a ");
                String timeString = stf.format(date);

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Hospital");
//                getCompleteAddressString(lastlocation.getLatitude(),lastlocation.getLongitude());
                double latitud=lastlocation.getLatitude();
                double longitud=lastlocation.getLongitude();
                String Eaddress=getCompleteAddressString(lastlocation.getLatitude(),lastlocation.getLongitude());
                Map<String, String> map = new LinkedHashMap<String, String>();
                map.put("Latitude", String.valueOf(latitud));
                map.put("Longitude", String.valueOf(longitud));
                map.put("Eaddress",Eaddress);
                map.put("Time",timeString);
                map.put("Date",dateString);
                myRef.child(MyApp.myphone).setValue(map);
//                Toast.makeText(getActivity(), "request submit hospital"+latitude+","+longitude, Toast.LENGTH_SHORT).show();
//                sendSMS("9601722640", "EMERGEMCY SMS FROM EMSE (Hospital),"+getCompleteAddressString(lastlocation.getLatitude(),lastlocation.getLongitude())+"Latitude"+lastlocation.getLatitude()+"Longitude"+lastlocation.getLongitude());
                sendSMS(MyApp.family_no, "EMERGEMCY SMS FROM EMSE (hospital),\n"+
                       "http://www.google.com/maps/place/"+lastlocation.getLatitude()+","+lastlocation.getLongitude());

            }
        });
        fire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Time
                long date = System.currentTimeMillis();

                SimpleDateFormat sdf = new SimpleDateFormat(" MM/dd/yyyy ");
                String dateString = sdf.format(date);
                SimpleDateFormat stf = new SimpleDateFormat(" hh:mm:a ");
                String timeString = stf.format(date);

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Fire");
                Map<String,String> map= new LinkedHashMap<String, String>();
                map.put("Latitude",String.valueOf(lastlocation.getLatitude()));
                map.put("Longitude",String.valueOf(lastlocation.getLongitude()));
                map.put("Time",timeString);
                map.put("Date",dateString);
                map.put("Eaddress",String.valueOf(getCompleteAddressString(lastlocation.getLatitude(),lastlocation.getLongitude())));
                myRef.child(MyApp.myphone).setValue(map);
                //getCompleteAddressString(lastlocation.getLatitude(),lastlocation.getLongitude());
//                sendsms();
                Toast.makeText(getActivity(), "request submit fire "+lastlocation, Toast.LENGTH_SHORT).show();
                sendSMS(MyApp.family_no, "EMERGEMCY SMS FROM EMSE (Fire),\n"+
                        "http://www.google.com/maps/place/"+lastlocation.getLatitude()+","+lastlocation.getLongitude());
//                +getCompleteAddressString(lastlocation.getLatitude(),lastlocation.getLongitude())+
            }
        });
        police.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Time
                long date = System.currentTimeMillis();

                SimpleDateFormat sdf = new SimpleDateFormat(" MM/dd/yyyy ");
                String dateString = sdf.format(date);
                SimpleDateFormat stf = new SimpleDateFormat(" hh:mm:a ");
                String timeString = stf.format(date);

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Police");
                //getCompleteAddressString(lastlocation.getLatitude(),lastlocation.getLongitude());
                Map<String,String>map=new LinkedHashMap<String, String>();
                map.put("Latitude",String.valueOf(lastlocation.getLatitude()));
                map.put("Longitude",String.valueOf(lastlocation.getLongitude()));
                map.put("Time",timeString);
                map.put("Date",dateString);
                map.put("Eaddress",String.valueOf(getCompleteAddressString(lastlocation.getLatitude(),lastlocation.getLongitude())));
                myRef.child(MyApp.myphone).setValue(map);
                Toast.makeText(getActivity(), "request submit police", Toast.LENGTH_SHORT).show();
                sendSMS(MyApp.family_no, "EMERGEMCY SMS FROM EMSE (Police),"+getCompleteAddressString(lastlocation.getLatitude(),lastlocation.getLongitude())+
                        "http://www.google.com/maps/place/"+lastlocation.getLatitude()+","+lastlocation.getLongitude());
            }
        });

//        it's function loaction
        buildGoogleApiClient();
        // Inflate the layout for this fragment
        return v;
    }

    private void sendSMS(String s, String s1) {
//        int permissionCheck= ContextCompat.checkSelfPermission(home.this,Manifest.permission.SEND_SMS)


                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(s, null, s1, null, null);
        Toast.makeText(getActivity(),"Request Send to family member "+s,Toast.LENGTH_LONG).show();

    }


    private synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }


    //    question
    @Override
    public void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
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
    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
//                Toast.makeText(getActivity(),"My Current loction address" + strReturnedAddress.toString()+":"+lastlocation.getLongitude()+","+lastlocation.getLatitude(),Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity(), "No Address returned!",Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
//            Toast.makeText(getActivity(),"Canont get Address!"+e,Toast.LENGTH_LONG).show();
            Toast.makeText(getActivity(),"Canont get Address!",Toast.LENGTH_LONG).show();
        }
        return strAdd;
    }



    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000);
                LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, mLocationRequest, this);
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        lastlocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if(lastlocation!=null)
//            getCompleteAddressString(lastlocation.getLatitude(),lastlocation.getLongitude());
            {
            }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(getActivity(), "Connection suspended",Toast.LENGTH_SHORT).show();
        googleApiClient.connect();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//        Toast.makeText(getActivity(), "Connection failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode(),Toast.LENGTH_SHORT).show();
        Toast.makeText(getActivity(), "Connection failed",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onLocationChanged(Location location) {
        //Toast.makeText(getActivity(),"onchaged "+ location.toString(),Toast.LENGTH_LONG).show();

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
