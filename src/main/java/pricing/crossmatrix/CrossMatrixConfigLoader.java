package pricing.crossmatrix;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.function.Supplier;

import pricing.currency.Currency;
import pricing.currency.CurrencyPair;
import pricing.currency.ICurrencyFactory;
import pricing.currency.UnsupportedCurrency;
import pricing.util.Config;

public class CrossMatrixConfigLoader implements Supplier<List<CrossVia>> {

	private final static String CROSS_PREFIX = "CROSS.";

	private final List<CrossVia> crosses = new ArrayList<CrossVia>();

	public CrossMatrixConfigLoader(String crossMatrixFile, ICurrencyFactory currencyFactory) {
		try {
			Properties prop = Config.getProp(crossMatrixFile);

			for (Currency base : currencyFactory.getAllCurrencies()) {
				for (Currency term : currencyFactory.getAllCurrencies()) {
					Object value = prop.get(CROSS_PREFIX + base.getName() + "." + term.getName());
					if (value != null) {
						String cross = (String) value;
						CrossType crossType;
						Currency crossCcy = null;
						try {
							crossType = CrossType.valueOf(cross);
						} catch (Exception e) {
							crossType = CrossType.CURRENCY;
							try {
								crossCcy = currencyFactory.getCurrency(cross);
							} catch (UnsupportedCurrency e1) {
								throw new IllegalArgumentException("unknown cross currency " + cross);
							}
						}
						crosses.add(new CrossVia(CurrencyPair.newCurrencyPair(base, term), crossType, crossCcy));
					}
				}
			}

		} catch (IOException e) {
			throw new IllegalArgumentException("error reading cross matrix config " + crossMatrixFile);
		}
	}

	@Override
	public List<CrossVia> get() {
		return crosses;
	}

}
