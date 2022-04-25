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
import java.io.Reader ;
import java.io.Writer ;
import java.io.*;
import java.io.File;
import java.nio.*;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author 99kar
 */
public class PPMImage extends RGBImage {
    
     
    //private RGBPixel [][]image ;

    public PPMImage(java.io.File file) throws UnsupportedFileFormatException, FileNotFoundException{
        
        super(0,0,0);
        String type ;
        System.out.println("1ppmimage");
       
        String filename = file.getName(); 
        System.out.println("2");
       
        try{
            Scanner sc = new Scanner(file); 
          
            type = sc.next();
            if (!type.equals("P3")){
                throw new UnsupportedFileFormatException();
            }
            
            this.settWidth(sc.nextInt());
            this.setHeight(sc.nextInt());
            this.setColorDepth(sc.nextInt());
            
            this.image = new RGBPixel[this.getHeight()][this.getWidth()];
            
            for (int row = 0; row < this.getHeight() ; row++){
                for (int col = 0 ; col < this.getWidth() ; col++){
                    this.image[row][col] = new RGBPixel((short)sc.nextInt(),(short)sc.nextInt(),(short)sc.nextInt());
                    
                }
            }
        } catch(InputMismatchException ex) {
          System.out.println("Invalid file contents!");
        } 
         System.out.println("2ppmigae");

    }
    
    public PPMImage(RGBImage img){
        
        super(img) ;
       
    }
    
    public PPMImage(YUVImage img){
        super(img);
    }
            
   
    public  String toString() {
        
      System.out.println("tostring");
        StringBuilder return_string  = new StringBuilder();
        return_string.append("p3 \n");
        return_string.append(this.getWidth() + " " + this.getHeight()+ " ");
        return_string.append(this. getColorDepth()+ " ");
        
        for(int row = 0 ; row < this.getHeight() ; row++){
            for(int col = 0 ; col < this.getWidth() ;col++){
                return_string.append(this.image[row][col].toString());
            }
            return_string.append("\n");
        }

        System.out.println("to stringpp image");
        return return_string.toString() ;


    }
    
    public void toFile(java.io.File file) {
         System.out.println("tofile");
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
            wr.write("P3\n");
           
            wr.write(String.valueOf(this.getWidth())+ " ");
            wr.write(String.valueOf(this.getHeight())+ " ");
            wr.write(String.valueOf(this.getColorDepth()) + "\n");
            for (int row = 0; row < this.getHeight() ; row++){
                for (int col = 0 ; col < this.getWidth() ; col++){
                    
                    wr.write(this.image[row][col].toString() );
                }
                wr.write("\n") ;
            }
            System.out.println("to file ppm iimage");
            wr.close();
        }
        catch(IOException ex) {
            System.out.println("error");
        }
        
    }
}
