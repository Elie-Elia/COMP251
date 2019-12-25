import java.util.*;
import java.io.*;

public class Multiply{

    private static int randomInt(int size) {
        Random rand = new Random();
        int maxval = (1 << size) - 1;
        return rand.nextInt(maxval + 1);
    }
    
    public static int[] naive(int size, int x, int y) {

    	int power = (int) Math.pow(2.0, size);
        x %= power;
        y %= power;
        if(size == 1) {
            int[] result = new int[2];
            result[0] = x*y;
            result[1] = 1;
            return result;
        } else {
        	int[] result = new int[2];
        	size = size/2 + size%2;
            int valA  = x / (int) Math.pow(2.0, size);
            int valB = x % (int) Math.pow(2.0, size);
            int valC = y / (int) Math.pow(2.0, size);
            int valD = y % (int) Math.pow(2.0, size);
            int[] tempE = naive(size, valA, valC);
            int[] tempF = naive(size, valB, valD);
            int[] tempG = naive(size, valB, valC);
            int[] tempH = naive(size, valA, valD);
            result[1] += 3*size + tempE[1] + tempF[1] + tempG[1] + tempH[1];
            result[0] = ((int) Math.pow(2.0, size*2))*tempE[0]+((int) Math.pow(2.0, size))*(tempG[0]+tempH[0])+tempF[0];
            return result;
        }
    }

    public static int[] karatsuba(int size, int x, int y) {
        
    	int pow = (int) Math.pow(2.0, size);
        x %= pow;
        y %= pow;
        if(size == 1) {
            int[] result = new int[2];
            result[0] = x*y;
            result[1] = 1;
            return result;
        } else {
            int[] result = new int[2];
            size = size/2 + size%2;
            int valA  = x / ((int)  Math.pow(2.0, size));
            int valB = x % ((int)  Math.pow(2.0, size));
            int valC = y / ((int)  Math.pow(2.0, size));
            int valD = y % ((int)  Math.pow(2.0, size));
            int[] tempE = karatsuba(size, valA, valC);
            int[] tempF = karatsuba(size, valB, valD);
            int[] tempG = karatsuba(size, valA-valB, valC-valD);
            result[1] += tempE[1] +tempF[1] + tempG[1] + 6*size;
            result[0] = ((int) Math.pow(2.0, size*2))*tempE[0]+((int)  Math.pow(2.0, size))*(tempE[0]+tempF[0]-tempG[0])+tempF[0];
            return result;
        }
        
    }
    
    public static void main(String[] args){

        try{
            int maxRound = 20;
            int maxIntBitSize = 16;
            for (int size=1; size<=maxIntBitSize; size++) {
                int sumOpNaive = 0;
                int sumOpKaratsuba = 0;
                for (int round=0; round<maxRound; round++) {
                    int x = randomInt(size);
                    int y = randomInt(size);
                    int[] resNaive = naive(size,x,y);
                    int[] resKaratsuba = karatsuba(size,x,y);
            
                    if (resNaive[0] != resKaratsuba[0]) {
                        throw new Exception("Return values do not match! (x=" + x + "; y=" + y + "; Naive=" + resNaive[0] + "; Karatsuba=" + resKaratsuba[0] + ")");
                    }
                    
                    if (resNaive[0] != (x*y)) {
                        int myproduct = x*y;
                        throw new Exception("Evaluation is wrong! (x=" + x + "; y=" + y + "; Your result=" + resNaive[0] + "; True value=" + myproduct + ")");
                    }
                    
                    sumOpNaive += resNaive[1];
                    sumOpKaratsuba += resKaratsuba[1];
                }
                int avgOpNaive = sumOpNaive / maxRound;
                int avgOpKaratsuba = sumOpKaratsuba / maxRound;
                System.out.println(size + "," + avgOpNaive + "," + avgOpKaratsuba);
            }
        }
        catch (Exception e){
            System.out.println(e);
        }

   } 
}
