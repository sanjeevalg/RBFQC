import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Arrays;


/**  Builds the BWT of a string.

  Builds the BWT by sorting all of the cyclic permutations.
**/
public class BWTEncoder {

    // You may want to use this ;-)
    private final class CyclicPermutation
        implements Comparable<CyclicPermutation> {
        private int offset;
        
        
        public void setOffset(int a){
            this.offset = a;
            
        }
        public int getOffset(){
            return this.offset;
        }
        @Override
        public int compareTo(CyclicPermutation n) {
            int val = 0;
            int index1 = this.getOffset();
            int index2 = n.getOffset();
            
            for(int i = 0; i < text.length(); i++){
                int a = text.charAt(index1);
                int b = text.charAt(index2);
                if(a > b){
                    val = 1;
                    break;
                }
                if(a < b){
                    val = -1;
                    break;
                }
                index1 = ((index1 + 1) % text.length()); 
                index2 = ((index2 + 1) % text.length()); 
            }
            
            return val;
        }
    }

    String text;
    CyclicPermutation[] data;

    /** Silence checkstyle. */
    public BWTEncoder() { }

    /** Construct the BWT of a string.

        Derives the list of possible permutations, sorts them, and then
        extracts the last colunm

        @param input The input String
        @return The BWT of the input string
        @throws IOException On invalid input
    */
    public String createBWT(String input) throws IOException {
        this.text = input + "$";
        for(int i = 0; i < input.length(); i++){
            int character = input.charAt(i);
            int dollar = '$';
            if(character <= dollar){
                throw new IOException();
            }
            
        }
        
        StringBuilder bwt = new StringBuilder();
        data = new CyclicPermutation[text.length()];
        for(int i = 1; i <= text.length(); i++){
            CyclicPermutation n = new CyclicPermutation();
            n.setOffset((text.length() - i) % text.length());
            data[i - 1] = n;
        }
        
        Arrays.sort(data);
        for(int i = 0; i < text.length(); i++){
            char add = text.charAt((data[i].getOffset() + text.length() - 1) % text.length());
            bwt.append(add);
        }

        return bwt.toString();
    }

    /** Reads the input from standard in.
        @return Returns a string with the input data without newlines
        @throws IOException if the file cannot be read
    */
    public static String readInput() throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);
        StringBuilder sb = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        reader.close();
        input.close();

        return sb.toString();
    }

    /** Main method, load file into string, compute BWT.
        @param args Input arguments, ingorned
    */
    public static void main(String[] args) {
        String text = "";

        try {
            text = readInput();
        } catch (IOException e) {
            System.err.println("Cant read input");
            System.exit(1);
        }

        BWTEncoder bwt = new BWTEncoder();
        try {
            System.out.println(bwt.createBWT(text));
        } catch (IOException e) {
            System.err.println("Invalid Input");
            System.exit(1);
        }
    }
}
