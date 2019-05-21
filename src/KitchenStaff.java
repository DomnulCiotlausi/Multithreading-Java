import java.io.Serializable;
import java.util.Random;

@SuppressWarnings("serial")
public class KitchenStaff implements Runnable, Serializable {
	private StockManagement sm;
	// each staff will have its own unique ID
	private int id;
	private Dish dish;
	// we use latches to notify the kitchen staff
	// all kitchen staff will have the same latch
	private Latch latch;
	// the status can be: waiting or preparing
	private String status;

	public KitchenStaff(int id, StockManagement sm, Latch latch) {
		this.setId(id);
		this.sm = sm;
		this.latch = latch;
	}

	@Override
	public synchronized void run() {
		while (true) {
			synchronized (latch) {
				try {
					setStatus("Waiting");
					// if there is nothign to do, wait
					latch.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// after being notified, get dish the first available dish
				Dish dish = sm.getDish();
				// if the dish is null, go to sleep
				if (dish != null) {
					// take the necessary ingredients to make the dish
					takeIngredients(dish);
					setStatus("Preparing " + dish.getName());
					prepareDish();
					// the the dish to the ready dishes
					sm.addDishReady(dish);
				}
			}
		}
	}

	// take the required ingredients
	public synchronized void takeIngredients(Dish dish) {
		for (Ingredient id : dish.getIngredients()) {
			for (Ingredient ism : sm.getIngredients()) {
				if (id.getName().equals(ism.getName())) {
					ism.removeQuantity(id.getQuantity());
				}
			}
		}
	}

	public void prepareDish() {
		// set the time for preparing the dish to be between 2 and 6 seconds
		int r = new Random().nextInt(4000) + 2000;
		try {
			Thread.sleep(r);
		} catch (InterruptedException e) {
			System.err.println("Error while preparing.");
		}
	}

	// getters and setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Dish getDish() {
		return dish;
	}

	public void setDish(Dish dish) {
		this.dish = dish;
	}

	public Latch getLatch() {
		return latch;
	}
}
