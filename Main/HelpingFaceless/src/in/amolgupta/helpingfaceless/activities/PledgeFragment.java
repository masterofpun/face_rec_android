package in.amolgupta.helpingfaceless.activities;

import in.amolgupta.helpingfaceless.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import de.passsy.holocircularprogressbar.HoloCircularProgressBar;

public class PledgeFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View fragmentView = inflater.inflate(R.layout.fragment_pledge, null);
		HoloCircularProgressBar bar = (HoloCircularProgressBar) fragmentView.findViewById(R.id.holoCircularProgressBar1);
		bar.setProgress((float)0.365);
		return fragmentView;
	}
}
