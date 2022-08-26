package com.mobileapp.clusteringexample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;

public class MarkerClusterRenderer extends DefaultClusterRenderer<User> {   // 1

    private static final int MARKER_DIMENSION = 48;  // 2

    private final IconGenerator iconGenerator;
    private final ImageView markerImageView;
    private Context mContext;
    public MarkerClusterRenderer(Context context, GoogleMap map, ClusterManager<User> clusterManager) {
        super(context, map, clusterManager);
        iconGenerator = new IconGenerator(context);  // 3
        markerImageView = new ImageView(context);
        mContext=context;
        markerImageView.setLayoutParams(new ViewGroup.LayoutParams(MARKER_DIMENSION, MARKER_DIMENSION));
        iconGenerator.setContentView(markerImageView);  // 4
    }
    @Override
    protected void onBeforeClusterRendered(Cluster<User> cluster, MarkerOptions markerOptions)
    {
        final Drawable clusterIcon = mContext.getResources().getDrawable(R.drawable.ic_cluster_purple);
        iconGenerator.setBackground(clusterIcon);
        LayoutInflater myInflater = (LayoutInflater)contextN.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View clusterView= myInflater.inflate(R.layout.cluster_view, null, false);
        iconGenerator.setContentView(clusterView);
        iconGenerator.makeIcon(String.valueOf(cluster.getSize()));
        BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(iconGenerator.makeIcon());
        markerOptions.icon(icon);

    }

    @Override
    protected void onBeforeClusterItemRendered(User item, MarkerOptions markerOptions) { // 5
        markerImageView.setImageResource(R.drawable.ic_location);  // 6
        Bitmap icon = iconGenerator.makeIcon();  // 7
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));  // 8
        markerOptions.title(item.getTitle());
    }
} 