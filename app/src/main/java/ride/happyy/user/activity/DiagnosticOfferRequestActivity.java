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
        getSupportActionBar().setTitle("Diagnostic");
    }

    public void onClickDiagnosticRequestBtn(View view){
        Toast.makeText(this,"Your request has been send to authority!You will get a discount code very soon",Toast.LENGTH_LONG).show();

    }
}
