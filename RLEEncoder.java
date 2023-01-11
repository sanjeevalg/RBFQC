import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;


public final class RLEEncoder {

   
    private RLEEncoder() { }

    /** Run Length Encode the String.

        @param input The input String
        @return The run length encoded version of the input string
        @throws IOException On invalid input
    */
    public static String runLengthEncode(String input) throws IOException {
        for(int i = 0; i < input.length(); i++){
            String num = input.charAt(i) + "";
            if(isInt(num)){
                throw new IOException();
            }
        }
        StringBuilder out = new StringBuilder();
        if(input.length() > 0){
            char comp = input.charAt(0);
            int count = 0;
            for(int i = 0; i < input.length(); i++){
                if(comp == input.charAt(i)){
                    count++;
                }
                else{
                    if(count > 1){
                        out.append(comp);
                        out.append(count);
                    }
                    else{
                        out.append(comp);
                    }
                    count = 1;
                    comp = input.charAt(i);
                }
                
            }
            out.append(comp);
            if(count > 1){
                out.append(count);
            }
        
        }

        return out.toString();
    }

    public static boolean isInt(String a){
        try{
            Integer.parseInt(a);
            return true;
        }
        catch(NumberFormatException e){
            return false;
        }
        
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

        try {
            System.out.println(runLengthEncode(text));
        } catch (IOException e) {
            System.err.println("Invalid Input");
            System.exit(1);
        }
    }
}
