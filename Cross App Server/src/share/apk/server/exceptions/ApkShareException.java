package share.apk.server.exceptions;

public class ApkShareException extends Exception {

	public ApkShareException() {
	}

	public ApkShareException(String message) {
		super(message);
	}

	public ApkShareException(Throwable cause) {
		super(cause);
	}

	public ApkShareException(String message, Throwable cause) {
		super(message, cause);
	}

	public ApkShareException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
