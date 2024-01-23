package com.qtone.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zhangpk
 * @Description:
 * @Date:Created in 17:02 2023/7/5
 * @Modified By:
 */
public class TxtUtil {

    public static void main(String[] args) throws IOException {
        String fileName1 = "D:\\Deskop\\江西割接\\hdkt_trail_bak.sql";
        String fileName2 = "D:\\Deskop\\江西割接\\hdkt_trail_bak_jx.sql";
        quanbu(fileName1,fileName2);
    }

    public static void quanbu(String fileName1,String fileName2) throws IOException {
        String str = null;
        //int count = 0;
        //读文件
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName1))));
        //写文件
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(fileName2))));
        //读每行如果不为null
        while ((str = br.readLine()) != null) {
//              String replace = str.replace("\"type\":\"profile_set\"", "\"type\":\"profile_delete\"");
//             String replace = str.replace("name", "$name");
//              String replace = str.replace("signup_time","$signup_time");
//             String replace = str.replace("\"properties\":{","\"properties\":{\"$is_login_id\":true,");
            if (str.isEmpty() == false) {
//                String[] split = str.split("\t");
                String replace = str.replace("hdkt_trail_bak","hdkt_trail_bak_jx");
                //bufferedWriter.write("UPDATE Group_Department.Name_Group_Department_Five SET staff_department= '" + split[1] + "' and  staff_group = '" + split[2] + "'   WHERE staff_username = '" + split[0] + "'" + "\r\n");
                bufferedWriter.write(replace + "\r\n");
                //刷新
                bufferedWriter.flush();
            }
//             bufferedWriter.write("INSERT INTO Group_Department.Name_Group_Department_Two VALUES ("+split[0]+","+split[1]+","+split[2]+","+split[3]+","+split[4]+");"+"\r\n");
            // String replace = str.replace("\"", "");

            //写进文件的内容
//            bufferedWriter.write(replace + "\r\n");
//           刷新
//            bufferedWriter.flush();
        }
//        while ((str = br.readLine())!=null){
//            boolean length = str.isEmpty();
//
//            if (length==true){
//                count++;
//            }
//        }
//        System.out.println(count);
    }


    public static List<String> read(String fileName1) throws IOException {
        String str="";
        List<String> list = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName1))));
        while ((str = br.readLine()) != null) {
            if (str.isEmpty() == false) {
                list.add(str);
            }
        }
        return list;
    }

    public static List<String> write(List<String> contents, String fileName) throws IOException {
        String str="";
        List<String> list = new ArrayList<>();
        //写文件
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(fileName))));
        for(String content:contents){
            bufferedWriter.write(content + "\r\n");
            //刷新
            bufferedWriter.flush();
        }
        return list;
    }

}
