package ride.happyy.user.activity;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ride.happyy.user.R;
import ride.happyy.user.config.Config;
import ride.happyy.user.model.OutOfDhakaFare;
import ride.happyy.user.model.OutOfDhakaServiceModel;
import ride.happyy.user.model.OutofDhakaFareCalModel;
import ride.happyy.user.model.OutofDhakaServerresponse;
import ride.happyy.user.net.MyApiService;
import ride.happyy.user.net.NetworkCall;
import ride.happyy.user.net.ResponseCallback;

public class OutOfDhakaActivity extends BaseAppCompatNoDrawerActivity {
    private OutOfDhakaServiceModel  outOfDhakaServiceModel;
    private MyApiService myApiService = new NetworkCall();
    private Fragment fragment;
    private ProgressBar reqProgressBar;
    private FrameLayout primioFrameLayout,noahFrameLayout,hiaceFrameLayout;
    private TextView fromTv,toTv,pickUpTv,dropOffAddress,responseMessageTv;
    private TextView primioRentTv,noahRentTv,hiaceRentTv;
    private TextView primioInfoTv,noahInfoTv,hiaceInfoTv,mDisplayDate,booking_timeEt;
    private EditText picuppAddress,phoneNumberEditText;
    private Spinner districtSpinerfrom,divisionSpinner,districSpinner,bookinig_time_hour,bookinig_time_minute,bookinig_time_am_pm,car_quantity;
    private Button primioRequestButton,noahRequestButton,hiaceRequestButton;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private static final String TAG = "OutOfDhakaActivity";

    private SimpleDateFormat mFormatter = new SimpleDateFormat("MMMM dd yyyy hh:mm aa");
    private Button mButton;

    private SlideDateTimeListener listener =null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_of_dhaka);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Out Of Dhaka");
            //init view
        initView();
        outOfDhakaServiceModel  =new OutOfDhakaServiceModel();
        mDisplayDate = (TextView) findViewById(R.id.booking_date_outof_dha);
        booking_timeEt = findViewById(R.id.booking_date_outof_dha_et);
        listener = new SlideDateTimeListener() {

            @Override
            public void onDateTimeSet(Date date)
            {
                String formatedDate = mFormatter.format(date);
                mDisplayDate.setText(formatedDate);
            }

            // Optional cancel listener
            @Override
            public void onDateTimeCancel()
            {

            }
        };

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*

                new SlideDateTimePicker.Builder(getSupportFragmentManager())
                        .setListener(listener)
                        .setInitialDate(new Date())
                        //.setMinDate(minDate)
                        //.setMaxDate(maxDate)
                        //.setIs24HourTime(true)
                        //.setTheme(SlideDateTimePicker.HOLO_DARK)
                        //.setIndicatorColor(Color.parseColor("#990000"))
                        .build()
                        .show();
                */
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        OutOfDhakaActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = day + "/" + month + "/" + year;
                mDisplayDate.setText(date);
            }
        };

    }

  void   initView(){
        responseMessageTv   = findViewById(R.id.responsemessagetv);
        reqProgressBar=findViewById(R.id.requestprogressbar);
      //spinner for division and District
      districtSpinerfrom = findViewById(R.id.districtSpinerfrom);
      divisionSpinner   =   findViewById(R.id.divisionSpinerOutOfDhaka);
      districSpinner    =   findViewById(R.id.districtSpinerOutOfDhaka);
      bookinig_time_hour = findViewById(R.id.bookinig_time_houre);
      bookinig_time_minute =findViewById(R.id.bookinig_time_minute);
      bookinig_time_am_pm =findViewById(R.id.bookinig_time_am_pm);
      car_quantity = findViewById(R.id.car_quantity);

        //frame layout
      primioFrameLayout     =   findViewById(R.id.frameLayoutForPrimio);
      noahFrameLayout       =   findViewById(R.id.frameLayoutForNoah);
      hiaceFrameLayout      =   findViewById(R.id.frameLayoutForHiace);

      //Text view
      fromTv       =   findViewById(R.id.outOfDhakaFromTv);
     // toTv         = findViewById()
      //pickUpTv      =   findViewById(R.id.pickupOutDhakaEdt);
      dropOffAddress     =   findViewById(R.id.dropOfffOutDhakaTv);
      //edit text;
      picuppAddress =   findViewById(R.id.pickupOutDhakaEdt);
      phoneNumberEditText   =   findViewById(R.id.phoneNumberOutDhakaET);


      //edit text for rent and car info

      primioRentTv      =  findViewById(R.id.primioRentTv);
      noahRentTv        =   findViewById(R.id.noahRentTv);
      hiaceRentTv       =   findViewById(R.id.haiceRentTv);
      //
      primioInfoTv      =   findViewById(R.id.primioInfoTv);
      noahInfoTv        =   findViewById(R.id.noahRentTv);
      hiaceInfoTv       =   findViewById(R.id.hiaceInfoTv);

      //request button
      primioRequestButton   =   findViewById(R.id.requestBtnForPrimio);
      noahRequestButton     =   findViewById(R.id.requestBtnForNoah);
      hiaceRequestButton    =   findViewById(R.id.requestBtnForHiace);

      ArrayAdapter<String> division = new ArrayAdapter<String>(
              getApplicationContext(), R.layout.layout_text_view_for_drop_down, getResources().getStringArray(R.array.spinnerDivisionArray));
      division.setDropDownViewResource(android.R.layout.select_dialog_singlechoice );
      divisionSpinner.setAdapter(division);
      divisionSpinner.setPopupBackgroundResource(R.color.material_drawer_accent);
//primio
      primioFrameLayout.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              //selected
              primioFrameLayout.setBackground(getResources().getDrawable(R.drawable.material_drawer_circle_mask));
              noahFrameLayout.setBackground(getResources().getDrawable(R.drawable.circle_white_with_gray_edge));
              hiaceFrameLayout.setBackground(getResources().getDrawable(R.drawable.circle_white_with_gray_edge));
              //buttonaction
              //selected
              primioRequestButton.setVisibility(View.VISIBLE);
              noahRequestButton.setVisibility(View.GONE);
              hiaceRequestButton.setVisibility(View.GONE);

          }
      });
