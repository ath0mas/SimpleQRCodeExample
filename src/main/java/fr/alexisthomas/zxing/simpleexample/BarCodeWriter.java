package fr.alexisthomas.zxing.simpleexample;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class BarCodeWriter {

    /**
     * Encode a text into a barcode, with QRCode format.
     *
     * @param text Text to encode
     * @throws WriterException
     * @throws IOException
     */
    public static void generateQRCodeFromText(String text) throws WriterException, IOException {
        int width = 300;
        int height = 300;

        String imageFormat = "png";
        String fileName = "qrcode_new.png";

        BitMatrix bitMatrix = new QRCodeWriter().encode(text, BarcodeFormat.QR_CODE, width, height);
        MatrixToImageWriter.writeToStream(bitMatrix, imageFormat, new FileOutputStream(new File(fileName)));
    }
}
