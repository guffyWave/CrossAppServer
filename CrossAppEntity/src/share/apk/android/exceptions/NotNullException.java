package share.apk.android.exceptions;

public class NotNullException extends Exception {
	
	public NotNullException() {
	}

	public NotNullException(String message) {
		super(message);
	}

	public NotNullException(Throwable cause) {
		super(cause);
	}

	public NotNullException(String message, Throwable cause) {
		super(message, cause);
	}
	public NotNullException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
	
}
