package ride.happyy.user.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ride.happyy.user.R;
import ride.happyy.user.model.MyNotification;


public class NotificationAddapter extends ArrayAdapter<MyNotification> {

    Context mContext;
    int mResource;
    public NotificationAddapter(Context context, int resource, ArrayList<MyNotification> object) {
        super(context, resource, object);
        mContext =context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        convertView = layoutInflater.inflate(mResource,parent,false);
        //slistTv
        TextView notificationDetailsTv = convertView.findViewById(R.id.notificationDetailsTv);
        String nDetails = getItem(position).getDetails();
        notificationDetailsTv.setText(nDetails);
        return convertView;



    }
}
