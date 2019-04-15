package ride.happyy.user.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ride.happyy.user.R;
import ride.happyy.user.app.App;
import ride.happyy.user.config.Config;
import ride.happyy.user.listeners.AppStatusListener;
import ride.happyy.user.listeners.PolyPointsListener;
import ride.happyy.user.listeners.TripCancellationListener;
import ride.happyy.user.model.DriverBean;
import ride.happyy.user.model.PolyPointsBean;
import ride.happyy.user.model.TripCancellationBean;
import ride.happyy.user.net.DataManager;
import ride.happyy.user.util.AppConstants;

public class OnTripActivity extends BaseAppCompatNoDrawerActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMyLocationButtonClickListener, com.google.android.gms.location.LocationListener,
        android.location.LocationListener,GoogleMap.OnMarkerClickListener {

    private static final String TAG = "OnTripA";

    private static final int LOCATION_SOURCE = 0;
    private static final int LOCATION_DESTINATION = 1;
    private boolean isMapInit = true;
    protected LocationManager locationManager;
    private DriverBean driverBean;
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private LinearLayout llDriverInfo;
    private TextView txtETA;
    private ImageView ivETAMarker;
    private TextView txtDriverName;
    private TextView txtCarNumber,customer_confirmation_code;
    private RatingBar ratingDriver;
    private ImageView ivDriverPhoto;
    private ImageView ivCarPhoto;
    private Marker markerCar;
    private String tripID;
    private PolyPointsBean polyPointsBean;
    private Polyline polyLine;
    private LatLngBounds bounds;
    private Handler mHandler=new Handler();
    private GoogleApiClient mGoogleApiClient;
    private LinearLayout extra_info_for_trip_ll;
    private TextView trip_source_nameTV,trip_destination_nameTV,ontrip_fareTV;
    private Location mLocation;
    private Button btn_driver_cancel;
    private boolean photo_once=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_trip);

        getSupportActionBar().hide();
        swipeView.setPadding(0, 0, 0, 0);

        if (getIntent().hasExtra("bean")) {
            driverBean = (DriverBean) getIntent().getSerializableExtra("bean");
        } else {
            Toast.makeText(this, R.string.message_something_went_wrong, Toast.LENGTH_SHORT).show();
            finish();
        }


        initViews();
        initMap();
//        populateDriverDetails();
      //  mHandler.post(appStatusTask);
        setProgressScreenVisibility(true, true);

    }


    private void initViews() {
        extra_info_for_trip_ll=findViewById(R.id.extra_info_for_trip_ll);
        trip_source_nameTV=findViewById(R.id.trip_source_nameTV);
        trip_destination_nameTV=findViewById(R.id.trip_destination_nameTV);
        ontrip_fareTV=findViewById(R.id.ontrip_fareTV);
        llDriverInfo = (LinearLayout) findViewById(R.id.ll_on_trip_details);

        txtETA = (TextView) findViewById(R.id.txt_on_trip_estimated_time_of_arrival);
        ivETAMarker = (ImageView) findViewById(R.id.iv_on_trip_marker);

        txtDriverName = (TextView) findViewById(R.id.txt_on_trip_driver_name);
        txtCarNumber = (TextView) findViewById(R.id.txt_on_trip_car_number);
        customer_confirmation_code =(TextView) findViewById(R.id.customer_confirmation_code);

        ratingDriver = (RatingBar) findViewById(R.id.rating_on_trip_driver_rating);

        ivDriverPhoto = (ImageView) findViewById(R.id.iv_on_trip_driver_photo);
        ivCarPhoto = (ImageView) findViewById(R.id.iv_on_trip_car_photo);
        btn_driver_cancel=findViewById(R.id.btn_driver_cancel);


    }

    private void initMap() {


        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_home_map);


        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                mMap.setPadding(0, (int) ((100 * px) + mActionBarHeight + getStatusBarHeight()), 0, (int) (230 * px));
                if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    if (!checkForLocationPermissions()) {
                        getLocationPermissions();
                    }
                    checkLocationSettingsStatus();
                } else {
                    mMap.setMyLocationEnabled(true);
                  //  getCurrentLocation();
                }


