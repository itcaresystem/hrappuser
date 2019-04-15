package ride.happyy.user.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ride.happyy.user.R;
import ride.happyy.user.adapter.NotificationAddapter;
import ride.happyy.user.app.App;
import ride.happyy.user.config.Config;
import ride.happyy.user.model.MyNotification;
import ride.happyy.user.net.NetworkCall;
import ride.happyy.user.net.ResponseCallback;

public class NotificationsActivity extends BaseAppCompatNoDrawerActivity {
    private Button invite_friend_btn;
    private TextView codeTv;
    private String code;
    private ListView notificationListView;
    private NetworkCall networkCall;
    private ArrayList<MyNotification> myNotifications = new ArrayList<>();
    //private ArrayList<MyNotification> myNotificationArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Notifications");
        networkCall = new NetworkCall();

        notificationListView = findViewById(R.id.notificationsListView);




    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllMessage();

        if(myNotifications.size()>0){
            NotificationAddapter notificationAddapter = new NotificationAddapter(this, R.layout.adapter_notification, myNotifications);
            notificationListView.setAdapter(notificationAddapter);
        }else {
            MyNotification myNotification1 = new MyNotification("Welcome to Happyy Ride ! Ride with Happyy. We are everywhere in Bangladesh.");
            MyNotification myNotification2 = new MyNotification("Get Discount with Happyy Ride");
            MyNotification myNotification3 = new MyNotification("Get points To Share The Apps.");
            MyNotification myNotification4 = new MyNotification("Have a nice day.");
            myNotifications = new ArrayList<>();
            myNotifications.add(myNotification1);
            myNotifications.add(myNotification2);
            myNotifications.add(myNotification3);
            myNotifications.add(myNotification4);
            NotificationAddapter notificationAddapter = new NotificationAddapter(this, R.layout.adapter_notification, myNotifications);
            notificationListView.setAdapter(notificationAddapter);
        }
    }

    public void getAllMessage(){
        if(networkCall==null){
            networkCall = new NetworkCall();

        }

        if(App.isNetworkAvailable()){
            networkCall.getMyAllMessage(Config.getInstance().getPhone(), new ResponseCallback<ArrayList<MyNotification>>() {
                @Override
                public void onSuccess(ArrayList<MyNotification> data) {
                    myNotifications =data;

                }

                @Override
                public void onError(Throwable th) {

                }
            });
        }

    }
}
