package ride.happyy.user.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import ride.happyy.user.R;
import ride.happyy.user.adapter.CarTypeRecyclerAdapter;
import ride.happyy.user.app.App;
import ride.happyy.user.config.Config;
import ride.happyy.user.dialogs.PopupMessage;
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
import ride.happyy.user.model.CarBean;
import ride.happyy.user.model.DriverBean;
import ride.happyy.user.model.FareBean;
import ride.happyy.user.model.LandingPageBean;
import ride.happyy.user.model.PlaceBean;
import ride.happyy.user.model.PolyPointsBean;
import ride.happyy.user.model.UserBean;
import ride.happyy.user.net.DataManager;
import ride.happyy.user.net.WSAsyncTasks.FCMRegistrationTask;
import ride.happyy.user.net.WSAsyncTasks.LocationNameTask;
import ride.happyy.user.net.WSAsyncTasks.LocationTask;
import ride.happyy.user.util.AppConstants;


public class LandingPageActivity extends BaseAppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMyLocationButtonClickListener, com.google.android.gms.location.LocationListener,
        android.location.LocationListener {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private static final int UPDATE_INTERVAL = 10000;
    private static final int FASTEST_INTERVAL = 5000;
    private static final int DISPLACEMENT = 10;
    private static final String TAG = "LandingPA";

    private static final LocationRequest mLocationRequest = LocationRequest.create()
            .setInterval(5000)         // 5 seconds
            .setFastestInterval(16)    // 16ms = 60fps
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQ_SEARCH_SOURCE_SELECT = 0;
    private static final int REQ_SEARCH_DESTINATION_SELECT = 1;
    private static final int REQ_SEARCH_DESTINATION_ESTIMATE_SELECT = 2;
    private static final int REQ_REQUEST_RIDE = 3;
    private static final int REQ_ESTIMATED_DESTINATION = 4;
    private static final int LOCATION_SOURCE = 0;
    private static final int LOCATION_DESTINATION = 1;

    private static GoogleMapOptions options = new GoogleMapOptions()
            .mapType(GoogleMap.MAP_TYPE_NORMAL)
            .compassEnabled(true)
            .rotateGesturesEnabled(true)
            .tiltGesturesEnabled(true)
            .zoomControlsEnabled(true)
            .scrollGesturesEnabled(true)
            .mapToolbarEnabled(true);

    //    private GoogleApiClient mGoogleApiClient;
    private Location LastLocation;
    private GoogleMap mMap;
    private Toolbar toolbarHome;
    private TextView txtActionSearch;
    private FrameLayout framePickup;
    private ImageView ivMarker;
    private ImageView ivBottomMarker;
    private LinearLayout llLandingBottomBar;
    private ImageView ivLocationButton;
    private SupportMapFragment mapFragment;
    //    private View lytBottom;
    private TextView txtTime;
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
    private FrameLayout flLandingPage;
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

    private ImageButton notificatinImageButton, menubarHome,outOfdhakaPage;
    private NotificationBadge mNotificationBadge;
    private int countNotification =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_landing_page);
        outOfdhakaPage = findViewById(R.id.btnForOutofDhakaPage);

        outOfdhakaPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent outOfDhakaIntent = new Intent(getApplicationContext(),OutOfDhakaActivity.class);
                startActivity(outOfDhakaIntent);

            }
        });
        isGetLocationEnabled = false;





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

        setProgressScreenVisibility(true, true);
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
                .addProfiles(new ProfileDrawerItem().withName("Rajib Hossain").withEmail("01623473041").withIcon(getResources().getDrawable(R.drawable.ic_profile_photo_default)))
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
        SecondaryDrawerItem item10 = new SecondaryDrawerItem().withIdentifier(10).withName("Diagnostic Test Offer").withIcon(R.drawable.ic_local_hospital_red_24dp);
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
                        Toast.makeText(getApplicationContext(), "HOME", Toast.LENGTH_SHORT).show();
                        Intent homeIntent = new Intent(getApplicationContext(),LandingPageActivity.class);
                        startActivity(homeIntent);
                        finish();
                        break;

                    case 2:
                        Intent historyIntent = new Intent(getApplicationContext(),HistoryActivityWithTabs.class);
                        startActivity(historyIntent);
                        Toast.makeText(getApplicationContext(), "History", Toast.LENGTH_SHORT).show();

                        break;
                    case 3:
                        Intent myPromIntent = new Intent(getApplicationContext(),PromotionActivity.class);
                        startActivity(myPromIntent);
                        Toast.makeText(getApplicationContext(), "Promotion", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Intent discountIntent = new Intent(getApplicationContext(),DiscountActivity.class);
                        startActivity(discountIntent);
                        Toast.makeText(getApplicationContext(), "Discount", Toast.LENGTH_SHORT).show();
                        break;
                    case 10:
                        Intent daignosticIntent = new Intent(getApplicationContext(),DiagnosticOfferRequestActivity.class);
                        startActivity(daignosticIntent);
                        Toast.makeText(getApplicationContext(), "Discount", Toast.LENGTH_SHORT).show();
                        break;
                    case 5:

                        Toast.makeText(getApplicationContext(), "Notification", Toast.LENGTH_SHORT).show();
                        item5.withName("Notifications").withBadge("0").withBadgeStyle(new BadgeStyle().withTextColor(Color.WHITE).withColorRes(R.color.red));
                        Intent notificationIntent = new Intent(getApplicationContext(),NotificationsActivity.class);
                        startActivity(notificationIntent);
                        break;
                    case 6:
                        Intent helpIntent = new Intent(getApplicationContext(),HelpSimpleActivity.class);
                        startActivity(helpIntent);
                        Toast.makeText(getApplicationContext(), "Help", Toast.LENGTH_SHORT).show();
                        break;
                    case 7:
                        Intent aboutIntent = new Intent(getApplicationContext(),AboutsActivit.class);
                        startActivity(aboutIntent);
                        Toast.makeText(getApplicationContext(), "About", Toast.LENGTH_SHORT).show();
                        break;
                    case 8:

                        Intent settingIntent = new Intent(getApplicationContext(),SettingsPageActivity.class);
                        startActivity(settingIntent);
                        Toast.makeText(getApplicationContext(), "Setting", Toast.LENGTH_SHORT).show();
                        break;
                    case 9:
                        Intent logoutIntent = new Intent(getApplicationContext(),WelcomeActivity.class);
                        startActivity(logoutIntent);
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
            if (isConfirmationPage) {
                onBackClick();
            } else {
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                } else {
                    onBackPressed();
                }
            }
        }

        if (keyCode == KeyEvent.KEYCODE_MENU) {
            openOptionsMenu();
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (checkForLocationPermissions()) {
            if (!isConfirmationPage && sourceBean == null) {
                if (checkPlayServices()) {
                    getCurrentLocation();
//            buildGoogleApiClient();
//            createLocationRequest();
                }
            }
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
            fetchLandingPageDetails();
        } else {
            setProgressScreenVisibility(true, false);
            Snackbar.make(coordinatorLayout, AppConstants.NO_NETWORK_AVAILABLE, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.btn_retry, snackBarRefreshOnClickListener).show();
        }
    }

    public void initViews() {





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



        snackBarRefreshOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                //  mVibrator.vibrate(25);
                getData();
            }
        };

       // btnRequest = (Button) findViewById(R.id.btn_request);

        rlFare = (RelativeLayout) findViewById(R.id.rl_fare);
        happyyFareWithService = findViewById(R.id.ll_fare_new);

        coordinatorLayout.removeView(toolbar);
