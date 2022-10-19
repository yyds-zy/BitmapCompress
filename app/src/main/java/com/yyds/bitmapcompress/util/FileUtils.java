package com.yyds.bitmapcompress.util;

import android.content.Context;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 阿飞の小蝴蝶 on 2022/10/14
 * Describe:
 */
public class FileUtils {
    /**
     * 获取Bitmap路径列表
     * @return
     */
    public static List<String> getBitmapPathList(Context context) {
        List<String> bitmapPathList = new ArrayList();
        String filePath = getFilePath(context);
        if (null == filePath) {
            return null;
        }
        bitmapPathList.clear();
        File file = new File(filePath);
        File[] tempList = file.listFiles();
        if (tempList == null) {
            Toast.makeText(context,"请放置原始图片",Toast.LENGTH_SHORT).show();
            return null;
        }
        long length = tempList.length;
        if (length == 0) {
            Toast.makeText(context,"原始图片文件夹为空",Toast.LENGTH_SHORT).show();
            return null;
        }
        for (int i = 0; i < length; i++) {
            if (tempList[i].isFile()) {
                String bitmapPath = tempList[i].getAbsolutePath();
                if (bitmapPath.endsWith(".jpg") || bitmapPath.endsWith(".png")) {
                    bitmapPathList.add(bitmapPath);
                }
            }
        }
        return bitmapPathList;
    }

    /**
     * 获取Bitmap路径
     * @return
     */
    public static String getFilePath(Context context) {
        File dir = new File(Constant.ROOT_BITMAP_PATH + context.getPackageName() + Constant.ORI_BITMAP_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir.getPath();
    }
}
