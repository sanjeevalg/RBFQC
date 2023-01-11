//package com.test;



//package com.test;
import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;




public class seqread1 {

    private static int contentOfEntry;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Inside main method");
		try {			
			
			BufferedReader br = new BufferedReader(new FileReader(new File("d:/dataref/data/F2.txt")));
			String prevLine = br.readLine();
			String nextLine;
			System.out.println(prevLine);
                        //String theString = "is this good or is this bad?";
                        String substring = "N";

                        int index = prevLine.indexOf(substring);
                        int i=0;
                        int a[]=new int[100];
                        int delta[]=new int[100];
                        int last=0;
                        FileWriter writer1 = new FileWriter("d:/dataref/data/F7.txt");
                            while(index != -1) {
                                
                               
                              //  a[i]=index;                               
                                                      
                          //  System.out.print(a[i]+"\t");
                            
                            int count = 0;
                            int dif = index - last+1;
                            while(prevLine.charAt(index) == 'N') {
                            	count++;
                            	index++;
                            }
                            
                            last = index;
                            writer1.write(dif+"="+count+",");
                            System.out.println(dif+"="+count+",");
                            
                            index = prevLine.indexOf(substring, index + 1);
                            // a[i]=index;
                            //i++;
                            }
                            writer1.close();
                  /*          int count = 1;
                            
                            boolean preVal = true;
                        for(int i1=0;i1<20;i1++)
                        { 
                        delta[i1]=a[i1]-last;
                        //if(delta[i1]<=0)break;
                                last=a[i1];
                            System.out.print(delta[i1]+"\n");
                              try{    
                            	  FileWriter writer1 = new FileWriter("e:\\F7.txt", true);
                          if(delta[i1] == 1) {
                        	  count++;
                          } else {
                        	  if(preVal) {
                        		  writer1.write(delta[i1]+"=");
                        		  preVal = false;
                              } else {
                            	  writer1.write(count+","+delta[i1]+"=");
                            	  count=1;
                              }
                        	 
                          }
          
     
    
           writer1.close(); 
          
          }catch(Exception e){System.out.println(e);}    
          System.out.println("Success..."); 
                        }
                        String newtext = prevLine.replaceAll("N","");
                        String newtext1 = newtext.replaceAll("A","00");
                        String newtext2 = newtext1.replaceAll("C","10");
                        String newtext3 = newtext2.replaceAll("G","10");
                        String newtext4 = newtext3.replaceAll("T","11");
                       
        
       
                        try
                        {
                            
                        FileWriter writer = new FileWriter("e:\\seq.txt");
             writer.write(newtext4);
             writer.close();

*/
            
         }
         catch (IOException ioe)
             {
             ioe.printStackTrace();
         }
        
        	
                
                
	}

}
