package pricing.currency;

import java.util.List;

public interface ICurrencyFactory {
	
	Currency getCurrency(String currency) throws UnsupportedCurrency;
	
	CurrencyPair getCurrencyPair(String pair) throws UnsupportedCurrency;

	List<Currency> getAllCurrencies();
}
