package ride.happyy.user.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import ride.happyy.user.MainActivity;
import ride.happyy.user.R;
import ride.happyy.user.activity.DriverRatingActivity;
import ride.happyy.user.activity.NotificationsActivity;
import ride.happyy.user.activity.OnTripActivity;
import ride.happyy.user.activity.SplashActivity;
import ride.happyy.user.model.BasicBean;
import ride.happyy.user.model.SuccessBean;
import ride.happyy.user.net.parsers.TripEndParser;

public class HappyyRideFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "HFMService";
    private SuccessBean successBean;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        String request_id="";
        // ...

        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.i(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.

        if (remoteMessage.getData().size() > 0) {
            Log.i(TAG, "Message data payload: " + remoteMessage.getData());
            Log.i(TAG, "Response: " + remoteMessage.getData().get("response"));
//happyriderequesttone
            request_id = remoteMessage.getData().get("request_id");
            initiateDriverRatingService(request_id);
        }
        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.i(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            String body = remoteMessage.getNotification().getBody();


            //   initiateDriverRatingService ("123");

        }

        // ...

//        Log.i(TAG, "onMessageReceived: Remote Message : " + remoteMessage.);
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
      /*  Log.i(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.i(TAG, "Message data payload: " + remoteMessage.getData());
            Log.i(TAG, "Response: " + remoteMessage.getData().get("response"));

            Toast.makeText(this," Data Notification Received",Toast.LENGTH_LONG).show();
            String body = remoteMessage.getData().get("response");
            TripEndParser tripEndParser = new TripEndParser();
            BasicBean basicBean = tripEndParser.parseBasicResponse(body);

            if (basicBean == null)
                stopSelf();
            else {
                if (basicBean.getStatus().equalsIgnoreCase("Success")) {
//                    initiateDriverRatingService(basicBean.getId());
                    initiateDriverRatingService(basicBean.getId());
                } else if (basicBean.getStatus().equalsIgnoreCase("Error")) {
                    stopSelf();
                } else {
                    stopSelf();
                }
            }
            */

           // if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
//                scheduleJob();
           // } else {
                // Handle message within 10 seconds
//                handleNow();
          //  }

     //   }


        // Check if message contains a notification payload.
      //  if (remoteMessage.getNotification() != null) {
          //  String title = remoteMessage.getNotification().getTitle(); //get title

          //  String message = remoteMessage.getNotification().getBody(); //get message

          //  String click_action = remoteMessage.getNotification().getClickAction(); //get click_action



         //   Log.d(TAG, "Message Notification Title: " + title);

          //  Log.d(TAG, "Message Notification Body: " + message);

           // Log.d(TAG, "Message Notification click_action: " + click_action);



          //  sendNotification(title, message,click_action);
            /*

            Log.i(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            String body = remoteMessage.getNotification().getBody();
            Toast.makeText(this," Short Notification Received",Toast.LENGTH_LONG).show();
            TripEndParser tripEndParser = new TripEndParser();
            BasicBean basicBean = tripEndParser.parseBasicResponse(body);

            if (basicBean == null)
                stopSelf();
            else {
                if (basicBean.getStatus().equalsIgnoreCase("Success")) {
//                    initiateDriverRatingService(basicBean.getId());
                    initiateDriverRatingService(basicBean.getId());
                } else if (basicBean.getStatus().equalsIgnoreCase("Error")) {

                    stopSelf();
                } else {
                    stopSelf();
                }
            }

*/
     //   }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    public void initiateDriverRatingService (String requestID){

        Log.i(TAG, "initiateDriverRatingService: >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> SERVICE STARTED>>>>>>>>>>>>>>>>>>>>>");

        Intent intent = new Intent(this, SplashActivity.class);
        intent.putExtra("request_id_from", requestID);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

/*
    public void initiateDriverRatingService(String id) {

        Log.i(TAG, "initiateDriverRatingService: >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> SERVICE STARTED>>>>>>>>>>>>>>>>>>>>>");

        startActivity(new Intent(this, DriverRatingActivity.class)
                .putExtra("id", id)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));

    }
    */
    /*

    protected void onMessage(Context context, Intent intent) {
        Log.i(TAG, "Received message");
        String message = intent.getExtras().getString("price");
        Log.d("OnMSG",message);

       // displayMessage(context, message);

       // DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
       // dataBaseHelper.openDataBase();
      //  dataBaseHelper.insertData(message);
       // dataBaseHelper.close();

        // notifies user
      //  generateNotification (context, message);
    }
    */
/*
    private void sendNotification(String title,String messageBody, String click_action) {

      //  DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
       // dataBaseHelper.openDataBase();
       // dataBaseHelper.insertData(message);
        //dataBaseHelper.close();

        Intent intent;

        if(click_action.equals("NOTIFICATIONACTIVITY")){

            intent = new Intent(this, NotificationsActivity.class);

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        }

        else if(click_action.equals("MAINACTIVITY")){

            intent = new Intent(this, NotificationsActivity.class);

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        }else{

            intent = new Intent(this, NotificationsActivity.class);

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        }



        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0  Request code , intent,

                PendingIntent.FLAG_ONE_SHOT);




        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)

                .setSmallIcon(R.mipmap.ic_launcher)

                .setContentTitle(title)

                .setContentText(messageBody)

                .setAutoCancel(true)

                .setSound(defaultSoundUri)

                .setContentIntent(pendingIntent);



        NotificationManager notificationManager =

                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);



        notificationManager.notify(0  ID of notification , notificationBuilder.build());

    }
    */
}
