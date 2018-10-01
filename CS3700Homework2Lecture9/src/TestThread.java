/*
 * David Hau
 * CS3700
 * 10/1/2018
 */

/*
//Executor  --separate thread creation and execution execute() 
import java.util.concurrent.Executor; 
import java.util.concurrent.Executors; 
import java.util.concurrent.ThreadPoolExecutor; 
import java.util.concurrent.TimeUnit; 

public class TestThread
{ 
   public static void main(final String[] arguments) throws InterruptedException
   { 
      Executor executor = Executors.newCachedThreadPool(); 
      executor.execute(new Task());     //start thread it will execute run() as it is a Runnable object  
      ThreadPoolExecutor pool = (ThreadPoolExecutor)executor; 
      pool.shutdown(); 
   }   

   static class Task implements Runnable
   { 
      public void run()
      { 
         try
         { 
            Long duration = (long) (Math.random() * 5); 
            System.out.println("Running Task!"); 
            TimeUnit.SECONDS.sleep(duration); 
            System.out.println("Task Completed"); 
         }
         catch (InterruptedException e)
         { 
            e.printStackTrace(); 
         } 
      } 
   } 
}
*/

/*
//ExecutorService -->subinterface of ExecutorService use schedule()-->execute task after specified delay 
import java.util.concurrent.Executors; 
import java.util.concurrent.ScheduledExecutorService; 
import java.util.concurrent.ScheduledFuture; 
import java.util.concurrent.TimeUnit; 

public class TestThread
{
	public static void main(final String[] arguments) throws InterruptedException
	{
    final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1); //create 1 thread in ThreadPool 
    final ScheduledFuture<?> beepHandler =  
       scheduler.scheduleAtFixedRate(new BeepTask(), 2, 2, TimeUnit.SECONDS); //initially wait delay then repeat after specified delay 
    //also try scheduler.scheduleWithFixedDelay 
    scheduler.schedule(new Runnable() { 
       @Override 
       public void run()
       { 
          beepHandler.cancel(true); 
          scheduler.shutdown();            
       }
       }, 10, TimeUnit.SECONDS); 
    } 
	
	static class BeepTask implements Runnable
	{ 
		public void run()
		{
			System.out.println("beep");       
		}
	}
} 
*/

/*
//Concurrent Collections 
//BlockingQueue 

import java.util.Random; 
import java.util.concurrent.ArrayBlockingQueue; 
import java.util.concurrent.BlockingQueue; 

public class TestThread { 

 public static void main(final String[] arguments) throws InterruptedException { 

    BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(10);   //create queue size 10 
    Producer producer = new Producer(queue);  //pass queue to producer 

    Consumer consumer = new Consumer(queue);  //pass queue to consumer 

    new Thread(producer).start(); //exectute a new thread start at Producer.run() 
    new Thread(consumer).start(); //exectute a new thread start at Consumer.run() 

    Thread.sleep(4000);   //put main thread to sleep for 4 seconds. Note program will end when it wakes up 
 }   

 static class Producer implements Runnable { 

    private BlockingQueue<Integer> queue; 

    public Producer(BlockingQueue queue) { 
       this.queue = queue; 
    } 

    @Override 
    public void run() {       //producer thread starts here  
       Random random = new Random(); 

       try { 
          int result = random.nextInt(100); 

          Thread.sleep(1000); 
          queue.put(result);  //this is atomic. Do not have to worry about thread interference 
          System.out.println("Added: " + result); 

          result = random.nextInt(100); 
          Thread.sleep(1000); 
          queue.put(result);  //this is atomic. 
          System.out.println("Added: " + result); 

          result = random.nextInt(100); 
          Thread.sleep(1000); 
          queue.put(result);  //this is atomic. 
          System.out.println("Added: " + result); 

       } catch (InterruptedException e) { 
          e.printStackTrace(); 
       } 
    }     
 } 

 static class Consumer implements Runnable { 
    private BlockingQueue<Integer> queue; 

    public Consumer(BlockingQueue queue) { 
       this.queue = queue; 
    } 

    @Override 
    public void run() {   //consumer thread starts here 
       try { 
          System.out.println("Removed: " + queue.take());//this is atomic. 
          System.out.println("Removed: " + queue.take());//this is atomic. 
          System.out.println("Removed: " + queue.take());//this is atomic. 
       } catch (InterruptedException e) { 
          e.printStackTrace(); 
       } 
    } 
 } 
} 
*/

