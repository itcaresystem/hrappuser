package ride.happyy.user.activity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.Toolbar;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ride.happyy.user.R;
import ride.happyy.user.app.App;
import ride.happyy.user.listeners.BasicListener;
import ride.happyy.user.listeners.EditProfileListener;
import ride.happyy.user.model.BasicBean;
import ride.happyy.user.model.UserBean;
import ride.happyy.user.net.DataManager;
import ride.happyy.user.util.AppConstants;
import ride.happyy.user.util.ImageFilePath;

public class ProfileEditActivity extends BaseAppCompatNoDrawerActivity {

    private static final int REQUEST_IMAGE_CAMERA = 1;
    private static final int REQUEST_IMAGE_GALLERY = 2;
    private static final int REQ_MOBILE_VERIFICATION = 3;
    private Toolbar toolbarEdit;
    private EditText etxtName;
    private EditText etxtEmail;
    private TextView etxtPhone;
    private UserBean bean;
    private Spinner spinner;
    private ImageView ivProfilePhoto;
    private String imagePath = "";
    private String displayPicImage = "";
    private Dialog dialog;
    private String emailPattern;
    private String email;
    private ArrayAdapter<CharSequence> adapter;
    private String name;
    private String phone;
    //    private AuthConfig authConfig;
    private String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        bean = (UserBean) getIntent().getSerializableExtra("bean");

        initViews();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Profile");

    }

    public void initViews() {

       /* AuthConfig.Builder builder = new AuthConfig.Builder();

        builder.withAuthCallBack(new AuthCallback() {
            @Override
            public void success(DigitsSession session, String phoneNumber) {

                performMobileAvailabilityCheck(phoneNumber);
            }

            @Override
            public void failure(DigitsException exception) {

            }
        });

        authConfig = builder.build();*/

        etxtName = (EditText) findViewById(R.id.etxt_name_edit_page);
        etxtEmail = (EditText) findViewById(R.id.etxt_email_edit_page);
        etxtPhone = (TextView) findViewById(R.id.etxt_number_edit_page);

        ivProfilePhoto = (ImageView) findViewById(R.id.iv_profile_photo);

        coordinatorLayout.removeView(toolbar);

        toolbarEdit = (Toolbar) getLayoutInflater().inflate(R.layout.toolbar_edit_page, toolbar);
        coordinatorLayout.addView(toolbarEdit, 0);
        setSupportActionBar(toolbarEdit);

        if (bean != null)
            populateUserDetails();

    }

    public void populateUserDetails() {

        swipeView.setRefreshing(false);

        ivProfilePhoto.setVisibility(View.VISIBLE);

        etxtName.setText(bean.getName());
        etxtEmail.setText(bean.getEmail());
        etxtPhone.setText(bean.getMobileNumber());

        Glide.with(getApplicationContext())
                .load(bean.getProfilePhoto())
                .apply(new RequestOptions()
                        .centerCrop()
                        .circleCrop())
                .into(ivProfilePhoto);
    }

    public void onSuccessButtonClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        if (App.isNetworkAvailable()) {
            if (collectEditProfileData()) {
                performEditProfile();
            }
        } else {
            Snackbar.make(coordinatorLayout, AppConstants.NO_NETWORK_AVAILABLE, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
        }

    }

    private boolean collectEditProfileData() {

        emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        email = etxtEmail.getText().toString();

        if (etxtName.getText().toString().equals("")) {
            Snackbar.make(coordinatorLayout, R.string.message_name_is_required, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            return false;
        }

        if (email.equalsIgnoreCase("")) {
            Snackbar.make(coordinatorLayout, R.string.message_email_is_required, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Snackbar.make(coordinatorLayout, R.string.message_enter_a_valid_email_address, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            return false;
        }
        return true;
    }

    public void performEditProfile() {

        swipeView.setRefreshing(true);
        JSONObject postData = getEditProfileJSObj();

        List<String> fileList = getFileList();

        DataManager.performEditProfile(postData, fileList, new EditProfileListener() {

            @Override
            public void onLoadCompleted(UserBean userBean) {
                swipeView.setRefreshing(false);

                App.saveToken();
                Toast.makeText(ProfileEditActivity.this, R.string.message_profile_updated_successfully, Toast.LENGTH_LONG).show();
                finish();

            }

            @Override
            public void onLoadFailed(String error) {
                swipeView.setRefreshing(false);
                Snackbar.make(coordinatorLayout, error, Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();

            }
        });
    }

    private List<String> getFileList() {
        List<String> fileList = new ArrayList<>();

        if (displayPicImage != null && !displayPicImage.equals(""))
            fileList.add(displayPicImage);

        return fileList;
    }

    private JSONObject getEditProfileJSObj() {
        JSONObject postData = new JSONObject();

        name = etxtName.getText().toString();
        email = etxtEmail.getText().toString();
        phone = etxtPhone.getText().toString();

        try {
            postData.put("name", name);
            if (!email.equalsIgnoreCase(bean.getEmail()))
                postData.put("email", email);
            if (!phone.equalsIgnoreCase(bean.getMobileNumber()))
                postData.put("phone", phone);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return postData;

    }

    public void onPhotoEditClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_photo_edit);
        dialog.show();
    }

    public void onCameraClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        onAddProfilePhotoFromCamera();
    }

    public void onGalleryClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        onAddProfilePhotoFromGallery();
    }

    public void onAddProfilePhotoFromGallery() {

        if (!checkForReadWritePermissions()) {
            getReadWritePermissions();

        } else {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_IMAGE_GALLERY);

            dialog.dismiss();
        }
    }

    public void onAddProfilePhotoFromCamera() {

        if (!checkForReadWritePermissions()) {
            getReadWritePermissions();
        } else {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Ensure that there's a camera activity to handle the intent
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                try {
                    photoFile = createImageFile(0);
                } catch (IOException ex) {
                    System.out.println(ex);
                }
                if (photoFile != null) {
                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                                Uri.fromFile(photoFile));
                    } else {
                        Uri photoURI = FileProvider.getUriForFile(getApplicationContext(),
                                getApplicationContext().getPackageName() + ".provider", photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    }
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAMERA);

                    dialog.dismiss();
                }
            }
        }
    }

    private File createImageFile(int op) throws IOException {
        File image = null;

        if (op == 0) {
            // Create an image file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                    .format(new Date());
            String imageFileName = "HappyyRide" + timeStamp + "_";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File storageDir = new File(
                        Environment.getExternalStorageDirectory() + "/HappyyRide/Photo/");
                if (!storageDir.exists()) {
                    storageDir.mkdirs();
                }
                image = new File(storageDir + imageFileName + ".jpg");
            } else {
                image = new File(getFilesDir() + "/" + imageFileName + ".jpg");
            }

            image.createNewFile();
            // Save a file: path for use with ACTION_VIEW intents
            imagePath = image.getAbsolutePath();
        }
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAMERA && resultCode == RESULT_OK) {
            displayPicImage = imagePath;
            //    setBannerPic(tempImagePath);
            setDisplayPic(imagePath);
        }
        if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == RESULT_OK) {

/*            Uri uri = data.getData();
            Cursor cursor = getContentResolver().query(uri, new String[]{
                    MediaStore.Images.ImageColumns.DATA}, null, null, null);
            cursor.moveToFirst();
            String imageFilePath = cursor.getString(0);
            cursor.close();/
            System.out.println(data.getData());
            System.out.println(data.getData().getScheme());
            System.out.println(data.getData().getEncodedPath());
            /if (data.getData().getScheme().equalsIgnoreCase("file"))
                imageFilePath = "file://"+data.getData().getPath();
            else*/
            String imageFilePath = ImageFilePath.getPath(getApplicationContext(), data.getData());
            System.out.println(imageFilePath);

            displayPicImage = imageFilePath;
            //    setBannerPic(tempImagePath);
            setDisplayPic(imageFilePath);

            if (dialog != null)
                dialog.dismiss();
        }
        if (requestCode == REQ_MOBILE_VERIFICATION && resultCode == RESULT_OK) {

            String phone = "";
            if (data.hasExtra("phone"))
                phone = data.getStringExtra("phone");

            Toast.makeText(getApplicationContext(), R.string.message_phone_verified_successfully,
                    Toast.LENGTH_LONG).show();
            etxtPhone.setText(phone);
        }
    }

    private void setDisplayPic(String tempImagePath) {

        Glide.with(getApplicationContext())
                .load(tempImagePath)
                .apply(new RequestOptions()
                        .error(R.drawable.ic_dummy_photo)
                        .fallback(R.drawable.ic_dummy_photo)
                        .centerCrop()
                        .circleCrop())
                .into(ivProfilePhoto);

    }

    public void onProfileEditPhoneClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

