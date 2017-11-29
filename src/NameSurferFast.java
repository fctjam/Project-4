import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/*
 
 * The main method should read all the data from the files and store it in two ArrayLists – one for male names and 
 * one for female names – of NameRecord objects. It should then offer the following menu to the user:
1 – Find best year for a female name
2 – Find best rank for a female name
3 – Find best year for a male name
4 – Find best rank for a male name
5 – Quit
Enter your selection.
If 1, 2, 3, or 4 is entered, the program should prompt the user for a name, search for that name in the ArrayList 
(search should ignore case), print the desired information, and display the menu again. If the name is not found in 
the ArrayList print an error message and display the menu again.
 */

public class NameSurferFast {

	// Constants for standard file prefix and ending
	static final String FILE_PREIX = "names\\yob";
	static final String FILE_ENDING = ".txt";
	
	// Setup two ArrayLists – one for male names and one for female names – of NameRecord objects
	static ArrayList<NameRecord> femaleRecords = new ArrayList<NameRecord>();
	static ArrayList<NameRecord> maleRecords  = new ArrayList<NameRecord>();
	
	static HashMap<String,Integer> femaleIndexMap = new HashMap<String,Integer>();  
	static HashMap<String,Integer> maleIndexMap = new HashMap<String,Integer>();  
	
	// Update the name redord in the appropriate name list, depending on whether it's male or female
	public static void updateNameRecord(String name, String gender, int year, int rank) {
		NameRecord nameRec = null;
		int yearIndex = year - NameRecord.START;
		
		
		// if male, operate on male list
		if (gender.equals("M")) {
			// Get index of the name in the maleRecords or femaleRecords list (or null if there isn't one)
			Integer nameIndex = maleIndexMap.get(name);
			// if a name was not found, create a new name record
			if (nameIndex == null) {
				// create a new name record
				nameRec = new NameRecord(name);
				nameRec.setRank(yearIndex, rank);
			}

			// if a name was not found ...
			if (nameIndex == null) {
				// add nameRec created earlier
				maleRecords.add(nameRec);
				// set index for this name on the map
				maleIndexMap.put(name, new Integer(maleRecords.size() - 1));
			}
			// name was found, so retrieve record using the saved index on the map
			else {
				nameRec = maleRecords.get(nameIndex);
				nameRec.setRank(yearIndex, rank);
				maleRecords.set(nameIndex, nameRec);
			}
		}
		// else, read from female list
		else {
			// Get index of the name in the maleRecords or femaleRecords list (or null if there isn't one)
			Integer nameIndex = femaleIndexMap.get(name);
			// if a name was not found, create a new name record
			if (nameIndex == null) {
				// create a new name record
				nameRec = new NameRecord(name);
				nameRec.setRank(yearIndex, rank);
			}

			// if a name was not found ...
			if (nameIndex == null) {
				// add nameRec created earlier
				femaleRecords.add(nameRec);
				// set index for this name on the map
				femaleIndexMap.put(name, new Integer(femaleRecords.size() - 1));
			}
			// name was found, so retrieve record using the saved index on the map
			else {
				nameRec = femaleRecords.get(nameIndex);
				nameRec.setRank(yearIndex, rank);
				femaleRecords.set(nameIndex, nameRec);
			}
		}
	}
	
	// Read the file for one year, and create/update the records in the femaleRecords and maleRecords arrays
	public static void readFileYear(int year) {
		// Compose the file name using the year passed
		String fileName = FILE_PREIX + year + FILE_ENDING;
		

		try {
			// Open file using the correct file name
		    Scanner sc = new Scanner(new File(fileName));
			
			// iterate reading each line
		    while (sc.hasNextLine()) {
				// read name, gender, and rank together from one line
		    	String line = sc.nextLine();
		    	
				// get individual name, gender, rank values from the comma separated value	
		    	String[] strArr = line.split(",");

		    	String name = strArr[0];
		    	String gender = strArr[1];
		    	int rank = Integer.parseInt(strArr[2]);
		    	
				// update name record in the appropriate list
				updateNameRecord(name, gender, year, rank);

		    }
		}
		catch (Exception e) {
			System.out.println("ERROR> Error reading file: " + e);
			System.exit(0);
		}
		
		System.out.println("QQ> Finished readng file: " + fileName);
		System.out.println("QQ> Male names: " + maleRecords.size());
		System.out.println("QQ> Female names: " + femaleRecords.size());

	}

