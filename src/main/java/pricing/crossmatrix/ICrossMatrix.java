package pricing.crossmatrix;

import java.util.List;

import pricing.currency.CurrencyPair;

public interface ICrossMatrix {

	/**
	 * 
	 * @param pair CurrencyPair to find cross for
	 * @return CrossVia if exists or null
	 */
	CrossVia getCrossVia(CurrencyPair pair);

	/**
	 * Find CrossVia('s) required to get conversion rate for the currency pair. For
	 * example, for calculating rate for AUDDKK, crosses AUDUSD(DIRECT),
	 * USDEUR(INVERT) and EURDKK(DIRECT) are the pairs required to involve.
	 * 
	 * @param pair CurrencyPair to find crosses for
	 * @return CrossVia('s) required to get conversion rate for the currency pair
	 */
	List<CrossVia> getCrosses(CurrencyPair pair);

}