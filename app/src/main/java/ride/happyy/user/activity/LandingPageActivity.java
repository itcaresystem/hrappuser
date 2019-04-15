package ride.happyy.user.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.data.DataBufferUtils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.SphericalUtil;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.nex3z.notificationbadge.NotificationBadge;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import ride.happyy.user.OneDayReq;
import ride.happyy.user.R;
import ride.happyy.user.ShowVehiclesOnMapActivity;
import ride.happyy.user.adapter.CarTypeRecyclerAdapter;
import ride.happyy.user.app.App;
import ride.happyy.user.config.Config;
import ride.happyy.user.dialogs.PopupMessage;
import ride.happyy.user.dialogs.PricePolicyDialog;
import ride.happyy.user.listeners.BasicListener;
import ride.happyy.user.listeners.CarInfoListener;
import ride.happyy.user.listeners.LandingPageListener;
import ride.happyy.user.listeners.PermissionListener;
import ride.happyy.user.listeners.PolyPointsListener;
import ride.happyy.user.listeners.TotalFareListener;
import ride.happyy.user.menuFragments.AboutFragment;
import ride.happyy.user.menuFragments.GetDiscountFragment;
import ride.happyy.user.menuFragments.HelpFragment;
import ride.happyy.user.menuFragments.HistoryFragment;
import ride.happyy.user.menuFragments.NotificationFragment;
import ride.happyy.user.menuFragments.PromotionsFragments;
import ride.happyy.user.menuFragments.SettingsFragment;
import ride.happyy.user.model.BasicBean;
import ride.happyy.user.model.Car;
import ride.happyy.user.model.CarBean;
import ride.happyy.user.model.DestinationBean;
import ride.happyy.user.model.DriverBean;
import ride.happyy.user.model.FareBean;
import ride.happyy.user.model.LandingPageBean;
import ride.happyy.user.model.OnedayRequestModel;
import ride.happyy.user.model.OnedayRequestResponse;
import ride.happyy.user.model.PlaceBean;
import ride.happyy.user.model.PolyPointsBean;
import ride.happyy.user.model.SourceBean;
import ride.happyy.user.model.UserBean;
import ride.happyy.user.net.DataManager;
import ride.happyy.user.net.NetworkCall;
import ride.happyy.user.net.ResponseCallback;
import ride.happyy.user.net.WSAsyncTasks.FCMRegistrationTask;
import ride.happyy.user.net.WSAsyncTasks.LocationNameTask;
import ride.happyy.user.net.WSAsyncTasks.LocationTask;
import ride.happyy.user.util.AppConstants;
import ride.happyy.user.util.FareCalculation;

import static android.provider.Settings.Secure.LOCATION_MODE_HIGH_ACCURACY;


public class LandingPageActivity extends BaseAppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMyLocationButtonClickListener, com.google.android.gms.location.LocationListener,
        android.location.LocationListener,GoogleMap.OnMarkerClickListener {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private static final int UPDATE_INTERVAL = 10000;
    private static final int FASTEST_INTERVAL = 5000;
    private static final int DISPLACEMENT = 10;
    private static final String TAG = "LandingPA";
    private String fcm_tocken;
    private String dri_phone_re_req="";
    String fcm_tocken_fr="";

    private static final LocationRequest mLocationRequest = LocationRequest.create()
            .setInterval(5000)         // 5 seconds
            .setFastestInterval(16)    // 16ms = 60fps
            .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQ_SEARCH_SOURCE_SELECT = 0;
    private static final int REQ_SEARCH_DESTINATION_SELECT = 1;
    private static final int REQ_SEARCH_DESTINATION_ESTIMATE_SELECT = 2;
    private static final int REQ_REQUEST_RIDE = 3;
    private static final int REQ_ESTIMATED_DESTINATION = 4;
    private static final int LOCATION_SOURCE = 0;
    private static final int LOCATION_DESTINATION = 1;
    private int click_pick_or_des =1;
    private NetworkCall networkCall;

    private static GoogleMapOptions options = new GoogleMapOptions()
            .mapType(GoogleMap.MAP_TYPE_NORMAL)
            .compassEnabled(true)
            .rotateGesturesEnabled(true)
            .tiltGesturesEnabled(true)
            .zoomControlsEnabled(true)
            .scrollGesturesEnabled(true)
            .mapToolbarEnabled(true);

    //    private GoogleApiClient mGoogleApiClient;
    private ArrayList<Car> carsLocation;
    private ArrayList<Car> bikesLocation;
    private ArrayList<Car> onlyCarsLocation;
    private ArrayList<Car> ambulanceLocation;
    protected LocationManager locationManager;
    private Marker CurrentMarker;
    private double dLatitude;
    private double dLongitude;
    private LatLng current;
    private Location LastLocation;
    private GoogleMap mMap;
    private Toolbar toolbarHome;
    private TextView txtActionSearch;
    private FrameLayout framePickup;
    // private ImageView ivMarker;
    private ImageView ivBottomMarker;
    private LinearLayout llLandingBottomBar;
    private ImageView ivLocationButton;
    private SupportMapFragment mapFragment;
    //    private View lytBottom;
    private TextView txtTime,destinationTV;
    private TextView txtMaxSize;
    private TextView txtFare;
    private String carType = String.valueOf(-1);
    //    private int searchPlaceType = AppConstants.SEARCH_SOURCE;
    private TextView txtSource;
    private LinearLayout llConfirmation;
    private boolean isConfirmationPage = false;
    private boolean isCameraMoved;
    private CardView cvConfirmationPage;
    private TextView txtDestination;
    private TextView txtTotalFare;
    private RelativeLayout rlFare;
    private LinearLayout happyyFareWithService;
    private View viewDottedLine;
    private TextView txtCarOne;
    private TextView txtCarTwo;
    private TextView txtCarThree;
    private TextView carFour;
    private TextView txtFareEstimate;
    private TextView txtTo;
    private LinearLayout llDestinationEstimated;
    private TextView txtEstimatedDestination;
    private Button btnRequest;
    private View.OnClickListener snackBarRefreshOnClickListener;

    private int searchType;
    private FareBean fareBean;
    private PolyPointsBean polyPointsBean;
    private Polyline polyLine;
    private LatLngBounds bounds;
    private LatLng newLatLng1;
    private LatLng newLatLng2;
    private ImageView carOneImage;
    private ImageView carTwoImage;
    private ImageView carThreeImage;
    private ImageView carFourImage;
    //  private TextView txtCarAvailability;
    private String time;
    private String distance;
    private boolean isDestinationEstimateSelect = false;
    private LinearLayout llFare;
    // private TextView txtCarArrivalEstimatedTime;
    private CarBean carBean;
    private LandingPageBean landingPageBean;
    private PlaceBean destinationBean;
    private PlaceBean sourceBean;
    private BottomSheetBehavior<LinearLayout> bottomSheetBehavior;
    private ViewGroup.LayoutParams param;
    private FrameLayout destination_select_fl;
    private ViewGroup.LayoutParams param1;
    private TextView txtEstimatedFare;
    private boolean isMapInit = true;
    private GoogleApiClient mGoogleApiClient;
    private TextView txtFareLabel;
    private LinearLayout llProgressBar;
    private LinearLayout llEstimation;
    private LinearLayout llConfirmationProgress;
    private boolean isInit = true;
    private CarTypeRecyclerAdapter adapterCarTypes;
    private RecyclerView rvCarTypes;

    private AutocompleteFilter typeFilter;

    // for select service
    private LinearLayout linear_layout_bike,linear_layout_cng, linear_layout_car_trip, linear_layout_car_one_hour, linear_layout_car_two_hour , linear_layout_car_four_hour, linear_layout_car_one_day;
    private LinearLayout bikeLinearLayout,cngLinearLayout, carLinearLayout,ambulanceLinearLayout, oneHourLinearLayout,twoHoursLinearLayout, fourHoursLinearLayout, dayLinearLayout,dayNoahLinearLayout,dayHiaceLinearLayout;
    private ImageView bikeRequestImageView,cngRequestImageView, carRequestImageView,ambulanceRequestImageView, carOneHourRequestImageView, carTwoHoursRequestImageView, carFourHoursRequestImageView,carDayImageView, carDayPrimioRequestImageView, carDayNoahRequestImageView, carDayHiceRequestImageView;
    private TextView bikeFareTextView,cngFareTextView,carFareTextView,ambulanceFareTextView, oneHourFareTextView,twoHoursFareTextView,fourHoursFareTextView,dayFareTextView,dayNoahFareTextView,dayHiaceFareTextView;
    private ImageButton bikeInfoImageButton, cngInfoImageButton,carInfoImageButton, oneHourInfoImageButton, twoHourInfoImageButton,fourHourInfoImageButton, dayInfoImageButton;
    private Button bikeRequestBtn,cngRequestBtn, carRequestBtn,ambulanceRequestBtn, carOneHourRequestBtn, carTwoHoursRequestBtn, carFourHoursRequestBtn, carDayPrimioRequestBtn, carDayNoahRequestBtn, carDayHiceRequestBtn;
    private Button outOfDhakaService, packageService,distinationConfirmButton;
    PricePolicyDialog pricePolicyDialog;
    TextView bikeInfoTv,cngInfoTV,carInfoTv,ambulanceInfoTv,premioInfoTv,noahInfoTv,hiaceInfoTv,hirecarInfoTv;

    private ImageButton notificatinImageButton, menubarHome,outOfdhakaPage,outOfDhakaServImBtn;
    private NotificationBadge mNotificationBadge;
    private int countNotification =0;
    FareBean fareBeanAfterCalculate;
    ImageView pickup_select_marker_imv;
    ImageView destination_select_marker;
    private boolean backPressedToExitOnce;
    private boolean isLocNamFoAtu=false;
   // private boolean isLocNamFoAtu_des=false;
    private String locNamFoAuto="";
    private ProgressBar doneProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        networkCall = new NetworkCall();

        setContentView(R.layout.activity_landing_page);
        outOfdhakaPage = findViewById(R.id.btnForOutofDhakaPage);
        outOfDhakaServImBtn =findViewById(R.id.outOfDhakaServ);
        doneProgressBar=findViewById(R.id.doneProgressBar);
        outOfdhakaPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent outOfDhakaIntent = new Intent(getApplicationContext(),OutOfDhakaActivity.class);
                startActivity(outOfDhakaIntent);

            }
        });
        outOfDhakaServImBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent outOfDhakaIntent = new Intent(getApplicationContext(),OutOfDhakaActivity.class);
                startActivity(outOfDhakaIntent);

            }
        });
        isGetLocationEnabled = true;

        if(getIntent().hasExtra("dri_phone_re_req")){
            dri_phone_re_req =getIntent().getStringExtra("dri_phone_re_req");

        }



       /* if (!checkForLocationPermissions()) {
            getLocationPermissions();
        } else {
            checkLocationSettingsStatus();
        }

        if (!checkForReadWritePermissions()) {
            getReadWritePermissions();
        }else{
            isGetLocationEnabled=true;
        }*/
        initDrawer();

        initViews();
        initMap();

        //    setProgressScreenVisibility(true, true);
//        getData();

        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // getSupportActionBar().setHomeButtonEnabled(true);
        // getSupportActionBar().setTitle("");
        //  getSupportActionBar().setIcon(R.drawable.happyridetitlebarimagefinal);
        // getSupportActionBar().setDisplayShowTitleEnabled(true);
        //   getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        Configuration config = getResources().getConfiguration();
        //  getSupportActionBar().setCustomView(R.layout.layout_actionbar_title_extra_w);
        getSupportActionBar().hide();
        menubarHome     =   findViewById(R.id.menubarnImageBtn);
        notificatinImageButton = findViewById(R.id.driverNotificationImageBtn);
        mNotificationBadge      = findViewById(R.id.notificationBadgeHome);
        countNotification = 4;
        mNotificationBadge.setNumber(countNotification);

        initFCM();

        typeFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS)
                .setTypeFilter(3)
                .build();


    }


    private void initDrawer() {
        //initialize and create the image loader logic
        DrawerImageLoader.init(new AbstractDrawerImageLoader() {
            @Override
            public void set(ImageView imageView, Uri uri, Drawable placeholder) {
                Picasso.with(imageView.getContext()).load(uri).placeholder(placeholder).into(imageView);
            }

            @Override
            public void cancel(ImageView imageView) {
                Picasso.with(imageView.getContext()).cancelRequest(imageView);
            }




    /*
    @Override
    public Drawable placeholder(Context ctx) {
        return super.placeholder(ctx);
    }

    @Override
    public Drawable placeholder(Context ctx, String tag) {
        return super.placeholder(ctx, tag);
    }
    */
        });
        UserBean userBean = new UserBean();
        Toolbar myTestToolBar = findViewById(R.id.toolbarMyTest);
        // Config.getInstance().setProfilePhoto(userBean.getProfilePhoto());
        String profileImageUri = profileImageUri = userBean.getProfilePhoto();

        if(profileImageUri ==null && profileImageUri ==""){

        }

        //getResources().getDrawable(R.drawable.ic_profile_photo_default)

        // Create the AccountHeader
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.bg_header)
                .addProfiles(new ProfileDrawerItem().withName(Config.getInstance().getName()).withEmail(Config.getInstance().getPhone()).withIcon(getResources().getDrawable(R.drawable.ic_profile_photo_default)))
                .withOnAccountHeaderProfileImageListener(new AccountHeader.OnAccountHeaderProfileImageListener() {
                    @Override
                    public boolean onProfileImageClick(View view, IProfile profile, boolean current) {
                        Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_SHORT).show();
                        Intent profileSettingIntent = new Intent(getApplicationContext(),SettingsPageActivity.class);
                        startActivity(profileSettingIntent);
                        return false;
                    }

                    @Override
                    public boolean onProfileImageLongClick(View view, IProfile profile, boolean current) {

                        Toast.makeText(getApplicationContext(), "No Action is Set!!!Yet", Toast.LENGTH_SHORT).show();

                        return false;
                    }
                })
                .build();

        // faw_percent('\uf29b'),
        //    faw_percentage('\uf541')
        //if you want to update the items at a later time it is recommended to keep it in a variable
        final SecondaryDrawerItem item1 = new SecondaryDrawerItem().withIdentifier(1).withName("Home").withIcon(R.drawable.ic_home_red_24dp);
        final SecondaryDrawerItem item2 = new SecondaryDrawerItem().withIdentifier(2).withName("History").withIcon(R.drawable.ic_history_red_24dp);
        SecondaryDrawerItem item3 = new SecondaryDrawerItem().withIdentifier(3).withName("Promotions").withIcon(R.drawable.ic_label_red_24dp);
        SecondaryDrawerItem item4 = new SecondaryDrawerItem().withIdentifier(4).withName("Get Discount").withIcon(R.drawable.ic_person_add_red_24dp);
        SecondaryDrawerItem item10 = new SecondaryDrawerItem().withIdentifier(10).withName("New Feature").withIcon(R.drawable.ic_local_hospital_red_24dp);
        final SecondaryDrawerItem item5 = new SecondaryDrawerItem().withIdentifier(5).withName("Notifications").withIcon(R.drawable.ic_notifications_none_red_24dp).withBadge("3").withBadgeStyle(new BadgeStyle().withTextColor(Color.WHITE).withColorRes(R.color.red));;
        SecondaryDrawerItem item6 = new SecondaryDrawerItem().withIdentifier(6).withName("Help").withIcon(R.drawable.ic_help_outline_red_24dp);
        SecondaryDrawerItem item7 = new SecondaryDrawerItem().withIdentifier(7).withName("About").withIcon(R.drawable.ic_perm_device_information_red_24dp);
        SecondaryDrawerItem item8 = new SecondaryDrawerItem().withIdentifier(8).withName("Setting").withIcon(R.drawable.ic_settings_applications_black_24dp);
        SecondaryDrawerItem item9 = new SecondaryDrawerItem().withIdentifier(9).withName("Log Out").withIcon(R.drawable.ic_power_settings_new_red_24dp);
        // SecondaryDrawerItem item11 = new SecondaryDrawerItem().withIdentifier(10).withName("Setting");

