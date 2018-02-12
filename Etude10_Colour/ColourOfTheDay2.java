import java.util.*;

/**
 * ColourOfTheDay2
 */
public class ColourOfTheDay2 {
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
<<<<<<< HEAD
                if (green > blue) c = Colour.GREEN;
=======
                if (green > blue) c  = Colour.GREEN;
>>>>>>> 725e76bedd78bd43401d2e7d5c59882bc40fad0b
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

        int upper = (int) Math.sqrt(n);
        
        // Count the colours of the previous days of the same factor.
        for (int i = 2; i <= upper; i++) {
            if (n % i == 0) {
                Colour c = colour(n - i);
                counts.put(c, counts.get(c) + 1);

                c = colour(n - (n / i));
                counts.put(c, counts.get(c) + 1);
            }
        }

        return counts;
	}

    public static void main(String[] args) {
        question1(1000000);
        question2();
        // question3(); // Produces stack overflow error.
    }

	private static void printSample() {
		for (int n = 2; n <= 1000; n++) {
            System.out.println(n + " " + colour(n));
        }
	}
    
    private static void question1(int n) {
        Colour d1 = colour(1);
        Colour d2 = colour(2);
        Colour d3 = colour(3);

        for (int i = 3; i <= n; i++) {
            d1 = d2;
            d2 = d3;
            d3 = colour(i);

            if (i % 10000 == 0) System.out.print("\rCURRENT: " + i + " " + d3);

            if (d1 == Colour.GOLD && d1 == d2 && d2 == d3) {
                System.out.println("\nTHREE DAY HOLIDAY ON DAY " + i);
                return;
            }
        }

        System.out.println("\nNO THREE HOLIDAY IN FIRST " + n + " DAYS");
    }    

    private static void question2() {
        try {
            int numHolidays = 0;
            long start = 1000 * daysInYear;

            for (long i = 1; i <= daysInYear; i++) {
                if (colour(start + i) == Colour.GOLD) numHolidays++; 
                System.out.println(i + " " + colour(start + i));
            }
            
            System.out.println(numHolidays + " HOLIDAYS IN YEAR 1,000");
        } catch (StackOverflowError e) {
            System.out.println("Stack Overflow error");
        }
    }

	private static void question3() {
		int numHolidays = 0;
        long start = 10000000L * daysInYear;
        try {
            for (long i = 1; i <= daysInYear; i++) {
                if (colour(start + i) == Colour.GOLD) numHolidays++; 
            }

            System.out.println(numHolidays + " HOLIDAYS IN YEAR 10,000,000");
        } catch (StackOverflowError e) {
            System.out.println("Stack Overflow error");
        }

	}
}
