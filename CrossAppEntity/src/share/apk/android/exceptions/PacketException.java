package share.apk.android.exceptions;

public class PacketException extends ApkShareException {

	public PacketException() {
	}

	public PacketException(String message) {
		super(message);
	}

	public PacketException(Throwable cause) {
		super(cause);
	}

	public PacketException(String message, Throwable cause) {
		super(message, cause);
	}

	public PacketException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