//create the drawer and remember the `Drawer` result object
        DrawerBuilder drawerBuilder = new DrawerBuilder();
        drawerBuilder.withActivity(this);
        drawerBuilder.withToolbar(myTestToolBar);
        drawerBuilder.withAccountHeader(headerResult);
        drawerBuilder.addDrawerItems(
                item1,
                new DividerDrawerItem(),
                item2,
                new DividerDrawerItem(),
                item3,
                new DividerDrawerItem(),
                item4,
                new DividerDrawerItem(),
                item10,
                new DividerDrawerItem(),
                item5,
                new DividerDrawerItem(),
                item6,
                new DividerDrawerItem(),
                item7,
                new DividerDrawerItem(),
                item8,
                new DividerDrawerItem(),
                item9,
                new DividerDrawerItem()
                // item11
        );
        drawerBuilder.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                // do something with the clicked item :D
                Fragment fragment = null;
                switch ((int)drawerItem.getIdentifier()) {
                    case 1:
                       // Toast.makeText(getApplicationContext(), "HOME", Toast.LENGTH_SHORT).show();
                        Intent homeIntent = new Intent(getApplicationContext(),LandingPageActivity.class);
                        startActivity(homeIntent);
                        // finish();
                        break;

                    case 2:
                        Intent historyIntent = new Intent(getApplicationContext(),TripsActivity.class);
                        startActivity(historyIntent);
                      //  Toast.makeText(getApplicationContext(), "History", Toast.LENGTH_SHORT).show();

                        break;
                    case 3:
                        Intent myPromIntent = new Intent(getApplicationContext(),PromotionActivity.class);
                        startActivity(myPromIntent);
                       // Toast.makeText(getApplicationContext(), "Promotion", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Intent discountIntent = new Intent(getApplicationContext(),DiscountActivity.class);
                        startActivity(discountIntent);
                      //  Toast.makeText(getApplicationContext(), "Discount", Toast.LENGTH_SHORT).show();
                        break;
                    case 10:
                       // Intent daignosticIntent = new Intent(getApplicationContext(),DiagnosticOfferRequestActivity.class);
                        // startActivity(daignosticIntent);
                        Toast.makeText(getApplicationContext(), "Coming soon....", Toast.LENGTH_LONG).show();
                        break;
                    case 5:

                       // Toast.makeText(getApplicationContext(), "Notification", Toast.LENGTH_SHORT).show();
                        item5.withName("Notifications").withBadge("0").withBadgeStyle(new BadgeStyle().withTextColor(Color.WHITE).withColorRes(R.color.red));
                        Intent notificationIntent = new Intent(getApplicationContext(),NotificationsActivity.class);
                        startActivity(notificationIntent);
                        break;
                    case 6:
                        Intent helpIntent = new Intent(getApplicationContext(),HelpSimpleActivity.class);
                        startActivity(helpIntent);
                     //   Toast.makeText(getApplicationContext(), "Help", Toast.LENGTH_SHORT).show();
                        break;
                    case 7:
                        Intent aboutIntent = new Intent(getApplicationContext(),AboutsActivit.class);
                        startActivity(aboutIntent);
                      //  Toast.makeText(getApplicationContext(), "About", Toast.LENGTH_SHORT).show();
                        break;
                    case 8:

                        Intent settingIntent = new Intent(getApplicationContext(),SettingsPageActivity.class);
                        startActivity(settingIntent);
                      //  Toast.makeText(getApplicationContext(), "Setting", Toast.LENGTH_SHORT).show();
                        break;
                    case 9:
                      //  App.logout();
                       // Intent logoutIntent = new Intent(getApplicationContext(),WelcomeActivity.class);
                      //  startActivity(logoutIntent);

                        App.logout();
                        startActivity(new Intent(getApplicationContext(), SplashActivity.class)
                                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                        finish();
                        Toast.makeText(getApplicationContext(), "Log Out", Toast.LENGTH_SHORT).show();
                        break;


                }
                return true;
            }
        });
        Drawer result = drawerBuilder.build();

        //  result.addStickyFooterItem(new PrimaryDrawerItem().withName("HAPPY RIDE!"));
        //     getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);

        //modify an item of the drawer
        //  item5.withName("Notificatons").withBadge("19").withBadgeStyle(new BadgeStyle().withTextColor(Color.WHITE).withColorRes(R.color.red));
//notify the drawer about the updated element. it will take care about everything else
        // result.updateItem(item5);
    }


    public  void onClickMenuBarHome(View view){
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.bg_header)
                .addProfiles(
                        new ProfileDrawerItem().withName("Mike Penz").withEmail("mikepenz@gmail.com").withIcon(getResources().getDrawable(R.drawable.ic_profile_photo_default))
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();
        Toast.makeText(this,"clicked",Toast.LENGTH_LONG).show();
        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("Home drawer");
        SecondaryDrawerItem item2 = new SecondaryDrawerItem().withIdentifier(2).withName("Secondary Drwaer");

//create the drawer and remember the `Drawer` result object
        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(
                        item1,
                        new DividerDrawerItem(),
                        item2,
                        new SecondaryDrawerItem().withName("Secondary Drwaer Item1")
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {

                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                        return false;
                    }
                })
                .build();
        //set the selection to the item with the identifier 1
        result.setSelection(1);
//set the selection to the item with the identifier 2
        result.setSelection(item2);
//set the selection and also fire the `onItemClick`-listener
        result.setSelection(1, true);

        //modify an item of the drawer
        item1.withName("A new name for this drawerItem").withBadge("19").withBadgeStyle(new BadgeStyle().withTextColor(Color.WHITE).withColorRes(R.color.md_red_700));
//notify the drawer about the updated element. it will take care about everything else
        result.updateItem(item1);

//to update only the name, badge, icon you can also use one of the quick methods
        //  result.updateName();

//the result object also allows you to add new items, remove items, add footer, sticky footer, ..
        result.addItem(new DividerDrawerItem());
        result.addStickyFooterItem(new PrimaryDrawerItem().withName("StickyFooterItem"));

//remove items with an identifier
        result.removeItem(2);

//open / close the drawer
        result.openDrawer();
        result.closeDrawer();

