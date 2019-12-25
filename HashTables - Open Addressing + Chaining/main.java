import java.io.*;
import java.util.*;


public class main {     

     
    public static void main(String[] args) {
    	
    	int[] listX = new int[]{86, 85, 6, 97, 19, 66, 26, 14, 15, 49, 75, 64, 35, 54, 31, 9, 82, 29, 81, 13};
;
        int listXA = 554;
 
        int listYA = 1018;
        int[] listY = new int[]{52, 71, 34, 95, 68, 25, 44, 38, 47, 77, 92, 84, 94, 12, 61, 9, 89, 56, 28, 75};
 
        Chaining chainX = new Chaining(10,0, listXA);
        int collisionsX = chainX.insertKeyArray(listX);
        Chaining chainY = new Chaining(10, 0, listYA);
        int collisionsY = chainY.insertKeyArray(listY);
        System.out.println("Chaining x" + collisionsX);
        System.out.println("Chaining y" + collisionsY);
        Open_Addressing openX = new Open_Addressing(10,0,listXA);
        int collisionsOpenX = openX.insertKeyArray(listX);
        Open_Addressing openY = new Open_Addressing(10,0,listYA);
        int collisionsOpenY = openY.insertKeyArray(listY);
        System.out.println("Open x" + collisionsOpenX);
        System.out.println("Open y" + collisionsOpenY);
        
        
    }
}