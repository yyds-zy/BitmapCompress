package com.yyds.bitmapcompress;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import com.yyds.bitmapcompress.util.BitmapUtils;
import com.yyds.bitmapcompress.util.Constant;
import com.yyds.bitmapcompress.util.FileUtils;
import com.yyds.bitmapcompress.util.SharedPreferenceUtil;
import com.yyds.log.util.LogUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.SecretKey;

import biz.laenger.android.vpbs.BottomSheetUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView;
    private Button buttonQuality, buttonQualityPause;
    private Button buttonProportion, buttonProportionPause;
    private Button buttonBlend, buttonBlendPause;
    private Button buttonSetting;
    private LinearLayout ll_quality,ll_proportion,ll_blend;
    private boolean isCompressing;
    private SeekBar seekBar;
    private TextView tvCurrentProgress;
    private float progress;
    private int currentPosition = 0;
    private List<String> mBitmapPathList = new ArrayList();
    private boolean isQualityPause;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            Bitmap bitmap = BitmapFactory.decodeFile(mBitmapPathList.get(what));
            imageView.setImageBitmap(bitmap);
            tvCurrentProgress.setText("当前压缩进度: = " + progress);
            if (progress == 100) {
                Toast.makeText(MainActivity.this,"压缩完成",Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        seekBar = findViewById(R.id.seekbar);
        seekBar.setOnTouchListener((view, motionEvent) -> true);
        imageView = findViewById(R.id.iv_img);
        tvCurrentProgress = findViewById(R.id.tv_current_progress);
        buttonSetting = findViewById(R.id.btn_setting);

        buttonQuality = findViewById(R.id.btn_quality_compress);
        buttonProportion = findViewById(R.id.btn_proportion_compress);
        buttonBlend = findViewById(R.id.btn_blend_compress);

        buttonQualityPause = findViewById(R.id.btn_quality_pause);
        buttonProportionPause = findViewById(R.id.btn_proportion_pause);
        buttonBlendPause = findViewById(R.id.btn_blend_pause);

        ll_quality = findViewById(R.id.lin_quality);
        ll_proportion = findViewById(R.id.lin_proportion);
        ll_blend = findViewById(R.id.lin_blend);

        buttonSetting.setOnClickListener(this);

        buttonQuality.setOnClickListener(this);
        buttonProportion.setOnClickListener(this);
        buttonBlend.setOnClickListener(this);

        buttonQualityPause.setOnClickListener(this);
        buttonProportionPause.setOnClickListener(this);
        buttonBlendPause.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int quality = SharedPreferenceUtil.getInstance().getInt(SharedPreferenceUtil.SP_CONFIG, SharedPreferenceUtil.KEY_QUALITY);
        switch (view.getId()) {
            case R.id.btn_setting:
                startActivity(new Intent(MainActivity.this,SettingActivity.class));
                break;
            case R.id.btn_quality_compress:
                if (isCompressing) {
                    Toast.makeText(MainActivity.this,"正在压缩中,请不要频繁点击",Toast.LENGTH_SHORT).show();
                    return;
                }

                mBitmapPathList.clear();
                mBitmapPathList = FileUtils.getBitmapPathList(this);
                new Thread(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void run() {
                        isCompressing = true;
                        if (mBitmapPathList == null) return;
                        int i = currentPosition;
                        do {
                            currentPosition = i;
                            progress = 100 * (currentPosition + 1) / mBitmapPathList.size();
                            seekBar.setProgress((int) progress,true);

                            long start = System.currentTimeMillis();
                            Bitmap bitmap = BitmapFactory.decodeFile(mBitmapPathList.get(i));
                            long haha1 = System.currentTimeMillis();
                            long cost1 = haha1 - start;
                            Log.d("haha","路径转图片花费时间" + cost1 + "ms");

                            BitmapUtils.saveBitmap(MainActivity.this, bitmap,quality);
                            long end = System.currentTimeMillis();
                            long cost2 = end - haha1;
                            Log.d("haha","保存且质量压缩花费时间" + cost2 + "ms");

                            Message message = handler.obtainMessage();
                            message.what = i;
                            handler.sendMessage(message);

                            if (!isQualityPause) {
                                i++;
                            }

                            if (progress == 100) {
                                isCompressing = false;
                            }
                        } while (i < mBitmapPathList.size());
                    }
                }).start();
                break;
            case R.id.btn_quality_pause:
                if (isQualityPause) {
                    isQualityPause = false;
                    buttonQualityPause.setText("暂停");
                } else {
                    isQualityPause = true;
                    buttonQualityPause.setText("继续");
                }
                break;
            case R.id.btn_proportion_compress:

                break;
            case R.id.btn_proportion_pause:

                break;
            case R.id.btn_blend_compress:

                break;
            case R.id.btn_blend_pause:

                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        int mode = SharedPreferenceUtil.getInstance().getInt(SharedPreferenceUtil.SP_CONFIG, SharedPreferenceUtil.KEY_MODE);
        ll_blend.setVisibility(View.GONE);
        ll_proportion.setVisibility(View.GONE);
        ll_quality.setVisibility(View.GONE);
        switch (mode) {
            case Constant.MODE_QUALITY:
                ll_quality.setVisibility(View.VISIBLE);
                break;
            case Constant.MODE_PROPORTION:
                ll_proportion.setVisibility(View.VISIBLE);
                break;
            case Constant.MODE_BLEND:
                ll_blend.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }
}