/****************************************
 * Adam Tracy                           *
 * Crowd Organizer App                  *
 * CrowdOrganizerApp                    *
 * Handles the front end of reading the *
 * events log and sending calls to the  *
 * customer class based on what the     *
 * event is.                            *
 ***************************************/
package crowd;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class CrowdOrganizerApp {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		// declare an option, open the events file, and create a log file
		CustomerPrQ prQ = new CustomerPrQ();
		File file = new File("Events.txt");
		Scanner event = new Scanner(file);
		PrintWriter log = new PrintWriter(new BufferedWriter(new FileWriter(
				"log.txt", true)));

		log.printf(">>> program starting%n");

		// while the event file has a new line, switch on first character and do
		// something
		while (event.hasNext()) {
			String line = event.nextLine();

			switch (line.substring(0, 1)) {
			// open the store up
			case "O":
				log.printf("STORE IS OPENING%n");
				prQ.arrangeCustomerQ();
				log.printf(">>> initial heap built containing " + prQ.getSub()
						+ " nodes.%n");
				break;
			// close the store
			case "C":
				log.printf("STORE IS CLOSING%n");
				log.printf(">>> heap currently has " + prQ.getSub()
						+ " nodes remaining.%n");
				while (prQ.getSub() != 0) {
					prQ.serveRemainingCustomers();
					log.printf("SERVING: " + prQ.getName() + " ("
							+ prQ.getPri() + ")%n");
				}
				log.printf(">>> heap is now empty.%n");
				break;
			// add a new customer after 6 am
			case "N":
				prQ.addCustomertoQ(line);
				log.printf("ADDING: " + prQ.getName() + " (" + prQ.getPri()
						+ ")%n");
				break;
			// serve a customer
			case "S":
				prQ.serveACustomer();
				log.printf("SERVING: " + prQ.getName() + " (" + prQ.getPri()
						+ ")%n");
				break;
			// do nothing if garbage data
			default:
				break;
			}
		}

		// finish up and close everything.
		log.printf(">>> program terminating%n");
		event.close();
		log.close();
	}

}
