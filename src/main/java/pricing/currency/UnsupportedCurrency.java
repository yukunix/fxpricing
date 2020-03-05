package pricing.currency;

public class UnsupportedCurrency extends Exception {

	private static final long serialVersionUID = 1L;

	public UnsupportedCurrency() {
	}

	public UnsupportedCurrency(String message) {
		super(message);
	}

	public UnsupportedCurrency(Throwable cause) {
		super(cause);
	}

	public UnsupportedCurrency(String message, Throwable cause) {
		super(message, cause);
	}

	public UnsupportedCurrency(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
