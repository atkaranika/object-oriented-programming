/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw1;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.*;
/**
 *
 * @author 99kar
 */
// 1.this programm accepts as input an arithmetic expression.                                                                                                  // 
// 2.According to the user's input it perforsm a list of                                                                                                       //
// 3.operations                                                                                                                                                //
// examples 5 + (((3.3 + 6.6) * 9.2 ) + 12.546) * 2.323 + ( ( ( 33.3 + 2342.1 ) * 55.555 ) - 10000.009 )+11.334 * 2.3^ 3.5                                     //
// 1+1+1/2+1/( 2*3 )+1/( 2*3*4 )+1/( 2*3*4* 5 ) +1 /(2*3*4*5 *6 ) + 1 / (2*3*4 *5*6 *7)+ 1/(2* 3*4 *5* 6*7*8) +1 /(2*3*4*5 *6*7*8*9 )+1/(2*3*4*5*6*7*8*9* 10 ) //
// (1+1/1000000000)^1000000000                                                                                                                                 //
// 2.3 ^ 3.5 * 5 + ((((3.3 + 2.8) * 19.2 ) - 100.546) / 2.323 + ( ( ( 33.3 +2342.1 ) * 55.555 ) - 10000.009 )) * 8.567 / 3.451                                 //
// 1.0 + 2.0 * 3.0 / ( 6.0*6.0 + 5.0*44.0) ^ 0.25                                                                                                              //
// 2.33 * 5.66 / 1.12 / 3.28 / 2.0                                                                                                                             //
// expreσsions with extension operator                                                                                                                         //
// 2.3 ^ 3.5 * 5 + (((3.3 + 2.8) * 19.2 ) - 100.546) \*3 / 2.323                                                                                               //
// 5 + ((((2.8 * (3.3 + 2.8) * 19.2 ) - 10.54) \*2 - 10.54) / 2.323) \-3 + 8                                                                                   //



public class ArithmeticCalculator 
    {
        public static void main(String []args)
            {
                java.util.Scanner sc = new java.util.Scanner(System.in);
                String line = sc.nextLine();
                System.out.print("Expression:" );
                //At first we call the constructor. //
                Tree test = new Tree(line);     
                String str = sc.nextLine();       
                String[] splitted = str.split(" ");

                for(String iter : splitted){
                     if(iter.equals("-d"))                              //the dot file will be printed on user's screen.//
                        {
                            System.out.println(test.toDotString());
                        }
                     if(iter.equals("-s"))                              //the equivalent expression will be printed with posix form. //
                        {
                            System.out.println("Postfix: " + test.toString());
                        }
                     if(iter.equals("-c"))                              //the result of the arithmetic operation will be printed. //
                        {
                            System.out.println("Result: " + test.calculate());
                        }
                }
      }
    }
    
