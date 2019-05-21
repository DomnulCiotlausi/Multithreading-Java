import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

@SuppressWarnings("serial")
public class BusinessApplication extends JFrame {
	private Container content;
	private CommunicationLayer cl;
	private JPanel centerPanel = new JPanel();

	public BusinessApplication(CommunicationLayer cl) {
		super("BusinessApplication");
		this.cl = cl;
		init();
	}

	private void init() {
		content = getContentPane();
		content.setLayout(new BorderLayout());

		content.add(mainPanel(), BorderLayout.NORTH);

		this.setSize(600, 400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	// the main panel frame
	private JPanel mainPanel() {
		JPanel panel = new JPanel();

		JButton currentStock = new JButton("Current Stock");
		JButton orderStatus = new JButton("Order Status");
		JButton staffStatus = new JButton("Staff Status");

		currentStock.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				content.removeAll();
				content.add(stock());
				content.revalidate();
			}
		});

		orderStatus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				content.removeAll();
				content.add(order());
				content.revalidate();
			}
		});

		staffStatus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				content.removeAll();
				content.add(staff());
				content.revalidate();
			}
		});

		panel.add(currentStock);
		panel.add(orderStatus);
		panel.add(staffStatus);
		return panel;
	}

	// stock details frame
	private JPanel stock() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		int restockLevel = cl.getStockManagement().getRestockLevel();

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

		centerPanel.add(new JLabel("           Ingredients"));
		centerPanel.add(new JLabel(" "));
		for (Ingredient i : cl.getStockManagement().getIngredients()) {
			centerPanel.add(new JLabel("           " + i.getName() + ": " + i.getQuantity()));
		}

		JButton changeRestock = new JButton("Change Resotck Level");
		changeRestock.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cl.getStockManagement().setRestockLevel(10);
			}
		});

		SpinnerModel model = new SpinnerNumberModel(restockLevel, 0, 100, 1);
		JSpinner restockSpinner = new JSpinner(model);

		JButton applyRestock = new JButton("Apply");
		applyRestock.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cl.getStockManagement().setRestockLevel((int) restockSpinner.getValue());
				content.removeAll();
				content.add(stock());
				content.revalidate();
			}
		});

		JButton editIngredients = new JButton("Edit Ingredients");
		editIngredients.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				content.removeAll();
				content.add(editIngredients());
				content.revalidate();
			}
		});

		JButton addNewIngredient = new JButton("Add New Ingredient");
		addNewIngredient.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				content.removeAll();
				content.add(addNewIngredient());
				content.revalidate();
			}
		});

		JButton addNewDish = new JButton("Add New Dish");
		addNewDish.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				content.removeAll();
				content.add(addNewDish());
				content.revalidate();
			}
		});

		JButton editDish = new JButton("Edit Dishes");
		editDish.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				content.removeAll();
				content.add(editDish());
				content.revalidate();
			}
		});

		JButton removeIngredient = new JButton("Remove Ingredient");
		removeIngredient.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				content.removeAll();
				content.add(removeIngredient());
				content.revalidate();
			}
		});

		JButton removeDish = new JButton("Remove Dish");
		removeDish.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				content.removeAll();
				content.add(removeDish());
				content.revalidate();
			}
		});

		JButton addSupplier = new JButton("Add Supplier");
		addSupplier.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				content.removeAll();
				content.add(addSupplier());
				content.revalidate();
			}
		});

		JButton editSupplier = new JButton("Edit Supplier");
		editSupplier.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				content.removeAll();
				content.add(editSupplier());
				content.revalidate();
			}
		});

		JButton removeSupplier = new JButton("Remove Supplier");
		removeSupplier.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				content.removeAll();
				content.add(removeSupplier());
				content.revalidate();
			}
		});

		JButton update = new JButton("Update");
		update.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				content.removeAll();
				content.add(stock());
				content.revalidate();
			}
		});

		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				content.removeAll();
				content.add(mainPanel());
				content.revalidate();
			}
		});

		JPanel westPanel = new JPanel();
		westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
		westPanel.add(new JLabel("Dishes"));
		westPanel.add(new JLabel(" "));

		for (Dish d : cl.getStockManagement().getDishes()) {
			westPanel.add(new JLabel(d.getName()));
		}

		JPanel panelNorth = new JPanel();
		panelNorth.add(new JLabel("Restock level: " + restockLevel));
		panelNorth.add(new JLabel("  |  Change restock level:"));
		panelNorth.add(restockSpinner);
		panelNorth.add(applyRestock);
		panelNorth.add(update);

		JPanel panelEast = new JPanel();
		panelEast.setLayout(new BoxLayout(panelEast, BoxLayout.Y_AXIS));
		panelEast.add(editIngredients);
		panelEast.add(addNewIngredient);
		panelEast.add(removeIngredient);
		panelEast.add(addNewDish);
		panelEast.add(editDish);
		panelEast.add(removeDish);
		panelEast.add(addSupplier);
		panelEast.add(editSupplier);
		panelEast.add(removeSupplier);

		panel.add(panelNorth, BorderLayout.NORTH);
		panel.add(panelEast, BorderLayout.EAST);
		panel.add(centerPanel, BorderLayout.CENTER);
		panel.add(westPanel, BorderLayout.WEST);
		panel.add(back, BorderLayout.SOUTH);
		return panel;
	}

	// add a new supplier frame
	private JPanel addSupplier() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		JPanel subPanel = new JPanel();
		subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.Y_AXIS));

		JTextField name = new JTextField("Name", 20);

		SpinnerModel model = new SpinnerNumberModel(0, 0, 1000000, 1);
		JSpinner distSpinner = new JSpinner(model);

		JButton add = new JButton("Add");
		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cl.getStockManagement().addSupplier(new Supplier(name.getText(), (int) distSpinner.getValue()));
			}
		});

		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				content.removeAll();
				content.add(stock());
				content.revalidate();
			}
		});
		subPanel.add(name);
		subPanel.add(distSpinner);
		subPanel.add(add);

		panel.add(subPanel, BorderLayout.NORTH);
		panel.add(back, BorderLayout.SOUTH);
		return panel;
	}

	// edit a supplier frame
	private JPanel editSupplier() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		JPanel subPanel = new JPanel();
		subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.Y_AXIS));

		JPanel tempPanel = new JPanel();

		ArrayList<Supplier> suppliers = cl.getStockManagement().getSuppliers();
		String[] supplier = new String[suppliers.size()];
		for (int i = 0; i < supplier.length; i++) {
			supplier[i] = suppliers.get(i).getName();
		}
		JComboBox<String> jSupplier = new JComboBox<String>(supplier);
		jSupplier.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tempPanel.removeAll();
				tempPanel.setLayout(new BoxLayout(tempPanel, BoxLayout.Y_AXIS));
				Supplier s = suppliers.get(jSupplier.getSelectedIndex());

				JTextField name = new JTextField(s.getName(), 20);
				SpinnerModel model = new SpinnerNumberModel(s.getDist(), 0, 1000000, 1);
				JSpinner distSpinner = new JSpinner(model);

				JButton apply = new JButton("Apply");
				apply.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						s.setName(name.getText());
						s.setDist((int) distSpinner.getValue());
					}
				});

				tempPanel.add(name);
				tempPanel.add(distSpinner);
				tempPanel.add(apply);
				content.revalidate();
			}
		});

		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				content.removeAll();
				content.add(stock());
				content.revalidate();
			}
		});
		subPanel.add(jSupplier);
		subPanel.add(tempPanel);

		panel.add(subPanel, BorderLayout.NORTH);
		panel.add(back, BorderLayout.SOUTH);
		return panel;
	}

	// remove a supplier frame
	private JPanel removeSupplier() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		JPanel subPanel = new JPanel();
		subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.Y_AXIS));

		ArrayList<Supplier> suppliers = cl.getStockManagement().getSuppliers();
		String[] supplier = new String[suppliers.size()];
		for (int i = 0; i < supplier.length; i++) {
			supplier[i] = suppliers.get(i).getName();
		}
		JComboBox<String> jSupplier = new JComboBox<String>(supplier);

		JButton remove = new JButton("Remove");
		remove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Supplier s = suppliers.get(jSupplier.getSelectedIndex());
					suppliers.remove(s);
				} catch (Exception e1) {
					System.err.println("No more suppliers to remove");
				}

				content.removeAll();
				content.add(removeSupplier());
				content.revalidate();
			}
		});

		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				content.removeAll();
				content.add(stock());
				content.revalidate();
			}
		});
		subPanel.add(jSupplier);
		subPanel.add(remove);

		panel.add(subPanel, BorderLayout.NORTH);
		panel.add(back, BorderLayout.SOUTH);
		return panel;
	}

	// remove an ingredient frame
	private JPanel removeIngredient() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		JPanel subPanel = new JPanel();
		subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.Y_AXIS));

		ArrayList<Ingredient> ingredients = cl.getStockManagement().getIngredients();
		String[] name = new String[ingredients.size()];
		for (int i = 0; i < name.length; i++) {
			name[i] = ingredients.get(i).getName();
		}
		JComboBox<String> jIngredients = new JComboBox<String>(name);

		JButton remove = new JButton("Remove");
		remove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					ingredients.remove(jIngredients.getSelectedIndex());
				} catch (Exception e1) {
					System.err.println("No more ingredients.");
				}
				content.removeAll();
				content.add(removeIngredient());
				content.revalidate();
			}
		});

		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				content.removeAll();
				content.add(stock());
				content.revalidate();
			}
		});

		subPanel.add(jIngredients);
		subPanel.add(remove);

		panel.add(subPanel, BorderLayout.NORTH);
		panel.add(back, BorderLayout.SOUTH);
		return panel;
	}

	// remove a dish frame
	private JPanel removeDish() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		JPanel subPanel = new JPanel();
		subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.Y_AXIS));

		ArrayList<Dish> dishes = cl.getStockManagement().getDishes();
		String[] name = new String[dishes.size()];
		for (int i = 0; i < name.length; i++) {
			name[i] = dishes.get(i).getName();
		}
		JComboBox<String> jDishes = new JComboBox<String>(name);

		JButton remove = new JButton("Remove");
		remove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					dishes.remove(jDishes.getSelectedIndex());
				} catch (Exception e1) {
					System.err.println("No more dishes.");
				}
				content.removeAll();
				content.add(removeDish());
				content.revalidate();
			}
		});

		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				content.removeAll();
				content.add(stock());
				content.revalidate();
			}
		});

		subPanel.add(jDishes);
		subPanel.add(remove);

		panel.add(subPanel, BorderLayout.NORTH);
		panel.add(back, BorderLayout.SOUTH);
		return panel;
	}


	// add a new dish frame
	private JPanel addNewDish() {
		centerPanel.removeAll();
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		JTextField name = new JTextField("Name", 20);
		JTextField description = new JTextField("Description", 20);

		SpinnerModel model = new SpinnerNumberModel(0, 0, 1000, 1);
		JSpinner priceSpinner = new JSpinner(model);

		ArrayList<Ingredient> ingredients = cl.getStockManagement().getIngredients();
		String[] ingredientName = new String[ingredients.size()];
		for (int i = 0; i < ingredientName.length; i++) {
			ingredientName[i] = ingredients.get(i).getName();
		}
		JComboBox<String> jIngredient = new JComboBox<String>(ingredientName);

		ArrayList<Ingredient> dishIngredients = new ArrayList<Ingredient>();
		SpinnerModel model2 = new SpinnerNumberModel(0, 0, 100, 1);
		JSpinner quantitySpinner = new JSpinner(model2);

		JButton add = new JButton("Add");
		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Ingredient i = ingredients.get(jIngredient.getSelectedIndex());
				for (Ingredient in : dishIngredients) {
					if (in.getName().equals(i.getName())) {
						System.err.println("Ingredient already selected.");
						return;
					}
				}
				dishIngredients.add(
						new Ingredient(i.getName(), i.getUnit(), i.getSupplier(), (int) quantitySpinner.getValue()));
				centerPanel.add(new JLabel(i.getName() + " " + (int) quantitySpinner.getValue()));
				centerPanel.revalidate();
			}
		});

		JButton apply = new JButton("Apply");
		apply.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// AICI COAE
				cl.getStockManagement().addDish(new Dish(name.getText(), description.getText(),
						(int) priceSpinner.getValue(), dishIngredients));
			}
		});

		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				content.removeAll();
				content.add(stock());
				content.revalidate();
			}
		});

		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

		centerPanel.add(name);
		centerPanel.add(description);
		centerPanel.add(jIngredient);
		centerPanel.add(new JLabel("Quantity"));
		centerPanel.add(quantitySpinner);
		centerPanel.add(new JLabel("Price"));
		centerPanel.add(priceSpinner);
		centerPanel.add(add);
		centerPanel.add(apply);

		panel.add(centerPanel, BorderLayout.NORTH);
		panel.add(back, BorderLayout.SOUTH);
		return panel;
	}

	// edit a dish frame
	private JPanel editDish() {
		centerPanel.removeAll();
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		ArrayList<Dish> dishes = cl.getStockManagement().getDishes();
		String[] dishName = new String[dishes.size()];
		for (int i = 0; i < dishName.length; i++) {
			dishName[i] = dishes.get(i).getName();
		}
		JComboBox<String> jDish = new JComboBox<String>(dishName);

		JButton select = new JButton("Modify");
		select.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				content.removeAll();
				content.add(editDish2(dishes.get(jDish.getSelectedIndex())));
				content.revalidate();
			}
		});

		JButton remove = new JButton("Remove");
		remove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					cl.getStockManagement().removeDish(dishes.get(jDish.getSelectedIndex()));
				} catch (Exception e1) {
					System.err.println("No more dishes");
				}
				content.removeAll();
				content.add(editDish());
				content.revalidate();
			}
		});

		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				content.removeAll();
				content.add(stock());
				content.revalidate();
			}
		});

		JPanel subPanel = new JPanel();
		subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.Y_AXIS));
		subPanel.add(jDish);
		subPanel.add(select);
		subPanel.add(remove);

		panel.add(subPanel, BorderLayout.NORTH);
		panel.add(back, BorderLayout.SOUTH);
		return panel;
	}

	// edit a specific dish frame
	private JPanel editDish2(Dish dish) {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		JPanel subPanel = new JPanel();
		subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.Y_AXIS));

		JTextField name = new JTextField(dish.getName(), 20);
		JTextField description = new JTextField(dish.getDescription(), 20);

		SpinnerModel model = new SpinnerNumberModel(dish.getPrice(), 0, 1000, 1);
		JSpinner priceSpinner = new JSpinner(model);

		ArrayList<Ingredient> ingredients = dish.getIngredients();
		String[] ingredientName = new String[ingredients.size()];
		for (int i = 0; i < ingredientName.length; i++) {
			ingredientName[i] = ingredients.get(i).getName();
		}
		JComboBox<String> jIngredient = new JComboBox<String>(ingredientName);

		JPanel tempPanel = new JPanel();
		JButton selectIngredient = new JButton("Select Ingredient");
		selectIngredient.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tempPanel.removeAll();
				tempPanel.setLayout(new BoxLayout(tempPanel, BoxLayout.Y_AXIS));

				JPanel modify = new JPanel();
				modify.setLayout(new BoxLayout(modify, BoxLayout.Y_AXIS));

				JButton remove = new JButton("Remove Ingredient");
				remove.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						ingredients.remove(ingredients.get(jIngredient.getSelectedIndex()));
						content.removeAll();
						content.add(editDish2(dish));
						content.revalidate();
					}
				});

				tempPanel.add(remove);
				subPanel.add(tempPanel);
				content.revalidate();
			}
		});

		JButton apply = new JButton("Apply Changes");
		apply.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dish.setName(name.getText());
				dish.setDescription(description.getText());
				dish.setPrice((int) priceSpinner.getValue());
			}
		});

		JPanel tempPanel2 = new JPanel();
		JButton addNewIngredient = new JButton("Add New Ingredient");
		addNewIngredient.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tempPanel2.removeAll();
				tempPanel2.setLayout(new BoxLayout(tempPanel2, BoxLayout.Y_AXIS));

				ArrayList<Ingredient> smIngredient = cl.getStockManagement().getIngredients();
				String[] smIngredientName = new String[smIngredient.size()];
				for (int i = 0; i < smIngredientName.length; i++) {
					smIngredientName[i] = smIngredient.get(i).getName();
				}
				JComboBox<String> jsmIngredient = new JComboBox<String>(smIngredientName);

				SpinnerModel model3 = new SpinnerNumberModel(0, 0, 100, 1);
				JSpinner quantitySpinner = new JSpinner(model3);

				JButton selectIngredient = new JButton("Add Ingredient");
				selectIngredient.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						Ingredient i = smIngredient.get(jsmIngredient.getSelectedIndex());
						ingredients.add(new Ingredient(i.getName(), i.getUnit(), i.getSupplier(),
								(int) quantitySpinner.getValue()));
						content.removeAll();
						content.add(editDish2(dish));
						content.revalidate();
					}
				});

				tempPanel2.add(jsmIngredient);
				tempPanel2.add(quantitySpinner);
				tempPanel2.add(selectIngredient);
				subPanel.add(tempPanel2);
				content.revalidate();
			}
		});

		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				content.removeAll();
				content.add(editDish());
				content.revalidate();
			}
		});

		subPanel.add(new JLabel(dish.getName()));
		subPanel.add(name);
		subPanel.add(description);
		subPanel.add(new JLabel("Price"));
		subPanel.add(priceSpinner);
		subPanel.add(jIngredient);
		subPanel.add(selectIngredient);
		subPanel.add(addNewIngredient);
		subPanel.add(apply);

		panel.add(subPanel, BorderLayout.NORTH);
		panel.add(back, BorderLayout.SOUTH);
		return panel;
	}

	// add a new ingredient frame
	private JPanel addNewIngredient() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		JTextField name = new JTextField("Name", 20);
		JTextField unit = new JTextField("Unit", 20);

		ArrayList<Supplier> arraySupplier = cl.getStockManagement().getSuppliers();
		String[] suppliersString = new String[arraySupplier.size()];
		for (int j = 0; j < suppliersString.length; j++) {
			suppliersString[j] = arraySupplier.get(j).getName();
		}
		JComboBox<String> jSuppliers = new JComboBox<String>(suppliersString);

		SpinnerModel model = new SpinnerNumberModel(0, 0, 100, 1);
		JSpinner quantitySpinner = new JSpinner(model);

		JButton add = new JButton("Add Ingredient");
		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Ingredient i = new Ingredient(name.getText(), unit.getText(),
						arraySupplier.get(jSuppliers.getSelectedIndex()), (int) quantitySpinner.getValue());
				if (!cl.getStockManagement().getIngredients().contains(i)) {
					cl.getStockManagement().addIngredients(i);
				} else {
					System.err.println("Ingredient already in stock.");
				}

			}
		});

		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				content.removeAll();
				content.add(stock());
				content.revalidate();
			}
		});

		JPanel subPanel = new JPanel();
		subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.Y_AXIS));
		subPanel.add(name);
		subPanel.add(unit);
		subPanel.add(jSuppliers);
		subPanel.add(quantitySpinner);
		subPanel.add(add);

		panel.add(subPanel, BorderLayout.NORTH);
		panel.add(back, BorderLayout.SOUTH);
		return panel;
	}

	// edit an ingredient frame
	private JPanel editIngredients() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		ArrayList<Ingredient> arrayIngredient = cl.getStockManagement().getIngredients();
		String[] arrayIngredientName = new String[arrayIngredient.size()];
		for (int i = 0; i < arrayIngredientName.length; i++) {
			arrayIngredientName[i] = arrayIngredient.get(i).getName();
		}
		ArrayList<Supplier> arraySupplier = cl.getStockManagement().getSuppliers();
		JComboBox<String> jIngredients = new JComboBox<String>(arrayIngredientName);
		jIngredients.addActionListener(new ActionListener() {
			private JPanel details = new JPanel();

			@Override
			public void actionPerformed(ActionEvent arg0) {
				panel.remove(details);
				details.removeAll();
				details.add(ingredient());
				panel.add(details);
				panel.revalidate();
			}

			private JPanel ingredient() {
				JPanel panel = new JPanel();
				panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

				int i = jIngredients.getSelectedIndex();
				Ingredient ingredient = arrayIngredient.get(i);

				JTextField name = new JTextField(ingredient.getName(), 20);
				JTextField unit = new JTextField(ingredient.getUnit(), 20);

				String[] suppliersString = new String[arraySupplier.size()];
				for (int j = 0; j < suppliersString.length; j++) {
					suppliersString[j] = arraySupplier.get(j).getName();
				}
				JComboBox<String> jSuppliers = new JComboBox<String>(suppliersString);

				SpinnerModel model = new SpinnerNumberModel(ingredient.getQuantity(), 0, 100, 1);
				JSpinner quantitySpinner = new JSpinner(model);

				JButton apply = new JButton("Apply");
				apply.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						ingredient.setName(name.getText());
						ingredient.setUnit(unit.getText());
						ingredient.setSupplier(arraySupplier.get(jSuppliers.getSelectedIndex()));
						ingredient.setQuantity((int) quantitySpinner.getValue());
					}
				});

				panel.add(name);
				panel.add(unit);
				panel.add(jSuppliers);
				panel.add(quantitySpinner);
				panel.add(apply);
				return panel;
			}

		});

		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				content.removeAll();
				content.add(stock());
				content.revalidate();
			}
		});

		panel.add(jIngredients, BorderLayout.NORTH);
		panel.add(back, BorderLayout.SOUTH);
		return panel;
	}

	// frame about orders
	private JPanel order() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		JPanel subPanel = new JPanel();

		JButton status = new JButton("Status");
		status.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				content.removeAll();
				content.add(orderStatus());
				content.revalidate();
			}
		});

		JButton removeCompletedOrders = new JButton("Remove Completed Orders");
		removeCompletedOrders.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				content.removeAll();
				content.add(removeCompletedOrders());
				content.revalidate();
			}
		});

		JButton removeSpecificOrder = new JButton("Remove Specific Orders");
		removeSpecificOrder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				content.removeAll();
				content.add(removeSpecificOrder());
				content.revalidate();
			}
		});

		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				content.removeAll();
				content.add(mainPanel());
				content.revalidate();
			}
		});

		subPanel.add(status);
		subPanel.add(removeCompletedOrders);
		subPanel.add(removeSpecificOrder);

		panel.add(subPanel, BorderLayout.NORTH);
		panel.add(back, BorderLayout.SOUTH);
		return panel;
	}

	// order's status frame
	private JPanel orderStatus() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		JPanel subPanel = new JPanel();
		subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.Y_AXIS));

		JPanel subPanel2 = new JPanel();
		subPanel2.setLayout(new BoxLayout(subPanel2, BoxLayout.Y_AXIS));

		ArrayList<Order> orders = cl.getStockManagement().getAllOrders();
		for (Order o : orders) {
			subPanel.add(new JLabel("Order nr. " + o.getId() + ": " + o.getStatus()));
		}

		JButton update = new JButton("Update");
		update.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				content.removeAll();
				content.add(orderStatus());
				content.revalidate();
			}
		});

		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				content.removeAll();
				content.add(order());
				content.revalidate();
			}
		});

		subPanel2.add(update);
		panel.add(subPanel2, BorderLayout.EAST);
		panel.add(subPanel, BorderLayout.NORTH);
		panel.add(back, BorderLayout.SOUTH);
		return panel;
	}

	// remove completed order frame
	private JPanel removeCompletedOrders() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		JLabel mess;
		try {
			ArrayList<Order> orders = cl.getStockManagement().getOrdersCompleted();
			cl.getStockManagement().getAllOrders().removeAll(orders);

			for (Order order : orders) {
				File file = new File("Orders" + File.separator + order.getId() + ".txt");
				file.delete();
			}
			mess = new JLabel("Removal succesfull");
		} catch (Exception e1) {
			System.err.println("Error removing completed orders");
			mess = new JLabel("Error removing completed orders");
		}

		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				content.removeAll();
				content.add(order());
				content.revalidate();
			}
		});

		panel.add(mess, BorderLayout.NORTH);
		panel.add(back, BorderLayout.SOUTH);
		return panel;

	}

	// remove specific order frame
	private JPanel removeSpecificOrder() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		JPanel subPanel = new JPanel();
		subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.Y_AXIS));
		JPanel tempPanel = new JPanel();

		ArrayList<Order> orders = cl.getStockManagement().getAllOrders();
		String[] name = new String[orders.size()];
		for (int i = 0; i < name.length; i++) {
			name[i] = "Order nr. " + orders.get(i).getId();
		}
		JComboBox<String> jOrders = new JComboBox<String>(name);
		jOrders.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tempPanel.removeAll();
				tempPanel.setLayout(new BoxLayout(tempPanel, BoxLayout.Y_AXIS));

				JButton remove = new JButton("Remove");
				remove.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							orders.remove(jOrders.getSelectedIndex());
							content.removeAll();
							content.add(removeSpecificOrder());
							content.revalidate();
						} catch (Exception e1) {
							System.err.println("No more orders to remove");
						}
					}
				});

				tempPanel.add(remove);
				subPanel.add(tempPanel);
				content.revalidate();
			}
		});

		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				content.removeAll();
				content.add(order());
				content.revalidate();
			}
		});
		subPanel.add(jOrders);

		panel.add(subPanel, BorderLayout.NORTH);
		panel.add(back, BorderLayout.SOUTH);
		return panel;
	}

	// staff frame
	private JPanel staff() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		JPanel kitchenPanel = new JPanel();
		kitchenPanel.setLayout(new BoxLayout(kitchenPanel, BoxLayout.Y_AXIS));
		for (KitchenStaff ks : cl.getKitchenStaff()) {
			kitchenPanel.add(new JLabel("Kitchen Staff " + ks.getId() + ": " + ks.getStatus()));
		}

		JPanel dronePanel = new JPanel();
		dronePanel.setLayout(new BoxLayout(dronePanel, BoxLayout.Y_AXIS));
		for (Drone d : cl.getDrones()) {
			dronePanel.add(new JLabel("Drone " + d.getId() + ": " + d.getStatus()));
		}

		JButton addKS = new JButton("Add Kitchen Staff");
		addKS.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				content.removeAll();
				cl.addKitchenStaff(
						new KitchenStaff(3, cl.getStockManagement(), cl.getStockManagement().getKitchenLatch()));
				content.add(staff());
				content.revalidate();
			}
		});

		JButton addDrone = new JButton("Add Drone");
		addDrone.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				content.removeAll();
				cl.addDrone(new Drone(3, 100, cl.getStockManagement().getDroneLatch(), cl.getStockManagement()));
				content.add(staff());
				content.revalidate();
			}
		});

		JButton editKS = new JButton("Remove Kitchen Staff");
		editKS.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				content.removeAll();
				content.add(editKitchenStaff());
				content.revalidate();
			}
		});

		JButton editDrone = new JButton("Remove Drone");
		editDrone.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				content.removeAll();
				content.add(editDrone());
				content.revalidate();
			}
		});

		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				content.removeAll();
				content.add(mainPanel());
				content.revalidate();
			}
		});

		JPanel panelNorth = new JPanel();
		panelNorth.add(addKS);
		panelNorth.add(editKS);
		panelNorth.add(addDrone);
		panelNorth.add(editDrone);

		panel.add(panelNorth, BorderLayout.NORTH);
		panel.add(kitchenPanel, BorderLayout.WEST);
		panel.add(dronePanel, BorderLayout.EAST);
		panel.add(back, BorderLayout.SOUTH);
		return panel;
	}

	// edit kitchen staff frame
	private JPanel editKitchenStaff() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		JPanel subPanel = new JPanel();

		ArrayList<KitchenStaff> arrayKS = cl.getKitchenStaff();
		String[] nameKS = new String[arrayKS.size()];
		for (int i = 0; i < nameKS.length; i++) {
			nameKS[i] = "Kitchen Staff nr. " + arrayKS.get(i).getId();
		}
		JComboBox<String> jKS = new JComboBox<String>(nameKS);

		JButton remove = new JButton("Remove");
		remove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					arrayKS.remove(jKS.getSelectedIndex());
				} catch (Exception e1) {
					System.err.println("No more Kitchen Staff.");
				}
				content.removeAll();
				content.add(editKitchenStaff());
				content.revalidate();
			}
		});

		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				content.removeAll();
				content.add(staff());
				content.revalidate();
			}
		});

		subPanel.add(remove);
		panel.add(subPanel, BorderLayout.CENTER);
		panel.add(jKS, BorderLayout.NORTH);
		panel.add(back, BorderLayout.SOUTH);
		return panel;
	}

	// edit drone frame
	private JPanel editDrone() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		JPanel subPanel = new JPanel();

		ArrayList<Drone> arrayDrone = cl.getDrones();
		String[] nameDrone = new String[arrayDrone.size()];
		for (int i = 0; i < nameDrone.length; i++) {
			nameDrone[i] = "Kitchen Staff nr. " + arrayDrone.get(i).getId();
		}
		JComboBox<String> jDrone = new JComboBox<String>(nameDrone);

		JButton remove = new JButton("Remove");
		remove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					arrayDrone.remove(jDrone.getSelectedIndex());
				} catch (Exception e1) {
					System.err.println("No more Drones.");
				}
				content.removeAll();
				content.add(editDrone());
				content.revalidate();
			}
		});

		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				content.removeAll();
				content.add(staff());
				content.revalidate();
			}
		});

		subPanel.add(remove);
		panel.add(subPanel, BorderLayout.CENTER);
		panel.add(jDrone, BorderLayout.NORTH);
		panel.add(back, BorderLayout.SOUTH);
		return panel;
	}
}