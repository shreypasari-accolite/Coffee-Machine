package test;

import java.util.HashMap;
import java.util.Map;

import modal.BeverageIngredient;
import modal.Machine;

public class VendingMachineDriver {

	public void execute() {
		Machine machine = Machine.getInstance();
		machine.setCount(3);
		machine.addMachineIngredient("tea_leaves", 50);
		machine.addMachineIngredient("coffee_beans", 100);
		machine.addMachineIngredient("water", 500);
		machine.addMachineIngredient("tea_leaves", 590);
		machine.addMachineIngredient("water", 500);

		Map<String, BeverageIngredient> beverages = new HashMap<>();

		BeverageIngredient teaIngredients = new BeverageIngredient();
		teaIngredients.addBevIngredient("tea_leaves", 10);
		teaIngredients.addBevIngredient("water", 100);
		beverages.put("tea", teaIngredients);

		BeverageIngredient greenTeaIngredients = new BeverageIngredient();
		greenTeaIngredients.addBevIngredient("green_mixture", 100);
		greenTeaIngredients.addBevIngredient("water", 100);
		beverages.put("green_tea", greenTeaIngredients);

		BeverageIngredient hotCoffeeIngredients = new BeverageIngredient();
		hotCoffeeIngredients.addBevIngredient("coffee_beans", 50);
		hotCoffeeIngredients.addBevIngredient("water", 100);
		beverages.put("hot_coffee", hotCoffeeIngredients);

		BeverageIngredient hotMilkIngredients = new BeverageIngredient();
		hotMilkIngredients.addBevIngredient("milk", 200);
		hotMilkIngredients.addBevIngredient("water", 100);
		beverages.put("hot_milk", hotMilkIngredients);

		BeverageIngredient coldCoffeeIngredients = new BeverageIngredient();
		coldCoffeeIngredients.addBevIngredient("coffee_beans", 75);
		coldCoffeeIngredients.addBevIngredient("water", 100);
		coldCoffeeIngredients.addBevIngredient("milk", 100);
		beverages.put("cold_coffee", coldCoffeeIngredients);

		machine.processBeverages(beverages);
		machine.lowOnIngredients();
	}
}
