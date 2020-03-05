package pricing.rate;

public class NoRateException extends Exception {

	private static final long serialVersionUID = 1L;

	public NoRateException() {
	}

	public NoRateException(String message) {
		super(message);
	}

	public NoRateException(Throwable cause) {
		super(cause);
	}

	public NoRateException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoRateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
