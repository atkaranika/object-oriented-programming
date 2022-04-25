/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw1;

/**
 *
 * @author 99kar
 */
public class node {
    char operator ;
    node left ;
    node right ;
    String cur_float;
    public node (char op , node left , node right){
        this.left = left;
        this.right = right;
        this.operator = op ;
    }
    
    public node (){
    }
}
