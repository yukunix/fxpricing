package pricing.crossmatrix;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import pricing.currency.Currency;
import pricing.currency.CurrencyConfigLoader;
import pricing.currency.CurrencyFactory;
import pricing.currency.CurrencyPair;
import pricing.currency.ICurrencyFactory;

public class CrossMatrixTest {

	private final static Currency AUD = Currency.newCurrency("AUD", 2);
	private final static Currency USD = Currency.newCurrency("USD", 2);
	private final static Currency CNY = Currency.newCurrency("CNY", 2);
	private final static Currency CZK = Currency.newCurrency("CZK", 2);
	private final static Currency NOK = Currency.newCurrency("NOK", 2);
	private static final Currency EUR = Currency.newCurrency("EUR", 2);
	private static final Currency DKK = Currency.newCurrency("DKK", 2);
	private static final Currency JPY = Currency.newCurrency("JPY", 0);
	private final static CurrencyPair AUDUSD = CurrencyPair.newCurrencyPair(AUD, USD);
	private final static CurrencyPair AUDAUD = CurrencyPair.newCurrencyPair(AUD, AUD);
	private final static CurrencyPair USDAUD = CurrencyPair.newCurrencyPair(USD, AUD);
	private final static CurrencyPair USDEUR = CurrencyPair.newCurrencyPair(USD, EUR);
	private final static CurrencyPair AUDCNY = CurrencyPair.newCurrencyPair(AUD, CNY);
	private final static CurrencyPair CZKNOK = CurrencyPair.newCurrencyPair(CZK, NOK);
	private final static CurrencyPair AUDDKK = CurrencyPair.newCurrencyPair(AUD, DKK);
	private final static CurrencyPair EURDKK = CurrencyPair.newCurrencyPair(EUR, DKK);
	
	
	private ICurrencyFactory ccyFactory;
	private ICrossMatrix crossMatrix;
	
	@Before
	public void setup() throws Exception {
		CurrencyConfigLoader currencyLoader = new CurrencyConfigLoader("/currency.config");
		ccyFactory = new CurrencyFactory(currencyLoader);
		
		CrossMatrixConfigLoader loader = new CrossMatrixConfigLoader("/matrix.config", ccyFactory);
		crossMatrix = new CrossMatrix(loader);
	}
	
	@Test
	public void testGetCrossVia() throws Exception {
		CrossVia crossVia = crossMatrix.getCrossVia(AUDUSD);
		assertEquals(CrossType.DIRECT, crossVia.getType());
		assertEquals(AUDUSD, crossVia.getCcyPair());
		assertNull(crossVia.getCcy());
		
		crossVia = crossMatrix.getCrossVia(USDAUD);
		assertEquals(CrossType.INVERT, crossVia.getType());
		assertEquals(USDAUD, crossVia.getCcyPair());
		assertNull(crossVia.getCcy());
		
		crossVia = crossMatrix.getCrossVia(AUDAUD);
		assertEquals(CrossType.UNITY, crossVia.getType());
		assertEquals(AUDAUD, crossVia.getCcyPair());
		assertNull(crossVia.getCcy());
		
		crossVia = crossMatrix.getCrossVia(AUDCNY);
		assertEquals(CrossType.CURRENCY, crossVia.getType());
		assertEquals(AUDCNY, crossVia.getCcyPair());
		assertEquals(USD, crossVia.getCcy());
		
		crossVia = crossMatrix.getCrossVia(CZKNOK);
		assertEquals(CrossType.CURRENCY, crossVia.getType());
		assertEquals(CZKNOK, crossVia.getCcyPair());
		assertEquals(EUR, crossVia.getCcy());
		
	}

	@Test
	public void testGetCrosses() throws Exception {
		List<CrossVia> crosses = crossMatrix.getCrosses(AUDDKK);
		assertEquals(3, crosses.size());
		
		CrossVia crossVia = crosses.get(0);
		assertEquals(CrossType.DIRECT, crossVia.getType());
		assertEquals(AUDUSD, crossVia.getCcyPair());
		assertNull(crossVia.getCcy());
		
		crossVia = crosses.get(1);
		assertEquals(CrossType.INVERT, crossVia.getType());
		assertEquals(USDEUR, crossVia.getCcyPair());
		assertNull(crossVia.getCcy());
		
		crossVia = crosses.get(2);
		assertEquals(CrossType.DIRECT, crossVia.getType());
		assertEquals(EURDKK, crossVia.getCcyPair());
		assertNull(crossVia.getCcy());
		
	}
}
