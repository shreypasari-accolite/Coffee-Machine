package modal;

import java.util.HashMap;
import java.util.Map;

public class BeverageIngredient {
	// BeverageIngredient class stores all the ingredients used to make a beverage
	
	private Map<String, Integer> bevIngredients;

	public BeverageIngredient() {
		bevIngredients = new HashMap<>();
	}

	public void addBevIngredient(String name, Integer quantity) {
		bevIngredients.put(name, quantity);
	}

	public Map<String, Integer> getBevIngredients() {
		return bevIngredients;
	}

}
