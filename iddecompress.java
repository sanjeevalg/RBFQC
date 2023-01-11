

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class iddecompress {
	public static final String SEPERATOR = ",";
	public static final String NEW_LINE = "\n";
	public static final String REXEX = "[.| |:|_|-|/]";
	public static final String FILE_READ_FROM = "c:/test/F1.txt";
	public static final String FILE_WRITE_TO = "c:/test/F1DEC.txt";
	public static final String IDENTICAL = "c:/test/identical.txt";
	public static final String INCREMENT = "c:/test/increment.txt";
	public static final String RANDOM = "c:/test/random.txt";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		testWrite();
		testRead();
	}

	/**
	 * Method to create encrypted file
	 */
	public static void testWrite() {
		System.out.println("Inside main method");
		try {

			BufferedReader br = new BufferedReader(new FileReader(new File(
					FILE_READ_FROM)));

			FileOutputStream identFile = new FileOutputStream(new File(
					IDENTICAL));
			FileOutputStream incrementFile = new FileOutputStream(new File(
					INCREMENT));
			FileOutputStream randomFile = new FileOutputStream(new File(RANDOM));

			String str;
			List<String> tempList = new ArrayList<String>();
			List<String> identical = new ArrayList<String>();
			List<String> increment = new ArrayList<String>();
			List<String> random = new ArrayList<String>();
			while ((str = br.readLine()) != null) {
				tempList.add(str);
			}
			String[] prevEle = tempList.get(0).split(REXEX);
			String[][] arrEle = new String[prevEle.length][tempList.size()];
			int index = 0;
			String[] ele;
			for (String temp : tempList) {
				ele = temp.split(REXEX);
				for (int i = 0; i < ele.length; i++) {
					arrEle[i][index] = ele[i];
				}
				index++;
			}
			for (int i = 0; i < arrEle.length; i++) {

				@SuppressWarnings({ "unchecked", "rawtypes" })
				Set<String> set = new LinkedHashSet(Arrays.asList(arrEle[i]));
				if (set.size() == 1) {
					identical.add(set.iterator().next() + SEPERATOR + i
							+ SEPERATOR + tempList.size());
					continue;
				}
				boolean incFlag = true;
				List<String> temp = new ArrayList<String>();
				temp.add("column=" + i);
				String tempStr = arrEle[i][0];
				for (int j = 0; j < arrEle[i].length; j++) {
					// System.out.print(arrEle[i][j]+"\t");
					temp.add(arrEle[i][j]);
					try {
						if (Integer.parseInt(tempStr) > Integer
								.parseInt(arrEle[i][j])) {
							incFlag = false;
						}
						tempStr = arrEle[i][j];
					} catch (Exception e) {
						incFlag = false;
					}
				}
				if (incFlag) {
					increment.addAll(temp);
				} else {
					random.addAll(temp);
				}
				// System.out.println();
			}

			System.out.println("Identical:" + identical);
			System.out.println("Increment: " + increment);
			System.out.println("Random: " + random);
			String firstLine = tempList.get(0);

			String position = "POSRow=" + tempList.size() + " column="
					+ prevEle.length;
			identFile.write((position + NEW_LINE).getBytes());
			identFile.write((firstLine + NEW_LINE).getBytes());
			for (String temp : identical) {
				identFile.write((temp + NEW_LINE).getBytes());
			}
			int count = 0;
			int preEle=0, currEle=0;
			for (String temp : increment) {
				if (temp.contains("column")) {
					if (count == 1) {
						incrementFile.write(NEW_LINE.getBytes());
					}					
					incrementFile.write(temp.getBytes());
					incrementFile.write(NEW_LINE.getBytes());
				} else {
					if(count==0) {
						preEle = Integer.parseInt(temp);
						incrementFile.write((temp + ",").getBytes());
					} else {
						currEle = Integer.parseInt(temp);
						incrementFile.write(((currEle - preEle) + ",").getBytes());
						preEle = currEle;
					}
				}
				count = 1;
			}
			count = 0;
			for (String temp : random) {
				if (temp.contains("column")) {
					if (count == 1) {
						randomFile.write(NEW_LINE.getBytes());
					}
					count = 1;
					randomFile.write(temp.getBytes());
					randomFile.write(NEW_LINE.getBytes());
				} else {
					randomFile.write((temp + ",").getBytes());
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to create decrypted file
	 */
	public static void testRead() {
		try {
			FileOutputStream f1DEC = new FileOutputStream(new File(
					FILE_WRITE_TO));

			BufferedReader identFile = new BufferedReader(new FileReader(
					new File(IDENTICAL)));

			BufferedReader incrementFile = new BufferedReader(new FileReader(
					new File(INCREMENT)));

			BufferedReader randomFile = new BufferedReader(new FileReader(
					new File(RANDOM)));

			String pos = identFile.readLine();
			String firstLine = identFile.readLine();
			List<String> spcCharList = new ArrayList<String>();

			// Get map with special splitting character
			int index = 0;
			for (char ch : firstLine.toCharArray()) {
				if ((ch + "").matches(REXEX)) {
					spcCharList.add(ch + "");
				}
				index++;
			}

			// Get Array Row and Column
			String str;
			String[] strArr = pos.split(" ");
			int row = Integer.parseInt(strArr[0].split("=")[1]);
			int column = Integer.parseInt(strArr[1].split("=")[1]);
			String[][] arrEle = new String[row][column];
			String[] contents = new String[row];

			// Make Array from identical file
			while ((str = identFile.readLine()) != null) {
				String[] arr = str.split(SEPERATOR);
				String value = arr[0];
				index = Integer.parseInt(arr[1]);
				int count = Integer.parseInt(arr[2]);
				for (int i = 0; i < count; i++) {
					arrEle[i][index] = value;
				}
			}

			// Make Array from increment file
			
			while ((str = incrementFile.readLine()) != null) {
				if (str.contains("column")) {
					column = Integer.parseInt(str.split("=")[1]);
				} else {
					int count = 0, prevEle = 0;
					String[] arr = str.split(",");
					for (int i = 0; i < row; i++) {
						String value = arr[i];
						if(count==0) {
							arrEle[i][column] = value;
							prevEle = Integer.parseInt(value);
						} else {
							arrEle[i][column] = Integer.parseInt(value) + prevEle + "";
							prevEle += Integer.parseInt(value);
						}
						count = 1;
					}
				}
				
			}

			// Make Array from random file
			while ((str = randomFile.readLine()) != null) {
				if (str.contains("column")) {
					column = Integer.parseInt(str.split("=")[1]);
				} else {
					String[] arr = str.split(",");
					for (int i = 0; i < row; i++) {
						String value = arr[i];
						arrEle[i][column] = value;
					}
				}
			}
			System.out.println("List: " + spcCharList);
			for (int i = 0; i < arrEle.length; i++) {
				StringBuilder sb = new StringBuilder();
				for (int j = 0; j < arrEle[i].length; j++) {
					System.out.print(arrEle[i][j] + "\t");
					sb.append(arrEle[i][j]);
					if (j < spcCharList.size()) {
						sb.append(spcCharList.get(j));
					}
				}
				contents[i] = sb.toString();
				System.out.println();
			}
			System.out.println("After Reading content from F1 file");
			for (int i = 0; i < contents.length; i++) {
				System.out.println(contents[i]);
				f1DEC.write((contents[i] + NEW_LINE).getBytes());
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}