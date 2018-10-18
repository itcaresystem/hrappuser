package ride.happyy.user;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class ShowVehiclesOnMapActivity extends FragmentActivity implements OnMapReadyCallback,LocationListener,GoogleMap.OnMarkerClickListener {
    private GoogleMap mMap;
    Double curent_la;
    Double curent_lon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_vehicles_on_map);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_car_show_map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            return;
        }
        mMap.setMyLocationEnabled(true);
        ArrayList<VehiclesLocationInfo> vehiclesArrayList = new ArrayList<>();
        VehiclesLocationInfo vehiclesLocationInfo = new VehiclesLocationInfo("1","1","23.8103","90.4125");
        VehiclesLocationInfo vehiclesLocationInfo1 = new VehiclesLocationInfo("2","1","22.7010","90.3535");
        VehiclesLocationInfo vehiclesLocationInfo2 = new VehiclesLocationInfo("3","1","22.341900","91.815536");
        vehiclesArrayList.add(vehiclesLocationInfo);
        vehiclesArrayList.add(vehiclesLocationInfo1);
        vehiclesArrayList.add(vehiclesLocationInfo2);


            String lat_i = "23.8103";
            String long_i = "90.4125";

            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(Double.parseDouble(lat_i) , Double.parseDouble(long_i)))
                    .title(Double.valueOf(lat_i).toString() + "," + Double.valueOf(long_i).toString())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))
            );

        lat_i = "22.7010";
        long_i = "90.3535";

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(Double.parseDouble(lat_i) , Double.parseDouble(long_i)))
                .title(Double.valueOf(lat_i).toString() + "," + Double.valueOf(long_i).toString())
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
        );

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(23.6850,90.3563), 15.0f));


    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }



    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

     class VehiclesLocationInfo {
        private    String id="";
        private  String tyoe ="";
        private    String lat="";
        private  String long_t ="";
        public VehiclesLocationInfo(String id, String tyoe) {
            this.id = id;
            this.tyoe = tyoe;
        }

        public VehiclesLocationInfo(String id, String tyoe, String lat, String long_t) {
            this.id = id;
            this.tyoe = tyoe;
            this.lat = lat;
            this.long_t = long_t;
        }
    }
}
