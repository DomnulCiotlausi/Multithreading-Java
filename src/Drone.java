import java.io.Serializable;

@SuppressWarnings("serial")
public class Drone implements Runnable, Serializable {

	// each drone has its own unique id
	private int id, speed;
	// all drones will use the same latch
	private Latch latch;
	private StockManagement sm;
	private Ingredient i;
	private Order order;
	private String status;

	public Drone(int id, int speed, Latch latch, StockManagement sm) {
		this.setId(id);
		this.setSpeed(speed);
		this.latch = latch;
		this.sm = sm;
	}

	@Override
	public synchronized void run() {
		while (true) {
			synchronized (latch) {
				try {
					setStatus("Waiting");
					latch.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				try {
					// take the order to be delivered
					order = sm.getOrder();
				} catch (Exception e) {
				}
				// if the order is null, then there are no orders to deliver, so the the notify came because there is an ingredient which fell below the restocking level
				if (order != null) {
					setStatus("Delivering order nr. " + order.getId());
					// deliver the order
					deliverOrder(order);
					System.err.println("Drone ID: " + id + ", Order ID: " + order.getId());
					order = null;
				} else {
					try {
						// if we get here, that means that the notify came because there is an ingredient to fetch
						i = sm.getOrderedIngredient();
					} catch (Exception e) {
					}
					if (i != null) {
						setStatus("Getting " + i.getName());
						getIngredient(i);
						System.err.println("Drone ID: " + id + ", Ingredient: " + i.getName());
						i = null;
					}
				}
			}
		}
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public void getIngredient(Ingredient i) {
		// set the time to be equal to the distance between the restaurant and the supplier times 2 and divided to the speed of the drone
		int time = ((i.getSupplier().getDist() * 2) / speed) * 100;
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// add the newly fetched ingredient
		sm.addOrderedIngredients(i);
	}
	
	// pretty self explanatory
	public void deliverOrder(Order order) {
		int time = (order.getClient().getPostcode().getDistance() / speed) * 100;
		try {
			order.setStatus("Dispatched");
			Thread.sleep(time);
			System.out.println("Order nr." + order.getId() + " delivered.");
			order.setStatus("Delivered");
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// getters and setter
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

	public Latch getLatch() {
		return latch;
	}
}
