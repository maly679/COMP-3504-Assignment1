package Prog4;

//Items class/object definition

public class Items {

	int id;
	String name;
	int quantity;
	double price;
	int supplierID;

	public Items(int id,
			String name, int quantity, double price, int supplierID) {

		this.id = id;
		this.name = name;
		this.quantity = quantity;
		this.price = price;
		this.supplierID = supplierID;
	}

}
