/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ce326.hw2;

/**
 *
 * @author 99kar
 */
public class UnsupportedFileFormatException extends java.lang.Exception {
    static final long serialVersionUID = -4567891456L;
    public UnsupportedFileFormatException(){
        
    }
    public UnsupportedFileFormatException(String msg){
        System.out.println(msg);
       
    }

    
}
