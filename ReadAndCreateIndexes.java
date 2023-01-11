
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class ReadAndCreateIndexes {
    public static void main(String[] args) {
        String line = null;
        try {
            FileReader fr = new FileReader("D://dataref/t2.fq");
            BufferedReader br = new BufferedReader(fr);

            FileWriter fw = new FileWriter("D://dataref/t2-index.fq");
            BufferedWriter bw = new BufferedWriter(fw);
            int lineCount = 0;
            while ((line = br.readLine()) != null) {
                lineCount++;
                String notMatchChars = line.replaceAll("[?!(A|C|T|G)]","");
                if(!notMatchChars.isEmpty()) {
                    StringBuilder sb = new StringBuilder();
                    List<Character> listChars = new ArrayList<>();
                    for(int i=0;i<notMatchChars.length();i++) {
                        char nonM = notMatchChars.charAt(i);
                        if(listChars.contains(nonM)) continue;
                        listChars.add(nonM);
                        sb.append(nonM + " => ");
                        int index = line.indexOf(nonM);
                        sb.append(index + ", ");
                        while (index > 0) {
                            index = line.indexOf(nonM, index+1);
                            if (index > 0) {
                                sb.append(index + ", ");
                            }
                        }
                        sb.append("\n");
                    }
                    bw.write("Non matched characters: " + listChars);
                    bw.write("\nFound Line No.: " + lineCount);
                    bw.write("\nFound Line: " + line);
                    bw.write("\nTotal Indexes: \n" + sb);
                }

            }
            bw.close();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
