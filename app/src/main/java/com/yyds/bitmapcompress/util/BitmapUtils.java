package com.yyds.bitmapcompress.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ThumbnailUtils;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by 阿飞の小蝴蝶 on 2022/10/12
 * Describe:
 */
public class BitmapUtils {

    /**
     * 图片压缩：质量压缩方法
     * @param beforeBitmap 要压缩的图片
     * @return 压缩后的图片
     */
     public static Bitmap compressImage(Bitmap beforeBitmap) {

        // 可以捕获内存缓冲区的数据，转换成字节数组。
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        if (beforeBitmap != null) {
            // 第一个参数：图片压缩的格式；第二个参数：压缩的比率；第三个参数：压缩的数据存放到bos中
            beforeBitmap.compress(Bitmap.CompressFormat.JPEG, 10, bos);

            // 循环判断压缩后的图片大小是否满足要求，这里限制100kb，若不满足则继续压缩，每次递减10%压缩
//            int options = 100;
//            while (bos.toByteArray().length / 1024 > 100) {
//                bos.reset();// 置为空
//                beforeBitmap.compress(Bitmap.CompressFormat.JPEG, options, bos);
//                options -= 10;
//            }

            // 从bos中将数据读出来 转换成图片
            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            Bitmap afterBitmap = BitmapFactory.decodeStream(bis);
            return afterBitmap;
        }
        return null;
    }

    /**
     * 图片压缩：获得缩略图
     * @param beforeBitmap 要压缩的图片
     * @param width 缩略图宽度
     * @param height 缩略图高度
     * @return 压缩后的图片
     */
    static public Bitmap getThumbnail(Bitmap beforeBitmap, int width, int height) {
        return ThumbnailUtils.extractThumbnail(beforeBitmap, width, height);
    }

    /**
     * 图片压缩: 按尺寸压缩
     * @param beforeBitmap 要压缩的图片
     * @param newWidth 压缩后的宽度
     * @param newHeight 压缩后的高度
     * @return 压缩后的图片
     */
    static public Bitmap compressBitmap1(Bitmap beforeBitmap, double newWidth, double newHeight) {
        // 图片原有的宽度和高度
        float beforeWidth = beforeBitmap.getWidth();
        float beforeHeight = beforeBitmap.getHeight();

        // 计算宽高缩放率
        float scaleWidth = 0;
        float scaleHeight = 0;
        if (beforeWidth > beforeHeight) {
            scaleWidth = ((float) newWidth) / beforeWidth;
            scaleHeight = ((float) newHeight) / beforeHeight;
        } else {
            scaleWidth = ((float) newWidth) / beforeHeight;
            scaleHeight = ((float) newHeight) / beforeWidth;
        }

        // 矩阵对象
        Matrix matrix = new Matrix();
        // 缩放图片动作 缩放比例
        matrix.postScale(scaleWidth, scaleHeight);
        // 创建一个新的Bitmap 从原始图像剪切图像
        Bitmap afterBitmap = Bitmap.createBitmap(beforeBitmap, 0, 0,
                (int) beforeWidth, (int) beforeHeight, matrix, true);
        return afterBitmap;
    }

    /**
     * 图片压缩: 规定尺寸等比例压缩，宽高不能超过限制要求
     * @param beforBitmap 要压缩的图片
     * @param maxWidth 最大宽度限制
     * @param maxHeight 最大高度限制
     * @return 压缩后的图片
     */
     public static Bitmap compressBitmap(Bitmap beforBitmap, double maxWidth, double maxHeight) {

        // 图片原有的宽度和高度
        float beforeWidth = beforBitmap.getWidth();
        float beforeHeight = beforBitmap.getHeight();
        if (beforeWidth <= maxWidth && beforeHeight <= maxHeight) {
            return beforBitmap;
        }

        // 计算宽高缩放率，等比例缩放
        float scaleWidth =  ((float) maxWidth) / beforeWidth;
        float scaleHeight = ((float)maxHeight) / beforeHeight;
        float scale = scaleWidth;
        if (scaleWidth > scaleHeight) {
            scale = scaleHeight;
        }
        Log.d("BitmapUtils", "before[" + beforeWidth + ", " + beforeHeight + "] max[" + maxWidth
                + ", " + maxHeight + "] scale:" + scale);

        // 矩阵对象
        Matrix matrix = new Matrix();
        // 缩放图片动作 缩放比例
        matrix.postScale(scale, scale);
        // 创建一个新的Bitmap 从原始图像剪切图像
        Bitmap afterBitmap = Bitmap.createBitmap(beforBitmap, 0, 0,
                (int) beforeWidth, (int) beforeHeight, matrix, true);
        return afterBitmap;
    }

    /**
     * 图片按比例大小压缩方法
     * @param image （根据Bitmap图片压缩）
     * @return
     */
    public static Bitmap compressScale(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        // 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
        if (baos.toByteArray().length / 1024 > 1024) {
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 80, baos);// 这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        Log.i("", w + "---------------" + h);
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        // float hh = 800f;// 这里设置高度为800f  军哥  我建议800 正常的市面手机够用
        // float ww = 800f;// 这里设置宽度为800f  军哥  我建议800 正常的市面手机够用
        float hh = 800;
        float ww = 800;
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) { // 如果高度高的话根据高度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be; // 设置缩放比例
        // newOpts.inPreferredConfig = Config.RGB_565;//降低图片从ARGB888到RGB565
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
    }
}
