/*
 * David Hau
 * CS3700
 * 10/1/2018
 */

//Using Atomic Variable --> gurantee Atomic operations when updating variable c 

import java.util.concurrent.atomic.AtomicInteger; 

class AtomicCounter
{ 
    private AtomicInteger c = new AtomicInteger(0);
    
    public void increment()
    { 
        c.incrementAndGet(); 
    } 

    public void decrement()
    { 
        c.decrementAndGet(); 
    } 

    public int value()
    { 
        return c.get(); 
    } 
}