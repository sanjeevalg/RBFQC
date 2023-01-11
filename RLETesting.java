import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
/**Jason Zhang jzhan127@jhu.edu
 * test RLE
 */

public class RLETesting {
    private String test;
    @Before
    public void setup(){
        this.test = "";
    }

    @Test
    public void testEncode() throws IOException {
        String a = "a3b4c5";
        test = "aaabbbbccccc";
        assertEquals(RLEEncoder.runLengthEncode(test), a);
    }
    @Test
    public void testEncodeOne() throws IOException {
        String a = "a3";
        test = "aaa";
        assertEquals(RLEEncoder.runLengthEncode(test), a);
    }
    @Test
    public void testEncodeEmpty() throws IOException {
        String a = "";
        test = "";
        assertEquals(RLEEncoder.runLengthEncode(test), a);
    }
    @Test
    public void testEncodeSpaces() throws IOException {
        String a = " 4";
        test = "    ";
        assertEquals(RLEEncoder.runLengthEncode(test), a);
    }
    @Test (expected = IOException.class)
    public void testEncodeException() throws IOException {
        String a = "!";
        test = "0";
        assertEquals(RLEEncoder.runLengthEncode(test), a);
    }
    @Test 
    public void testDecode() throws IOException {
        String a = "aaabbbccc";
        test = "a3b3c3";
        assertEquals(RLEDecoder.runLengthDecode(test), a);
    }
    @Test 
    public void testDecodeOne() throws IOException {
        String a = "aaaa";
        test = "a4";
        assertEquals(RLEDecoder.runLengthDecode(test), a);
    }
    @Test (expected = IOException.class)
    public void testDecodeException() throws IOException {
        String a = "a3";
        test = "aa";
        assertEquals(RLEDecoder.runLengthDecode(test), a);
    }
    @Test 
    public void testDecodeEmpty() throws IOException {
        String a = "";
        test = "";
        assertEquals(RLEDecoder.runLengthDecode(test), a);
    }

}
