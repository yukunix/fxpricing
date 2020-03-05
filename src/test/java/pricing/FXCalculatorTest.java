package pricing;

import static org.junit.Assert.*;

import java.util.Map;
import java.util.function.Supplier;

import org.junit.Before;
import org.junit.Test;

import pricing.crossmatrix.CrossMatrix;
import pricing.crossmatrix.CrossMatrixConfigLoader;
import pricing.crossmatrix.ICrossMatrix;
import pricing.currency.CurrencyConfigLoader;
import pricing.currency.CurrencyFactory;
import pricing.currency.CurrencyPair;
import pricing.currency.ICurrencyFactory;
import pricing.rate.IRateFactory;
import pricing.rate.RateConfigLoader;
import pricing.rate.RateFactory;

public class FXCalculatorTest {

	private IFXCalculator calculator;
	
	@Before
	public void setup() throws Exception {
		String currencyConfig = "/currency.config";
		CurrencyConfigLoader currencyLoader = new CurrencyConfigLoader(currencyConfig);
		ICurrencyFactory ccyFactory = new CurrencyFactory(currencyLoader);

		String crossMatrixFile = "/matrix.config";
		CrossMatrixConfigLoader crossMatrixLoader = new CrossMatrixConfigLoader(crossMatrixFile, ccyFactory);
		ICrossMatrix crossMatrix = new CrossMatrix(crossMatrixLoader);

		String rateConfigFile = "/rate.config";
		Supplier<Map<CurrencyPair, Double>> ratesLoader = new RateConfigLoader(rateConfigFile, ccyFactory);
		IRateFactory rateFactory = new RateFactory(ratesLoader);
		
		calculator = new FXCalculator(ccyFactory, crossMatrix, rateFactory);
	}
	
	@Test
	public void testConvert() throws Exception {
		String base = "AUD";
		double baseAmount = 100;
		String term = "USD";
		assertEquals("83.71", calculator.convert(base, baseAmount, term));
		
		base = "AUD";
		baseAmount = 100;
		term = "AUD";
		assertEquals("100.00", calculator.convert(base, baseAmount, term));
		
		base = "AUD";
		baseAmount = 100;
		term = "DKK";
		assertEquals("505.76", calculator.convert(base, baseAmount, term));
		
		base = "JPY";
		baseAmount = 100;
		term = "USD";
		assertEquals("0.83", calculator.convert(base, baseAmount, term));
		
		base = "NOK";
		baseAmount = 100;
		term = "CZK";
		assertEquals("318.55", calculator.convert(base, baseAmount, term));
		
		base = "CZK";
		baseAmount = 100;
		term = "NOK";
		assertEquals("31.39", calculator.convert(base, baseAmount, term));
	}

}
