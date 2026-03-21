package com.example.ultssetup;
/**
 * Write a description of class TestCharHandler here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class TestCharHandler
{
    // instance variables - replace the example below with your own
    private int x;

    /**
     * Constructor for objects of class TestCharHandler
     */
    public TestCharHandler(String... exclude)
    {
        CharHandler charHandle = CharHandler.getInstance();
        charHandle.exclude(exclude);
        String[] excluded = charHandle.getExcluded();
        for(String i : excluded) {
            System.out.println(i);
        }
        CharEnabler.update();
        System.out.println("done");
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public int sampleMethod(int y)
    {
        // put your code here
        return x + y;
    }
}
