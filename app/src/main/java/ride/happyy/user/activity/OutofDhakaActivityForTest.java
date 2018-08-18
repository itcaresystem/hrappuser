package ride.happyy.user.activity;

import android.os.Bundle;

import ride.happyy.user.R;

public class OutofDhakaActivityForTest extends BaseAppCompatNoDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abouts);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("OUTSIDE DHAKA");
    }
}
