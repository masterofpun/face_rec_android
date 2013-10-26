package in.amolgupta.helpingfaceless.Views.Fragment;

import in.amolgupta.helpingfaceless.R;
import in.amolgupta.helpingfaceless.activities.UpoadForm;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class DashboardFragment extends Fragment implements
		android.view.View.OnClickListener {
	Button mUploadButton;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View fragmentView = inflater.inflate(R.layout.dashboard_fragment, null);
		mUploadButton = (Button) fragmentView.findViewById(R.id.btn_upload);
		mUploadButton.setOnClickListener(this);
		return fragmentView;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_upload:
			Intent mUploadIntent = new Intent(getActivity(), UpoadForm.class);
			startActivity(mUploadIntent);

			break;

		default:
			break;
		}
	}

}
