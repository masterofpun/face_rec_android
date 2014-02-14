package in.amolgupta.helpingfaceless.utils;

import com.google.gson.Gson;

import android.location.Location;

public class LocationRequestData {
	String lat;
	String lng;
	String address;
	String locality;
	String sublocality;
	String city;
	String state;
	String country;

	public LocationRequestData(Location location) {
		lat = String.valueOf(location.getLatitude());
		lng = String.valueOf(location.getLongitude());
	}
	public String getRequestString(){
		Gson gson= new Gson();
		return gson.toJson(this);
	}
}