/*
//ConcurrentMap Entering/Removing key/token will not have thread interference  

import java.util.ConcurrentModificationException; 
import java.util.HashMap; 
import java.util.Iterator; 
import java.util.Map; 
import java.util.concurrent.ConcurrentHashMap; 

public class TestThread { 
	
 public static void main(final String[] arguments) { 
    Map<String,String> map = new ConcurrentHashMap<String, String>(); 
    
    map.put("1", "One");  //put() will be checked and inserted as an atomic operation 
    map.put("2", "Two"); 
    map.put("3", "Three"); 
    map.put("5", "Five"); 
    map.put("6", "Six"); 

    System.out.println("Initial ConcurrentHashMap: " + map); 
    Iterator<String> iterator = map.keySet().iterator(); 

    try {  
       while(iterator.hasNext()) { 
          String key = iterator.next(); 

          if(key.equals("3")) { 
             map.put("4", "Four"); 
          } 
       } 

    } catch(ConcurrentModificationException cme) { 
       cme.printStackTrace(); 
    } 

    System.out.println("ConcurrentHashMap after modification: " + map); 

    map = new HashMap<String, String>(); 
    map.put("1", "One"); 
    map.put("2", "Two"); 
    map.put("3", "Three"); 
    map.put("5", "Five"); 
    map.put("6", "Six"); 

    System.out.println("Initial HashMap: " + map); 
    iterator = map.keySet().iterator(); 

    try { 
       while(iterator.hasNext()) { 
          String key = iterator.next(); 

          if(key.equals("3")){ 
             map.put("4", "Four"); 
          } 
       }
       
       System.out.println("HashMap after modification: " + map);
       
    } catch(ConcurrentModificationException cme) { 
       cme.printStackTrace(); 
    } 
 }   
}
*/

/*
//ConcurrentNavigableMap - subinterface of ConcurrentMap implements ConcurrentSkipListMap which is a concurrent TreeMap 
import java.util.concurrent.ConcurrentNavigableMap; 
import java.util.concurrent.ConcurrentSkipListMap; 

public class TestThread {
	
 public static void main(final String[] arguments) { 
    ConcurrentNavigableMap<String,String> map = 
       new ConcurrentSkipListMap<String, String>(); 

    map.put("1", "One");   //will guarantee NOT to interfere with other threads when inserting put() 
    map.put("2", "Two"); 
    map.put("3", "Three"); 
    map.put("5", "Five"); 
    map.put("6", "Six"); 

    System.out.println("Initial ConcurrentHashMap: "+map); 
    System.out.println("HeadMap(\"2\") of ConcurrentHashMap: "+map.headMap("2")); 
    System.out.println("TailMap(\"2\") of ConcurrentHashMap: "+map.tailMap("2")); 
    System.out.println( 
       "SubMap(\"2\", \"4\") of ConcurrentHashMap: "+map.subMap("2","4")); 
 }   
} 

*/

/*
//Atomic Variable 
//AtomicInteger -->NOTE: creating over a 1000 threads 
import java.util.concurrent.atomic.AtomicInteger; 

public class TestThread { 

 static class Counter { 

    private AtomicInteger c = new AtomicInteger(0); 

    public void increment() { 
       c.getAndIncrement(); // c will be read and incremented as one step. No context switch  
    } 

    public int value() { 
       return c.get();  //atomic operation to read value of c 
    } 
 } 


 public static void main(final String[] arguments) throws InterruptedException { 
    final Counter counter = new Counter(); 

    //1000 threads are created 
    for(int i = 0; i < 1000 ; i++) { 

       new Thread(new Runnable() { 
          public void run() {  //threads will starts running here 
             counter.increment();   
          } 
       }).start();  
    }   

    Thread.sleep(6000);  //wait 6 seconds 
    System.out.println("Final number (should be 1000): " + counter.value()); 
 } 
} 
*/

/*
//AtomicReference  

import java.util.concurrent.atomic.AtomicReference; 

public class TestThread { 
 private static String message = "hello"; 
 private static AtomicReference<String> atomicReference; 


 public static void main(final String[] arguments) throws InterruptedException { 
    atomicReference = new AtomicReference<String>(message); 

    new Thread("Thread 1") { 

       public void run() { 
          atomicReference.compareAndSet(message, "Thread 1"); 
          message = message.concat("-Thread 1!"); 
       }; 
    }.start(); 

    System.out.println("Message is: " + message); 
    System.out.println("Atomic Reference of Message is: " + atomicReference.get()); 
 } 
}
*/

