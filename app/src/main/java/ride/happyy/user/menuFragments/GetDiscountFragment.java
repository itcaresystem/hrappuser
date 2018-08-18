package ride.happyy.user.menuFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ride.happyy.user.R;
import ride.happyy.user.activity.BaseAppCompatNoDrawerActivity;
public class GetDiscountFragment extends BaseAppCompatNoDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_get_discount);

        initViews();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Discount");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            /*
            if (etxtPromoCode.isShown()) {
                onBackClick();

            } else {
                onBackPressed();
            }
            */
        }
        return true;
    }

    private void onBackClick() {

       /* etxtPromoCode.setVisibility(View.GONE);
        txtAddCode.setText("ADD CODE");*/
        finish();
    }

    public void initViews() {

        coordinatorLayout.removeView(toolbar);



    }

}
