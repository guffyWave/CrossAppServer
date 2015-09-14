package share.apk.android.exceptions;

public class InvalidFileTypeException extends ApkShareException {

	public InvalidFileTypeException() {
	}

	public InvalidFileTypeException(String message) {
		super(message);
	}

	public InvalidFileTypeException(Throwable cause) {
		super(cause);
	}

	public InvalidFileTypeException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidFileTypeException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
