package com.mobileapp.clusteringexample;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.mobileapp.clusteringexample.databinding.ActivityMapsBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private List<MarkerOptions> listMarkers = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void setUpClusterManager(GoogleMap googleMap){
        ClusterManager<User> clusterManager = new ClusterManager<>(this, googleMap);
        clusterManager.setRenderer(new MarkerClusterRenderer(this, googleMap, clusterManager));
        googleMap.setOnCameraIdleListener(clusterManager);
        List<User> items = getItems();
        clusterManager.addItems(items);  // 4
        clusterManager.cluster();//5
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(listMarkers.get(0).getPosition(), 13.0f));// 6
    }

    private JSONArray readAssets(){
        try{
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getAssets().open("places.txt")));
            String line = "";
            StringBuilder stringBuilder = new StringBuilder("");
            while ((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line);
            }
            return new JSONArray(stringBuilder.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        return new JSONArray();
    }

    private List<User> getItems()
    {
        List<User>users=new ArrayList<>();
        try{
            JSONArray jsonArray=readAssets();
            for (int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                double lat = jsonObject.getDouble("lat");
                double lng = jsonObject.getDouble("lng");
                String name = jsonObject.getString("name");
                LatLng latLng = new LatLng(lat, lng);
                User user=new User(name,new LatLng(lat,lng));
                MarkerOptions markerOptions = new MarkerOptions().position(latLng).title(name);
                listMarkers.add(markerOptions);
                users.add(user);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return users;
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
        setUpClusterManager(mMap);
    }
}