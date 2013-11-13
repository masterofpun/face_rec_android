package in.amolgupta.helpingfaceless.Views.Fragment;

import in.amolgupta.helpingfaceless.R;
import in.amolgupta.helpingfaceless.activities.UpoadForm;
import in.amolgupta.helpingfaceless.common.Constants;
import in.amolgupta.helpingfaceless.entities.ImageData;
import in.amolgupta.helpingfaceless.parser.CrowsourceDataParser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONException;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.squareup.okhttp.OkHttpClient;

public class DashboardFragment extends Fragment implements
		android.view.View.OnClickListener {
	Button mUploadButton;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View fragmentView = inflater.inflate(R.layout.dashboard_fragment, null);
		mUploadButton = (Button) fragmentView.findViewById(R.id.btn_upload);
		mUploadButton.setOnClickListener(this);
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
				images =CrowsourceDataParser.Parse(result);
				
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return true;
		}

		@Override
		protected void onPostExecute(final Boolean success) {

			if (success) {

			} else {

			}
		}

		@Override
		protected void onCancelled() {
		}
	}

}