//                populateDriverDetails();
                mHandler.postDelayed(appStatusTask, 3000);


            }
        });

        /*try {
            ViewGroup.LayoutParams params = mapFragment.getView().getLayoutParams();
            params.height = (int) (height - (230 * px) *//*- getNavBarHeight()*//* - mActionBarHeight - getStatusBarHeight()*//*- (230 * px)*//*);

            Log.i(TAG, "onActivityResult: MapHeight " + params.height);
            mapFragment.getView().setLayoutParams(params);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
    protected void setUpLocationClientIfNeeded() {
       /* if(!checkForLocationPermissions())
            getLocationPermissions();*/


        if (App.getInstance().getGoogleApiClient() == null) {

            mGoogleApiClient = new GoogleApiClient.Builder(App.getInstance().getApplicationContext())
                    .addApi(LocationServices.API)
                    .enableAutoManage(this, this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
            //		mGoogleApiClient = new LocationClient(getApplicationContext(),this,this);
            App.getInstance().setGoogleApiClient(mGoogleApiClient);
        } else {
            mGoogleApiClient = App.getInstance().getGoogleApiClient();
        }

        if (isMapInit) {
            mGoogleApiClient.registerConnectionCallbacks(this);
            mGoogleApiClient.registerConnectionFailedListener(this);
            isMapInit = false;
        }
    }

    public void getCurrentLocation() {
        setUpLocationClientIfNeeded();
        if (!mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        }
        if (ActivityCompat.checkSelfPermission(App.getInstance(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(App.getInstance(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (!checkForLocationPermissions()) {
                getLocationPermissions();
            }
            checkLocationSettingsStatus();
        } else {
            if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {

                if (LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient) != null) {
                    Config.getInstance().setCurrentLatitude(""
                            + LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient).getLatitude());
                    Config.getInstance().setCurrentLongitude(""
                            + LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient).getLongitude());
                }
            /*else{
                System.out.println("Last Location : " + mockLocation);
				currentLatitude = ""+mockLocation.getLatitude();
				currentLongitude = ""+mockLocation.getLongitude();
			}*/
            }
        }

        locationManager = (LocationManager) App.getInstance().getSystemService(Context.LOCATION_SERVICE);
        if (hasLocationPermissions) {
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            } else {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
            }
        }
       /* if ((Config.getInstance().getCurrentLatitude() == null
                || Config.getInstance().getCurrentLongitude() == null)
                || (Config.getInstance().getCurrentLatitude().equals("")
                || Config.getInstance().getCurrentLatitude().equals(""))) {
            //Toast.makeText(MapActivity.this, "Retrieving Current Location...", Toast.LENGTH_SHORT).show();
            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            if (hasLocationPermissions) {
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                } else {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
                }
            }

            //			mHandler.postDelayed(periodicTask, 3000);
        } else {
            if (mGoogleApiClient != null) {
                mGoogleApiClient.disconnect();
            }
        }*/
    }

