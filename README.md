# BitmapCompress
图片批量压缩

## 导入图片
adb push D:\zy_file\ori_pic\ /sdcard/Android/data/com.yyds.bitmapcompress  需要注意可以看到对应文件夹，我这边是集成了之前的[FlyingLogFrame](https://github.com/yyds-zy/LogFrame)框架，在Application中默认初始化创建了外部存储私有路径。

## 导出图片
adb pull /sdcard/Android/data/com.yyds.bitmapcompress/generate_pic  导出图片文件夹到PC

## 质量压缩
通过控制compress的第二个参数quality来设置图片质量，quality值在0~100之间。值越大图片越清晰，图片越大。
可以用过while循环判断压缩后的图片大小是否满足要求，可以限制想要的图片大小，如果给后台图片服务器提供150kb即可，若不满足则继续压缩，每次递减10%压缩，切不可小于0。
```
            int options = 100;
            while (bos.toByteArray().length / 1024 > 100) {
                bos.reset();// 置为空
                beforeBitmap.compress(Bitmap.CompressFormat.JPEG, options, bos);
                options -= 10;
            }
```
## 等比例压缩
这里需要注意现在市面上流行的分辨率已经很高了，为了取中间值建议设置1080.在安卓发展过程中16-17年时建议取800.
如果宽度大的话根据宽度固定大小缩放,如果高度高的话根据高度固定大小缩放. 取缩放比。设置给BitmapFactory.Options.
通过BitmapFactory.decodeStream得到等比例压缩后的图片。
