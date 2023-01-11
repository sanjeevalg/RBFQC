import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ReadReplaceFile2{

    private static int tupleSize = 4;
    private byte[] charCodes = {0,1,2,3};
    private static char[] chars = {'A','C','G','T'};
    private static byte[] refCodes = new byte[1<<30];
    private static byte[] tarCodes = new byte[1<<30];

    private static int refCodeLen = 0;
    private static int targetCodeLen = 0;
    private static Map<String, String> map = new HashMap<>();
//    private static List<String> searches = new ArrayList<>();
//    private static String testFile = "c:/test/test.fasta";
    private static String test1File = "D://dataref/data/test1.txt";
    private static String t2File = "D://dataref/data/test2.txt";
    private static String indexMaxChars = "D://dataref/data/maxMatchedChars.fq";

    public static void main(String[] args) {
        System.out.println("This is the main method");
        refCodeLen = readAssignFile(test1File, refCodes);
        targetCodeLen = readAssignFile(t2File, tarCodes);
        generateHashTable(refCodes, tupleSize);
        searchCodes(refCodes, tarCodes, tupleSize);
//        System.out.println("Matched list: " + searches);
    }

    private static byte getIndex(char ch) {
        byte index = -1;
        if(ch == 'A' || ch == 'a') {
            index = 0;
        } else if(ch == 'C' || ch == 'c') {
            index = 1;
        } else if(ch == 'G' || ch == 'g') {
            index = 2;
        } else if(ch == 'T' || ch == 't') {
            index = 3;
        }
        return index;
    }

    private static int readAssignFile(String fileName, byte[] refCodes) {
        int index = 0;
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            String line = null;
            while ((line = br.readLine()) != null) {
                for(int i=0;i<line.length();i++) {
                    byte b = getIndex(line.charAt(i));
                    if(b != -1) {
                        refCodes[index++] = b;
                    }
                }
            }
            return index;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return index;
    }

    private static void generateHashTable(byte[] refCodes, int tupleSize) {
        FileInputStream in = null;

        try {
            int keyValue = 0;
            //StringBuilder sb = new StringBuilder();
            int count=0;
            for(int i=0;i<=refCodeLen-tupleSize;i++) {
                if(count==0) {
                    for (int j = i; j < tupleSize + i; j++) {
                        keyValue += (int) refCodes[j] * (int) Math.pow(tupleSize, count++);
                       // sb.append(chars[refCodes[j]]);
                    }
                } else {
                    //sb.append(chars[refCodes[count]]);
                    keyValue = keyValue/tupleSize;
                    keyValue +=  (int) (refCodes[count] * (int) Math.pow(tupleSize, 3));
                    count++;
                }

                String pos = map.get(keyValue+"");
                if (pos == null) {
                  //  System.out.print(sb+":"+keyValue+", ");
                    map.put(keyValue+"",i+"");
                } else {
                    map.put(keyValue+"",pos + "," + i);
                }
               // sb = new StringBuilder(sb.substring(1));

            }
            System.out.println("Generated map: " + map);
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*private static void generateHashTable(byte[] refCodes, int tupleSize) {
        FileInputStream in = null;

        try {
            int c = 0;
            StringBuilder sb = new StringBuilder();
            int count = 0;
            int keyValue = 0;
            int index = 0;

            for(int i=0;i<refCodeLen;i++) {

                sb.append(chars[refCodes[i]]);
                keyValue += (int)refCodes[i] * (int)Math.pow(tupleSize, count);
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
                    //System.out.println("String for:" + sb +",key:"+key);
                    sb = new StringBuilder();
                }
            }
            System.out.println("Generated map: " + map);
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    private static void searchCodes(byte[] refCodes, byte[] targetCodes, int tupleSize) {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        int keyValue = 0;
        StringBuilder notMatchedStringBuilder = new StringBuilder();
        FileOutputStream out = null;
        int pos = 0;
        try {
            out = new FileOutputStream(new File(indexMaxChars));
            for (int i = 0; i < targetCodeLen; i++) {
                char ch = chars[targetCodes[i]];
                sb.append(ch);
                keyValue += getIndex(ch) * (int) Math.pow(tupleSize, count);
                count++;
                //  index++;
                //Get Tuple Size
                if (sb.length() == tupleSize) {
                    int key = keyValue;
                    count = 0;
                    keyValue = 0;
                    pos = i + 1 - tupleSize;
                    String position = map.get(key + "");
                    //Get all list of position and get max matched characters
                    if (position != null) {
                        String [] positions= position.split(",");
                        int max = tupleSize;
                        if(!notMatchedStringBuilder.toString().isEmpty()) {
                            if (notMatchedStringBuilder.length() == tupleSize) {
                                sb = replaceOneChar(notMatchedStringBuilder.toString());
                                int kValue = 0;
                                for (int k = 0; k < sb.length(); k++) {
                                    kValue += getIndex(sb.charAt(k)) * (int) Math.pow(tupleSize, k);
                                }
                                String position1 = map.get(kValue+"");
                                if (position1 != null) {
                                    notMatchedStringBuilder = new StringBuilder();

                                    for (String refPos: positions) {
                                        int matchedCars = calculateMaxPosition(refCodes, targetCodes, pos, Integer.parseInt(refPos));
                                        if (matchedCars > max) {
                                            max = matchedCars;
                                        }
                                    }
                                    sb = new StringBuilder();
                                    out.write(("(0, " + position1 + "," + max + ")").getBytes());
                                    i += max - tupleSize;
                                }
                            } else {
                                out.write(notMatchedStringBuilder.toString().getBytes());
                            }
                        }
                        notMatchedStringBuilder = new StringBuilder();
                        for (String refPos: positions) {
                            int matchedCars = calculateMaxPosition(refCodes, targetCodes, pos, Integer.parseInt(refPos));
                            if (matchedCars > max) {
                                max = matchedCars;
                            }
                        }
                        sb = new StringBuilder();
                        //Write matched characters with position into file
//                        searches.add("(" + position + "," + max + ")");
                        out.write(("(" + position + "," + max + ")").getBytes());
                        i += max - tupleSize;
                    } else {
                        if (notMatchedStringBuilder.length() == tupleSize) {
                            sb = replaceOneChar(notMatchedStringBuilder.toString());
                            int kValue = 0;
                            for (int k = 0; k < sb.length(); k++) {
                                kValue += getIndex(sb.charAt(k)) * (int) Math.pow(tupleSize, k);
                            }
                            String position1 = map.get(kValue + "");
                            if (position1 != null) {
                                String [] positions= position1.split(",");
                                int max = tupleSize;
                                notMatchedStringBuilder = new StringBuilder();
                                for (String refPos: positions) {
                                    int matchedCars = calculateMaxPosition(refCodes, targetCodes, pos, Integer.parseInt(refPos));
                                    if (matchedCars > max) {
                                        max = matchedCars;
                                    }
                                }
                                sb = new StringBuilder();
                                //Write matched characters with position into file
//                                searches.add("(0, " + position1 + "," + max + ")");
                                out.write(("(0, " + position1 + "," + max + ")").getBytes());
                                i += max - tupleSize;
                            }
                        }
//                        searches.add(sb.charAt(0) + "");
                       // out.write((sb.charAt(0) + "").getBytes());
                        count = 3;
                        for (int j = 1; j < tupleSize; j++) {
                            keyValue = keyValue + (getIndex(sb.charAt(j)) * (int) Math.pow(tupleSize, j - 1));
                        }
                        notMatchedStringBuilder.append(sb.charAt(0));
                        sb = new StringBuilder(sb.substring(1));

                    }
                }
            }
            if (sb.length() < 4) {
                for (int i = 0; i < sb.length(); i++,pos++) {
//                    searches.add(sb.charAt(i) + "");
                    char ch = sb.charAt(i);
                    if(notMatchedStringBuilder.length() < tupleSize) {
                        notMatchedStringBuilder.append(ch);
                    } else if (notMatchedStringBuilder.length() == tupleSize) {
                        StringBuilder str = replaceOneChar(notMatchedStringBuilder.toString());
                        int kValue = 0;
                        for (int k = 0; k < str.length(); k++) {
                            kValue += getIndex(str.charAt(k)) * (int) Math.pow(tupleSize, k);
                        }
                        String position1 = map.get(kValue + "");
                        if(position1 != null) {
                            String[] positions = position1.split(",");
                            int max = tupleSize;
                            notMatchedStringBuilder = new StringBuilder();
                            notMatchedStringBuilder.append(ch);

                            for (String refPos: positions) {
                                int matchedCars = calculateMaxPosition(refCodes, targetCodes, pos,  Integer.parseInt(refPos));
                                if (matchedCars > max) {
                                    max = matchedCars;
                                }
                            }
                            out.write(("(0," + position1 + "," + max + ")").getBytes());
                            pos = pos + max - tupleSize;
                        } else {
                            out.write(notMatchedStringBuilder.toString().getBytes());
                            notMatchedStringBuilder = new StringBuilder();
                            notMatchedStringBuilder.append(ch);
                        }
                    } else {
                         out.write((sb.charAt(i) + "").getBytes());
                    }
                }
                if(!notMatchedStringBuilder.toString().isEmpty() && notMatchedStringBuilder.length() < 4) {
                    out.write(notMatchedStringBuilder.toString().getBytes());
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if(out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static StringBuilder replaceOneChar(String str) {
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<str.length();i++){
            if(str.charAt(i) == 'A' || str.charAt(i) == 'a') {
                sb.append('T');
            } else if(str.charAt(i) == 'C' || str.charAt(i) == 'c') {
                sb.append('G');
            } else if(str.charAt(i) == 'G' || str.charAt(i) == 'g') {
                sb.append('C');
            } else if(str.charAt(i) == 'T' || str.charAt(i) == 't') {
                sb.append('A');
            }
        }
        return sb;
    }

    public static int calculateMaxPosition(byte[] refCodes, byte[] targetCodes, int targetPos, int refPosition) {
        int matchedChars = tupleSize;
        targetPos += tupleSize;
        refPosition += tupleSize;
        while(refCodes[refPosition] == targetCodes[targetPos]) {
            refPosition++;
            targetPos++;
            matchedChars++;
            if(refPosition >= refCodeLen || targetPos >= targetCodeLen) break;
        }
        return matchedChars;
    }
}