public void onClickTripInfo(View view){
    if(extra_info_for_trip_ll.getVisibility()==View.VISIBLE){
        extra_info_for_trip_ll.setVisibility(View.GONE);
    }else{
        extra_info_for_trip_ll.setVisibility(View.VISIBLE);
    }

}
    public void onCancelButtonClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        if (App.isNetworkAvailable()) {
            performTripCancellation();
        } else {
            Snackbar.make(coordinatorLayout, AppConstants.NO_NETWORK_AVAILABLE, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
        }
    }

    public void onContactButtonClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        if (checkForCallPermissions()) {
            performCall(driverBean.getDriverNumber());
        } else {
            getCallPermissions();
        }
    }

    public void populateDriverDetails() {

        if(mMap != null&& driverBean!=null){

            switch (driverBean.getDriverStatus()){
                case 0:
                    mMap.clear();
                    onPlotDriverCar();
                    break;
                case 1:
                    mMap.clear();
                    onPlotDriverCar();
                    break;
                default:
                    mMap.clear();
                   /* onPlotLocation(true, LOCATION_SOURCE,
                    driverBean.getDSourceLatitude(), driverBean.getDSourceLongitude());

                    onPlotLocation(true, LOCATION_DESTINATION,
                            driverBean.getDDestinationLatitude(), driverBean.getDDestinationLongitude());

                             */
                    if(!Config.getInstance().getCurrentLatitude().equals("")&& !Config.getInstance().getCurrentLongitude().equals("")) {
                        onPlotDriverCarAfterConfirmArive( Double.parseDouble(Config.getInstance().getCurrentLatitude()), Double.parseDouble(Config.getInstance().getCurrentLongitude()));
                    }

                   // fetchPolyPoints(true);
                    break;

            }

        }
/*
        if (mMap != null) {
            onPlotLocation(true, LOCATION_SOURCE,
                    driverBean.getDSourceLatitude(), driverBean.getDSourceLongitude());
            onPlotLocation(true, LOCATION_DESTINATION,
                    driverBean.getDDestinationLatitude(), driverBean.getDDestinationLongitude());
           // onPlotDriverCar();
        }
        */

        txtETA.setText(driverBean.getTime() != null && !driverBean.getTime().equalsIgnoreCase("null")
                ? driverBean.getTime() : getString(R.string.label_not_available));
        txtDriverName.setText(driverBean.getDriverName());
        txtCarNumber.setText(driverBean.getCarNumber());
        customer_confirmation_code.setText("TP.Code : "+driverBean.getTrip_confirmation_code());
        ratingDriver.setRating(driverBean.getRating());
        trip_source_nameTV.setText(driverBean.getSourcseLocation());
        trip_destination_nameTV.setText(driverBean.getDestinationLocation());
        ontrip_fareTV.setText(driverBean.getFare());

        if(driverBean.getDriverStatus()==2){
            btn_driver_cancel.setVisibility(View.GONE);

        }else {
            btn_driver_cancel.setVisibility(View.VISIBLE);
        }
if(photo_once) {
    switch (driverBean.getCarType()) {
        case "1":
            Glide.with(getApplicationContext())
                    .load(driverBean.getCarPhoto())
                    .apply(new RequestOptions()
                            .error(R.drawable.bikeperfectlogoselected)
                            .fallback(R.drawable.bikeperfectlogoselected))
                    .into(ivCarPhoto);
            break;
        case "2":
            Glide.with(getApplicationContext())
                    .load(driverBean.getCarPhoto())
                    .apply(new RequestOptions()
                            .error(R.drawable.cng)
                            .fallback(R.drawable.cng))
                    .into(ivCarPhoto);
            break;
        case "3":
            Glide.with(getApplicationContext())
                    .load(driverBean.getCarPhoto())
                    .apply(new RequestOptions()
                            .error(R.drawable.carlogo111)
                            .fallback(R.drawable.carlogo111))
                    .into(ivCarPhoto);
            break;
        case "4":
            Glide.with(getApplicationContext())
                    .load(driverBean.getCarPhoto())
                    .apply(new RequestOptions()
                            .error(R.drawable.ambulancetestimagepng)
                            .fallback(R.drawable.ambulancetestimagepng))
                    .into(ivCarPhoto);
            break;
        default:
            Glide.with(getApplicationContext())
                    .load(driverBean.getCarPhoto())
                    .apply(new RequestOptions()
                            .error(R.drawable.carlogo111)
                            .fallback(R.drawable.carlogo111))
                    .into(ivCarPhoto);

    }




        Log.i(TAG, "populateDriverDetails: DriverPhoto " + driverBean.getDriverPhoto());

        Glide.with(getApplicationContext())
                .load(driverBean.getDriverPhoto())
                .apply(new RequestOptions()
                        .error(R.drawable.ic_dummy_photo)
                        .fallback(R.drawable.ic_dummy_photo)
                        .centerCrop()
                        .circleCrop())
                .into(ivDriverPhoto);

        Log.i(TAG, "populateDriverDetails: CarPhoto " + driverBean.getCarPhoto());
    photo_once=false;
}

        setProgressScreenVisibility(false, false);
//        fetchAppStatus();
    }
