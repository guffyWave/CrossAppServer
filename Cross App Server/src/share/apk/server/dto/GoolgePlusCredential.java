package share.apk.server.dto;

import javax.persistence.Embeddable;

@Embeddable
public class GoolgePlusCredential {
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
