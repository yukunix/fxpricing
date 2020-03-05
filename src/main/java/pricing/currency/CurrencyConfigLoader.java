package pricing.currency;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.Supplier;

import pricing.util.Config;

public class CurrencyConfigLoader implements Supplier<Map<String, Currency>> {

	private static final String CURRENCIES = "CURRENCIES";
	private static final String PRECISION_PREFIX = "PRECISION.";
	private static final int DEFAULT_PRECISION = 2;
	
	private final Map<String, Currency> currencies = new HashMap<String, Currency>();
	
	public CurrencyConfigLoader(String currencyFile) {
		try {
			Properties prop = Config.getProp(currencyFile);
			String currencyList = (String) prop.get(CURRENCIES);
			String[] split = currencyList.split(",");
			for (String ccyName : split) {
				String precisionStr = (String) prop.getOrDefault(PRECISION_PREFIX + ccyName, DEFAULT_PRECISION);
				int precision = Integer.parseInt(precisionStr);
				Currency currency = Currency.newCurrency(ccyName, precision);
				currencies.put(ccyName, currency);
			}
		} catch (IOException e) {
			throw new IllegalArgumentException("error reading currency config file " + currencyFile);
		}
	}
	@Override
	public Map<String, Currency> get() {
		return currencies;
	}

}
