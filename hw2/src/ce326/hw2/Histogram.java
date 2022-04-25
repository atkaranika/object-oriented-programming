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



import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;

/**
 *
 * @author 99kar
 */
public class Histogram {
    float [] hist_array = new float[256];
    YUVImage img ;
    float []pdf = new float[256] ;
    float []prob = new float[256];
    
    public Histogram(YUVImage img){
        this.img = img;
        hist_array = new float[256];
        int index ;
        for (int range = 0; range < hist_array.length; range++) {
            hist_array[range] = 0 ;
        }
        
        for(int row = 0 ; row < img.height; row++){
            for(int col = 0 ; col < img.width ; col++){
                
                index = img.image[row][col].getY() ;
              
                hist_array[index]++;
                
            }
        }
       
    }
    
    public String toString(){
        
        StringBuilder whole_file  = new StringBuilder();
             
        for (int range = 0; range < hist_array.length; range++) {
             whole_file.append(range);
             whole_file.append(multipleTimes((int)hist_array[range]/1000,'#') );
             whole_file.append(multipleTimes((int)hist_array[range]/100,'$') );
             whole_file.append(multipleTimes((int)hist_array[range]/10,'@') );
             whole_file.append(multipleTimes((int)hist_array[range],'*') );
             whole_file.append('\n' );
        }
        return whole_file.toString();
    
    }
    


    private String multipleTimes(int num, char c) {
        StringBuilder builder = new StringBuilder();
        for (int j = 0; j < num; j++) {
            builder.append(c);
        }
        return builder.toString();
    }
    
    
     public void toFile(java.io.File file){

        if (file.exists()){
            file.delete();   
        }
         try {
             file.createNewFile();
         } catch (IOException ex) {

         }
         
        try{
            FileWriter wr = new FileWriter(file.getName());
            wr.write(this.toString());
            wr.close();
        }
        catch(IOException ex) {
            System.out.println("error");
        }
   }
     
     
     public YUVPixel[][] equalize(){
         //ipologismos piknotitas pithanonitas
   
         for (int range = 0 ; range < prob.length; range++){
             pdf[range] = 0 ;
        }
        for (int range = 0 ; range < hist_array.length; range++){
            prob[range] = this.hist_array[range]/(img.width * img.height) ;
            for(int i = 0; i <= range; i++){
                 pdf[range] += prob[i]; 
            }
        }
         
        for (int range = 0 ; range < hist_array.length; range++){
            pdf[range] = pdf[range] *235;
        }
         
         for(int row = 0 ; row < img.height ; row++){
            for(int col = 0 ; col < img.width ; col++){
               this.img.image[row][col].setY((short)pdf[this.img.image[row][col].getY()]) ;
            }
        }
         
         return this.img.image;
     }
     
     public short getEqualizedLuminocity(int luminocity){
         return (short) pdf[luminocity];
     }
    
}
