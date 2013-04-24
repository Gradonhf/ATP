/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atp;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import java.awt.image.BufferedImage;
import processing.core.*;
import processing.video.*;
/**
 *
 * @author GradonHFaulkner
 */
public class videoSketch extends PApplet{
   

Capture cam; //Set up the camera
com.google.zxing.Reader reader = new com.google.zxing.qrcode.QRCodeReader();

int WIDTH = 640;
int HEIGHT = 480;

PImage cover;  //This will have the cover image
String lastISBNAcquired = "";  //This is the last ISBN we acquired

// Grabs the image file    

public void setup() {
  size(640, 480);
    PFont metaBold;
  // The font "Meta-Bold.vlw" must be located in the 
  // current sketch's "data" directory to load successfully
  metaBold = loadFont("SansSerif-48.vlw");
  textFont(metaBold, 36); 

  cam = new Capture(this, WIDTH, HEIGHT);
  cam.start();
}
 

public void draw() {
  if (cam.available() == true) {
    cam.read();
    pushMatrix();
      scale(-1,1);
      translate(-cam.width, 0);
      image(cam, 0, 0); 
    popMatrix();
    
    
    //image(cam, 0,0);
    try {
       // Now test to see if it has a QR code embedded in it
       LuminanceSource source = new BufferedImageLuminanceSource((BufferedImage)cam.getImage());
       BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));       
       Result result = reader.decode(bitmap); 
       //Once we get the results, we can do some display
       if (result.getText() != null) { 
          println(result.getText());
          ResultPoint[] points = result.getResultPoints();
          //Draw some ellipses on at the control points
          for (int i = 0; i < points.length; i++) {
          
            
            ellipse(points[i].getX(), points[i].getY(), 20,20);

            text(i, points[i].getX(), points[i].getY());
          }
          //Now fetch the book cover, if it is found
          if (!result.getText().equals(lastISBNAcquired)) {
             String url = "http://covers.oreilly.com/images/" + result.getText() + "/cat.gif";
             try {
                cover = loadImage(url,"gif");
                lastISBNAcquired = result.getText();
             } catch (Exception e) {
               cover = null;
             }
          }
          //Superimpose the cover on the image
          if (cover != null) {
            image(cover, points[1].getX(), points[1].getY());
          }
       }
    } catch (Exception e) {
//         println(e.toString()); 
    }
  }
}
}
