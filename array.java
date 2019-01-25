import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.IntStream;
import java.lang.reflect.Array;

public class array {
	
	public static void main(String[] args){
		
// 1		
		int length;
		double density;
		Scanner scan = new Scanner(System.in);

		System.out.println("Please enter a positive integer array length: ");
		length = scan.nextInt();
		while (length < 1){ //catch if invalid input is given
			System.out.println("Please enter a valid length: ");
			length = scan.nextInt();
		}
    	
		System.out.println("Please enter density between 0 and 1: ");
		density = scan.nextDouble();
		while((density < 0) || (density > 1)){ //checks for invalid input
			System.out.println("Please enter a valid density: ");
			density = scan.nextDouble();
		}
		
		sparseMax(ds_converter(createDenseArray(length, density))); //composition of function calls- all functions are called. order of output is different than the example on the handout but all the information is there.
		denseMax(sd_converter(length, createSparseArray(length, density))); //same as above

	}
	
	
	
// 2
	
    public static int[] createDenseArray(int length, double density){
    	int[] denseArray = new int[length];
    	Random random = new Random();
    	long startTime = System.nanoTime();
    	
    	for (int i = 0; i < length; i++){
    		if(random.nextDouble() < density){
    			denseArray[i] = (random.nextInt(1000000) + 1);
    		}else{
    			denseArray[i] = 0;
    		}
    	}
    	System.out.println("Created Dense Array of length " + length + " in a time of " + (System.nanoTime() - startTime) / 1000000000.0 + " seconds");
		return denseArray;
    		
    	}
    
    
    
// 3

    public static ArrayList<int[]> createSparseArray(int length, double density){
    	long startTime = System.nanoTime();
    	
    	Random random = new Random();
    	int[] first_array =  IntStream.range(0, length).toArray();
    	ArrayList<int[]> sparsearray = new ArrayList<int[]>();

    	for (int i : first_array){
    		if(random.nextDouble() < density){
    			int[] b = new int[2];
    			b[0] = first_array[i];
    			b[1] = random.nextInt(1000000) + 1;
    			sparsearray.add(b);
    		}else{
    			continue;
    		}
    	}
    	System.out.println("Created Sparse Array of length " + length + " in a time of " + (System.nanoTime() - startTime) / 1000000000.0 + " seconds");
    	
    	return sparsearray;	
    }

// 4

    public static ArrayList<int[]> ds_converter(int[] densearray){
    	long startTime = System.nanoTime();
    	int length = densearray.length;
    	
    	int[] transition_array =  IntStream.range(0, length).toArray();
    	ArrayList<int[]> final_array = new ArrayList<int[]>();
    	
    	for (int i : transition_array){
    		if (densearray[i] != 0){
    			int[] x = new int[2];
    			x[0] = transition_array[i];
    			x[1] = densearray[i];
    			final_array.add(x);
    		}else{
    			continue;
    		}
    	}
    	
    	System.out.println("Converted to Sparse of length " +length+ " in a time of " + (System.nanoTime() - startTime) / 1000000000.0 + " seconds");
    	return final_array;
    }

// 5
    public static int[] sd_converter(int length, ArrayList<int[]> sparsearray){
    	//do cases, first value then rest of list(?)
    	//if the index of k + 1 [0] =/= k + 1
    	int[] final_array = new int[length];
    	int j = 0;
    	
    	long startTime = System.nanoTime();
    	
    	for(int k = 0; k < length - sparsearray.size(); k++){
    		int[] x = new int[2];
    		x[0] = 0;
    		x[1] = 0;
    		sparsearray.add(x);
    	}
    	
    	
    	for(int[] i : sparsearray){
    		if(i[0] == j){
    			final_array[j] = i[1];
    		}else{
    			final_array[j] = 0;
    		}
    		j++;
    	}
    	System.out.println("Converted to Dense of length " + length + " in a time of " + (System.nanoTime() - startTime) / 1000000000.0 + " seconds");
    	
    	return final_array;
    	
    }
// 6    
    public static String denseMax(int[] densearray){
    	int x = 0;
    	int index = 0;
    	long startTime = System.nanoTime();
    	
    	for(int i = 0; i < densearray.length; i++){
    		if(densearray[i] > x){
    			x = densearray[i];
    			index = i;
    			}
    		}
    	
    	
    	System.out.print("Max value (dense): " + x + ", index: " + index +", find time of " + (System.nanoTime() - startTime) / 1000000000.0 + " seconds");
    	return null;
    }
// 7    
   public static String sparseMax(ArrayList<int[]> sparsearray){
	   	int x = 0;
	   	int index = 0;
    	long startTime = System.nanoTime();
    	
	   	for(int[] i : sparsearray){
	   		if(i[1] > x){
	   			x = i[1];
	   			index = i[0];		
	   		}
	   	}
	   	
	   	System.out.print("Max value (sparse): " + x + ", index: " + index +", find time of " + (System.nanoTime() - startTime) / 1000000000.0 + " seconds \n");
    	return null;
    }
   
// 8
   
/* Testing/using NanoTime: 
 * 
 * using uniform settings of size 100 and density 0.25:
 * 
 * createDenseArray: Created Dense Array of length 100 in a time of 0.104639 milliseconds
 * createSparseArray: Created Sparse Array of length 100 in a time of 30.888367 milliseconds
 * ds_converter: Converted to Sparse of length 100 in a time of 30.857004 milliseconds
 * sd_converter: Converted to Dense of length 100 in a time of 0.055883 milliseconds
 * denseMax: Max value (dense): 931489, index: 7, find time of 0.007698 milliseconds
 * sparseMax: Max value (sparse): 994304, index: 60, find time of 0.030223 milliseconds
 * 
 * From this, we can observe that converting from sparse to dense is much more efficient than the reverse; and also that creating a dense 
 * array is much faster than creating a sparse array of the same size (100) and density (.25)/
 * 
 * Finding the max value is faster in the dense array by a small amount, maybe within a margin of error.
 * 
 * Out of idle curiosity, inputting a massive length, like, 10^8 with density .5, the longest result was:
 * Converted to Sparse of length 10000000 in a time of 1.229703117 seconds
 * 
 * Anything bigger seems to crash.
 * 
 */
    
    
}

//Sources used:
//<https://stackoverflow.com/questions/19758449/how-to-correctly-create-an-arraylist-of-arraylists>
//<https://docs.oracle.com/javase/tutorial/java/nutsandbolts/arrays.html>
//<Khttps://classes.cs.uoregon.edu/17F/cis212/examples/10-7b.java>
//<https://classes.cs.uoregon.edu/17F/cis212/examples/10-7a.java>
//<https://stackoverflow.com/questions/7346827/how-to-find-the-array-index-with-a-value>
//<https://stackoverflow.com/questions/7074402/how-to-insert-an-object-in-an-arraylist-at-a-specific-position>
//<https://stackoverflow.com/questions/924208/how-to-convert-nanoseconds-to-seconds-using-the-timeunit-enum>
//<https://stackoverflow.com/questions/9008532/how-to-find-index-position-of-an-element-in-a-list-when-contains-returns-true>
//<https://stackoverflow.com/questions/18814182/the-type-of-the-expression-must-be-an-array-type-but-it-resolved-to-arraylist-t>