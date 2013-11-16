package in.amolgupta.helpingfaceless.services;

import in.amolgupta.helpingfaceless.common.Constants;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

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

	String createRequestJSON() {
		String input = new String();
		JsonObject inputTaskObject = new JsonObject();
		inputTaskObject.addProperty("id", taskId);
		JsonObject inputUserObject = new JsonObject();
		inputUserObject.addProperty("selected", feedback);

		JsonObject inputObject = new JsonObject();
		inputObject.addProperty("access_token", "e2ad9723-b938-457b-8377-6e42f01f6697");
		inputObject.addProperty("task", inputTaskObject.toString());
		inputObject.addProperty("user", inputUserObject.toString());
		Log.d("HF_API",inputObject.toString());
		return inputObject.toString();
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(Constants.mFeedBackURL);
			httppost.setEntity(new StringEntity(createRequestJSON()));
			httppost.setHeader("Accept", "application/json");
			httppost.setHeader("Content-type", "application/json");
			HttpResponse response = httpclient.execute(httppost);
			Log.d("HF_API", response.getStatusLine().toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
