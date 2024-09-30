package com.cyc.imagon.service;

import java.io.*;

/**
 * ClassName: CountTxt
 * Package: com.cyc.imagon.service
 * Description:
 *
 * @Author CYC
 * @Create 2024/4/10 20:23
 * @Version 1.0
 */
public class CountTxt {
    static final String PATH_NAME = "src/main/resources/file/count.ig";
    static int countintxt = 0;

    public int readTxt() {
        File mc = new File(PATH_NAME);
        if (!mc.exists()) {
            try {
                mc.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (FileReader reader = new FileReader(mc);
             BufferedReader br = new BufferedReader(reader)
        ) {
            String line = "0";
            line = br.readLine();
            countintxt = Integer.parseInt(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return countintxt;
    }

    public void writeTxt(int count) {
        try {
            File writeName = new File(PATH_NAME); // 相对路径，如果没有则要建立一个新的output.txt文件
            //writeName.createNewFile(); // 创建新文件,有同名的文件的话直接覆盖
            try (FileWriter writer = new FileWriter(writeName);
                 BufferedWriter out = new BufferedWriter(writer)
            ) {
                out.write(count + "\r\n"); // \r\n即为换行
                out.flush(); // 把缓存区内容压入文件
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
