package fr.alexisthomas.zxing.simpleexample;

import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test {@link BarCodeWriter}.
 */
public class BarCodeWriterTest {

    static ClassLoader classLoader = BarCodeReaderTest.class.getClassLoader();

    @Test
    public void testGenerateQRCodeFromText() throws Exception {
        String imagePathTarget = new File(classLoader.getResource("chartQRCodeGenerated.png").getPath()).getPath();
        File imageTarget = new File(imagePathTarget);

        String rootGeneratedPath = imagePathTarget.substring(0, imagePathTarget.lastIndexOf(File.separator)) + File.separator + "generated" + File.separator;
        // delete rootGenerated folder before use
        File rootGeneratedFolder = new File(rootGeneratedPath);
        if (rootGeneratedFolder.exists()) {
            deleteDirectory(rootGeneratedFolder);
        }

        File wrongSizeFile = BarCodeWriter.generateQRCodeFromText("http://www.alexisthomas.fr", 300, rootGeneratedPath);
        File wrongGeneratedFile = BarCodeWriter.generateQRCodeFromText("wrong_value", 350, rootGeneratedPath);
        File rightGeneratedFile = BarCodeWriter.generateQRCodeFromText("http://www.alexisthomas.fr", 350, rootGeneratedPath);

        assertFalse("A BarCode with non valid dimensions should not be correct.", compareBarCodeFiles(wrongSizeFile, imageTarget));
        assertFalse(compareBarCodeFiles(wrongGeneratedFile, imageTarget));
        assertTrue(compareBarCodeFiles(rightGeneratedFile, imageTarget));
    }

    private static boolean deleteDirectory(File path) {
        if (path.exists()) {
            File[] files = path.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file);
                } else {
                    file.delete();
                }
            }
        }
        return (path.delete());
    }

    private static boolean compareBarCodeFiles(File file1, File file2) {
        try {
            int height1 = ImageIO.read(file1).getHeight();
            int width1 = ImageIO.read(file1).getWidth();
            int height2 = ImageIO.read(file2).getHeight();
            int width2 = ImageIO.read(file2).getWidth();

            if ((height1 != height2) || (width1 != width2)) {
                System.out.println("Mismatch in dimensions");
                return false;
            } else {
                String text1 = BarCodeReader.getBarCodeTextFromFile(file1.getPath());
                String text2 = BarCodeReader.getBarCodeTextFromFile(file2.getPath());

                if (!text1.equals(text2)) {
                    System.out.println("Mismatch in encoded texts");
                    return false;
                }
            }

        } catch (IOException e) {
            System.out.println("Error while reading files");
            return false;
        } catch (ChecksumException | NotFoundException | FormatException e) {
            e.printStackTrace();
        }

        System.out.println("Correct dimensions et encoded text");
        return true;
    }

}
