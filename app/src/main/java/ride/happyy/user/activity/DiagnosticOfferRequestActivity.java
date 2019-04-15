package ride.happyy.user.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import ride.happyy.user.R;

public class DiagnosticOfferRequestActivity extends BaseAppCompatNoDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daignostic_offer_request);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Diagnostic service Upcoming.......");
    }

    public void onClickDiagnosticRequestBtn(View view){
        Toast.makeText(this,"This Service is not available still now.Will be available very Soon!!",Toast.LENGTH_LONG).show();

    }
}
