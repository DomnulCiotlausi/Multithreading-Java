import java.io.Serializable;

@SuppressWarnings("serial")
public class Inte implements Serializable {
	// this class is used to keep track of the id's of the orders
	// we use this class because we want to pass the id by reference
	
	private int id;

	public Inte(int id) {
		this.setId(id);
	}

	// getters and setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}