package pricing;

import java.util.Map;
import java.util.Scanner;
import java.util.function.Supplier;

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

public class Main {

	public static void main(String[] args) {
		String currencyConfig = "/currency.config";
		CurrencyConfigLoader currencyLoader = new CurrencyConfigLoader(currencyConfig);
		ICurrencyFactory ccyFactory = new CurrencyFactory(currencyLoader);

		String crossMatrixFile = "/matrix.config";
		CrossMatrixConfigLoader crossMatrixLoader = new CrossMatrixConfigLoader(crossMatrixFile, ccyFactory);
		ICrossMatrix crossMatrix = new CrossMatrix(crossMatrixLoader);

		String rateConfigFile = "/rate.config";
		Supplier<Map<CurrencyPair, Double>> ratesLoader = new RateConfigLoader(rateConfigFile, ccyFactory);
		IRateFactory rateFactory = new RateFactory(ratesLoader);

		IFXCalculator fxCalculator = new FXCalculator(ccyFactory, crossMatrix, rateFactory);

		try (Scanner scanner = new Scanner(System.in)) {
			while (scanner.hasNextLine()) {
				String base = null;
				String term = null;
				try {
					String input = scanner.nextLine();
					String[] split = input.trim().split(" ");
					
					base = split[0].toUpperCase();
					String baseAmt = split[1];
					term = split[3].toUpperCase();
					
					String termAmt = fxCalculator.convert(base, Double.parseDouble(baseAmt), term);
					
					System.out.println(String.format("%s %s = %s %s", base, baseAmt, term, termAmt));
				} catch (Exception e) {
					System.out.println("Unable to find rate for " + base + "/" + term);
//					e.printStackTrace();
				}
			}
		}
	}

}