/*        Digits.logout();
        Digits.authenticate(authConfig);
        new Digits.Builder().withTheme(R.style.AppTheme).build();*/

        FirebaseAuth.getInstance().signOut();
        startActivityForResult(new Intent(this, MobileVerificationActivity.class)
                , REQ_MOBILE_VERIFICATION);

    }

    public void performMobileAvailabilityCheck(final String phoneNumber) {

        swipeView.setRefreshing(true);

        JSONObject postData = getMobileAvailabilityCheckJSObj(phoneNumber);

        DataManager.performMobileAvailabilityCheck(postData, new BasicListener() {

            @Override
            public void onLoadCompleted(BasicBean basicBean) {
                swipeView.setRefreshing(false);

                if (basicBean.isPhoneAvailable()) {
                    Toast.makeText(getApplicationContext(), R.string.message_phone_number_successfully_verified,
                            Toast.LENGTH_LONG).show();
                    etxtPhone.setText(phoneNumber);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.message_phone_number_already_exists,
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onLoadFailed(String error) {
                swipeView.setRefreshing(false);
                Snackbar.make(coordinatorLayout, error, Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();

            }
        });
    }

    private JSONObject getMobileAvailabilityCheckJSObj(String phoneNumber) {

        JSONObject postData = new JSONObject();

        try {

            postData.put("phone", phoneNumber);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return postData;
    }
}
