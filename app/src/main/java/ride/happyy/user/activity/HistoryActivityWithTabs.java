package ride.happyy.user.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ride.happyy.user.R;
import ride.happyy.user.menuFragments.historyFragments.BikeFragment;
import ride.happyy.user.menuFragments.historyFragments.CarFragment;
import ride.happyy.user.menuFragments.historyFragments.CngFragment;
import ride.happyy.user.menuFragments.historyFragments.AmbulanceFragment;

public class HistoryActivityWithTabs extends BaseAppCompatNoDrawerActivity {
    private static final String BIKE_TAB = "Bike";
    private static final String CNG_TAB = "CNG";
    private static final String CAR_TAB = "Car";
    private static final String AMBULANCE_TAB = "Ambulance";

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.ic_bike_trip,
            R.drawable.cng,
            R.drawable.carhirelogotime,
            R.drawable.ambulanc_png
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_with_tabs);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("TRIP HISTORY");

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }

    private void setupTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tabs, null);
        tabOne.setText("BIKE");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_history_red_24dp, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tabs, null);
        tabTwo.setText("CNG");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_history_red_24dp, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tabs, null);
        tabThree.setText("CAR");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_history_red_24dp, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);
        TextView tabFour= (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tabs, null);
        tabFour.setText("AMBULANCE");
        tabFour.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_history_red_24dp, 0, 0);
        tabLayout.getTabAt(3).setCustomView(tabFour);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new BikeFragment(), "BIKE");
        adapter.addFrag(new CngFragment(), "CNG");
        adapter.addFrag(new CarFragment(), "CAR");
        adapter.addFrag(new AmbulanceFragment(), "AMBUL.");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }



}
