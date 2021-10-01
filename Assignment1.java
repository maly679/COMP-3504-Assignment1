package Prog4;
import java.io.*;
import java.util.*;

public class Assignment1 {

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
		System.out.println("Please select an option:" + "\r\n0: Exit" + "\r\n1: Add a new Tool" + "\r\n2: Check stock quantity" + "\r\n3: Search a Tool" + "\r\n4: Delete a Tool");
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
	 * Checks quantity of stocks currently in inventory
	 * @param 
	 */
	public void stockCheck() {

		// Prints all items currently in inventory
		for (Items i : itemsList) {
			System.out.println(i.getId() + ";" + i.getName() + ";" + i.getQuantity() + ";" + i.getPrice() + ";" + i.getSupplierID());	// message to be changed

			// Prints only items with stock below threshold
			if (i.getQuantity() < 40) {		// Creates order line for item if stock < 40
				System.out.println("Order line created: " + i.getId() + ";" + i.getQuantity() + ";" + i.getSupplierID());	// message to be changed

				// Calls order line for the item
				// generateOrderline(i.getId(), i.getQuantity(), i.getSupplierID());	// method to be created
			} else {	// No stock problem
				// System.out.println("Item " + i.getId() + "-" + i.getName() + " is above threshold.\n");	// message to be changed
			}
		}

		System.out.println();

		// Return to main menu
		displayOptions();
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
				for (Items i : itemsList) {
					if (toolSearch.equals(Integer.toString(i.getId())) || toolSearch.equals(i.getName())) {
						System.out.println("Selected Tool: \r\n\r\n" + i.getAllInfo() + "\r\n");
						toolToDelete = i.getAllInfo();
						//removes from arrayList
						itemsList.remove(i);
						System.out.println("Are you sure you want to delete this tool? (yes/no):");
						deleteToolPrompt = reader.readLine();
						found = true;
						break;						
					} 
				}
				if (!found) {
					System.out.println("Tool not found! Try again.");
					deleteTool();
				}
				if (deleteToolPrompt.equals("yes")) {
					//Removes from text file
					removeTool(toolToDelete);
				} else {
					if (!deleteToolPrompt.equals("yes")) {
						System.out.println("Tool not removed! \r\n");
						displayOptions();
					}
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
