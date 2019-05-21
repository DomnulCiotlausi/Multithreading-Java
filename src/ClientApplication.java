import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class ClientApplication extends JFrame {
	private Container content;
	private CommunicationLayer cl;
	private Client client;
	private int totalPrice = 0;

	public ClientApplication(CommunicationLayer cl) {
		super("Client Application");
		this.cl = cl;
		init();
	}

	private void init() {
		content = getContentPane();
		content.setLayout(new BorderLayout());

		content.add(first(), BorderLayout.NORTH);

		this.setSize(600, 400);
		setVisible(true);
	}

	// in this frame you chose login or sign up
	private JPanel first() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		JButton login = new JButton("Login");
		JButton signUp = new JButton("Sign Up");

		login.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				content.removeAll();
				content.add(login());
				content.revalidate();
			}
		});

		signUp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				content.removeAll();
				content.add(signUp());
				content.revalidate();
			}

		});

		panel.add(login);
		panel.add(signUp);
		return panel;
	}

	// sign up frame
	private JPanel signUp() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		int size = 20;
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		// username
		JPanel username = new JPanel();
		JLabel usernameLabel = new JLabel("Username:");
		JTextField usernameText = new JTextField(size);
		username.add(usernameLabel);
		username.add(usernameText);
		panel.add(username);

		// password
		JPanel password = new JPanel();
		JLabel passwordLabel = new JLabel("Password:");
		JPasswordField passwordText = new JPasswordField(size);
		password.add(passwordLabel);
		password.add(passwordText);
		panel.add(password);

		// Adress
		JPanel address = new JPanel();
		JLabel addressLabel = new JLabel("Adress:");
		JTextField addressText = new JTextField(size);
		address.add(addressLabel);
		address.add(addressText);
		panel.add(address);

		// Postcode
		JPanel postcode = new JPanel();
		JLabel postcodeLabel = new JLabel("Postcode:");
		String[] postcodeString = new String[cl.getPostcodes().size()];
		int index = 0;
		for (Postcode pc : cl.getPostcodes()) {
			postcodeString[index++] = pc.getPostcode();
		}
		JComboBox<String> jPostcodes = new JComboBox<String>(postcodeString);
		panel.add(postcodeLabel);
		postcode.add(jPostcodes);
		panel.add(postcode);

		// create new client
		JButton next = new JButton("Next");
		next.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					try {
						if (!cl.checkUser(usernameText.getText())) {
							System.err.println("User already created.");
							return;
						}
					} catch (ClassNotFoundException e1) {
					}
					client = cl.createUser(usernameText.getText(), passwordText.getText(), addressText.getText(),
							convertFromStringToPostcode((String) jPostcodes.getSelectedItem()));
					content.removeAll();
					content.add(loggedIn());
					content.revalidate();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				content.removeAll();
				content.add(first());
				content.revalidate();
			}
		});

		panel.add(next);

		mainPanel.add(panel, BorderLayout.NORTH);
		mainPanel.add(back, BorderLayout.SOUTH);
		return mainPanel;
	}

	private Postcode convertFromStringToPostcode(String string) {
		for (Postcode p : cl.getPostcodes()) {
			if (p.getPostcode().equals(string)) {
				return p;
			}
		}
		return null;
	}

	// login frame
	private JPanel login() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		int size = 20;
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		// username
		JPanel username = new JPanel();
		JLabel usernameLabel = new JLabel("Username:");
		JTextField usernameText = new JTextField(size);
		username.add(usernameLabel);
		username.add(usernameText);
		panel.add(username);

		// password
		JPanel password = new JPanel();
		JLabel passwordLabel = new JLabel("Password:");
		JPasswordField passwordText = new JPasswordField(size);
		password.add(passwordLabel);
		password.add(passwordText);
		panel.add(password);

		// check the user details
		JButton next = new JButton("Next");
		next.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					client = cl.checkUser(usernameText.getText(), passwordText.getText());
					if (client != null) {
						content.removeAll();
						content.add(loggedIn());
						content.revalidate();

					} else {
						System.err.println("Wrong username or password.");
					}
				} catch (ClassNotFoundException | IOException e1) {
					System.err.println("Error while checking for user.");
				}
			}
		});
		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				content.removeAll();
				content.add(first());
				content.revalidate();
			}
		});

		panel.add(next);

		mainPanel.add(panel, BorderLayout.NORTH);
		mainPanel.add(back, BorderLayout.SOUTH);
		return mainPanel;
	}

	
	// the logged in frame
	public JPanel loggedIn() {
		boolean available;
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		ArrayList<String> listDishes = new ArrayList<String>();
		for (Dish d : cl.getStockManagement().getAvailableDishes()) {
			listDishes.add(d.getName());
		}
		if (listDishes.isEmpty()) {
			available = false;
		} else {
			available = true;
		}

		String[] arrayDishes = listDishes.toArray(new String[0]);
		JComboBox<String> jDishes = new JComboBox<String>(arrayDishes);
		jDishes.addActionListener(new ActionListener() {
			private JPanel details = new JPanel();

			@Override
			public void actionPerformed(ActionEvent arg0) {
				panel.remove(details);
				details.removeAll();
				details.revalidate();
				details = dishDetails(
						cl.getStockManagement().convertFromStringToDish((String) jDishes.getSelectedItem()));
				details.add(new JLabel("Total Price: " + Integer.toString(totalPrice)));

				panel.add(details, BorderLayout.CENTER);
				panel.revalidate();
			}

		});

		DefaultListModel<String> model = new DefaultListModel<>();
		JList<String> selectedDishes = new JList<>(model);

		JButton selectDish = new JButton("Add");
		selectDish.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String name = (String) jDishes.getSelectedItem();
					model.addElement(name);
					totalPrice += cl.getStockManagement().convertFromStringToDish(name).getPrice();
				} catch (Exception e2) {
				}
			}
		});

		JButton removeDish = new JButton("Remove");
		removeDish.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = selectedDishes.getSelectedIndex();
				if (index != -1) {
					try {
						totalPrice -= cl.getStockManagement().convertFromStringToDish(selectedDishes.getSelectedValue())
								.getPrice();
						model.remove(index);
					} catch (Exception e2) {
					}
				}
			}
		});

		JButton order = new JButton("Order");
		order.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (model.getSize() == 0) {
					System.err.println("Basket empty.");
					return;
				}

				ArrayList<String> stringDishes = new ArrayList<String>();
				for (int i = 0; i < model.getSize(); i++) {
					stringDishes.add(model.getElementAt(i));
				}
				try {
					cl.createOrder(
							new Order(0, client, cl.getStockManagement().convertFromStringToDishes(stringDishes)));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				content.removeAll();
				content.add(orderPanel());
				content.revalidate();
			}
		});

		JButton viewPastOrders = new JButton("View past orders");
		viewPastOrders.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				content.removeAll();
				content.add(pastOrders());
				content.revalidate();
			}

		});

		JPanel subPanelNorth = new JPanel();
		if (available) {
			subPanelNorth.add(jDishes);
			subPanelNorth.add(selectDish);
			subPanelNorth.add(removeDish);
		} else {
			subPanelNorth.add(new JLabel("Nothing available at the moment."));
		}
		subPanelNorth.add(viewPastOrders);
		panel.add(subPanelNorth, BorderLayout.NORTH);
		panel.add(selectedDishes, BorderLayout.EAST);
		panel.add(order, BorderLayout.SOUTH);
		panel.add(stockLevels(), BorderLayout.WEST);

		return panel;
	}

	// past orders frame
	private JPanel pastOrders() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		ArrayList<Order> orders = null;
		try {
			orders = cl.getPastOrders(client);
			System.out.println(orders);
		} catch (ClassNotFoundException | IOException e) {
			System.err.println("Error while loading past orders.");
		}
		String[] orderName = new String[orders.size()];
		int i = 0;
		for (Order order : orders) {
			orderName[i++] = new String("Order nr. " + order.getId() + ", Status: " + order.getStatus());
			System.out.println(order.getStatus());
		}
		JComboBox<String> pastOrders = new JComboBox<String>(orderName);

		pastOrders.addActionListener(new ActionListener() {
			private JLabel info;
			private JPanel subPanel = new JPanel();

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					ArrayList<Dish> dishes = cl.getOrder(pastOrders.getSelectedIndex() + 1);
					subPanel.setLayout(new GridLayout(dishes.size() + 1, 3));
					subPanel.removeAll();
					subPanel.add(new JLabel("Name"));
					subPanel.add(new JLabel("Description"));
					subPanel.add(new JLabel("Price"));
					for (Dish d : dishes) {
						subPanel.add(new JLabel(d.getName()));
						subPanel.add(new JLabel(d.getDescription()));
						subPanel.add(new JLabel(Integer.toString(d.getPrice())));
					}
					panel.add(subPanel, BorderLayout.CENTER);
					panel.revalidate();
				} catch (ClassNotFoundException | IOException e1) {
					info = new JLabel("Error while loading past orders.");
					panel.add(info, BorderLayout.CENTER);
				}
			}
		});

		JButton update = new JButton("Update");
		update.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				content.removeAll();
				content.add(pastOrders());
				content.revalidate();
			}
		});

		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				content.removeAll();
				content.add(loggedIn());
				content.revalidate();
			}
		});

		JPanel subPanel = new JPanel();
		subPanel.add(update);
		subPanel.add(back);

		panel.add(pastOrders, BorderLayout.NORTH);
		panel.add(subPanel, BorderLayout.SOUTH);
		return panel;
	}

	// dish details frame
	private JPanel dishDetails(Dish dish) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		JLabel description = new JLabel("Description: " + dish.getDescription());
		JLabel price = new JLabel("Price: " + Integer.toString(dish.getPrice()));

		panel.add(description);
		panel.add(price);

		return panel;
	}

	// frame after making an order
	public JPanel orderPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		JLabel orderSucceful = new JLabel("Order has been made. Thank you for using our services.");
		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				content.removeAll();
				content.add(loggedIn());
				content.revalidate();
			}
		});
		panel.add(orderSucceful, BorderLayout.NORTH);
		panel.add(back, BorderLayout.SOUTH);

		return panel;
	}

	// the stock levels frame
	private JPanel stockLevels() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		for (Ingredient ing : cl.getStockManagement().getIngredients()) {
			JLabel jl = new JLabel(ing.getName() + " " + ing.getQuantity());
			panel.add(jl);
		}

		return panel;
	}
}
