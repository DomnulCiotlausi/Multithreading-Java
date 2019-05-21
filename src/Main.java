import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class Main {

	@SuppressWarnings({ "unused", "unchecked" })
	public static void main(String[] args) {
		// check if there is a state of the program
		File file = new File("Backup.txt");
		if (file.exists()) {
			try {
				// if yes, start the input stream
				FileInputStream fis = new FileInputStream(file);
				ObjectInputStream ois = new ObjectInputStream(fis);

				try {
					// read all the objects saved
					ArrayList<Ingredient> ingredients = (ArrayList<Ingredient>) ois.readObject();
					ArrayList<Dish> dishes = (ArrayList<Dish>) ois.readObject();
					ArrayList<Supplier> suppliers = (ArrayList<Supplier>) ois.readObject();
					ArrayList<Order> orders = (ArrayList<Order>) ois.readObject();
					ArrayList<KitchenStaff> KS = (ArrayList<KitchenStaff>) ois.readObject();
					ArrayList<Drone> drones = (ArrayList<Drone>) ois.readObject();
					ArrayList<Postcode> postcodes = (ArrayList<Postcode>) ois.readObject();
					Inte orderID = (Inte) ois.readObject();
					Latch kitchenLatch = KS.get(0).getLatch();
					Latch droneLatch = drones.get(0).getLatch();

					// initialize all the threads and apps
					StockManagement sm = new StockManagement(ingredients, dishes, kitchenLatch, droneLatch, suppliers);
					CommunicationLayer cl = new CommunicationLayer(sm, postcodes, KS, drones, orderID);

					for (KitchenStaff ks : KS) {
						new Thread(new KitchenStaff(ks.getId(), sm, kitchenLatch)).start();
					}
					for (Drone d : drones) {
						new Thread(new Drone(d.getId(), d.getSpeed(), droneLatch, sm)).start();
					}

					ClientApplication ca = new ClientApplication(cl);
					BusinessApplication ba = new BusinessApplication(cl);

					MainThread mainThread = new MainThread(sm);
					MainThread2 mainThread2 = new MainThread2(cl);

					new Thread(mainThread).start();
					new Thread(mainThread2).start();

					// this part saves all the states of the app before closing
					Runtime.getRuntime()
							.addShutdownHook(new Thread(new DataPersistence(sm.getIngredients(), sm.getDishes(),
									sm.getSuppliers(), sm.getAllOrders(), cl.getKitchenStaff(), cl.getDrones(),
									cl.getPostcodes(), orderID)));
				} catch (ClassNotFoundException e) {
					System.err.println("Error while loading the backup1");
					System.exit(0);
				}
				ois.close();
			} catch (IOException e) {
				System.err.println("Error while loading backup2");
				System.exit(0);
			}
		} else {
			// this is a basic setup which i will leave for you so you don't
			// have to add everything from scratch

			Supplier s1 = new Supplier("Supplier1", 1000);
			Supplier s2 = new Supplier("Supplier2", 2000);
			Supplier s3 = new Supplier("Supplier3", 3000);
			ArrayList<Supplier> arraySupplier = new ArrayList<Supplier>();
			arraySupplier.add(s1);
			arraySupplier.add(s2);
			arraySupplier.add(s3);

			// set dishes
			Ingredient i1 = new Ingredient("Water", "litre", s1, 1);
			Ingredient i2 = new Ingredient("Salt", "kg", s1, 2);
			Ingredient i3 = new Ingredient("Sugar", "kg", s2, 3);
			Ingredient i4 = new Ingredient("Meat", "kg", s2, 4);
			Ingredient i5 = new Ingredient("Vegetable", "kg", s3, 5);
			Ingredient i6 = new Ingredient("Fruit", "kg", s3, 6);

			ArrayList<Ingredient> list = new ArrayList<Ingredient>();
			list.add(i1);
			list.add(i2);

			list = new ArrayList<Ingredient>();
			list.add(i3);
			list.add(i4);

			list = new ArrayList<Ingredient>();
			list.add(i5);
			list.add(i6);

			list = new ArrayList<Ingredient>();
			list.add(i1);
			list.add(i4);
			list.add(i5);

			Dish d1 = new Dish("Dish1", "Good.", 10, list);

			list = new ArrayList<Ingredient>();
			list.add(i1);
			list.add(i2);
			list.add(i6);

			Dish d2 = new Dish("Dish2", "Fine.", 20, list);

			list = new ArrayList<Ingredient>();
			list.add(i6);
			list.add(i3);

			Dish d3 = new Dish("Dish3", "OK.", 5, list);

			// set stock
			list = new ArrayList<Ingredient>();
			Ingredient i11 = new Ingredient("Water", "litre", s1, 10);
			Ingredient i22 = new Ingredient("Salt", "kg", s1, 10);
			Ingredient i33 = new Ingredient("Sugar", "kg", s2, 10);
			Ingredient i44 = new Ingredient("Meat", "kg", s2, 10);
			Ingredient i55 = new Ingredient("Vegetable", "kg", s3, 10);
			Ingredient i66 = new Ingredient("Fruit", "kg", s3, 10);
			list.add(i11);
			list.add(i22);
			list.add(i33);
			list.add(i44);
			list.add(i55);
			list.add(i66);

			ArrayList<Dish> dishes = new ArrayList<Dish>();
			dishes.add(d1);
			dishes.add(d2);
			dishes.add(d3);

			ArrayList<Postcode> postcodes = new ArrayList<Postcode>();
			postcodes.add(new Postcode("SO01 111", 1000));
			postcodes.add(new Postcode("SO02 222", 2000));
			postcodes.add(new Postcode("SO03 333", 3000));
			postcodes.add(new Postcode("SO04 444", 4000));
			postcodes.add(new Postcode("SO05 555", 5000));

			Latch kitchenLatch = new Latch();
			Latch droneLatch = new Latch();

			StockManagement sm = new StockManagement(list, dishes, kitchenLatch, droneLatch, arraySupplier);

			Drone drone1 = new Drone(1, 100, droneLatch, sm);
			Drone drone2 = new Drone(2, 100, droneLatch, sm);
			ArrayList<Drone> drones = new ArrayList<Drone>();
			drones.add(drone1);
			drones.add(drone2);

			KitchenStaff ks1 = new KitchenStaff(1, sm, kitchenLatch);
			KitchenStaff ks2 = new KitchenStaff(2, sm, kitchenLatch);
			ArrayList<KitchenStaff> kitchenStaff = new ArrayList<KitchenStaff>();
			kitchenStaff.add(ks1);
			kitchenStaff.add(ks2);

			new Thread(ks1).start();
			new Thread(ks2).start();

			new Thread(drone1).start();
			new Thread(drone2).start();

			CommunicationLayer cl = new CommunicationLayer(sm, postcodes, kitchenStaff, drones, new Inte(0));
			ClientApplication ca = new ClientApplication(cl);
			BusinessApplication ba = new BusinessApplication(cl);

			MainThread mainThread = new MainThread(sm);
			MainThread2 mainThread2 = new MainThread2(cl);

			new Thread(mainThread).start();
			new Thread(mainThread2).start();

			Runtime.getRuntime()
					.addShutdownHook(new Thread(new DataPersistence(sm.getIngredients(), sm.getDishes(),
							sm.getSuppliers(), sm.getAllOrders(), cl.getKitchenStaff(), cl.getDrones(),
							cl.getPostcodes(), cl.getOrderID())));
		}
	}
}
