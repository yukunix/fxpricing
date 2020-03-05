package pricing.currency;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class CurrencyFactory implements ICurrencyFactory {

	private final Map<String, Currency> currencies = new HashMap<String, Currency>();

	public CurrencyFactory(Supplier<Map<String, Currency>> currencyLoader) {
		currencies.putAll(currencyLoader.get());
	}

	@Override
	public Currency getCurrency(String currencyName) throws UnsupportedCurrency {
		Currency currency = currencies.get(currencyName);
		if (currency != null) {
			return currency;
		} else {
			throw new UnsupportedCurrency("unknown currency " + currencyName);
		}
	}

	@Override
	public CurrencyPair getCurrencyPair(String pair) throws UnsupportedCurrency {
		String base = pair.substring(0, 3);
		Currency baseCcy = currencies.get(base);
		String term = pair.substring(3);
		Currency termCcy = currencies.get(term);
		if (baseCcy == null || termCcy == null) {
			throw new UnsupportedCurrency("unknown ccy pair " + pair);
		}
		return CurrencyPair.newCurrencyPair(baseCcy, termCcy);
	}

	@Override
	public List<Currency> getAllCurrencies() {
		return new ArrayList<Currency>(currencies.values());
	}
}
