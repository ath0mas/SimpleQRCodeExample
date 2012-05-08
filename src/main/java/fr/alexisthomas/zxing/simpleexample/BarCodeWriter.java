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

public class BarCodeWriter {

    /**
     * Encode a text into a barcode, with QRCode format.
     *
     * @param text     Text to encode
     * @param filePath
     * @return
     * @throws WriterException
     * @throws IOException
     */
    public static File generateQRCodeFromText(String text, int taille, String filePath) throws WriterException, IOException {
        String imageFormat = "png";
        String fileName = "qrcode_new_" + Calendar.getInstance().getTime().getTime() + ".png";
        File fileFolder = new File(filePath);
        if (!fileFolder.exists()) {
            fileFolder.mkdirs();
        }
        File generatedFile = new File(filePath + fileName);

        BitMatrix bitMatrix = new QRCodeWriter().encode(text, BarcodeFormat.QR_CODE, taille, taille);
        MatrixToImageWriter.writeToStream(bitMatrix, imageFormat, new FileOutputStream(generatedFile));

        return generatedFile;
    }
}
