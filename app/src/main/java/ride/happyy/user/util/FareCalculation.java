package ride.happyy.user.util;

import java.util.HashMap;

public class FareCalculation {
    AppConstants appConstants = new AppConstants();
    public String bikeFareEstimate="";
    public String cngFareEstimate="";
    public String carFareEstimate="";
    public String ambulanceFareEstimate="";
    public float distance=0f;

    public HashMap estimateFare= new HashMap();

    public HashMap estimateFareCalculation(float distance, float time ){
       float distanceInKm=Math.round(distance/1000);

        float timeinminute = Math.round(time/60);

        //bike calculation_start

        float normalBikeFareDistance =Math.round(appConstants.BIKE_BASE_FARE+(distanceInKm*appConstants.BIKE_PER_KM_FARE));
        normalBikeFareDistance=normalBikeFareDistance+Math.round((normalBikeFareDistance*10)/100);
        float normalFareTime =appConstants.BIKE_BASE_FARE+(timeinminute*appConstants.BIKE_PER_MIN_FARE);

        if(normalBikeFareDistance>appConstants.BIKE_MIN_FARE){

            estimateFare.put("bikeFare", ((Float) normalBikeFareDistance));


        }else {
            estimateFare.put("bikeFare",(Float)appConstants.BIKE_MIN_FARE);

        }

        //CNG_calculation_start

        float normalCNGFareDistance =Math.round((appConstants.CNG_BASE_FARE+(distanceInKm*appConstants.CNG_PER_KM_FARE))+timeinminute*appConstants.CNG_PER_MIN_FARE);
        normalCNGFareDistance   = normalCNGFareDistance+Math.round((normalCNGFareDistance*10)/100);

        float normalCNGFareTime =appConstants.CNG_BASE_FARE+(timeinminute*appConstants.CNG_PER_MIN_FARE);

        if(normalCNGFareDistance>appConstants.CNG_MIN_FARE){

            estimateFare.put("cngFare",(Float)normalCNGFareDistance);



        }else {
            estimateFare.put("cngFare",(Float)appConstants.CNG_MIN_FARE);
            bikeFareEstimate = ((Float) appConstants.CNG_MIN_FARE).toString();
        }

        //CAR_calculation_start +timeinminute*appConstants.CAR_PER_MIN_FARE

        float normalCarFareDistance =Math.round((appConstants.CAR_BASE_FARE+(distanceInKm*appConstants.CAR_PER_KM_FARE))+timeinminute*appConstants.CAR_PER_MIN_FARE);
        normalCarFareDistance   = normalCarFareDistance+Math.round((normalCarFareDistance*12)/100);
        float normalCarFareTime =appConstants.CAR_BASE_FARE+(timeinminute*appConstants.CAR_PER_MIN_FARE);

        if(normalCarFareDistance>appConstants.CAR_MIN_FARE){

            estimateFare.put("carFare",(Float)normalCarFareDistance);



        }else {
            estimateFare.put("carFare",(Float)appConstants.CAR_MIN_FARE);

        }

        //Ambulance_calculation_start

        float normalAmbulanceFareDistance =Math.round(appConstants.AMBULANCE_BASE_FARE+(timeinminute*appConstants.AMBULANCE_PER_MIN_FARE));
      //  normalAmbulanceFareDistance = normalAmbulanceFareDistance+Math.round((normalAmbulanceFareDistance*10)/100);
        float normalAmbulanceFareTime =appConstants.AMBULANCE_BASE_FARE+(distance*appConstants.AMBULANCE_PER_MIN_FARE);

        if(normalAmbulanceFareDistance>appConstants.AMBULANCE_MIN_FARE){

            estimateFare.put("ambulanceFare",(Float)normalAmbulanceFareDistance);



        }else {
            estimateFare.put("ambulanceFare",(Float)appConstants.AMBULANCE_MIN_FARE);
            bikeFareEstimate = ((Float) appConstants.AMBULANCE_MIN_FARE).toString();
        }

        //Hire_one_houre Package
        float normalHireCarFare  = Math.round(appConstants.CAR_HIRE_BASE_FARE+(distanceInKm*appConstants.CAR_HIRE_PER_KM_FARE)+timeinminute*appConstants.CAR_HIRE_PER_MIN_FARE);
        normalHireCarFare = normalHireCarFare+Math.round((normalHireCarFare*10)/100);

        if(normalHireCarFare>appConstants.CAR_HIRE_MIN_FARE){
            estimateFare.put("carHireFare",(Float)normalHireCarFare);

        }else {
            estimateFare.put("carHireFare",(Float)appConstants.CAR_HIRE_MIN_FARE);

        }

        estimateFare.put("ondayHirePremioFare",(Float)appConstants.CAR_TEN_HOURS_PACKAGE_BASE_FARE_PREMIO);
        estimateFare.put("onedayHireNoahFare",(Float)appConstants.CAR_TEN_HOURS_PACKAGE_BASE_FARE_NOAH);
        estimateFare.put("onedayHireHiaceFare",(Float)appConstants.CAR_TEN_HOURS_PACKAGE_BASE_FARE_HIACE);

        /*

        //Hire_two_houres Package
        float normalTwoHoursHireCarFare  = Math.round(appConstants.CAR_HIRE_BASE_FARE+(distance*appConstants.CAR_HIRE_PER_KM_FARE));

        if(normalTwoHoursHireCarFare>appConstants.CAR_TWO_HOURS_PACKAGE_BASE_FARE){
            estimateFare.put("twohoursHireFare",(Float)normalTwoHoursHireCarFare);

        }else {
            estimateFare.put("twohoursHireFare",(Float)appConstants.CAR_TWO_HOURS_PACKAGE_BASE_FARE);

        }

        //Hire_Four_houre Package
        float normalFourHoursHireCarFare  = Math.round(appConstants.CAR_HIRE_BASE_FARE+(distance*appConstants.CAR_HIRE_PER_KM_FARE));

        if(normalFourHoursHireCarFare>appConstants.CAR_FOUR_HOURS_PACKAGE_BASE_FARE){
            estimateFare.put("fourhoursHireFare",(Float)normalFourHoursHireCarFare);

        }else {
            estimateFare.put("fourhoursHireFare",(Float)appConstants.CAR_FOUR_HOURS_PACKAGE_BASE_FARE);

        }

        */

        //Hire_car_one_day_premio Package


        /*

        float normalOnedayHireCarPremioFare  = Math.round(appConstants.CAR_HIRE_BASE_FARE+(distance*appConstants.CAR_HIRE_PER_KM_FARE));

        if(normalOnedayHireCarPremioFare>appConstants.CAR_TEN_HOURS_PACKAGE_BASE_FARE_PREMIO){
            estimateFare.put("ondayHirePremioFare",(Float)normalOnedayHireCarPremioFare);

        }else {
            estimateFare.put("ondayHirePremioFare",(Float)appConstants.CAR_TEN_HOURS_PACKAGE_BASE_FARE_PREMIO);

        }

        //Hire_car_one_day_noah Package
        float normalOnedayHireCarNoahFare  = Math.round(appConstants.CAR_HIRE_BASE_FARE+(distance*appConstants.CAR_HIRE_PER_KM_FARE));

        if(normalOnedayHireCarNoahFare>appConstants.CAR_TEN_HOURS_PACKAGE_BASE_FARE_NOAH){
            estimateFare.put("onedayHireNoahFare",(Float)normalOnedayHireCarNoahFare);

        }else {
            estimateFare.put("onedayHireNoahFare",(Float)appConstants.CAR_TEN_HOURS_PACKAGE_BASE_FARE_NOAH);

        }

        //Hire_car_one_day_hiace Package
        float normalOnedayHireCarHiaceFare  = Math.round(appConstants.CAR_HIRE_BASE_FARE+(distance*appConstants.CAR_HIRE_PER_KM_FARE));

        if(normalOnedayHireCarHiaceFare>appConstants.CAR_TEN_HOURS_PACKAGE_BASE_FARE_HIACE){
            estimateFare.put("onedayHireHiaceFare",(Float)normalOnedayHireCarHiaceFare);

        }else {
            estimateFare.put("onedayHireHiaceFare",(Float)appConstants.CAR_TEN_HOURS_PACKAGE_BASE_FARE_HIACE);

        }

        */



      //  estimateFare.put("twohoursHireFare",(Float)appConstants.CAR_TWO_HOURS_PACKAGE_BASE_FARE);
      //  estimateFare.put("fourhoursHireFare",(Float)appConstants.CAR_FOUR_HOURS_PACKAGE_BASE_FARE);
      //  estimateFare.put("ondayHirePremioFare",(Float)appConstants.CAR_TEN_HOURS_PACKAGE_BASE_FARE_PREMIO);
       // estimateFare.put("onedayHireNoahFare",(Float)appConstants.CAR_TEN_HOURS_PACKAGE_BASE_FARE_NOAH);
       // estimateFare.put("onedayHireHiaceFare",(Float)appConstants.CAR_TEN_HOURS_PACKAGE_BASE_FARE_HIACE);






        return estimateFare;
    }
}
