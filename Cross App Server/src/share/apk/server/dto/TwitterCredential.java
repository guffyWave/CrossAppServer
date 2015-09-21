package share.apk.server.dto;

import javax.persistence.Embeddable;

@Embeddable
public class TwitterCredential {
	String twitterID;
	String twitterOAuthAccessToken;

	public String getTwitterID() {
		return twitterID;
	}

	public void setTwitterID(String twitterID) {
		this.twitterID = twitterID;
	}

	public String getTwitterOAuthAccessToken() {
		return twitterOAuthAccessToken;
	}

	public void setTwitterOAuthAccessToken(String twitterOAuthAccessToken) {
		this.twitterOAuthAccessToken = twitterOAuthAccessToken;
	}

}
