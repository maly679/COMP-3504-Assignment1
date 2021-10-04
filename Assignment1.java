package Prog4;
import java.io.*;
import java.util.*;

public class Assignment1 {

	int orderID;
	Date date;
	
	//Array list of items and suppliers to be populated.
	ArrayList<Items> itemsList = new ArrayList<Items>();
	ArrayList<Suppliers> supplierList = new ArrayList<Suppliers>();

	//Files, present in respective Java Project Folder (run System.getProperty("user.dir") to get this location that is expected for text files.
	File itemsFile = new File("items.txt");
	File suppliersFile = new File("suppliers.txt");

	public static void main (String[] args)  {	
		//Initiate process.
		Assignment1 asg1 = new Assignment1();
		asg1.populateItems();
		asg1.populateSuppliers();
		asg1.displayOptions();
	}

	//Populate Items text file into arrayList.
	public void populateItems() {

		try (BufferedReader br = new BufferedReader(new FileReader(itemsFile))) {
			String st;
			while ((st = br.readLine()) != null) {

				//added so that blank lines can be skipped.
				if(st.length() > 0) {
					String [] items = st.split(";");
					itemsList.add(new Items(Integer.parseInt(items[0]), items[1], Integer.parseInt(items[2]), Double.parseDouble(items[3]), Integer.parseInt(items[4])));
				}
			}

			br.close();
		}catch (FileNotFoundException ex){
			System.out.println(ex);
			System.exit(0);

		}
		catch (IOException e){
			System.out.println(e);
			System.exit(0);
		}
		catch (NumberFormatException nfe) {
			System.out.println("Error in reading data from items.txt file. Please ensure that it contains the correct data types! Bye.");
			System.exit(0);
		}


	}
	//Populate Suppliers text file into arrayList.
	public void populateSuppliers() {

		try (BufferedReader br = new BufferedReader(new FileReader(suppliersFile))) {
			String st;
			while ((st = br.readLine()) != null) {
				if(st.length() > 0) {
					String [] suppliers = st.split(";");
					supplierList.add(new Suppliers(Integer.parseInt(suppliers[0]), suppliers[1], suppliers[2],suppliers[3]));
				}
			}
			br.close();
		}catch (FileNotFoundException ex){
			System.out.println(ex);
			System.exit(0);
		}
		catch (IOException ex){
			System.out.println(ex);
			System.exit(0);
		}
		catch (NumberFormatException nfe) {
			System.out.println("Error in reading data from suppliers.txt file. Please ensure that it contains the correct data types! Bye.");
			System.exit(0);	
		}

	}

	//Display options for user processing, on front end.
	public void displayOptions()  {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		//Selection Switch Case. Can be modified for new features/additions.
		System.out.println("Please select an option:" + "\r\n0: Exit" + "\r\n1: Add a new Tool" + "\r\n2: Check stock quantity" + "\r\n3: Search a Tool" + "\r\n4: Delete a Tool" + "\r\n5: Generate order line");
		System.out.print("\nUser Selection: ");

		try {

			int selection = Integer.parseInt(reader.readLine());
			switch(selection) {
			//Exit System
			case 0:
				System.out.println("Thanks for using the system! Good bye.");
				System.exit(0);
				//Add new Item
			case 1:
				addItem();
				break;
			case 2:	// Error checking; can be changed for later
				stockCheck();
				break;
			case 3: 
				searchTool();
				break;
			case 4:
				deleteTool();
				break;
			case 5:
				generateOrderline();
				break;
			default:
				//Invalid Selection
				System.out.println("Invalid selection. Please make sure you are selecting an available option");
				displayOptions();
			}

		} catch (Exception IOE) {
			System.out.println("Make sure you are entering a valid selection number!");
			displayOptions();
		}


	}

	public void addItem() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("\r\n" +  "Please enter new tool information in the following format:" + "\r\n\n" + "id;description or name of tool;quantity in stock;price;supplier id"
				+ "\r\n\n" + "Ensure that id, supplier id and quantity of stock are integers and that price is a number!"
				+ "\r\n" + "If you want to cancel this operation instead, enter exit." );
		System.out.print("\nUser Entry: ");

