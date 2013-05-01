/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helloworldbarcode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Hashtable;



import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.WriterException;
//import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;

import com.google.zxing.EncodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.*;

import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import javax.imageio.ImageIO;

/**
 *
 * @author csembree
 */
public class HelloWorldBarCode {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, NotFoundException, ChecksumException, FormatException {

        int width = 440;
        int height = 48;


        BitMatrix bitMatrix;
        try {
            bitMatrix = new Code128Writer().encode("Hola, estoy Barcode text-o", BarcodeFormat.CODE_128, width, height, null);
            //MatrixToImageWriter.writeToStream(bitMatrix, "png", new FileOutputStream(new File("/home/kas/zxing_barcode.png")));
            MatrixToImageWriter.writeToStream(bitMatrix, "png", new FileOutputStream(new File("/Users/csembree/NetBeansProjects/HelloWorldBarCode/zxing_barcode.png")));
        } catch (WriterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }




        //-------------
        Charset charset = Charset.forName("UTF-8");
        CharsetEncoder encoder = charset.newEncoder();
        byte[] b = null;
        try {
            // Convert a string to UTF-8 bytes in a ByteBuffer
            //ByteBuffer bbuf = encoder.encode(CharBuffer.wrap("utf 8 characters - i used hebrew, but you should write some of your own language characters"));
            ByteBuffer bbuf = encoder.encode(CharBuffer.wrap("Hello! I am QR code text"));
            b = bbuf.array();
        } catch (CharacterCodingException e) {
            System.out.println(e.getMessage());
        }

        String data;
        try {
            data = new String(b, "UTF-8");
            // get a byte matrix for the data
            BitMatrix matrix = null;
            int h = 100;
            int w = 100;
            com.google.zxing.Writer writer = new MultiFormatWriter();
            try {
                Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>(2);
                hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
                matrix = writer.encode(data,
                        com.google.zxing.BarcodeFormat.QR_CODE, w, h, hints);
            } catch (com.google.zxing.WriterException e) {
                System.out.println(e.getMessage());
            }

            // change this path to match yours (this is my mac home folder, you can use: c:\\qr_png.png if you are on windows)
            String filePath = "/Users/csembree/NetBeansProjects/HelloWorldBarCode/qr_pn2.png";
            File file = new File(filePath);
            try {
                MatrixToImageWriter.writeToFile(matrix, "PNG", file);
                System.out.println("printing to " + file.getAbsolutePath());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } catch (UnsupportedEncodingException e) {
            System.out.println(e.getMessage());
        }


        //BinaryBitmap binaryBitmap=new BinaryBitmap(new HybridBinarizer(new RGBLuminanceSource(bitmap)));

        Result result = null;
        BinaryBitmap binaryBitmap;

        try {

            binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(ImageIO.read(new FileInputStream("/Users/csembree/NetBeansProjects/HelloWorldBarCode/qr_pn2.png")))));
            result = new MultiFormatReader().decode(binaryBitmap);
            System.out.println("QR Code DECODED: " + result.getText());

        } catch (Exception ex) {
            ex.printStackTrace();
        }


        InputStream barCodeInputStream = new FileInputStream("/Users/csembree/NetBeansProjects/HelloWorldBarCode/zxing_barcode.png");
        BufferedImage barCodeBufferedImage = ImageIO.read(barCodeInputStream);

        LuminanceSource source = new BufferedImageLuminanceSource(barCodeBufferedImage);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Reader reader = new MultiFormatReader();
        result = reader.decode(bitmap);

        System.out.println("Barcode DECODED: " + result.getText());
    }
}
