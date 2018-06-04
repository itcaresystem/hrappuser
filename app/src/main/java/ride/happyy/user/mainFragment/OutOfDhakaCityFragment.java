package ride.happyy.user.mainFragment;


import android.app.ActionBar;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import ride.happyy.user.R;

public class OutOfDhakaCityFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private final static String TARGET_IN_DHAKA_CITY ="In_Dhaka_City";
    private final static String TARGET_OUT_OF_DHAKA_CITY ="Out_Of_Dhaka";
    private final static String TARGET_HOURLY_PACKAGE="package";


    private String mParam1;
    private String mParam2;

    Spinner spinnerDivision,spinnerDistrict;
    LinearLayout pickUpOrDroff;
    ScrollView price;
    Button inDhakaCityOutOfDhaka,hourlyPackageOutOfDhaka;
    TextView textView,primoDetails,rloashDetails,noyaDetails;
    RadioGroup carRadioGroup;
    RadioButton primioCarRadioButton,rloashCarRadioButton,noyaCarNoyaRadioButton,radioButton;
    String car;
    View view;




    public OutOfDhakaCityFragment() {
        // Required empty public constructor
    }


    public static OutOfDhakaCityFragment newInstance(String param1, String param2) {
        OutOfDhakaCityFragment fragment = new OutOfDhakaCityFragment();
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
       view =inflater.inflate(R.layout.fragment_out_of_dhaka_city, container, false);

        spinnerDivision=view.findViewById(R.id.spinnerDivision);
        spinnerDistrict=view.findViewById(R.id.spinnerDistrict);
        pickUpOrDroff=view.findViewById(R.id.pickUpOrDroff);
        price=view.findViewById(R.id.price);
        inDhakaCityOutOfDhaka=view.findViewById(R.id.inDhakaCityOutOfDhaka);
        hourlyPackageOutOfDhaka=view.findViewById(R.id.hourlyPackageOutOfDhaka);
        textView=view.findViewById(R.id.textView);
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



        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        ArrayAdapter<String> division = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.spinnerDivisionArray));
        division.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDivision.setAdapter(division);
    }

    @Override
    public void onResume() {
        super.onResume();
        spinnerDivision.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if (selectedItem.equals("--select Division--")){
                    textView.setVisibility(View.GONE);
                    spinnerDistrict.setVisibility(View.GONE);
                    pickUpOrDroff.setVisibility(View.GONE);
                    price.setVisibility(View.GONE);
                }else setDistrict(selectedItem);
            }
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

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


        inDhakaCityOutOfDhaka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OutOfDhakaCityFragment myFragment = (OutOfDhakaCityFragment)getFragmentManager().findFragmentByTag(TARGET_OUT_OF_DHAKA_CITY);
                if(myFragment != null  && myFragment.isVisible()) getActivity().getSupportFragmentManager().beginTransaction().remove(myFragment).commit();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.containFrameLayout, new InDhakaCityFragment(),TARGET_IN_DHAKA_CITY)
                        .addToBackStack(TARGET_IN_DHAKA_CITY).commit();

            }
        });
        hourlyPackageOutOfDhaka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OutOfDhakaCityFragment myFragment = (OutOfDhakaCityFragment)getFragmentManager().findFragmentByTag(TARGET_OUT_OF_DHAKA_CITY);
                if(myFragment != null  && myFragment.isVisible()) getActivity().getSupportFragmentManager().beginTransaction().remove(myFragment).commit();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.containFrameLayout, new HourlyPackageFragment(),TARGET_HOURLY_PACKAGE)
                        .addToBackStack(TARGET_HOURLY_PACKAGE).commit();
            }
        });

    }



    private void setDistrict(String division){
        if (division.equals("Dhaka")){
            ArrayAdapter<String> district = new ArrayAdapter<String>(
                    getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.spinnerDhakaDistrictArray));
            district.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerDistrict.setAdapter(district);
            textView.setVisibility(View.VISIBLE);
            spinnerDistrict.setVisibility(View.VISIBLE);
            pickUpOrDroff.setVisibility(View.VISIBLE);
            price.setVisibility(View.VISIBLE);

        }else if (division.equals("Chittagong")){
            ArrayAdapter<String> district = new ArrayAdapter<String>(
                    getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.spinnerChittagongDistrictArray));
            district.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerDistrict.setAdapter(district);
            textView.setVisibility(View.VISIBLE);
            spinnerDistrict.setVisibility(View.VISIBLE);
            pickUpOrDroff.setVisibility(View.VISIBLE);
            price.setVisibility(View.VISIBLE);

        }else if (division.equals("Barisal")){
            ArrayAdapter<String> district = new ArrayAdapter<String>(
                    getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.spinnerBarisalDistrictArray));
            district.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerDistrict.setAdapter(district);
            textView.setVisibility(View.VISIBLE);
            spinnerDistrict.setVisibility(View.VISIBLE);
            pickUpOrDroff.setVisibility(View.VISIBLE);
            price.setVisibility(View.VISIBLE);

        }else if (division.equals("Sylhet")){
            ArrayAdapter<String> district = new ArrayAdapter<String>(
                    getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.spinnerSylhetDistrictArray));
            district.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerDistrict.setAdapter(district);
            textView.setVisibility(View.VISIBLE);
            spinnerDistrict.setVisibility(View.VISIBLE);
            pickUpOrDroff.setVisibility(View.VISIBLE);
            price.setVisibility(View.VISIBLE);

        }else if (division.equals("khulan")){
            ArrayAdapter<String> district = new ArrayAdapter<String>(
                    getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.spinnerkhulanDistrictArray));
            district.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerDistrict.setAdapter(district);
            textView.setVisibility(View.VISIBLE);
            spinnerDistrict.setVisibility(View.VISIBLE);
            pickUpOrDroff.setVisibility(View.VISIBLE);
            price.setVisibility(View.VISIBLE);

        }else if (division.equals("Rajshahi")){
            ArrayAdapter<String> district = new ArrayAdapter<String>(
                    getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.spinnerRajshahiDistrictArray));
            district.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerDistrict.setAdapter(district);
            textView.setVisibility(View.VISIBLE);
            spinnerDistrict.setVisibility(View.VISIBLE);
            pickUpOrDroff.setVisibility(View.VISIBLE);
            price.setVisibility(View.VISIBLE);

        }else if (division.equals("Rangpur")){
            ArrayAdapter<String> district = new ArrayAdapter<String>(
                    getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.spinnerDhakaDistrictArray));
            district.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerDistrict.setAdapter(district);
            textView.setVisibility(View.VISIBLE);
            spinnerDistrict.setVisibility(View.VISIBLE);
            pickUpOrDroff.setVisibility(View.VISIBLE);
            price.setVisibility(View.VISIBLE);

        }else if (division.equals("Mymensing")){
            ArrayAdapter<String> district = new ArrayAdapter<String>(
                    getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.spinnerMymensingDistrictArray));
            district.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerDistrict.setAdapter(district);
            textView.setVisibility(View.VISIBLE);
            spinnerDistrict.setVisibility(View.VISIBLE);
            pickUpOrDroff.setVisibility(View.VISIBLE);
            price.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.primoDetails:dialogBox(v);
                                  break;
            case R.id.rloashDetails:dialogBox(v);
                                  break;
            case R.id.noyaDetails:dialogBox(v);
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
