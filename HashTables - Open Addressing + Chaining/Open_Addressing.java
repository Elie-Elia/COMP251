import java.io.*;
import java.util.*;

public class Open_Addressing {
     public int m; // number of SLOTS AVAILABLE
     public int A; // the default random number
     int w;
     int r;
     public int[] Table;

     protected Open_Addressing(int w, int seed, int A) {

         this.w = w;
         this.r = (int) (w-1)/2 +1;
         this.m = power2(r);
         if (A==-1){
            this.A = generateRandom((int) power2(w-1), (int) power2(w),seed);
         }
        else{
            this.A = A;
        }
         this.Table = new int[m];
         for (int i =0; i<m; i++) {
             Table[i] = -1;
         }
         
     }
     
                 /** Calculate 2^w*/
     public static int power2(int w) {
         return (int) Math.pow(2, w);
     }
     public static int generateRandom(int min, int max, int seed) {     
         Random generator = new Random(); 
                 if(seed>=0){
                    generator.setSeed(seed);
                 }
         int i = generator.nextInt(max-min-1);
         return i+min+1;
     }
        /**Implements the hash function g(k)*/
        public int probe(int key, int i) {
        	int hashcode = chain (key); //hash value obtained from function used in chaining class
        	return (hashcode + i) % m; //hash value returned from function g(k)
     }
     
     
     /**Inserts key k into hash table. Returns the number of collisions encountered*/
        public int insertKey(int key){
        	int i=0; //counter for the indexing of probe
        	int collisioncount =0; //counter for the number of collisions encountered
        	int hashvalue = probe(key, i); //hash value obtained
        	/**while the value in the particular hashcode index of the table is greater than or equal to zero
        	 * (it is not -1 to indicate empty or MIN_VALUE to detect a removal), keep probing for new hashcodes
        	 */
        	while (Table[hashvalue] >= 0 && i<m) { 
        		i++; //increment probing counter
        		hashvalue = probe(key,i); //calculate new hashvalue
        		collisioncount ++; //increment counter
        	}
        	/** As long as the hashvalue we've calculated is less than or equal to the number of slots available in the table
        	 * and the value found at the index in the particular index in the Table is less than 0
        	 * (ie. place of a removed element or an empty slot) we can insert the key
        	 */
        	if (hashvalue <=m && Table[hashvalue] <0) { 
        		Table[hashvalue] = key; //key can be inserted into this Table index
        	}
            return collisioncount; //number of collisions is returned
        }
        
        /**Sequentially inserts a list of keys into the HashTable. Outputs total number of collisions */
        public int insertKeyArray (int[] keyArray){
            int collision = 0;
            for (int key: keyArray) {
                collision += insertKey(key);
            }
            return collision;
        }
            
         /**Inserts key k into hash table. Returns the number of collisions encountered*/
        public int removeKey(int key){
        	int i=0; //counter for probing counter
        	int collisioncount =0; //counter for number of collisions encountered
        	int hashvalue = probe(key, i); //obtain hash value
        	while (Table[hashvalue] !=key && Table[hashvalue] != -1 && i<m) { 
        		/** Keep looping through table while the particular indexed slot in table is not the key, is not negative one
        		 * and the probing counter i is less than the number of slots in the table m
        		 */
        		i++; //increment probing counter
        		hashvalue = probe(key,i); //obtain new hash value
        		collisioncount ++; //increment collision count
        	}
        	if (hashvalue <= m && Table[hashvalue] == key) { //if key is found in table at a particular index of Table
        		Table[hashvalue] = Integer.MIN_VALUE; //integer.min value is used as a flag for the deleted item 
        	}else if (Table[hashvalue]== -1) {
        		collisioncount++;
        		//empty slot in table found which is marked as a collision
        	}
            return collisioncount; //number of collisions is returned 
        }
        private int chain (int key) { //h(k) implemented from Chaining Class 
        	int remainderval = key % power2(w);
        	int valbeforeshift = A * remainderval;
        	return valbeforeshift >> (w-r);
        }
}