//      toolbar.setVisibility(View.GONE);
        toolbarHome = (Toolbar) getLayoutInflater().inflate(R.layout.toolbar_landing_page, toolbar);
        coordinatorLayout.addView(toolbarHome, 0);
        setSupportActionBar(toolbarHome);


        rvCarTypes = (RecyclerView) findViewById(R.id.rv_bottom_sheet_landing_car_types);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvCarTypes.setLayoutManager(layoutManager);

        ivBottomMarker = (ImageView) findViewById(R.id.iv_bottom_marker);

        llConfirmationProgress = (LinearLayout) findViewById(R.id.ll_confirmation_progress);

        txtFareEstimate = (TextView) findViewById(R.id.txt_fare_estimate);
        txtTo = (TextView) findViewById(R.id.txt_to);
        llDestinationEstimated = (LinearLayout) findViewById(R.id.ll_destination_estimated);

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

        flLandingPage = (FrameLayout) findViewById(R.id.fl_landing_page);

       // framePickup = (FrameLayout) findViewById(R.id.frame_pickup_landing_page);
        ivMarker = (ImageView) findViewById(R.id.iv_marker);

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

        setBottomSheetBehavior();

        param1 = flLandingPage.getLayoutParams();
        param1.height = (int) (height - getStatusBarHeight() - mActionBarHeight);
        Log.i(TAG, "onSlide: PAram Height : " + param1.height);
        flLandingPage.setLayoutParams(param1);

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
    public void onClickDoneButton(View view){
        if (txtSource.getText().toString()==null){
            massage ="Please select sourch";

        }
        if (txtDestination.getText().toString()==null){
            massage ="Please select destination";
        }
        Toast.makeText(this,massage,Toast.LENGTH_SHORT).show();


        if(txtSource.getText().toString()!=null || txtDestination.getText().toString()!=null) {
            happyyFareWithService.setVisibility(View.VISIBLE);


        }
        mapAutoZoom();
        fetchPolyPoints(true);
      //  onDestinationSelect();
        ivLocationButton.setVisibility(View.GONE);
        ivBottomMarker.setVisibility(View.GONE);
        mMap.getUiSettings().setScrollGesturesEnabled(false);
        distinationConfirmButton.setVisibility(View.GONE);


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
                    param = mapFragment.getView().getLayoutParams();
                    param.height = (int) (height - getStatusBarHeight() - mActionBarHeight/* - (80 * px * (1 - slideOffset))*/ - bottomSheet.getHeight() * (slideOffset));
//                Log.i(TAG, "onSlide: PAram Height : " + param.height);
                    mapFragment.getView().setLayoutParams(param);

                    param1 = flLandingPage.getLayoutParams();
                    param1.height = (int) (height - getStatusBarHeight() - mActionBarHeight /*- (80 * px * (1 - slideOffset))*/ - bottomSheet.getHeight() * (slideOffset));
                    Log.i(TAG, "onSlide: PAram Height : " + param1.height);
                    flLandingPage.setLayoutParams(param1);
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
                mMap.setPadding(0, (int) ((100 * px) + mActionBarHeight + getStatusBarHeight()), 0, (int) (100 * px));



                initMapLoad();

            }
        });
    }