/*
//AtomicIntegerArray 
import java.util.concurrent.atomic.AtomicIntegerArray; 

public class TestThread { 
	
  private static AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(10); 
  public static void main(final String[] arguments) throws InterruptedException { 

      for (int i = 0; i<atomicIntegerArray.length(); i++) { 
          atomicIntegerArray.set(i, 1); 
      } 

      Thread t1 = new Thread(new Increment()); 
      Thread t2 = new Thread(new Compare()); 

      t1.start(); 
      t2.start();
      
      t1.join(); 
      t2.join(); 
      
      System.out.println("Values: "); 
      for (int i = 0; i<atomicIntegerArray.length(); i++) { 
          System.out.print(atomicIntegerArray.get(i) + " "); 
      } 
  } 

  static class Increment implements Runnable { 

      public void run() { 

          for(int i = 0; i<atomicIntegerArray.length(); i++) { 
              int add = atomicIntegerArray.incrementAndGet(i); 
              System.out.println("Thread " + Thread.currentThread().getId() 
                      + ", index " +i + ", value: "+ add); 
          } 
      } 
  } 

  static class Compare implements Runnable { 
      public void run() { 
          for(int i = 0; i<atomicIntegerArray.length(); i++) { 
              boolean swapped = atomicIntegerArray.compareAndSet(i, 2, 3); 
              if(swapped) { 
                  System.out.println("Thread " + Thread.currentThread().getId() 
                          + ", index " +i + ", value: 3"); 
              } 
          } 
      } 
  } 
}
*/

/*
//AtomicReferenceArray 
import java.util.concurrent.atomic.AtomicReferenceArray; 

public class TestThread { 

 private static String[] source = new String[10]; 
 private static AtomicReferenceArray<String> atomicReferenceArray  
    = new AtomicReferenceArray<String>(source); 

 public static void main(final String[] arguments) throws InterruptedException { 

    for (int i = 0; i<atomicReferenceArray.length(); i++) { 
       atomicReferenceArray.set(i, "item-2"); 
    } 

    Thread t1 = new Thread(new Increment()); 
    Thread t2 = new Thread(new Compare()); 

    t1.start(); 
    t2.start(); 

    t1.join(); 
    t2.join();         

 }

 static class Increment implements Runnable { 

    public void run() { 

       for(int i = 0; i<atomicReferenceArray.length(); i++) { 
          String add = atomicReferenceArray.getAndSet(i,"item-"+ (i+1)); 
          System.out.println("Thread " + Thread.currentThread().getId()  
             + ", index " +i + ", value: "+ add); 
       } 
    } 
 } 

 static class Compare implements Runnable { 
    public void run() { 

       for(int i = 0; i<atomicReferenceArray.length(); i++) { 
          System.out.println("Thread " + Thread.currentThread().getId()  
             + ", index " +i + ", value: "+ atomicReferenceArray.get(i)); 
          boolean swapped = atomicReferenceArray.compareAndSet(i, "item-2", "updated-item-2"); 
          System.out.println("Item swapped: " + swapped); 

          if(swapped) { 
             System.out.println("Thread " + Thread.currentThread().getId()  
                + ", index " +i + ", updated-item-2"); 
          } 
       } 
    } 
 } 
}
*/

//
//Future with ExecutorService passing Callable NOTE: will execute call() vs run() for Runnable 
import java.util.concurrent.Callable; 
import java.util.concurrent.ExecutionException; 
import java.util.concurrent.ExecutorService; 
import java.util.concurrent.Executors; 
import java.util.concurrent.Future; 

public class TestThread { 

 public static void main(final String[] arguments) throws InterruptedException, 
    ExecutionException { 

    ExecutorService executor = Executors.newSingleThreadExecutor(); 

    System.out.println("Factorial Service called for 10!"); 
    Future<Long> result10 = executor.submit(new FactorialService(10)); //start executing factorial of 10 NONBLOCKING 

    System.out.println("Factorial Service called for 20!"); 
    Future<Long> result20 = executor.submit(new FactorialService(20)); //start executing factorial of 20 NONBLOCKING 

    Long factorial10 = result10.get();    //main thread will wait, block, until value is returned 
    System.out.println("10! = " + factorial10); 

    Long factorial20 = result20.get(); //main thread will wait, block, until value is returned 
    System.out.println("20! = " + factorial20); 

    executor.shutdown();  //stop all threads 
 }   


 static class FactorialService implements Callable<Long> { 
    private int number; 

    public FactorialService(int number) { 
       this.number = number; 
    } 

    @Override 
    public Long call() throws Exception { //threads will start executing here 
       return factorial(); 
    } 

    private Long factorial() throws InterruptedException { 
       long result = 1;  

       while (number != 0) {  
          result = number * result;  
          number--;  
          Thread.sleep(100);  
       } 
       return result;  
    } 
 } 
}
//