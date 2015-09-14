package share.apk.android.exceptions;

public class FileException extends ApkShareException {
	
	public FileException() {
	}

	public FileException(String message) {
		super(message);
	}

	public FileException(Throwable cause) {
		super(cause);
	}

	public FileException(String message, Throwable cause) {
		super(message, cause);
	}

	public FileException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
