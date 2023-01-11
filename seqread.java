
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;




public class seqread {

    

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
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
                            while(index != -1) {
                               
                                a[i]=index;                               
                                                      
                                System.out.print(a[i]+"\t");
                            index = prevLine.indexOf(substring, index + 1);
                            // a[i]=index;
                            i++;
                                            }
                        for(int i1=0;i1<16;i1++)
                        { 
                        delta[i1]=a[i1]-last;
                                last=a[i1];
                            System.out.print(delta[i1]+"\n");
                                  
            FileWriter writer1 = new FileWriter("d:/dataref/data/F7.txt", true);
                          
          writer1.write(delta[i1]+"\t");
     
    
           writer1.close(); 
          
          } 
                        
                        String newtext = prevLine.replaceAll("N","");
                        String newtext1 = newtext.replaceAll("A","00");
                        String newtext2 = newtext1.replaceAll("C","10");
                        String newtext3 = newtext2.replaceAll("G","10");
                        String newtext4 = newtext3.replaceAll("T","11");
                       
        
       
                        try
                        {
                            
                        FileWriter writer = new FileWriter("d:/dataref/data/seq.txt");
             writer.write(newtext4);
             writer.close();


            
         }
         catch (IOException ioe)
             {
             ioe.printStackTrace();
         }
        
        		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
                
                
	}

}

