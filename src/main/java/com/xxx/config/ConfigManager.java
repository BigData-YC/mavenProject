package com.xxx.config;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.Properties;

/**
 * @ClassName: ConfigManager
 * @Description: TODO
 * @Author: Yangchong
 * @Data: 2019/6/25 12:55
 * @Version: 1.0
 **/

public class ConfigManager {
    static Logger logger = Logger.getLogger(ConfigManager.class);
    public static String conf;
    public static String bin;
    public static String dev;

    public static void init(String[] args) {
        if (args.length != 0) {
            for (int i = 0; i < args.length; i++) {
                if ("-Denv".equalsIgnoreCase(args[i])) {
                    if (args.length > i + 1) {
                        dev = args[i + 1].toLowerCase();
                        System.setProperty("runENV", args[i + 1].toLowerCase());
                    }
                }
                if ("-config".equalsIgnoreCase(args[i])) {
                    if (args.length > i + 1) {
                        conf = args[i + 1].toLowerCase();
                    }
                }
                if ("-class".equalsIgnoreCase(args[i])) {
                    if (args.length > i + 1) {
                        bin = args[i + 1].toLowerCase();
                    }
                }
            }
        }
    }

    public InputStream getFile2IO(String fileName) {
        InputStream inputstream = null;
        String filePath = null;
        try {
            filePath = conf + File.separator + dev + File.separator + fileName;
            System.out.println("load file:" + filePath);
            inputstream = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            logger.warn(fileName + e);
        }
        return inputstream;
    }

    /**
     * 根据文件名字获取配置
     *
     * @param fileName
     * @return
     */
    public Properties getFile2Properties(String fileName) {
        Properties pro = new Properties();
        try {
            pro.load(getFile2IO(fileName));
        } catch (IOException e) {
            logger.warn(fileName + e);
        }
        return pro;
    }

    public Properties getFile2Properties(File fileName) {
        Properties pro = new Properties();
        InputStream inputstream = null;
        try {
            inputstream = new FileInputStream(fileName);
            pro.load(inputstream);
        } catch (IOException e) {
            logger.warn(fileName.getAbsolutePath() + e);
        } finally {
            try {
                inputstream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return pro;
    }

    public static void main(String[] args) {
        ConfigManager.init(args);
        System.out.println(ConfigManager.bin);
    }
}
