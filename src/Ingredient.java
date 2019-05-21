import java.io.Serializable;

@SuppressWarnings("serial")
public class Ingredient implements Serializable {

	// unit is saved as a string and ussually is something like kg or littre
	private String name, unit;
	private Supplier supplier;
	private int quantity;

	// contrutors
	public Ingredient(String name, String unit, Supplier supplier, int quantity) {
		this.setName(name);
		this.setUnit(unit);
		this.setSupplier(supplier);
		this.setQuantity(quantity);
	}

	public Ingredient(String name, String unit, Supplier supplier) {
		this.setName(name);
		this.setUnit(unit);
		this.setSupplier(supplier);
		this.setQuantity(0);
	}

	// getters and setters
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void addQuantity(int quantity) {
		this.quantity += quantity;
	}

	public void removeQuantity(int quantity) {
		this.quantity -= quantity;
	}
}
