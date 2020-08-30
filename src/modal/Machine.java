package modal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Machine {

	// A singleton class Machine which will do all the processing for the machine

	private static final Machine instance = new Machine();
	private int outletCount;
	private Inventory inventory;

	private Machine() {
		inventory = Inventory.getInstance();
	}

	public static Machine getInstance() {
		return instance;
	}

	public int getCount() {
		return outletCount;
	}

	public void setCount(int count) {
		this.outletCount = count;
	}

	public void addMachineIngredient(String name, Integer quantity) {
		// method to add ingredient in the machine

		Ingredient i = new Ingredient(name, quantity);
		inventory.addIngredient(i);
	}

	public void makeBeverage(String name, BeverageIngredient beverageIngredient) {
		// method to create a beverage
		// input: beverage name and the ingredients required

		Map<String, Integer> ingredients = beverageIngredient.getBevIngredients();
		List<String> inSufficientIngredients = new ArrayList<>();
		List<String> unAvailableIngredients = new ArrayList<>();
		boolean canPrepare = true;

		for (String iName : ingredients.keySet()) {
			Ingredient i = new Ingredient(iName, ingredients.get(iName));

			// check if a ingredient is available in stock or not
			if (inventory.isIngredientAvailable(i)) {

				// check if there is sufficient amount of ingredient available to make the
				// beverage
				if (!inventory.isIngredientSufficient(i)) {
					inSufficientIngredients.add(iName);
					canPrepare = false;
				}
			} else {
				unAvailableIngredients.add(iName);
				canPrepare = false;
			}
		}
		if (canPrepare) {
			// if all the ingredients are available
			// decrement the quantities required to make the beverage
			for (String iName : ingredients.keySet()) {
				Ingredient i = new Ingredient(iName, ingredients.get(iName));
				inventory.decrementIngredient(i);
			}
			System.out.println(name + " is prepared.");
		} else {
			// print all insufficient ingredients
			if (inSufficientIngredients.size() > 0) {
				String inSufficientIngredientNames = convertListToString(inSufficientIngredients);
				System.out.println(name + " cannot be prepared because item(s) " + inSufficientIngredientNames
						+ " are not sufficient.");
			}

			// print all unavailable ingredients
			if (unAvailableIngredients.size() > 0) {
				String unAvailableIngredientNames = convertListToString(unAvailableIngredients);
				System.out.println(name + " cannot be prepared because item(s) " + unAvailableIngredientNames
						+ " are not available.");
			}
		}
	}

	// checks which all ingredients are running low on quantity
	public void lowOnIngredients() {
		List<Ingredient> lowStock = inventory.lowOnIngredients();
		if (lowStock.size() == 0) {
			System.out.println("No item is running low.");
		} else {
			for (int i = 0; i < lowStock.size(); i++) {
				System.out.println(lowStock.get(i).getName() + " is running low, only " + lowStock.get(i).getQuantity()
						+ " unit(s) left.");
			}
		}
	}

	private String convertListToString(List<String> IngredientsList) {
		int size = IngredientsList.size();
		String result = size == 0 ? ""
				: size == 1 ? IngredientsList.get(0)
						: String.join(", ", IngredientsList.subList(0, --size)).concat(" and ")
								.concat(IngredientsList.get(size));

		return result;
	}

	public void processBeverages(Map<String, BeverageIngredient> beverages) {
		// TODO Auto-generated method stub
		// create a pool of threads, all the outlets can process in parallel
		ExecutorService threadPool = Executors.newFixedThreadPool(getCount());
		for (String beverageName : beverages.keySet()) {
			threadPool.submit(new Runnable() {
				public void run() {
					makeBeverage(beverageName, beverages.get(beverageName));
				}
			});
		}
		// shut down the thread pool after submitting all the tasks
		threadPool.shutdown();
		try {
			threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
