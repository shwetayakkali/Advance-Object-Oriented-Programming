/* 
 * Subsets.java 
 * 
 * Version: 
 *     1.1 
 * 
 */

/**
 * This program produces all combinations of n people who are attending a party
 *
 * @author      Shweta Yakkali
 * @author      Aishwarya Desai
 */

import java.io.*;
import java.util.*;


/** 
 * Class Subsets creates a function called subset  to which string values and index to it are passed, when
 * called this function calculates all the possible subsets of the people going to the party.

 * @author      Shweta Yakkali 
 * @author      Aishwarya Desai 
 */


public class Subsets{
/**
 * The main method asks the user to input the number of people who will be going 
 * to the party after which an object 's' is created and the subset method is called.
 *
 */


public static String temp="";

     public static void main(String []args)throws IOException{

     Scanner scr = new Scanner(System.in);
     System.out.print("Enter the number of people going to the party : ");
     int n=scr.nextInt();                                           //n is the number of the people the user inputs will be going to the party
     String str=""; 

     for(int i=0;i<n;i++){
        
         str=str+(i+1);                                            //number of people going in string format
         }

         System.out.print("{}, ");                                //to print the null set 
        
    	Subsets s = new Subsets();                               //this line will create an object of s

        for(int i = 0; i<str.length();i++){                      //traverse through all characters in string str

            s.subset("",str,i);                                 // calls the function subset()
            
        }

        if(n>0)
        System.out.print(temp.substring(0,(temp.length()-2))); //printing the sets
        
       scr.close();                                            //scanner closed
     }


/**

  * The subset method takes as parameters the null string, the str string and the running value of index
  * to calculate the possible values of the subsets.

  * @param       substr        null string initially and then the appended string
  * @param       str           the string containing the people
  * @param       index         the current index value

  */
     
  
     void subset(String substr,String str,int index){

         String s1 = ""+str.charAt(index);                   //creating a variable on each stack
         s1 = substr+s1;                                     //append the subset so far
         
         temp=temp+("{"+s1+"}"+", ");                        //print all the possible subsets of n people

         for(int i=index+1;i<str.length();i++)

           subset(s1,str,i);                                 //call recursively
     }
     
}