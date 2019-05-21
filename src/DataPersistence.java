import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class DataPersistence implements Runnable {
	// all the stats to be saved
	private ArrayList<Ingredient> ingredients;
	private ArrayList<Dish> dishes;
	private ArrayList<Order> orders;
	private ArrayList<KitchenStaff> KS;
	private ArrayList<Drone> drones;
	private ArrayList<Supplier> suppliers;
	private ArrayList<Postcode> postcodes;
	private Inte orderID;

	public DataPersistence(ArrayList<Ingredient> ingredients, ArrayList<Dish> dishes, ArrayList<Supplier> suppliers,
			ArrayList<Order> orders, ArrayList<KitchenStaff> KS, ArrayList<Drone> drones, ArrayList<Postcode> postcodes,
			Inte orderID) {
		this.setIngredients(ingredients);
		this.setDishes(dishes);
		this.setSuppliers(suppliers);
		this.setOrders(orders);
		this.setKS(KS);
		this.setDrones(drones);
		this.setPostcodes(postcodes);
		this.setOrderID(orderID);
	}

	@Override
	public void run() {
		// save the stats in the backup file
		File file = new File("Backup.txt");
		try {
			// open the stream
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			// write the objects
			oos.writeObject(this.getIngredients());
			oos.writeObject(this.getDishes());
			oos.writeObject(this.getSuppliers());
			oos.writeObject(this.getOrders());
			oos.writeObject(this.getKS());
			oos.writeObject(this.getDrones());
			oos.writeObject(this.getPostcodes());
			oos.writeObject(this.getOrderID());
			oos.flush();
			oos.close();

			// create the file or overwrite it
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// getters and setters
	public ArrayList<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(ArrayList<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	public ArrayList<Dish> getDishes() {
		return dishes;
	}

	public void setDishes(ArrayList<Dish> dishes) {
		this.dishes = dishes;
	}

	public ArrayList<Order> getOrders() {
		return orders;
	}

	public void setOrders(ArrayList<Order> orders) {
		this.orders = orders;
	}

	public ArrayList<KitchenStaff> getKS() {
		return KS;
	}

	public void setKS(ArrayList<KitchenStaff> kS) {
		KS = kS;
	}

	public ArrayList<Drone> getDrones() {
		return drones;
	}

	public void setDrones(ArrayList<Drone> drones) {
		this.drones = drones;
	}

	public ArrayList<Supplier> getSuppliers() {
		return suppliers;
	}

	public void setSuppliers(ArrayList<Supplier> suppliers) {
		this.suppliers = suppliers;
	}

	public ArrayList<Postcode> getPostcodes() {
		return postcodes;
	}

	public void setPostcodes(ArrayList<Postcode> postcodes) {
		this.postcodes = postcodes;
	}

	public Inte getOrderID() {
		return orderID;
	}

	public void setOrderID(Inte orderID) {
		this.orderID = orderID;
	}
}
