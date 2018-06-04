package ride.happyy.user.menuFragments.historyFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ride.happyy.user.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BikeFragment extends Fragment {


    public BikeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bike, container, false);
    }

}
