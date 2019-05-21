import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class CommunicationLayer {

	// all the online clients
	private ArrayList<Client> onlineClients;
	private StockManagement sm;
	private Inte orderID;
	private ArrayList<Postcode> postcodes;
	private ArrayList<KitchenStaff> kitchenStaff;
	private ArrayList<Drone> drones;
	// the ids of the kitchen staff and drones
	private int ksID, droneID;

	public CommunicationLayer(StockManagement sm, ArrayList<Postcode> postcodes, ArrayList<KitchenStaff> kitchenStaff,
			ArrayList<Drone> drones, Inte orderID) {
		this.setKitchenStaff(kitchenStaff);
		this.setDrones(drones);
		this.setPostcodes(postcodes);
		this.orderID = orderID;
		this.onlineClients = new ArrayList<Client>();
		this.setStockManagement(sm);

		// set the current ids to be equals
		try {
			ksID = kitchenStaff.get(kitchenStaff.size() - 1).getId();
		} catch (Exception e) {
			ksID = 0;
		}
		try {
			droneID = drones.get(drones.size() - 1).getId();
		} catch (Exception e) {
			droneID = 0;
		}

		// create the clients file directory if it doesn't exist
		File clientDir = new File("Clients");
		if (!clientDir.exists()) {
			new File("Clients").mkdir();
		}

		// create the order directory if it doesn't exist
		File orderDir = new File("Orders");
		if (!orderDir.exists()) {
			new File("Orders").mkdir();
		}
	}

	public Client createUser(String username, String password, String address, Postcode postcode) throws IOException {
		// create a new client file in the clients folder
		File file = new File("Clients" + File.separator + username + ".txt");
		// open the output stream
		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		Client client = new Client(username, password, address, postcode);
		this.onlineClients.add(client);
		onlineClients.add(client);
		// wirte the client object
		oos.writeObject(client);
		oos.flush();
		oos.close();

		file.createNewFile();
		return client;
	}

	// check if user exists for the login panel
	public Client checkUser(String username, String password) throws IOException, ClassNotFoundException {
		// go thorugh all the client from the client folder
		for (File f : new File("Clients").listFiles()) {
			// open the input stream
			FileInputStream fis = new FileInputStream(f);
			ObjectInputStream ois = new ObjectInputStream(fis);

			// if the username and password are found
			Client client = (Client) ois.readObject();
			if (client.getUsername().equals(username)) {
				if (client.getPassword().equals(password)) {
					if (onlineClients.contains(client)) {
						// if the user is already online
						System.err.println("Client already online.");
						ois.close();
						return null;
					} else {
						// add the clinet to the online clients
						onlineClients.add(client);
						ois.close();
						return client;
					}
				}
			}
			// close the stream
			ois.close();
		}
		return null;
	}

	// check if user exists for the sign up panel
	// in rest the same as above
	public boolean checkUser(String username) throws IOException, ClassNotFoundException {
		for (File f : new File("Clients").listFiles()) {
			FileInputStream fis = new FileInputStream(f);
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			Client client = (Client) ois.readObject();
			if (client.getUsername().equals(username)) {
				ois.close();
				return false;
			}
			ois.close();
		}
		System.err.println("Username or password incorrect.");
		return true;
	}

	public StockManagement getStockManagement() {
		return sm;
	}

	public void setStockManagement(StockManagement sm) {
		this.sm = sm;
	}

	// create a new order
	public void createOrder(Order order) throws IOException {
		// set the id of the order
		orderID.setId(orderID.getId() + 1);
		order.setId(orderID.getId());
		// add the order to the stock management
		sm.addOrder(order);

		// create order in file
		order.getClient().addCurrentOrder(order);

		// open the stream
		File file = new File("Orders" + File.separator + orderID.getId() + ".txt");
		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);

		// write the client object
		oos.writeObject(order);
		oos.flush();
		oos.close();

		// create the file
		file.createNewFile();
	}

	// every second we update all the orders in the files
	// in rest the same as above
	public void updateOrder(Order order) throws IOException {
		File file = new File("Orders" + File.separator + order.getId() + ".txt");
		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);

		oos.writeObject(order);
		oos.flush();
		oos.close();

		file.createNewFile();
	}

	public ArrayList<Postcode> getPostcodes() {
		return postcodes;
	}

	public void setPostcodes(ArrayList<Postcode> postcodes) {
		this.postcodes = postcodes;
	}

	// retrun the past orders of a client
	public ArrayList<Order> getPastOrders(Client client) throws IOException, ClassNotFoundException {
		ArrayList<Order> pastOrders = new ArrayList<Order>();
		// go throuh all the orders and get the orders from a specific client
		for (File f : new File("Orders").listFiles()) {
			FileInputStream fis = new FileInputStream(f);
			ObjectInputStream ois = new ObjectInputStream(fis);
			Order order = (Order) ois.readObject();
			if (client.getUsername().equals(order.getClient().getUsername())) {
				pastOrders.add(order);
			}
			ois.close();
		}
		return pastOrders;
	}

	// the same as above but return an the dishes from a specific order with a given id
	public ArrayList<Dish> getOrder(int id) throws IOException, ClassNotFoundException {
		for (File f : new File("Orders").listFiles()) {
			FileInputStream fis = new FileInputStream(f);
			ObjectInputStream ois = new ObjectInputStream(fis);
			Order order = (Order) ois.readObject();
			if (order.getId() == id) {
				ois.close();
				return order.getDishes();
			}
			ois.close();
		}
		return null;
	}

	// getters, setters and adders
	public ArrayList<KitchenStaff> getKitchenStaff() {
		return kitchenStaff;
	}

	public void setKitchenStaff(ArrayList<KitchenStaff> kitchenStaff) {
		this.kitchenStaff = kitchenStaff;
	}

	public void addKitchenStaff(KitchenStaff ks) {
		ks.setId(++ksID);
		this.kitchenStaff.add(ks);
		new Thread(ks).start();
	}

	public ArrayList<Drone> getDrones() {
		return drones;
	}

	public void setDrones(ArrayList<Drone> drones) {
		this.drones = drones;
	}

	public void addDrone(Drone d) {
		d.setId(++droneID);
		this.drones.add(d);
		new Thread(d).start();
	}

	public Inte getOrderID() {
		return this.orderID;
	}
	
	public ArrayList<Client> getOnlineClients(){
		return this.onlineClients;
	}

}
