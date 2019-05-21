import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Dish implements Serializable {

	private String name, description;
	private int price;
	// all the ingredients required to make the dish
	private ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();

	public Dish(String name, String description, int price, ArrayList<Ingredient> ingredients) {
		this.setName(name);
		this.setDescription(description);
		this.setPrice(price);
		this.setIngredients(ingredients);
	}

	//getters and setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public ArrayList<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(ArrayList<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	// use this to convert the string to ingredients
	public ArrayList<String> toStringIngredients() {
		ArrayList<String> names = new ArrayList<String>();
		for (Ingredient i : ingredients) {
			names.add(i.getName());
		}
		return names;
	}
}
