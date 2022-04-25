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
public class RGBPixel {
    int pixel ;
    private short red;
    private short green ;
    private short blue ; 
    
    public RGBPixel(short red, short green, short blue){
        this.pixel = blue | (green << 8) | (red << 16);
        this.red = red ;
        this.blue = blue ;
        this.green = green ;
        
    }
    
    public RGBPixel(RGBPixel pixel){
        this.pixel = pixel.pixel;
        this.red = pixel.red ;
        this.blue = pixel.blue ;
        this.green = pixel.green ;
        
    }
    
    public RGBPixel(YUVPixel pixel){
        
        short C = (short)(pixel.getY() - 16);
        short D = (short)(pixel.getU() - 128);
        short E = (short)(pixel.getV() - 128);
        this.red = (short)clip((short) (( 298 * C + 409 * E + 128) >> 8));
        this.green = (short)clip((short) (( 298 * C - 100 * D - 208 * E + 128) >> 8));
        this.blue = (short)clip((short) (( 298 * C + 516 * D + 128) >> 8));

        this.pixel =blue | (green << 8) | (red << 16) ;
    }

    public short getRed(){
        return this.red ;
    }
    
    public short getGreen(){
        return this.green ;
    }
    
    public short getBlue(){
        return this.blue ;
    }
    
    public int getRGB(){
        return this.pixel ;
    }
    
    public void setRed(short red){
        this.red = red ;
        this.pixel = this.blue | (this.green << 8) | (red << 16);
    }
    
    public void setGreen(short green){
        this.green = green;
        this.pixel = this.blue | (green << 8) | (this.red << 16);
    }
    
    public void setBlue(short blue){
        this.blue = blue;
        this.pixel = blue | (this.green << 8) | (this.red << 16);
    }
    
    
    void setRGB(int value){
        this.pixel = value ;
        this.red = (short)(value & (0x00ff0000)); 
        this.green = (short)(value & (0x0000ff00));
        this.blue = (short)(value & (0x000000ff));
    }
    
    final void setRGB(short red, short green, short blue){
        this.red = red ;
        this.green = green;
        this.blue = blue ;
        this.pixel = this.pixel = blue | (green << 8) | (red << 16);
    }
    
    public String toString(){
        StringBuilder ret_str = new StringBuilder(this.red + " " + this.green + " " +this.blue+ " " ) ;
        return (ret_str.toString());
    }
    
    public int clip (short num){
        if (num > 255)
            return 255;
        else if (num < 0)
            return 0 ;
        else return num ;
    }
    
    
}
