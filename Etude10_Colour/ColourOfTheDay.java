import java.util.*;

/**
 * ColourOfTheDay
 */
public class ColourOfTheDay {
    static enum Colour { NONE, RED, GREEN, BLUE, GOLD }
    static final long daysInYear = 350L;
    static Map<Long, Colour> colourOfDay = new HashMap<>(); 
    static Set<Long> primes = new HashSet<>();
    static { primes.add(2L); } 

    static public boolean isPrime(long n) {
        if (primes.contains(n)) return true;
        if (n % 2 == 0) return false;

        for (long i = 3; i * i <= n; i += 2) {
            if(n % i == 0) return false;
        }

        primes.add(n);
        return true;
    }

    static Colour colour(long day) {
        Colour c = Colour.NONE;

        if (colourOfDay.containsKey(day)) return colourOfDay.get(day);
        
        if (day == 1) c = Colour.NONE;
        else if (day == 7) c = Colour.GOLD;
        else if (isPrime(day)) {
            long dayOfWeek = day % 7;
            if (dayOfWeek == 1 || dayOfWeek == 4) c = Colour.RED;
            else if (dayOfWeek == 2 || dayOfWeek == 5) c = Colour.GREEN;
            else if (dayOfWeek == 3 || dayOfWeek == 6) c = Colour.BLUE;
            // return Colour.NONE; 
        } else {
            Map<Colour, Integer> counts = getCounts(day);
            int red = counts.get(Colour.RED);
            int green = counts.get(Colour.GREEN);
            int blue = counts.get(Colour.BLUE);
    
            // If all the same
            if (red == green && green == blue) {
                c = Colour.GOLD;
            }   
            // If two the same
            else if (blue == green && blue != red) c = Colour.RED;
            else if (red == green && red != blue) c = Colour.BLUE;
            else if (red == blue && red != green) c = Colour.GREEN;
            // If all diffrent
            else if (red > green) {
                if (red > blue) c = Colour.RED;
                else c = Colour.BLUE;
            } else {
                if (green > blue) c = Colour.GREEN;
                else c = Colour.BLUE;
            }
        }

        colourOfDay.put(day, c);
        return c;
    }

	private static Map<Colour, Integer> getCounts(long n) {
        Map<Colour, Integer> counts = new HashMap<>();

		for (Colour c : Colour.values()) {
            counts.put(c, 0);
        }     

        long upper = (long) Math.sqrt(n);

        // Count the colour of the factor days.
        for (long i = 2; i <= upper; i++) {
            if (n % i == 0) {
                Colour c = colour(i);
                counts.put(c, counts.get(c) + 1);

                c = colour(n / i);
                counts.put(c, counts.get(c) + 1);
            }
        }

        return counts;
	}

    public static void main(String[] args) {
        question1(10000000);
        question2();
        question3();
    }

	private static void printSample() {
		for (long n = 2; n <= 1000; n++) {
            System.out.println(n + " " + colour(n));
        }
    }
    
	private static void question1(long n) {
		Colour d1 = colour(1);
        Colour d2 = colour(2);
        Colour d3 = colour(3);

        for (long i = 3; i <= n; i++) {
            d1 = d2;
            d2 = d3;
            d3 = colour(i);

            if (i % 1000 == 0) System.out.print("\rCURRENT: " + i + " " + d3);

            if (d1 == Colour.GOLD && d1 == d2 && d2 == d3) {
                System.out.println("\nTHREE DAY HOLIDAY ON DAY " + i);
                return;
            }
        }

        System.out.println("\nNO THREE HOLIDAY IN FIRST " + n + " YEARS");
	}
    
        private static void question2() {
            long numHolidays = 0;
            long start = 1000 * daysInYear;
    
            for (long i = 1; i <= daysInYear; i++) {
                if (colour(start + i) == Colour.GOLD) numHolidays++; 
            }
    
            System.out.println(numHolidays + " HOLIDAYS IN YEAR 1,000");
        }

	private static void question3() {
		long numHolidays = 0;
        long start = 10000000L * daysInYear;

        for (long i = 1; i <= daysInYear; i++) {
            if (colour(start + i) == Colour.GOLD) numHolidays++; 
        }

        System.out.println(numHolidays + " HOLIDAYS IN YEAR 10,000,000");
	}
}
