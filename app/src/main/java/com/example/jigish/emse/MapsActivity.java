package com.example.jigish.emse;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    GoogleApiClient googleApiClient;
    Location lastlocation;
    private GoogleMap mMap;
    static  double lati ,longi;

//    static  final CameraPosition Vadodara=CameraPosition.builder()
//            .target(new LatLng(Double.parseDouble(Fire.lati),Double.parseDouble(Fire.longi)))
//            .zoom(16)
//            .bearing(0)
//            .tilt(45)
//            .build();
//    static  final CameraPosition PVadodara=CameraPosition.builder()
//            .target(new LatLng(Double.parseDouble(police.lati), Double.parseDouble(police.longi)))
//            .zoom(16)
//            .bearing(0)
//            .tilt(45)
//            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
//        Toast.makeText(getApplicationContext(), "get data " + Double.parseDouble(Fire.lati) + "," + Double.parseDouble(Fire.longi), Toast.LENGTH_LONG).show();
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//        LatLng baroda=new LatLng(Double.parseDouble(hospital.lati),Double.parseDouble(hospital.longi));
//        mMap.addMarker(new MarkerOptions().position(baroda).title("Vadodara"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(baroda));

//        flyto(baroda);
//        flyto(Vadodara);
//        flyto(PVadodara);

        String locs = getIntent().getStringExtra("loc");
        if (locs.equals("all")) {
            for (int i = 0; i < MyApp.allres.size(); i++) {
                double lat = MyApp.allres.get(MyApp.allloc.get(i)).latitude;
                double longi = MyApp.allres.get(MyApp.allloc.get(i)).longitude;

                LatLng sydney = new LatLng(lat, longi);
                Toast.makeText(getApplication(), "lat =" + lat + "long =" + longi, Toast.LENGTH_LONG).show();
                mMap.addMarker(new MarkerOptions().position(sydney).title(MyApp.allloc.get(i)));
            }
        } else {

            int i = Integer.parseInt(locs);
            //for(int i=0;i<MyApp.allres.size();i++)
            //{
            double lat = MyApp.allres.get(MyApp.allloc.get(i)).latitude;
            double longi = MyApp.allres.get(MyApp.allloc.get(i)).longitude;

            LatLng sydney = new LatLng(lat, longi);
            Toast.makeText(getApplication(), "lat =" + lat + "long =" + longi, Toast.LENGTH_LONG).show();
            mMap.addMarker(new MarkerOptions().position(sydney).title(MyApp.allloc.get(i)));
            //}
        }
        LatLng ll = new LatLng(Double.parseDouble(MyApp.lati), Double.parseDouble(MyApp.longi));
        mMap.addMarker(new MarkerOptions().position(ll)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//        LatLng li=new LatLng(Double.parseDouble(Fire.lati), Double.parseDouble(Fire.longi));
//        mMap.addMarker(new MarkerOptions().position(li)
//               .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//        LatLng lp=new LatLng(Double.parseDouble(police.lati), Double.parseDouble(police.longi));
//        mMap.addMarker(new MarkerOptions().position(lp)
//                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
////        LatLng ll=new LatLng(Double.parseDouble(hospital.lati), Double.parseDouble(hospital.longi));
//        mMap.addMarker(new MarkerOptions().position(ll)
//                .icon(BitmapDescriptorFactory
//                        .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ll, 15));


    }

    public void flyto(CameraPosition target) {
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(target), 10000, null);
    }



    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LocationRequest mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, mLocationRequest, this);
        lastlocation =LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if(lastlocation!=null)
        {
             lati=lastlocation.getLatitude();
            longi=lastlocation.getLongitude();

        }


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }
}
