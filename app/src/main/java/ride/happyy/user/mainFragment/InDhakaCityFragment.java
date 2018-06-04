package ride.happyy.user.mainFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ride.happyy.user.R;

public class InDhakaCityFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private final static String TARGET_IN_DHAKA_CITY ="In_Dhaka_City";
    private final static String TARGET_OUT_OF_DHAKA_CITY ="Out_Of_Dhaka";
    private final static String TARGET_HOURLY_PACKAGE="package";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button outOfDhakaCityInDhaka,hourlyPackageInDhaka;


    public InDhakaCityFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static InDhakaCityFragment newInstance(String param1, String param2) {
        InDhakaCityFragment fragment = new InDhakaCityFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_in_dhaka_city, container, false);
        outOfDhakaCityInDhaka =view.findViewById(R.id.outOfDhakaCityInDhaka);
        hourlyPackageInDhaka =view.findViewById(R.id.hourlyPackageInDhaka);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        outOfDhakaCityInDhaka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InDhakaCityFragment myFragment = (InDhakaCityFragment)getFragmentManager().findFragmentByTag(TARGET_IN_DHAKA_CITY);
                if(myFragment != null  && myFragment.isVisible()) getActivity().getSupportFragmentManager().beginTransaction().remove(myFragment).commit();
                    getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.containFrameLayout, new OutOfDhakaCityFragment(),TARGET_OUT_OF_DHAKA_CITY)
                        .addToBackStack(TARGET_OUT_OF_DHAKA_CITY).commit();
                //   transaction.addToBackStack(FRAGMENT_LIST_VIEW);
            }
        });
        hourlyPackageInDhaka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InDhakaCityFragment myFragment = (InDhakaCityFragment)getFragmentManager().findFragmentByTag(TARGET_IN_DHAKA_CITY);
                if(myFragment != null  && myFragment.isVisible()) getActivity().getSupportFragmentManager().beginTransaction().remove(myFragment).commit();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.containFrameLayout, new HourlyPackageFragment(),TARGET_HOURLY_PACKAGE)
                        .addToBackStack(TARGET_HOURLY_PACKAGE).commit();
            }
        });
    }


}
