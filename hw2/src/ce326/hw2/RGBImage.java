/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ce326.hw2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 99kar
 */

public class RGBImage implements Image {
    public static final int MAX_COLORDEPTH = 255;
    private int width ; 
    private int height ; 
    private int colordepth ;
    RGBPixel [][]image ;
    
    
    public RGBImage(int width, int height, int colordepth){
        this.width = width ;
        this.height = height;
        this.colordepth = colordepth ;
        this.image = new RGBPixel[height][width];  
    }

    public RGBImage(RGBImage copyImg){
        
       
        this.width = copyImg.width;
        this.height = copyImg.height;
        this.colordepth = copyImg.colordepth;
        this.image = copyImg.image;
    }

    RGBImage(YUVImage yuvImg) {
       
      
       this.width = yuvImg.width ;
       this.height = yuvImg.height;
       this.image = new RGBPixel[height][width];
       for (int row = 0 ; row < height ; row++){
       for(int col = 0 ; col < width ; col++){
            this.image[row][col] = new RGBPixel(yuvImg.image[row][col]);
            }
        }  
    }

    public int getWidth(){
        return this.width ;
    }
    
    public int getHeight(){
        return this.height ;
    }
    
    public int getColorDepth(){
        return this.colordepth ;
    }
        

     public void  settWidth(int width){
        this.width = width ;
    }
    
    public void setHeight(int height){
        this.height = height;
    }
    
    public void setColorDepth(int colordepth){
        this.colordepth = colordepth;
    }
   
    public RGBPixel getPixel(int row, int col){
        return this.image[row][col];
    }
    
    public void setPixel(int row, int col, RGBPixel pixel ){
        this.image[row][col].setRGB(pixel.getRGB()) ;
    }
    
    @Override
    public void grayscale() {
        int row ;
        int col ;
        short gray;
        for (row=0; row < this.height ; row++){
            for(col = 0 ; col < this.width ; col++){
                gray = (short)(this.image[row][col].getRed() * 0.3 + this.image[row][col].getGreen() * 0.59 + this.image[row][col].getBlue() * 0.11 );
                this.image[row][col].setRGB(gray,gray,gray);
               
            }
        }
            
    }

    @Override
    public void doublesize() {
        
        RGBPixel [][]new_image ;
        int old_width= this.width ;
        int old_height = this.height ;
        this.width = 2 * this.width; 
        this.height  = 2 * this.height; 
        int row ;
        int col ; 
        
        new_image = new RGBPixel[height][width];
        for (row=0; row < old_height ; row++){
             for(col = 0 ; col < old_width ; col++){
                              
                 new_image[2* row][2*col] = new RGBPixel(this.image[row][col]);
                 new_image[2* row+1][2*col] = new RGBPixel(this.image[row][col]);
                 new_image[2* row][2*col+1] = new RGBPixel(this.image[row][col]);
                 new_image[2* row+1][2*col+1] = new RGBPixel(this.image[row][col]);
                 
             }
         }
        
        this.image = new_image;
    }

    @Override
    public void halfsize() {
        
        
        RGBPixel [][]new_image ;
        
        this.width = this.width/2; 
        this.height  = this.height/2; 
        int row ;
        int col ; 
        
        new_image = new RGBPixel[height][width];
        for (row=0; row < this.height ; row++){
             for(col = 0 ; col < this.width ; col++){
                 RGBPixel pixel = new RGBPixel((short)((this.image[2*row][2*col].getRed() + this.image[2*row+1][2*col].getRed() + this.image[2*row][2*col+1].getRed()+ this.image[2*row+1][2*col+1].getRed())/4),    (short)((this.image[2*row][2*col].getGreen() + this.image[2*row+1][2*col].getGreen() + this.image[2*row][2*col+1].getGreen()+ this.image[2*row+1][2*col+1].getGreen())/4), (short)((this.image[2*row][2*col].getBlue() + this.image[2*row+1][2*col].getBlue() + this.image[2*row][2*col+1].getBlue()+ this.image[2*row+1][2*col+1].getBlue())/4));
                 
                 setPixel(row, col, pixel);
                              
                 new_image[row][col] = new RGBPixel(pixel);
  
             }
         }
        this.image = new_image;
        
    }

    @Override
    public void rotateClockwise() {
        
        RGBPixel [][]new_image ;
        new_image = new RGBPixel[width][height];
        
        int old_height = this.height ;
        int old_width = this.width;
        this.height = this.width ;
        this.width = old_height ;
        
        for (int row = 0 ; row < this.height ; row++){
            for(int col = 0 ; col < this. width ; col++){
                new_image[row][col] = this.image[width-col-1][row];
            }
        }
        this.image = new_image;
        
    }
    

    
}
