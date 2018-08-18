package ride.happyy.user;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import ride.happyy.user.R;

import ride.happyy.user.mainFragment.HourlyPackageFragment;
import ride.happyy.user.mainFragment.InDhakaCityFragment;
import ride.happyy.user.mainFragment.OutOfDhakaCityFragment;
import ride.happyy.user.menuFragments.AboutFragment;
import ride.happyy.user.menuFragments.GetDiscountFragment;
import ride.happyy.user.menuFragments.HelpFragment;
import ride.happyy.user.menuFragments.HistoryFragment;
import ride.happyy.user.menuFragments.PromotionsFragments;
import ride.happyy.user.menuFragments.SettingsFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    LinearLayout linearLayout;
    Button inDhakaCity,outOfDhakaCity,hourlyPackage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onStart() {
        super.onStart();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        linearLayout =findViewById(R.id.linearLayout);
        inDhakaCity =findViewById(R.id.inDhakaCity);
        outOfDhakaCity=findViewById(R.id.outOfDhakaCity);
        hourlyPackage=findViewById(R.id.hourlyPackage);


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        inDhakaCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displaySelectedScreen(v.getId());
            }
        });
        outOfDhakaCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displaySelectedScreen(v.getId());
            }
        });
        hourlyPackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displaySelectedScreen(v.getId());
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

//        if (id == R.id.history) {
//            // Handle the camera action
//

       displaySelectedScreen(id);
        return true;
    }
    Fragment fragment = null;
    private void displaySelectedScreen(int id){
        switch (id){
            case R.id.homePage:
               // fragment = getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT);
                if(fragment != null){
                    linearLayout.setVisibility(View.VISIBLE);
                    getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                }
                break;
            case R.id.history:
                linearLayout.setVisibility(View.GONE);
              //  fragment =new HistoryFragment();
                break;
            case R.id.promotions:
                linearLayout.setVisibility(View.GONE);
               // fragment =new PromotionsFragments();
                break;
            case R.id.get_discount:
                linearLayout.setVisibility(View.GONE);
              // fragment =new GetDiscountFragment();
                break;

            case R.id.setting:
                linearLayout.setVisibility(View.GONE);
               // fragment =new SettingsFragment();
                break;
            case R.id.help:
                linearLayout.setVisibility(View.GONE);
                fragment =new HelpFragment();
                break;
            case R.id.about:
                linearLayout.setVisibility(View.GONE);
              //  fragment =new AboutFragment();
                break;
            case R.id.inDhakaCity:
                linearLayout.setVisibility(View.GONE);
               // fragment =new InDhakaCityFragment();
                break;
            case R.id.outOfDhakaCity:
                linearLayout.setVisibility(View.GONE);
               // fragment =new OutOfDhakaCityFragment();
                break;
            case R.id.hourlyPackage:
                linearLayout.setVisibility(View.GONE);
               // fragment =new HourlyPackageFragment();
                break;


        }
        //else if (id == R.id.promotions) {
//
//        } else if (id == R.id.get_discount) {
//
//        } else if (id == R.id.notifications) {
//
//        } else if (id == R.id.setting) {
//
//        } else if (id == R.id.help) {
//
//        }else if (id == R.id.english) {
//
//        }
        if(id != R.id.homePage){
            if (fragment != null)getSupportFragmentManager().beginTransaction().replace(R.id.containFrameLayout,fragment).commit();
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);


        }else {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }

    }

}
