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
	
	public String getAllInfo() {
		return (this.id + ";" + this.name + ";" + this.quantity + ";" + this.price + ";" + this.supplierID);
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
	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setSupplierID(int supplierID) {
		this.supplierID = supplierID;
	}

	// String builder
	public String toString() {
		return "";
	}
}
