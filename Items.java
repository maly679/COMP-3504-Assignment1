package Prog4;

//Items class/object definition

public class Items {

	int id;
	String name;
	int quantity;
	double price;
	int supplierID;

	// Constructor
	public Items(int id,
			String name, int quantity, double price, int supplierID) {

		this.id = id;
		this.name = name;
		this.quantity = quantity;
		this.price = price;
		this.supplierID = supplierID;
	}

	// Beginning of getters
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getQuantity() {
		return quantity;
	}

	public double getPrice() {
		return price;
	}

	public int getSupplierID() {
		return supplierID;
	}

	// Beginning of setters
	public void setId() {
		this.id = id;
	}

	public void setName() {
		this.name = name;
	}

	public void setQuantity() {
		this.quantity = quantity;
	}

	public void setPrice() {
		this.price = price;
	}

	public void setSupplierID() {
		this.supplierID = supplierID;
	}

	// String builder
	public String toString() {
		return "";
	}
}
