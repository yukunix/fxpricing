package pricing.currency;

public class CurrencyPair extends Base {

	private final Currency baseCcy;
	private final Currency termCcy;

	public CurrencyPair(Currency base, Currency term) {
		super(base.getName() + term.getName());
		baseCcy = base;
		termCcy = term;
	}
	
	public static CurrencyPair newCurrencyPair(Currency base, Currency term) {
		return new CurrencyPair(base, term);
	}
	
	public Currency getBaseCcy() {
		return baseCcy;
	}

	public Currency getTermCcy() {
		return termCcy;
	}
	
	public CurrencyPair getInvert() {
		return CurrencyPair.newCurrencyPair(termCcy, baseCcy);
	}

	@Override
	public String toString() {
		return getName();
	}
}