boolean carZoomBefor=true;
    private void onPlotDriverCar() {

        try {
       //    mMap.clear();
//           mMap.addMarker(new MarkerOptions().position(newLatLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_driver_car_landing_page)));

           // BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_driver_details_car);
          //  Bitmap b = bitmapDrawable.getBitmap();
            Bitmap smallMarker ;
            if(!driverBean.getCarType().equals("")) {
                smallMarker = resizeOnTropMarker(driverBean.getCarType());
            }else {
                BitmapDrawable bitmapDrawableCar = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_driver_details_car);
                Bitmap car = bitmapDrawableCar.getBitmap();
                smallMarker = Bitmap.createScaledBitmap(car, 50, 50, false);
            }



            if (mMap != null) {
                if (markerCar == null) {
                    markerCar = mMap.addMarker(new MarkerOptions()
                            .position(driverBean.getCarLatLng())
                            .icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
                            .flat(true));
                } else {
                    markerCar = mMap.addMarker(new MarkerOptions()
                            .position(driverBean.getCarLatLng())
                            .icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
                            .flat(true));
                  //  markerCar.setPosition(driverBean.getCarLatLng());
                }
                if(carZoomBefor) {
                    mapAutoZoom();
                    carZoomBefor=false;

                }

                onPlotLocation(true, LOCATION_SOURCE,
                        driverBean.getDSourceLatitude(), driverBean.getDSourceLongitude());
                fetchPolyPoints(true);
               // Toast.makeText(this, "Before trip action!!!", Toast.LENGTH_SHORT).show();
            }


        } catch (NumberFormatException e) {
            e.printStackTrace();

        }
    }

    boolean carZoomafter=true;
    private void onPlotDriverCarAfterConfirmArive(double latitude,double longitude) {

        try {
           // mMap.clear();

            Bitmap smallMarker ;
            if(!driverBean.getCarType().equals("")) {
                smallMarker = resizeOnTropMarker(driverBean.getCarType());
            }else {
                BitmapDrawable bitmapDrawableCar = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_driver_details_car);
                Bitmap car = bitmapDrawableCar.getBitmap();
                smallMarker = Bitmap.createScaledBitmap(car, 50, 50, false);
            }



            if (mMap != null) {
                if (markerCar == null) {
                    markerCar = mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(latitude,longitude))
                            .icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
                            .flat(true));
                } else {
                   // markerCar.setPosition(new LatLng(latitude,longitude));
                    markerCar = mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(latitude,longitude))
                            .icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
                            .flat(true));
                }
                if(carZoomafter) {
                    mapAutoZoom();
                    carZoomafter=false;
                }

                onPlotLocation(true, LOCATION_DESTINATION,
                        driverBean.getDDestinationLatitude(), driverBean.getDDestinationLongitude());
                fetchPolyPoints(true);
            }


        } catch (NumberFormatException e) {
            e.printStackTrace();

        }
    }

    Runnable appStatusTask = new Runnable() {
        @Override
        public void run() {
            if (App.isNetworkAvailable()) {
                fetchAppStatus();
                mHandler.postDelayed(appStatusTask,3000);
            }

        }

    };

    private void fetchAppStatus() {

        HashMap<String, String> urlParams = new HashMap<>();
        JSONObject postData = getJsonData();

        DataManager.fetchAppStatus(postData, new AppStatusListener() {
            @Override
            public void onLoadCompleted(DriverBean driverBeanWS) {

                if(driverBeanWS !=null && driverBeanWS.getTrip_status().equals("3")){
                    mHandler.removeCallbacks(appStatusTask);
                    Intent tripDetailsIntent=new Intent(getBaseContext(),DriverRatingActivity.class);
                    tripDetailsIntent.putExtra("trip_id",driverBeanWS.getTripID());
                    tripDetailsIntent.putExtra("driverdetails",driverBeanWS);
                    startActivity(tripDetailsIntent);
                    finish();

                }else {

                    if (driverBeanWS.getAppStatus() != 0) {
                        driverBean = driverBeanWS;
                        populateDriverDetails();
                    } else {
                        mHandler.removeCallbacks(appStatusTask);
                        Toast.makeText(OnTripActivity.this, R.string.message_trip_ended, Toast.LENGTH_LONG).show();
                        startActivity(new Intent(OnTripActivity.this, SplashActivity.class)
                                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                        finish();
                    }
                }
            }

            @Override
            public void onLoadFailed(String error) {
                Log.i(TAG, "onLoadFailed: Error " + error);
            }
        });

    }

    public JSONObject getJsonData() {
        JSONObject jsonData = new JSONObject();
        try {
            jsonData.put("phone", Config.getInstance().getPhone());
            jsonData.put("customer_id", Config.getInstance().getUserID());
            jsonData.put("passenger_latitude",Config.getInstance().getCurrentLatitude());
            jsonData.put("passenger_longitude",Config.getInstance().getCurrentLongitude());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonData;
    }

    public void performTripCancellation() {

        swipeView.setRefreshing(true);
        JSONObject postData = geTripCancellationJSObj();

        DataManager.performTripCancellation(postData, new TripCancellationListener() {

            @Override
            public void onLoadCompleted(TripCancellationBean tripCancellationBean) {
                swipeView.setRefreshing(false);
              //  mHandler.removeCallbacks(appStatusTask);
                Toast.makeText(OnTripActivity.this, R.string.message_trip_cancelled, Toast.LENGTH_SHORT).show();

                startActivity(new Intent(OnTripActivity.this, LandingPageActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();

            }

            @Override
            public void onLoadFailed(String error) {

                swipeView.setRefreshing(false);

//                finish();
            }
        });
    }

    private JSONObject geTripCancellationJSObj() {

        tripID = driverBean.getTripID();

        JSONObject postData = new JSONObject();

        try {
            postData.put("trip_id", tripID);
            postData.put("phone",Config.getInstance().getPhone());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return postData;
    }

    public Bitmap resizeOnTropMarker(String cartype){
        Bitmap onTripMarker=null;
        switch (cartype){
            case "1":
                BitmapDrawable bitmapDrawableBike = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_marker_bike);
                Bitmap bike = bitmapDrawableBike.getBitmap();
                onTripMarker = Bitmap.createScaledBitmap(bike, 60, 95, false);
                break;
            case "2":
                BitmapDrawable bitmapDrawableCng = (BitmapDrawable) getResources().getDrawable(R.drawable.cng);
                Bitmap cng = bitmapDrawableCng.getBitmap();
                onTripMarker = Bitmap.createScaledBitmap(cng, 50, 50, false);
                break;
            case "3":
                BitmapDrawable bitmapDrawableCar = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_marker_car);
                Bitmap car = bitmapDrawableCar.getBitmap();
                onTripMarker = Bitmap.createScaledBitmap(car, 60, 90, false);
                break;
            case "4":
                BitmapDrawable bitmapDrawableAmbulance = (BitmapDrawable) getResources().getDrawable(R.drawable.ambulancetestimagepng);
                Bitmap ambulance = bitmapDrawableAmbulance.getBitmap();
                onTripMarker = Bitmap.createScaledBitmap(ambulance, 50, 50, false);
                break;
             default:
                 break;
        }

        return onTripMarker;


    }

int twice=0;
    Bitmap myLocationMarker=null;
    public void onPlotLocation(boolean isMarkerNeeded, int type, double latitude, double longitude) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_destination_new);
        Bitmap b = bitmapDrawable.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, 165, 120, false);

        LatLng newLatLng = null;
        try {
            newLatLng = new LatLng(latitude, longitude);
            if (isMarkerNeeded) {
                switch (type) {
                    case LOCATION_SOURCE:
                        mMap.addMarker(new MarkerOptions()
                                .position(newLatLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.pickupnewicone)));
                        break;
                    case LOCATION_DESTINATION:
                        mMap.addMarker(new MarkerOptions()
                                .position(newLatLng).icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
                        break;
                    default:
                      //  mMap.addMarker(new MarkerOptions()
                              //  .position(newLatLng).icon(BitmapDescriptorFactory.defaultMarker()));
                        break;
                }
            }

            Log.i(TAG, "onPlotLocation: Position" + newLatLng);

        } catch (NumberFormatException e) {
            e.printStackTrace();

        }
    }
