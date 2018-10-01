/*
 * David Hau
 * CS3700
 * 10/1/2018
 */

public class SleepMessages
{
	public static void main(String[] args) throws InterruptedException
	{
		String importantInfo[] =
			{
					"Mares eats oats",
					"Does eat oats",
					"Little lambs eat ivy",
					"A kid will eat ivy too"
					
			};
		
		for(int i = 0; i < importantInfo.length; i++)
		{
			// Pause for 1 seconds
			Thread.sleep(1000);
			// Print a message
			System.out.println(importantInfo[i]);
		}
		
		System.out.println();
		
		// INTERRUPT
		for(int i = 0; i < importantInfo.length; i++)
		{
			try
			{
				// Pause for 1 seconds
				Thread.sleep(1000);
			}
			catch (InterruptedException e)
			{
				// We've been interrupted: no more messages
				return;
			}
			// Print a message
			System.out.println(importantInfo[i]);
		}
		
		System.out.println();
		
		// Example 2
		/*
		for(int i = 0; i < inputs.length; i++)
		{
			heavyCrunch(inputs[i]);
			if(Thread.interrupted())
			{
				return;
			}
		}
		*/
		
		// Allow interrupt handling code to be centralized in a "catch" clause
		if(Thread.interrupted())
		{
			throw new InterruptedException();
		}
		
	}
}
