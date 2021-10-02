package Prog4;

public class Orders {

	int itemId;
	String itemName;
	int itemAmount;
	String supplierName;
	
	// Constructor
	public Orders(int itemId, String itemName, int itemAmount, String supplierName) {
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemAmount = itemAmount;
		this.supplierName = supplierName;
	}
	
	// Beginning of getters
	public int getItemId() {
		return itemId;
	}
	
	public String getItemName() {
		return itemName;
	}
	
	public int getItemAmount() {
		return itemAmount;
	}
	
	public String getSupplierName() {
		return supplierName;
	}
	
	// Beginning of setters
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	public void setItemAmount(int itemAmount) {
		this.itemAmount = itemAmount;
	}
	
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	
	public String stringBuilder() {
		return "Item Id: " + itemId + "\nItem Description: " + itemName + "\nAmount Ordered: " + itemAmount + "\nSupplier: " + supplierName + "\n\n";
	}
}
