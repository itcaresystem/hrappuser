package ride.happyy.user.menuFragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ride.happyy.user.R;
import ride.happyy.user.menuFragments.historyFragments.BikeFragment;
import ride.happyy.user.menuFragments.historyFragments.CarFragment;
import ride.happyy.user.menuFragments.historyFragments.CngFragment;
import ride.happyy.user.menuFragments.historyFragments.AmbulanceFragment;


public class HistoryFragment extends Fragment {

    ViewPager viewPager;
    TabLayout tab_layout;
    View view;
    CustomPagerAdapter customPagerAdapter;
    private static final String SELECT_NUMBER="selected_number";
    public HistoryFragment() {
        // Required empty public constructor
    }

//    public  static HistoryFragment newInstance(int sectionNumber){
//        HistoryFragment fragment=new HistoryFragment();
//        Bundle args = new Bundle();
//        args.putInt(SELECT_NUMBER,sectionNumber);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_history_menu, container, false);
        viewPager = view.findViewById(R.id.viewpager);
        tab_layout = view.findViewById(R.id.tab_layout);
        customPagerAdapter = new CustomPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(customPagerAdapter);
        tab_layout.setupWithViewPager(viewPager);
        return view;
    }

    class CustomPagerAdapter extends FragmentPagerAdapter {

        public CustomPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new BikeFragment();
                case 1:
                    return new CarFragment();
                case 2:
                    return new CngFragment();
                case 3:
                    return new AmbulanceFragment();
            }
              return null;
        }

        @Override
        public int getCount() {
            return 4;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Bike";
                case 1:
                    return "Car";
                case 2:
                    return "Food";
                case 3:
                    return "Parcel";
            }
              return null;
        }
    }



}
