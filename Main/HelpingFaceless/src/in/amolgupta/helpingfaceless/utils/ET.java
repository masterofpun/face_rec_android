package in.amolgupta.helpingfaceless.utils;

import java.util.HashMap;
import java.util.Map;

import android.util.Log;

import com.flurry.android.FlurryAgent;

public class ET {
	private static final String LOCATION = "location";
	private static final String SIDEBAR = "sidebar";
	private static final String UPLOAD_OFFLINE ="offline upload";
	private static final String RATING = "rating";
	private static final String INVITE = "invite friends";
	private static final String FB_ID = "fbid";
	public static String SIMILARITY = "similarity";
	public static String FACEBOOK = "facebook login";
	public static String APP_OPEN = "app opened";
	public static String ANDROID_ID = "androidid";
	public static String UPLOAD = "upload";
	static String id;
	
	public static void trackComparison(Boolean value) {
		Map<String, String> event = new HashMap<String, String>();
		event.put("option", value == null ? "Cantsay" : value == false ? "No"
				: "yes");
		Log.d("adding event", event.toString());
		FlurryAgent.logEvent(SIMILARITY, event);
	}

	public static void trackFaceBookLogin(String fbid) {
		Map<String, String> event = new HashMap<String, String>();
		event.put(ANDROID_ID , id);
		event.put(FB_ID,fbid);
		Log.d("adding event", event.toString());
		FlurryAgent.logEvent(FACEBOOK, event);
	}

	public static void trackAppopened() {
		Map<String, String> event = new HashMap<String, String>();
		event.put(ANDROID_ID, id);
		Log.d("adding event", event.toString());
		FlurryAgent.logEvent(APP_OPEN, event);
	}

	public static void trackUploadClicked() {
		Map<String, String> event = new HashMap<String, String>();
		event.put(ANDROID_ID , id);
		Log.d("adding event", event.toString());
		FlurryAgent.logEvent(UPLOAD, event);
	}
	
	public static void trackSidebarClicked() {
		Map<String, String> event = new HashMap<String, String>();
		event.put(ANDROID_ID , id);
		Log.d("adding event", event.toString());
		FlurryAgent.logEvent(SIDEBAR, event);
	}
	public static void trackofflineUpload(String location) {
		Map<String, String> event = new HashMap<String, String>();
		event.put(ANDROID_ID , id);
		event.put(LOCATION, location);
		Log.d("adding event", event.toString());
		FlurryAgent.logEvent(UPLOAD_OFFLINE, event);
	}
	public static void trackRatingClicked() {
		Map<String, String> event = new HashMap<String, String>();
		event.put(ANDROID_ID , id);
		Log.d("adding event", event.toString());
		FlurryAgent.logEvent(RATING, event);
	}
	public static void trackInviteFriends() {
		Map<String, String> event = new HashMap<String, String>();
		event.put(ANDROID_ID , id);
		Log.d("adding event", event.toString());
		FlurryAgent.logEvent(INVITE, event);
	}
	
	
	
}