	// Return the best year for the name, and if none is found, return -1
	public static int getBestYear(String name, ArrayList<NameRecord> records) {
		NameRecord nameRec;

		// iterate through list and find record matching the name
		for (int i =0 ; i < records.size(); i++) {
			// if there is an existing record update it, and reset it on the list, and return
			nameRec = records.get(i);
			if (nameRec.getName().equalsIgnoreCase(name)) {
				return nameRec.bestYear();
			}
		}
		
		// if I made it through the loop without finding, return -1 to indicate none was found
		return -1;
	}
	

	// Return the best rank for the name, and if none is found, return -1
	public static int getBestRank(String name, ArrayList<NameRecord> records) {
		NameRecord nameRec;

		// iterate through list and find record matching the name
		for (int i =0 ; i < records.size(); i++) {
			// if there is an existing record update it, and reset it on the list, and return
			nameRec = records.get(i);
			if (nameRec.getName().equalsIgnoreCase(name)) {
				int bestYearIndex = nameRec.bestYear() - NameRecord.START;
				return nameRec.getRank(bestYearIndex);
			}
		}
		
		// if I made it through the loop without finding, return -1 to indicate none was found
		return -1;
	}
	
	
	
	public static void main(String[] args) {
		
		Scanner scnr = new Scanner(System.in);

		// Iterate through valid years, and read names from each year file
		for (int y = NameRecord.START; y <= NameRecord.END; y++) {
			// call method to read the name data for one year
			readFileYear(y);
		}

		// Present options and loop until user wannts to quit
		int selection = 0;
		int bestYear = 0;
		int bestRank = 0;
		String name = ""; 

		do {		
			System.out.println("1 - Find best year for a female name");
			System.out.println("2 - Find best rank for a female name");
			System.out.println("3 - Find best year for a male name");
			System.out.println("4 - Find best rank for a male name");
			System.out.println("5 - Quit");
			System.out.println("Enter your selection.");
			
			// read selection
			selection = Integer.parseInt(scnr.nextLine());

			// if selection will require a name (1-4), prompt for name (do it here, so you don't have to do it in each option 1 through 4)
			if (selection > 0 && selection < 5) {
				System.out.print("\nEnter a name to search: ");
				name = scnr.nextLine();
			}
			
			// switch statement for each option
			switch (selection) {
			case 1:
				bestYear = getBestYear(name, femaleRecords);
				if (bestYear >= 0) {
					System.out.println("The best year for " + name + " was " + bestYear);
				}
				else {
					System.out.println("Sorry, we have no record of the name " + name);
				}
				break;
				
			case 2:
				bestRank = getBestRank(name, femaleRecords);
				if (bestRank >= 0) {
					System.out.println("The best rank for " + name + " was " + bestRank);
				}
				else {
					System.out.println("Sorry, we have no record of the name " + name);
				}
				break;
				
			case 3:
				bestYear = getBestYear(name, maleRecords);
				if (bestYear >= 0) {
					System.out.println("The best year for " + name + " was " + bestYear);
				}
				else {
					System.out.println("Sorry, we have no record of the name " + name);
				}
				break;
				
			case 4:
				bestRank = getBestRank(name, maleRecords);
				if (bestRank >= 0) {
					System.out.println("The best rank for " + name + " was " + bestRank);
				}
				else {
					System.out.println("Sorry, we have no record of the name " + name);
				}
				break;
			}
		}
		while (selection != 5);

	}

}