		String newItem = "";
		try {
			newItem = reader.readLine();
			if (newItem.equals("exit")) {
				displayOptions();
			}

			//Processing user input of new item. Splitting by the ';' for processing purposes.
			String [] newLineCheck = newItem.split(";");

			//Ensure it meets the length required.
			if (newLineCheck.length != 5) {
				System.out.println("Error! The length of the tool information should total 5, and be separated by semi-colons!");
				addItem();
			}

			try {
				//Assigning new object and value to global items array list.
				assignNewItemsObject(newLineCheck);
			} catch (NumberFormatException e) {
				System.out.println("Incorrect input types! ID, Supplier ID and quantity in stock must all be integers, price a number, and the name of tool a string!");
				addItem();
			}

			//Re -joining as string, in order to add back into items text file, as required.
			newItem = String.join(";", newLineCheck);

		} catch (Exception e) {

			System.out.println("Invalid input. Please make sure that it fits the threshhold required!");
			addItem();
		}


		outputToItemsFile(newItem);

	}

	//Adding new Items object to ArrayList, upon addItem() invokation/user entry.
	public void assignNewItemsObject(String [] newLineCheck) {

		int id = Integer.parseInt(newLineCheck[0]);
		String name = newLineCheck[1];
		int quantity = Integer.parseInt(newLineCheck[2]);
		double price = Double.parseDouble(newLineCheck[3]);
		int supplierId = Integer.parseInt(newLineCheck[4]);
		itemsList.add(new Items(id, name, quantity, price, supplierId));

	}

	/**
	 * Displays quantity of all stocks currently in inventory
	 */
	public void stockCheck() {

		// Prints all items currently in inventory
		for (Items i : itemsList) {
			System.out.println(i.getId() + ";" + i.getName() + ";" + i.getQuantity() + ";" + i.getPrice() + ";" + i.getSupplierID());	// message to be changed

			if (i.getQuantity() < 40) {		// Displays stock with quantity below 40
				 System.out.println("Item Below Threshold: " + i.getId() + ";" + i.getQuantity() + ";" + i.getSupplierID());	// message to be changed
			} else {	// No stock problem
			}
		}

		// Return to main menu
		displayOptions();
	}

	/**
	 * Generates an order line for stock with quantity below 40
	 * Places stock with quantity below 40 into an Orders arrayList
	 */
	public void generateOrderline() {
		int orderAmount = 0;
		final int DEFAULTQUANTITY = 50;
		
		ArrayList<Orders> orderLine = new ArrayList<Orders>();
		
		for (Items i : itemsList) {
			if (i.getQuantity() < 40) {		// Finds order line item with stock below 40
				// Assigns order amount to default values
				orderAmount = DEFAULTQUANTITY - i.getQuantity();
				
				for (Suppliers s : supplierList) {
					if (i.getSupplierID() == s.getId()) {	// Finds corresponding Supplier information for item
						orderLine.add(new Orders(i.getId(), i.getName(), orderAmount, s.getCompanyName()));
					} else {
					}
				}
			} else {	// No stock problem
			}
		}
		
		// Prints a preview of the order line
		previewOrderLine(orderLine);
		// Returns to order line sub-menu
		optionsOrderLine(orderLine);
		
	}
	
	/**
	 * Menu options for the order line sub-menu
	 * @param orderLine - Orders arrayList
	 */
	public void optionsOrderLine(ArrayList<Orders> orderLine) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int selection;
		
		System.out.println("Would you like to modify an item's order amount?\n1. YES, Modify Orders\n2. YES, Add To Order\n3. YES, Remove An Order\n4. NO, Export Order Line to Output File\n5. NO, Return to main menu");
		System.out.print("\nUser Selection: ");
		
		try {
			selection = Integer.parseInt(reader.readLine());
			switch(selection) {
				case 1:	// Modifies item quantity in order line
					modifyOrderLine(orderLine);
					break;
				case 2: // Add item to order line not presently there
					addToOrderLine(orderLine);
					break;
				case 3: // Remove item from order line that already exists
					removeFromOrderLine(orderLine);
					break;
				case 4:	// Prints order line to orders.txt
					 outputToOrdersFile(orderLine);
					break;
				case 5: // Returns to main menu
					displayOptions();
					break;
				default:	//Invalid Selection
					System.out.println("Invalid selection. Please make sure you are selecting an available option");
					optionsOrderLine(orderLine);
			}

		} catch (Exception IOE) {
			System.out.println("Make sure you are entering a valid selection number!");
			displayOptions();
		}
	}
	
	/**
	 * Alters order amount for specified item, matched with item's id
	 * @param orderLine - Orders arrayList
	 */
	public void modifyOrderLine(ArrayList<Orders> orderLine) {
		BufferedReader reader1 = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader reader2 = new BufferedReader(new InputStreamReader(System.in));
		int itemID;
		int itemAmount;
		int index;
		
		System.out.print("Enter Item ID to change amount: ");
		
		try {
			itemID = Integer.parseInt(reader1.readLine());
			for (int i = 0; i < orderLine.size(); i++) {
				if (orderLine.get(i).getItemId() == itemID) {
					System.out.print("Enter change quantity amount: ");
					itemAmount = Integer.parseInt(reader2.readLine());
					orderLine.get(i).setItemAmount(itemAmount);
				} else {}
			}
			
			// Prints a preview of the order line
			previewOrderLine(orderLine);
			// Returns to order line sub-menu
			optionsOrderLine(orderLine);
		} catch (Exception IOE) {
			System.out.println("Input not recognized. Please try again.");
			modifyOrderLine(orderLine);
		}
	}
	
	/**
	 * Adds an item line to orderLine by obtaining item ID and amount to add
	 * @param orderLine - Adds to orderline
	 */
	public void addToOrderLine(ArrayList<Orders> orderLine) {
		BufferedReader reader1 = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader reader2 = new BufferedReader(new InputStreamReader(System.in));
		int itemID;
		int itemAmount;
		int index;
		
		System.out.print("Which item do you want to order (Please enter Item ID): ");
		
		try {
			itemID = Integer.parseInt(reader1.readLine());
			for (Items i : itemsList) {
				if (i.getId() == itemID) {
					System.out.print("Enter amount to order: ");
					itemAmount = Integer.parseInt(reader2.readLine());
					for (Suppliers s : supplierList) {
						if (i.getSupplierID() == s.getId()) {
							orderLine.add(new Orders(i.getId(), i.getName(), itemAmount, s.getCompanyName()));
						} else {}
					}
				} else {}
			}
		} catch (Exception IOE) {
			System.out.println("Input not recognized. Please try again.");
			addToOrderLine(orderLine);
		}
		
		// 
		previewOrderLine(orderLine);
		// 
		optionsOrderLine(orderLine);
	}
	
	
	/**
	 * Locates and removes item from order line matched with items ID
	 * @param orderLine - Removes from orderline
	 */
	public void removeFromOrderLine(ArrayList<Orders> orderLine) {
		BufferedReader reader1 = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader reader2 = new BufferedReader(new InputStreamReader(System.in));
		int itemID;
		int index;
		
		System.out.println("Which item do you want to delete (Please eneter item ID): ");
		
		try {
			itemID = Integer.parseInt(reader1.readLine());
			for (int i = 0; i < orderLine.size(); i++) {
				if (orderLine.get(i).getItemId() == itemID) {
					orderLine.remove(i);
				} else {}
			}
			
			// Prints a preview of the order line
			previewOrderLine(orderLine);
			// Returns to order line sub-menu
			optionsOrderLine(orderLine);
			
		} catch (Exception IOE) {
			System.out.println("Item not found. Please try again.");
			removeFromOrderLine(orderLine);
		}
	}
	
	/**
	 * Prints a preview of the order line
	 * @param orderLine - Orders arrayList
	 */
	private void previewOrderLine(ArrayList<Orders> orderLine) {
		// Calls for order ID to be generated
		generateOrderId();

		// Calls for date to be generated
		generateOrderDate();
		
		System.out.println("PREVIEW OF ORDER LINE");
		
		System.out.println("**********************************************************************" 
				+ "\nORDER ID.: " + orderID + "\nDate Ordered: " + date);
		
		for (Orders o : orderLine) {
			System.out.println(o.stringBuilder());
		}
		
		System.out.println("**********************************************************************");
	}
	
	/**
	 * Prints order line to orders.txt in file explorer
	 * @param orderLine - Orders arrayList
	 * @throws IOException
	 */
	private void outputToOrdersFile(ArrayList<Orders> orderLine) throws IOException {
		
		String content1 = "**********************************************************************"
				+ "\nORDER ID.: " + orderID + "\nDate Ordered: " + date + "\n\n";
		
		String content3 = "**********************************************************************";
		
		// append mode
		// if file not exists, create and write
		// if file exists, append to the end of the file
		try (FileWriter fw = new FileWriter("orders.txt", true);
			BufferedWriter bw = new BufferedWriter(fw)) {

			bw.write(content1);
			for (Orders o : orderLine) {
				bw.write(o.stringBuilder());
			}
			bw.write(content3);
			bw.newLine();   // add new line, System.lineSeparator()

		}
		
		System.out.println("Order line has been written to file explorer\n");
		
		// Return to main menu
		displayOptions();
	}
	
	/**
	 * Generates a date for order line
	 */
	private int generateOrderId() {
		Random rand = new Random();
		int minRange = 10000, maxRange = 99999;
		
		orderID = rand.nextInt(maxRange - minRange) + minRange;
		
		return orderID;
	}
	
	/**
	 * Generates an order ID for order line
	 */
	private Date generateOrderDate() {
		date = new Date();
		
		return date;
	}

	public void searchTool () {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("\r\n" +  "Please enter the tool name or tool id, or enter exit to return to the original menu:" );
			System.out.print("\nUser Entry: ");
			String toolSearch = reader.readLine();
			boolean found = false;
			String searchAgain = "no";
			if (toolSearch.equals("exit")) {
				displayOptions();
			} else {
				for (Items i : itemsList) {
					if (toolSearch.equals(Integer.toString(i.getId())) || toolSearch.equals(i.getName())) {
						System.out.println("Selected Tool: \r\n\r\n" + i.getAllInfo() + "\r\n");
						found = true;
						System.out.println("Would you like to search for another tool? (yes/no): ");
						searchAgain = reader.readLine();

						break;						
					} 
				}
				if (!found) {
					System.out.println("Tool not found! Try again.");
					searchTool();
				}
				if (searchAgain.equals("yes")) {
					searchTool();
				} else if (!searchAgain.equals("no")) {
					System.out.println("Invalid entry! Returning to main menu. \r\n");
				}
			}
		} catch (Exception e) {
			System.out.println(e + " Invalid input! Please try again.");
			searchTool();
		}
		displayOptions();
	}

	public void deleteTool() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("\r\n" +  "Please enter the tool name or tool id to delete, or enter exit to return to the original menu:" );
			System.out.print("\nUser Entry: ");
			String toolSearch = reader.readLine();
			boolean found = false;
			String deleteToolPrompt = "no";
			String toolToDelete = "";
			if (toolSearch.equals("exit")) {
				displayOptions();
			} else {
				for (int i = 0; i < itemsList.size(); i++) {
					if (toolSearch.equals(Integer.toString(itemsList.get(i).getId())) || toolSearch.equals(itemsList.get(i).getName())) {
						System.out.println("Selected Tool: \r\n\r\n" + itemsList.get(i).getAllInfo() + "\r\n");
						//removes from arrayList
						System.out.println("Are you sure you want to delete this tool? (yes/no):");
						deleteToolPrompt = reader.readLine();
						if (deleteToolPrompt.equals("yes")) {
							toolToDelete = itemsList.get(i).getAllInfo();
							//Removes from text file
							itemsList.remove(i);
							removeTool(toolToDelete);
							found = true;
							break;				
						}
					} 
				}
				if (!found) {
					System.out.println("Tool not found! Try again.");
					deleteTool();
				}

					if (!deleteToolPrompt.equals("yes")) {
						System.out.println("Tool not removed! \r\n");
						displayOptions();
					}
			}
		} catch (Exception e) {
			System.out.println(e + " Invalid input! Please try again.");
			deleteTool();
		}
	}

	//Remove tool from items.txt file
	public void removeTool(String toolToDelete)  {

		try (BufferedReader br = new BufferedReader(new FileReader(itemsFile))) {
			File tempFile = new File("myTempFile.txt");
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
			String st;
			while ((st = br.readLine()) != null) {

				String trimmedLine = st.trim();
				if(trimmedLine.equals(toolToDelete.trim()))continue;
				writer.write(st + System.getProperty("line.separator"));
			}
			System.out.println(toolToDelete + " has been successfully removed.");
			br.close();
			writer.close();
			itemsFile.delete();
			boolean successful = tempFile.renameTo(itemsFile);
			displayOptions();
		}catch (FileNotFoundException ex){
			System.out.println(ex);
			System.exit(0);

		}
		catch (IOException e){
			System.out.println(e);
			System.exit(0);
		}
	}

	//Output new item to file, upon addItem() invokation/user entry.
	public void outputToItemsFile(String newItem) {

		try(FileWriter fw = new FileWriter(itemsFile, true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter out = new PrintWriter(bw))
		{
			//adding new Item to text file.
			out.println(System.getProperty("line.separator") + newItem);
			out.close();
			System.out.println(newItem + " added to items inventory successfully.");
			//return to Display Options.
			displayOptions();

		} catch (IOException e) {
			System.out.println(e);
			displayOptions();
		}
	}
}
