import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Supplier implements Serializable {

	// the name of the supplier
	private String name;
	// the distance to the supplier
	private int dist;
	// the ingredients it can provide
	private ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();

	public Supplier(String name, int dist, ArrayList<Ingredient> ingredients) {
		this.setName(name);
		this.setDist(dist);
		this.setIngredients(ingredients);
	}

	public Supplier(String name, int dist) {
		this.setName(name);
		this.setDist(dist);
	}

	// getters and setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDist() {
		return dist;
	}

	public void setDist(int dist) {
		this.dist = dist;
	}

	public ArrayList<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(ArrayList<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

}
