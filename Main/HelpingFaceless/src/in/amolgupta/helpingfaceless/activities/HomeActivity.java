package in.amolgupta.helpingfaceless.activities;

import in.amolgupta.helpingfaceless.R;
import in.amolgupta.helpingfaceless.Views.Fragment.DashboardFragment;
import in.amolgupta.helpingfaceless.entities.NavItem;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class HomeActivity extends ActionBarActivity implements
		OnItemClickListener {

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private DashboardFragment fragment;
	private NavigationAdapter adapter;
	private ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		mNavItems.add(new NavItem("Home"));
		mNavItems.add(new NavItem("Upload Image"));
		mNavItems.add(new NavItem("My Pledge"));
		mNavItems.add(new NavItem("Sign Out"));

		adapter = new NavigationAdapter(mNavItems, this);
		mDrawerList.setAdapter(adapter);
		mDrawerList.setOnItemClickListener(this);
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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
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
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		switch (arg2) {

		case 0:
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
		case 1:
			Intent uploadIntent = new Intent(this, UploadForm.class);
			startActivity(uploadIntent);
		case 2:
			break;
		case 3:
			finish();
		default:
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
		}

	}
}
