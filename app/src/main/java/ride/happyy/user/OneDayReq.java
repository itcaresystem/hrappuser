package ride.happyy.user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import ride.happyy.user.activity.LandingPageActivity;

public class OneDayReq extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_day_req);
    }

    public void onClickGoHome(View view) {
        Intent homeIntent = new Intent(this, LandingPageActivity.class);
        startActivity(homeIntent);
        finish();
    }
}
