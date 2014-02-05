package in.amolgupta.helpingfaceless.activities;

import in.amolgupta.helpingfaceless.R;
import in.amolgupta.helpingfaceless.Views.Fragment.DashboardFragment;
import in.amolgupta.helpingfaceless.entities.NavItem;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookException;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.facebook.widget.ProfilePictureView;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;

public class HomeActivity extends HFBaseActivity implements OnClickListener {

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private Fragment fragment;
	private NavigationAdapter adapter;
	private ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();
	private ActionBarDrawerToggle mDrawerToggle;
	private ProfilePictureView profileImage;
	private TextView profileName;
	private TextView uploadButton;
	private TextView InviteButton;
	private TextView SignOut;
	private TextView HomeButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		View profileView = LayoutInflater.from(this).inflate(
				R.layout.profile_view, mDrawerList, false);
		profileImage = (ProfilePictureView) profileView
				.findViewById(R.id.profile_pic);
		profileName = (TextView) profileView
				.findViewById(R.id.txt_profile_name);
		uploadButton = (TextView) profileView
				.findViewById(R.id.txt_upload_image);
		InviteButton = (TextView) profileView
				.findViewById(R.id.txt_invite_friends);
		HomeButton = (TextView) profileView.findViewById(R.id.txt_home);
		SignOut = (TextView) profileView.findViewById(R.id.txt_sign_out);
		SignOut.setOnClickListener(this);
		uploadButton.setOnClickListener(this);
		InviteButton.setOnClickListener(this);
		HomeButton.setOnClickListener(this);
		mDrawerList.addHeaderView(profileView);
		makeMeRequest(Session.getActiveSession());
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		// mNavItems.add(new NavItem("Home"));
		// mNavItems.add(new NavItem("My Pledge"));
		// mNavItems.add(new NavItem("Help"));

