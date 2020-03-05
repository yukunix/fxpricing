package pricing.crossmatrix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import pricing.currency.Currency;
import pricing.currency.CurrencyPair;

public class CrossMatrix implements ICrossMatrix {
	
	private final Map<Currency, Map<Currency, CrossVia>> matrix = new HashMap<Currency, Map<Currency,CrossVia>>();
	
	public CrossMatrix(final Supplier<List<CrossVia>> crossMatrixLoader) {
		List<CrossVia> list = crossMatrixLoader.get();
		
		for (CrossVia crossVia : list) {
			Currency baseCcy = crossVia.getCcyPair().getBaseCcy();
			Map<Currency, CrossVia> row = matrix.get(baseCcy);
			if (row == null) {
				row = new HashMap<Currency, CrossVia>();
				matrix.put(baseCcy, row);
			}
			row.put(crossVia.getCcyPair().getTermCcy(), crossVia);
		}
	}
	
	@Override
	public CrossVia getCrossVia(CurrencyPair pair) {
		Map<Currency, CrossVia> row = matrix.get(pair.getBaseCcy());
		if (row != null) {
			return row.get(pair.getTermCcy());
		} else {
			return null;
		}
	}

	List<CrossVia> getCrosses(CurrencyPair pair, List<CrossVia> crosses) {
		CrossVia crossVia = getCrossVia(pair);
		CrossType type = crossVia.getType();
		if (type == CrossType.CURRENCY) {
			CurrencyPair leftPair = CurrencyPair.newCurrencyPair(pair.getBaseCcy(), crossVia.getCcy());
			getCrosses(leftPair, crosses);
			CurrencyPair rightPair = CurrencyPair.newCurrencyPair(crossVia.getCcy(), pair.getTermCcy());
			getCrosses(rightPair, crosses);
		} else {
			crosses.add(crossVia);
		}

		return crosses;
	}

	@Override
	public List<CrossVia> getCrosses(CurrencyPair pair) {
		List<CrossVia> crosses = new ArrayList<CrossVia>();
		getCrosses(pair, crosses);

		return crosses;
	}
}
