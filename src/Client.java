import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Queue;

@SuppressWarnings("serial")
public class Client implements Serializable {
	private String username, password, address;
	private Queue<Order> previousOrders, currentOrders;
	private Postcode postcode;

	public Client(String username, String password, String address, Postcode postcode) {
		this.setUsername(username);
		this.setPassword(password);
		this.setAddress(address);
		this.setPostcode(postcode);
		this.previousOrders = new ArrayDeque<Order>();
		this.currentOrders = new ArrayDeque<Order>();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void addPreviousOrder(Order order) {
		previousOrders.add(order);
	}

	public void addCurrentOrder(Order order) {
		currentOrders.add(order);
	}

	public Postcode getPostcode() {
		return postcode;
	}

	public void setPostcode(Postcode postcode) {
		this.postcode = postcode;
	}

}