		adapter = new NavigationAdapter(mNavItems, this);
		mDrawerList.setAdapter(adapter);
		fragment = new DashboardFragment();
		if (fragment != null) {
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragment).commit();
			mDrawerLayout.closeDrawer(mDrawerList);

		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.ic_drawer, /* nav drawer icon to replace 'Up' caret */
		R.string.drawer_open, /* "open drawer" description */
		R.string.drawer_close /* "close drawer" description */
		) {

			/** Called when a drawer has settled in a completely closed state. */
			public void onDrawerClosed(View view) {
			}

			/** Called when a drawer has settled in a completely open state. */
			public void onDrawerOpened(View drawerView) {
			}
		};

		// Set the drawer toggle as the DrawerListener
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		// mDrawerList.setOnItemClickListener(this);

	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	public class NavigationAdapter extends BaseAdapter {
		LayoutInflater inflater;
		ArrayList<NavItem> mNavItems;

		public NavigationAdapter(ArrayList<NavItem> mNavItems, Context ctx) {
			inflater = LayoutInflater.from(ctx);
			this.mNavItems = mNavItems;
		}

		@Override
		public int getCount() {
			return mNavItems.size();
		}

		@Override
		public Object getItem(int arg0) {
			return mNavItems.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {

			return arg0;
		}

		@Override
		public View getView(int arg0, View convertView, ViewGroup arg2) {
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.nav_item, arg2, false);
			}
			TextView mTxtTitle = (TextView) convertView
					.findViewById(R.id.txt_nav_title);
			mTxtTitle.setText(mNavItems.get(arg0).getTitle());
			return convertView;
		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Pass the event to ActionBarDrawerToggle, if it returns
		// true, then it has handled the app icon touch event
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle your other action bar items...

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	// @Override
	// public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long
	// arg3) {
	// switch (arg2) {
	//
	// case 0:
	// fragment = new DashboardFragment();
	// if (fragment != null) {
	// FragmentManager fragmentManager = getSupportFragmentManager();
	// fragmentManager.beginTransaction()
	// .replace(R.id.content_frame, fragment).commit();
	// mDrawerLayout.closeDrawer(mDrawerList);
	//
	// } else {
	// // error in creating fragment
	// Log.e("MainActivity", "Error in creating fragment");
	// }
	// break;
	// case 1:
	//
	// case 98:
	// fragment = new PledgeFragment();
	// if (fragment != null) {
	// FragmentManager fragmentManager = getSupportFragmentManager();
	// fragmentManager.beginTransaction()
	// .replace(R.id.content_frame, fragment).commit();
	// mDrawerLayout.closeDrawer(mDrawerList);
	//
	// } else {
	// // error in creating fragment
	// Log.e("MainActivity", "Error in creating fragment");
	// }
	// break;
	// case 99:
	// Intent helpIntent = new Intent(this, HelpActivity.class);
	// startActivity(helpIntent);
	// break;
	//
	// default:
	// fragment = new DashboardFragment();
	// if (fragment != null) {
	// FragmentManager fragmentManager = getSupportFragmentManager();
	// fragmentManager.beginTransaction()
	// .replace(R.id.content_frame, fragment).commit();
	// mDrawerLayout.closeDrawer(mDrawerList);
	//
	// } else {
	// // error in creating fragment
	// Log.e("MainActivity", "Error in creating fragment");
	// }
	// break;
	// }
	//
	// }

	private void makeMeRequest(final Session session) {
		// Make an API call to get user data and define a
		// new callback to handle the response.
		Request request = Request.newMeRequest(session,
				new Request.GraphUserCallback() {
					@Override
					public void onCompleted(GraphUser user, Response response) {
						// If the response is successful
						if (session == Session.getActiveSession()) {
							if (user != null) {
								// Set the id for the ProfilePictureView
								// view that in turn displays the profile
								// picture.
								profileImage.setProfileId(user.getId());
								// Set the Textview's text to the user's name.
								profileName.setText(user.getName());
							}
						}
						if (response.getError() != null) {
							// Handle errors, will do so later.
						}
					}
				});
		request.executeAsync();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.txt_home:
			fragment = new DashboardFragment();
			if (fragment != null) {
				FragmentManager fragmentManager = getSupportFragmentManager();
				fragmentManager.beginTransaction()
						.replace(R.id.content_frame, fragment).commit();
				mDrawerLayout.closeDrawer(mDrawerList);

			} else {
				// error in creating fragment
				Log.e("MainActivity", "Error in creating fragment");
			}
			break;
		case R.id.txt_invite_friends:
			sendRequestDialog();
			break;
		case R.id.txt_upload_image:
			Intent uploadIntent = new Intent(this, UploadForm.class);
			startActivity(uploadIntent);
			break;
		case R.id.txt_sign_out:
			Session session = Session.getActiveSession();
			if (!session.isClosed()) {
				session.closeAndClearTokenInformation();
			}
			SharedPreferences pref = getApplicationContext()
					.getSharedPreferences("MyPref", 0); // 0 - for private
														// mode
			Editor editor = pref.edit();

			editor.putString("session", ""); // Storing string
			editor.putBoolean("isLoggedIn", false); // Storing string

			editor.commit(); // commit changes
			Intent loginIntent = new Intent(HomeActivity.this,
					SetupActivity.class);
			startActivity(loginIntent);
			finish();
			break;
		default:

		}

	}
	private void sendRequestDialog() {
	    Bundle params = new Bundle();
	    params.putString("message", "Help me find missing people.");
	    WebDialog requestsDialog = (
	        new WebDialog.RequestsDialogBuilder(HomeActivity.this,
	            Session.getActiveSession(),
	            params))
	            .setOnCompleteListener(new OnCompleteListener() {

	                @Override
	                public void onComplete(Bundle values,
	                    FacebookException error) {
	                    if (values!=null && values.getString("request") != null) {
	                        Toast.makeText(HomeActivity.this.getApplicationContext(), 
	                            "Request sent",  
	                            Toast.LENGTH_SHORT).show();
	                    } else {
	                        Toast.makeText(HomeActivity.this.getApplicationContext(), 
	                            "Request cancelled", 
	                            Toast.LENGTH_SHORT).show();
	                    }
	                }

	            })
	            .build();
	    requestsDialog.show();
	}
}
