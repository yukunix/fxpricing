package pricing;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import pricing.crossmatrix.CrossType;
import pricing.crossmatrix.CrossVia;
import pricing.crossmatrix.ICrossMatrix;
import pricing.currency.Currency;
import pricing.currency.CurrencyPair;
import pricing.currency.ICurrencyFactory;
import pricing.rate.IRateFactory;
import pricing.rate.NoRateException;

public class FXCalculator implements IFXCalculator {

	private final ICurrencyFactory currencyFactory;
	private final ICrossMatrix crossMatrix;
	private final IRateFactory rateFactory;

	public FXCalculator(ICurrencyFactory currencyFactory, ICrossMatrix crossMatrix, IRateFactory rateFactory) {
		this.currencyFactory = currencyFactory;
		this.crossMatrix = crossMatrix;
		this.rateFactory = rateFactory;
	}

	@Override
	public String convert(String base, double baseAmount, String term) throws Exception {
		Currency termCcy = currencyFactory.getCurrency(term);
		CurrencyPair pair = currencyFactory.getCurrencyPair(base + term);

		List<CrossVia> crosses = crossMatrix.getCrosses(pair);

		double termAmount = calculate(crosses, baseAmount);
		
		BigDecimal termAmt = new BigDecimal(termAmount)
				.setScale(termCcy.getPrecision(), RoundingMode.HALF_UP);
		return termAmt.toPlainString();
	}

	double calculate(List<CrossVia> crosses, double baseAmount) throws NoRateException {
		double termAmount = baseAmount;

		for (CrossVia crossVia : crosses) {
			CrossType type = crossVia.getType();
			double rate;
			switch (type) {
			case DIRECT:
				rate = rateFactory.getRate(crossVia.getCcyPair());
				termAmount = termAmount * rate;
				break;
			case INVERT:
				rate = rateFactory.getRate(crossVia.getCcyPair().getInvert());
				termAmount = termAmount / rate;
				break;
			case UNITY:
				break;
			default:
				System.err.println("unexpected cross currency " + crossVia.getCcyPair());
				break;
			}
		}

		return termAmount;
	}

}
