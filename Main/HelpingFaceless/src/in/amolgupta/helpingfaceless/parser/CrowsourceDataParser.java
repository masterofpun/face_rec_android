package in.amolgupta.helpingfaceless.parser;

import in.amolgupta.helpingfaceless.entities.ImageData;
import in.amolgupta.helpingfaceless.entities.TaskDetails;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

/**
 * @author amol
 * 
 */
public class CrowsourceDataParser {
	/**
	 * @param input
	 * @return
	 * @throws JSONException
	 */
	public static TaskDetails Parse(String input) throws JSONException {
		JSONObject root = new JSONObject(input);
		JSONObject imageOne = root.getJSONObject("one");
		JSONObject imagetwo = root.getJSONObject("two");
		JSONObject task = root.getJSONObject("task");
		
		Gson gson = new Gson();
		ImageData mImageOne = gson.fromJson(imageOne.toString(),
				ImageData.class);
		ImageData mImageTwo = gson.fromJson(imagetwo.toString(),
				ImageData.class);
		ArrayList<ImageData> images = new ArrayList<ImageData>();
		images.add(mImageOne);
		images.add(mImageTwo);
		return  new TaskDetails(mImageOne, mImageTwo, task.getString("id"));
	}
}
