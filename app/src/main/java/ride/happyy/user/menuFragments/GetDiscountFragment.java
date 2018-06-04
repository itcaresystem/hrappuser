package ride.happyy.user.menuFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ride.happyy.user.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GetDiscountFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GetDiscountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GetDiscountFragment extends Fragment {

    public GetDiscountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_get_discount, container, false);
    }

}
