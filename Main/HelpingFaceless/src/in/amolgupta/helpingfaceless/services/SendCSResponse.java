package in.amolgupta.helpingfaceless.services;

import in.amolgupta.helpingfaceless.common.Constants;
import in.amolgupta.helpingfaceless.parser.CrowsourceDataParser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonObject;
import com.squareup.okhttp.OkHttpClient;

public class SendCSResponse extends AsyncTask<Void, Void, Boolean> {
	String taskId, auth, feedback;
	OkHttpClient client = new OkHttpClient();

	public SendCSResponse(String taskId, String auth, String feedback) {
		super();
		this.taskId = taskId;
		this.auth = auth;
		this.feedback = feedback;

	}

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

	String createRequestJSON() {
		String input = new String();
		JsonObject inputTaskObject = new JsonObject();
		inputTaskObject.addProperty("id", taskId);
		JsonObject inputUserObject = new JsonObject();
		inputUserObject.addProperty("selected", feedback);

		JsonObject inputObject = new JsonObject();
		inputObject.addProperty("access_token", "some_token");
		inputObject.addProperty("task", inputTaskObject.toString());
		inputObject.addProperty("user", inputUserObject.toString());
		return inputObject.toString();

	}

	@Override
	protected Boolean doInBackground(Void... params) {
		try {
			String result = get(new URL(Uri.parse(Constants.mFeedBackURL)
					.buildUpon().toString()));
			Log.d("HF_API", result);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
