package in.amolgupta.helpingfaceless.activities;

import in.amolgupta.helpingfaceless.BuildConfig;
import in.amolgupta.helpingfaceless.R;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.flurry.android.FlurryAgent;
import com.loopj.android.airbrake.AirbrakeNotifier;

public class HFBaseActivity extends ActionBarActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (!BuildConfig.DEBUG)
			AirbrakeNotifier.register(this, "eaebac6bfd3a1e9b84dd2624bee294e9",
					"production");
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onStart() {
		super.onStart();
		FlurryAgent.onStartSession(this, getResources().getString(R.string.flurry_key));

	}

	@Override
	protected void onStop() {
		super.onStop();
		FlurryAgent.onEndSession(this);
	}
}
