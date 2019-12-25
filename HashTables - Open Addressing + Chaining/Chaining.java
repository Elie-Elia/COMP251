import java.io.*;
import java.util.*;

public class Chaining {
    
     public int m; // number of SLOTS 
     public int A; // the default random number
     int w;
     int r;
     public ArrayList<ArrayList<Integer>>  Table;

    // if A==-1, then a random A is generated. else, input A is used.
    protected Chaining(int w, int seed, int A){
         this.w = w;
         this.r = (int) (w-1)/2 +1;
         this.m = power2(r);
         this.Table = new ArrayList<ArrayList<Integer>>(m);
         for (int i=0; i<m; i++) {
             Table.add(new ArrayList<Integer>());
         }
         if (A==-1){
         this.A = generateRandom((int) power2(w-1), (int) power2(w),seed);
        }
        else{
            this.A = A;
        }

     }
    /** Calculate 2^w*/
     public static int power2(int w) {
         return (int) Math.pow(2, w);
     }
     //generate a random number in a range (for A)
     public static int generateRandom(int min, int max, int seed) {     
         Random generator = new Random(); 
                 if(seed>=0){
                    generator.setSeed(seed);
                 }
         int i = generator.nextInt(max-min-1);
         return i+min+1;     
    }




    /**Implements the hash function h(k)*/
    public int chain (int key) {
    	int remainderval = (key*A) % power2(w); //implementation of the hash function h(k)
    	return remainderval >> (w-r); //returns the hash value from h(k)
    }
        
    
    /**Inserts key k into hash table. Returns the number of collisions encountered*/
    public int insertKey(int key){
    	int hashcode = chain (key); //hash value obtained from chaining function
    	int collisions=0; //a counter for the number of collisions is initialized at 0
    	if (Table.get(hashcode).size() == 0) { //if the arraylist found at index (hashcode) is of length zero
    		collisions = 0; //collisions encountered are zero
    		Table.get(hashcode).add(key); //key is added to the arraylist at the specific index	
    	}else {
    		collisions = Table.get(hashcode).size(); //otherwise there are values already in arraylist
    		//size of the arraylist at the particular table index is the number of collisions
    		Table.get(hashcode).add(0,key); //key is added to the start of the arraylist
    	}
        return collisions; //number of collisions is returned

    }

    
    
    /**Sequentially inserts a list of keys into the HashTable. Outputs total number of collisions */
    public int insertKeyArray (int[] keyArray){
        int collision = 0; 
        for (int key: keyArray) {
            collision += insertKey(key);
        }
        return collision;
    }


}