package share.apk.server.jsonResponse;

public class UserResult {
	private long id;
	private String emailID;
	private String phoneNumber;
	private String gcmID;
	private String displayName;
	private String displayPicFileURI;
	private boolean userActivationStatus;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmailID() {
		return emailID;
	}

	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getGcmID() {
		return gcmID;
	}

	public void setGcmID(String gcmID) {
		this.gcmID = gcmID;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayPicFileURI() {
		return displayPicFileURI;
	}

	public void setDisplayPicFileURI(String displayPicFileURI) {
		this.displayPicFileURI = displayPicFileURI;
	}

	public boolean isUserActivationStatus() {
		return userActivationStatus;
	}

	public void setUserActivationStatus(boolean userActivationStatus) {
		this.userActivationStatus = userActivationStatus;
	}

}