//get the reference to the `DrawerLayout` itself
        result.getDrawerLayout();


        mNotificationBadge.setNumber(1);




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            onBackPressed();
            /*
            if (isConfirmationPage) {
                onBackClick();
            } else {
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                } else {
                    onBackPressed();
                }
            }
            */
        }


        if (keyCode == KeyEvent.KEYCODE_MENU) {
            openOptionsMenu();
        }

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkHighAcouracy();
        if (checkForLocationPermissions()) {
            if (happyyFareWithService.getVisibility()!=View.VISIBLE) {
                if (checkPlayServices()) {
                    getCurrentLocation();
//            buildGoogleApiClient();
//            createLocationRequest();
                }
            }
        }
        if (App.isNetworkAvailable()){
            if(carsLocation==null) {
                carsLocation = new ArrayList<>();
            }

        NetworkCall myNewNetworkCall= new NetworkCall();
        myNewNetworkCall.getAllLocations("", new ResponseCallback<ArrayList<Car>>() {
            @Override
            public void onSuccess(ArrayList<Car> data) {
                carsLocation=data;
               if(happyyFareWithService.getVisibility()!=View.VISIBLE) {
                   populateCarsLocation(carsLocation, 0);
               }
            }

            @Override
            public void onError(Throwable th) {

            }
        });
        }
    }

    public void checkHighAcouracy(){
        int locationMode = 0;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
                locationMode = Settings.Secure.getInt(getContentResolver(), Settings.Secure.LOCATION_MODE);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        if(locationMode == LOCATION_MODE_HIGH_ACCURACY) {
            //request location updates
            // initViews();
        } else { //redirect user to settings page
            PopupMessage popupMessage = new PopupMessage(this);
            popupMessage.show(getString(R.string.message_please_enable_location_service_from_the_settings),
                    0, getString(R.string.btn_open_settings)," ");
            popupMessage.setPopupActionListener(new PopupMessage.PopupActionListener() {
                @Override
                public void actionCompletedSuccessfully(boolean result) {
                    Log.d(TAG, "actionCompletedSuccessfully: Settings Button clicked : ");
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(myIntent);
                    // isLocationServiceEnableRequestShown = false;
                }

                @Override
                public void actionFailed() {
                    // isLocationServiceEnableRequestShown = false;
                }
            });
            //startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        }
    }
    public Bitmap resizeOnTropMarker(int cartype){
        Bitmap onTripMarker=null;
        switch (cartype){
            case 1:
                BitmapDrawable bitmapDrawableBike = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_marker_bike);
                Bitmap bike = bitmapDrawableBike.getBitmap();
                onTripMarker = Bitmap.createScaledBitmap(bike, 60, 95, false);
                break;
            case 2:
                BitmapDrawable bitmapDrawableCng = (BitmapDrawable) getResources().getDrawable(R.drawable.cng);
                Bitmap cng = bitmapDrawableCng.getBitmap();
                onTripMarker = Bitmap.createScaledBitmap(cng, 50, 50, false);
                break;
            case 3:
                BitmapDrawable bitmapDrawableCar = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_marker_car);
                Bitmap car = bitmapDrawableCar.getBitmap();
                onTripMarker = Bitmap.createScaledBitmap(car, 60, 90, false);
                break;
            case 4:
                BitmapDrawable bitmapDrawableAmbulance = (BitmapDrawable) getResources().getDrawable(R.drawable.ambulancetestimagepng);
                Bitmap ambulance = bitmapDrawableAmbulance.getBitmap();
                onTripMarker = Bitmap.createScaledBitmap(ambulance, 50, 50, false);
                break;
            default:
                break;
        }

        return onTripMarker;


    }

    private void populateBikeLocation(ArrayList<Car> bikeLoca,int req_type) {
       mMap.clear();

        if (sourceBean != null && destinationBean != null && checkLocationName()) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_destination_new);
            Bitmap b = bitmapDrawable.getBitmap();
            Bitmap smallMarker = Bitmap.createScaledBitmap(b, 185, 130, false);

            BitmapDrawable bitmapDrawable_pick = (BitmapDrawable) getResources().getDrawable(R.drawable.pickupnewicone);
            Bitmap b_pick = bitmapDrawable_pick.getBitmap();
            Bitmap smallMarker_pick = Bitmap.createScaledBitmap(b_pick, 110, 110, false);

          LatLng  newLatLng1 = new LatLng(sourceBean.getDLatitude(), sourceBean.getDLongitude());
          LatLng  newLatLng2 = new LatLng(destinationBean.getDLatitude(), destinationBean.getDLongitude());
            // MarkerOptions pickupMarker = new MarkerOptions().position(newLatLng1).icon(BitmapDescriptorFactory.fromResource(R.drawable.pickupnewicone));
            MarkerOptions pickupMarker = new MarkerOptions().position(newLatLng1).icon(BitmapDescriptorFactory.fromBitmap(smallMarker_pick));
            MarkerOptions destinationMarker = new MarkerOptions().position(newLatLng2).icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
            mMap.addMarker(pickupMarker);
            mMap.addMarker(destinationMarker);
            fetchPolyPoints(true,false);
            mapAutoZoom();
        }
        if(bikesLocation==null)
            bikeLoca=new ArrayList<>();

        if(bikeLoca.size()>0) {
            Bitmap smallMarker = resizeOnTropMarker(1) ;

            for (int i=0;i<bikeLoca.size();i++){
                MarkerOptions carMarkerBike = new MarkerOptions().position(new LatLng(bikeLoca.get(i).getLatitude(), bikeLoca.get(i).getLongitute())).icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
                mMap.addMarker(carMarkerBike);


            }
        }else{
            // Toast.makeText(getApplicationContext(),"No carslocation found",Toast.LENGTH_LONG).show();
        }

    }

    private void populateOnlyCarLocation(ArrayList<Car> onlyCarLoca,int req_type) {

        mMap.clear();
        LatLng carLatLng =null;
        if (sourceBean != null && destinationBean != null && checkLocationName()) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_destination_new);
            Bitmap b = bitmapDrawable.getBitmap();
            Bitmap smallMarker = Bitmap.createScaledBitmap(b, 185, 130, false);

            BitmapDrawable bitmapDrawable_pick = (BitmapDrawable) getResources().getDrawable(R.drawable.pickupnewicone);
            Bitmap b_pick = bitmapDrawable_pick.getBitmap();
            Bitmap smallMarker_pick = Bitmap.createScaledBitmap(b_pick, 110, 110, false);

           LatLng newLatLng1 = new LatLng(sourceBean.getDLatitude(), sourceBean.getDLongitude());
           LatLng newLatLng2 = new LatLng(destinationBean.getDLatitude(), destinationBean.getDLongitude());
            // MarkerOptions pickupMarker = new MarkerOptions().position(newLatLng1).icon(BitmapDescriptorFactory.fromResource(R.drawable.pickupnewicone));
            MarkerOptions pickupMarker = new MarkerOptions().position(newLatLng1).icon(BitmapDescriptorFactory.fromBitmap(smallMarker_pick));
            MarkerOptions destinationMarker = new MarkerOptions().position(newLatLng2).icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
            mMap.addMarker(pickupMarker);
            mMap.addMarker(destinationMarker);
            fetchPolyPoints(true,false);
        }
        if(onlyCarLoca.size()>0) {
            Bitmap smallMarker = resizeOnTropMarker(3) ;

            for (int i=0;i<onlyCarLoca.size();i++){
                MarkerOptions carMarkerBike = new MarkerOptions().position(new LatLng(onlyCarLoca.get(i).getLatitude(), onlyCarLoca.get(i).getLongitute())).icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
                mMap.addMarker(carMarkerBike);


            }
        }else{
            // Toast.makeText(getApplicationContext(),"No carslocation found",Toast.LENGTH_LONG).show();
        }

    }

    private void populateAmbulaLocation(ArrayList<Car> ambulaLoca,int req_type) {
        mMap.clear();
        LatLng carLatLng =null;
        if (sourceBean != null && destinationBean != null && checkLocationName()) {

            BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_destination_new);
            Bitmap b = bitmapDrawable.getBitmap();
            Bitmap smallMarker = Bitmap.createScaledBitmap(b, 185, 130, false);

            BitmapDrawable bitmapDrawable_pick = (BitmapDrawable) getResources().getDrawable(R.drawable.pickupnewicone);
            Bitmap b_pick = bitmapDrawable_pick.getBitmap();
            Bitmap smallMarker_pick = Bitmap.createScaledBitmap(b_pick, 110, 110, false);
            LatLng newLatLng1 = new LatLng(sourceBean.getDLatitude(), sourceBean.getDLongitude());
            LatLng newLatLng2 = new LatLng(destinationBean.getDLatitude(), destinationBean.getDLongitude());
            // MarkerOptions pickupMarker = new MarkerOptions().position(newLatLng1).icon(BitmapDescriptorFactory.fromResource(R.drawable.pickupnewicone));
            MarkerOptions pickupMarker = new MarkerOptions().position(newLatLng1).icon(BitmapDescriptorFactory.fromBitmap(smallMarker_pick));
            MarkerOptions destinationMarker = new MarkerOptions().position(newLatLng2).icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
            mMap.addMarker(pickupMarker);
            mMap.addMarker(destinationMarker);
            fetchPolyPoints(true,false);
        }

        if(ambulaLoca.size()>0) {
            Bitmap smallMarker = resizeOnTropMarker(4) ;

            for (int i=0;i<ambulaLoca.size();i++){
                MarkerOptions carMarkerBike = new MarkerOptions().position(new LatLng(ambulaLoca.get(i).getLatitude(), ambulaLoca.get(i).getLongitute())).icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
                mMap.addMarker(carMarkerBike);
            }
        }else{
            // Toast.makeText(getApplicationContext(),"No carslocation found",Toast.LENGTH_LONG).show();
        }

    }
    boolean carsloconce=true;
    private void populateCarsLocation(ArrayList<Car> carsLocation,int req_type) {
        if(carsLocation.size()>0 && carsloconce) {
            Bitmap smallMarker ;

            for (int i=0;i<carsLocation.size();i++){

                    smallMarker = resizeOnTropMarker(carsLocation.get(i).getCar_type());
                    MarkerOptions carMarkerAll = new MarkerOptions().position(new LatLng(carsLocation.get(i).getLatitude(), carsLocation.get(i).getLongitute())).icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
                   //  Double d =carsLocation.get(i).getLatitude();
                  //   String s = d.toString();
                   //  Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                    mMap.addMarker(carMarkerAll);

            }
            carsloconce=false;
        }else{
          //  Toast.makeText(getApplicationContext(),"No carslocation found",Toast.LENGTH_LONG).show();
        }

    }

    private boolean checkPlayServices() {

        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int result = googleApiAvailability.isGooglePlayServicesAvailable(this);
        if (result != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(result)) {
                googleApiAvailability.getErrorDialog(this, result,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }
            return false;
        }
        return true;
    }

    private void getData() {
        if (App.isNetworkAvailable()) {
            //  fetchLandingPageDetails();
        } else {
            setProgressScreenVisibility(true, false);
            Snackbar.make(coordinatorLayout, AppConstants.NO_NETWORK_AVAILABLE, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.btn_retry, snackBarRefreshOnClickListener).show();
        }
    }

    public void initViews() {


        //info tv
        bikeInfoTv =findViewById(R.id.bikeInfoTv);
        cngInfoTV =findViewById(R.id.cngInfoTv);
        carInfoTv =findViewById(R.id.carInfoTv);
        ambulanceInfoTv=findViewById(R.id.ambulanceInfoTv);
        hirecarInfoTv=findViewById(R.id.hirecarInfoTv);
        premioInfoTv =findViewById(R.id.premioInfoTvLP);
        noahInfoTv=findViewById(R.id.noahInfoTvLP);
        hiaceInfoTv=findViewById(R.id.hiaceInfoTvLP);



        btnRequest = (Button) findViewById(R.id.btn_request);
        //init laouts

        //confirm Bike START
        bikeLinearLayout        = findViewById(R.id.confirm_request_bike_ll);
        bikeRequestImageView    = findViewById(R.id.confirm_request_bike_imv);
        bikeFareTextView        = findViewById(R.id.confirm_request_bike_fare_tv);
        bikeInfoImageButton     = findViewById(R.id.confirm_request_bike_info_imbtn);
        bikeRequestBtn          = findViewById(R.id.btn_request_for_bike);



        //END
        //CNG Start cngLinearLayout

        cngLinearLayout        = findViewById(R.id.confirm_request_cng_ll);
        cngRequestImageView    = findViewById(R.id.confirm_request_cng_imv);
        cngFareTextView        = findViewById(R.id.confirm_request_cng_fare_tv);
        cngInfoImageButton     = findViewById(R.id.confirm_request_cng_info_imbtn);
        cngRequestBtn          = findViewById(R.id.btn_request_for_cng);
        //end

        //confirm CAR START
        carLinearLayout         = findViewById(R.id.confirm_request_car_ll);
        carRequestImageView     = findViewById(R.id.confirm_request_car_imv);
        carFareTextView         = findViewById(R.id.confirm_request_car_fare_tv);
        carInfoImageButton      = findViewById(R.id.confirm_request_ca_info_imbtn);
        carRequestBtn           = findViewById(R.id.btn_request_for_car);
        //END Car

        //Start Ambulance
        ambulanceLinearLayout   =   findViewById(R.id.confirm_request_ambulance_ll);
        ambulanceRequestImageView   =   findViewById(R.id.confirm_request_ambulance_imv);
        ambulanceFareTextView   =   findViewById(R.id.confirm_request_ambulance_fare_tv);
        ambulanceRequestBtn     =   findViewById(R.id.btn_request_for_ambulance);

        //Start One Hour Car
        oneHourLinearLayout         = findViewById(R.id.confirm_request_car_one_hour_ll);
        carOneHourRequestImageView  = findViewById(R.id.confirm_request_car_one_hour_imv);
        oneHourFareTextView         = findViewById(R.id.confirm_request_car_one_hour_fare_tv);
        oneHourInfoImageButton      = findViewById(R.id.confirm_request_car_one_hour_info_imbtn);
        carOneHourRequestBtn        = findViewById(R.id.btn_request_for_car_one_hour);
        //End

        // Start Two Hours
        twoHoursLinearLayout        = findViewById(R.id.confirm_request_car_two_hours_ll);
        carTwoHoursRequestImageView = findViewById(R.id.confirm_request_car_two_hours_imv);
        twoHoursFareTextView        = findViewById(R.id.confirm_request_car_two_hour_fare_tv);
        twoHourInfoImageButton      = findViewById(R.id.confirm_request_two_hours_info_imbtn);
        carTwoHoursRequestBtn       = findViewById(R.id.btn_request_for_car_two_hours);
        //END

        // Start Four Hours
        fourHoursLinearLayout           = findViewById(R.id.confirm_request_car_four_hours_ll);
        carFourHoursRequestImageView    = findViewById(R.id.confirm_four_hours_imv);
        fourHoursFareTextView           = findViewById(R.id.confirm_request_car_four_hours_fare_tv);
        fourHourInfoImageButton         = findViewById(R.id.confirm_request_car_four_hours_info_imbtn);
        carFourHoursRequestBtn          = findViewById(R.id.btn_request_for_car_four_hours);
        //End

        // START ONE DAY For Premio

        dayLinearLayout             = findViewById(R.id.confirm_request_car_day_ll);
        carDayPrimioRequestImageView  = findViewById(R.id.confirm_request_car_day_imv);
        dayFareTextView             = findViewById(R.id.confirm_request_car_day_fare_tv);
        dayInfoImageButton          = findViewById(R.id.confirm_request_ca_day_info_imbtn);
        carDayPrimioRequestBtn      = findViewById(R.id.btn_request_for_car_one_day_primio);
        // Noah
        dayNoahLinearLayout         =  findViewById(R.id.confirm_request_car_day_noah_ll);
        carDayNoahRequestImageView  =  findViewById(R.id.confirm_request_car_day_noah_imv);
        dayNoahFareTextView         =  findViewById(R.id.confirm_request_car_day_noah_fare_tv);
        carDayNoahRequestBtn        =  findViewById(R.id.btn_request_for_car_one_day_noah);

        // Hice
        dayHiaceLinearLayout    =  findViewById(R.id.confirm_request_car_day_hiace_brand_ll);
        carDayHiceRequestImageView  = findViewById(R.id.confirm_request_car_day_hiace_imv);
        dayHiaceFareTextView        = findViewById(R.id.confirm_request_car_day_hiace_fare_tv);
        carDayHiceRequestBtn        = findViewById(R.id.btn_request_for_car_one_day_hiace);

        //Bike LinearLayout Onclick
        bikeLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bikesLocation!=null)
                populateBikeLocation(bikesLocation,1);
                //  bikeRequestImageView.setBackground(getResources(R.drawable.bg_round_edges_black));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    //selected
                    bikeRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_black));
                    cngRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    carRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    ambulanceRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    carOneHourRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    carTwoHoursRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    carFourHoursRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    carDayPrimioRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    carDayNoahRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    carDayHiceRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));

                }
                bikeRequestBtn.setVisibility(View.VISIBLE);
                cngRequestBtn.setVisibility(View.GONE);
                btnRequest.setVisibility(View.GONE);
                carRequestBtn.setVisibility(View.GONE);
                ambulanceRequestBtn.setVisibility(View.GONE);
                carOneHourRequestBtn.setVisibility(View.GONE);
                carTwoHoursRequestBtn.setVisibility(View.GONE);
                carFourHoursRequestBtn.setVisibility(View.GONE);
                carDayPrimioRequestBtn.setVisibility(View.GONE);
                carDayNoahRequestBtn.setVisibility(View.GONE);
                carDayHiceRequestBtn.setVisibility(View.GONE);
            }
        });

        //cngLinearLayout Onclick
        cngLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // if(bikesLocation!=null)
                  //  populateBikeLocation(bikesLocation,1);
                //  bikeRequestImageView.setBackground(getResources(R.drawable.bg_round_edges_black));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {

                    bikeRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    //selected
                    cngRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_black));
                    carRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    ambulanceRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    carOneHourRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    carTwoHoursRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    carFourHoursRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    carDayPrimioRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    carDayNoahRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    carDayHiceRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));

                }

                bikeRequestBtn.setVisibility(View.GONE);
                cngRequestBtn.setVisibility(View.VISIBLE);
                btnRequest.setVisibility(View.GONE);
                carRequestBtn.setVisibility(View.GONE);
                ambulanceRequestBtn.setVisibility(View.GONE);
                carOneHourRequestBtn.setVisibility(View.GONE);
                carTwoHoursRequestBtn.setVisibility(View.GONE);
                carFourHoursRequestBtn.setVisibility(View.GONE);
                carDayPrimioRequestBtn.setVisibility(View.GONE);
                carDayNoahRequestBtn.setVisibility(View.GONE);
                carDayHiceRequestBtn.setVisibility(View.GONE);
            }
        });

        //Car LinearLayout Onclick
        carLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onlyCarsLocation!=null) {
                    populateOnlyCarLocation(onlyCarsLocation, 3);
                }
                //  bikeRequestImageView.setBackground(getResources(R.drawable.bg_round_edges_black));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    bikeRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    cngRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    //Sellected
                    carRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_black));
                    ambulanceRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    carOneHourRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    carTwoHoursRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    carFourHoursRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    carDayPrimioRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    carDayNoahRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    carDayHiceRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));


                }


                bikeRequestBtn.setVisibility(View.GONE);
                cngRequestBtn.setVisibility(View.GONE);
                btnRequest.setVisibility(View.GONE);
                //selected
                carRequestBtn.setVisibility(View.VISIBLE);
                ambulanceRequestBtn.setVisibility(View.GONE);
                carOneHourRequestBtn.setVisibility(View.GONE);
                carTwoHoursRequestBtn.setVisibility(View.GONE);
                carFourHoursRequestBtn.setVisibility(View.GONE);
                carDayPrimioRequestBtn.setVisibility(View.GONE);
                carDayNoahRequestBtn.setVisibility(View.GONE);
                carDayHiceRequestBtn.setVisibility(View.GONE);
            }
        });

        //Ambulance click LinearLayout
        ambulanceLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ambulanceLocation!=null) {
                    populateAmbulaLocation(ambulanceLocation, 4);
                }
                //  bikeRequestImageView.setBackground(getResources(R.drawable.bg_round_edges_black));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    bikeRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    cngRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    carRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    //selected
                    ambulanceRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_black));
                    carOneHourRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    carTwoHoursRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    carFourHoursRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    carDayPrimioRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    carDayNoahRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    carDayHiceRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));

                }


                bikeRequestBtn.setVisibility(View.GONE);
                cngRequestBtn.setVisibility(View.GONE);
                btnRequest.setVisibility(View.GONE);
                carRequestBtn.setVisibility(View.GONE);
                //selected
                ambulanceRequestBtn.setVisibility(View.VISIBLE);
                carOneHourRequestBtn.setVisibility(View.GONE);
                carTwoHoursRequestBtn.setVisibility(View.GONE);
                carFourHoursRequestBtn.setVisibility(View.GONE);
                carDayPrimioRequestBtn.setVisibility(View.GONE);
                carDayNoahRequestBtn.setVisibility(View.GONE);
                carDayHiceRequestBtn.setVisibility(View.GONE);
            }
        });

        //car one hour LinearLayout Onclick
        oneHourLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onlyCarsLocation!=null) {
                    populateOnlyCarLocation(onlyCarsLocation, 3);
                }
                //  bikeRequestImageView.setBackground(getResources(R.drawable.bg_round_edges_black));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    bikeRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    cngRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    carRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    ambulanceRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    //selected
                    carOneHourRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_black));
                    carTwoHoursRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    carFourHoursRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    carDayPrimioRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    carDayNoahRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    carDayHiceRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));

                }


                bikeRequestBtn.setVisibility(View.GONE);
                cngRequestBtn.setVisibility(View.GONE);
                btnRequest.setVisibility(View.GONE);
                carRequestBtn.setVisibility(View.GONE);
                ambulanceRequestBtn.setVisibility(View.GONE);
                //selected
                carOneHourRequestBtn.setVisibility(View.VISIBLE);
                carTwoHoursRequestBtn.setVisibility(View.GONE);
                carFourHoursRequestBtn.setVisibility(View.GONE);
                carDayPrimioRequestBtn.setVisibility(View.GONE);
                carDayNoahRequestBtn.setVisibility(View.GONE);
                carDayHiceRequestBtn.setVisibility(View.GONE);
            }
        });

        //Bike LinearLayout Onclick
        twoHoursLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  bikeRequestImageView.setBackground(getResources(R.drawable.bg_round_edges_black));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    bikeRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    cngRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    carRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    ambulanceRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    carOneHourRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    //selected
                    carTwoHoursRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_black));
                    carFourHoursRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    carDayPrimioRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    carDayNoahRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    carDayHiceRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));

                }
                bikeRequestBtn.setVisibility(View.GONE);
                cngRequestBtn.setVisibility(View.GONE);
                btnRequest.setVisibility(View.GONE);
                carRequestBtn.setVisibility(View.GONE);
                ambulanceRequestBtn.setVisibility(View.GONE);
                carOneHourRequestBtn.setVisibility(View.GONE);
                //Selectes
                carTwoHoursRequestBtn.setVisibility(View.VISIBLE);
                carFourHoursRequestBtn.setVisibility(View.GONE);
                carDayPrimioRequestBtn.setVisibility(View.GONE);
                carDayNoahRequestBtn.setVisibility(View.GONE);
                carDayHiceRequestBtn.setVisibility(View.GONE);
            }
        });

        //Bike LinearLayout Onclick
        fourHoursLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  bikeRequestImageView.setBackground(getResources(R.drawable.bg_round_edges_black));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    bikeRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    cngRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    carRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    ambulanceRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    carOneHourRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    carTwoHoursRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    //selected
                    carFourHoursRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_black));
                    carDayPrimioRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    carDayNoahRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    carDayHiceRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));

                }
                bikeRequestBtn.setVisibility(View.GONE);
                cngRequestBtn.setVisibility(View.GONE);
                btnRequest.setVisibility(View.GONE);
                carRequestBtn.setVisibility(View.GONE);
                ambulanceRequestBtn.setVisibility(View.GONE);
                carOneHourRequestBtn.setVisibility(View.GONE);
                carTwoHoursRequestBtn.setVisibility(View.GONE);
                //selected
                carFourHoursRequestBtn.setVisibility(View.VISIBLE);
                carDayPrimioRequestBtn.setVisibility(View.GONE);
                carDayNoahRequestBtn.setVisibility(View.GONE);
                carDayHiceRequestBtn.setVisibility(View.GONE);
            }
        });

        //premio LinearLayout Onclick
        dayLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onlyCarsLocation!=null) {
                    populateOnlyCarLocation(onlyCarsLocation, 3);
                }
                //  bikeRequestImageView.setBackground(getResources(R.drawable.bg_round_edges_black));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    bikeRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    cngRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    carRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    ambulanceRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    carOneHourRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    carTwoHoursRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    carFourHoursRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    //selected
                    // carDayImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_black));
                    //selected
                    carDayPrimioRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_black));
                    carDayNoahRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    carDayHiceRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));

                }



                bikeRequestBtn.setVisibility(View.GONE);
                cngRequestBtn.setVisibility(View.GONE);
                btnRequest.setVisibility(View.GONE);
                carRequestBtn.setVisibility(View.GONE);
                ambulanceRequestBtn.setVisibility(View.GONE);
                carOneHourRequestBtn.setVisibility(View.GONE);
                carTwoHoursRequestBtn.setVisibility(View.GONE);
                carFourHoursRequestBtn.setVisibility(View.GONE);
                //selected
                carDayPrimioRequestBtn.setVisibility(View.VISIBLE);
                carDayNoahRequestBtn.setVisibility(View.GONE);
                carDayHiceRequestBtn.setVisibility(View.GONE);
            }
        });
        dayNoahLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onlyCarsLocation!=null) {
                    populateOnlyCarLocation(onlyCarsLocation, 3);
                }
                //  bikeRequestImageView.setBackground(getResources(R.drawable.bg_round_edges_black));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    bikeRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    cngRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    carRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    ambulanceRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    carOneHourRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    carTwoHoursRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    carFourHoursRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    //  carDayImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));

                    carDayPrimioRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    //selected
                    carDayNoahRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_black));
                    carDayHiceRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));

                }


                bikeRequestBtn.setVisibility(View.GONE);
                cngRequestBtn.setVisibility(View.GONE);
                btnRequest.setVisibility(View.GONE);
                carRequestBtn.setVisibility(View.GONE);
                ambulanceRequestBtn.setVisibility(View.GONE);
                carOneHourRequestBtn.setVisibility(View.GONE);
                carTwoHoursRequestBtn.setVisibility(View.GONE);
                carFourHoursRequestBtn.setVisibility(View.GONE);
                carDayPrimioRequestBtn.setVisibility(View.GONE);
                //selected
                carDayNoahRequestBtn.setVisibility(View.VISIBLE);
                carDayHiceRequestBtn.setVisibility(View.GONE);
            }
        });
        dayHiaceLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onlyCarsLocation!=null) {
                    populateOnlyCarLocation(onlyCarsLocation, 3);
                }
                //  bikeRequestImageView.setBackground(getResources(R.drawable.bg_round_edges_black));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    bikeRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    cngRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    carRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    ambulanceRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    carOneHourRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    carTwoHoursRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    carFourHoursRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    //  carDayImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    //selected
                    carDayPrimioRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));

                    carDayNoahRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
                    //selected
                    carDayHiceRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_black));

                }

                bikeRequestBtn.setVisibility(View.GONE);
                cngRequestBtn.setVisibility(View.GONE);
                btnRequest.setVisibility(View.GONE);
                carRequestBtn.setVisibility(View.GONE);
                ambulanceRequestBtn.setVisibility(View.GONE);
                carOneHourRequestBtn.setVisibility(View.GONE);
                carTwoHoursRequestBtn.setVisibility(View.GONE);
                carFourHoursRequestBtn.setVisibility(View.GONE);
                carDayPrimioRequestBtn.setVisibility(View.GONE);
                //selected
                carDayNoahRequestBtn.setVisibility(View.GONE);
                carDayHiceRequestBtn.setVisibility(View.VISIBLE);
            }
        });

        //start info button click event

        bikeInfoTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pricePolicyDialog = new PricePolicyDialog(LandingPageActivity.this);
                pricePolicyDialog.showBikePricePolicy();
            }
        });

        cngInfoTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pricePolicyDialog = new PricePolicyDialog(LandingPageActivity.this);
                pricePolicyDialog.showCngPricePolicy();
            }
        });
        carInfoTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pricePolicyDialog = new PricePolicyDialog(LandingPageActivity.this);
                pricePolicyDialog.showCarPricePolicy();
            }
        });
        ambulanceInfoTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pricePolicyDialog = new PricePolicyDialog(LandingPageActivity.this);
                pricePolicyDialog.showAmbulancePricePolicy();
            }
        });

        hirecarInfoTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pricePolicyDialog = new PricePolicyDialog(LandingPageActivity.this);
                pricePolicyDialog.showHirecarPricePolicy();
            }
        });

        premioInfoTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pricePolicyDialog = new PricePolicyDialog(LandingPageActivity.this);
                pricePolicyDialog.showPremioPricePolicy();
            }
        });
        noahInfoTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pricePolicyDialog = new PricePolicyDialog(LandingPageActivity.this);
                pricePolicyDialog.showNoahPricePolicy();
            }
        });
        hiaceInfoTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pricePolicyDialog = new PricePolicyDialog(LandingPageActivity.this);
                pricePolicyDialog.showHiacePricePolicy();
            }
        });



        snackBarRefreshOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                //  mVibrator.vibrate(25);
                getData();
            }
        };

        // btnRequest = (Button) findViewById(R.id.btn_request);

        //    rlFare = (RelativeLayout) findViewById(R.id.rl_fare);
        happyyFareWithService = findViewById(R.id.ll_fare_new);

        coordinatorLayout.removeView(toolbar);
