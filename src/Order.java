import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Order implements Serializable {

	// the client who made the order
	private Client client;
	// all the dishes in the order
	private ArrayList<Dish> dishes;
	// each order has its own unique id
	private int id;
	// the status of the order
	private String status;

	public Order(int id, Client client, ArrayList<Dish> dishes) {
		this.setClient(client);
		this.setDishes(dishes);
		this.setId(id);
		setStatus("Waiting");
	}

	// getters and setters
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public ArrayList<Dish> getDishes() {
		return dishes;
	}

	public void setDishes(ArrayList<Dish> dishes) {
		this.dishes = dishes;
	}

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
}
