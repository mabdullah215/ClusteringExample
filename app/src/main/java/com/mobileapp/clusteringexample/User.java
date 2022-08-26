package com.mobileapp.clusteringexample;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class User implements ClusterItem {

    private final String username;
    private final LatLng latLng;

    public User(String username, LatLng latLng) {
        this.username = username;
        this.latLng = latLng;
    }

    @Override
    public LatLng getPosition() {  // 1
        return latLng;
    }

    @Override
    public String getTitle() {  // 2
        return username;
    }

    @Override
    public String getSnippet() {
        return "";
    }
}