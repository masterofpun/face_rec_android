package in.amolgupta.helpingfaceless.activities;

import in.amolgupta.helpingfaceless.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class HelpItemFragment extends Fragment {
	TextView txtTitle, txtContent;
	int position;
	Button btnFinish;

	HelpItemFragment(int position) {
		super();
		this.position = position+1;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup rootView = (ViewGroup) inflater.inflate(
				R.layout.fragment_screen_slide_page, container, false);
		txtTitle = (TextView) rootView.findViewById(R.id.help_txt_one);
		txtContent = (TextView) rootView.findViewById(R.id.help_txt_details);
		btnFinish = (Button) rootView.findViewById(R.id.help_btn_finish);
		txtTitle.setText("Step " + position);
		txtContent.setText("Do something blah blah");
		if (position == HelpActivity.NUM_PAGES) {
			btnFinish.setVisibility(View.VISIBLE);
			btnFinish.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					HelpItemFragment.this.getActivity().finish();

				}
			});
		}
		return rootView;
	}
}
