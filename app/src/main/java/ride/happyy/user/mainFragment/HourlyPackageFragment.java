package ride.happyy.user.mainFragment;


import android.app.ActionBar;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import ride.happyy.user.R;


public class HourlyPackageFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Button outOfDhakaCityButton,inDhakaCityButton;
    TextView textView,primoDetails,rloashDetails,noyaDetails;
    RadioGroup carRadioGroup;
    RadioButton primioCarRadioButton,rloashCarRadioButton,noyaCarNoyaRadioButton,radioButton;
    String car;
    View view;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;




    public HourlyPackageFragment() {
        // Required empty public constructor
    }

    public static HourlyPackageFragment newInstance(String param1, String param2) {
        HourlyPackageFragment fragment = new HourlyPackageFragment();
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

        view= inflater.inflate(R.layout.fragment_hourly_package, container, false);
     //   textView=view.findViewById(R.id.textView);
        primoDetails=view.findViewById(R.id.primoDetails);
        primoDetails.setOnClickListener(this);
        rloashDetails=view.findViewById(R.id.rloashDetails);
        rloashDetails.setOnClickListener(this);
        noyaDetails=view.findViewById(R.id.noyaDetails);
        noyaDetails.setOnClickListener(this);
        carRadioGroup= view.findViewById(R.id.carRadioGroup);
        primioCarRadioButton =view.findViewById(R.id.primioCarRadioButton);
        rloashCarRadioButton =view.findViewById(R.id.rloashCarRadioButton);
        noyaCarNoyaRadioButton =view.findViewById(R.id.noyaCarNoyaRadioButton);
        outOfDhakaCityButton =view.findViewById(R.id.outOfDhakaCityButton);
        outOfDhakaCityButton.setOnClickListener(this);
        inDhakaCityButton =view.findViewById(R.id.inDhakaCityButton);
        inDhakaCityButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        carRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selectedId = carRadioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) view.findViewById(selectedId);
                car = radioButton.getText().toString();
                if (car.equals("Primio")) {
                    rloashDetails.setVisibility(View.INVISIBLE);
                    noyaDetails.setVisibility(View.INVISIBLE);
                    primoDetails.setVisibility(View.VISIBLE);
                } else if (car.equals("Rloash")) {
                    rloashDetails.setVisibility(View.VISIBLE);
                    noyaDetails.setVisibility(View.INVISIBLE);
                    primoDetails.setVisibility(View.INVISIBLE);
                } else if (car.equals("Noya")) {
                    rloashDetails.setVisibility(View.INVISIBLE);
                    noyaDetails.setVisibility(View.VISIBLE);
                    primoDetails.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
    private final static String TARGET_IN_DHAKA_CITY ="In_Dhaka_City";
    private final static String TARGET_OUT_OF_DHAKA_CITY ="Out_Of_Dhaka";
    private final static String TARGET_HOURLY_PACKAGE="hourly_package";

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.primoDetails:dialogBox(v);
                break;
            case R.id.rloashDetails:dialogBox(v);
                break;
            case R.id.noyaDetails:dialogBox(v);
                break;
            case R.id.outOfDhakaCityButton:
                HourlyPackageFragment myFragment = (HourlyPackageFragment)getFragmentManager().findFragmentByTag(TARGET_HOURLY_PACKAGE);
                if(myFragment != null  && myFragment.isVisible()) getActivity().getSupportFragmentManager().beginTransaction().remove(myFragment).commit();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.containFrameLayout, new OutOfDhakaCityFragment(),TARGET_OUT_OF_DHAKA_CITY)
                        .addToBackStack(TARGET_OUT_OF_DHAKA_CITY).commit();
                break;
            case R.id.inDhakaCityButton:
                HourlyPackageFragment myFragment1 = (HourlyPackageFragment)getFragmentManager().findFragmentByTag(TARGET_HOURLY_PACKAGE);
                if(myFragment1 != null  && myFragment1.isVisible()) getActivity().getSupportFragmentManager().beginTransaction().remove(myFragment1).commit();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.containFrameLayout, new InDhakaCityFragment(),TARGET_IN_DHAKA_CITY)
                        .addToBackStack(TARGET_IN_DHAKA_CITY).commit();
                break;

        }
    }

    private void dialogBox(View v){
        final Dialog dialog = new Dialog(v.getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dailog_box_pricing_policy);
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);

    }
}
