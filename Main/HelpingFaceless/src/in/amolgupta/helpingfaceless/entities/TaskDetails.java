package in.amolgupta.helpingfaceless.entities;

public class TaskDetails {
	ImageData mFirstImage;

	public TaskDetails(ImageData mFirstImage, ImageData mSecondImage, String id) {
		super();
		this.mFirstImage = mFirstImage;
		this.mSecondImage = mSecondImage;
		this.id = id;
	}

	public ImageData getmFirstImage() {
		return mFirstImage;
	}

	public void setmFirstImage(ImageData mFirstImage) {
		this.mFirstImage = mFirstImage;
	}

	public ImageData getmSecondImage() {
		return mSecondImage;
	}

	public void setmSecondImage(ImageData mSecondImage) {
		this.mSecondImage = mSecondImage;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	ImageData mSecondImage;
	String id;
}
