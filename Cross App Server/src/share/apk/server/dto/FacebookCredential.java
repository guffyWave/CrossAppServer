package share.apk.server.dto;

import javax.persistence.Entity;

@Entity
public class FacebookCredential extends SocialCredential {
	String facebookID;
	String facebookOAuthAccessToken;

	public String getFacebookID() {
		return facebookID;
	}

	public void setFacebookID(String facebookID) {
		this.facebookID = facebookID;
	}

	public String getFacebookOAuthAccessToken() {
		return facebookOAuthAccessToken;
	}

	public void setFacebookOAuthAccessToken(String facebookOAuthAccessToken) {
		this.facebookOAuthAccessToken = facebookOAuthAccessToken;
	}

}
