/*
 * David Hau
 * CS3700
 * 10/1/2018
 */

//Thread Interference Safe  

class SynchronizedCounter
{ 
    private int c = 0; 

    public synchronized void increment()
    { 
        c++; 
    } 

    public synchronized void decrement()
    { 
        c--; 
    } 

    public synchronized int value()
    { 
        return c; 
    }

} 