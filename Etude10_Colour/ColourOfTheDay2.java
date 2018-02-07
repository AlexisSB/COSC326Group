import java.util.*;

/**
 * ColourOfTheDay2
 */
public class ColourOfTheDay2 {
    static enum Colour { NONE, RED, GREEN, BLUE, GOLD }
    static final int daysInYear = 350;

    static public boolean isPrime(long n) {
        if (n % 2 == 0) return false;

        for (long i = 3; i * i <= n;i += 2) {
            if(n % i == 0) return false;
        }

        return true;
    }

    static Colour colour(long day) {
        if (day == 1) return Colour.NONE;
        if (day == 7) return Colour.GOLD;

        if (isPrime(day)) {
            long dayOfWeek = day % 7;
            if (dayOfWeek == 1 || dayOfWeek == 4) return Colour.RED;
            if (dayOfWeek == 2 || dayOfWeek == 5) return Colour.GREEN;
            if (dayOfWeek == 3 || dayOfWeek == 6) return Colour.BLUE;
            return Colour.NONE; 
        }

        Map<Colour, Integer> counts = getCounts(day);
        int red = counts.get(Colour.RED);
        int green = counts.get(Colour.GREEN);
        int blue = counts.get(Colour.BLUE);

        // If all the same
        if (red == green && green == blue) {
            return Colour.GOLD;
        } 
        
        // If two the same
        if (blue == green && blue != red) return Colour.RED;
        if (red == green && red != blue) return Colour.BLUE;
        if (red == blue && red != green) return Colour.GREEN;

        // If all diffrent
        if (red > green) {
            if (red > blue) return Colour.RED;
            else return Colour.BLUE;
        } 

        return Colour.GREEN;
    }

	private static Map<Colour, Integer> getCounts(long n) {
        Map<Colour, Integer> counts = new HashMap<>();
        
		for (Colour c : Colour.values()) {
            counts.put(c, 0);
        }        
        
        while (n % 2 == 0) {
            counts.put(Colour.GREEN, counts.get(Colour.GREEN) + 1);
            n /= 2;
        }
        
        for (long i = 3; i <= Math.sqrt(n); i += 2) {
            while (n % i == 0) {
                counts.put(colour(i), counts.get(colour(i)) + 1);
                n /= i;
            }
        }
        
        if (n > 2)  counts.put(colour(n), counts.get(colour(n)) + 1);

        return counts;
	}

    public static void main(String[] args) {
        question1(Long.MAX_VALUE);
        question2();
        question3();
    }

	private static void question3() {
		long numHolidays = 0;
        long start = 10000000 * daysInYear;

        for (long i = 1; i <= daysInYear; i++) {
            if (colour(start + i) == Colour.GOLD) numHolidays++; 
        }

        System.out.println(numHolidays + " HOLIDAYS IN YEAR 10,000,000");
	}

	private static void question2() {
		long numHolidays = 0;
        long start = 1000 * daysInYear;

        for (long i = 1; i <= daysInYear; i++) {
            if (colour(start + i) == Colour.GOLD) numHolidays++; 
        }

        System.out.println(numHolidays + " HOLIDAYS IN YEAR 1,000");
	}

	private static void question1(long n) {
		Colour d1 = colour(1);
        Colour d2 = colour(2);
        Colour d3 = colour(3);

        for (long i = 3; i <= n; i++) {
            d1 = d2;
            d2 = d3;
            d3 = colour(i);

            if (d1 == Colour.GOLD && d1 == d2 && d2 == d3) {
                System.out.println("THREE DAY HOLIDAY ON DAY " + i);
                break;
            }
        }
	}    
}