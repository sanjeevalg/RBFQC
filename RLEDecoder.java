import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;


public final class RLEDecoder {

    
    private RLEDecoder() { }
   
    public static String runLengthDecode(String input) throws IOException {
        
        StringBuilder out = new StringBuilder();
        for(int i = 0; i < input.length(); i++){
            char n = input.charAt(i);
            if(!checkInt(n)){
                if(i + 1 < input.length() && checkInt(input.charAt(i + 1))){
                    String val = "";
                    int j = i + 1;
                    while(j < input.length() && checkInt(input.charAt(j))){
                        val += input.charAt(j);
                        j++;
                    }
                    int count = Integer.parseInt(val);
                    if(count <= 1){
                        throw new IOException();
                    }
                    for(int s = 0; s < count; s++){
                        out.append(n);
                    }
                }
                else if(i + 1 < input.length() && !checkInt(input.charAt(i + 1)) && n == input.charAt(i + 1)){
                    throw new IOException();
                }
                else{
                    out.append(n);
                }
            }
        }

        return out.toString();
    }

    public static boolean checkInt(char n){
        try{
            Integer.parseInt(n + "");
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
            System.out.println(runLengthDecode(text));
        } catch (IOException e) {
            System.err.println("Invalid Input");
            System.exit(1);
        }
    }
}
