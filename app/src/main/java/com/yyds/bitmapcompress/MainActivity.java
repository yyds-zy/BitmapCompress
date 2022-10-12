package com.yyds.bitmapcompress;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.yyds.bitmapcompress.util.BitmapUtils;
import com.yyds.log.util.LogUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button button;
    private String filePath;
    private List<String> bitmapPathList = new ArrayList();

    private boolean isCompressing,isPauseCompress;
    ProgressBar progressBar;
    private float progress;
    private int currentPosition = 0;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            Bitmap bitmap = BitmapFactory.decodeFile(bitmapPathList.get(what));
            imageView.setImageBitmap(bitmap);
            textView.setText("当前进度: = " + progress);
        }
    };
    private TextView textView;

    //adb push D:\zy_file\ori_pic\ /sdcard/Android/data/com.yyds.bitmapcompress
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.progress);

        textView = findViewById(R.id.tv_current_progress);
        imageView = findViewById(R.id.iv_img);

        button = findViewById(R.id.btn_pic);
        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View view) {
                isPauseCompress();
                if (!isPauseCompress) {
                    button.setText("pause");
                    //Toast.makeText(MainActivity.this,"compressing...",Toast.LENGTH_SHORT).show();
                    return;
                }
                button.setText("compress");
                bitmapPathList.clear();
                List<String> bitmapPathList = getBitmapPathList();
                new Thread(new Runnable() {

                    @Override
                    public void run() {
//                        isCompressing = true;
                        if (bitmapPathList == null) return;
                        for (int i = currentPosition; i < bitmapPathList.size(); i++) {
                            if (!isPauseCompress) {
                                isCompressing = false;
                                return;
                            }
                            currentPosition = i;
                            progress = 100 * (currentPosition + 1) / bitmapPathList.size();
                            Log.d("xuezhiyuan", progress +"");
                            progressBar.setProgress((int) progress);
                            Bitmap bitmap = BitmapFactory.decodeFile(bitmapPathList.get(i));
                            Bitmap afterBitmap = BitmapUtils.compressImage(bitmap);
                            saveBitmap(MainActivity.this, afterBitmap);
                            Message message = handler.obtainMessage();
                            message.what = i;
                            handler.sendMessage(message);
//                            if (progress == 100) {
//                                isCompressing = false;
//                            }
                        }
                    }
                }).start();
            }
        });
    }

    public void isPauseCompress() {
        if (isPauseCompress) {
            isPauseCompress = false;
        } else {
            isPauseCompress = true;
        }
    }

    /**
     *
     * @return
     */
    public List<String> getBitmapPathList() {
        filePath = getFilePath(this);
        if (null == filePath) {
            return null;
        }
        bitmapPathList.clear();
        File file = new File(filePath);
        File[] tempList = file.listFiles();
        if (tempList == null) {
            Toast.makeText(this,"请放置原始图片",Toast.LENGTH_SHORT).show();
            return null;
        }
        long length = tempList.length;
        if (length == 0) {
            Toast.makeText(this,"原始图片文件夹为空",Toast.LENGTH_SHORT).show();
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

    public String getFilePath(Context context) {
        //data/data/com.yyds.viewmodel_livedata/
        File dir = new File("sdcard/Android/data/" + context.getPackageName() + "/ori_pic/");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir.getPath();
    }


    public void saveBitmap(Context context,Bitmap bitmap) {
        String savePath = "sdcard/Android/data/" + context.getPackageName() + "/generate_pic/";
        File filePic;
        try {
            filePic = new File(savePath + System.currentTimeMillis() + ".jpg");
            if (!filePic.exists()) {
                filePic.getParentFile().mkdirs();
                filePic.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(filePic);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.v("saveBitmap: failed " + e.getMessage());
            return;
        }
        LogUtils.v("saveBitmap: " + filePic.getAbsolutePath());
    }
}