import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

@SuppressWarnings("serial")
public class StockManagement implements Serializable {
	// all the ingredients in stock
	private ArrayList<Ingredient> ingredients;
	// all the dishes from the "menu"
	private ArrayList<Dish> dishes;
	// the restock level for the ingredients
	private int restockLevel;
	// all the orders
	private Queue<Order> orders;
	// orders ready to be delivered
	private Queue<Order> ordersReady;
	// dishes prepared
	private ArrayList<Dish> dishesReady;
	// dishes that needs to be prepared for an order
	private Queue<Dish> dishesToBePrepared;
	// the latches
	private Latch kitchenLatch, droneLatch;
	// the ingredients which fell below the restocking level
	private Queue<Ingredient> orderedIngredients;
	// suppliers
	private ArrayList<Supplier> arraySupplier;
	// orders completed
	private ArrayList<Order> ordersCompleted;
	// all the orders
	private ArrayList<Order> allOrders;
	private Queue<Order> order;

	public StockManagement(ArrayList<Ingredient> ingredients, ArrayList<Dish> dishes, Latch kitchenLatch,
			Latch droneLatch, ArrayList<Supplier> arraySupplier) {
		this.dishes = dishes;
		this.ingredients = ingredients;
		this.orders = new ArrayDeque<Order>();
		this.setOrdersReady(new ArrayDeque<Order>());
		this.dishesReady = new ArrayList<Dish>();
		this.dishesToBePrepared = new ArrayDeque<Dish>();
		this.kitchenLatch = kitchenLatch;
		this.droneLatch = droneLatch;
		this.orderedIngredients = new ArrayDeque<Ingredient>();
		this.restockLevel = 10;
		this.setSuppliers(arraySupplier);
		this.setOrdersCompleted(new ArrayList<Order>());
		this.setAllOrders(new ArrayList<Order>());
		this.order = new ArrayDeque<Order>();
	}

	public void addIngredients(Ingredient a) {
		for (Ingredient i : ingredients) {
			// if the ingredient is in stock, just increment its quantity
			if (i.getName().equals(a.getName())) {
				i.addQuantity(a.getQuantity());
				return;
			}
		}
		// if not, add it to the stock
		ingredients.add(a);
	}

	// getters and setters
	public void addDish(Dish dish) {
		this.dishes.add(dish);
	}

	public void removeDish(Dish dish) {
		this.dishes.remove(dish);
	}

	public ArrayList<Ingredient> getIngredients() {
		return ingredients;
	}

	public ArrayList<Dish> getDishes() {
		return dishes;
	}

	public void setDishes(ArrayList<Dish> dishes) {
		this.dishes = dishes;
	}

	public int getRestockLevel() {
		return restockLevel;
	}

	public void setRestockLevel(int restockLevel) {
		this.restockLevel = restockLevel;
	}

	public ArrayList<String> toStringIngredients() {
		ArrayList<String> names = new ArrayList<String>();
		for (Ingredient i : ingredients) {
			names.add(i.getName());
		}
		return names;
	}

	// add a new order
	public void addOrder(Order order) {
		order.setStatus("Hasn't been started");
		this.allOrders.add(order);
		orders.add(order);
		for (Dish d : order.getDishes()) {
			// add all the dishes from the order
			this.dishesToBePrepared.add(d);
		}
	}

	// convert from a given arraylist of string to arraylist of dishes
	public ArrayList<Dish> convertFromStringToDishes(ArrayList<String> string) {
		ArrayList<Dish> dishes = new ArrayList<Dish>();
		for (Dish d : this.dishes) {
			for (String s : string) {
				if (s.equals(d.getName())) {
					dishes.add(d);
				}
			}
		}
		return dishes;
	}

	// the same thing as above, but only for one dish
	public Dish convertFromStringToDish(String string) {
		for (Dish d : dishes) {
			if (d.getName().equals(string)) {
				return d;
			}
		}
		return null;
	}

	// get all the dishes which can be made with the current stock
	public ArrayList<Dish> getAvailableDishes() {
		ArrayList<Dish> availableDishes = new ArrayList<Dish>();
		for (Dish d : dishes) {
			for (Ingredient id : d.getIngredients()) {
				for (Ingredient ism : this.ingredients) {
					if (id.getName().equals(ism.getName())) {
						if (id.getQuantity() < ism.getQuantity()) {
							if (!availableDishes.contains(d)) {
								availableDishes.add(d);
								break;
							}
						}
					}
				}
			}
		}
		return availableDishes;
	}

