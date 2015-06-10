/****************************************
 * Adam Tracy                           *
 * Crowd Organizer App                  *
 * CustomerPrQ                          *
 * Takes care of all the backend stuff  *
 * when the main program calls for what *
 * method to use based on the events    *
 ***************************************/
package crowd;

import java.io.*;
import java.util.Scanner;

public class CustomerPrQ {

	// variable declarations
	private String[] nameArr;
	private int[] priArr;
	private int priority;
	private int n = 101;
	private int sub = 0;
	private int temp;
	private String tempS;
	private String name;

	// ***********Constructor*************************
	/**
	 * constructor to build the empty arrays
	 * 
	 * @throws IOException
	 */
	public CustomerPrQ() throws IOException {
		nameArr = new String[30];
		priArr = new int[30];
	}

	// *********Public Methods***********************
	/**
	 * reads the line at 6 am. cleans it up and inserts into heap
	 * 
	 * @throws FileNotFoundException
	 */
	public void arrangeCustomerQ() throws FileNotFoundException {
		File file = new File("LineAt6am.csv");
		Scanner start = new Scanner(file);

		while (start.hasNext()) {
			String line = start.nextLine();
			if (!line.substring(0, 1).equalsIgnoreCase("/")) {
				String[] temp = line.split(",");
				priority = n;
				heapInsert(temp[0], determinePriorityValue(temp, priority));
				n++;
			}
		}
		start.close();
	}

	/**
	 * serves the remaining customers after the store closes
	 */
	public void serveRemainingCustomers() {
		heapDelete();
	}

	/**
	 * adds a new customer to the heap if coming after 6 am
	 * 
	 * @param line
	 */
	public void addCustomertoQ(String line) {
		line = line.substring(12);
		String[] temp = line.split(",");
		priority = n;
		heapInsert(temp[0], determinePriorityValue(temp, priority));
		n++;
	}

	/**
	 * customer getting served - calls delete
	 */
	public void serveACustomer() {
		heapDelete();
	}

	// ***********Getter Methods*******************
	/**
	 * getter for the subscript
	 * 
	 * @return
	 */
	public int getSub() {
		return sub;
	}

	/**
	 * getter for name of person
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * getter for priority in heap
	 * 
	 * @return
	 */
	public int getPri() {
		return priority;
	}

	// **************Private Methods***********************
	/**
	 * the actual method to insert into the heap. inserts at bottom and walks up
	 * 
	 * @param name2
	 * @param priority2
	 */
	private void heapInsert(String name2, int priority2) {
		name = name2;
		priority = priority2;
		nameArr[sub] = name2;
		priArr[sub] = priority2;
		sub++;
		walkUp(sub - 1);
	}

	/**
	 * the delete method. gets rid of top of heap. and uses walkdown
	 */
	private void heapDelete() {
		name = nameArr[0];
		priority = priArr[0];
		priArr[0] = priArr[sub - 1];
		nameArr[0] = nameArr[sub - 1];
		priArr[sub - 1] = 0;
		nameArr[sub - 1] = null;
		sub--;
		walkDown(0);
	}

	/**
	 * moving a "node" from bottom of heap to top after insert based on priority
	 * 
	 * @param sub
	 */
	private void walkUp(int sub) {
		int i = sub;
		while (i > 0 && priArr[i] < priArr[(i - 1) / 2]) {
			temp = priArr[i];
			priArr[i] = priArr[(i - 1) / 2];
			priArr[(i - 1) / 2] = temp;
			tempS = nameArr[i];
			nameArr[i] = nameArr[(i - 1) / 2];
			nameArr[(i - 1) / 2] = tempS;
			i = (i - 1) / 2;
		}
	}

	/**
	 * moving a "node" from top to bottom after delete based on priority
	 * 
	 * @param start
	 */
	private void walkDown(int start) {
		int i = start;
		int smCh = subOfSmCh(i);
		while ((2 * i + 1) <= (sub - 1) && (priArr[i] >= priArr[smCh])) {
			temp = priArr[i];
			priArr[i] = priArr[smCh];
			priArr[smCh] = temp;
			tempS = nameArr[i];
			nameArr[i] = nameArr[smCh];
			nameArr[smCh] = tempS;
			i = smCh;
			smCh = subOfSmCh(i);
		}
	}

	/**
	 * decides which "node" is smaller when comparing children in a walkdown
	 * 
	 * @param i
	 * @return
	 */
	private int subOfSmCh(int i) {
		if (2 * i + 2 > (sub - 1) || priArr[2 * i + 1] <= priArr[2 * i + 2]) {
			return 2 * i + 1;
		} else {
			return 2 * i + 2;
		}

	}

	/**
	 * calculates the priority value of a "node" based on their eligibility
	 * 
	 * @param temp
	 * @param priority
	 * @return
	 */
	private int determinePriorityValue(String[] temp, int priority) {
		if (temp[1].equalsIgnoreCase("employee")) {
			priority -= 25;
		} else if (temp[1].equalsIgnoreCase("owner")) {
			priority -= 80;
		}

		if (temp[2].equalsIgnoreCase("vip")) {
			priority -= 5;
		} else if (temp[2].equalsIgnoreCase("superVIP")) {
			priority -= 10;
		}

		if (Integer.parseInt(temp[3]) >= 65) {
			priority -= 15;
			if (Integer.parseInt(temp[3]) >= 80) {
				priority -= 15;
			}
		}
		return priority;
	}
}
