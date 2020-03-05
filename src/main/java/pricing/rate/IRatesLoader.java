package pricing.rate;

import java.util.Map;

import pricing.currency.CurrencyPair;

public interface IRatesLoader {
	
	Map<CurrencyPair, Double> getRates();

}
