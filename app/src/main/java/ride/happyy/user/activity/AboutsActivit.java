package ride.happyy.user.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import ride.happyy.user.R;

public class AboutsActivit extends BaseAppCompatNoDrawerActivity {
    private TextView txtAboutVersionCode,terms_and_condition_tv;
    private String version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abouts);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("ABOUT");
        initViews();
    }

    private void initViews() {



        txtAboutVersionCode = (TextView) findViewById(R.id.txt_about_app_version);
        terms_and_condition_tv = findViewById(R.id.terms_and_condition_tv);

        // TextView link = (TextView) findViewById(R.id.textView1);



        PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        txtAboutVersionCode.setText("v "+version);
        String linkText = "<a href='http://happyyride.com/terms_and_conditions.php'>Terms of Service</a>";
        terms_and_condition_tv.setText(Html.fromHtml(linkText));
        terms_and_condition_tv.setMovementMethod(LinkMovementMethod.getInstance());
    }

}
