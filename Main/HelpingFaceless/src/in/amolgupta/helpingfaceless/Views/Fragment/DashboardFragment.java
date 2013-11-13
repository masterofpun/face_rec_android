package in.amolgupta.helpingfaceless.Views.Fragment;

import in.amolgupta.helpingfaceless.R;
import in.amolgupta.helpingfaceless.activities.UpoadForm;
import in.amolgupta.helpingfaceless.common.Constants;
import in.amolgupta.helpingfaceless.entities.ImageData;
import in.amolgupta.helpingfaceless.entities.TaskDetails;
import in.amolgupta.helpingfaceless.parser.CrowsourceDataParser;
import in.amolgupta.helpingfaceless.services.SendCSResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

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
	private DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

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

		FetchRandomImages task = new FetchRandomImages();
		task.execute();
		return fragmentView;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_upload:
			Intent mUploadIntent = new Intent(getActivity(), UpoadForm.class);
			startActivity(mUploadIntent);

			break;
		case R.id.btn_negitive:
			new SendCSResponse(task.getId(), "", "negitive").execute();
			break;
		case R.id.btn_skip:
			new SendCSResponse(task.getId(), "", "skip").execute();
			break;
		case R.id.btn_positive:
			new SendCSResponse(task.getId(), "", "positive").execute();
			break;
		default:
			break;
		}
	}

	public class FetchRandomImages extends AsyncTask<Void, Void, Boolean> {
		OkHttpClient client = new OkHttpClient();
		private ArrayList<ImageData> images;

		byte[] readFully(InputStream in) throws IOException {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			for (int count; (count = in.read(buffer)) != -1;) {
				out.write(buffer, 0, count);
			}
			return out.toByteArray();
		}

		String get(URL url) throws IOException {
			HttpURLConnection connection = client.open(url);
			InputStream in = null;
			try {
				// Read the response.
				in = connection.getInputStream();
				byte[] response = readFully(in);
				return new String(response, "UTF-8");
			} finally {
				if (in != null)
					in.close();
			}
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			try {
				String result = get(new URL(Uri
						.parse(Constants.mRandomImagesURL).buildUpon()
						.toString()));
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
						mImageOne, options, animateFirstListener);
				ImageLoader.getInstance().displayImage(
						Constants.mHostURL
								+ task.getmSecondImage().getPhoto_medium_url(),
						mImageTwo, options, animateFirstListener);
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
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}
}
