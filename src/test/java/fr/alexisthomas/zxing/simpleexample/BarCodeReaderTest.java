package fr.alexisthomas.zxing.simpleexample;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * Test for {@link BarCodeReader}.
 */
public class BarCodeReaderTest {

    static ClassLoader classLoader = BarCodeReaderTest.class.getClassLoader();

    @Test
    public void testGetBarCodeTextFromFile() throws Exception {
        String imagePath = classLoader.getResource("chartQRCodeGenerated.png").getPath();
        String textRead = BarCodeReader.getBarCodeTextFromFile(imagePath);
        assertThat(textRead, is("http://www.alexisthomas.fr"));
    }

}
