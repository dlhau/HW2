/*
 * David Hau
 * CS3700
 * 10/1/2018
 */

/*
//Memoization using Map
import java.util.Map; 
import java.util.HashMap; 

public class Lect4 { 
    public static void main(String[] args) { 
        Fibber x = new Fibber(); 
        System.out.print(x.fib(10));    //print fibonnaci of 10 
    } 
} 

class Fibber { 

    private Map<Integer, Integer> memo = new HashMap<>(); 
    public int fib(int n) { 
        if (n < 0) { 
            throw new IllegalArgumentException( 
                    "Index was negative. No such thing as a negative index in a series."); 

            // base cases 
        } else if (n == 0 || n == 1) { 
            return n; 
        } 

        // see if we've already calculated this, grab calculated value 
        if (memo.containsKey(n)) { 
            System.out.printf("grabbing memo[%d]\n", n); 
            return memo.get(n); 
        } 

        System.out.printf("computing fib(%d)\n", n); 
        int result = fib(n - 1) + fib(n - 2); 
        
        // memoize 
        memo.put(n, result);//save results in map 

        return result;
    }
}
*/

//
//BARRIER AND LECT4 BELOW  REQUIRED TO RUN BARRIER EXAMPLE 
class Lect4 
{ 
  static class MyThread1 implements Runnable 
  { 
      public MyThread1(Barrier barrier) 
      { 
          this.barrier = barrier; 
      } 

      public void run()  //thread1 will start executing here 
      { 
          try 
          { 
              Thread.sleep(1000); 
              System.out.println("MyThread1 waiting on barrier"); 
              barrier.block(); //assumes it is faster hence it will wait for thread2 
              System.out.println("MyThread1 has been released"); 
          } catch (InterruptedException ie) 
          { 
              System.out.println(ie); 
          } 
      } 

      private Barrier barrier; 

  }

  static class MyThread2 implements Runnable 
  { 
      Barrier barrier; 

      public MyThread2(Barrier barrier) 
      { 
          this.barrier = barrier; 
      } 

      public void run()   //thread2 will start executing here 
      { 
          try 
          { 
              Thread.sleep(3000); 
              System.out.println("MyThread2 releasing blocked threads\n"); 
              barrier.release(); //assume thread2 is slower thus it release() but, 
              // FOR EXAMPLE we can check an atomiccounter to have last thread release() 
              System.out.println("MyThread1 releasing blocked threads\n"); 

          } catch (InterruptedException ie) 

          { 
              System.out.println(ie); 
          } 
      } 
  } 

  public static void main(String[] args) throws InterruptedException 
  {
      /* 
       *     MyThread1            MyThread2 
       *         ...                    ... 
       *         BR.block();            ... 
       *         ...                    BR.release(); 
       */
    //
      Barrier BR = new Barrier(); 
      Thread t1 = new Thread(new Lect4.MyThread1(BR)); 
      Thread t2 = new Thread(new Lect4.MyThread2(BR)); 
      t1.start();   
      t2.start(); 
      t1.join(); 
      t2.join(); 
  } 
}
//