/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ce326.hw2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
    
/**
 *
 * @author 99kar
 */
public class YUVImage {
  
    int width;
    int height ;
    YUVPixel [][] image ;
    
    
    public YUVImage(int width, int height){
        this.width = width ;
        this.height = height;
        
        this.image = new YUVPixel[width][height];
        for (int row = 0 ; row < height ; row++){
            for(int col = 0 ; col < width ; col++){
                this.image[row][col] = new YUVPixel((short)16,(short)128,(short)128);
            }
        }
    }
    public YUVImage(YUVImage copyImg){
        
        this.width = copyImg.width ;
        this.height = copyImg.height;
         this.image = copyImg.image ;
    }
     
    
   public YUVImage(RGBImage RGBImg){
       
      
       this.width = RGBImg.getWidth() ;
       this.height = RGBImg.getHeight();
       this.image = new YUVPixel[this.height][this.width];
       for (int row = 0 ; row < this.height ; row++){
            for(int col = 0 ; col < this.width ; col++){
                this.image[row][col] = new YUVPixel(RGBImg.image[row][col]);
            }
        }  
   }
   
   public YUVImage(java.io.File file) throws UnsupportedFileFormatException, FileNotFoundException{
      
        String type ;
        
        String filename = file.getName();
        if(!file.exists()){
            throw new FileNotFoundException("file not found");

        }
        
        try{
            Scanner sc = new Scanner(file); 
            type = sc.next();
            if (!type.equals("YUV3")){
                throw new UnsupportedFileFormatException();
            }
            
            this.width = sc.nextInt();
            this.height = sc.nextInt();
            this.image = new YUVPixel[height][width];
            
            
            for (int row = 0; row < height ; row++){
                for (int col = 0 ; col < width ; col++){
                    this.image[row][col] = new YUVPixel((short)sc.nextInt(),(short)sc.nextInt(),(short)sc.nextInt());
                   
                }
            }
            sc.close();
        } catch(InputMismatchException ex) {
          System.out.println("Invalid file contents!");
        } 

   }
    
 public String toString(){
        System.out.println("to string yuv image");
        StringBuilder return_string  = new StringBuilder();
        return_string.append("YUV3 \n");
        return_string.append(this.width + " " + this.height+ " ");
       
        for(int row = 0 ; row < this.height ; row++){
            for(int col = 0 ; col < this.width ;col++){
                return_string.append(this.image[row][col].toString());
            }
            return_string.append("\n");
        }
        System.out.println("return to stringpp image");

        return return_string.toString() ;
   }
   
   
   public void toFile(java.io.File file){
       System.out.println("to file yuv  image");
        if (file.exists()){
            file.delete();
           
            
        }
         try {
             file.createNewFile();
         } catch (IOException ex) {
             Logger.getLogger(PPMImage.class.getName()).log(Level.SEVERE, null, ex);
         }
         
         
        try{
            FileWriter wr = new FileWriter(file.getName());
            wr.write("YUV3\n");
            wr.write(String.valueOf(this.width)+ " ");
            wr.write(String.valueOf((this.height))+ " " + "\n");
           
            for (int row = 0; row < height ; row++){
                for (int col = 0 ; col < width ; col++){
                    wr.write(this.image[row][col].toString());
    
                }
                wr.write("\n") ;
            }
            wr.close();
            System.out.println("to file yuv  image");
        }
        catch(IOException ex) {
            System.out.println("error");
        }
   }
    void equalize() {
        Histogram hist = new Histogram(this);
        this.image = hist.equalize();
    }
       
}
