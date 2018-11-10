package ride.happyy.user.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import ride.happyy.user.R;
import ride.happyy.user.config.Config;
import ride.happyy.user.listeners.BasicListener;
import ride.happyy.user.model.BasicBean;
import ride.happyy.user.net.DataManager;

public class ChangePasswordActivity extends BaseAppCompatNoDrawerActivity {
    EditText oldPasswordETX,newPasswordETX,reTypeNewpasswordETX;
    Button saveBTN;
    ProgressBar saveProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Change Password");
        oldPasswordETX=findViewById(R.id.oldpasswordETV);
        newPasswordETX=findViewById(R.id.newpasswordETV);
        reTypeNewpasswordETX=findViewById(R.id.retypenewpasswordETV);
        saveProgressBar=findViewById(R.id.savePassworPB);
    }

  public  void onClickSaveBtn(View view){
      JSONObject postData=new JSONObject();
      try {
          postData.put("oldpassword",oldPasswordETX.getText().toString());
          postData.put("newpassword",newPasswordETX.getText().toString());
          postData.put("phone", Config.getInstance().getPhone());
          postData.put("savepassword","savepassword");
      } catch (JSONException e) {
          e.printStackTrace();
      }
      if(checkInputData()){
          saveProgressBar.setVisibility(View.VISIBLE);
            DataManager.performNewPassword(postData, new BasicListener() {
                @Override
                public void onLoadCompleted(BasicBean basicBean) {
                    Toast.makeText(getBaseContext(),"New Password Saved!!",Toast.LENGTH_SHORT).show();
                    saveProgressBar.setVisibility(View.GONE);
                    Intent intent=new Intent(getBaseContext(),SettingsPageActivity.class);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onLoadFailed(String error) {
                    Toast.makeText(getBaseContext(),"Something wrong please retry!!",Toast.LENGTH_SHORT).show();

                }
            });

        }

    }
  public boolean checkInputData(){
        if(oldPasswordETX.getText().equals("")){
            Toast.makeText(this,"Please enter old password!!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(oldPasswordETX.getText().length()<8){
          Toast.makeText(this,"Please enter correct password",Toast.LENGTH_SHORT).show();
          return false;
        }

      if(newPasswordETX.getText().equals("")){
          Toast.makeText(this,"Please enter new password!!",Toast.LENGTH_SHORT).show();
          return false;
      }
      if(newPasswordETX.getText().length()<8){
          Toast.makeText(this,"Password lenth minimum 8!!",Toast.LENGTH_SHORT).show();
          return false;
      }
/*
      if(reTypeNewpasswordETX.getText().equals("")){
          Toast.makeText(this,"Please enter retype password!!",Toast.LENGTH_SHORT).show();
          return false;
      }
      if(!newPasswordETX.getText().equals(reTypeNewpasswordETX.getText().toString())){
          Toast.makeText(this,"New password and retypepassword should same!!!",Toast.LENGTH_SHORT).show();
          return false;
      }
      */
        return true;
  }
}
