package in.amolgupta.helpingfaceless.entities;

public class ImageData {
	String id, photo_full_url, photo_thumb_url, photo_medium_url;
	private String photo_face_crop_thumb_url;
	private String photo_face_crop_medium_url;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPhoto_full_url() {
		return photo_full_url;
	}

	public void setPhoto_full_url(String photo_full_url) {
		this.photo_full_url = photo_full_url;
	}

	public String getPhoto_thumb_url() {
		return photo_thumb_url;
	}

	public void setPhoto_thumb_url(String photo_thumb_url) {
		this.photo_thumb_url = photo_thumb_url;
	}

	public String getPhoto_medium_url() {
		return photo_medium_url;
	}

	public void setPhoto_medium_url(String photo_medium_url) {
		this.photo_medium_url = photo_medium_url;
	}

	public String getPhoto_face_crop_thumb_url() {
		return photo_face_crop_thumb_url;
	}

	public void setPhoto_face_crop_thumb_url(String photo_face_crop_thumb_url) {
		this.photo_face_crop_thumb_url = photo_face_crop_thumb_url;
	}

	public String getPhoto_face_crop_medium_url() {
		return photo_face_crop_medium_url;
	}

	public void setPhoto_face_crop_medium_url(String photo_face_crop_medium_url) {
		this.photo_face_crop_medium_url = photo_face_crop_medium_url;
	}

}
