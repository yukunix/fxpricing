package pricing.currency;

public class Currency extends Base {

	private final int precision;
	
	public Currency(String name, int precision) {
		super(name);
		this.precision = precision;
	}
	
	public int getPrecision() {
		return precision;
	}
	
	public static Currency newCurrency(String currency, int precision) {
		return new Currency(currency, precision);
	}
}
