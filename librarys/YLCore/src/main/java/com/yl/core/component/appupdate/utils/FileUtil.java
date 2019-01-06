package com.yl.core.component.appupdate.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

/**
 * Create by zm on 2018/10/30
 */

public final class FileUtil {

    /**
     * 创建保存的文件夹
     */
    public static void createDirDirectory(String downloadPath) {
        File dirDirectory = new File(downloadPath);
        if (!dirDirectory.exists()) {
            dirDirectory.mkdirs();
        }
    }

    /**
     * 创建一个随机读写
     */
    public static RandomAccessFile createRAFile(String downloadPath, String fileName) {
        //断点读写
        try {
            return new RandomAccessFile(createFile(downloadPath, fileName), "rwd");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 创建一个文件
     *
     * @param downloadPath 路径
     * @param fileName     名字
     * @return 文件
     */
    public static File createFile(String downloadPath, String fileName) {
        return new File(downloadPath, fileName);
    }

    /**
     * 查看一个文件是否存在
     *
     * @param downloadPath 路径
     * @param fileName     名字
     * @return true | false
     */
    public static boolean fileExists(String downloadPath, String fileName) {
        return new File(downloadPath, fileName).exists();
    }

    /**
     * 删除一个文件
     *
     * @param downloadPath 路径
     * @param fileName     名字
     * @return true | false
     */
    public static boolean delete(String downloadPath, String fileName) {
        return new File(downloadPath, fileName).delete();
    }
}
