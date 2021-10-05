package Prog4;

//Suppliers class/object definition

public class Suppliers {

	int id;
	String companyName;
	String address;
	String salesContact;

	// Constructor 
	public Suppliers(int id,
			String companyName, String address, String salesContact) {

		this.id = id;
		this.companyName = companyName;
		this.address = address;
		this.salesContact = salesContact;
	}
	
	// Beginning of getters
	public int getId() {
		return id;
	}
	
	public String getCompanyName() {
		return companyName;
	}
	
	public String getAddress() {
		return address;
	}
	
	public String getSalesContact() {
		return salesContact;
	}
	
	// Beginning of setters
	public void setId(int id) {
		this.id = id;
	}
	
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public void setSalesContact(String salesContact) {
		this.salesContact = salesContact;
	}
	
	public String stringBuilder() {
		return "";
	}

}