//noah
      noahFrameLayout.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              primioFrameLayout.setBackground(getResources().getDrawable(R.drawable.circle_white_with_gray_edge));
              //selected
              noahFrameLayout.setBackground(getResources().getDrawable(R.drawable.material_drawer_circle_mask));
              hiaceFrameLayout.setBackground(getResources().getDrawable(R.drawable.circle_white_with_gray_edge));
              //buttonaction
              primioRequestButton.setVisibility(View.GONE);
              //selected
              noahRequestButton.setVisibility(View.VISIBLE);
              hiaceRequestButton.setVisibility(View.GONE);

          }
      });
//hiace
      hiaceFrameLayout.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              primioFrameLayout.setBackground(getResources().getDrawable(R.drawable.circle_white_with_gray_edge));
              noahFrameLayout.setBackground(getResources().getDrawable(R.drawable.circle_white_with_gray_edge));
              //selected
              hiaceFrameLayout.setBackground(getResources().getDrawable(R.drawable.material_drawer_circle_mask));
              //buttonaction
              primioRequestButton.setVisibility(View.GONE);
              noahRequestButton.setVisibility(View.GONE);
              //selected
              hiaceRequestButton.setVisibility(View.VISIBLE);

          }
      });

      primioRequestButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              lytContent.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
              if (checkFields()) {
                  responseMessageTv.setVisibility(View.GONE);
                  reqProgressBar.setVisibility(View.VISIBLE);

                  outOfDhakaServiceModel.setUserId(Config.getInstance().getPhone());
                  outOfDhakaServiceModel.setServiceType("9");
                  outOfDhakaServiceModel.setPickupAddress(picuppAddress.getText().toString()+","+districtSpinerfrom.getSelectedItem().toString());
                  outOfDhakaServiceModel.setDropOffAddress(dropOffAddress.getText().toString());
                  outOfDhakaServiceModel.setDivision(divisionSpinner.getSelectedItem().toString());
                  outOfDhakaServiceModel.setDistrict(districSpinner.getSelectedItem().toString());
                  outOfDhakaServiceModel.setPhoneNumber(phoneNumberEditText.getText().toString());
                  outOfDhakaServiceModel.setCarType("1");
                  outOfDhakaServiceModel.setRent(primioRentTv.getText().toString().substring(1));
                  outOfDhakaServiceModel.setDocument("Nothing");
                  outOfDhakaServiceModel.setRequestTimeLocationAddres(Config.getInstance().getCurrentLocation());
                  outOfDhakaServiceModel.setBooking_date(mDisplayDate.getText().toString()+":"+bookinig_time_hour.getSelectedItem().toString()+":"+bookinig_time_minute.getSelectedItem().toString()+":"+bookinig_time_am_pm.getSelectedItem().toString());
                  outOfDhakaServiceModel.setCarQuantity(car_quantity.getSelectedItem().toString());



                 myApiService.sendOutOfdhakaRequest(outOfDhakaServiceModel, new ResponseCallback<String>() {
                     @Override
                     public void onSuccess(String data) {
                         reqProgressBar.setVisibility(View.GONE);
                       //  Toast.makeText(getBaseContext(),data,Toast.LENGTH_LONG).show();
                         responseMessageTv.setText(data.toString());
                         responseMessageTv.setVisibility(View.VISIBLE);
                     }

                     @Override
                     public void onError(Throwable th) {
                         reqProgressBar.setVisibility(View.GONE);
                         Toast.makeText(getBaseContext(),th.getMessage(),Toast.LENGTH_LONG).show();
                         responseMessageTv.setText(th.getMessage());
                         responseMessageTv.setVisibility(View.VISIBLE);
                     }
                 });




                 // String info = outOfDhakaServiceModel.getDistrict() + outOfDhakaServiceModel.getPhoneNumber() + outOfDhakaServiceModel.getDistrict() + outOfDhakaServiceModel.getDivision();


                 // Toast.makeText(getApplicationContext(), info, Toast.LENGTH_SHORT).show();
              }else {
                  Toast.makeText(getApplicationContext(),"Please enter correct info!!", Toast.LENGTH_SHORT).show();
              }


          }
      });


      noahRequestButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              lytContent.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
              if (checkFields()) {
                  responseMessageTv.setVisibility(View.GONE);
                  reqProgressBar.setVisibility(View.VISIBLE);
                  outOfDhakaServiceModel.setUserId(Config.getInstance().getPhone());
                  outOfDhakaServiceModel.setServiceType("9");
                  outOfDhakaServiceModel.setPickupAddress(picuppAddress.getText().toString()+","+districtSpinerfrom.getSelectedItem().toString());
                  outOfDhakaServiceModel.setDropOffAddress(dropOffAddress.getText().toString());
                  outOfDhakaServiceModel.setDivision(divisionSpinner.getSelectedItem().toString());
                  outOfDhakaServiceModel.setDistrict(districSpinner.getSelectedItem().toString());
                  outOfDhakaServiceModel.setPhoneNumber(phoneNumberEditText.getText().toString());
                  outOfDhakaServiceModel.setCarType("2");
                  outOfDhakaServiceModel.setRent(noahRentTv.getText().toString().substring(1));
                  outOfDhakaServiceModel.setDocument("Nothing");
                  outOfDhakaServiceModel.setRequestTimeLocationAddres(Config.getInstance().getCurrentLocation());
                  outOfDhakaServiceModel.setBooking_date(mDisplayDate.getText().toString()+":"+bookinig_time_hour.getSelectedItem().toString()+":"+bookinig_time_minute.getSelectedItem().toString()+":"+bookinig_time_am_pm.getSelectedItem().toString());
                  outOfDhakaServiceModel.setCarQuantity(car_quantity.getSelectedItem().toString());

               myApiService.sendOutOfdhakaRequest(outOfDhakaServiceModel, new ResponseCallback<String>() {
                   @Override
                   public void onSuccess(String data) {
                       reqProgressBar.setVisibility(View.GONE);
                    //   Toast.makeText(getApplicationContext(),data,Toast.LENGTH_LONG).show();
                       responseMessageTv.setText(data.toString());
                       responseMessageTv.setVisibility(View.VISIBLE);
                   }

                   @Override
                   public void onError(Throwable th) {
                       reqProgressBar.setVisibility(View.GONE);
                     Toast.makeText(getApplicationContext(),th.getMessage(),Toast.LENGTH_LONG).show();

                   }
               });



                  // String info = outOfDhakaServiceModel.getDistrict() + outOfDhakaServiceModel.getPhoneNumber() + outOfDhakaServiceModel.getDistrict() + outOfDhakaServiceModel.getDivision();
                  // Toast.makeText(getApplicationContext(), info, Toast.LENGTH_SHORT).show();
              }else {
                  Toast.makeText(getApplicationContext(),"Please enter correct info!!", Toast.LENGTH_SHORT).show();
              }
          }
      });

      hiaceRequestButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              lytContent.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
              if (checkFields()) {
                  responseMessageTv.setVisibility(View.GONE);
                  reqProgressBar.setVisibility(View.VISIBLE);
                  outOfDhakaServiceModel.setUserId(Config.getInstance().getPhone());
                  outOfDhakaServiceModel.setServiceType("9");
                  outOfDhakaServiceModel.setPickupAddress(picuppAddress.getText().toString()+","+districtSpinerfrom.getSelectedItem().toString());
                  outOfDhakaServiceModel.setDropOffAddress(dropOffAddress.getText().toString());
                  outOfDhakaServiceModel.setDivision(divisionSpinner.getSelectedItem().toString());
                  outOfDhakaServiceModel.setDistrict(districSpinner.getSelectedItem().toString());
                  outOfDhakaServiceModel.setPhoneNumber(phoneNumberEditText.getText().toString());
                  outOfDhakaServiceModel.setCarType("3");
                  outOfDhakaServiceModel.setRent(hiaceRentTv.getText().toString().substring(1));
                  outOfDhakaServiceModel.setDocument("Nothing");
                  outOfDhakaServiceModel.setRequestTimeLocationAddres(Config.getInstance().getCurrentLocation());
                  outOfDhakaServiceModel.setBooking_date(mDisplayDate.getText().toString()+":"+bookinig_time_hour.getSelectedItem().toString()+":"+bookinig_time_minute.getSelectedItem().toString()+":"+bookinig_time_am_pm.getSelectedItem().toString());
                  outOfDhakaServiceModel.setCarQuantity(car_quantity.getSelectedItem().toString());
                  myApiService.sendOutOfdhakaRequest(outOfDhakaServiceModel, new ResponseCallback<String>() {
                      @Override
                      public void onSuccess(String data) {
                          reqProgressBar.setVisibility(View.GONE);

                        //  Toast.makeText(getApplicationContext(),data,Toast.LENGTH_LONG).show();
                          responseMessageTv.setText(data.toString());
                          responseMessageTv.setVisibility(View.VISIBLE);

                      }

                      @Override
                      public void onError(Throwable th) {
                          reqProgressBar.setVisibility(View.GONE);
                          Toast.makeText(getApplicationContext(),th.getMessage(),Toast.LENGTH_LONG).show();

                      }
                  });


                  // String info = outOfDhakaServiceModel.getDistrict() + outOfDhakaServiceModel.getPhoneNumber() + outOfDhakaServiceModel.getDistrict() + outOfDhakaServiceModel.getDivision();
                  // Toast.makeText(getApplicationContext(), info, Toast.LENGTH_SHORT).show();
              }else {
                  Toast.makeText(getApplicationContext(),"Please enter correct info!!", Toast.LENGTH_SHORT).show();
              }


          }
      });

    }

    public boolean fieldValidatonCheck(){
        if(!divisionSpinner.isSelected()){
            Toast.makeText(this,"Select Division",Toast.LENGTH_SHORT).show();
            return false;

        }else if(!districSpinner.isSelected()){
            Toast.makeText(this,"Select District",Toast.LENGTH_SHORT).show();
            return false;

        }else if (picuppAddress.getText().toString()==""){
            Toast.makeText(this,"Type Picup Address",Toast.LENGTH_SHORT).show();
            return false;

        }
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        ArrayAdapter<String> division = new ArrayAdapter<String>(
                getApplicationContext(), R.layout.layout_text_view_for_drop_down, getResources().getStringArray(R.array.spinnerDivisionArray));
        division.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        divisionSpinner.setAdapter(division);
        divisionSpinner.setPopupBackgroundResource(R.color.green_1);
    }
    @Override
    public void onResume() {
        super.onResume();
        divisionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if (selectedItem.equals("Select")) {
                    //  textView.setVisibility(View.GONE);
                    //  spinnerDistrict.setVisibility(View.GONE);
                    // // pickUpOrDroff.setVisibility(View.GONE);
                    //  price.setVisibility(View.GONE);
                } else setDistrict(selectedItem);
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        selectDsrtrict();
    }
   void selectDsrtrict(){
        districSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                reqProgressBar.setVisibility(View.VISIBLE);

                OutofDhakaFareCalModel fareCalModel = new OutofDhakaFareCalModel();
                String toDistrict =      districSpinner.getSelectedItem().toString();
                String fromDistrict = districtSpinerfrom.getSelectedItem().toString();
                fareCalModel.setDistrict_from(fromDistrict);
                fareCalModel.setDistrict_to(toDistrict);

                myApiService.getOutOfDhakaFare(toDistrict,fromDistrict, new ResponseCallback<OutOfDhakaFare>() {
                    @Override
                    public void onSuccess(OutOfDhakaFare data) {
                        primioRentTv.setText(data.getPrimioRent());
                        noahRentTv.setText(data.getNoahRent());
                        hiaceRentTv.setText(data.getHiaceRent());
                        dropOffAddress.setText(data.getDropOffAddress());
                        reqProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable th) {

                    }
                });
/*
          switch (toDistrict){
              case "Faridpur":
                  primioRentTv.setText("৳6000");
                  noahRentTv.setText("৳7300");
                  hiaceRentTv.setText("৳8000");
                  dropOffAddress.setText(toDistrict+" Sodor");
                  break;
              case "Gazipur":
                  primioRentTv.setText("৳3000");
                  noahRentTv.setText("৳4500");
                  hiaceRentTv.setText("৳5000");
                  dropOffAddress.setText(toDistrict+" Sodor");
                  break;
              case "Gopalganj":
                  primioRentTv.setText("৳7500");
                  noahRentTv.setText("৳9500");
                  hiaceRentTv.setText("৳10000");
                  dropOffAddress.setText(toDistrict+" Sodor");
                  break;
              case "Kishoregonj":
                  primioRentTv.setText("৳4500");
                  noahRentTv.setText("৳6000");
                  hiaceRentTv.setText("৳6000");
                  dropOffAddress.setText(toDistrict+" Sodor");
                  break;
              case "Madaripur":
                  primioRentTv.setText("৳8000");
                  noahRentTv.setText("৳10000");
                  hiaceRentTv.setText("৳11000");
                  dropOffAddress.setText(toDistrict+" Sodor");
                  break;
              case "Munshiganj":
                  primioRentTv.setText("৳3500");
                  noahRentTv.setText("৳4800");
                  hiaceRentTv.setText("৳5300");
                  dropOffAddress.setText(toDistrict+" Sodor");
                  break;
              case "Narayanganj":
                  primioRentTv.setText("৳3000");
                  noahRentTv.setText("৳4500");
                  hiaceRentTv.setText("৳5000");
                  dropOffAddress.setText(toDistrict+" Sodor");
                  break;
              case "Narsingdi":
                  primioRentTv.setText("৳3500");
                  noahRentTv.setText("৳5000");
                  hiaceRentTv.setText("৳5500");
                  dropOffAddress.setText(toDistrict+" Sodor");
                  break;
              case "Rajbari":
                  primioRentTv.setText("৳6300");
                  noahRentTv.setText("৳7700");
                  hiaceRentTv.setText("৳8200");
                  dropOffAddress.setText(toDistrict+" Sodor");
                  break;
              case "Shariatpur":
                  primioRentTv.setText("৳7000");
                  noahRentTv.setText("৳9000");
                  hiaceRentTv.setText("৳9200");
                  dropOffAddress.setText(toDistrict+" Sodor");
                  break;
              case "Tangail":
                  primioRentTv.setText("৳4200");
                  noahRentTv.setText("৳6000");
                  hiaceRentTv.setText("৳6200");
                  dropOffAddress.setText(toDistrict+" Sodor");
                  break;
              case "Manikganj":
                  primioRentTv.setText("৳4000");
                  noahRentTv.setText("৳5500");
                  hiaceRentTv.setText("৳6000");
                  dropOffAddress.setText(toDistrict+" Sodor");
                  break;
              case "Brahmanbaria":
                  primioRentTv.setText("৳4500");
                  noahRentTv.setText("৳6000");
                  hiaceRentTv.setText("৳6500");
                  dropOffAddress.setText(toDistrict+" Sodor");
                  break;
              case "Chandpur":
                  primioRentTv.setText("৳4000");
                  noahRentTv.setText("৳5500");
                  hiaceRentTv.setText("৳6000");
                  dropOffAddress.setText(toDistrict+" Sodor");
                  break;
              case "Chittagong":
                  primioRentTv.setText("৳7300");
                  noahRentTv.setText("৳8500");
                  hiaceRentTv.setText("৳9000");
                  dropOffAddress.setText(toDistrict+" Sodor");
                  break;
              case "Comilla":
                  primioRentTv.setText("৳4500");
                  noahRentTv.setText("৳5500");
                  hiaceRentTv.setText("৳6000");
                  dropOffAddress.setText(toDistrict+" Sodor");
                  break;
              case "Feni":
                  primioRentTv.setText("৳5000");
                  noahRentTv.setText("৳6300");
                  hiaceRentTv.setText("৳7300");
                  break;
              case "Lakshmipur":
                  primioRentTv.setText("৳6000");
                  noahRentTv.setText("৳6250");
                  hiaceRentTv.setText("৳7200");
                  dropOffAddress.setText(toDistrict+" Sodor");
                  break;
              case "Noakhali":
                  primioRentTv.setText("৳5000");
                  noahRentTv.setText("৳6500");
                  hiaceRentTv.setText("৳7000");
                  dropOffAddress.setText(toDistrict+" Sodor");
                  break;
              case "Jamalpur":
                  primioRentTv.setText("৳5000");
                  noahRentTv.setText("৳6800");
                  hiaceRentTv.setText("৳7000");
                  dropOffAddress.setText(toDistrict+" Sodor");
                  break;
              case "Mymensingh":
                  primioRentTv.setText("৳4000");
                  noahRentTv.setText("৳5500");
                  hiaceRentTv.setText("৳6000");
                  dropOffAddress.setText(toDistrict+" Sodor");
                  break;
              case "Netrokona":
                  primioRentTv.setText("৳5200");
                  noahRentTv.setText("৳7000");
                  hiaceRentTv.setText("৳7500");
                  dropOffAddress.setText(toDistrict+" Sodor");
                  break;
              case "Sherpur":
                  primioRentTv.setText("৳5000");
                  noahRentTv.setText("৳7100");
                  hiaceRentTv.setText("৳7300");
                  break;
              case "Bogra":
                  primioRentTv.setText("৳6000");
                  noahRentTv.setText("৳7000");
                  hiaceRentTv.setText("৳7000");
                  dropOffAddress.setText(toDistrict+" Sodor");
                  break;
              case "Natore":
                  primioRentTv.setText("৳9000");
                  noahRentTv.setText("৳10500");
                  hiaceRentTv.setText("৳11000");
                  dropOffAddress.setText(toDistrict+" Sodor");
                  break;
              case "Pabna":
                  primioRentTv.setText("৳8000");
                  noahRentTv.setText("৳9000");
                  hiaceRentTv.setText("৳9200");
                  break;
              case "Sirajgang":
                  primioRentTv.setText("৳6000");
                  noahRentTv.setText("৳7600");
                  hiaceRentTv.setText("৳8000");
                  dropOffAddress.setText(toDistrict+" Sodor");
                  break;
              case "Rangpur":
                  primioRentTv.setText("৳9500");
                  noahRentTv.setText("৳11000");
                  hiaceRentTv.setText("৳11500");
                  dropOffAddress.setText(toDistrict+" Sodor");
                  break;
              case "Sunamganj":
                  primioRentTv.setText("৳10000");
                  noahRentTv.setText("৳11600");
                  hiaceRentTv.setText("৳12000");
                  dropOffAddress.setText(toDistrict+" Sodor");
                  break;
              case "Sylhet":
                  primioRentTv.setText("৳6800");
                  noahRentTv.setText("৳9000");
                  hiaceRentTv.setText("৳9300");
                  dropOffAddress.setText(toDistrict+" Sodor");
                  break;
              case "Barguna":
                  primioRentTv.setText("৳0000");
                  noahRentTv.setText("৳0000");
                  hiaceRentTv.setText("৳0000");
                  break;
              case "Barisal":
                  primioRentTv.setText("৳0000");
                  noahRentTv.setText("৳0000");
                  hiaceRentTv.setText("৳0000");
                  break;
              case "Bhola":
                  primioRentTv.setText("৳0000");
                  noahRentTv.setText("৳0000");
                  hiaceRentTv.setText("৳0000");
                  break;
              case "Jhalokati":
                  primioRentTv.setText("৳0000");
                  noahRentTv.setText("৳0000");
                  hiaceRentTv.setText("৳0000");
                  break;
              case "Patuakhuli":
                  primioRentTv.setText("৳0000");
                  noahRentTv.setText("৳0000");
                  hiaceRentTv.setText("৳0000");
                  break;
              case "Pirojpur":
                  primioRentTv.setText("৳0000");
                  noahRentTv.setText("৳0000");
                  hiaceRentTv.setText("৳0000");
                  break;
              case "Bandarban":
                  primioRentTv.setText("৳0000");
                  noahRentTv.setText("৳0000");
                  hiaceRentTv.setText("৳0000");
                  break;
              case "Cox's Bazar":
                  primioRentTv.setText("৳0000");
                  noahRentTv.setText("৳0000");
                  hiaceRentTv.setText("৳0000");
                  break;
              case "Khagrachhari":
                  primioRentTv.setText("৳0000");
                  noahRentTv.setText("৳0000");
                  hiaceRentTv.setText("৳0000");
                  break;
              case "Rangamati":
                  primioRentTv.setText("৳0000");
                  noahRentTv.setText("৳0000");
                  hiaceRentTv.setText("৳0000");
                  break;
              case "Dhaka":
                  primioRentTv.setText("৳0000");
                  noahRentTv.setText("৳0000");
                  hiaceRentTv.setText("৳0000");
                  break;
              case "Bagerhat":
                  primioRentTv.setText("৳0000");
                  noahRentTv.setText("৳0000");
                  hiaceRentTv.setText("৳0000");
                  break;
              case "Chuadanga":
                  primioRentTv.setText("৳0000");
                  noahRentTv.setText("৳0000");
                  hiaceRentTv.setText("৳0000");
                  break;
              case "Jessore":
                  primioRentTv.setText("৳0000");
                  noahRentTv.setText("৳0000");
                  hiaceRentTv.setText("৳0000");
                  break;
              case "Jhenaidah":
                  primioRentTv.setText("৳0000");
                  noahRentTv.setText("৳0000");
                  hiaceRentTv.setText("৳0000");
                  break;
              case "Khulna":
                  primioRentTv.setText("৳0000");
                  noahRentTv.setText("৳0000");
                  hiaceRentTv.setText("৳0000");
                  break;
              case "Kushtia":
                  primioRentTv.setText("৳0000");
                  noahRentTv.setText("৳0000");
                  hiaceRentTv.setText("৳0000");
                  break;
              case "Magura":
                  primioRentTv.setText("৳0000");
                  noahRentTv.setText("৳0000");
                  hiaceRentTv.setText("৳0000");
                  break;
              case "Meherpur":
                  primioRentTv.setText("৳0000");
                  noahRentTv.setText("৳0000");
                  hiaceRentTv.setText("৳0000");
                  break;
              case "Narail":
                  primioRentTv.setText("৳0000");
                  noahRentTv.setText("৳0000");
                  hiaceRentTv.setText("৳0000");
                  break;
              case "Satkhira":
                  primioRentTv.setText("৳0000");
                  noahRentTv.setText("৳0000");
                  hiaceRentTv.setText("৳0000");
                  break;
              case "Naogaon":
                  primioRentTv.setText("৳0000");
                  noahRentTv.setText("৳0000");
                  hiaceRentTv.setText("৳0000");
                  break;
              case "Cpnawabgang":
                  primioRentTv.setText("৳0000");
                  noahRentTv.setText("৳0000");
                  hiaceRentTv.setText("৳0000");
                  break;
              case "Rajshahi":
                  primioRentTv.setText("৳0000");
                  noahRentTv.setText("৳0000");
                  hiaceRentTv.setText("৳0000");
                  break;
              case "Dinajpur":
                  primioRentTv.setText("৳0000");
                  noahRentTv.setText("৳0000");
                  hiaceRentTv.setText("৳0000");
                  break;
              case "Gaibandha":
                  primioRentTv.setText("৳0000");
                  noahRentTv.setText("৳0000");
                  hiaceRentTv.setText("৳0000");
                  break;
              case "Kurigram":
                  primioRentTv.setText("৳0000");
                  noahRentTv.setText("৳0000");
                  hiaceRentTv.setText("৳0000");
                  break;
              case "Lalmonirhat":
                  primioRentTv.setText("৳0000");
                  noahRentTv.setText("৳0000");
                  hiaceRentTv.setText("৳0000");
                  break;
              case "Nilfamari":
                  primioRentTv.setText("৳0000");
                  noahRentTv.setText("৳0000");
                  hiaceRentTv.setText("৳0000");
                  break;
              case "Panchagarh":
                  primioRentTv.setText("৳0000");
                  noahRentTv.setText("৳0000");
                  hiaceRentTv.setText("৳0000");
                  break;
              case "Thakurgaon":
                  primioRentTv.setText("৳0000");
                  noahRentTv.setText("৳0000");
                  hiaceRentTv.setText("৳0000");
                  break;
              case "Habiganj":
                  primioRentTv.setText("৳0000");
                  noahRentTv.setText("৳0000");
                  hiaceRentTv.setText("৳0000");
                  break;
              case "Moulvibazar":
                  primioRentTv.setText("৳0000");
                  noahRentTv.setText("৳0000");
                  hiaceRentTv.setText("৳0000");
                  break;
                default:
                    primioRentTv.setText("৳0000");
                    noahRentTv.setText("৳0000");
                    hiaceRentTv.setText("৳0000");
                    dropOffAddress.setText(toDistrict+" Sodor");


          }
          */
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
   }

   public boolean checkFields(){
        if(divisionSpinner.getSelectedItem().toString()=="Select"){
            Toast.makeText(this,"Please Select Division",Toast.LENGTH_SHORT).show();
            return false;
        }
       if(districSpinner.getSelectedItem().toString()=="Select"){
           Toast.makeText(this,"Please Select District",Toast.LENGTH_SHORT).show();
           return false;
       }

       if(picuppAddress.getText().toString()==""){
           Toast.makeText(this,"Enter Pick Up address",Toast.LENGTH_SHORT).show();
           return false;
       }
       if(phoneNumberEditText.getText().toString()==""||phoneNumberEditText.getText().toString()==null||phoneNumberEditText.getText().toString().length()!=11){
           Toast.makeText(this,"Please Enter Valid phone number!!",Toast.LENGTH_SHORT).show();
           return false;
       }
       if(mDisplayDate.getText().toString()==""||mDisplayDate.getText().toString()==null){
           Toast.makeText(this,"Please select booking date!!",Toast.LENGTH_SHORT).show();
           return false;
       }
       return true;
   }

  //  OutOfDhakaServiceModel OutOfDhakaServiceModel = new OutOfDhakaServiceModel();
    /*
    String userId   ="";
    String serviceType   ="";
    String pickupAddress   ="";
    String dropOffAddress   ="";
    String fromAddress   ="";
    String division   ="";
    String district   ="";
    String phoneNumber   ="";
    String carType   ="";
    String rent   ="";
    String document   ="";
    String requestTimeLocationAddres   ="";
     */



    private void setDistrict(String division){
        if (division.equals("Dhaka")){
            ArrayAdapter<String> district = new ArrayAdapter<String>(
                    getApplicationContext(), R.layout.layout_text_view_for_drop_down, getResources().getStringArray(R.array.spinnerDhakaDistrictArray));
            district.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
            districSpinner.setAdapter(district);
            districSpinner.setPopupBackgroundResource(R.color.green_1);
           // textView.setVisibility(View.VISIBLE);
            districSpinner.setVisibility(View.VISIBLE);
          //  pickUpOrDroff.setVisibility(View.VISIBLE);
          //  price.setVisibility(View.VISIBLE);

        }else if (division.equals("Chittagong")){
            ArrayAdapter<String> district = new ArrayAdapter<String>(
                    getApplicationContext(), R.layout.layout_text_view_for_drop_down, getResources().getStringArray(R.array.spinnerChittagongDistrictArray));
            district.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
            districSpinner.setAdapter(district);
            districSpinner.setPopupBackgroundResource(R.color.green_1);
         //   textView.setVisibility(View.VISIBLE);
            districSpinner.setVisibility(View.VISIBLE);
         //   pickUpOrDroff.setVisibility(View.VISIBLE);
        //    price.setVisibility(View.VISIBLE);

        }else if (division.equals("Barisal")){
            ArrayAdapter<String> district = new ArrayAdapter<String>(
                    getApplicationContext(), R.layout.layout_text_view_for_drop_down, getResources().getStringArray(R.array.spinnerBarisalDistrictArray));
            district.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
            districSpinner.setAdapter(district);
            districSpinner.setPopupBackgroundResource(R.color.green_1);
         //   textView.setVisibility(View.VISIBLE);
            districSpinner.setVisibility(View.VISIBLE);
         //   pickUpOrDroff.setVisibility(View.VISIBLE);
         //   price.setVisibility(View.VISIBLE);

        }else if (division.equals("Sylhet")){
            ArrayAdapter<String> district = new ArrayAdapter<String>(
                    getApplicationContext(), R.layout.layout_text_view_for_drop_down, getResources().getStringArray(R.array.spinnerSylhetDistrictArray));
            district.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
            districSpinner.setAdapter(district);
            districSpinner.setPopupBackgroundResource(R.color.green_1);
          //  textView.setVisibility(View.VISIBLE);
            districSpinner.setVisibility(View.VISIBLE);
          //  pickUpOrDroff.setVisibility(View.VISIBLE);
           // price.setVisibility(View.VISIBLE);

        }else if (division.equals("khulna")){
            ArrayAdapter<String> district = new ArrayAdapter<String>(
                    getApplicationContext(), R.layout.layout_text_view_for_drop_down, getResources().getStringArray(R.array.spinnerkhulanDistrictArray));
            district.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
            districSpinner.setAdapter(district);
            districSpinner.setPopupBackgroundResource(R.color.green_1);
           // textView.setVisibility(View.VISIBLE);
            districSpinner.setVisibility(View.VISIBLE);
          //  pickUpOrDroff.setVisibility(View.VISIBLE);
         //   price.setVisibility(View.VISIBLE);

        }else if (division.equals("Rajshahi")){
            ArrayAdapter<String> district = new ArrayAdapter<String>(
                    getApplicationContext(), R.layout.layout_text_view_for_drop_down, getResources().getStringArray(R.array.spinnerRajshahiDistrictArray));
            district.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
            districSpinner.setAdapter(district);
        //    textView.setVisibility(View.VISIBLE);
            districSpinner.setVisibility(View.VISIBLE);
            districSpinner.setPopupBackgroundResource(R.color.green_1);
        //    pickUpOrDroff.setVisibility(View.VISIBLE);
         //   price.setVisibility(View.VISIBLE);

        }else if (division.equals("Rangpur")){
            ArrayAdapter<String> district = new ArrayAdapter<String>(
                    getApplicationContext(), R.layout.layout_text_view_for_drop_down, getResources().getStringArray(R.array.spinnerRangpurDistrictArray));
            district.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
            districSpinner.setAdapter(district);
            districSpinner.setPopupBackgroundResource(R.color.green_1);
         //   textView.setVisibility(View.VISIBLE);

            districSpinner.setVisibility(View.VISIBLE);
        //    pickUpOrDroff.setVisibility(View.VISIBLE);
         //   price.setVisibility(View.VISIBLE);

        }else if (division.equals("Mymensingh")){
            ArrayAdapter<String> district = new ArrayAdapter<String>(
                    getApplicationContext(), R.layout.layout_text_view_for_drop_down, getResources().getStringArray(R.array.spinnerMymensingDistrictArray));
            district.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
            districSpinner.setAdapter(district);
            districSpinner.setPopupBackgroundResource(R.color.green_1);
        //    textView.setVisibility(View.VISIBLE);
            districSpinner.setVisibility(View.VISIBLE);
        //    pickUpOrDroff.setVisibility(View.VISIBLE);
         //   price.setVisibility(View.VISIBLE);
        }
    }


}
