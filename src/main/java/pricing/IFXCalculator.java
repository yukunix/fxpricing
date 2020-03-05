package pricing;

public interface IFXCalculator {

	/**
	 * Convert specified amount of base currency to term currency
	 * 
	 * @param base       currency
	 * @param baseAmount double
	 * @param term       currency
	 * @return the amount of term currency equivalent to the {@code baseAmount} of
	 *         base currency
	 */
	String convert(String base, double baseAmount, String term) throws Exception;
}
