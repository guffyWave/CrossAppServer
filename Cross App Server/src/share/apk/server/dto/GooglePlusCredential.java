package share.apk.server.dto;

import javax.persistence.Entity;

@Entity
public class GooglePlusCredential extends SocialCredential {
	String goolgePlusID;
	String goolgePlusOAuthAccessToken;

	public String getGoolgePlusID() {
		return goolgePlusID;
	}

	public void setGoolgePlusID(String goolgePlusID) {
		this.goolgePlusID = goolgePlusID;
	}

	public String getGoolgePlusOAuthAccessToken() {
		return goolgePlusOAuthAccessToken;
	}

	public void setGoolgePlusOAuthAccessToken(String goolgePlusOAuthAccessToken) {
		this.goolgePlusOAuthAccessToken = goolgePlusOAuthAccessToken;
	}

}