//      toolbar.setVisibility(View.GONE);
        toolbarHome = (Toolbar) getLayoutInflater().inflate(R.layout.toolbar_landing_page, toolbar);
        coordinatorLayout.addView(toolbarHome, 0);
        setSupportActionBar(toolbarHome);


        //   rvCarTypes = (RecyclerView) findViewById(R.id.rv_bottom_sheet_landing_car_types);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        //  rvCarTypes.setLayoutManager(layoutManager);

        ivBottomMarker = (ImageView) findViewById(R.id.iv_bottom_marker);
        destinationTV   = findViewById(R.id.dest_top_part);


        llConfirmationProgress = (LinearLayout) findViewById(R.id.ll_confirmation_progress);

        //  txtFareEstimate = (TextView) findViewById(R.id.txt_fare_estimate);
        //  txtTo = (TextView) findViewById(R.id.txt_to);
        // llDestinationEstimated = (LinearLayout) findViewById(R.id.ll_destination_estimated);

        carOneImage = (ImageView) findViewById(R.id.iv_car_one);
        carTwoImage = (ImageView) findViewById(R.id.iv_car_two);
        carThreeImage = (ImageView) findViewById(R.id.iv_car_three);
        carFourImage = (ImageView) findViewById(R.id.iv_car_four);

        txtCarOne = (TextView) findViewById(R.id.txt_la_go);
        txtCarTwo = (TextView) findViewById(R.id.txt_la_x);
        txtCarThree = (TextView) findViewById(R.id.txt_la_xl);
        carFour = (TextView) findViewById(R.id.txt_la_xll);

        //  txtCarArrivalEstimatedTime = (TextView) findViewById(R.id.txt_min_time);

//        ivActionSearch = (ImageView) toolbarHome.findViewById(R.id.ic_action_search);

        // txtCarAvailability = (TextView) findViewById(R.id.txt_cars_available);
        txtSource = (TextView) findViewById(R.id.txt_source);
        txtDestination = (TextView) findViewById(R.id.txt_destination);

        txtTime = (TextView) findViewById(R.id.txt_time);
        txtMaxSize = (TextView) findViewById(R.id.txt_max_size);
        txtFare = (TextView) findViewById(R.id.txt_fare);
        txtEstimatedDestination = (TextView) findViewById(R.id.txt_estimated_destination);

        cvConfirmationPage = (CardView) findViewById(R.id.cv_confirmation_page);

        txtEstimatedFare = (TextView) findViewById(R.id.txt_estimated_fare);

        llProgressBar = (LinearLayout) findViewById(R.id.ll_landing_progress_bar);
        llEstimation = (LinearLayout) findViewById(R.id.ll_estimation);
        llFare = (LinearLayout) findViewById(R.id.ll_fare);

        destination_select_fl = (FrameLayout) findViewById(R.id.destination_select_fl);
        pickup_select_marker_imv = findViewById(R.id.pickup_select_marker);
        destination_select_marker=findViewById(R.id.destination_select_marker);

        // framePickup = (FrameLayout) findViewById(R.id.frame_pickup_landing_page);
        //  ivMarker = (ImageView) findViewById(R.id.iv_marker);

        llLandingBottomBar = (LinearLayout) findViewById(R.id.ll_landing_estimation_bottom_sheet);
        ivLocationButton = (FloatingActionButton) findViewById(R.id.fab_location_button);
        distinationConfirmButton = findViewById(R.id.distinationconfirm);


        txtActionSearch = (TextView) toolbarHome.findViewById(R.id.txt_action_search);

        txtTotalFare = (TextView) findViewById(R.id.txt_total_fare);

        viewDottedLine = (View) findViewById(R.id.view_dotted_line);
/*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            txtActionSearch.setCompoundDrawablesRelative(null,null,null,null);
        }
*/

        llConfirmation = (LinearLayout) findViewById(R.id.ll_confirmation);
        btnRequest = (Button) findViewById(R.id.btn_request);

        txtFareLabel = (TextView) findViewById(R.id.txt_fare_lable);

        //   setBottomSheetBehavior();