int intPath=0;
    public void fetchPolyPoints(final boolean isPolyLineNeeded) {

        HashMap<String, String> urlParams = new HashMap<>();

//        if (sourceBean != null && destinationBean != null) {
        if(driverBean.getDriverStatus()==0 || driverBean.getDriverStatus()==1 ){
            urlParams.put("origin", Config.getInstance().getCurrentLatitude() + "," + Config.getInstance().getCurrentLongitude());
            urlParams.put("destination", driverBean.getCarLatitude() + "," + driverBean.getCarLongitude());
        }else {

           // urlParams.put("origin", driverBean.getSourceLatitude() + "," + driverBean.getSourceLongitude());
            urlParams.put("origin", Config.getInstance().getCurrentLatitude() + "," + Config.getInstance().getCurrentLongitude());
            urlParams.put("destination", driverBean.getDestinationLatitude() + "," + driverBean.getDDestinationLongitude());
        }
        urlParams.put("mode", "driving");
        urlParams.put("key", getString(R.string.browser_api_key));
//        }

        DataManager.fetchPolyPoints(urlParams, new PolyPointsListener() {

            @Override
            public void onLoadCompleted(PolyPointsBean polyPointsBeanWS) {
                swipeView.setRefreshing(false);

                polyPointsBean = polyPointsBeanWS;

/*                time = String.valueOf(polyPointsBean.getTime());
                distance = String.valueOf(polyPointsBean.getDistance());*/

                Log.i(TAG, "onLoadCompleted: Time Taken" + polyPointsBean.getTimeText());
                Log.i(TAG, "onLoadCompleted: Distance" + polyPointsBean.getDistanceText());

                if (isPolyLineNeeded) {
                    populatePath();
                }
            }

            @Override
            public void onLoadFailed(String error) {
                swipeView.setRefreshing(false);
               // Snackbar.make(coordinatorLayout, error, Snackbar.LENGTH_LONG)
                      //  .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            }
        });
    }

    private void populatePath() {

        List<List<HashMap<String, String>>> routes = polyPointsBean.getRoutes();

        ArrayList<LatLng> points = null;
        PolylineOptions polyLineOptions = null;

        // traversing through routes
        for (int i = 0; i < routes.size(); i++) {
            points = new ArrayList<LatLng>();
            polyLineOptions = new PolylineOptions();
            List path = routes.get(i);

            for (int j = 0; j < path.size(); j++) {
                HashMap point = (HashMap) path.get(j);

                double lat = Double.parseDouble((String) point.get("lat"));
                double lng = Double.parseDouble((String) point.get("lng"));
                LatLng position = new LatLng(lat, lng);

                points.add(position);
            }

            polyLineOptions.addAll(points);
            polyLineOptions.width(6);
            polyLineOptions.color(ContextCompat.getColor(getApplicationContext(), R.color.black));

        }

        polyLine = mMap.addPolyline(polyLineOptions);
    }

    public void mapAutoZoom() {

       /* if (sourceBean != null && destinationBean != null) {
            newLatLng1 = new LatLng(sourceBean.getDLatitude(), sourceBean.getDLongitude());
            newLatLng2 = new LatLng(destinationBean.getDLatitude(), destinationBean.getDLongitude());
        }*/

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        if(driverBean != null && driverBean.getDriverStatus()==0 && !Config.getInstance().getCurrentLatitude().equals("") && !Config.getInstance().getCurrentLongitude().equals("")){
                  builder.include(new LatLng(Double.parseDouble(Config.getInstance().getCurrentLatitude()),Double.parseDouble(Config.getInstance().getCurrentLongitude())));
                  builder.include(driverBean.getCarLatLng());

        }else{
            builder.include(driverBean.getSourceLatLng());
            builder.include(driverBean.getDestinationLatLng());
        }



        Log.i(TAG, "mapAutoZoom: SOURCE : " + driverBean.getSourceLatLng());
        Log.i(TAG, "mapAutoZoom: DESTINATION : " + driverBean.getDestinationLatLng());
        Log.i(TAG, "mapAutoZoom: CAR : " + driverBean.getCarLatLng());
        bounds = builder.build();

//        mMap.setPadding(0, (int) (height - getStatusBarHeight() - mActionBarHeight - (px * 160)), 0, (int) (height - getStatusBarHeight() - mActionBarHeight - (px * 120)));

//        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, (int) (40 * px)));
        if (mapFragment.getView() != null) {
            if (mapFragment.getView().getHeight() > 150 * px)
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, (int) (40 * px)));
            else
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, (int) (5 * px)));
        }

    }


    public void onOnTripDriverDetailsClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        //mVibrator.vibrate(25);

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
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        if(location!=null) {
           // mLocation=location;
            Config.getInstance().setCurrentLatitude(String.valueOf(location.getLatitude()));
            Config.getInstance().setCurrentLongitude(String.valueOf(location.getLongitude()));
        }

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
