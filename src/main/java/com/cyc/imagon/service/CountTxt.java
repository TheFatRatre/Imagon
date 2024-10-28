package com.cyc.imagon.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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
            File writeName = new File(PATH_NAME);
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