/*
        param1 = flLandingPage.getLayoutParams();
        param1.height = (int) (height - getStatusBarHeight() - mActionBarHeight);
        Log.i(TAG, "onSlide: PAram Height : " + param1.height);
        flLandingPage.setLayoutParams(param1);

        */
        ivLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                //mVibrator.vibrate(25);
                moveToCurrentLocation();

                if (mGoogleApiClient == null || mGoogleApiClient.isConnected() || !mGoogleApiClient.isConnecting()) {
                    getCurrentLocation();

                } else {
                    mGoogleApiClient.connect();
                }

            }
        });
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionCheckCompleted(int requestCode, boolean isPermissionGranted) {

                if (requestCode == REQUEST_PERMISSIONS_LOCATION & isPermissionGranted) {

                    Log.i(TAG, "onPermissionCheckCompleted: PERMISSION GRANTED !!!!");
                    if (checkPlayServices()) {
                        getCurrentLocation();
                    }
                }

            }
        };

        setPermissionListener(permissionListener);

    }
    String massage="";
    FareCalculation fareCalculation = new FareCalculation();
    LandingPageBean carInfoBean = null;
    ArrayList<CarBean> carBeanArrayList =null;

    public boolean checkLocationName(){
        if (txtSource.getText().toString().equals("") || txtSource.getText().toString().equals("Loading...")){
            massage ="Please select source";
            Toast.makeText(this,massage,Toast.LENGTH_LONG).show();
            return false;

        }
        if (txtDestination.getText().toString().equals("") || txtDestination.getText().toString().equals("Loading...") ){
            massage ="Please select destination";
            Toast.makeText(this,massage,Toast.LENGTH_LONG).show();
            return false;

        }
        return true;
    }

    public void onClickDoneButton(View view){
        doneProgressBar.setVisibility(View.VISIBLE);

        if(carsLocation==null) {
            carsLocation = new ArrayList<>();
        }else {
            if(carsLocation.size()>0){
                bikesLocation =new ArrayList<>();
                onlyCarsLocation = new ArrayList<>();
                ambulanceLocation = new ArrayList<>();
                for (int i=0;i<carsLocation.size();i++){
                    if(carsLocation.get(i).getCar_type()==1){
                        bikesLocation.add(carsLocation.get(i));
                    }
                    if(carsLocation.get(i).getCar_type()==3){
                        onlyCarsLocation.add(carsLocation.get(i));
                    }
                    if(carsLocation.get(i).getCar_type()==4){
                        ambulanceLocation.add(carsLocation.get(i));
                    }
                }
            }else {
                bikesLocation =new ArrayList<>();
                onlyCarsLocation = new ArrayList<>();
                ambulanceLocation = new ArrayList<>();
            }
        }




        if (sourceBean != null && destinationBean != null && checkLocationName()) {


           // newLatLng1 = new LatLng(sourceBean.getDLatitude(), sourceBean.getDLongitude());
           // newLatLng2 = new LatLng(destinationBean.getDLatitude(), destinationBean.getDLongitude());
            mMap.clear();
            mMap.isMyLocationEnabled();
           // MarkerOptions pickupMarker = new MarkerOptions().position(newLatLng1).icon(BitmapDescriptorFactory.fromResource(R.drawable.pickupnewicone));
          //  MarkerOptions pickupMarker = new MarkerOptions().position(newLatLng1).icon(BitmapDescriptorFactory.fromBitmap(smallMarker_pick));
           // MarkerOptions destinationMarker = new MarkerOptions().position(newLatLng2).icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
           // mMap.addMarker(pickupMarker);
          //  mMap.addMarker(destinationMarker);


            //view
            pickup_select_marker_imv.setVisibility(View.GONE);
            destination_select_marker.setVisibility(View.GONE);
            distinationConfirmButton.setVisibility(View.GONE);
            fetchPolyPoints(true,true);
            populateBikeLocation(bikesLocation,1);
            bikeDefault();
        }else {
            doneProgressBar.setVisibility(View.GONE);
        }


    }

    public void bikeDefault(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            //selected
            bikeRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_black));
            cngRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
            carRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
            ambulanceRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
            carOneHourRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
            carTwoHoursRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
            carFourHoursRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
            carDayPrimioRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
            carDayNoahRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));
            carDayHiceRequestImageView.setBackground(getResources().getDrawable(R.drawable.bg_round_edges_gray));

        }
        bikeRequestBtn.setVisibility(View.VISIBLE);
        cngRequestBtn.setVisibility(View.GONE);
        btnRequest.setVisibility(View.GONE);
        carRequestBtn.setVisibility(View.GONE);
        ambulanceRequestBtn.setVisibility(View.GONE);
        carOneHourRequestBtn.setVisibility(View.GONE);
        carTwoHoursRequestBtn.setVisibility(View.GONE);
        carFourHoursRequestBtn.setVisibility(View.GONE);
        carDayPrimioRequestBtn.setVisibility(View.GONE);
        carDayNoahRequestBtn.setVisibility(View.GONE);
        carDayHiceRequestBtn.setVisibility(View.GONE);
    }

    private BitmapDescriptor getMarkerIconFromDrawable(Drawable drawable) {
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, (int)getResources().getDimension(R.dimen.dest_width), (int)getResources().getDimension(R.dimen.dest_height));
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    Marker markerCar;
    DriverBean driverBean=new DriverBean();
    ArrayList<CarBean> availableCarsList = new ArrayList<>();
    private void onPlotDriverCar() {

        try {
//           mMap.addMarker(new MarkerOptions().position(newLatLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_driver_car_landing_page)));

            BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_driver_details_car);
            Bitmap b = bitmapDrawable.getBitmap();
            Bitmap smallMarker = Bitmap.createScaledBitmap(b, 50, 50, false);

            if (mMap != null) {
                if (markerCar == null) {
                    markerCar = mMap.addMarker(new MarkerOptions()
                            .position(driverBean.getCarLatLng())
                            .icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
                            .flat(true));
                } else {
                    markerCar.setPosition(driverBean.getCarLatLng());
                }
                mapAutoZoom();
                fetchPolyPoints(true,false);
            }


        } catch (NumberFormatException e) {
            e.printStackTrace();

        }
    }



    public void setBottomSheetBehavior() {

        bottomSheetBehavior = BottomSheetBehavior.from(llLandingBottomBar);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

                /*if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_DRAGGING

                bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_SETTLING){
                    param = myMapFragment.getView().getLayoutParams();
                    param.height = (int) (height - getStatusBarHeight() - mActionBarHeight - bottomSheet.getHeight());
                    Log.i(TAG, "onSlide: PAram Height : " + param.height);
                    myMapFragment.getView().setLayoutParams(param);
                } else if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    param = myMapFragment.getView().getLayoutParams();
                    param.height = (int) (height - getStatusBarHeight() - mActionBarHeight - bottomSheet.getHeight());
                    Log.i(TAG, "onSlide: PAram Height : " + param.height);
                    myMapFragment.getView().setLayoutParams(param);
                } else if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    param = myMapFragment.getView().getLayoutParams();
                    param.height = (int) (height - getStatusBarHeight() - mActionBarHeight - bottomSheet.getHeight());
                    Log.i(TAG, "onSlide: PAram Height : " + param.height);
                    myMapFragment.getView().setLayoutParams(param);
                }/*/
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                Log.i(TAG, "onSlide: offset : " + slideOffset);
// mapFragmentView.animate().scaleX(1 - slideOffset).scaleY(1 - slideOffset).setDuration(0).start();

                try {

                    //  param = mapFragment.getView().getLayoutParams();
                    //   param.height = (int) (height - getStatusBarHeight() - mActionBarHeight/* - (80 * px * (1 - slideOffset))*/ - bottomSheet.getHeight() * (slideOffset));
//                Log.i(TAG, "onSlide: PAram Height : " + param.height);
                    //  mapFragment.getView().setLayoutParams(param);

                    //   param1 = flLandingPage.getLayoutParams();
                    //  param1.height = (int) (height - getStatusBarHeight() - mActionBarHeight /*- (80 * px * (1 - slideOffset))*/ - bottomSheet.getHeight() * (slideOffset));
                    //  Log.i(TAG, "onSlide: PAram Height : " + param1.height);
                    // flLandingPage.setLayoutParams(param1);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initMap() {
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_home_map);


        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
               // mMap.setPadding(0, (int) ((100 * px) + mActionBarHeight + getStatusBarHeight()), 0, (int) (100 * px));
                mMap.setPadding(0, 0, 0, 0);



                initMapLoad();
                //  getCurrentLocation();

            }
        });
    }
    LatLng newLatLng=null;
    int onfirstLoad=0;
    private void initMapLoad() {

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
           getCurrentLocation();
        }

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                mMap.setMaxZoomPreference(20f);

            }
        });

        mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {

            @Override
            public void onCameraMove() {


                if (happyyFareWithService.getVisibility()!=View.VISIBLE) {
                    /*
                    switch (click_pick_or_des){
                        case 1:
                            pickup_select_marker_imv.setVisibility(View.VISIBLE);
                            destination_select_marker.setVisibility(View.GONE);

                            break;
                        case 2:
                            pickup_select_marker_imv.setVisibility(View.GONE);
                            destination_select_marker.setVisibility(View.VISIBLE);
                            break;
                    }
                    */

                    isCameraMoved = true;
                    mMap.setMaxZoomPreference(18f); //15 prev...
                }
            }
        });


        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                boolean isInDhakaCityB=false;
                boolean isInDhakaOr=false;
                CameraPosition postion = mMap.getCameraPosition();
                LatLng center = postion.target;
                Log.i(TAG, "onCameraIdle: GetLocationName Called : " + center);
                isInDhakaCityB=isInDhakaCity(center.latitude,center.longitude);

                if(happyyFareWithService.getVisibility()!=View.VISIBLE) {

                    switch (click_pick_or_des) {
                        case 1:
                           if(sourceBean==null)
                               sourceBean = new PlaceBean();
                            if(isCameraMoved) {
                                if(isInDhakaCityB) {
                                    outOfDhakaServImBtn.setVisibility(View.GONE);
                                    sourceBean.setLatitude(String.valueOf(center.latitude));
                                    sourceBean.setLongitude(String.valueOf(center.longitude));
                                   //getLocationName(String.valueOf(sourceBean.getDLatitude()), String.valueOf(center.longitude));
                                  // getlocationAuto(center.latitude,center.longitude);

                                    getAddressFromLocation(center.latitude,center.longitude,getApplicationContext());
                                    if(!txtSource.getText().equals("") && !txtDestination.getText().equals("")){
                                        distinationConfirmButton.setVisibility(View.VISIBLE);
                                    }

                                }else{
                                    distinationConfirmButton.setVisibility(View.GONE);
                                    txtSource.setText("");
                                   // isInDhakaOr=isInDhakaCityOR(center.latitude,center.longitude);
                                    if(isInDhakaOr){
                                        outOfDhakaServImBtn.setVisibility(View.VISIBLE);

                                    }else {
                                        outOfDhakaServImBtn.setVisibility(View.GONE);
                                        Toast.makeText(LandingPageActivity.this, "Service is not available in this area!!", Toast.LENGTH_LONG).show();
                                        outOfDhakaServImBtn.setVisibility(View.VISIBLE);
                                    }
                                }
                                isCameraMoved=false;
                                }

                            break;
                        case 2:
                            if(destinationBean==null)
                                destinationBean = new PlaceBean();
                            if (isCameraMoved) {
                                if(isInDhakaCityB) {
                                    outOfDhakaServImBtn.setVisibility(View.GONE);
                                    destinationBean.setLatitude(String.valueOf(center.latitude));
                                    destinationBean.setLongitude(String.valueOf(center.longitude));
                                    getAddressFromLocationDes(center.latitude,center.longitude,getApplicationContext());
                                   // getLocationNameDestination(String.valueOf(destinationBean.getDLatitude()), String.valueOf(destinationBean.getDLongitude()));
                                    if(!txtSource.getText().equals("") && !txtDestination.getText().equals("")){
                                        distinationConfirmButton.setVisibility(View.VISIBLE);
                                    }
                                }else {
                                    distinationConfirmButton.setVisibility(View.GONE);
                                    txtDestination.setText("");
                                   // isInDhakaOr=isInDhakaCityOR(center.latitude,center.longitude);
                                        if(isInDhakaOr){
                                            outOfDhakaServImBtn.setVisibility(View.VISIBLE);

                                        }else {
                                            outOfDhakaServImBtn.setVisibility(View.GONE);
                                            Toast.makeText(LandingPageActivity.this, "Service is not available in this area!!", Toast.LENGTH_LONG).show();
                                            outOfDhakaServImBtn.setVisibility(View.VISIBLE);
                                        }
                                }
                                isCameraMoved=false;
                            }
                            break;


                    }
                    isCameraMoved=false;

                }else {

                  //  isCameraMoved=false;
                }

                isCameraMoved=false;
            }

        });
    }


    public boolean isInDhakaCity(double latitude,double longitute){
        if((latitude<=23.900002 && latitude>=23.661270) && (longitute<=90.509105 && longitute>=90.329547)){
            return true;

        }else {
            return false;
        }
    }
    public boolean isInDhakaCityOR(double latitude,double longitute){
        if((latitude<=23.900002 && latitude>=23.661270)) {
            return true;
        }else if ((longitute<=90.509105 && longitute>=90.329547)){

        }else{
            return false;
        }
        return false;
    }

    private void initFCM() {

        FCMRegistrationTask fcmRegistrationTask = new FCMRegistrationTask();
        fcmRegistrationTask.setFCMRegistrationTaskListener(new FCMRegistrationTask.FCMRegistrationTaskListener() {
            @Override
            public void dataDownloadedSuccessfully(String fcmToken) {

                Log.i(TAG, "dataDownloadedSuccessfully: FCM TOKEN : " + fcmToken);
                fcm_tocken_fr = fcmToken;

                JSONObject postData = getUpdateFCMTokenJSObj(fcmToken);

                DataManager.performUpdateFCMToken(postData, new BasicListener() {
                    @Override
                    public void onLoadCompleted(BasicBean basicBean) {

                    }

                    @Override
                    public void onLoadFailed(String error) {

                    }
                });

            }

            @Override
            public void dataDownloadFailed() {

            }
        });
        fcmRegistrationTask.execute();

    }


    private JSONObject getUpdateFCMTokenJSObj(String fcmToken) {
        JSONObject postData = new JSONObject();

        try {
            postData.put("fcm_token", fcmToken);
            postData.put("phone", Config.getInstance().getPhone());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return postData;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_SEARCH_SOURCE_SELECT && resultCode == RESULT_OK) {

            sourceBean = (PlaceBean) data.getSerializableExtra("bean");
            Log.i(TAG, "onActivityResult: SourceName" + sourceBean.getName() +"source address=========="+sourceBean.getAddress());
            Log.i(TAG, "onActivityResult: SourceLatitude : " + sourceBean.getDLatitude());
            Log.i(TAG, "onActivityResult: SourceLongitude : " + sourceBean.getDLongitude());
            if (sourceBean != null) {
                isLocNamFoAtu=true;
                locNamFoAuto=sourceBean.getName();
                txtSource.setText(sourceBean.getName());
                   onSourceSelect();
            }
        }


        if (requestCode == REQ_SEARCH_DESTINATION_SELECT && resultCode == RESULT_OK) {

            destinationBean = (PlaceBean) data.getSerializableExtra("bean");
            Log.i(TAG, "onActivityResult: Destination name" + destinationBean.getName());
            Log.i(TAG, "onActivityResult: DestinationLatitude : " + destinationBean.getDLatitude());
            Log.i(TAG, "onActivityResult: DestinationLongitude : " + destinationBean.getDLongitude());

            if (destinationBean != null) {
                isLocNamFoAtu=true;
                locNamFoAuto=destinationBean.getName();
                txtDestination.setText(destinationBean.getName());
                 onDestinationSelect();
            }
        }


        if (requestCode == REQ_REQUEST_RIDE && resultCode == RESULT_OK) {
            if(happyyFareWithService.getVisibility()==View.VISIBLE){
                happyyFareWithService.setVisibility(View.GONE);
            }
            if(distinationConfirmButton.getVisibility()==View.GONE) {
                distinationConfirmButton.setVisibility(View.VISIBLE);
            };

            DriverBean driverBean = (DriverBean) data.getSerializableExtra("bean");
            startActivity(new Intent(this, OnTripActivity.class)
                    .putExtra("bean", driverBean)
                    .putExtra("source", sourceBean)
                    .putExtra("destination", destinationBean)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));

            finish();

        }
        /*

        if (requestCode == REQ_ESTIMATED_DESTINATION && resultCode == RESULT_OK) {

            destinationBean = (PlaceBean) data.getSerializableExtra("bean");
            llProgressBar.setVisibility(View.VISIBLE);
            llEstimation.setVisibility(View.GONE);

            if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            } else {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }


            if (sourceBean == null && mMap != null) {
                sourceBean = new PlaceBean();
                LatLng center = mMap.getCameraPosition().target;
                sourceBean.setLatitude(String.valueOf(center.latitude));
                sourceBean.setLongitude(String.valueOf(center.longitude));
            } else if (sourceBean == null) {
                Snackbar.make(coordinatorLayout, AppConstants.WEB_ERROR_MSG, Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
                return;
            }
            fetchPolyPoints(false);
            showFareEstimation(destinationBean.getName());
        }
        */
    }


    private void onSourceSelect() {

        mMap.clear();
       // txtSource.setText(sourceBean.getAddress());
        onPlotLocation(false, LOCATION_SOURCE, sourceBean.getDLatitude(), sourceBean.getDLongitude());
        isCameraMoved=false;
        /*
        try {
            if (destinationBean.getDLatitude() != 0 && destinationBean.getDLongitude() != 0) {
                onPlotLocation(true, LOCATION_DESTINATION, destinationBean.getDLatitude(), destinationBean.getDLongitude());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (sourceBean.getDLatitude() != 0 && sourceBean.getDLongitude() != 0 && destinationBean.getDLatitude() != 0 && destinationBean.getDLongitude() != 0) {
                mapAutoZoom();
               // isCameraMoved=false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        */

    }

    private void onDestinationSelect() {
        mMap.clear();
       // txtDestination.setText(destinationBean.getName());
        onPlotLocation(false, LOCATION_DESTINATION, destinationBean.getDLatitude(), destinationBean.getDLongitude());
        isCameraMoved=false;
        /*
        if (sourceBean.getDLatitude() != 0 && sourceBean.getDLongitude() != 0
                && destinationBean.getDLatitude() != 0 && destinationBean.getDLongitude() != 0) {
            onPlotLocation(true, LOCATION_SOURCE, sourceBean.getDLatitude(), sourceBean.getDLongitude());
            onPlotLocation(true, LOCATION_DESTINATION, destinationBean.getDLatitude(), destinationBean.getDLongitude());
            isCameraMoved=false;
        }
*/

    }

    private void showFareEstimation(String location) {
        if (destinationBean != null) {
//            fetchTotalfare();
            txtFareLabel.setText(R.string.label_estd_fare);
        }
        txtFareEstimate.setVisibility(View.GONE);
        txtTo.setVisibility(View.VISIBLE);
        llDestinationEstimated.setVisibility(View.VISIBLE);
        txtEstimatedDestination.setText(location);
    }

    public void onClickOutsideDhaka(View view){
        Intent myOutDhakaIntent = new Intent(this,OutOfDhakaActivity.class);
        startActivity(myOutDhakaIntent);
    }


    public void onBackClick() {

        mMap.clear();

    }


    //okey code
    public void onSourceClick(View view) {
        mMap.clear();

        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        click_pick_or_des = 1;
        if(happyyFareWithService.getVisibility()==View.VISIBLE){
            happyyFareWithService.setVisibility(View.GONE);
            distinationConfirmButton.setVisibility(View.VISIBLE);
        }
        if(sourceBean.getDLatitude()!=0.0&& sourceBean.getDLongitude()!=0.0) {
            onPlotLocation(false, LOCATION_SOURCE, sourceBean.getDLatitude(), sourceBean.getDLongitude());
        }
        pickup_select_marker_imv.setVisibility(View.VISIBLE);
        destination_select_marker.setVisibility(View.GONE);

        searchType = AppConstants.SEARCH_SOURCE;
        Intent intent = new Intent(LandingPageActivity.this, SearchPageActivity.class);
        intent.putExtra("search_type", searchType);
        startActivityForResult(intent, REQ_SEARCH_SOURCE_SELECT);
/*
        if (sourceBean == null)
            sourceBean = new PlaceBean();
        if (destinationBean == null)
            destinationBean = new PlaceBean();

        if (sourceBean.getDLatitude() != 0.0 && sourceBean.getDLatitude() != 0.0) {
            if (destinationBean.getDLatitude() != 0.0 && destinationBean.getDLongitude() != 0.0) {
                LatLng latLng_pick = new LatLng(destinationBean.getDLatitude(), destinationBean.getDLongitude());
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(latLng_pick).icon(BitmapDescriptorFactory.fromResource(R.drawable.droffoff)));
                onPlotLocation(true,);
            }else {
                destinationBean.setLatitude(Config.getInstance().getCurrentLatitude());
                destinationBean.setLongitude(Config.getInstance().getCurrentLongitude());
                LatLng latLng_pick = new LatLng(destinationBean.getDLatitude(), destinationBean.getDLongitude());
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(latLng_pick).icon(BitmapDescriptorFactory.fromResource(R.drawable.droffoff)));
            }
            // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(sourceBean.getDLatitude(), sourceBean.getDLatitude()), 17.0f));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(sourceBean.getDLatitude(), sourceBean.getDLatitude()), 17));

        }else {

            if (destinationBean.getDLatitude() != 0.0 && destinationBean.getDLongitude() != 0.0) {
                LatLng latLng_pick = new LatLng(destinationBean.getDLatitude(), destinationBean.getDLongitude());
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(latLng_pick).icon(BitmapDescriptorFactory.fromResource(R.drawable.droffoff)));
            }else {
                destinationBean.setLatitude(Config.getInstance().getCurrentLatitude());
                destinationBean.setLongitude(Config.getInstance().getCurrentLongitude());
                LatLng latLng_pick = new LatLng(destinationBean.getDLatitude(), destinationBean.getDLongitude());
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(latLng_pick).icon(BitmapDescriptorFactory.fromResource(R.drawable.droffoff)));
            }
            sourceBean.setLatitude(Config.getInstance().getCurrentLatitude());
            sourceBean.setLongitude(Config.getInstance().getCurrentLongitude());
            //  mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(sourceBean.getDLatitude(),sourceBean.getDLongitude()), 17.0f));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(sourceBean.getDLatitude(), sourceBean.getDLatitude()), 17));
        }

        */


    }
    public void onDestinationClick(View view) {
        mMap.clear();
        if(happyyFareWithService.getVisibility()==View.VISIBLE){
            happyyFareWithService.setVisibility(View.GONE);
            distinationConfirmButton.setVisibility(View.VISIBLE);
        }

        click_pick_or_des=2;
        if(sourceBean==null)
            sourceBean = new PlaceBean();
        if(destinationBean==null)
            destinationBean =new PlaceBean();
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        if(destinationBean.getDLongitude()!=0.0&& destinationBean.getDLongitude()!=0.0) {
            onPlotLocation(false, LOCATION_DESTINATION, destinationBean.getDLatitude(), destinationBean.getDLongitude());
        }
        pickup_select_marker_imv.setVisibility(View.GONE);
        destination_select_marker.setVisibility(View.VISIBLE);

        searchType = AppConstants.SEARCH_DESTINATION;

        Intent intent = new Intent(LandingPageActivity.this, SearchPageActivity.class);
        intent.putExtra("search_type", searchType);
        startActivityForResult(intent, REQ_SEARCH_DESTINATION_SELECT);
/*
        if(destinationBean.getDLatitude()!=0.0 && destinationBean.getDLatitude()!=0.0) {
            if(sourceBean.getDLatitude()!=0.0 && sourceBean.getDLongitude()!=0.0){
                LatLng latLng_dest=new LatLng(sourceBean.getDLatitude(),sourceBean.getDLongitude());
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(latLng_dest).icon(BitmapDescriptorFactory.fromResource(R.drawable.pickupnewicone)));
            }else {
                sourceBean.setLatitude(Config.getInstance().getCurrentLatitude());
                sourceBean.setLongitude(Config.getInstance().getCurrentLongitude());
                LatLng latLng_dest=new LatLng(sourceBean.getDLatitude(),sourceBean.getDLongitude());
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(latLng_dest).icon(BitmapDescriptorFactory.fromResource(R.drawable.pickupnewicone)));
            }
            // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(destinationBean.getDLatitude(), destinationBean.getDLatitude()), 17.0f));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(destinationBean.getDLatitude(), destinationBean.getDLatitude()), 17));

        }else {
            if(sourceBean.getDLatitude()!=0.0 && sourceBean.getDLongitude()!=0.0){
                LatLng latLng_dest=new LatLng(sourceBean.getDLatitude(),sourceBean.getDLongitude());
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(latLng_dest).icon(BitmapDescriptorFactory.fromResource(R.drawable.pickupnewicone)));
            }else {
                sourceBean.setLatitude(Config.getInstance().getCurrentLatitude());
                sourceBean.setLongitude(Config.getInstance().getCurrentLongitude());
                LatLng latLng_dest=new LatLng(sourceBean.getDLatitude(),sourceBean.getDLongitude());
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(latLng_dest).icon(BitmapDescriptorFactory.fromResource(R.drawable.pickupnewicone)));
            }
            destinationBean.setLatitude(Config.getInstance().getCurrentLatitude());
            destinationBean.setLongitude(Config.getInstance().getCurrentLongitude());
            // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(destinationBean.getDLatitude(), destinationBean.getDLatitude()), 17.0f));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(destinationBean.getDLatitude(), destinationBean.getDLatitude()), 17));

        }

        */

    }


    private void displayLocation() {

        LatLng center = new LatLng(LastLocation.getLatitude(),LastLocation.getLongitude());
        LatLng northSide = SphericalUtil.computeOffset(center,100000,0);
        LatLng southSide = SphericalUtil.computeOffset(center,100000,180);

        Log.i(TAG, "displayLocation: OnPlotLocation Called .........>>>>>>>>>>>>>>>>>>>>>>>>..");

        if (LastLocation != null ) {

            //   onPlotLocation(false, LOCATION_SOURCE, LastLocation.getLatitude(), LastLocation.getLongitude());
            getLocationName(String.valueOf(LastLocation.getLatitude()), String.valueOf(LastLocation.getLongitude()));
//            getLocationName(LastLocation.getLatitude(), LastLocation.getLongitude());
        }
    }

    private void displayLocation_destination() {

        LatLng center = new LatLng(LastLocation.getLatitude(),LastLocation.getLongitude());
        LatLng northSide = SphericalUtil.computeOffset(center,100000,0);
        LatLng southSide = SphericalUtil.computeOffset(center,100000,180);

        Log.i(TAG, "displayLocation: OnPlotLocation Called .........>>>>>>>>>>>>>>>>>>>>>>>>..");

        if (LastLocation != null ) {

            //   onPlotLocation(false, LOCATION_SOURCE, LastLocation.getLatitude(), LastLocation.getLongitude());
            getLocationName(String.valueOf(LastLocation.getLatitude()), String.valueOf(LastLocation.getLongitude()));
//            getLocationName(LastLocation.getLatitude(), LastLocation.getLongitude());
        }
    }

    private void getLocationName(double currentLatitude, double currentLongitude) {

        LocationTask locationTask = new LocationTask(currentLatitude, currentLongitude);
        locationTask.setLocationTaskListener(new LocationTask.LocationTaskListener() {
            @Override
            public void dataDownloadedSuccessfully(PlaceBean placeBean) {

                sourceBean = placeBean;

                if (placeBean != null) {
                    txtActionSearch.setText(placeBean.getName());
                    txtSource.setText(placeBean.getName());
                }
            }

            @Override
            public void dataDownloadFailed() {

            }
        });
        locationTask.execute();


    }

    private void moveToCurrentLocation() {
        try {
            if (Config.getInstance().getCurrentLatitude() != null && !Config.getInstance().getCurrentLatitude().equals("")
                    && Config.getInstance().getCurrentLongitude() != null && !Config.getInstance().getCurrentLongitude().equals("")) {
                dLatitude = Double.parseDouble(Config.getInstance().getCurrentLatitude());
                dLongitude = Double.parseDouble(Config.getInstance().getCurrentLongitude());

                FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
                //   double lat = fusedLocationProviderClient.
                current = new LatLng(dLatitude, dLongitude);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(current, 15));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void getlocationAuto(double dLatitude,double dLongitude){
        txtSource.setText("Loading...");
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        builder.setLatLngBounds(new LatLngBounds(new LatLng(dLatitude,dLongitude),new LatLng(dLatitude,dLongitude)));
        PlaceListTask placesListTask = new PlaceListTask(dLatitude,dLongitude);
        //placesListTask.setStrAddress(txtInput);
        placesListTask.setmLatitude(dLatitude);
        placesListTask.setmLongitude(dLongitude);
        placesListTask.execute();

    }


    public  void getAddressFromLocation(final double latitude, final double longitude, final Context context) {

        txtSource.setText("Loading...");
        BackGrounForLoca backGrounForLoca = new BackGrounForLoca(latitude,longitude,context);
        backGrounForLoca.execute();


    }

    protected void getLocationName(final String latitude, final String longitude) {

//        swipeView.setRefreshing(true);
       txtSource.setText("Loading...");


        HashMap<String, String> urlParams = new HashMap<>();
        //	postData.put("uid", id);
        urlParams.put("latlng", latitude + "," + longitude);
        urlParams.put("sensor", "true");
        urlParams.put("key", getString(R.string.browser_api_key));

        LocationNameTask locationNameTask = new LocationNameTask(urlParams);
        locationNameTask.setLocationNameTaskListener(new LocationNameTask.LocationNameTaskListener() {

            @Override
            public void dataDownloadedSuccessfully(String address) {

                //	System.out.println(landingBean.getStatus());
                if(isLocNamFoAtu) {
                    if (null != address) {
                        txtSource.setText(locNamFoAuto+","+address);
                        System.out.println("Location Name Retrieved : " + address);
                        Config.getInstance().setCurrentLocation(locNamFoAuto+","+address);

                        //   txtActionSearch.setText(address);

                        // txtDestination.setText(address);
                        if (sourceBean == null) {
                            sourceBean = new PlaceBean();
                        }
                        sourceBean.setAddress(locNamFoAuto+","+address);
                        sourceBean.setName(locNamFoAuto+","+address);
                        // sourceBean.setLatitude(latitude);
                        // sourceBean.setLongitude(longitude);


                    }
                    isLocNamFoAtu=false;
                    locNamFoAuto="";
                }else{
                    if (null != address) {
                        txtSource.setText(address);
                        System.out.println("Location Name Retrieved : " + address);
                        Config.getInstance().setCurrentLocation(address);

                        //   txtActionSearch.setText(address);

                        // txtDestination.setText(address);
                        if (sourceBean == null) {
                            sourceBean = new PlaceBean();
                        }
                        sourceBean.setAddress(address);
                        sourceBean.setName(address);
                        // sourceBean.setLatitude(latitude);
                        // sourceBean.setLongitude(longitude);


                    }
                }
            }

            @Override
            public void dataDownloadFailed() {

            }
        });
        locationNameTask.execute();
    }

    //for destination event

    public  void getAddressFromLocationDes(final double latitude, final double longitude, final Context context) {
        txtDestination.setText("Loading...");

        BackGrounForDes backGrounForDes = new BackGrounForDes(latitude,longitude,context);
        backGrounForDes.execute();

        /*
        Thread thread = new Thread() {
            @Override
            public void run() {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                String result = null;
                try {
                    List < Address > addressList = geocoder.getFromLocation(latitude, longitude, 1);
                    if (addressList != null && addressList.size() > 0) {
                        Address address = addressList.get(0);
                        StringBuilder sb = new StringBuilder();
                        String knownName = address.getFeatureName();
                        String addresslineee = address.getAddressLine(0);
                        String subLocality = address.getSubLocality();
                        String city = address.getLocality();
                        // String state = address.getAdminArea();
                        // String country = address.getCountryName();
                        String postalCode = address.getPostalCode();


                        result = knownName+" "+addresslineee+","+subLocality+","+city+","+","+postalCode+".";

                        if(isLocNamFoAtu) {

                            if (null != result) {
                                txtDestination.setText(locNamFoAuto+","+result);
                                System.out.println("Location Name Retrieved : " + locNamFoAuto+","+result);
                                Config.getInstance().setCurrentLocation(locNamFoAuto+","+result);


                                if (destinationBean == null) {
                                    destinationBean = new PlaceBean();
                                }
                                destinationBean.setAddress(locNamFoAuto+","+result);
                                // destinationBean.setName(address);
                                // destinationBean.setLatitude(latitude);
                                //  destinationBean.setLongitude(longitude);
                            }
                            isLocNamFoAtu=false;
                            locNamFoAuto="";
                        }else {
                            txtDestination.setText(result);
                            System.out.println("Location Name Retrieved : " + result);
                            Config.getInstance().setCurrentLocation(result);


                            if (destinationBean == null) {
                                destinationBean = new PlaceBean();
                            }
                            destinationBean.setAddress(result);
                        }




                    }
                } catch (IOException e) {
                    Log.e("Location Address Loader", "Unable connect to Geocoder", e);
                } finally {

                }
            }
        };
        thread.start();

        */
    }

    protected void getLocationNameDestination(final String latitude, final String longitude) {

//        swipeView.setRefreshing(true);
        txtDestination.setText("Loading...");

        HashMap<String, String> urlParams = new HashMap<>();
        //	postData.put("uid", id);
        urlParams.put("latlng", latitude + "," + longitude);
        urlParams.put("sensor", "true");
        urlParams.put("key", getString(R.string.browser_api_key));

        LocationNameTask locationNameTask = new LocationNameTask(urlParams);
        locationNameTask.setLocationNameTaskListener(new LocationNameTask.LocationNameTaskListener() {

            @Override
            public void dataDownloadedSuccessfully(String address) {
                if(isLocNamFoAtu) {

                    if (null != address) {
                        txtDestination.setText(locNamFoAuto+","+address);
                        System.out.println("Location Name Retrieved : " + locNamFoAuto+","+address);
                        Config.getInstance().setCurrentLocation(locNamFoAuto+","+address);


                        if (destinationBean == null) {
                            destinationBean = new PlaceBean();
                        }
                        destinationBean.setAddress(locNamFoAuto+","+address);
                        // destinationBean.setName(address);
                        // destinationBean.setLatitude(latitude);
                        //  destinationBean.setLongitude(longitude);
                    }
                    isLocNamFoAtu=false;
                    locNamFoAuto="";
                }else {
                    if (null != address) {
                        txtDestination.setText(address);
                        System.out.println("Location Name Retrieved : " + address);
                        Config.getInstance().setCurrentLocation(address);


                        if (destinationBean == null) {
                            destinationBean = new PlaceBean();
                        }
                        destinationBean.setAddress(address);
                        // destinationBean.setName(address);
                        // destinationBean.setLatitude(latitude);
                        //  destinationBean.setLongitude(longitude);
                    }

                }
            }

            @Override
            public void dataDownloadFailed() {

            }
        });
        locationNameTask.execute();
    }
/*
    private void fetchCarDetails() {

        if (destinationBean == null) {
            llEstimation.setVisibility(View.GONE);
            llProgressBar.setVisibility(View.VISIBLE);
        }

//        swipeView.setRefreshing(true)
//        center = mMap.getProjection().getVisibleRegion().latLngBounds.getCenter();

        if (sourceBean == null ) {
            if(mMap != null){
                LatLng center = mMap.getCameraPosition().target;
                sourceBean = new PlaceBean();
                sourceBean.setLatitude(String.valueOf(center.latitude));
                sourceBean.setLongitude(String.valueOf(center.longitude));
            }else{
                Log.i(TAG, "fetchCarDetails: CAR DETAILS ERROR ");
                Snackbar.make(coordinatorLayout, AppConstants.WEB_ERROR_MSG, Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
                return;
            }
        }


        HashMap<String, String> urlParams = new HashMap<>();
        urlParams.put("car_type", carType);
        urlParams.put("car_type_id", carType);
        urlParams.put("latitude", sourceBean.getLatitude());
        urlParams.put("longitude", sourceBean.getLongitude());

        JSONObject postData = getJsonData();

        DataManager.fetchCarAvailability(postData, new CarInfoListener() {
            @Override
            public void onLoadCompleted(CarBean carBeanWS) {
                swipeView.setRefreshing(false);
                carBean = carBeanWS;
                populateCarDetails(carBeanWS);

            }

            @Override
            public void onLoadFailed(String error) {
                swipeView.setRefreshing(false);
                // txtCarAvailability.setText(R.string.label_no_cars_available);
                //txtCarArrivalEstimatedTime.setVisibility(View.GONE);
                Toast.makeText(LandingPageActivity.this, error, Toast.LENGTH_SHORT).show();

            }
        });
    }

    public JSONObject getJsonData() {
        JSONObject jsonData = new JSONObject();
        try {
            jsonData.put("car_type",carType);
            jsonData.put("latitude",sourceBean.getLatitude());
            jsonData.put("longitude",sourceBean.getLongitude());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonData;
    }

    private void populateCarDetails(CarBean carBean) {

        if (carBean.getCarsAvailable().equalsIgnoreCase(getString(R.string.label_no_cars_available))) {
           // txtCarAvailability.setText(R.string.label_no_cars_available);
           // txtCarArrivalEstimatedTime.setVisibility(View.GONE);
        } else {
           // txtCarArrivalEstimatedTime.setVisibility(View.VISIBLE);
           // txtCarAvailability.setText(R.string.btn_set_pickup_location);
        }

        // txtCarArrivalEstimatedTime.setText(carBean.getMinTime());
        txtTime.setText(carBean.getMinTime());
        txtMaxSize.setText(carBean.getMaxSize());
        if (destinationBean == null) {
            txtFare.setText(carBean.getMinFare());
        }

        if (destinationBean == null) {
            llEstimation.setVisibility(View.VISIBLE);
            llProgressBar.setVisibility(View.GONE);
        }
    }

    public void fetchTotalfare() {

        HashMap<String, String> urlParams = null;
        try {
            urlParams = new HashMap<>();
            urlParams.put("car_type", String.valueOf(carType));
            if (sourceBean.getName() != null && !sourceBean.getName().equals("")) {
                urlParams.put("source", sourceBean.getName());
            }
            if (destinationBean.getName() != null && destinationBean.getName().equals("")) {
                urlParams.put("destination", destinationBean.getName());
            }
            urlParams.put("source_latitude", sourceBean.getLatitude());
            urlParams.put("source_longitude", sourceBean.getLongitude());
            urlParams.put("destination_latitude", destinationBean.getLatitude());
            urlParams.put("destination_longitude", destinationBean.getLongitude());
            urlParams.put("distance", String.valueOf(distance));
            urlParams.put("time", String.valueOf(time));

            Log.i(TAG, "fetchTotalfare: Time " + time);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject jsonObjectData = new JSONObject();
        try {
            if (sourceBean.getName() != null && !sourceBean.getName().equals("")) {
                urlParams.put("source", sourceBean.getName());
                jsonObjectData.put("source", sourceBean.getName());
            }
            if (destinationBean.getName() != null && destinationBean.getName().equals("")) {
                urlParams.put("destination", destinationBean.getName());
                jsonObjectData.put("destination", destinationBean.getName());
            }

            jsonObjectData.put("car_type", String.valueOf(carType));
            jsonObjectData.put("source_latitude", sourceBean.getLatitude());
            jsonObjectData.put("source_longitude", sourceBean.getLongitude());
            jsonObjectData.put("destination_latitude", destinationBean.getLatitude());
            jsonObjectData.put("destination_longitude", destinationBean.getLongitude());
            jsonObjectData.put("distance", String.valueOf(distance));
            jsonObjectData.put("time", String.valueOf(time));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        DataManager.fetchTotalFare(jsonObjectData, new TotalFareListener() {

            @Override
            public void onLoadCompleted(FareBean fareBeanWS) {

                if (isConfirmationPage) {
                    llFare.setVisibility(View.VISIBLE);
                    llConfirmationProgress.setVisibility(View.GONE);

                }
                swipeView.setRefreshing(false);
                fareBean = fareBeanWS;
                populateFareDetails(fareBeanWS);

                txtFare.setVisibility(View.VISIBLE);

                llProgressBar.setVisibility(View.GONE);
                llEstimation.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadFailed(String error) {
                swipeView.setRefreshing(false);

                if (isConfirmationPage) {

                    txtFareEstimate.setVisibility(View.VISIBLE);
                    llProgressBar.setVisibility(View.GONE);
                }

                if (isConfirmationPage) {
                    llFare.setVisibility(View.VISIBLE);
                    rlFare.setVisibility(View.GONE);
                    llConfirmationProgress.setVisibility(View.GONE);
                }

                txtFare.setVisibility(View.GONE);

                txtEstimatedFare.setVisibility(View.GONE);

                llProgressBar.setVisibility(View.GONE);
                llEstimation.setVisibility(View.VISIBLE);

                if (!isFinishing()) {
                    PopupMessage popupMessage = new PopupMessage(LandingPageActivity.this);
                    popupMessage.setPopupActionListener(new PopupMessage.PopupActionListener() {
                        @Override
                        public void actionCompletedSuccessfully(boolean result) {

                            if (!isConfirmationPage) {
                                destinationBean = null;
                                txtTo.setVisibility(View.GONE);
                                txtFareLabel.setText(R.string.label_min_fare);
                                txtFare.setVisibility(View.VISIBLE);
                                txtFare.setText(carBean.getMinFare());
                                llDestinationEstimated.setVisibility(View.GONE);
                                txtFareEstimate.setVisibility(View.VISIBLE);
                            } else {
                                mMap.clear();
                                onPlotLocation(true, LOCATION_SOURCE, sourceBean.getDLatitude(), sourceBean.getDLongitude());
                                destinationBean = null;
                                txtDestination.setText("");
                                rlFare.setVisibility(View.GONE);
                                happyyFareWithService.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void actionFailed() {

                        }
                    });
                    popupMessage.show(error, 0);
                }

            }
        });
    }

    private void populateFareDetails(FareBean fareBean) {

        if (fareBean.getTotalFare() != null) {
            txtTotalFare.setText(fareBean.getTotalFare());
            txtFare.setText(fareBean.getTotalFare());
        }
    }

    public void getEstimatedFare() {

        String source = txtActionSearch.getText().toString();
        String destination = txtEstimatedDestination.getText().toString();

        HashMap<String, String> urlParams = new HashMap<>();
        urlParams.put("source", source);
        urlParams.put("destination", destination);

        urlParams.put("car_type", String.valueOf(carType));

        urlParams.put("source_latitude", sourceBean.getLatitude());
        urlParams.put("source_longitude", sourceBean.getLongitude());
        urlParams.put("destination_latitude", destinationBean.getLatitude());
        urlParams.put("destination_longitude", destinationBean.getLongitude());

        urlParams.put("distance", String.valueOf(distance));
        urlParams.put("time", String.valueOf(time));

        Log.i(TAG, "getEstimatedFare: Time " + time);

        JSONObject jsonObjectData = new JSONObject();
        try {
            jsonObjectData.put("source", source);
            jsonObjectData.put("destination", destination);
            jsonObjectData.put("car_type", String.valueOf(carType));
            jsonObjectData.put("source_latitude", sourceBean.getLatitude());
            jsonObjectData.put("source_longitude", sourceBean.getLongitude());
            jsonObjectData.put("destination_latitude", destinationBean.getLatitude());
            jsonObjectData.put("destination_longitude", destinationBean.getLongitude());
            jsonObjectData.put("distance", String.valueOf(distance));
            jsonObjectData.put("time", String.valueOf(time));

        } catch (JSONException e) {
            e.printStackTrace();
        }


        DataManager.fetchTotalFare(jsonObjectData, new TotalFareListener() {

            @Override
            public void onLoadCompleted(FareBean fareBean) {
                swipeView.setRefreshing(false);
              //  populateEstimatedFare(fareBean);
            }

            @Override
            public void onLoadFailed(String error) {
                swipeView.setRefreshing(false);

            }
        });
    }


   */

    public void onRequestRideClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        if (carBean != null) {
            if (carBean.getCarsAvailable().equalsIgnoreCase(getString(R.string.label_no_cars_available)) && txtDestination.getText().length() != 0) {

                Snackbar.make(coordinatorLayout, R.string.message_no_cars_available_for_the_location, Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            } else {

                if (txtDestination.getText().length() == 0) {
                    Snackbar.make(coordinatorLayout, R.string.message_destination_required, Snackbar.LENGTH_SHORT)
                            .setAction(R.string.btn_refresh, snackBarRefreshOnClickListener).show();
                } else {
                    if (fareBean != null && sourceBean != null && destinationBean != null) {
                        Intent intent = new Intent(LandingPageActivity.this, RequestingPageActivity.class);
                        intent.putExtra("fare_bean", fareBean);
                        intent.putExtra("car_type", String.valueOf(carType));
                        intent.putExtra("source_bean", sourceBean);
                        intent.putExtra("destination_bean", destinationBean);

    /*                    intent.putExtra("source_latitude", sourceBean.getLatitude());
                        intent.putExtra("source_longitude", sourceBean.getLongitude());
                        intent.putExtra("destination_latitude", destinationBean.getLatitude());
                        intent.putExtra("destination_longitude", destinationBean.getLongitude());*/

                        startActivityForResult(intent, REQ_REQUEST_RIDE);
                    } else {
                        Snackbar.make(coordinatorLayout, R.string.message_something_went_wrong, Snackbar.LENGTH_LONG)
                                .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
                    }
                }
            }
        }
    }

    //Bike Request
    public void onRequestRideClickBike(View view){
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        Intent requestAcIntent = new Intent(this,RequestingPageActivity.class);
        requestAcIntent.putExtra("fare",fareBeanAfterCalculate.getBikeFare());
        requestAcIntent.putExtra("fare_bean", fareBeanAfterCalculate);
        requestAcIntent.putExtra("car_type","1");
        requestAcIntent.putExtra("car_type_id","1");
        requestAcIntent.putExtra("source_bean", sourceBean);
        requestAcIntent.putExtra("destination_bean", destinationBean);
        requestAcIntent.putExtra("distance", String.valueOf(distance));
        requestAcIntent.putExtra("time", String.valueOf(time));
        if(!dri_phone_re_req.equals("")) {
            requestAcIntent.putExtra("dri_phone_re_req", dri_phone_re_req);
        }
        startActivity(requestAcIntent);


        //   Toast.makeText(this,"No Bike Available",Toast.LENGTH_SHORT).show();
    }
//CNG onRequestRideClickCNG

    public void onRequestRideClickCNG(View view){
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        Intent requestAcIntent = new Intent(this,RequestingPageActivity.class);
        requestAcIntent.putExtra("fare",fareBeanAfterCalculate.getCngFare());
        requestAcIntent.putExtra("fare_bean", fareBeanAfterCalculate);
        requestAcIntent.putExtra("car_type","2");
        requestAcIntent.putExtra("car_type_id","2");
        requestAcIntent.putExtra("source_bean", sourceBean);
        requestAcIntent.putExtra("destination_bean", destinationBean);
        requestAcIntent.putExtra("distance", String.valueOf(distance));
        requestAcIntent.putExtra("time", String.valueOf(time));
        if(!dri_phone_re_req.equals("")) {
            requestAcIntent.putExtra("dri_phone_re_req", dri_phone_re_req);
        }
        startActivity(requestAcIntent);
    }
    // onRequestRideClickCar
    public void onRequestRideClickCar(View view){
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        Intent requestAcIntent = new Intent(this,RequestingPageActivity.class);
        requestAcIntent.putExtra("fare",fareBeanAfterCalculate.getCarFare());
        requestAcIntent.putExtra("fare_bean", fareBeanAfterCalculate);
        requestAcIntent.putExtra("car_type","3");
        requestAcIntent.putExtra("car_type_id","3");
        requestAcIntent.putExtra("source_bean", sourceBean);
        requestAcIntent.putExtra("destination_bean", destinationBean);
        requestAcIntent.putExtra("distance", String.valueOf(distance));
        requestAcIntent.putExtra("time", String.valueOf(time));
        if(!dri_phone_re_req.equals("")) {
            requestAcIntent.putExtra("dri_phone_re_req", dri_phone_re_req);
        }
        startActivity(requestAcIntent);

    }

    public void onRequestRideClickAmbulance(View view){
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        Intent requestAcIntent = new Intent(this,RequestingPageActivity.class);
        requestAcIntent.putExtra("fare",fareBeanAfterCalculate.getAmbulanceFare());
        requestAcIntent.putExtra("fare_bean", fareBeanAfterCalculate);
        requestAcIntent.putExtra("car_type","4");
        requestAcIntent.putExtra("car_type_id","4");
        requestAcIntent.putExtra("source_bean", sourceBean);
        requestAcIntent.putExtra("destination_bean", destinationBean);
        requestAcIntent.putExtra("distance", String.valueOf(distance));
        requestAcIntent.putExtra("time", String.valueOf(time));
        if(!dri_phone_re_req.equals("")) {
            requestAcIntent.putExtra("dri_phone_re_req", dri_phone_re_req);
        }
        startActivity(requestAcIntent);

    }
    public void onRequestRideClickCarOneHoure(View view){
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        Intent requestAcIntent = new Intent(this,RequestingPageActivity.class);
        requestAcIntent.putExtra("fare",fareBeanAfterCalculate.getCarHireFare());
        requestAcIntent.putExtra("fare_bean", fareBeanAfterCalculate);
        requestAcIntent.putExtra("car_type","3");
        requestAcIntent.putExtra("car_type_id","5");
        requestAcIntent.putExtra("source_bean", sourceBean);
        requestAcIntent.putExtra("destination_bean", destinationBean);
        requestAcIntent.putExtra("distance", String.valueOf(distance));
        requestAcIntent.putExtra("time", String.valueOf(time));
        if(!dri_phone_re_req.equals("")) {
            requestAcIntent.putExtra("dri_phone_re_req", dri_phone_re_req);
        }
        startActivity(requestAcIntent);
    }
    public void onRequestRideClickCarTwoHours(View view){
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        Toast.makeText(this,"No Car Available",Toast.LENGTH_SHORT).show();
    }
    public void onRequestRideClickCarFourHours(View view){
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        Toast.makeText(this,"No Car Available",Toast.LENGTH_SHORT).show();
    }
    public void onRequestRideClickCarDayPrimio(View view){
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        Intent requestAcIntent = new Intent(this,RequestingPageActivity.class);
        requestAcIntent.putExtra("fare",fareBeanAfterCalculate.getOndayHirePremioFare());
        requestAcIntent.putExtra("fare_bean", fareBeanAfterCalculate);
        requestAcIntent.putExtra("car_type","3");
        requestAcIntent.putExtra("car_type_id","6");
        requestAcIntent.putExtra("source_bean", sourceBean);
        requestAcIntent.putExtra("destination_bean", destinationBean);
        requestAcIntent.putExtra("distance", String.valueOf(distance));
        requestAcIntent.putExtra("time", String.valueOf(time));
        if(!dri_phone_re_req.equals("")) {
            requestAcIntent.putExtra("dri_phone_re_req", dri_phone_re_req);
        }
        startActivity(requestAcIntent);
    }
    public void onRequestRideClickCarDayNoah(View view){
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        /*
        Intent requestAcIntent = new Intent(this,RequestingPageActivity.class);
        requestAcIntent.putExtra("fare",fareBeanAfterCalculate.getOnedayHireNoahFare());
        requestAcIntent.putExtra("fare_bean", fareBeanAfterCalculate);
        requestAcIntent.putExtra("car_type","3");
        requestAcIntent.putExtra("car_type_id","7");
        requestAcIntent.putExtra("source_bean", sourceBean);
        requestAcIntent.putExtra("destination_bean", destinationBean);
        requestAcIntent.putExtra("distance", String.valueOf(distance));
        requestAcIntent.putExtra("time", String.valueOf(time));
        if(!dri_phone_re_req.equals("")) {
            requestAcIntent.putExtra("dri_phone_re_req", dri_phone_re_req);
        }
        startActivity(requestAcIntent);
        */

        OnedayRequestModel onedayReqData = new OnedayRequestModel();
        onedayReqData.setDistance(String.valueOf(distance));
        onedayReqData.setTime(String.valueOf(time));
        onedayReqData.setFare(fareBeanAfterCalculate.getOnedayHireHiaceFare());
        onedayReqData.setCar_type("3");
        onedayReqData.setCar_type_id("7");
        onedayReqData.setCustomer_phone(Config.getInstance().getPhone());
        onedayReqData.setFcm_token(fcm_tocken_fr);
        onedayReqData.setCustomer_name(Config.getInstance().getName());
        onedayReqData.setCustomer_photo(Config.getInstance().getProfilePhoto());
        onedayReqData.setCustomer_location(sourceBean.getAddress());
        onedayReqData.setCustomer_latitude(sourceBean.getLatitude());
        onedayReqData.setCustomer_longitude(sourceBean.getLongitude());
        onedayReqData.setDestination_location(destinationBean.getAddress());
        onedayReqData.setDestination_latitude(destinationBean.getLatitude());
        onedayReqData.setDestination_longitude(destinationBean.getLongitude());

        if(!dri_phone_re_req.equals("")) {
            // requestAcIntent.putExtra("dri_phone_re_req", dri_phone_re_req);
            onedayReqData.setDri_phone_re_req(dri_phone_re_req);
        }

        networkCall.sendOndeDayReq(onedayReqData, new ResponseCallback<OnedayRequestResponse>() {
            @Override
            public void onSuccess(OnedayRequestResponse data) {
                doneProgressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),"Your request has sent.",Toast.LENGTH_LONG).show();
                Intent onedayRequestAc = new Intent(LandingPageActivity.this, OneDayReq.class);
                startActivity(onedayRequestAc);
                finish();


            }

            @Override
            public void onError(Throwable th) {
                doneProgressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),"Something Wrong!!!",Toast.LENGTH_LONG).show();
                Intent onedayRequestAc = new Intent(LandingPageActivity.this, LandingPageActivity.class);
                startActivity(onedayRequestAc);

            }
        });
    }
    public void onRequestRideClickCarHice(View view){
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        doneProgressBar.setVisibility(View.VISIBLE);
        /*
        Intent requestAcIntent = new Intent(this,RequestingPageActivity.class);
        requestAcIntent.putExtra("fare",fareBeanAfterCalculate.getOnedayHireHiaceFare());
        requestAcIntent.putExtra("fare_bean", fareBeanAfterCalculate);
        requestAcIntent.putExtra("car_type","3");
        requestAcIntent.putExtra("car_type_id","8");
        requestAcIntent.putExtra("source_bean", sourceBean);
        requestAcIntent.putExtra("destination_bean", destinationBean);
        requestAcIntent.putExtra("distance", String.valueOf(distance));
        requestAcIntent.putExtra("time", String.valueOf(time));
        if(!dri_phone_re_req.equals("")) {
            requestAcIntent.putExtra("dri_phone_re_req", dri_phone_re_req);
        }
        startActivity(requestAcIntent);


        postData.put("distance", distance);

        postData.put("fare", fare);
        postData.put("car_type",carType);
        postData.put("car_type_id",car_type_id);
        postData.put("customer_phone",Config.getInstance().getPhone());
        postData.put("fcm_token",fcm_tocken_fr);
        postData.put("customer_name",Config.getInstance().getName());
        postData.put("customer_photo",Config.getInstance().getProfilePhoto());
        postData.put("customer_location",sourceBean.getAddress());
        postData.put("customer_latitude",sourceBean.getDLatitude());
        postData.put("customer_longitude",sourceBean.getDLongitude());
        postData.put("destination_location",destinationBean.getAddress());
        postData.put("destination_latitude",destinationBean.getDLatitude());
        postData.put("destination_longitude",destinationBean.getDLongitude());
        postData.put("dri_phone_re_req",dri_phone_re_req);

        */

        OnedayRequestModel onedayReqData = new OnedayRequestModel();
        onedayReqData.setDistance(String.valueOf(distance));
        onedayReqData.setTime(String.valueOf(time));
        onedayReqData.setFare(fareBeanAfterCalculate.getOnedayHireHiaceFare());
        onedayReqData.setCar_type("3");
        onedayReqData.setCar_type_id("8");
        onedayReqData.setCustomer_phone(Config.getInstance().getPhone());
        onedayReqData.setFcm_token(fcm_tocken_fr);
        onedayReqData.setCustomer_name(Config.getInstance().getName());
        onedayReqData.setCustomer_photo(Config.getInstance().getProfilePhoto());
        onedayReqData.setCustomer_location(sourceBean.getAddress());
        onedayReqData.setCustomer_latitude(sourceBean.getLatitude());
        onedayReqData.setCustomer_longitude(sourceBean.getLongitude());
        onedayReqData.setDestination_location(destinationBean.getAddress());
        onedayReqData.setDestination_latitude(destinationBean.getLatitude());
        onedayReqData.setDestination_longitude(destinationBean.getLongitude());

        if(!dri_phone_re_req.equals("")) {
           // requestAcIntent.putExtra("dri_phone_re_req", dri_phone_re_req);
            onedayReqData.setDri_phone_re_req(dri_phone_re_req);
        }

        networkCall.sendOndeDayReq(onedayReqData, new ResponseCallback<OnedayRequestResponse>() {
            @Override
            public void onSuccess(OnedayRequestResponse data) {
                doneProgressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),"Your request has sent.",Toast.LENGTH_LONG).show();
                Intent onedayRequestAc = new Intent(LandingPageActivity.this, OneDayReq.class);
                startActivity(onedayRequestAc);
                finish();

            }

            @Override
            public void onError(Throwable th) {
                doneProgressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),"Something Wrong!!!",Toast.LENGTH_LONG).show();
                Intent onedayRequestAc = new Intent(LandingPageActivity.this, LandingPageActivity.class);
                startActivity(onedayRequestAc);

            }
        });



    }







    public void onPlotLocation(boolean isMarkerNeeded, int type, double latitude, double longitude) {

        LandingPageBean landingPageBean = new LandingPageBean();
        ArrayList<CarBean> carBeanArrayList =landingPageBean.getCars();
        LatLng newLatLng = null;
        try {
            newLatLng = new LatLng(latitude, longitude);
            if (isMarkerNeeded) {
                switch (type) {
                    case LOCATION_SOURCE:
                        mMap.clear();
                        mMap.addMarker(new MarkerOptions().position(newLatLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.pickupnewicone)));
                        break;
                    case LOCATION_DESTINATION:
                        mMap.clear();
                        mMap.addMarker(new MarkerOptions().position(newLatLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.droffoff)));
                        break;
                    default:
                        mMap.addMarker(new MarkerOptions().position(newLatLng).icon(BitmapDescriptorFactory.defaultMarker()));
                        break;
                }
            }


           mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(newLatLng, 15));
            isCameraMoved =false;
            Log.i(TAG, "onPlotLocation: Position" + newLatLng);

        } catch (NumberFormatException e) {
            e.printStackTrace();

        }


    }


    public void fetchPolyPoints(final boolean isPolyLineNeeded,final boolean isFareCalNeed) {

        HashMap<String, String> urlParams = new HashMap<>();
        urlParams.put("origin", sourceBean.getLatitude() + "," + sourceBean.getLongitude());
        urlParams.put("destination", destinationBean.getLatitude() + "," + destinationBean.getLongitude());
        urlParams.put("mode", "driving");
        urlParams.put("key", getString(R.string.browser_api_key));

        DataManager.fetchPolyPoints(urlParams, new PolyPointsListener() {

            @Override
            public void onLoadCompleted(PolyPointsBean polyPointsBeanWS) {
                swipeView.setRefreshing(false);

                polyPointsBean = polyPointsBeanWS;
                time = String.valueOf(polyPointsBean.getTime());
                distance = String.valueOf(polyPointsBean.getDistance());
                Log.i(TAG, "onLoadCompleted: Time Taken" + polyPointsBean.getTimeText());
                Log.i(TAG, "onLoadCompleted: Distance" + polyPointsBean.getDistanceText());
                /*
                Float distancedinroundfigur=1f;
                if(polyPointsBean.getDistanceText().length()>3) {
                    distancedinroundfigur = Float.parseFloat(polyPointsBean.getDistanceText().substring(0, polyPointsBean.getDistanceText().length() - 3));

                }
                float distancefloat = distancedinroundfigur;
                */
                happyyFareWithService.setVisibility(View.VISIBLE);
                doneProgressBar.setVisibility(View.GONE);
                if(isFareCalNeed) {
                    float distanceInKm=Math.round(polyPointsBean.getDistance()/1000);
                    float timeinminute = Math.round(polyPointsBean.getTime()/60);
                    JSONObject postData = getJsonData(distanceInKm ,timeinminute);
                    DataManager.fetchTotalFare(postData, new TotalFareListener() {
                        @Override
                        public void onLoadCompleted(FareBean fareBeanWs) {

                            if (fareBeanAfterCalculate == null)
                                fareBeanAfterCalculate = new FareBean();
                            fareBeanAfterCalculate = fareBeanWs;
                            bikeFareTextView.setText("৳" + fareBeanAfterCalculate.getBikeFare());
                            cngFareTextView.setText("৳" + fareBeanAfterCalculate.getCngFare());
                            carFareTextView.setText("৳" + fareBeanAfterCalculate.getCarFare());
                            ambulanceFareTextView.setText("৳" + fareBeanAfterCalculate.getAmbulanceFare());
                            oneHourFareTextView.setText("৳" + fareBeanAfterCalculate.getCarHireFare());
                            dayFareTextView.setText("৳" + fareBeanAfterCalculate.getOndayHirePremioFare());
                            dayNoahFareTextView.setText("৳" + fareBeanAfterCalculate.getOnedayHireNoahFare());
                            dayHiaceFareTextView.setText("৳" + fareBeanAfterCalculate.getOnedayHireHiaceFare());

                            happyyFareWithService.setVisibility(View.VISIBLE);
                            doneProgressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onLoadFailed(String error) {

                        }
                    });
                }

                if (isPolyLineNeeded) {
                   // if (!isDestinationEstimateSelect)
                        populatePath();
                }

            }

            @Override
            public void onLoadFailed(String error) {
                swipeView.setRefreshing(false);
                Log.i(TAG, "onLoadFailed: POLYPOINTS : ");
                Snackbar.make(coordinatorLayout, error, Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            }
        });
    }
    public JSONObject getJsonData(float distanceInKm,float timeinminute) {
        JSONObject jsonData =new JSONObject();

        try {
            jsonData.put("distanceInKm",distanceInKm);
            jsonData.put("timeinminute",timeinminute);
            jsonData.put("promo_code","hr123");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonData;
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

        //  mMap.addMarker(new MarkerOptions())

    }

    public void mapAutoZoom() {


        if (sourceBean != null && destinationBean != null) {
            newLatLng1 = new LatLng(sourceBean.getDLatitude(), sourceBean.getDLongitude());
            newLatLng2 = new LatLng(destinationBean.getDLatitude(), destinationBean.getDLongitude());
        }

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(newLatLng1);
        builder.include(newLatLng2);
        bounds = builder.build();


        //   mMap.setPadding(0, (int) (height - getStatusBarHeight() - mActionBarHeight - (px * 160)), 0, (int) (height - getStatusBarHeight() - mActionBarHeight - (px * 120)));

//        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, (int) (40 * px)));

        if (mapFragment.getView() != null) {
            if (mapFragment.getView().getHeight() > 150 * px)
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, (int) (80 * px))); //20
            else
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, (int) (5 * px))); //5
        }


        //  mMap.setPadding(20,75,20,150);

    }

    public void onLayoutClickLandingPage(View view) {

        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }

    public int getNavBarHeight() {
        Context context = App.getInstance().getApplicationContext();
        int result = 0;
        int resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");

        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
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
    int onece =0;
    @Override
    public void onLocationChanged(Location location) {
        Log.i(TAG, "onLocationChanged: >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.");
        if ((Config.getInstance().getCurrentLatitude() == null || Config.getInstance().getCurrentLongitude() == null)
                || (Config.getInstance().getCurrentLatitude().equals("") || Config.getInstance().getCurrentLatitude().equals(""))) {
            Config.getInstance().setCurrentLatitude("" + location.getLatitude());
            Config.getInstance().setCurrentLongitude("" + location.getLongitude());
        } else {
            Config.getInstance().setCurrentLatitude("" + location.getLatitude());
            Config.getInstance().setCurrentLongitude("" + location.getLongitude());
        }

        if(CurrentMarker!=null){
            CurrentMarker.remove();
        }

        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(latLng);
        markerOption.title(" ");
        // markerOption.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

      //  markerOption.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_source_old));
      //  CurrentMarker = mMap.addMarker(markerOption);
        if(onece==0) {
            CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(15).build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            onece = 1;
        }

        Log.i(TAG, "onLocationChanged: LATITUDE : " + location.getLatitude());
        Log.i(TAG, "onLocationChanged: LONGITUDE : " + location.getLongitude());





        if (isInit) {
            getData();
            isInit = false;
        }
        if (sourceBean == null && mMap != null) {
            LastLocation = location;
            // displayLocation();
        }

        if (destinationBean == null && mMap != null) {
            LastLocation = location;
            // displayLocation();
        }
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
//        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult arg0) {
    }

    @Override
    public void onConnected(Bundle arg0) {
        try {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if (!checkForLocationPermissions())
                    getLocationPermissions();
                checkLocationSettingsStatus();
            } else {
                LocationServices.getFusedLocationProviderClient(this);
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
                //	mGoogleApiClient.requestLocationUpdates(mLocationRequest,HomeActivity.this);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onConnectionSuspended(int arg0) {

    }

    private static final int SPLASH_TIME = 3000;

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
/*
    int doubleBackToExitPressed = 1;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressed == 2) {
            finishAffinity();
            System.exit(0);
        }
        else {
            doubleBackToExitPressed++;
            Toast.makeText(this, "Please press Back again to exit", Toast.LENGTH_SHORT).show();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressed=1;
            }
        }, 2000);
    }
    */

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

    private class PlaceListTask extends AsyncTask<String, Integer, ArrayList<AutocompletePrediction>> {

        private String strAddress;
        private double mLatitude;
        private double mLongitude;

        public PlaceListTask(double mLatitude, double mLongitude) {
            super();
            this.mLatitude = mLatitude;
            this.mLongitude = mLongitude;

        }

        public double getmLatitude() {
            return mLatitude;
        }

        public void setmLatitude(double mLatitude) {
            this.mLatitude = mLatitude;
        }

        public double getmLongitude() {
            return mLongitude;
        }

        public void setmLongitude(double mLongitude) {
            this.mLongitude = mLongitude;
        }


        public String getStrAddress() {
            return strAddress;
        }

        public void setStrAddress(String strAddress) {
            this.strAddress = strAddress;
        }

        @Override
        protected ArrayList<AutocompletePrediction> doInBackground(String... params) {
            mGoogleApiClient = new GoogleApiClient
                    .Builder(LandingPageActivity.this)
                    .addApi(Places.GEO_DATA_API)
                    .addApi(Places.PLACE_DETECTION_API)
                    .enableAutoManage(LandingPageActivity.this, LandingPageActivity.this)
                    .build();
           // LatLngBounds BOUNDS_OF_DHAKA_CITY = new LatLngBounds(new LatLng(23.661270,90.329547), new LatLng(23.900002,90.509105));
            LatLngBounds selectedLocation = new LatLngBounds(new LatLng(mLatitude,mLongitude), new LatLng(mLatitude,mLongitude));

            if (mGoogleApiClient.isConnected()) {
                Log.i(TAG, "Starting autocomplete query for: " + strAddress);
                AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                        .setCountry("BD")
                        .build();

                // Submit the query to the autocomplete API and retrieve a PendingResult that will
                // contain the results when the query completes.
                PendingResult<AutocompletePredictionBuffer> results =
                        Places.GeoDataApi
                                .getAutocompletePredictions(mGoogleApiClient, null, selectedLocation,
                                        typeFilter);

                // This method should have been called off the main UI thread. Block and wait for at most 60s
                // for a result from the API.
                AutocompletePredictionBuffer autocompletePredictions = results
                        .await(60, TimeUnit.SECONDS);

                // Confirm that the query completed successfully, otherwise return null
                final com.google.android.gms.common.api.Status status = autocompletePredictions.getStatus();
                if (!status.isSuccess()) {
//                Toast.makeText(getContext(), "Error contacting API: " + status.toString(),
//                        Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Error getting autocomplete prediction API call: " + status.toString());
                    autocompletePredictions.release();
                    return null;
                }

                Log.i(TAG, "Query completed. Received " + autocompletePredictions.getCount()
                        + " predictions.");

                // Freeze the results immutable representation that can be stored safely.
                return DataBufferUtils.freezeAndClose(autocompletePredictions);
            }
            Log.e(TAG, "Google API client is not connected for autocomplete query.");
            return null;

        }

        @Override
        protected void onPostExecute(ArrayList<AutocompletePrediction> result) {
            super.onPostExecute(result);

            if (result != null) {

                if(isLocNamFoAtu) {
                    if (result.size()>0) {
                        String address = result.get(0).getPrimaryText(null)+","+result.get(0).getFullText(null);
                        txtSource.setText(locNamFoAuto+","+address);
                        System.out.println("Location Name Retrieved : " + address);
                        Config.getInstance().setCurrentLocation(locNamFoAuto+","+address);

                        //   txtActionSearch.setText(address);

                        // txtDestination.setText(address);
                        if (sourceBean == null) {
                            sourceBean = new PlaceBean();
                        }
                        sourceBean.setAddress(locNamFoAuto+","+address);
                        sourceBean.setName(locNamFoAuto+","+address);
                        // sourceBean.setLatitude(latitude);
                        // sourceBean.setLongitude(longitude);


                    }
                    isLocNamFoAtu=false;
                    locNamFoAuto="";
                }else{
                    if (result.size()>0) {
                        String address = result.get(0).getPrimaryText(null)+","+result.get(0).getFullText(null);
                        txtSource.setText(address);
                        System.out.println("Location Name Retrieved : " + address);
                        Config.getInstance().setCurrentLocation(address);

                        //   txtActionSearch.setText(address);

                        // txtDestination.setText(address);
                        if (sourceBean == null) {
                            sourceBean = new PlaceBean();
                        }
                        sourceBean.setAddress(address);
                        sourceBean.setName(address);
                        // sourceBean.setLatitude(latitude);
                        // sourceBean.setLongitude(longitude);


                    }
                }

            } else {
               // swipeView.setRefreshing(false);

            }
        }
    }
 private class  BackGrounForLoca extends AsyncTask {
        double lat;
        double lon;
        Context myContex;
     public BackGrounForLoca(double lat,double lon,Context myContex) {
         this.lat = lat;
         this.lon = lon;
         this.myContex = myContex;
     }

     @Override
     protected void onPreExecute() {
         super.onPreExecute();


     }

     @Override
     protected Object doInBackground(Object[] objects) {
         Geocoder geocoder = new Geocoder(myContex, Locale.getDefault());
         String result = null;
         try {
             List < Address > addressList = geocoder.getFromLocation(lat, lon, 1);
             if (addressList != null && addressList.size() > 0) {
                 Address address = addressList.get(0);

                        /*
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                            sb.append(address.getAddressLine(i)); //.append("\n");
                        }
                        sb.append(address.getFeatureName()).append(",");
                        sb.append(address.getLocality()).append(",");
                        sb.append(address.getPostalCode()).append(",");
                        result = sb.toString();
                        */
                // String knownName = address.getFeatureName();
                 String addresslineee = address.getAddressLine(0);
                // String subLocality = address.getSubLocality();
                // String city = address.getLocality();
                 // String state = address.getAdminArea();
                 // String country = address.getCountryName();
                // String postalCode = address.getPostalCode();
                 result = addresslineee+".";

             }
         } catch (IOException e) {
             Log.e("Location Address Loader", "Unable connect to Geocoder", e);
         } finally {

         }
         return result;
     }

     @Override
     protected void onPostExecute(Object o) {
         super.onPostExecute(o);
         String result="";
         if(o!=null){
             result =o.toString();
         }else {
             result ="Unknown Place";
         }
         if(isLocNamFoAtu) {
             if (null != result) {
                 txtSource.setText(locNamFoAuto+","+result);
                 System.out.println("Location Name Retrieved : " + result);
                 Config.getInstance().setCurrentLocation(locNamFoAuto+","+result);

                 //   txtActionSearch.setText(address);

                 // txtDestination.setText(address);
                 if (sourceBean == null) {
                     sourceBean = new PlaceBean();
                 }
                 sourceBean.setAddress(locNamFoAuto+","+result);
                 sourceBean.setName(locNamFoAuto+","+result);
                 // sourceBean.setLatitude(latitude);
                 // sourceBean.setLongitude(longitude);


             }
             isLocNamFoAtu=false;
             locNamFoAuto="";
         }else {

             txtSource.setText(result);

             Config.getInstance().setCurrentLocation(result);

             //   txtActionSearch.setText(address);

             // txtDestination.setText(address);
             if (sourceBean == null) {
                 sourceBean = new PlaceBean();
             }
             sourceBean.setAddress(result);
             sourceBean.setName(result);

         }
     }
 }


    private class  BackGrounForDes extends AsyncTask {
        double lat;
        double lon;
        Context myContex;
        public BackGrounForDes(double lat,double lon,Context myContex) {
            this.lat = lat;
            this.lon = lon;
            this.myContex = myContex;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected Object doInBackground(Object[] objects) {
            Geocoder geocoder = new Geocoder(myContex, Locale.getDefault());
            String result = null;
            try {
                List < Address > addressList = geocoder.getFromLocation(lat, lon, 1);
                if (addressList != null && addressList.size() > 0) {
                    Address address = addressList.get(0);

                        /*
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                            sb.append(address.getAddressLine(i)); //.append("\n");
                        }
                        sb.append(address.getFeatureName()).append(",");
                        sb.append(address.getLocality()).append(",");
                        sb.append(address.getPostalCode()).append(",");
                        result = sb.toString();
                        */
                   // String knownName = address.getFeatureName();
                    String addresslineee = address.getAddressLine(0);
                    String subLocality = address.getSubLocality();
                   // String city = address.getLocality();
                    // String state = address.getAdminArea();
                    // String country = address.getCountryName();
                  //  String postalCode = address.getPostalCode();


                    result = addresslineee+".";


                }
            } catch (IOException e) {
                Log.e("Location Address Loader", "Unable connect to Geocoder", e);
            } finally {

            }
            return result;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            String result="";
            if(o!=null){
                 result =o.toString();
            }else {
                result ="Unknown Place";
            }


            if(isLocNamFoAtu) {

                if (null != result) {
                    txtDestination.setText(locNamFoAuto+","+result);
                    System.out.println("Location Name Retrieved : " + locNamFoAuto+","+result);
                    Config.getInstance().setCurrentLocation(locNamFoAuto+","+result);


                    if (destinationBean == null) {
                        destinationBean = new PlaceBean();
                    }
                    destinationBean.setAddress(locNamFoAuto+","+result);
                    // destinationBean.setName(address);
                    // destinationBean.setLatitude(latitude);
                    //  destinationBean.setLongitude(longitude);
                }
                isLocNamFoAtu=false;
                locNamFoAuto="";
            }else {
                txtDestination.setText(result);
                System.out.println("Location Name Retrieved : " + result);
                Config.getInstance().setCurrentLocation(result);


                if (destinationBean == null) {
                    destinationBean = new PlaceBean();
                }
                destinationBean.setAddress(result);
            }

        }
    }
    private class BackgroundTask extends AsyncTask {

        Intent intent;
        Geocoder geocoder = new Geocoder(getApplicationContext());
       List<Address>  addresses;

        {
            try {
                addresses = geocoder.getFromLocation(37.42279, -122.08506,1);
               Address add= addresses.get(0);
               add.getFeatureName();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            intent = new Intent(LandingPageActivity.this, ShowVehiclesOnMapActivity.class);
        }

        @Override
        protected Object doInBackground(Object[] params) {

            /*  Use this method to load background
             * data that your app needs. */

            try {
                Thread.sleep(SPLASH_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
//            Pass your loaded data here using Intent

//            intent.putExtra("data_key", "");
            startActivity(intent);
            // finish();
        }
    }
}




