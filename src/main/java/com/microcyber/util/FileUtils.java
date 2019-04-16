package com.microcyber.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileUtils {
    public static String getResourceAsString(String fileName) {
        InputStream in = FileUtils.class.getClassLoader().getResourceAsStream(fileName);
        InputStreamReader isr = new InputStreamReader(in);
        StringBuilder sb = new StringBuilder();
        while (true) {
            int i = 0;
            try {
                i = isr.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (i == -1) {
                break;
            }
            sb.append((char) i);
        }
        return sb.toString();
    }
}
