package in.amolgupta.helpingfaceless.activities;

import in.amolgupta.helpingfaceless.R;

import java.io.File;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * @author amol
 * 
 */
public class UploadForm extends HFBaseActivity implements OnClickListener {
	private static final int TAKE_PICTURE = 85;
	private Uri imageUri;
	private Uri selectedImage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upload_form);
		Button mUploadButton = (Button) findViewById(R.id.btn_upload_to_server);
		Button mTryAgain = (Button) findViewById(R.id.btn_try_again);
		mTryAgain.setOnClickListener(this);

		mUploadButton.setOnClickListener(this);
		
		LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		Location l=lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		takePhoto();

	}

	/**
	 * Intent to open the camera to capture photo
	 */
	public void takePhoto() {
		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		File photo = new File(Environment.getExternalStorageDirectory(),
				"Pic.jpg");
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
		imageUri = Uri.fromFile(photo);
		startActivityForResult(intent, TAKE_PICTURE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onActivityResult(int, int,
	 * android.content.Intent)
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case TAKE_PICTURE:
			/*
			 * Handle camera output
			 */
			if (resultCode == Activity.RESULT_OK) {
				selectedImage = imageUri;
				getContentResolver().notifyChange(selectedImage, null);
				ImageView imageView = (ImageView) findViewById(R.id.img_upload_form);
				ContentResolver cr = getContentResolver();
				Bitmap bitmap;
				try {
					bitmap = android.provider.MediaStore.Images.Media
							.getBitmap(cr, selectedImage);

					imageView.setImageBitmap(bitmap);
					Toast.makeText(this, selectedImage.toString(),
							Toast.LENGTH_LONG).show();
					// UploadTask upload = new UploadTask(selectedImage);
					// upload.execute();
				} catch (Exception e) {
					Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT)
							.show();
					Log.e("Camera", e.toString());
				}
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_upload_to_server:
			UploadTask upload = new UploadTask(selectedImage);
			upload.execute();
			finish();
			break;
		case R.id.btn_try_again:
			takePhoto();
		}
	}

	public class UploadTask extends AsyncTask<String, Void, Void> {
		private DefaultHttpClient mHttpClient;
		Uri imageUri;
		private NotificationManager mNotifyManager;
		private android.support.v4.app.NotificationCompat.Builder mBuilder;

		public UploadTask(Uri image) {
			this.imageUri = image;
			HttpParams params = new BasicHttpParams();
			params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION,
					HttpVersion.HTTP_1_1);
			mHttpClient = new DefaultHttpClient(params);
			mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
			mBuilder = new NotificationCompat.Builder(UploadForm.this);
			mBuilder.setContentTitle("Upload")
					.setContentText("Upload in progress")
					.setSmallIcon(R.drawable.ic_launcher);
			mBuilder.setProgress(0, 0, true);
			// Displays the progress bar for the first time.
			mNotifyManager.notify(1, mBuilder.build());
		}

		public void uploadUserPhoto(File image) {

			try {

				HttpPost httppost = new HttpPost(
						"http://helpingfaceless.com/api/v1/information/upload");

				MultipartEntity multipartEntity = new MultipartEntity(
						HttpMultipartMode.BROWSER_COMPATIBLE);
				// multipartEntity.addPart("Title", new StringBody("Title"));
				// multipartEntity.addPart("Nick", new StringBody("Nick"));
				// multipartEntity.addPart("Email", new StringBody("Email"));
				multipartEntity.addPart("photo", new FileBody(image));
				httppost.setEntity(multipartEntity);

				Object a = mHttpClient.execute(httppost,
						new PhotoUploadResponseHandler());

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		protected Void doInBackground(String... params) {
			File f = new File(imageUri.getPath());
			uploadUserPhoto(f);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			mBuilder.setContentText("Upload complete").setProgress(0, 0, false);
			mNotifyManager.notify(1, mBuilder.build());
			super.onPostExecute(result);
		}
	}

	class PhotoUploadResponseHandler implements ResponseHandler<Object> {

		@Override
		public Object handleResponse(HttpResponse response)
				throws ClientProtocolException, IOException {

			HttpEntity r_entity = response.getEntity();
			String responseString = EntityUtils.toString(r_entity);
			Log.d("UPLOAD", responseString);

			return null;
		}

	}
}