LatLng newLatLng=null;
    int onfirstLoad=0;
    private void initMapLoad() {

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {



                //recently change

             /*   if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED || bottomSheetBehavior.getPeekHeight() == 100 * px) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
                */
            }
        });

        mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {

            @Override
            public void onCameraMove() {

                /*if (sourceBean != null & destinationBean != null) {
                    fetchTotalfare();
                    txtFare.setText(fareBean.getTotalFare());
                }*/

                //Custom recently 14/08/2018
                /*
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED || bottomSheetBehavior.getPeekHeight() == 100 * px) {
                    bottomSheetBehavior.setPeekHeight(0);
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                } */

                if (!isConfirmationPage) {
                    mMap.getUiSettings().setScrollGesturesEnabled(true);
                    mMap.setMaxZoomPreference(18f);
                   // framePickup.setVisibility(View.INVISIBLE);
                    ivBottomMarker.setVisibility(View.VISIBLE);
                    ivMarker.setVisibility(View.VISIBLE);
                   // ivLocationButton.setVisibility(View.VISIBLE);
                    distinationConfirmButton.setVisibility(View.VISIBLE);

                    isCameraMoved = true;
                }
            }
        });


        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                onfirstLoad=1;

                if (sourceBean != null & destinationBean != null) {
                    if (!isConfirmationPage) {
                        fetchPolyPoints(false);
                    }
                    if (fareBean != null) {
                        txtFare.setText(fareBean.getTotalFare());
                    }
                }

                if (!isConfirmationPage) {

                    CameraPosition postion = mMap.getCameraPosition();
                    LatLng center = postion.target;

                    // framePickup.setVisibility(View.VISIBLE);
                    if(happyyFareWithService.getVisibility()==View.VISIBLE) {
                        ivBottomMarker.setVisibility(View.GONE);
                        //onfirstLoad =2;
                    }else {
                        ivBottomMarker.setVisibility(View.VISIBLE);

                    }

                    ivMarker.setVisibility(View.INVISIBLE);
                  //  ivLocationButton.setVisibility(View.VISIBLE);

                    if (bottomSheetBehavior.getPeekHeight() == 0) {
                        bottomSheetBehavior.setPeekHeight((int) (100 * px));
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//                        llLandingBottomBar.animate().translationY(00*px).setDuration(1000).start();
                    }

                    Log.i(TAG, "onCameraIdle: GetLocationName Called : " + center);
                    if (isCameraMoved) {
                        String lat = Config.getInstance().getCurrentLatitude();
                        String lomg = Config.getInstance().getCurrentLongitude();

                        getLocationName(Config.getInstance().getCurrentLatitude(), Config.getInstance().getCurrentLongitude());

                        if (sourceBean == null)
                            sourceBean = new PlaceBean();
                        sourceBean.setLatitude(Config.getInstance().getCurrentLatitude());
                        sourceBean.setLongitude(Config.getInstance().getCurrentLongitude());
                        newLatLng = new LatLng(sourceBean.getDLatitude(),sourceBean.getDLongitude());
                        mMap.clear();
                       MarkerOptions marker = new MarkerOptions().position(newLatLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.pickuplocationtest1));


                      mMap.addMarker(marker);

                if (marker!=null)
                          marker=null;
//
                   /*     MarkerOptions  markerOptionscurrect = new MarkerOptions().position(newLatLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_source_marker_old));
                        mMap.addMarker(markerOptionscurrect);
                        if(markerOptionscurrect!=null) {
                            markerOptionscurrect = null;
                        }
                        */


                        getLocationNameDestination(String.valueOf(center.latitude),String.valueOf(center.longitude));

                       // getLocationName(String.valueOf(center.latitude), String.valueOf(center.longitude));
//                        getLocationName(center.latitude, center.longitude);

                     /*   if (sourceBean == null)
                            sourceBean = new PlaceBean();
                        sourceBean.setLatitude(Config.getInstance().getCurrentLatitude());
                        sourceBean.setLongitude(Config.getInstance().getCurrentLongitude());
                        */

                        if (destinationBean == null)
                            destinationBean = new PlaceBean();
                        destinationBean.setLatitude(String.valueOf(center.latitude));
                        destinationBean.setLongitude(String.valueOf(center.longitude));

                        if (App.isNetworkAvailable()) {
                            fetchLandingPageDetails();
//                            fetchCarDetails();
                        } else {
                            Snackbar.make(coordinatorLayout, AppConstants.NO_NETWORK_AVAILABLE, Snackbar.LENGTH_LONG)
                                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
                        }

                        if (destinationBean != null) {
//                            getEstimatedFare();
                        }
                    }
                    isCameraMoved = false;
                }
            }
        });
    }


    private void initFCM() {

        FCMRegistrationTask fcmRegistrationTask = new FCMRegistrationTask();
        fcmRegistrationTask.setFCMRegistrationTaskListener(new FCMRegistrationTask.FCMRegistrationTaskListener() {
            @Override
            public void dataDownloadedSuccessfully(String fcmToken) {

                Log.i(TAG, "dataDownloadedSuccessfully: FCM TOKEN : " + fcmToken);

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
//            postData.put("user_id", userBean.getUserID());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return postData;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_SEARCH_DESTINATION_SELECT && resultCode == RESULT_OK) {

            destinationBean = (PlaceBean) data.getSerializableExtra("bean");

            if (sourceBean != null & destinationBean != null
                    && sourceBean.getName() != null & destinationBean.getName() != null) {
                if (sourceBean.getName().equalsIgnoreCase(destinationBean.getName())) {

                    mMap.clear();
                    destinationBean = null;
                    txtDestination.setText("");
                    rlFare.setVisibility(View.GONE);
                    happyyFareWithService.setVisibility(View.GONE);

                    onSourceSelect();

                    Snackbar.make(coordinatorLayout, R.string.message_source_and_destination_are_same, Snackbar.LENGTH_LONG)
                            .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();

                }
            }

            Log.i(TAG, "onActivityResult: ON DESTINATION SELECT ");

            fetchCarDetails();

            if (destinationBean != null) {
                llFare.setVisibility(View.GONE);
                happyyFareWithService.setVisibility(View.GONE);
                llConfirmationProgress.setVisibility(View.VISIBLE);
            }

            Log.i(TAG, "onActivityResult: SourceLatitude : " + sourceBean.getDLatitude());
            Log.i(TAG, "onActivityResult: SourceLongitude : " + sourceBean.getDLongitude());

//            Log.i(TAG, "onActivityResult: DestinationLatitude : " + destinationBean.getDLatitude());
//            Log.i(TAG, "onActivityResult: DestinationLongitude : " + destinationBean.getDLongitude());

            if (sourceBean != null && destinationBean != null) {
                onDestinationSelect();
            }
        }

        if (requestCode == REQ_SEARCH_SOURCE_SELECT && resultCode == RESULT_OK) {

            sourceBean = (PlaceBean) data.getSerializableExtra("bean");

            if (sourceBean != null && destinationBean != null) {
                if (sourceBean.getName().equalsIgnoreCase(destinationBean.getName())) {

                    mMap.clear();
                    destinationBean = null;
                    txtDestination.setText("");
                    rlFare.setVisibility(View.GONE);
                    happyyFareWithService.setVisibility(View.GONE);

                    Snackbar.make(coordinatorLayout, R.string.message_source_and_destination_are_same, Snackbar.LENGTH_LONG)
                            .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
                }
            }

            Log.i(TAG, "onActivityResult: SourceName" + sourceBean.getName());
//            Log.i(TAG, "onActivityResult: DestinationName" + destinationBean.getName());
            Log.i(TAG, "onActivityResult: SourceLatitude : " + sourceBean.getDLatitude());
            Log.i(TAG, "onActivityResult: SourceLongitude : " + sourceBean.getDLongitude());

            fetchCarDetails();
            if (sourceBean != null) {
                onSourceSelect();
            }
        }

        if (requestCode == REQ_REQUEST_RIDE && resultCode == RESULT_OK) {

            DriverBean driverBean = (DriverBean) data.getSerializableExtra("bean");
            startActivity(new Intent(this, OnTripActivity.class)
                    .putExtra("bean", driverBean)
                    .putExtra("source", sourceBean)
                    .putExtra("destination", destinationBean)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();

        }

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
    }


    private void onSourceSelect() {

        mMap.clear();
        txtSource.setText(sourceBean.getName());
        onPlotLocation(true, LOCATION_SOURCE, sourceBean.getDLatitude(), sourceBean.getDLongitude());
        try {
            if (destinationBean.getDLatitude() != 0 && destinationBean.getDLongitude() != 0) {
                onPlotLocation(true, LOCATION_DESTINATION, destinationBean.getDLatitude(), destinationBean.getDLongitude());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (sourceBean.getDLatitude() != 0 && sourceBean.getDLongitude() != 0 && destinationBean.getDLatitude() != 0 && destinationBean.getDLongitude() != 0) {
                rlFare.setVisibility(View.VISIBLE);
                happyyFareWithService.setVisibility(View.VISIBLE);
                ivBottomMarker.setVisibility(View.GONE);
                ivLocationButton.setVisibility(View.GONE);
                distinationConfirmButton.setVisibility(View.GONE);
                viewDottedLine.setVisibility(View.VISIBLE);
                ivMarker.setVisibility(View.GONE);
                mapAutoZoom();
                fetchPolyPoints(true);

                ///customise for testing purpose recently
                mMap.getUiSettings().setScrollGesturesEnabled(false);
                isCameraMoved=false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onDestinationSelect() {
        mMap.clear();
        onPlotLocation(true, LOCATION_SOURCE, sourceBean.getDLatitude(), sourceBean.getDLongitude());
        onPlotLocation(true, LOCATION_DESTINATION, destinationBean.getDLatitude(), destinationBean.getDLongitude());
        txtDestination.setText(destinationBean.getName());
        if (sourceBean.getDLatitude() != 0 && sourceBean.getDLongitude() != 0
                && destinationBean.getDLatitude() != 0 && destinationBean.getDLongitude() != 0) {

            rlFare.setVisibility(View.VISIBLE);
            happyyFareWithService.setVisibility(View.VISIBLE);
            ivBottomMarker.setVisibility(View.GONE);
            ivLocationButton.setVisibility(View.GONE);
            ivMarker.setVisibility(View.GONE);
            distinationConfirmButton.setVisibility(View.GONE);
            viewDottedLine.setVisibility(View.VISIBLE);
            mapAutoZoom();
            fetchPolyPoints(true);
            mMap.getUiSettings().setScrollGesturesEnabled(false);
            //customized recently for test
            isCameraMoved=false;
        }


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


    public void onLocationButtonClick(View view) {

        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        //mVibrator.vibrate(25);

        Log.i(TAG, "onLocationButtonClick: Clicked");

   //  displayLocation();

        sourceBean = null;
        if (mGoogleApiClient != null) {
            if (mGoogleApiClient.isConnected() || !mGoogleApiClient.isConnecting()) {

                getCurrentLocation();
            } else {
                mGoogleApiClient.connect();
            }
        } else {
            setUpLocationClientIfNeeded();
        }
    }

    public void onClickOutsideDhaka(View view){
        Intent myOutDhakaIntent = new Intent(this,OutOfDhakaActivity.class);
        startActivity(myOutDhakaIntent);
    }

    public void onFareEstimateClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        if (mMap != null) {
            LatLng center = mMap.getCameraPosition().target;
            if (sourceBean == null)
                sourceBean = new PlaceBean();
            sourceBean.setLatitude(String.valueOf(center.latitude));
            sourceBean.setLongitude(String.valueOf(center.longitude));
        }

        searchType = AppConstants.SEARCH_ESTIMATED_DESTINATION;

        Intent intent = new Intent(LandingPageActivity.this, SearchPageActivity.class);
        intent.putExtra("search_type", searchType);
        startActivityForResult(intent, REQ_ESTIMATED_DESTINATION);
    }


    public void layoutConfirmationPage() {

        mMap.getUiSettings().setAllGesturesEnabled(false);
        mMap.getUiSettings().setScrollGesturesEnabled(false);
        mMap.getUiSettings().setZoomGesturesEnabled(false);
        mMap.getUiSettings().setTiltGesturesEnabled(false);
        mMap.getUiSettings().setRotateGesturesEnabled(false);

        if (!isConfirmationPage) {
            bottomSheetBehavior.setPeekHeight(0);

            ivBottomMarker.setVisibility(View.GONE);

            ivMarker.setVisibility(View.GONE);

            ivLocationButton.setVisibility(View.GONE);

            // framePickup.setVisibility(View.GONE);

        }
    }

    public void onBackClick() {

        mMap.clear();

        fetchLandingPageDetails();

        try {
            ViewGroup.LayoutParams params = mapFragment.getView().getLayoutParams();
            params.height = height;
            mapFragment.getView().setLayoutParams(params);
        } catch (Exception e) {
            e.printStackTrace();
        }

        llConfirmationProgress.setVisibility(View.GONE);

        getCurrentLocation();
//        txtSource.setText("");
        txtDestination.setText("");

        txtFare.setVisibility(View.VISIBLE);

        rlFare.setVisibility(View.GONE);
        llFare.setVisibility(View.GONE);
        happyyFareWithService.setVisibility(View.GONE);

        viewDottedLine.setVisibility(View.GONE);

        Log.i(TAG, "onBackClick: >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        mMap.getUiSettings().setScrollGesturesEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setTiltGesturesEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(true);

        CameraPosition postion = mMap.getCameraPosition();
        LatLng center = postion.target;

        txtActionSearch.setText(Config.getInstance().getCurrentLocation());

        cvConfirmationPage.setVisibility(View.GONE);
//        rvCarList.setVisibility(View.VISIBLE);
        bottomSheetBehavior.setPeekHeight((int) (100 * px));
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

//        llEstimation.setVisibility(View.VISIBLE);
        // framePickup.setVisibility(View.VISIBLE);
        ivBottomMarker.setVisibility(View.VISIBLE);
        ivMarker.setVisibility(View.GONE);
        ivLocationButton.setVisibility(View.GONE);
        btnRequest.setVisibility(View.GONE);
        distinationConfirmButton.setVisibility(View.GONE);
        //Modified recently
      llConfirmation.setVisibility(View.GONE);

        mMap.clear();

        sourceBean = null;
        destinationBean = null;

        txtTo.setVisibility(View.GONE);
        llDestinationEstimated.setVisibility(View.GONE);
        txtFareEstimate.setVisibility(View.VISIBLE);

        isConfirmationPage = false;
    }


    public void onLaGoCarClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        carType = landingPageBean.getCars().get(0).getCarID();

        if (App.isNetworkAvailable()) {
            fetchCarDetails();
        } else {
            Snackbar.make(coordinatorLayout, AppConstants.NO_NETWORK_AVAILABLE, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
        }

        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }

        llProgressBar.setVisibility(View.VISIBLE);
        llEstimation.setVisibility(View.GONE);

        txtCarOne.setBackgroundResource(R.drawable.bg_round_edges);
        txtCarTwo.setBackgroundResource(R.color.transparent);
        txtCarThree.setBackgroundResource(R.color.transparent);
        carFour.setBackgroundResource(R.color.transparent);
        txtCarOne.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        txtCarTwo.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        txtCarThree.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        carFour.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));

        if (sourceBean != null & destinationBean != null) {
            fetchPolyPoints(false);
            if (fareBean != null) {
                txtFare.setText(fareBean.getTotalFare());
            }
            txtFareLabel.setText(R.string.label_estd_fare);
        }
    }

    public void onLaXCarClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        carType = landingPageBean.getCars().get(1).getCarID();

        if (App.isNetworkAvailable()) {
            fetchCarDetails();
        } else {
            Snackbar.make(coordinatorLayout, AppConstants.NO_NETWORK_AVAILABLE, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
        }

        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }

        llProgressBar.setVisibility(View.VISIBLE);
        llEstimation.setVisibility(View.GONE);

        txtCarTwo.setBackgroundResource(R.drawable.bg_round_edges);
        txtCarOne.setBackgroundResource(R.color.transparent);
        txtCarThree.setBackgroundResource(R.color.transparent);
        carFour.setBackgroundResource(R.color.transparent);
        txtCarTwo.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        txtCarOne.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        txtCarThree.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        carFour.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));

        if (sourceBean != null & destinationBean != null) {
            fetchPolyPoints(false);
            if (fareBean != null) {
                txtFare.setText(fareBean.getTotalFare());
            }
            txtFareLabel.setText(R.string.label_estd_fare);
        }
    }

    public void onCarXlClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        carType = landingPageBean.getCars().get(2).getCarID();

        if (App.isNetworkAvailable()) {
            fetchCarDetails();
        } else {
            Snackbar.make(coordinatorLayout, AppConstants.NO_NETWORK_AVAILABLE, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
        }

        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }

        llProgressBar.setVisibility(View.VISIBLE);
        llEstimation.setVisibility(View.GONE);

        txtCarThree.setBackgroundResource(R.drawable.bg_round_edges);
        txtCarOne.setBackgroundResource(R.color.transparent);
        txtCarTwo.setBackgroundResource(R.color.transparent);
        carFour.setBackgroundResource(R.color.transparent);
        txtCarThree.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        txtCarTwo.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        txtCarOne.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        carFour.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));

        if (sourceBean != null & destinationBean != null) {
            fetchPolyPoints(false);
            if (fareBean != null) {
                txtFare.setText(fareBean.getTotalFare());
            }
            txtFareLabel.setText(R.string.label_estd_fare);
        }
    }

    public void onCarXxlClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        carType = landingPageBean.getCars().get(3).getCarID();

        if (App.isNetworkAvailable()) {
            fetchCarDetails();
        } else {
            Snackbar.make(coordinatorLayout, AppConstants.NO_NETWORK_AVAILABLE, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
        }

        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }

        llProgressBar.setVisibility(View.VISIBLE);
        llEstimation.setVisibility(View.GONE);

        carFour.setBackgroundResource(R.drawable.bg_round_edges);
        txtCarOne.setBackgroundResource(R.color.transparent);
        txtCarTwo.setBackgroundResource(R.color.transparent);
        txtCarThree.setBackgroundResource(R.color.transparent);
        carFour.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        txtCarThree.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        txtCarTwo.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        txtCarOne.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));

        if (sourceBean != null & destinationBean != null) {
            fetchPolyPoints(false);
            if (fareBean != null) {
                txtFare.setText(fareBean.getTotalFare());
            }
            txtFareLabel.setText(R.string.label_estd_fare);
        }
    }


    public void onCarTypeSelected(int position, CarBean bean) {

        carType = bean.getCarID();

        if (App.isNetworkAvailable()) {
            fetchCarDetails();
            llProgressBar.setVisibility(View.VISIBLE);
            llEstimation.setVisibility(View.GONE);

            if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }

            if (sourceBean != null & destinationBean != null) {
                fetchPolyPoints(false);
                if (fareBean != null) {
                    txtFare.setText(fareBean.getTotalFare());
                }
                txtFareLabel.setText(R.string.label_estd_fare);
            }
        } else {
            Snackbar.make(coordinatorLayout, AppConstants.NO_NETWORK_AVAILABLE, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
        }

    }

    public void onSourceClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        searchType = AppConstants.SEARCH_SOURCE;

        Intent intent = new Intent(LandingPageActivity.this, SearchPageActivity.class);
        intent.putExtra("search_type", searchType);
        startActivityForResult(intent, REQ_SEARCH_SOURCE_SELECT);

    }

    public void onDestinationClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        searchType = AppConstants.SEARCH_DESTINATION;

        Intent intent = new Intent(LandingPageActivity.this, SearchPageActivity.class);
        intent.putExtra("search_type", searchType);
        startActivityForResult(intent, REQ_SEARCH_DESTINATION_SELECT);
    }


    private void displayLocation() {

        LatLng center = new LatLng(LastLocation.getLatitude(),LastLocation.getLongitude());
        LatLng northSide = SphericalUtil.computeOffset(center,100000,0);
        LatLng southSide = SphericalUtil.computeOffset(center,100000,180);

        Log.i(TAG, "displayLocation: OnPlotLocation Called .........>>>>>>>>>>>>>>>>>>>>>>>>..");

        if (LastLocation != null && !isConfirmationPage) {

            onPlotLocation(false, LOCATION_SOURCE, LastLocation.getLatitude(), LastLocation.getLongitude());
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

    protected void getLocationName(final String latitude, final String longitude) {

//        swipeView.setRefreshing(true);

        /*String currentLatitude = Config.getInstance().getCurrentLatitude();
        String currentLongitude = Config.getInstance().getCurrentLongitude();

        System.out.println("Current Location : " + currentLatitude + "," + currentLongitude);*/

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
                if (null != address) {
                    System.out.println("Location Name Retrieved : " + address);
                    Config.getInstance().setCurrentLocation(address);

                    txtActionSearch.setText(address);
                    txtSource.setText(address);
                   // txtDestination.setText(address);
                    if (sourceBean == null)
                        sourceBean = new PlaceBean();
                    sourceBean.setAddress(address);
                    sourceBean.setName(address);
                    sourceBean.setLatitude(latitude);
                    sourceBean.setLongitude(longitude);
                    /*					txtLocation.setText(address);
                    Toast.makeText(CreateActivity.this,"Location Name Retrieved : "+address, Toast.LENGTH_SHORT).show();
					 */
                }
            }

            @Override
            public void dataDownloadFailed() {

            }
        });
        locationNameTask.execute();
    }

    //for destination event

    protected void getLocationNameDestination(final String latitude, final String longitude) {

//        swipeView.setRefreshing(true);

        /*String currentLatitude = Config.getInstance().getCurrentLatitude();
        String currentLongitude = Config.getInstance().getCurrentLongitude();

        System.out.println("Current Location : " + currentLatitude + "," + currentLongitude);*/

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
                if (null != address) {
                    System.out.println("Location Name Retrieved : " + address);
                    Config.getInstance().setCurrentLocation(address);

                    txtActionSearch.setText(address);
                 //   txtSource.setText(address);
                    txtDestination.setText(address);
                    if (destinationBean == null)
                        destinationBean = new PlaceBean();
                    destinationBean.setAddress(address);
                    destinationBean.setName(address);
                    destinationBean.setLatitude(latitude);
                    destinationBean.setLongitude(longitude);
                    /*					txtLocation.setText(address);
                    Toast.makeText(CreateActivity.this,"Location Name Retrieved : "+address, Toast.LENGTH_SHORT).show();
					 */
                }
            }

            @Override
            public void dataDownloadFailed() {

            }
        });
        locationNameTask.execute();
    }

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
        urlParams.put("latitude", sourceBean.getLatitude());
        urlParams.put("longitude", sourceBean.getLongitude());

        DataManager.fetchCarAvailability(urlParams, new CarInfoListener() {
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

        DataManager.fetchTotalFare(urlParams, new TotalFareListener() {

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

        DataManager.fetchTotalFare(urlParams, new TotalFareListener() {

            @Override
            public void onLoadCompleted(FareBean fareBean) {
                swipeView.setRefreshing(false);
                populateEstimatedFare(fareBean);
            }

            @Override
            public void onLoadFailed(String error) {
                swipeView.setRefreshing(false);

            }
        });
    }

    public void populateEstimatedFare(FareBean fareBean) {

        txtFare.setText(fareBean.getTotalFare());
    }

    public void onEstimatedDestinationClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        onFareEstimateClick(view);
    }

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
        Toast.makeText(this,"No Bike Available",Toast.LENGTH_SHORT).show();
   }
//CNG onRequestRideClickCNG

    public void onRequestRideClickCNG(View view){
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        Toast.makeText(this,"No CNG Available",Toast.LENGTH_SHORT).show();
    }
   // onRequestRideClickCar
    public void onRequestRideClickCar(View view){
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        Toast.makeText(this,"No Car Available",Toast.LENGTH_SHORT).show();

    }
    public void onRequestRideClickCarOneHoure(View view){
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        Toast.makeText(this,"No Car Available",Toast.LENGTH_SHORT).show();
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
        Toast.makeText(this,"No Premio Car Available",Toast.LENGTH_SHORT).show();
    }
    public void onRequestRideClickCarDayNoah(View view){
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        Toast.makeText(this,"No Noah Car Available",Toast.LENGTH_SHORT).show();
    }
    public void onRequestRideClickCarHice(View view){
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        Toast.makeText(this,"No Hiace Car Available",Toast.LENGTH_SHORT).show();
    }

    public void onRequestRideClickAmbulance(View view){
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        Toast.makeText(this,"No Ambulance Available",Toast.LENGTH_SHORT).show();

    }


    public void fetchLandingPageDetails() {

        Log.i(TAG, "fetchLandingPageDetails: AuthToken" + Config.getInstance().getAuthToken());

        HashMap<String, String> urlParams = new HashMap<>();

        if (mMap != null) {
            LatLng center = mMap.getCameraPosition().target;
            urlParams.put("latitude", String.valueOf(center.latitude));
            urlParams.put("longitude", String.valueOf(center.longitude));
        } else {
            urlParams.put("latitude", Config.getInstance().getCurrentLatitude());
            urlParams.put("longitude", Config.getInstance().getCurrentLongitude());
        }
        DataManager.fetchLandingPageDetails(urlParams, new LandingPageListener() {

            @Override
            public void onLoadCompleted(LandingPageBean landingPageBeanWS) {
                swipeView.setRefreshing(false);
                setProgressScreenVisibility(false, false);
                landingPageBean = landingPageBeanWS;
                populateLandingPageDetails(landingPageBeanWS);

            }

            @Override
            public void onLoadFailed(String error) {
                swipeView.setRefreshing(false);
                setProgressScreenVisibility(true, false);
                Snackbar.make(coordinatorLayout, error, Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.btn_retry, snackBarRefreshOnClickListener).show();
            }
        });
    }

    private void populateLandingPageDetails(LandingPageBean landingPageBean) {


        Collections.sort(landingPageBean.getCars());

        /*CarBean bean1 = landingPageBean.getCars().get(0);
        CarBean bean2 = landingPageBean.getCars().get(1);
        CarBean bean3 = landingPageBean.getCars().get(2);
        CarBean bean4 = landingPageBean.getCars().get(3);

        txtCarOne.setText(bean1.getCarName());
        txtCarTwo.setText(bean2.getCarName());
        txtCarThree.setText(bean3.getCarName());
        carFour.setText(bean4.getCarName());

        Glide.with(getApplicationContext())
                .load(bean1.getCarImage())
                .into(carOneImage);

        Glide.with(getApplicationContext())
                .load(bean2.getCarImage())
                .into(carTwoImage);

        Glide.with(getApplicationContext())
                .load(bean3.getCarImage())
                .into(carThreeImage);

        Glide.with(getApplicationContext())
                .load(bean4.getCarImage())
                .into(carFourImage);*/


        if (adapterCarTypes == null) {

            adapterCarTypes = new CarTypeRecyclerAdapter(this, landingPageBean);
            adapterCarTypes.setCarTypeRecyclerAdapterListener(new CarTypeRecyclerAdapter.CarTypeRecyclerAdapterListener() {
                @Override
                public void onRefresh() {

                }

                @Override
                public void onSelectedCar(int position, CarBean carBean) {
                    carType = carBean.getCarID();
                    onCarTypeSelected(position, carBean);
                }
            });
            rvCarTypes.setAdapter(adapterCarTypes);
        } else {
            if (landingPageBean.getCars() != null && !landingPageBean.getCars().isEmpty()) {
                adapterCarTypes.setLandingPageBean(landingPageBean);
                adapterCarTypes.notifyDataSetChanged();
            } else {
              //  txtCarAvailability.setText(R.string.label_no_cars_available);
               // txtCarArrivalEstimatedTime.setVisibility(View.GONE);
            }
        }

        if (carType.equalsIgnoreCase("") || landingPageBean.getCar(carType) == null) {
            carType = landingPageBean.getCars() != null && !landingPageBean.getCars().isEmpty()
                    ? landingPageBean.getCars().get(0).getCarID() : "-1";
        }

        fetchCarDetails();
    }

    public void onPlotLocation(boolean isMarkerNeeded, int type, double latitude, double longitude) {

        LatLng newLatLng = null;
        try {
            newLatLng = new LatLng(latitude, longitude);
            if (isMarkerNeeded) {
                switch (type) {
                    case LOCATION_SOURCE:
                        mMap.addMarker(new MarkerOptions().position(newLatLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.pickuplocationtest1)));
                        break;
                    case LOCATION_DESTINATION:
                        mMap.addMarker(new MarkerOptions().position(newLatLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.dropofffinal)));
                        break;
                    default:
                        mMap.addMarker(new MarkerOptions().position(newLatLng).icon(BitmapDescriptorFactory.defaultMarker()));
                        break;
                }
            }
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(newLatLng, 17));
            Log.i(TAG, "onPlotLocation: Position" + newLatLng);

        } catch (NumberFormatException e) {
            e.printStackTrace();

        }
    }


    public void fetchPolyPoints(final boolean isPolyLineNeeded) {

        HashMap<String, String> urlParams = new HashMap<>();

//        if (sourceBean != null && destinationBean != null) {
        urlParams.put("origin", sourceBean.getLatitude() + "," + sourceBean.getLongitude());
        urlParams.put("destination", destinationBean.getLatitude() + "," + destinationBean.getLongitude());
        urlParams.put("mode", "driving");
        urlParams.put("key", getString(R.string.browser_api_key));
//        }

        DataManager.fetchPolyPoints(urlParams, new PolyPointsListener() {

            @Override
            public void onLoadCompleted(PolyPointsBean polyPointsBeanWS) {
                swipeView.setRefreshing(false);

                polyPointsBean = polyPointsBeanWS;
                time = String.valueOf(polyPointsBean.getTime());
                distance = String.valueOf(polyPointsBean.getDistance());

                Log.i(TAG, "onLoadCompleted: Time Taken" + polyPointsBean.getTimeText());
                Log.i(TAG, "onLoadCompleted: Distance" + polyPointsBean.getDistanceText());

                fetchTotalfare();

                if (isPolyLineNeeded) {
                    if (!isDestinationEstimateSelect)
                        populatePath();
                    isDestinationEstimateSelect = false;
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


        if (sourceBean != null && destinationBean != null) {
            newLatLng1 = new LatLng(sourceBean.getDLatitude(), sourceBean.getDLongitude());
            newLatLng2 = new LatLng(destinationBean.getDLatitude(), destinationBean.getDLongitude());
        }

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(newLatLng1);
        builder.include(newLatLng2);
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

    protected void getCurrentLocation() {

        setUpLocationClientIfNeeded();
        if (!mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        }
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (!checkForLocationPermissions())
                getLocationPermissions();
            checkLocationSettingsStatus();
        } else {
            if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {


                if (LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient) != null) {
                    Config.getInstance().setCurrentLatitude(""
                            + LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient).getLatitude());
                    Config.getInstance().setCurrentLongitude(""
                            + LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient).getLongitude());
//                    getLocationName();
                }
            }
            /*else{
                System.out.println("Last Location : " + mockLocation);
				currentLatitude = ""+mockLocation.getLatitude();
				currentLongitude = ""+mockLocation.getLongitude();
			}*/

            if ((Config.getInstance().getCurrentLatitude() == null || Config.getInstance().getCurrentLongitude() == null)
                    || (Config.getInstance().getCurrentLatitude().equals("") || Config.getInstance().getCurrentLatitude().equals(""))) {
//            Toast.makeText(BaseAppCompatActivity.this, "Retrieving Current Location...", Toast.LENGTH_SHORT).show();
                LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                } else {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
                }
            } else {
                if (isInit) {
                    getData();
                    isInit = false;
                }
                /*if (mGoogleApiClient != null) {
                    mGoogleApiClient.disconnect();
                }*/
            }

            //			mHandler.postDelayed(periodicTask, 3000);
        }
    }

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




        if (isInit) {
            getData();
            isInit = false;
        }
        if (sourceBean == null && mMap != null) {
            LastLocation = location;
            displayLocation();
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
}




