package modal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Inventory {
	// An inventory class which stores all the stock for the machine 
	// Singleton class are there can be only one place where all the stock is stored for the machine
	
	private static final Inventory inventoryInstance = new Inventory();
	
	// threshold amount, if any ingredient is below this amount it will be considered as running low
	private static final int lowQuantityThreshold = 50;

	// A map to store the ingredient name and its quantity in the machine
	private Map<String, Integer> stock;
	
	private Inventory() {
		stock = new HashMap<>();
	}
	
	public static Inventory getInstance() {
		return inventoryInstance;
	}
	
	public Map<String, Integer> getStock() {
		return stock;
	}
	
	// Add or update the ingredient in the stock
	public void addIngredient(Ingredient ingredient) {
		if (!stock.containsKey(ingredient.getName())) {
			stock.put(ingredient.getName(), 0);
		}
		stock.put(ingredient.getName(), stock.get(ingredient.getName())+ingredient.getQuantity());
	}
	
	// Check if a particular ingredient is available in stock or not
	public boolean isIngredientAvailable(Ingredient ingredient) {
		if (!stock.containsKey(ingredient.getName()))
			return false;
		return true;
			
	}
	
	// Check if a particular ingredient is sufficient to make a beverage or not
	public boolean isIngredientSufficient(Ingredient ingredient) {
		if (stock.get(ingredient.getName())>=ingredient.getQuantity())
			return true;
		return false;
			
	}
	
	// Decrement the quantity of ingredient used to make a beverage
	public void decrementIngredient(Ingredient ingredient) {
		stock.put(ingredient.getName(), stock.get(ingredient.getName())-ingredient.getQuantity());
	}
	
	// Return a list of ingredients which are running low on quantity
	public List<Ingredient> lowOnIngredients() {
		List<Ingredient> lowIngredients = new ArrayList<>();
		for (String ingredientName : stock.keySet()) {
			if (stock.get(ingredientName) <= lowQuantityThreshold) {
				lowIngredients.add(new Ingredient(ingredientName, stock.get(ingredientName)));
			}
		}
		return lowIngredients;
	}
}
