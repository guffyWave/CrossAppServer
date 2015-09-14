package share.apk.android.exceptions;

public class NegativeValueException extends Exception {
	public NegativeValueException() {
	}

	public NegativeValueException(String message) {
		super(message);
	}

	public NegativeValueException(Throwable cause) {
		super(cause);
	}

	public NegativeValueException(String message, Throwable cause) {
		super(message, cause);
	}

	public NegativeValueException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
