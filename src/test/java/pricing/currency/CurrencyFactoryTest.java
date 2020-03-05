package pricing.currency;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class CurrencyFactoryTest {
	
	private final static Currency AUD = Currency.newCurrency("AUD", 2);
	private final static Currency USD = Currency.newCurrency("USD", 2);
	private final static Currency CNY = Currency.newCurrency("CNY", 2);
	private final static CurrencyPair AUDUSD = CurrencyPair.newCurrencyPair(AUD, USD);
	private final static CurrencyPair USDCNY = CurrencyPair.newCurrencyPair(USD, CNY);
	private final static CurrencyPair AUDCNY = CurrencyPair.newCurrencyPair(AUD, CNY);
	
	private CurrencyFactory ccyFactory;
	
	@Before
	public void setup() {
		CurrencyConfigLoader currencyLoader = new CurrencyConfigLoader("/currency.config");
		ccyFactory = new CurrencyFactory(currencyLoader);
	}

	@Test
	public void testGetCurrency() throws Exception {
		assertEquals(AUD, ccyFactory.getCurrency("AUD"));
		assertEquals(USD, ccyFactory.getCurrency("USD"));
		assertEquals(CNY, ccyFactory.getCurrency("CNY"));
		
		assertEquals(AUDUSD, ccyFactory.getCurrencyPair("AUDUSD"));
		assertEquals(USDCNY, ccyFactory.getCurrencyPair("USDCNY"));
		assertEquals(AUDCNY, ccyFactory.getCurrencyPair("AUDCNY"));
	}

	@Test(expected = UnsupportedCurrency.class)
	public void testGetCurrencyUnsupportedCurrency() throws UnsupportedCurrency  {
		ccyFactory.getCurrency("AUDxxxxx");
	}

	@Test(expected = UnsupportedCurrency.class)
	public void testGetCurrencyPairUnsupportedCurrency() throws UnsupportedCurrency  {
		ccyFactory.getCurrencyPair("AUDxxxxx");
	}
	
	@Test
	public void testGetAllCurrencies() {
		List<Currency> allCurrencies = ccyFactory.getAllCurrencies();
		assertEquals(11, allCurrencies.size());
	}

}
