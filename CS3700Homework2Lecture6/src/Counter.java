/*
 * David Hau
 * CS3700
 * 10/1/2018
 */

//INITIALLY -->problem for concurrent application  

class Counter
{ 
    private int c = 0; 
    
    public void increment()
    { 
        c++; 
    } 

    public void decrement()
    { 
        c--; 
    } 

    public int value()
    { 
        return c; 
    } 
}