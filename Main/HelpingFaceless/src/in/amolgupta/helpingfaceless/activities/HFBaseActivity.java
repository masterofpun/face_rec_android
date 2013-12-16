package in.amolgupta.helpingfaceless.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.loopj.android.airbrake.AirbrakeNotifier;

public class HFBaseActivity extends ActionBarActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		AirbrakeNotifier.register(this, "eaebac6bfd3a1e9b84dd2624bee294e9");

		super.onCreate(savedInstanceState);
	}
}
