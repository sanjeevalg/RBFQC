
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class Fileread {

    public static void main(String[] args) {
        try {
            File file = new File("c:/test/target.fastq");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuffer stringBuffer1 = new StringBuffer();
            StringBuffer stringBuffer2 = new StringBuffer();
            StringBuffer stringBuffer3 = new StringBuffer();
            String line;
            stringBuffer1.delete(0, stringBuffer1.length());
            stringBuffer2.delete(0, stringBuffer2.length());
            stringBuffer3.delete(0, stringBuffer3.length());
            int count = 1;
            while ((line = bufferedReader.readLine()) != null) {
                switch (count) {
                    case 1:
                        stringBuffer1.append(line);
                        stringBuffer1.append("\n");
                        count++;
                        break;
                    case 2:
                        stringBuffer2.append(line);
                        stringBuffer2.append("\n");
                        count++;
                        break;
                    case 3:
                        count++;
                        break;
                    case 4:
                        stringBuffer3.append(line);
                        stringBuffer3.append("\n");
                        count = 1;
                        break;
                    default:
                        break;
                }

            }
            try {
                FileWriter writer1 = new FileWriter("c:/test/F1.txt", true);
                FileWriter writer2 = new FileWriter("c:/test/F2.txt", true);
                FileWriter writer3 = new FileWriter("c:/test/F3.txt", true);
                writer1.write(stringBuffer1.toString());
                writer2.write(stringBuffer2.toString());
                writer3.write(stringBuffer3.toString());

                writer1.close();
                writer2.close();
                writer3.close();
            } catch (Exception e) {
                System.out.println(e);
            }
            System.out.println("Success...");


        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            File file = new File("c:/test/F2.txt");
            FileWriter writer4 = new FileWriter("c:/test/nLOC.txt");
            FileWriter writer5 = new FileWriter("c:/test/Seq.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader1 = new BufferedReader(fileReader);
            StringBuffer stringBuffer12 = new StringBuffer();


            String line1;
            while ((line1 = bufferedReader1.readLine()) != null) {
                stringBuffer12.append(line1);

            }

            char character = 'N';
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < stringBuffer12.length(); i++) {
                if (stringBuffer12.charAt(i) == character) {
                    int j = i;
                    while (stringBuffer12.charAt(i) == character) {
                        i++;
                    }
                    writer4.write("("+j+","+(i-j)+")");
                } else {
                    sb.append(stringBuffer12.charAt(i));
                }
            }
            writer5.write(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
