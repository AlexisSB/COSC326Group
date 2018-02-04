import java.io.IOException;
import java.util.*;
import java.math.*;

/**
 * \brief   This is a colour class which contains methods and variable to
 *          maintain colour of the day.
 */
public class Colour{
  enum CLR {
    NOCLR, RED, GREEN, BLUE, GOLD;
  }
  public static List<CLR> daysColor;
  public static Integer pMethod = 1;

  /**
   * \brief   This is a colour class default constructor which initialise all
   *          public variables.
   */
  public Colour(){
    daysColor = new ArrayList<CLR>();
    daysColor.add(CLR.NOCLR);
  }

  public static List<Long> factors(Long n) {
    Long i;
    List<Long> fact = new ArrayList<Long>();
    for (i = 2L; i < n; i++) {
      if(((n % i) == 0)) {
        fact.add(i);
      }
    }
    return fact;
  }

  static boolean checkPrime(long n)
  {
    // Converting long to BigInteger
    BigInteger big = new BigInteger(String.valueOf(n));
    return big.isProbablePrime(1);
  }

  static CLR codPrime(long n)
  {
    CLR clr = CLR.NOCLR;
    if ((((n-1)%7) == 0) || (((n-4)%7) == 0)) {
      clr = CLR.RED;
    } else if ((((n-2)%7) == 0) || (((n-5)%7) == 0)) {
      clr = CLR.GREEN;
    } else if ((((n-3)%7) == 0) || (((n-6)%7) == 0)) {
      clr = CLR.BLUE;
    }
    return clr;
  }

  static CLR codNonprimeM1(Long n)
  {
    CLR clr = CLR.NOCLR;
    Long redCnt = 0L;
    Long greenCnt = 0L;
    Long blueCnt = 0L;
    List<Long> nFact = factors(n);
    for(int i=0;i<nFact.size();i++){
      Long cFact = nFact.get(i);
      CLR lClr = CLR.NOCLR;
      int day = (int)(long)(cFact);
      lClr = daysColor.get(day);
      if(lClr == CLR.RED) {
        redCnt++;
      } else if(lClr == CLR.GREEN) {
        greenCnt++;
      } else if(lClr == CLR.BLUE) {
        blueCnt++;
      }
    }
    if((redCnt == blueCnt) && (blueCnt == greenCnt)) {
      clr = CLR.GOLD;
    } else if ((redCnt == blueCnt) && (redCnt != greenCnt)) {
      clr = CLR.GREEN;
    } else if ((redCnt == greenCnt) && (redCnt != blueCnt)) {
      clr = CLR.BLUE;
    } else if ((greenCnt == blueCnt) && (redCnt != blueCnt)) {
      clr = CLR.RED;
    } else {
      clr = (redCnt > blueCnt) ? ( (redCnt > greenCnt) ? CLR.RED : CLR.GREEN ) :
                    ( (blueCnt > greenCnt) ? CLR.BLUE : CLR.GREEN );
    }
    return clr;
  }

  static CLR codNonprimeM2(Long n)
  {
    CLR clr = CLR.NOCLR;
    Long redCnt = 0L;
    Long greenCnt = 0L;
    Long blueCnt = 0L;
    List<Long> nFact = factors(n);
    for(int i=0;i<nFact.size();i++){
      Long cFact = nFact.get(i);
      CLR lClr = CLR.NOCLR;
      int day = (int)(long)(((n/cFact)-1)*cFact);
      lClr = daysColor.get(day);
      if(lClr == CLR.RED) {
        redCnt++;
      } else if(lClr == CLR.GREEN) {
        greenCnt++;
      } else if(lClr == CLR.BLUE) {
        blueCnt++;
      }
    }
    if((redCnt == blueCnt) && (blueCnt == greenCnt)) {
      clr = CLR.GOLD;
    } else if ((redCnt == blueCnt) && (redCnt != greenCnt)) {
      clr = CLR.GREEN;
    } else if ((redCnt == greenCnt) && (redCnt != blueCnt)) {
      clr = CLR.BLUE;
    } else if ((greenCnt == blueCnt) && (redCnt != blueCnt)) {
      clr = CLR.RED;
    } else {
      clr = (redCnt > blueCnt) ? ( (redCnt > greenCnt) ? CLR.RED : CLR.GREEN ) :
                    ( (blueCnt > greenCnt) ? CLR.BLUE : CLR.GREEN );
    }
    return clr;
  }

  static CLR colourOfDay(Long n)
  {
    CLR clr;
    if (n == 1) {
      clr = CLR.NOCLR;
    } else if (n == 7) {
      clr = CLR.GOLD;
    } else if (true == checkPrime(n)) {
      clr = codPrime(n);
    } else {
      clr = (pMethod == 1) ? codNonprimeM1(n) : codNonprimeM2(n);
    }
    daysColor.add(clr);
    return clr;
  }

