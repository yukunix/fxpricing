package pricing.rate;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import pricing.currency.CurrencyPair;

public class RateFactory implements IRateFactory {
	
	private final Map<CurrencyPair, Double> rates = new HashMap<CurrencyPair, Double>();
	
	public RateFactory(final Supplier<Map<CurrencyPair, Double>> ratesLoader) {
		rates.putAll(ratesLoader.get());
	}

	@Override
	public double getRate(CurrencyPair pair) throws NoRateException {
		Double rate = rates.get(pair);
		if (rate == null) {
			throw new NoRateException("no rate for currency pair " + pair);
		}
		return rate.doubleValue();
	}

}
