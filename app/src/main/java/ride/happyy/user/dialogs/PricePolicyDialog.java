package ride.happyy.user.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import ride.happyy.user.R;
import ride.happyy.user.activity.BaseAppCompatNoDrawerActivity;


public class PricePolicyDialog extends BaseAppCompatNoDrawerActivity {
    private Activity mContext;
    private Dialog dialog;
    private TextView pricingPolicy,shordescription,bikeBaseFareTV,bikePerKmFareTV,bikePerMinuteFareTV,bikeMinmumFareTv,noteTv;

    public PricePolicyDialog(Activity mContext){
       this.mContext=mContext;
    };
    public void showBikePricePolicy(){
        dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.pricing_polici_layout_bike);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        //
        pricingPolicy = dialog.findViewById(R.id.pp_title);
        shordescription =  dialog.findViewById(R.id.pp_shortDescriptionTV);
        bikeBaseFareTV = dialog.findViewById(R.id.pp_basefareTv);
        bikePerKmFareTV = dialog.findViewById(R.id.pp_perkmTv);
        bikePerMinuteFareTV =  dialog.findViewById(R.id.pp_perminuteTv);
        bikeMinmumFareTv = dialog.findViewById(R.id.pp_minimumfareTV);
        noteTv =  dialog.findViewById(R.id.pp_note);

        //

        dialog.show();


}




}
