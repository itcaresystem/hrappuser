package ride.happyy.user.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ride.happyy.user.R;

public class DiscountActivity extends BaseAppCompatNoDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("DISCOUNT");
    }
}
