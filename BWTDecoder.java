import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Arrays;

/**Jason Zhang jzhan127@jhu.edu 
 * Class to decode a BWT back to the original string. */
public final class BWTDecoder {

    /** Make checkstyle happy. */
    private BWTDecoder() { }

    /** Decode the BWT of a string.
        @param bwt the input bwt string
        @return The original string (before BWT)
        @throws IOException On invalid input
    */
    public static String decodeBWT(String bwt) throws IOException {
        int count = 0;
        int dollar = '$';
        for(int i = 0; i < bwt.length(); i++){
            int character = bwt.charAt(i);
            if(character < dollar){
                throw new IOException();
            }
            if(character == dollar){
                count++;
                if(count > 1){
                    throw new IOException();
                }
            }
        }
        if(count < 1){
            throw new IOException();
        }
        StringBuilder text = new StringBuilder();
        String first = firstCol(bwt);
        int row = 0;
        while(bwt.charAt(row) != '$'){
            text.append(bwt.charAt(row));
            row = applyLF(first, bwt.charAt(row), rank(bwt, row));
        }
        return text.reverse().toString();
    }
    public static int applyLF(String first, char last, int rank){
        int count = -1;
        int place = 0;
        for(int i = 0; i < first.length(); i++){
            if(first.charAt(i) == last){
                count++;
                if(count == rank){
                    place = i;
                    break;
                }
            }
        }
        
        return place;
    }
    public static int rank(String bwt, int row){
        char hold = bwt.charAt(row);
        int val = -1;
        for(int i = 0; i < bwt.length(); i++){
            if(bwt.charAt(i) == hold){
                val++;
                if(i == row){
                    break;
                }
            }
        }
        return val;
    }
    public static String firstCol(String bwt){
        StringBuilder first = new StringBuilder();
        char[] temp = new char[bwt.length()];
        for(int i = 0; i < bwt.length(); i++){
            temp[i] = bwt.charAt(i);
        }
        Arrays.sort(temp);
        for(int i = 0; i < bwt.length(); i++){
            first.append(temp[i]);
        }
        return first.toString();
    }

    /** Reads the input data into a string.
        @return A string with the input data wiht newlines removed
        @throws IOException On error reading input
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

    /** Reads a BWT string from standard in and returns the original string.
        @param args ignored
    */
    public static void main(String[] args) {
        String bwt = "";

        try {
            bwt = readInput();
        } catch (IOException e) {
            System.err.println("Cant read input");
            System.exit(1);
        }

        try {
            System.out.println(BWTDecoder.decodeBWT(bwt));
        } catch (IOException e) {
            System.err.println("Invalid input");
            System.exit(1);
        }
    }
}
