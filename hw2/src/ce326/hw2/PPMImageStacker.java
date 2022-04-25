/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ce326.hw2;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.*;
import java.io.* ;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List ;
import java.lang.Exception;
/**
 *
 * @author 99kar
 */
public class PPMImageStacker {
    public File[] files ;
    public  PPMImage image;
    public String content ;
    List <PPMImage>images = new LinkedList<PPMImage>();       //edw apothikevoume tis eikones pou diavazei h stacker 
    String []strs  ;
    StringBuilder stacked_img  = new StringBuilder();
    PPMImage r_img;
    
    public PPMImageStacker(java.io.File dir) throws FileNotFoundException {
        if(!dir.exists()){
            throw new FileNotFoundException("[ERROR] Directory"+ dir.getName()+" does not exist!");

        }
        if (!dir.isDirectory()){
            throw new FileNotFoundException("[ERROR] "+ dir.getName()+" is not a directory!");
        }
        this.files = dir.listFiles();
        strs  = new String[files.length];
        
    }
    
    public void stack() throws UnsupportedFileFormatException, FileNotFoundException{


        for (int size = 0 ; size <  files.length ; size++){
            images.add(new PPMImage(files[size]));

        }
        RGBImage rgb = new RGBImage(images.get(0).getWidth(), images.get(0).getHeight() , 255);
        r_img = new PPMImage(rgb);
        int j = 0;
        for (j = 0 ; j < files.length ; j++){
            strs[j] = new String(images.get(j).toString());

        }

       
        String [][]splitted_strs = new String[files.length][images.get(0).getHeight() * images.get(0).getWidth() * 3 + 4];
        for(int counter = 0; counter < files.length ; counter++){
            splitted_strs[counter]  = strs[counter].split(" ");
        }
        

        stacked_img.append("P3\n");
        stacked_img.append(String.valueOf(images.get(0).getWidth())  + " "+ String.valueOf(images.get(0).getHeight())+ " "+String.valueOf(images.get(0).getColorDepth()) +'\n');
        short red = 0 ;
        short green= 0 ;
        short blue = 0;
        int color = 0 ; 
        int row = 0 ;
        int col = 0;
        
        for(int counter = 4 ; counter < images.get(0).getHeight() * images.get(0).getWidth()*3 +4 ; counter++){
            int avg = 0 ;
            for(int i = 0 ; i < files.length ; i++){
              try{
                avg += Integer.parseInt(splitted_strs[i][counter]); 
              }
              catch(Exception e){
                  System.out.println(splitted_strs[i][counter]+ "error");
              }
              
            }
          
            avg = avg/files.length;
            if(row < images.get(0).getHeight()  && col < images.get(0).getWidth()){
                if (color == 0){
                    
                    red = (short)avg ;
                }

                else if (color == 1){
                    green  = (short)avg ;
                }
                else if (color  == 2){
                   
                    blue = (short)avg ;
                    color = -1 ;
                    this.r_img.image[row][col] =  new RGBPixel(red,green,blue);
                    if(col ==images.get(0).getWidth()-1){
                        col= 0;
                        row++;
                    }
                    else col++;

                }
            }

            color++ ;
            stacked_img.append(String.valueOf(avg)+ " ");
            if(counter% images.get(0).getWidth()*3 == 0 ){
                stacked_img.append('\n');
            }
        }
      
        return ;
   }

    public PPMImage getStackedImage() {
       
        return this.r_img;
       
    }
    
}
