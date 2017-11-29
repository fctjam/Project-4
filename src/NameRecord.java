/** Alexa, Ashley, Leanna 
    CSC 220-05
    Project 4 
    December 8, 2017 
    
    names”. */

/*
 * NameRecord – encapsulates the data for one name: the String name and its rank over the years. .
 */
  public class NameRecord {
	  
	  // Create constants START=1880 and END=2016 define the start and end years in the data.
	  public static final int START=1880;
	  public static final int END=2016;
	  
	  // Use an int array to store the int rank  numbers
	  int [] rankNumbers = new int[END - START + 1];
	  
	  // name
	  String name;


	  // Constructor – takes a String name and sets up the NameRecord object.
	  public NameRecord(String name) {
		  this.name = name;
	  }

	  // returns the name
	  public String getName() {
		  return name;
	  }

	  // sets the rank of the name in the given year. Use the convention that year=0 is 1880, year=1 is 1881, and so on.
	  public void setRank (int year, int rank) {
		  rankNumbers[year] = rank;
	  }
	  
	  // returns the rank of the name in the given year. Use the convention that year=0 is 1880, year=1 is 1881, and so on.
	  public int getRank(int year) {
		  return rankNumbers[year];
	  }
	  
	  // returns the year where the name was most popular, using the earliest year in the event of a tie. Returns the actual year,
	  // for example 1920, so the caller does not need to adjust for START. It is safe to assume that every name has at least one 
	  // year with a non-zero rank.  
	  public int bestYear() {
		  // iterate through the whole array of years
		  int highestYear = 0;
		  int highestRank = 0;
		  for (int i = 0; i < rankNumbers.length; i++) {
			  if (rankNumbers[i] > highestRank) {
				  highestRank = rankNumbers[i];
				  highestYear = i;
			  }
		  }
		  
		  // add the start 
		  return START + highestYear;
		  
	  }
  }

