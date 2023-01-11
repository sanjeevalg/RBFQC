import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
/**Jason Zhang jzhan127@jhu.edu
 * test bwt
 */

public class BWTTesting {

    private String test;
    private BWTEncoder n;
    @Before
    public void setup(){
        this.test = "";
        n = new BWTEncoder();
        
    }
    
    @Test
    public void testEncode() throws IOException {
        String a = "nj$osa";
        test = "jason";
        assertEquals(n.createBWT(test), a);
    }
    @Test
    public void testEncodeOne() throws IOException {
        String a = "a$";
        test = "a";
        assertEquals(n.createBWT(test), a);
    }
    @Test
    public void testEncodeEmpty() throws IOException {
        String a = "$";
        test = "";
        assertEquals(n.createBWT(test), a);
    }
    @Test (expected = IOException.class)
    public void testEncodeException() throws IOException {
        String a = "$$";
        test = "$$jason";
        assertEquals(n.createBWT(test), a);
    }
    
    @Test 
    public void testDecode() throws IOException {
        String a = "jason";
        test = "nj$osa";
        assertEquals(BWTDecoder.decodeBWT(test), a);
    }
    @Test 
    public void testDecodeOne() throws IOException {
        String a = "a";
        test = "a$";
        assertEquals(BWTDecoder.decodeBWT(test), a);
    }
    @Test (expected = IOException.class)
    public void testDecodeException() throws IOException {
        String a = "a";
        test = "$a$";
        assertEquals(BWTDecoder.decodeBWT(test), a);
    }
    @Test (expected = IOException.class)
    public void testDecodeException2() throws IOException {
        String a = "a";
        test = "!a$";
        assertEquals(BWTDecoder.decodeBWT(test), a);
    }
    
    @Test 
    public void testDecodeEmpty() throws IOException {
        String a = "";
        test = "$";
        assertEquals(BWTDecoder.decodeBWT(test), a);
    }
    

    
    
}
