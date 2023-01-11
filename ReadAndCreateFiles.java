import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class ReadAndCreateFiles {
    public static void main(String[] args) {
        String line = null;
        try {
            FileReader fr = new FileReader("D://dataref/test.fq");
            BufferedReader br = new BufferedReader(fr);

            FileWriter fw1 = new FileWriter("D://dataref/SRR1063349.fastq");
            BufferedWriter bw1 = new BufferedWriter(fw1);

            FileWriter fw2 = new FileWriter("D://dataref/t2.fq");
            BufferedWriter bw2 = new BufferedWriter(fw2);

            FileWriter fw3 = new FileWriter("D://dataref/t3.fq");
            BufferedWriter bw3 = new BufferedWriter(fw3);

            while ((line = br.readLine()) != null)  {
                bw1.write(line + "\n"); // read and write into first line
                line = br.readLine();
                bw2.write(line + "\n"); // write second line
                br.readLine(); // to skip third line
                line = br.readLine();
                
                bw3.write(line + "\n"); // write fourth line
            }
                   
            
            bw1.close();
            bw2.close();
            bw3.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
