package ride.happyy.user.menuFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import ride.happyy.user.R;
import ride.happyy.user.activity.BaseAppCompatNoDrawerActivity;


public class AboutFragment extends BaseAppCompatNoDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_notification);

        initViews();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("About");
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