	// check if we have all the necessary ingredients to make a dish from an order
	public void checkDish() {
		// if there are no dishes to be prepared, end the method
		if (this.dishesToBePrepared.isEmpty()) {
			return;
		}
		// boolean to check if we can make the dish
		boolean ok = true;
		Dish dish = this.dishesToBePrepared.peek();

		// check if we  have all the ingredients
		for (Ingredient id : dish.getIngredients()) {
			int quantity = -1;
			for (Ingredient ism : this.ingredients) {
				if (id.getName().equals(ism.getName())) {
					quantity = ism.getQuantity();
					if (id.getQuantity() < quantity) {
						quantity -= id.getQuantity();
						break;
					}
					if (quantity < 0) {
						ok = false;
					}
				}
			}
		}

		// if we do not have all the ingredients, move the checked dish at the end of the queue and the n end the method
		if (!ok) {
			this.dishesToBePrepared.add(this.dishesToBePrepared.poll());
			return;
		}
		// if we have all the ingredients, we begin the order so we change its status
		for (Order o : orders) {
			if (o.getDishes().contains(this.dishesToBePrepared.peek())) {
				o.setStatus("Being prepared");
			}
		}
		// and notify the staff to begin the dish
		synchronized (kitchenLatch) {
			kitchenLatch.notifyAll();
		}
	}

	// return the next dish to be prepared
	public synchronized Dish getDish() {
		return this.dishesToBePrepared.poll();
	}

	// check if any ingredient fell below the restocking level, if yes, order new ingredients
	public void checkIngredients() throws Exception {
		for (Ingredient i : this.ingredients) {
			if (i.getQuantity() < restockLevel) {
				this.orderNewIngredients(i);
				synchronized (droneLatch) {
					droneLatch.notifyAll();
				}
			}
		}
	}
	
	public void orderNewIngredients(Ingredient i) {
		if (this.orderedIngredients.contains(i)) {
			return;
		}
		this.orderedIngredients.add(i);
	}

	// get the ordered ingredient
	public Ingredient getOrderedIngredient() throws NullPointerException {
		Ingredient i = this.orderedIngredients.peek();
		this.orderedIngredients.add(i);
		return this.orderedIngredients.poll();
	}

	// add newly fetched ingredient
	public void addOrderedIngredients(Ingredient i) {
		for (Ingredient si : this.ingredients) {
			if (si.equals(i)) {
				si.addQuantity(restockLevel * 5);
			}
		}
		if (this.orderedIngredients == null) {
			return;
		}
		while (true) {
			try {
				if (this.orderedIngredients.peek().equals(i)) {
					this.orderedIngredients.poll();
					return;
				} else {
					this.orderedIngredients.add(this.orderedIngredients.poll());
				}
			} catch (Exception e) {
				return;
			}
		}
	}

	// add the newly made ingredient
	public void addDishReady(Dish dish) {
		this.dishesReady.add(dish);
	}

	
	// check if an order is ready to be delivered
	public synchronized void checkIfOrderIsReady() {
		// if there are no dishes ready, end method
		if (this.dishesReady.isEmpty()) {
			return;
		}
		// go through all the orders
		for (Order order : this.orders) {
			// if all the dishes from the orders are made
			boolean ok = this.dishesReady.containsAll(order.getDishes());
			if (ok) {
				this.order.add(order);
				// remove all the dishes from the order
				this.dishesReady.removeAll(order.getDishes());

				if (!orders.isEmpty()) {
					// remove the order from the list of orders
					while (true) {
						if (orders.peek().equals(order)) {
							orders.poll();
							break;
						} else {
							orders.add(orders.poll());
						}
					}
				}
				// notify drone to deliver the order
				synchronized (droneLatch) {
					droneLatch.notifyAll();
				}
			}
		}
	}

	// get the order fro the drones
	public Order getOrder() {
		synchronized (droneLatch) {
			Order order = this.order.poll();
			order.setStatus("Ready to be dispatched");
			this.ordersCompleted.add(order);
			return order;
		}
	}

	// getters and setters
	public ArrayList<Supplier> getSuppliers() {
		return arraySupplier;
	}

	public void setSuppliers(ArrayList<Supplier> arraySupplier) {
		this.arraySupplier = arraySupplier;
	}

	public void addSupplier(Supplier supplier) {
		this.arraySupplier.add(supplier);
	}

	public void removeSupplier(Supplier supplier) {
		this.arraySupplier.remove(supplier);
	}

	public Queue<Order> getOrdersReady() {
		return ordersReady;
	}

	public void setOrdersReady(Queue<Order> ordersReady) {
		this.ordersReady = ordersReady;
	}

	public Latch getKitchenLatch() {
		return this.kitchenLatch;
	}

	public Latch getDroneLatch() {
		return this.droneLatch;
	}

	public ArrayList<Order> getOrdersCompleted() {
		return ordersCompleted;
	}

	public void setOrdersCompleted(ArrayList<Order> ordersCompleted) {
		this.ordersCompleted = ordersCompleted;
	}

	public void clearCompletedOrders() {
		this.ordersCompleted.clear();
	}

	public ArrayList<Order> getAllOrders() {
		return allOrders;
	}

	public void setAllOrders(ArrayList<Order> allOrders) {
		this.allOrders = allOrders;
	}

}
