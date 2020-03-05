package pricing.rate;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import pricing.currency.CurrencyConfigLoader;
import pricing.currency.CurrencyFactory;
import pricing.currency.ICurrencyFactory;

public class RateFactoryTest {
	
	private ICurrencyFactory ccyFactory;
	private IRateFactory rateFactory;
	
	@Before
	public void setup() throws Exception {
		CurrencyConfigLoader currencyLoader = new CurrencyConfigLoader("/currency.config");
		ccyFactory = new CurrencyFactory(currencyLoader);
		RateConfigLoader loader = new RateConfigLoader("/rate.config", ccyFactory);
		rateFactory = new RateFactory(loader);
	}

	@Test
	public void testGetRate() throws Exception {
		double rate = rateFactory.getRate(ccyFactory.getCurrencyPair("AUDUSD"));
		assertEquals(0.8371, rate, 0.00001);
		
		rate = rateFactory.getRate(ccyFactory.getCurrencyPair("CADUSD"));
		assertEquals(0.8711, rate, 0.00001);
		
		rate = rateFactory.getRate(ccyFactory.getCurrencyPair("USDJPY"));
		assertEquals(119.95, rate, 0.00001);
		
		rate = rateFactory.getRate(ccyFactory.getCurrencyPair("EURCZK"));
		assertEquals(27.6028, rate, 0.00001);
	}

}
