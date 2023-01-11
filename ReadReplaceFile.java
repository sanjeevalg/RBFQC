
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadReplaceFile {

    private static Map<String, List<Integer>> map = new HashMap<>();
    private static String testFile = "D://dataref/test.fasta";
    private static String test1File = "D://dataref/test1.fasta";
    private static String t2File = "D://dataref/t2.fq";
    private static String indexMaxChars = "D://dataref/maxMatchedChars.txt";
    public static void main(String[] args) {
        String line = null;
        try {
            FileReader fr = new FileReader(testFile);
            BufferedReader br = new BufferedReader(fr);
            FileWriter fw = new FileWriter(test1File);
            BufferedWriter bw = new BufferedWriter(fw);
            line = br.readLine();

            while ((line = br.readLine()) != null) {
                String modifiedLine = line;
                String notMatchChars = line.replaceAll("[?!(A|C|T|G)]","");
              // System.out.println("notMatchChars string: " + notMatchChars);
                if(!notMatchChars.isEmpty()) {
                    String pat = "[";
                    for (int i = 0; i < notMatchChars.length(); i++) {
                        pat += notMatchChars.charAt(i) + "|";
                    }
                    if (pat.length() > 1) {
                        pat = pat.substring(0, pat.length() - 1);
                    }
                    pat += "]*";
                   // System.out.println("Generated Pattern: " + pat);
                    modifiedLine = line.replaceAll(pat, "");
                    //System.out.println(modifiedLine);
                }
                bw.write(modifiedLine + "\n");
            }
            bw.close();
            br.close();
            generateHashTable(test1File,4);
            createFileWithMatch(t2File, test1File, indexMaxChars, 4);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Method to generate Hash table
     * @param finePath
     * @param tupleSize
     * @throws IOException
     */
    private static void generateHashTable(String finePath, int tupleSize) throws IOException {
        FileInputStream in = null;

        try {
            in = new FileInputStream(finePath);
            int c = 0;
            StringBuilder sb = new StringBuilder();
            int count = 0;
            int keyValue = 0;
            int index = 0;

            while ((c = in.read()) != -1) {
                char ch = (char)c;
                if (ch == '\n') { continue; }
                sb.append(ch);
                keyValue += getCharValue(ch) * (int)Math.pow(tupleSize, count);
                count++;
                index++;
                if (index%tupleSize == 0) {
                   int key = keyValue;
                   count = 0;
                   keyValue = 0;
                   List<Integer> list = map.get(key+"");
                   if (list == null || list.isEmpty()) {
                        list = new ArrayList<>();
                        list.add(index-tupleSize);
                        map.put(key+"", list);
                   } else {
                       list.add(index-tupleSize);
                       map.put(key+"", list);
                   }
                    System.out.println("String for:" + sb +",key:"+key);
                   sb = new StringBuilder();
                }
            }
            System.out.println("Generated map: " + map);
        }finally {
            if (in != null) {
                in.close();
            }
        }
    }
    /**
     * Method to get character value
     * @param ch
     * @return
     */
    private static int getCharValue(char ch) {
        int charValue = 0;
        if (ch == 'A' || ch == 'a') {
            charValue = 0;
        } else if (ch == 'C' || ch == 'c') {
            charValue = 1;
        } else if (ch == 'G' || ch == 'g') {
            charValue = 2;
        } else if (ch == 'T' || ch == 't') {
            charValue = 3;
        }
        return charValue;
    }
    /**
     * Method createFileWithMatch() to create file with matched characters
     * @param file1
     * @param file2
     * @param newFile
     * @param tupleSize
     * @throws IOException
     */
    private static void createFileWithMatch(String file1, String file2, String newFile, int tupleSize) throws IOException {
        FileInputStream in1 = null;
        FileInputStream in2 = null;
        FileOutputStream out = null;
        int c = 0;
        int count = 0;
        int keyValue = 0;
        int index = 0;
        try {
            in1 = new FileInputStream(file1);
            in2 = new FileInputStream(file2);
            out = new FileOutputStream(newFile);
            StringBuilder sb = new StringBuilder();
            //Read first file and get grouping
            while ((c = in1.read()) != -1) {
                char ch = (char) c;
                if (ch == '\n') { continue; }
                sb.append(ch);
                keyValue += getCharValue(ch) * (int)Math.pow(tupleSize, count);
                count++;
                index++;
                //Get Tuple Size
                if (sb.length() == tupleSize) {
                    int key = keyValue;
                    count = 0;
                    keyValue = 0;

                    int pos = index - tupleSize;
                    List<Integer> list = map.get(key+"");

                    //Get all list of position and get max matched characters
                    if(list != null) {
                        int max = tupleSize;
                        for (int position : list) {
                            int matchedCars = calculateMaxPosition(file1, file2, pos, position);
                            if (matchedCars > max) {
                                max = matchedCars;
                            }
                        }
                        sb = new StringBuilder();
                        //Write matched characters with position into file
                        out.write((index-tupleSize + "," + max + ",").getBytes());
                        for(int i=0;i<max-tupleSize;i++){in1.read();index++;}
                    } else {
                        out.write((sb.charAt(0)+",").getBytes());
                        count=3;
                        for(int i=1;i<tupleSize;i++) {
                            keyValue = keyValue + (getCharValue(sb.charAt(i)) * (int)Math.pow(tupleSize, i-1));
                        }
                        sb = new StringBuilder(sb.substring(1));
                    }
                }
            }


        } finally {
            if (in1 != null) {
                in1.close();
            }
            if (in2 != null) {
                in2.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }
    /**
     * method calculateMaxPosition() to calculate max matched characters
     * @param file1
     * @param file2
     * @param index1
     * @param index2
     * @return
     * @throws IOException
     */
    private static int calculateMaxPosition(String file1, String file2, int index1, int index2)  throws IOException {
        int matchedChars = 0;
        FileInputStream in1 = null;
        FileInputStream in2 = null;
        int c = 0;

        try {
            in1 = new FileInputStream(file1);
            in2 = new FileInputStream(file2);
            for(int i=0;i<index1;i++) {
                c = in1.read();
                char ch = (char) c;
                if (ch == '\n') {
                    continue;
                }
            }
            for(int i=0;i<index2;i++) {
                c = in2.read();
                char ch = (char) c;
                if (ch == '\n') {
                    continue;
                }
            }
            while(in1.read() == in2.read()) {
                matchedChars++;
            }
        }  finally {
            if (in1 != null) {
                in1.close();
            }
            if (in2 != null) {
                in2.close();
            }
        }
        return matchedChars;
    }
}
