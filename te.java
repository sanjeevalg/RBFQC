/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication8;

/**
 *
 * @author str567
 */
public class te {
    public static void main(String args[]){
        
    int MAX_ARR_NUM=10;
    
    int []point;
        point=new int[100];
        int []loc_next;
        loc_next=new int[100];
        int SUB_STR_LEN=4;
       byte[] ref_seq_code = { 0, 0, 3, 2, 3, 0,2,2 };
       
        for (int i = 0; i < MAX_ARR_NUM; i++) {
            point[i] = -1;
           System.out.println(point[i]);
        }
        long value = 0;
        for (int k = SUB_STR_LEN - 1; k >= 0; k--) {
            value <<= 2;
            value += ref_seq_code[k];
            //System.out.println(ref_seq_code[k]);  
        }
        System.out.println(value);
        int id = (int)(value&(long)(MAX_ARR_NUM - 1));
        System.out.println(id);
        loc_next[0] = point[id];
        System.out.println(loc_next[0]);
        point[id] = 0;
        System.out.println(point[id]);
for (int i = 0; i < MAX_ARR_NUM; i++)
{
            
           System.out.println(point[i]);
}
        int step_len = 8 - SUB_STR_LEN + 1;
        int shift_bit_num = SUB_STR_LEN*2-2;
        int one_sub_str = SUB_STR_LEN - 1;

        for (int i = 1; i < step_len; i++) {
            value >>= 2;
            System.out.println(value);
            value += ((long)ref_seq_code[i + one_sub_str]<<shift_bit_num);
            System.out.println(value);

            id = (int)(value&(long)(MAX_ARR_NUM - 1));
            System.out.println(id);
            loc_next[i] = point[id];
            point[id] = i;
    // System.out.println(loc_next[i]+"point="+point[id]);
 }
    for (int i = 0; i < MAX_ARR_NUM; i++)
{
            
           System.out.println(point[i]+"\t"+loc_next[i]);
}
    }}
