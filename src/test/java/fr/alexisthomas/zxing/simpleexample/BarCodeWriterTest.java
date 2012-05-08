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
 * Test de la classe BarCodeWriter.
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
        File wrongGeneratedFile = BarCodeWriter.generateQRCodeFromText("valeur_erronee", 350, rootGeneratedPath);
        File rightGeneratedFile = BarCodeWriter.generateQRCodeFromText("http://www.alexisthomas.fr", 350, rootGeneratedPath);

        assertFalse("Un BarCode de dimensions invalides ne devrait pas être correct.", compareBarCodeFiles(wrongSizeFile, imageTarget));
        assertFalse(compareBarCodeFiles(wrongGeneratedFile, imageTarget));
        assertTrue(compareBarCodeFiles(rightGeneratedFile, imageTarget));
    }

    private static boolean deleteDirectory(File path) {
        if (path.exists()) {
            File[] files = path.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    deleteDirectory(files[i]);
                } else {
                    files[i].delete();
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
                System.out.println("Dimensions différentes");
                return false;
            } else {
                String text1 = BarCodeReader.getBarCodeTextFromFile(file1.getPath());
                String text2 = BarCodeReader.getBarCodeTextFromFile(file2.getPath());

                if (!text1.equals(text2)) {
                    System.out.println("Textes encodés différents");
                    return false;
                }
            }

        } catch (IOException e) {
            System.out.println("Erreur lors de la lecture des fichiers");
            return false;
        } catch (ChecksumException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (FormatException e) {
            e.printStackTrace();
        }

        System.out.println("Dimensions et texte encodés corrects");
        return true;
    }

}