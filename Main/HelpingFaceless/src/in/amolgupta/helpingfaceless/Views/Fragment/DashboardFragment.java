package in.amolgupta.helpingfaceless.Views.Fragment;

import in.amolgupta.helpingfaceless.R;
import in.amolgupta.helpingfaceless.activities.ImageFragment;
import in.amolgupta.helpingfaceless.activities.UploadForm;
import in.amolgupta.helpingfaceless.common.Constants;
import in.amolgupta.helpingfaceless.entities.ImageData;
import in.amolgupta.helpingfaceless.entities.TaskDetails;
import in.amolgupta.helpingfaceless.parser.CrowsourceDataParser;
import in.amolgupta.helpingfaceless.services.SendCSResponse;
import in.amolgupta.helpingfaceless.utils.RequestUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONException;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.squareup.okhttp.OkHttpClient;

public class DashboardFragment extends Fragment implements
		android.view.View.OnClickListener {
	Button mUploadButton;
	ImageView /* mImageOne, mImageTwo */mThmbOne, mThmbTwo;
	private TaskDetails task;
	Button mBtnPositive, mBtnNegitive, mBtnSkip;
	private DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private static Animation fadeInAnimation;
	private Animation fadeOutAnimation;
	private static final int NUM_PAGES = 2;
	private ViewPager mPager;
	ProgressBar mProgressOne, mProgressTwo;
	ImageLoadingListener mListnerOne, mListnerTwo;

	void initializeViews(View fragmentView) {
		mUploadButton = (Button) fragmentView.findViewById(R.id.btn_upload);
		// mImageOne = (ImageView)
		// fragmentView.findViewById(R.id.img_cwds_img1);
		// mImageTwo = (ImageView)
		// fragmentView.findViewById(R.id.img_cwds_img2);
		mProgressOne = (ProgressBar) fragmentView
				.findViewById(R.id.img_progress_one);
		mProgressTwo = (ProgressBar) fragmentView
				.findViewById(R.id.img_progress_two);
		mThmbOne = (ImageView) fragmentView.findViewById(R.id.thmb_cwds_img1);
		mThmbTwo = (ImageView) fragmentView.findViewById(R.id.thmb_cwds_img2);
		mBtnNegitive = (Button) fragmentView.findViewById(R.id.btn_negitive);
		mBtnPositive = (Button) fragmentView.findViewById(R.id.btn_positive);
		mBtnSkip = (Button) fragmentView.findViewById(R.id.btn_skip);
		mUploadButton.setOnClickListener(this);
		mBtnNegitive.setOnClickListener(this);
		mBtnPositive.setOnClickListener(this);
		mBtnSkip.setOnClickListener(this);

		fadeInAnimation = AnimationUtils.loadAnimation(getActivity(),
				R.anim.fade_in);
		fadeOutAnimation = AnimationUtils.loadAnimation(getActivity(),
				R.anim.fade_out);
		mThmbOne.setOnClickListener(this);
		mThmbTwo.setOnClickListener(this);
		mListnerOne = new ImageLoadingListener() {

			@Override
			public void onLoadingStarted(String imageUri, View view) {
				mThmbOne.setVisibility(View.INVISIBLE);
				mProgressOne.setVisibility(View.VISIBLE);
			}

			@Override
			public void onLoadingFailed(String imageUri, View view,
					FailReason failReason) {
				Toast.makeText(DashboardFragment.this.getActivity(),
						"Unanle to fetch image", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onLoadingComplete(String imageUri, View view,
					Bitmap loadedImage) {
				mThmbOne.setVisibility(View.VISIBLE);
				mProgressOne.setVisibility(View.INVISIBLE);

			}

			@Override
			public void onLoadingCancelled(String imageUri, View view) {
				Toast.makeText(DashboardFragment.this.getActivity(),
						"Unanle to fetch image", Toast.LENGTH_SHORT).show();
			}
		};
		
		mListnerTwo= new ImageLoadingListener() {

			@Override
			public void onLoadingStarted(String imageUri, View view) {
				mThmbTwo.setVisibility(View.INVISIBLE);
				mProgressTwo.setVisibility(View.VISIBLE);
			}

			@Override
			public void onLoadingFailed(String imageUri, View view,
					FailReason failReason) {
				Toast.makeText(DashboardFragment.this.getActivity(),
						"Unanle to fetch image", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onLoadingComplete(String imageUri, View view,
					Bitmap loadedImage) {
				mThmbTwo.setVisibility(View.VISIBLE);
				mProgressTwo.setVisibility(View.INVISIBLE);

			}

			@Override
			public void onLoadingCancelled(String imageUri, View view) {
				Toast.makeText(DashboardFragment.this.getActivity(),
						"Unanle to fetch image", Toast.LENGTH_SHORT).show();
			}
		};
		
		// SpringSystem springSystem = SpringSystem.create();
		//
		// // Add a spring to the system.
		// Spring spring = springSystem.createSpring();
		//
		// // Add a listener to observe the motion of the spring.
		// spring.addListener(new SimpleSpringListener() {
		//
		// @Override
		// public void onSpringUpdate(Spring spring) {
		// // You can observe the updates in the spring
		// // state by asking its current value in onSpringUpdate.
		// float value = (float) spring.getCurrentValue();
		// float scale = 1f - (value * 0.5f);
		// // mImageOne.setScaleX(scale);
		// // mImageOne.setScaleY(scale);
		// }
		//
		// });
		//
		// // Set the spring in motion; moving from 0 to 1
		// spring.setEndValue(1);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View fragmentView = inflater.inflate(R.layout.dashboard_fragment, null);
		initializeViews(fragmentView);
		options = new DisplayImageOptions.Builder()
				// .showImageOnLoading(R.drawable.ic_stub)
				// .showImageForEmptyUri(R.drawable.ic_empty)
				// .showImageOnFail(R.drawable.ic_error)
				.cacheInMemory(true).cacheOnDisc(true)
				.displayer(new RoundedBitmapDisplayer(0)).build();

		// FetchRandomImages task = new FetchRandomImages();
		FetchRandomImages fetchTask = new FetchRandomImages();
		fetchTask.execute();
		return fragmentView;
	}

	private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
		public ScreenSlidePagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return new ImageFragment();
		}

		@Override
		public int getCount() {
			return NUM_PAGES;
		}
	}

	@Override
	public void onClick(View v) {
		FetchRandomImages fetchTask = new FetchRandomImages();
		switch (v.getId()) {
		case R.id.btn_upload:
			Intent mUploadIntent = new Intent(getActivity(), UploadForm.class);
			startActivity(mUploadIntent);

			break;
		case R.id.btn_negitive:
			new SendCSResponse(task.getId(), "", "negitive").execute();
			Toast.makeText(getActivity(),
					"Thank you! Fetching Another set of images",
					Toast.LENGTH_SHORT).show();

			fetchTask.execute();
			break;
		case R.id.btn_skip:
			new SendCSResponse(task.getId(), "", "skip").execute();
			Toast.makeText(getActivity(),
					"Thank you! Fetching Another set of images",
					Toast.LENGTH_SHORT).show();
			fetchTask.execute();
			break;
		case R.id.btn_positive:
			new SendCSResponse(task.getId(), "", "positive").execute();
			Toast.makeText(getActivity(),
					"Thank you! Fetching Another set of images",
					Toast.LENGTH_SHORT).show();
			fetchTask.execute();
			break;
		case R.id.thmb_cwds_img1:
			// ImageLoader.getInstance().displayImage(
			// Constants.mHostURL
			// + task.getmFirstImage().getPhoto_medium_url(),
			// mImageOne, options, null);
			break;
		case R.id.thmb_cwds_img2:
			// ImageLoader.getInstance().displayImage(
			// Constants.mHostURL
			// + task.getmSecondImage().getPhoto_medium_url(),
			// mImageOne, options, null);
			break;
		case R.id.img_cwds_img1:

			break;
		default:
			break;
		}
	}

	public class FetchRandomImages extends AsyncTask<Void, Void, Boolean> {
		OkHttpClient client = new OkHttpClient();
		private ArrayList<ImageData> images;
		@Override
		protected void onPreExecute() {

			mThmbOne.setVisibility(View.INVISIBLE);
			mThmbTwo.setVisibility(View.INVISIBLE);
			mProgressOne.setVisibility(View.VISIBLE);
			mProgressTwo.setVisibility(View.VISIBLE);
			super.onPreExecute();
		}
		@Override
		protected Boolean doInBackground(Void... params) {
			try {
				String result = RequestUtils.get(
						new URL(Uri.parse(Constants.mRandomImagesURL)
								.buildUpon().toString()), client);
				Log.d("HF_API", result);
				task = CrowsourceDataParser.Parse(result);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			} catch (JSONException e) {
				// TODO Auto-generated catch blockR
				e.printStackTrace();
			}

			return true;
		}

		@Override
		protected void onPostExecute(final Boolean success) {

			if (success) {

				// ImageLoader.getInstance().displayImage(
				// Constants.mHostURL
				// + task.getmFirstImage().getPhoto_medium_url(),
				// mImageOne, options, null);
				// String mImageURL = task.getmFirstImage()
				// .getPhoto_face_crop_medium_url();
				// if (mImageURL == null)
				String mImageURL = Constants.mHostURL
						+ task.getmFirstImage().getPhoto_medium_url();
				ImageLoader.getInstance().displayImage(

				mImageURL, mThmbOne, options, mListnerOne);
				// ImageLoader.getInstance().displayImage(
				// Constants.mHostURL
				// + task.getmSecondImage().getPhoto_medium_url(),
				// mImageTwo, options, animateFirstListener);
				// String mSecondImageURL = task.getmSecondImage()
				// .getPhoto_face_crop_medium_url();
				// if (mSecondImageURL == null)
				String mSecondImageURL = Constants.mHostURL
						+ task.getmSecondImage().getPhoto_medium_url();
				ImageLoader.getInstance().displayImage(mSecondImageURL,
						mThmbTwo, options, mListnerTwo);
				// mImageTwo.startAnimation(fadeInAnimation);
				// mImageOne.startAnimation(fadeOutAnimation);
				// mPager.beginFakeDrag();
				// mPager.fakeDragBy((float) .5);
				// mPager.endFakeDrag();
			} else {

			}
		}

		@Override
		protected void onCancelled() {
		}
	}

	private static class AnimateFirstDisplayListener extends
			SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections
				.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					// imageView.startAnimation(fadeInAnimation);
				}
			}
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.app_menu, menu);

		super.onCreateOptionsMenu(menu, inflater);
	}
}
