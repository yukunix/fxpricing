package pricing.rate;

import pricing.currency.CurrencyPair;

public interface IRateFactory {

	/**
	 * 
	 * @param pair CurrencyPair - Major currency pair
	 * @return conversion rate for the major pair
	 */
	double getRate(CurrencyPair pair) throws NoRateException;

}
