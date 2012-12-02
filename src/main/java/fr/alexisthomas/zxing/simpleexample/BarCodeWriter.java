package fr.alexisthomas.zxing.simpleexample;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

/**
 * Writer for Barcode.
 */
public class BarCodeWriter {

    /**
     * Encode a text into a barcode, with QRCode format.
     *
     * @param text Text to encode
     * @param size Same value for width and height
     * @param filePath Path as string in which to store generated file
     * @return Generated QRCode file
     * @throws WriterException error writing QRCode
     * @throws IOException error writing to file
     */
    public static File generateQRCodeFromText(String text, int size, String filePath) throws WriterException, IOException {
        String imageFormat = "png";
        String fileName = "qrcode_new_" + Calendar.getInstance().getTime().getTime() + ".png";
        File fileFolder = new File(filePath);
        if (!fileFolder.exists()) {
            fileFolder.mkdirs();
        }
        File generatedFile = new File(filePath + fileName);

        BitMatrix bitMatrix = new QRCodeWriter().encode(text, BarcodeFormat.QR_CODE, size, size);
        MatrixToImageWriter.writeToStream(bitMatrix, imageFormat, new FileOutputStream(generatedFile));

        return generatedFile;
    }

}