  /**
   * \brief   Main function which executes when we run the application.
   */
  public static void main(String args[]) {
    Long daysInYear = 350L;
    Colour cod = new Colour();

    // Question 1
    Long q1Year = 1000L;
    Long q1Days = q1Year * daysInYear;
    cod.daysColor.clear();
    daysColor.add(CLR.NOCLR);
    pMethod = 1;
    int gold3 = 0;
    Long nDayGold3 = 0L;
    for(Long i=1L;i<=q1Days;i++){
      CLR clr;
      clr = cod.colourOfDay(i);
      System.out.println(i + "\t" + clr);
      if(clr == CLR.GOLD) {
        nDayGold3 = i;
        if((++gold3) == 3) {
          break;
        }
      } else {
        gold3 = 0;
      }
    }
    if(3 == gold3) {
      System.out.println("Three consicutive holiday found in " + q1Year + " years on Day " + nDayGold3);
    } else {
      System.out.println("There are no three consicutive holidays in " + q1Year + " years");
    }

    // Question 2
    Long q2Year = 1000L;
    Long q2Days = q2Year * daysInYear;
    cod.daysColor.clear();
    daysColor.add(CLR.NOCLR);
    pMethod = 1;
    Long nGold = 0L, nRed = 0L, nGreen = 0L, nBlue = 0L, nNoClr = 0L;
    for(Long i=1L;i<=q2Days;i++){
      CLR clr;
      clr = cod.colourOfDay(i);
      System.out.println(i + "\t" + clr);
      if( clr == CLR.GOLD ) {
        nGold++;
      } else if ( clr == CLR.GREEN ) {
        nGreen++;
      } else if ( clr == CLR.BLUE) {
        nBlue++;
      } else if ( clr == CLR.RED ) {
        nRed++;
      } else if ( clr == CLR.NOCLR ) {
        nNoClr++;
      }
    }
    System.out.println( nRed + "Days with RED as COD");
    System.out.println( nBlue + "Days with BLUE as COD");
    System.out.println( nGreen + "Days with GREEN as COD");
    System.out.println( nGold + "Days with GOLD as COD");
    System.out.println( nNoClr + "Days with NO COLOUR as COD");
    System.out.println("No of days with Gold Colour of the day is " + nGold + " in " + q2Year + " years");

    nGold = 0L;
    nRed = 0L;
    nGreen = 0L;
    nBlue = 0L;
    nNoClr = 0L;
    cod.daysColor.clear();
    daysColor.add(CLR.NOCLR);
    pMethod = 2;
    for(Long i=1L;i<=q2Days;i++){
      CLR clr;
      clr = cod.colourOfDay(i);
      System.out.println(i + "\t" + clr);
      if( clr == CLR.GOLD ) {
        nGold++;
      } else if ( clr == CLR.GREEN ) {
        nGreen++;
      } else if ( clr == CLR.BLUE) {
        nBlue++;
      } else if ( clr == CLR.RED ) {
        nRed++;
      } else if ( clr == CLR.NOCLR ) {
        nNoClr++;
      } 
    }
    System.out.println( nRed + "Days with RED as COD");
    System.out.println( nBlue + "Days with BLUE as COD");
    System.out.println( nGreen + "Days with GREEN as COD");
    System.out.println( nGold + "Days with GOLD as COD");
    System.out.println( nNoClr + "Days with NO COLOUR as COD");
    System.out.println("No of days with Gold Colour of the day is " + nGold + " in " + q2Year + " years");

    // Question 3
    nGold = 0L;
    nRed = 0L;
    nGreen = 0L;
    nBlue = 0L;
    nNoClr = 0L;
    cod.daysColor.clear();
    daysColor.add(CLR.NOCLR);
    Long q3Year = 10000000L;
    Long q3Days = q3Year * daysInYear;
    pMethod = 1;
    for(Long i=1L;i<=q3Days;i++){
      CLR clr = cod.colourOfDay(i);
      System.out.println(i + "\t" + clr );
      if( clr == CLR.GOLD ) {
        nGold++;
      } else if ( clr == CLR.GREEN ) {
        nGreen++;
      } else if ( clr == CLR.BLUE) {
        nBlue++;
      } else if ( clr == CLR.RED ) {
        nRed++;
      } else if ( clr == CLR.NOCLR ) {
        nNoClr++;
      }
    }
    System.out.println( nRed + "Days with RED as COD");
    System.out.println( nBlue + "Days with BLUE as COD");
    System.out.println( nGreen + "Days with GREEN as COD");
    System.out.println( nGold + "Days with GOLD as COD");
    System.out.println( nNoClr + "Days with NO COLOUR as COD");
  }
}
