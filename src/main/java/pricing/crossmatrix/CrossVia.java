package pricing.crossmatrix;

import pricing.currency.Currency;
import pricing.currency.CurrencyPair;

public class CrossVia {
	
	private final CurrencyPair ccyPair;
	private final CrossType type;
	private final Currency ccy;
	
	public CrossVia(CurrencyPair ccyPair, CrossType type, Currency ccy) {
		this.ccyPair = ccyPair;
		this.type = type;
		this.ccy = ccy;
	}

	public CurrencyPair getCcyPair() {
		return ccyPair;
	}

	public CrossType getType() {
		return type;
	}

	public Currency getCcy() {
		return ccy;
	}

	@Override
	public String toString() {
		return "CrossVia [ccyPair=" + ccyPair + ", type=" + type + ", ccy=" + ccy + "]";
	}
	

}
