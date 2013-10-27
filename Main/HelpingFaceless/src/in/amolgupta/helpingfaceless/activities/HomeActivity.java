package in.amolgupta.helpingfaceless.activities;

import in.amolgupta.helpingfaceless.R;
import in.amolgupta.helpingfaceless.SetupActivity;
import in.amolgupta.helpingfaceless.common.Constants;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

public class HomeActivity extends FragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		launchLoginScreen();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	private void launchLoginScreen() {
		/*
		 * Read prefrences to get Logged in state.
		 */
		if (Constants.mIsLoggedIN==false) {
			Intent homeIntent = new Intent(this, SetupActivity.class);
			homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(homeIntent);
			finish();
		}

	}
}
