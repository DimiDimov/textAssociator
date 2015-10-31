/**
* JUnit tests for the TextAssociator class.
* @author Darren Hou
* 
* To use, import this .java file into your Stacks project by right-clicking
* the project -> Import -> File System -> location of file.
* The JUnit library must be added by right-clicking the project -> Properties -> 
*  Java Build Path -> Libraries -> Add Library -> JUnit 4
* To run, right click ArrayStackTest -> Run As -> JUnit Test
* The results of the test will show in the JUnit tab, to the right of PackageExplorer.
* Clicking on any failed test displays the failure trace (expected vs. actual).
* 
* No guarantees that if you pass every test, your code is 100% correct. 
* However if you fail any, it is very likely something is wrong with your code.
* 
*/

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
 

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
 

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.EmptyStackException;

//import junitx.util.PrivateAccessor;

public class TextAssociatorTest {
	private static TextAssociator stack;
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	 
	@Before
	public void setUpBefore() throws Exception {
		//System.setOut(new PrintStream(outContent));
		stack = new TextAssociator();
		stack.push(0);
		stack.push(1.91212);
		stack.push(2.31);
	}
	
	@Test(expected = EmptyStackException.class) 
	public void testPopException() {
		while (!stack.isEmpty()) {
			stack.pop();
		}
		stack.pop();
	}
}
