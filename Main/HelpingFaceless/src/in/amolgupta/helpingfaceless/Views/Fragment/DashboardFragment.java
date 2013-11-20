package in.amolgupta.helpingfaceless.Views.Fragment;

import in.amolgupta.helpingfaceless.R;
import in.amolgupta.helpingfaceless.activities.UpoadForm;
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
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringSystem;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.squareup.okhttp.OkHttpClient;

public class DashboardFragment extends Fragment implements
		android.view.View.OnClickListener {
	Button mUploadButton;
	ImageView mImageOne, mImageTwo;
	private TaskDetails task;
	ImageButton mBtnPositive, mBtnNegitive, mBtnSkip;
	ViewPager mViewPager;
	private DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private static Animation fadeInAnimation;
	private Animation fadeOutAnimation;

	void initializeViews(View fragmentView) {
		mUploadButton = (Button) fragmentView.findViewById(R.id.btn_upload);
		mImageOne = (ImageView) fragmentView.findViewById(R.id.img_cwds_img1);
		mImageTwo = (ImageView) fragmentView.findViewById(R.id.img_cwds_img2);
		mBtnNegitive = (ImageButton) fragmentView
				.findViewById(R.id.btn_negitive);
		mBtnPositive = (ImageButton) fragmentView
				.findViewById(R.id.btn_positive);
		mBtnSkip = (ImageButton) fragmentView.findViewById(R.id.btn_skip);
		mUploadButton.setOnClickListener(this);
		mBtnNegitive.setOnClickListener(this);
		mBtnPositive.setOnClickListener(this);
		mBtnSkip.setOnClickListener(this);
		fadeInAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
		fadeOutAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out);

//		SpringSystem springSystem = SpringSystem.create();
//
//		// Add a spring to the system.
//		Spring spring = springSystem.createSpring();
//
//		// Add a listener to observe the motion of the spring.
//		spring.addListener(new SimpleSpringListener() {
//
//			@Override
//			public void onSpringUpdate(Spring spring) {
//				// You can observe the updates in the spring
//				// state by asking its current value in onSpringUpdate.
//				float value = (float) spring.getCurrentValue();
//				float scale = 1f - (value * 0.5f);
//				// mImageOne.setScaleX(scale);
//				// mImageOne.setScaleY(scale);
//			}
//
//		});
//
//		// Set the spring in motion; moving from 0 to 1
//		spring.setEndValue(1);
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
				.displayer(new RoundedBitmapDisplayer(20)).build();

		// FetchRandomImages task = new FetchRandomImages();
		FetchRandomImages fetchTask = new FetchRandomImages();
		fetchTask.execute();
		return fragmentView;
	}

	@Override
	public void onClick(View v) {
		FetchRandomImages fetchTask = new FetchRandomImages();
		switch (v.getId()) {
		case R.id.btn_upload:
			Intent mUploadIntent = new Intent(getActivity(), UpoadForm.class);
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
		default:
			break;
		}
	}

	public class FetchRandomImages extends AsyncTask<Void, Void, Boolean> {
		OkHttpClient client = new OkHttpClient();
		private ArrayList<ImageData> images;

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

				ImageLoader.getInstance().displayImage(
						Constants.mHostURL
								+ task.getmFirstImage().getPhoto_medium_url(),
						mImageOne, options, null);
				ImageLoader.getInstance().displayImage(
						Constants.mHostURL
								+ task.getmSecondImage().getPhoto_medium_url(),
						mImageTwo, options, animateFirstListener);
//				mImageTwo.startAnimation(fadeInAnimation);
//				mImageOne.startAnimation(fadeOutAnimation);

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
					imageView.startAnimation(fadeInAnimation);
				}
			}
		}
	}

}
