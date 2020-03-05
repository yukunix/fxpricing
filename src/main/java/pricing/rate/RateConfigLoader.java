package pricing.rate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.Supplier;

import pricing.currency.Currency;
import pricing.currency.CurrencyPair;
import pricing.currency.ICurrencyFactory;
import pricing.util.Config;

public class RateConfigLoader implements Supplier<Map<CurrencyPair, Double>> {

	private final static String RATES_PREFIX = "RATE.";
	
	private final Map<CurrencyPair, Double> rates = new HashMap<CurrencyPair, Double>();
	
	public RateConfigLoader(String rateConfigFile, ICurrencyFactory currencyFactory) {
		try {
			Properties prop = Config.getProp(rateConfigFile);
			for (Currency base : currencyFactory.getAllCurrencies()) {
				for (Currency term : currencyFactory.getAllCurrencies()) {
					CurrencyPair pair = CurrencyPair.newCurrencyPair(base, term);
					Object rate = prop.get(RATES_PREFIX + pair.getName());
					if (rate != null) {
						rates.put(pair, Double.parseDouble((String)rate));
					}
				}
			}
		} catch (IOException e) {
			throw new IllegalArgumentException("error in reading rates file " + rateConfigFile);
		}
	}
	
	@Override
	public Map<CurrencyPair, Double> get() {
		return rates;
	}

}
