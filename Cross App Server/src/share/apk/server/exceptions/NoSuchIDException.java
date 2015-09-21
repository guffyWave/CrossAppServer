package share.apk.server.exceptions;

public class NoSuchIDException extends Exception {

	public NoSuchIDException() {
	}

	public NoSuchIDException(String message) {
		super(message);
	}

	public NoSuchIDException(Throwable cause) {
		super(cause);
	}

	public NoSuchIDException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoSuchIDException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
