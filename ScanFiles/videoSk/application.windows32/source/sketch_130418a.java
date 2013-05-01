import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.video.*; 
import com.google.zxing.*; 
import java.awt.image.BufferedImage; 

import com.google.zxing.common.detector.*; 
import com.google.zxing.oned.rss.*; 
import com.google.zxing.oned.rss.expanded.decoders.*; 
import com.google.zxing.*; 
import com.google.zxing.client.result.*; 
import com.google.zxing.pdf417.detector.*; 
import com.google.zxing.qrcode.detector.*; 
import com.google.zxing.maxicode.*; 
import com.google.zxing.pdf417.decoder.ec.*; 
import com.google.zxing.pdf417.*; 
import com.google.zxing.datamatrix.*; 
import com.google.zxing.aztec.*; 
import com.google.zxing.pdf417.decoder.*; 
import com.google.zxing.maxicode.decoder.*; 
import com.google.zxing.oned.rss.expanded.*; 
import com.google.zxing.oned.*; 
import com.google.zxing.common.*; 
import com.google.zxing.pdf417.encoder.*; 
import com.google.zxing.common.reedsolomon.*; 
import com.google.zxing.multi.qrcode.*; 
import com.google.zxing.multi.*; 
import com.google.zxing.qrcode.*; 
import com.google.zxing.aztec.decoder.*; 
import com.google.zxing.qrcode.decoder.*; 
import com.google.zxing.aztec.detector.*; 
import com.google.zxing.client.j2se.*; 
import com.google.zxing.multi.qrcode.detector.*; 
import com.google.zxing.qrcode.encoder.*; 
import com.google.zxing.datamatrix.decoder.*; 
import com.google.zxing.datamatrix.detector.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class sketch_130418a extends PApplet {





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
           // fill(#ff8c00);
            ellipse(points[i].getX(), points[i].getY(), 20,20);
           // fill(#ff0000);
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
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "sketch_130418a" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
