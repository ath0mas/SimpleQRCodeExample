package fr.alexisthomas.zxing.simpleexample;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

/**
 * Test de la classe BarCodeReader.
 */
public class BarCodeReaderTest {

    static ClassLoader classLoader = BarCodeReaderTest.class.getClassLoader();

    @Test
    public void testGetBarCodeTextFromFile() throws Exception {
        String imagePath = classLoader.getResource("chartQRCodeGenerated.png").getPath();
        String textRead = BarCodeReader.getBarCodeTextFromFile(imagePath);
        assertThat(textRead, not("valeur_erronee"));
        assertThat(textRead, is("http://www.alexisthomas.fr"));
    }

}