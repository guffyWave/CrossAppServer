package share.apk.server.exceptions;

public class EmailIDException extends Exception {

	public EmailIDException(String message) {
		super(message);
	}

	public EmailIDException(Throwable cause) {
		super(cause);
	}

	public EmailIDException(String message, Throwable cause) {
		super(message, cause);
	}

	public EmailIDException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
