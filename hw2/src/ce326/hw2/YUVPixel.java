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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author 99kar
 */

/**
 *
 * @author 99kar
 */
public class YUVPixel {
    private int pixel ;
    private short y;
    private short u ;
    private short v ; 
    
    
    
    public YUVPixel(short Y, short U, short V){
        this.pixel = V | (U << 8) | (Y << 16);
        y = Y;
        u = U;
        v = V;
    }
    
    
    public YUVPixel(YUVPixel pixel){
        this.pixel = pixel.pixel ;
        this.y = pixel.y;
        this.u = pixel.u;
        this.v = pixel.v;
    }
    
    
    public YUVPixel(RGBPixel pixel){
        int y = ( (  66 * pixel.getRed() + 129 * pixel.getGreen() +  25 * pixel.getBlue() + 128) >> 8) +  16 ;
        int u =  ( ( -38 * pixel.getRed() -  74 * pixel.getGreen() + 112 * pixel.getBlue() + 128) >> 8) + 128;
        int v = ( ( 112 * pixel.getRed() -  94 * pixel.getGreen() -  18 * pixel.getBlue() + 128) >> 8) + 128;
      
        this.y = (short)y;
        this.u = (short)u;
        this.v = (short)v ;
      
        this.pixel = v | (u << 8) | (y << 16); ;
    }
    
    
    public short getY(){
        return y;
    }
    public short getU(){
        return u;
    }
    public short getV(){
        return v;
    }
    
    public void setY(short Y){
        this.pixel = this.v| (this.u << 8) | (Y << 16);
        this.y = Y;
    }
    
    public void setU(short U){
        this.pixel =  this.v| (U << 8) | (this.y << 16);
        this.u = U;
    }
    
    public void setV(short V){
        this.pixel =  V| (this.u << 8) | ( this.y<< 16);
        this.v = V;
    }
    public String toString(){
        StringBuilder ret_str = new StringBuilder(this.y + " " + this.u + " " +this.v+ " " ) ;
        return (ret_str.toString());
    }
}
